/*
 * Mathematiques.java                           
 * 12 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class Mathematiques {
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param min
     * @return nombre premier
     */
    public static int trouverNombrePremier(int min) {
        
        if (min < 0) {
            throw new IllegalArgumentException();
        }
        
        int candidat = min + 1;
        
        while (!estNombrePremier(candidat)) {
            candidat++;
        }

        return candidat;
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param nombre
     * @return le nombre est-il premier
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
     * TODO commenter le rôle de cette méthode (SRP)
     * @param min 
     * @param max 
     * @return un integer
     */
    public static int genererNombreAleatoire(int min, int max) {
        
        return min + (int) (Math.random() * ((max - min) + 1));
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param valeurEnsemble
     * @return le groupe multiplicatif
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
     * TODO commenter le rôle de cette méthode (SRP)
     * @param valeurEnsemble
     * @return le groupe multiplicatif
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
     * TODO commenter le rôle de cette méthode (SRP)
     * @param nombre
     * @param exposant
     * @param modulo
     * @return le calcul modulo
     */
    public static int calculExponentielleModulo(int nombre, int exposant,
                                                int modulo) {
        
        if (exposant < 0) {
            throw new IllegalArgumentException();
        }
        
        int resultat = 1;
        int nb = nombre;
        int exp = exposant;
        while (exp > 0) {
            
            if (exp % 2 == 1) {
                resultat = (resultat * nb) % modulo;
            }
            nb = (nb * nb) % modulo;
            exp /= 2;
        }
        
        return resultat;
    }
}
