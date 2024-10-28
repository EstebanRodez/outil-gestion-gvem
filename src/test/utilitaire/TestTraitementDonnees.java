/**
 * TestTraitementDonnees.java
 * 28 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.utilitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import application.utilitaire.ImportationCSV;
import application.utilitaire.TraitementDonnees;

/**
 * Classe de Test pour {@link application.utilitaire.TraitementDonnees}
 * 
 * @author Esteban Vroemen
 * @version 1.0
 */
@TestMethodOrder(OrderAnnotation.class)
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

//    /**
//     * Méthode de test pour
//     * {@link application.utilitaire.TraitementDonnees#creerExpositions(
//     * java.util.ArrayList)}.
//     */
//    @Test
//    void testCreerExpositions() {
//        
//        // Méthode protected testée dans TestImportationCSV
//    }
//
//    /**
//     * Méthode de test pour
//     * {@link application.utilitaire.TraitementDonnees#creerEmployes(
//     * java.util.ArrayList)}.
//     */
//    @Test
//    void testCreerEmployes() {
//        
//        // Méthode protected testée dans TestImportationCSV
//    }
//
//    /**
//     * Méthode de test pour
//     * {@link application.utilitaire.TraitementDonnees#creerConferenciers(
//     * java.util.ArrayList)}.
//     */
//    @Test
//    void testCreerConferenciers() {
//        
//        // Méthode protected testée dans TestImportationCSV
//    }
//
//    /**
//     * Méthode de test pour
//     * {@link application.utilitaire.TraitementDonnees#creerVisites(
//     * java.util.ArrayList)}.
//     */
//    @Test
//    void testCreerVisites() {
//        
//        // Méthode protected testée dans TestImportationCSV
//    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#getExpositions()}.
     */
    @Test
    @Order(1)
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
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#getEmployes()}.
     */
    @Test
    @Order(2)
    void testGetEmployes() {
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides1.csv");
        });
        
        assertNotNull(TraitementDonnees.getEmployes());
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#getConferenciers()}.
     */
    @Test
    @Order(3)
    void testGetConferenciers() {
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv");
        });
        
        assertNotNull(TraitementDonnees.getConferenciers());
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#getClients()}.
     */
    @Test
    @Order(4)
    void testGetClients() {
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides1.csv");
        });
        
        assertNotNull(TraitementDonnees.getClients());
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#getVisites()}.
     */
    @Test
    @Order(5)
    void testGetVisites() {
        
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides1.csv");
        });
        
        assertNotNull(TraitementDonnees.getVisites());
    }

}
