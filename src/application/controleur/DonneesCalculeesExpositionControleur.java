/*
 * DonneesImporteesExpositionControleur.java                           
 * 25 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import application.EchangeurDeVue;
import application.modele.CritereFiltreVisite;
import application.modele.Visite;
import application.utilitaire.GenererPdf;
import application.utilitaire.TraitementDonnees;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;

/**
 * Contrôleur pour la gestion des données importées des expositions.
 * 
 * Cette classe gère l'affichage et les interactions relatives aux données 
 * calculées des expositions dans l'application. Elle permet à l'utilisateur 
 * de visualiser les informations des expositions, ainsi que d'effectuer 
 * des actions telles que le retour à l'écran d'accueil ou la fermeture de 
 * l'application.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class DonneesCalculeesExpositionControleur {
    
    private static LinkedHashMap<String, Visite> visites
    = TraitementDonnees.getDonnees().getVisites();
    
    private static LinkedHashMap<String, Visite> expositionsCalculees 
    = new LinkedHashMap<>();
    
    // Format pour les dates au format jj/MM/aaaa
    private static final DateTimeFormatter DATE_FORMAT 
    = AccueilControleur.getDateFormatterFR();
    
    /** Ensemble des choix disponibles pour filtrer */
    public static final String[] choix 
        = {"Expositions qui n’ont aucune visite",
           "Expositions et leur nombre moyen de visites programmées chaque jour",
           "Expositions et leur nombre moyen de visites programmées chaque "
               + "semaine",
           "L’ensembles des expositions et leur nombre moyen de visites prévues "
                   + "chaque jour",
           "L’ensembles des expositions et leur nombre moyen de visites "
                   + "prévues chaque semaine"
        };
    
    @FXML
    private Button aideAction;
    
    @FXML
    private Button btnFiltres;

    @FXML
    private Button btnRetour;

    @FXML
    private Button btnValider;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> aucuneVisite;
    
    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> date;
    
    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> horaireDebut;

    @FXML
    private ChoiceBox<String> listePhrase;

    @FXML
    private TableView<Map.Entry<String, Visite>> tableExposition;
    
    /**
     * 
     */
    @FXML
    public void initialize() {
        
        listePhrase.getItems().addAll(choix);
        
        // défini la valeur par défaut
        listePhrase.setValue(choix[0]);
        
        aucuneVisite.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getExposition().getIntitule()); 
        });
        
        date.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getDate().format(DATE_FORMAT)); 
        });
        
        horaireDebut.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).toStringHoraireDebut()); 
        });
        
        afficherVisites(visites);
        
    }
    
    private static Visite getVisite(
            CellDataFeatures<Entry<String, Visite>, String> celluleDonnees) {
        
        return celluleDonnees.getValue().getValue();
    }
    
    @FXML
    void convertirPdfOnAction(ActionEvent event) {
        String chemin;
        chemin = AccueilControleur.chemin();
            
        try {
            GenererPdf.troisColonneCalculeesPdf(expositionsCalculees, chemin, 
                                                choix[0], "Exposition");
            AccueilControleur.alertePdfSucces();
        } catch (IOException err) {  
            AccueilControleur.alertePdfEchec(err);
        }
               
    }
    
    @FXML
    void btnFiltresAction(ActionEvent event) {
        EchangeurDeVue.creerPopUp("donneesCalculeesExpositionFiltresPopUP");
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
    void retourAccueilAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }

    @FXML
    void quitterAction(ActionEvent event) {
        EchangeurDeVue.getFenetreAppli().hide();
    }

    @FXML
    void aideAction(ActionEvent event) {
    	// Appel de la méthode lancerAide de AccueilControleur avec un indice spécifique
    	// Utilise l'indice 2 pour ouvrir un lien d'aide correspondant
    	// à la réference de la partit des Expositions dans données calculées
        AccueilControleur.lancerAide(2);
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuDonneesCalculeesVue");
    }
    
    private void afficherVisites(
            LinkedHashMap<String, Visite> visites) {
        
        ObservableList<Map.Entry<String, Visite>> exposListe
        = FXCollections.observableArrayList(visites.entrySet());
        
        expositionsCalculees.clear();
        for (Map.Entry<String, Visite> entry : exposListe) {
            expositionsCalculees.put(entry.getKey(), entry.getValue());
        }
        
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
    public void appliquerFiltreInverse(CritereFiltreVisite critere) {

        LinkedHashMap<String, Visite> visitesNonCorrespondantes 
        = new LinkedHashMap<>();

        for (Map.Entry<String, Visite> paire : visites.entrySet()) {
            
            boolean match = false; 

            // Vérifie si la visite est hors de la plage de dates spécifiée
            if (critere.getDateDebut() != null) {
                
                LocalDate dateDebut = critere.getDateDebut();
                LocalDate dateFin
                = critere.getDateFin() != null ? critere.getDateFin()
                                               : critere.getDateDebut();
                
                // Si la date de la visite est avant la date de début ou après la date de fin, elle ne correspond pas
                if (paire.getValue().getDate().isBefore(dateDebut)
                    || paire.getValue().getDate().isAfter(dateFin)) {
                    match = true;  // Hors de la plage de dates, donc non correspondant
                }
            }

            // Vérifie si la visite est hors de la plage horaire spécifiée
            if (critere.getHoraireDebut() != 0) {
                
                int horaireDebut = critere.getHoraireDebut();
                int horaireFin
                = critere.getHoraireFin() != 0 ? critere.getHoraireFin()
                                               : critere.getHoraireDebut();
                
                // Si l'horaire de la visite est avant l'horaire de début ou après l'horaire de fin, elle ne correspond pas
                if (paire.getValue().getHoraireDebut() < horaireDebut
                    || paire.getValue().getHoraireDebut() > horaireFin) {
                    match = true;  // Hors de la plage horaire, donc non correspondant
                }
            }

            // Si l'une des conditions de non-correspondance est vraie, ajouter la visite à la liste
            if (match) {
                visitesNonCorrespondantes.putLast(paire.getKey(), paire.getValue());
            }
        }
        
        afficherVisites(visitesNonCorrespondantes);
    }
}
