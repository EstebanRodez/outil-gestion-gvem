/*
 * MenuStatistiqueClassementControleur.java                           
 * 12 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import application.EchangeurDeVue;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Contrôleur des classements
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class MenuStatistiqueClassementControleur {
    
    @FXML
    private Button btnAccueil;

    @FXML
    private Button btnConferencier;

    @FXML
    private Button btnExposition;

    @FXML
    private Button btnMenuPourcentages;

    @FXML
    private Button btnRetour;

    @FXML
    void btnAccueilAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }
    
    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuStatistiqueVue");
    }

    @FXML
    void btnMenuPourcentagesAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuStatistiquePourcentageVue");
    }

    @FXML
    void btnExpositionAction(ActionEvent event) {
        EchangeurDeVue.changerVue("statistiqueExpositionClassementVue");
    }

    @FXML
    void btnConferencierAction(ActionEvent event) {
        EchangeurDeVue.changerVue("statistiqueConferencierClassementVue");
    }

}
