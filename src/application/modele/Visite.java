/*
 * Visite.java                           
 * 8 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

import java.time.LocalDate;

/**
 * Une visite est décrite par :<br>
 * - son identifiant
 * - l’exposition concernée<br>
 * - le conférencier qui va assurer cette visite<br>
 * - l’horaire de début de la visite<br>
 * - la date de la visite<br>
 * - le client qui a réservé la visite, sous la forme d’un intitulé<br>
 * et d’un numéro de téléphone. L’intitulé est un texte libre qui
 * pourrait être un nom et un prénom et/ou le nom d’un organisme
 * (par exemple « M.Jacques Dupont » ou « Les amis des musées de
 * Rodez » ou encore « M. Jacques Dupont pour les amis des musées
 * de Rodez »)
 * @author Esteban Vroemen
 * @version 1.0
 */
public class Visite {
    
    private static final String ERREUR_IDENTIFIANT_INVALIDE =
    """
    Impossible de créer une visite.
    La référence de l'identifiant de la visite ne doit pas être nulle ou
    l'identifiant ne doit pas être vide.                
    """;
    
    private static final String ERREUR_HORAIRE_DEBUT_INVALIDE =
    """
    Impossible de créer une visite.
    L'horaire de début de la visite ne doit pas être négatif ou supérieur à
    1439.
    Exemples : 0 = 0h00, 1439 = 23h59, 612 = 10h12
    """;
    
    private static final String ERREUR_DATE_INVALIDE =
    """
    Impossible de créer une visite.
    La référence de la date de la visite ne doit pas être nulle.                
    """;
    
    private static final String ERREUR_CLIENT_INVALIDE =
    """
    Impossible de créer une visite.
    La référence du client de la visite ne doit pas être nulle.                   
    """;
    
    private static final String ERREUR_EXPOSITION_INVALIDE =
    """
    Impossible de créer une visite.
    La référence de l'exposition de la visite ne doit pas être nulle.                
    """;
    
    private static final String ERREUR_EXPLOYE_INVALIDE =
    """
    Impossible de créer une visite.
    La référence de l'employé de la visite ne doit pas être nulle.                   
    """;
    
    private static final String ERREUR_CONFEFENCIER_INVALIDE =
    """
    Impossible de créer une visite.
    La référence du conférencier de la visite ne doit pas être nulle.                   
    """;
    
    private String identifiant;
    
    private int horaireDebut;
    
    private LocalDate date;
    
    private Client client;
    
    private Exposition exposition;
    
    private Employe employe;
    
    private Conferencier conferencier;

    /**
     * Initialise une visite avec son identifiant, son horaire de
     * début, sa date, le client, l'exposition, l'employé, et le
     * conférencier associés à cette visite.
     * 
     * @param identifiant l'identifiant de la visite
     * @param horaireDebut l'horaire de la visite
     *                     (en nombre de minutes)
     * @param date la date de la visite
     * @param client le client de la visite
     * @param exposition l'exposition de la visite
     * @param employe l'employé de la visite
     * @param conferencier le conférencier de la visite
     * @throws IllegalArgumentException si la référence de
     *                                  l'identifiant est nulle ou
     *                                  l'identifiant est vide
     * @throws IllegalArgumentException si la valeur de l'horaire de
     *                                  début est négative ou
     *                                  supérieur à 1439
     * @throws IllegalArgumentException si la référence de la date
     *                                  est nulle
     * @throws IllegalArgumentException si la référence du client est
     *                                  nulle
     * @throws IllegalArgumentException si la référence de
     *                                  l'exposition est nulle
     * @throws IllegalArgumentException si la référence de l'employe
     *                                  est nulle
     * @throws IllegalArgumentException si la référence du
     *                                  conférencier est nulle
     */
    public Visite(String identifiant, int horaireDebut, LocalDate date,
                  Client client, Exposition exposition, Employe employe,
                  Conferencier conferencier) {
        
        if (identifiant == null || identifiant.isBlank()) {
            throw new IllegalArgumentException(ERREUR_IDENTIFIANT_INVALIDE);
        }
        
        /* 
         * 24 * 60 = 1440
         * Il y a 1440 minutes dans une journée
         * 0 = 0h00, 1439 = 23h59
         */
        if (horaireDebut < 0 || horaireDebut >= 1440) {
            throw new IllegalArgumentException(ERREUR_HORAIRE_DEBUT_INVALIDE);
        }
        
        if (date == null) {
            throw new IllegalArgumentException(ERREUR_DATE_INVALIDE);
        }
        
        if (client == null) {
            throw new IllegalArgumentException(ERREUR_CLIENT_INVALIDE);
        }
        
        if (exposition == null) {
            throw new IllegalArgumentException(ERREUR_EXPOSITION_INVALIDE);
        }
        
        if (employe == null) {
            throw new IllegalArgumentException(ERREUR_EXPLOYE_INVALIDE);
        }
        
        if (conferencier == null) {
            throw new IllegalArgumentException(ERREUR_CONFEFENCIER_INVALIDE);
        }
        
        this.identifiant = identifiant.trim();
        this.horaireDebut = horaireDebut;
        this.date = date;
        this.client = client;
        this.exposition = exposition;
        this.employe = employe;
        this.conferencier = conferencier;
    }

    /**
     * Récupère l'identifiant de la visite.
     * @return identifiant l'identifiant de la visite
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * Récupère l'horaire de début de la visite.
     * @return horaireDebut l'horaire de début de la visite
     */
    public int getHoraireDebut() {
        return horaireDebut;
    }

    /**
     * Récupère la date de la visite.
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Récupère le client de la visite.
     * @return client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Récupère l'exposition de la visite.
     * @return exposition
     */
    public Exposition getExposition() {
        return exposition;
    }

    /**
     * Récupère l'employé de la visite.
     * @return employe
     */
    public Employe getEmploye() {
        return employe;
    }

    /**
     * Récupère le conférencier de la visite.
     * @return conferencier
     */
    public Conferencier getConferencier() {
        return conferencier;
    }
    
    /**
     * Renvoie l'horaire de début sous le format "heures"h"minutes"
     * Exemples : 360 : 6h00, 600 : 10h00, 1000 : 16h40
     * @return l'horaire de début de la visite avec le format
     */
    public String toStringHoraireDebut() {
        return horaireDebut/60 + "h" 
               + (horaireDebut%60 <= 9 ? "0" + horaireDebut%60
                                       : horaireDebut%60);
    }

}
