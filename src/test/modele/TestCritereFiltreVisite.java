/*
 * TestCritereFiltreVisite.java                           
 * 17 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package test.modele;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import application.modele.CritereFiltreVisite;

/**
 * Classe de test pour {@link application.modele.CritereFiltreVisite}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class TestCritereFiltreVisite {
    
    private CritereFiltreVisite critereFiltre;

    @BeforeEach
    void setUp() {
        critereFiltre = new CritereFiltreVisite();
    }

    @Test
    void testSetExpositionTemporaire() {
        critereFiltre.setExpositionTemporaire(true);
        assertTrue(critereFiltre.getExpositionTemporaire());
        assertFalse(critereFiltre.getExpositionPermanente());
    }

    @Test
    void testSetExpositionPermanente() {
        critereFiltre.setExpositionPermanente(true);
        assertTrue(critereFiltre.getExpositionPermanente());
        assertFalse(critereFiltre.getExpositionTemporaire());
    }

    @Test
    void testSetEstInterne() {
        critereFiltre.setEstInterne(true);
        assertTrue(critereFiltre.getInterne());
        assertFalse(critereFiltre.getExterne());
    }

    @Test
    void testSetExterne() {
        critereFiltre.setExterne(true);
        assertTrue(critereFiltre.getExterne());
        assertFalse(critereFiltre.getInterne());
    }

    @Test
    void testSetEtGetConferencier() {
        String conferencier = "Jean Dupont";
        critereFiltre.setConferencier(conferencier);
        assertEquals(conferencier, critereFiltre.getConferencier());
    }

    @Test
    void testSetEtGetExposition() {
        String exposition = "Art Moderne";
        critereFiltre.setExposition(exposition);
        assertEquals(exposition, critereFiltre.getExposition());
    }

    @Test
    void testSetEtGetDateDebut() {
        LocalDate dateDebut = LocalDate.of(2024, 11, 1);
        critereFiltre.setDateDebut(dateDebut);
        assertEquals(dateDebut, critereFiltre.getDateDebut());
    }

    @Test
    void testSetEtGetDateFin() {
        LocalDate dateFin = LocalDate.of(2024, 11, 30);
        critereFiltre.setDateFin(dateFin);
        assertEquals(dateFin, critereFiltre.getDateFin());
    }

    @Test
    void testSetEtGetHoraireDebut() {
        int horaireDebut = 10;
        critereFiltre.setHoraireDebut(horaireDebut);
        assertEquals(horaireDebut, critereFiltre.getHoraireDebut());
    }

    @Test
    void testSetEtGetHoraireFin() {
        int horaireFin = 18;
        critereFiltre.setHoraireFin(horaireFin);
        assertEquals(horaireFin, critereFiltre.getHoraireFin());
    }
}
