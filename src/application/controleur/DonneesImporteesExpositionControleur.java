/*
 * DonneesImporteesExpositionControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import application.utilitaire.TraitementDonnees;
import application.modele.Exposition;
import application.modele.ExpositionTemporaire;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Contrôleur pour la gestion des données importées des expositions.
 * 
 * Cette classe gère l'affichage et les interactions relatives aux données 
 * importées des expositions dans l'application. Elle permet à l'utilisateur 
 * de visualiser les informations des expositions, ainsi que d'effectuer 
 * des actions telles que le retour à l'écran d'accueil ou la fermeture de 
 * l'application.
 * 
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class DonneesImporteesExpositionControleur {
    
    private Stage fenetreAppli;
    
    private static List<Exposition> expo = TraitementDonnees.getExpositions();
    
    // Format pour les dates au format jj/MM/aaaa
    private static final DateTimeFormatter DATE_FORMAT 
    = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private Button btnRetour;
    
    @FXML
    private TableColumn<Exposition, String> dateDebut;

    @FXML
    private TableColumn<Exposition, String> dateFin;

    @FXML
    private TableColumn<Exposition, String> identifiant;

    @FXML
    private TableColumn<Exposition, String> intitule;

    @FXML
    private TableColumn<Exposition, String> motsCles;

    @FXML
    private TableColumn<Exposition, Integer> nbOeuvre;

    @FXML
    private TableColumn<Exposition, Integer> periodeDebut;

    @FXML
    private TableColumn<Exposition, Integer> periodeFin;

    @FXML
    private TableColumn<Exposition, String> resume;

    @FXML
    private TableView<Exposition> tableExposition;
    
    /**
     * 
     */
    @FXML
    public void initialize() {
        
        dateDebut.setCellValueFactory(
                new Callback<CellDataFeatures<Exposition, String>, 
                             ObservableValue<String>>() {
                    
            public ObservableValue<String> call(
                    CellDataFeatures<Exposition, String> ligne) {
                
                /* La date de début n'existe pas */
                if (!(ligne.getValue() instanceof ExpositionTemporaire)) {
                    return null;
                }
                
                ExpositionTemporaire expoTempo 
                = (ExpositionTemporaire) ligne.getValue();
                return new SimpleStringProperty(
                        formatDate(expoTempo.getDateDebut()));
            }
        });
        
        dateFin.setCellValueFactory(
                new Callback<CellDataFeatures<Exposition, String>, 
                             ObservableValue<String>>() {
                    
            public ObservableValue<String> call(
                    CellDataFeatures<Exposition, String> ligne) {
                
                /* La date de fin n'existe pas */
                if (!(ligne.getValue() instanceof ExpositionTemporaire)) {
                    return null;
                }
                
                ExpositionTemporaire expoTempo 
                = (ExpositionTemporaire) ligne.getValue();
                return new SimpleStringProperty(
                        formatDate(expoTempo.getDateFin()));
            }
        });

        identifiant.setCellValueFactory(
                new PropertyValueFactory<>("identifiant"));
        intitule.setCellValueFactory(new PropertyValueFactory<>("intitule"));
        periodeDebut.setCellValueFactory(
                new PropertyValueFactory<>("periodeDebut"));
        periodeFin.setCellValueFactory(
                new PropertyValueFactory<>("periodeFin"));
        nbOeuvre.setCellValueFactory(new PropertyValueFactory<>("nbOeuvre"));
        resume.setCellValueFactory(new PropertyValueFactory<>("resume"));
        
        // Pour motscles, convertie le tableau en chaîne de caractère
        motsCles.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        toStringMotsCles(cellData.getValue().getMotsCles()))
        );
    
        // Populate the table with the imported exhibitions
        ObservableList<Exposition> exposList
        = FXCollections.observableArrayList(expo);
        tableExposition.setItems(exposList);
    }
    
    /**
     * Formatte une date en chaîne de caractère en format française.
     * 
     * @param date la date à convertir
     * @return la data convertie dans le format français
     */
    private static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMAT) : "";
    }

    
    /**
     * Transforme le tableau des mots clés en une chaîne de caractère
     * plus visible.
     * 
     * @param motsCles la liste des mots clés
     * @return la chaîne de caractère contenant les mots clés
     */
    private static String toStringMotsCles(String[] motsCles) {
        return motsCles != null ? String.join(", ", motsCles) : "";
    }
    
    /**
     * Définit la fenêtre de l'application.
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
        this.fenetreAppli = fenetreAppli;
    }

    @FXML
    void retourAccueilAction(ActionEvent event) throws IOException {
        
        FXMLLoader loader
        = new FXMLLoader(
                getClass().getResource("/application/vue/accueilVue.fxml"));
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

        AccueilControleur.lancerAide();
    }

    @FXML
    void btnRetourAction(ActionEvent event) throws IOException {
        
        FXMLLoader loader
        = new FXMLLoader(
                getClass().getResource("/application/vue/menuDonneesImporterVue"
                                       + ".fxml")
                );
        Parent menuDonneesImporterVue = loader.load();
        MenuDonneesImporterControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(menuDonneesImporterVue));
    }
}
