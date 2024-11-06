/*
 * MenuImportationControleur.java                           
 * 5 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.EchangeurDeVue;
import application.utilitaire.FichierDonneesInvalides;
import application.utilitaire.ImportationCSV;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    
    /** Listes des fichiers selectionnees */
    private List<File> fichiersSelectionnes = new ArrayList<>();
    
    @FXML
    private Label labelEmplacementUn, labelEmplacementDeux, 
                  labelEmplacementTrois, labelEmplacementQuatre, 
                  labelMessageErr;

    @FXML
    private Button btnParcourirUn, btnParcourirDeux, btnParcourirTrois,
                   btnParcourirQuatre,btnSupprimerUn, btnSupprimerDeux, 
                   btnSupprimerTrois, btnSupprimerQuatre, btnValider, btnRetour;
    /**
     * Initialisation de la vue
     */
    @FXML
    public void initialize() {
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
            = new FileChooser.
                      ExtensionFilter("Fichiers données (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        
        fichiersImportes
            = fileChooser.showOpenMultipleDialog(EchangeurDeVue.
                                                    getFenetreAppli());
        
        fichiersSelectionnes = new ArrayList<>(fichiersImportes);
    }
    
    /**
     * Met a jour les labels pour afficher les fichiers selectionnes
     */
    private void mettreAJourLabelsFichiers() {
        String nomFichier;
        File fichier;
        List<Label> labels = Arrays.asList(labelEmplacementUn, 
                                           labelEmplacementDeux, 
                                           labelEmplacementTrois, 
                                           labelEmplacementQuatre);

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
        return (labelEmplacementUn.getText() != null &&
                !labelEmplacementUn.getText().isEmpty()) &&
               (labelEmplacementDeux.getText() != null &&
                !labelEmplacementDeux.getText().isEmpty()) &&
               (labelEmplacementTrois.getText() != null &&
                !labelEmplacementTrois.getText().isEmpty()) &&
               (labelEmplacementQuatre.getText() != null &&
                !labelEmplacementQuatre.getText().isEmpty());
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("importerVue");
    }
    
    @FXML
    void btnValiderAction(ActionEvent event) {
        exploiterDonnee();
    }
    
    /**
     * Supprime le nom du fichier du champ correspondant
     * et désactive "Valider" si aucun fichier n'est sélectionné.
     */
    @FXML
    void btnSupprimerAction(ActionEvent event) {
        
        if (event.getSource() == btnSupprimerUn) {
            fichiersSelectionnes.set(0,null);
            labelEmplacementUn.setText(null);
        } else if (event.getSource() == btnSupprimerDeux) {
            fichiersSelectionnes.set(1,null);
            labelEmplacementDeux.setText(null);
        } else if (event.getSource() == btnSupprimerTrois) {
            fichiersSelectionnes.set(2,null);
            labelEmplacementTrois.setText(null);
        } else if (event.getSource() == btnSupprimerQuatre) {
            fichiersSelectionnes.set(3,null);
            labelEmplacementQuatre.setText(null);
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

            if (event.getSource() == btnParcourirUn) {
                fichiersSelectionnes.set(0,fichier);
                labelEmplacementUn.setText(nomFichier);
            } else if (event.getSource() == btnParcourirDeux) {
                fichiersSelectionnes.set(1,fichier);
                labelEmplacementDeux.setText(nomFichier);
            } else if (event.getSource() == btnParcourirTrois) {
                fichiersSelectionnes.set(2,fichier);
                labelEmplacementTrois.setText(nomFichier);
            } else if (event.getSource() == btnParcourirQuatre) {
                fichiersSelectionnes.set(3,fichier);
                labelEmplacementQuatre.setText(nomFichier);
            }

            btnValider.setDisable(!isFichierValide());
            mettreAJourLabelsFichiers(); 
        }
    } 
}

