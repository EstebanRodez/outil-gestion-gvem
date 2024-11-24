/*
 * StatistiqueConferencierPourcentageControleur.java                           
 * 16 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import application.utilitaire.GenererPdf;
import application.utilitaire.TraitementDonnees;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
public class StatistiqueConferencierPourcentageControleur {
    
    private static LinkedHashMap<String, Visite> visites
        = TraitementDonnees.getDonnees().getVisites();
    
    // Format pour les dates au format jj/MM/aaaa
    private static final DateTimeFormatter DATE_FORMAT 
    = AccueilControleur.getDateFormatterFR();
    
    private static  List<VisiteCalculResultat> resultatsPdf 
        = new ArrayList<>();
    
    private static String date = "";
    
    @FXML
    private TableColumn<VisiteCalculResultat, String> Conferencier;

    @FXML
    private Button btnFiltres;

    @FXML
    private Button btnRetour;
    
    @FXML
    private Button aideAction;
    
    @FXML
    private Label labelDate;

    @FXML
    private TableColumn<VisiteCalculResultat, String> tauxVisites;

    @FXML
    private TableView<VisiteCalculResultat> tableConferencier;
    
    /**
     */
    @FXML
    public void initialize() {
                
        Conferencier.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getIntitule()));
            
        tauxVisites.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                    cellData.getValue().getCalculVisitesPourcentage()));
        
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
        
        calculerTauxVisitesParConferencier(visites, dateDebutGlobal,
                                                    dateFinGlobal);
        
        date = "Du " + dateDebutGlobal.format(DATE_FORMAT) 
                + " au " + dateFinGlobal.format(DATE_FORMAT);
        labelDate.setText(date);
    }
    
    @FXML
    void convertirPdfOnAction(ActionEvent event) {
        String chemin;
        chemin = AccueilControleur.chemin();
            
        try {
            GenererPdf.deuxColonnePdf(resultatsPdf , chemin, 
                                      "Pourcentages des Conférenciers",
                                        "Conferencier", 'S', 'R', date);
            AccueilControleur.alertePdfSucces();
        } catch (IOException err) {  
            AccueilControleur.alertePdfEchec(err);
        }
               
    }

    @FXML
    void aideAction(ActionEvent event) {
    	AccueilControleur.lancerAide(13);
    }

    @FXML
    void btnFiltresAction(ActionEvent event) {
        EchangeurDeVue.creerPopUp("statistiqueConferencierPourcentageFiltrePopUp");
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuStatistiquePourcentageVue");
    }

    @FXML
    void quitterAction(ActionEvent event) {
        EchangeurDeVue.getFenetreAppli().hide();
    }

    @FXML
    void retourAccueilAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }
    
    private void calculerTauxVisitesParConferencier(
            LinkedHashMap<String, Visite> visites,
            LocalDate dateDebut,
            LocalDate dateFin) {
        
        // Initialiser les dates globales de début et de fin si elles ne sont pas spécifiées
        LocalDate dateDebutGlobal = dateDebut != null ? dateDebut 
                                                      : LocalDate.MAX;
        LocalDate dateFinGlobal = dateFin != null ? dateFin 
                                                  : LocalDate.MIN;

        // Parcourir toutes les visites pour ajuster les dates globales de début et de fin
        for (Map.Entry<String, Visite> paire : visites.entrySet()) {
            LocalDate dateVisite = paire.getValue().getDate();

            // Ajuster dateDebutGlobal et dateFinGlobal en fonction des dates de visite trouvées
            if (dateDebut == null && dateVisite.isBefore(dateDebutGlobal)) {
                dateDebutGlobal = dateVisite;
            }
            if (dateFin == null && dateVisite.isAfter(dateFinGlobal)) {
                dateFinGlobal = dateVisite;
            }
        }
        
        // Création d'une Map pour compter les visites par conferencier
        Map<String, Integer> visitesParConferencier = new HashMap<>();
        
        // Compter les visites pour chaque conferencier
        for (Map.Entry<String, Visite> paire : visites.entrySet()) {
            
            String intituleConferencier
            = paire.getValue().getConferencier().getNom();
            visitesParConferencier.put(
                intituleConferencier,
                visitesParConferencier.getOrDefault(intituleConferencier, 0) + 1
            );
        }
                
        // Calculer la somme totale des visites
        double sommeTotaleVisites;
        sommeTotaleVisites = 0;
        for (Integer visitesCompteur : visitesParConferencier.values()) {
            sommeTotaleVisites += visitesCompteur;
        }
        
        // Calculer le total de visites pour chaque exposition
        List<VisiteCalculResultat> resultats = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : visitesParConferencier
                                                .entrySet()) {
            String intituleExposition = entry.getKey();
            double totalVisites = entry.getValue();
                        
            double pourcentageVisites;
            pourcentageVisites = (totalVisites / sommeTotaleVisites) * 100;
            
            // arrondir à 2 chiffre après la virgule
            double pourcentageVisitesArrondi;
            pourcentageVisitesArrondi = Math.round(pourcentageVisites * 100.0) / 
                                                                       100.0;
            // ajout du symbole "%"
            String pourcentageAvecSymbole;
            pourcentageAvecSymbole = pourcentageVisitesArrondi + " %";

            resultats.add(new VisiteCalculResultat(intituleExposition, 
                                                   pourcentageAvecSymbole));
        }
        
        ObservableList<VisiteCalculResultat> exposListe 
        = FXCollections.observableArrayList(resultats);
        
        resultatsPdf.clear();
        for (VisiteCalculResultat entry : exposListe) {
            resultatsPdf.add(entry);
        }
        
        tableConferencier.setItems(exposListe);
        
        date = "Du " + dateDebutGlobal.format(DATE_FORMAT) 
                + " au " + dateFinGlobal.format(DATE_FORMAT);
        labelDate.setText(date);
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

        calculerTauxVisitesParConferencier(visitesFiltrees, 
                                        critere.getDateDebut(), 
                                        critere.getDateFin());
            
        }

}
