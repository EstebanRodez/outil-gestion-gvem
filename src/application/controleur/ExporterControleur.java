/*
 * ExporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.net.DatagramSocket;
import java.net.InetAddress;
import application.EchangeurDeVue;

import application.utilitaire.Cryptage;
import application.utilitaire.Serveur;

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
                    "Impossible de connaître l'adresse IP de l'interface Ethernet", 
                    ButtonType.OK);
            boiteIpInconnu.setTitle("Erreur adresse IP inconnue");
            boiteIpInconnu.setHeaderText("Erreur adresse IP inconnue");
            boiteIpInconnu.showAndWait(); 
        }
    } 

    @FXML
    void btnExporterAction(ActionEvent event) {
        
        // Clé de chiffrement
        // TODO Améliorer la pop up
        // String cle = JOptionPane.showInputDialog("Entrez la clé de cryptage Vigenère :");
        
        EchangeurDeVue.creerPopUp("chargementPopUp");
        
        // TODO Envoyer la clé à distance
        // Clé de chiffrement : 12
        String nomFichierCrypte;
        nomFichierCrypte = Cryptage.creerFichierDonnees("12");
        String[] fichiersCryptes = {nomFichierCrypte};
        
        // Initialiser le thread
        Thread attente;
        attente = new Thread(() -> {
            try {
                Serveur.envoyerFichiers(65431, fichiersCryptes);
                
                /* 
                 * Tout s'est déroulé si il n'a pas été interrompu
                 * donc on affiche la vue de confirmation de
                 * l'exportation 
                 */
                if (!Thread.currentThread().isInterrupted()) {
                    EchangeurDeVue.fermerPopUp("chargementPopUp");
                    EchangeurDeVue.changerVue("exporterValideVue");
                }
            } finally {
                
            }
        });
        
        ChargementPopUpControleur controleur
        = EchangeurDeVue.getFXMLLoader("chargementPopUp").getController();
        controleur.setThreadAttente(attente);
        attente.start();
        
//        try {
//            // Files.delete(Path.of(nomFichierCrypte));
//        } catch (IOException e) {
//            
//        }
    }

    @FXML
    void btnAideAction(ActionEvent event) {
        AccueilControleur.lancerAide();
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }
}
