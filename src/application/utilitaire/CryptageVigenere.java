package application.utilitaire;

public class CryptageVigenere {
    private final String key;
    private static final String CRYPTED_IDENTIFIER = "CRYPTED";

    public CryptageVigenere(String key) {
        this.key = key;
    }

    // Méthode pour chiffrer un texte avec identifiant
    public String encrypt(String text) {
        StringBuilder encryptedText = new StringBuilder(CRYPTED_IDENTIFIER + "\n");
        int keyLength = key.length();

        for (int i = 0; i < text.length(); i++) {
            char charText = text.charAt(i);
            char charKey = key.charAt(i % keyLength);
            char encryptedChar = (char) ((charText + charKey) % 256);
            encryptedText.append(encryptedChar);
        }

        return encryptedText.toString();
    }
}
