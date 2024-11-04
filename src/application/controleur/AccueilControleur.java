/*
 * AcceuilControleur.java                           
 * 11 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import application.EchangeurDeVue;
import application.utilitaire.TraitementDonnees;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 * Contrôleur de l'interface d'accueil de l'application.
 * 
 * Cette classe gère les interactions utilisateur au sein de l'interface d'accueil. 
 * Elle est responsable de la navigation entre différentes vues de l'application 
 * en réponse aux actions des boutons. 
 * Chaque méthode de cette classe est associée à un bouton dans l'interface 
 * pour effectuer des actions spécifiques comme consulter, importer, 
 * exporter des données ou quitter l'application. 
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class AccueilControleur {
    
    private static final String LIEN_AIDE
    = "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0u"
      + "ORFgoRIY";
    
    @FXML
    private Button btnAide;

    @FXML
    private Button btnConsulterDonnees;

    @FXML
    private Button btnExporter;

    @FXML
    private Button btnImporter;

    @FXML
    private Button btnQuitter;
    
    /**
     * Lance l'aide à l'utilisateur
     */
    public static void lancerAide() {
        
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(getLienAide()));
        } catch (IOException | URISyntaxException e) {
            lancerErreurAide();
        }
    }
    
    /**
     * Renvoie le lien de la fiche d'aide.
     * @return le lien de la fiche d'aide
     */
    public static String getLienAide() {
        return LIEN_AIDE;
    }
    
    /**
     * Lance une fenêtre d'erreur en cas d'échec d'ouverture de la
     * fiche d'aide.
     */
    private static void lancerErreurAide() {
        
        Alert boiteErreurOuvertureAide
        = new Alert(Alert.AlertType.ERROR,
                    "impossible d'ouvrir le fichier d'aide", ButtonType.OK);
        boiteErreurOuvertureAide.setTitle("Erreur d'affichage aide");
        boiteErreurOuvertureAide.setHeaderText("Erreur d'affichage aide");
        boiteErreurOuvertureAide.showAndWait();
    }
    
    /**
     * 
     */
    @FXML
    public void initialize() {
        
        if (TraitementDonnees.isDonneesVides()) {
            btnConsulterDonnees.setDisable(true);
            btnExporter.setDisable(true);
        }
    }

    @FXML
    void btnAideAction(ActionEvent event) {
        lancerAide();
    }

    @FXML
    void btnConsulterDonneesAction(ActionEvent event) {   
        EchangeurDeVue.changerVue("menuDonneesImporterVue");
    }

    @FXML
    void btnExporterAction(ActionEvent event) {
        EchangeurDeVue.changerVue("exporterVue");
    }

    @FXML
    void btnImporterAction(ActionEvent event) {
        EchangeurDeVue.changerVue("importerVue");
    }

    @FXML
    void btnQuitterAction(ActionEvent event) {
        EchangeurDeVue.getFenetreAppli().hide();
    }

}