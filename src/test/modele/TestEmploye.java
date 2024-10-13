/*
 * TestEmploye.java                           
 * 9 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.modele.Employe;

/**
 * Classe de test pour {@link application.modele.Employe}
 * @author Esteban Vroemen
 * @version 1.0
 */
class TestEmploye {
    
    private final String IDENTIFIANT_EMPLOYE_VALIDE = "N000001";
    
    private final String NOM_EMPLOYE_VALIDE = "Mathieu";
    
    private final String PRENOM_EMPLOYE_VALIDE = "Bernoulli";
    
    private final String NUMTEL_EMPLOYE_VALIDE = "2345";
    
    private final Employe[] EMPLOYES_VALIDES =
    {
        new Employe("N000001", "Thenieres", "Baptiste", "1234"),
        new Employe("N000002", "Laluti", "Ayoub", "5678"),
        new Employe("N000003", "Augé", "Romain", "9876"),
        new Employe("N000004", "Vroemen", "Esteban", "2049"),
    };
    
    /**
     * Méthode de test pour 
     * {@link application.modele.Employe#Employe(java.lang.String, java.lang.String, java.lang.String)}
     * et
     * {@link application.modele.Employe#Employe(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * Cas uniquement invalides
     */
    @Test
    void testEmployeInvalide() {
        
        /*
         * Tests spécifiques au constructeur à 3 arguments
         */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(null, null, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe("", null, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(null, "", null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(null, null, ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe("", "", null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(null, "", ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe("", null, ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe("", "", ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(" ", "  ", "  "));
        
        /*
         * Tests avec un unique argument null
         */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, 
                                       NOM_EMPLOYE_VALIDE, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(null, NOM_EMPLOYE_VALIDE, 
                                       PRENOM_EMPLOYE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, null, 
                                       PRENOM_EMPLOYE_VALIDE));
        
        /*
         * Tests avec un unique argument vide
         */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, 
                                       NOM_EMPLOYE_VALIDE, ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe("", NOM_EMPLOYE_VALIDE, 
                                       PRENOM_EMPLOYE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, "", 
                                       PRENOM_EMPLOYE_VALIDE));
        
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, null, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(null, NOM_EMPLOYE_VALIDE, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(null, null, PRENOM_EMPLOYE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, "", ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe("", NOM_EMPLOYE_VALIDE, ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe("", "", PRENOM_EMPLOYE_VALIDE));
        
        /*
         * Tests spécifiques au constructeur à 4 arguments
         */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(null, null, null, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe("", null, null, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(null, "", null, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(null, null, "", null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(null, null, null, ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe("", "", null, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe("", null, "", null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe("", null, null, ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(" ", "  ", "   ", "    "));
        
        /*
         * Tests avec un unique argument null
         */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, 
                                       NOM_EMPLOYE_VALIDE, null,
                                       NUMTEL_EMPLOYE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(null, NOM_EMPLOYE_VALIDE, 
                                       PRENOM_EMPLOYE_VALIDE,
                                       NUMTEL_EMPLOYE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, null, 
                                       PRENOM_EMPLOYE_VALIDE,
                                       NUMTEL_EMPLOYE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE,
                                       NOM_EMPLOYE_VALIDE, 
                                       PRENOM_EMPLOYE_VALIDE, null));
        
        /*
         * Tests avec un unique argument vide
         */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, 
                                       NOM_EMPLOYE_VALIDE, "",
                                       NUMTEL_EMPLOYE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe("", NOM_EMPLOYE_VALIDE, 
                                       PRENOM_EMPLOYE_VALIDE,
                                       NUMTEL_EMPLOYE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, "", 
                                       PRENOM_EMPLOYE_VALIDE,
                                       NUMTEL_EMPLOYE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE,
                                       NOM_EMPLOYE_VALIDE, 
                                       PRENOM_EMPLOYE_VALIDE, ""));
        

        
        /*
         * Tests spécifiques aux numéros de téléphone
         * Identifiant, nom et prénom toujours valides
         */
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, 
                                       NOM_EMPLOYE_VALIDE,
                                       PRENOM_EMPLOYE_VALIDE, null));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, 
                                       NOM_EMPLOYE_VALIDE,
                                       PRENOM_EMPLOYE_VALIDE, ""));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, 
                                       NOM_EMPLOYE_VALIDE,
                                       PRENOM_EMPLOYE_VALIDE, "123"));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, 
                                       NOM_EMPLOYE_VALIDE,
                                       PRENOM_EMPLOYE_VALIDE, "12345"));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, 
                                       NOM_EMPLOYE_VALIDE,
                                       PRENOM_EMPLOYE_VALIDE, "12a4"));
    }

    /**
     * Méthode de test pour 
     * {@link application.modele.Employe#Employe(java.lang.String, java.lang.String, java.lang.String)}
     * et
     * {@link application.modele.Employe#Employe(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * Cas uniquement valides
     */
    @Test
    void testEmployeValide() {
        
        /*
         * Tests spécifiques au constructeur à 3 arguments
         */
        assertDoesNotThrow(() -> new Employe(IDENTIFIANT_EMPLOYE_VALIDE, 
                                             NOM_EMPLOYE_VALIDE,
                                             PRENOM_EMPLOYE_VALIDE));
        assertDoesNotThrow(() -> new Employe("N000002", "Thenieres", 
                                             "Baptiste"));
        assertDoesNotThrow(() -> new Employe("N000003", "Laluti", "Ayoub"));
        assertDoesNotThrow(() -> new Employe("N000004", "Augé", "Romain"));
        assertDoesNotThrow(() -> new Employe("N000005", "Vroemen", "Esteban"));
        
        /*
         * Tests spécifiques au constructeur à 4 arguments
         */
        assertDoesNotThrow(() -> new Employe("N000001", "Thenieres", "Baptiste", 
                                             "1234"));
        assertDoesNotThrow(() -> new Employe("N000002", "Laluti", "Ayoub", 
                                             "5678"));
        assertDoesNotThrow(() -> new Employe("N000003", "Augé", "Romain", 
                                             "9876"));
        assertDoesNotThrow(() -> new Employe("N000004", "Vroemen", "Esteban", 
                                             "2049"));
    }

    /**
     * Méthode de test pour 
     * {@link application.modele.Employe#getIdentifiant()}.
     */
    @Test
    void testGetIdentifiant() {

        assertEquals("N000001", EMPLOYES_VALIDES[0].getIdentifiant());
        assertEquals("N000002", EMPLOYES_VALIDES[1].getIdentifiant());
        assertEquals("N000003", EMPLOYES_VALIDES[2].getIdentifiant());
        assertEquals("N000004", EMPLOYES_VALIDES[3].getIdentifiant());
        
        assertNotEquals(null, EMPLOYES_VALIDES[1].getIdentifiant());
        assertNotEquals("", EMPLOYES_VALIDES[2].getIdentifiant());
        assertNotEquals("  ", EMPLOYES_VALIDES[3].getIdentifiant());
        assertNotEquals("E0000001", EMPLOYES_VALIDES[0].getIdentifiant());
    }

    /**
     * Méthode de test pour {@link application.modele.Employe#getNumTel()}.
     */
    @Test
    void testGetNumTel() {
        
        assertEquals("1234", EMPLOYES_VALIDES[0].getNumTel());
        assertEquals("5678", EMPLOYES_VALIDES[1].getNumTel());
        assertEquals("9876", EMPLOYES_VALIDES[2].getNumTel());
        assertEquals("2049", EMPLOYES_VALIDES[3].getNumTel());
        
        assertNotEquals(null, EMPLOYES_VALIDES[1].getNumTel());
        assertNotEquals("", EMPLOYES_VALIDES[2].getNumTel());
        assertNotEquals("  ", EMPLOYES_VALIDES[3].getNumTel());
        assertNotEquals("123", EMPLOYES_VALIDES[0].getNumTel());
        assertNotEquals("12345", EMPLOYES_VALIDES[0].getNumTel());
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Employe#setNumTel(java.lang.String)}.
     */
    @Test
    void testSetNumTel() {

        Employe testEmploye;
        
        testEmploye = new Employe(IDENTIFIANT_EMPLOYE_VALIDE, 
                                  NOM_EMPLOYE_VALIDE,
                                  PRENOM_EMPLOYE_VALIDE);
        assertThrows(IllegalArgumentException.class,
                     () -> testEmploye.setNumTel(null));
        assertThrows(IllegalArgumentException.class,
                     () -> testEmploye.setNumTel(""));
        assertThrows(IllegalArgumentException.class,
                     () -> testEmploye.setNumTel("    "));
        assertThrows(IllegalArgumentException.class,
                     () -> testEmploye.setNumTel("123"));
        assertThrows(IllegalArgumentException.class,
                     () -> testEmploye.setNumTel("12345"));
        assertThrows(IllegalArgumentException.class,
                     () -> testEmploye.setNumTel("1t34"));
        
        assertDoesNotThrow(() -> testEmploye.setNumTel("1234"));
        assertDoesNotThrow(() -> testEmploye.setNumTel("5678"));
        assertDoesNotThrow(() -> testEmploye.setNumTel("9875"));
    }

    /**
     * Méthode de test pou {@link application.modele.Employe#getNom()}.
     */
    @Test
    void testGetNom() {
        
        assertEquals("Thenieres", EMPLOYES_VALIDES[0].getNom());
        assertEquals("Laluti", EMPLOYES_VALIDES[1].getNom());
        assertEquals("Augé", EMPLOYES_VALIDES[2].getNom());
        assertEquals("Vroemen", EMPLOYES_VALIDES[3].getNom());
        
        assertNotEquals(null, EMPLOYES_VALIDES[1].getNom());
        assertNotEquals("", EMPLOYES_VALIDES[2].getNom());
        assertNotEquals("  ", EMPLOYES_VALIDES[3].getNom());
        assertNotEquals("Theniere", EMPLOYES_VALIDES[0].getNom());
    }

    /**
     * Méthode de test pour {@link application.modele.Employe#getPrenom()}.
     */
    @Test
    void testGetPrenom() {
        
        assertEquals("Baptiste", EMPLOYES_VALIDES[0].getPrenom());
        assertEquals("Ayoub", EMPLOYES_VALIDES[1].getPrenom());
        assertEquals("Romain", EMPLOYES_VALIDES[2].getPrenom());
        assertEquals("Esteban", EMPLOYES_VALIDES[3].getPrenom());
        
        assertNotEquals(null, EMPLOYES_VALIDES[1].getPrenom());
        assertNotEquals("", EMPLOYES_VALIDES[2].getPrenom());
        assertNotEquals("  ", EMPLOYES_VALIDES[3].getPrenom());
        assertNotEquals("Ayoube", EMPLOYES_VALIDES[1].getPrenom());
    }

}
