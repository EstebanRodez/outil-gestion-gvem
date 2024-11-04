package application.utilitaire;

import java.io.File;
import java.util.Random;

import javax.swing.JFileChooser;

public class Main {
    /**
     * Classe principale qui détecte automatiquement si plusieurs fichiers sont cryptés ou non,
     * et les décrypte ou crypte en conséquence.
     * @author Romain Augé
     * @version 1.3
     */
    public static void main(String[] args) {
        // Demande à l'utilisateur de sélectionner plusieurs fichiers CSV
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sélectionnez des fichiers CSV à crypter ou décrypter");
        fileChooser.setMultiSelectionEnabled(true); // Permet la sélection multiple

        // Ouvre le sélecteur de fichiers et attend la sélection
        int userSelection = fileChooser.showOpenDialog(null);

        // Vérifie si des fichiers ont été sélectionnés
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File[] fichiersSelectionnes = fileChooser.getSelectedFiles(); // Tous les fichiers sélectionnés
            GestionDeFichier gestionFichiers = new GestionDeFichier();

            // Traitement de chaque fichier sélectionné
            for (File fichier : fichiersSelectionnes) {
                try {
                    // Déterminer le chemin du fichier de décalage correspondant
                    String cheminFichierDecalage = fichier.getParent() + File.separator + "shift_" + fichier.getName() + ".shift";
                    File fichierDecalage = new File(cheminFichierDecalage);

                    // Si le fichier de décalage existe, le fichier est déjà crypté
                    if (fichierDecalage.exists()) {
                        // Décryptage
                        decrypterFichier(fichier, gestionFichiers, fichierDecalage);
                    } else {
                        // Cryptage
                        crypterFichier(fichier, gestionFichiers);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }

    /**
     * Crypte un fichier et génère un fichier de décalage.
     * @param fichier Le fichier à crypter
     * @param gestionFichiers L'objet GestionDeFichier pour lire et écrire les fichiers
     */
    private static void crypterFichier(File fichier, GestionDeFichier gestionFichiers) throws Exception {
        System.out.println("Fichier détecté comme non crypté : " + fichier.getName());

        // Générer un décalage aléatoire entre 1 et 10
        Random random = new Random();
        int decalage = random.nextInt(10) + 1; // Décalage aléatoire entre 1 et 10

        // Lire le contenu du fichier CSV
        String contenu = gestionFichiers.readFile(fichier.getAbsolutePath());

        // Crypter le contenu
        CryptageCesar chiffreur = new CryptageCesar(decalage);
        String contenuCrypte = chiffreur.encrypt(contenu);

        // Sauvegarder le fichier crypté et le fichier de décalage
        String cheminFichierCrypte = fichier.getParent() + File.separator + "encrypted_" + fichier.getName();
        String cheminFichierDecalage = fichier.getParent() + File.separator + "shift_" + fichier.getName() + ".shift";
        
        gestionFichiers.writeFile(cheminFichierCrypte, contenuCrypte);
        gestionFichiers.writeFile(cheminFichierDecalage, Integer.toString(decalage));

        System.out.println("Fichier crypté avec succès : " + cheminFichierCrypte);
        System.out.println("Décalage stocké dans le fichier : " + cheminFichierDecalage);
    }

    /**
     * Décrypte un fichier crypté et supprime le fichier de décalage.
     * @param fichier Le fichier à décrypter
     * @param gestionFichiers L'objet GestionDeFichier pour lire et écrire les fichiers
     * @param fichierDecalage Le fichier contenant le décalage de cryptage
     */
    private static void decrypterFichier(File fichier, GestionDeFichier gestionFichiers, File fichierDecalage) throws Exception {
        System.out.println("Fichier détecté comme crypté : " + fichier.getName());

        // Lire le décalage depuis le fichier .shift
        String contenuDecalage = gestionFichiers.readFile(fichierDecalage.getAbsolutePath());
        int decalage = Integer.parseInt(contenuDecalage.trim()); // Convertir le contenu en entier

        // Lire le contenu crypté du fichier
        String contenuCrypte = gestionFichiers.readFile(fichier.getAbsolutePath());

        // Décrypter le contenu
        DecryptageCesar dechiffreur = new DecryptageCesar(decalage);
        String contenuDecrypte = dechiffreur.decrypt(contenuCrypte);

        // Sauvegarder le fichier décrypté
        String cheminFichierDecrypte = fichier.getParent() + File.separator + "decrypted_" + fichier.getName();
        gestionFichiers.writeFile(cheminFichierDecrypte, contenuDecrypte);

        // Supprimer le fichier de décalage
        if (fichierDecalage.delete()) {
            System.out.println("Le fichier de décalage a été supprimé.");
        }

        System.out.println("Fichier décrypté avec succès : " + cheminFichierDecrypte);
    }
}
