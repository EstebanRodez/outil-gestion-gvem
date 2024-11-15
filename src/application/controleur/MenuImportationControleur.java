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
import java.util.HashMap;
import java.util.List;

import application.EchangeurDeVue;
import application.utilitaire.FichierDonneesInvalides;
import application.utilitaire.GestionCSV;
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
        int indexLabel = -1;
        for (File fichier : fichiersSelectionnes) {
            try {
                indexLabel ++;
                ImportationCSV.importerDonnees(fichier.getAbsolutePath());
            } catch (FichierDonneesInvalides | IllegalArgumentException err) {
                labelMessageErr.setText(err.getMessage());
                labels.get(indexLabel).setStyle(EN_ROUGE);;
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
        
        if (fichiersImportes != null) {
            fichiersSelectionnes = new ArrayList<>(fichiersImportes);  
        }
        
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
        Boolean fichierNonVide,
                emplacementValide,
                fichiersDistincts,
                extensionValide,
                typeFichier;
        String text;
        int indexLabel;
        
        // Par defaut
        labelMessageErr.setText(" ");
        for (Label label : labels) {
            label.setStyle(EN_NOIR);
        }
        
        indexLabel = -1;
        fichierNonVide = true;
        for (File fichier : fichiersSelectionnes) {
            indexLabel ++;
            if (fichier == null || fichier.length() == 0) { 
                fichierNonVide = false;
                labels.get(indexLabel).setStyle(EN_ROUGE);
            }
        }
        
        extensionValide = true;
        indexLabel = -1;
        for (File fichier : fichiersSelectionnes) {
            try {
                indexLabel++;
                if (fichier != null || fichier.length() > 0) {
                    if (!GestionCSV.isFichierValide(fichier.getAbsolutePath())) {
                        extensionValide = false;
                        labels.get(indexLabel).setStyle(EN_ROUGE);
                    }
                }
            } catch (IOException err) {
                //On rentrera pas ici
            }
        }
        
        typeFichier = true;
        indexLabel = -1;
        for (File fichier : fichiersSelectionnes) {
            try {
                indexLabel++;
                if (fichier != null || fichier.length() > 0) {
                    if (!GestionCSV.isLettreIdentifiantValide(
                                GestionCSV.getTypeCSV(fichier.getAbsolutePath()))) {
                        typeFichier = false;
                        labels.get(indexLabel).setStyle(EN_ROUGE);
                    }
                }
            } catch (IOException err) {
                //On rentrera pas ici
            }
        }
        
        emplacementValide = true;
        for (int indiceLabel = 0; indiceLabel < labels.size(); indiceLabel++) {
             text = labels.get(indiceLabel).getText();
            if (text == null || text.isEmpty()) {
                emplacementValide = false;
            }
        }
    
        fichiersDistincts = true;
        for (int fichierSelect = 0; fichierSelect < fichiersSelectionnes.size()
                                  ; fichierSelect++) {
            for (int autreFichier = fichierSelect + 1; 
                    autreFichier < fichiersSelectionnes.size(); autreFichier++) {             
                if (fichiersSelectionnes.get(fichierSelect) != null 
                        && fichiersSelectionnes.get(autreFichier) != null 
                        && comparerFichier(fichiersSelectionnes.get(fichierSelect),
                                           fichiersSelectionnes.get(autreFichier))) {
                    fichiersDistincts = false;
                    labelMessageErr.setText("Erreur: fichiers identiques");
                    labels.get(fichierSelect).setStyle(EN_ROUGE);
                    labels.get(autreFichier).setStyle(EN_ROUGE);
                } 
            }
        }
    
        return fichierNonVide && extensionValide 
                && emplacementValide && typeFichier 
                && fichiersDistincts;
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
        if (fichier1.length() == fichier2.length()) {
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
        fichiersSelectionnesOrdre();
        exploiterDonnee();
        // Afficher une alerte avec les noms des fichiers sélectionnés
        Alert boiteInformationImportation
        = new Alert(Alert.AlertType.INFORMATION, FICHIER_IMPORTER_SUCCES, ButtonType.OK);

        boiteInformationImportation.setTitle("Importation");
        boiteInformationImportation.setHeaderText(
                "Fichiers importés avec succès");
        boiteInformationImportation.showAndWait();
        EchangeurDeVue.changerVue("accueilVue");
    }
    
    /**
     * Met dans l'ordre les fichiers selectionnes
     */
    private void fichiersSelectionnesOrdre() {
        char lettre;
        HashMap<Character, File> fichiersParLettre = new HashMap<>();

        for (File fichier : fichiersSelectionnes) {
            try {
                 lettre = GestionCSV.getTypeCSV(fichier.getAbsolutePath());
                fichiersParLettre.put(lettre, fichier);
            } catch (IOException e) {
                // Ignorer les erreurs ici
            }
        }

        fichiersSelectionnes.clear();
        for (char lettreOrdre : new char[] {'E', 'C', 'N', 'R'}) {
            if (fichiersParLettre.containsKey(lettreOrdre)) {
                fichiersSelectionnes.add(fichiersParLettre.get(lettreOrdre));
            }
        }
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

