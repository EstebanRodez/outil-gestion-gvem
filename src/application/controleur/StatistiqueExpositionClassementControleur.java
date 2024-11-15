/*
 * StatistiqueExpositionClassementControleur.java                           
 * 15 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.EchangeurDeVue;
import application.modele.Visite;
import application.modele.VisiteCalculResultat;
import application.utilitaire.TraitementDonnees;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class StatistiqueExpositionClassementControleur {
    
    private static LinkedHashMap<String, Visite> visites
    = TraitementDonnees.getVisites();
    
    @FXML
    private TableColumn<VisiteCalculResultat, String> Exposition;

    @FXML
    private Button btnFiltres;

    @FXML
    private Button btnRetour;

    @FXML
    private TableColumn<VisiteCalculResultat, Double> nbTotal;

    @FXML
    private TableView<VisiteCalculResultat> tableExposition;
    
    /**
     */
    @FXML
    public void initialize() {
                
        Exposition.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getIntituleExposition()));
            
        nbTotal.setCellValueFactory(
            cellData -> new SimpleDoubleProperty(
                    cellData.getValue().getCalculVisites()).asObject());
        
        calculerTotalVisitesParExposition(visites);
    }

    @FXML
    void aideAction(ActionEvent event) {

    }

    @FXML
    void btnFiltresAction(ActionEvent event) {
        //TODO filtres date heure type expo
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuStatistiqueClassementVue");
    }

    @FXML
    void quitterAction(ActionEvent event) {
        EchangeurDeVue.getFenetreAppli().hide();
    }

    @FXML
    void retourAccueilAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }
    
    private void calculerTotalVisitesParExposition(
            LinkedHashMap<String, Visite> visites) {
        
        // Création d'une Map pour compter les visites par exposition
        Map<String, Integer> visitesParExposition = new HashMap<>();
        
        // Compter les visites pour chaque exposition
        for (Map.Entry<String, Visite> paire : visites.entrySet()) {
            
            String intituleExposition 
            = paire.getValue().getExposition().getIntitule();
            visitesParExposition.put(
                intituleExposition,
                visitesParExposition.getOrDefault(intituleExposition, 0) + 1
            );
        }
        
        System.out.println("visitesParExposition : " + visitesParExposition);
        
        // Calculer le total de visites pour chaque exposition
        List<VisiteCalculResultat> resultats = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : visitesParExposition
                                                .entrySet()) {
            String intituleExposition = entry.getKey();
            double totalVisites = entry.getValue();

            System.out.println("totalVisites : " + totalVisites);

            resultats.add(new VisiteCalculResultat(intituleExposition, 
                                                    totalVisites));
        }
        
        // Créer une ObservableList à partir des résultats
        ObservableList<VisiteCalculResultat> exposListe 
        = FXCollections.observableArrayList(resultats);
        
        // Trier la liste de manière décroissante en fonction de la colonne nbTotal
        SortedList<VisiteCalculResultat> sortedList 
        = new SortedList<>(exposListe,
                           (v1, v2) -> Double.compare(v2.getCalculVisites(), 
                                                      v1.getCalculVisites()));
        
        tableExposition.setItems(sortedList);
    }

}
