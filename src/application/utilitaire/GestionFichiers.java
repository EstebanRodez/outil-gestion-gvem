/*
 * GestionFichiers.java
 * 23 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TODO commenter le fonctionnement
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class GestionFichiers {
    
    // Méthode pour lire le contenu d'un fichier texte (ou binaire converti en chaîne)
    /**
     * TODO commenter le rôle de la méthode
     * @param chemin
     * @return le contenu du fichier
     * @throws IOException
     */
    public static String lireFichier(String chemin) throws IOException {
        
        StringBuilder contenu = new StringBuilder();
        try (BufferedReader fluxLecture
             = new BufferedReader(new FileReader(chemin))) {
            
            String ligne;
            ligne = fluxLecture.readLine();
            while (ligne != null) {
                contenu.append(ligne);
                ligne = fluxLecture.readLine();
                if (ligne != null) {
                    contenu.append("\n");
                }
            }
        }
        return contenu.toString();
    }

    // Méthode pour écrire le contenu dans un fichier (utilisée pour .csv et .bin)
    /**
     * TODO commenter le rôle de la méthode
     * @param chemin
     * @param contenu
     * @throws IOException
     */
    public static void ecrireFichier(String chemin, String contenu)
            throws IOException {
        
        try (BufferedWriter fluxEcriture
             = new BufferedWriter(new FileWriter(chemin))) {
            
            fluxEcriture.write(contenu);
        }
    }
}
