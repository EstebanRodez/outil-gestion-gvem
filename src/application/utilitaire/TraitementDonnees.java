/*
 * TraitementDonnees.java                           
 * 25 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import application.modele.Client;
import application.modele.Conferencier;
import application.modele.Donnees;
import application.modele.Employe;
import application.modele.Exposition;
import application.modele.ExpositionTemporaire;
import application.modele.Indisponibilite;
import application.modele.Visite;

/**
 * Classe responsable du traitement des données importées depuis des
 * fichiers CSV pour créer des objets représentant des expositions,
 * employés, conférenciers, clients, et visites, et pour gérer leurs
 * listes.
 * 
 * @author Ayoub Laluti
 * @author Esteban Vroemen
 * @version 1.0
 */
public class TraitementDonnees {
    
    private static final String ERREUR_CONFERENCIER_INTROUVABLE 
    = " Erreur : Aucun conferencier n'est associé a l'identifiant indiqué";
    
    private static final String ERREUR_EMPLOYE_INTROUVABLE 
    = " Erreur : Aucun employé n'est associé a l'identifiant indiqué";
      
    private static final String ERREUR_EXPOSITION_INTROUVABLE 
    = " Erreur : Aucune exposition n'est associé a l'identifiant indiqué";
    
    private final static DateTimeFormatter FORMATTER_DATE_FR
    = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static Donnees donnees = new Donnees();
    
//    private static LinkedHashMap<String, Exposition> expositions
//    = new LinkedHashMap<>();
//    
//    private static LinkedHashMap<String, Employe> employes
//    = new LinkedHashMap<>();
//    
//    private static LinkedHashMap<String, Conferencier> conferenciers
//    = new LinkedHashMap<>();
//    
//    private static ArrayList<Client> clients
//    = new ArrayList<>();
//    
//    private static LinkedHashMap<String, Visite> visites
//    = new LinkedHashMap<>();

    /**
     * Crée des objets Exposition ou ExpositionTemporaire à partir
     * des données fournies et les ajoute à la liste des expositions.
     * 
     * @param donneesLignes Une liste de tableaux de chaînes, chaque
     *                      tableau représentant une ligne CSV
     *                      contenant les informations d'une
     *                      exposition ou exposition temporaire.
     */
    protected static void creerExpositions(ArrayList<String[]> donneesLignes) {
        
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

        for (String[] donneesLigne : donneesLignes) {
            
            identifiant = donneesLigne[0];
            intitule = donneesLigne[1];
            periodeDebut = Integer.parseInt(donneesLigne[2]);
            periodeFin = Integer.parseInt(donneesLigne[3]);
            nbOeuvre = Integer.parseInt(donneesLigne[4]);
            motsCles = donneesLigne[5].replace("#", "").split(", ");
            resume = donneesLigne[6];
            
            if (donneesLigne.length == 7) { // Exposition permanente

                expo = new Exposition(identifiant, intitule, periodeDebut, 
                                      periodeFin, nbOeuvre, motsCles,
                                      resume);

            } else { // Exposition temporaire
                
                dateDebut = LocalDate.parse(donneesLigne[7], FORMATTER_DATE_FR);
                dateFin = LocalDate.parse(donneesLigne[8], FORMATTER_DATE_FR);

                expo = new ExpositionTemporaire(identifiant, intitule,
                                                periodeDebut, periodeFin,
                                                nbOeuvre, motsCles, resume,
                                                dateDebut, dateFin);
            }
            donnees.getExpositions().putLast(identifiant, expo);
        }
    }
    
    /**
     * Crée des objets Employe à partir des données fournies et les
     * ajoute à la liste des employés.
     * 
     * @param donneesLignes Une liste de tableaux de chaînes, chaque
     *                      tableau représentant une ligne CSV
     *                      contenant les informations d'un employé.
     */
    protected static void creerEmployes(ArrayList<String[]> donneesLignes) {
        
        String identifiant, 
               nom,
               prenom,
               numTel;

        Employe employe;

        for (String[] donneesLigne : donneesLignes) {

            identifiant = donneesLigne[0];
            nom = donneesLigne[1];
            prenom = donneesLigne[2];
            if (donneesLigne.length == 3 || donneesLigne[3].isBlank()) { 
                
                employe = new Employe(identifiant, nom, prenom);
            } else { 
                
                numTel = donneesLigne[3];
                employe = new Employe(identifiant, nom, prenom, numTel);   
            }
            donnees.getEmployes().putLast(identifiant, employe);
        }     
    }
    
    /**
     * Crée des objets Conferencier à partir des données fournies et
     * les ajoute à la liste des conférenciers.
     * 
     * @param donneesLignes Une liste de tableaux de chaînes, chaque
     *                      tableau représentant une ligne CSV
     *                      contenant les informations d'un
     *                      conférencier.
     */
    protected static void creerConferenciers(ArrayList<String[]> donneesLignes) {
        
        String identifiant,
               nom,
               prenom;
        String[] specialites;
        String numTel;
        boolean estInterne;

        Conferencier conferencier;
        Indisponibilite[] indisponibilites;

        for (String[] donneesLigne : donneesLignes) {

            identifiant = donneesLigne[0];
            nom = donneesLigne[1];
            prenom = donneesLigne[2];
            specialites = donneesLigne[3].replace("#", "").split(", ");
            numTel = donneesLigne[4]; 
            estInterne = donneesLigne[5].equalsIgnoreCase("oui");

            if (donneesLigne.length == 6 
                || donneesLigne[6].isBlank()
                || donneesLigne[7].isBlank()) {

                conferencier = new Conferencier(identifiant, nom, prenom, 
                                                specialites, numTel,
                                                estInterne);
            } else {                     

                indisponibilites = creeIndisponibilité(donneesLigne);
                conferencier = new Conferencier(identifiant, nom, prenom, 
                                                specialites, numTel, 
                                                estInterne, indisponibilites);   
            }
            donnees.getConferenciers().putLast(identifiant, conferencier);
        }
    }
    
    /**
     * Crée des objets Visite à partir des données fournies et les
     * ajoute à la liste des visites.
     * 
     * @param donneesLignes Une liste de tableaux de chaînes, chaque
     *                      tableau représentant une ligne CSV
     *                      contenant les informations d'une visite
     */
    protected static void creerVisites(ArrayList<String[]> donneesLignes) {
        
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

        for (String[] donneesLigne : donneesLignes) {
            
            identifiant = donneesLigne[0];
            date = LocalDate.parse(donneesLigne[4], FORMATTER_DATE_FR); 

            decoupageHeureDebut = donneesLigne[5].split("h");
            heureDebut = Integer.parseInt(decoupageHeureDebut[0]) * 60 
                         + Integer.parseInt(decoupageHeureDebut[1]);

            // Chercher l'exposition par son identifiant
            exposition = donnees.getExpositions().get(donneesLigne[1]);
            if (exposition == null) {
                throw new IllegalArgumentException(
                        ERREUR_EXPOSITION_INTROUVABLE);
            }
            
            // Chercher le conférencier par son identifiant
            conferencier = donnees.getConferenciers().get(donneesLigne[2]);
            if (conferencier == null) {
                throw new IllegalArgumentException(
                        ERREUR_CONFERENCIER_INTROUVABLE);
            }
            
            // Chercher l'employé par son identifiant
            employe = donnees.getEmployes().get(donneesLigne[3]);
            if (employe == null) {
                throw new IllegalArgumentException(ERREUR_EMPLOYE_INTROUVABLE);
            }

            // Extraire l'intitulé et le numéro de téléphone
            intitule = donneesLigne[6];
            numTel = donneesLigne[7];

            // Vérifier si le client existe déjà
            client = trouverClient(intitule, numTel);
            if (client == null) {
                
                // Si le client n'existe pas, en créer un nouveau
                client = new Client(intitule, numTel);
                donnees.getClients().add(client);
            }

            // Créer l'objet Visite
            visite = new Visite(identifiant, heureDebut, date, client,
                    exposition, employe, conferencier);

            donnees.getVisites().putLast(identifiant, visite);        
        }
    }
    
    /**
     * Crée un tableau d'objets Indisponibilite à partir des données
     * fournies.
     * 
     * @param donnees Un tableau de chaînes représentant les
     *                indisponibilités, avec des paires de dates de
     *                début et de fin.
     * @return Un tableau d'objets Indisponibilite.
     */
    private static Indisponibilite[] creeIndisponibilité(String[] donnees) {
        
        int nbIndisponibilites,
            indice;
        
        LocalDate dateDebut,
                  dateFin;
        
        Indisponibilite[] indisponibilites;
        
        for (nbIndisponibilites = 0, indice = 6;
             indice+1 < donnees.length
             && !donnees[indice].isBlank()
             && !donnees[indice+1].isBlank();
             nbIndisponibilites++, indice += 2)
            ; // empty body
        
        indisponibilites = new Indisponibilite[nbIndisponibilites];
        for (int i = 0, indiceDebut = 6;
             i < nbIndisponibilites;
             i++, indiceDebut += 2) {
            
            dateDebut = LocalDate.parse(donnees[indiceDebut],
                                        FORMATTER_DATE_FR);

            // Si une date de fin est fournie
            if (!donnees[indiceDebut].equals(donnees[indiceDebut + 1])) {
                
                dateFin = LocalDate.parse(donnees[indiceDebut + 1],
                                          FORMATTER_DATE_FR);
                indisponibilites[i] = new Indisponibilite(dateDebut, dateFin);
            } else {
                
                // Ajouter seulement la date de début
                indisponibilites[i] = new Indisponibilite(dateDebut);
            }
        }
        
        return indisponibilites;
    }
    
    /**
     * Cherche une exposition dans la liste des expositions par son
     * identifiant.
     * 
     * @param idExposition L'identifiant unique de l'exposition
     *                     recherchée.
     * @return L'objet Exposition correspondant à l'identifiant.
     * @throws IllegalArgumentException si aucune exposition avec cet
     *                                  identifiant n'est trouvée.
     */
//    private static Exposition chercherExposition(String idExposition) {
//        for (Exposition expo : expositions) {
//            if (expo.getIdentifiant().equals(idExposition)) {
//                return expo;
//            }
//        }
//        throw new IllegalArgumentException(ERREUR_EXPOSITION_INTROUVABLE);
//    }
    
    /**
     * Cherche un conférencier dans la liste des conférenciers par
     * son identifiant.
     * 
     * @param idConferencier L'identifiant unique du conférencier
     *                       recherché.
     * @return L'objet Conferencier correspondant à l'identifiant.
     * @throws IllegalArgumentException si aucun conférencier avec
     *                                  cet identifiant n'est trouvé.
     */
//    private static Conferencier chercherConferencier(String idConferencier) {
//        for (Conferencier conf : conferenciers) {
//            if (conf.getIdentifiant().equals(idConferencier)) {
//                return conf;
//            }
//        }
//        throw new IllegalArgumentException(ERREUR_CONFERENCIER_INTROUVABLE);
//    }
    
    /**
     * Cherche un employé dans la liste des employés par son identifiant.
     * 
     * @param idEmploye L'identifiant unique de l'employé recherché.
     * @return L'objet Employe correspondant à l'identifiant.
     * @throws IllegalArgumentException si aucun employé avec cet identifiant
     *                                  n'est trouvé.
     */
//    private static Employe chercherEmploye(String idEmploye) {
//        for (Employe employe : employes) {
//            if (employe.getIdentifiant().equals(idEmploye)) {
//                return employe;
//            }
//        }
//        throw new IllegalArgumentException(ERREUR_EMPLOYE_INTROUVABLE);
//    }
    
    /**
     * Cherche un client existant dans la liste des clients en
     * fonction de son intitule (nom) et de son numéro de téléphone.
     * 
     * @param intitule Le nom ou la désignation du client.
     * @param numTel Le numéro de téléphone du client.
     * @return L'objet Client si un client correspondant est trouvé,
     *         sinon null.
     */
    private static Client trouverClient(String intitule, String numTel) {
        for (Client client : donnees.getClients()) {
            if (client.getIntitule().equals(intitule) 
                && client.getNumTel().equals(numTel)) {
                return client; // Client trouvé
            }
        }
        return null;
    }

//    /**
//     * Récupère la liste des expositions traitées.
//     * 
//     * @return Une liste d'objets Exposition
//     */
//    public static LinkedHashMap<String, Exposition> getExpositions() {
//        return expositions;
//    }
//    
//    /**
//     * Récupère la liste des employés traitées.
//     * 
//     * @return Une liste d'objets Employe
//     */
//    public static LinkedHashMap<String, Employe> getEmployes() {
//        return employes;
//    }
//    
//    /**
//     * Récupère la liste des conférenciers traitées.
//     * 
//     * @return Une liste d'objets Conferencier
//     */
//    public static LinkedHashMap<String, Conferencier> getConferenciers() {
//        return conferenciers;
//    }
//    
//    /**
//     * Récupère la liste des clients traitées.
//     * 
//     * @return Une liste d'objets Client
//     */
//    public static ArrayList<Client> getClients() {
//        return clients;
//    }
//    
//    /**
//     * Récupère la liste des visites traitées.
//     * 
//     * @return Une liste d'objets Visite
//     */
//    public static LinkedHashMap<String, Visite> getVisites() {
//        return visites;
//    }
//    
//    /**
//     * @param expositions
//     */
//    public static void setExpositions(
//            LinkedHashMap<String, Exposition> expositions) {
//        TraitementDonnees.expositions = expositions;
//    }
//
//    /**
//     * @param employes
//     */
//    public static void setEmployes(LinkedHashMap<String, Employe> employes) {
//        TraitementDonnees.employes = employes;
//    }
//
//    /**
//     * @param conferenciers
//     */
//    public static void setConferenciers(
//            LinkedHashMap<String, Conferencier> conferenciers) {
//        TraitementDonnees.conferenciers = conferenciers;
//    }
//
//    /**
//     * @param clients
//     */
//    public static void setClients(ArrayList<Client> clients) {
//        TraitementDonnees.clients = clients;
//    }
//
//    /**
//     * @param visites
//     */
//    public static void setVisites(LinkedHashMap<String, Visite> visites) {
//        TraitementDonnees.visites = visites;
//    }
    
    /**
     * Récupère les données stockées en mémoire.
     * 
     * @return donnees les données de l'application
     */
    public static Donnees getDonnees() {
        return donnees;
    }
    
    /**
     * Définit les données stockées en mémoire.
     * 
     * @param donnees les données à stocker
     */
    public static void setDonnees(Donnees donnees) {
        TraitementDonnees.donnees = donnees;
    }

    /**
     * Vérifie si un identifiant donné est unique dans sa catégorie
     * d'éléments.
     * 
     * @param identifiant à vérifier
     * @return true si l'identifiant est unique dans sa liste
     *         correspondante sinon false
     */
    protected static boolean isIdentifiantUnique(String identifiant) {
        
        char lettreIdentifiant = identifiant.charAt(0);
        if (lettreIdentifiant == 'E') { // Exposition
            return !donnees.getExpositions().containsKey(identifiant);
        } else if (lettreIdentifiant == 'R') { // Visite
            return !donnees.getVisites().containsKey(identifiant);
        } else if (lettreIdentifiant == 'N') { // Employé
            return !donnees.getVisites().containsKey(identifiant);
        } else { // Forcément Conférencier
            return !donnees.getConferenciers().containsKey(identifiant);
        }
    }

    /**
     * Supprime les données stockées en mémoire.
     * Opération irréversible.
     */
    public static void supprimerDonnees() {
        
        donnees = null;
    }
}
