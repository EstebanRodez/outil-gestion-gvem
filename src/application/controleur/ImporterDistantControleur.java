package application.controleur;

import java.io.IOException;

import application.EchangeurDeVue;
import application.utilitaire.Client;
import application.utilitaire.Decryptage;
import application.utilitaire.DecryptageException;
import application.utilitaire.EchangeDiffieHellman;
import application.utilitaire.GenerationDonneeSecreteException;
import application.utilitaire.GestionFichiers;
import application.utilitaire.Mathematiques;
import application.utilitaire.Serveur;
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
    
    private static final String[] NOMS_FICHIERS_DONNEES_CRYPTEES
    = {"Donnees_cryptees"};
    
    private static final String[] NOMS_FICHIERS_CLES_RECUS
    = {"p.txt", "g.txt", "g^a.txt"};
    
    private static final String[] NOMS_FICHIERS_CLES_ENVOIS
    = {"g^b.txt"};

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
        
        int cleSecrete;
        try {
            cleSecrete
            = EchangeDiffieHellman.genererDonneeSecreteBob(ipServeur);
            System.out.println(cleSecrete);
        } catch (GenerationDonneeSecreteException e) {
            e.printStackTrace();
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
}
