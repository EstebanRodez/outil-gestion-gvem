/*
 * IhmMusee.java                           
 * 14 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class IhmMusee extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vue/accueilVue.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        EchangeurDeVue.setFenetreAppli(stage);
        EchangeurDeVue.setSceneAppli(scene);
        
//        AccueilControleur controleur = loader.getController();
//        controleur.setFenetreAppli(stage);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * Fonction de lancement de l'application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Permet d'obtenir le FXML Loader d'une page FXML
     * @param lien le lien de la page FXML
     * @return le FXML Loader de la page
     */
    public static FXMLLoader getFXMLLoader(String lien) {
        return new FXMLLoader(IhmMusee.class.getResource(lien));
    }
    
}
