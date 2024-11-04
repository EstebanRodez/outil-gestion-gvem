/*
 * ExporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import application.EchangeurDeVue;
import application.utilitaire.CryptageVigenere;
import application.utilitaire.DecryptageVigenere;
import application.utilitaire.GestionDeFichier;
import application.utilitaire.Serveur;
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

    private void crypterFichierVigenere(File fichier,
            GestionDeFichier gestionFichiers, String key) throws IOException {
        
        String contenu = gestionFichiers.readFile(fichier.getAbsolutePath());
        CryptageVigenere chiffreurVigenere = new CryptageVigenere(key);
        String contenuCrypte = chiffreurVigenere.cryptage(contenu);

        String cheminFichierCrypte
        = fichier.getParent() + File.separator + "vigenere_encrypted_"
          + fichier.getName().replace(".csv", ".bin");
        gestionFichiers.writeFile(cheminFichierCrypte, contenuCrypte);
        System.out.println("Fichier crypté avec succès : "
                           + cheminFichierCrypte);
    }

    private void decrypterFichierVigenere(File fichier,
            GestionDeFichier gestionFichiers, String key) throws IOException {
        
        String contenuCrypte
        = gestionFichiers.readFile(fichier.getAbsolutePath());
        DecryptageVigenere dechiffreurVigenere = new DecryptageVigenere(key);
        String contenuDecrypte = dechiffreurVigenere.decrypt(contenuCrypte);

        String cheminFichierDecrypte
        = fichier.getParent() + File.separator+ "vigenere_decrypted_"
          + fichier.getName().replace(".bin", ".csv");
        gestionFichiers.writeFile(cheminFichierDecrypte, contenuDecrypte);
        System.out.println("Fichier décrypté avec succès : "
                           + cheminFichierDecrypte);
    }

    @FXML
    void btnAideAction(ActionEvent event) {
        AccueilControleur.lancerAide();
    }

    @FXML
    void btnExporterAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir des fichiers CSV ou BIN à exporter");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Fichiers CSV ou BIN", "*.csv",
                                            "*.bin"));

        List<File> fichiersSelectionnes
        = fileChooser.showOpenMultipleDialog(EchangeurDeVue.getFenetreAppli());

        if (fichiersSelectionnes == null || fichiersSelectionnes.isEmpty()) {
            
            Alert alert
            = new Alert(Alert.AlertType.WARNING, "Aucun fichier sélectionné.",
                        ButtonType.OK);
            alert.setTitle("Alerte");
            alert.setHeaderText("Aucun fichier sélectionné");
            alert.showAndWait();
            return;
        }

        String key
        = JOptionPane.showInputDialog("Entrez la clé de cryptage Vigenère :");

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
            } catch (IOException e) {
                Logger.getLogger(
                    ExporterControleur.class.getName()).log(Level.SEVERE,
                                                            null, e);
            }
        }

        // Envoi des fichiers cryptés
        for (File fichier : fichiersSelectionnes) {
            
            String cheminFichier
            = fichier.getParent() + (fichier.getName().endsWith(".csv") 
              ? "/vigenere_encrypted_"
                + fichier.getName().replace(".csv", ".bin")
              : "/vigenere_decrypted_"
                + fichier.getName().replace(".bin", ".csv"));
            
            Serveur.envoyerFichiers(65432, new String[]{cheminFichier});
        }
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }
} 
