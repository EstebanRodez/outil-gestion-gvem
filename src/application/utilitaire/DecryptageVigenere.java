package application.utilitaire;

public class DecryptageVigenere {
    private final String key;
    private static final String CRYPTED_IDENTIFIER = "CRYPTED";

    public DecryptageVigenere(String key) {
        this.key = key;
    }

    // Méthode pour déchiffrer un texte
    public String decrypt(String encryptedText) {
        if (!encryptedText.startsWith(CRYPTED_IDENTIFIER)) {
            throw new IllegalArgumentException("Le fichier n'est pas crypté avec Vigenère.");
        }

        StringBuilder decryptedText = new StringBuilder();
        int keyLength = key.length();
        encryptedText = encryptedText.substring(CRYPTED_IDENTIFIER.length() + 1); // Retirer l'identifiant

        for (int i = 0; i < encryptedText.length(); i++) {
            char charEncrypted = encryptedText.charAt(i);
            char charKey = key.charAt(i % keyLength);
            char decryptedChar = (char) ((charEncrypted - charKey + 256) % 256);
            decryptedText.append(decryptedChar);
        }

        return decryptedText.toString();
    }
}
