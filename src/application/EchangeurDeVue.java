/**
 * EchangeurDeVue.java
 * 25 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * TODO commenter le fonctionnement
 * 
 * @author Esteban Vroemen
 * @version 1.0
 */
public class EchangeurDeVue {

    /** Cache permettant d'éviter de charger deux fois la même vue */
    private static HashMap<String, Parent> cacheVue = new HashMap<>();
    
    /** Cache permettant de charger un contrôleur */
    private static HashMap<String, FXMLLoader> cacheFXMLLoader
    = new HashMap<>();
    
    private static Stage fenetreAppli;
    
    private static Scene sceneAppli;
    
    /**
     * Définit la fenêtre de l'application.
     * @param fenetreAppli la fenêtre de l'application
     */
    public static void setFenetreAppli(Stage fenetreAppli) {
        EchangeurDeVue.fenetreAppli = fenetreAppli;
    }
    
    /**
     * Renvoie la fenêtre de l'application
     * @return la fenêtre de l'application
     */
    public static Stage getFenetreAppli() {
        return fenetreAppli;
    }
    
    /**
     * Définit la scène de l'application.
     * @param sceneAppli la scène de l'application
     */
    public static void setSceneAppli(Scene sceneAppli) {
        EchangeurDeVue.sceneAppli = sceneAppli;
    }
    
    /**
     * Renvoie la fenêtre de l'application
     * @return la fenêtre de l'application
     */
    public static Scene getSceneAppli() {
        return sceneAppli;
    }
    
    /**
     * Renvoie le FXML Loader d'une vue donnée en argument.
     * @param nomVue le nom de la vue
     * @return la FXML Loader de la vue
     */
    public static FXMLLoader getFXMLLoader(String nomVue) {
        return cacheFXMLLoader.get(nomVue);
    }
    
    /**
     * Sert à changer de vue dans tous les contrôleurs
     * @param nomVue le nom de la vue sans l'extension .fxml
     * @throws IllegalArgumentException
     * @throws IllegalArgumentException
     */
    public static void changerVue(String nomVue) {
        
        if (nomVue == null) {
            throw new IllegalArgumentException();
        }
        
        if (nomVue.isBlank()) {
            throw new IllegalArgumentException();
        }
        
        Parent parentVue = null;
        if (cacheVue.containsKey(nomVue)) {
            
            parentVue = cacheVue.get(nomVue);
        } else {
            
            try {
                FXMLLoader loader
                = IhmMusee.getFXMLLoader(
                        "/application/vue/" + nomVue + ".fxml");
                cacheFXMLLoader.put(nomVue, loader);
                parentVue = loader.load();
                cacheVue.put(nomVue, parentVue);
            } catch (IOException | IllegalStateException e) {
                lancerErreurChargementVue(nomVue);
            }
        }
        
        if (parentVue != null) {
            // sceneAppli = new Scene(parentVue);
            sceneAppli.setRoot(parentVue);
        }
    }
    
    private static void lancerErreurChargementVue(String nomVue) {
        
        Alert boiteErreurChargementVue
        = new Alert(Alert.AlertType.ERROR,
                    "Impossible de charger la vue nommé " + nomVue,
                    ButtonType.OK);
        boiteErreurChargementVue.setTitle("Erreur d'affichage");
        boiteErreurChargementVue.setHeaderText("Erreur de chargement de "
                                               + nomVue);
        boiteErreurChargementVue.showAndWait();
    }
    
}
