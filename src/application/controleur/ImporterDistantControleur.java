package application.controleur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import application.EchangeurDeVue;
import application.utilitaire.Client;
import application.utilitaire.DecryptageVigenere;
import application.utilitaire.GestionDeFichier;
import application.utilitaire.ImportationCSV;
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
    
    private static final String DOSSIER_IMPORTATION = "fichiersImporteesRecu";
    
    private static final String[] CHEMIN_FICHIER_CSV_RECU = {
        "vigenere_encrypted_expositions 28_08_24 17_26.bin",
        "vigenere_encrypted_employes 28_08_24 17_26.bin",
        "vigenere_encrypted_conferencier 28_08_24 17_26.bin", 
        "vigenere_encrypted_visites 28_08_24 17_26.bin"
    };

    /**
     * Vérifie si une adresse IP est valide
     * @param ip Adresse IP sous forme de chaîne
     * @return true si l'adresse est valide, sinon false
     */
    private static boolean isValideAdresseIP(String ip) {
        // Expression régulière pour vérifier une adresse IPv4
        String ipPattern = 
            "^([0-9]{1,3}\\.){3}[0-9]{1,3}$";
        
        if (!ip.matches(ipPattern)) {
            return false;
        }

        String[] segments = ip.split("\\.");
        for (String segment : segments) {
            int value = Integer.parseInt(segment);
            if (value < 0 || value > 255) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Vérifie si le port est valide
     * @param port Chaîne représentant le port
     * @return true si le port est un entier valide entre 0 et 65535,
     *         sinon false
     */
    private static boolean isPortValide(String port) {
        return port.matches("(\\d){1,5}") && Integer.parseInt(port) >= 0
               && Integer.parseInt(port) <= 65535;
    }

    private void decrypterFichierVigenere(File fichier,
            GestionDeFichier gestionFichiers, String key) throws Exception {
        
        String contenuCrypte
        = gestionFichiers.readFile(fichier.getAbsolutePath());
        
        if (contenuCrypte == null || contenuCrypte.isEmpty()) {
            throw new IOException(
                    "Le contenu du fichier est vide ou introuvable : "
                    + fichier.getAbsolutePath());
        }
        
        DecryptageVigenere dechiffreurVigenere = new DecryptageVigenere(key);
        String contenuDecrypte = dechiffreurVigenere.decrypt(contenuCrypte);

        String cheminFichierDecrypte
        = fichier.getParent() + File.separator + "dechiffre_"
          + fichier.getName().replace(".bin", ".csv");
        gestionFichiers.writeFile(cheminFichierDecrypte, contenuDecrypte);

        // Vérification si le fichier a été écrit correctement
        if (!new File(cheminFichierDecrypte).exists()) {
            throw new IOException("Erreur d'écriture du fichier déchiffré : "
                                  + cheminFichierDecrypte);
        }
    }

    private void showInvalidInputAlert(String ipServeur, String port) {
        
        String message;
        String title;
        if (!isValideAdresseIP(ipServeur) && !isPortValide(port)) {
            message = "Veuillez respecter la norme d'écriture d'une adresse IP "
                      + "(ex : 192.168.2.3) et d'un port (entre 0 et 65535).";
            title = "Erreur d'adresse IP et de port";
        } else if (!isValideAdresseIP(ipServeur)) {
            message = "Veuillez respecter la norme d'écriture d'une adresse IP "
                      + "(ex : 192.168.2.3).";
            title = "Erreur sur l'adresse IP";
        } else {
            message = "Veuillez respecter la norme d'écriture d'un port (entre "
                      + "0 et 65535).";
            title = "Erreur sur le port";
        }
        
        //TODO ajouter un header et un title
        new Alert(Alert.AlertType.ERROR, message, ButtonType.OK).showAndWait();
    }

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

    @FXML
    void btnAideAction(ActionEvent event) {
        AccueilControleur.lancerAide();
    }

    @FXML
    void btnConnexionAction(ActionEvent event) {
        
        String ipServeur = txtFieldIPServeur.getText().trim();
        String port = txtFieldPort.getText().trim();

        if (isValideAdresseIP(ipServeur) && isPortValide(port)) {
        	
            Client.recevoirFichiers(ipServeur, Integer.parseInt(port),
                                    CHEMIN_FICHIER_CSV_RECU,
                                    DOSSIER_IMPORTATION);

            GestionDeFichier gestionFichiers = new GestionDeFichier();
            String key
            = javax.swing.JOptionPane.showInputDialog(
                    "Entrez la clé de décryptage Vigenère :");
                
            // Créer le dossier d'importation s'il n'existe pas
            File dossierImportes = new File(DOSSIER_IMPORTATION);
            if (!dossierImportes.exists()) {
                dossierImportes.mkdir(); // Créer le dossier
            }
            
            ArrayList<File> fichiersSelectionnes = new ArrayList<>();
            for (String nomFichier : CHEMIN_FICHIER_CSV_RECU) {
                fichiersSelectionnes.add(new File(dossierImportes, nomFichier));
            }

            // Réception des fichiers depuis le serveur
            Client.recevoirFichiers(ipServeur, Integer.parseInt(port),
                                    CHEMIN_FICHIER_CSV_RECU,
                                    DOSSIER_IMPORTATION);

            // Vérification si les fichiers ont été sélectionnés et traitement
            if (!fichiersSelectionnes.isEmpty()) {

                StringBuilder nomsFichiers = new StringBuilder();
                for (File fichier : fichiersSelectionnes) {
                    try {
                        if (fichier.getName().endsWith(".bin")) {
                            decrypterFichierVigenere(fichier, gestionFichiers,
                                                     key);
                            fichier
                            = new File(fichier.getParent() + "/dechiffre_"
                                       + fichier.getName().replace(".bin",
                                                                   ".csv"));
                        }
                        
                        ImportationCSV.importerDonnees(
                                fichier.getAbsolutePath());
                    } catch (Exception e) {
                    }
                }
            }
        } else {
            showInvalidInputAlert(ipServeur, port);
        }
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("importerVue");
    }
}
