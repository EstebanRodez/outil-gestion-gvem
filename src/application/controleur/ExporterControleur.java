/*
 * ExporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.net.DatagramSocket;
import java.net.InetAddress;

import application.EchangeurDeVue;
import application.utilitaire.ExportationCSV;
import application.utilitaire.ExportationCSVException;
import application.utilitaire.ThreadExportation;
import application.utilitaire.Vigenere;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;


/**
 * Contrôleur pour la gestion de l'exportation des données.
 * 
 * Cette classe permet à l'utilisateur de se connecter à un serveur 
 * en fournissant une adresse IP et un port. Elle gère également 
 * la validation des entrées et l'affichage des erreurs éventuelles.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class ExporterControleur {

    @FXML
    private Button btnAide;

    @FXML
    private Button btnExporter;

    @FXML
    private Button btnRetour;

    @FXML
    private Label labelIp;

    @FXML
    private Label labelPort;
    
    /**
     * Méthode d'initialisation appelée après le chargement de 
     * l'interface utilisateur.
     */
    @FXML
    public void initialize() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            labelIp.setText(socket.getLocalAddress().getHostAddress());
        } catch (Exception e) {
            Alert boiteIpInconnu = new Alert(Alert.AlertType.ERROR, 
                    "Impossible de connaître l'adresse IP de l'interface "
                    + "Ethernet", 
                    ButtonType.OK);
            boiteIpInconnu.setTitle("Erreur adresse IP inconnue");
            boiteIpInconnu.setHeaderText("Erreur adresse IP inconnue");
            boiteIpInconnu.showAndWait(); 
        }
    } 

    @FXML
    void btnExporterAction(ActionEvent event) {
        
        EchangeurDeVue.creerPopUp("chargementPopUp");
        
        boolean erreurExportation = false;
        try {
            ExportationCSV.exporterDonnees();
        } catch (ExportationCSVException e) {
            erreurExportation = true;
            lancerErreurExportationCSV();
        }
        
        for (String nomFichier : Vigenere.getNomsFichiersDonnees()) {
            erreurExportation = !Vigenere.verifierFichier(nomFichier);
        }
        
        if (!erreurExportation) {
            
            EchangeurDeVue.creerPopUp("chargementPopUp");
            ThreadExportation threadExportation = new ThreadExportation();
            
            ChargementPopUpControleur controleur
            = EchangeurDeVue.getFXMLLoader("chargementPopUp").getController();
            controleur.setThreadExportation(threadExportation);
            threadExportation.start();
        }
        
        
    }

    @FXML
    void btnAideAction(ActionEvent event) {
    	// Appel de la méthode lancerAide de AccueilControleur avec un indice spécifique
    	// Utilise l'indice 7 pour ouvrir un lien d'aide correspondant
    	// à la réference de la partit de l'exportation
        AccueilControleur.lancerAide(7);
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    public static void lancerErreurExportationCSV() {
        
        Alert boiteErreurExportationCSV
        = new Alert(Alert.AlertType.ERROR, 
                    "Vos données n'ont pas pu être exportées dans un format "
                    + "csv.", ButtonType.OK);
        boiteErreurExportationCSV.setTitle("Erreur Exportation");
        boiteErreurExportationCSV.setHeaderText(
                "Erreur lors de l'exportation de vos données");
        boiteErreurExportationCSV.showAndWait();
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param message 
     */
    public static void lancerErreurGenerationDonneeSecrete(String message) {
        
        Alert boiteErreurGenerationDonneeSecrete
        = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        boiteErreurGenerationDonneeSecrete.setTitle("Erreur Exportation");
        boiteErreurGenerationDonneeSecrete.setHeaderText(
                "Erreur lors de la génération de la donnée secrète");
        boiteErreurGenerationDonneeSecrete.showAndWait();
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    public static void lancerErreurAlphabet() {
        
        Alert boiteErreurAlphabet
        = new Alert(Alert.AlertType.ERROR, 
                    "Un caractère contenu dans vos fichiers, n'est pas dans "
                    + "l'alphabet universel du cryptage. Cryptage impossible",
                    ButtonType.OK);
        boiteErreurAlphabet.setTitle("Erreur Exportation");
        boiteErreurAlphabet.setHeaderText(
                "Erreur lors du cryptage de vos données");
        boiteErreurAlphabet.showAndWait();
    } 
    
}
