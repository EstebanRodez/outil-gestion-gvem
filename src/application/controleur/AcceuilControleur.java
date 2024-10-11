/*
 * AcceuilControleur.java                           
 * 11 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.awt.Button;
import java.awt.event.ActionEvent;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class AcceuilControleur {
    
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
        //TODO renvoyait vers la page consulterDonnesVue.fxml
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