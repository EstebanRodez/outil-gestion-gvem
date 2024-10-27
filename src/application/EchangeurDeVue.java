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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe utilitaire permettant la gestion de vues pour une
 * application JavaFX. Elle centralise le changement de vue dans
 * l'application, permettant ainsi d'éviter le rechargement de vues
 * déjà chargées et de simplifier la navigation entre les différentes
 * interfaces graphiques.
 * <p>
 * Les vues sont mises en cache pour éviter les rechargements et
 * améliorer les performances. Cette classe offre également un
 * mécanisme de gestion des erreurs lors du chargement des vues.
 * </p>
 * 
 * @author Esteban Vroemen
 * @version 1.0
 */
public class EchangeurDeVue {

    /** Cache permettant d'éviter de charger deux fois la même vue */
    private static HashMap<String, Parent> cacheVue = new HashMap<>();
    
    /** 
     * Cache permettant de stocker un contrôleur FXML Loader associé 
     * à chaque vue. Utile pour récupérer un contrôleur si besoin
     */
    private static HashMap<String, FXMLLoader> cacheFXMLLoader
    = new HashMap<>();
    
    private static HashMap<String, Stage> cachePopUp = new HashMap<>();
    
    /** Fenêtre principale de l'application */
    private static Stage fenetreAppli;
    
    /** Scène principale de l'application */
    private static Scene sceneAppli;
    
    private static final String ERREUR_NOM_VUE_INVALIDE =
    """
    Impossible de changer de vue.
    La référence de la vue ne doit pas être nulle.
    """;
    
    private static final String ERREUR_NOM_VUE_VIDE =
    """
    Impossible de changer de vue.
    Le nom de la vue ne doit pas être vide.
    """;
    
    /**
     * Définit la fenêtre de l'application.
     *
     * @param fenetreAppli la fenêtre principale de l'application
     */
    public static void setFenetreAppli(Stage fenetreAppli) {
        EchangeurDeVue.fenetreAppli = fenetreAppli;
    }
    
    /**
     * Retourne la fenêtre principale de l'application.
     *
     * @return la fenêtre de l'application
     */
    public static Stage getFenetreAppli() {
        return fenetreAppli;
    }
    
    /**
     * Définit la scène principale de l'application.
     *
     * @param sceneAppli la scène principale de l'application
     */
    public static void setSceneAppli(Scene sceneAppli) {
        EchangeurDeVue.sceneAppli = sceneAppli;
    }
    
    /**
     * Retourne la scène principale de l'application.
     * 
     * @return la scène de l'application
     */
    public static Scene getSceneAppli() {
        return sceneAppli;
    }
    
    /**
     * Retourne le FXML Loader associé à une vue.
     * 
     * @param nomVue le nom de la vue
     * @return le FXML Loader de la vue, ou null si la vue n'a pas
     *         été chargée
     */
    public static FXMLLoader getFXMLLoader(String nomVue) {
        return cacheFXMLLoader.get(nomVue);
    }
    
    /**
     * Retourne le Parent associé à une vue.
     * 
     * @param nomVue le nom de la vue
     * @return le Parent de la vue, ou null si la vue n'a pas été
     *         chargée
     */
    public static Parent getParent(String nomVue) {
        return cacheVue.get(nomVue);
    }
    
    /**
     * Change la vue affichée dans l'application en chargeant la vue
     * spécifiée. Si la vue est déjà dans le cache, elle est
     * réutilisée ; sinon, elle est chargée et ajoutée au cache.
     * <p>
     * Affiche une boîte d'erreur si la vue ne peut pas être chargée.
     * </p>
     * 
     * @param nomVue le nom de la vue avec ou sans l'extension .fxml
     * @throws IllegalArgumentException si la référence de la vue est
     *                                  nulle
     * @throws IllegalArgumentException si la nom de la vue est vide
     */
    public static void changerVue(String nomVue) {
        
        if (nomVue == null) {
            throw new IllegalArgumentException(ERREUR_NOM_VUE_INVALIDE);
        }
        
        if (nomVue.isBlank()) {
            throw new IllegalArgumentException(ERREUR_NOM_VUE_VIDE);
        }
        
        String lienVue = "/application/vue/" + nomVue;
        if (!nomVue.matches(".*\\.fxml$")) {
            lienVue += ".fxml";
        }
        
        Parent parentVue = null;
        if (cacheVue.containsKey(nomVue)) {
            
            parentVue = cacheVue.get(nomVue);
        } else {
            
            try {
                FXMLLoader loader = IhmMusee.getFXMLLoader(lienVue);
                cacheFXMLLoader.put(nomVue, loader);
                parentVue = loader.load();
                cacheVue.put(nomVue, parentVue);
            } catch (IOException e) {
                lancerErreurChargementVue(nomVue);
            }
        }
        
        if (parentVue != null) {
            sceneAppli.setRoot(parentVue);
        }
    }
    
    /**
     * Crée et affiche une fenêtre pop-up (Stage) pour une vue
     * spécifique.<br>
     * Si la vue a déjà été chargée auparavant, elle est récupérée
     * depuis le cache. Sinon, elle est chargée, mise en cache, puis
     * affichée en tant que pop-up.<br>
     * Les interactions avec la fenêtre principale sont bloquées tant
     * que le pop-up est affiché.
     *
     * @param nomVuePopUp le nom de la vue avec ou sans extension
     *                    .fxml pour laquelle le pop-up doit être
     *                    créé
     */
    public static void creerPopUp(String nomVuePopUp) {
        
        if (nomVuePopUp == null) {
            throw new IllegalArgumentException(ERREUR_NOM_VUE_INVALIDE);
        }
        
        if (nomVuePopUp.isBlank()) {
            throw new IllegalArgumentException(ERREUR_NOM_VUE_VIDE);
        }
        
        String lienPopUp = "/application/vue/" + nomVuePopUp;
        if (!nomVuePopUp.matches(".*\\.fxml$")) {
            lienPopUp += ".fxml";
        }
        
        Stage stagePopUp = null;
        if (cachePopUp.containsKey(nomVuePopUp)) {
            
            stagePopUp = cachePopUp.get(nomVuePopUp);
        } else {
            
            Parent parentVue = null;
            if (cacheVue.containsKey(nomVuePopUp)) {
                
                parentVue = cacheVue.get(nomVuePopUp);
            } else {
                
                try {
                    FXMLLoader loader = IhmMusee.getFXMLLoader(lienPopUp);
                    cacheFXMLLoader.put(nomVuePopUp, loader);
                    parentVue = loader.load();
                    cacheVue.put(nomVuePopUp, parentVue);
                } catch (IOException e) {
                    lancerErreurChargementVue(nomVuePopUp);
                }
            }
            
            stagePopUp = new Stage();
            
            // Bloque l'accès à la fenêtre principale
            stagePopUp.initModality(Modality.APPLICATION_MODAL);
            stagePopUp.setScene(new Scene(parentVue));
            cachePopUp.put(nomVuePopUp, stagePopUp);
        }
        
        if (stagePopUp != null) {
            stagePopUp.show();
        }
        
    }
    
    /**
     * Ferme une fenêtre pop-up précédemment ouverte et mise en
     * cache.
     * Si le pop-up spécifié est introuvable dans le cache, cette
     * méthode n'a aucun effet.
     *
     * @param nomVuePopUp le nom de la vue associée au pop-up à
     *                    fermer
     */
    public static void fermerPopUp(String nomVuePopUp) {
        
        if (cachePopUp.get(nomVuePopUp) != null) {
            cachePopUp.get(nomVuePopUp).close();
        }
    }
    
    /**
     * Affiche une boîte de dialogue d'erreur si le chargement d'une
     * vue échoue.
     *
     * @param nomVue le nom de la vue dont le chargement a échoué
     */
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
