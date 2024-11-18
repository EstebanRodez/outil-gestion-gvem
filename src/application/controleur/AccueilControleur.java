package application.controleur;

import java.awt.Desktop;
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
 * Cette classe gère les interactions utilisateur au sein de
 * l'interface d'accueil. Elle est responsable de la navigation entre
 * différentes vues de l'application en réponse aux actions des
 * boutons.
 * Chaque méthode de cette classe est associée à un bouton dans
 * l'interface pour effectuer des actions spécifiques comme
 * consulter, importer, exporter des données ou quitter
 * l'application.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class AccueilControleur {

    // Stockage des liens avec signets
    private static final String[] liens = {
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.phccnmenp45", // pageAccueil
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.szfgaenvr4bk",// donnees calc Conf
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.1gzjdlhuen4l", // donnees calc Expo
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.qmhq2qcd6w1o", // donnees calc Visite
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.rzodinig137o", // donnees import Expo
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.412ilhctyjqe", // donnees import Conf
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.qmhq2qcd6w1o", // donnees import Visite
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.an8iar9agec5", // exportation
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.axvpwnml3l6h", // import fichier
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.vv8lnbpm49el" // import fichier distant
    };

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
     * Ouvre l'URL dans le navigateur avec un signet spécifique
     * 
     * @param indice l'indice du lien à ouvrir.
     */
    public static void lancerAide(int indice) {
        // Validation de l'indice
        if (indice < 0 || indice >= liens.length) {
            afficherErreurAide();
            return;
        }

        String url = liens[indice]; // Obtient l'URL avec le signet associé

        try {
            Desktop desktop = Desktop.getDesktop();
            URI uri = new URI(url);
            desktop.browse(uri);  // Ouvre l'URL dans le navigateur
        } catch (IOException | URISyntaxException e) {
            afficherErreurAide();  // En cas d'erreur, affiche une boîte d'erreur
        }
    }

    /**
     * Affiche une fenêtre d'erreur en cas d'échec d'ouverture de l'URL.
     */
    private static void afficherErreurAide() {
        Alert boiteErreurOuvertureAide = new Alert(Alert.AlertType.ERROR,
                "Impossible d'ouvrir le fichier d'aide", ButtonType.OK);
        boiteErreurOuvertureAide.setTitle("Erreur d'affichage aide");
        boiteErreurOuvertureAide.setHeaderText("Erreur d'affichage aide");
        boiteErreurOuvertureAide.showAndWait();
    }

    @FXML
    void btnAideAction(ActionEvent event) {
        // Appel à ouvrirDansNavigateur avec un indice spécifique pour ouvrir l'aide
    	lancerAide(1); // Par exemple, ouvre le lien associé à l'indice 1
    }

    @FXML
    public void initialize() {
        
        if (TraitementDonnees.getDonnees().isDonneesVides()) {
            btnConsulterDonnees.setDisable(true);
            btnExporter.setDisable(true);
        }
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
