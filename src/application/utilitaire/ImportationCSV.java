/*
 * ImportationCSV.java                           
 * 16 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import application.modele.Exposition;
import application.modele.ExpositionTemporaire;

/**
 * La classe ImportationCSV permet d'importer et de traiter des
 * données à partir d'un fichier CSV, en créant des objets Exposition
 * ou ExpositionTemporaire selon le type de données.
 */
public class ImportationCSV {

    private static final String ERREUR_NOMBRE_ARGUMENTS 
    = "Erreur: Le nombre de colonne est incorrect.";

    private static final String ERREUR_CONTENU_FICHIER 
    = "Erreur: Le fichier csv n'est ni des expositions, ni des visites, "
      + "ni des employés et ni des conferenciers.";

    private static final String ERREUR_LECTURE_FICHIER 
    = " Erreur : Une erreur d'entrée/sortie est survenue lors de la "
      + "lecture/écriture du fichier.";

    final static DateTimeFormatter formatter
    = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static List<Exposition> expositions = new ArrayList<>();

    /**
     * Importe les données d'un fichier CSV et les stocke 
     * sous forme de liste de tableaux de chaînes.
     * 
     * @param lienFichier Le chemin vers le fichier CSV à importer
     * @return Une liste de tableaux de chaînes représentant les
     *         données du CSV
     * @throws IOException si une erreur survient lors 
     *         de la lecture du fichier
     */
    public static List<String[]> importer(String lienFichier)
            throws IOException {

        List<String[]> data = new ArrayList<>();
        BufferedReader fichierCSV;
        String ligne;
        String[] valeur;
        String entete;

        try {
            fichierCSV = new BufferedReader(new FileReader(lienFichier));
            entete = fichierCSV.readLine();
            if (!entete.substring(0,5).equals("Ident") && entete != null) {
                valeur = entete.split(";");
                data.add(valeur);
            } // else, on saute la ligne

            while ((ligne = fichierCSV.readLine()) != null) {
                valeur = ligne.split(";");
                data.add(valeur);
            }

            fichierCSV.close();        

        } catch (IOException pbLecture) {
            throw new IOException(ERREUR_LECTURE_FICHIER, pbLecture);
        }

        return data;
    } 

    /**
     * Traite les données importées en fonction de leur type 
     * (Exposition, Visite, Employé, Conférencier).
     * 
     * @param donnee La liste des données CSV à traiter.
     * @throws IllegalArgumentException si la référence de
     *                                  l'identifiant est incorrect
     */
    public static void traitementDonnees(List<String[]> donnee) {

        char typeCSV;

        typeCSV = donnee.get(0)[0].charAt(0);

        if (typeCSV == 'E') { // Exposition
            creerExposition(donnee);
        } else if (typeCSV == 'R') { // Visite

        } else if (typeCSV == 'N') { // Employé

        } else if (typeCSV == 'C') { // Conférencier

        } else {
            throw new IllegalArgumentException(ERREUR_CONTENU_FICHIER);
        }
    }

    /**
     * Crée des objets Exposition ou ExpositionTemporaire à partir
     * des données CSV et les ajoute à la liste des expositions.
     * 
     * @param donnee La liste des données CSV représentant des
     *               expositions
     * @throws IllegalArgumentException si le nombre d'argument d'une
     *                                  ligne est incorrect
     */
    private static void creerExposition(List<String[]> donnee) {
        
        String identifiant;
        String intitule;
        int periodeDebut;
        int periodeFin;
        int nbOeuvre;
        String[] motsCles;
        String resume;
        LocalDate dateDebut;
        LocalDate dateFin;

        Exposition expo;
        ExpositionTemporaire expoTemporaire;

        for (String[] ligne : donnee) {

            // Vérifier si la ligne n'est pas vide
            if (ligne.length > 0) {
                identifiant = ligne[0];
                intitule = ligne[1];
                periodeDebut = Integer.parseInt(ligne[2]);
                periodeFin = Integer.parseInt(ligne[3]);
                nbOeuvre = Integer.parseInt(ligne[4]);
                motsCles = ligne[5].replace("#", "").split(", ");
                resume = ligne[6];

                if (ligne.length == 7) { // Exposition normale

                    expo = new Exposition(identifiant, intitule, periodeDebut, 
                            periodeFin, nbOeuvre, motsCles,
                            resume);
                    expositions.add(expo);

                } else if (ligne.length == 9 ) { // Exposition temporaire
                    
                    dateDebut = LocalDate.parse(ligne[7], formatter);
                    dateFin = LocalDate.parse(ligne[8], formatter);

                    expoTemporaire 
                    = new ExpositionTemporaire(identifiant, intitule,
                                               periodeDebut, periodeFin,
                                               nbOeuvre, motsCles, resume,
                                               dateDebut, dateFin);

                    expositions.add(expoTemporaire);
                } else {
                    throw new IllegalArgumentException(ERREUR_NOMBRE_ARGUMENTS); 
                }
            }
        }
    }

    /**
     * Récupère la liste des expositions traitées.
     * 
     * @return Une liste d'objets Exposition
     */
    public static List<Exposition> getExpositions() {
        return expositions;
    }
}
