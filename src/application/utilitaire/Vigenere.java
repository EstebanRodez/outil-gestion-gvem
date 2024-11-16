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
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class Vigenere {
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param donneeSecrete
     * @param alphabet 
     * @return la clé de chiffrement
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
     * TODO commenter le rôle de cette méthode (SRP)
     * @param cheminFichier
     * @return l'alphabet du fichier
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
