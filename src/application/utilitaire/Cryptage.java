/*
 * Cryptage.java                           
 * 6 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
            
            fluxEcriture.writeObject(TraitementDonnees.getDonnees());
            
            fluxEcriture.close();
            
            FileInputStream fluxLecture
            = new FileInputStream(NOM_FICHIER_DONNEES);
            FileOutputStream flux2Ecriture
            = new FileOutputStream(NOM_FICHIER_CRYPTAGE);
            
            cryptage(cle, fluxLecture, flux2Ecriture);
            
            fluxLecture.close();
            flux2Ecriture.close();
            
            return NOM_FICHIER_CRYPTAGE;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param cle
     * @param fluxLecture
     * @param fluxEcriture
     * @throws IOException
     */
    private static void cryptage(String cle, FileInputStream fluxLecture,
                                 FileOutputStream fluxEcriture)
                                         throws IOException {
        
        byte[] cleBytes = cle.getBytes();
        int longueurCle = cleBytes.length;
        
        int indiceByte = 0;
        int byteData;
        while ((byteData = fluxLecture.read()) != -1) {
            
            // Opération XOR pour cryptage
            byte encryptedByte
            = (byte) (byteData ^ cleBytes[indiceByte % longueurCle]);
            fluxEcriture.write(encryptedByte);
            indiceByte++;
        }
    }
}
