/*
 * DonneesCalculeesConferencierEnsembleJourFiltreControlleur.java                           
 * 5 nov. 2024
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * Cette classe est responsable de gérer l'interface utilisateur de la fenêtre
 * de filtres pour les données calculées sur les conférenciers pour l'ensemble
 * des conférenciers. Elle permet de :
 * - Saisir les critères de filtrage (dates, type d'exposition, type de conférencier).
 * - Valider les critères et transmettre ces filtres au contrôleur principal.
 * - Réinitialiser les champs de saisie.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class DonneesCalculeesConferencierEnsembleJourFiltreControleur {
    
    @FXML
    private Button btnValider;
    
    @FXML
    private Button btnReset;

    @FXML
    private DatePicker dateDebut;
    
    @FXML
    private DatePicker dateFin;
    
    @FXML
    private RadioButton radioExternes;

    @FXML
    private RadioButton radioInternes;

    @FXML
    private RadioButton radioPermanente;

    @FXML
    private RadioButton radioTemporaire;

    @FXML
    private ToggleGroup typeConf;

    @FXML
    private ToggleGroup typeExpo;

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
        
        if (typeExpo.getSelectedToggle() != null) {

            if (typeExpo.getSelectedToggle() == radioPermanente) {
                critere.setExpositionPermanente(true);
            } else {
                critere.setExpositionTemporaire(true);
            }
        }
        
        if (typeConf.getSelectedToggle() != null) {

            if (typeConf.getSelectedToggle() == radioInternes) {
                critere.setEstInterne(true);
            } else {
                critere.setExterne(true);
            }
        }
            
        // Passer le critère de filtre au contrôleur principal via EchangeurDeVue
        DonneesCalculeesConferencierEnsembleJourControleur controleurPrincipal;
        controleurPrincipal 
        = EchangeurDeVue.getFXMLLoader(
                "donneesCalculeesConferencierEnsembleJourVue").getController();
        controleurPrincipal.appliquerFiltreMoyenneJour(critere);
            
        // Fermer la popup
        EchangeurDeVue.fermerPopUp(
                "donneesCalculeesConferencierEnsembleJourFiltrePopUp");
    }
    
    @FXML
    void btnResetAction(ActionEvent event) {
        
        dateDebut.setValue(null);
        dateFin.setValue(null);
        
        radioExternes.setSelected(false);
        radioInternes.setSelected(false);
        radioPermanente.setSelected(false);
        radioTemporaire.setSelected(false);
        
        
    }

}

