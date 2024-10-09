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
 * @author Esteban Vroemen
 */
class TestClient {
    
    private final String INTITULE_CLIENT_VALIDE = "Tom";
    
    private final String NUMTEL_CLIENT_VALIDE = "112345678";
    
    private final Client[] CLIENTS_VALIDES =
    {
        new Client("Esteban", "012345678"),
        new Client("Romain", "012345678 "),
        new Client("Ayoub", "987654321"),
        new Client(" Baptiste", "154564474"),
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
                     () -> new Client(INTITULE_CLIENT_VALIDE, "1005245478"));
    }
    
    /**
     * Méthode de test pour
     * {@link application.modele.Client#Client(java.lang.String, java.lang.String)}.
     * Cas uniquement valides
     */
    @Test
    void testClientValide() {
        
        assertDoesNotThrow(() -> new Client("Esteban", "012345678"));
        assertDoesNotThrow(() -> new Client("Romain", "012345678 "));
        assertDoesNotThrow(() -> new Client("Ayoub", "987654321"));
        assertDoesNotThrow(() -> new Client(" Baptiste", "154564474"));
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
        
        assertEquals("012345678", CLIENTS_VALIDES[0].getNumTel());
        assertEquals("012345678", CLIENTS_VALIDES[1].getNumTel());
        assertEquals("987654321", CLIENTS_VALIDES[2].getNumTel());
        assertEquals("154564474", CLIENTS_VALIDES[3].getNumTel());
        
        assertNotEquals(null, CLIENTS_VALIDES[1].getNumTel());
        assertNotEquals("", CLIENTS_VALIDES[2].getNumTel());
        assertNotEquals("0123456789", CLIENTS_VALIDES[0].getNumTel());
        assertNotEquals("01234567", CLIENTS_VALIDES[0].getNumTel());
        assertNotEquals("112345678", CLIENTS_VALIDES[0].getNumTel());
    }

}
