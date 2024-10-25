/*
 * DonneesImporteesConferencierControleur.java                           
 * 25 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import application.modele.Conferencier;
import application.modele.Indisponibilite;
import application.utilitaire.TraitementDonnees;
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
 * Contrôleur pour la gestion des données importées des conférenciers.
 * 
 * Cette classe gère l'affichage et les interactions relatives aux données 
 * caclulées des conférenciers dans l'application. Elle permet à l'utilisateur 
 * de visualiser les informations des conférenciers, ainsi que d'effectuer 
 * des actions telles que le retour à l'écran d'accueil ou la fermeture de 
 * l'application. 
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class DonneesCalculeesConferencierControleur {
    
    private Stage fenetreAppli;
    
    /**
     * Définit la fenêtre de l'application.
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
      this.fenetreAppli = fenetreAppli;
    }
    
    static List<Conferencier> conf = TraitementDonnees.getConferenciers();
    
    @FXML
    private Button btnRetour;
    
    @FXML
    private TableColumn<Conferencier, String> estInterne;

    @FXML
    private TableColumn<Conferencier, String> identifiant;

    @FXML
    private TableColumn<Conferencier, String> indisponibilites;

    @FXML
    private TableColumn<Conferencier, String> nom;

    @FXML
    private TableColumn<Conferencier, String> numTel;

    @FXML
    private TableColumn<Conferencier, String> prenom;

    @FXML
    private TableColumn<Conferencier, String> specialites;

    @FXML
    private TableView<Conferencier> tableExposition;
    
    /**
     * 
     */
    @FXML
    public void initialize() {
        
        estInterne.setCellValueFactory(cellData -> 
        new SimpleStringProperty(getEstInterneAsString(cellData.getValue().estInterne())));
        identifiant.setCellValueFactory(new PropertyValueFactory<>("identifiant"));


        indisponibilites.setCellValueFactory(cellData -> 
        new SimpleStringProperty(getIndisponibilitesAsString(cellData.getValue().getIndisponibilites())));
        
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        numTel.setCellValueFactory(new PropertyValueFactory<>("numTel"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        specialites.setCellValueFactory(cellData -> 
        new SimpleStringProperty(String.join(", ", cellData.getValue().getSpecialites())));
        
        ObservableList<Conferencier> exposList = FXCollections.observableArrayList(conf);
        tableExposition.setItems(exposList);
    }
    
    private static String getEstInterneAsString(Boolean estInterne) {
        return estInterne != null && estInterne ? "Oui" : "Non";
    }
    
    private static String getIndisponibilitesAsString(Indisponibilite[] indisponibilitesArray) {
        if (indisponibilitesArray != null && indisponibilitesArray.length > 0) {
            return Arrays.stream(indisponibilitesArray)
                         .map(Indisponibilite::toString)
                         .collect(Collectors.joining(", "));
        } else {
            return "Aucune indisponibilité";
        }
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/menuDonneesCalculeesVue.fxml"));
        Parent menuDonneesCalculeesVue = loader.load();
        MenuDonneesCalculeesControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(menuDonneesCalculeesVue));
    }

}
