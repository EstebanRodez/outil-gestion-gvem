package application.controleur;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import application.EchangeurDeVue;
import application.utilitaire.Client;
import application.utilitaire.Decryptage;

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
    
    private static final String NOM_FICHIER_DONNEES_CRYPTEES
    = "Donnees_cryptees";

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
        String[] fichiersRecus = {NOM_FICHIER_DONNEES_CRYPTEES};
        Client.recevoirFichiers(ipServeur, 65432, fichiersRecus, null);
        
        // TODO Recevoir la clé à distance
        // Clé de chiffrement : 12
        if (Decryptage.decrypterFichierDonnees("12")) {
            System.out.println("Données reçues avec succès");
        } else {
            System.out.println("Echec");
        }
        
        try {
            Files.delete(Path.of(NOM_FICHIER_DONNEES_CRYPTEES));
        } catch (IOException e) {
            
        }
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("importerVue");
    }
}
