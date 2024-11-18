/*
 * StatistiqueExpositionClassementControleur.java                           
 * 15 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.EchangeurDeVue;
import application.modele.CritereFiltreVisite;
import application.modele.ExpositionTemporaire;
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
    = TraitementDonnees.getDonnees().getVisites();
    
    @FXML
    private TableColumn<VisiteCalculResultat, String> Exposition;

    @FXML
    private Button btnFiltres;

    @FXML
    private Button btnRetour;
    
    @FXML
    private Button aideAction;

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
                        cellData.getValue().getIntitule()));
            
        nbTotal.setCellValueFactory(
            cellData -> new SimpleDoubleProperty(
                    cellData.getValue().getCalculVisites()).asObject());
        
        calculerTotalVisitesParExposition(visites);
    }

    @FXML
    void aideAction(ActionEvent event) {
    	// Appel de la méthode lancerAide de AccueilControleur avec un indice spécifique
    	// Utilise l'indice 10 pour ouvrir un lien d'aide correspondant
    	// à la réference de la partit des statistique des classement des expositions
        AccueilControleur.lancerAide(10);
    }

    @FXML
    void btnFiltresAction(ActionEvent event) {
        EchangeurDeVue.creerPopUp("statistiqueExpositionClassementFiltrePopUp");
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
                
        // Calculer le total de visites pour chaque exposition
        List<VisiteCalculResultat> resultats = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : visitesParExposition
                                                .entrySet()) {
            String intituleExposition = entry.getKey();
            double totalVisites = entry.getValue();

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

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param critere
     */
    public void appliquerFiltre(CritereFiltreVisite critere) {

        LinkedHashMap<String, Visite> visitesFiltrees = new LinkedHashMap<>();

        for (Map.Entry<String, Visite> paire : visites.entrySet()) {

            boolean match = true; 
            
            // Filtrer par date de visite
            if (critere.getDateDebut() != null) {
             
                LocalDate dateFin
                = critere.getDateFin() != null ? critere.getDateFin()
                                               : critere.getDateDebut();
                if (paire.getValue().getDate().isBefore(critere.getDateDebut())
                    || paire.getValue().getDate().isAfter(dateFin)) {
                    match = false;
                }
            }
            
            // Filtrer par plage horaire
            if (critere.getHoraireDebut() != 0) {
                int horaireFin;
                horaireFin = critere.getHoraireFin() != 0 
                                                    ? critere.getHoraireFin() 
                                                    : critere.getHoraireDebut();
                if (paire.getValue().getHoraireDebut() < critere.getHoraireDebut() 
                    || paire.getValue().getHoraireDebut() > horaireFin) {
                    match = false;
                }
            }
            
            // Filtrer par exposition temporaire
            if (critere.getExpositionTemporaire()
                && !(paire.getValue().getExposition() instanceof ExpositionTemporaire)) {
                
                match = false;
            }
            
            // Filtrer par exposition permanente
            if (critere.getExpositionPermanente()
                && paire.getValue().getExposition() instanceof ExpositionTemporaire) {
                
                match = false;
            }
            
            if (match) {
                visitesFiltrees.putLast(paire.getKey(), paire.getValue());
            }
        }

        calculerTotalVisitesParExposition(visitesFiltrees);
            
        }
}



