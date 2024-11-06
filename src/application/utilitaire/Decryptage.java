/*
 * Decryptage.java                           
 * 6 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class Decryptage {
    
    private static final String NOM_FICHIER_DONNEES_CRYPTEES
    = "Donnees_cryptees";
    
    private static final String NOM_FICHIER_DECRYPTAGE = "Donnees_decryptees";
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param cle
     * @return true
     */
    public static boolean decrypterFichierDonnees(String cle) {
        
        String contenuFichier,
               contenuDecrypte;
        
        try {
            
            contenuFichier
            = GestionFichiers.lireFichier(NOM_FICHIER_DONNEES_CRYPTEES);
            contenuDecrypte = decrypter(contenuFichier, cle);
            GestionFichiers.ecrireFichier(NOM_FICHIER_DECRYPTAGE,
                                          contenuDecrypte);
            Files.delete(Path.of(NOM_FICHIER_DONNEES_CRYPTEES));
            
            ObjectInputStream fluxLecture
            = new ObjectInputStream(new FileInputStream(NOM_FICHIER_DECRYPTAGE));
            
            fluxLecture.readObject();
            // TODO Relire les données et les insérer
            fluxLecture.close();
            
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        
    }
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param texte
     * @param cle
     * @return le texte décrypté
     */
    public static String decrypter(String texte, String cle) {

        StringBuilder textDecrypter = new StringBuilder();
        int keyLength = cle.length();

        for (int i = 0; i < texte.length(); i++) {
            char charEncrypted = texte.charAt(i);
            char charKey = cle.charAt(i % keyLength);
            char decryptedChar = (char) ((charEncrypted - charKey + 256) % 256);
            textDecrypter.append(decryptedChar);
        }

        return textDecrypter.toString();
    }
}
