/*
 * Vigenère.java                           
 * 15 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * @author Esteban Vroemen
 * @version 1.0
 */
public class Vigenere {
    
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
                    if (!lettres.contains(lettre)) {
                        lettres.add(lettre);
                        chaineAlphabet.append(lettre);
                    }
                }
                ligne = fluxLecture.readLine();
            }
            
            fluxLecture.close();
        } catch (IOException e) {
            // Ne rien faire
            e.printStackTrace();
        }
        
        return chaineAlphabet.toString();
    }
}
