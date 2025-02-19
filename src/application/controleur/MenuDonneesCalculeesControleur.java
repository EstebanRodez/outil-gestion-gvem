/*
 * menuDonneesImporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import application.EchangeurDeVue;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Contrôleur pour le menu des données importées.
 * 
 * Cette classe gère les interactions de l'utilisateur avec le menu
 * permettant d'accéder aux différentes fonctionnalités relatives aux
 * données importées, telles que les conférenciers, les expositions
 * et les visites.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class MenuDonneesCalculeesControleur {
    
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
    void btnAccueilAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }

    @FXML
    void btnConferencierAction(ActionEvent event) {
        EchangeurDeVue.changerVue("donneesCalculeesConferencierVue");
    }

    @FXML
    void btnDonnesImporteesAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuDonneesImporterVue");
    }

    @FXML
    void btnExpositionAction(ActionEvent event) {
        EchangeurDeVue.changerVue("donneesCalculeesExpositionVue");
    }

    @FXML
    void btnStatistiqueAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuStatistiqueVue");
    }

    @FXML
    void btnVisiteAction(ActionEvent event) {
        EchangeurDeVue.changerVue("donneesCalculeesVisiteVue");
    }


}
