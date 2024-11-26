/*
 * ImporterDistantControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import application.EchangeurDeVue;
import application.utilitaire.EchangeDiffieHellman;
import application.utilitaire.FichierDonneesInvalidesException;
import application.utilitaire.GenerationDonneeSecreteException;
import application.utilitaire.ImportationCSV;
import application.utilitaire.Reseau;
import application.utilitaire.SauvegardeDonnees;
import application.utilitaire.TraitementDonnees;
import application.utilitaire.Vigenere;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * Contrôleur pour la gestion de l'importation de données à distance.
 * 
 * Cette classe permet à l'utilisateur de spécifier une adresse IP et
 * un port pour se connecter à un serveur distant. Elle inclut
 * également des fonctionnalités pour afficher des règles
 * d'utilisation via un lien.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class ImporterDistantControleur {

    @FXML
    private Button btnAide;

    @FXML
    private Button btnConnexion;

    @FXML
    private Button btnRetour;

    @FXML
    private TextField txtFieldIPServeur;

    @FXML
    private TextField txtFieldPort;
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    @FXML
    public void initialize() {
        txtFieldPort.setText(Integer.toString(Reseau.getPortExportation()));
    }

    @FXML
    void btnAideAction(ActionEvent event) {
    	// Appel de la méthode lancerAide de AccueilControleur avec un indice spécifique
    	// Utilise l'indice 9 pour ouvrir un lien d'aide correspondant
    	// à la réference de la partit des conferenciers dans données calculées
        AccueilControleur.lancerAide(9);;
    }

    @FXML
    void btnConnexionAction(ActionEvent event) {
        
        String ipServeur = txtFieldIPServeur.getText().trim();
        String portServeur = txtFieldPort.getText().trim();
        boolean champsValides = true;
        
        if (!Reseau.isAdresseIPValide(ipServeur)) {
            champsValides = false;
            lancerErreurAdresseIPInvalide();
        } else if (!Reseau.isAdresseIPDisponible(ipServeur)) {
            champsValides = false;
            lancerErreurAdresseIPIndisponible();
        } else if (!Reseau.isPortValide(portServeur)) {
            champsValides = false;
            lancerErreurPortInvalide();
        }
        
        /* Le port d'exportation est forcément valide donc on l'enregistre */
        Reseau.setPortExportation(Integer.parseInt(portServeur));
        
        boolean cleSecreteRecue = false;
        int cleSecrete = -1;
        if (champsValides) {
            
            try {
                cleSecrete
                = EchangeDiffieHellman.genererDonneeSecreteBob(ipServeur);
                cleSecreteRecue = true;
            } catch (GenerationDonneeSecreteException erreur) {
                lancerErreurGenerationDonneeSecrete(erreur.getMessage());
            }
        }
            
        if (cleSecreteRecue) {
            
            String[] nomFichierEnvois = Vigenere.getNomsFichiersEnvois();
            String[] nomFichierDonnees = Vigenere.getNomsFichiersDonnees();
            
            Reseau.recevoirFichiers(ipServeur, Integer.parseInt(portServeur),
                                    nomFichierEnvois, null);
            
            for (int indiceNomFichier = 0;
                 indiceNomFichier < nomFichierEnvois.length;
                 indiceNomFichier++) {
                
                String cleChiffrement
                = Vigenere.genererCleChiffrement(cleSecrete);
                
                Vigenere.decrypter(nomFichierEnvois[indiceNomFichier],
                                   cleChiffrement);
                
                try {
                    ImportationCSV.importerDonnees(
                            nomFichierDonnees[indiceNomFichier]);
                    SauvegardeDonnees.sauvegarderDonnees(
                            TraitementDonnees.getDonnees());
                    EchangeurDeVue.changerVue("importerDistantValideVue");
                } catch (FichierDonneesInvalidesException e) {
                    lancerErreurFichiersImporteesInvalides();
                }
                
            }
            
            Vigenere.supprimerFichiersEnvois();
            Vigenere.supprimerFichiersDonnees();
        }
        
        EchangeDiffieHellman.supprimerFichiersAlice();
        EchangeDiffieHellman.supprimerFichiersBob();
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    private static void lancerErreurFichiersImporteesInvalides() {
        
        Alert boiteErreurGenerationDonneeSecrete
        = new Alert(Alert.AlertType.ERROR, 
                    "Les fichiers importées contiennent des données "
                    + "incorrectes. Importation annulée", ButtonType.OK);
        boiteErreurGenerationDonneeSecrete.setTitle(
                "Erreur Fichiers importées");
        boiteErreurGenerationDonneeSecrete.setHeaderText(
                "Erreur dans les fichiers importées");
        boiteErreurGenerationDonneeSecrete.showAndWait();
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param message
     */
    private static void lancerErreurGenerationDonneeSecrete(String message) {
        
        Alert boiteErreurGenerationDonneeSecrete
        = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        boiteErreurGenerationDonneeSecrete.setTitle(
                "Erreur Lancement Importation");
        boiteErreurGenerationDonneeSecrete.setHeaderText(
                "Erreur dans le processus de l'importation");
        boiteErreurGenerationDonneeSecrete.showAndWait();
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    private static void lancerErreurPortInvalide() {
        
        Alert boiteErreurAdressePortInvalide
        = new Alert(Alert.AlertType.ERROR,
                    "Le port que vous avez saisi est invalide. Il doit être "
                    + "compris entre 1 et 65535", ButtonType.OK);
        boiteErreurAdressePortInvalide.setTitle("Erreur Port");
        boiteErreurAdressePortInvalide.setHeaderText(
                "Erreur dans la saisie du port");
        boiteErreurAdressePortInvalide.showAndWait();
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    private static void lancerErreurAdresseIPIndisponible() {
        
        Alert boiteErreurAdresseIPIndisponible
        = new Alert(Alert.AlertType.ERROR,
                    "L'adresse IP que vous avez saisie, n'est pas disponible",
                    ButtonType.OK);
        boiteErreurAdresseIPIndisponible.setTitle("Erreur Adresse IP");
        boiteErreurAdresseIPIndisponible.setHeaderText(
                "Erreur dans la saisie de l'adresse IP");
        boiteErreurAdresseIPIndisponible.showAndWait();
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    private static void lancerErreurAdresseIPInvalide() {
        
        Alert boiteErreurAdresseIPInvalide
        = new Alert(Alert.AlertType.ERROR,
                    "L'adresse IP que vous avez saisie, n'a pas un format "
                    + "valide", ButtonType.OK);
        boiteErreurAdresseIPInvalide.setTitle("Erreur Adresse IP");
        boiteErreurAdresseIPInvalide.setHeaderText(
                "Erreur dans la saisie de l'adresse IP");
        boiteErreurAdresseIPInvalide.showAndWait();
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("importerVue");
    }
    
}
