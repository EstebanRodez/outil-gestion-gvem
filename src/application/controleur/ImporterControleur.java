/*
 * ImporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class ImporterControleur {
    
    private Stage fenetreAppli;
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
      this.fenetreAppli = fenetreAppli;
    }
    
    @FXML
    private Button btnAide;

    @FXML
    private Button btnImporterDistant;

    @FXML
    private Button btnImporterLocal;

    @FXML
    private Button btnRetour;

    @FXML
    void btnAideAction(ActionEvent event) {

    }

    @FXML
    void btnImporterDistantAction(ActionEvent event) {

    }

    @FXML
    void btnImporterLocalAction(ActionEvent event) {

    }

    @FXML
    void btnRetourAction(ActionEvent event) {

    }

}
