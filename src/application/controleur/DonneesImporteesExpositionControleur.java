/*
 * DonneesImporteesExpositionControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import application.EchangeurDeVue;
import application.modele.Exposition;
import application.modele.ExpositionTemporaire;
import application.utilitaire.TraitementDonnees;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
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
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class DonneesImporteesExpositionControleur {
    
    private static LinkedHashMap<String, Exposition> expositions
    = TraitementDonnees.getDonnees().getExpositions();
    
    // Format pour les dates au format jj/MM/aaaa
    private static final DateTimeFormatter DATE_FORMAT 
    = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private Button btnRetour;
    
    @FXML
    private TableColumn<Map.Entry<String, Exposition>, String> dateDebut;

    @FXML
    private TableColumn<Map.Entry<String, Exposition>, String> dateFin;

    @FXML
    private TableColumn<Map.Entry<String, Exposition>, String> identifiant;

    @FXML
    private TableColumn<Map.Entry<String, Exposition>, String> intitule;

    @FXML
    private TableColumn<Map.Entry<String, Exposition>, String> motsCles;

    @FXML
    private TableColumn<Map.Entry<String, Exposition>, String> nbOeuvre;

    @FXML
    private TableColumn<Map.Entry<String, Exposition>, String> periodeDebut;

    @FXML
    private TableColumn<Map.Entry<String, Exposition>, String> periodeFin;

    @FXML
    private TableColumn<Map.Entry<String, Exposition>, String> resume;

    @FXML
    private TableView<Map.Entry<String, Exposition>> tableExposition;
    
    /**
     * 
     */
    @FXML
    public void initialize() {
        
        dateDebut.setCellValueFactory(
            new Callback<CellDataFeatures<Map.Entry<String, Exposition>, String>, 
                         ObservableValue<String>>() {
                    
            public ObservableValue<String> call(
                    CellDataFeatures<Map.Entry<String, Exposition>,
                                     String> ligne) {
                
                Exposition objet = ligne.getValue().getValue();
                /* La date de début n'existe pas */
                if (!(objet instanceof ExpositionTemporaire)) {
                    return null;
                }
                
                ExpositionTemporaire expoTempo = (ExpositionTemporaire) objet;
                return new SimpleStringProperty(
                        formatDate(expoTempo.getDateDebut()));
            }
        });
        
        dateFin.setCellValueFactory(
            new Callback<CellDataFeatures<Map.Entry<String, Exposition>, String>, 
                         ObservableValue<String>>() {
                    
            public ObservableValue<String> call(
                    CellDataFeatures<Map.Entry<String, Exposition>,
                                     String> ligne) {
                
                Exposition objet = ligne.getValue().getValue();
                /* La date de fin n'existe pas */
                if (!(objet instanceof ExpositionTemporaire)) {
                    return null;
                }
                
                ExpositionTemporaire expoTempo = (ExpositionTemporaire) objet;
                return new SimpleStringProperty(
                        formatDate(expoTempo.getDateFin()));
            }
        });

        identifiant.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getKey())
        );
        intitule.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                    getExposition(cellData).getIntitule())
        );
        periodeDebut.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                    Integer.toString(getExposition(cellData).getPeriodeDebut()))
        );
        periodeFin.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                    Integer.toString(getExposition(cellData).getPeriodeFin()))
        );
        nbOeuvre.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                    Integer.toString(getExposition(cellData).getNbOeuvre()))
        );
        resume.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                    getExposition(cellData).getResume())
        );
        motsCles.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                    getExposition(cellData).toStringMotsCles())
        );
    
        ObservableList<Map.Entry<String, Exposition>> exposListe
        = FXCollections.observableArrayList(expositions.entrySet());
        tableExposition.setItems(exposListe);
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
    
    private static Exposition getExposition(
            CellDataFeatures<Entry<String, Exposition>, String> celluleDonnees) {
        
        return celluleDonnees.getValue().getValue();
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
        EchangeurDeVue.changerVue("menuDonneesImporterVue");
    }
}
