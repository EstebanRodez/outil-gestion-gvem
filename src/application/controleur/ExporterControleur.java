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
import java.net.URI;
import java.net.URISyntaxException;

import application.EchangeurDeVue;
import application.utilitaire.EchangeDiffieHellman;
import application.utilitaire.ExportationCSV;
import application.utilitaire.ExportationCSVException;
import application.utilitaire.GenerationDonneeSecreteException;
import application.utilitaire.GestionFichiers;
import application.utilitaire.Serveur;
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
        
        EchangeurDeVue.creerPopUp("chargementPopUp");
        
        try {
            ExportationCSV.exporterDonnees();
        } catch (ExportationCSVException e) {
            e.printStackTrace();
        }
        
        Thread attente;
        attente = new Thread(() -> {
            
            int cleSecrete = -1;
            try {
                cleSecrete = EchangeDiffieHellman.genererDonneeSecreteAlice();
                System.out.println(cleSecrete);
            } catch (GenerationDonneeSecreteException e) {
                e.printStackTrace();
            }
            
            String[] nomFichiersDonnees = Vigenere.getNomsFichiersDonnees();
            String[] nomFichiersAlphabet = Vigenere.getNomsFichiersAlphabet();
            for (int indiceNomFichier = 0;
                 indiceNomFichier < nomFichiersDonnees.length;
                 indiceNomFichier++) {
                
                String alphabet
                = Vigenere.recupererAlphabet(nomFichiersDonnees[indiceNomFichier]);
                try {
                    GestionFichiers.ecrireFichier(
                        nomFichiersAlphabet[indiceNomFichier], alphabet);
                } catch (IOException e) {
                    // Ne rien faire
                }
                String cleChiffrement
                = Vigenere.genererCleChiffrement(cleSecrete, alphabet);
                
                System.out.println(cleChiffrement);
                
                Vigenere.crypter(nomFichiersDonnees[indiceNomFichier],
                                 cleChiffrement, alphabet);
            }
            
            Serveur.envoyerFichiers(65432, Vigenere.getNomsFichiersEnvois());
            Serveur.envoyerFichiers(65432, nomFichiersAlphabet);
            
            // FIXME erreur thread
//            if (!Thread.currentThread().isInterrupted()) {
//                EchangeurDeVue.fermerPopUp("chargementPopUp");
//                EchangeurDeVue.changerVue("exporterValideVue");
//            }
        });
        
        ChargementPopUpControleur controleur
        = EchangeurDeVue.getFXMLLoader("chargementPopUp").getController();
        controleur.setThreadAttente(attente);
        attente.start();
    }

    @FXML
    void btnAideAction(ActionEvent event) {
    	// Appel de la méthode lancerAide de AccueilControleur avec un indice spécifique
    	// Utilise l'indice 8 pour ouvrir un lien d'aide correspondant
    	// à la réference de la partit de l'exportation
        AccueilControleur.lancerAide(8);
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }
    
}
