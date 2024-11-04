/*
 * ExporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import application.EchangeurDeVue;
import application.utilitaire.CryptageVigenere;
import application.utilitaire.DecryptageVigenere;
import application.utilitaire.GestionDeFichier;
import application.utilitaire.TraitementDonnees;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * Contrôleur pour la gestion de l'exportation des données.
 * 
 * Cette classe permet à l'utilisateur de se connecter à un serveur 
 * en fournissant une adresse IP et un port. Elle gère également 
 * la validation des entrées et l'affichage des erreurs éventuelles.
 * 
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
     * l'interface utilisateur.
     */
    @FXML
    public void initialize() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            labelIp.setText(socket.getLocalAddress().getHostAddress());
        } catch (Exception e) {
            Alert boiteIpInconnu = new Alert(Alert.AlertType.ERROR, 
                    "Impossible de connaître l'adresse IP de l'interface Ethernet", 
                    ButtonType.OK);
            boiteIpInconnu.setTitle("Erreur adresse IP inconnue");
            boiteIpInconnu.setHeaderText("Erreur adresse IP inconnue");
            boiteIpInconnu.showAndWait(); 
        }
    } 

    @FXML
    void btnExporterAction(ActionEvent event) {
        // Clé de chiffrement
        String key = JOptionPane.showInputDialog("Entrez la clé de cryptage Vigenère :");

        // Chemins des fichiers temporaire et final
        String fichierTemp = "donnees_temp.dat";
        String fichierFinal = "donnees_chiffrees.dat";

        try {
            // Enregistrement des données formatées dans le fichier temporaire
            sauvegarderDonneesFormatees(fichierTemp);
            
            // Chiffrement du fichier temporaire et écriture dans le fichier final
            crypterDonneesVigenere(fichierTemp, fichierFinal, key);
            
            System.out.println("Données chiffrées exportées dans " + fichierFinal);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'exportation des données.");
        }
    }
    
    /**
     * Sauvegarde les données formatées des ArrayLists dans un fichier temporaire.
     */
    private void sauvegarderDonneesFormatees(String nomFichier) throws IOException {
        File fichier = new File(nomFichier);
        if (!fichier.exists()) {
            if (!fichier.createNewFile()) {
                throw new IOException("Échec de la création du fichier temporaire : " + nomFichier);
            }
        }
        
        try (FileWriter writer = new FileWriter(fichier)) {
            // Sauvegarde de chaque liste formatée dans le fichier
            ecrireListeFormattee(writer, TraitementDonnees.getExpositions());
            ecrireListeFormattee(writer, TraitementDonnees.getEmployes());
            ecrireListeFormattee(writer, TraitementDonnees.getConferenciers());
            ecrireListeFormattee(writer, TraitementDonnees.getClients());
            ecrireListeFormattee(writer, TraitementDonnees.getVisites());
        }
    }

    /**
     * Écrit chaque élément d'une liste formatée dans le fichier.
     */
    private void ecrireListeFormattee(FileWriter writer, ArrayList<?> liste) throws IOException {
        for (Object objet : liste) {
            writer.write(objet.toString() + "\n");  // Conversion en texte formaté et ajout de saut de ligne
        }
    }
    
    /**
     * Lit le fichier temporaire, chiffre son contenu, et l'enregistre dans le fichier final.
     */
    private void crypterDonneesVigenere(String fichierSource, String fichierDestination, String key) throws IOException {
        CryptageVigenere chiffreurVigenere = new CryptageVigenere(key);

        File fichierChiffre = new File(fichierDestination);
        if (!fichierChiffre.exists()) {
            if (!fichierChiffre.createNewFile()) {
                throw new IOException("Échec de la création du fichier chiffré : " + fichierDestination);
            }
        }

        try (
            BufferedReader reader = new BufferedReader(new FileReader(fichierSource));
            FileWriter writer = new FileWriter(fichierChiffre)
        ) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String ligneChiffree = chiffreurVigenere.cryptage(ligne);  // Chiffrement de la ligne
                writer.write(ligneChiffree + "\n");  // Écriture de la ligne chiffrée dans le fichier de destination
            }
        }
    }
    
    /**
     * Déchiffre un fichier .dat crypté par Vigenère et enregistre le contenu déchiffré.
     *
     * @param fichier Le fichier .dat chiffré à déchiffrer.
     * @param gestionFichiers Utilitaire pour la gestion des fichiers.
     * @param key La clé de déchiffrement Vigenère.
     * @throws IOException En cas d'erreur de lecture ou d'écriture de fichier.
     */
    private void decrypterFichierVigenere(File fichier, GestionDeFichier gestionFichiers, String key) throws IOException {
        String contenuCrypte = gestionFichiers.readFile(fichier.getAbsolutePath());
        if (contenuCrypte.isEmpty()) {
            System.err.println("Le fichier est vide ou introuvable : " + fichier.getAbsolutePath());
            return;
        }

        DecryptageVigenere dechiffreurVigenere = new DecryptageVigenere(key);
        String contenuDecrypte = dechiffreurVigenere.decrypt(contenuCrypte);

        String cheminFichierDecrypte = fichier.getParent() + File.separator + "decrypted_" 
                + fichier.getName().replace(".dat", "_decrypted.dat");

        gestionFichiers.writeFile(cheminFichierDecrypte, contenuDecrypte);
        System.out.println("Fichier décrypté avec succès : " + cheminFichierDecrypte);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Fichier décrypté avec succès : " + cheminFichierDecrypte, ButtonType.OK);
        alert.setTitle("Déchiffrement réussi");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    void btnAideAction(ActionEvent event) {
        AccueilControleur.lancerAide();
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }
}
