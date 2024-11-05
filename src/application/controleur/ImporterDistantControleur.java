package application.controleur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.EchangeurDeVue;
import application.utilitaire.Client;
import application.utilitaire.DecryptageVigenere;
import application.utilitaire.GestionDeFichier;
import application.utilitaire.ImportationCSV;
import application.utilitaire.TraitementDonnees;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class ImporterDistantControleur {

    private static final String DOSSIER_IMPORTATION = "Dossier données cryptée";

    // Liste des fichiers .dat chiffrés attendus
    private static final String[] CHEMIN_FICHIER_DAT_RECU = {
        "expositions.dat", "employes.dat", "conferenciers.dat", 
        "visites.dat", "clients.dat"
    };

    /**
     * Décrypte un fichier chiffré par Vigenère et enregistre le contenu déchiffré.
     * 
     * @param fichier Le fichier chiffré à déchiffrer.
     * @param gestionFichiers L'outil de gestion des fichiers.
     * @param key La clé de déchiffrement.
     * @throws Exception Si une erreur survient durant le décryptage.
     */
    private void decrypterFichierVigenere(File fichier, GestionDeFichier gestionFichiers, String key) throws Exception {
        String contenuCrypte = gestionFichiers.readFile(fichier.getAbsolutePath());

        if (contenuCrypte == null || contenuCrypte.isEmpty()) {
            throw new IOException("Le contenu du fichier est vide ou introuvable : " + fichier.getAbsolutePath());
        }

        DecryptageVigenere dechiffreurVigenere = new DecryptageVigenere(key);
        String contenuDecrypte = dechiffreurVigenere.decrypt(contenuCrypte);

        // Créer un fichier de sortie pour les données décryptées
        String cheminFichierDecrypte = fichier.getParent() + File.separator + "dechiffre_" + fichier.getName();
        gestionFichiers.writeFile(cheminFichierDecrypte, contenuDecrypte);

        if (!new File(cheminFichierDecrypte).exists()) {
            throw new IOException("Erreur d'écriture du fichier déchiffré : " + cheminFichierDecrypte);
        }
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
            // Réception des fichiers depuis le serveur distant
            Client.recevoirFichiers(ipServeur, Integer.parseInt(port), CHEMIN_FICHIER_DAT_RECU, DOSSIER_IMPORTATION);

            File dossierImportes = new File(DOSSIER_IMPORTATION);
            if (!dossierImportes.exists()) {
                dossierImportes.mkdir();
            }

            ArrayList<File> fichiersSelectionnes = new ArrayList<>();
            for (String nomFichier : CHEMIN_FICHIER_DAT_RECU) {
                fichiersSelectionnes.add(new File(dossierImportes, nomFichier));
            }

            String key = javax.swing.JOptionPane.showInputDialog("Entrez la clé de décryptage Vigenère :");
            GestionDeFichier gestionFichiers = new GestionDeFichier();

            if (!fichiersSelectionnes.isEmpty()) {
                for (File fichier : fichiersSelectionnes) {
                    try {
                        if (fichier.exists()) {
                            decrypterFichierVigenere(fichier, gestionFichiers, key);
                            File fichierDecrypte = new File(fichier.getParent() + "/dechiffre_" + fichier.getName());

                            // Charger et traiter les données déchiffrées
                            ArrayList<String[]> donneesLignes = ImportationCSV.importerDonnees(fichierDecrypte.getAbsolutePath());

                            // Traiter chaque fichier pour remplir les listes de données
                            if (fichier.getName().contains("expositions")) {
                                TraitementDonnees.creerExpositions(donneesLignes);
                            } else if (fichier.getName().contains("employes")) {
                                TraitementDonnees.creerEmployes(donneesLignes);
                            } else if (fichier.getName().contains("conferenciers")) {
                                TraitementDonnees.creerConferenciers(donneesLignes);
                            } else if (fichier.getName().contains("visites")) {
                                TraitementDonnees.creerVisites(donneesLignes);
                            } else if (fichier.getName().contains("clients")) {
                                TraitementDonnees.creerClients(donneesLignes);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO: Améliorer la gestion des erreurs
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
