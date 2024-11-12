/*
 * MenuStatistiqueControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import application.EchangeurDeVue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Contrôleur pour le menu des statistiques.
 * 
 * Cette classe gère les interactions de l'utilisateur avec le menu
 * des statistiques, permettant d'accéder aux différentes options
 * liées aux données statistiques de l'application.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class MenuStatistiqueControleur {
    
    @FXML
    private Button btnAccueil;

    @FXML
    private Button btnClassement;

    @FXML
    private Button btnDonnesCalculees;

    @FXML
    private Button btnDonnesImportees;

    @FXML
    private Button btnPourcentage;

    @FXML
    void btnAccueilAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }

    @FXML
    void btnPourcentageAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuStatistiquePourcentageVue");
    }

    @FXML
    void btnDonnesCalculeesAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuDonneesCalculeesVue");
    }

    @FXML
    void btnDonnesImporteesAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuDonneesImporterVue");
    }

    @FXML
    void btnClassementAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuStatistiqueClassementVue");
    }

}
