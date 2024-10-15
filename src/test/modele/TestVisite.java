/*
 * TestVisite.java                           
 * 14 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import application.modele.Client;
import application.modele.Conferencier;
import application.modele.Employe;
import application.modele.Exposition;
import application.modele.Visite;

/**
 * Classe de test pour {@link application.modele.Visite}
 * @author Esteban Vroemen
 * @version 1.0
 */
class TestVisite {
    
    private final String IDENTIFIANT_VISITE_VALIDE = "V000001";
    
    private final int HORAIRE_DEBUT_VISITE_VALIDE = 610; // 10h10
    
    private final LocalDate DATE_VISITE_VALIDE = LocalDate.of(2024, 10, 12);
    
    private final Client CLIENT_VISITE_VALIDE 
    = new Client("Esteban", "123456789");
    
    private final Exposition EXPOSITION_VISITE_VALIDE
    = new Exposition("E000001", "Les paysages impressionnistes", 1780, 1810, 25,
                     new String[] {"paysage", "impressionnisme",
                                   "Monet", "Pissarro", "Sysley",
                                   "Signac"},
                     "Très belle exposition sur l'impressionnisme.");
    
    private final Employe EMPLOYE_VISITE_VALIDE
    = new Employe("N000001", "Mathieu", "Bernoulli", "1234");
    
    private final Conferencier CONFERENCIER_VISITE_VALIDE
    = new Conferencier("C000001", "Thenieres", "Baptiste", 
                       new String[] {"impressionnisme", "paysage"}, 
                       "123456789", true);
    
    private final Visite[] VISITES_VALIDES =
    {
        new Visite(IDENTIFIANT_VISITE_VALIDE, HORAIRE_DEBUT_VISITE_VALIDE,
                   DATE_VISITE_VALIDE, CLIENT_VISITE_VALIDE,
                   EXPOSITION_VISITE_VALIDE, EMPLOYE_VISITE_VALIDE,
                   CONFERENCIER_VISITE_VALIDE),
        new Visite("V000002", 600, LocalDate.of(2024, 11, 20),
                   new Client("Alice", "987654321"),
                   new Exposition("E000002", "Le surréalisme en couleurs", 1920,
                                  1950, 30,
                                  new String[] {"surréalisme", "Dali", 
                                                "Magritte", "Ernst", "Tanguy", 
                                                "Man Ray"},
                                  "Une exploration fascinante des couleurs dans"
                                  + " le mouvement surréaliste."),
                   new Employe("N000002", "Chloé", "Fourier", "5678"),
                   new Conferencier("C000002", "Durand", "Marie", 
                                    new String[] {"surréalisme", "peinture"}, 
                                    "623456789", false)),
        new Visite("V000003", 720, LocalDate.of(2024, 12, 5),
                   new Client("Benjamin", "456123789"),
                   new Exposition("E000003", "L'Art abstrait moderne", 1945,
                                  1980, 40,
                                  new String[] {"abstraction", "Kandinsky", 
                                                "Pollock", "Rothko", 
                                                "Mondrian"},
                                  "Un voyage au cœur de l'art abstrait."),
                   new Employe("N000003", "Julien", "Descartes", "7890"),
                   new Conferencier("C000003", "Dupont", "Jacques", 
                                    new String[] {"abstraction", "modernisme"}, 
                                    "612345678", true)),
        new Visite("V000004", 960, LocalDate.of(2025, 1, 10),
                   new Client("Camille", "321654987"),
                   new Exposition("E000004", "Le cubisme décomposé", 1907, 1925,
                                  35, new String[] {"cubisme", "Picasso", 
                                                    "Braque", "Gris"},
                                  "Un décryptage fascinant du cubisme."),
                   new Employe("N000004", "Luc", "Galois", "9012"),
                   new Conferencier("C000004", "Martin", "Sophie", 
                                    new String[] {"cubisme",
                                                  "peinture moderne"}, 
                                    "698765432", false)),
        new Visite("V000005", 1160, LocalDate.of(2025, 2, 28),
                   new Client("Diane", "789123456"),
                   new Exposition("E000005", "Les sculptures grecques", -500,
                                  -100, 20,
                                  new String[] {"sculpture", "Grèce antique", 
                                                "Phidias", "Praxitèle"},
                                  "Un retour sur l'âge d'or de la sculpture "
                                  + "grecque."),
                   new Employe("N000005", "Clara", "Euler", "3456"),
                   new Conferencier("C000005", "Lemoine", "Paul", 
                                    new String[] {"sculpture",
                                                  "archéologie grecque"}, 
                                    "712345678", true)),
    };

    /**
     * Méthode de test pour
     * {@link application.modele.Visite#Visite(
     * java.lang.String, int, java.time.LocalDate, application.modele.Client, application.modele.Exposition, application.modele.Employe, application.modele.Conferencier)}.
     * Cas uniquement invalides
     */
    @Test
    void testVisiteInvalide() {
        
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(null,
                                      HORAIRE_DEBUT_VISITE_VALIDE,
                                      DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite("",
                                      HORAIRE_DEBUT_VISITE_VALIDE,
                                      DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(IDENTIFIANT_VISITE_VALIDE,
                                      -1,
                                      DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(IDENTIFIANT_VISITE_VALIDE,
                                      1440,
                                      DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(IDENTIFIANT_VISITE_VALIDE,
                                      -999,
                                      DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(IDENTIFIANT_VISITE_VALIDE,
                                      44444,
                                      DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(IDENTIFIANT_VISITE_VALIDE,
                                      HORAIRE_DEBUT_VISITE_VALIDE,
                                      null, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(IDENTIFIANT_VISITE_VALIDE,
                                      HORAIRE_DEBUT_VISITE_VALIDE,
                                      DATE_VISITE_VALIDE, 
                                      null,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(IDENTIFIANT_VISITE_VALIDE,
                                      HORAIRE_DEBUT_VISITE_VALIDE,
                                      DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      null, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(IDENTIFIANT_VISITE_VALIDE,
                                      HORAIRE_DEBUT_VISITE_VALIDE,
                                      DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      null,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(IDENTIFIANT_VISITE_VALIDE,
                                      HORAIRE_DEBUT_VISITE_VALIDE,
                                      DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      null));
    }
    
    /**
     * Méthode de test pour
     * {@link application.modele.Visite#Visite(
     * java.lang.String, int, java.time.LocalDate, application.modele.Client, application.modele.Exposition, application.modele.Employe, application.modele.Conferencier)}.
     * Cas uniquement valides
     */
    @Test
    void testVisiteValide() {

        assertDoesNotThrow(() -> new Visite(IDENTIFIANT_VISITE_VALIDE,
                                            HORAIRE_DEBUT_VISITE_VALIDE,
                                            DATE_VISITE_VALIDE, 
                                            CLIENT_VISITE_VALIDE,
                                            EXPOSITION_VISITE_VALIDE, 
                                            EMPLOYE_VISITE_VALIDE,
                                            CONFERENCIER_VISITE_VALIDE));
        assertDoesNotThrow(
                () -> new Visite("V000002", 600, LocalDate.of(2024, 11, 20),
                                 new Client("Alice", "987654321"),
                                 new Exposition("E000002",
                                                "Le surréalisme en couleurs",
                                                1920, 1950, 30,
                                                new String[] {"surréalisme", 
                                                              "Dali", 
                                                              "Magritte", 
                                                              "Ernst", 
                                                              "Tanguy", 
                                                              "Man Ray"},
                                                "Une exploration fascinante des"
                                                + " couleurs dans le mouvement"
                                                + " surréaliste."),
                                 new Employe("N000002", "Chloé", "Fourier", 
                                             "5678"),
                                 new Conferencier("C000002", "Durand", "Marie", 
                                                  new String[] {"surréalisme", 
                                                                "peinture"}, 
                                                  "623456789", false)));
        assertDoesNotThrow(
                () -> new Visite("V000003", 720, LocalDate.of(2024, 12, 5),
                                 new Client("Benjamin", "456123789"),
                                 new Exposition("E000003",
                                                "L'Art abstrait moderne",
                                                1945, 1980, 40,
                                                new String[] {"abstraction", 
                                                              "Kandinsky", 
                                                              "Pollock", 
                                                              "Rothko", 
                                                              "Mondrian"},
                                                "Un voyage au cœur de l'art "
                                                + "abstrait."),
                                 new Employe("N000003", "Julien", "Descartes", 
                                             "7890"),
                                 new Conferencier("C000003", "Dupont", "Jacques", 
                                                  new String[] {"abstraction",
                                                                "modernisme"}, 
                                                  "612345678", true)));
                                 
            assertDoesNotThrow(
                () -> new Visite("V000004", 960, LocalDate.of(2025, 1, 10),
                                 new Client("Camille", "321654987"),
                                 new Exposition("E000004",
                                                "Le cubisme décomposé",
                                                1907, 1925, 35,
                                                new String[] {"cubisme", 
                                                              "Picasso", 
                                                              "Braque", 
                                                              "Gris"},
                                                "Un décryptage fascinant du "
                                                + "cubisme."),
                                 new Employe("N000004", "Luc", "Galois", 
                                             "9012"),
                                 new Conferencier("C000004", "Martin", "Sophie", 
                                         new String[] {"cubisme", 
                                                       "peinture moderne"}, 
                                         "698765432", false)));
                                 
            assertDoesNotThrow(
                () -> new Visite("V000005", 1160, LocalDate.of(2025, 2, 28),
                                 new Client("Diane", "789123456"),
                                 new Exposition("E000005",
                                                "Les sculptures grecques",
                                                -500, -100, 20,
                                                new String[] {"sculpture", 
                                                              "Grèce antique", 
                                                              "Phidias", 
                                                              "Praxitèle"},
                                                "Un retour sur l'âge d'or de la"
                                                + " sculpture grecque."),
                                 new Employe("N000005", "Clara", "Euler", 
                                             "3456"),
                                 new Conferencier("C000005", "Lemoine", "Paul", 
                                         new String[] {"sculpture",
                                                       "archéologie grecque"}, 
                                         "712345678", true)));
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getIdentifiant()}.
     */
    @Test
    void testGetIdentifiant() {
        
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getHoraireDebut()}.
     */
    @Test
    void testGetHoraireDebut() {
        
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getDate()}.
     */
    @Test
    void testGetDate() {
        
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getClient()}.
     */
    @Test
    void testGetClient() {
        
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getExposition()}.
     */
    @Test
    void testGetExposition() {
        
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getEmploye()}.
     */
    @Test
    void testGetEmploye() {
        
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getConferencier()}.
     */
    @Test
    void testGetConferencier() {
        
    }

}
