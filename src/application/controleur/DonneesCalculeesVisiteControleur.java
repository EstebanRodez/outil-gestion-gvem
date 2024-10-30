/*
 * DonneesImporteesVisiteControleur.java                           
 * 25 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import application.EchangeurDeVue;
import application.modele.CritereFiltre;
import application.modele.Visite;
import application.utilitaire.TraitementDonnees;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Contrôleur pour la gestion des données importées des visites.
 * 
 * Cette classe gère l'affichage et les interactions relatives aux données 
 * calculées des visites dans l'application. Elle permet à l'utilisateur 
 * de visualiser les informations des visites, ainsi que d'effectuer 
 * des actions telles que le retour à l'écran d'accueil ou la fermeture de 
 * l'application.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class DonneesCalculeesVisiteControleur {
    
    private static ArrayList<Visite> visites = TraitementDonnees.getVisites();
    
    @FXML
    private Label LabelResultat;
    
    @FXML
    private Button btnRetour;
    
    @FXML
    private Button btnFiltres;
    
    @FXML
    private TableColumn<Visite, String> conferencier;

    @FXML
    private TableColumn<Visite, LocalDate> date;

    @FXML
    private TableColumn<Visite, String> employe;

    @FXML
    private TableColumn<Visite, String> exposition;

    @FXML
    private TableColumn<Visite, String> horaireDebut;

    @FXML
    private TableColumn<Visite, String> identifiant;

    @FXML
    private TableColumn<Visite, String> intitule;

    @FXML
    private TableColumn<Visite, String> numTel;

    @FXML
    private TableView<Visite> tableExposition;
    
    /**
     * 
     */
    @FXML
    public void initialize() {

        conferencier.setCellValueFactory(cellData -> {
            Visite visite = cellData.getValue();
            return new SimpleStringProperty(visite.getConferencier().getNom()); 
        });
        
        date.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        
        employe.setCellValueFactory(cellData -> {
            Visite visite = cellData.getValue();
            return new SimpleStringProperty(visite.getEmploye().getNom()); 
        });
        
        exposition.setCellValueFactory(cellData -> {
            Visite visite = cellData.getValue();
            return new SimpleStringProperty(visite.getExposition()
                                                   .getIntitule()); 
        });
        
        horaireDebut.setCellValueFactory(cellData -> {
            Visite visite = cellData.getValue();
            return new SimpleStringProperty(visite.toStringHoraireDebut()); 
        });
        
        identifiant.setCellValueFactory(
                new PropertyValueFactory<>("identifiant"));
        
        intitule.setCellValueFactory(cellData -> {
            Visite visite = cellData.getValue();
            return new SimpleStringProperty(visite.getClient().getIntitule()); 
        });
        
        numTel.setCellValueFactory(cellData -> {
            Visite visite = cellData.getValue();
            return new SimpleStringProperty(visite.getClient().getNumTel()); 
        });
        
        
        ObservableList<Visite> visitesListe
        = FXCollections.observableArrayList(visites);
        tableExposition.setItems(visitesListe);
    }

    @FXML
    void retourAccueilAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }

    @FXML
    void quitterAction(ActionEvent event) {
        EchangeurDeVue.getFenetreAppli().hide();
    }

    @FXML
    void aideAction(ActionEvent event) {
        AccueilControleur.lancerAide();
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuDonneesCalculeesVue");
    }
    
    @FXML
    void btnFiltresAction(ActionEvent event) {
        EchangeurDeVue.creerPopUp("donneesCalculeesVisiteFiltresPopUP");
    }

    /**
     * Applique les critères de filtrage sur la liste des visites.
     * Parcourt la liste des visites et les filtre en fonction des
     * critères spécifiés dans l'objet CritereFiltre.
     * Les visites qui correspondent à tous les critères 
     * (type d'exposition, conférencier, exposition, dates 
     * et horaires)
     *  sont ajoutées à la liste des visites filtrées.
     *
     * @param critere objet contenant les critères de filtrage à 
     *                appliquer
     */
    public void appliquerFiltre(CritereFiltre critere) {
        // Filtrer la liste des visites en fonction des critères reçus
        ObservableList<Visite> visitesFiltrees = FXCollections
                                                 .observableArrayList();

        for (Visite visite : visites) {
            boolean match = true;

            // Filtrer par type d'exposition
            if (critere.getTypeExposition() != null 
                && !visite.getExposition().getType()
                                           .equals(critere
                                                   .getTypeExposition())) {
                match = false;
            }

            // Filtrer par conférencier
            if (critere.getConferencier() != null 
                && !visite.getConferencier().getNom()
                                             .equals(critere
                                                     .getConferencier())) { 
                match = false;
            }
            
            // Filtrer par exposition
            if (critere.getExposition() != null 
                && !visite.getExposition().getIntitule()
                                           .equals(critere.getExposition())) { 
                match = false;
            }

            // Filtrer par date de visite
            if (critere.getDateDebut() != null) {
                LocalDate dateFin;
                dateFin = critere.getDateFin() != null ? critere.getDateFin() 
                                                       : critere.getDateDebut();
                if (visite.getDate().isBefore(critere.getDateDebut()) 
                    || visite.getDate().isAfter(dateFin)) {
                    match = false;
                }
            }
            
            // Filtrer par plage horaire
            if (critere.getHoraireDebut() != 0) {
                int horaireFin;
                horaireFin = critere.getHoraireFin() != 0 
                                                    ? critere.getHoraireFin() 
                                                    : critere.getHoraireDebut();
                if (visite.getHoraireDebut() < critere.getHoraireDebut() 
                    || visite.getHoraireDebut() > horaireFin) {
                    match = false;
                }
            }

            if (match) {
                visitesFiltrees.add(visite);
            }
        }
        
        tableExposition.setItems(visitesFiltrees);
        
        LabelResultat.setText("Nombre de visite correspondant aux filtres : " 
                               + visitesFiltrees.size());
        
    }

}
