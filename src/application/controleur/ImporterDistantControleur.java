/*
 * ImporterDistantControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.IOException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import application.EchangeurDeVue;
import application.utilitaire.Client;
import application.utilitaire.EchangeDiffieHellman;
import application.utilitaire.FichierDonneesInvalidesException;
import application.utilitaire.GenerationDonneeSecreteException;
import application.utilitaire.GestionFichiers;
import application.utilitaire.ImportationCSV;
import application.utilitaire.Vigenere;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * Contrôleur pour la gestion de l'importation de données à distance.
 * 
 * Cette classe permet à l'utilisateur de spécifier une adresse IP et
 * un port pour se connecter à un serveur distant. Elle inclut
 * également des fonctionnalités pour afficher des règles
 * d'utilisation via un lien.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class ImporterDistantControleur {

    @FXML
    private Button btnAide;

    @FXML
    private Button btnConnexion;

    @FXML
    private Button btnRetour;

    @FXML
    private TextField txtFieldIPServeur;

    @FXML
    private TextField txtFieldPort;

    @FXML
    void btnAideAction(ActionEvent event) {
    	// Appel de la méthode lancerAide de AccueilControleur avec un indice spécifique
    	// Utilise l'indice 9 pour ouvrir un lien d'aide correspondant
    	// à la réference de la partit des conferenciers dans données calculées
        AccueilControleur.lancerAide(9);;
    }

    @FXML
    void btnConnexionAction(ActionEvent event) {
        
        String ipServeur = txtFieldIPServeur.getText().trim();
        
        if (isAdresseIPValide(ipServeur)) {
            
            int cleSecrete = -1;
            try {
                cleSecrete
                = EchangeDiffieHellman.genererDonneeSecreteBob(ipServeur);
                System.out.println(cleSecrete);
            } catch (GenerationDonneeSecreteException e) {
                e.printStackTrace();
            }
            
            String[] nomFichierEnvois = Vigenere.getNomsFichiersEnvois();
            String[] nomFichierAlphabet = Vigenere.getNomsFichiersAlphabet();
            String[] nomFichierDonnees = Vigenere.getNomsFichiersDonnees();
            
            Client.recevoirFichiers(ipServeur, 65432, nomFichierEnvois,
                                    null);
            Client.recevoirFichiers(ipServeur, 65432, nomFichierAlphabet,
                                    null);
            
            for (int indiceNomFichier = 0;
                 indiceNomFichier < nomFichierEnvois.length;
                 indiceNomFichier++) {
                
                String alphabet = "";
                try {
                    alphabet = GestionFichiers.lireFichier(
                                   nomFichierAlphabet[indiceNomFichier]);
                } catch (IOException e) {
                    // Ne rien faire
                }
                
                String cleChiffrement
                = Vigenere.genererCleChiffrement(cleSecrete, alphabet);
                
                System.out.println(cleChiffrement);
                
                Vigenere.decrypter(nomFichierEnvois[indiceNomFichier],
                                   cleChiffrement, alphabet);
                
                try {
                    ImportationCSV.importerDonnees(
                            nomFichierDonnees[indiceNomFichier]);
                } catch (FichierDonneesInvalidesException e) {
                    // Ne rien faire
                }
            }
        }
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("importerVue");
    }
    
    /**
     * Vérifie si une adresse IP est valide
     * @param ip Adresse IP sous forme de chaîne
     * @return true si l'adresse est valide, sinon false
     */
    private static boolean isAdresseIPValide(String ip) {
        // Expression régulière pour vérifier une adresse IPv4
        String ipPattern = 
            "^([0-9]{1,3}\\.){3}[0-9]{1,3}$";
        
        if (!ip.matches(ipPattern)) {
            return false;
        }

        String[] segments = ip.split("\\.");
        for (String segment : segments) {
            int value = Integer.parseInt(segment);
            if (value < 0 || value > 255) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Vérifie si le port est valide
     * @param port Chaîne représentant le port
     * @return true si le port est un entier valide entre 0 et 65535,
     *         sinon false
     */
    private static boolean isPortValide(String port) {
        return port.matches("(\\d){1,5}") && Integer.parseInt(port) >= 0
               && Integer.parseInt(port) <= 65535;
    }
}
