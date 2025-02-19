/*
 * ImportationCSV.java                           
 * 16 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * La classe ImportationCSV permet d'importer et de verifier des
 * données à partir d'un fichier CSV.
 *
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class ImportationCSV {

    //private static ArrayList<Exposition> expositions = new ArrayList<>();

    private static final String ERREUR_FICHIER_INVALIDE =
    """
    Erreur de fichier invalide.
    Le fichier est invalide pour une extraction de données CSV.
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

    /**
     * Importe les données d'un fichier CSV.
     * 
     * @param cheminFichier le chemin du fichier
     * @throws IllegalArgumentException si le fichier n'est pas
     *                                  valide ou n'est pas en
     *                                  extension CSV
     * @throws IllegalArgumentException
     * @throws IllegalArgumentException
     * @throws FichierDonneesInvalidesException si les données du fichier sont
     *                                          invalides
     */

    public static void importerDonnees(String cheminFichier)
            throws FichierDonneesInvalidesException {

        try {

            BufferedReader fichierCSV;
            if (GestionCSV.isFichierValide(cheminFichier)) {

                FileInputStream fileInputStream
                = new FileInputStream(cheminFichier);
                InputStreamReader inputStreamReader
                = new InputStreamReader(fileInputStream, "windows-1252");
                fichierCSV = new BufferedReader(inputStreamReader);
            } else {
                throw new IllegalArgumentException(ERREUR_FICHIER_INVALIDE);
            }

            parcourirFichier(fichierCSV);
        } catch (IOException e) {

            throw new IllegalArgumentException(ERREUR_FICHIER_ACCES);
        }
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
            throws IOException, FichierDonneesInvalidesException {

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
            throw new FichierDonneesInvalidesException(ERREUR_DONNEES_INCORRECTES);
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

        if (donneesLignes.size() == 0) {
            return false;
        }

        char lettreIdentifiant;
        String premierIdentifiant = donneesLignes.get(0)[0];
        
        /* Pour vérifier l'unicité de chaque identifiant */
        HashSet<String> listeIdentifiants = new HashSet<>();

        /*
         * On récupère le type de données grâce à l'identifiant de la
         * première ligne contenant des données dans le fichier
         * Si on rencontre l'entête c'est forcément la ligne en
         * dessous
         */
        if (premierIdentifiant.matches("^Ident$")) {

            if (donneesLignes.size() == 1) {
                return false;
            }

            lettreIdentifiant = donneesLignes.get(1)[0].charAt(0);
            donneesLignes.remove(0); // on supprime l'entête
        } else {
            lettreIdentifiant = premierIdentifiant.charAt(0);
        }
        
        if (!GestionCSV.isLettreIdentifiantValide(lettreIdentifiant)) {
            return false;
        }
        

        for (String[] donnees : donneesLignes) {

            String identifiant = donnees[0];
            if (!identifiant.matches(String.format(FORMAT_IDENTIFIANT,
                                                   lettreIdentifiant))
                || listeIdentifiants.contains(identifiant)
                || !TraitementDonnees.isIdentifiantUnique(identifiant)) {
                
                return false;
            }
            listeIdentifiants.add(identifiant);
            
            /* L'identifiant est forcément valide dans ce cas */
            if (lettreIdentifiant == 'E') { // Exposition

                if (donnees.length < 7 // Manque de valeurs
                    || !donnees[2].matches("^\\d+$") // PériodeDeb
                    || !donnees[3].matches("^\\d+$") // PériodeFin
                    || !donnees[4].matches("^\\d+$") // nombre
                    || !donnees[5].matches("^#.*#$") // motClé
                    || donnees.length >= 9 // Si ExpositionTemporaire
                    && (!donnees[7].matches(FORMAT_DATE_FR) // Début
                        || !isNombresDateValides(donnees[7])
                        || !donnees[8].matches(FORMAT_DATE_FR) // Fin
                        || !isNombresDateValides(donnees[8]))
                    ) {

                    return false;
                }

            } else if (lettreIdentifiant == 'R') { // Visite

                if (donnees.length < 8 // Manque de valeurs
                    || !donnees[1].matches("^E(\\d){6}$") // Exposition
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

                if (donnees.length < 3 // Manque de valeurs
                    || donnees.length >= 4 // Telephone est optionnel
                       && !donnees[3].isBlank() 
                       && !donnees[3].matches("^(\\d){4}$") // Telephone
                    ) {

                    return false;
                }
            } else { // Forcément Conférencier

                /* On vérifie les format des indisponibilités */
                int indiceVerif;
                for (indiceVerif = 6;
                     indiceVerif < donnees.length 
                     && (donnees[indiceVerif].matches(FORMAT_DATE_FR)
                         && isNombresDateValides(donnees[indiceVerif])
                         || donnees[indiceVerif].isBlank());
                     indiceVerif++) 
                    ; // empty body

                if (donnees.length < 6 // Manque de valeurs
                    || !donnees[3].matches("^#.*#$") // Spécialité
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
