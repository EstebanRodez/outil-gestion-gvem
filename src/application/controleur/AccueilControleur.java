/*
 * AcceuilControleur.java                           
 * 11 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Contrôleur de l'interface d'accueil de l'application.
 * 
 * Cette classe gère les interactions utilisateur au sein de l'interface d'accueil. 
 * Elle est responsable de la navigation entre différentes vues de l'application 
 * en réponse aux actions des boutons. 
 * Chaque méthode de cette classe est associée à un bouton dans l'interface 
 * pour effectuer des actions spécifiques comme consulter, importer, 
 * exporter des données ou quitter l'application. 
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class AccueilControleur {
    
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
    void btnConsulterDonneesAction(ActionEvent event) throws IOException {   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/menuDonneesImporterVue.fxml"));
        Parent menuDonneesImporterVue = loader.load();
        MenuDonneesImporterControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(menuDonneesImporterVue));
    }

    @FXML
    void btnExporterAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/exporterVue.fxml"));
        Parent exporterVue = loader.load();
        ExporterControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(exporterVue));
    }

    @FXML
    void btnImporterAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/importerVue.fxml"));
        Parent importerVue = loader.load();
        ImporterControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(importerVue));
    }

    @FXML
    void btnQuitterAction(ActionEvent event) {
        fenetreAppli.hide();
    }

}