/*
 * DonneesCalculeesConferencierMoyenneSemaineFiltreControleur.java                           
 * 10 nov. 2024
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

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class DonneesCalculeesConferencierMoyenneSemaineFiltreControleur {

    @FXML
    private Button btnValider;

    @FXML
    private DatePicker dateDebut;
    
    @FXML
    private DatePicker dateFin;

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
            
        // Passer le critère de filtre au contrôleur principal via EchangeurDeVue
        DonneesCalculeesConferencierMoyenneSemaineControleur controleurPrincipal;
        controleurPrincipal = EchangeurDeVue
                               .getFXMLLoader("donneesCalculeesConferencierMoyenneSemaineVue")
                                .getController();
        controleurPrincipal.appliquerFiltreMoyenneJour(critere);
            
        // Fermer la popup
        EchangeurDeVue.fermerPopUp("donneesCalculeesConferencierMoyenneSemaineFiltrePopUp");
    }

}
