/*
 * ExporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.awt.Desktop;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import application.utilitaire.Serveur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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

    private static String[] CHEMIN_FICHIER_CSV = {
                                             "conferencier 28_08_24 17_26.csv", 
                                             "employes 28_08_24 17_26.csv",
                                             "expositions 28_08_24 17_26.csv",
                                             "visites 28_08_24 17_26.csv"};
    
    /**
     * Définit la fenêtre de l'application.
     * @param fenetreAppli
     */
    public void setFenetreAppli(Stage fenetreAppli) {
      this.fenetreAppli = fenetreAppli;
    }
    
    
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
     * l'interface utilisateur. Cette méthode est utilisée pour 
     * configurer les éléments de l'interface,
     * initialiser les données, et définir les actions des boutons.
     */
    @FXML
    public void initialize() {
        String ip;
        DatagramSocket socket; //UDP
        
        try{
            socket = new DatagramSocket();
            //Connextion au serveur DNS public de google avec port valide
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
            labelIp.setText(ip);
          } catch (SocketException | UnknownHostException e) {
              Alert boiteIpInconnu 
                  = new Alert(Alert.AlertType.ERROR, 
                              "Impossible de connaître l'adresse"
                                      + " IP de l'interface Ethernet",
                              ButtonType.OK);

              boiteIpInconnu.setTitle("Erreur adresse IP inconnue");
              boiteIpInconnu.setHeaderText("Erreur adresse IP inconnue");

              boiteIpInconnu.showAndWait();
        }
    }

    @FXML
    void btnAideAction(ActionEvent event) {
    	final String LIEN_REGLES
        = "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?usp=sharing";

        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(LIEN_REGLES));
        } catch (IOException | URISyntaxException e) {
            Alert boiteErreurInconnueOuverture =
                    new Alert(Alert.AlertType.ERROR, 
                              "impossible d'ouvrir le fichier d'aide",
                              ButtonType.OK);

            boiteErreurInconnueOuverture.setTitle("Erreur d'affichage aide");
            boiteErreurInconnueOuverture.setHeaderText("Erreur d'affichage aide");

            boiteErreurInconnueOuverture.showAndWait();
        }
    }
    
    @FXML
    void btnExporterAction(ActionEvent event) {
        Serveur.envoyerFichiers(65432, CHEMIN_FICHIER_CSV);
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
