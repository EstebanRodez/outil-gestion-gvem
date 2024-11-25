/*
 * GestionCSV.java                           
 * 13 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Classe utilitaire pour la gestion des fichiers CSV dans l'application.
 * Permet de déterminer le type de données stockées dans un fichier CSV
 * et de valider l'existence et le format des fichiers CSV.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class GestionCSV {
    
    private static final char[] LETTRES_IDENTIFIANT_VALIDES
    = {'E', 'R', 'C', 'N'};

    private static final String ERREUR_CHEMIN_FICHIER_INVALIDE
    = "La référence du chemin du fichier ne doit pas être nulle;";

    private static final String ERREUR_CHEMIN_FICHIER_VIDE
    = "Le chemin du fichier ne doit pas être nul.";
    
    /**
     * Détecte le type de données stockées dans un fichier CSV, en fonction du
     * premier caractère de l'identifiant dans le fichier. Ce type est défini 
     * par une lettre au début de l'identifiant
     * (par exemple, 'E' pour Exposition).
     *
     * @param cheminFichier le chemin du fichier CSV à analyser.
     * @return la lettre représentant le type de données stockées dans le CSV :
     *         'E', 'R', 'C', ou 'N'. Retourne 0 si le type est invalide.
     * @throws IOException si une erreur de lecture survient lors de
     *                     l'ouverture du fichier.
     */
    public static char getTypeCSV(String cheminFichier) throws IOException {
        
        if (!isFichierValide(cheminFichier)) {
            return 0;
        }
        
        FileInputStream fileInputStream
        = new FileInputStream(cheminFichier);
        InputStreamReader inputStreamReader
        = new InputStreamReader(fileInputStream, "windows-1252");
        BufferedReader fichierCSV = new BufferedReader(inputStreamReader);
        
        String premiereLigne = fichierCSV.readLine();
        char lettreIdentifiant = 0;
        if (!premiereLigne.isBlank()) {
            String[] valeurs = premiereLigne.split(";");
            
            if (valeurs.length > 0) {
                String premierIdentifiant = valeurs[0];
                lettreIdentifiant = premierIdentifiant.charAt(0); 
                if (premierIdentifiant.matches("^Ident$")) {
                    
                    String deuxiemeLigne = fichierCSV.readLine();
                    if (deuxiemeLigne != null) {
                        lettreIdentifiant = deuxiemeLigne.charAt(0);
                    }
                } else {
                    lettreIdentifiant = premierIdentifiant.charAt(0);                      
                }
            }
        } 
        fichierCSV.close();
        
        /* 
         * La lettre trouvée pour l'identifiant ne correspond à aucun type
         * de fichier CSV que l'application traite donc la lettre trouvée
         * est supprimé
         */
        if (!isLettreIdentifiantValide(lettreIdentifiant)) {
            lettreIdentifiant = 0;
        }
        
        return lettreIdentifiant;
    }
    
    /**
     * Vérifie si le fichier spécifié est un fichier CSV valide.
     * La validité est déterminée en vérifiant si le fichier existe,
     * a une taille non nulle, et si son extension est '.csv'.
     *
     * @param chemin le chemin du fichier à valider.
     * @return true si le fichier est valide, sinon false.
     * @throws IOException si une erreur d'accès au fichier survient.
     */
    public static boolean isFichierValide(String chemin) throws IOException {
        return chemin != null && !chemin.isBlank()
               && Files.exists(Path.of(chemin))    
               && chemin.endsWith(".csv")
               && Files.size(Path.of(chemin)) != 0;
    }
    
    /**
     * Vérifie si une lettre donnée correspond à l'une des lettres valides
     * utilisées comme identifiants dans les fichiers CSV traités
     * par l'application.
     *
     * @param lettre la lettre à vérifier.
     * @return true si la lettre est un identifiant valide, sinon false.
     */
    public static boolean isLettreIdentifiantValide(char lettre) {
        
        int indiceVerif;
        for (indiceVerif = 0;
             indiceVerif < LETTRES_IDENTIFIANT_VALIDES.length
             && lettre != LETTRES_IDENTIFIANT_VALIDES[indiceVerif];
             indiceVerif++)
            ; // empty body
        
        return indiceVerif != LETTRES_IDENTIFIANT_VALIDES.length;
    }
    
    /**
     * Vérifie si le fichier est vide ou contient uniquement des espaces.
     * Cette méthode lit le fichier ligne par ligne et utilise la méthode
     * trim() pour vérifier si une ligne ne contient que des espaces.
     * 
     * @param cheminFichier le chemin du fichier à vérifier.
     * @return true si le fichier est vide ou ne contient que des espaces,
     *         false sinon.
     * @throws IOException si une erreur de lecture du fichier se produit.
     * @throws IllegalArgumentException si la référence du chemin du fichier
     *                                  est nulle ou la chemin est vide
     */
    public static boolean isFichierVide(String cheminFichier) throws IOException {
        
        if (cheminFichier == null) {
            throw new IllegalArgumentException(ERREUR_CHEMIN_FICHIER_INVALIDE);
        }
        
        if (cheminFichier.isBlank()) {
            throw new IllegalArgumentException(ERREUR_CHEMIN_FICHIER_VIDE);
        }
        
        FileInputStream fileInputStream
        = new FileInputStream(cheminFichier);
        InputStreamReader inputStreamReader
        = new InputStreamReader(fileInputStream, "windows-1252");
        BufferedReader lecteur = new BufferedReader(inputStreamReader);
        
        String ligne;
        while ((ligne = lecteur.readLine()) != null) {
            if (!ligne.replace(";", "").isBlank()) {
                lecteur.close();
                return false;
            }
        }
        
        lecteur.close();
        return true;
    }
}
