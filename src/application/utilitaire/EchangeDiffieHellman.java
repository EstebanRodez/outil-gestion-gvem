/*
 * EchangeDiffieHellman.java                           
 * 15 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Cette classe implémente le protocole d'échange de clés Diffie-Hellman,
 * permettant à deux parties (Alice et Bob) de générer une clé secrète commune
 * à travers un canal de communication non sécurisé.<br>
 * 
 * Le protocole repose sur l'utilisation d'opérations exponentielles
 * modulaires dans un groupe fini, garantissant la sécurité cryptographique
 * de la clé échangée.<br>
 * <br>
 * Cette classe gère :<br>
 * <ul>
 *     <li>La génération des paramètres (nombre premier p, générateur g, 
 *         et exponents secrets).</li>
 *     <li>L'envoi et la réception de fichiers contenant les 
 *         clés intermédiaires.</li>
 *     <li>Le calcul de la clé secrète commune.</li>
 * </ul>
 * <br>
 * Elle s'appuie sur d'autres classes utilitaires pour la gestion des fichiers, 
 * la communication réseau et les opérations mathématiques.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class EchangeDiffieHellman {
    
    private static final int PORT_EXPORTATION = Reseau.getPortExportation();
    
    private static final String[] NOMS_FICHIER_CLES_ALICE
    = {"p.txt", "g.txt", "g^a.txt"};
    
    private static final String[] NOMS_FICHIER_CLES_BOB
    = {"g^b.txt"};

    private static final String ERREUR_IPSERVEUR_INVALIDE =
    """
    Impossible de générer la donnée secrète pour l'échange des fichiers cryptés.
    L'IP du serveur (Alice) ne doit pas être nul ou vide.
    """;

    private static final String ERREUR_ECRITURE_ALICE =
    """
    Impossible de générer la donnée secrète pour l'échange des fichiers cryptés.
    Le fichier à envoyer pour p ou g ou g^a n'a pas pu être crée.
    """;

    private static final String ERREUR_LECTURE_ALICE =
    """
    Impossible de générer la donnée secrète pour l'échange des fichiers cryptés.
    Le fichier reçu pour g^b n'a pas pu être lu.
    """;

    private static final String ERREUR_COMMUNICATION_FERMEE =
    """
    Impossible de générer la donnée secrète pour l'échange des fichiers cryptés.
    L'IP de l'autre appareil n'a pas être déterminée.
    """;

    private static final String ERREUR_ECRITURE_BOB = 
    """
    Impossible de générer la donnée secrète pour l'échange des fichiers cryptés.
    Le fichier à envoyer pour g^b n'a pas pu être crée.
    """;

    private static final String ERREUR_LECTURE_BOB =
    """
    Impossible de générer la donnée secrète pour l'échange des fichiers cryptés.
    Le fichier reçu pour p ou g ou g^a n'a pas pu être lu.
    """;
    
    /**
     * Génère la clé secrète pour Alice en suivant le protocole Diffie-Hellman.
     * 
     * <p>Cette méthode effectue les opérations suivantes :</p>
     * <ul>
     *   <li>Génération des paramètres p (nombre premier) et g (générateur).</li>
     *   <li>Calcul de g^a mod p, où a est un entier secret.</li>
     *   <li>Envoi des fichiers contenant p, g, et g^a à Bob.</li>
     *   <li>Réception de g^b envoyé par Bob.</li>
     *   <li>Calcul de la clé secrète commune (g^(ab) mod p).</li>
     * </ul>
     *
     * @return la clé secrète calculée par Alice.
     * @throws GenerationDonneeSecreteException si une erreur survient :
     * <ul>
     *   <li>Si l'écriture des fichiers p, g ou g^a échoue.</li>
     *   <li>Si la connexion avec Bob échoue (adresse IP du client non
     *       déterminée).</li>
     *   <li>Si le fichier contenant g^b ne peut pas être lu ou contient des
     *       données non valides.</li>
     * </ul>
     */
    public static int genererDonneeSecreteAlice()
            throws GenerationDonneeSecreteException {

        int p = Mathematiques.trouverNombrePremier(
                Mathematiques.genererNombreAleatoire(1000,9999));
        System.out.println(p);

        int g = Mathematiques.trouverDernierGroupeMultiplicatif(p);
        System.out.println(g);

        int a = Mathematiques.genererNombreAleatoire(1, p);
        int gExpA = Mathematiques.calculExponentielleModulo(g, a, p);

        try {
            
            GestionFichiers.ecrireFichier(NOMS_FICHIER_CLES_ALICE[0],
                                          Integer.toString(p));
            GestionFichiers.ecrireFichier(NOMS_FICHIER_CLES_ALICE[1],
                                          Integer.toString(g));
            GestionFichiers.ecrireFichier(NOMS_FICHIER_CLES_ALICE[2],
                                          Integer.toString(gExpA));
        } catch (IOException e) {
            throw new GenerationDonneeSecreteException(ERREUR_ECRITURE_ALICE);
        }  
        
        InetAddress ipClient
        = Reseau.envoyerFichiers(PORT_EXPORTATION, NOMS_FICHIER_CLES_ALICE);       
        if (ipClient == null) {
            
            /*
             * Le serveur s'est arrêté ou n'a pas du accepté de connexion.
             * La génération de la donnée secrète devient alors inutile
             */      
            throw new GenerationDonneeSecreteException(
                    ERREUR_COMMUNICATION_FERMEE);
        }

        Reseau.recevoirFichiers(ipClient.getHostAddress(), PORT_EXPORTATION,
                                NOMS_FICHIER_CLES_BOB, null);
        int gExpB;
        try {
            gExpB = Integer.parseInt(GestionFichiers.lireFichier("g^b.txt"));
        } catch (NumberFormatException | IOException e) {
            throw new GenerationDonneeSecreteException(ERREUR_LECTURE_ALICE);
        }
        
        return Mathematiques.calculExponentielleModulo(gExpB, a, p); // bouchon
    }
    
    /**
     * Génère la clé secrète pour Bob en suivant le protocole Diffie-Hellman.
     * 
     * <p>Cette méthode effectue les opérations suivantes :</p>
     * <ul>
     *   <li>Réception des fichiers p, g et g^a envoyés par Alice.</li>
     *   <li>Calcul de g^b mod p, où b est un entier secret.</li>
     *   <li>Envoi de g^b à Alice.</li>
     *   <li>Calcul de la clé secrète commune (g^(ab) mod p).</li>
     * </ul>
     *
     * @param ipServeur l'adresse IP du serveur (Alice).
     * @return la clé secrète calculée par Bob.
     * @throws IllegalArgumentException si l'adresse IP du serveur est nulle
     *                                  ou vide.
     * @throws GenerationDonneeSecreteException si une erreur survient :
     * <ul>
     *   <li>Si les fichiers contenant p, g ou g^a ne peuvent pas être lus ou
     *       contiennent des données non valides.</li>
     *   <li>Si l'écriture du fichier contenant g^b échoue.</li>
     * </ul>
     */
    public static int genererDonneeSecreteBob(String ipServeur)
            throws GenerationDonneeSecreteException {
        
        if (ipServeur == null || ipServeur.isBlank()) {
            throw new IllegalArgumentException(ERREUR_IPSERVEUR_INVALIDE);
        }
        
        Reseau.recevoirFichiers(ipServeur, PORT_EXPORTATION,
                                NOMS_FICHIER_CLES_ALICE, null);

        int p, g, gExpA;
        try {
            p = Integer.parseInt(
                    GestionFichiers.lireFichier(NOMS_FICHIER_CLES_ALICE[0]));
            g = Integer.parseInt(
                    GestionFichiers.lireFichier(NOMS_FICHIER_CLES_ALICE[1]));
            gExpA = Integer.parseInt(
                    GestionFichiers.lireFichier(NOMS_FICHIER_CLES_ALICE[2]));
        } catch (NumberFormatException | IOException e) {
            throw new GenerationDonneeSecreteException(ERREUR_LECTURE_BOB);
        }

        int b = Mathematiques.genererNombreAleatoire(1, p);
        int gExpB = Mathematiques.calculExponentielleModulo(g, b, p);
        try {
            GestionFichiers.ecrireFichier(NOMS_FICHIER_CLES_BOB[0],
                                          Integer.toString(gExpB));
        } catch (IOException e) {
            e.printStackTrace();
            throw new GenerationDonneeSecreteException(ERREUR_ECRITURE_BOB);
        }  
        Reseau.envoyerFichiers(PORT_EXPORTATION, NOMS_FICHIER_CLES_BOB);

        return Mathematiques.calculExponentielleModulo(gExpA, b, p);
    }
    
    /**
     * Supprime tous les fichiers crées par Alice.<br>
     * Fichiers concernés : p, g, g^a
     */
    public static void supprimerFichiersAlice() {
        
        for (String nomFichier : NOMS_FICHIER_CLES_ALICE) {
            
            try {
                Files.deleteIfExists(Path.of(nomFichier));
            } catch (IOException e) {
                // Ne rien faire
            }
        }
    }
    
    /**
     * Supprime tous les fichiers crées par Bob.<br>
     * Fichiers concernés : g^b
     */
    public static void supprimerFichiersBob() {
        
        for (String nomFichier : NOMS_FICHIER_CLES_BOB) {
            
            try {
                Files.deleteIfExists(Path.of(nomFichier));
            } catch (IOException e) {
                // Ne rien faire
            }
        }
    }
}
