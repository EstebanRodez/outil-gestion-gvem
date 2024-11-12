/*
 * DonneesCalculeesExpositionMoyenneJourControleur.java                           
 * 1 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
public class DonneesCalculeesExpositionMoyenneJourControleur {
    
    private static LinkedHashMap<String, Visite> visites
    = TraitementDonnees.getDonnees().getVisites();
    
    // Format pour les dates au format jj/MM/aaaa
    private static final DateTimeFormatter DATE_FORMAT 
    = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private static String[] choix = {"exposition qui n’ont aucune visite",
                                    "exposition et leur nombre moyen de " 
                                    + "visites programmées chaque jour",
                                    "exposition et leur nombre moyen de "
                                    + "visites programmées chaque semaine",
                                    " exposition et leur nombre moyen de "
                                    + "visites prévues chaque jour",
                                    " exposition et leur nombre moyen de "
                                    + "visites prévues chaque semaine"};
    
    @FXML
    private Button btnFiltres;

    @FXML
    private Button btnRetour;

    @FXML
    private Button btnValider;
    
    @FXML
    private Label labelDate;

    @FXML
    private TableColumn<VisiteCalculResultat, String> Exposition;
    @FXML
    private Button btnGenererPDF;

    
    @FXML
    private TableColumn<VisiteCalculResultat, Double> nbMoyen;
    
    @FXML
    private ChoiceBox<String> listePhrase;

    @FXML
    private TableView<VisiteCalculResultat> tableExposition;
    
    /**
     * 
     */
    @FXML
    public void initialize() {
        
        listePhrase.getItems().addAll(choix);
        
        // défini la valeur par défaut
        listePhrase.setValue(choix[1]);
        
        Exposition.setCellValueFactory(
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

        // Appel avec les dates globales
        calculerMoyenneVisitesParExposition(visites, dateDebutGlobal,
                                                     dateFinGlobal);
        
        labelDate.setText("du " + dateDebutGlobal.format(DATE_FORMAT) 
                          + " au " + dateFinGlobal.format(DATE_FORMAT));
    }
    
    @FXML
    void btnFiltresAction(ActionEvent event) {
        EchangeurDeVue.creerPopUp("donneesCalculeesExpositionMoyenneJourFiltrePopUp");
    }
    
    @FXML
    void btnValiderAction(ActionEvent event) {
        
        if (listePhrase.getValue().equals(choix[0])) {
            EchangeurDeVue.changerVue("donneesCalculeesExpositionVue");
            listePhrase.setValue(choix[0]);
        }
        
        if (listePhrase.getValue().equals(choix[1])) {
            EchangeurDeVue.changerVue("donneesCalculeesExpositionMoyenneJourVue");
        }
        
        if (listePhrase.getValue().equals(choix[2])) {
            EchangeurDeVue.changerVue("donneesCalculeesExpositionMoyenneSemaineVue");
        }
        
        if (listePhrase.getValue().equals(choix[3])) {
            EchangeurDeVue.changerVue("donneesCalculeesExpositionEnsembleJourVue");
        }
        
        if (listePhrase.getValue().equals(choix[4])) {
            EchangeurDeVue.changerVue("donneesCalculeesExpositionEnsembleSemaineVue");
        }
    }
    
    @FXML
    void btnGenererPDFAction(ActionEvent event) {
        /*try {
            List<VisiteCalculResultat> results = tableExposition.getItems();
            CreerPdf pdfGenerator = new CreerPdf();
            pdfGenerator.generatePdf("rapport_moyenne_expositions_jours.pdf", results);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } */
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
     * exposition en tenant compte d'une période de temps spécifiée
     * par les dates de début et de fin.
     * 
     * @param visites la liste des visites à utiliser pour le calcul
     * @param dateDebut la date de début de la période de calcul,
     *                  peut être null
     * @param dateFin la date de fin de la période de calcul,
     *                peut être null
     */
    private void calculerMoyenneVisitesParExposition(
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

        // Calculer le nombre total de jours global entre dateDebutGlobal et dateFinGlobal
        long totalJours = ChronoUnit.DAYS.between(dateDebutGlobal, 
                                                  dateFinGlobal) + 1;
        System.out.println("Nombre total de jours global : " + totalJours);

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

        // Calculer la moyenne de visites pour chaque exposition
        List<VisiteCalculResultat> resultats = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : visitesParExposition
                                                .entrySet()) {
            String intituleExposition = entry.getKey();
            int totalVisites = entry.getValue();

            // Calculer la moyenne des visites pour cette exposition
            double moyenneVisites = totalJours > 0 ? (double) totalVisites / 
                                                              totalJours : 0;
            
            // arrondir à 2 chiffre après la virgule
            double moyenneVisitesArrondi = Math.round(moyenneVisites * 100.0) / 
                                                                       100.0;

            resultats.add(new VisiteCalculResultat(intituleExposition, 
                                                    moyenneVisitesArrondi));
        }

        // Mettre à jour le tableau avec les résultats
        ObservableList<VisiteCalculResultat> exposListe 
        = FXCollections.observableArrayList(resultats);
        tableExposition.setItems(exposListe);
        
        labelDate.setText("du " + dateDebutGlobal.format(DATE_FORMAT) 
                          + " au " + dateFinGlobal.format(DATE_FORMAT));
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

        // Maintenant, calculez les moyennes en utilisant la liste filtrée
        calculerMoyenneVisitesParExposition(visitesFiltrees, 
                                            critere.getDateDebut(), 
                                            critere.getDateFin());

    }


}

