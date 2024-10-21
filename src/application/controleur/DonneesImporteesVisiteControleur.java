/*
 * DonneesImporteesVisiteControleur.java                           
 * 16 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import application.modele.Client;
import application.modele.Conferencier;
import application.modele.Employe;
import application.modele.Exposition;
import application.modele.Visite;
import application.utilitaire.ImportationCSV;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Contrôleur pour la gestion des données importées des visites.
 * 
 * Cette classe gère l'affichage et les interactions relatives aux données 
 * importées des visites dans l'application. Elle permet à l'utilisateur 
 * de visualiser les informations des visites, ainsi que d'effectuer 
 * des actions telles que le retour à l'écran d'accueil ou la fermeture de 
 * l'application.
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class DonneesImporteesVisiteControleur {
    
    private Stage fenetreAppli;
    
    private static List<Visite> visite = ImportationCSV.getVisites();
    
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
        
        
        ObservableList<Visite> visiteList
        = FXCollections.observableArrayList(visite);
        tableExposition.setItems(visiteList);
    }

    @FXML
    void retourAccueilAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/accueilVue.fxml"));
        Parent accueilVue = loader.load();
        AccueilControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(accueilVue));
    }

    @FXML
    void quitterAction(ActionEvent event) {
        fenetreAppli.hide();
    }

    @FXML
    void aideAction(ActionEvent event) {
    	final String LIEN_REGLES
        = "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?usp=sharing";

        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(LIEN_REGLES));
        } catch (IOException | URISyntaxException e) {
            Alert boiteErreurInconnueOuverture =
                    new Alert(Alert.AlertType.ERROR, 
                              "impossible d'ouvrir le fichier d'aide",
                              ButtonType.OK);

            boiteErreurInconnueOuverture.setTitle("Erreur d'affichage aide");
            boiteErreurInconnueOuverture.setHeaderText("Erreur d'affichage aide");

            boiteErreurInconnueOuverture.showAndWait();
        }
    }

    @FXML
    void btnRetourAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/menuDonneesImporterVue.fxml"));
        Parent menuDonneesImporterVue = loader.load();
        MenuDonneesImporterControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(menuDonneesImporterVue));
    }

}
