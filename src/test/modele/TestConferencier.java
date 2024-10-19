/*
 * TestConferencier.java                           
 * 15 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.modele;

import static org.junit.Assert.assertTrue;
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
    private final String NUMTEL_CONF_VALIDE = "0123456789";
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
        		"Lexpert", "Noemie", new String[]{"peinture", "impressionnisme", "art contemporain"}, 
        		"0600000001", true,new Indisponibilite[] {
        				new Indisponibilite(LocalDate.of(2024,10,22)), new Indisponibilite(LocalDate.of(2024,10,26))
        				});
        // conferencier4 est diferent de 1 et 2 et 3
        Conferencier conferencier4 = new Conferencier("C000003", 
        		"Dujardin", "Oceane", new String[]{"art moderne"}, 
        		"0611111111", true, 
        		new Indisponibilite[] {
        				new Indisponibilite(LocalDate.of(2024,11,07)),
        				new Indisponibilite(LocalDate.of(2024,11,07)),
        				new Indisponibilite(LocalDate.of(2024,11,19)),
        				new Indisponibilite(LocalDate.of(2024,11,22))
        		});
      //même que 4 sauf que pas d'indisponibilites
        Conferencier conferencier5 = new Conferencier("C000008", 
        		"Deneuve", "Zoé", new String[]{"photo","peinture"}, 
        		"0600000003", false);
        
        // conferencier avec un paramètre different avec 1
        Conferencier conferencier6 = new Conferencier(ID_CONF_VALIDE, 
        		"Vroemen", PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        
        Conferencier conferencier7 = new Conferencier(ID_CONF_VALIDE, 
        		NOM_CONF_VALIDE, "Ayoub", SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        
        Conferencier conferencier8 = new Conferencier(ID_CONF_VALIDE, 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, new String[]{"photo","peinture"}, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        
        Conferencier conferencier9 = new Conferencier(ID_CONF_VALIDE, 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		"0603080911", EST_INTERNE_VALIDE, 
        		INDISPONIBILITES_VALIDES);
        
        Conferencier conferencier10 = new Conferencier(ID_CONF_VALIDE, 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, false, 
        		INDISPONIBILITES_VALIDES);
        
        Conferencier conferencier11 = new Conferencier(ID_CONF_VALIDE, 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
        		new Indisponibilite[] {
        			new Indisponibilite(LocalDate.of(2024,10,22)), new Indisponibilite(LocalDate.of(2024,10,26))
        		});
        
        Conferencier conferencier12 = new Conferencier(ID_CONF_VALIDE, 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE);
        
        
        // Même référence
        assertTrue(conferencier1.equals(conferencier1));
        assertTrue(conferencier2.equals(conferencier2));
        assertTrue(conferencier3.equals(conferencier3));
        assertTrue(conferencier4.equals(conferencier4));
        assertTrue(conferencier5.equals(conferencier5));
        assertTrue(conferencier6.equals(conferencier6));
        assertTrue(conferencier7.equals(conferencier7));
        assertTrue(conferencier8.equals(conferencier8));
        assertTrue(conferencier9.equals(conferencier9));
        assertTrue(conferencier10.equals(conferencier10));
        assertTrue(conferencier11.equals(conferencier11));
        assertTrue(conferencier12.equals(conferencier12));
        
        // Objets avec les mêmes valeurs
        assertTrue(conferencier1.equals(conferencier2));
        assertTrue(conferencier2.equals(conferencier1));
        
        // Objets différents
        
        assertFalse(conferencier1.equals(conferencier3));
        assertFalse(conferencier1.equals(conferencier4));
        assertFalse(conferencier1.equals(conferencier5));
        assertFalse(conferencier1.equals(conferencier6));
        assertFalse(conferencier1.equals(conferencier7));
        assertFalse(conferencier1.equals(conferencier8));
        assertFalse(conferencier1.equals(conferencier9));
        assertFalse(conferencier1.equals(conferencier10));
        assertFalse(conferencier1.equals(conferencier11));
        assertFalse(conferencier1.equals(conferencier12));
        assertFalse(conferencier2.equals(conferencier3));
        assertFalse(conferencier2.equals(conferencier4));
        assertFalse(conferencier2.equals(conferencier5));
        assertFalse(conferencier2.equals(conferencier6));
        assertFalse(conferencier2.equals(conferencier7));
        assertFalse(conferencier2.equals(conferencier8));
        assertFalse(conferencier2.equals(conferencier9));
        assertFalse(conferencier2.equals(conferencier10));
        assertFalse(conferencier2.equals(conferencier11));
        assertFalse(conferencier2.equals(conferencier12));
        assertFalse(conferencier3.equals(conferencier1));
        assertFalse(conferencier3.equals(conferencier2));
        assertFalse(conferencier3.equals(conferencier4));
        assertFalse(conferencier3.equals(conferencier5));
        assertFalse(conferencier3.equals(conferencier6));
        assertFalse(conferencier3.equals(conferencier7));
        assertFalse(conferencier3.equals(conferencier8));
        assertFalse(conferencier3.equals(conferencier9));
        assertFalse(conferencier3.equals(conferencier10));
        assertFalse(conferencier3.equals(conferencier11));
        assertFalse(conferencier3.equals(conferencier12));
        assertFalse(conferencier4.equals(conferencier1));
        assertFalse(conferencier4.equals(conferencier2));
        assertFalse(conferencier4.equals(conferencier3));
        assertFalse(conferencier4.equals(conferencier5));
        assertFalse(conferencier4.equals(conferencier6));
        assertFalse(conferencier4.equals(conferencier7));
        assertFalse(conferencier4.equals(conferencier8));
        assertFalse(conferencier4.equals(conferencier9));
        assertFalse(conferencier4.equals(conferencier10));
        assertFalse(conferencier4.equals(conferencier11));
        assertFalse(conferencier4.equals(conferencier12));
        assertFalse(conferencier5.equals(conferencier1));
        assertFalse(conferencier5.equals(conferencier2));
        assertFalse(conferencier5.equals(conferencier3));
        assertFalse(conferencier5.equals(conferencier4));
        assertFalse(conferencier5.equals(conferencier6));
        assertFalse(conferencier5.equals(conferencier7));
        assertFalse(conferencier5.equals(conferencier8));
        assertFalse(conferencier5.equals(conferencier9));
        assertFalse(conferencier5.equals(conferencier10));
        assertFalse(conferencier5.equals(conferencier11));
        assertFalse(conferencier5.equals(conferencier12));
        
        // Test avec null
        assertFalse(conferencier1.equals(null));
        assertFalse(conferencier2.equals(null));
        assertFalse(conferencier3.equals(null));
        assertFalse(conferencier4.equals(null));
        assertFalse(conferencier5.equals(null));
        assertFalse(conferencier6.equals(null));
        assertFalse(conferencier7.equals(null));
        assertFalse(conferencier8.equals(null));
        assertFalse(conferencier9.equals(null));
        assertFalse(conferencier10.equals(null));
        assertFalse(conferencier11.equals(null));
        assertFalse(conferencier12.equals(null));
        
        // Test avec un objet d'une autre classe
        assertFalse(conferencier1.equals(new Object()));
        assertFalse(conferencier2.equals(new Object()));
        assertFalse(conferencier3.equals(new Object()));
        assertFalse(conferencier4.equals(new Object()));
        assertFalse(conferencier5.equals(new Object()));
        assertFalse(conferencier6.equals(new Object()));
        assertFalse(conferencier7.equals(new Object()));
        assertFalse(conferencier8.equals(new Object()));
        assertFalse(conferencier9.equals(new Object()));
        assertFalse(conferencier10.equals(new Object()));
        assertFalse(conferencier11.equals(new Object()));
        assertFalse(conferencier12.equals(new Object()));
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
                              + "[Art, Histoire]"
                              + ", numéro de téléphone : " + NUMTEL_CONF_VALIDE
                              + ", status(interne ou externe) : " 
                              + EST_INTERNE_VALIDE
                              + ", liste des indisponibilites : " 
                              + "[Le 2/8/2025, Du 22/12/2024 au 26/12/2024]";
        
        //TODO : Corriger test pour conferencier avec indisponibilités vide
        assertEquals(expectedString1, conferencier1.toString());
        
        Conferencier conferencier2 = new Conferencier(ID_CONF_VALIDE, 
        		NOM_CONF_VALIDE, PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE, 
        		NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE);
        
        String expectedString2 = "identifiant : " + ID_CONF_VALIDE
                              + ", nom : " + NOM_CONF_VALIDE
                              + ", prenom : " + PRENOM_CONF_VALIDE
                              + ", specialites : " 
                              + "[Art, Histoire]"
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
