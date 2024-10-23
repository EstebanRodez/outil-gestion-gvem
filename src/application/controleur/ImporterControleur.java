/*
 * ImporterControleur.java                           
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

import application.utilitaire.FichierDonneesInvalides;
import application.utilitaire.ImportationCSV;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    	final String LIEN_REGLES
        = "https://drive.google.com/file/d/1DmblRvNDZ0PTUA0iGn9vQYf74aj1lMUH/view?usp=sharing";

        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(LIEN_REGLES));
        } catch (IOException | URISyntaxException e) {
            Alert boiteErreurInconnueOuverture =
                    new Alert(Alert.AlertType.ERROR, 
                              "impossible d'ouvrir le fichier d'aide",
                              ButtonType.OK);

            boiteErreurInconnueOuverture.setTitle("Erreur d'affichage aide");
            boiteErreurInconnueOuverture.setHeaderText("Erreur d'affichage aide");

            boiteErreurInconnueOuverture.showAndWait();
        }
    }

    @FXML
    void btnImporterDistantAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/importerDistantVue.fxml"));
        Parent importerDistantVue = loader.load();
        ImporterDistantControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(importerDistantVue));
    }

    @FXML
    void btnImporterLocalAction(ActionEvent event) throws IOException {
        // Créer une instance de FileChooser
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers données (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Ouvrir le dialogue pour choisir un fichier
        List<File> fichierSelectionne = fileChooser.showOpenMultipleDialog(fenetreAppli);
        
        // Vérifier si un fichier a été sélectionné
        if (fichierSelectionne != null && !fichierSelectionne.isEmpty()) {
            // Liste pour stocker les noms des fichiers sans chemin
            StringBuilder nomsFichiers = new StringBuilder();
            
            for (File fichier : fichierSelectionne) {

                try {
                    
                    ImportationCSV.importerDonnees(fichier.getAbsolutePath());
                } catch (FichierDonneesInvalides e) {
                    
                    Alert boiteErreurDonneesInvalides
                    = new Alert(Alert.AlertType.ERROR, 
                                "Les données du fichier sélectionné sont "
                                + "incorrectes.", ButtonType.OK);

                    boiteErreurDonneesInvalides.setTitle("Erreur fichier");
                    boiteErreurDonneesInvalides.setHeaderText(
                            "Erreur données fichier");
                    boiteErreurDonneesInvalides.showAndWait();
                }
                
                // Ajouter le nom du fichier (sans le chemin) à la liste
                nomsFichiers.append(fichier.getName()).append("\n");
            }
            
            // Afficher une alerte avec les noms des fichiers sélectionnés
            Alert boiteInformationSucces =
                    new Alert(Alert.AlertType.INFORMATION, 
                              "Les fichiers suivants ont été sélectionnés :"
                            + "\n" + nomsFichiers.toString(),
                              ButtonType.OK);

            boiteInformationSucces.setTitle("Fichiers importés avec succès");
            boiteInformationSucces.setHeaderText("Fichiers importés avec succès");

            boiteInformationSucces.showAndWait();
        } else {
            // Afficher une alerte si aucun fichier n'a été sélectionné
            Alert boiteErreurInconnueOuverture =
                    new Alert(Alert.AlertType.ERROR, 
                              "Aucun fichier sélectionné",
                              ButtonType.OK);

            boiteErreurInconnueOuverture.setTitle("Erreur fichier");
            boiteErreurInconnueOuverture.setHeaderText("Erreur récupération fichier");

            boiteErreurInconnueOuverture.showAndWait();
        }
    }


    @FXML
    void btnRetourAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/accueilVue.fxml"));
        Parent accueuilVue = loader.load();
        AccueilControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(accueuilVue));
    }

}
