/*
 * TestIndisponibilite.java                           
 * 10 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import application.modele.Indisponibilite;

/**
 * Classe de test pour {@link application.modele.Indisponibilite}
 * @author Esteban Vroemen
 * @version 1.0
 */
class TestIndisponibilite {
    
    private final LocalDate DATE_VALIDE = LocalDate.of(2024, 10, 9);
    
    private final Indisponibilite[] INDISPONIBILITES_VALIDES =
    {
        new Indisponibilite(LocalDate.of(2005, 5, 28)),
        new Indisponibilite(LocalDate.of(2005, 3, 21)),
        new Indisponibilite(LocalDate.of(2005, 8, 14)),
        new Indisponibilite(LocalDate.of(2005, 11, 20)),  
        new Indisponibilite(LocalDate.of(2005, 5, 28),
                            LocalDate.of(2005, 6, 28)),
        new Indisponibilite(LocalDate.of(2005, 3, 21),
                            LocalDate.of(2005, 4, 21)),
        new Indisponibilite(LocalDate.of(2005, 8, 14),
                            LocalDate.of(2005, 9, 14)),
        new Indisponibilite(LocalDate.of(2005, 11, 20),
                            LocalDate.of(2005, 12, 20)), 
    };

    /**
     * Méthode de test pour 
     * {@link application.modele.Indisponibilite#Indisponibilite(java.time.LocalDate)}
     * et
     * {@link application.modele.Indisponibilite#Indisponibilite(java.time.LocalDate, java.time.LocalDate)}
     * Cas uniquement invalides
     */
    @Test
    void testIndisponibiliteInvalide() {
        
        /*
         * Tests spécifiques au constructeur à 1 argument
         */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Indisponibilite(null));
        
        /*
         * Tests spécifiques au constructeur à 2 argument
         */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Indisponibilite(DATE_VALIDE, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Indisponibilite(null, DATE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Indisponibilite(DATE_VALIDE, DATE_VALIDE));
        assertThrows(IllegalArgumentException.class,
                     () -> new Indisponibilite(DATE_VALIDE,
                                               LocalDate.of(2005, 5, 28)));
        assertThrows(IllegalArgumentException.class,
                     () -> new Indisponibilite(DATE_VALIDE,
                                               LocalDate.of(2005, 3, 21)));
        assertThrows(IllegalArgumentException.class,
                     () -> new Indisponibilite(DATE_VALIDE,
                                               LocalDate.of(2005, 8, 14)));
        assertThrows(IllegalArgumentException.class,
                     () -> new Indisponibilite(DATE_VALIDE,
                                               LocalDate.of(2005, 11, 20)));
    }

    /**
     * Méthode de test pour 
     * {@link application.modele.Indisponibilite#Indisponibilite(java.time.LocalDate)}
     * et
     * {@link application.modele.Indisponibilite#Indisponibilite(java.time.LocalDate, java.time.LocalDate)}
     * Cas uniquement valides
     */
    @Test
    void testIndisponibiliteValide() {
        
        /*
         * Tests spécifiques au constructeur à 1 argument
         */
        assertDoesNotThrow(() -> new Indisponibilite(DATE_VALIDE));
        assertDoesNotThrow(() -> new Indisponibilite(LocalDate.of(2005, 5, 28)));
        assertDoesNotThrow(() -> new Indisponibilite(LocalDate.of(2005, 3, 21)));
        assertDoesNotThrow(() -> new Indisponibilite(LocalDate.of(2005, 8, 14)));
        assertDoesNotThrow(() -> new Indisponibilite(LocalDate.of(2005, 11, 20)));
        
        /*
         * Tests spécifiques au constructeur à 2 argument
         */
        assertDoesNotThrow(() -> new Indisponibilite(LocalDate.of(2005, 5, 28),
                                                     DATE_VALIDE));
        assertDoesNotThrow(() -> new Indisponibilite(LocalDate.of(2005, 3, 21),
                                                     DATE_VALIDE));
        assertDoesNotThrow(() -> new Indisponibilite(LocalDate.of(2005, 8, 14),
                                                     DATE_VALIDE));
        assertDoesNotThrow(() -> new Indisponibilite(LocalDate.of(2005, 11, 20),
                                                     DATE_VALIDE));
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Indisponibilite#getDateFin()}.
     */
    @Test
    void testGetDateFin() {
        
        assertEquals(null, INDISPONIBILITES_VALIDES[0].getDateFin());
        assertEquals(null, INDISPONIBILITES_VALIDES[1].getDateFin());
        assertEquals(null, INDISPONIBILITES_VALIDES[2].getDateFin());
        assertEquals(null, INDISPONIBILITES_VALIDES[3].getDateFin());
        assertEquals(LocalDate.of(2005, 6, 28),
                     INDISPONIBILITES_VALIDES[4].getDateFin());
        assertEquals(LocalDate.of(2005, 4, 21),
                     INDISPONIBILITES_VALIDES[5].getDateFin());
        assertEquals(LocalDate.of(2005, 9, 14),
                     INDISPONIBILITES_VALIDES[6].getDateFin());
        assertEquals(LocalDate.of(2005, 12, 20),
                     INDISPONIBILITES_VALIDES[7].getDateFin());
        
        assertNotEquals(LocalDate.of(2005, 6, 28),
                        INDISPONIBILITES_VALIDES[0].getDateFin());
        assertNotEquals(LocalDate.of(2005, 4, 28),
                        INDISPONIBILITES_VALIDES[1].getDateFin());
        assertNotEquals(LocalDate.of(2005, 11, 28),
                        INDISPONIBILITES_VALIDES[2].getDateFin());
        assertNotEquals(LocalDate.of(2005, 9, 28),
                        INDISPONIBILITES_VALIDES[3].getDateFin());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[4].getDateFin());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[5].getDateFin());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[6].getDateFin());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[7].getDateFin());
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Indisponibilite#setDateFin(java.time.LocalDate)}.
     */
    @Test
    void testSetDateFin() {
        
        assertThrows(IllegalArgumentException.class, 
                    () -> INDISPONIBILITES_VALIDES[0].setDateFin(null));
        assertThrows(IllegalArgumentException.class, 
                    () -> INDISPONIBILITES_VALIDES[0].setDateFin(
                            LocalDate.of(2005, 4, 28)));
        assertThrows(IllegalArgumentException.class, 
                    () -> INDISPONIBILITES_VALIDES[1].setDateFin(
                            LocalDate.of(2005, 2, 21)));
        assertThrows(IllegalArgumentException.class, 
                    () -> INDISPONIBILITES_VALIDES[2].setDateFin(
                            LocalDate.of(2005, 7, 14)));
        assertThrows(IllegalArgumentException.class, 
                    () -> INDISPONIBILITES_VALIDES[3].setDateFin(
                            LocalDate.of(2005, 10, 20)));
        assertThrows(IllegalArgumentException.class, 
                    () -> INDISPONIBILITES_VALIDES[0].setDateFin(
                            LocalDate.of(2005, 5, 28)));
        assertThrows(IllegalArgumentException.class, 
                    () -> INDISPONIBILITES_VALIDES[1].setDateFin(
                            LocalDate.of(2005, 3, 21)));
        assertThrows(IllegalArgumentException.class, 
                    () -> INDISPONIBILITES_VALIDES[2].setDateFin(
                            LocalDate.of(2005, 8, 14)));
        assertThrows(IllegalArgumentException.class, 
                    () -> INDISPONIBILITES_VALIDES[3].setDateFin(
                            LocalDate.of(2005, 11, 20)));
        
        assertDoesNotThrow(() -> INDISPONIBILITES_VALIDES[0].setDateFin(
                LocalDate.of(2005, 6, 28)));
        assertDoesNotThrow(() -> INDISPONIBILITES_VALIDES[1].setDateFin(
                LocalDate.of(2005, 4, 21)));
        assertDoesNotThrow(() -> INDISPONIBILITES_VALIDES[2].setDateFin(
                LocalDate.of(2005, 9, 14)));
        assertDoesNotThrow(() -> INDISPONIBILITES_VALIDES[3].setDateFin(
                LocalDate.of(2005, 12, 20)));
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Indisponibilite#getDateDebut()}.
     */
    @Test
    void testGetDateDebut() {
        
        assertEquals(LocalDate.of(2005, 5, 28),
                     INDISPONIBILITES_VALIDES[0].getDateDebut());
        assertEquals(LocalDate.of(2005, 3, 21),
                     INDISPONIBILITES_VALIDES[1].getDateDebut());
        assertEquals(LocalDate.of(2005, 8, 14),
                     INDISPONIBILITES_VALIDES[2].getDateDebut());
        assertEquals(LocalDate.of(2005, 11, 20),
                     INDISPONIBILITES_VALIDES[3].getDateDebut());
        assertEquals(LocalDate.of(2005, 5, 28),
                     INDISPONIBILITES_VALIDES[4].getDateDebut());
        assertEquals(LocalDate.of(2005, 3, 21),
                     INDISPONIBILITES_VALIDES[5].getDateDebut());
        assertEquals(LocalDate.of(2005, 8, 14),
                     INDISPONIBILITES_VALIDES[6].getDateDebut());
        assertEquals(LocalDate.of(2005, 11, 20),
                     INDISPONIBILITES_VALIDES[7].getDateDebut());
        
        assertNotEquals(null, INDISPONIBILITES_VALIDES[0].getDateDebut());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[1].getDateDebut());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[2].getDateDebut());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[3].getDateDebut());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[4].getDateDebut());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[5].getDateDebut());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[6].getDateDebut());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[7].getDateDebut());
        assertNotEquals(LocalDate.of(2005, 6, 28),
                        INDISPONIBILITES_VALIDES[0].getDateDebut());
        assertNotEquals(LocalDate.of(2005, 4, 28),
                        INDISPONIBILITES_VALIDES[1].getDateDebut());
        assertNotEquals(LocalDate.of(2005, 11, 28),
                        INDISPONIBILITES_VALIDES[2].getDateDebut());
        assertNotEquals(LocalDate.of(2005, 9, 28),
                        INDISPONIBILITES_VALIDES[3].getDateDebut());
        assertNotEquals(LocalDate.of(2005, 6, 28),
                        INDISPONIBILITES_VALIDES[4].getDateDebut());
        assertNotEquals(LocalDate.of(2005, 4, 28),
                        INDISPONIBILITES_VALIDES[5].getDateDebut());
        assertNotEquals(LocalDate.of(2005, 11, 28),
                        INDISPONIBILITES_VALIDES[6].getDateDebut());
        assertNotEquals(LocalDate.of(2005, 9, 28),
                        INDISPONIBILITES_VALIDES[7].getDateDebut());

    }

}
