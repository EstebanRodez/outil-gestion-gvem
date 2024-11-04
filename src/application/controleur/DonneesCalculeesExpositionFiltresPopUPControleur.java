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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class DonneesCalculeesExpositionFiltresPopUPControleur {
        
    @FXML
    private Button btnValider;

    @FXML
    private TextField labelAnneesDebut;

    @FXML
    private TextField labelAnneesFin;

    @FXML
    private TextField labelHeureDebut;

    @FXML
    private TextField labelHeureFin;

    @FXML
    private TextField labelJourDebut;

    @FXML
    private TextField labelJourFin;

    @FXML
    private TextField labelMinuteDebut;

    @FXML
    private TextField labelMinuteFin;

    @FXML
    private TextField labelMoisDebut;

    @FXML
    private TextField labelMoisFin;

    @FXML
    void btnValiderAction(ActionEvent event) {
        LocalDate dateDebut = null;
        LocalDate dateFin = null;
        
        CritereFiltreVisite critere = new CritereFiltreVisite();
        
        if (!labelJourDebut.getText().isEmpty() 
                && !labelMoisDebut.getText().isEmpty() 
                && !labelAnneesDebut.getText().isEmpty()) {
                dateDebut = LocalDate.of(
                    Integer.parseInt(labelAnneesDebut.getText()), 
                    Integer.parseInt(labelMoisDebut.getText()), 
                    Integer.parseInt(labelJourDebut.getText())
                );
                critere.setDateDebut(dateDebut);
            }

            if (!labelJourFin.getText().isEmpty() 
                && !labelMoisFin.getText().isEmpty() 
                && !labelAnneesFin.getText().isEmpty()) {
                dateFin = LocalDate.of(
                    Integer.parseInt(labelAnneesFin.getText()), 
                    Integer.parseInt(labelMoisFin.getText()), 
                    Integer.parseInt(labelJourFin.getText())
                );
                critere.setDateFin(dateFin);
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

}


