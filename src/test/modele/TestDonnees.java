/*
 * TestDonnees.java                           
 * 18 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;

import application.modele.Client;
import application.modele.Conferencier;
import application.modele.Donnees;
import application.modele.Employe;
import application.modele.Exposition;
import application.modele.Indisponibilite;
import application.modele.Visite;

/**
 * Classe de Test pour {@link application.modele.Donnees}
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
class TestDonnees {
    
    private final Donnees DONNEE_VALIDE = new Donnees();

    /**
     * Méthode de test pour
     * {@link application.modele.Donnees#getExpositions()}.
     */
    @Test
    void testGetExpositions() {
        
        LinkedHashMap<String, Exposition> expositions = new LinkedHashMap<>();
        assertDoesNotThrow(() -> {
            expositions.putLast("E000001",
                                new Exposition("Les paysages impressionnistes",
                                               1725, 1850, 15,
                                               new String[] {"Romain", "Ayoub",
                                                             "Esteban",
                                                             "Baptiste",
                                                             "paysage", "mars"},
                                               "Une grande exposition sur des "
                                               + "paysages originaux et "
                                               + "audacieux"));
        });
        assertDoesNotThrow(() -> DONNEE_VALIDE.setExpositions(expositions));
        
        assertDoesNotThrow(() -> DONNEE_VALIDE.getExpositions());
        assertNotNull(DONNEE_VALIDE.getExpositions());
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Donnees#setExpositions(java.util.LinkedHashMap)}.
     */
    @Test
    void testSetExpositions() {
        
        LinkedHashMap<String, Exposition> expositions = new LinkedHashMap<>();
        assertDoesNotThrow(() -> {
            expositions.putLast("E000001",
                                new Exposition("Les paysages impressionnistes",
                                               1725, 1850, 15,
                                               new String[] {"Romain", "Ayoub",
                                                             "Esteban",
                                                             "Baptiste",
                                                             "paysage", "mars"},
                                               "Une grande exposition sur des "
                                               + "paysages originaux et "
                                               + "audacieux"));
        });
        assertDoesNotThrow(() -> DONNEE_VALIDE.setExpositions(expositions));
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Donnees#getEmployes()}.
     */
    @Test
    void testGetEmployes() {
        
        LinkedHashMap<String, Employe> employes = new LinkedHashMap<>();
        assertDoesNotThrow(() -> {
            employes.putLast("N000001",
                             new Employe("Thenieres", "Baptiste", "1234"));
        });
        assertDoesNotThrow(() -> DONNEE_VALIDE.setEmployes(employes));
        
        assertDoesNotThrow(() -> DONNEE_VALIDE.getEmployes());
        assertNotNull(DONNEE_VALIDE.getEmployes());
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Donnees#setEmployes(java.util.LinkedHashMap)}.
     */
    @Test
    void testSetEmployes() {
        
        LinkedHashMap<String, Employe> employes = new LinkedHashMap<>();
        assertDoesNotThrow(() -> {
            employes.putLast("N000001",
                             new Employe("Thenieres", "Baptiste", "1234"));
        });
        assertDoesNotThrow(() -> DONNEE_VALIDE.setEmployes(employes));
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Donnees#getConferenciers()}.
     */
    @Test
    void testGetConferenciers() {
        
        LinkedHashMap<String, Conferencier> conferenciers
        = new LinkedHashMap<>();
        assertDoesNotThrow(() -> {
            conferenciers.putLast(
                "C000001",
                new Conferencier("Lexpert", "Noemie",
                                 new String[] {"peinture", "impressionnisme",
                                               "art contemporain"},
                                 "0600000001", true,
                                 new Indisponibilite[] {
                                     new Indisponibilite(
                                             LocalDate.of(2024,10,22)),
                                     new Indisponibilite(
                                             LocalDate.of(2024,10,26))
                                 }));
        });
        assertDoesNotThrow(() -> DONNEE_VALIDE.setConferenciers(conferenciers));
        
        assertDoesNotThrow(() -> DONNEE_VALIDE.getConferenciers());
        assertNotNull(DONNEE_VALIDE.getConferenciers());
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Donnees#setConferenciers(java.util.LinkedHashMap)}.
     */
    @Test
    void testSetConferenciers() {
        
        LinkedHashMap<String, Conferencier> conferenciers
        = new LinkedHashMap<>();
        assertDoesNotThrow(() -> {
            conferenciers.putLast(
                "C000001",
                new Conferencier("Lexpert", "Noemie",
                                 new String[] {"peinture", "impressionnisme",
                                               "art contemporain"},
                                 "0600000001", true,
                                 new Indisponibilite[] {
                                     new Indisponibilite(
                                             LocalDate.of(2024,10,22)),
                                     new Indisponibilite(
                                             LocalDate.of(2024,10,26))
                                 }));
        });
        assertDoesNotThrow(() -> DONNEE_VALIDE.setConferenciers(conferenciers));
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Donnees#getClients()}.
     */
    @Test
    void testGetClients() {
        
        ArrayList<Client> clients = new ArrayList<>();
        assertDoesNotThrow(() -> {
            clients.add(new Client("Esteban", "0012345678"));
            clients.add(new Client("Romain", "0012345678"));
            clients.add(new Client("Ayoub", "0987654321"));
            clients.add(new Client("Baptiste", "0154564474"));
        });
        assertDoesNotThrow(() -> DONNEE_VALIDE.setClients(clients));
        
        assertDoesNotThrow(() -> DONNEE_VALIDE.getClients());
        assertNotNull(DONNEE_VALIDE.getClients());
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Donnees#setClients(java.util.ArrayList)}.
     */
    @Test
    void testSetClients() {
        
        ArrayList<Client> clients = new ArrayList<>();
        assertDoesNotThrow(() -> {
            clients.add(new Client("Esteban", "0012345678"));
            clients.add(new Client("Romain", "0012345678"));
            clients.add(new Client("Ayoub", "0987654321"));
            clients.add(new Client("Baptiste", "0154564474"));
        });
        assertDoesNotThrow(() -> DONNEE_VALIDE.setClients(clients));
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Donnees#getVisites()}.
     */
    @Test
    void testGetVisites() {
        
        LinkedHashMap<String, Visite> visites = new LinkedHashMap<>();
        assertDoesNotThrow(() -> {
            visites.putLast(
                "R000001",
                new Visite(600, LocalDate.of(2024, 11, 20),
                           new Client("Alice", "0987654321"),
                           new Exposition("Le surréalisme en couleurs", 1920,
                                          1950, 30,
                                          new String[] {"surréalisme", "Dali", 
                                                        "Magritte", "Ernst",
                                                        "Tanguy", "Man Ray"},
                                         "Une exploration fascinante des "
                                         + "couleurs dans le mouvement "
                                         + "surréaliste."),
                           new Employe("Chloé", "Fourier", "5678"),
                           new Conferencier("Durand", "Marie", 
                                            new String[] {"surréalisme",
                                                          "peinture"}, 
                                            "0623456789", false)));
        });
        assertDoesNotThrow(() -> DONNEE_VALIDE.setVisites(visites));
        
        assertDoesNotThrow(() -> DONNEE_VALIDE.getVisites());
        assertNotNull(DONNEE_VALIDE.getVisites());
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Donnees#setVisites(java.util.LinkedHashMap)}.
     */
    @Test
    void testSetVisites() {
        
        LinkedHashMap<String, Visite> visites = new LinkedHashMap<>();
        assertDoesNotThrow(() -> {
            visites.putLast(
                "R000001",
                new Visite(600, LocalDate.of(2024, 11, 20),
                           new Client("Alice", "0987654321"),
                           new Exposition("Le surréalisme en couleurs", 1920,
                                          1950, 30,
                                          new String[] {"surréalisme", "Dali", 
                                                        "Magritte", "Ernst",
                                                        "Tanguy", "Man Ray"},
                                         "Une exploration fascinante des "
                                         + "couleurs dans le mouvement "
                                         + "surréaliste."),
                           new Employe("Chloé", "Fourier", "5678"),
                           new Conferencier("Durand", "Marie", 
                                            new String[] {"surréalisme",
                                                          "peinture"}, 
                                            "0623456789", false)));
        });
        assertDoesNotThrow(() -> DONNEE_VALIDE.setVisites(visites));
    }

    /**
     * Méthode de test pour
     * {@link application.modele.Donnees#isDonneesVides()}.
     */
    @Test
    void testIsDonneesVides() {
        
        Donnees donneesValide1 = new Donnees();
        Donnees donneesValide2 = new Donnees();
        Donnees donneesValide3 = new Donnees();
        Donnees donneesValide4 = new Donnees();
        
        assertTrue(donneesValide1.isDonneesVides());
        assertTrue(donneesValide2.isDonneesVides());
        assertTrue(donneesValide3.isDonneesVides());
        assertTrue(donneesValide4.isDonneesVides());
        
        LinkedHashMap<String, Conferencier> conferenciers
        = new LinkedHashMap<>();
        assertDoesNotThrow(() -> {
            conferenciers.putLast(
                "C000001",
                new Conferencier("Lexpert", "Noemie",
                                 new String[] {"peinture", "impressionnisme",
                                               "art contemporain"},
                                 "0600000001", true,
                                 new Indisponibilite[] {
                                     new Indisponibilite(
                                             LocalDate.of(2024,10,22)),
                                     new Indisponibilite(
                                             LocalDate.of(2024,10,26))
                                 }));
        });
        assertDoesNotThrow(() -> donneesValide1.setConferenciers(conferenciers));
        assertFalse(donneesValide1.isDonneesVides());
        
        LinkedHashMap<String, Visite> visites = new LinkedHashMap<>();
        assertDoesNotThrow(() -> {
            visites.putLast(
                "R000001",
                new Visite(600, LocalDate.of(2024, 11, 20),
                           new Client("Alice", "0987654321"),
                           new Exposition("Le surréalisme en couleurs", 1920,
                                          1950, 30,
                                          new String[] {"surréalisme", "Dali", 
                                                        "Magritte", "Ernst",
                                                        "Tanguy", "Man Ray"},
                                         "Une exploration fascinante des "
                                         + "couleurs dans le mouvement "
                                         + "surréaliste."),
                           new Employe("Chloé", "Fourier", "5678"),
                           new Conferencier("Durand", "Marie", 
                                            new String[] {"surréalisme",
                                                          "peinture"}, 
                                            "0623456789", false)));
        });
        assertDoesNotThrow(() -> donneesValide2.setVisites(visites));
        assertFalse(donneesValide2.isDonneesVides());
        
        LinkedHashMap<String, Employe> employes = new LinkedHashMap<>();
        assertDoesNotThrow(() -> {
            employes.putLast("N000001",
                             new Employe("Thenieres", "Baptiste", "1234"));
        });
        assertDoesNotThrow(() -> donneesValide3.setEmployes(employes));
        assertFalse(donneesValide3.isDonneesVides());
        
        LinkedHashMap<String, Exposition> expositions = new LinkedHashMap<>();
        assertDoesNotThrow(() -> {
            expositions.putLast("E000001",
                                new Exposition("Les paysages impressionnistes",
                                               1725, 1850, 15,
                                               new String[] {"Romain", "Ayoub",
                                                             "Esteban",
                                                             "Baptiste",
                                                             "paysage", "mars"},
                                               "Une grande exposition sur des "
                                               + "paysages originaux et "
                                               + "audacieux"));
        });
        assertDoesNotThrow(() -> donneesValide4.setExpositions(expositions));
        assertFalse(donneesValide4.isDonneesVides());
    }

}
