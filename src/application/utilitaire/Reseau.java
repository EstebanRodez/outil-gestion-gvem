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
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

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
    
    private static int portExportation = 65430;
    
    private static ServerSocket serverSocket;
    
    private static final String ERREUR_PORT_INVALIDE =
    """
    Le port doit être entre 1 et 65535.     
    """;

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
        return portExportation;
    }
    
    /**
     * Définit le port d'exportation utilisé pour la communication réseau.
     * Le port doit être un entier valide dans l'intervalle [1, 65535].
     * 
     * @param nouveauPort Le numéro de port à utiliser
     * @throws IllegalArgumentException Si le port est en dehors des limites
     *                                  valides
     */
    public static void setPortExportation(int nouveauPort) {
        if (nouveauPort > 0 && nouveauPort <= 65535) { 
            portExportation = nouveauPort;
        } else {
            throw new IllegalArgumentException(ERREUR_PORT_INVALIDE);
        }
    }
    
    /**
     * Vérifie si une adresse IP donnée est syntaxiquement valide.
     * Cette méthode utilise une expression régulière pour valider
     * une adresse IPv4, puis vérifie que chaque segment est compris
     * entre 0 et 255.
     * 
     * @param adresseIP L'adresse IP à valider
     * @return {@code true} si l'adresse est valide, {@code false} sinon
     */
    public static boolean isAdresseIPValide(String adresseIP) {
        
        // Expression régulière pour vérifier une adresse IPv4
        String ipPattern = 
            "^([0-9]{1,3}\\.){3}[0-9]{1,3}$";
        
        if (!adresseIP.matches(ipPattern)) {
            return false;
        }

        String[] segmentsIP = adresseIP.split("\\.");
        for (String segment : segmentsIP) {
            int value = Integer.parseInt(segment);
            if (value < 0 || value > 255) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Vérifie si une chaîne de caractères représente un numéro de port valide.
     * Le port est valide s'il est constitué de 1 à 5 chiffres et compris
     * entre 0 et 65535.
     * 
     * @param port La chaîne représentant le numéro de port à valider
     * @return {@code true} si le port est valide, {@code false} sinon
     */
    public static boolean isPortValide(String port) {
        return port.matches("(\\d){1,5}") && Integer.parseInt(port) >= 0
               && Integer.parseInt(port) <= 65535;
    }
    
    /**
     * Vérifie si une adresse IP est actuellement accessible.
     * La méthode tente d'établir une connexion avec l'adresse IP pour
     * vérifier sa disponibilité.
     * 
     * @param adresseIP L'adresse IP à tester
     * @return {@code true} si l'adresse IP est accessible, {@code false} sinon
     */
    public static boolean isAdresseIPDisponible(String adresseIP) {
        
        try {
            return InetAddress.getByName(adresseIP).isReachable(0);
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Vérifie si un port est disponible pour établir une connexion avec une
     * adresse IP donnée.
     * La méthode tente d'ouvrir une connexion socket sur l'adresse et le
     * port spécifiés.
     * 
     * @param adresseIP L'adresse IP à vérifier
     * @param port Le port à tester
     * @return {@code true} si le port est disponible, {@code false} sinon
     */
    public static boolean isPortDisponible(String adresseIP, int port) {
        
        try (Socket socket = new Socket(adresseIP, port)) {
            socket.close();
            return true;
        } catch (IOException e) {
            // Ne rien faire
        }
        
        return false;
    }
    
    /**
     * Vérifie si un port est utilisable sur la machine locale.
     * La méthode tente de lier un serveur socket sur le port spécifié.
     * 
     * @param port Le numéro de port à tester
     * @return {@code true} si le port est utilisable, {@code false} sinon
     * @throws UnknownHostException 
     * @throws SocketException 
     */
    public static boolean isPortUtilisable(int port) {
        
        System.out.println(port);
        try (ServerSocket socket = new ServerSocket(port)) {
            return true;
        } catch (IOException e) {
            // Ne rien faire
        }
        
        return false;
    }
    
    /**
     * Obtient l'adresse IP locale de la machine sur laquelle cette méthode
     * est appelée.<br>
     * La méthode crée une connexion fictive à un serveur DNS public
     * (Google DNS : 8.8.8.8) pour déterminer l'adresse IP utilisée pour
     * accéder au réseau.
     * 
     * @return L'adresse IP locale de la machine, ou {@code null} si une
     *         erreur se produit
     */
    public static String getMonAdresseIP() {
        
        try {
            DatagramSocket socket = new DatagramSocket();
            
            // DNS Google
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            
            String monAdresseIP = socket.getLocalAddress().getHostAddress();
            socket.close();
            return monAdresseIP;
        } catch (IOException e) {
            // Ne rien faire
        }
        
        return null;
    }
}
