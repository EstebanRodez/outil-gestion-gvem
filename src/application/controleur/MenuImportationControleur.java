/*
 * MenuImportationControleur.java                           
 * 5 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import application.EchangeurDeVue;
import application.utilitaire.FichierDonneesInvalides;
import application.utilitaire.ImportationCSV;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;


/**
 * Contrôleur pour la gestion de l'importation des données.
 * 
 * Cette classe permet à l'utilisateur de choisir entre deux méthodes 
 * d'importation de données : depuis un fichier local ou à distance. 
 * Elle gère également les interactions liées à l'aide et au retour 
 * vers l'interface d'accueil.
 * 
 * @author Ayoub Laluti
 * @version 1.0
 */
public class MenuImportationControleur {
    
    private static final String FICHIER_IMPORTER_SUCCES 
        = "Les fichiers ont été importés avec succès";

    private static final String EN_ROUGE 
        = "-fx-text-fill: red; -fx-background-color: #ffffff;";
    private static final String EN_NOIR 
    = "-fx-text-fill: black; -fx-background-color: #ffffff;";

    /** Listes des fichiers selectionnees */
    private ArrayList<File> fichiersSelectionnes = new ArrayList<>();
    
    @FXML
    private Label labelEmplacementUn, labelEmplacementDeux, 
                  labelEmplacementTrois, labelEmplacementQuatre, 
                  labelMessageErr;

    @FXML
    private Button btnParcourirUn, btnParcourirDeux, btnParcourirTrois,
                   btnParcourirQuatre,btnSupprimerUn, btnSupprimerDeux, 
                   btnSupprimerTrois, btnSupprimerQuatre, btnValider, btnRetour;
    
    /** Listes des labels */
    private List<Label> labels;
    
    /** Listes des boutons supprimer */
    private List<Button> btnSupprimer;
    
    /** Listes des boutons parcourir */
    private List<Button> btnParcourir;
    
    /**
     * Initialisation de la vue
     */
    @FXML
    public void initialize() {
        labels = Arrays.asList(labelEmplacementUn, 
                               labelEmplacementDeux, 
                               labelEmplacementTrois, 
                               labelEmplacementQuatre);
        
        btnSupprimer = Arrays.asList(btnSupprimerUn, 
                                     btnSupprimerDeux, 
                                     btnSupprimerTrois, 
                                     btnSupprimerQuatre);
        
        btnParcourir = Arrays.asList(btnParcourirUn, 
                                     btnParcourirDeux, 
                                     btnParcourirTrois, 
                                     btnParcourirQuatre);
        
        importerFichier();
        mettreAJourLabelsFichiers(); 
        
        if (!isFichierValide()) {
            btnValider.setDisable(true);
        } 
         
    }
    
    /**
     * Exploite les donnees importees
     */
    private void exploiterDonnee() {
        for (File fichier : fichiersSelectionnes) {
            try {
                ImportationCSV.importerDonnees(fichier.getAbsolutePath());
            } catch (FichierDonneesInvalides err) {
                labelMessageErr.setText(err.getMessage());
                btnValider.setDisable(true);
            }
        }
    }

    /**
     * Permet d'importer des fichiers depuis le disque local
     */
    private void importerFichier() {
        List<File> fichiersImportes;
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilter
            = new FileChooser
                      .ExtensionFilter("Fichiers données (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        
        fichiersImportes
            = fileChooser.showOpenMultipleDialog(EchangeurDeVue
                                                    .getFenetreAppli());
        
        fichiersSelectionnes = new ArrayList<>(fichiersImportes);
        
        // Si la liste a plus de 4 éléments, on la réduit à 4 éléments
        if (fichiersSelectionnes.size() > 4) {
            fichiersSelectionnes = new ArrayList<>(fichiersSelectionnes
                                                       .subList(0, 4));
        } else {
            while (fichiersSelectionnes.size() < 4) {
                fichiersSelectionnes.add(null);  
            }  
        }
    }
    
    /**
     * Met a jour les labels pour afficher les fichiers selectionnes
     */
    private void mettreAJourLabelsFichiers() {
        String nomFichier;
        File fichier;
        
        for (int numFichier = 0; numFichier < fichiersSelectionnes.size() 
                && numFichier < labels.size(); numFichier++) {
            fichier = fichiersSelectionnes.get(numFichier);
            
            // Vérifiez si l'élément n'est pas null avant d'appeler getName()
            if (fichier != null) {
                nomFichier = fichier.getName();
                labels.get(numFichier).setText(nomFichier);
            } else {
                labels.get(numFichier).setText("");
            }
        }
        
        // Videz les labels restants si la liste des fichiers est plus petite
        for (int i = fichiersSelectionnes.size(); i < labels.size(); i++) {
            labels.get(i).setText("");  
        }       
    }

    /**
     * Vérifie si les fichiers sélectionnés sont valides.
     * @return true si au moins un champ de fichier est rempli, sinon false.
     */
    private boolean isFichierValide() {
        Boolean emplacementValide,
                fichiersDistincts;
        String text;
        
        // Par defaut
        labelMessageErr.setText(" ");
        for (Label label : labels) {
            label.setStyle(EN_NOIR);
        }
        
        emplacementValide = true;
        for (int indiceLabel = 0; indiceLabel < labels.size(); indiceLabel++) {
             text = labels.get(indiceLabel).getText();
            if (text == null || text.isEmpty()) {
                emplacementValide = false;
            }
        }
        
        fichiersDistincts = true;
        for (int i = 0; i < fichiersSelectionnes.size(); i++) {
            for (int j = i + 1; j < fichiersSelectionnes.size(); j++) {
                if (fichiersSelectionnes.get(i) != null 
                        && fichiersSelectionnes.get(j) != null 
                        && comparerFichier(fichiersSelectionnes.get(i),
                                           fichiersSelectionnes.get(j))) {
                    fichiersDistincts = false;
                    labelMessageErr.setText("Erreur: fichiers identiques");
                    labels.get(j).setStyle(EN_ROUGE);
                    labels.get(i).setStyle(EN_ROUGE);
                } 
            }
        }
    
        return emplacementValide && fichiersDistincts;
    }
    
    /**
     * Compare deux fichiers pour savoir s'ils sont identiques.
     * Compare la taille des deux fichiers et lit byte par byte
     * @param fichier1
     * @param fichier2
     * @return true si les fichiers sont identiques sinon false
     * @throws IOException 
     */
    private boolean comparerFichier(File fichier1, File fichier2) {
        if (fichier1.length() != fichier2.length()) {
            try (FileInputStream lecture1 = new FileInputStream(fichier1);
                    FileInputStream lecture2 = new FileInputStream(fichier2)) {

                      int byte1, 
                          byte2;
                      // Lire byte par byte des deux fichiers et comparer
                      while ((byte1 = lecture1.read()) != -1 
                                  && (byte2 = lecture2.read()) != -1) {
                          if (byte1 != byte2) {
                              return false; 
                          }
                      }
            } catch (FileNotFoundException erreur) {
                     labelMessageErr.setText("Erreur: fichiers introuvables");
            } catch (IOException e) {
                     labelMessageErr.setText("Erreur: Problème d'ouverture/fermeture de fichiers");
            }    
            return true;
        }
        return false;
    }
        

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("importerVue");
    }
    
    @FXML
    void btnValiderAction(ActionEvent event) {
        Collections.sort(fichiersSelectionnes); 
        exploiterDonnee();
        // Afficher une alerte avec les noms des fichiers sélectionnés
        Alert boiteInformationImportation
        = new Alert(Alert.AlertType.INFORMATION, FICHIER_IMPORTER_SUCCES, ButtonType.OK);

        boiteInformationImportation.setTitle("Importation");
        boiteInformationImportation.setHeaderText(
                "Fichiers importés avec succès");
        boiteInformationImportation.showAndWait();
    }
    
    /**
     * Supprime le nom du fichier du champ correspondant
     * et désactive "Valider" si aucun fichier n'est sélectionné.
     */
    @FXML
    void btnSupprimerAction(ActionEvent event) {
        
        for (int i = 0; i < btnSupprimer.size(); i++) {
            if (event.getSource() == btnSupprimer.get(i)) {
                fichiersSelectionnes.set(i, null); 
                labels.get(i).setText("");         
            }
        }

        btnValider.setDisable(!isFichierValide());
        mettreAJourLabelsFichiers(); 
    }
    
    @FXML
    void btnParcourirAction(ActionEvent event) {
        FileChooser fileChooser;
        File fichier;
        
        fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier csv");
        fileChooser.getExtensionFilters()
                        .add(new FileChooser
                                     .ExtensionFilter("Fichiers CSV", "*.csv"));
        fichier = fileChooser.showOpenDialog(null);

        if (fichier != null) {
            String nomFichier = fichier.getName();

            for (int i = 0; i < btnParcourir.size(); i++) {
                if (event.getSource() == btnParcourir.get(i)) {
                    fichiersSelectionnes.set(i, fichier);
                    labels.get(i).setText(nomFichier);
                }
            }

            btnValider.setDisable(!isFichierValide());
            mettreAJourLabelsFichiers(); 
        }
    } 
}

