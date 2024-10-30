/*
 * DonneesCalculeesVisiteFiltresPopUPControleur.java                           
 * 25 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.LocalDate;
import java.util.ArrayList;

import application.EchangeurDeVue;
import application.modele.CritereFiltre;
import application.modele.Visite;
import application.utilitaire.TraitementDonnees;
import javafx.collections.FXCollections;
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
    
    private String[] expositions;
    private String[] conferenciers;
    
    private ArrayList<Visite> visites = TraitementDonnees.getVisites();
    private ArrayList<String> listeExpositions = new ArrayList<>();
    private ArrayList<String> listeConferenciers = new ArrayList<>();
    
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
        for (Visite visite : visites) {
            String expo = visite.getExposition().getIntitule();
            String conf = visite.getConferencier().getNom();
            // Éviter les doublons
            if (!listeExpositions.contains(expo) && !listeConferenciers
                                                     .contains(conf)) { 
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
        LocalDate dateDebut = null;
        LocalDate dateFin = null;
        
        CritereFiltre critere = new CritereFiltre();
        
        if (typeExpo.getSelectedToggle() != null) {
            critere.setTypeExposition(typeExpo.getSelectedToggle() 
                                      == radioPermanente ? "permanente" 
                                                         : "temporaire");
        }

        if (listeConf.getValue() != null) {
            critere.setConferencier(listeConf.getValue());
        }
        
        if (listeExpo.getValue() != null) {
            critere.setExposition(listeExpo.getValue());
        }
        
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
        DonneesCalculeesVisiteControleur controleurPrincipal;
        controleurPrincipal = EchangeurDeVue
                               .getFXMLLoader("donneesCalculeesVisiteVue")
                                .getController();
        controleurPrincipal.appliquerFiltre(critere);
        
        // Fermer la popup
        EchangeurDeVue.fermerPopUp("donneesCalculeesVisiteFiltresPopUP");
    }

}