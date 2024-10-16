/*
 * TestConferencier.java                           
 * 15 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import application.modele.Conferencier;
import application.modele.Indisponibilite;

/**
 * Classe de Test pour {@link application.modele.Conferencier}
 * @author Romain Augé
 * @version 1.0
 */
class TestConferencier {
    
	private final String ID_CONF_VALIDE = "C000001";
    private final String NOM_CONF_VALIDE = "Dupont";
    private final String PRENOM_CONF_VALIDE = "Pierre";
    private final String[] SPECIALITE_CONF_VALIDE = {"Art", "Histoire"};
    private final String NUMTEL_CONF_VALIDE = "123456789";
    private final boolean EST_INTERNE_VALIDE = true;
    private final Indisponibilite[] INDISPONIBILITES_VALIDES = 
    	{new Indisponibilite(LocalDate.of (2025, 8, 2)), 
    			new Indisponibilite(LocalDate.of (2024, 12, 22), 
    					LocalDate.of(2024,12,26))};

    /**
     * Méthode de test pour 
     * {@link application.modele.Conferencier#Conferencier(java.lang.String, java.lang.String,
     *  java.lang.String[], java.lang.String, boolean, application.modele.Indisponibilite[])}.
     * Cas uniquement invalides
     */
    @Test
    void testConferencierInvalide() {
    	// Test avec identifiant null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(null,NOM_CONF_VALIDE, PRENOM_CONF_VALIDE,
                    		 SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
                    		 EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier("",NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, 
                    		 SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
                    		 EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES));
    	
        // Test avec nom null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,null, PRENOM_CONF_VALIDE,
                    		 SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
                    		 EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,"", PRENOM_CONF_VALIDE, 
                    		 SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
                    		 EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES));

        // Test avec prénom null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, null, 
                    		 SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
                    		 EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, "", 
                    		 SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
                    		 EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES));

        // Test avec spécialité null, vide ou plus de 6 éléments
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE, null, NUMTEL_CONF_VALIDE, 
                    		 EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE, new String[0], 
                    		 NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
                    		 INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, 
                    		 new String[]{"A", "B", "C", "D", "E", "F", "G"}, 
                    		 NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
                    		 INDISPONIBILITES_VALIDES));

        /* Test avec numéro de téléphone null, vide, trop long,
        trop court ou non numérique */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE,SPECIALITE_CONF_VALIDE, null, 
                    		 EST_INTERNE_VALIDE,INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, "", 
                    		 EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, 
                    		 SPECIALITE_CONF_VALIDE, "12345", EST_INTERNE_VALIDE, 
                    		 INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, 
                    		 SPECIALITE_CONF_VALIDE, "123", EST_INTERNE_VALIDE, 
                    		 INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, "12a4", 
                    		 EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
               		 PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, "1234567a9", 
               		 EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES));
        
        /* Test avec Indisponibilités null */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE,SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
                    		 EST_INTERNE_VALIDE,null));
        assertThrows(IllegalArgumentException.class, 
                () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
               		 PRENOM_CONF_VALIDE,SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
               		 EST_INTERNE_VALIDE, new Indisponibilite[]{new Indisponibilite(LocalDate.of (2024, 10, 16)), null}));
    

    	// Test avec identifiant null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(null,NOM_CONF_VALIDE, PRENOM_CONF_VALIDE,
                    		 SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
                    		 EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier("",NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, 
                    		 SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
                    		 EST_INTERNE_VALIDE));
    	
        // Test avec nom null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,null, PRENOM_CONF_VALIDE,
                    		 SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
                    		 EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,"", PRENOM_CONF_VALIDE, 
                    		 SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
                    		 EST_INTERNE_VALIDE));

        // Test avec prénom null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, null, 
                    		 SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
                    		 EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, "", 
                    		 SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
                    		 EST_INTERNE_VALIDE));

        // Test avec spécialité null, vide ou plus de 6 éléments
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE, null, NUMTEL_CONF_VALIDE, 
                    		 EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE, new String[0], 
                    		 NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, 
                    		 new String[]{"A", "B", "C", "D", "E", "F", "G"}, 
                    		 NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE));

        /* Test avec numéro de téléphone null, vide, trop long,
        trop court ou non numérique */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE,SPECIALITE_CONF_VALIDE, null, 
                    		 EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, "", 
                    		 EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, 
                    		 SPECIALITE_CONF_VALIDE, "12345", EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, 
                    		 SPECIALITE_CONF_VALIDE, "123", EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, "12a4", 
                    		 EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
               		 PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, "1234567a9", 
               		 EST_INTERNE_VALIDE));
    }

    /**
     * Méthode de test pour 
     * {@link application.modele.Conferencier#Conferencier(java.lang.String,java.lang.String, 
     * java.lang.String, java.lang.String[], java.lang.String, boolean, application.modele.Indisponibilite[])}.
     * Cas uniquement valides
     */
    @Test
    void testConferencierValide() {
        assertDoesNotThrow(() -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
        		PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
        		EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES));
        
        assertDoesNotThrow(() -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
        		PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
        		EST_INTERNE_VALIDE));
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getIdentifiant()}.
     */
    @Test
    void testGetIdentifiant() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
        		PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
        		EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES);
        assertEquals(ID_CONF_VALIDE, conferencier.getIdentifiant());
    }
    
    /**
     * Méthode de test pour {@link application.modele.Conferencier#getNom()}.
     */
    @Test
    void testGetNom() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
        		PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
        		EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES);
        assertEquals(NOM_CONF_VALIDE, conferencier.getNom());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getPrenom()}.
     */
    @Test
    void testGetPrenom() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
        		PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
        		EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES);
        assertEquals(PRENOM_CONF_VALIDE, conferencier.getPrenom());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getSpecialite()}.
     */
    @Test
    void testGetSpecialite() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
        		PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
        		EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES);
        assertArrayEquals(SPECIALITE_CONF_VALIDE, conferencier.getSpecialites());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getNumTel()}.
     */
    @Test
    void testGetNumTel() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
        		PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
        		EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES);
        assertEquals(NUMTEL_CONF_VALIDE, conferencier.getNumTel());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getEstInterne()}.
     */
    @Test
    void testGetEstInterne() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
        		PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
        		EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES);
        assertTrue(conferencier.estInterne());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getIndisponibilites()}.
     */
    @Test
    void testGetIndisponibilites() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
        		PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
        		EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES);
        assertArrayEquals(INDISPONIBILITES_VALIDES, conferencier.getIndisponibilites());
    }
}
