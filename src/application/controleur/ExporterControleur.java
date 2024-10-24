/*
 * ExporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.awt.Desktop;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;

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
        InetAddress[] listadresse;
        
        try {
         // Récupérer toutes les adresses locales
            listadresse = InetAddress.getAllByName(InetAddress
                                                   .getLocalHost()
                                                       .getHostName());
            
            for (InetAddress adresse : listadresse) {
                // Vérifier si c'est une adresse IPv4 non loopback et de classe A
                if (adresse instanceof Inet4Address 
                        && !adresse.isLoopbackAddress() 
                        && isClassA(adresse.getHostAddress())) {
                    labelIp.setText(adresse.getHostAddress()); 
                }
            }
        } catch (Exception e) {  
            Alert boiteIpInconnu =
                    new Alert(Alert.AlertType.ERROR, 
                              "Impossible de connaître l'adresse "
                                  + "IP de l'interface Ethernet",
                                      ButtonType.OK);

            boiteIpInconnu.setTitle("Erreur adresse IP inconnue");
            boiteIpInconnu.setHeaderText("Erreur adresse IP inconnue");

            boiteIpInconnu.showAndWait();  
        }  
    } 

    /**
     * Vérifie si une adresse IPv4 donnée appartient à la classe A.
     * Les adresses de classe A sont celles dont le premier octet 
     * est compris entre 1 et 126 inclus. La méthode divise l'adresse IP 
     * en octets, extrait le premier octet,et détermine s'il appartient
     * à la plage de la classe A.
     * 
     * @param ipAddress L'adresse IPv4 à vérifier 
     *        sous forme de chaîne (format "xxx.xxx.xxx.xxx").
     * @return true si l'adresse IP appartient à la classe A, sinon false.
     * @throws NumberFormatException si l'adresse IP contient des valeurs 
     *         non numériques.
     * @throws ArrayIndexOutOfBoundsException si l'adresse IP 
     *         n'est pas correctement formatée (moins de 4 octets).
     */
    private static boolean isClassA(String ipAddress) {
        String[] octets;
        int firstOctet;
        try {
            // Diviser l'adresse IP en octets
            octets = ipAddress.split("\\.");
            
            // Extraire le premier octet et le convertir en entier
            firstOctet = Integer.parseInt(octets[0]);
            
            // Vérifier si le premier octet est compris entre 1 et 126
            return firstOctet >= 1 && firstOctet <= 126;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // En cas d'erreur (par exemple, adresse IP mal formée)
            return false;
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
