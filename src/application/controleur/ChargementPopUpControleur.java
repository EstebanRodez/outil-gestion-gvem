/*
 * ChargementPopUpControleur.java                           
 * 23 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import application.EchangeurDeVue;
import application.utilitaire.Reseau;
import application.utilitaire.ThreadExportation;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration; 

/**
 * Cette classe contrôle les interactions et comportements de la 
 * fenêtre pop-up 
 * de chargement. Elle gère l'animation de la fenêtre, les actions utilisateur 
 * comme la fermeture de la fenêtre, et les interactions avec d'autres composants 
 * liés au traitement en cours (par exemple, le thread d'exportation ou le serveur réseau).
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class ChargementPopUpControleur {
    
    private static ThreadExportation threadExportation;
      
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
        Reseau.fermerServeur();
        
        if (threadExportation != null && threadExportation.isAlive()) {
            
            threadExportation.arreterExportation();
        }

        // Fermer la fenêtre pop-up
        EchangeurDeVue.fermerPopUp("chargementPopUp");
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param threadExportation
     */
    public void setThreadExportation(ThreadExportation threadExportation) {
        ChargementPopUpControleur.threadExportation = threadExportation;
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