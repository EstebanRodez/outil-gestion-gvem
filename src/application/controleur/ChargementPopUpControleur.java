/*
 * ChargementPopUpControleur.java                           
 * 23 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

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
    
    private static Thread attente;
      
    @FXML
    private Button btnQuitter;
    
    @FXML
    private ImageView spinner;
    
    @FXML
    void initialize() {

        // Faire tourner l'image
        startImageRotation();
    }
    
    @FXML
    void btnQuitterAction(ActionEvent event) {
        
        // Demander l'arrêt du serveur
        Serveur.fermerServeur();
        
        if (attente != null && attente.isAlive()) {
            
            // Interrompt le thread pour sortir de l'attente de connexion
            System.out.println("test2");
            attente.interrupt();
            try {
                
                // Attendre que le thread termine jusqu'à 500ms
                attente.join(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Fermer la fenêtre pop-up
        EchangeurDeVue.fermerPopUp("chargementPopUp");
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param attente
     */
    public void setThreadAttente(Thread attente) {
        ChargementPopUpControleur.attente = attente;
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

}