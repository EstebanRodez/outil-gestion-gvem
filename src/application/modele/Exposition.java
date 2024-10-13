/*
 * Exposition.java                           
 * 8 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

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
 * @version 1.0
 */
public class Exposition {
    
    private static String ERREUR_IDENTIFIANT_INVALIDE =
    """
    Impossible de créer une exposition.
    L'identifiant de l'exposition ne doit pas être nul ou vide.                
    """;
    
    private static String ERREUR_INTITULE_INVALIDE =
    """
    Impossible de créer une exposition.
    L'intitulé de l'exposition ne doit pas être nul ou vide.                
    """;
    
    private static String ERREUR_PERIODE_INVALIDE =
    """
    Impossible de créer une exposition.
    La période de l'exposition ne doit pas être nul ou vide.                
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

    private String identifiant;
    
    private String intitule;
    
    private int periodeDeb;
    
    private int periodeFin;
    
    private int nbOeuvre;
    
    private String[] motsCles;
    
    private String resume;

    /**
     * Initialise une nouvelle exposition avec un identifiant,
     * un intitulé, une période, un nombre d'oeuvres, une liste de
     * mots clés et un résumé
     * @param identifiant l'identifiant/code de l'exposition
     * @param intitule le nom ou la désignation de l'exposition
     * @param periodeDeb est le début de la période durant laquelle
     *                   aura lieu l'exposition
     * @param periodeFin est la fin de la période durant laquelle aura
     *                   lieu l'exposition
     * @param nbOeuvre le nombre d'oeuvre présent dans l'exposition
     * @param motsCles liste de mots clés par lequels on peut
     *                 retrouver l'exposition
     * @param resume résumé de l'exposition
     * @throws IllegalArgumentException si la référence de
     *                                  l'identifiant est nul ou
     *                                  l'identifiant est vide
     * @throws IllegalArgumentException si la référence de l'intitulé
     *                                  est nul ou l'intitulé est
     *                                  vide
     * @throws IllegalArgumentException si la référence de est nul ou
     *                                  vide
     * @throws IllegalArgumentException si la référence du nombre
     *                                  d'oeuvre est nul ou vide
     * @throws IllegalArgumentException si la référence de la liste
     *                                  des mots clés est nul ou vide
     * @throws IllegalArgumentException si la référence du résumé est
     *                                  nul ou vide
     * @throws IllegalArgumentException si le nombre de mots clés est
     *                                  supérieur à 10
     */
    public Exposition (String identifiant,String intitule, int periodeDeb,
                       int periodeFin, int nbOeuvre, String[] motsCles,
                       String resume) {
        
        if (identifiant == null || identifiant.isBlank()) {
            throw new IllegalArgumentException(ERREUR_IDENTIFIANT_INVALIDE);
        }
        
        if (intitule == null || identifiant.isBlank()) {
            throw new IllegalArgumentException(ERREUR_INTITULE_INVALIDE);
        }
        
        if (periodeDeb <= 0 || periodeFin <= 0 || periodeDeb > periodeFin) {
            throw new IllegalArgumentException(ERREUR_PERIODE_INVALIDE);
        }
        
        if (nbOeuvre <= 0) {
            throw new IllegalArgumentException(ERREUR_NBOEUVRE_INVALIDE);
        }
        
        if (motsCles == null || motsCles.length == 0 || motsCles.length > 10) {
            
            throw new IllegalArgumentException(ERREUR_MOTSCLES_INVALIDE);
        } else {
            
            for (int i = 0; i < motsCles.length ; i++) {
                if (motsCles[i] == null || motsCles[i].isBlank()){
                    throw new IllegalArgumentException(
                            ERREUR_MOTSCLES_INVALIDE);
                }
            }
        }
        
        if (resume == null || resume.isBlank()) {
            throw new IllegalArgumentException(ERREUR_RESUME_INVALIDE);
        }

        this.identifiant = identifiant;
        this.intitule = intitule;
        this.periodeDeb = periodeDeb;
        this.periodeFin = periodeFin;
        this.nbOeuvre = nbOeuvre;
        this.motsCles = motsCles;
        this.resume = resume;
    }

    /**
     * Récupère l'identifiant de l'exposition
     * @return l'identifiant de l'exposition
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * Récupère l'idntitulé de l'exposition
     * @return l'intitulé de l'exposition
     */
    public String getIntitule() {
        return intitule;
    }

    /**
     * Récupère la période du début de l'exposition
     * @return la période du début de l'exposition
     */
    public int getPeriodeDeb() {
        return periodeDeb;
    }

    /**
     * Récupère la période de fin de l'exposition
     * @return la période de fin de l'exposition
     */
    public int getPeriodeFin() {
        return periodeFin;
    }

    /**
     * Récupère le nombre d'oeuvre présentent dans l'exposition
     * @return le nombre d'oeuvre de l'exposition
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
}
