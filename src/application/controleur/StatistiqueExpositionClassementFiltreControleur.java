/*
 * StatistiqueExpositionClassementFiltreControleur.java                           
 * 15 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.LocalDate;

import application.modele.CritereFiltreVisite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class StatistiqueExpositionClassementFiltreControleur {
    
    @FXML
    private Button btnValider;

    @FXML
    private DatePicker dateDebut;

    @FXML
    private DatePicker dateFin;

    @FXML
    private TextField labelHeureDebut;

    @FXML
    private TextField labelHeureFin;

    @FXML
    private TextField labelMinuteDebut;

    @FXML
    private TextField labelMinuteFin;

    @FXML
    private RadioButton radioPermanente;

    @FXML
    private RadioButton radioTemporaire;

    @FXML
    private ToggleGroup typeExpo;

    @FXML
    void btnValiderAction(ActionEvent event) {
        
        LocalDate dateDebutSelectionne,
        dateFinSelectionne;

        dateDebutSelectionne = dateDebut.getValue();
        dateFinSelectionne = dateFin.getValue();
        
        CritereFiltreVisite critere = new CritereFiltreVisite();
        
        if (typeExpo.getSelectedToggle() != null) {
        
          if (typeExpo.getSelectedToggle() == radioPermanente) {
              critere.setExpositionPermanente(true);
          } else {
              critere.setExpositionTemporaire(true);
          }
        }

    }
    
}
