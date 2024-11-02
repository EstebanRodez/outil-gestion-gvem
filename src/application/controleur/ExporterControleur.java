/*
 * ExporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import application.utilitaire.CryptageVigenere;
import application.utilitaire.DecryptageVigenere;
import application.utilitaire.GestionDeFichier;
import application.utilitaire.Serveur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Contrôleur pour la gestion de l'exportation des données.
 * 
 * Cette classe permet à l'utilisateur de se connecter à un serveur 
 * en fournissant une adresse IP et un port. Elle gère également 
 * la validation des entrées et l'affichage des erreurs éventuelles.
 * 
 * @author Baptiste Thenieres
 * @author Romain Augé
 * @version 1.0
 */
public class ExporterControleur {
    
    private Stage fenetreAppli;

    /**
     * Définit la fenêtre de l'application.
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
        this.fenetreAppli = fenetreAppli;
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

    @FXML
    void btnAideAction(ActionEvent event) {
        final String LIEN_REGLES
            = "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?usp=sharing";

        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(LIEN_REGLES));
        } catch (IOException | URISyntaxException e) {
            Alert boiteErreurInconnueOuverture =
                    new Alert(Alert.AlertType.ERROR, 
                              "Impossible d'ouvrir le fichier d'aide",
                              ButtonType.OK);

            boiteErreurInconnueOuverture.setTitle("Erreur d'affichage aide");
            boiteErreurInconnueOuverture.setHeaderText("Erreur d'affichage aide");

            boiteErreurInconnueOuverture.showAndWait();
        }
    }
    
    @FXML
    void btnExporterAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir des fichiers CSV ou BIN à exporter");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV ou BIN", "*.csv", "*.bin"));
        
        List<File> fichiersSelectionnes = fileChooser.showOpenMultipleDialog(fenetreAppli);
        
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
    void btnRetourAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/accueilVue.fxml"));
        Parent accueilVue = loader.load();
        AccueilControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(accueilVue));
    }
} 
