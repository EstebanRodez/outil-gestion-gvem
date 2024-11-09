/*
 * Decryptage.java                           
 * 6 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import application.modele.Donnees;

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
     * @throws DecryptageException 
     */
    public static boolean decrypterFichierDonnees(String cle)
            throws DecryptageException {
        
        try {
                   
            FileInputStream fluxLecture
            = new FileInputStream(NOM_FICHIER_DONNEES_CRYPTEES);
            FileOutputStream fluxEcriture
            = new FileOutputStream(NOM_FICHIER_DECRYPTAGE);
            
            decrypter(cle, fluxLecture, fluxEcriture);
            
            fluxLecture.close();
            fluxEcriture.close();
            
            ObjectInputStream flux2Lecture
            = new ObjectInputStream(new FileInputStream(NOM_FICHIER_DECRYPTAGE));
            
            Donnees donnees = (Donnees) flux2Lecture.readObject();
            TraitementDonnees.setDonnees(donnees);
            
            flux2Lecture.close();
            
            return true;
        } catch (IOException | ClassNotFoundException e) {
            
            throw new DecryptageException();
        }
        
    }
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param cle
     * @param fluxLecture 
     * @param fluxEcriture 
     * @throws IOException 
     */
    private static void decrypter(String cle, FileInputStream fluxLecture,
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
