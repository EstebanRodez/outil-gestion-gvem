/*
 * Vigenère.java                           
 * 15 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Cette classe contient des méthodes pour effectuer des opérations de
 * chiffrement et de déchiffrement basées sur le chiffre de Vigenère.
 * Elle permet de générer une clé de chiffrement à partir d'une donnée secrète
 * et d'un alphabet donné, ainsi que de récupérer un alphabet unique à partir
 * d'un fichier texte.<br>
 * <br>
 * Le chiffre de Vigenère est une méthode de chiffrement polyalphabétique qui
 * utilise une clé répétée pour décaler les caractères du texte en clair
 * selon les positions définies dans l'alphabet.<br>
 * Cette classe facilite ces opérations sur des données textuelles.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class Vigenere {
    
    private static final String[] NOMS_FICHIERS_DONNEES
    = {
        "conferenciers.csv", "employes.csv",
        "expositions.csv", "visites.csv"
    };
    
    private static final String[] NOMS_FICHIERS_ALPHABET
    = {
        "conferenciers_alphabet", "employes_alphabet",
        "expositions_alphabet", "visites_alphabet"
    };
    
    private static final String[] NOMS_FICHIERS_ENVOIS
    = {
        "conferenciers_crypté", "employes_crypté",
        "expositions_crypté", "visites_crypté"
    };
    
    /**
     * Génère une clé de chiffrement basée sur une donnée secrète et
     * un alphabet donné. La clé est calculée en transformant la donnée
     * secrète en une séquence de caractères issus de l'alphabet, en
     * appliquant des décalages successifs.
     *
     * @param donneeSecrete la donnée secrète utilisée pour générer la clé.
     * @param alphabet l'alphabet à utiliser pour les caractères de la clé.
     * @return une chaîne de caractères représentant la clé de chiffrement.
     */
    public static String genererCleChiffrement(int donneeSecrete,
                                               String alphabet) {
        
        int donnee = donneeSecrete;
        int longueurAlphabet = alphabet.length();
        StringBuilder cle = new StringBuilder();
        
        while (donnee > 0) {
            int decalage = donnee % longueurAlphabet;
            cle.append(alphabet.charAt(decalage));
            donnee /= longueurAlphabet;
        }
        
        return cle.toString();
    }
    
    /**
     * Récupère l'alphabet utilisé dans un fichier texte donné.
     * L'alphabet est construit en ajoutant chaque caractère unique du
     * fichier, dans l'ordre d'apparition, à une chaîne de caractères.
     *
     * @param cheminFichier le chemin du fichier texte à analyser.
     * @return une chaîne de caractères représentant l'alphabet unique
     *         trouvé dans le fichier.
     */
    public static String recupererAlphabet(String cheminFichier) {
        
        HashSet<Character> lettres = new HashSet<>();
        StringBuilder chaineAlphabet = new StringBuilder();
        try {
            
            FileInputStream fileInputStream
            = new FileInputStream(cheminFichier);
            InputStreamReader inputStreamReader
            = new InputStreamReader(fileInputStream, "windows-1252");
            BufferedReader fluxLecture = new BufferedReader(inputStreamReader);
            
            String ligne = fluxLecture.readLine();
            while (ligne != null) {
                
                for (int indiceLigne = 0;
                     indiceLigne < ligne.length();
                     indiceLigne++) {
                    
                    Character lettre = ligne.charAt(indiceLigne);
                    lettres.add(lettre);
                }
                ligne = fluxLecture.readLine();
            }
            
            fluxLecture.close();
        } catch (IOException e) {
            // Ne rien faire
            e.printStackTrace();
        }
        
        Character[] tabLettres = lettres.toArray(new Character[lettres.size()]);
        Arrays.sort(tabLettres);
        
        for (char lettre : tabLettres) {
            chaineAlphabet.append(lettre);
        }
        
        return chaineAlphabet.toString();
    }
    
    /**
     * Crypte le contenu d'un fichier à l'aide d'une clé de chiffrement et
     * d'un alphabet défini.
     * 
     * <p>La méthode effectue les étapes suivantes :</p>
     * <ul>
     *   <li>Lit le contenu du fichier spécifié ligne par ligne.</li>
     *   <li>Applique un chiffrement par substitution modulaire en fonction
     *       de l'alphabet et de la clé.</li>
     *   <li>Écrit le contenu chiffré dans un nouveau fichier avec le
     *       suffixe "_crypté".</li>
     * </ul>
     * 
     * @param cheminFichier chemin du fichier à crypter.
     * @param cle clé de chiffrement utilisée.
     * @param alphabet alphabet utilisé pour le chiffrement.
     */
    public static void crypter(String cheminFichier, String cle,
                               String alphabet) {
        
        try {
            
            FileInputStream fileInputStream
            = new FileInputStream(cheminFichier);
            InputStreamReader inputStreamReader
            = new InputStreamReader(fileInputStream, "windows-1252");
            BufferedReader fluxLecture = new BufferedReader(inputStreamReader);
            
            String nomFichierCrypte
            = cheminFichier.substring(0, cheminFichier.lastIndexOf("."))
              + "_crypté";
            FileOutputStream fileOutputStream
            = new FileOutputStream(nomFichierCrypte);
            OutputStreamWriter outputStreamWriter
            = new OutputStreamWriter(fileOutputStream, "windows-1252");
            BufferedWriter fluxEcriture
            = new BufferedWriter(outputStreamWriter);
            
            int modulo = alphabet.length();
            String ligne = fluxLecture.readLine();
            while (ligne != null) {
                
                for (int indiceLigne = 0;
                     indiceLigne < ligne.length();
                     indiceLigne++) {
                    
                    char lettre = ligne.charAt(indiceLigne);
                    int indexLettre = alphabet.indexOf(lettre);
                    int indexLettreCrypte
                    = (indexLettre
                       + alphabet.indexOf(cle.charAt(indiceLigne%cle.length())))
                       % modulo;
                    char lettreCrypte = alphabet.charAt(indexLettreCrypte);
                    fluxEcriture.append(lettreCrypte);
                }
                ligne = fluxLecture.readLine();
                fluxEcriture.write("\n");
            }
            
            fluxLecture.close();
            fluxEcriture.close();
        } catch (IOException e) {
            // Ne rien faire
            e.printStackTrace();
        }
    }
    
    /**
     * Décrypte le contenu d'un fichier chiffré à l'aide d'une clé de
     * déchiffrement et d'un alphabet défini.
     * 
     * <p>La méthode effectue les étapes suivantes :</p>
     * <ul>
     *   <li>Lit le contenu du fichier chiffré ligne par ligne.</li>
     *   <li>Applique un déchiffrement par substitution modulaire en fonction
     *       de l'alphabet et de la clé.</li>
     *   <li>Écrit le contenu déchiffré dans un nouveau fichier avec
     *       l'extension ".csv".</li>
     * </ul>
     * 
     * @param cheminFichier chemin du fichier à décrypter.
     * @param cle clé de déchiffrement utilisée.
     * @param alphabet alphabet utilisé pour le déchiffrement.
     */
    public static void decrypter(String cheminFichier, String cle,
                                 String alphabet) {
        
        try {
            
            FileInputStream fileInputStream
            = new FileInputStream(cheminFichier);
            InputStreamReader inputStreamReader
            = new InputStreamReader(fileInputStream, "windows-1252");
            BufferedReader fluxLecture = new BufferedReader(inputStreamReader);
            
            String nomFichierDecrypte
            = cheminFichier.substring(0, cheminFichier.lastIndexOf("_crypté"))
              + ".csv";
            FileOutputStream fileOutputStream
            = new FileOutputStream(nomFichierDecrypte);
            OutputStreamWriter outputStreamWriter
            = new OutputStreamWriter(fileOutputStream, "windows-1252");
            BufferedWriter fluxEcriture
            = new BufferedWriter(outputStreamWriter);
            
            int modulo = alphabet.length();
            String ligne = fluxLecture.readLine();
            while (ligne != null) {
                
                for (int indiceLigne = 0;
                     indiceLigne < ligne.length();
                     indiceLigne++) {
                    
                    char lettreCrypte = ligne.charAt(indiceLigne);
                    int indexLettreCrypte = alphabet.indexOf(lettreCrypte);
                    int indexLettre
                    = (indexLettreCrypte
                       - alphabet.indexOf(cle.charAt(indiceLigne%cle.length()))
                       + modulo) % modulo;
                    char lettre = alphabet.charAt(indexLettre);
                    fluxEcriture.append(lettre);
                }
                ligne = fluxLecture.readLine();
                fluxEcriture.write("\n");
            }
            
            fluxLecture.close();
            fluxEcriture.close();
        } catch (IOException e) {
            // Ne rien faire
            e.printStackTrace();
        }
    }

    /**
     * Récupère les noms des fichiers de données.
     * 
     * @return un tableau contenant les noms des fichiers de données.
     */
    public static String[] getNomsFichiersDonnees() {
        return NOMS_FICHIERS_DONNEES;
    }

    /**
     * Récupère les noms des fichiers contenant les alphabets.
     * 
     * @return un tableau contenant les noms des fichiers d'alphabets.
     */
    public static String[] getNomsFichiersAlphabet() {
        return NOMS_FICHIERS_ALPHABET;
    }

    /**
     * Récupère les noms des fichiers utilisés pour les envois.
     * 
     * @return un tableau contenant les noms des fichiers d'envois.
     */
    public static String[] getNomsFichiersEnvois() {
        return NOMS_FICHIERS_ENVOIS;
    }
    
    /**
     * Supprime tous les fichiers de données.
     */
    public static void supprimerFichiersDonnees() {
        
        for (String nomFichier : NOMS_FICHIERS_DONNEES) {
            
            try {
                Files.deleteIfExists(Path.of(nomFichier));
            } catch (IOException e) {
                // Ne rien faire
            }
        }
    }
    
    /**
     * Supprime tous les fichiers d'alphabets.
     */
    public static void supprimerFichiersAlphabet() {
        
        for (String nomFichier : NOMS_FICHIERS_ALPHABET) {
            
            try {
                Files.deleteIfExists(Path.of(nomFichier));
            } catch (IOException e) {
                // Ne rien faire
            }
        }
    }

    /**
     * Supprime tous les fichiers d'envois.
     */
    public static void supprimerFichiersEnvois() {
        
        for (String nomFichier : NOMS_FICHIERS_ENVOIS) {
            
            try {
                Files.deleteIfExists(Path.of(nomFichier));
            } catch (IOException e) {
                // Ne rien faire
            }
        }
    }
    
}
