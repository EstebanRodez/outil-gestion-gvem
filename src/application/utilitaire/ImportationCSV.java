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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


/**
 * La classe ImportationCSV permet d'importer et de verifier des
 * données à partir d'un fichier CSV.
 * 
 * @author Ayoub Laluti
 * @author Esteban Vroemen
 * @version 2.0
 */
public class ImportationCSV {
    
    private static final String ERREUR_FICHIER_INVALIDE =
    """
    Erreur de fichier invalide.
    Le fichier est invalide pour une extraction de données CSV.
    """;
    
    private static final String ERREUR_FICHIER_INTROUVABLE =
    """
    Erreur de fichier inexistant.
    Le fichier est introuvable.
    """;
    
    private static final String ERREUR_FICHIER_ACCES =
    """
    Erreur d'accès fichier.
    Le fichier n'est pas lisible/accessible.
    """;
    
    private static final String ERREUR_DONNEES_INCORRECTES =
    """
    Erreur des données fichier.
    Le fichier contient des données incorrectes.
    """;
    
    private static final String FORMAT_IDENTIFIANT = "^%c(\\d){6}$";
    
    private static final String FORMAT_DATE_FR
    = "^(\\d{2})\\/(\\d{2})\\/(\\d{4})$";
    
    private static final Character[] LETTRES_IDENTIFIANT_VALIDES
    = {'E', 'R', 'C', 'N'};
    
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
            
            if (!isFichierValide(cheminFichier)) {
                throw new IllegalArgumentException(ERREUR_FICHIER_INVALIDE);
            }
            
            FileInputStream fileInputStream
            = new FileInputStream(cheminFichier);
            InputStreamReader inputStreamReader
            = new InputStreamReader(fileInputStream, "windows-1252");
            BufferedReader fichierCSV = new BufferedReader(inputStreamReader);
                
            parcourirFichier(fichierCSV);
        } catch (FileNotFoundException e) {

            throw new IllegalArgumentException(ERREUR_FICHIER_INTROUVABLE);
        } catch (IOException e) {

            throw new IllegalArgumentException(ERREUR_FICHIER_ACCES);
        }
    }

    /**
     * Indique si un fichier est valide pour importer ses données.
     * 
     * @param cheminFichier le chemin du fichier à vérifier
     * @return true si on peut importer des données depuis ce fichier
     *         sinon false
     * @throws IOException si la taille du fichier n'est pas
     *                     vérifiable
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
     * Parcourt un fichier CSV pour valider et charger les lignes de
     * données en mémoire. Toutes les lignes sont vérifiées pour leur
     * validité avant d'être enregistrées en mémoire.<br>
     * Si une ou plusieurs lignes sont invalides, une exception est
     * levée.
     *
     * @param fichierCSV le lecteur de fichier CSV ouvert pour lecture
     * @throws IOException en cas de problème de lecture du fichier
     * @throws FichierDonneesInvalides si les données du fichier sont
     *                                 invalides pour une extraction
     */
    private static void parcourirFichier(BufferedReader fichierCSV)
             throws IOException, FichierDonneesInvalides {
        
        String ligne;
        ArrayList<String[]> donnees = new ArrayList<>();
        
        ligne = fichierCSV.readLine();
        while (ligne != null && !ligne.matches("^;*")) {
            donnees.add(ligne.split(";"));
            ligne = fichierCSV.readLine();
        }
        fichierCSV.close();
        
        if (verifierLignes(donnees)) {
            enregistrerLignes(donnees);
        } else {
            throw new FichierDonneesInvalides(ERREUR_DONNEES_INCORRECTES);
        }
    }

    /**
     * Enregistre les lignes de données en mémoire en fonction de la
     * classe spécifié par l'identifiant dans la première ligne de
     * données. Les lignes sont ensuite passées à des méthodes
     * spécifiques de création selon la classe :
     * Exposition, Visite, Employé ou Conférencier.
     *
     * @param donneesLignes les lignes de données à enregistrer
     */
    private static void enregistrerLignes(ArrayList<String[]> donneesLignes) {
        
        char lettreIdentifiant = donneesLignes.get(0)[0].charAt(0);
        if (lettreIdentifiant == 'E') { // Exposition
            TraitementDonnees.creerExpositions(donneesLignes);
        } else if (lettreIdentifiant == 'R') { // Visite
            TraitementDonnees.creerVisites(donneesLignes);
        } else if (lettreIdentifiant == 'N') { // Employé
            TraitementDonnees.creerEmployes(donneesLignes);
        } else { // Forcément Conférencier
            TraitementDonnees.creerConferenciers(donneesLignes);
        }
    }

    /**
     * Vérifie le format des lignes de données CSV pour déterminer si
     * elles sont valides pour une extraction. Les données sont
     * validées en fonction de l'identifiant de la classe en première
     * ligne (ex. "E" pour Exposition, "R" pour Visite, etc.) et en
     * fonction de règles spécifiques à chaque classe.
     *
     * @param donneesLignes les lignes de données à vérifier
     * @return true si toutes les lignes respectent le format
     *         attendu, false sinon
     */
    private static boolean verifierLignes(ArrayList<String[]> donneesLignes) {
        
        char lettreIdentifiant;
        String premierIdentifiant = donneesLignes.get(0)[0];
        String deuxiemeIdentifiant = donneesLignes.get(1)[0];
        
        /*
         * On récupère le type de données grâce à l'identifiant de la
         * première ligne contenant des données dans le fichier
         * Si on rencontre l'entête c'est forcément la ligne en
         * dessous
         */
        if (premierIdentifiant.matches("^Ident$")) {
            lettreIdentifiant = deuxiemeIdentifiant.charAt(0);
            donneesLignes.remove(0); // on supprime l'entête
        } else {
            lettreIdentifiant = premierIdentifiant.charAt(0);
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
        
        for (String[] donnees : donneesLignes) {
            
            String identifiant = donnees[0];
            if (!identifiant.matches(
                    String.format(FORMAT_IDENTIFIANT, lettreIdentifiant))) {
                
                return false;
            }
            
            /* L'identifiant est forcément valide dans ce cas */
            if (lettreIdentifiant == 'E') { // Exposition
                    
                if (!donnees[2].matches("^\\d+$") // PériodeDeb
                    || !donnees[3].matches("^\\d+$") // PériodeFin
                    || !donnees[4].matches("^\\d+$") // nombre
                    || !donnees[5].matches("^#.*#$") // motClé
                    || donnees.length > 7
                       && (!donnees[7].matches(FORMAT_DATE_FR) // Début
                           || !isNombresDateValides(donnees[7]))
                    || donnees.length > 7 
                       && (!donnees[8].matches(FORMAT_DATE_FR) // Fin
                           || !isNombresDateValides(donnees[8]))
                    ) {
                    
                    return false;
                }
                
            } else if (lettreIdentifiant == 'R') { // Visite
                
                if (!donnees[1].matches("^E(\\d){6}$") // Exposition
                    || !donnees[2].matches("^C(\\d){6}$") // Conférencier
                    || !donnees[3].matches("^N(\\d){6}$") // Employé
                    || !donnees[4].matches(FORMAT_DATE_FR) // date
                    || !isNombresDateValides(donnees[4])
                    || !donnees[5].matches("^(\\d){2}h(\\d){2}$") // heureDebut
                    || !donnees[7].matches("^(\\d){10}$") // Telephone
                    ) {
                    
                    return false;
                }
            } else if (lettreIdentifiant == 'N') { // Employé
                
                if (!donnees[3].isBlank() 
                    && !donnees[3].matches("^(\\d){4}$") // Telephone
                    ) {
                    
                    return false;
                }
            } else { // Forcément Conférencier
                
                /* On vérifie les format des indisponibilités */
                for (indiceVerif = 6;
                     indiceVerif < donnees.length 
                     && (donnees[indiceVerif].matches(FORMAT_DATE_FR)
                         && isNombresDateValides(donnees[indiceVerif])
                         || donnees[indiceVerif].isBlank());
                     indiceVerif++) 
                    ; // empty body
                
                if (!donnees[3].matches("^#.*#$") // Spécialité
                    || !donnees[4].matches("^(\\d){10}$") // Telephone
                    || !donnees[5].matches("^(?i)(oui|non)$") // Employe
                    || indiceVerif != donnees.length // Indisponibilite
                    ) {
                    
                    return false;
                }
            }
        }
        
        return true;
    }   
    
    /**
     * Vérifie si une date donnée au format "jour/mois/année" est
     * valide.<br>
     * La méthode détermine si le jour est cohérent avec le mois et
     * l'année fournis, en prenant en compte les années bissextiles
     * pour le mois de février.
     *
     * @param date la date sous forme de chaîne de caractères au
     *             format "jour/mois/année"
     * @return true si la date est valide, false sinon
     */
    private static boolean isNombresDateValides(String date) {
        
        String[] nombres = date.split("/");
        int mois = Integer.parseInt(nombres[1]);
        if (mois > 12 || mois <= 0) {
           return false; 
        }
        
        int annee = Integer.parseInt(nombres[2]);
        int nombreJour;
        if (mois == 2) {
            nombreJour = annee % 4 == 0 && annee % 100 != 0 
                         || annee % 400 == 0 ? 29 : 28;
        } else if (mois < 8) {
            nombreJour = mois % 2 == 1 ? 31 : 30;
        } else {
            nombreJour = mois % 2 == 0 ? 31 : 30;
        }
        
        return Integer.parseInt(nombres[0]) <= nombreJour;
    }
}
