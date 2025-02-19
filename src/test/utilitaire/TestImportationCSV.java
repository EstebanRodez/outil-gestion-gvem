/**
 * TestImportationCSV.java
 * 27 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.utilitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.utilitaire.FichierDonneesInvalidesException;
import application.utilitaire.ImportationCSV;
import application.utilitaire.TraitementDonnees;

/**
 * Classe de Test pour {@link application.utilitaire.ImportationCSV}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
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
        
        // Exception car Identifiant Exposition non trouvé
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides1.csv");
        });
        
        // On insère des expositions valides pour éviter cette exception
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides1.csv");
        });
        
        // Exception car Identifiant Conférencier non trouvé
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides2.csv");
        });
        
        // On insère des conférenciers valides pour éviter cette exception
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv");
        });
        
        // Exception car Identifiant Employé non trouvé
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides_sans_entete.csv");
        });
        
        TraitementDonnees.supprimerDonnees();
        
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees("ressources/tests/texte.txt");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    "ressources/tests/CSV/fichier_vide_avec_entete.csv");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees(
                    "ressources/tests/CSV/fichier_taille_zero.csv");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ImportationCSV.importerDonnees(
                    "ressources/tests/fichier_inexistant");
        });
        
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    "ressources/tests/CSV/fichier_invalide1.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    "ressources/tests/CSV/fichier_invalide2.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    "ressources/tests/CSV/fichier_vide.csv");
        });
        
        // Fichiers Expositions avec données incorrectes
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides1.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides2.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides3.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides4.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides5.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides6.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides7.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides8.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides9.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides10.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides11.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides12.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides13.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides14.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides15.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_invalides16.csv");
        });
        
        // Fichiers Conférenciers avec données incorrectes
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide1.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide2.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide3.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide4.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide5.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide6.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide7.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide8.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide9.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_invalide10.csv");
        });
        
        // Fichiers Employés avec données incorrectes
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_invalides1.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_invalides2.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_invalides3.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_invalides4.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_invalides5.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_invalides6.csv");
        });
        
        // Fichiers Visites avec données incorrectes
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides1.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides2.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides3.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides4.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides5.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides6.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides7.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides8.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides9.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_invalides10.csv");
        });
        
        // Tentatives d'enregistrement en double
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides1.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv");
        });
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides1.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides1.csv");
        });
        
        TraitementDonnees.supprimerDonnees();
        insererDonneesValides();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides1.csv");
        });
        assertThrows(FichierDonneesInvalidesException.class, () -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides1.csv");
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
        
        // Test utile pour 100% couverture
        assertDoesNotThrow(() -> {
            new ImportationCSV();
        });
        
        // Fichiers Expositions avec données correctes
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides1.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides2.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides3.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EXPOSITIONS+"expositions_valides_sans_entete.csv");
        });
        TraitementDonnees.supprimerDonnees();
        
        // Fichiers Conférencier avec données correctes
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide1.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide2.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide3.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide4.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide5.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide6.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide_sans_entete.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_CONFERENCIERS+"conferencier_valide_beaucoup.csv");
        });
        TraitementDonnees.supprimerDonnees();
        
        // Fichiers Employés avec données correctes
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides1.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides2.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides3.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides4.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides_sans_entete.csv");
        });
        TraitementDonnees.supprimerDonnees();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_EMPLOYES+"employes_valides_beaucoup.csv");
        });
        TraitementDonnees.supprimerDonnees();
        
        // Fichiers Visites avec données correctes
        insererDonneesValides();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides1.csv");
        });
        TraitementDonnees.supprimerDonnees();
        
        insererDonneesValides();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides2.csv");
        });
        TraitementDonnees.supprimerDonnees();
        
        insererDonneesValides();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides3.csv");
        });
        TraitementDonnees.supprimerDonnees();
        
        insererDonneesValides();
        assertDoesNotThrow(() -> {
            ImportationCSV.importerDonnees(
                    CHEMIN_VISITES+"visites_valides_sans_entete.csv");
        });
        TraitementDonnees.supprimerDonnees();
        
    }
    
    /**
     * Insère des données valides en mémoire pour insérer ensuite des
     * visites valides
     */
    private void insererDonneesValides() {
        
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
    }

}
