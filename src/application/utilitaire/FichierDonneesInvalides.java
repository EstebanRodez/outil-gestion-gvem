/**
 * FichierDonneesInvalides.java
 * 23 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

/**
 * Exception générée si un fichier .csv contient des données incorrectes
 * 
 * @author Esteban Vroemen
 * @version 1.0
 */
public class FichierDonneesInvalides extends Exception {

    /**
     * Initialise l'exception avec un message d'erreur
     * @param erreurContenuFichier le message d'erreur à transmettre
     */
    public FichierDonneesInvalides(String erreurContenuFichier) {
        super(erreurContenuFichier);
    }

    /** Pour l'interface Serializable de la classe Exception */
    private static final long serialVersionUID = 1L;

}
