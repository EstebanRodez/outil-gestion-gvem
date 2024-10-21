/*
 * ImportationCSV.java                           
 * 16 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import application.modele.Client;
import application.modele.Conferencier;
import application.modele.Employe;
import application.modele.Exposition;
import application.modele.ExpositionTemporaire;
import application.modele.Indisponibilite;

/**
 * La classe ImportationCSV permet d'importer et de traiter des
 * données à partir d'un fichier CSV, en créant des objets Exposition
 * ou ExpositionTemporaire selon le type de données.
 * @author Ayoub Laluti
 * @author Esteban Vroemen
 * @version 1.0
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

    private static ArrayList<Exposition> expositions = new ArrayList<>();
    
    private static ArrayList<Employe> employes = new ArrayList<>();
    
    private static ArrayList<Conferencier> conferenciers = new ArrayList<>();
    
    /**
     * Importe les données d'un fichier normalement en .csv
     * @param cheminFichier le chemin du fichier
     */
    public static void importerDonnees(String cheminFichier) {
        
        BufferedReader fichierCSV;
        try {
            
            fichierCSV = new BufferedReader(new FileReader(cheminFichier));
            if (isFichierValide(cheminFichier)) {
                
                fichierCSV.close();  
                throw new IllegalArgumentException();
            } else {
                
                parcourirFichier(fichierCSV);
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

        return cheminFichier != null && cheminFichier.isBlank() 
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
        return cheminFichier.substring(cheminFichier.lastIndexOf('.'), 
                                       cheminFichier.length()).equals("csv");
    }

    /**
     * Parcours le fichier CSV ligne à ligne.
     * Enregistre la ligne en fonction du type de données et si la
     * ligne est valide.
     * 
     * @param fichierCSV le fichier CSV ouvert
     * @throws IOException 
     */
    private static void parcourirFichier(BufferedReader fichierCSV)
             throws IOException {
        
        String ligne;
        while ((ligne = fichierCSV.readLine()) != null) {
            if (verifierLigne(ligne)) {
                enregistrerLigne(ligne);
            }
        }
    }

    /**
     * Enregistre la ligne en mémoire en fonction de son identifiant.
     * 
     * @param ligne la ligne où on doit récupérer les données
     */
    private static void enregistrerLigne(String ligne) {
        
        // TODO Enregistrer la ligne en fonction de son identifiant
        // et dans son tableau correspondant
    }

    /**
     * Vérifie le format de la ligne pour déduire si elle est valide
     * pour une extraction de ses données.
     * 
     * @param ligne la ligne à vérifier
     */
    private static boolean verifierLigne(String ligne) {
        
        // TODO Vérifier la validité de la ligne
        return false; // bouchon
    }

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
    public static ArrayList<String[]> importer(String lienFichier)
            throws IOException {

        ArrayList<String[]> data = new ArrayList<>();
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
            creerVisite(donnee);
        } else if (typeCSV == 'N') { // Employé
            creerEmploye(donnee);
        } else if (typeCSV == 'C') { // Conférencier
            creerConferencier(donnee);
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
     * Crée des objets Employe  à partir des données CSV et les
     * ajoute à la liste des employés.
     * 
     * @param donnee La liste des données CSV représentant des
     *               employés
     * @throws IllegalArgumentException si le nombre d'argument d'une
     *                                  ligne est incorrect
     */
    private static void creerEmploye(List<String[]> donnee) {
        String identifiant, 
               nom,
               prenom,
               numTel;
         
        Employe employe;
         
        for (String[] ligne : donnee) {
        
             // Vérifier si la ligne n'est pas vide
            if (ligne.length > 0) {
                identifiant = ligne[0];
                nom = ligne[1];
                prenom = ligne[2];
                numTel = ligne[3];
                if (ligne.length == 4) { 
        
                    employe = new Employe(identifiant, nom, prenom, numTel);
                    employes.add(employe);
                } else {
                    throw new IllegalArgumentException(ERREUR_NOMBRE_ARGUMENTS); 
                }       
            }
        }     
    }
    
    /**
     * Crée des objets Conferencier  à partir des données CSV et les
     * ajoute à la liste des conferenciers.
     * 
     * @param donnee La liste des données CSV représentant des
     *               conferenciers
     * @throws IllegalArgumentException si le nombre d'argument d'une
     *                                  ligne est incorrect
     */
    private static void creerConferencier(List<String[]> donnee) {
         String identifiant,
                nom,
                prenom;
         String[] specialites;
         String numTel;
         boolean estInterne;
         
         Conferencier conferencier;
         Indisponibilite[] indisponibilites;
         
         for (String[] ligne : donnee) {

             // Vérifier si la ligne n'est pas vide
             if (ligne.length > 0) {
                 identifiant = ligne[0];
                 nom = ligne[1];
                 prenom = ligne[2];
                 specialites = ligne[3].replace("#", "").split(", ");
                 numTel = ligne[4]; 
                 estInterne = ligne[5].equalsIgnoreCase("oui") ? true : false;
                 
                 
                 if (ligne.length == 6) { 
                     conferencier = new Conferencier(identifiant, nom, prenom, 
                                                     specialites, numTel,
                                                     estInterne);
                     conferenciers.add(conferencier);
                 } else if (ligne.length > 6){                     
                 
                     indisponibilites = creeIndisponibilité(ligne);
                     conferencier = new Conferencier(identifiant, nom, prenom, 
                                                     specialites, numTel, 
                                                     estInterne, 
                                                     indisponibilites);
                     conferenciers.add(conferencier);
                 } else {
                     throw new IllegalArgumentException(ERREUR_NOMBRE_ARGUMENTS); 
                 }       
             }
         }
    }
    
    /**
     * Crée des objets Viste  à partir des données CSV et les
     * ajoute à la liste des visites.
     * 
     * @param donnee La liste des données CSV représentant des
     *               visite
     * @throws IllegalArgumentException si le nombre d'argument d'une
     *                                  ligne est incorrect
     */
    private static void creerVisite(List<String[]> donnee) {
         String identifiant;
         int horaireDebut;
        
         LocalDate date;
        
         Client client;
         Exposition exposition;       
         Employe employe; 
         Conferencier conferencier;
              
        for (String[] ligne : donnee) {
        
          
        }     
    }
    
    /**
     * Crée un tableau de l'objet Indiponibilité
     * 
     * @param la ligne du tableau csv qui contient des indisponiblitées
     */
    private static Indisponibilite[] creeIndisponibilité(String[] ligne) {
        int nombreIndisponibilites,
            debutIndex;
        
        LocalDate dateDebut,
                  dateFin;
        
        Indisponibilite[] indisponibilites;
        
        nombreIndisponibilites = (ligne.length - 6) / 2;
        indisponibilites 
            = new Indisponibilite [nombreIndisponibilites];

        for (int i = 0; i < nombreIndisponibilites; i++) {
            
            debutIndex = 6 + i * 2;
            dateDebut = LocalDate.parse(ligne[debutIndex],
                                        formatter);

            // Si une date de fin est fournie
            if (debutIndex + 1 < ligne.length 
                    && !ligne[debutIndex + 1].isEmpty() 
                    && !ligne[debutIndex]
                            .equals(ligne[debutIndex + 1])) {
                
                dateFin = LocalDate.parse(ligne[debutIndex + 1],
                                          formatter);
                indisponibilites[i] 
                 = new Indisponibilite(dateDebut, dateFin);
            } else {
                // Ajouter seulement la date de début
                indisponibilites[i] 
                  = new Indisponibilite(dateDebut);
            }
        }
        return  indisponibilites;
    }

    /**
     * Récupère la liste des expositions traitées.
     * 
     * @return Une liste d'objets Exposition
     */
    public static List<Exposition> getExpositions() {
        return expositions;
    }
    
    /**
     * Récupère la liste des employes traitées.
     * 
     * @return Une liste d'objets Employe
     */
    public static List<Employe> getEmployes() {
        return employes;
    }
    
    /**
     * Récupère la liste des conferenciers traitées.
     * 
     * @return Une liste d'objets Conferencier
     */
    public static List<Conferencier> getConferenciers() {
        return conferenciers;
    }
}
