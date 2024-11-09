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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import application.modele.Client;
import application.modele.Conferencier;
import application.modele.Employe;
import application.modele.Exposition;
import application.modele.Visite;

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
            
            int indice;
            String[] tableauCles;
            
//            /*
//             * On transforme les LinkedHashMap en deux tableaux
//             * distincts, un pour les valeurs et l'autre pour les
//             * clés.
//             */
//            LinkedHashMap<String, Exposition> expositions
//            = TraitementDonnees.getExpositions();
//            Exposition[] tableauExpositions
//            = new Exposition[expositions.size()];
//            tableauCles = new String[expositions.size()];
//            indice = 0;
//            for (Map.Entry<String, Exposition> paire
//                 : TraitementDonnees.getExpositions().entrySet()) {
//                
//                tableauCles[indice] = paire.getKey();
//                tableauExpositions[indice] = paire.getValue();
//                indice++;
//            }
//            fluxEcriture.writeObject(tableauCles);
//            fluxEcriture.writeObject(tableauExpositions);
//            
//            LinkedHashMap<String, Employe> employes
//            = TraitementDonnees.getEmployes();
//            Employe[] tableauEmployes
//            = new Employe[employes.size()];
//            tableauCles = new String[employes.size()];
//            indice = 0;
//            for (Map.Entry<String, Employe> paire
//                 : employes.entrySet()) {
//                
//                tableauCles[indice] = paire.getKey();
//                tableauEmployes[indice] = paire.getValue();
//                indice++;
//            }
//            fluxEcriture.writeObject(tableauCles);
//            fluxEcriture.writeObject(tableauEmployes);
//            
//            LinkedHashMap<String, Conferencier> conferenciers
//            = TraitementDonnees.getConferenciers();
//            Conferencier[] tableauConferenciers
//            = new Conferencier[conferenciers.size()];
//            tableauCles = new String[conferenciers.size()];
//            indice = 0;
//            for (Map.Entry<String, Conferencier> paire
//                 : conferenciers.entrySet()) {
//                
//                tableauCles[indice] = paire.getKey();
//                tableauConferenciers[indice] = paire.getValue();
//                indice++;
//            }
//            fluxEcriture.writeObject(tableauCles);
//            fluxEcriture.writeObject(tableauConferenciers);
//            
//            ArrayList<Client> clients = TraitementDonnees.getClients();
//            fluxEcriture.writeObject(
//                    clients.toArray(new Client[clients.size()]));
//            
//            LinkedHashMap<String, Visite> visites
//            = TraitementDonnees.getVisites();
//            Visite[] tableauVisites
//            = new Visite[visites.size()];
//            tableauCles = new String[visites.size()];
//            indice = 0;
//            for (Map.Entry<String, Visite> paire
//                 : visites.entrySet()) {
//                
//                tableauCles[indice] = paire.getKey();
//                tableauVisites[indice] = paire.getValue();
//                indice++;
//            }
//            fluxEcriture.writeObject(tableauCles);
//            fluxEcriture.writeObject(tableauVisites);
            
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
