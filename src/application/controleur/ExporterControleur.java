/*
 * ExporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.net.DatagramSocket;
import java.net.InetAddress;

import application.EchangeurDeVue;
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
     * l'interface utilisateur. Cette méthode est utilisée pour 
     * configurer les éléments de l'interface,
     * initialiser les données, et définir les actions des boutons.
     */
    @FXML
    public void initialize() {
        
        try(DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            labelIp.setText(socket.getLocalAddress().getHostAddress());
          
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

    @FXML
    void btnAideAction(ActionEvent event) {
        AccueilControleur.lancerAide();
    }
    
    @FXML
    void btnExporterAction(ActionEvent event) {
        EchangeurDeVue.creerPopUp("chargementPopUp");
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }

}
