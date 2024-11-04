/*
 * DonneesCalculeesExpositionMoyenneJourControleur.java                           
 * 1 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
public class DonneesCalculeesExpositionMoyenneJourControleur {
    
    private static ArrayList<Visite> visites
    = TraitementDonnees.getVisites();
    
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
    private TableColumn<VisiteMoyenneResultat, String> Exposition;
    
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
        listePhrase.setValue(choix[1]);
        
        Exposition.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIntituleExposition()));
        
        nbMoyen.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMoyenneVisites()).asObject());
        
        calculerMoyenneVisitesParExpositionParJour(visites);
    }
    
    /**
     * Formatte une date en chaîne de caractère en format française.
     * 
     * @param date la date à convertir
     * @return la data convertie dans le format français
     */
    private static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMAT) : "";
    }

    
    /**
     * Transforme le tableau des mots clés en une chaîne de caractère
     * plus visible.
     * 
     * @param motsCles la liste des mots clés
     * @return la chaîne de caractère contenant les mots clés
     */
    private static String toStringMotsCles(String[] motsCles) {
        return motsCles != null ? String.join(", ", motsCles) : "";
    }
    
    @FXML
    void btnFiltresAction(ActionEvent event) {
        EchangeurDeVue.creerPopUp("donneesCalculeesExpositionMoyenneJourFiltrePopUp");
    }
    
    @FXML
    void btnValiderAction(ActionEvent event) {
        
        if (listePhrase.getValue().equals(choix[0])) {
            EchangeurDeVue.changerVue("donneesCalculeesExpositionVue");
        }
        
        if (listePhrase.getValue().equals(choix[1])) {
            EchangeurDeVue.changerVue("donneesCalculeesExpositionMoyenneJourVue");
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
    private void calculerMoyenneVisitesParExpositionParJour(List<Visite> visites) {
        // Initialiser les dates globales de début et de fin
        LocalDate dateDebutGlobal = LocalDate.MAX;
        LocalDate dateFinGlobal = LocalDate.MIN;

        // Parcourir toutes les visites pour trouver les dates globales min et max
        for (Visite visite : visites) {
            LocalDate dateVisite = visite.getDate();

            if (dateVisite.isBefore(dateDebutGlobal)) {
                dateDebutGlobal = dateVisite;
            }

            if (dateVisite.isAfter(dateFinGlobal)) {
                dateFinGlobal = dateVisite;
            }
        }

        // Calculer le nombre total de jours global entre dateDebutGlobal et dateFinGlobal
        long totalJours = java.time.temporal.ChronoUnit.DAYS.between(dateDebutGlobal, dateFinGlobal) + 1;
        System.out.println("Nombre total de jours global : " + totalJours);

        // Création d'une Map pour compter les visites par exposition
        Map<String, Integer> visitesParExposition = new HashMap<>();

        // Compter les visites pour chaque exposition
        for (Visite visite : visites) {
            String intituleExposition = visite.getExposition().getIntitule();
            visitesParExposition.put(intituleExposition, visitesParExposition.getOrDefault(intituleExposition, 0) + 1);
        }

        // Calculer la moyenne de visites pour chaque exposition
        List<VisiteMoyenneResultat> resultats = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : visitesParExposition.entrySet()) {
            String intituleExposition = entry.getKey();
            int totalVisites = entry.getValue();

            // Calculer la moyenne des visites pour cette exposition
            double moyenneVisites = totalJours > 0 ? (double) totalVisites / totalJours : 0;

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
        List<Visite> visitesFiltrees = new ArrayList<>();

        for (Visite visite : visites) {
            boolean match = true; 

            // Filtrer par date de visite
            if (critere.getDateDebut() != null) {
                LocalDate dateFin = critere.getDateFin() != null ? critere.getDateFin() : critere.getDateDebut();
                if (visite.getDate().isBefore(critere.getDateDebut()) || visite.getDate().isAfter(dateFin)) {
                    match = false;
                }
            }
      
            if (match) {
                visitesFiltrees.add(visite);
            }
        }

        // Maintenant, calculez les moyennes en utilisant la liste filtrée
        calculerMoyenneVisitesParExpositionParJour(visitesFiltrees);
    }


}

