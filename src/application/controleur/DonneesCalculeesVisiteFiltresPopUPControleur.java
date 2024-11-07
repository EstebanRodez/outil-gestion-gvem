/*
 * DonneesCalculeesVisiteFiltresPopUPControleur.java                           
 * 25 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import application.EchangeurDeVue;
import application.modele.CritereFiltreVisite;
import application.modele.Visite;
import application.utilitaire.TraitementDonnees;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class DonneesCalculeesVisiteFiltresPopUPControleur {
    
    private String[] expositions;
    
    private String[] conferenciers;
    
    private LinkedHashMap<String, Visite> visites
    = TraitementDonnees.getDonnees().getVisites();
    
    private ArrayList<String> listeExpositions = new ArrayList<>();
    
    private ArrayList<String> listeConferenciers = new ArrayList<>();
    
    @FXML
    private DatePicker dateDebut;
    
    @FXML
    private DatePicker dateFin;
    
    @FXML
    private Button btnValider;

    @FXML
    private TextField labelHeureDebut;

    @FXML
    private TextField labelHeureFin;

    @FXML
    private TextField labelMinuteDebut;

    @FXML
    private TextField labelMinuteFin;

    @FXML
    private ChoiceBox<String> listeConf;

    @FXML
    private ChoiceBox<String> listeExpo;

    @FXML
    private RadioButton radioPermanente;

    @FXML
    private RadioButton radioTemporaire;

    @FXML
    private ToggleGroup typeExpo;
    
    /**
     * 
     */
    @FXML
    public void initialize() {
        
        // Possibilité de ne rien choisir
        listeExpositions.add(null);
        listeConferenciers.add(null);
        
        // Extraire les intitulés des expositions et des conférenciers
        for (Map.Entry<String, Visite> paire  : visites.entrySet()) {
            
            String expo = paire.getValue().getExposition().getIntitule();
            String conf = paire.getValue().getConferencier().getNom();
            
            // Éviter les doublons
            if (!listeExpositions.contains(expo)
                && !listeConferenciers.contains(conf)) {
                
                listeExpositions.add(expo);
                listeConferenciers.add(conf);
            }
        }

        expositions = listeExpositions.toArray(new String[0]);
        conferenciers = listeConferenciers.toArray(new String[0]);

        listeExpo.setItems(FXCollections.observableArrayList(expositions));
        listeConf.setItems(FXCollections.observableArrayList(conferenciers));
    }


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

        if (listeConf.getValue() != null) {
            critere.setConferencier(listeConf.getValue());
        }
        
        if (listeExpo.getValue() != null) {
            critere.setExposition(listeExpo.getValue());
        }
        
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
        DonneesCalculeesVisiteControleur controleurPrincipal;
        controleurPrincipal = EchangeurDeVue
                               .getFXMLLoader("donneesCalculeesVisiteVue")
                                .getController();
        controleurPrincipal.appliquerFiltre(critere);
        
        EchangeurDeVue.fermerPopUp("donneesCalculeesVisiteFiltresPopUP");
    }

}