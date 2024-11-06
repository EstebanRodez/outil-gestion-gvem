/*
 * ExporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import application.EchangeurDeVue;

import application.utilitaire.Cryptage;
import application.utilitaire.CryptageVigenere;
import application.utilitaire.DecryptageVigenere;
import application.utilitaire.Serveur;
import application.utilitaire.TraitementDonnees;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;


/**
 * Contrôleur pour la gestion de l'exportation des données.
 * 
 * Cette classe permet à l'utilisateur de se connecter à un serveur 
 * en fournissant une adresse IP et un port. Elle gère également 
 * la validation des entrées et l'affichage des erreurs éventuelles.
 * 
 * @version 1.0
 */
public class ExporterControleur {

    @FXML
    private Button btnAide;

    @FXML
    private Button btnExporter;

    @FXML
    private Button btnRetour;

    @FXML
    private Label labelIp;

    @FXML
    private Label labelPort;

    private static final String FORMAT_NOM_SAUVEGARDE
    = "données %d-%d-%d %dh%dm%ds";
    
    private static final String NOM_DOSSIER_TEMPORAIRE
    = "Dossier données";
    private static final String NOM_DOSSIER_FINAL
    = "Dossier données cryptée";
    
    /**
     * Méthode d'initialisation appelée après le chargement de 
     * l'interface utilisateur.
     */
    @FXML
    public void initialize() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            labelIp.setText(socket.getLocalAddress().getHostAddress());
        } catch (Exception e) {
            Alert boiteIpInconnu = new Alert(Alert.AlertType.ERROR, 
                    "Impossible de connaître l'adresse IP de l'interface Ethernet", 
                    ButtonType.OK);
            boiteIpInconnu.setTitle("Erreur adresse IP inconnue");
            boiteIpInconnu.setHeaderText("Erreur adresse IP inconnue");
            boiteIpInconnu.showAndWait(); 
        }
    } 

    @FXML
    void btnExporterAction(ActionEvent event) {
        
        // Clé de chiffrement
        // TODO Améliorer la pop up
        // String cle = JOptionPane.showInputDialog("Entrez la clé de cryptage Vigenère :");
        
        // TODO Envoyer la clé à distance
        // Clé de chiffrement : 12
        String nomFichierCrypte;
        nomFichierCrypte = Cryptage.creerFichierDonnees("12");
        String[] fichiersCryptes = {nomFichierCrypte};
        
        Serveur.envoyerFichiers(65432, fichiersCryptes);
        try {
            Files.delete(Path.of(nomFichierCrypte));
        } catch (IOException e) {
            
        }
    }
    
    /**
     * Sauvegarde les données formatées des ArrayLists dans un fichier temporaire.
     */
    private void sauvegarderDonneesFormatees(Path fichierTemp) throws IOException {
        // Créez le répertoire temporaire s'il n'existe pas
        File dossierTemporaire = new File(NOM_DOSSIER_TEMPORAIRE);
        if (!dossierTemporaire.exists()) {
            if (!dossierTemporaire.mkdirs()) {  // Crée tous les répertoires nécessaires
                throw new IOException("Échec de la création du dossier temporaire : " + NOM_DOSSIER_TEMPORAIRE);
            }
        }

        // Créez le fichier dans le dossier temporaire
        File fichier = fichierTemp.toFile();
        if (!fichier.exists()) {
            if (!fichier.createNewFile()) {
                throw new IOException("Échec de la création du fichier temporaire : " + fichierTemp);
            }
        }

        try {
            ObjectOutputStream fluxEcriture = new ObjectOutputStream(new FileOutputStream(fichier));
            // Sauvegarde de chaque liste formatée dans le fichier
            fluxEcriture.writeObject(TraitementDonnees.getExpositions());
            fluxEcriture.writeObject(TraitementDonnees.getEmployes());
            fluxEcriture.writeObject(TraitementDonnees.getConferenciers());
            fluxEcriture.writeObject(TraitementDonnees.getClients());
            fluxEcriture.writeObject(TraitementDonnees.getVisites());
            fluxEcriture.close();

            Alert alertInfo = new Alert(AlertType.INFORMATION);
            alertInfo.setTitle("Information");
            alertInfo.setHeaderText(null);
            alertInfo.setContentText("Le fichier de sauvegarde de votre partie a bien été sauvegardé :\n" + fichierTemp.toString());
            alertInfo.show();
        } catch (IOException erreur) {
            erreur.printStackTrace();

            Alert alertErreur = new Alert(AlertType.ERROR);
            alertErreur.setTitle("Erreur");
            alertErreur.setHeaderText(null);
            alertErreur.setContentText(erreur.getMessage());
            alertErreur.show();
        }
    }
    
    /**
     * Lit le fichier temporaire, chiffre son contenu, et l'enregistre dans le fichier final.
     */
    private void crypterDonneesVigenere(Path fichierTemp, Path fichierFinal, String key) throws IOException {
        // Créez le répertoire pour le fichier final s'il n'existe pas
        File dossierFinal = new File(NOM_DOSSIER_FINAL);
        if (!dossierFinal.exists()) {
            if (!dossierFinal.mkdirs()) {  // Crée tous les répertoires nécessaires
                throw new IOException("Échec de la création du dossier final : " + NOM_DOSSIER_FINAL);
            }
        }

        // Créez le fichier final
        File fichierChiffre = fichierFinal.toFile();
        if (!fichierChiffre.exists()) {
            if (!fichierChiffre.createNewFile()) {
                throw new IOException("Échec de la création du fichier chiffré : " + fichierFinal);
            }
        }

        CryptageVigenere chiffreurVigenere = new CryptageVigenere(key);

        try (
            BufferedReader reader = new BufferedReader(new FileReader(fichierTemp.toFile()));
            FileWriter writer = new FileWriter(fichierChiffre)
        ) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String ligneChiffree = chiffreurVigenere.cryptage(ligne);  // Chiffrement de la ligne
                writer.write(ligneChiffree + "\n");  // Écriture de la ligne chiffrée dans le fichier de destination
            }
        }
    }
    
    /**
     * Déchiffre un fichier .dat crypté par Vigenère et enregistre le contenu déchiffré.
     *
     * @param fichier Le fichier .dat chiffré à déchiffrer.
     * @param gestionFichiers Utilitaire pour la gestion des fichiers.
     * @param key La clé de déchiffrement Vigenère.
     * @throws IOException En cas d'erreur de lecture ou d'écriture de fichier.
     */
//    private void decrypterFichierVigenere(File fichier, GestionDeFichier gestionFichiers, String key) throws IOException {
//        String contenuCrypte = gestionFichiers.readFile(fichier.getAbsolutePath());
//        if (contenuCrypte.isEmpty()) {
//            System.err.println("Le fichier est vide ou introuvable : " + fichier.getAbsolutePath());
//            return;
//        }
//
//        DecryptageVigenere dechiffreurVigenere = new DecryptageVigenere(key);
//        String contenuDecrypte = dechiffreurVigenere.decrypt(contenuCrypte);
//
//        String cheminFichierDecrypte = fichier.getParent() + File.separator + "decrypted_" 
//                + fichier.getName().replace(".dat", "_decrypted.dat");
//
//        gestionFichiers.writeFile(cheminFichierDecrypte, contenuDecrypte);
//        System.out.println("Fichier décrypté avec succès : " + cheminFichierDecrypte);
//
//        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Fichier décrypté avec succès : " + cheminFichierDecrypte, ButtonType.OK);
//        alert.setTitle("Déchiffrement réussi");
//        alert.setHeaderText(null);
//        alert.showAndWait();
//    }

    @FXML
    void btnAideAction(ActionEvent event) {
        AccueilControleur.lancerAide();
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }
}
