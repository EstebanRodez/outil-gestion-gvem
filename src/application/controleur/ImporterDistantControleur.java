/*
 * ImporterDistantControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import application.EchangeurDeVue;
import application.utilitaire.EchangeDiffieHellman;
import application.utilitaire.GenerationDonneeSecreteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
        AccueilControleur.lancerAide();
    }

    @FXML
    void btnConnexionAction(ActionEvent event) {
        
        String ipServeur = txtFieldIPServeur.getText().trim();
        
        if (isAdresseIPValide(ipServeur)) {
            int cleSecrete;
            try {
                cleSecrete
                = EchangeDiffieHellman.genererDonneeSecreteBob(ipServeur);
                System.out.println(cleSecrete);
            } catch (GenerationDonneeSecreteException e) {
                e.printStackTrace();
            }
        }
        
//        Client.recevoirFichiers(ipServeur, 65433, NOMS_FICHIERS_CLES_RECUS,
//                                null);
//        
//        int p, g, gExpA;
//        try {
//            p = Integer.parseInt(
//                    GestionFichiers.lireFichier(NOMS_FICHIERS_CLES_RECUS[0]));
//            g = Integer.parseInt(
//                    GestionFichiers.lireFichier(NOMS_FICHIERS_CLES_RECUS[1]));
//            gExpA = Integer.parseInt(
//                    GestionFichiers.lireFichier(NOMS_FICHIERS_CLES_RECUS[2]));
//        } catch (NumberFormatException | IOException e) {
//            p = 0;
//            g = 0;
//            gExpA = 0;
//            e.printStackTrace();
//        }
//        
//        int b = Mathematiques.genererNombreAleatoire(100,999);
//        int gExpB = Mathematiques.calculExponentielleModulo(g, b, p);
//        
//        try {
//            GestionFichiers.ecrireFichier(
//                NOMS_FICHIERS_CLES_ENVOIS[0], Integer.toString(gExpB)
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//            Thread.currentThread().interrupt();
//        }  
//        Serveur.envoyerFichiers(65433, NOMS_FICHIERS_CLES_ENVOIS);
//        
//        int cleSecret = Mathematiques.calculExponentielleModulo(gExpA, b, p);
//        
//        Client.recevoirFichiers(ipServeur, 65433,
//                                NOMS_FICHIERS_DONNEES_CRYPTEES, null);
//
//        try {
//            Decryptage.decrypterFichierDonnees(Integer.toString(cleSecret));
//            System.out.println("Données reçues avec succès");
//        } catch (DecryptageException erreur) {
//            System.out.println("Échec du décryptage des données");
//        }

//        try {
//            Files.delete(Path.of(NOMS_FICHIERS_DONNEES_CRYPTEES));
//        } catch (IOException erreur) {
//
//        }
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
