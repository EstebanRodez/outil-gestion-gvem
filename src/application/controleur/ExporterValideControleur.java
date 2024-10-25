/*
 * ExporterValideControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Contrôleur pour la confirmation d'exportation des données.
 * 
 * Cette classe gère les interactions de l'utilisateur après qu'une 
 * exportation valide a été établie. Elle permet à l'utilisateur 
 * d'envoyer des données, d'accéder à l'aide ou de retourner à la vue 
 * précédente.
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class ExporterValideControleur {
    
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
    private Button btnEnvoyer;

    @FXML
    private Button btnRetour;

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
                              "impossible d'ouvrir le fichier d'aide",
                              ButtonType.OK);

            boiteErreurInconnueOuverture.setTitle("Erreur d'affichage aide");
            boiteErreurInconnueOuverture.setHeaderText("Erreur d'affichage aide");

            boiteErreurInconnueOuverture.showAndWait();
        }
    }

    @FXML
    void btnEnvoyerAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/accueilVue.fxml"));
        Parent accueuilVue = loader.load();
        AccueilControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(accueuilVue));
    }

    @FXML
    void btnRetourAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/exporterVue.fxml"));
        Parent exporterVue = loader.load();
        ExporterControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(exporterVue));
    }

}
