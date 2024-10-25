/*
 * ImporterDistantControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import application.utilitaire.Client;
import application.utilitaire.ImportationCSV;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Contrôleur pour la gestion de l'importation de données à distance.
 * 
 * Cette classe permet à l'utilisateur de spécifier une adresse IP et un port
 * pour se connecter à un serveur distant. Elle inclut également des 
 * fonctionnalités pour afficher des règles d'utilisation via un lien.
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class ImporterDistantControleur {
    
    private Stage fenetreAppli;
    
    private static String[] CHEMIN_FICHIER_CSV_RECU = {
                                        "recu/expositions 28_08_24 17_26.csv",
                                        "recu/employes 28_08_24 17_26.csv",
                                        "recu/conferencier 28_08_24 17_26.csv", 
                                        "recu/visites 28_08_24 17_26.csv"};
    
    /**
     * Définit la fenêtre de l'application.
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
      this.fenetreAppli = fenetreAppli;
    }
    
    /**
     * Vérifie si une adresse IP est valide
     * @param ip Adresse IP sous forme de chaîne
     * @return true si l'adresse est valide, sinon false
     */
    private boolean isValidIPAddress(String ip) {
        // Expression régulière pour vérifier une adresse IPv4
        String ipPattern = 
            "^([0-9]{1,3}\\.){3}[0-9]{1,3}$";
        
        if (!ip.matches(ipPattern)) {
            return false;
        }

        String[] segments = ip.split("\\.");
        for (String segment : segments) {
            int value = Integer.parseInt(segment);
            if (value < 0 || value > 255) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Vérifie si le port est valide
     * @param port Chaîne représentant le port
     * @return true si le port est un entier valide entre 0 et 65535, sinon false
     */
    private boolean isValidPort(String port) {
        try {
            int portValue = Integer.parseInt(port);
            return portValue >= 0 && portValue <= 65535;
        } catch (NumberFormatException e) {
            return false;  // Si la chaîne n'est pas un entier, retourne false
        }
    }
    
    @FXML
    private Button btnAide;

    @FXML
    private Button btnConnexion;

    @FXML
    private Button btnRetour;

    @FXML
    private TextField txtFieldIPServeur;

    @FXML
    private TextField txtFieldPort;

    @FXML
    void btnAideAction(ActionEvent event) {

        AccueilControleur.lancerAide();
    }

    @FXML
    void btnConnexionAction(ActionEvent event) throws IOException {
        String ipServeur = txtFieldIPServeur.getText().trim();
        String port = txtFieldPort.getText().trim();

        if (isValidIPAddress(ipServeur) && isValidPort(port)) {
            // Réception des fichiers depuis le serveur
            Client.recevoirFichiers(ipServeur, Integer.parseInt(port), CHEMIN_FICHIER_CSV_RECU);

            // Traitement des fichiers reçus
            List<File> fichiersSelectionnes = new ArrayList<>();
            for (String cheminFichier : CHEMIN_FICHIER_CSV_RECU) {
                fichiersSelectionnes.add(new File(cheminFichier));
            }

            // Vérification si les fichiers ont été sélectionnés et traitement
            if (!fichiersSelectionnes.isEmpty()) {
                StringBuilder nomsFichiers = new StringBuilder();
                for (File fichier : fichiersSelectionnes) {
                    try {
                        // Importer et traiter les données CSV
                        ImportationCSV.importerDonnees(fichier.getAbsolutePath());

                        // Ajouter le nom du fichier traité à la liste
                        nomsFichiers.append(fichier.getName()).append("\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // Afficher les noms des fichiers traités (facultatif)
                System.out.println("Fichiers traités : \n" + nomsFichiers.toString());
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/importerDistantValideVue.fxml"));
            Parent importerDistantValideVue = loader.load();
            ImporterDistantValideControleur controleur = loader.getController();
            controleur.setFenetreAppli(fenetreAppli);
            fenetreAppli.setScene(new Scene(importerDistantValideVue));
        } else if (isValidIPAddress(ipServeur) && !isValidPort(port)) {
            Alert boiteAlerte = new Alert(Alert.AlertType.ERROR,"Veuillez "
                    + "respecter la norme d'écriture "
                    + "d'un port :"
                    + "\n entre 0 et 65535");

            boiteAlerte.setTitle("Erreur sur le port");
            boiteAlerte.setHeaderText("Erreur sur le port");
            boiteAlerte.showAndWait();

        } else {
            Alert boiteAlerte = new Alert(Alert.AlertType.ERROR,"Veuillez "
                    + "respecter la norme d'écriture "
                    + "d'une adresse ip "
                    + "\n(exemple : 192.168.2.3)."
                    + " \noctet maximum = 255");

            boiteAlerte.setTitle("Erreur sur l'adresse IP");
            boiteAlerte.setHeaderText("Erreur sur l'adresse IP");
            boiteAlerte.showAndWait();
        }
    }

    @FXML
    void btnRetourAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/importerVue.fxml"));
        Parent importerVue = loader.load();
        ImporterControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(importerVue));
    }

}
