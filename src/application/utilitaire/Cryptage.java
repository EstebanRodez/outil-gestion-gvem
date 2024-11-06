/*
 * Cryptage.java                           
 * 6 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class Cryptage {
    
    private static String NOM_FICHIER_DONNEES = "Donnees";
    
    private static String NOM_FICHIER_CRYPTAGE = "Donnees_cryptees";
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param cle 
     * @return le chemin du fichier de données
     */
    public static String creerFichierDonnees(String cle) {
        
        try {
            
            ObjectOutputStream fluxEcriture
            = new ObjectOutputStream(
                    new FileOutputStream(NOM_FICHIER_DONNEES));
            
            fluxEcriture.writeObject(TraitementDonnees.getExpositions());
            fluxEcriture.writeObject(TraitementDonnees.getEmployes());
            fluxEcriture.writeObject(TraitementDonnees.getConferenciers());
            fluxEcriture.writeObject(TraitementDonnees.getClients());
            fluxEcriture.writeObject(TraitementDonnees.getVisites());
            fluxEcriture.close();
            
            String contenuFichier,
                   contenuCrypte;
            contenuFichier = GestionFichiers.lireFichier(NOM_FICHIER_DONNEES);
            contenuCrypte = cryptage(contenuFichier, cle);
            GestionFichiers.ecrireFichier(NOM_FICHIER_CRYPTAGE, contenuCrypte);
            Files.delete(Path.of(NOM_FICHIER_DONNEES));
            
            return NOM_FICHIER_CRYPTAGE;
        } catch (IOException e) {
            return null;
        }
        
    }
    
    private static String cryptage(String texte, String cle) {
        StringBuilder textCrypter = new StringBuilder();
        int keyLength = cle.length();

        for (int i = 0; i < texte.length(); i++) {
            char charText = texte.charAt(i);
            char charKey = cle.charAt(i % keyLength);
            char encryptedChar = (char) ((charText + charKey) % 256);
            textCrypter.append(encryptedChar);
        }

        return textCrypter.toString();
    }
}
