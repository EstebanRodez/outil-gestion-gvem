package application.utilitaire;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
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
 * @autor Romain Augé
 * @version 1.0
 */
public class ImportationCSV {

    // Messages d'erreur
    private static final String ERREUR_NOMBRE_ARGUMENTS 
    = "Erreur: Le nombre de colonne est incorrect.";

    private static final String ERREUR_CONTENU_FICHIER 
    = "Erreur: Le fichier csv n'est ni des expositions, ni des visites, "
      + "ni des employés et ni des conferenciers.";

    private static final String ERREUR_LECTURE_FICHIER 
    = "Erreur : Une erreur d'entrée/sortie est survenue lors de la "
      + "lecture/écriture du fichier.";
    
    private static final String ERREUR_CONFERENCIER_INTROUVABLE 
    = "Erreur : Aucun conferencier n'est associé à l'identifiant indiqué.";
    
    private static final String ERREUR_EMPLOYE_INTROUVABLE 
    = "Erreur : Aucun employé n'est associé à l'identifiant indiqué.";
      
    private static final String ERREUR_EXPOSITION_INTROUVABLE 
    = "Erreur : Aucune exposition n'est associée à l'identifiant indiqué.";

    final static DateTimeFormatter formatter
    = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static ArrayList<Exposition> expositions = new ArrayList<>();
    
    private static ArrayList<Employe> employes = new ArrayList<>();
    
    private static ArrayList<Conferencier> conferenciers = new ArrayList<>();
    
    private static ArrayList<Client> clients = new ArrayList<>();
    
    private static ArrayList<Visite> visites = new ArrayList<>();

    /**
     * Importe les données d'un fichier normalement en .csv
     * @param cheminFichier le chemin du fichier
     */
    public static void importerDonnees(String cheminFichier) {
        try {
            BufferedReader fichierCSV;
            if (isFichierBinaire(cheminFichier)) {
                String contenuDecrypte = decrypterFichierBinaire(cheminFichier);
                fichierCSV = new BufferedReader(new StringReader(contenuDecrypte));
            } else {
                fichierCSV = new BufferedReader(new FileReader(cheminFichier));
            }
            parcourirFichier(fichierCSV);
            fichierCSV.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Erreur : Fichier non trouvé.", e);
        } catch (IOException e) {
            throw new IllegalArgumentException(ERREUR_LECTURE_FICHIER, e);
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
    private static boolean isFichierValide(String cheminFichier) throws IOException {
        return cheminFichier != null && !cheminFichier.isBlank() 
                && Files.size(Path.of(cheminFichier)) != 0
                && isExtensionCSV(cheminFichier);
    }
    

    private static boolean isFichierBinaire(String cheminFichier) throws IOException {
        // Vérification simple pour un fichier binaire crypté par taille et contenu
        return Files.size(Path.of(cheminFichier)) > 0 && cheminFichier.endsWith(".bin");
    }
    /**
     * Vérifie si un fichier contient l'extension .csv
     * 
     * @param cheminFichier le chemin du fichier à vérifier
     * @return true si le fichier a l'extension .csv sinon false
     */
    private static boolean isExtensionCSV(String cheminFichier) {
        return cheminFichier.endsWith(".csv");
    }
    
    private static String decrypterFichierBinaire(String cheminFichier) throws IOException {
        try (FileInputStream fis = new FileInputStream(cheminFichier)) {
            byte[] bytes = fis.readAllBytes();
            byte[] decryptedBytes = Base64.getDecoder().decode(bytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IOException("Erreur lors du décryptage du fichier binaire.", e);
        }
    }

    /**
     * Parcours le fichier CSV ligne à ligne.
     * Enregistre la ligne en fonction du type de données et si la
     * ligne est valide.
     * 
     * @param fichierCSV le fichier CSV ouvert
     * @throws IOException 
     */
    private static void parcourirFichier(BufferedReader fichierCSV) throws IOException {
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
     * @return true si la ligne est valide, sinon false
     */
    private static boolean verifierLigne(String ligne) {
        // TODO Vérifier la validité de la ligne
        return true; // Placeholder, remplacer par une vraie logique de validation
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
    public static ArrayList<String[]> importer(String lienFichier) throws IOException {
        ArrayList<String[]> data = new ArrayList<>();
        BufferedReader fichierCSV;
        if (isFichierBinaire(lienFichier)) {
            String contenuDecrypte = decrypterFichierBinaire(lienFichier);
            fichierCSV = new BufferedReader(new StringReader(contenuDecrypte));
        } else {
            fichierCSV = new BufferedReader(new InputStreamReader(new FileInputStream(lienFichier), StandardCharsets.ISO_8859_1));
        }
        String ligne;
        String entete = fichierCSV.readLine();
        if (entete != null && !entete.startsWith("Ident")) {
            data.add(entete.split(";"));
        }
        while ((ligne = fichierCSV.readLine()) != null) {
            data.add(ligne.split(";"));
        }
        fichierCSV.close();
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
        char typeCSV = donnee.get(0)[0].charAt(0);
        switch (typeCSV) {
            case 'E' -> creerExposition(donnee);
            case 'R' -> creerVisite(donnee);
            case 'N' -> creerEmploye(donnee);
            case 'C' -> creerConferencier(donnee);
            default -> throw new IllegalArgumentException(ERREUR_CONTENU_FICHIER);
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
                 estInterne = ligne[5].equalsIgnoreCase("oui");
                 
                 
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
         
              
        for (String[] ligne : donnee) {
         // Vérifier si la ligne n'est pas vide
            if (ligne.length > 0) {
                identifiant = ligne[0];
                date = LocalDate.parse(ligne[4], formatter); 
                
                decoupageHeureDebut = ligne[5].split("h");
                heureDebut = Integer.parseInt(decoupageHeureDebut[0]) * 60 
                             + Integer.parseInt(decoupageHeureDebut[1]);
                
                // Chercher l'exposition par son identifiant
                exposition = chercherExposition(ligne[1]);
                // Chercher le conférencier par son identifiant
                conferencier = chercherConferencier(ligne[2]);
                // Chercher l'employé par son identifiant
                employe = chercherEmploye(ligne[3]);
                
                // Extraire l'intitulé et le numéro de téléphone
                intitule = ligne[6];
                numTel = ligne[7];
                
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
