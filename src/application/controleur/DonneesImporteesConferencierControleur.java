/*
 * DonneesImporteesConferencierControleur.java                           
 * 16 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.IOException;

import application.modele.Conferencier;
import application.modele.Indisponibilite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class DonneesImporteesConferencierControleur {
    
    private Stage fenetreAppli;
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
      this.fenetreAppli = fenetreAppli;
    }
    
    @FXML
    private Button btnRetour;
    
    @FXML
    private TableColumn<Conferencier, Boolean> estInterne;

    @FXML
    private TableColumn<Conferencier, String> identifiant;

    @FXML
    private TableColumn<Conferencier, Indisponibilite[]> indisponibilites;

    @FXML
    private TableColumn<Conferencier, String> nom;

    @FXML
    private TableColumn<Conferencier, String> numTel;

    @FXML
    private TableColumn<Conferencier, String> prenom;

    @FXML
    private TableColumn<Conferencier, String[]> specialites;

    @FXML
    private TableView<Conferencier> tableExposition;

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
