/*
 * AcceuilControleur.java                           
 * 11 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class AccueilControleur {
    
    @FXML
    private Button btnAide;

    @FXML
    private Button btnConsulterDonnees;

    @FXML
    private Button btnExporter;

    @FXML
    private Button btnImporter;

    @FXML
    private Button btnQuitter;

    @FXML
    void btnAideAction(ActionEvent event) {

    }

    @FXML
    void btnConsulterDonneesAction(ActionEvent event) {
        //TODO renvoyait vers la page menuDonneesImporterVue.fxml
    }

    @FXML
    void btnExporterAction(ActionEvent event) {
        //TODO renvoyait vers la page exporter.fxml
    }

    @FXML
    void btnImporterAction(ActionEvent event) {
        //TODO renvoyait vers la page importerVue.fxml
    }

    @FXML
    void btnQuitterAction(ActionEvent event) {
        //TODO quitter l'application
    }

}