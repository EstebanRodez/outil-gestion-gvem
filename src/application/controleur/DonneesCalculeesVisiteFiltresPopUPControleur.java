/*
 * DonneesCalculeesVisiteFiltresPopUPControleur.java                           
 * 25 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import application.EchangeurDeVue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class DonneesCalculeesVisiteFiltresPopUPControleur {
    
    private Stage boitePopUp;
    
    /**
     * Définit la fenêtre de l'application.
     * @param boitePopUp 
     */
    public void setPopUp(Stage boitePopUp) {
      this.boitePopUp = boitePopUp;
    }
    
    @FXML
    private Button btnValider;

    @FXML
    private TextField labelAnnees;

    @FXML
    private TextField labelHeure;

    @FXML
    private TextField labelJour;

    @FXML
    private TextField labelMinute;

    @FXML
    private TextField labelMois;

    @FXML
    private ChoiceBox<?> listeConf;

    @FXML
    private ChoiceBox<?> listeExpo;

    @FXML
    private RadioButton radioPermanente;

    @FXML
    private RadioButton radioTemporaire;

    @FXML
    private ToggleGroup typeExpo;


    @FXML
    void btnValiderAction(ActionEvent event) {
        this.boitePopUp.close();
        
    }

}
