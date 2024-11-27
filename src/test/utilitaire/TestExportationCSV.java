/*
 * TestExportationCSV.java                           
 * 27 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package test.utilitaire;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import application.utilitaire.ExportationCSV;
import application.utilitaire.ImportationCSV;
import application.utilitaire.TraitementDonnees;

/**
 * Classe de Test pour {@link application.utilitaire.ExportationCSV}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
class TestExportationCSV {
    
    private final String[] FICHIERS_EXPORTATION
    = {"conferenciers.csv", "employes.csv", "expositions.csv", "visites.csv" };
    
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
     * Test method for {@link application.utilitaire.ExportationCSV#exporterDonnees()}.
     */
    @Test
    void testExporterDonnees() {
        
        // Importation de données valides
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides1.csv");
        });
        
        assertDoesNotThrow(() -> {
            ExportationCSV.exporterDonnees();
        });
        supprimerFichiersExportation();
        TraitementDonnees.supprimerDonnees();
    }
    
    private void supprimerFichiersExportation() {
        
        for (String fichier : FICHIERS_EXPORTATION) {
            
            try {
                Files.deleteIfExists(Path.of(fichier));
            } catch (IOException e) {
                // Ne rien faire
            }
        }
    }

}
