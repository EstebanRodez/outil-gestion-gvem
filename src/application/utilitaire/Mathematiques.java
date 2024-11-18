/*
 * Mathematiques.java                           
 * 12 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

/**
 * Classe utilitaire offrant diverses méthodes mathématiques, 
 * incluant la recherche de nombres premiers, la génération de nombres
 * aléatoires, et des calculs modulo pour les groupes multiplicatifs.
 * Utilisée pour effectuer des opérations avancées et optimisées
 * sur les entiers.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class Mathematiques {
    
    private static final String ERREUR_MIN_MAX_EGAUX =
    """
    Impossible de générer un nombre aléatoire.
    La borne minimale ne doit pas être égale à la borne maximum.
    """;
    
    private static final String ERREUR_MIN_SUPERIEUR_MAX =
    """
    Impossible de générer un nombre aléatoire.
    La borne minimale ne doit pas être supérieure à la borne maximum.
    """;

    private static final String ERREUR_EXPOSANT_NEGATIF =
    """
    Impossible de procéder au calcul exponentielle modulaire.
    L'exposant ne doit pas être négatif.
    """;

    private static final String ERREUR_MODULO_NEGATIF_NUL =
    """
    Impossible de procéder au calcul exponentielle modulaire.
    Le modulo ne doit pas être négatif ou nul.
    """;

    /**
     * Recherche le premier nombre premier supérieur ou égal au nombre donné.
     * Si le nombre donné est négatif, une exception est levée.
     *
     * @param min la valeur minimale pour commencer la recherche du
     *            nombre premier.
     * @return le plus petit nombre premier supérieur à min.
     */
    public static int trouverNombrePremier(int min) {
        
        if (min <= 2) {
            return 2;
        }
        
        int candidat = min + 1;
        
        while (!estNombrePremier(candidat)) {
            candidat++;
        }

        return candidat;
    }
    
    /**
     * Détermine si un nombre donné est premier.
     * Un nombre premier est un nombre entier supérieur à 1,
     * divisible uniquement par lui-même et 1.
     *
     * @param nombre le nombre à vérifier.
     * @return true si le nombre est premier, sinon false.
     */
    public static boolean estNombrePremier(int nombre) {
        
        if (nombre <= 1) return false;
        if (nombre <= 3) return true; // 2 et 3 sont premiers
        if (nombre % 2 == 0 || nombre % 3 == 0) return false;

        // Crible d'Ératosthène
        for (int i = 5; i * i <= nombre; i += 6) {
            if (nombre % i == 0 || nombre % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Génère un nombre aléatoire compris entre deux valeurs inclusives.
     *
     * @param min la borne inférieure.
     * @param max la borne supérieure.
     * @return un entier aléatoire compris entre min et max inclus.
     * @throws IllegalArgumentException
     * @throws IllegalArgumentException
     */
    public static int genererNombreAleatoire(int min, int max) {
        
        if (min == max) {
            throw new IllegalArgumentException(ERREUR_MIN_MAX_EGAUX);
        }
        
        if (min > max) {
            throw new IllegalArgumentException(ERREUR_MIN_SUPERIEUR_MAX);
        }
        
        return min + (int) (Math.random() * ((max - min) + 1));
    }
    
    /**
     * Trouve le premier élément du groupe multiplicatif pour un ensemble donné.
     * Un groupe multiplicatif est un ensemble d'éléments de 1 à (n-1),
     * tels que chaque élément a un inverse multiplicatif sous modulo n.
     *
     * @param valeurEnsemble la taille de l'ensemble
     *                       (doit être un entier positif).
     * @return la première valeur ayant un inverse multiplicatif dans cet
     *         ensemble, ou -1 si aucun groupe multiplicatif n'est trouvé.
     */
    public static int trouverPremierGroupeMultiplicatif(int valeurEnsemble) {
        
        for (int valeur = 2; valeur < valeurEnsemble; valeur++) {
            
            int exposant = 1;
            for (int calcul = valeur;
                 exposant < valeurEnsemble && calcul != 1;
                 exposant++,
                 calcul = (calcul*valeur) % valeurEnsemble) ;
            
            if (exposant == valeurEnsemble-1) {
                return valeur;
            }
        }
        
        return -1;
    }
    
    /**
     * Trouve le dernier élément du groupe multiplicatif pour un ensemble donné.
     * Un groupe multiplicatif est un ensemble d'éléments de 1 à (n-1),
     * tels que chaque élément a un inverse multiplicatif sous modulo n.
     *
     * @param valeurEnsemble la taille de l'ensemble
     *                       (doit être un entier positif).
     * @return la première valeur ayant un inverse multiplicatif dans
     *         cet ensemble, ou -1 si aucun groupe multiplicatif
     *         n'est trouvé.
     */
    public static int trouverDernierGroupeMultiplicatif(int valeurEnsemble) {
        
        for (int valeur = valeurEnsemble-1; valeur > 1; valeur--) {
            
            int exposant = 1;
            for (int calcul = valeur;
                 exposant < valeurEnsemble && calcul != 1;
                 exposant++, calcul = (calcul*valeur) % valeurEnsemble)
                ; // empty body
            
            if (exposant == valeurEnsemble-1) {
                return valeur;
            }
        }
        
        return -1;
    }
    
    /**
     * Calcule la puissance d'un nombre élevé à un exposant dans le cadre
     * d'une opération modulaire, utilisant l'exponentiation rapide.
     *
     * @param nombre la base de l'exponentiation.
     * @param exposant l'exposant.
     * @param modulo la valeur modulaire.
     * @return le résultat de (nombre^exposant) % modulo.
     * @throws IllegalArgumentException si l'exposant est négatif.
     * @throws IllegalArgumentException
     */
    public static int calculExponentielleModulo(int nombre, int exposant,
                                                int modulo) {
        
        if (exposant < 0) {
            throw new IllegalArgumentException(ERREUR_EXPOSANT_NEGATIF);
        }
        
        if (modulo <= 0) {
            throw new IllegalArgumentException(ERREUR_MODULO_NEGATIF_NUL);
        }
        
        long resultat = 1;
        long nb = nombre;
        int exp = exposant;
        while (exp > 0) {
            
            if (exp % 2 == 1) {
                resultat = (resultat * nb) % modulo;
            }
            nb = (nb * nb) % modulo;
            exp /= 2;
        }
        
        return (int) resultat;
    }
}
