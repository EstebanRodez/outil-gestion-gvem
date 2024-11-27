/*
 * TestExportationCSVException.java                           
 * 25 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package test.utilitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.utilitaire.ExportationCSVException;

/**
 * Classe de Test pour
 * {@link application.utilitaire.ExportationCSVException}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
class TestExportationCSVException {

    /**
     * Méthode de test pour
     * {@link application.utilitaire.ExportationCSVException#FichierDonneesInvalidesException()}.
     */
    @Test
    void testFichierDonneesInvalidesException() {
        assertThrows(ExportationCSVException.class, () -> {
            throw new ExportationCSVException();
        });
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.ExportationCSVException#FichierDonneesInvalidesException(java.lang.String)}.
     */
    @Test
    void testFichierDonneesInvalidesExceptionString() {
        assertThrows(ExportationCSVException.class, () -> {
            throw new ExportationCSVException("Message");
        });
    }

}
