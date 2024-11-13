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
import java.util.ArrayList;

/**
 * Classe utilitaire pour la gestion des fichiers CSV dans l'application.
 * Permet de déterminer le type de données stockées dans un fichier CSV
 * et de valider l'existence et le format des fichiers CSV.
 */
public class GestionCSV {
    
    private static final char[] LETTRES_IDENTIFIANT_VALIDES
    = {'E', 'R', 'C', 'N'};
    
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
        
        FileInputStream fileInputStream
        = new FileInputStream(cheminFichier);
        InputStreamReader inputStreamReader
        = new InputStreamReader(fileInputStream, "windows-1252");
        BufferedReader fichierCSV = new BufferedReader(inputStreamReader);
        
        String premiereLigne = fichierCSV.readLine();
        char lettreIdentifiant = 0;
        if (premiereLigne != null) {
        
            String premierIdentifiant = premiereLigne.split(";")[0];
            if (premierIdentifiant.matches("^Ident$")) {
                
                String deuxiemeLigne = fichierCSV.readLine();
                if (deuxiemeLigne != null) {
                    lettreIdentifiant = deuxiemeLigne.charAt(0);
                }
            } else {
                lettreIdentifiant = premierIdentifiant.charAt(0);
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
               && Files.size(Path.of(chemin)) != 0
               && chemin.endsWith(".csv");
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
     * Lit les lignes d'un fichier CSV spécifié et retourne les données
     * sous forme de liste. Chaque ligne est transformée en un tableau de
     * chaînes de caractères, les éléments étant séparés par le caractère ';'.
     *
     * @param cheminFichier le chemin du fichier CSV à lire.
     * @return une liste d'arrays de chaînes de caractères représentant
     *         les lignes de données du fichier CSV.
     * @throws IOException si une erreur d'entrée/sortie se produit lors de
     *                     la lecture du fichier.
     */
    public static ArrayList<String[]> getDonneesLignes(String cheminFichier)
            throws IOException {
        
        FileInputStream fileInputStream
        = new FileInputStream(cheminFichier);
        InputStreamReader inputStreamReader
        = new InputStreamReader(fileInputStream, "windows-1252");
        BufferedReader fichierCSV = new BufferedReader(inputStreamReader);
        
        String ligne;
        ArrayList<String[]> donnees = new ArrayList<>();
        
        ligne = fichierCSV.readLine();
        while (ligne != null && !ligne.matches("^;*")) {
            donnees.add(ligne.split(";"));
            ligne = fichierCSV.readLine();
        }
        fichierCSV.close();
        
        return donnees;
    }
}
