/*
 * Serveur.java                                      21 oct. 2024
 * IUT Rodez, info2 2023-2024, pas de copyright ni "copyleft" 
 */
package application.utilitaire;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** 
 * Classe Serveur responsable de l'envoi d'un fichier texte à un client une fois la connexion établie.
 * @author Baptiste Thenieres
 */
public class Serveur {

    /**
     * Envoie plusieurs fichiers à un client via une connexion socket.
     * 
     * Pour chaque fichier, le serveur envoie d'abord la taille du fichier, suivie de son contenu. 
     * Cela permet au client de savoir exactement combien de données il doit recevoir 
     * avant de passer au fichier suivant.
     *
     * @param port Le port sur lequel le serveur écoute les connexions du client.
     * @param cheminsFichiers Un tableau contenant les chemins des fichiers à envoyer.
     * 
     * @throws IOException Si une erreur survient lors de la lecture des fichiers ou de l'envoi de données au client.
     */
    public static void envoyerFichiers(int port, String[] cheminsFichiers) {
        final int TAILLE_BLOC_DONNEES = 1024;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur en attente de connexion sur le port " + port + "...");

            try (Socket clientSocket = serverSocket.accept();
                 BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream());
                 DataOutputStream dataOut = new DataOutputStream(out)) {

                for (String cheminFichier : cheminsFichiers) {
                    File fichier = new File(cheminFichier);

                    if (!fichier.exists() || !fichier.isFile()) {
                        System.err.println("Le fichier " + fichier.getAbsolutePath() + " n'existe pas.");
                        return;
                    }

                    // Envoyer la taille du fichier au client
                    dataOut.writeLong(fichier.length());
                    dataOut.flush();  // S'assurer que la taille est envoyée avant le fichier

                    // Envoyer le contenu du fichier
                    try (FileInputStream fileIn = new FileInputStream(fichier)) {
                        byte[] buffer = new byte[TAILLE_BLOC_DONNEES];
                        int tailleBloc;
                        while ((tailleBloc = fileIn.read(buffer)) != -1) {
                            dataOut.write(buffer, 0, tailleBloc);
                        }
                        dataOut.flush();  // S'assurer que toutes les données sont envoyées
                        System.out.println("Fichier " + fichier.getAbsolutePath() + " envoyé.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
