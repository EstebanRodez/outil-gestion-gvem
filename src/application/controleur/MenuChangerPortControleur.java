/*
 * MenuChangerPortControleur.java                           
 * 21 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import application.EchangeurDeVue;
import application.utilitaire.Reseau;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Contrôleur pour le menu de changement de port.
 * Gère la modification et l'affichage du port actuel utilisé par l'application.
 */
public class MenuChangerPortControleur {

    @FXML
    private Button btnAide;

    @FXML
    private Button btnChanger;

    @FXML
    private Button btnRetour;

    @FXML
    private TextField txtFieldNouveauPort;

    @FXML
    private Label labelPortActuel;

    /**
     * Initialise le contrôleur en affichant le port actuel au démarrage.
     */
    @FXML
    public void initialize() {
        rafraichirPort();
    }

    /**
     * Met à jour dynamiquement le label du port actuel.
     */
    public void rafraichirPort() {
        int portActuel = Reseau.getPortExportation();
        labelPortActuel.setText(Integer.toString(portActuel));
    }

    @FXML
    void btnAideAction(ActionEvent event) {
    }

    @FXML
    void btnChangerAction(ActionEvent event) {
        try {
            int nouveauPort = Integer.parseInt(txtFieldNouveauPort.getText());
            Reseau.setPortExportation(nouveauPort);

            // Mettre à jour labelPortActuel dans ce contrôleur
            rafraichirPort();

            // Vérifier si la vue exporterVue est déjà chargée
            FXMLLoader loader = EchangeurDeVue.getFXMLLoader("exporterVue");
            if (loader != null) {
                ExporterControleur controleur = (ExporterControleur) loader.getController();
                if (controleur != null) {
                    controleur.rafraichirPort();
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le port a été mis à jour avec succès : " + Reseau.getPortExportation());
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un nombre valide.");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuReglageVue");
    }
}
