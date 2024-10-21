/*
 * Serveur.java                                      21 oct. 2024
 * IUT Rodez, info2 2023-2024, pas de copyright ni "copyleft" 
 */
package application.utilitaire;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** 
 * Classe Serveur responsable de l'envoi d'un fichier texte à un client une fois la connexion établie.
 * @author tom.jammes
 */
public class Serveur {

//    /** 
//     * Méthode principale pour lancer le serveur.
//     * @param args non utilisé
//     */
//    public static void main(String[] args) {
//        envoyerFichier(12345, "fichierATransmettre.txt");
//    }

    /** 
     * Cette méthode attend la connexion d'un client et lui envoie un fichier texte.
     * 
     * @param port Le port sur lequel le serveur écoute.
     * @param cheminFichier Le chemin du fichier texte à envoyer.
     */
    public static void envoyerFichier(int port, String cheminFichier) {
        final int TAILLE_BLOC_DONNEES = 1024;
        
        try {
            // Création d'un ServerSocket écoutant sur le port spécifié.
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Serveur en attente de connexion sur le port " + port + "...");

            // Attente d'une connexion client.
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connexion établie avec le client.");

            // Ouverture du fichier à envoyer.
            File fichier = new File(cheminFichier);
            if (!fichier.exists() || !fichier.isFile()) {
                System.err.println("Le fichier spécifié n'existe pas ou n'est pas valide.");
                clientSocket.close();
                serverSocket.close();
                return;
            }

            // Lecture du fichier.
            FileInputStream fileIn = new FileInputStream(fichier);
            BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream());

            // Envoi du fichier en blocs de données au client.
            byte[] buffer = new byte[TAILLE_BLOC_DONNEES];
            int tailleBloc;
            while ((tailleBloc = fileIn.read(buffer)) != -1) {
                out.write(buffer, 0, tailleBloc);
                System.out.println("Bloc de " + tailleBloc + " octets envoyé.");
            }

            // Fermeture des flux et des connexions.
            System.out.println("Fichier envoyé : " + fichier.getAbsolutePath());
            fileIn.close();
            out.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
