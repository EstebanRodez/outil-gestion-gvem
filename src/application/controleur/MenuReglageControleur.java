/*
 * MenuReglageControleur.java                           
 * 21 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.util.Optional;

import application.EchangeurDeVue;
import application.utilitaire.SauvegardeDonnees;
import application.utilitaire.TraitementDonnees;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class MenuReglageControleur {

    @FXML
    private Button btnAccueil;

    @FXML
    private Button btnAide;

    @FXML
    private Button btnPort;

    @FXML
    private Button btnRenit;

    @FXML
    void btnAccueilAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }

    @FXML
    void btnAideAction(ActionEvent event) {

    }

    @FXML
    void btnPortAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuChangerPort");
    }

    @FXML
    void btnRenitAction(ActionEvent event) {
        
        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Réinitialisation des données");
        alert.setHeaderText("Êtes-vous sûr de vouloir réinitialiser les "
                            + "données ?");
        alert.setContentText("Cette action est irréversible. Toutes les "
                             + "données seront supprimées.");

        // Attendre la réponse de l'utilisateur
        Optional<ButtonType> choix = alert.showAndWait();
        if (choix.isPresent() && choix.get() == ButtonType.OK) {
            
            // Si l'utilisateur confirme, supprimer le fichier
            if (SauvegardeDonnees.supprimerDonnees()) {
                
                TraitementDonnees.supprimerDonnees();
                
                // Succès de la suppression
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText(
                        "Les données ont été réinitialisées avec succès.");
                successAlert.showAndWait();
                EchangeurDeVue.changerVue("accueilVue");
            } else {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(
                        "Impossible de supprimer les données.");
                errorAlert.setContentText(
                        "Veuillez vérifier les permissions ou réessayer.");
                errorAlert.showAndWait();
            }
        }
    }
}
