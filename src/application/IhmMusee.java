/*
 * IhmMusee.java                           
 * 14 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application;

import application.utilitaire.SauvegardeDonnees;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Contrôleur principal/central
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
        
        boolean sessionRestauree = SauvegardeDonnees.restaurerDonnees();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vue/accueilVue.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        EchangeurDeVue.setFenetreAppli(stage);
        EchangeurDeVue.setSceneAppli(scene);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        if (sessionRestauree) {
            Alert boiteInformationSession
            = new Alert(Alert.AlertType.INFORMATION,
                        "Vos données de l'ancienne session ont été chargées "
                        + "avec succès", ButtonType.OK);

            boiteInformationSession.setTitle("Session restaurée");
            boiteInformationSession.setHeaderText(
                    "Votre ancienne session a été restaurée.");
            boiteInformationSession.showAndWait();
        }
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
