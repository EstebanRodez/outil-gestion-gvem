/*
 * DonneesImporteesConferencierControleur.java                           
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
 * Contrôleur pour la gestion des données importées des conférenciers.
 * 
 * Cette classe gère l'affichage et les interactions relatives aux
 * données cacullées des conférenciers dans l'application. Elle
 * permet à l'utilisateur de visualiser les informations des
 * conférenciers, ainsi que d'effectuer des actions telles que le
 * retour à l'écran d'accueil ou la fermeture de l'application. 
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class DonneesCalculeesConferencierControleur {
    
    private static LinkedHashMap<String, Visite> visites
    = TraitementDonnees.getDonnees().getVisites();
    
    // Format pour les dates au format jj/MM/aaaa
    private static final DateTimeFormatter DATE_FORMAT 
    = AccueilControleur.getDateFormatterFR();

    private static LinkedHashMap<String, Visite> conferenciersCalculees 
    = new LinkedHashMap<>();
    
    /** Ensemble des choix disponibles pour filtrer */
    public static final String[] choix
    = {
        "Conférencier qui n’ont aucune visite",
        "Conférencier et leur nombre moyen de visites programmées chaque jour",
        "Conférencier et leur nombre moyen de visites programmées chaque "
        + "semaine",
        "L’ensemble des conférenciers et leur nombre moyen de visites prévues "
        + "chaque jour",
        "L’ensemble des conférenciers et leur nombre moyen de visites prévues "
        + "chaque semaine"
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
    private TableView<Map.Entry<String, Visite>> tableConferencier;
    
    /**
     * Méthode lancée à l'initialisation du contrôleur
     */
    @FXML
    public void initialize() {
        
        listePhrase.getItems().addAll(choix);
        
        // défini la valeur par défaut
        listePhrase.setValue(choix[0]);
        
        aucuneVisite.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getConferencier().getNom()); 
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
    
    @FXML
    void convertirPdfOnAction(ActionEvent event) {
        String chemin;
        chemin = AccueilControleur.chemin();
            
        try {
            GenererPdf.troisColonneCalculeesPdf(conferenciersCalculees, chemin, 
                                                choix[0], "Conferencier");
            AccueilControleur.alertePdfSucces();
        } catch (IOException err) {  
            AccueilControleur.alertePdfEchec(err);
        }
               
    }
    
    private static Visite getVisite(
            CellDataFeatures<Entry<String, Visite>, String> celluleDonnees) {
        
        return celluleDonnees.getValue().getValue();
    }
    
    @FXML
    void btnFiltresAction(ActionEvent event) {
        EchangeurDeVue.creerPopUp("donneesCalculeesConferencierFiltresPopUP");
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
    	// Appel de la méthode lancerAide de AccueilControleur avec un indice spécifique
    	// Utilise l'indice 1 pour ouvrir un lien d'aide correspondant
    	// à la réference de la partit des conferenciers dans données calculées
        AccueilControleur.lancerAide(1); 
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuDonneesCalculeesVue");
    }
    
    private void afficherVisites(
            LinkedHashMap<String, Visite> visites) {
        
        ObservableList<Map.Entry<String, Visite>> confListe
        = FXCollections.observableArrayList(visites.entrySet());
        
        conferenciersCalculees.clear();
        for (Map.Entry<String, Visite> entry : confListe) {
            conferenciersCalculees.put(entry.getKey(), entry.getValue());
        }
        
        tableConferencier.setItems(confListe);
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
                
                /* 
                 * Si la date de la visite est avant la date de début
                 * ou après la date de fin, elle ne correspond pas
                 */
                if (paire.getValue().getDate().isBefore(dateDebut)
                    || paire.getValue().getDate().isAfter(dateFin)) {
                    
                    // Hors de la plage de dates, donc non correspondant
                    match = true;
                }
            }

            // Vérifie si la visite est hors de la plage horaire spécifiée
            if (critere.getHoraireDebut() != 0) {
                
                int horaireDebut = critere.getHoraireDebut();
                int horaireFin
                = critere.getHoraireFin() != 0 ? critere.getHoraireFin()
                                               : critere.getHoraireDebut();
                
                /* 
                 * Si l'horaire de la visite est avant l'horaire de
                 * début ou après l'horaire de fin, elle ne
                 * correspond pas
                 */
                if (paire.getValue().getHoraireDebut() < horaireDebut
                    || paire.getValue().getHoraireDebut() > horaireFin) {
                    
                    // Hors de la plage horaire, donc non correspondant
                    match = true;
                }
            }

            /* 
             * Si l'une des conditions de non-correspondance est
             * vraie, ajouter la visite à la liste
             */
            if (match) {
                visitesNonCorrespondantes.putLast(paire.getKey(), paire.getValue());
            }
        }
        
        afficherVisites(visitesNonCorrespondantes);
    }

}
