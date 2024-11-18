/*
 * donneesCalculeesConferencierEnsembleJourControleur.java                           
 * 5 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class DonneesCalculeesConferencierEnsembleJourControleur {
    
    private static LinkedHashMap<String, Visite> visites
    = TraitementDonnees.getDonnees().getVisites();
    
    // Format pour les dates au format jj/MM/aaaa
    private static final DateTimeFormatter DATE_FORMAT 
    = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private static String[] choix 
    = {
        "conférencier qui n’ont aucune visite",
        "conférencier et leur nombre moyen de visites programmées chaque jour",
        "conférencier et leur nombre moyen de visites programmées chaque "
        + "semaine",
        "l’esembles des conférencier et leur nombre moyen de visites prévues "
        + "chaque jour",
        "l’esembles des conférencier et leur nombre moyen de visites prévues "
        + "chaque semaine"
    };
    
    @FXML
    private Button btnFiltres;

    @FXML
    private Button btnRetour;

    @FXML
    private Button btnValider;
    
    @FXML
    private Label labelDate;

    @FXML
    private TableColumn<VisiteCalculResultat, String> Conferencier;
    
    @FXML
    private TableColumn<VisiteCalculResultat, Double> nbMoyen;
    
    @FXML
    private ChoiceBox<String> listePhrase;

    @FXML
    private TableView<VisiteCalculResultat> tableConferencier;
    
    /**
     * 
     */
    @FXML
    public void initialize() {
        
        listePhrase.getItems().addAll(choix);
        
        // défini la valeur par défaut
        listePhrase.setValue(choix[3]);
        
        Conferencier.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getIntitule()));
        
        nbMoyen.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(
                        cellData.getValue().getCalculVisites()).asObject());
        
        // Déterminer les dates de début et de fin globales
        LocalDate dateDebutGlobal = LocalDate.MAX;
        LocalDate dateFinGlobal = LocalDate.MIN;

        for (Visite visite : visites.values()) {
            LocalDate dateVisite = visite.getDate();
            if (dateVisite.isBefore(dateDebutGlobal)) {
                dateDebutGlobal = dateVisite;
            }
            if (dateVisite.isAfter(dateFinGlobal)) {
                dateFinGlobal = dateVisite;
            }
        }
        
        calculerMoyenneVisitesEnsembleConferencier(visites, dateDebutGlobal,
                                                            dateFinGlobal);
        
        labelDate.setText("du " + dateDebutGlobal.format(DATE_FORMAT) 
                          + " au " + dateFinGlobal.format(DATE_FORMAT));
    }
    
    @FXML
    void btnFiltresAction(ActionEvent event) {
        EchangeurDeVue.creerPopUp(
                "donneesCalculeesConferencierEnsembleJourFiltrePopUp");
    }
    
    @FXML
    void btnValiderAction(ActionEvent event) {
        
        if (listePhrase.getValue().equals(choix[0])) {
            EchangeurDeVue.changerVue("donneesCalculeesConferencierVue");
        }
        
        if (listePhrase.getValue().equals(choix[1])) {
            EchangeurDeVue.changerVue(
                    "donneesCalculeesConferencierMoyenneJourVue");
        }
        
        if (listePhrase.getValue().equals(choix[2])) {
            EchangeurDeVue.changerVue(
                    "donneesCalculeesConferencierMoyenneSemaineVue");
        }
        
        if (listePhrase.getValue().equals(choix[3])) {
                EchangeurDeVue.changerVue(
                        "donneesCalculeesConferencierEnsembleJourVue");
        }
        
        if (listePhrase.getValue().equals(choix[4])) {
            EchangeurDeVue.changerVue(
                    "donneesCalculeesConferencierEnsembleSemaineVue");
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
     * Calcule la moyenne de visites programmées par jour pour chaque
     * conférencier à partir d'une liste de visites spécifiée.
     * 
     * @param visites la liste des visites à utiliser pour le calcul
     */
    private void calculerMoyenneVisitesEnsembleConferencier(
            LinkedHashMap<String, Visite> visites,
            LocalDate dateDebut,
            LocalDate dateFin) {

        /* 
         * Initialiser les dates globales de début et de fin si elles
         * ne sont pas spécifiées
         */
        LocalDate dateDebutGlobal = dateDebut != null ? dateDebut 
                                                      : LocalDate.MAX;
        LocalDate dateFinGlobal = dateFin != null ? dateFin 
                                                  : LocalDate.MIN;      

        /* 
         * Parcourir toutes les visites pour ajuster les dates
         * globales de début et de fin
         */
        for (Map.Entry<String, Visite> paire : visites.entrySet()) {
            LocalDate dateVisite = paire.getValue().getDate();

            /* 
             * Ajuster dateDebutGlobal et dateFinGlobal en fonction
             * des dates de visite trouvées
             */
            if (dateDebut == null && dateVisite.isBefore(dateDebutGlobal)) {
                dateDebutGlobal = dateVisite;
            }
            if (dateFin == null && dateVisite.isAfter(dateFinGlobal)) {
                dateFinGlobal = dateVisite;
            }
        }

        /* 
         * Calculer le nombre total de jours global entre
         * dateDebutGlobal et dateFinGlobal
         */
        long totalJours = ChronoUnit.DAYS.between(dateDebutGlobal, 
                                                  dateFinGlobal) + 1;

        // Calculer le nombre total de visites
        int totalVisitesGlobal = visites.size();

        // Calculer la moyenne globale
        double moyenneGlobale
        = totalJours > 0 ? (double) totalVisitesGlobal / totalJours
                         : 0;
        
        // arrondir à 2 chiffre après la virgule
        double moyenneVisitesArrondi
        = Math.round(moyenneGlobale * 100.0) / 100.0;
        
        /* 
         * Créer la liste de résultats avec une seule entrée pour la
         * moyenne globale
         */
        List<VisiteCalculResultat> resultats = new ArrayList<>();
        resultats.add(new VisiteCalculResultat("Tout les conferenciers", 
                                                moyenneVisitesArrondi));

        // Mettre à jour le tableau avec le résultat
        ObservableList<VisiteCalculResultat> confsListe 
        = FXCollections.observableArrayList(resultats);
        tableConferencier.setItems(confsListe);
        
        labelDate.setText("du " + dateDebutGlobal.format(DATE_FORMAT) 
                          + " au " + dateFinGlobal.format(DATE_FORMAT));
        
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
            
         // Filtrer par exposition temporaire
            if (critere.getExpositionTemporaire()
                && !(paire.getValue().getExposition()
                     instanceof ExpositionTemporaire)) {
                
                match = false;
            }
            
            // Filtrer par exposition permanente
            if (critere.getExpositionPermanente()
                && paire.getValue().getExposition()
                   instanceof ExpositionTemporaire) {
                
                match = false;
            }
            
            // Filtrer par conférencier interne
            if (critere.getInterne()
                && !(paire.getValue().getConferencier().estInterne())) {
                
                match = false;
            }
            
            // Filtrer par conférencier externe
            if (critere.getExterne()
                && (paire.getValue().getConferencier().estInterne())) {
                
                match = false;
            }
      
            if (match) {
                visitesFiltrees.putLast(paire.getKey(), paire.getValue());
            }
        }

        calculerMoyenneVisitesEnsembleConferencier(visitesFiltrees, 
                                                   critere.getDateDebut(), 
                                                   critere.getDateFin());
    }

}