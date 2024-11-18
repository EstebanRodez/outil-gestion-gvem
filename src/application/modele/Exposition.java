/*
 * Exposition.java                           
 * 8 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

import java.io.Serializable;

/**
 * Une exposition, qu'elle soit temporaire ou permanente, sera
 * décrite par :<br>
 * - un intitulé,<br>
 * - la période que ses oeuvres recouvrent (deux années),<br>
 * - le nombre d’œuvres,<br>
 * - des mots-clés (au plus 10)<br>
 * - et un résumé constitué de quelques lignes de texte.<br>
 * Par exemple, l'exposition "Les paysages impressionnistes" 
 * regroupera 20 tableaux datés de 1880 à 1895 et sera décrite par
 * les mots-clés : paysage, impressionnisme, Monet, Pissarro, Sysley
 * et Signac. Le résumé pourrait être « Très belle exposition mettant
 * en évidence les approches différentes de grands peintres du
 * mouvement impressionniste ». Pour une exposition temporaire,
 * on connaîtra, en plus des éléments précédents, la date de début
 * et la date de fin de celle-ci. Par exemple, du 17 mars 2025
 * au 21 juin 2025.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class Exposition implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private static String ERREUR_INTITULE_INVALIDE =
    """
    Impossible de créer une exposition.
    L'intitulé de l'exposition ne doit pas être nul ou vide.                
    """;
    
    private static String ERREUR_PERIODE_INVALIDE =
    """
    Impossible de créer une exposition.
    L'année de début de la période ne doit pas être supérieur
    à l'année de fin de la période.                
    """;
    
    private static String ERREUR_NBOEUVRE_INVALIDE =
    """
    Impossible de créer une exposition.
    Le nombre d'oeuvre dans l'exposition ne doit pas être nul ou vide.                
    """;
    
    private static String ERREUR_MOTSCLES_INVALIDE =
    """
    Impossible de créer une exposition.
    La liste de mots clés de l'exposition ne doit pas être nul ou vide.                
    """;
    
    private static String ERREUR_RESUME_INVALIDE =
    """
    Impossible de créer une exposition.
    Le résumé de l'exposition ne doit pas être nul ou vide.                
    """;
    
    private String intitule;
    
    private int periodeDebut;
    
    private int periodeFin;
    
    private int nbOeuvre;
    
    private String[] motsCles;
    
    private String resume;

    /**
     * Initialise une nouvelle exposition avec un identifiant,
     * un intitulé, une période avec une année de début et une année
     * de fin, un nombre d'oeuvres, une liste de mots clés et un
     * résumé.
     * 
     * @param intitule le nom ou la désignation de l'exposition
     * @param periodeDebut l'année de début que les oeuvres de
     *                     l'exposition recouvrent
     * @param periodeFin l'année de fin que les oeuvres de
     *                   l'exposition recouvrent
     * @param nbOeuvre le nombre d'oeuvre présentes dans l'exposition
     * @param motsCles liste de mots clés permettant de retrouver
     *                 l'exposition
     * @param resume le résumé de l'exposition
     * @throws IllegalArgumentException si la référence de l'intitulé
     *                                  est nulle ou l'intitulé est
     *                                  vide
     * @throws IllegalArgumentException si l'année de la période de
     *                                  début est supérieur à l'année
     *                                  de fin
     * @throws IllegalArgumentException si le nombre d'oeuvres est
     *                                  négatif ou nul
     * @throws IllegalArgumentException si la référence de la liste
     *                                  des mots clés est nulle, la
     *                                  liste est vide ou sa taille
     *                                  est supérieur à 10
     * @throws IllegalArgumentException si l'une des valeurs de la
     *                                  liste des mots clés est
     *                                  invalide
     * @throws IllegalArgumentException si la référence du résumé est
     *                                  nulle ou le résumé est vide
     */
    public Exposition (String intitule, int periodeDebut, int periodeFin,
                       int nbOeuvre, String[] motsCles, String resume) {
        
        if (intitule == null || intitule.isBlank()) {
            throw new IllegalArgumentException(ERREUR_INTITULE_INVALIDE);
        }
        
        if (periodeDebut > periodeFin) {
            throw new IllegalArgumentException(ERREUR_PERIODE_INVALIDE);
        }
        
        if (nbOeuvre <= 0) {
            throw new IllegalArgumentException(ERREUR_NBOEUVRE_INVALIDE);
        }
        
        if (motsCles == null || motsCles.length == 0 || motsCles.length > 10) {
            throw new IllegalArgumentException(ERREUR_MOTSCLES_INVALIDE);   
        }
        
        /* Vérification de la validité de chaque mot clé */
        for (int i = 0; i < motsCles.length ; i++) {
            if (motsCles[i] == null || motsCles[i].isBlank()){
                throw new IllegalArgumentException(
                        ERREUR_MOTSCLES_INVALIDE);
            }
        }
        
        if (resume == null || resume.isBlank()) {
            throw new IllegalArgumentException(ERREUR_RESUME_INVALIDE);
        }

        this.intitule = intitule.trim();
        this.periodeDebut = periodeDebut;
        this.periodeFin = periodeFin;
        this.nbOeuvre = nbOeuvre;
        this.motsCles = motsCles;
        this.resume = resume.trim();
    }

    /**
     * Récupère l'intitulé de l'exposition
     * @return l'intitulé de l'exposition
     */
    public String getIntitule() {
        return intitule;
    }

    /**
     * Récupère l'année de début de la période de l'exposition
     * @return l'année de début de la période de l'exposition
     */
    public int getPeriodeDebut() {
        return periodeDebut;
    }

    /**
     * Récupère l'année de fin de la période de l'exposition
     * @return l'année de fin de la période de l'exposition
     */
    public int getPeriodeFin() {
        return periodeFin;
    }

    /**
     * Récupère le nombre d'oeuvres présentes dans l'exposition
     * @return le nombre d'oeuvres de l'exposition
     */
    public int getNbOeuvre() {
        return nbOeuvre;
    }

    /**
     * Récupère les mots clés définissant l'exposition
     * @return les mots clés de l'exposition
     */
    public String[] getMotsCles() {
        return motsCles;
    }

    /**
     * Récupère le résumé de l'exposition
     * @return le résumé de l'exposition
     */
    public String getResume() {
        return resume;
    }
    
    /**
     * Renvoie la liste des mots clés en une chaîne de caractères,
     * chaque mot clé est séparé par une virgule.
     * 
     * @return la chaîne de caractères contenant les mots clés
     */
    public String toStringMotsCles() {
        return String.join(", ", motsCles);
    }

}
