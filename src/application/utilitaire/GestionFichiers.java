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
 * Classe utilitaire qui permet d'écrire et de lire des fichiers
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class GestionFichiers {
    
    /**
     * 
     * Permet de lire le contenu d'un fichier.
     * 
     * @param chemin le chemin du fichier
     * @return le contenu du fichier
     * @throws IOException si une erreur survient à la lecture
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

    /**
     * Permet d'écrire du contenu dans un fichier.
     * 
     * @param chemin le chemin du fichier
     * @param contenu le contenu à écrire
     * @throws IOException si une erreur survient à l'écriture
     */
    public static void ecrireFichier(String chemin, String contenu)
            throws IOException {
        
        try (BufferedWriter fluxEcriture
             = new BufferedWriter(new FileWriter(chemin))) {
            
            fluxEcriture.write(contenu);
        }
    }
}
