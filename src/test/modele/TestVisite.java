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
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
class TestVisite {
    
    private final int HORAIRE_DEBUT_VISITE_VALIDE = 610; // 10h10
    
    private final LocalDate DATE_VISITE_VALIDE = LocalDate.of(2024, 10, 12);
    
    private final Client CLIENT_VISITE_VALIDE 
    = new Client("Esteban", "0123456789");
    
    private final Exposition EXPOSITION_VISITE_VALIDE
    = new Exposition("Les paysages impressionnistes", 1780, 1810, 25,
                     new String[] {"paysage", "impressionnisme",
                                   "Monet", "Pissarro", "Sysley",
                                   "Signac"},
                     "Très belle exposition sur l'impressionnisme.");
    
    private final Employe EMPLOYE_VISITE_VALIDE
    = new Employe("Mathieu", "Bernoulli", "1234");
    
    private final Conferencier CONFERENCIER_VISITE_VALIDE
    = new Conferencier("Thenieres", "Baptiste", 
                       new String[] {"impressionnisme", "paysage"}, 
                       "0123456789", true);
    
    private final Visite[] VISITES_VALIDES =
    {
        new Visite(HORAIRE_DEBUT_VISITE_VALIDE,
                   DATE_VISITE_VALIDE, CLIENT_VISITE_VALIDE,
                   EXPOSITION_VISITE_VALIDE, EMPLOYE_VISITE_VALIDE,
                   CONFERENCIER_VISITE_VALIDE),
        new Visite(600, LocalDate.of(2024, 11, 20),
                   new Client("Alice", "0987654321"),
                   new Exposition("Le surréalisme en couleurs", 1920,
                                  1950, 30,
                                  new String[] {"surréalisme", "Dali", 
                                                "Magritte", "Ernst", "Tanguy", 
                                                "Man Ray"},
                                  "Une exploration fascinante des couleurs dans"
                                  + " le mouvement surréaliste."),
                   new Employe("Chloé", "Fourier", "5678"),
                   new Conferencier("Durand", "Marie", 
                                    new String[] {"surréalisme", "peinture"}, 
                                    "0623456789", false)),
        new Visite(720, LocalDate.of(2024, 12, 5),
                   new Client("Benjamin", "0456123789"),
                   new Exposition("L'Art abstrait moderne", 1945,
                                  1980, 40,
                                  new String[] {"abstraction", "Kandinsky", 
                                                "Pollock", "Rothko", 
                                                "Mondrian"},
                                  "Un voyage au cœur de l'art abstrait."),
                   new Employe("Julien", "Descartes", "7890"),
                   new Conferencier("Dupont", "Jacques", 
                                    new String[] {"abstraction", "modernisme"}, 
                                    "0612345678", true)),
        new Visite(960, LocalDate.of(2025, 1, 10),
                   new Client("Camille", "0321654987"),
                   new Exposition("Le cubisme décomposé", 1907, 1925,
                                  35, new String[] {"cubisme", "Picasso", 
                                                    "Braque", "Gris"},
                                  "Un décryptage fascinant du cubisme."),
                   new Employe("Luc", "Galois", "9012"),
                   new Conferencier("Martin", "Sophie", 
                                    new String[] {"cubisme",
                                                  "peinture moderne"}, 
                                    "0698765432", false)),
        new Visite(1160, LocalDate.of(2025, 2, 28),
                   new Client("Diane", "0789123456"),
                   new Exposition("Les sculptures grecques", -500,
                                  -100, 20,
                                  new String[] {"sculpture", "Grèce antique", 
                                                "Phidias", "Praxitèle"},
                                  "Un retour sur l'âge d'or de la sculpture "
                                  + "grecque."),
                   new Employe("Clara", "Euler", "3456"),
                   new Conferencier("Lemoine", "Paul", 
                                    new String[] {"sculpture",
                                                  "archéologie grecque"}, 
                                    "0712345678", true)),
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
                     () -> new Visite(-1, DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(1440, DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(-999, DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(44444, DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(HORAIRE_DEBUT_VISITE_VALIDE, null, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(HORAIRE_DEBUT_VISITE_VALIDE,
                                      DATE_VISITE_VALIDE, null,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(HORAIRE_DEBUT_VISITE_VALIDE,
                                      DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE, null, 
                                      EMPLOYE_VISITE_VALIDE,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(HORAIRE_DEBUT_VISITE_VALIDE,
                                      DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, null,
                                      CONFERENCIER_VISITE_VALIDE));
        assertThrows(IllegalArgumentException.class, 
                     () -> new Visite(HORAIRE_DEBUT_VISITE_VALIDE,
                                      DATE_VISITE_VALIDE, 
                                      CLIENT_VISITE_VALIDE,
                                      EXPOSITION_VISITE_VALIDE, 
                                      EMPLOYE_VISITE_VALIDE, null));
    }
    
    /**
     * Méthode de test pour
     * {@link application.modele.Visite#Visite(
     * java.lang.String, int, java.time.LocalDate, application.modele.Client, application.modele.Exposition, application.modele.Employe, application.modele.Conferencier)}.
     * Cas uniquement valides
     */
    @Test
    void testVisiteValide() {

        assertDoesNotThrow(() -> new Visite(
                                            HORAIRE_DEBUT_VISITE_VALIDE,
                                            DATE_VISITE_VALIDE, 
                                            CLIENT_VISITE_VALIDE,
                                            EXPOSITION_VISITE_VALIDE, 
                                            EMPLOYE_VISITE_VALIDE,
                                            CONFERENCIER_VISITE_VALIDE));
        assertDoesNotThrow(
                () -> new Visite(600, LocalDate.of(2024, 11, 20),
                                 new Client("Alice", "0987654321"),
                                 new Exposition("Le surréalisme en couleurs",
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
                                 new Employe("Chloé", "Fourier", "5678"),
                                 new Conferencier("Durand", "Marie", 
                                                  new String[] {"surréalisme", 
                                                                "peinture"}, 
                                                  "0623456789", false)));
        assertDoesNotThrow(
                () -> new Visite(720, LocalDate.of(2024, 12, 5),
                                 new Client("Benjamin", "0456123789"),
                                 new Exposition("L'Art abstrait moderne",
                                                1945, 1980, 40,
                                                new String[] {"abstraction", 
                                                              "Kandinsky", 
                                                              "Pollock", 
                                                              "Rothko", 
                                                              "Mondrian"},
                                                "Un voyage au cœur de l'art "
                                                + "abstrait."),
                                 new Employe("Julien", "Descartes", "7890"),
                                 new Conferencier("Dupont", "Jacques", 
                                                  new String[] {"abstraction",
                                                                "modernisme"}, 
                                                  "0612345678", true)));
                                 
            assertDoesNotThrow(
                () -> new Visite(960, LocalDate.of(2025, 1, 10),
                                 new Client("Camille", "0321654987"),
                                 new Exposition("Le cubisme décomposé",
                                                1907, 1925, 35,
                                                new String[] {"cubisme", 
                                                              "Picasso", 
                                                              "Braque", 
                                                              "Gris"},
                                                "Un décryptage fascinant du "
                                                + "cubisme."),
                                 new Employe("Luc", "Galois", "9012"),
                                 new Conferencier("Martin", "Sophie", 
                                         new String[] {"cubisme", 
                                                       "peinture moderne"}, 
                                         "0698765432", false)));
                                 
            assertDoesNotThrow(
                () -> new Visite(1160, LocalDate.of(2025, 2, 28),
                                 new Client("Diane", "0789123456"),
                                 new Exposition("Les sculptures grecques",
                                                -500, -100, 20,
                                                new String[] {"sculpture", 
                                                              "Grèce antique", 
                                                              "Phidias", 
                                                              "Praxitèle"},
                                                "Un retour sur l'âge d'or de la"
                                                + " sculpture grecque."),
                                 new Employe("Clara", "Euler", "3456"),
                                 new Conferencier("Lemoine", "Paul", 
                                         new String[] {"sculpture",
                                                       "archéologie grecque"}, 
                                         "0712345678", true)));
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getHoraireDebut()}.
     */
    @Test
    void testGetHoraireDebut() {
        
        assertEquals(610, VISITES_VALIDES[0].getHoraireDebut());
        assertEquals(600, VISITES_VALIDES[1].getHoraireDebut());
        assertEquals(720, VISITES_VALIDES[2].getHoraireDebut());
        assertEquals(960, VISITES_VALIDES[3].getHoraireDebut());
        assertEquals(1160, VISITES_VALIDES[4].getHoraireDebut());
        
        assertNotEquals(609, VISITES_VALIDES[0].getHoraireDebut());
        assertNotEquals(599, VISITES_VALIDES[1].getHoraireDebut());
        assertNotEquals(719, VISITES_VALIDES[2].getHoraireDebut());
        assertNotEquals(959, VISITES_VALIDES[3].getHoraireDebut());
        assertNotEquals(1159, VISITES_VALIDES[4].getHoraireDebut());
        assertNotEquals(611, VISITES_VALIDES[0].getHoraireDebut());
        assertNotEquals(601, VISITES_VALIDES[1].getHoraireDebut());
        assertNotEquals(721, VISITES_VALIDES[2].getHoraireDebut());
        assertNotEquals(961, VISITES_VALIDES[3].getHoraireDebut());
        assertNotEquals(1161, VISITES_VALIDES[4].getHoraireDebut());
        assertNotEquals(-610, VISITES_VALIDES[0].getHoraireDebut());
        assertNotEquals(-600, VISITES_VALIDES[1].getHoraireDebut());
        assertNotEquals(-720, VISITES_VALIDES[2].getHoraireDebut());
        assertNotEquals(-960, VISITES_VALIDES[3].getHoraireDebut());
        assertNotEquals(-1160, VISITES_VALIDES[4].getHoraireDebut());
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getDate()}.
     */
    @Test
    void testGetDate() {
        
        assertEquals(LocalDate.of(2024, 10, 12), VISITES_VALIDES[0].getDate());
        assertEquals(LocalDate.of(2024, 11, 20), VISITES_VALIDES[1].getDate());
        assertEquals(LocalDate.of(2024, 12, 5), VISITES_VALIDES[2].getDate());
        assertEquals(LocalDate.of(2025, 1, 10), VISITES_VALIDES[3].getDate());
        assertEquals(LocalDate.of(2025, 2, 28), VISITES_VALIDES[4].getDate());
        
        assertNotEquals(null, VISITES_VALIDES[0].getDate());
        assertNotEquals(null, VISITES_VALIDES[1].getDate());
        assertNotEquals(null, VISITES_VALIDES[2].getDate());
        assertNotEquals(null, VISITES_VALIDES[3].getDate());
        assertNotEquals(null, VISITES_VALIDES[4].getDate());
        assertNotEquals(LocalDate.of(2024, 10, 11),
                        VISITES_VALIDES[0].getDate());
        assertNotEquals(LocalDate.of(2024, 11, 19),
                        VISITES_VALIDES[1].getDate());
        assertNotEquals(LocalDate.of(2024, 12, 4),
                        VISITES_VALIDES[2].getDate());
        assertNotEquals(LocalDate.of(2025, 1, 9),
                        VISITES_VALIDES[3].getDate());
        assertNotEquals(LocalDate.of(2025, 2, 27),
                        VISITES_VALIDES[4].getDate());
        assertNotEquals(LocalDate.of(2024, 10, 13),
                        VISITES_VALIDES[0].getDate());
        assertNotEquals(LocalDate.of(2024, 11, 21),
                        VISITES_VALIDES[1].getDate());
        assertNotEquals(LocalDate.of(2024, 12, 6),
                        VISITES_VALIDES[2].getDate());
        assertNotEquals(LocalDate.of(2025, 1, 11),
                        VISITES_VALIDES[3].getDate());
        assertNotEquals(LocalDate.of(2025, 3, 1),
                        VISITES_VALIDES[4].getDate());
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getClient()}.
     */
    @Test
    void testGetClient() {
        
        assertNotEquals(null, VISITES_VALIDES[0].getClient());
        assertNotEquals(null, VISITES_VALIDES[1].getClient());
        assertNotEquals(null, VISITES_VALIDES[2].getClient());
        assertNotEquals(null, VISITES_VALIDES[3].getClient());
        assertNotEquals(null, VISITES_VALIDES[4].getClient());
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getExposition()}.
     */
    @Test
    void testGetExposition() {
        
        assertNotEquals(null, VISITES_VALIDES[0].getExposition());
        assertNotEquals(null, VISITES_VALIDES[1].getExposition());
        assertNotEquals(null, VISITES_VALIDES[2].getExposition());
        assertNotEquals(null, VISITES_VALIDES[3].getExposition());
        assertNotEquals(null, VISITES_VALIDES[4].getExposition());
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getEmploye()}.
     */
    @Test
    void testGetEmploye() {
        
        assertNotEquals(null, VISITES_VALIDES[0].getEmploye());
        assertNotEquals(null, VISITES_VALIDES[1].getEmploye());
        assertNotEquals(null, VISITES_VALIDES[2].getEmploye());
        assertNotEquals(null, VISITES_VALIDES[3].getEmploye());
        assertNotEquals(null, VISITES_VALIDES[4].getEmploye());
    }

    /**
     * Méthode de test pour {@link application.modele.Visite#getConferencier()}.
     */
    @Test
    void testGetConferencier() {
        
        assertNotEquals(null, VISITES_VALIDES[0].getConferencier());
        assertNotEquals(null, VISITES_VALIDES[1].getConferencier());
        assertNotEquals(null, VISITES_VALIDES[2].getConferencier());
        assertNotEquals(null, VISITES_VALIDES[3].getConferencier());
        assertNotEquals(null, VISITES_VALIDES[4].getConferencier());
    }
    
    /**
     * Méthode de test pour
     * {@link application.modele.Visite#toStringHoraireDebut()}.
     */
    @Test
    void testToStringHoraireDebut() {
        
        assertEquals("10h10", VISITES_VALIDES[0].toStringHoraireDebut());
        assertEquals("10h00", VISITES_VALIDES[1].toStringHoraireDebut());
        assertEquals("12h00", VISITES_VALIDES[2].toStringHoraireDebut());
        assertEquals("16h00", VISITES_VALIDES[3].toStringHoraireDebut());
        assertEquals("19h20", VISITES_VALIDES[4].toStringHoraireDebut());
        
        assertNotEquals(null, VISITES_VALIDES[0].toStringHoraireDebut());
        assertNotEquals(null, VISITES_VALIDES[1].toStringHoraireDebut());
        assertNotEquals(null, VISITES_VALIDES[2].toStringHoraireDebut());
        assertNotEquals(null, VISITES_VALIDES[3].toStringHoraireDebut());
        assertNotEquals(null, VISITES_VALIDES[4].toStringHoraireDebut());
        assertNotEquals("10h09", VISITES_VALIDES[0].toStringHoraireDebut());
        assertNotEquals("9h59", VISITES_VALIDES[1].toStringHoraireDebut());
        assertNotEquals("11h59", VISITES_VALIDES[2].toStringHoraireDebut());
        assertNotEquals("15h59", VISITES_VALIDES[3].toStringHoraireDebut());
        assertNotEquals("19h19", VISITES_VALIDES[4].toStringHoraireDebut());
        assertNotEquals("10h11", VISITES_VALIDES[0].toStringHoraireDebut());
        assertNotEquals("10h01", VISITES_VALIDES[1].toStringHoraireDebut());
        assertNotEquals("12h01", VISITES_VALIDES[2].toStringHoraireDebut());
        assertNotEquals("16h01", VISITES_VALIDES[3].toStringHoraireDebut());
        assertNotEquals("19h21", VISITES_VALIDES[4].toStringHoraireDebut());
        assertNotEquals("1010", VISITES_VALIDES[0].toStringHoraireDebut());
        assertNotEquals("1000", VISITES_VALIDES[1].toStringHoraireDebut());
        assertNotEquals("1200", VISITES_VALIDES[2].toStringHoraireDebut());
        assertNotEquals("1600", VISITES_VALIDES[3].toStringHoraireDebut());
        assertNotEquals("1920", VISITES_VALIDES[4].toStringHoraireDebut());
        assertNotEquals("10h9", VISITES_VALIDES[0].toStringHoraireDebut());
        assertNotEquals("10h0", VISITES_VALIDES[1].toStringHoraireDebut());
        assertNotEquals("12h0", VISITES_VALIDES[2].toStringHoraireDebut());
        assertNotEquals("16h0", VISITES_VALIDES[3].toStringHoraireDebut());
        assertNotEquals("19h2", VISITES_VALIDES[4].toStringHoraireDebut());
    }

}
