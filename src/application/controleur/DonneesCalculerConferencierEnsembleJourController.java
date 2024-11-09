/*
 * donneesCalculeesConferencierEnsembleJourControleur.java                           
 * 5 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
public class DonneesCalculerConferencierEnsembleJourController {
    
    private static LinkedHashMap<String, Visite> visites
    = TraitementDonnees.getDonnees().getVisites();
    
    private static String[] choix = {"conférencers qui n’ont aucune visite",
                                    "conférencers et leur nombre moyen de " 
                                    + "visites programmées chaque jour",
                                    "conférencers et leur nombre moyen de "
                                    + "visites programmées chaque semaine",
                                    "l’esembles des conférencers et leur nombre moyen de "
                                    + "visites prévues chaque jour",
                                    "l’esembles des conférencers et leur nombre moyen de "
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
    private TableView<VisiteMoyenneResultat> tableConferencier;
    
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
        
        calculerMoyenneVisitesEnsembleConferencier(visites);
    }
    
    @FXML
    void btnFiltresAction(ActionEvent event) {
        EchangeurDeVue.creerPopUp("donneesCalculeesConfrencierEnsembleJourFiltrePopUp");
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
            System.out.println("choix 3");
        }
        
        if (listePhrase.getValue().equals(choix[3])) {
            EchangeurDeVue.changerVue("donneesCalculeesConferencierEnsembleJourVue");
        }
        
        if (listePhrase.getValue().equals(choix[4])) {
            System.out.println("choix 5");
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
     * Calcule la moyenne de visites programmées par jour pour chaque conférencier
     * à partir d'une liste de visites spécifiée.
     * 
     * @param visites la liste des visites à utiliser pour le calcul
     */
    private void calculerMoyenneVisitesEnsembleConferencier(
            LinkedHashMap<String, Visite> visites) {
        // Initialiser les dates globales de début et de fin
        LocalDate dateDebutGlobal = LocalDate.MAX;
        LocalDate dateFinGlobal = LocalDate.MIN;

        // Trouver les dates globales min et max
        for (Map.Entry<String, Visite> paire : visites.entrySet()) {
            LocalDate dateVisite = paire.getValue().getDate();

            if (dateVisite.isBefore(dateDebutGlobal)) {
                dateDebutGlobal = dateVisite;
            }

            if (dateVisite.isAfter(dateFinGlobal)) {
                dateFinGlobal = dateVisite;
            }
        }

        // Calculer le nombre total de jours global entre dateDebutGlobal et dateFinGlobal
        long totalJours = ChronoUnit.DAYS.between(dateDebutGlobal, dateFinGlobal) + 1;
        System.out.println("Nombre total de jours global : " + totalJours);

        // Calculer le nombre total de visites
        int totalVisitesGlobal = visites.size();

        // Calculer la moyenne globale
        double moyenneGlobale = totalJours > 0 ? (double) totalVisitesGlobal / totalJours : 0;
        
        System.out.println("totalVisitesGlobal : " + totalVisitesGlobal);
        System.out.println("totalJours : " + totalJours + "\n");
        
        // Créer la liste de résultats avec une seule entrée pour la moyenne globale
        List<VisiteMoyenneResultat> resultats = new ArrayList<>();
        resultats.add(new VisiteMoyenneResultat("Toutes les conferencier", moyenneGlobale));

        // Mettre à jour le tableau avec le résultat
        ObservableList<VisiteMoyenneResultat> confsListe = FXCollections.observableArrayList(resultats);
        tableConferencier.setItems(confsListe);
        
    }
    
    /**
     * Applique les critères de filtrage inversés sur la liste des visites.
     * Parcourt la liste des visites et affiche celles qui ne correspondent
     * pas aux critères spécifiés dans l'objet CritereFiltre.
     * Les visites qui ne respectent pas au moins un des critères 
     * (type de conferenicer, conférencier, conférencier, dates et horaires)
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

        // Maintenant, calculez la moyenne global en utilisant la liste filtrée
        calculerMoyenneVisitesEnsembleConferencier(visitesFiltrees);
    }

}
