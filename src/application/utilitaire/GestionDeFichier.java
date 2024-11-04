/*
 * GestionDeFichier.java
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
 */
public class GestionDeFichier {
    
    // Méthode pour lire le contenu d'un fichier texte (ou binaire converti en chaîne)
    /**
     * TODO commenter le rôle de la méthode
     * @param path
     * @return le contenu du fichier
     * @throws IOException
     */
    public String readFile(String path) throws IOException {
        
        StringBuilder contenu = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contenu.append(line).append("\n");
            }
        }
        return contenu.toString();
    }

    // Méthode pour écrire le contenu dans un fichier (utilisée pour .csv et .bin)
    /**
     * TODO commenter le rôle de la méthode
     * @param path
     * @param contenu
     * @throws IOException
     */
    public void writeFile(String path, String contenu) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(contenu);
        }
    }
}
