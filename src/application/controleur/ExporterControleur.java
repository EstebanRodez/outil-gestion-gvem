/*
 * ExporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Contrôleur pour la gestion de l'exportation des données.
 * 
 * Cette classe permet à l'utilisateur de se connecter à un serveur 
 * en fournissant une adresse IP et un port. Elle gère également 
 * la validation des entrées et l'affichage des erreurs éventuelles.
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class ExporterControleur {
    
    private Stage fenetreAppli;
    
    /**
     * Définit la fenêtre de l'application.
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
      this.fenetreAppli = fenetreAppli;
    }
    
    /**
     * Méthode pour vérifier si une adresse IP est valide
     * @param ip Adresse IP sous forme de chaîne
     * @return true si l'adresse est valide, sinon false
     */
    private boolean isValidIPAddress(String ip) {
        // Expression régulière pour vérifier une adresse IPv4
        String ipPattern = 
            "^([0-9]{1,3}\\.){3}[0-9]{1,3}$";
        
        if (!ip.matches(ipPattern)) {
            return false;
        }

        String[] segments = ip.split("\\.");
        for (String segment : segments) {
            int value = Integer.parseInt(segment);
            if (value < 0 || value > 255) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Méthode pour vérifier si le port est valide
     * @param port Chaîne représentant le port
     * @return true si le port est un entier valide entre 0 et 65535, sinon false
     */
    private boolean isValidPort(String port) {
        try {
            int portValue = Integer.parseInt(port);
            return portValue >= 0 && portValue <= 65535;
        } catch (NumberFormatException e) {
            return false;  // Si la chaîne n'est pas un entier, retourne false
        }
    }
    
    @FXML
    private Button btnAide;

    @FXML
    private Button btnConnexion;

    @FXML
    private Button btnRetour;

    @FXML
    private TextField txtFieldIPServeur;

    @FXML
    private TextField txtFieldPort;

    @FXML
    void btnAideAction(ActionEvent event) {

    }

    @FXML
    void btnConnexionAction(ActionEvent event) throws IOException {
        String ipServeur = txtFieldIPServeur.getText().trim();
        String port = txtFieldPort.getText().trim();
        if (isValidIPAddress(ipServeur) && isValidPort(port)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/exporterValideVue.fxml"));
            Parent exporterValideVue = loader.load();
            ExporterValideControleur controleur = loader.getController();
            controleur.setFenetreAppli(fenetreAppli);
            fenetreAppli.setScene(new Scene(exporterValideVue));
        } else if (isValidIPAddress(ipServeur) && !isValidPort(port)) {
            Alert boiteAlerte = new Alert(Alert.AlertType.ERROR,"Veuillez "
                    + "respecter la norme d'écriture "
                    + "d'un port :"
                    + "\n entre 0 et 65535");

            boiteAlerte.setTitle("Erreur sur le port");
            boiteAlerte.setHeaderText("Erreur sur le port");
            boiteAlerte.showAndWait();

        } else {
            Alert boiteAlerte = new Alert(Alert.AlertType.ERROR,"Veuillez "
                    + "respecter la norme d'écriture "
                    + "d'une adresse ip "
                    + "\n(exemple : 192.168.2.3)."
                    + " \noctet maximum = 255");

            boiteAlerte.setTitle("Erreur sur l'adresse IP");
            boiteAlerte.setHeaderText("Erreur sur l'adresse IP");
            boiteAlerte.showAndWait();
        }
    }

    @FXML
    void btnRetourAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/accueilVue.fxml"));
        Parent accueuilVue = loader.load();
        AccueilControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(accueuilVue));
    }

}
