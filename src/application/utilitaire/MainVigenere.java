/**
 * MainVigenere.java
 * 25 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

/**
 * TODO commenter le fonctionnement
 */
public class MainVigenere {
    
    /**
     * TODO commenter le rôle de la méthode
     * @param args
     */
    public static void main(String[] args) {
        String key = javax.swing.JOptionPane.showInputDialog("Entrez la clé de chiffrement Vigenère :");

        File[] fichiersSelectionnes = ouvrirNavigateurFichiers();

        if (fichiersSelectionnes != null && fichiersSelectionnes.length > 0) {
            GestionDeFichier gestionFichiers = new GestionDeFichier();

            for (File fichier : fichiersSelectionnes) {
                try {
                    // Déterminer si le fichier est crypté en vérifiant son extension
                    if (fichier.getName().endsWith(".bin")) {
                        // Décryptage si le fichier est en .bin
                        decrypterFichierVigenere(fichier, gestionFichiers, key);
                    } else {
                        // Cryptage si le fichier n'est pas en .bin
                        crypterFichierVigenere(fichier, gestionFichiers, key);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }

    private static File[] ouvrirNavigateurFichiers() {
        FileDialog fileDialog = new FileDialog((Frame) null, "Sélectionnez des fichiers CSV ou BIN", FileDialog.LOAD);
        fileDialog.setMultipleMode(true);
        fileDialog.setVisible(true);

        // Récupérer les fichiers sélectionnés
        File[] fichiersSelectionnes = fileDialog.getFiles();
        return (fichiersSelectionnes != null && fichiersSelectionnes.length > 0) ? fichiersSelectionnes : null;
    }

    private static void crypterFichierVigenere(File fichier, GestionDeFichier gestionFichiers, String key) throws Exception {
        System.out.println("Fichier détecté comme non crypté avec Vigenère : " + fichier.getName());

        String contenu = gestionFichiers.readFile(fichier.getAbsolutePath());
        CryptageVigenere chiffreurVigenere = new CryptageVigenere(key);
        String contenuCrypte = chiffreurVigenere.cryptage(contenu);

        // Chemin du fichier crypté en .bin
        String cheminFichierCrypte = fichier.getParent() + File.separator + "vigenere_encrypted_" + fichier.getName().replace(".csv", ".bin");
        gestionFichiers.writeFile(cheminFichierCrypte, contenuCrypte);

        System.out.println("Fichier crypté avec succès : " + cheminFichierCrypte);
    }

    private static void decrypterFichierVigenere(File fichier, GestionDeFichier gestionFichiers, String key) throws Exception {
        System.out.println("Fichier détecté comme crypté avec Vigenère : " + fichier.getName());

        String contenuCrypte = gestionFichiers.readFile(fichier.getAbsolutePath());
        DecryptageVigenere dechiffreurVigenere = new DecryptageVigenere(key);
        String contenuDecrypte = dechiffreurVigenere.decrypt(contenuCrypte);

        // Chemin du fichier décrypté en .csv
        String cheminFichierDecrypte = fichier.getParent() + File.separator + "vigenere_decrypted_" + fichier.getName().replace(".bin", ".csv");
        gestionFichiers.writeFile(cheminFichierDecrypte, contenuDecrypte);

        System.out.println("Fichier décrypté avec succès : " + cheminFichierDecrypte);
    }
}
