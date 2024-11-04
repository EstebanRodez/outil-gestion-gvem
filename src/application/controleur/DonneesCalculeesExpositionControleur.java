/*
 * DonneesImporteesExpositionControleur.java                           
 * 25 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import application.EchangeurDeVue;
import application.modele.CritereFiltreVisite;
import application.modele.Visite;
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
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableColumn<Visite, String> aucuneVisite;
    
    @FXML
    private TableColumn<Visite, LocalDate> date;
    
    @FXML
    private TableColumn<Visite, String> horaireDebut;

    @FXML
    private ChoiceBox<String> listePhrase;

    @FXML
    private TableView<Visite> tableExposition;
    
    /**
     * 
     */
    @FXML
    public void initialize() {
        
        listePhrase.getItems().addAll(choix);
        
        // défini la valeur par défaut
        listePhrase.setValue(choix[0]);
        
        aucuneVisite.setCellValueFactory(cellData -> {
            Visite visite = cellData.getValue();
            return new SimpleStringProperty(visite.getExposition()
                                                   .getIntitule()); 
        });
        
        date.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        
        horaireDebut.setCellValueFactory(cellData -> {
            Visite visite = cellData.getValue();
            return new SimpleStringProperty(visite.toStringHoraireDebut()); 
        });
        
        ObservableList<Visite> exposListe
        = FXCollections.observableArrayList(visites);
        tableExposition.setItems(exposListe);
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
        EchangeurDeVue.creerPopUp("donneesCalculeesExpositionFiltresPopUP");
    }
    
    @FXML
    void btnValiderAction(ActionEvent event) {
        
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
        ObservableList<Visite> visitesNonCorrespondantes = FXCollections.observableArrayList();

        for (Visite visite : visites) {
            boolean match = false; 

            // Vérifie si la visite est hors de la plage de dates spécifiée
            if (critere.getDateDebut() != null) {
                LocalDate dateDebut = critere.getDateDebut();
                LocalDate dateFin = critere.getDateFin() != null ? critere.getDateFin() : critere.getDateDebut();
                
                // Si la date de la visite est avant la date de début ou après la date de fin, elle ne correspond pas
                if (visite.getDate().isBefore(dateDebut) || visite.getDate().isAfter(dateFin)) {
                    match = true;  // Hors de la plage de dates, donc non correspondant
                }
            }

            // Vérifie si la visite est hors de la plage horaire spécifiée
            if (critere.getHoraireDebut() != 0) {
                int horaireDebut = critere.getHoraireDebut();
                int horaireFin = critere.getHoraireFin() != 0 ? critere.getHoraireFin() : critere.getHoraireDebut();
                
                // Si l'horaire de la visite est avant l'horaire de début ou après l'horaire de fin, elle ne correspond pas
                if (visite.getHoraireDebut() < horaireDebut || visite.getHoraireDebut() > horaireFin) {
                    match = true;  // Hors de la plage horaire, donc non correspondant
                }
            }

            // Si l'une des conditions de non-correspondance est vraie, ajouter la visite à la liste
            if (match) {
                visitesNonCorrespondantes.add(visite);
            }
        }
        tableExposition.setItems(visitesNonCorrespondantes);
    }


}
