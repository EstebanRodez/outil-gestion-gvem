/*
 * ImportationCSV.java                           
 * 16 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.modele.Client;
import application.modele.Conferencier;
import application.modele.Employe;
import application.modele.Exposition;
import application.modele.ExpositionTemporaire;
import application.modele.Indisponibilite;
import application.modele.Visite;

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
    
    private static final String ERREUR_CONFERENCIER_INTROUVABLE 
    = " Erreur : Aucun conferencier n'est associé a l'identifiant indiqué";
    
    private static final String ERREUR_EMPLOYE_INTROUVABLE 
    = " Erreur : Aucun employé n'est associé a l'identifiant indiqué";
      
    private static final String ERREUR_EXPOSITION_INTROUVABLE 
    = " Erreur : Aucune exposition n'est associé a l'identifiant indiqué";

    private final static DateTimeFormatter FORMATTER_DATE_FR
    = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static ArrayList<Exposition> expositions = new ArrayList<>();
    
    private static ArrayList<Employe> employes = new ArrayList<>();
    
    private static ArrayList<Conferencier> conferenciers = new ArrayList<>();
    
    private static ArrayList<Client> clients = new ArrayList<>();
    
    private static ArrayList<Visite> visites = new ArrayList<>();
    
    private static final String FORMAT_IDENTIFIANT = "^[EVC]\\d{6}$";
    
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
            creerExpositions(lignes);
        } else if (lettreIdentifiant == 'R') { // Visite
            creerVisites(lignes);
        } else if (lettreIdentifiant == 'N') { // Employé
            creerEmployes(lignes);
        } else if (lettreIdentifiant == 'C') { // Conférencier
            creerConferenciers(lignes);
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
                
                if (!donnees[2].matches("^\\d+$") // PériodeDeb
                    || !donnees[3].matches("^\\d+$") // PériodeFin
                    || !donnees[4].matches("^\\d+$") // nombre
                    || !donnees[5].matches("^#.*#$") // motClé
                    ) {
                    
                    return false;
                }
                
                /* On vérifie les format des indisponibilités */
                for (indiceVerif = 7;
                     indiceVerif < donnees.length 
                     && donnees[indiceVerif].matches(FORMAT_DATE_FR);
                     indiceVerif++) 
                    ; // empty body
                
                /* La vérification a du échoué sur une date */
                if (indiceVerif < donnees.length) {
                    return false;
                }
            } else if (lettreIdentifiant == 'R') { // Visite
                
                // TODO Vérification de Visite
            } else if (lettreIdentifiant == 'N') { // Employé
                
                // TODO Vérification de Employé
            } else if (lettreIdentifiant == 'C') { // Conférencier
                
                // TODO Vérification de Conférencier
            }
        }
        
        return true;
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

//        ArrayList<String[]> data = new ArrayList<>();
//        BufferedReader fichierCSV;
//        FileInputStream fileInputStream;
//        InputStreamReader inputStreamReader;
//        
//        String ligne;
//        String[] valeur;
//        String entete;
//
//        try {
//            fileInputStream = new FileInputStream(lienFichier);
//            inputStreamReader 
//                = new InputStreamReader(fileInputStream, 
//                                        StandardCharsets.ISO_8859_1);
//            fichierCSV = new BufferedReader(inputStreamReader);
//            entete = fichierCSV.readLine();
//            if (!entete.substring(0,5).equals("Ident") && entete != null) {
//                valeur = entete.split(";");
//                data.add(valeur);
//            } // else, on saute la ligne
//
//            while ((ligne = fichierCSV.readLine()) != null) {
//                valeur = ligne.split(";");
//                data.add(valeur);
//            }
//
//            fichierCSV.close();        
//
//        } catch (IOException pbLecture) {
//            throw new IOException(ERREUR_LECTURE_FICHIER, pbLecture);
//        }
//
//        return data;
        
        return null; // bouchon
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

//        char typeCSV;
//
//        typeCSV = donnee.get(0)[0].charAt(0);
//
//        if (typeCSV == 'E') { // Exposition
//            // creerExposition(donnee);
//        } else if (typeCSV == 'R') { // Visite
//            creerVisite(donnee);
//        } else if (typeCSV == 'N') { // Employé
//            creerEmploye(donnee);
//        } else if (typeCSV == 'C') { // Conférencier
//            creerConferencier(donnee);
//        } else {
//            throw new IllegalArgumentException(ERREUR_CONTENU_FICHIER);
//        }
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
    private static void creerExpositions(ArrayList<String> lignes) {
        
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
        
        String[] donnees;

        for (String ligne : lignes) {
            
            donnees = ligne.split(";");
            
            // Vérifier si la ligne n'est pas vide
            if (donnees.length > 0) {
                identifiant = donnees[0];
                intitule = donnees[1];
                periodeDebut = Integer.parseInt(donnees[2]);
                periodeFin = Integer.parseInt(donnees[3]);
                nbOeuvre = Integer.parseInt(donnees[4]);
                motsCles = donnees[5].replace("#", "").split(", ");
                resume = donnees[6];

                if (donnees.length == 7) { // Exposition normale

                    expo = new Exposition(identifiant, intitule, periodeDebut, 
                            periodeFin, nbOeuvre, motsCles,
                            resume);
                    expositions.add(expo);

                } else if (donnees.length == 9 ) { // Exposition temporaire
                    
                    dateDebut = LocalDate.parse(donnees[7], FORMATTER_DATE_FR);
                    dateFin = LocalDate.parse(donnees[8], FORMATTER_DATE_FR);

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
    private static void creerEmployes(ArrayList<String> lignes) {
        
        String identifiant, 
               nom,
               prenom,
               numTel;

        Employe employe;

        String[] donnees;

        for (String ligne : lignes) {

            donnees = ligne.split(";");

            // Vérifier si la ligne n'est pas vide
            if (donnees.length > 0) {
                identifiant = donnees[0];
                nom = donnees[1];
                prenom = donnees[2];
                numTel = donnees[3];
                if (donnees.length == 4) { 

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
    private static void creerConferenciers(ArrayList<String> lignes) {
        
         String identifiant,
                nom,
                prenom;
         String[] specialites;
         String numTel;
         boolean estInterne;
         
         Conferencier conferencier;
         Indisponibilite[] indisponibilites;
         
         String[] donnees;
         
         for (String ligne : lignes) {
             
             donnees = ligne.split(";");

             // Vérifier si la ligne n'est pas vide
             if (donnees.length > 0) {
                 identifiant = donnees[0];
                 nom = donnees[1];
                 prenom = donnees[2];
                 specialites = donnees[3].replace("#", "").split(", ");
                 numTel = donnees[4]; 
                 estInterne = donnees[5].equalsIgnoreCase("oui");
                 
                 
                 if (donnees.length == 6) { 
                     conferencier = new Conferencier(identifiant, nom, prenom, 
                                                     specialites, numTel,
                                                     estInterne);
                     conferenciers.add(conferencier);
                 } else if (donnees.length > 6){                     
                     
                     indisponibilites = creeIndisponibilité(donnees);
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
    private static void creerVisites(ArrayList<String> lignes) {
        
        String identifiant;
        int heureDebut;
        String[] decoupageHeureDebut;
        String intitule,
               numTel;

        LocalDate date;

        Client client;
        Exposition exposition;       
        Employe employe; 
        Conferencier conferencier;
        Visite visite;

        String[] donnees;

        for (String ligne : lignes) {
            
            donnees = ligne.split(";");
            
            // Vérifier si la ligne n'est pas vide
            if (donnees.length > 0) {
                identifiant = donnees[0];
                date = LocalDate.parse(donnees[4], FORMATTER_DATE_FR); 

                decoupageHeureDebut = donnees[5].split("h");
                heureDebut = Integer.parseInt(decoupageHeureDebut[0]) * 60 
                        + Integer.parseInt(decoupageHeureDebut[1]);

                // Chercher l'exposition par son identifiant
                exposition = chercherExposition(donnees[1]);
                // Chercher le conférencier par son identifiant
                conferencier = chercherConferencier(donnees[2]);
                // Chercher l'employé par son identifiant
                employe = chercherEmploye(donnees[3]);

                // Extraire l'intitulé et le numéro de téléphone
                intitule = donnees[6];
                numTel = donnees[7];

                // Vérifier si le client existe déjà
                client = trouverClient(intitule, numTel);
                if (client == null) {
                    // Si le client n'existe pas, en créer un nouveau
                    client = new Client(intitule, numTel);
                    clients.add(client); // Ajoute le nouveau client à la liste
                }

                // Créer l'objet Visite
                visite = new Visite(identifiant, heureDebut, date, client,
                        exposition, employe, conferencier);

                visites.add(visite);        
            } else {
                throw new IllegalArgumentException(ERREUR_NOMBRE_ARGUMENTS); 
            }
        }
    }
    
    /**
     * Crée un tableau de l'objet Indiponibilité
     * 
     * @param la ligne du tableau csv qui contient des indisponiblitées
     */
    private static Indisponibilite[] creeIndisponibilité(String[] donnees) {
        
        int nombreIndisponibilites;
        
        LocalDate dateDebut,
                  dateFin;
        
        Indisponibilite[] indisponibilites;
        
        nombreIndisponibilites = (donnees.length - 6) / 2;
        indisponibilites = new Indisponibilite[nombreIndisponibilites];

        for (int i = 0, debutIndex = 6;
             i < nombreIndisponibilites && !donnees[debutIndex].isBlank(); 
             i++, debutIndex += i * 2) {
            
            dateDebut = LocalDate.parse(donnees[debutIndex],
                                        FORMATTER_DATE_FR);

            // Si une date de fin est fournie
            if (debutIndex + 1 < donnees.length 
                    && !donnees[debutIndex + 1].isEmpty() 
                    && !donnees[debutIndex]
                            .equals(donnees[debutIndex + 1])) {
                
                dateFin = LocalDate.parse(donnees[debutIndex + 1],
                                          FORMATTER_DATE_FR);
                indisponibilites[i] 
                 = new Indisponibilite(dateDebut, dateFin);
            } else {
                // Ajouter seulement la date de début
                indisponibilites[i] 
                  = new Indisponibilite(dateDebut);
            }
        }
        return indisponibilites;
    }
    
    /**
     * Méthode pour chercher une Exposition par son identifiant
     * @param idExposition
     * @return l'objet Exposition
     * @throws IllegalArgumentException si aucune exposition n'est trouvée
     */
    private static Exposition chercherExposition(String idExposition) {
        for (Exposition expo : expositions) {
            if (expo.getIdentifiant().equals(idExposition)) {
                return expo;
            }
        }
        throw new IllegalArgumentException(ERREUR_EXPOSITION_INTROUVABLE);
    }
    
    /**
     * Méthode pour chercher un conferencier par son identifiant
     * @param idConferencier
     * @return l'objet Conferencier
     * @throws IllegalArgumentException si aucun conferencier n'est trouvé
     */
    private static Conferencier chercherConferencier(String idConferencier) {
        for (Conferencier conf : conferenciers) {
            if (conf.getIdentifiant().equals(idConferencier)) {
                return conf;
            }
        }
        throw new IllegalArgumentException(ERREUR_CONFERENCIER_INTROUVABLE);
    }
    
    /**
     * Méthode pour chercher un Employé par son identifiant
     * @param idEmploye
     * @return l'objet Employé
     * @throws IllegalArgumentException si aucun employé n'est trouvé
     */
    private static Employe chercherEmploye(String idEmploye) {
        for (Employe employe : employes) {
            if (employe.getIdentifiant().equals(idEmploye)) {
                return employe;
            }
        }
        throw new IllegalArgumentException(ERREUR_EMPLOYE_INTROUVABLE);
    }
    
    /**
     * Trouve un client existant dans la liste des clients.
     * 
     * @param intitule le nom ou la désignation du client
     * @param numTel le numéro de téléphone du client
     * @return l'objet Client si trouvé, sinon null
     */
    private static Client trouverClient(String intitule, String numTel) {
        for (Client client : clients) {
            if (client.getIntitule().equals(intitule) 
                && client.getNumTel().equals(numTel)) {
                return client; // Client trouvé
            }
        }
        return null;
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
    
    /**
     * Récupère la liste des clients traitées.
     * 
     * @return Une liste d'objets Client
     */
    public static List<Client> getClients() {
        return clients;
    }
    
    /**
     * Récupère la liste des visites traitées.
     * 
     * @return Une liste d'objets Visite
     */
    public static List<Visite> getVisites() {
        return visites;
    }
}
