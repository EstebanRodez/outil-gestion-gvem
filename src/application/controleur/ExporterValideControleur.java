/*
 * ExporterValideControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import application.EchangeurDeVue;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Contrôleur pour la confirmation d'exportation des données.
 * 
 * Cette classe gère les interactions de l'utilisateur après qu'une 
 * exportation valide a été établie. Elle permet à l'utilisateur 
 * d'envoyer des données, d'accéder à l'aide ou de retourner à la vue 
 * précédente.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class ExporterValideControleur {
    
    @FXML
    private Button btnAide;

    @FXML
    private Button btnEnvoyer;

    @FXML
    private Button btnRetour;

    @FXML
    void btnAideAction(ActionEvent event) {
        AccueilControleur.lancerAide();
    }

    @FXML
    void btnEnvoyerAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("exporterVue");
    }

}
