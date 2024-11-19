/*
 * Reseau.java                           
 * 19 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe utilitaire pour gérer les opérations réseau liées à l'envoi et à
 * la réception de fichiers entre un serveur et un client via des sockets.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class Reseau {
    
    private static final int PORT_EXPORTATION = 65430;
    
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
                
                return clientSocket.getInetAddress();
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
     * Ferme le serveur si celui-ci est encore ouvert.
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
    
    /**
     * Récupère le port utilisé pour l'exportation des données
     * 
     * @return le port pour l'exportation des données
     */
    public static int getPortExportation() {
        return PORT_EXPORTATION;
    }
}
