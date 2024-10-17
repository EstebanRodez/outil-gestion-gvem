/*
 * MenuDonnesCalculeesControleur.java                           
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
 * Contrôleur pour le menu des données calculées.
 * 
 * Cette classe gère les interactions de l'utilisateur avec le menu qui
 * permet d'accéder aux différentes fonctionnalités liées aux données
 * calculées, comme les exposés, les visites, et les statistiques.
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class MenuDonneesCalculeesControleur {
    
private Stage fenetreAppli;
    
    /**
     * Définit la fenêtre de l'application.
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
      this.fenetreAppli = fenetreAppli;
    }
    
    @FXML
    private Button btnAccueil;

    @FXML
    private Button btnConferencier;

    @FXML
    private Button btnDonnesImportees;

    @FXML
    private Button btnExposition;

    @FXML
    private Button btnStatistique;

    @FXML
    private Button btnVisite;

    @FXML
    void btnAccueilAction(ActionEvent event) {

    }

    @FXML
    void btnConferencierAction(ActionEvent event) {

    }

    @FXML
    void btnDonnesImporteesAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/menuDonneesImporterVue.fxml"));
        Parent menuDonneesImporterVue = loader.load();
        MenuDonneesImporterControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(menuDonneesImporterVue));
    }

    @FXML
    void btnExpositionAction(ActionEvent event) {

    }

    @FXML
    void btnStatistiqueAction(ActionEvent event) {

    }

    @FXML
    void btnVisiteAction(ActionEvent event) {

    }

}
