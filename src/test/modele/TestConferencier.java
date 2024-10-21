/*
 * TestConferencier.java                           
 * 15 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.modele;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import application.modele.Conferencier;
import application.modele.Indisponibilite;

/**
 * Classe de Test pour {@link application.modele.Conferencier}
 * @author Romain Augé
 * @author Esteban Vroemen
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
	                    LocalDate.of(2024,12,26)),
    };
    
    private final Conferencier[] CONFERENCIERS_VALIDES =
    {
        new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, PRENOM_CONF_VALIDE,
                         SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
                         EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES),
        new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, PRENOM_CONF_VALIDE,
                         SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
                         EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES),
        new Conferencier("C000002", "Lexpert", "Noemie",
                         new String[] {"peinture", "impressionnisme",
                                       "art contemporain"},
                         "0600000001", true,
                         new Indisponibilite[] {
                             new Indisponibilite(LocalDate.of(2024,10,22)),
                             new Indisponibilite(LocalDate.of(2024,10,26))
                         }),
        new Conferencier("C000003", "Dujardin", "Oceane",
                         new String[] {"art moderne"}, "0611111111", true,
                         new Indisponibilite[] {
                             new Indisponibilite(LocalDate.of(2024,11,07)),
                             new Indisponibilite(LocalDate.of(2024,11,07)),
                             new Indisponibilite(LocalDate.of(2024,11,19)),
                             new Indisponibilite(LocalDate.of(2024,11,22))
                         }),
        new Conferencier("C000008", "Deneuve", "Zoé", 
                         new String[] {"photo","peinture"}, "0600000003",
                         false),
        new Conferencier(ID_CONF_VALIDE, "Vroemen", PRENOM_CONF_VALIDE,
                         SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
                         EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES),
        new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, "Ayoub",
                         SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
                         EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES),
        new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, PRENOM_CONF_VALIDE,
                         new String[] {"photo","peinture"},
                         NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE, 
                         INDISPONIBILITES_VALIDES),
        new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, PRENOM_CONF_VALIDE,
                         SPECIALITE_CONF_VALIDE, "0603080911",
                         EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES),
        new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, PRENOM_CONF_VALIDE,
                         SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE, false, 
                         INDISPONIBILITES_VALIDES),
        new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, PRENOM_CONF_VALIDE,
                         SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
                         EST_INTERNE_VALIDE, 
                         new Indisponibilite[] {
                             new Indisponibilite(LocalDate.of(2024,10,22)),
                             new Indisponibilite(LocalDate.of(2024,10,26))
                         }),
        new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, PRENOM_CONF_VALIDE,
                         SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
                         EST_INTERNE_VALIDE),
    };

    /**
     * Méthode de test pour 
     * {@link application.modele.Conferencier#Conferencier(java.lang.String,
     * java.lang.String, java.lang.String[], java.lang.String, boolean,
     * application.modele.Indisponibilite[])}.
     * Cas uniquement invalides
     */
    @Test
    void testConferencierInvalide() {
        
    	// Test avec identifiant null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(null, NOM_CONF_VALIDE,
                                            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE,
                    		            NUMTEL_CONF_VALIDE,
                    		            EST_INTERNE_VALIDE,
                    		            INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier("", NOM_CONF_VALIDE,
                                            PRENOM_CONF_VALIDE,
                                            SPECIALITE_CONF_VALIDE,
                                            NUMTEL_CONF_VALIDE, 
                    		            EST_INTERNE_VALIDE,
                    		            INDISPONIBILITES_VALIDES));
    	
        // Test avec nom null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, null,
                                            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE,
                    		            NUMTEL_CONF_VALIDE,
                    		            EST_INTERNE_VALIDE,
                    		            INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, "",
                                            PRENOM_CONF_VALIDE, 
                    		            SPECIALITE_CONF_VALIDE,
                    		            NUMTEL_CONF_VALIDE, 
                    		            EST_INTERNE_VALIDE,
                    		            INDISPONIBILITES_VALIDES));

        // Test avec prénom null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE,
                                            null, SPECIALITE_CONF_VALIDE,
                                            NUMTEL_CONF_VALIDE, 
                    		            EST_INTERNE_VALIDE,
                    		            INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, "", 
                    		            SPECIALITE_CONF_VALIDE,
                    		            NUMTEL_CONF_VALIDE, 
                    		            EST_INTERNE_VALIDE,
                    		            INDISPONIBILITES_VALIDES));

        // Test avec spécialité null, vide ou plus de 6 éléments
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE, null,
                    		            NUMTEL_CONF_VALIDE,
                    		            EST_INTERNE_VALIDE,
                    		            INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE, new String[0], 
                    		            NUMTEL_CONF_VALIDE,
                    		            EST_INTERNE_VALIDE, 
                    		            INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE,
                                            PRENOM_CONF_VALIDE, 
                    		            new String[] {"A", "B", "C", "D",
                    		                          "E", "F", "G"}, 
                    		            NUMTEL_CONF_VALIDE,
                    		            EST_INTERNE_VALIDE, 
                    		            INDISPONIBILITES_VALIDES));

        /* Test avec numéro de téléphone null, vide, trop long,
        trop court ou non numérique */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, null, 
                    		            EST_INTERNE_VALIDE,
                    		            INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, "", 
                    		            EST_INTERNE_VALIDE,
                    		            INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE,
                                            PRENOM_CONF_VALIDE,
                                            SPECIALITE_CONF_VALIDE, "12345",
                                            EST_INTERNE_VALIDE,
                    		            INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE,
                                            PRENOM_CONF_VALIDE, 
                    		            SPECIALITE_CONF_VALIDE, "123",
                    		            EST_INTERNE_VALIDE, 
                    		            INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, "12a4", 
                    		            EST_INTERNE_VALIDE,
                    		            INDISPONIBILITES_VALIDES));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
               		                    PRENOM_CONF_VALIDE,
               		                    SPECIALITE_CONF_VALIDE,
               		                    "01234567a9",
               		                    EST_INTERNE_VALIDE, 
               		                    INDISPONIBILITES_VALIDES));
        
        /* Test avec Indisponibilités null */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, 
                    		            NUMTEL_CONF_VALIDE,
                    		            EST_INTERNE_VALIDE, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
               		                    PRENOM_CONF_VALIDE,
               		                    SPECIALITE_CONF_VALIDE,
               		                    NUMTEL_CONF_VALIDE, 
               		                    EST_INTERNE_VALIDE,
               		                    new Indisponibilite[] 
               		                    {new Indisponibilite(
               		                           LocalDate.of(2024, 10, 16)), 
               				     null}));
    

    	// Test avec identifiant null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(null, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, 
                    		            NUMTEL_CONF_VALIDE,
                    		            EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier("", NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, 
                    		            NUMTEL_CONF_VALIDE,
                    		            EST_INTERNE_VALIDE));
    	
        // Test avec nom null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, null, 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, 
                    		            NUMTEL_CONF_VALIDE,
                    		            EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE,"", 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, 
                    		            NUMTEL_CONF_VALIDE,
                    		            EST_INTERNE_VALIDE));

        // Test avec prénom null ou vide
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            null, SPECIALITE_CONF_VALIDE,
                    		            NUMTEL_CONF_VALIDE, 
                    		            EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, "",
                                            SPECIALITE_CONF_VALIDE,
                                            NUMTEL_CONF_VALIDE, 
                    		            EST_INTERNE_VALIDE));

        // Test avec spécialité null, vide ou plus de 6 éléments
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE, null,
                    		            NUMTEL_CONF_VALIDE, 
                    		            EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE, new String[0], 
                    		            NUMTEL_CONF_VALIDE,
                    		            EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE, 
                    	                    new String[] {"A", "B", "C", "D",
                    	                                  "E", "F", "G"}, 
                    		            NUMTEL_CONF_VALIDE,
                    		            EST_INTERNE_VALIDE));

        /* Test avec numéro de téléphone null, vide, trop long,
        trop court ou non numérique */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, null, 
                    		            EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, "", 
                    		            EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, "12345",
                    		            EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, "123", 
                    		            EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
                    		            PRENOM_CONF_VALIDE,
                    		            SPECIALITE_CONF_VALIDE, "12a4", 
                    		            EST_INTERNE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, 
               		                    PRENOM_CONF_VALIDE,
               		                    SPECIALITE_CONF_VALIDE,
               		                    "01234567a9", EST_INTERNE_VALIDE));
    }
    
    /**
     * Méthode de test pour 
     * {@link application.modele.Conferencier#Conferencier(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String[], java.lang.String,
     * boolean, application.modele.Indisponibilite[])}.
     * Cas uniquement valides
     */
    @Test
    void testConferencierValide() {
        
        assertDoesNotThrow(() -> {
            new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE,
                             PRENOM_CONF_VALIDE, SPECIALITE_CONF_VALIDE,
                             NUMTEL_CONF_VALIDE, EST_INTERNE_VALIDE,
                             INDISPONIBILITES_VALIDES);
        });
        		
        assertDoesNotThrow(() -> {
            new Conferencier(ID_CONF_VALIDE,NOM_CONF_VALIDE, PRENOM_CONF_VALIDE,
                             SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
                             EST_INTERNE_VALIDE);
        });
    }
    

    /**
     * Méthode de test pour
     * {@link application.modele.Conferencier#equals(java.lang.Object)}.
     */
    @Test
    void testEquals() {
        
        // Même référence
        assertTrue(CONFERENCIERS_VALIDES[0].equals(CONFERENCIERS_VALIDES[0]));
        assertTrue(CONFERENCIERS_VALIDES[1].equals(CONFERENCIERS_VALIDES[1]));
        assertTrue(CONFERENCIERS_VALIDES[2].equals(CONFERENCIERS_VALIDES[2]));
        assertTrue(CONFERENCIERS_VALIDES[3].equals(CONFERENCIERS_VALIDES[3]));
        assertTrue(CONFERENCIERS_VALIDES[4].equals(CONFERENCIERS_VALIDES[4]));
        assertTrue(CONFERENCIERS_VALIDES[5].equals(CONFERENCIERS_VALIDES[5]));
        assertTrue(CONFERENCIERS_VALIDES[6].equals(CONFERENCIERS_VALIDES[6]));
        assertTrue(CONFERENCIERS_VALIDES[7].equals(CONFERENCIERS_VALIDES[7]));
        assertTrue(CONFERENCIERS_VALIDES[8].equals(CONFERENCIERS_VALIDES[8]));
        assertTrue(CONFERENCIERS_VALIDES[9].equals(CONFERENCIERS_VALIDES[9]));
        assertTrue(CONFERENCIERS_VALIDES[10].equals(CONFERENCIERS_VALIDES[10]));
        assertTrue(CONFERENCIERS_VALIDES[11].equals(CONFERENCIERS_VALIDES[11]));
        
        // Objets avec les mêmes valeurs
        assertTrue(CONFERENCIERS_VALIDES[0].equals(CONFERENCIERS_VALIDES[1]));
        assertTrue(CONFERENCIERS_VALIDES[1].equals(CONFERENCIERS_VALIDES[0]));
        
        // Objets différents
        assertFalse(CONFERENCIERS_VALIDES[0].equals(CONFERENCIERS_VALIDES[2]));
        assertFalse(CONFERENCIERS_VALIDES[0].equals(CONFERENCIERS_VALIDES[3]));
        assertFalse(CONFERENCIERS_VALIDES[0].equals(CONFERENCIERS_VALIDES[4]));
        assertFalse(CONFERENCIERS_VALIDES[0].equals(CONFERENCIERS_VALIDES[5]));
        assertFalse(CONFERENCIERS_VALIDES[0].equals(CONFERENCIERS_VALIDES[6]));
        assertFalse(CONFERENCIERS_VALIDES[0].equals(CONFERENCIERS_VALIDES[7]));
        assertFalse(CONFERENCIERS_VALIDES[0].equals(CONFERENCIERS_VALIDES[8]));
        assertFalse(CONFERENCIERS_VALIDES[0].equals(CONFERENCIERS_VALIDES[9]));
        assertFalse(CONFERENCIERS_VALIDES[0].equals(CONFERENCIERS_VALIDES[10]));
        assertFalse(CONFERENCIERS_VALIDES[0].equals(CONFERENCIERS_VALIDES[11]));
        assertFalse(CONFERENCIERS_VALIDES[1].equals(CONFERENCIERS_VALIDES[2]));
        assertFalse(CONFERENCIERS_VALIDES[1].equals(CONFERENCIERS_VALIDES[3]));
        assertFalse(CONFERENCIERS_VALIDES[1].equals(CONFERENCIERS_VALIDES[4]));
        assertFalse(CONFERENCIERS_VALIDES[1].equals(CONFERENCIERS_VALIDES[5]));
        assertFalse(CONFERENCIERS_VALIDES[1].equals(CONFERENCIERS_VALIDES[6]));
        assertFalse(CONFERENCIERS_VALIDES[1].equals(CONFERENCIERS_VALIDES[7]));
        assertFalse(CONFERENCIERS_VALIDES[1].equals(CONFERENCIERS_VALIDES[8]));
        assertFalse(CONFERENCIERS_VALIDES[1].equals(CONFERENCIERS_VALIDES[9]));
        assertFalse(CONFERENCIERS_VALIDES[1].equals(CONFERENCIERS_VALIDES[10]));
        assertFalse(CONFERENCIERS_VALIDES[1].equals(CONFERENCIERS_VALIDES[11]));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(CONFERENCIERS_VALIDES[0]));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(CONFERENCIERS_VALIDES[1]));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(CONFERENCIERS_VALIDES[3]));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(CONFERENCIERS_VALIDES[4]));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(CONFERENCIERS_VALIDES[5]));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(CONFERENCIERS_VALIDES[6]));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(CONFERENCIERS_VALIDES[7]));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(CONFERENCIERS_VALIDES[8]));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(CONFERENCIERS_VALIDES[9]));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(CONFERENCIERS_VALIDES[10]));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(CONFERENCIERS_VALIDES[11]));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(CONFERENCIERS_VALIDES[0]));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(CONFERENCIERS_VALIDES[1]));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(CONFERENCIERS_VALIDES[2]));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(CONFERENCIERS_VALIDES[4]));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(CONFERENCIERS_VALIDES[5]));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(CONFERENCIERS_VALIDES[6]));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(CONFERENCIERS_VALIDES[7]));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(CONFERENCIERS_VALIDES[8]));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(CONFERENCIERS_VALIDES[9]));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(CONFERENCIERS_VALIDES[10]));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(CONFERENCIERS_VALIDES[11]));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(CONFERENCIERS_VALIDES[0]));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(CONFERENCIERS_VALIDES[1]));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(CONFERENCIERS_VALIDES[2]));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(CONFERENCIERS_VALIDES[3]));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(CONFERENCIERS_VALIDES[5]));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(CONFERENCIERS_VALIDES[6]));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(CONFERENCIERS_VALIDES[7]));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(CONFERENCIERS_VALIDES[8]));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(CONFERENCIERS_VALIDES[9]));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(CONFERENCIERS_VALIDES[10]));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(CONFERENCIERS_VALIDES[11]));
        
        // Test avec null
        assertFalse(CONFERENCIERS_VALIDES[0].equals(null));
        assertFalse(CONFERENCIERS_VALIDES[1].equals(null));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(null));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(null));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(null));
        assertFalse(CONFERENCIERS_VALIDES[5].equals(null));
        assertFalse(CONFERENCIERS_VALIDES[6].equals(null));
        assertFalse(CONFERENCIERS_VALIDES[7].equals(null));
        assertFalse(CONFERENCIERS_VALIDES[8].equals(null));
        assertFalse(CONFERENCIERS_VALIDES[9].equals(null));
        assertFalse(CONFERENCIERS_VALIDES[10].equals(null));
        assertFalse(CONFERENCIERS_VALIDES[11].equals(null));
        
        // Test avec un objet d'une autre classe
        assertFalse(CONFERENCIERS_VALIDES[0].equals(new Object()));
        assertFalse(CONFERENCIERS_VALIDES[1].equals(new Object()));
        assertFalse(CONFERENCIERS_VALIDES[2].equals(new Object()));
        assertFalse(CONFERENCIERS_VALIDES[3].equals(new Object()));
        assertFalse(CONFERENCIERS_VALIDES[4].equals(new Object()));
        assertFalse(CONFERENCIERS_VALIDES[5].equals(new Object()));
        assertFalse(CONFERENCIERS_VALIDES[6].equals(new Object()));
        assertFalse(CONFERENCIERS_VALIDES[7].equals(new Object()));
        assertFalse(CONFERENCIERS_VALIDES[8].equals(new Object()));
        assertFalse(CONFERENCIERS_VALIDES[9].equals(new Object()));
        assertFalse(CONFERENCIERS_VALIDES[10].equals(new Object()));
        assertFalse(CONFERENCIERS_VALIDES[11].equals(new Object()));
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#toString()}.
     */
    @Test
    void testToString() {
        Conferencier conferencier1
        = new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, PRENOM_CONF_VALIDE,
                           SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
                           EST_INTERNE_VALIDE, INDISPONIBILITES_VALIDES);
        
        String expectedString1 = "identifiant : " + ID_CONF_VALIDE
                                 + ", nom : " + NOM_CONF_VALIDE
                                 + ", prenom : " + PRENOM_CONF_VALIDE
                                 + ", specialites : " 
                                 + "[Art, Histoire]"
                                 + ", numéro de téléphone : "
                                 + NUMTEL_CONF_VALIDE
                                 + ", status(interne ou externe) : " 
                                 + EST_INTERNE_VALIDE
                                 + ", liste des indisponibilites : " 
                                 + "[Le 2/8/2025, Du 22/12/2024 au 26/12/2024]";
        
        assertEquals(expectedString1, CONFERENCIERS_VALIDES[0].toString());
        
        // TODO Faire les tests toString sur le tableau des conférenciers
//        Conferencier conferencier2 
//        = new Conferencier(ID_CONF_VALIDE, NOM_CONF_VALIDE, PRENOM_CONF_VALIDE,
//                           SPECIALITE_CONF_VALIDE, NUMTEL_CONF_VALIDE,
//                           EST_INTERNE_VALIDE);
//        
//        String expectedString2 = "identifiant : " + ID_CONF_VALIDE
//                                 + ", nom : " + NOM_CONF_VALIDE
//                                 + ", prenom : " + PRENOM_CONF_VALIDE
//                                 + ", specialites : " 
//                                 + "[Art, Histoire]"
//                                 + ", numéro de téléphone : "
//                                 + NUMTEL_CONF_VALIDE
//                                 + ", status(interne ou externe) : " 
//                                 + EST_INTERNE_VALIDE;
//        
//        assertEquals(expectedString2, CONFERENCIERS_VALIDES[1].toString());
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Conferencier#getIdentifiant()}.
     */
    @Test
    void testGetIdentifiant() {
        
        assertEquals("C000001", CONFERENCIERS_VALIDES[0].getIdentifiant());
        assertEquals("C000001", CONFERENCIERS_VALIDES[1].getIdentifiant());
        assertEquals("C000002", CONFERENCIERS_VALIDES[2].getIdentifiant());
        assertEquals("C000003", CONFERENCIERS_VALIDES[3].getIdentifiant());
        assertEquals("C000008", CONFERENCIERS_VALIDES[4].getIdentifiant());
        assertEquals("C000001", CONFERENCIERS_VALIDES[5].getIdentifiant());
        assertEquals("C000001", CONFERENCIERS_VALIDES[6].getIdentifiant());
        assertEquals("C000001", CONFERENCIERS_VALIDES[7].getIdentifiant());
        assertEquals("C000001", CONFERENCIERS_VALIDES[8].getIdentifiant());
        assertEquals("C000001", CONFERENCIERS_VALIDES[9].getIdentifiant());
        assertEquals("C000001", CONFERENCIERS_VALIDES[10].getIdentifiant());
        assertEquals("C000001", CONFERENCIERS_VALIDES[11].getIdentifiant());
        
        assertNotEquals(null, CONFERENCIERS_VALIDES[0].getIdentifiant());
        assertNotEquals(null, CONFERENCIERS_VALIDES[1].getIdentifiant());
        assertNotEquals(null, CONFERENCIERS_VALIDES[2].getIdentifiant());
        assertNotEquals(null, CONFERENCIERS_VALIDES[3].getIdentifiant());
        assertNotEquals(null, CONFERENCIERS_VALIDES[4].getIdentifiant());
        assertNotEquals(null, CONFERENCIERS_VALIDES[5].getIdentifiant());
        assertNotEquals(null, CONFERENCIERS_VALIDES[6].getIdentifiant());
        assertNotEquals(null, CONFERENCIERS_VALIDES[7].getIdentifiant());
        assertNotEquals(null, CONFERENCIERS_VALIDES[8].getIdentifiant());
        assertNotEquals(null, CONFERENCIERS_VALIDES[9].getIdentifiant());
        assertNotEquals(null, CONFERENCIERS_VALIDES[10].getIdentifiant());
        assertNotEquals(null, CONFERENCIERS_VALIDES[11].getIdentifiant());
        assertNotEquals("", CONFERENCIERS_VALIDES[0].getIdentifiant());
        assertNotEquals("", CONFERENCIERS_VALIDES[1].getIdentifiant());
        assertNotEquals("", CONFERENCIERS_VALIDES[2].getIdentifiant());
        assertNotEquals("", CONFERENCIERS_VALIDES[3].getIdentifiant());
        assertNotEquals("", CONFERENCIERS_VALIDES[4].getIdentifiant());
        assertNotEquals("", CONFERENCIERS_VALIDES[5].getIdentifiant());
        assertNotEquals("", CONFERENCIERS_VALIDES[6].getIdentifiant());
        assertNotEquals("", CONFERENCIERS_VALIDES[7].getIdentifiant());
        assertNotEquals("", CONFERENCIERS_VALIDES[8].getIdentifiant());
        assertNotEquals("", CONFERENCIERS_VALIDES[9].getIdentifiant());
        assertNotEquals("", CONFERENCIERS_VALIDES[10].getIdentifiant());
        assertNotEquals("", CONFERENCIERS_VALIDES[11].getIdentifiant());
    }
    
    /**
     * Méthode de test pour {@link application.modele.Conferencier#getNom()}.
     */
    @Test
    void testGetNom() {
        
        assertEquals("Dupont", CONFERENCIERS_VALIDES[0].getNom());
        assertEquals("Dupont", CONFERENCIERS_VALIDES[1].getNom());
        assertEquals("Lexpert", CONFERENCIERS_VALIDES[2].getNom());
        assertEquals("Dujardin", CONFERENCIERS_VALIDES[3].getNom());
        assertEquals("Deneuve", CONFERENCIERS_VALIDES[4].getNom());
        assertEquals("Vroemen", CONFERENCIERS_VALIDES[5].getNom());
        assertEquals("Dupont", CONFERENCIERS_VALIDES[6].getNom());
        assertEquals("Dupont", CONFERENCIERS_VALIDES[7].getNom());
        assertEquals("Dupont", CONFERENCIERS_VALIDES[8].getNom());
        assertEquals("Dupont", CONFERENCIERS_VALIDES[9].getNom());
        assertEquals("Dupont", CONFERENCIERS_VALIDES[10].getNom());
        assertEquals("Dupont", CONFERENCIERS_VALIDES[11].getNom());
        
        assertNotEquals(null, CONFERENCIERS_VALIDES[0].getNom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[1].getNom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[2].getNom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[3].getNom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[4].getNom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[5].getNom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[6].getNom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[7].getNom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[8].getNom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[9].getNom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[10].getNom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[11].getNom());
        assertNotEquals("", CONFERENCIERS_VALIDES[0].getNom());
        assertNotEquals("", CONFERENCIERS_VALIDES[1].getNom());
        assertNotEquals("", CONFERENCIERS_VALIDES[2].getNom());
        assertNotEquals("", CONFERENCIERS_VALIDES[3].getNom());
        assertNotEquals("", CONFERENCIERS_VALIDES[4].getNom());
        assertNotEquals("", CONFERENCIERS_VALIDES[5].getNom());
        assertNotEquals("", CONFERENCIERS_VALIDES[6].getNom());
        assertNotEquals("", CONFERENCIERS_VALIDES[7].getNom());
        assertNotEquals("", CONFERENCIERS_VALIDES[8].getNom());
        assertNotEquals("", CONFERENCIERS_VALIDES[9].getNom());
        assertNotEquals("", CONFERENCIERS_VALIDES[10].getNom());
        assertNotEquals("", CONFERENCIERS_VALIDES[11].getNom());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getPrenom()}.
     */
    @Test
    void testGetPrenom() {
        
        assertEquals("Pierre", CONFERENCIERS_VALIDES[0].getPrenom());
        assertEquals("Pierre", CONFERENCIERS_VALIDES[1].getPrenom());
        assertEquals("Noemie", CONFERENCIERS_VALIDES[2].getPrenom());
        assertEquals("Oceane", CONFERENCIERS_VALIDES[3].getPrenom());
        assertEquals("Zoé", CONFERENCIERS_VALIDES[4].getPrenom());
        assertEquals("Pierre", CONFERENCIERS_VALIDES[5].getPrenom());
        assertEquals("Ayoub", CONFERENCIERS_VALIDES[6].getPrenom());
        assertEquals("Pierre", CONFERENCIERS_VALIDES[7].getPrenom());
        assertEquals("Pierre", CONFERENCIERS_VALIDES[8].getPrenom());
        assertEquals("Pierre", CONFERENCIERS_VALIDES[9].getPrenom());
        assertEquals("Pierre", CONFERENCIERS_VALIDES[10].getPrenom());
        assertEquals("Pierre", CONFERENCIERS_VALIDES[11].getPrenom());
        
        assertNotEquals(null, CONFERENCIERS_VALIDES[0].getPrenom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[1].getPrenom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[2].getPrenom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[3].getPrenom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[4].getPrenom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[5].getPrenom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[6].getPrenom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[7].getPrenom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[8].getPrenom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[9].getPrenom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[10].getPrenom());
        assertNotEquals(null, CONFERENCIERS_VALIDES[11].getPrenom());
        assertNotEquals("", CONFERENCIERS_VALIDES[0].getPrenom());
        assertNotEquals("", CONFERENCIERS_VALIDES[1].getPrenom());
        assertNotEquals("", CONFERENCIERS_VALIDES[2].getPrenom());
        assertNotEquals("", CONFERENCIERS_VALIDES[3].getPrenom());
        assertNotEquals("", CONFERENCIERS_VALIDES[4].getPrenom());
        assertNotEquals("", CONFERENCIERS_VALIDES[5].getPrenom());
        assertNotEquals("", CONFERENCIERS_VALIDES[6].getPrenom());
        assertNotEquals("", CONFERENCIERS_VALIDES[7].getPrenom());
        assertNotEquals("", CONFERENCIERS_VALIDES[8].getPrenom());
        assertNotEquals("", CONFERENCIERS_VALIDES[9].getPrenom());
        assertNotEquals("", CONFERENCIERS_VALIDES[10].getPrenom());
        assertNotEquals("", CONFERENCIERS_VALIDES[11].getPrenom());
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Conferencier#getSpecialite()}.
     */
    @Test
    void testGetSpecialite() {
        
        assertArrayEquals(new String[] {"Art", "Histoire"},
                          CONFERENCIERS_VALIDES[0].getSpecialites());
        assertArrayEquals(new String[] {"Art", "Histoire"},
                          CONFERENCIERS_VALIDES[1].getSpecialites());
        assertArrayEquals(
            new String[] {"peinture", "impressionnisme", "art contemporain"},
            CONFERENCIERS_VALIDES[2].getSpecialites()
        );
        assertArrayEquals(new String[] {"art moderne"}, 
                          CONFERENCIERS_VALIDES[3].getSpecialites());
        assertArrayEquals(new String[] {"photo","peinture"},
                          CONFERENCIERS_VALIDES[4].getSpecialites());
        assertArrayEquals(new String[] {"Art", "Histoire"},
                          CONFERENCIERS_VALIDES[5].getSpecialites());
        assertArrayEquals(new String[] {"Art", "Histoire"},
                          CONFERENCIERS_VALIDES[6].getSpecialites());
        assertArrayEquals(new String[] {"photo","peinture"},
                          CONFERENCIERS_VALIDES[7].getSpecialites());
        assertArrayEquals(new String[] {"Art", "Histoire"},
                          CONFERENCIERS_VALIDES[8].getSpecialites());
        assertArrayEquals(new String[] {"Art", "Histoire"},
                          CONFERENCIERS_VALIDES[9].getSpecialites());
        assertArrayEquals(new String[] {"Art", "Histoire"},
                          CONFERENCIERS_VALIDES[10].getSpecialites());
        assertArrayEquals(new String[] {"Art", "Histoire"},
                          CONFERENCIERS_VALIDES[11].getSpecialites());
        
        assertNotEquals(null, CONFERENCIERS_VALIDES[0].getSpecialites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[1].getSpecialites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[2].getSpecialites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[3].getSpecialites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[4].getSpecialites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[5].getSpecialites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[6].getSpecialites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[7].getSpecialites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[8].getSpecialites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[9].getSpecialites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[10].getSpecialites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[11].getSpecialites());
        assertNotEquals("", CONFERENCIERS_VALIDES[0].getSpecialites());
        assertNotEquals("", CONFERENCIERS_VALIDES[1].getSpecialites());
        assertNotEquals("", CONFERENCIERS_VALIDES[2].getSpecialites());
        assertNotEquals("", CONFERENCIERS_VALIDES[3].getSpecialites());
        assertNotEquals("", CONFERENCIERS_VALIDES[4].getSpecialites());
        assertNotEquals("", CONFERENCIERS_VALIDES[5].getSpecialites());
        assertNotEquals("", CONFERENCIERS_VALIDES[6].getSpecialites());
        assertNotEquals("", CONFERENCIERS_VALIDES[7].getSpecialites());
        assertNotEquals("", CONFERENCIERS_VALIDES[8].getSpecialites());
        assertNotEquals("", CONFERENCIERS_VALIDES[9].getSpecialites());
        assertNotEquals("", CONFERENCIERS_VALIDES[10].getSpecialites());
        assertNotEquals("", CONFERENCIERS_VALIDES[11].getSpecialites());
    }

    /**
     * Méthode de test pour {@link application.modele.Conferencier#getNumTel()}.
     */
    @Test
    void testGetNumTel() {
        
        assertEquals("0123456789", CONFERENCIERS_VALIDES[0].getNumTel());
        assertEquals("0123456789", CONFERENCIERS_VALIDES[1].getNumTel());
        assertEquals("0600000001", CONFERENCIERS_VALIDES[2].getNumTel());
        assertEquals("0611111111", CONFERENCIERS_VALIDES[3].getNumTel());
        assertEquals("0600000003", CONFERENCIERS_VALIDES[4].getNumTel());
        assertEquals("0123456789", CONFERENCIERS_VALIDES[5].getNumTel());
        assertEquals("0123456789", CONFERENCIERS_VALIDES[6].getNumTel());
        assertEquals("0123456789", CONFERENCIERS_VALIDES[7].getNumTel());
        assertEquals("0603080911", CONFERENCIERS_VALIDES[8].getNumTel());
        assertEquals("0123456789", CONFERENCIERS_VALIDES[9].getNumTel());
        assertEquals("0123456789", CONFERENCIERS_VALIDES[10].getNumTel());
        assertEquals("0123456789", CONFERENCIERS_VALIDES[11].getNumTel());
        
        assertNotEquals(null, CONFERENCIERS_VALIDES[0].getNumTel());
        assertNotEquals(null, CONFERENCIERS_VALIDES[1].getNumTel());
        assertNotEquals(null, CONFERENCIERS_VALIDES[2].getNumTel());
        assertNotEquals(null, CONFERENCIERS_VALIDES[3].getNumTel());
        assertNotEquals(null, CONFERENCIERS_VALIDES[4].getNumTel());
        assertNotEquals(null, CONFERENCIERS_VALIDES[5].getNumTel());
        assertNotEquals(null, CONFERENCIERS_VALIDES[6].getNumTel());
        assertNotEquals(null, CONFERENCIERS_VALIDES[7].getNumTel());
        assertNotEquals(null, CONFERENCIERS_VALIDES[8].getNumTel());
        assertNotEquals(null, CONFERENCIERS_VALIDES[9].getNumTel());
        assertNotEquals(null, CONFERENCIERS_VALIDES[10].getNumTel());
        assertNotEquals(null, CONFERENCIERS_VALIDES[11].getNumTel());
        assertNotEquals("", CONFERENCIERS_VALIDES[0].getNumTel());
        assertNotEquals("", CONFERENCIERS_VALIDES[1].getNumTel());
        assertNotEquals("", CONFERENCIERS_VALIDES[2].getNumTel());
        assertNotEquals("", CONFERENCIERS_VALIDES[3].getNumTel());
        assertNotEquals("", CONFERENCIERS_VALIDES[4].getNumTel());
        assertNotEquals("", CONFERENCIERS_VALIDES[5].getNumTel());
        assertNotEquals("", CONFERENCIERS_VALIDES[6].getNumTel());
        assertNotEquals("", CONFERENCIERS_VALIDES[7].getNumTel());
        assertNotEquals("", CONFERENCIERS_VALIDES[8].getNumTel());
        assertNotEquals("", CONFERENCIERS_VALIDES[9].getNumTel());
        assertNotEquals("", CONFERENCIERS_VALIDES[10].getNumTel());
        assertNotEquals("", CONFERENCIERS_VALIDES[11].getNumTel());
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Conferencier#getEstInterne()}.
     */
    @Test
    void testGetEstInterne() {
        
        assertTrue(CONFERENCIERS_VALIDES[0].estInterne());
        assertTrue(CONFERENCIERS_VALIDES[1].estInterne());
        assertTrue(CONFERENCIERS_VALIDES[2].estInterne());
        assertTrue(CONFERENCIERS_VALIDES[3].estInterne());
        assertFalse(CONFERENCIERS_VALIDES[4].estInterne());
        assertTrue(CONFERENCIERS_VALIDES[5].estInterne());
        assertTrue(CONFERENCIERS_VALIDES[6].estInterne());
        assertTrue(CONFERENCIERS_VALIDES[7].estInterne());
        assertTrue(CONFERENCIERS_VALIDES[8].estInterne());
        assertFalse(CONFERENCIERS_VALIDES[9].estInterne());
        assertTrue(CONFERENCIERS_VALIDES[10].estInterne());
        assertTrue(CONFERENCIERS_VALIDES[11].estInterne());
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Conferencier#getIndisponibilites()}.
     */
    @Test
    void testGetIndisponibilites() {
        
        assertArrayEquals(
            new Indisponibilite[] {
                new Indisponibilite(LocalDate.of (2025, 8, 2)), 
                new Indisponibilite(LocalDate.of (2024, 12, 22), 
                                    LocalDate.of(2024,12,26)),
            },
            CONFERENCIERS_VALIDES[0].getIndisponibilites()
        );
        assertArrayEquals(
            new Indisponibilite[] {
                new Indisponibilite(LocalDate.of (2025, 8, 2)), 
                new Indisponibilite(LocalDate.of (2024, 12, 22), 
                                    LocalDate.of(2024,12,26)),
            },
            CONFERENCIERS_VALIDES[1].getIndisponibilites()
        );
        assertArrayEquals(
            new Indisponibilite[] {
                new Indisponibilite(LocalDate.of(2024,10,22)),
                new Indisponibilite(LocalDate.of(2024,10,26))
            },
            CONFERENCIERS_VALIDES[2].getIndisponibilites()
        );
        assertArrayEquals(
            new Indisponibilite[] {
                new Indisponibilite(LocalDate.of(2024,11,07)),
                new Indisponibilite(LocalDate.of(2024,11,07)),
                new Indisponibilite(LocalDate.of(2024,11,19)),
                new Indisponibilite(LocalDate.of(2024,11,22))
            },
            CONFERENCIERS_VALIDES[3].getIndisponibilites()
        );
        assertEquals(null, CONFERENCIERS_VALIDES[4].getIndisponibilites());
        assertArrayEquals(
            new Indisponibilite[] {
                new Indisponibilite(LocalDate.of (2025, 8, 2)), 
                new Indisponibilite(LocalDate.of (2024, 12, 22), 
                                    LocalDate.of(2024,12,26)),
            },
            CONFERENCIERS_VALIDES[5].getIndisponibilites()
        );
        assertArrayEquals(
            new Indisponibilite[] {
                new Indisponibilite(LocalDate.of (2025, 8, 2)), 
                new Indisponibilite(LocalDate.of (2024, 12, 22), 
                                    LocalDate.of(2024,12,26)),
            },
            CONFERENCIERS_VALIDES[6].getIndisponibilites()
        );
        assertArrayEquals(
            new Indisponibilite[] {
                new Indisponibilite(LocalDate.of (2025, 8, 2)), 
                new Indisponibilite(LocalDate.of (2024, 12, 22), 
                                    LocalDate.of(2024,12,26)),
            },
            CONFERENCIERS_VALIDES[7].getIndisponibilites()
        );
        assertArrayEquals(
            new Indisponibilite[] {
                new Indisponibilite(LocalDate.of (2025, 8, 2)), 
                new Indisponibilite(LocalDate.of (2024, 12, 22), 
                                    LocalDate.of(2024,12,26)),
            },
            CONFERENCIERS_VALIDES[8].getIndisponibilites()
        );
        assertArrayEquals(
            new Indisponibilite[] {
                new Indisponibilite(LocalDate.of (2025, 8, 2)), 
                new Indisponibilite(LocalDate.of (2024, 12, 22), 
                                    LocalDate.of(2024,12,26)),
            },
            CONFERENCIERS_VALIDES[9].getIndisponibilites()
        );
        assertArrayEquals(
            new Indisponibilite[] {
                new Indisponibilite(LocalDate.of(2024,10,22)),
                new Indisponibilite(LocalDate.of(2024,10,26))
            },
            CONFERENCIERS_VALIDES[10].getIndisponibilites()
        );
        assertEquals(null,CONFERENCIERS_VALIDES[11].getIndisponibilites());
        
        assertNotEquals(null, CONFERENCIERS_VALIDES[0].getIndisponibilites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[1].getIndisponibilites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[2].getIndisponibilites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[3].getIndisponibilites());
        assertNotEquals(
            new Indisponibilite[] {
                new Indisponibilite(LocalDate.of (2025, 8, 2)), 
                new Indisponibilite(LocalDate.of (2024, 12, 22), 
                                    LocalDate.of(2024,12,26)),
            },
            CONFERENCIERS_VALIDES[4].getIndisponibilites()
        );
        assertNotEquals(null, CONFERENCIERS_VALIDES[5].getIndisponibilites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[6].getIndisponibilites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[7].getIndisponibilites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[8].getIndisponibilites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[9].getIndisponibilites());
        assertNotEquals(null, CONFERENCIERS_VALIDES[10].getIndisponibilites());
        assertNotEquals(
            new Indisponibilite[] {
                new Indisponibilite(LocalDate.of (2025, 8, 2)), 
                new Indisponibilite(LocalDate.of (2024, 12, 22), 
                                    LocalDate.of(2024,12,26)),
            },
            CONFERENCIERS_VALIDES[11].getIndisponibilites()
        );
        assertNotEquals("", CONFERENCIERS_VALIDES[0].getIndisponibilites());
        assertNotEquals("", CONFERENCIERS_VALIDES[1].getIndisponibilites());
        assertNotEquals("", CONFERENCIERS_VALIDES[2].getIndisponibilites());
        assertNotEquals("", CONFERENCIERS_VALIDES[3].getIndisponibilites());
        assertNotEquals("", CONFERENCIERS_VALIDES[4].getIndisponibilites());
        assertNotEquals("", CONFERENCIERS_VALIDES[5].getIndisponibilites());
        assertNotEquals("", CONFERENCIERS_VALIDES[6].getIndisponibilites());
        assertNotEquals("", CONFERENCIERS_VALIDES[7].getIndisponibilites());
        assertNotEquals("", CONFERENCIERS_VALIDES[8].getIndisponibilites());
        assertNotEquals("", CONFERENCIERS_VALIDES[9].getIndisponibilites());
        assertNotEquals("", CONFERENCIERS_VALIDES[10].getIndisponibilites());
        assertNotEquals("", CONFERENCIERS_VALIDES[11].getIndisponibilites());
        assertNotEquals(new Indisponibilite[0],
                        CONFERENCIERS_VALIDES[0].getIndisponibilites());
        assertNotEquals(new Indisponibilite[0],
                        CONFERENCIERS_VALIDES[1].getIndisponibilites());
        assertNotEquals(new Indisponibilite[0],
                        CONFERENCIERS_VALIDES[2].getIndisponibilites());
        assertNotEquals(new Indisponibilite[0],
                        CONFERENCIERS_VALIDES[3].getIndisponibilites());
        assertNotEquals(new Indisponibilite[0],
                        CONFERENCIERS_VALIDES[4].getIndisponibilites());
        assertNotEquals(new Indisponibilite[0],
                        CONFERENCIERS_VALIDES[5].getIndisponibilites());
        assertNotEquals(new Indisponibilite[0],
                        CONFERENCIERS_VALIDES[6].getIndisponibilites());
        assertNotEquals(new Indisponibilite[0],
                        CONFERENCIERS_VALIDES[7].getIndisponibilites());
        assertNotEquals(new Indisponibilite[0],
                        CONFERENCIERS_VALIDES[8].getIndisponibilites());
        assertNotEquals(new Indisponibilite[0],
                        CONFERENCIERS_VALIDES[9].getIndisponibilites());
        assertNotEquals(new Indisponibilite[0],
                        CONFERENCIERS_VALIDES[10].getIndisponibilites());
        assertNotEquals(new Indisponibilite[0],
                        CONFERENCIERS_VALIDES[11].getIndisponibilites());
    }
}
