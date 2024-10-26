/*
 * ChargementPopUpControleur.java                           
 * 23 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.IOException;

import application.EchangeurDeVue;
import application.utilitaire.Serveur;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class ChargementPopUpControleur {
    
    private Stage boitePopUp;
    
    private Thread attente;
    private volatile boolean running = true; // Drapeau pour contrôler l'exécution du thread
    
    private static String[] CHEMIN_FICHIER_CSV = {
            "conferencier 28_08_24 17_26.csv", 
            "employes 28_08_24 17_26.csv",
            "expositions 28_08_24 17_26.csv",
            "visites 28_08_24 17_26.csv"};
    
    
    /**
     * Définit la fenêtre de l'application.
     * @param boitePopUp 
     */
    public void setPopUp(Stage boitePopUp) {
      this.boitePopUp = boitePopUp;
    }
    
    @FXML
    private Button btnQuitter;
    
    /**
     * 
     */
    @FXML
    public void initialize() {
        // Initialiser le thread
        attente = new Thread(() -> {
            try {
                Serveur.envoyerFichiers(65432, CHEMIN_FICHIER_CSV);

                Platform.runLater(() -> {
                    this.boitePopUp.close();
                    EchangeurDeVue.changerVue("accueilVue");
                });
            } finally {
                running = false;
            }
        });

        attente.start();
    }

    @FXML
    void btnQuitterAction(ActionEvent event) {
        running = false;
        
        // Demander l'arrêt du serveur
        Serveur.fermerServeur();
        
        if (attente != null && attente.isAlive()) {
            attente.interrupt(); // Interrompt le thread pour sortir de l'attente de connexion
            try {
                attente.join(500); // Attendre que le thread termine jusqu'à 500ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Fermer la fenêtre pop-up
        this.boitePopUp.close();
    }
}