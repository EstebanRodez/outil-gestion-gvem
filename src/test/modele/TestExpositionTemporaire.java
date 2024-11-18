/*
 * TestExpositionTemporaire.java                           
 * 14 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import application.modele.ExpositionTemporaire;

/**
 * Classe de test pour {@link application.modele.ExpositionTemporaire}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
class TestExpositionTemporaire {
    
    private final String INTITULE_EXPO_VALIDE = "Les paysages impressionnistes";
    
    private final int PERIODEDEB_EXPO_VALIDE = 1880;
    
    private final int PERIODEFIN_EXPO_VALIDE = 1895;
    
    private final int NBOEUVRE_EXPO_VALIDE = 20;
    
    private final String[] MOTSCLES_EXPO_VALIDE
    = {"paysage", "impressionnisme", "Monet", "Pissarro", "Sysley", "Signac"};
    
    private final String RESUME_EXPO_VALIDE
    = "Très belle exposition sur l'impressionnisme.";
    
    private final LocalDate DATE_VALIDE = LocalDate.of(2024, 10, 14);
    
    private final ExpositionTemporaire[] EXPOSITIONS_TEMP_VALIDES =
    {
        new ExpositionTemporaire(INTITULE_EXPO_VALIDE, PERIODEDEB_EXPO_VALIDE,
                                 PERIODEFIN_EXPO_VALIDE, NBOEUVRE_EXPO_VALIDE,
                                 MOTSCLES_EXPO_VALIDE, RESUME_EXPO_VALIDE,
                                 LocalDate.of(2005, 5, 28), DATE_VALIDE),
        new ExpositionTemporaire(INTITULE_EXPO_VALIDE, PERIODEDEB_EXPO_VALIDE,
                                 PERIODEFIN_EXPO_VALIDE, NBOEUVRE_EXPO_VALIDE,
                                 MOTSCLES_EXPO_VALIDE, RESUME_EXPO_VALIDE,
                                 LocalDate.of(2005, 3, 21), DATE_VALIDE),
        new ExpositionTemporaire(INTITULE_EXPO_VALIDE, PERIODEDEB_EXPO_VALIDE,
                                 PERIODEFIN_EXPO_VALIDE, NBOEUVRE_EXPO_VALIDE,
                                 MOTSCLES_EXPO_VALIDE, RESUME_EXPO_VALIDE,
                                 LocalDate.of(2005, 8, 14), DATE_VALIDE),
        new ExpositionTemporaire(INTITULE_EXPO_VALIDE, PERIODEDEB_EXPO_VALIDE,
                                 PERIODEFIN_EXPO_VALIDE, NBOEUVRE_EXPO_VALIDE,
                                 MOTSCLES_EXPO_VALIDE, RESUME_EXPO_VALIDE,
                                 LocalDate.of(2005, 11, 20), DATE_VALIDE),
    };

    /**
     * Méthode de test pour
     * {@link application.modele.ExpositionTemporaire#ExpositionTemporaire(
     * java.lang.String, java.lang.String, int, int, int, java.lang.String[], java.lang.String, java.time.LocalDate, java.time.LocalDate)}.
     * Cas uniquement invalides
     */
    @Test
    void testExpositionTemporaireInvalide() {
        
        assertThrows(IllegalArgumentException.class, 
                     () -> new ExpositionTemporaire(INTITULE_EXPO_VALIDE,
                                                    PERIODEDEB_EXPO_VALIDE,
                                                    PERIODEFIN_EXPO_VALIDE,
                                                    NBOEUVRE_EXPO_VALIDE,
                                                    MOTSCLES_EXPO_VALIDE,
                                                    RESUME_EXPO_VALIDE,
                                                    null, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new ExpositionTemporaire(INTITULE_EXPO_VALIDE,
                                                    PERIODEDEB_EXPO_VALIDE,
                                                    PERIODEFIN_EXPO_VALIDE,
                                                    NBOEUVRE_EXPO_VALIDE,
                                                    MOTSCLES_EXPO_VALIDE,
                                                    RESUME_EXPO_VALIDE,
                                                    DATE_VALIDE, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new ExpositionTemporaire(INTITULE_EXPO_VALIDE,
                                                    PERIODEDEB_EXPO_VALIDE,
                                                    PERIODEFIN_EXPO_VALIDE,
                                                    NBOEUVRE_EXPO_VALIDE,
                                                    MOTSCLES_EXPO_VALIDE,
                                                    RESUME_EXPO_VALIDE,
                                                    null, DATE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new ExpositionTemporaire(INTITULE_EXPO_VALIDE,
                                                    PERIODEDEB_EXPO_VALIDE,
                                                    PERIODEFIN_EXPO_VALIDE,
                                                    NBOEUVRE_EXPO_VALIDE,
                                                    MOTSCLES_EXPO_VALIDE,
                                                    RESUME_EXPO_VALIDE,
                                                    DATE_VALIDE, DATE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new ExpositionTemporaire(INTITULE_EXPO_VALIDE,
                                                    PERIODEDEB_EXPO_VALIDE,
                                                    PERIODEFIN_EXPO_VALIDE,
                                                    NBOEUVRE_EXPO_VALIDE,
                                                    MOTSCLES_EXPO_VALIDE,
                                                    RESUME_EXPO_VALIDE,
                                                    DATE_VALIDE,
                                                    LocalDate.of(2005, 5, 28)));
        assertThrows(IllegalArgumentException.class, 
                     () -> new ExpositionTemporaire(INTITULE_EXPO_VALIDE,
                                                    PERIODEDEB_EXPO_VALIDE,
                                                    PERIODEFIN_EXPO_VALIDE,
                                                    NBOEUVRE_EXPO_VALIDE,
                                                    MOTSCLES_EXPO_VALIDE,
                                                    RESUME_EXPO_VALIDE,
                                                    DATE_VALIDE,
                                                    LocalDate.of(2005, 3, 21)));
        assertThrows(IllegalArgumentException.class, 
                     () -> new ExpositionTemporaire(INTITULE_EXPO_VALIDE,
                                                    PERIODEDEB_EXPO_VALIDE,
                                                    PERIODEFIN_EXPO_VALIDE,
                                                    NBOEUVRE_EXPO_VALIDE,
                                                    MOTSCLES_EXPO_VALIDE,
                                                    RESUME_EXPO_VALIDE,
                                                    DATE_VALIDE,
                                                    LocalDate.of(2005, 8, 14)));
        assertThrows(IllegalArgumentException.class, 
                     () -> new ExpositionTemporaire(INTITULE_EXPO_VALIDE,
                                                    PERIODEDEB_EXPO_VALIDE,
                                                    PERIODEFIN_EXPO_VALIDE,
                                                    NBOEUVRE_EXPO_VALIDE,
                                                    MOTSCLES_EXPO_VALIDE,
                                                    RESUME_EXPO_VALIDE,
                                                    DATE_VALIDE,
                                                    LocalDate.of(2005, 11, 20)));
    }
    
    /**
     * Méthode de test pour
     * {@link application.modele.ExpositionTemporaire#ExpositionTemporaire(
     * java.lang.String, java.lang.String, int, int, int, java.lang.String[], java.lang.String, java.time.LocalDate, java.time.LocalDate)}.
     * Cas uniquement valides
     */
    @Test
    void testExpositionTemporaireValide() {

        assertDoesNotThrow(
                () -> new ExpositionTemporaire(INTITULE_EXPO_VALIDE,
                                               PERIODEDEB_EXPO_VALIDE,
                                               PERIODEFIN_EXPO_VALIDE,
                                               NBOEUVRE_EXPO_VALIDE,
                                               MOTSCLES_EXPO_VALIDE,
                                               RESUME_EXPO_VALIDE,
                                               LocalDate.of(2005, 5, 28),
                                               DATE_VALIDE));
        assertDoesNotThrow(
                () -> new ExpositionTemporaire(INTITULE_EXPO_VALIDE,
                                               PERIODEDEB_EXPO_VALIDE,
                                               PERIODEFIN_EXPO_VALIDE,
                                               NBOEUVRE_EXPO_VALIDE,
                                               MOTSCLES_EXPO_VALIDE,
                                               RESUME_EXPO_VALIDE,
                                               LocalDate.of(2005, 3, 21),
                                               DATE_VALIDE));
        assertDoesNotThrow(
                () -> new ExpositionTemporaire(INTITULE_EXPO_VALIDE,
                                               PERIODEDEB_EXPO_VALIDE,
                                               PERIODEFIN_EXPO_VALIDE,
                                               NBOEUVRE_EXPO_VALIDE,
                                               MOTSCLES_EXPO_VALIDE,
                                               RESUME_EXPO_VALIDE,
                                               LocalDate.of(2005, 8, 14),
                                               DATE_VALIDE));
        assertDoesNotThrow(
                () -> new ExpositionTemporaire(INTITULE_EXPO_VALIDE,
                                               PERIODEDEB_EXPO_VALIDE,
                                               PERIODEFIN_EXPO_VALIDE,
                                               NBOEUVRE_EXPO_VALIDE,
                                               MOTSCLES_EXPO_VALIDE,
                                               RESUME_EXPO_VALIDE,
                                               LocalDate.of(2005, 11, 20),
                                               DATE_VALIDE));
    }

    /**
     * Méthode de test pour
     * {@link application.modele.ExpositionTemporaire#getDateDebut()}.
     */
    @Test
    void testGetDateDebut() {
        
        assertEquals(LocalDate.of(2005, 5, 28),
                     EXPOSITIONS_TEMP_VALIDES[0].getDateDebut());
        assertEquals(LocalDate.of(2005, 3, 21),
                     EXPOSITIONS_TEMP_VALIDES[1].getDateDebut());
        assertEquals(LocalDate.of(2005, 8, 14),
                     EXPOSITIONS_TEMP_VALIDES[2].getDateDebut());
        assertEquals(LocalDate.of(2005, 11, 20),
                     EXPOSITIONS_TEMP_VALIDES[3].getDateDebut());
        
        assertNotEquals(null, EXPOSITIONS_TEMP_VALIDES[0].getDateDebut());
        assertNotEquals(null, EXPOSITIONS_TEMP_VALIDES[1].getDateDebut());
        assertNotEquals(null, EXPOSITIONS_TEMP_VALIDES[2].getDateDebut());
        assertNotEquals(null, EXPOSITIONS_TEMP_VALIDES[3].getDateDebut());
        assertNotEquals(LocalDate.of(2005, 5, 29),
                        EXPOSITIONS_TEMP_VALIDES[0].getDateDebut());
        assertNotEquals(LocalDate.of(2005, 3, 22),
                        EXPOSITIONS_TEMP_VALIDES[1].getDateDebut());
        assertNotEquals(LocalDate.of(2005, 8, 15),
                        EXPOSITIONS_TEMP_VALIDES[2].getDateDebut());
        assertNotEquals(LocalDate.of(2005, 11, 21),
                        EXPOSITIONS_TEMP_VALIDES[3].getDateDebut());
    }

    /**
     * Méthode de test pour
     * {@link application.modele.ExpositionTemporaire#getDateFin()}.
     */
    @Test
    void testGetDateFin() {
        
        assertEquals(DATE_VALIDE, EXPOSITIONS_TEMP_VALIDES[0].getDateFin());
        assertEquals(DATE_VALIDE, EXPOSITIONS_TEMP_VALIDES[1].getDateFin());
        assertEquals(DATE_VALIDE, EXPOSITIONS_TEMP_VALIDES[2].getDateFin());
        assertEquals(DATE_VALIDE, EXPOSITIONS_TEMP_VALIDES[3].getDateFin());
        
        assertNotEquals(null, EXPOSITIONS_TEMP_VALIDES[0].getDateFin());
        assertNotEquals(null, EXPOSITIONS_TEMP_VALIDES[1].getDateFin());
        assertNotEquals(null, EXPOSITIONS_TEMP_VALIDES[2].getDateFin());
        assertNotEquals(null, EXPOSITIONS_TEMP_VALIDES[3].getDateFin());
        assertNotEquals(LocalDate.of(2005, 5, 29),
                        EXPOSITIONS_TEMP_VALIDES[0].getDateFin());
        assertNotEquals(LocalDate.of(2005, 3, 22),
                        EXPOSITIONS_TEMP_VALIDES[1].getDateFin());
        assertNotEquals(LocalDate.of(2005, 8, 15),
                        EXPOSITIONS_TEMP_VALIDES[2].getDateFin());
        assertNotEquals(LocalDate.of(2005, 11, 21),
                        EXPOSITIONS_TEMP_VALIDES[3].getDateFin());
    }

}
