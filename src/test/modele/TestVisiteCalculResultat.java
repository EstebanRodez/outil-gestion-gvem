package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.modele.VisiteCalculResultat;

/**
 * Classe de test pour {@link application.modele.VisiteCalculResultat}
 * @author Baptiste Thenieres
 * @version 1.0
 */
class TestVisiteCalculResultat {

    @Test
    void testConstructeurAvecDouble() {
        String intitule = "Exposition A";
        double calculVisites = 150.0;

        VisiteCalculResultat resultat = new VisiteCalculResultat(intitule, calculVisites);

        assertEquals(intitule, resultat.getIntitule(), "L'intitulé devrait correspondre à celui fourni");
        assertEquals(calculVisites, resultat.getCalculVisites(), "Le calcul des visites devrait correspondre à celui fourni");
        assertNull(resultat.getCalculVisitesPourcentage(), "Le pourcentage des visites devrait être nul");
    }

    @Test
    void testConstructeurAvecStringPourcentage() {
        String intitule = "Exposition B";
        String calculVisitesPourcentage = "75%";

        VisiteCalculResultat resultat = new VisiteCalculResultat(intitule, calculVisitesPourcentage);

        assertEquals(intitule, resultat.getIntitule(), "L'intitulé devrait correspondre à celui fourni");
        assertEquals(calculVisitesPourcentage, resultat.getCalculVisitesPourcentage(), "Le pourcentage des visites devrait correspondre à celui fourni");
        assertEquals(0.0, resultat.getCalculVisites(), "Le calcul des visites devrait être initialisé à 0");
    }

    @Test
    void testAccesseurIntitule() {
        String intitule = "Exposition C";
        VisiteCalculResultat resultat = new VisiteCalculResultat(intitule, 120.0);

        assertEquals(intitule, resultat.getIntitule(), "L'intitulé devrait être correctement retourné par le getter");
    }

    @Test
    void testAccesseurCalculVisites() {
        double calculVisites = 200.0;
        VisiteCalculResultat resultat = new VisiteCalculResultat("Exposition D", calculVisites);

        assertEquals(calculVisites, resultat.getCalculVisites(), "Le calcul des visites devrait être correctement retourné par le getter");
    }

    @Test
    void testAccesseurCalculVisitesPourcentage() {
        String calculVisitesPourcentage = "50%";
        VisiteCalculResultat resultat = new VisiteCalculResultat("Exposition E", calculVisitesPourcentage);

        assertEquals(calculVisitesPourcentage, resultat.getCalculVisitesPourcentage(), "Le pourcentage des visites devrait être correctement retourné par le getter");
    }
}
