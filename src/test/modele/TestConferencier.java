/*
 * TestConferencier.java                           
 * 15 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;

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
	{
	    new Indisponibilite(LocalDate.of (2025, 8, 2)), 
	    new Indisponibilite(LocalDate.of (2024, 12, 22), 
	    					LocalDate.of(2024,12,26))
	};

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
                    		 PRENOM_CONF_VALIDE,SPECIALITE_CONF_VALIDE, 
                    		 NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE,null));
        assertThrows(IllegalArgumentException.class, 
                () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
               		 PRENOM_CONF_VALIDE,SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
               		 EST_INTERNE_VALIDE, new Indisponibilite[]
               				 {new Indisponibilite(LocalDate.of (2024, 10, 16)), 
               				  null}));
    

    	// Test avec identifiant null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(null,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE,SPECIALITE_CONF_VALIDE, 
                    		 NUMTEL_CONF_VALIDE,EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier("",NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
                    		 NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE));
    	
        // Test avec nom null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,null, 
                    		 PRENOM_CONF_VALIDE,SPECIALITE_CONF_VALIDE, 
                    		 NUMTEL_CONF_VALIDE,EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,"", 
                    		 PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
                    		 NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE));

        // Test avec prénom null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 null, SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
                    		 EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 "", SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
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
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 				PRENOM_CONF_VALIDE, 
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
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
                    		 "12345", EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, 
                    		 PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, "123", 
                    		 EST_INTERNE_VALIDE));
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
        assertDoesNotThrow(() -> new Conferencier(ID_CONF_VALIDE,
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, 
        		EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES));
        
        assertDoesNotThrow(() -> new Conferencier(ID_CONF_VALIDE,
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE));
    }
    

    /**
     * Méthode de test pour {@link application.modele.Conferencier#equals()}.
     */
    @Test
    void testEquals() {
    	// conferencier1 et conferencier2 sont identiques
        Conferencier conferencier1 = new Conferencier(ID_CONF_VALIDE, 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        Conferencier conferencier2 = new Conferencier(ID_CONF_VALIDE, 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        //même que 1 et 2 sauf que pas d'indisponibilites
        Conferencier conferencier3 = new Conferencier("C000002", 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE);
        // conferencier4 est diferent de 1 et 2 et 3
        Conferencier conferencier4 = new Conferencier("C000002", 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
      //même que 4 sauf que pas d'indisponibilites
        Conferencier conferencier5 = new Conferencier("C000002", 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE);
        
        // Même référence
        assertEquals(conferencier1, conferencier1);
        assertEquals(conferencier2, conferencier2);
        assertEquals(conferencier3, conferencier3);
        assertEquals(conferencier4, conferencier4);
        assertEquals(conferencier5, conferencier5);
        
        // Objets avec les mêmes valeurs
        assertEquals(conferencier1, conferencier2);
        assertEquals(conferencier2, conferencier1);
        
        // Objets différents
        
        assertNotEquals(conferencier1, conferencier3);
        assertNotEquals(conferencier1, conferencier4);
        assertNotEquals(conferencier1, conferencier5);
        assertNotEquals(conferencier2, conferencier3);
        assertNotEquals(conferencier2, conferencier4);
        assertNotEquals(conferencier2, conferencier5);
        assertNotEquals(conferencier3, conferencier1);
        assertNotEquals(conferencier3, conferencier2);
        assertNotEquals(conferencier4, conferencier1);
        assertNotEquals(conferencier4, conferencier2);
        assertNotEquals(conferencier4, conferencier3);
        assertNotEquals(conferencier4, conferencier5);
        assertNotEquals(conferencier5, conferencier1);
        assertNotEquals(conferencier5, conferencier2);
        
        // Test avec null
        assertNotEquals(conferencier1, null);
        assertNotEquals(conferencier2, null);
        assertNotEquals(conferencier3, null);
        assertNotEquals(conferencier4, null);
        assertNotEquals(conferencier5, null);
        
        // Test avec un objet d'une autre classe
        assertNotEquals(conferencier1, new Object());
        assertNotEquals(conferencier2, new Object());
        assertNotEquals(conferencier3, new Object());
        assertNotEquals(conferencier4, new Object());
        assertNotEquals(conferencier5, new Object());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#toString()}.
     */
    @Test
    void testToString() {
        Conferencier conferencier1 = new Conferencier(ID_CONF_VALIDE, 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        
        String expectedString1 = "identifiant : " + ID_CONF_VALIDE
                              + ", nom : " + NOM_CONF_VALIDE
                              + ", prenom : " + PRENOM_CONF_VALIDE
                              + ", specialites : " 
                              + Arrays.toString(SPECIALITE_CONF_VALIDE)
                              + ", numéro de téléphone : " + NUMTEL_CONF_VALIDE
                              + ", status(interne ou externe) : " 
                              + EST_INTERNE_VALIDE
                              + ", liste des indisponibilites : " 
                              + Arrays.toString(INDISPONIBILITES_VALIDES);
        
        //TODO : Corriger test pour conferencier avec indisponibilités vide
        assertEquals(expectedString1, conferencier1.toString());
        
        Conferencier conferencier2 = new Conferencier(ID_CONF_VALIDE, 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE);
        
        String expectedString2 = "identifiant : " + ID_CONF_VALIDE
                              + ", nom : " + NOM_CONF_VALIDE
                              + ", prenom : " + PRENOM_CONF_VALIDE
                              + ", specialites : " 
                              + Arrays.toString(SPECIALITE_CONF_VALIDE)
                              + ", numéro de téléphone : " + NUMTEL_CONF_VALIDE
                              + ", status(interne ou externe) : " 
                              + EST_INTERNE_VALIDE;
        
        assertEquals(expectedString2, conferencier2.toString());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getIdentifiant()}.
     */
    @Test
    void testGetIdentifiant() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        assertEquals(ID_CONF_VALIDE, conferencier.getIdentifiant());
    }
    
    /**
     * Méthode de test pour {@link application.modele.Conferencier#getNom()}.
     */
    @Test
    void testGetNom() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        assertEquals(NOM_CONF_VALIDE, conferencier.getNom());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getPrenom()}.
     */
    @Test
    void testGetPrenom() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        assertEquals(PRENOM_CONF_VALIDE, conferencier.getPrenom());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getSpecialite()}.
     */
    @Test
    void testGetSpecialite() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        assertArrayEquals(SPECIALITE_CONF_VALIDE, conferencier.getSpecialites());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getNumTel()}.
     */
    @Test
    void testGetNumTel() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        assertEquals(NUMTEL_CONF_VALIDE, conferencier.getNumTel());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getEstInterne()}.
     */
    @Test
    void testGetEstInterne() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        assertTrue(conferencier.estInterne());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getIndisponibilites()}.
     */
    @Test
    void testGetIndisponibilites() {
        Conferencier conferencier = new Conferencier(ID_CONF_VALIDE,
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        assertArrayEquals(INDISPONIBILITES_VALIDES, conferencier.getIndisponibilites());
    }
}
