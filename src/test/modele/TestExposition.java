/*
 * TestExposition.java                           
 * 12 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.modele.Exposition;

/**
 * Classe de Test pour {@link application.modele.Exposition}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
class TestExposition {
    
    private final String INTITULE_EXPO_VALIDE = "Les paysages impressionnistes";
    
    private final int PERIODEDEB_EXPO_VALIDE = 1880;
    
    private final int PERIODEFIN_EXPO_VALIDE = 1895;
    
    private final int NBOEUVRE_EXPO_VALIDE = 20;
    
    private final String[] MOTSCLES_EXPO_VALIDE
    = {"paysage", "impressionnisme", "Monet", "Pissarro", "Sysley", "Signac"};
    
    private final String RESUME_EXPO_VALIDE
    = "Très belle exposition sur l'impressionnisme.";
    
    private final Exposition[] EXPO_VALIDES =
    {
        new Exposition(INTITULE_EXPO_VALIDE, 1725, 1850, 15,
                       new String[] {"Romain", "Ayoub", "Esteban", "Baptiste",
                                     "paysage", "mars"},
                       "Une grande exposition sur des paysages originaux et"
                       + " audacieux"),
        new Exposition(INTITULE_EXPO_VALIDE, 1800, 1900, 45,
                       new String[] {"cubisme", "oeuvre", "abstrait", 
                                     "art moderne"},
                       "Une exposition d'œuvres cubistes influentes."),
    };

    /**
     * Méthode de test pour 
     * {@link application.modele.Exposition#Exposition(java.lang.String, java.lang.String,
     *  int, int, int, java.lang.String[], java.lang.String)}.
     * Cas uniquement invalides
     */
    @Test
    void testExpositionInvalide() {

        // Test avec intitulé null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(null, 
                                          PERIODEDEB_EXPO_VALIDE, 
                                          PERIODEFIN_EXPO_VALIDE,
                                          NBOEUVRE_EXPO_VALIDE, 
                                          MOTSCLES_EXPO_VALIDE,
                                          RESUME_EXPO_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition("",
                                          PERIODEDEB_EXPO_VALIDE, 
                                          PERIODEFIN_EXPO_VALIDE,
                                          NBOEUVRE_EXPO_VALIDE, 
                                          MOTSCLES_EXPO_VALIDE,
                                          RESUME_EXPO_VALIDE));

        // Test avec périodes invalides
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(INTITULE_EXPO_VALIDE, 
                                          2025, 1880, NBOEUVRE_EXPO_VALIDE, 
                                          MOTSCLES_EXPO_VALIDE,
                                          RESUME_EXPO_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(INTITULE_EXPO_VALIDE, 1880, 0, 
                                          NBOEUVRE_EXPO_VALIDE, 
                                          MOTSCLES_EXPO_VALIDE,
                                          RESUME_EXPO_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(INTITULE_EXPO_VALIDE, 0, -40,
                                          NBOEUVRE_EXPO_VALIDE, 
                                          MOTSCLES_EXPO_VALIDE,
                                          RESUME_EXPO_VALIDE));

        // Test avec nombre d'œuvres invalide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(INTITULE_EXPO_VALIDE, 
                                          PERIODEDEB_EXPO_VALIDE,
                                          PERIODEFIN_EXPO_VALIDE, 0,
                                          MOTSCLES_EXPO_VALIDE,
                                          RESUME_EXPO_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(INTITULE_EXPO_VALIDE, 
                                          PERIODEDEB_EXPO_VALIDE,
                                          PERIODEFIN_EXPO_VALIDE, -5, 
                                          MOTSCLES_EXPO_VALIDE, 
                                          RESUME_EXPO_VALIDE));

        // Test avec liste de mots-clés invalide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(INTITULE_EXPO_VALIDE, 
                                          PERIODEDEB_EXPO_VALIDE,
                                          PERIODEFIN_EXPO_VALIDE, 
                                          NBOEUVRE_EXPO_VALIDE, null,
                                          RESUME_EXPO_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(INTITULE_EXPO_VALIDE, 
                                          PERIODEDEB_EXPO_VALIDE,
                                          PERIODEFIN_EXPO_VALIDE, 
                                          NBOEUVRE_EXPO_VALIDE, new String[0], 
                                          RESUME_EXPO_VALIDE));
        
        // Test avec plus de 10 mots-clés
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(INTITULE_EXPO_VALIDE, 
                                          PERIODEDEB_EXPO_VALIDE,
                                          PERIODEFIN_EXPO_VALIDE, 
                                          NBOEUVRE_EXPO_VALIDE, 
                                          new String[]{"clé1", "clé2", "clé3", 
                                                       "clé4", "clé5", "clé6",
                                                       "clé7", "clé8", "clé9",
                                                       "clé10", "clé11"}, 
                                          RESUME_EXPO_VALIDE));

        // Test avec un mot-clé null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(INTITULE_EXPO_VALIDE, 
                                          PERIODEDEB_EXPO_VALIDE,
                                          PERIODEFIN_EXPO_VALIDE, 
                                          NBOEUVRE_EXPO_VALIDE, 
                                          new String[]{"paysage", null}, 
                                          RESUME_EXPO_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(INTITULE_EXPO_VALIDE, 
                                          PERIODEDEB_EXPO_VALIDE,
                                          PERIODEFIN_EXPO_VALIDE, 
                                          NBOEUVRE_EXPO_VALIDE, 
                                          new String[]{"paysage", ""}, 
                                          RESUME_EXPO_VALIDE));

        // Test avec résumé invalide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(INTITULE_EXPO_VALIDE, 
                                          PERIODEDEB_EXPO_VALIDE,
                                          PERIODEFIN_EXPO_VALIDE, 
                                          NBOEUVRE_EXPO_VALIDE,
                                          MOTSCLES_EXPO_VALIDE, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Exposition(INTITULE_EXPO_VALIDE,
                                          PERIODEDEB_EXPO_VALIDE,
                                          PERIODEFIN_EXPO_VALIDE, 
                                          NBOEUVRE_EXPO_VALIDE,
                                          MOTSCLES_EXPO_VALIDE, ""));
    }

    /**
     * Méthode de test pour 
     * {@link application.modele.Exposition#Exposition(java.lang.String, java.lang.String,
     *  int, int, int, java.lang.String[], java.lang.String)}.
     * Cas uniquement valides
     */
    @Test
    void testExpositionValide() {
        
        assertDoesNotThrow(() -> new Exposition(INTITULE_EXPO_VALIDE, 
                                                PERIODEDEB_EXPO_VALIDE,
                                                PERIODEFIN_EXPO_VALIDE, 
                                                NBOEUVRE_EXPO_VALIDE,
                                                MOTSCLES_EXPO_VALIDE, 
                                                RESUME_EXPO_VALIDE));
    }

    /**
     * Méthode de test pour {@link application.modele.Exposition#getIntitule()}.
     */
    @Test
    void testGetIntitule() {
        
        Exposition expo = new Exposition(INTITULE_EXPO_VALIDE, 
                                         PERIODEDEB_EXPO_VALIDE,
                                         PERIODEFIN_EXPO_VALIDE, 
                                         NBOEUVRE_EXPO_VALIDE,
                                         MOTSCLES_EXPO_VALIDE, 
                                         RESUME_EXPO_VALIDE);
        assertEquals(INTITULE_EXPO_VALIDE, expo.getIntitule());
    }
    
    /**
     * Méthode de test pour 
     * {@link application.modele.Exposition#getPeriodeDeb()}.
     */
    @Test
    void testGetPeriodeDebut() {
        
        assertEquals(1725, EXPO_VALIDES[0].getPeriodeDebut());
        assertEquals(1800, EXPO_VALIDES[1].getPeriodeDebut());
        
        assertNotEquals(4444, EXPO_VALIDES[1].getPeriodeDebut());
        assertNotEquals(0, EXPO_VALIDES[1].getPeriodeDebut());
        assertNotEquals(-50, EXPO_VALIDES[0].getPeriodeDebut());
        assertNotEquals(1724, EXPO_VALIDES[0].getPeriodeDebut());
        assertNotEquals(1726, EXPO_VALIDES[0].getPeriodeDebut());
        assertNotEquals(1799, EXPO_VALIDES[1].getPeriodeDebut());
        assertNotEquals(1801, EXPO_VALIDES[1].getPeriodeDebut());
    }
    
    /**
     * Méthode de test pour 
     * {@link application.modele.Exposition#getPeriodeFin()}.
     */
    @Test
    void testGetPeriodeFin() {
        
        assertEquals(1850, EXPO_VALIDES[0].getPeriodeFin());
        assertEquals(1900, EXPO_VALIDES[1].getPeriodeFin());
        
        assertNotEquals(9999, EXPO_VALIDES[1].getPeriodeFin());
        assertNotEquals(0, EXPO_VALIDES[1].getPeriodeFin());
        assertNotEquals(-50, EXPO_VALIDES[0].getPeriodeFin());
        assertNotEquals(1849, EXPO_VALIDES[0].getPeriodeFin());
        assertNotEquals(1851, EXPO_VALIDES[0].getPeriodeFin());
        assertNotEquals(1899, EXPO_VALIDES[1].getPeriodeFin());
        assertNotEquals(1901, EXPO_VALIDES[1].getPeriodeFin());
    }

    /**
     * Méthode de test pour 
     * {@link application.modele.Exposition#getNbOeuvre()}.
     */
    @Test
    void testGetNbOeuvre() {
        
        assertEquals(15, EXPO_VALIDES[0].getNbOeuvre());
        assertEquals(45, EXPO_VALIDES[1].getNbOeuvre());

        assertNotEquals(20, EXPO_VALIDES[0].getNbOeuvre());
        assertNotEquals(5555, EXPO_VALIDES[0].getNbOeuvre());
        assertNotEquals(-15, EXPO_VALIDES[0].getNbOeuvre());
        assertNotEquals(-45, EXPO_VALIDES[1].getNbOeuvre());
    }

    /**
     * Méthode de test pour 
     * {@link application.modele.Exposition#getMotsCles()}.
     */
    @Test
    void testGetMotsCles() {
        
        assertArrayEquals(new String[] {"Romain", "Ayoub", "Esteban", 
                                        "Baptiste", "paysage", "mars"}, 
                          EXPO_VALIDES[0].getMotsCles());
        assertArrayEquals(new String[] {"cubisme", "oeuvre", "abstrait",
                                        "art moderne"},
                          EXPO_VALIDES[1].getMotsCles());

        assertNotEquals(new String[] {"abstrait"}, 
                        EXPO_VALIDES[1].getMotsCles());
        assertNotEquals(new String[] {"cubisme", "oeuvre", "abstrait",
                                      "art moderne", null},
                        EXPO_VALIDES[1].getMotsCles());
        assertNotEquals(new String[] {"Romain", "Ayoub", "Esteban", "Baptiste", 
                                      "paysage", "mars", ""}, 
                        EXPO_VALIDES[0].getMotsCles());
        assertNotEquals(null, EXPO_VALIDES[1].getMotsCles());
    }

    /**
     * Méthode de test pour 
     * {@link application.modele.Exposition#getResume()}.
     */
    @Test
    void testGetResume() {
        
        assertEquals("Une grande exposition sur des paysages originaux et"
                     + " audacieux",
                     EXPO_VALIDES[0].getResume());
        assertEquals("Une exposition d'œuvres cubistes influentes.",
                     EXPO_VALIDES[1].getResume());

        assertNotEquals("Exposition moderne", EXPO_VALIDES[1].getResume());
        assertNotEquals("", EXPO_VALIDES[0].getResume());
        assertNotEquals(null, EXPO_VALIDES[1].getResume());
    }
    
    /**
     * Méthode de test pour 
     * {@link application.modele.Exposition#toStringMotsCles()}.
     */
    @Test
    void testToStringMotsCles() {
        
        assertEquals("Romain, Ayoub, Esteban, Baptiste, paysage, mars",
                     EXPO_VALIDES[0].toStringMotsCles());
        assertEquals("cubisme, oeuvre, abstrait, art moderne",
                     EXPO_VALIDES[1].toStringMotsCles());

        assertNotNull(EXPO_VALIDES[0].toStringMotsCles());
        assertNotNull(EXPO_VALIDES[1].toStringMotsCles());
        assertNotEquals("", EXPO_VALIDES[0].toStringMotsCles());
        assertNotEquals("", EXPO_VALIDES[1].toStringMotsCles());
    }
}
