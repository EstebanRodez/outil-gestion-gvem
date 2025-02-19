/*
 * TestClient.java                           
 * 9 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.modele.Client;

/**
 * Classe de test pour {@link application.modele.Client}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
class TestClient {
    
    private final String INTITULE_CLIENT_VALIDE = "Tom";
    
    private final String NUMTEL_CLIENT_VALIDE = "0112345678";
    
    private final Client[] CLIENTS_VALIDES =
    {
        new Client("Esteban", "0012345678"),
        new Client("Romain", "0012345678"),
        new Client("Ayoub", "0987654321"),
        new Client("Baptiste", "0154564474"),
    };

    /**
     * Méthode de test pour
     * {@link application.modele.Client#Client(java.lang.String, java.lang.String)}.
     * Cas uniquement invalides
     */
    @Test
    void testClientInvalide() {

        assertThrows(IllegalArgumentException.class, 
                     () -> new Client(null, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client("", null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client(null, ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client("", ""));
        
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client(null, "Baptiste"));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client("Romain", null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client("Ayoub", ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client("", "Esteban"));
        
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client(INTITULE_CLIENT_VALIDE, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client(INTITULE_CLIENT_VALIDE, ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client(null, NUMTEL_CLIENT_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client("", NUMTEL_CLIENT_VALIDE));
        
        /*
         * Tests spécifiques aux numéros de téléphone
         * Intitule toujours valide
         */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client(INTITULE_CLIENT_VALIDE, ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client(INTITULE_CLIENT_VALIDE, "0125"));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client(INTITULE_CLIENT_VALIDE, "11545444"));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client(INTITULE_CLIENT_VALIDE, "10052454789"));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Client(INTITULE_CLIENT_VALIDE, "010052d547"));
    }
    
    /**
     * Méthode de test pour
     * {@link application.modele.Client#Client(java.lang.String, java.lang.String)}.
     * Cas uniquement valides
     */
    @Test
    void testClientValide() {
        
        assertDoesNotThrow(() -> new Client("Esteban", "0012345678"));
        assertDoesNotThrow(() -> new Client("Romain", "0012345678"));
        assertDoesNotThrow(() -> new Client("Ayoub", "0987654321"));
        assertDoesNotThrow(() -> new Client(" Baptiste", "0154564474"));
    }

    /**
     * Méthode de test pour 
     * {@link application.modele.Client#getIntitule()}.
     */
    @Test
    void testGetIntitule() {
        
        assertEquals("Esteban", CLIENTS_VALIDES[0].getIntitule());
        assertEquals("Romain", CLIENTS_VALIDES[1].getIntitule());
        assertEquals("Ayoub", CLIENTS_VALIDES[2].getIntitule());
        assertEquals("Baptiste", CLIENTS_VALIDES[3].getIntitule());
        
        assertNotEquals(null, CLIENTS_VALIDES[1].getIntitule());
        assertNotEquals("", CLIENTS_VALIDES[2].getIntitule());
        assertNotEquals("Estebane", CLIENTS_VALIDES[0].getIntitule());
    }

    /**
     * Méthode de test pour 
     * {@link application.modele.Client#getNumTel()}.
     */
    @Test
    void testGetNumTel() {
        
        assertEquals("0012345678", CLIENTS_VALIDES[0].getNumTel());
        assertEquals("0012345678", CLIENTS_VALIDES[1].getNumTel());
        assertEquals("0987654321", CLIENTS_VALIDES[2].getNumTel());
        assertEquals("0154564474", CLIENTS_VALIDES[3].getNumTel());
        
        assertNotEquals(null, CLIENTS_VALIDES[1].getNumTel());
        assertNotEquals("", CLIENTS_VALIDES[2].getNumTel());
        assertNotEquals("0123456789", CLIENTS_VALIDES[0].getNumTel());
        assertNotEquals("01234567", CLIENTS_VALIDES[0].getNumTel());
        assertNotEquals("112345678", CLIENTS_VALIDES[0].getNumTel());
    }
    
    /**
     * Méthode de test pour 
     * {@link application.modele.Client#equals()}.
     */
    @Test
    void testEquals() {
        
        assertTrue(CLIENTS_VALIDES[0].equals(CLIENTS_VALIDES[0]));
        assertTrue(CLIENTS_VALIDES[1].equals(CLIENTS_VALIDES[1]));
        assertTrue(CLIENTS_VALIDES[2].equals(CLIENTS_VALIDES[2]));
        assertTrue(CLIENTS_VALIDES[3].equals(CLIENTS_VALIDES[3]));
        assertTrue(CLIENTS_VALIDES[0].equals(new Client("Esteban",
                                                        "0012345678")));
        assertTrue(CLIENTS_VALIDES[1].equals(new Client("Romain",
                                                        "0012345678")));
        assertTrue(CLIENTS_VALIDES[2].equals(new Client("Ayoub",
                                                        "0987654321")));
        assertTrue(CLIENTS_VALIDES[3].equals(new Client("Baptiste",
                                                        "0154564474")));
        
        assertFalse(CLIENTS_VALIDES[0].equals(null));
        assertFalse(CLIENTS_VALIDES[1].equals(null));
        assertFalse(CLIENTS_VALIDES[2].equals(null));
        assertFalse(CLIENTS_VALIDES[3].equals(null));
        assertFalse(CLIENTS_VALIDES[0].equals(CLIENTS_VALIDES[1]));
        assertFalse(CLIENTS_VALIDES[1].equals(CLIENTS_VALIDES[2]));
        assertFalse(CLIENTS_VALIDES[2].equals(CLIENTS_VALIDES[3]));
        assertFalse(CLIENTS_VALIDES[3].equals(CLIENTS_VALIDES[0]));
        assertFalse(CLIENTS_VALIDES[0].equals("Esteban"));
        assertFalse(CLIENTS_VALIDES[1].equals("Romain"));
        assertFalse(CLIENTS_VALIDES[2].equals("Ayoub"));
        assertFalse(CLIENTS_VALIDES[3].equals("Baptiste"));
        assertFalse(CLIENTS_VALIDES[0].equals("012345678"));
        assertFalse(CLIENTS_VALIDES[1].equals("012345678"));
        assertFalse(CLIENTS_VALIDES[2].equals("987654321"));
        assertFalse(CLIENTS_VALIDES[3].equals("154564474"));
        assertFalse(CLIENTS_VALIDES[0].equals(new Client("Estebane",
                                                         "0012345678")));
        assertFalse(CLIENTS_VALIDES[1].equals(new Client("Romaine",
                                                         "0012345678")));
        assertFalse(CLIENTS_VALIDES[2].equals(new Client("Ayoube",
                                                         "0987654321")));
        assertFalse(CLIENTS_VALIDES[3].equals(new Client("Baptistes",
                                                         "0154564474")));
        assertFalse(CLIENTS_VALIDES[0].equals(new Client("Esteban",
                                                         "0013345678")));
        assertFalse(CLIENTS_VALIDES[1].equals(new Client("Romain",
                                                         "0012346678")));
        assertFalse(CLIENTS_VALIDES[2].equals(new Client("Ayoub",
                                                         "0988654321")));
        assertFalse(CLIENTS_VALIDES[3].equals(new Client("Baptiste",
                                                         "0154564494")));
    }
    
    /**
     * Méthode de test pour 
     * {@link application.modele.Client#toString()}.
     */
    @Test
    void testToString() {
        
        assertEquals("Esteban 0012345678", CLIENTS_VALIDES[0].toString());
        assertEquals("Romain 0012345678", CLIENTS_VALIDES[1].toString());
        assertEquals("Ayoub 0987654321", CLIENTS_VALIDES[2].toString());
        assertEquals("Baptiste 0154564474", CLIENTS_VALIDES[3].toString());
        
        assertNotEquals(null, CLIENTS_VALIDES[0].toString());
        assertNotEquals(null, CLIENTS_VALIDES[1].toString());
        assertNotEquals(null, CLIENTS_VALIDES[2].toString());
        assertNotEquals(null, CLIENTS_VALIDES[3].toString());
        assertNotEquals("", CLIENTS_VALIDES[0].toString());
        assertNotEquals("", CLIENTS_VALIDES[1].toString());
        assertNotEquals("", CLIENTS_VALIDES[2].toString());
        assertNotEquals("", CLIENTS_VALIDES[3].toString());
        assertNotEquals("Estebane 012345678", CLIENTS_VALIDES[0].toString());
        assertNotEquals("Romaine 012345678", CLIENTS_VALIDES[1].toString());
        assertNotEquals("Ayoube 987654321", CLIENTS_VALIDES[2].toString());
        assertNotEquals("Baptistes 154564474", CLIENTS_VALIDES[3].toString());
    }

}
