/*
 * MenuStatistiquePourcentageControleur.java                           
 * 12 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import application.EchangeurDeVue;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Contrôleur des pourcentages
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class MenuStatistiquePourcentageControleur {

    @FXML
    private Button btnAccueil;

    @FXML
    private Button btnConferencier;

    @FXML
    private Button btnExposition;

    @FXML
    private Button btnMenuClassement;

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
    void btnMenuClassementAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuStatistiqueClassementVue");
    }

    @FXML
    void btnExpositionAction(ActionEvent event) {
        EchangeurDeVue.changerVue("statistiqueExpositionPourcentageVue");
    }

    @FXML
    void btnConferencierAction(ActionEvent event) {
        EchangeurDeVue.changerVue("statistiqueConferencierPourcentageVue");
    }

}
