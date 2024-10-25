/*
 * menuDonneesImporterControleur.java                           
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
 * Contrôleur pour le menu des données importées.
 * 
 * Cette classe gère les interactions de l'utilisateur avec le menu
 * permettant d'accéder aux différentes fonctionnalités relatives aux
 * données importées, telles que les conférenciers, les expositions
 * et les visites.
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class MenuDonneesImporterControleur {

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
    private Button btnDonnesCalculees;

    @FXML
    private Button btnExposition;

    @FXML
    private Button btnStatistique;

    @FXML
    private Button btnVisite;

    @FXML
    void btnAccueilAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/accueilVue.fxml"));
        Parent accueuilVue = loader.load();
        AccueilControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(accueuilVue));
    }

    @FXML
    void btnConferencierAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/donneesImporteesConferencierVue.fxml"));
        Parent donneesImporteesConferencierVue = loader.load();
        DonneesImporteesConferencierControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(donneesImporteesConferencierVue));
    }

    @FXML
    void btnDonnesCalculeesAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/MenuDonneesCalculeesVue.fxml"));
        Parent MenuDonneesCalculeesVue = loader.load();
        MenuDonneesCalculeesControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(MenuDonneesCalculeesVue));
    }

    @FXML
    void btnExpositionAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/donneesImporteesExpositionVue.fxml"));
        Parent donneesImporteesExpositionVue = loader.load();
        DonneesImporteesExpositionControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(donneesImporteesExpositionVue));
    }

    @FXML
    void btnStatistiqueAction(ActionEvent event) {

    }

    @FXML
    void btnVisiteAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/donneesImporteesVisiteVue.fxml"));
        Parent donneesImporteesVisiteVue = loader.load();
        DonneesImporteesVisiteControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(donneesImporteesVisiteVue));
    }


}
