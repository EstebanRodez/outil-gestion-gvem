/*
 * TestGestionCSV.java                           
 * 25 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package test.utilitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.utilitaire.GestionCSV;
import application.utilitaire.ImportationCSV;

/**
 * Classe de Test pour {@link application.utilitaire.GestionCSV}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
class TestGestionCSV {
    
    private final String CHEMIN_RACINE_TEST
    = "ressources/tests/";
    
    private final String CHEMIN_EXPOSITIONS
    = CHEMIN_RACINE_TEST + "CSV/expositions/";
    
    private final String CHEMIN_CONFERENCIERS
    = CHEMIN_RACINE_TEST + "CSV/conferencier/";
    
    private final String CHEMIN_EMPLOYES
    = CHEMIN_RACINE_TEST + "CSV/employes/";
    
    private final String CHEMIN_VISITES
    = CHEMIN_RACINE_TEST + "CSV/visites/";

    /**
     * Méthode de test pour
     * {@link application.utilitaire.GestionCSV#getTypeCSV(java.lang.String)}.
     */
    @Test
    void testGetTypeCSV() {
        
        // Test utile pour 100% couverture
        assertDoesNotThrow(() -> {
            new GestionCSV();
        });
    
        // Cas valides
        assertDoesNotThrow(() -> {
            assertEquals('R', GestionCSV.getTypeCSV(
                    CHEMIN_VISITES+"visites_valides1.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals('E', GestionCSV.getTypeCSV(
                    CHEMIN_EXPOSITIONS+"expositions_valides1.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals('C', GestionCSV.getTypeCSV(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals('N', GestionCSV.getTypeCSV(
                    CHEMIN_EMPLOYES+"employes_valides1.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals('R', GestionCSV.getTypeCSV(
                    CHEMIN_VISITES+"visites_valides2.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals('E', GestionCSV.getTypeCSV(
                    CHEMIN_EXPOSITIONS+"expositions_valides2.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals('C', GestionCSV.getTypeCSV(
                    CHEMIN_CONFERENCIERS+"conferencier_valide2.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals('N', GestionCSV.getTypeCSV(
                    CHEMIN_EMPLOYES+"employes_valides2.csv"));
        });
        
        // Cas invalides
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(null));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(""));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/texte.txt"));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/CSV/fichier_vide_avec_entete.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/CSV/fichier_taille_zero.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/texte.txt"));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/CSV/fichier_invalide1.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/CSV/fichier_invalide2.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/CSV/fichier_vide.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/fichier_inexistant"));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/CSV/faux_fichier_vide.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/CSV/faux_fichier.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/CSV/faux_fichier2.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/CSV/faux_fichier_avec_entete.csv"));
        });
        assertDoesNotThrow(() -> {
            assertEquals(0, GestionCSV.getTypeCSV(
                    "ressources/tests/CSV/faux_fichier_avec_entete2.csv"));
        });
          
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.GestionCSV#isFichierValide(java.lang.String)}.
     */
    @Test
    void testIsFichierValide() {
        
        
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.GestionCSV#isLettreIdentifiantValide(char)}.
     */
    @Test
    void testIsLettreIdentifiantValide() {
        
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.GestionCSV#isFichierVide(java.io.File)}.
     */
    @Test
    void testIsFichierVide() {
        
        
    }

}
