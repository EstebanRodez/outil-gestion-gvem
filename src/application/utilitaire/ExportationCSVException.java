/*
 * ExportationCSVException.java                           
 * 15 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class ExportationCSVException extends Exception {
    
    /**
     * Initialise l'exception sans message d'erreur
     */
    public ExportationCSVException() {
        super();
    }

    /**
     * Initialise l'exception avec un message d'erreur
     * @param message le message d'erreur à transmettre
     */
    public ExportationCSVException(String message) {
        super(message);
    }

    /** Pour l'interface Serializable de la classe Exception */
    private static final long serialVersionUID = 1L;
}
