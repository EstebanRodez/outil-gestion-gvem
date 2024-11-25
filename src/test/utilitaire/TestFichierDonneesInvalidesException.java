/*
 * TestFichierDonneesInvalidesException.java                           
 * 25 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package test.utilitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.utilitaire.FichierDonneesInvalidesException;

/**
 * Classe de Test pour
 * {@link application.utilitaire.FichierDonneesInvalidesException}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
class TestFichierDonneesInvalidesException {

    /**
     * Méthode de test pour
     * {@link application.utilitaire.FichierDonneesInvalidesException#FichierDonneesInvalidesException()}.
     */
    @Test
    void testFichierDonneesInvalidesException() {
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            throw new FichierDonneesInvalidesException();
        });
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.FichierDonneesInvalidesException#FichierDonneesInvalidesException(java.lang.String)}.
     */
    @Test
    void testFichierDonneesInvalidesExceptionString() {
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            throw new FichierDonneesInvalidesException("Message");
        });
    }

}
