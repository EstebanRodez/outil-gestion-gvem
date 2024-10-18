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

import java.util.List;

import application.utilitaire.ImportationCSV;
import application.modele.Exposition;
import application.modele.ExpositionTemporaire;
import javafx.beans.property.SimpleStringProperty;
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
 * Contrôleur pour la gestion des données importées des expositions.
 * 
 * Cette classe gère l'affichage et les interactions relatives aux données 
 * importées des expositions dans l'application. Elle permet à l'utilisateur 
 * de visualiser les informations des expositions, ainsi que d'effectuer 
 * des actions telles que le retour à l'écran d'accueil ou la fermeture de 
 * l'application.
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class DonneesImporteesExpositionControleur {
    
    private Stage fenetreAppli;
    
    /**
     * Définit la fenêtre de l'application.
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
        this.fenetreAppli = fenetreAppli;
    }
    
    static List<Exposition> expo = ImportationCSV.getExpositions();
    
    @FXML
    private Button btnRetour;
    
    @FXML
    private TableColumn<ExpositionTemporaire, String> dateDebut;

    @FXML
    private TableColumn<ExpositionTemporaire, String> dateFin;

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
        
        dateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));


        identifiant.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        intitule.setCellValueFactory(new PropertyValueFactory<>("intitule"));
        periodeDebut.setCellValueFactory(new PropertyValueFactory<>("periodeDebut"));
        periodeFin.setCellValueFactory(new PropertyValueFactory<>("periodeFin"));
        nbOeuvre.setCellValueFactory(new PropertyValueFactory<>("nbOeuvre"));
        resume.setCellValueFactory(new PropertyValueFactory<>("resume"));
        
        // Pour motscles, convertie le tableau en chaine de caractere
        motsCles.setCellValueFactory(cellData -> 
        new SimpleStringProperty(getMotsClesAsString
                (cellData.getValue().getMotsCles())));
    
    
        // Populate the table with the imported exhibitions
        ObservableList<Exposition> exposList = FXCollections.observableArrayList(expo);
        tableExposition.setItems(exposList);
    }

    // Helper method to convert String[] to String
    private String getMotsClesAsString(String[] motsCles) {
        return motsCles != null ? String.join(", ", motsCles) : "";
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
<<<<<<< HEAD
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
=======
        // Implement help functionality if needed
>>>>>>> 5b51919 (feat: ajout des élément sur le tableau importe exposition)
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
