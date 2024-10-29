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
    private TextField labelAnnees;

    @FXML
    private TextField labelHeure;

    @FXML
    private TextField labelJour;

    @FXML
    private TextField labelMinute;

    @FXML
    private TextField labelMois;

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
        
        // Extraire les intitulés des expositions et des conférenciers
        for (Visite visite : visites) {
            String expo = visite.getExposition().getIntitule();
            String conf = visite.getConferencier().getNom();
            if (!listeExpositions.contains(expo) && !listeConferenciers.contains(conf)) { // Éviter les doublons
                listeExpositions.add(expo);
                listeConferenciers.add(conf);
            }
        }

        // Convertir en tableau String[] et assigner à expositions
        expositions = listeExpositions.toArray(new String[0]);
        conferenciers = listeConferenciers.toArray(new String[0]);

        // Mettre à jour la ChoiceBox listeExpo avec les intitulés
        listeExpo.setItems(FXCollections.observableArrayList(expositions));
        listeConf.setItems(FXCollections.observableArrayList(conferenciers));
    }


    @FXML
    void btnValiderAction(ActionEvent event) {
        CritereFiltre critere = new CritereFiltre();
        
        // Récupérer les valeurs des champs de filtre et les assigner au modèle CritereFiltre
        critere.setTypeExposition(typeExpo.getSelectedToggle() == radioPermanente ? "Permanente" : "Temporaire");
        critere.setConferencier(listeConf.getValue());
        
        // Si les champs de date sont présents
        if (!labelJour.getText().isEmpty() && !labelMois.getText().isEmpty() && !labelAnnees.getText().isEmpty()) {
            critere.setDateDebut(LocalDate.of(
                Integer.parseInt(labelAnnees.getText()), 
                Integer.parseInt(labelMois.getText()), 
                Integer.parseInt(labelJour.getText())
            ));
        }
        
        // Passer le critère de filtre au contrôleur principal via EchangeurDeVue
        DonneesCalculeesVisiteControleur controleurPrincipal = EchangeurDeVue.getFXMLLoader("donneesCalculeesVisiteVue").getController();
        controleurPrincipal.appliquerFiltre(critere);
        
        // Fermer la popup
        boitePopUp.close();
        
    }

}
