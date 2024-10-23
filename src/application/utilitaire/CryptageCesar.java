/*
 * Cryptage.java
 * 23 oct. 2024
 * IUT de rodez pas de copyright
 */
package application.utilitaire;


/**
 * La classe Cryptage permet de crypter les données des fichiers CSV
 * que l'on souhaite envoyer
 * @author Esteban Vroemen
 * @version 1.0
 */
public class CryptageCesar {
	private final int decalage;

    // Constructeur qui initialise le décalage
    public CryptageCesar(int decalage) {
        this.decalage = decalage;
    }

    // Méthode pour crypter le texte
    public String encrypt(String data) {
        StringBuilder encrypted = new StringBuilder();
        
        // Parcourir chaque caractère du texte
        for (char ch : data.toCharArray()) {
            // Vérifier si le caractère est une lettre
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                // Appliquer le décalage et gérer le retour à zéro
                ch = (char) ((ch + decalage - base) % 26 + base);
            }
            encrypted.append(ch);
        }
        
        return encrypted.toString();
    }
}

