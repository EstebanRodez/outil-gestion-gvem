/*
 * ChargementPopUpControleur.java                           
 * 23 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.File;
import java.util.ArrayList;

import application.EchangeurDeVue;
import application.utilitaire.Serveur;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration; 

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
    
    private Thread attente;
    private volatile boolean running = true; // Drapeau pour contrôler l'exécution du thread
    
    private static String[] CHEMIN_FICHIER_CSV;
    
    @FXML
    private Button btnQuitter;
    
    @FXML
    private ImageView spinner;
    
    @FXML
    void initialize() {
        // Récupérer les fichiers CSV dans le dossier
        File dossier = new File("fichiersImportees");
        
        if (dossier.exists() && dossier.isDirectory()) {
            File[] liste = dossier.listFiles();
            ArrayList<String> cheminsFichiers = new ArrayList<>();
            
            if (liste != null) {
                for (File fichier : liste) {
                    cheminsFichiers.add(fichier.getAbsolutePath());
                }
            }
            
            // Convertir la liste en tableau
            CHEMIN_FICHIER_CSV = cheminsFichiers.toArray(new String[0]);
        }
        
        // Initialiser le thread
        attente = new Thread(() -> {
            try {
                Serveur.envoyerFichiers(65429, CHEMIN_FICHIER_CSV);
                
                /* 
                 * Tout s'est déroulé si il n'a pas été interrompu
                 * donc on affiche la vue de confirmation de
                 * l'exportation 
                 */
                if (!Thread.currentThread().isInterrupted()) {
                    EchangeurDeVue.changerVue("exporterValideVue");
                }
            } finally {
                running = false;
                EchangeurDeVue.fermerPopUp("chargementPopUp");
            }
        });

        attente.start();
        
     // Faire tourner l'image
        startImageRotation();
    }
    
    /**
     * Lance une animation de rotation infinie pour l'image affichée.
     * Fait tourner l'image de 360 degrés en 2 secondes.
     */
    private void startImageRotation() {
        RotateTransition rotateTransition;
        rotateTransition = new RotateTransition(Duration.seconds(2), spinner);
        rotateTransition.setByAngle(360); // Rotation de 360 degrés
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE); 
        rotateTransition.play(); // Lancer l'animation
    }

    @FXML
    void btnQuitterAction(ActionEvent event) {
        running = false;
        
        // Demander l'arrêt du serveur
        Serveur.fermerServeur();
        
        if (attente != null && attente.isAlive()) {
            
            // Interrompt le thread pour sortir de l'attente de connexion
            attente.interrupt();
            try {
                
                // Attendre que le thread termine jusqu'à 500ms
                attente.join(500); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Fermer la fenêtre pop-up
        EchangeurDeVue.fermerPopUp("chargementPopUp");
    }
}