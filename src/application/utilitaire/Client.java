/*
 * Serveur.java                                      21 oct. 2024
 * IUT Rodez, info2 2023-2024, pas de copyright ni "copyleft" 
 */
package application.utilitaire;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class Client {

//    /**
//     * TODO commenter le rôle de cette méthode (SRP)
//     * @param args
//     */
//    public static void main(String[] args) {
//        recevoirFichier("localhost", 12345, "fichierRecu.txt");
//    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param adresseServeur
     * @param port
     * @param cheminFichier
     */
    public static void recevoirFichier(String adresseServeur, int port, String cheminFichier) {
        final int TAILLE_BLOC_DONNEES = 1024;

        try {
            // Connexion au serveur
            Socket socket = new Socket(adresseServeur, port);
            System.out.println("Connexion au serveur établie.");

            // Ouverture du flux d'entrée pour recevoir le fichier
            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
            FileOutputStream fileOut = new FileOutputStream(cheminFichier);

            // Réception du fichier par blocs
            byte[] buffer = new byte[TAILLE_BLOC_DONNEES];
            int tailleBloc;
            while ((tailleBloc = in.read(buffer)) != -1) {
                fileOut.write(buffer, 0, tailleBloc);
            }

            // Fermeture des flux et du socket
            System.out.println("Fichier reçu et enregistré sous : " + cheminFichier);
            in.close();
            fileOut.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
