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
public class VisiteCalculResultat {
    
    private String intituleExposition;
    private double calculVisites;

    /**
     * TODO commenter l'état initial
     * @param intituleExposition
     * @param calculVisites 
     */
    public VisiteCalculResultat(String intituleExposition, double calculVisites) {
        this.intituleExposition = intituleExposition;
        this.calculVisites = calculVisites;
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
     * @return le calcul sur les visites
     */
    public double getCalculVisites() {
        return calculVisites;
    }
}
