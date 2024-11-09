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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import application.modele.Client;
import application.modele.Conferencier;
import application.modele.Donnees;
import application.modele.Employe;
import application.modele.Exposition;
import application.modele.Visite;

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
            
//            contenuFichier
//            = GestionFichiers.lireFichier(NOM_FICHIER_DONNEES_CRYPTEES);
//            contenuDecrypte = decrypter(contenuFichier, cle);
//            GestionFichiers.ecrireFichier(NOM_FICHIER_DECRYPTAGE,
//                                          contenuDecrypte);
//            Files.delete(Path.of(NOM_FICHIER_DONNEES_CRYPTEES));
            
            FileInputStream fluxLecture
            = new FileInputStream(NOM_FICHIER_DONNEES_CRYPTEES);
            FileOutputStream fluxEcriture
            = new FileOutputStream(NOM_FICHIER_DECRYPTAGE);
            
            decrypter(cle, fluxLecture, fluxEcriture);
            
            fluxLecture.close();
            fluxEcriture.close();
            
            ObjectInputStream flux2Lecture
            = new ObjectInputStream(new FileInputStream(NOM_FICHIER_DECRYPTAGE));
            
//            LinkedHashMap<String, Exposition> expositions
//            = new LinkedHashMap<>();
//            LinkedHashMap<String, Employe> employes
//            = new LinkedHashMap<>();
//            LinkedHashMap<String, Conferencier> conferenciers
//            = new LinkedHashMap<>();
//            ArrayList<Client> clients = new ArrayList<>();
//            LinkedHashMap<String, Visite> visites
//            = new LinkedHashMap<>();
//            
//            String[] tableauCles;
//            Exposition[] tableauExpositions;
//            Employe[] tableauEmployes;
//            Conferencier[] tableauConferenciers;
//            Client[] tableauClients;
//            Visite[] tableauVisites;
//            
//            tableauCles = (String[]) fluxLecture.readObject();
//            tableauExpositions = (Exposition[]) fluxLecture.readObject();
//            for (int indice = 0; indice < tableauExpositions.length; indice++) {
//                expositions.putLast(
//                        tableauCles[indice], tableauExpositions[indice]);
//            }
//            
//            tableauCles = (String[]) fluxLecture.readObject();
//            tableauEmployes = (Employe[]) fluxLecture.readObject();
//            for (int indice = 0; indice < tableauEmployes.length; indice++) {
//                employes.putLast(tableauCles[indice], tableauEmployes[indice]);
//            }
//            
//            tableauCles = (String[]) fluxLecture.readObject();
//            tableauConferenciers = (Conferencier[]) fluxLecture.readObject();
//            for (int indice = 0; indice < tableauConferenciers.length; indice++) {
//                conferenciers.putLast(
//                        tableauCles[indice], tableauConferenciers[indice]);
//            }
//            
//            tableauClients = (Client[]) fluxLecture.readObject();
//            clients.addAll(Arrays.asList(tableauClients));
//            
//            tableauCles = (String[]) fluxLecture.readObject();
//            tableauVisites = (Visite[]) fluxLecture.readObject();
//            for (int indice = 0; indice < tableauVisites.length; indice++) {
//                visites.putLast(tableauCles[indice], tableauVisites[indice]);
//            }
//            
//            // TODO Répartir en fonctions probablement
//            TraitementDonnees.setExpositions(expositions);
//            TraitementDonnees.setEmployes(employes);
//            TraitementDonnees.setConferenciers(conferenciers);
//            TraitementDonnees.setClients(clients);
//            TraitementDonnees.setVisites(visites);
            
            Donnees donnees = (Donnees) flux2Lecture.readObject();
            TraitementDonnees.setDonnees(donnees);
            
            fluxLecture.close();
            
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
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
