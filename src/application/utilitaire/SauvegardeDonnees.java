/*
 * SauvegardeDonnees.java                           
 * 19 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import application.modele.Donnees;

/**
 * Classe utilitaire pour la sauvegarde et la restauration des données 
 * de l'application via la sérialisation Java.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class SauvegardeDonnees {

    private static final String NOM_FICHIER_DONNEES = "donnees";

    /**
     * Sauvegarde les données de l'application dans un fichier sérialisé.
     * 
     * <p>Cette méthode utilise la sérialisation pour écrire l'objet 
     * {@link Donnees} dans un fichier nommé "donnees".</p>
     * 
     * @param donnees l'objet contenant les données à sauvegarder
     * @return true si les données ont été sauvegardées avec succès, false sinon
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    public static boolean sauvegarderDonnees(Donnees donnees) {
        
        ObjectOutputStream fluxEcriture;
        try {
            fluxEcriture
            = new ObjectOutputStream(new FileOutputStream(NOM_FICHIER_DONNEES));
            fluxEcriture.writeObject(donnees);
            
            fluxEcriture.close();
            return true;
        } catch (IOException e) {
            // Ne rien faire
        }   
        
        return false;
    }
    
    /**
     * Restaure les données de l'application à partir du fichier sérialisé.
     * 
     * <p>Cette méthode lit l'objet {@link Donnees} à partir du fichier 
     * "donnees" et le configure via
     * {@link TraitementDonnees#setDonnees(Donnees)}.
     * Si le fichier n'existe pas ou que sa lecture échoue, la méthode retourne 
     * false sans interrompre l'application.</p>
     * 
     * @return true si les données ont été restaurées avec succès, false sinon
     */
    public static boolean restaurerDonnees() {
        
        try {
            ObjectInputStream fluxLecture
            = new ObjectInputStream(new FileInputStream(NOM_FICHIER_DONNEES));
            
            Donnees donnees = (Donnees) fluxLecture.readObject();
            TraitementDonnees.setDonnees(donnees);
            
            fluxLecture.close();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            // Ne rien faire
        }
        
        return false;
    }
}
