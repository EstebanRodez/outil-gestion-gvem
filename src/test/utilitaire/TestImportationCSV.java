/**
 * TestImportationCSV.java
 * 27 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.utilitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.utilitaire.FichierDonneesInvalides;
import application.utilitaire.ImportationCSV;

/**
 * Classe de Test pour {@link application.utilitaire.ImportationCSV}
 * 
 * @author Esteban Vroemen
 * @version 1.0
 */
class TestImportationCSV {
    
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
     * {@link application.utilitaire.ImportationCSV#importerDonnees(
     * java.lang.String)}.
     * Cas uniquement invalides
     */
    @Test
    void testImporterDonneesInvalide() {
        
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees("ressources/tests/texte.txt");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees(
                    "ressources/tests/CSV/fichier_vide.csv");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees(
                    "ressources/tests/fichier_inexistant");
        });
        
        // Fichiers Expositions avec données incorrectes
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides1.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides2.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides3.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides4.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides5.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides6.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides7.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides8.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides9.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides10.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides11.csv");
        });
        
        // Fichiers Conférenciers avec données incorrectes
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide1.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide2.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide3.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide4.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide5.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide6.csv");
        });
        
        // Fichiers Employés avec données incorrectes
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_invalides1.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_invalides2.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_invalides3.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_invalides4.csv");
        });
        
        // Fichiers Visites avec données incorrectes
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides1.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides2.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides3.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides4.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides5.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides6.csv");
        });
        assertThrows(FichierDonneesInvalides.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides7.csv");
        });
        
    }
    
    /**
     * Méthode de test pour
     * {@link application.utilitaire.ImportationCSV#importerDonnees(
     * java.lang.String)}.
     * Cas uniquement valides
     */
    @Test
    void testImporterDonneesValide() {
        
        // Fichiers Expositions avec données correctes
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides2.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides_sans_entete.csv");
        });
        
        // Fichiers Conférencier avec données correctes
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide2.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide_sans_entete.csv");
        });
        
        // Fichiers Employés avec données correctes
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides2.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides3.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides_sans_entete.csv");
        });
        
        // Fichiers Visites avec données correctes
        // A exécuter forcément en dernier
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides2.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides_sans_entete.csv");
        });
        
    }

}
