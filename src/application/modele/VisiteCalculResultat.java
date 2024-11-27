/*
 * VisiteMoyenneResultat.java                           
 * 2 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

/**
 * Cette classe représente les résultats calculés pour une visite.
 * Elle encapsule les informations sur un intitulé 
 * (par exemple, le nom d'une catégorie ou d'une statistique) 
 * et un calcul associé, soit sous forme de valeur numérique brute, 
 * soit sous forme de pourcentage formaté.
 * Son rôle est de stocker et de fournir ces informations calculées 
 * pour les utiliser dans d'autres parties de l'application.
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
     * Constructeur permettant d'initialiser un objet avec un intitulé
     * et un calcul numérique.
     * @param intitule
     * @param calculVisites 
     */
    public VisiteCalculResultat(String intitule, double calculVisites) {
        this.intitule = intitule;
        this.calculVisites = calculVisites;
    }
    
    /**
     * Constructeur permettant d'initialiser un objet avec un intitulé 
     * et un calcul formaté en pourcentage.
     * @param intitule
     * @param calculVisites 
     */
    public VisiteCalculResultat(String intitule, String calculVisites) {
        this.intitule = intitule;
        this.calculVisitesPourcentage = calculVisites;
    }

    /**
     * Retourne l'intitulé associé à ce résultat.
     * @return l'intitule
     */
    public String getIntitule() {
        return intitule;
    }

    /**
     * Retourne la valeur numérique brute des calculs sur les visites.
     * Cette méthode est utilisée si le calcul a été initialisé en tant
     * que valeur numérique.
     * @return le calcul sur les visites
     */
    public double getCalculVisites() {
        return calculVisites;
    }
    
    /**
     * Retourne la valeur des calculs sur les visites formatée en pourcentage.
     * Cette méthode est utilisée si le calcul a été initialisé sous forme de 
     * chaîne pour un pourcentage.
     * @return le calcul sur les visites
     */
    public String getCalculVisitesPourcentage() {
        return calculVisitesPourcentage;
    }
}
