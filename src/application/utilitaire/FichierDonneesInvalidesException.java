/**
 * FichierDonneesInvalides.java
 * 23 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

/**
 * Exception générée si un fichier .csv contient des données incorrectes
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class FichierDonneesInvalidesException extends Exception {

    /** Pour l'interface Serializable de la classe Exception */
    private static final long serialVersionUID = 1L;
    
    /**
     * Initialise l'exception sans message d'erreur
     */
    public FichierDonneesInvalidesException() {
        super();
    }
    
    /**
     * Initialise l'exception avec un message d'erreur
     * @param erreurContenuFichier le message d'erreur à transmettre
     */
    public FichierDonneesInvalidesException(String erreurContenuFichier) {
        super(erreurContenuFichier);
    }

}
