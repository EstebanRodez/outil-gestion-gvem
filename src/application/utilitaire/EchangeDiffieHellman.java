/*
 * EchangeDiffieHellman.java                           
 * 15 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.IOException;
import java.net.InetAddress;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class EchangeDiffieHellman {
    
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
     * TODO commenter le rôle de cette méthode (SRP)
     * @return la donnée secrète
     * @throws GenerationDonneeSecreteException 
     * @throws GenerationDonneeSecreteException
     * @throws GenerationDonneeSecreteException
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
        InetAddress ipClient = Serveur.envoyerFichiers(65432, NOMS_FICHIER_CLES_ALICE);
        
        if (ipClient == null) {
            
            /*
             * Le serveur s'est arrêté ou n'a pas du accepté de connexion.
             * La génération de la donnée secrète devient alors inutile
             */      
            throw new GenerationDonneeSecreteException(
                    ERREUR_COMMUNICATION_FERMEE);
        }

        Client.recevoirFichiers(ipClient.getHostAddress(), 65432,
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
     * TODO commenter le rôle de cette méthode (SRP)
     * @param ipServeur 
     * @return la donnée secrète
     * @throws IllegalArgumentException 
     * @throws GenerationDonneeSecreteException 
     * @throws GenerationDonneeSecreteException
     */
    public static int genererDonneeSecreteBob(String ipServeur)
            throws GenerationDonneeSecreteException {
        
        if (ipServeur == null || ipServeur.isBlank()) {
            throw new IllegalArgumentException(ERREUR_IPSERVEUR_INVALIDE);
        }
        
        Client.recevoirFichiers(ipServeur, 65432,
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
        Serveur.envoyerFichiers(65432, NOMS_FICHIER_CLES_BOB);

        return Mathematiques.calculExponentielleModulo(gExpA, b, p);
    }
}
