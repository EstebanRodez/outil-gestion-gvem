/*
 * ImporterDistantValideControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import application.EchangeurDeVue;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Contrôleur pour la gestion de l'importation de données à distance après
 * une connexion réussie.
 * 
 * Cette classe permet à l'utilisateur de procéder à l'importation de données
 * à distance une fois qu'il a validé la connexion avec le serveur.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class ImporterDistantValideControleur {
    
    @FXML
    private Button btnImporter;

    @FXML
    private Button btnAide;

    @FXML
    private Button btnRetour;

    @FXML
    void btnAideAction(ActionEvent event) {
        AccueilControleur.lancerAide();
    }

    @FXML
    void btnImporterAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("importerDistantVue");
    }

}
