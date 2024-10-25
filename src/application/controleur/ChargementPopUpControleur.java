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
            while (running) { // Boucle tant que 'running' est vrai
                Serveur.envoyerFichiers(65422, CHEMIN_FICHIER_CSV);

                // Une fois le transfert terminé, fermer la fenêtre popup sur le thread JavaFX
                Platform.runLater(() -> {
                    EchangeurDeVue.changerVue("exporterValideVue");
                });

                // Sortir de la boucle après le transfert
                running = false; // Pour éviter une boucle infinie après le transfert
            }
        });

        // Démarrer le thread
        attente.start();
    }

    @FXML
    void btnQuitterAction(ActionEvent event) throws IOException, InterruptedException {
        
        //FIXME arreter le thread quand appuyer sur le bouton 'Quitter'
        
        // Indiquer au thread de s'arrêter
        running = false; // Signaler que le thread doit s'arrêter

        // Si vous avez besoin d'attendre que le thread se termine
        if (attente != null) {
            try {
                // Attendre que le thread se termine, mais ne pas bloquer indéfiniment
                if (attente.isAlive()) {
                    attente.join(500); // Attendre jusqu'à 0.5 seconde
                    if (attente.isAlive()) {
                        attente.interrupt();
                        System.out.println("Transfert annuler.");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Fermer la fenêtre pop-up
        this.boitePopUp.close();
    }
}
