/*
 * ImporterDistantControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import application.EchangeurDeVue;
import application.utilitaire.EchangeDiffieHellman;
import application.utilitaire.FichierDonneesInvalidesException;
import application.utilitaire.GenerationDonneeSecreteException;
import application.utilitaire.GestionFichiers;
import application.utilitaire.ImportationCSV;
import application.utilitaire.Reseau;
import application.utilitaire.SauvegardeDonnees;
import application.utilitaire.TraitementDonnees;
import application.utilitaire.Vigenere;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        
        final int PORT_EXPORTATION = Reseau.getPortExportation();
        
        String ipServeur = txtFieldIPServeur.getText().trim();
        
        if (Reseau.isAdresseIPValide(ipServeur)
            && Reseau.isAdresseIPDisponible(ipServeur)) {
            
            int cleSecrete = -1;
            try {
                cleSecrete
                = EchangeDiffieHellman.genererDonneeSecreteBob(ipServeur);
            } catch (GenerationDonneeSecreteException e) {
                e.printStackTrace();
            }
            
            String[] nomFichierEnvois = Vigenere.getNomsFichiersEnvois();
            String[] nomFichierDonnees = Vigenere.getNomsFichiersDonnees();
            
            Reseau.recevoirFichiers(ipServeur, PORT_EXPORTATION,
                                    nomFichierEnvois, null);
            
            for (int indiceNomFichier = 0;
                 indiceNomFichier < nomFichierEnvois.length;
                 indiceNomFichier++) {
                
                String cleChiffrement
                = Vigenere.genererCleChiffrement(cleSecrete);
                
                Vigenere.decrypter(nomFichierEnvois[indiceNomFichier],
                                   cleChiffrement);
                
                try {
                    ImportationCSV.importerDonnees(
                            nomFichierDonnees[indiceNomFichier]);
                    SauvegardeDonnees.sauvegarderDonnees(
                            TraitementDonnees.getDonnees());
                    EchangeurDeVue.changerVue("importerDistantValideVue");
                } catch (FichierDonneesInvalidesException e) {
                    // Ne rien faire
                }
                
            }
            
            EchangeDiffieHellman.supprimerFichiersAlice();
            EchangeDiffieHellman.supprimerFichiersBob();
            Vigenere.supprimerFichiersEnvois();
            Vigenere.supprimerFichiersDonnees();
        } else {
            
            Alert boiteErreurAdresseIPIndisponible
            = new Alert(Alert.AlertType.ERROR,
                        "L'adresse IP que vous avez saisie, n'est pas"
                        + "disponible", ButtonType.OK);
            boiteErreurAdresseIPIndisponible.setTitle("Erreur Adresse IP");
            boiteErreurAdresseIPIndisponible.setHeaderText(
                    "Erreur dans la saisie de votre adresse IP");
            boiteErreurAdresseIPIndisponible.showAndWait();
        }
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("importerVue");
    }
    
}
