package application.utilitaire;

import javax.swing.*;
import java.io.File;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Demande à l'utilisateur de sélectionner des fichiers CSV
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sélectionnez des fichiers CSV à crypter");
        fileChooser.setMultiSelectionEnabled(true); // Permet la sélection multiple
        
        // Ouvre le sélecteur de fichiers et attend la sélection
        int userSelection = fileChooser.showOpenDialog(null);
        
        // Vérifie si des fichiers ont été sélectionnés
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File[] inputFiles = fileChooser.getSelectedFiles();
            
            // Générer un décalage aléatoire entre 1 et 25
            Random random = new Random();
            int shift = random.nextInt(25) + 1; // Décalage entre 1 et 25
            
            // Afficher le décalage utilisé
            System.out.println("Décalage utilisé pour le cryptage : " + shift);
            
            // Instance de la classe de gestion de fichier
            GestionDeFichier fileHandler = new GestionDeFichier();
            CryptageCesar cipher = new CryptageCesar(shift); // Instance du cryptage

            for (File inputFile : inputFiles) {
                try {
                    // Lire le contenu du fichier CSV
                    String content = fileHandler.readFile(inputFile.getAbsolutePath());
                    
                    // Crypter le contenu
                    String encryptedData = cipher.encrypt(content);
                    
                    // Déterminer le chemin du fichier de sortie
                    String outputFilePath = inputFile.getParent() + File.separator + 
                                            "encrypted_" + inputFile.getName(); // Prefixe "encrypted_"
                    
                    // Écrire le contenu crypté dans un nouveau fichier
                    fileHandler.writeFile(outputFilePath, encryptedData);
                    
                    System.out.println("Fichier crypté avec succès : " + outputFilePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }
}
