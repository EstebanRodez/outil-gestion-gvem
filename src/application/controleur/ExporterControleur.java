/*
 * ExporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;


import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import application.utilitaire.CryptageVigenere;
import application.utilitaire.DecryptageVigenere;
import application.utilitaire.GestionDeFichier;
import application.utilitaire.Serveur;
import java.net.DatagramSocket;
import java.net.InetAddress;
import application.EchangeurDeVue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

/**
 * Contrôleur pour la gestion de l'exportation des données.
 * 
 * Cette classe permet à l'utilisateur de se connecter à un serveur 
 * en fournissant une adresse IP et un port. Elle gère également 
 * la validation des entrées et l'affichage des erreurs éventuelles.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class ExporterControleur {


	@FXML
	private Button btnAide;

	@FXML
	private Button btnExporter;

	@FXML
	private Button btnRetour;

	@FXML
	private Label labelIp;

	@FXML
	private Label labelPort;

	/**
	 * Méthode d'initialisation appelée après le chargement de 
	 * l'interface utilisateur. Cette méthode est utilisée pour 
	 * configurer les éléments de l'interface,
	 * initialiser les données, et définir les actions des boutons.
	 */
	@FXML
	public void initialize() {
		InetAddress[] listeAdresses;

		try(DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			labelIp.setText(socket.getLocalAddress().getHostAddress());

		} catch (Exception e) {  
			Alert boiteIpInconnu =
					new Alert(Alert.AlertType.ERROR, 
							"Impossible de connaître l'adresse "
									+ "IP de l'interface Ethernet",
									ButtonType.OK);
			boiteIpInconnu.setTitle("Erreur adresse IP inconnue");
			boiteIpInconnu.setHeaderText("Erreur adresse IP inconnue");

			boiteIpInconnu.showAndWait(); 
		}
	} 

	private void crypterFichierVigenere(File fichier, GestionDeFichier gestionFichiers, String key) throws Exception {
		String contenu = gestionFichiers.readFile(fichier.getAbsolutePath());
		CryptageVigenere chiffreurVigenere = new CryptageVigenere(key);
		String contenuCrypte = chiffreurVigenere.cryptage(contenu);

		String cheminFichierCrypte = fichier.getParent() + File.separator + "vigenere_encrypted_" + fichier.getName().replace(".csv", ".bin");
		gestionFichiers.writeFile(cheminFichierCrypte, contenuCrypte);
		System.out.println("Fichier crypté avec succès : " + cheminFichierCrypte);
	}

	private void decrypterFichierVigenere(File fichier, GestionDeFichier gestionFichiers, String key) throws Exception {
		String contenuCrypte = gestionFichiers.readFile(fichier.getAbsolutePath());
		DecryptageVigenere dechiffreurVigenere = new DecryptageVigenere(key);
		String contenuDecrypte = dechiffreurVigenere.decrypt(contenuCrypte);

		String cheminFichierDecrypte = fichier.getParent() + File.separator + "vigenere_decrypted_" + fichier.getName().replace(".bin", ".csv");
		gestionFichiers.writeFile(cheminFichierDecrypte, contenuDecrypte);
		System.out.println("Fichier décrypté avec succès : " + cheminFichierDecrypte);
	}

	/**
	 * Vérifie si une adresse IPv4 donnée appartient à la classe A.
	 * Les adresses de classe A sont celles dont le premier octet 
	 * est compris entre 1 et 126 inclus. La méthode divise l'adresse IP 
	 * en octets, extrait le premier octet,et détermine s'il appartient
	 * à la plage de la classe A.
	 * 
	 * @param ipAddress L'adresse IPv4 à vérifier 
	 *        sous forme de chaîne (format "xxx.xxx.xxx.xxx").
	 * @return true si l'adresse IP appartient à la classe A, sinon false.
	 * @throws NumberFormatException si l'adresse IP contient des valeurs 
	 *         non numériques.
	 * @throws ArrayIndexOutOfBoundsException si l'adresse IP 
	 *         n'est pas correctement formatée (moins de 4 octets).
	 */
	private static boolean isClassA(String ipAddress) {
		String[] octets;
		int firstOctet;
		try {
			// Diviser l'adresse IP en octets
			octets = ipAddress.split("\\.");

			// Extraire le premier octet et le convertir en entier
			firstOctet = Integer.parseInt(octets[0]);

			// Vérifier si le premier octet est compris entre 1 et 126
			return firstOctet >= 1 && firstOctet <= 126;
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			// En cas d'erreur (par exemple, adresse IP mal formée)
			return false;
		}
	}


	@FXML
	void btnAideAction(ActionEvent event) {
		AccueilControleur.lancerAide();
	}

	@FXML
	void btnExporterAction(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir des fichiers CSV ou BIN à exporter");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV ou BIN", "*.csv", "*.bin"));

		List<File> fichiersSelectionnes = fileChooser.showOpenMultipleDialog(EchangeurDeVue.getFenetreAppli());

		if (fichiersSelectionnes == null || fichiersSelectionnes.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Aucun fichier sélectionné.", ButtonType.OK);
			alert.setTitle("Alerte");
			alert.setHeaderText("Aucun fichier sélectionné");
			alert.showAndWait();
			return;
		}

		String key = JOptionPane.showInputDialog("Entrez la clé de cryptage Vigenère :");

		GestionDeFichier gestionFichiers = new GestionDeFichier();

		for (File fichier : fichiersSelectionnes) {
			try {
				if (fichier.getName().endsWith(".bin")) {
					// Décryptage si le fichier est en .bin
					decrypterFichierVigenere(fichier, gestionFichiers, key);
				} else {
					// Cryptage si le fichier est en .csv
					crypterFichierVigenere(fichier, gestionFichiers, key);
				}
			} catch (Exception e) {
				Logger.getLogger(ExporterControleur.class.getName()).log(Level.SEVERE, null, e);
			}
		}

		// Envoi des fichiers cryptés
		for (File fichier : fichiersSelectionnes) {
			String cheminFichier = fichier.getParent() + (fichier.getName().endsWith(".csv") 
					? "/vigenere_encrypted_" + fichier.getName().replace(".csv", ".bin")
							: "/vigenere_decrypted_" + fichier.getName().replace(".bin", ".csv"));
			Serveur.envoyerFichiers(65432, new String[]{cheminFichier});
		}
	}

	@FXML
	void btnRetourAction(ActionEvent event) {
		EchangeurDeVue.changerVue("accueilVue");

	}
} 
