/*
 * IhmMusee.java                           
 * 14 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application;

import application.controleur.AccueilControleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class IhmMusee extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vue/accueilVue.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        AccueilControleur controleur = loader.getController();
        controleur.setFenetreAppli(stage);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
