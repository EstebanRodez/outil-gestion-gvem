package application.utilitaire;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GestionDeFichier {
	/**
	 * La classe gestion de fichier pour cryptage permet de crypter les données des fichiers CSV
	 * que l'on souhaite envoyer
	 * @author Romain Augé
	 * @version 1.0
	 */
    // Méthode pour lire un fichier et retourner son contenu en tant que chaîne
    public String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(new File(filePath).toPath()));
    }

    // Méthode pour écrire des données dans un fichier
    public void writeFile(String filePath, String data) throws IOException {
        Files.write(new File(filePath).toPath(), data.getBytes());
    }
}


