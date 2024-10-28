/*
 * DonneesImporteesConferencierControleur.java                           
 * 25 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Contrôleur pour la gestion des données importées des conférenciers.
 * 
 * Cette classe gère l'affichage et les interactions relatives aux données 
 * caclulées des conférenciers dans l'application. Elle permet à l'utilisateur 
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
public class DonneesCalculeesConferencierControleur {
    
    private static ArrayList<Conferencier> conferenciers
    = TraitementDonnees.getConferenciers();
    
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
        
        ObservableList<Conferencier> conferenciersListe
        = FXCollections.observableArrayList(conferenciers);
        tableExposition.setItems(conferenciersListe);
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

}
