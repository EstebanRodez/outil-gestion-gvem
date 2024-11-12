/**
 * Serveur.java
 * 21 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/** 
 * Classe Serveur responsable de l'envoi d'un fichier texte à un
 * client une fois la connexion établie.
 * 
 * @author Baptiste Thenieres
 * @version 1.0
 */
public class Serveur {
    
    private static ServerSocket serverSocket;

    /**
     * Envoie plusieurs fichiers à un client via une connexion
     * socket.<br>
     * Pour chaque fichier, le serveur envoie d'abord la taille du 
     * fichier, suivie de son contenu. Cela permet au client de
     * savoir exactement combien de données il doit recevoir avant de
     * passer au fichier suivant.
     *
     * @param port Le port sur lequel le serveur écoute les
     *             connexions du client
     * @param cheminsFichiers Un tableau contenant les chemins des
     *                        fichiers à envoyer
     * @return adresse du client
     * @throws IOException Si une erreur survient lors de la lecture
     *                     des fichiers ou de l'envoi de données au
     *                     client
     */
    public static InetAddress envoyerFichiers(int port, String[] cheminsFichiers) {
        final int TAILLE_BLOC_DONNEES = 1024;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Serveur en attente de connexion sur le port " + port + "...");

            try (Socket clientSocket = serverSocket.accept();
                 BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream());
                 DataOutputStream dataOut = new DataOutputStream(out)) {

                for (String cheminFichier : cheminsFichiers) {
                    File fichier = new File(cheminFichier);

                    if (!fichier.exists() || !fichier.isFile()) {
                        System.err.println("Le fichier " + fichier.getAbsolutePath() + " n'existe pas.");
                        continue;
                    }

                    dataOut.writeLong(fichier.length());
                    dataOut.flush();

                    try (FileInputStream fileIn = new FileInputStream(fichier)) {
                        byte[] buffer = new byte[TAILLE_BLOC_DONNEES];
                        int tailleBloc;
                        while ((tailleBloc = fileIn.read(buffer)) != -1) {
                            dataOut.write(buffer, 0, tailleBloc);
                        }
                        dataOut.flush();
                        System.out.println("Fichier " + fichier.getAbsolutePath() + " envoyé.");
                    }
                }
                
                return clientSocket.getLocalAddress();
            }
        } catch (IOException e) {
            if (!Thread.currentThread().isInterrupted()) { // Vérifie si l'interruption est intentionnelle
                e.printStackTrace();
            }
        } finally {
            fermerServeur();
        }
        return null;
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    public static void fermerServeur() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                System.out.println("Serveur fermé.");
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture du serveur : " + e.getMessage());
            }
        }
    }
}