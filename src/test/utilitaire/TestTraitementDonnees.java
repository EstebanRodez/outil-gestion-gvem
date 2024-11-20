/**
 * TestTraitementDonnees.java
 * 28 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.utilitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.modele.Donnees;
import application.utilitaire.TraitementDonnees;

/**
 * Classe de Test pour {@link application.utilitaire.TraitementDonnees}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
class TestTraitementDonnees {    

    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#getDonnees()}.
     */
    @Test
    void testGetDonnees() {
        
        assertDoesNotThrow(() -> {
            TraitementDonnees.setDonnees(new Donnees());
        });
        assertDoesNotThrow(() -> {
            TraitementDonnees.getDonnees();
        });
        assertNotNull(TraitementDonnees.getDonnees());
    }

    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#setDonnees(
     * application.modele.Donnees)}.
     */
    @Test
    void testSetDonnees() {
        
        assertThrows(IllegalArgumentException.class, () -> {
            TraitementDonnees.setDonnees(null);
        });
        assertDoesNotThrow(() -> {
            TraitementDonnees.setDonnees(new Donnees());
        });
    }
    
    /**
     * Méthode de test pour
     * {@link application.utilitaire.TraitementDonnees#supprimerDonnees()}.
     */
    @Test
    void testSupprimerDonnees() {
        
        assertDoesNotThrow(() -> {
            TraitementDonnees.setDonnees(new Donnees());
        });
        assertDoesNotThrow(() -> {
            TraitementDonnees.supprimerDonnees();
        });
    }
}
