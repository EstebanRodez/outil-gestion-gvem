/*
 * TestGenerationDonneeSecreteException.java                           
 * 25 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package test.utilitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.utilitaire.GenerationDonneeSecreteException;

/**
 * Classe de Test pour
 * {@link application.utilitaire.GenerationDonneeSecreteException}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
class TestGenerationDonneeSecreteException {

    /**
     * Méthode de test pour
     * {@link application.utilitaire.GenerationDonneeSecreteException#FichierDonneesInvalidesException()}.
     */
    @Test
    void testFichierDonneesInvalidesException() {
        assertThrows(GenerationDonneeSecreteException.class, () -> {
            throw new GenerationDonneeSecreteException();
        });
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.GenerationDonneeSecreteException#FichierDonneesInvalidesException(java.lang.String)}.
     */
    @Test
    void testFichierDonneesInvalidesExceptionString() {
        assertThrows(GenerationDonneeSecreteException.class, () -> {
            throw new GenerationDonneeSecreteException("Message");
        });
    }

}
