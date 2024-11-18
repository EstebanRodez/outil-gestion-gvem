/**
 * Client.java
 * 21 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Cette classe est responsable de la gestion de la connexion
 * à un serveur distant pour recevoir des fichiers.
 * 
 * La classe permet de se connecter à un serveur via un socket
 * et de recevoir plusieurs fichiers, en sauvegardant chaque 
 * fichier dans un emplacement spécifié. 
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class Client {

    /**
     * Reçoit plusieurs fichiers d'un serveur via une connexion
     * socket.<br>
     * Le client lit d'abord la taille de chaque fichier envoyée par
     * le serveur, puis lit le contenu du fichier jusqu'à ce que 
     * toutes les données aient été reçues.<br>
     * Chaque fichier est sauvegardé localement avec le chemin
     * spécifié.
     *
     * @param adresseServeur L'adresse IP du serveur distant
     * @param port Le port sur lequel le serveur écoute
     * @param cheminsFichiers Un tableau contenant les chemins où
     *                        sauvegarder les fichiers reçus
     * @param dossierDestination Le dossier dans lequel les fichiers
     *                           seront enregistrés
     * @throws IOException Si une erreur survient lors de la
     *                     réception des fichiers ou de l'écriture
     *                     des données sur le disque
     */
    public static void recevoirFichiers(String adresseServeur, int port,
                                        String[] cheminsFichiers, String dossierDestination) {
        
        final int TAILLE_BLOC_DONNEES = 1024;

        try (Socket socket = new Socket(adresseServeur, port);
                
            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
            DataInputStream dataIn = new DataInputStream(in)) {
            
//            File dossier = new File(dossierDestination);
//            if (!dossier.exists()) {
//                dossier.mkdirs(); // Créer les répertoires nécessaires
//            }

            for (String cheminFichier : cheminsFichiers) {
                // Créer le fichier dans le dossier de destination
                File fichier = new File(dossierDestination, cheminFichier);

                // Lire la taille du fichier envoyé
                long tailleFichier = dataIn.readLong();
                
                try (FileOutputStream fileOut = new FileOutputStream(fichier)) {
                    byte[] buffer = new byte[TAILLE_BLOC_DONNEES];
                    int bytesLus;
                    long bytesRestants = tailleFichier;

                    // Lire le fichier jusqu'à ce que toutes les données soient reçues
                    while (bytesRestants > 0 && (bytesLus = dataIn.read(buffer, 
                            0, (int)Math.min(buffer.length, bytesRestants))) != -1) {
                        fileOut.write(buffer, 0, bytesLus);
                        bytesRestants -= bytesLus;
                    }
                    System.out.println("Fichier " + fichier.getAbsolutePath() 
                                       + " reçu.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
