/*
 * GenerationDonneeSecreteException.java                           
 * 15 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class GenerationDonneeSecreteException extends Exception {
    
    /**
     * Initialise l'exception sans message d'erreur
     */
    public GenerationDonneeSecreteException() {
        super();
    }

    /**
     * Initialise l'exception avec un message d'erreur
     * @param message le message d'erreur à transmettre
     */
    public GenerationDonneeSecreteException(String message) {
        super(message);
    }

    /** Pour l'interface Serializable de la classe Exception */
    private static final long serialVersionUID = 1L;
}
