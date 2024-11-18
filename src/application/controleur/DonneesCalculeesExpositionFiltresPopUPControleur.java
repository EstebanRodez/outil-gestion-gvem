/*
 * DonneesCalculeesExpositionFiltresPopUPControleur.java                           
 * 1 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.LocalDate;

import application.EchangeurDeVue;
import application.modele.CritereFiltreVisite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class DonneesCalculeesExpositionFiltresPopUPControleur {
        
    @FXML
    private Button btnValider;
    
    @FXML
    private Button btnReset;

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
    void btnValiderAction(ActionEvent event) {
        LocalDate dateDebutSelectionne,
        dateFinSelectionne;

        dateDebutSelectionne = dateDebut.getValue();
        dateFinSelectionne = dateFin.getValue();
        
        
        CritereFiltreVisite critere = new CritereFiltreVisite();
        
        if (dateDebutSelectionne != null){  
            critere.setDateDebut(dateDebutSelectionne);
        }

        if (dateFinSelectionne != null){  
            critere.setDateFin(dateFinSelectionne);
        }
            
        if(!labelHeureDebut.getText().isEmpty() 
           && !labelMinuteDebut.getText().isEmpty()) {
            critere.setHoraireDebut(Integer.parseInt(labelHeureDebut.getText()) 
                                    * 60 + Integer.parseInt(labelMinuteDebut
                                                             .getText()));
        }
        
        if(!labelHeureFin.getText().isEmpty() 
           && !labelMinuteFin.getText().isEmpty()) {
            critere.setHoraireFin(Integer.parseInt(labelHeureFin.getText()) 
                                  * 60 + Integer.parseInt(labelMinuteFin
                                                           .getText()));
        }
        
        // Passer le critère de filtre au contrôleur principal via EchangeurDeVue
        DonneesCalculeesExpositionControleur controleurPrincipal;
        controleurPrincipal = EchangeurDeVue
                               .getFXMLLoader("donneesCalculeesExpositionVue")
                                .getController();
        controleurPrincipal.appliquerFiltreInverse(critere);
            
        // Fermer la popup
        EchangeurDeVue.fermerPopUp("donneesCalculeesExpositionFiltresPopUP");
    }
    
    @FXML
    void btnResetAction(ActionEvent event) {
        
        dateDebut.setValue(null);
        dateFin.setValue(null);
        
        labelHeureDebut.setText("");
        labelHeureFin.setText("");
        labelMinuteDebut.setText("");
        labelMinuteFin.setText("");        
    }

}


