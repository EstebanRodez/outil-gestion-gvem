package application.utilitaire;

import java.io.*;

public class GestionDeFichier {
    
    // Méthode pour lire le contenu d'un fichier texte (ou binaire converti en chaîne)
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
    public void writeFile(String path, String contenu) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(contenu);
        }
    }
}
