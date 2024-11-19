/*
 * DonneesImporteesVisiteControleur.java                           
 * 16 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import application.EchangeurDeVue;
import application.modele.Visite;
import application.utilitaire.TraitementDonnees;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;

/**
 * Contrôleur pour la gestion des données importées des visites.
 * 
 * Cette classe gère l'affichage et les interactions relatives aux données 
 * importées des visites dans l'application. Elle permet à l'utilisateur 
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
public class DonneesImporteesVisiteControleur {
    
    private static LinkedHashMap<String, Visite> visites
    = TraitementDonnees.getDonnees().getVisites();
    
    // Format pour les dates au format jj/MM/aaaa
    private static final DateTimeFormatter DATE_FORMAT 
    = AccueilControleur.getDateFormatterFR();
    
    @FXML
    private Button aideAction;
    
    @FXML
    private Button btnRetour;
    
    @FXML
    private Button btnGenererPDF;
    
    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> conferencier;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> date;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> employe;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> exposition;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> horaireDebut;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> identifiant;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> intitule;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> numTel;

    @FXML
    private TableView<Map.Entry<String, Visite>> tableExposition;
    
    /**
     * 
     */
    @FXML
    public void initialize() {

        conferencier.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getConferencier().getNom()); 
        });
        
        date.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getDate().format(DATE_FORMAT)); 
        });
        
        employe.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getEmploye().getNom()); 
        });
        
        exposition.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getExposition().getIntitule()); 
        });
        
        horaireDebut.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).toStringHoraireDebut()); 
        });
        
        identifiant.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getKey()); 
        });
        
        intitule.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getClient().getIntitule()); 
        });
        
        numTel.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getClient().getNumTel()); 
        });
        
        ObservableList<Map.Entry<String, Visite>> visitesListe
        = FXCollections.observableArrayList(visites.entrySet());
        tableExposition.setItems(visitesListe);
    }
    
    @FXML
    void btnGenererPDFAction(ActionEvent event) {
        /*try {
            // Create a list to hold VisiteMoyenneResultat objects
            List<VisiteMoyenneResultat> results = new ArrayList<>();

            // Generate PDF with the results
            CreerPdf pdfGenerator = new CreerPdf();
            pdfGenerator.generatePdf("rapport_visites_sans_traitements.pdf", results);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } */
    }
    
    private static Visite getVisite(
            CellDataFeatures<Entry<String, Visite>, String> celluleDonnees) {
        
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
    	// Appel de la méthode lancerAide de AccueilControleur avec un indice spécifique
    	// Utilise l'indice 6 pour ouvrir un lien d'aide correspondant
    	// à la réference de la partit des visite dans données importer
        AccueilControleur.lancerAide(6);
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuDonneesImporterVue");
    }

}
