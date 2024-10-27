/*
 * ImporterDistantControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.EchangeurDeVue;
import application.utilitaire.Client;
import application.utilitaire.ImportationCSV;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Contrôleur pour la gestion de l'importation de données à distance.
 * 
 * Cette classe permet à l'utilisateur de spécifier une adresse IP et
 * un port pour se connecter à un serveur distant. Elle inclut
 * également des fonctionnalités pour afficher des règles
 * d'utilisation via un lien.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class ImporterDistantControleur {
    
    private static final String DOSSIER_IMPORTATION = "fichiersImporteesRecu";

    private static String[] CHEMIN_FICHIER_CSV_RECU = {
        "expositions 28_08_24 17_26.csv",
        "employes 28_08_24 17_26.csv",
        "conferencier 28_08_24 17_26.csv", 
        "visites 28_08_24 17_26.csv"
    };
    
    /**
     * Vérifie si une adresse IP est valide
     * @param ip Adresse IP sous forme de chaîne
     * @return true si l'adresse est valide, sinon false
     */
    private static boolean isValideAdresseIP(String ip) {
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
     * @return true si le port est un entier valide entre 0 et 65535,
     *         sinon false
     */
    private static boolean isPortValide(String port) {
        return port.matches("(\\d){1,5}") && Integer.parseInt(port) >= 0
               && Integer.parseInt(port) <= 65535;
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
    void btnConnexionAction(ActionEvent event) {
        
        String ipServeur = txtFieldIPServeur.getText().trim();
        String port = txtFieldPort.getText().trim();

        if (isValideAdresseIP(ipServeur) && isPortValide(port)) {
            // Créer le dossier d'importation s'il n'existe pas
            File dossierImportes = new File(DOSSIER_IMPORTATION);
            if (!dossierImportes.exists()) {
                dossierImportes.mkdir(); // Créer le dossier
            }

            // Réception des fichiers depuis le serveur
            Client.recevoirFichiers(ipServeur, Integer.parseInt(port), CHEMIN_FICHIER_CSV_RECU, DOSSIER_IMPORTATION);

            // Traitement des fichiers reçus
            ArrayList<File> fichiersSelectionnes = new ArrayList<>();
            for (String nomFichier : CHEMIN_FICHIER_CSV_RECU) {
                fichiersSelectionnes.add(new File(dossierImportes, nomFichier));
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

                // Afficher une alerte de succès
                Alert boiteInformation = new Alert(Alert.AlertType.INFORMATION,
                        "Les fichiers suivants ont été importés avec succès :\n" + nomsFichiers.toString());
                boiteInformation.setTitle("Importation réussie");
                boiteInformation.setHeaderText("Fichiers importés");
                boiteInformation.showAndWait();
            }
            
            EchangeurDeVue.changerVue("importerDistantValideVue");
        } else if (isValideAdresseIP(ipServeur) && !isPortValide(port)) {
            Alert boiteAlerte 
            = new Alert(Alert.AlertType.ERROR,"Veuillez respecter la norme "
                        + "d'écriture d'un port :\n entre 0 et 65535");
            boiteAlerte.setTitle("Erreur sur le port");
            boiteAlerte.setHeaderText("Erreur sur le port");
            boiteAlerte.showAndWait();

        } else {
            Alert boiteAlerte 
            = new Alert(Alert.AlertType.ERROR, "Veuillez respecter la norme "
                        + "d'écriture d'une adresse ip  \n(exemple : "
                        + "192.168.2.3).\nValeur maximale par octet = 255");
            boiteAlerte.setTitle("Erreur sur l'adresse IP");
            boiteAlerte.setHeaderText("Erreur sur l'adresse IP");
            boiteAlerte.showAndWait();
        }
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("importerVue");
    }
}
