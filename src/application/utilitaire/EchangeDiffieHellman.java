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
 */
public class EchangeDiffieHellman {
    
    // TODO Ajouter des messages d'erreurs précis
    
    private static final String[] NOMS_FICHIER_CLES_ALICE
    = {"p.txt", "g.txt", "g^a.txt"};
    
    private static final String[] NOMS_FICHIER_CLES_BOB
    = {"g^b.txt"};
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return la donnée secrète
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
            e.printStackTrace();
            throw new GenerationDonneeSecreteException();
        }  
        InetAddress ipClient = Serveur.envoyerFichiers(65432, NOMS_FICHIER_CLES_ALICE);
        
        if (ipClient == null) {
            
            /*
             * Le serveur s'est arrêté ou n'a pas du accepté de connexion.
             * La génération de la donnée secrète devient alors inutile
             */      
            return 0;
        }

        Client.recevoirFichiers(ipClient.getHostAddress(), 65432,
                                NOMS_FICHIER_CLES_BOB, null);
        int gExpB;
        try {
            gExpB = Integer.parseInt(GestionFichiers.lireFichier("g^b.txt"));
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            throw new GenerationDonneeSecreteException();
        }
        
        return Mathematiques.calculExponentielleModulo(gExpB, a, p); // bouchon
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param ipServeur 
     * @return la donnée secrète
     * @throws GenerationDonneeSecreteException 
     */
    public static int genererDonneeSecreteBob(String ipServeur)
            throws GenerationDonneeSecreteException {
        
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
            e.printStackTrace();
            throw new GenerationDonneeSecreteException();
        }

        int b = Mathematiques.genererNombreAleatoire(1, p);
        int gExpB = Mathematiques.calculExponentielleModulo(g, b, p);
        try {
            GestionFichiers.ecrireFichier(NOMS_FICHIER_CLES_BOB[0],
                                          Integer.toString(gExpB));
        } catch (IOException e) {
            e.printStackTrace();
            throw new GenerationDonneeSecreteException();
        }  
        Serveur.envoyerFichiers(65432, NOMS_FICHIER_CLES_BOB);

        return Mathematiques.calculExponentielleModulo(gExpA, b, p);
    }
}
