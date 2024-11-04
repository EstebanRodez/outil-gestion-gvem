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
     * {@link application.modele.Indisponibilite#Indisponibilite(
     * java.time.LocalDate)}
     * et
     * {@link application.modele.Indisponibilite#Indisponibilite(
     * java.time.LocalDate, java.time.LocalDate)}
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
     * {@link application.modele.Indisponibilite#Indisponibilite(
     * java.time.LocalDate)}
     * et
     * {@link application.modele.Indisponibilite#Indisponibilite(
     * java.time.LocalDate, java.time.LocalDate)}
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
    
    /**
     * Méthode de test pour
     * {@link application.modele.Indisponibilite#equals()}.
     */
    @Test
    void testEquals() {
        
        assertTrue(INDISPONIBILITES_VALIDES[0].equals(
                INDISPONIBILITES_VALIDES[0]));
        assertTrue(INDISPONIBILITES_VALIDES[1].equals(
                INDISPONIBILITES_VALIDES[1]));
        assertTrue(INDISPONIBILITES_VALIDES[2].equals(
                INDISPONIBILITES_VALIDES[2]));
        assertTrue(INDISPONIBILITES_VALIDES[3].equals(
                INDISPONIBILITES_VALIDES[3]));
        assertTrue(INDISPONIBILITES_VALIDES[4].equals(
                INDISPONIBILITES_VALIDES[4]));
        assertTrue(INDISPONIBILITES_VALIDES[5].equals(
                INDISPONIBILITES_VALIDES[5]));
        assertTrue(INDISPONIBILITES_VALIDES[6].equals(
                INDISPONIBILITES_VALIDES[6]));
        assertTrue(INDISPONIBILITES_VALIDES[7].equals(
                INDISPONIBILITES_VALIDES[7]));
        assertTrue(INDISPONIBILITES_VALIDES[0].equals(
                new Indisponibilite(LocalDate.of(2005, 5, 28))));
        assertTrue(INDISPONIBILITES_VALIDES[1].equals(
                new Indisponibilite(LocalDate.of(2005, 3, 21))));
        assertTrue(INDISPONIBILITES_VALIDES[2].equals(
                new Indisponibilite(LocalDate.of(2005, 8, 14))));
        assertTrue(INDISPONIBILITES_VALIDES[3].equals(
                new Indisponibilite(LocalDate.of(2005, 11, 20))));
        assertTrue(INDISPONIBILITES_VALIDES[4].equals(
                new Indisponibilite(LocalDate.of(2005, 5, 28),
                                    LocalDate.of(2005, 6, 28))));
        assertTrue(INDISPONIBILITES_VALIDES[5].equals(
                new Indisponibilite(LocalDate.of(2005, 3, 21),
                                    LocalDate.of(2005, 4, 21))));
        assertTrue(INDISPONIBILITES_VALIDES[6].equals(
                new Indisponibilite(LocalDate.of(2005, 8, 14),
                                    LocalDate.of(2005, 9, 14))));
        assertTrue(INDISPONIBILITES_VALIDES[7].equals(
                new Indisponibilite(LocalDate.of(2005, 11, 20),
                                    LocalDate.of(2005, 12, 20))));
        
        assertFalse(INDISPONIBILITES_VALIDES[0].equals(null));
        assertFalse(INDISPONIBILITES_VALIDES[1].equals(null));
        assertFalse(INDISPONIBILITES_VALIDES[2].equals(null));
        assertFalse(INDISPONIBILITES_VALIDES[3].equals(null));
        assertFalse(INDISPONIBILITES_VALIDES[4].equals(null));
        assertFalse(INDISPONIBILITES_VALIDES[5].equals(null));
        assertFalse(INDISPONIBILITES_VALIDES[6].equals(null));
        assertFalse(INDISPONIBILITES_VALIDES[7].equals(null));
        assertFalse(INDISPONIBILITES_VALIDES[0].equals(
                INDISPONIBILITES_VALIDES[1]));
        assertFalse(INDISPONIBILITES_VALIDES[1].equals(
                INDISPONIBILITES_VALIDES[2]));
        assertFalse(INDISPONIBILITES_VALIDES[2].equals(
                INDISPONIBILITES_VALIDES[3]));
        assertFalse(INDISPONIBILITES_VALIDES[3].equals(
                INDISPONIBILITES_VALIDES[4]));
        assertFalse(INDISPONIBILITES_VALIDES[4].equals(
                INDISPONIBILITES_VALIDES[5]));
        assertFalse(INDISPONIBILITES_VALIDES[5].equals(
                INDISPONIBILITES_VALIDES[6]));
        assertFalse(INDISPONIBILITES_VALIDES[6].equals(
                INDISPONIBILITES_VALIDES[7]));
        assertFalse(INDISPONIBILITES_VALIDES[7].equals(
                INDISPONIBILITES_VALIDES[0]));
        assertFalse(INDISPONIBILITES_VALIDES[0].equals("28/5/2005"));
        assertFalse(INDISPONIBILITES_VALIDES[1].equals("21/3/2005"));
        assertFalse(INDISPONIBILITES_VALIDES[2].equals("14/8/2005"));
        assertFalse(INDISPONIBILITES_VALIDES[3].equals("20/11/2005"));
        assertFalse(INDISPONIBILITES_VALIDES[4].equals("28/5/2005"));
        assertFalse(INDISPONIBILITES_VALIDES[5].equals("21/3/2005"));
        assertFalse(INDISPONIBILITES_VALIDES[6].equals("20/11/2005"));
        assertFalse(INDISPONIBILITES_VALIDES[7].equals("21/3/2005"));
        assertFalse(INDISPONIBILITES_VALIDES[0].equals(
                new Indisponibilite(LocalDate.of(2005, 5, 29))));
        assertFalse(INDISPONIBILITES_VALIDES[1].equals(
                new Indisponibilite(LocalDate.of(2005, 3, 22))));
        assertFalse(INDISPONIBILITES_VALIDES[2].equals(
                new Indisponibilite(LocalDate.of(2005, 8, 15))));
        assertFalse(INDISPONIBILITES_VALIDES[3].equals(
                new Indisponibilite(LocalDate.of(2005, 11, 21))));
        assertFalse(INDISPONIBILITES_VALIDES[4].equals(
                new Indisponibilite(LocalDate.of(2005, 5, 29),
                                    LocalDate.of(2005, 6, 28))));
        assertFalse(INDISPONIBILITES_VALIDES[5].equals(
                new Indisponibilite(LocalDate.of(2005, 3, 22),
                                    LocalDate.of(2005, 4, 21))));
        assertFalse(INDISPONIBILITES_VALIDES[6].equals(
                new Indisponibilite(LocalDate.of(2005, 8, 15),
                                    LocalDate.of(2005, 9, 14))));
        assertFalse(INDISPONIBILITES_VALIDES[7].equals(
                new Indisponibilite(LocalDate.of(2005, 11, 21),
                                    LocalDate.of(2005, 12, 20))));
        assertFalse(INDISPONIBILITES_VALIDES[0].equals(
                new Indisponibilite(LocalDate.of(2005, 5, 27))));
        assertFalse(INDISPONIBILITES_VALIDES[1].equals(
                new Indisponibilite(LocalDate.of(2005, 3, 20))));
        assertFalse(INDISPONIBILITES_VALIDES[2].equals(
                new Indisponibilite(LocalDate.of(2005, 8, 13))));
        assertFalse(INDISPONIBILITES_VALIDES[3].equals(
                new Indisponibilite(LocalDate.of(2005, 11, 19))));
        assertFalse(INDISPONIBILITES_VALIDES[4].equals(
                new Indisponibilite(LocalDate.of(2005, 5, 27),
                                    LocalDate.of(2005, 6, 28))));
        assertFalse(INDISPONIBILITES_VALIDES[5].equals(
                new Indisponibilite(LocalDate.of(2005, 3, 20),
                                    LocalDate.of(2005, 4, 21))));
        assertFalse(INDISPONIBILITES_VALIDES[6].equals(
                new Indisponibilite(LocalDate.of(2005, 8, 13),
                                    LocalDate.of(2005, 9, 14))));
        assertFalse(INDISPONIBILITES_VALIDES[7].equals(
                new Indisponibilite(LocalDate.of(2005, 11, 19),
                                    LocalDate.of(2005, 12, 20))));
        assertFalse(INDISPONIBILITES_VALIDES[4].equals(
                new Indisponibilite(LocalDate.of(2005, 5, 27),
                                    LocalDate.of(2005, 6, 29))));
        assertFalse(INDISPONIBILITES_VALIDES[5].equals(
                new Indisponibilite(LocalDate.of(2005, 3, 20),
                                    LocalDate.of(2005, 4, 22))));
        assertFalse(INDISPONIBILITES_VALIDES[6].equals(
                new Indisponibilite(LocalDate.of(2005, 8, 13),
                                    LocalDate.of(2005, 9, 15))));
        assertFalse(INDISPONIBILITES_VALIDES[7].equals(
                new Indisponibilite(LocalDate.of(2005, 11, 19),
                                    LocalDate.of(2005, 12, 21))));
        assertFalse(INDISPONIBILITES_VALIDES[4].equals(
                new Indisponibilite(LocalDate.of(2005, 5, 27),
                                    LocalDate.of(2005, 6, 27))));
        assertFalse(INDISPONIBILITES_VALIDES[5].equals(
                new Indisponibilite(LocalDate.of(2005, 3, 20),
                                    LocalDate.of(2005, 4, 20))));
        assertFalse(INDISPONIBILITES_VALIDES[6].equals(
                new Indisponibilite(LocalDate.of(2005, 8, 13),
                                    LocalDate.of(2005, 9, 13))));
        assertFalse(INDISPONIBILITES_VALIDES[7].equals(
                new Indisponibilite(LocalDate.of(2005, 11, 19),
                                    LocalDate.of(2005, 12, 20))));
    }
    
    /**
     * Méthode de test pour
     * {@link application.modele.Indisponibilite#toString()}.
     */
    @Test
    void testToString() {
        
        assertEquals("Le 28/5/2005",
                     INDISPONIBILITES_VALIDES[0].toString());
        assertEquals("Le 21/3/2005",
                     INDISPONIBILITES_VALIDES[1].toString());
        assertEquals("Le 14/8/2005",
                     INDISPONIBILITES_VALIDES[2].toString());
        assertEquals("Le 20/11/2005",
                     INDISPONIBILITES_VALIDES[3].toString());
        assertEquals("Du 28/5/2005 au 28/6/2005",
                     INDISPONIBILITES_VALIDES[4].toString());
        assertEquals("Du 21/3/2005 au 21/4/2005",
                     INDISPONIBILITES_VALIDES[5].toString());
        assertEquals("Du 14/8/2005 au 14/9/2005",
                     INDISPONIBILITES_VALIDES[6].toString());
        assertEquals("Du 20/11/2005 au 20/12/2005",
                     INDISPONIBILITES_VALIDES[7].toString());
        
        assertNotEquals(null, INDISPONIBILITES_VALIDES[0].toString());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[1].toString());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[2].toString());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[3].toString());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[4].toString());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[5].toString());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[6].toString());
        assertNotEquals(null, INDISPONIBILITES_VALIDES[7].toString());
        assertNotEquals("", INDISPONIBILITES_VALIDES[0].toString());
        assertNotEquals("", INDISPONIBILITES_VALIDES[1].toString());
        assertNotEquals("", INDISPONIBILITES_VALIDES[2].toString());
        assertNotEquals("", INDISPONIBILITES_VALIDES[3].toString());
        assertNotEquals("", INDISPONIBILITES_VALIDES[4].toString());
        assertNotEquals("", INDISPONIBILITES_VALIDES[5].toString());
        assertNotEquals("", INDISPONIBILITES_VALIDES[6].toString());
        assertNotEquals("", INDISPONIBILITES_VALIDES[7].toString());
        assertNotEquals("Le 27/5/2005", INDISPONIBILITES_VALIDES[0].toString());
        assertNotEquals("Le 20/3/2005", INDISPONIBILITES_VALIDES[1].toString());
        assertNotEquals("Le 13/8/2005", INDISPONIBILITES_VALIDES[2].toString());
        assertNotEquals("Le 19/11/2005",
                        INDISPONIBILITES_VALIDES[3].toString());
        assertNotEquals("Du 27/5/2005 au 28/6/2005",
                        INDISPONIBILITES_VALIDES[4].toString());
        assertNotEquals("Du 20/3/2005 au 21/4/2005",
                        INDISPONIBILITES_VALIDES[5].toString());
        assertNotEquals("Du 13/8/2005 au 14/09/2005",
                        INDISPONIBILITES_VALIDES[6].toString());
        assertNotEquals("Du 19/11/2005 au 20/12/2024",
                        INDISPONIBILITES_VALIDES[7].toString());
        assertNotEquals("Le 29/5/2005", INDISPONIBILITES_VALIDES[0].toString());
        assertNotEquals("Le 22/3/2005", INDISPONIBILITES_VALIDES[1].toString());
        assertNotEquals("Le 15/8/2005", INDISPONIBILITES_VALIDES[2].toString());
        assertNotEquals("Le 21/11/2005",
                        INDISPONIBILITES_VALIDES[3].toString());
        assertNotEquals("Du 29/5/2005 au 28/6/2005",
                        INDISPONIBILITES_VALIDES[4].toString());
        assertNotEquals("Du 22/3/2005 au 21/4/2005",
                        INDISPONIBILITES_VALIDES[5].toString());
        assertNotEquals("Du 15/8/2005 au 14/09/2005",
                        INDISPONIBILITES_VALIDES[6].toString());
        assertNotEquals("Du 21/11/2005 au 20/12/2024",
                        INDISPONIBILITES_VALIDES[7].toString());
        assertNotEquals("Du 28/5/2005 au 29/6/2005",
                        INDISPONIBILITES_VALIDES[4].toString());
        assertNotEquals("Du 21/3/2005 au 22/4/2005",
                        INDISPONIBILITES_VALIDES[5].toString());
        assertNotEquals("Du 14/8/2005 au 15/09/2005",
                        INDISPONIBILITES_VALIDES[6].toString());
        assertNotEquals("Du 20/11/2005 au 21/12/2024",
                        INDISPONIBILITES_VALIDES[7].toString());
        assertNotEquals("Du 28/5/2005 au 27/6/2005",
                        INDISPONIBILITES_VALIDES[4].toString());
        assertNotEquals("Du 21/3/2005 au 20/4/2005",
                        INDISPONIBILITES_VALIDES[5].toString());
        assertNotEquals("Du 14/8/2005 au 13/09/2005",
                        INDISPONIBILITES_VALIDES[6].toString());
        assertNotEquals("Du 20/11/2005 au 19/12/2024",
                        INDISPONIBILITES_VALIDES[7].toString());
    }
    
    /**
     * Méthode de test pour
     * {@link application.modele.Indisponibilite#hashCode()}.
     */
    @Test
    void testHashCode() {
        
        /*
         * Manière de calculer le hashCode d'une LocalDate :
         * (année & 0xFFFFF800) ^ ((année << 11) + (mois << 6) + (jour)
         */
        
        assertEquals(127305189, INDISPONIBILITES_VALIDES[0].hashCode());
        assertEquals(127301004, INDISPONIBILITES_VALIDES[1].hashCode());
        assertEquals(127310707, INDISPONIBILITES_VALIDES[2].hashCode());
        assertEquals(127316845, INDISPONIBILITES_VALIDES[3].hashCode());
        assertEquals(131411841, INDISPONIBILITES_VALIDES[4].hashCode());
        assertEquals(131407521, INDISPONIBILITES_VALIDES[5].hashCode());
        assertEquals(131417537, INDISPONIBILITES_VALIDES[6].hashCode());
        assertEquals(131423873, INDISPONIBILITES_VALIDES[7].hashCode());
        
        assertNotEquals(0, INDISPONIBILITES_VALIDES[0].hashCode());
        assertNotEquals(0, INDISPONIBILITES_VALIDES[1].hashCode());
        assertNotEquals(0, INDISPONIBILITES_VALIDES[2].hashCode());
        assertNotEquals(0, INDISPONIBILITES_VALIDES[3].hashCode());
        assertNotEquals(0, INDISPONIBILITES_VALIDES[4].hashCode());
        assertNotEquals(0, INDISPONIBILITES_VALIDES[5].hashCode());
        assertNotEquals(0, INDISPONIBILITES_VALIDES[6].hashCode());
        assertNotEquals(0, INDISPONIBILITES_VALIDES[7].hashCode());
        assertNotEquals("", INDISPONIBILITES_VALIDES[0].hashCode());
        assertNotEquals("", INDISPONIBILITES_VALIDES[1].hashCode());
        assertNotEquals("", INDISPONIBILITES_VALIDES[2].hashCode());
        assertNotEquals("", INDISPONIBILITES_VALIDES[3].hashCode());
        assertNotEquals("", INDISPONIBILITES_VALIDES[4].hashCode());
        assertNotEquals("", INDISPONIBILITES_VALIDES[5].hashCode());
        assertNotEquals("", INDISPONIBILITES_VALIDES[6].hashCode());
        assertNotEquals("", INDISPONIBILITES_VALIDES[7].hashCode());
        assertNotEquals(-127305189, INDISPONIBILITES_VALIDES[0].hashCode());
        assertNotEquals(-127301004, INDISPONIBILITES_VALIDES[1].hashCode());
        assertNotEquals(-127310707, INDISPONIBILITES_VALIDES[2].hashCode());
        assertNotEquals(-127316845, INDISPONIBILITES_VALIDES[3].hashCode());
        assertNotEquals(-131411841, INDISPONIBILITES_VALIDES[4].hashCode());
        assertNotEquals(-131407521, INDISPONIBILITES_VALIDES[5].hashCode());
        assertNotEquals(-131417537, INDISPONIBILITES_VALIDES[6].hashCode());
        assertNotEquals(-131423873, INDISPONIBILITES_VALIDES[7].hashCode());
        assertNotEquals(127305188, INDISPONIBILITES_VALIDES[0].hashCode());
        assertNotEquals(127301003, INDISPONIBILITES_VALIDES[1].hashCode());
        assertNotEquals(127310706, INDISPONIBILITES_VALIDES[2].hashCode());
        assertNotEquals(127316844, INDISPONIBILITES_VALIDES[3].hashCode());
        assertNotEquals(131411840, INDISPONIBILITES_VALIDES[4].hashCode());
        assertNotEquals(131407520, INDISPONIBILITES_VALIDES[5].hashCode());
        assertNotEquals(131417536, INDISPONIBILITES_VALIDES[6].hashCode());
        assertNotEquals(131423872, INDISPONIBILITES_VALIDES[7].hashCode());
        assertNotEquals(127305190, INDISPONIBILITES_VALIDES[0].hashCode());
        assertNotEquals(127301005, INDISPONIBILITES_VALIDES[1].hashCode());
        assertNotEquals(127310708, INDISPONIBILITES_VALIDES[2].hashCode());
        assertNotEquals(127316846, INDISPONIBILITES_VALIDES[3].hashCode());
        assertNotEquals(131411842, INDISPONIBILITES_VALIDES[4].hashCode());
        assertNotEquals(131407522, INDISPONIBILITES_VALIDES[5].hashCode());
        assertNotEquals(131417538, INDISPONIBILITES_VALIDES[6].hashCode());
        assertNotEquals(131423874, INDISPONIBILITES_VALIDES[7].hashCode());
    }
    
    /**
     * Méthode de test pour
     * {@link application.modele.Indisponibilite#compareTo(
     * application.modele.Indisponibilite)}.
     */
    @Test
    void testCompareTo() {
        
        assertEquals(
            2,
            INDISPONIBILITES_VALIDES[0].compareTo(INDISPONIBILITES_VALIDES[1])
        );
        assertEquals(
            -5,
            INDISPONIBILITES_VALIDES[1].compareTo(INDISPONIBILITES_VALIDES[2])
        );
        assertEquals(
            -3,
            INDISPONIBILITES_VALIDES[2].compareTo(INDISPONIBILITES_VALIDES[3])
        );
        assertEquals(
            6,
            INDISPONIBILITES_VALIDES[3].compareTo(INDISPONIBILITES_VALIDES[4])
        );
        assertEquals(
            2,
            INDISPONIBILITES_VALIDES[4].compareTo(INDISPONIBILITES_VALIDES[5])
        );
        assertEquals(
            -5,
            INDISPONIBILITES_VALIDES[5].compareTo(INDISPONIBILITES_VALIDES[6])
        );
        assertEquals(
            -3,
            INDISPONIBILITES_VALIDES[6].compareTo(INDISPONIBILITES_VALIDES[7])
        );
        assertEquals(
            6,
            INDISPONIBILITES_VALIDES[7].compareTo(INDISPONIBILITES_VALIDES[0])
        );
        assertEquals(
            -1,
            INDISPONIBILITES_VALIDES[0].compareTo(INDISPONIBILITES_VALIDES[4])
        );
        assertEquals(
            1,
            INDISPONIBILITES_VALIDES[4].compareTo(INDISPONIBILITES_VALIDES[0])
        );
        assertEquals(
            0,
            INDISPONIBILITES_VALIDES[0].compareTo(INDISPONIBILITES_VALIDES[0])
        );
        assertEquals(
            0,
            INDISPONIBILITES_VALIDES[4].compareTo(INDISPONIBILITES_VALIDES[4])
        );
        
        
        assertNotEquals(
            -2,
            INDISPONIBILITES_VALIDES[0].compareTo(INDISPONIBILITES_VALIDES[1])
        );
        assertNotEquals(
            5,
            INDISPONIBILITES_VALIDES[1].compareTo(INDISPONIBILITES_VALIDES[2])
        );
        assertNotEquals(
            3,
            INDISPONIBILITES_VALIDES[2].compareTo(INDISPONIBILITES_VALIDES[3])
        );
        assertNotEquals(
            -6,
            INDISPONIBILITES_VALIDES[3].compareTo(INDISPONIBILITES_VALIDES[4])
        );
        assertNotEquals(
            -2,
            INDISPONIBILITES_VALIDES[4].compareTo(INDISPONIBILITES_VALIDES[5])
        );
        assertNotEquals(
            5,
            INDISPONIBILITES_VALIDES[5].compareTo(INDISPONIBILITES_VALIDES[6])
        );
        assertNotEquals(
            3,
            INDISPONIBILITES_VALIDES[6].compareTo(INDISPONIBILITES_VALIDES[7])
        );
        assertNotEquals(
            -6,
            INDISPONIBILITES_VALIDES[7].compareTo(INDISPONIBILITES_VALIDES[0])
        );
        assertNotEquals(
            3,
            INDISPONIBILITES_VALIDES[0].compareTo(INDISPONIBILITES_VALIDES[1])
        );
        assertNotEquals(
            -4,
            INDISPONIBILITES_VALIDES[1].compareTo(INDISPONIBILITES_VALIDES[2])
        );
        assertNotEquals(
            -2,
            INDISPONIBILITES_VALIDES[2].compareTo(INDISPONIBILITES_VALIDES[3])
        );
        assertNotEquals(
            7,
            INDISPONIBILITES_VALIDES[3].compareTo(INDISPONIBILITES_VALIDES[4])
        );
        assertNotEquals(
            3,
            INDISPONIBILITES_VALIDES[4].compareTo(INDISPONIBILITES_VALIDES[5])
        );
        assertNotEquals(
            -4,
            INDISPONIBILITES_VALIDES[5].compareTo(INDISPONIBILITES_VALIDES[6])
        );
        assertNotEquals(
            -2,
            INDISPONIBILITES_VALIDES[6].compareTo(INDISPONIBILITES_VALIDES[7])
        );
        assertNotEquals(
            7,
            INDISPONIBILITES_VALIDES[7].compareTo(INDISPONIBILITES_VALIDES[0])
        );
        assertNotEquals(
            1,
            INDISPONIBILITES_VALIDES[0].compareTo(INDISPONIBILITES_VALIDES[1])
        );
        assertNotEquals(
            -6,
            INDISPONIBILITES_VALIDES[1].compareTo(INDISPONIBILITES_VALIDES[2])
        );
        assertNotEquals(
            -4,
            INDISPONIBILITES_VALIDES[2].compareTo(INDISPONIBILITES_VALIDES[3])
        );
        assertNotEquals(
            5,
            INDISPONIBILITES_VALIDES[3].compareTo(INDISPONIBILITES_VALIDES[4])
        );
        assertNotEquals(
            1,
            INDISPONIBILITES_VALIDES[4].compareTo(INDISPONIBILITES_VALIDES[5])
        );
        assertNotEquals(
            -6,
            INDISPONIBILITES_VALIDES[5].compareTo(INDISPONIBILITES_VALIDES[6])
        );
        assertNotEquals(
            -4,
            INDISPONIBILITES_VALIDES[6].compareTo(INDISPONIBILITES_VALIDES[7])
        );
        assertNotEquals(
            5,
            INDISPONIBILITES_VALIDES[7].compareTo(INDISPONIBILITES_VALIDES[0])
        );
        
    }

}
