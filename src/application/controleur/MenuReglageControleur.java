/*
 * MenuReglageControleur.java                           
 * 21 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;


import java.io.File;
import java.util.Optional;

import application.EchangeurDeVue;
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
        alert.setHeaderText("Êtes-vous sûr de vouloir réinitialiser les données ?");
        alert.setContentText("Cette action est irréversible. Toutes les données seront supprimées.");

        // Attendre la réponse de l'utilisateur
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si l'utilisateur confirme, supprimer le fichier
            File fichierDonnees = new File("donnees");
            if (fichierDonnees.exists()) {
                if (fichierDonnees.delete()) {
                    // Succès de la suppression
                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("Succès");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Les données ont été réinitialisées avec succès.");
                    successAlert.showAndWait();
                    EchangeurDeVue.changerVue("accueilVue");
                } else {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText("Impossible de supprimer les données.");
                    errorAlert.setContentText("Veuillez vérifier les permissions ou réessayer.");
                    errorAlert.showAndWait();
                }
            } else {
                // Fichier inexistant
                Alert notFoundAlert = new Alert(AlertType.WARNING);
                notFoundAlert.setTitle("Fichier introuvable");
                notFoundAlert.setHeaderText("Aucune donnée à réinitialiser.");
                notFoundAlert.setContentText("Le fichier \"donnee\" n'existe pas à la racine du projet.");
                notFoundAlert.showAndWait();
            }
        }
    }
}
