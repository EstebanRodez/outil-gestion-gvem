/**
 * TestTraitementDonnees.java
 * 28 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.utilitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.utilitaire.ImportationCSV;
import application.utilitaire.TraitementDonnees;

/**
 * Classe de Test pour {@link application.utilitaire.TraitementDonnees}
 * 
 * @author Esteban Vroemen
 * @version 1.0
 */
class TestTraitementDonnees {
    
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
     * {@link application.utilitaire.TraitementDonnees#getExpositions()}.
     */
    @Test
    void testGetExpositions() {
        
        // Test utile pour 100% couverture
        assertDoesNotThrow(() -> {
            new TraitementDonnees();
        });
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides1.csv");
        });
        
        assertNotNull(TraitementDonnees.getExpositions());
        TraitementDonnees.supprimerDonnees();
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#getEmployes()}.
     */
    @Test
    void testGetEmployes() {
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides1.csv");
        });
        
        assertNotNull(TraitementDonnees.getEmployes());
        TraitementDonnees.supprimerDonnees();
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#getConferenciers()}.
     */
    @Test
    void testGetConferenciers() {
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv");
        });
        
        assertNotNull(TraitementDonnees.getConferenciers());
        TraitementDonnees.supprimerDonnees();
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#getClients()}.
     */
    @Test
    void testGetClients() {
        
        // Données nécessaires
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv");
        });
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides1.csv");
        });
        
        assertNotNull(TraitementDonnees.getClients());
        TraitementDonnees.supprimerDonnees();
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#getVisites()}.
     */
    @Test
    void testGetVisites() {
        
        // Données nécessaires
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv");
        });
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides1.csv");
        });
        
        assertNotNull(TraitementDonnees.getVisites());
        TraitementDonnees.supprimerDonnees();
    }
    
    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#isDonneesVides()}.
     */
    @Test
    void testIsDonneesVides() {
        
        TraitementDonnees.supprimerDonnees();
        assertTrue(TraitementDonnees.isDonneesVides());
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides1.csv");
        });
        assertFalse(TraitementDonnees.isDonneesVides());
        TraitementDonnees.supprimerDonnees();
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides1.csv");
        });
        assertFalse(TraitementDonnees.isDonneesVides());
        TraitementDonnees.supprimerDonnees();
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv");
        });
        assertFalse(TraitementDonnees.isDonneesVides());
        TraitementDonnees.supprimerDonnees();
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides1.csv");
        });
        assertFalse(TraitementDonnees.isDonneesVides());
        TraitementDonnees.supprimerDonnees();
    }

}
