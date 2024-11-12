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
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class MenuStatistiqueClassementControleur {
    
    @FXML
    private Button btnConferencier;

    @FXML
    private Button btnMenuPourcentages;

    @FXML
    private Button btnExposition;

    @FXML
    private Button btnStatistique;

    @FXML
    void btnStatistiqueAction(ActionEvent event) {
        EchangeurDeVue.changerVue("MenuStatistiqueVue");
    }

    @FXML
    void btnMenuPourcentagesAction(ActionEvent event) {
        EchangeurDeVue.changerVue("MenuStatistiquePourcentageVue");
    }

    @FXML
    void btnExpositionAction(ActionEvent event) {
        
    }

    @FXML
    void btnConferencierAction(ActionEvent event) {

    }

}
