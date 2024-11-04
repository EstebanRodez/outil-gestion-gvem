/**
 * CryptageVigenere.java
 * 25 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

/**
 * TODO commenter le fonctionnement
 */
public class CryptageVigenere {
    
    private final String key;
    private static final String CRYPTED_IDENTIFIER = "CRYPTED";

    /**
     * TODO commenter l'état initial
     * @param key
     */
    public CryptageVigenere(String key) {
        this.key = key;
    }

    // Méthode pour chiffrer un texte avec identifiant
    /**
     * TODO commenter le rôle de la méthode
     * @param text
     * @return le texte crypté
     */
    public String cryptage(String text) {
        StringBuilder textCrypter = new StringBuilder(CRYPTED_IDENTIFIER + "\n");
        int keyLength = key.length();

        for (int i = 0; i < text.length(); i++) {
            char charText = text.charAt(i);
            char charKey = key.charAt(i % keyLength);
            char encryptedChar = (char) ((charText + charKey) % 256);
            textCrypter.append(encryptedChar);
        }

        return textCrypter.toString();
    }
}
