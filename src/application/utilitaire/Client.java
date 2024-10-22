/*
 * Serveur.java                                      21 oct. 2024
 * IUT Rodez, info2 2023-2024, pas de copyright ni "copyleft" 
 */
package application.utilitaire;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class Client {

    /**
     * Reçoit plusieurs fichiers d'un serveur via une connexion socket.
     * 
     * Le client lit d'abord la taille de chaque fichier envoyée par le serveur, 
     * puis lit le contenu du fichier jusqu'à ce que toutes les données aient été reçues.
     * Chaque fichier est sauvegardé localement avec le chemin spécifié.
     *
     * @param adresseServeur L'adresse IP du serveur distant.
     * @param port Le port sur lequel le serveur écoute.
     * @param cheminsFichiers Un tableau contenant les chemins où sauvegarder les fichiers reçus.
     * 
     * @throws IOException Si une erreur survient lors de la réception des fichiers ou de l'écriture des données sur le disque.
     */
    public static void recevoirFichiers(String adresseServeur, int port, String[] cheminsFichiers) {
        final int TAILLE_BLOC_DONNEES = 1024;

        try (Socket socket = new Socket(adresseServeur, port);
             BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
             DataInputStream dataIn = new DataInputStream(in)) {

            for (String cheminFichier : cheminsFichiers) {
                File fichier = new File(cheminFichier);

                // Lire la taille du fichier envoyé
                long tailleFichier = dataIn.readLong();

                try (FileOutputStream fileOut = new FileOutputStream(fichier)) {
                    byte[] buffer = new byte[TAILLE_BLOC_DONNEES];
                    int bytesLus;
                    long bytesRestants = tailleFichier;

                    // Lire le fichier jusqu'à ce que toutes les données soient reçues
                    while (bytesRestants > 0 && (bytesLus = dataIn.read(buffer, 0, (int)Math.min(buffer.length, bytesRestants))) != -1) {
                        fileOut.write(buffer, 0, bytesLus);
                        bytesRestants -= bytesLus;
                    }
                    System.out.println("Fichier " + fichier.getAbsolutePath() + " reçu.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
