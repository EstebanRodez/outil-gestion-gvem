/*
 * ImporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.File;
import java.io.IOException;
import java.util.List;

import application.EchangeurDeVue;
import application.utilitaire.FichierDonneesInvalides;
import application.utilitaire.ImportationCSV;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Contrôleur pour la gestion de l'importation des données.
 * 
 * Cette classe permet à l'utilisateur de choisir entre deux méthodes 
 * d'importation de données : depuis un fichier local ou à distance. 
 * Elle gère également les interactions liées à l'aide et au retour 
 * vers l'interface d'accueil.
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class ImporterControleur {
    
    private Stage fenetreAppli;
    
    /**
     * Définit la fenêtre de l'application.
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
      this.fenetreAppli = fenetreAppli;
    }
    
    @FXML
    private Button btnAide;

    @FXML
    private Button btnImporterDistant;

    @FXML
    private Button btnImporterLocal;

    @FXML
    private Button btnRetour;

    @FXML
    void btnAideAction(ActionEvent event) {

        AccueilControleur.lancerAide();
    }

    @FXML
    void btnImporterDistantAction(ActionEvent event) {
        EchangeurDeVue.changerVue("importerDistantVue");
    }

    @FXML
    void btnImporterLocalAction(ActionEvent event) throws IOException {
        // Créer une instance de FileChooser
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilter
        = new FileChooser.ExtensionFilter("Fichiers données (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Ouvrir le dialogue pour choisir un fichier
        List<File> fichierSelectionne
        = fileChooser.showOpenMultipleDialog(fenetreAppli);
        
        // Vérifier si un fichier a été sélectionné
        if (fichierSelectionne != null && !fichierSelectionne.isEmpty()) {
            // Liste pour stocker les noms des fichiers sans chemin
            StringBuilder nomsFichiers = new StringBuilder();
            
            for (File fichier : fichierSelectionne) {

                try {
                    
                    ImportationCSV.importerDonnees(fichier.getAbsolutePath());
                    
                    // Ajouter le nom du fichier (sans le chemin) à la liste
                    nomsFichiers.append(fichier.getName()).append("\n");
                } catch (FichierDonneesInvalides e) {
                    
                    Alert boiteErreurDonneesInvalides
                    = new Alert(Alert.AlertType.ERROR, 
                                "Les données du fichier " + fichier.getName() 
                                + " sont incorrectes.", ButtonType.OK);

                    boiteErreurDonneesInvalides.setTitle("Erreur fichier");
                    boiteErreurDonneesInvalides.setHeaderText(
                            "Erreur données fichier");
                    boiteErreurDonneesInvalides.showAndWait();
                }
                
            }
            
            // Afficher une alerte avec les noms des fichiers sélectionnés
            Alert boiteInformationSucces
            = new Alert(Alert.AlertType.INFORMATION, 
                        "Les fichiers suivants ont été sélectionnés :\n"
                        + nomsFichiers.toString(), ButtonType.OK);

            boiteInformationSucces.setTitle("Fichiers importés avec succès");
            boiteInformationSucces.setHeaderText(
                    "Fichiers importés avec succès");
            boiteInformationSucces.showAndWait();
        } else {
            
            // Afficher une alerte si aucun fichier n'a été sélectionné
            Alert boiteErreurInconnueOuverture
            = new Alert(Alert.AlertType.ERROR, "Aucun fichier sélectionné",
                        ButtonType.OK);

            boiteErreurInconnueOuverture.setTitle("Erreur fichier");
            boiteErreurInconnueOuverture.setHeaderText(
                    "Erreur récupération fichier");
            boiteErreurInconnueOuverture.showAndWait();
        }
    }


    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }

}
