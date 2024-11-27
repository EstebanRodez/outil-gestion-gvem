/*
 * ExportationCSVException.java                           
 * 15 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

/**
 * Exception pouvant être générée pendant l'exportation des données
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class ExportationCSVException extends Exception {
    
    /** Pour l'interface Serializable de la classe Exception */
    private static final long serialVersionUID = 1L;
    
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

}
