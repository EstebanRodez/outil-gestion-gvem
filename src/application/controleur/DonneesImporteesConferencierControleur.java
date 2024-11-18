/*
 * DonneesImporteesConferencierControleur.java                           
 * 16 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import application.EchangeurDeVue;
import application.modele.Conferencier;
import application.modele.Indisponibilite;
import application.utilitaire.TraitementDonnees;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;

/**
 * Contrôleur pour la gestion des données importées des conférenciers.
 * 
 * Cette classe gère l'affichage et les interactions relatives aux données 
 * importées des conférenciers dans l'application. Elle permet à l'utilisateur 
 * de visualiser les informations des conférenciers, ainsi que d'effectuer 
 * des actions telles que le retour à l'écran d'accueil ou la fermeture de 
 * l'application. 
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class DonneesImporteesConferencierControleur {
    
    private static LinkedHashMap<String, Conferencier> conferenciers
    = TraitementDonnees.getDonnees().getConferenciers();
    
    @FXML
    private Button aideAction;
    
    @FXML
    private Button btnRetour;
    
    @FXML
    private Button btnGenererPDF;
    
    @FXML
    private TableColumn<Map.Entry<String, Conferencier>, String> estInterne;

    @FXML
    private TableColumn<Map.Entry<String, Conferencier>, String> identifiant;

    @FXML
    private TableColumn<Map.Entry<String, Conferencier>, String> indisponibilites;

    @FXML
    private TableColumn<Map.Entry<String, Conferencier>, String> nom;

    @FXML
    private TableColumn<Map.Entry<String, Conferencier>, String> numTel;

    @FXML
    private TableColumn<Map.Entry<String, Conferencier>, String> prenom;

    @FXML
    private TableColumn<Map.Entry<String, Conferencier>, String> specialites;

    @FXML
    private TableView<Map.Entry<String, Conferencier>> tableExposition;
    
    @FXML
    void btnGenererPDFAction(ActionEvent event) {
        /*try {
            // Create a list to hold VisiteMoyenneResultat objects
            List<VisiteMoyenneResultat> results = new ArrayList<>();
           
            // Generate PDF with the results
            CreerPdf pdfGenerator = new CreerPdf();
            pdfGenerator.generatePdf("rapport_Conferenciers_sans_traitements.pdf", results);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } */
    }

    
    
    /**
     * 
     */
    @FXML
    public void initialize() {
        
        estInterne.setCellValueFactory(
            cellData -> new SimpleStringProperty(getEstInterneAsString(
                    getConferencier(cellData).estInterne()))
        );
        identifiant.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getKey())
        );
        indisponibilites.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                    toStringIndisponibilites(getConferencier(cellData)
                            .getIndisponibilites()))
        );
        nom.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                    getConferencier(cellData).getNom())
        );
        numTel.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                    getConferencier(cellData).getNumTel())
        );
        prenom.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                    getConferencier(cellData).getPrenom())
        );
        specialites.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                    getConferencier(cellData).toStringSpecialites())
        );
        
        ObservableList<Map.Entry<String, Conferencier>> conferenciersListe
        = FXCollections.observableArrayList(conferenciers.entrySet());
        tableExposition.setItems(conferenciersListe);
    }
    
    private static Conferencier getConferencier(
            CellDataFeatures<Entry<String, Conferencier>, String> celluleDonnees) {
        
        return celluleDonnees.getValue().getValue();
    }
    
    private static String getEstInterneAsString(Boolean estInterne) {
        return estInterne != null && estInterne ? "Oui" : "Non";
    }
    
    private static String toStringIndisponibilites(
            Indisponibilite[] indisponibilites) {
        
        if (indisponibilites == null || indisponibilites.length == 0) {
            return "Aucune indisponibilité";
        }
        
        String[] indisponibilitesTextes = new String[indisponibilites.length];
        
        for (int i = 0; i < indisponibilites.length; i++) {
            indisponibilitesTextes[i] = indisponibilites[i].toString();
        }
        
        return String.join(", ", indisponibilitesTextes);
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
    	// Utilise l'indice 5 pour ouvrir un lien d'aide correspondant
    	// à la réference de la partit des conferenciers dans données importer
        AccueilControleur.lancerAide(5);
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuDonneesImporterVue");
    }

}
