/*
 * TestImportationCSV.java                           
 * 17 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.utilitaire;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import application.modele.Exposition;
import application.utilitaire.ImportationCSV;

/**
 * Classe de Test pour {@link application.utilitaire.ImportationCSV}
 */
public class TestImportationCSV {
    
    private static final String FichierValide 
    = "ressources/tests/CSV/expositions_valide.csv";
    
    private static final String FichierValideSansEntete 
    = "ressources/tests/CSV/expositions_valide_sans_entete.csv";
    
    private static final String FichierInvalide 
    = "ressources/tests/CSV/donneeInvalide.csv";
    
    private static final String FichierInexistant 
    = "fichier/inexistant.csv";
    
    private static final String ExpositionValide 
    = "ressources/tests/CSV/expositions_valide.csv";
    
    private static final String ExpositionInvalide 
    = "ressources/tests/CSV/expositions_invalide.csv";

    String[] ligne1Valide = new String[] { "E000001", "Exposition1", "1800", 
                                           "1900", "10", "mot1, mot2", "Resume 1" };
    
    String[] ligne2Valide = new String[] { "E000002", "Exposition2", "2015",
                                           "2019", "7", "mot1", "Resume 2", 
                                           "10/10/2024", "31/12/2024" };
    
    /**
     * Teste la méthode {@link application.utilitaire.ImportationCSV#importer(String)}.
     * Cas : Fichier CSV valide
     */
    @Test
    void testImporterFichierValide() throws IOException {      
        List<String[]> valide = ImportationCSV.importer(FichierValide);
        
        assertNotNull(valide);
        assertFalse(valide.isEmpty());
        assertEquals(3, valide.size());  
        assertArrayEquals(ligne1Valide, valide.get(0));
        assertArrayEquals(ligne2Valide, valide.get(1));
        
        
        /* Fichier valide sans entete*/ 
        List<String[]> sansEntete = ImportationCSV.importer(FichierValideSansEntete);
        
        assertNotNull(sansEntete);
        assertFalse(sansEntete.isEmpty());
        assertEquals(1, sansEntete.size());  
        assertArrayEquals(ligne1Valide, sansEntete.get(0));
    }

    /**
     * Teste la méthode {@link application.utilitaire.ImportationCSV#importer(String)}.
     * Cas : Fichier inexistant
     */
    @Test
    void testImporterFichierInexistant() {    
        assertThrows(IOException.class, 
                     () -> ImportationCSV.importer(FichierInexistant));
    }
    
    /**
     * Teste la méthode {@link application.utilitaire.ImportationCSV#traitementDonnees(List)}.
     * Cas : Fichier avec un contenu incorrect
     * @throws IOException 
     */
    @Test
    void testTraitementDonneesInvalide() throws IOException {    
        List<String[]> donneeInvalide = ImportationCSV.importer(FichierInvalide);
        
        // FIXME vérifier le format des nombres dans le csv
        // List<String[]> donneeInvalide2 = ImportationCSV.importer("ressources/tests/CSV/expositions_nombres_invalides.csv");
        
        assertThrows(IllegalArgumentException.class, 
                    () -> ImportationCSV.traitementDonnees(donneeInvalide));
        // ImportationCSV.traitementDonnees(donneeInvalide2);
    }
    
    /**
     * Teste la méthode {@link application.utilitaire.ImportationCSV#creerExposition(List)}.
     * Cas : Données valides
     */
    @Test
    void testCreeExpositionValide() throws IOException {
        // Charger un fichier CSV de test avec des expositions valides
        
        List<String[]> donnees = ImportationCSV.importer(ExpositionValide);
        
        ImportationCSV.traitementDonnees(donnees);
        
        List<Exposition> expositions = ImportationCSV.getExpositions();
        
        assertNotNull(expositions);
        assertFalse(expositions.isEmpty());
        assertEquals(3, expositions.size());  // 3 expositions valides dans le fichier de test
        
        // Vérifie les propriétés de la première exposition
        Exposition expo1 = expositions.get(0);
        assertEquals("E000001", expo1.getIdentifiant());
        assertEquals("Exposition1", expo1.getIntitule());
        assertEquals(1800, expo1.getPeriodeDebut());
        assertEquals(1900, expo1.getPeriodeFin());
        assertEquals(10, expo1.getNbOeuvre());
        assertArrayEquals(new String[] { "mot1", "mot2" }, expo1.getMotsCles());
        assertEquals("Resume 1", expo1.getResume());
    }

    /**
     * Teste la méthode {@link application.utilitaire.ImportationCSV#creerExposition(List)}.
     * Cas : Fichier avec un mauvais nombre d'arguments
     */
    @Test
    void testCreerExpositionArgumentsIncorrect() throws IOException {
        // Charger un fichier CSV de test avec des lignes incorrectes 
        List<String[]> donnees = ImportationCSV.importer(ExpositionInvalide);
        
        assertThrows(IllegalArgumentException.class, 
                     () -> ImportationCSV.traitementDonnees(donnees));
    }

}
