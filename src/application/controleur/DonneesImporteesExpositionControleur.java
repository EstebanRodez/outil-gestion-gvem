/*
 * DonneesImporteesExpositionControleur.java                           
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
public class DonneesImporteesExpositionControleur {
    
    private Stage fenetreAppli;
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
      this.fenetreAppli = fenetreAppli;
    }
    
    @FXML
    private Button btnRetour;

    @FXML
    void retourAccueil(ActionEvent event) {

    }

    @FXML
    void quitterPartie(ActionEvent event) {

    }

    @FXML
    void afficherManuelUtilisateur(ActionEvent event) {

    }

    @FXML
    void btnRetourAction(ActionEvent event) {

    }

}
