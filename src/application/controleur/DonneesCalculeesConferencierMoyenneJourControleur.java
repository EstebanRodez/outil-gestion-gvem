/*
 * DonneesCalculeesConferencierEnsembleJourControleur.java                           
 * 5 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.EchangeurDeVue;
import application.modele.CritereFiltreVisite;
import application.modele.Visite;
import application.modele.VisiteMoyenneResultat;
import application.utilitaire.TraitementDonnees;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class DonneesCalculeesConferencierMoyenneJourControleur {
    
    private static LinkedHashMap<String, Visite> visites
    = TraitementDonnees.getVisites();
    
    private static String[] choix = {"conférencier qui n’ont aucune visite",
                                    "conférencier et leur nombre moyen de " 
                                    + "visites programmées chaque jour",
                                    "conférencier et leur nombre moyen de "
                                    + "visites programmées chaque semaine",
                                    "l’esembles des conférencier et leur nombre moyen de "
                                    + "visites prévues chaque jour",
                                    "l’esembles des conférencier et leur nombre moyen de "
                                    + "visites prévues chaque semaine"};
    
    @FXML
    private Button btnFiltres;

    @FXML
    private Button btnRetour;

    @FXML
    private Button btnValider;

    @FXML
    private TableColumn<VisiteMoyenneResultat, String> conferencier;
    
    @FXML
    private TableColumn<VisiteMoyenneResultat, Double> nbMoyen;
    
    @FXML
    private ChoiceBox<String> listePhrase;

    @FXML
    private TableView<VisiteMoyenneResultat> tableExposition;
    
    /**
     * 
     */
    @FXML
    public void initialize() {
        
        listePhrase.getItems().addAll(choix);
        
        // défini la valeur par défaut
        listePhrase.setValue(choix[2]);
        
        conferencier.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIntituleExposition()));
        
        nbMoyen.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMoyenneVisites()).asObject());
        
        calculerMoyenneVisitesParConferencier(visites);
    }
    
    @FXML
    void btnFiltresAction(ActionEvent event) {
        EchangeurDeVue.creerPopUp("donneesCalculeesConferencierMoyenneJourFiltrePopUp");
    }
    
    @FXML
    void btnValiderAction(ActionEvent event) {
        
        if (listePhrase.getValue().equals(choix[0])) {
            EchangeurDeVue.changerVue("donneesCalculeesConferencierVue");
        }
        
        if (listePhrase.getValue().equals(choix[1])) {
            EchangeurDeVue.changerVue("donneesCalculeesConferencierMoyenneJourVue");
        }
        
        if (listePhrase.getValue().equals(choix[2])) {
            System.out.println("choix 3 ");
        }
        
        if (listePhrase.getValue().equals(choix[3])) {
            System.out.println("choix 4 ");
        }
    }

    @FXML
    void retourAccueilAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }

    @FXML
    void quitterAction(ActionEvent event) {
        EchangeurDeVue.getFenetreAppli().hide();
    }

    @FXML
    void aideAction(ActionEvent event) {
        AccueilControleur.lancerAide();
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuDonneesCalculeesVue");
    }
    
    /**
     * Calcule la moyenne de visites programmées par jour pour chaque exposition
     * à partir d'une liste de visites spécifiée.
     * 
     * @param visites la liste des visites à utiliser pour le calcul
     */
    private void calculerMoyenneVisitesParConferencier(
            LinkedHashMap<String, Visite> visites) {
        
        // Initialiser les dates globales de début et de fin
        LocalDate dateDebutGlobal = LocalDate.MAX;
        LocalDate dateFinGlobal = LocalDate.MIN;

        // Parcourir toutes les visites pour trouver les dates globales min et max
        for (Map.Entry<String, Visite> paire : visites.entrySet()) {
            LocalDate dateVisite = paire.getValue().getDate();

            if (dateVisite.isBefore(dateDebutGlobal)) {
                dateDebutGlobal = dateVisite;
            }

            if (dateVisite.isAfter(dateFinGlobal)) {
                dateFinGlobal = dateVisite;
            }
        }

        System.out.println("dateDebutGlobal : " + dateDebutGlobal);
        System.out.println("dateFinGlobal : " + dateFinGlobal);
        
        // Calculer le nombre total de jours global entre dateDebutGlobal et dateFinGlobal
        long totalJours = ChronoUnit.DAYS.between(dateDebutGlobal, dateFinGlobal) + 1;
        System.out.println("Nombre total de jours global : " + totalJours);

        // Création d'une Map pour compter les visites par exposition
        Map<String, Integer> visitesParExposition = new HashMap<>();

        // Compter les visites pour chaque exposition
        for (Map.Entry<String, Visite> paire : visites.entrySet()) {
            
            String nomConferencier
            = paire.getValue().getConferencier().getNom();
            visitesParExposition.put(
                nomConferencier,
                visitesParExposition.getOrDefault(nomConferencier, 0) + 1
            );
        }

        // Calculer la moyenne de visites pour chaque exposition
        List<VisiteMoyenneResultat> resultats = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : visitesParExposition.entrySet()) {
            String intituleExposition = entry.getKey();
            int totalVisites = entry.getValue();

            // Calculer la moyenne des visites pour cette exposition
            double moyenneVisites = totalJours > 0 ? (double) totalVisites / totalJours : 0;
            
            System.out.println("totalVisites : " + totalVisites);
            System.out.println("totalJours : " + totalJours + "\n");

            // Ajouter le résultat à la liste
            resultats.add(new VisiteMoyenneResultat(intituleExposition, moyenneVisites));
        }

        // Mettre à jour le tableau avec les résultats
        ObservableList<VisiteMoyenneResultat> exposListe = FXCollections.observableArrayList(resultats);
        tableExposition.setItems(exposListe);
    }



    
    /**
     * Applique les critères de filtrage inversés sur la liste des visites.
     * Parcourt la liste des visites et affiche celles qui ne correspondent
     * pas aux critères spécifiés dans l'objet CritereFiltre.
     * Les visites qui ne respectent pas au moins un des critères 
     * (type d'exposition, conférencier, exposition, dates et horaires)
     * sont ajoutées à la liste des visites filtrées.
     *
     * @param critere objet contenant les critères de filtrage à 
     *                appliquer
     */
    public void appliquerFiltreMoyenneJour(CritereFiltreVisite critere) {
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
      
            if (match) {
                visitesFiltrees.putLast(paire.getKey(), paire.getValue());
            }
        }

        // Maintenant, calculez les moyennes en utilisant la liste filtrée
        calculerMoyenneVisitesParConferencier(visitesFiltrees);
    }


}

