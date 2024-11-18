/*
 * VisiteMoyenneResultat.java                           
 * 2 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class VisiteCalculResultat {
    
    private String intitule;
    private double calculVisites;
    private String calculVisitesPourcentage;

    /**
     * TODO commenter l'état initial
     * @param intitule
     * @param calculVisites 
     */
    public VisiteCalculResultat(String intitule, double calculVisites) {
        this.intitule = intitule;
        this.calculVisites = calculVisites;
    }
    
    /**
     * TODO commenter l'état initial
     * @param intitule
     * @param calculVisites 
     */
    public VisiteCalculResultat(String intitule, String calculVisites) {
        this.intitule = intitule;
        this.calculVisitesPourcentage = calculVisites;
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return l'intitule
     */
    public String getIntitule() {
        return intitule;
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return le calcul sur les visites
     */
    public double getCalculVisites() {
        return calculVisites;
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return le calcul sur les visites
     */
    public String getCalculVisitesPourcentage() {
        return calculVisitesPourcentage;
    }
}
