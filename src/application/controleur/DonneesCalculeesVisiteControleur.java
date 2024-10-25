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
import application.modele.Visite;
import application.utilitaire.TraitementDonnees;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Contrôleur pour la gestion des données importées des visites.
 * 
 * Cette classe gère l'affichage et les interactions relatives aux données 
 * calculées des visites dans l'application. Elle permet à l'utilisateur 
 * de visualiser les informations des visites, ainsi que d'effectuer 
 * des actions telles que le retour à l'écran d'accueil ou la fermeture de 
 * l'application.
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class DonneesCalculeesVisiteControleur {
    
    private Stage fenetreAppli;
    
    private static ArrayList<Visite> visites = TraitementDonnees.getVisites();
    
    /**
     * Définit la fenêtre de l'application.
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
      this.fenetreAppli = fenetreAppli;
    }
    
    @FXML
    private Button btnRetour;
    
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
            return new SimpleStringProperty(visite.getConferencier().getIdentifiant()); 
        });
        
        date.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        
        employe.setCellValueFactory(cellData -> {
            Visite visite = cellData.getValue();
            return new SimpleStringProperty(visite.getEmploye().getIdentifiant()); 
        });
        
        exposition.setCellValueFactory(cellData -> {
            Visite visite = cellData.getValue();
            return new SimpleStringProperty(visite.getExposition().getIdentifiant()); 
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
        fenetreAppli.hide();
    }

    @FXML
    void aideAction(ActionEvent event) {
        AccueilControleur.lancerAide();
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuDonneesCalculeesVue");
    }

}
