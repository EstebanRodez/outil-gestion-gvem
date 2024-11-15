/*
 * MenuImportationControleur.java                           
 * 5 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.File;

import application.EchangeurDeVue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;


/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class MenuImportationControleur {
    
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
       
        if (!isFichierValide()) {
            btnValider.setDisable(true);
        }
    }
    
    /**
     * Vérifie si au moins un fichier a été sélectionné.
     * @return true si au moins un champ de fichier est rempli, sinon false.
     */
    private boolean isFichierValide() {
        return (labelEmplacementUn.getText() != null &&
                !labelEmplacementUn.getText().isEmpty()) ||
               (labelEmplacementDeux.getText() != null &&
                !labelEmplacementDeux.getText().isEmpty()) ||
               (labelEmplacementTrois.getText() != null &&
                !labelEmplacementTrois.getText().isEmpty()) ||
               (labelEmplacementQuatre.getText() != null &&
                !labelEmplacementQuatre.getText().isEmpty());
    }

    @FXML
    void btnRetour(ActionEvent event) {
        EchangeurDeVue.changerVue("importerVue");
    }
    
    /**
     * Supprime le nom du fichier du champ correspondant
     * et désactive "Valider" si aucun fichier n'est sélectionné.
     */
    @FXML
    void btnSupprimer(ActionEvent event) {
        if (event.getSource() == btnSupprimerUn) {
            labelEmplacementUn.setText(null);
        } else if (event.getSource() == btnSupprimerDeux) {
            labelEmplacementDeux.setText(null);
        } else if (event.getSource() == btnSupprimerTrois) {
            labelEmplacementTrois.setText(null);
        } else if (event.getSource() == btnSupprimerQuatre) {
            labelEmplacementQuatre.setText(null);
        }

        btnValider.setDisable(!isFichierValide());
    }
    
    @FXML
    void btnParcourir(ActionEvent event) {
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
                labelEmplacementUn.setText(nomFichier);
            } else if (event.getSource() == btnParcourirDeux) {
                labelEmplacementDeux.setText(nomFichier);
            } else if (event.getSource() == btnParcourirTrois) {
                labelEmplacementTrois.setText(nomFichier);
            } else if (event.getSource() == btnParcourirQuatre) {
                labelEmplacementQuatre.setText(nomFichier);
            }

            btnValider.setDisable(!isFichierValide());
        }
    }
    
    
}

