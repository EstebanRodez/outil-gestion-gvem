/*
 * MenuReglageControleur.java                           
 * 21 nov. 2024
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
public class MenuReglageControleur {

    @FXML
    private Button btnAccueil;

    @FXML
    private Button btnAide;

    @FXML
    private Button btnPort;

    @FXML
    private Button btnRenit;

    @FXML
    void btnAccueilAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }

    @FXML
    void btnAideAction(ActionEvent event) {

    }

    @FXML
    void btnPortAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuChangerPort");
    }

    @FXML
    void btnRenitAction(ActionEvent event) {

    }

}
