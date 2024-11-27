package application.controleur;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import application.EchangeurDeVue;
import application.utilitaire.TraitementDonnees;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;

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
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.9v5510j1sdze", // donnees calc Visite
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.rzodinig137o", // donnees import Expo
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.412ilhctyjqe", // donnees import Conf
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.qmhq2qcd6w1o", // donnees import Visite
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.an8iar9agec5", // exportation
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.axvpwnml3l6h", // import fichier
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.vv8lnbpm49el", // import fichier distant
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.9in0biog55ti", // Stat Classement Expo
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.vldp0n93ted0", // Stat Classement Conf
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.6o2rk7o9eltt", // Stat Pourcentage Expo
        "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?tab=t.0#bookmark=id.ukb3nu3upixl" // Stat Pourcentage Conf
    };

    private static final String PDF_CREATION_SUCCES 
        = "Le fichier pdf a était crée avec succés";
    
    private static final String ERREUR_I_O 
        = "Une erreur est survenue lors de la génération du fichier PDF.";

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
    
    @FXML
    private Button btnReglage;

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
     * Retourne un formateur de dates pour le format français "jj/MM/aaaa".
     * 
     * <p>Ce formateur peut être utilisé pour afficher ou parser des dates 
     * dans le format standard français (jour/mois/année).</p>
     * 
     * @return un formateur de type {@link DateTimeFormatter} configuré
     *         pour le format "dd/MM/yyyy".
     */
    public static DateTimeFormatter getDateFormatterFR() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
    	lancerAide(0); // Par exemple, ouvre le lien associé à l'indice 0
    }

    /**
     * Méthode lancée à l'initialisation du contrôleur
     */
    @FXML
    public void initialize() {
        mettreAJourBoutons(); 
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

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de sortie");
        alert.setHeaderText("Êtes-vous sûr de vouloir quitter ?");

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            EchangeurDeVue.getFenetreAppli().hide();
        }
    }
    
    /**
     * Message d'alerte lorsque le pdf a était crée avec succes.
     */
    public static void alertePdfSucces() {
        Alert boiteInformationPdf
        = new Alert(Alert.AlertType.INFORMATION, PDF_CREATION_SUCCES, ButtonType.OK);

        boiteInformationPdf.setTitle("Importation");
        boiteInformationPdf.setHeaderText(
                "Fichier PDF crée avec succés");
        boiteInformationPdf.showAndWait();
    }
    
    /**
     * Message d'alerte lorsque le pdf n'a pas était crée.
     * @param err l'erreur
     */
    public static void alertePdfEchec(IOException err) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("Erreur d'I/O");
        errorAlert.setHeaderText(ERREUR_I_O);
        errorAlert.setContentText("Détails de l'erreur : " + err.getMessage());
        errorAlert.showAndWait();
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    @FXML
    public void mettreAJourBoutons() {
        
        if (TraitementDonnees.getDonnees().isDonneesVides()) {
            btnConsulterDonnees.setDisable(true);
            btnExporter.setDisable(true);
            btnReglage.setDisable(true);
        }
    }
    
    /**
     * Permet d'obtenir le chemin du fichier pdf choisit par l'utilisateur
     * Format du fichier : 
     * Nom du fichier + "1911241530"  (19 novembre 2024, 15:30)  + .pdf
     * @return chemin du fichier valide
     */
    public static String chemin() {
        String chemin = null,
               nomfichier;
        DirectoryChooser directoryChooser;
        File selectedDirectory,
             fichier;
        
        directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory
            (new File(System.getProperty("user.home"), "Documents"));
        selectedDirectory = directoryChooser.showDialog(null);
        
        if (selectedDirectory != null) {            
            TextInputDialog dialog = new TextInputDialog("nom_fichier");
            dialog.setTitle("Saisie du nom de fichier");
            dialog.setHeaderText("Veuillez entrer le nom du fichier (sans extension)");
            dialog.setContentText("Nom du fichier:");
            
            Optional<String> result = dialog.showAndWait();
            
            if (result.isPresent()) {
                nomfichier = result.get() + ".pdf";

                fichier = new File(selectedDirectory, nomfichier);
                int i = 1;

                while (fichier.exists()) {
                    nomfichier = result.get() + " (" + i + ").pdf";
                    fichier = new File(selectedDirectory, nomfichier);
                    i++;
                }
                chemin = fichier.getAbsolutePath();
            }
        } 
        return chemin;
    }
    
    @FXML
    void btnReglageAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuReglageVue");
    }
}
