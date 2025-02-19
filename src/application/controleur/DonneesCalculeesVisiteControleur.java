/*
 * DonneesImporteesVisiteControleur.java                           
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
import application.modele.ExpositionTemporaire;
import application.modele.Visite;
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
import javafx.scene.control.TableColumn.CellDataFeatures;

/**
 * Contrôleur pour la gestion des données importées des visites.
 * 
 * Cette classe gère l'affichage et les interactions relatives aux données 
 * calculées des visites dans l'application. Elle permet à l'utilisateur 
 * de visualiser les informations des visites, ainsi que d'effectuer 
 * des actions telles que le retour à l'écran d'accueil ou la fermeture de 
 * l'application.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class DonneesCalculeesVisiteControleur {
    
    private static LinkedHashMap<String, Visite> visites
        = TraitementDonnees.getDonnees().getVisites();
    
    private static LinkedHashMap<String, Visite> visitesAvecFiltre 
        = new LinkedHashMap<>();
    
    // Format pour les dates au format jj/MM/aaaa
    private static final DateTimeFormatter DATE_FORMAT 
    = AccueilControleur.getDateFormatterFR();
    
    @FXML
    private Button aideAction;
    
    @FXML
    private Label LabelResultat;
    
    @FXML
    private Button btnRetour;
    
    @FXML
    private Button btnFiltres;
    
    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> conferencier;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> date;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> employe;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> exposition;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> horaireDebut;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> identifiant;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> intitule;

    @FXML
    private TableColumn<Map.Entry<String, Visite>, String> numTel;

    @FXML
    private TableView<Map.Entry<String, Visite>> tableVisite;
    
    /**
     * 
     */
    @FXML
    public void initialize() {

        conferencier.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getConferencier().getNom()); 
        });
        
        date.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getDate().format(DATE_FORMAT)); 
        });
        
        employe.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getEmploye().getNom()); 
        });
        
        exposition.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getExposition().getIntitule()); 
        });
        
        horaireDebut.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).toStringHoraireDebut()); 
        });
        
        identifiant.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getKey()); 
        });
        
        intitule.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getClient().getIntitule()); 
        });
        
        numTel.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    getVisite(cellData).getClient().getNumTel()); 
        });
        
        afficherVisites(visites);
        
        ObservableList<Map.Entry<String, Visite>> visitesListe
        = FXCollections.observableArrayList(visites.entrySet());
        tableVisite.setItems(visitesListe);
    }
    
    private static Visite getVisite(
            CellDataFeatures<Entry<String, Visite>, String> celluleDonnees) {
        
        return celluleDonnees.getValue().getValue();
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
    	// Utilise l'indice 3 pour ouvrir un lien d'aide correspondant
    	// à la réference de la partit des Visites dans données calculées
        AccueilControleur.lancerAide(3);
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("menuDonneesCalculeesVue");
    }
    
    @FXML
    void btnFiltresAction(ActionEvent event) {
        EchangeurDeVue.creerPopUp("donneesCalculeesVisiteFiltresPopUP");
    }
    
    @FXML
    void convertirPdfOnAction(ActionEvent event) {
        String chemin;
        chemin = AccueilControleur.chemin();
            
        try {
            GenererPdf.visitePdf(visitesAvecFiltre, chemin, 'C');
            AccueilControleur.alertePdfSucces();
        } catch (IOException err) {  
            AccueilControleur.alertePdfEchec(err);
        }
               
    }
    
    private void afficherVisites(
            LinkedHashMap<String, Visite> visites) {
        
        ObservableList<Map.Entry<String, Visite>> visitesListe
        = FXCollections.observableArrayList(visites.entrySet());
        
        visitesAvecFiltre.clear();
        for (Map.Entry<String, Visite> entry : visitesListe) {
            visitesAvecFiltre.put(entry.getKey(), entry.getValue());
        }
        
        tableVisite.setItems(visitesListe);
    }

    /**
     * Applique les critères de filtrage sur la liste des visites.
     * Parcourt la liste des visites et les filtre en fonction des
     * critères spécifiés dans l'objet CritereFiltre.
     * Les visites qui correspondent à tous les critères 
     * (type d'exposition, conférencier, exposition, dates 
     * et horaires)
     *  sont ajoutées à la liste des visites filtrées.
     *
     * @param critere objet contenant les critères de filtrage à 
     *                appliquer
     */
    public void appliquerFiltre(CritereFiltreVisite critere) {
        
        // Filtrer la liste des visites en fonction des critères reçus
        LinkedHashMap<String, Visite> visitesFiltrees 
        = new LinkedHashMap<>();

        for (Map.Entry<String, Visite> paire : visites.entrySet()) {
            
            Visite visite = paire.getValue();
            boolean match = true;
            // Filtrer par exposition temporaire
            if (critere.getExpositionTemporaire()
                && !(visite.getExposition() instanceof ExpositionTemporaire)) {
                
                match = false;
            }
            
            // Filtrer par exposition permanente
            if (critere.getExpositionPermanente()
                && visite.getExposition() instanceof ExpositionTemporaire) {
                
                match = false;
            }
            
            // Filtrer par conférencier interne
            if (critere.getInterne()
                && !(visite.getConferencier().estInterne())) {
                
                match = false;
            }
            
            // Filtrer par conférencier externe
            if (critere.getExterne()
                && (visite.getConferencier().estInterne())) {
                
                match = false;
            }

            // Filtrer par conférencier
            if (critere.getConferencier() != null 
                && !visite.getConferencier().getNom()
                                            .equals(critere
                                                    .getConferencier())) { 
                match = false;
            }
            
            // Filtrer par exposition
            if (critere.getExposition() != null 
                && !visite.getExposition().getIntitule()
                                          .equals(critere.getExposition())) { 
                match = false;
            }

            // Filtrer par date de visite
            if (critere.getDateDebut() != null) {
                LocalDate dateFin;
                dateFin = critere.getDateFin() != null ? critere.getDateFin() 
                                                       : critere.getDateDebut();
                if (visite.getDate().isBefore(critere.getDateDebut()) 
                    || visite.getDate().isAfter(dateFin)) {
                    match = false;
                }
            }
            
            // Filtrer par plage horaire
            if (critere.getHoraireDebut() != 0) {
                int horaireFin;
                horaireFin = critere.getHoraireFin() != 0 
                                                    ? critere.getHoraireFin() 
                                                    : critere.getHoraireDebut();
                if (visite.getHoraireDebut() < critere.getHoraireDebut() 
                    || visite.getHoraireDebut() > horaireFin) {
                    match = false;
                }
            }

            if (match) {
                visitesFiltrees.putLast(paire.getKey(), paire.getValue());
            }
        }
        
        afficherVisites(visitesFiltrees);
        
        LabelResultat.setText("Nombre de visite correspondant aux filtres : " 
                               + visitesFiltrees.size());
    }

}
