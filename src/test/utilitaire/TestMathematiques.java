/*
 * TestMathematiques.java                           
 * 27 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package test.utilitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.utilitaire.Mathematiques;

/**
 * Classe de Test pour {@link application.utilitaire.Mathematiques}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
class TestMathematiques {

    /**
     * Méthode de test pour
     * {@link application.utilitaire.Mathematiques#trouverNombrePremier(int)}.
     */
    @Test
    void testTrouverNombrePremier() {
        
        // Test utile pour 100% couverture
        assertDoesNotThrow(() -> {
            new Mathematiques();
        });

        assertEquals(2, Mathematiques.trouverNombrePremier(0));
        assertEquals(2, Mathematiques.trouverNombrePremier(-10));
        assertEquals(7, Mathematiques.trouverNombrePremier(6));
        assertEquals(11, Mathematiques.trouverNombrePremier(10));
        assertEquals(11, Mathematiques.trouverNombrePremier(9));
        
        assertNotEquals(3, Mathematiques.trouverNombrePremier(0));
        assertNotEquals(3, Mathematiques.trouverNombrePremier(-10));
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.Mathematiques#estNombrePremier(int)}.
     */
    @Test
    void testEstNombrePremier() {
        
        assertTrue(Mathematiques.estNombrePremier(2));
        assertTrue(Mathematiques.estNombrePremier(3));
        assertTrue(Mathematiques.estNombrePremier(5));
        assertTrue(Mathematiques.estNombrePremier(7));
        assertTrue(Mathematiques.estNombrePremier(11));
        assertTrue(Mathematiques.estNombrePremier(13));
        assertTrue(Mathematiques.estNombrePremier(17));
        assertTrue(Mathematiques.estNombrePremier(19));
        assertTrue(Mathematiques.estNombrePremier(23));
        assertTrue(Mathematiques.estNombrePremier(29));
        assertTrue(Mathematiques.estNombrePremier(31));
        assertTrue(Mathematiques.estNombrePremier(37));
        assertTrue(Mathematiques.estNombrePremier(41));
        assertTrue(Mathematiques.estNombrePremier(43));
        assertTrue(Mathematiques.estNombrePremier(47));
        assertTrue(Mathematiques.estNombrePremier(53));
        assertTrue(Mathematiques.estNombrePremier(59));
        assertTrue(Mathematiques.estNombrePremier(61));
        assertTrue(Mathematiques.estNombrePremier(67));
        assertTrue(Mathematiques.estNombrePremier(71));
        assertTrue(Mathematiques.estNombrePremier(73));
        assertTrue(Mathematiques.estNombrePremier(79));
        assertTrue(Mathematiques.estNombrePremier(83));
        assertTrue(Mathematiques.estNombrePremier(89));
        
        assertFalse(Mathematiques.estNombrePremier(0));
        assertFalse(Mathematiques.estNombrePremier(4));
        assertFalse(Mathematiques.estNombrePremier(6));
        assertFalse(Mathematiques.estNombrePremier(8));
        assertFalse(Mathematiques.estNombrePremier(9));
        assertFalse(Mathematiques.estNombrePremier(10));
        assertFalse(Mathematiques.estNombrePremier(25));
        assertFalse(Mathematiques.estNombrePremier(32));
        assertFalse(Mathematiques.estNombrePremier(33));
        assertFalse(Mathematiques.estNombrePremier(34));
        assertFalse(Mathematiques.estNombrePremier(35));
        assertFalse(Mathematiques.estNombrePremier(36));
        assertFalse(Mathematiques.estNombrePremier(38));
        assertFalse(Mathematiques.estNombrePremier(39));
        assertFalse(Mathematiques.estNombrePremier(40));
        assertFalse(Mathematiques.estNombrePremier(42));
        assertFalse(Mathematiques.estNombrePremier(49));
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.Mathematiques#genererNombreAleatoire(int, int)}.
     */
    @Test
    void testGenererNombreAleatoire() {
        
        assertDoesNotThrow(() -> {
            assertNotNull(Mathematiques.genererNombreAleatoire(0, 10));
        });
        assertDoesNotThrow(() -> {
            assertNotNull(Mathematiques.genererNombreAleatoire(-10, 0));
        });
        assertDoesNotThrow(() -> {
            assertNotNull(Mathematiques.genererNombreAleatoire(-15, 20));
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            Mathematiques.genererNombreAleatoire(0, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Mathematiques.genererNombreAleatoire(30, 30);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Mathematiques.genererNombreAleatoire(-90, -90);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Mathematiques.genererNombreAleatoire(10, -5);
        });
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.Mathematiques#trouverPremierGroupeMultiplicatif(int)}.
     */
    @Test
    void testTrouverPremierGroupeMultiplicatif() {
        
        assertEquals(-1, Mathematiques.trouverPremierGroupeMultiplicatif(2));
        assertEquals(-1, Mathematiques.trouverPremierGroupeMultiplicatif(-9));
        assertEquals(2, Mathematiques.trouverPremierGroupeMultiplicatif(3));
        assertEquals(-1, Mathematiques.trouverPremierGroupeMultiplicatif(6));
        assertEquals(3, Mathematiques.trouverPremierGroupeMultiplicatif(7));
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.Mathematiques#trouverDernierGroupeMultiplicatif(int)}.
     */
    @Test
    void testTrouverDernierGroupeMultiplicatif() {
        
        assertEquals(-1, Mathematiques.trouverDernierGroupeMultiplicatif(2));
        assertEquals(-1, Mathematiques.trouverDernierGroupeMultiplicatif(-9));
        assertEquals(2, Mathematiques.trouverDernierGroupeMultiplicatif(3));
        assertEquals(-1, Mathematiques.trouverDernierGroupeMultiplicatif(6));
        assertEquals(5, Mathematiques.trouverDernierGroupeMultiplicatif(7));
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.Mathematiques#calculExponentielleModulo(int, int, int)}.
     */
    @Test
    void testCalculExponentielleModulo() {
        
        assertEquals(1, Mathematiques.calculExponentielleModulo(5, 2, 6));
        assertEquals(0, Mathematiques.calculExponentielleModulo(4, 8, 8));
        assertEquals(3, Mathematiques.calculExponentielleModulo(3, 6, 11));
        
        assertThrows(IllegalArgumentException.class, () -> {
            Mathematiques.calculExponentielleModulo(5, 2, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Mathematiques.calculExponentielleModulo(5, 2, -2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Mathematiques.calculExponentielleModulo(5, -1, 3);
        });
    }

}
