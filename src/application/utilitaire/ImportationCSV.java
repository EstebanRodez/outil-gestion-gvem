/*
 * ImportationCSV.java                           
 * 16 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


/**
 * La classe ImportationCSV permet d'importer et de verifier des
 * données à partir d'un fichier CSV.
 * @author Ayoub Laluti
 * @author Esteban Vroemen
 * @version 2.0
 */
public class ImportationCSV {

    private static final String ERREUR_CONTENU_FICHIER 
    = "Erreur: Le fichier csv n'est ni des expositions, ni des visites, "
      + "ni des employés et ni des conferenciers.";
    
    private static final String FORMAT_IDENTIFIANT = "^[ERCN](\\d){6}$";
    
    private static final String FORMAT_DATE_FR
    = "^(\\d{2})\\/(\\d{2})\\/(\\d{4})$";
    
    private static final Character[] LETTRES_IDENTIFIANT_VALIDES
    = {'E', 'R', 'C', 'N'};
    
    // TODO faire des messages d'erreurs détaillés pour les exceptions
    
    /**
     * Importe les données d'un fichier CSV.
     * 
     * @param cheminFichier le chemin du fichier
     * @throws IllegalArgumentException si le fichier n'est pas
     *                                  valide ou n'est pas en
     *                                  extension CSV
     * @throws IllegalArgumentException
     * @throws IllegalArgumentException
     * @throws FichierDonneesInvalides si les données du fichier sont
     *                                 invalides
     */
    public static void importerDonnees(String cheminFichier)
            throws FichierDonneesInvalides {
        
        try {
            
            FileInputStream fileInputStream
            = new FileInputStream(cheminFichier);
            InputStreamReader inputStreamReader
            = new InputStreamReader(fileInputStream,
                                    StandardCharsets.ISO_8859_1);
            BufferedReader fichierCSV = new BufferedReader(inputStreamReader);
            if (isFichierValide(cheminFichier)) {
                
                parcourirFichier(fichierCSV);
            } else {
                
                fichierCSV.close();  
                throw new IllegalArgumentException();
            }
        } catch (FileNotFoundException e) {

            throw new IllegalArgumentException();
        } catch (IOException e) {

            throw new IllegalArgumentException();
        }
    }

    /**
     * Indique si un fichier est valide pour importer ses données.
     * 
     * @param cheminFichier le chemin du fichier à vérifier
     * @return true si on peut importer des données depuis ce fichier
     *         sinon false
     * @throws IOException 
     */
    private static boolean isFichierValide(String cheminFichier)
            throws IOException {
        
        return cheminFichier != null && !cheminFichier.isBlank() 
               && Files.size(Path.of(cheminFichier)) != 0
               && isExtensionCSV(cheminFichier);
    }

    /**
     * Vérifie si un fichier contient l'extension .csv
     * 
     * @param cheminFichier le chemin du fichier à vérifier
     * @return true si le fichier a l'extension .csv ou sinon false
     */
    private static boolean isExtensionCSV(String cheminFichier) {
        
        return cheminFichier.substring(cheminFichier.lastIndexOf('.')+1, 
                                       cheminFichier.length()).equals("csv");
    }

    /**
     * Parcours un fichier CSV.
     * Vérifie la validité de toutes les lignes avant d'enregistrer
     * toutes les lignes en mémoire du fichier CSV
     * 
     * @param fichierCSV le fichier CSV ouvert
     * @throws IOException
     * @throws FichierDonneesInvalides si les données du fichier sont
     *                                 invalides pour une extraction
     */
    private static void parcourirFichier(BufferedReader fichierCSV)
             throws IOException, FichierDonneesInvalides {
        
        String ligne;
        ArrayList<String> lignes = new ArrayList<>();
        
        ligne = fichierCSV.readLine();
        while (ligne != null && !ligne.matches("^;*")) {
            lignes.add(ligne);
            ligne = fichierCSV.readLine();
        }
        
        if (verifierLignes(lignes)) {
            enregistrerLignes(lignes);
        } else {
            throw new FichierDonneesInvalides();
        }
    }

    /**
     * Enregistre les lignes en mémoire en fonction de l'identifiant
     * donné dans la première ligne.
     * 
     * @param lignes les lignes où on doit récupérer les données
     */
    private static void enregistrerLignes(ArrayList<String> lignes) {
        
        char lettreIdentifiant = lignes.get(0).split(";")[0].charAt(0);
        if (lettreIdentifiant == 'E') { // Exposition
            TraitementDonnees.creerExpositions(lignes);
        } else if (lettreIdentifiant == 'R') { // Visite
            TraitementDonnees.creerVisites(lignes);
        } else if (lettreIdentifiant == 'N') { // Employé
            TraitementDonnees.creerEmployes(lignes);
        } else if (lettreIdentifiant == 'C') { // Conférencier
            TraitementDonnees.creerConferenciers(lignes);
        } else {
            throw new IllegalArgumentException(ERREUR_CONTENU_FICHIER);
        }
    }

    /**
     * Vérifie le format des lignes d'un fichier CSV pour une
     * potentielle extraction des données dans les lignes.
     * 
     * @param lignes les lignes à vérifier
     */
    private static boolean verifierLignes(ArrayList<String> lignes) {
        
        char lettreIdentifiant;
        String premiereLigne = lignes.get(0);
        String deuxiemeLigne = lignes.get(1);
        
        /*
         * On récupère le type de données grâce à l'identifiant de la
         * première ligne contenant des données dans le fichier
         * Si on rencontre l'entête c'est forcément la ligne en
         * dessous
         */
        if (premiereLigne.matches("^Ident.*")) {
            lettreIdentifiant = deuxiemeLigne.split(";")[0].charAt(0);
            lignes.remove(0); // on supprime l'entête
        } else {
            lettreIdentifiant = premiereLigne.split(";")[0].charAt(0);
        }
        
        int indiceVerif;
        for (indiceVerif = 0; 
             indiceVerif < LETTRES_IDENTIFIANT_VALIDES.length
             && LETTRES_IDENTIFIANT_VALIDES[indiceVerif] != lettreIdentifiant;
             indiceVerif++)
            ; // empty body
        
        if (indiceVerif == LETTRES_IDENTIFIANT_VALIDES.length) {
            return false;
        }
        
        for (String ligne : lignes) {
            
            String[] donnees = ligne.split(";");
            String identifiant = donnees[0];
            if (!identifiant.matches(FORMAT_IDENTIFIANT)) {
                return false;
            }
            
            /* L'identifiant est forcément valide dans ce cas */
            if (lettreIdentifiant == 'E') { // Exposition
                
                if (donnees[2].matches("^\\d+$") // PériodeDeb
                    && donnees[3].matches("^\\d+$") // PériodeFin
                    && donnees[4].matches("^\\d+$") // nombre
                    && donnees[5].matches("^#.*#$") // motClé
                    && donnees[7].matches(FORMAT_DATE_FR) // Début
                    && donnees[8].matches(FORMAT_DATE_FR) // Fin
                    ) {
                    
                    return true;
                }
                
            } else if (lettreIdentifiant == 'R') { // Visite
                
                if (donnees[1].matches("^E(\\d){6}$") // Exposition
                    && donnees[2].matches("^C(\\d){6}$") // Conférencier
                    && donnees[3].matches("^N(\\d){6}$") // Employé
                    && donnees[4].matches(FORMAT_DATE_FR) // date
                    && donnees[5].matches("^(\\d){2}h(\\d){2}$") // heureDebut
                    && donnees[7].matches("^(\\d){10}$") // Telephone
                    ) {
                    
                    return true;
                }
            } else if (lettreIdentifiant == 'N') { // Employé
                
                if (donnees[3].isBlank() 
                    || donnees[3].matches("^(\\d){4}$") // Telephone
                    ) {
                    
                    return true;
                }
            } else if (lettreIdentifiant == 'C') { // Conférencier
                
                /* On vérifie les format des indisponibilités */
                for (indiceVerif = 7;
                     indiceVerif < donnees.length 
                     && donnees[indiceVerif].matches(FORMAT_DATE_FR);
                     indiceVerif++) 
                    ; // empty body
                
                if (donnees[3].matches("^#.*#$") // Spécialité
                    && donnees[4].matches("^(\\d){10}$") // Telephone
                    && donnees[5].matches("^(?i)(oui|non)$") // Employe
                    && indiceVerif == donnees.length // Indisponibilite
                    ) {
                    
                    return true;
                }
            }
        }
        
        return false;
    }   
}
