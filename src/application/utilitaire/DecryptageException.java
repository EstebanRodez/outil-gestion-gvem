/**
 * FichierDonneesInvalides.java
 * 23 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

/**
 * TODO
 * 
 * @author Esteban Vroemen
 * @version 1.0
 */
public class DecryptageException extends Exception {
    
    /**
     * Initialise l'exception sans message d'erreur
     */
    public DecryptageException() {
        super();
    }
    
    /**
     * Initialise l'exception avec un message d'erreur
     * @param erreurContenuFichier le message d'erreur à transmettre
     */
    public DecryptageException(String erreurContenuFichier) {
        super(erreurContenuFichier);
    }

    /** Pour l'interface Serializable de la classe Exception */
    private static final long serialVersionUID = 1L;

}
