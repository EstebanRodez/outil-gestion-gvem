package application.utilitaire;

/**
 * TODO commenter le fonctionnement
 */
public class DecryptageVigenere {
    private final String key;
    private static final String CRYPTED_IDENTIFIER = "CRYPTED";

    /**
     * TODO commenter l'état initial
     * @param key
     */
    public DecryptageVigenere(String key) {
        this.key = key;
    }

    // Méthode pour déchiffrer un texte
    /**
     * TODO commenter le rôle de la méthode
     * @param textCrypter
     * @return le texte décrypté
     */
    public String decrypt(String textCrypter) {
        if (!textCrypter.startsWith(CRYPTED_IDENTIFIER)) {
            throw new IllegalArgumentException("Le fichier n'est pas crypté avec Vigenère.");
        }

        StringBuilder textDecrypter = new StringBuilder();
        int keyLength = key.length();
        textCrypter = textCrypter.substring(CRYPTED_IDENTIFIER.length() + 1); // Retirer l'identifiant

        for (int i = 0; i < textCrypter.length(); i++) {
            char charEncrypted = textCrypter.charAt(i);
            char charKey = key.charAt(i % keyLength);
            char decryptedChar = (char) ((charEncrypted - charKey + 256) % 256);
            textDecrypter.append(decryptedChar);
        }

        return textDecrypter.toString();
    }
}
