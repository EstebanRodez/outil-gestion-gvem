/*
 * VisiteMoyenneResultat.java                           
 * 2 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class VisiteMoyenneResultat {
    
    private String intituleExposition;
    private double moyenneVisites;

    /**
     * TODO commenter l'état initial
     * @param intituleExposition
     * @param moyenneVisites
     */
    public VisiteMoyenneResultat(String intituleExposition, double moyenneVisites) {
        this.intituleExposition = intituleExposition;
        this.moyenneVisites = moyenneVisites;
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return l'intitule
     */
    public String getIntituleExposition() {
        return intituleExposition;
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return la moyenne
     */
    public double getMoyenneVisites() {
        return moyenneVisites;
    }
}
