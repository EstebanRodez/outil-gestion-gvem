/*
 * ImporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import application.EchangeurDeVue;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Contrôleur pour la gestion de l'importation des données.
 * 
 * Cette classe permet à l'utilisateur de choisir entre deux méthodes 
 * d'importation de données : depuis un fichier local ou à distance. 
 * Elle gère également les interactions liées à l'aide et au retour 
 * vers l'interface d'accueil.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class ImporterControleur {
    
    @FXML
    private Button btnAide;

    @FXML
    private Button btnImporterDistant;

    @FXML
    private Button btnImporterLocal;

    @FXML
    private Button btnRetour;

    @FXML
    void btnAideAction(ActionEvent event) {
    	// Appel de la méthode lancerAide de AccueilControleur avec un indice spécifique
    	// Utilise l'indice 8 pour ouvrir un lien d'aide correspondant
    	// à la réference de la partit des conferenciers dans données calculées
        AccueilControleur.lancerAide(8);
    }

    @FXML
    void btnImporterDistantAction(ActionEvent event) {
        EchangeurDeVue.changerVue("importerDistantVue");
    }

    @FXML
    void btnImporterLocalAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuImportationVue");
    }


    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }

}
