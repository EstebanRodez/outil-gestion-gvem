package application.controleur;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.utilitaire.Client;
import application.utilitaire.DecryptageVigenere;
import application.utilitaire.GestionDeFichier;
import application.utilitaire.ImportationCSV;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ImporterDistantControleur {

    private Stage fenetreAppli;

    private static final String[] CHEMIN_FICHIER_CSV_RECU = {
        "C:\\programecole\\annee2\\SAES3\\saeMusee\\ressources\\fichierscsv\\vigenere_encrypted_expositions 28_08_24 17_26.bin",
        "C:\\programecole\\annee2\\SAES3\\saeMusee\\ressources\\fichierscsv\\vigenere_encrypted_employes 28_08_24 17_26.bin",
        "C:\\programecole\\annee2\\SAES3\\saeMusee\\ressources\\fichierscsv\\vigenere_encrypted_conferencier 28_08_24 17_26.bin", 
        "C:\\programecole\\annee2\\SAES3\\saeMusee\\ressources\\fichierscsv\\vigenere_encrypted_visites 28_08_24 17_26.bin"
    };

    public void setFenetreAppli(Stage fenetreAppli) {
        this.fenetreAppli = fenetreAppli;
    }

    private boolean isValidIPAddress(String ip) {
        String ipPattern = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$";
        if (!ip.matches(ipPattern)) return false;
        for (String segment : ip.split("\\.")) {
            int value = Integer.parseInt(segment);
            if (value < 0 || value > 255) return false;
        }
        return true;
    }

    private boolean isValidPort(String port) {
        try {
            int portValue = Integer.parseInt(port);
            return portValue >= 0 && portValue <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void decrypterFichierVigenere(File fichier, GestionDeFichier gestionFichiers, String key) throws Exception {
        String contenuCrypte = gestionFichiers.readFile(fichier.getAbsolutePath());
        if (contenuCrypte == null || contenuCrypte.isEmpty()) {
            throw new IOException("Le contenu du fichier est vide ou introuvable : " + fichier.getAbsolutePath());
        }
        
        DecryptageVigenere dechiffreurVigenere = new DecryptageVigenere(key);
        String contenuDecrypte = dechiffreurVigenere.decrypt(contenuCrypte);

        String cheminFichierDecrypte = fichier.getParent() + File.separator + "dechiffre_" + fichier.getName().replace(".bin", ".csv");
        gestionFichiers.writeFile(cheminFichierDecrypte, contenuDecrypte);

        // Vérification si le fichier a été écrit correctement
        if (!new File(cheminFichierDecrypte).exists()) {
            throw new IOException("Erreur d'écriture du fichier déchiffré : " + cheminFichierDecrypte);
        }
    }

    private void showInvalidInputAlert(String ipServeur, String port) {
        String message;
        String title;

        if (!isValidIPAddress(ipServeur) && !isValidPort(port)) {
            message = "Veuillez respecter la norme d'écriture d'une adresse IP (ex : 192.168.2.3) et d'un port (entre 0 et 65535).";
            title = "Erreur d'adresse IP et de port";
        } else if (!isValidIPAddress(ipServeur)) {
            message = "Veuillez respecter la norme d'écriture d'une adresse IP (ex : 192.168.2.3).";
            title = "Erreur sur l'adresse IP";
        } else {
            message = "Veuillez respecter la norme d'écriture d'un port (entre 0 et 65535).";
            title = "Erreur sur le port";
        }

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
        final String LIEN_REGLES = "https://docs.google.com/document/d/1wA1ytqySDYe1D-2ZL1M0mLKMvUmv9SCtS0uORFgoRIY/edit?usp=sharing";

        try {
            Desktop.getDesktop().browse(new URI(LIEN_REGLES));
        } catch (IOException | URISyntaxException e) {
            new Alert(Alert.AlertType.ERROR, "Impossible d'ouvrir le fichier d'aide", ButtonType.OK).showAndWait();
        }
    }

    @FXML
    void btnConnexionAction(ActionEvent event) throws IOException {
        String ipServeur = txtFieldIPServeur.getText().trim();
        String port = txtFieldPort.getText().trim();

        if (isValidIPAddress(ipServeur) && isValidPort(port)) {
            Client.recevoirFichiers(ipServeur, Integer.parseInt(port), CHEMIN_FICHIER_CSV_RECU);

            GestionDeFichier gestionFichiers = new GestionDeFichier();
            String key = javax.swing.JOptionPane.showInputDialog("Entrez la clé de décryptage Vigenère :");

            for (String cheminFichier : CHEMIN_FICHIER_CSV_RECU) {
                File fichier = new File(cheminFichier);

                try {
                    if (fichier.getName().endsWith(".bin")) {
                        decrypterFichierVigenere(fichier, gestionFichiers, key);
                        fichier = new File(fichier.getParent() + "/dechiffre_" + fichier.getName().replace(".bin", ".csv"));
                    }

                    List<String[]> donnees = ImportationCSV.importer(fichier.getAbsolutePath());
                    ImportationCSV.traitementDonnees(donnees);

                } catch (Exception e) {
                    Logger.getLogger(ImporterDistantControleur.class.getName()).log(Level.SEVERE, "Erreur lors du traitement du fichier : " + cheminFichier, e);
                }
            }
        } else {
            showInvalidInputAlert(ipServeur, port);
        }
    }

    @FXML
    void btnRetourAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/vue/importerVue.fxml"));
        Parent importerVue = loader.load();
        ImporterControleur controleur = loader.getController();
        controleur.setFenetreAppli(fenetreAppli);
        fenetreAppli.setScene(new Scene(importerVue));
    }
}
