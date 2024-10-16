/*
 * ImporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.File;
import java.io.IOException;

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
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class ImporterControleur {
    
    private Stage fenetreAppli;
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
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
    void btnImporterLocalAction(ActionEvent event) {
        // Créer une instance de FileChooser
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers données (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Ouvrir le dialogue pour choisir un fichier
        File fichierSelectionne = fileChooser.showOpenDialog(fenetreAppli);
        
        // Vérifier si un fichier a été sélectionné
        if (fichierSelectionne != null) {
            // TODO ImportationCsv.importer(fichierSelectionne.getAbsolutePath()));
        } else {
            Alert boiteErreurInconnueOuverture =
                    new Alert(Alert.AlertType.ERROR, 
                              "Aucun fichier sélectionner",
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
