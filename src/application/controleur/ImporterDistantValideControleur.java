/*
 * ImporterDistantValideControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Contrôleur pour la gestion de l'importation de données à distance après
 * une connexion réussie.
 * 
 * Cette classe permet à l'utilisateur de procéder à l'importation de données
 * à distance une fois qu'il a validé la connexion avec le serveur.
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class ImporterDistantValideControleur {
    
    private Stage fenetreAppli;
    
    /**
     * Définit la fenêtre de l'application.
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
      this.fenetreAppli = fenetreAppli;
    }
    
    @FXML
    private Button btnImporter;

    @FXML
    private Button btnAide;

    @FXML
    private Button btnRetour;

    @FXML
    void btnAideAction(ActionEvent event) {

        AccueilControleur.lancerAide();
    }

    @FXML
    void btnImporterAction(ActionEvent event) {

    }

    @FXML
    void btnRetourAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/importerDistantVue.fxml"));
        Parent importerDistantVue = loader.load();
        ImporterDistantControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(importerDistantVue));
    }

}
