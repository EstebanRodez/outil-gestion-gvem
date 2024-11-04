/*
 * DecryptageCesar.java
 * 24 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

/**
 * La classe DecryptageCesar permet de décrypter les données des fichiers CSV
 * en utilisant un décalage binaire.
 * @author Romain Augé
 * @version 1.1
 */
public class DecryptageCesar {
    private final int decalage;

    // Constructeur qui initialise le décalage utilisé pour décrypter
    /**
     * TODO commenter l'état initial
     * @param decalage
     */
    public DecryptageCesar(int decalage) {
        this.decalage = decalage;
    }

    // Méthode pour décrypter le texte binaire crypté
    /**
     * TODO commenter le rôle de la méthode
     * @param encryptedData
     * @return le texte décrypté
     */
    public String decrypt(String encryptedData) {
        StringBuilder texteDecrypte = new StringBuilder();

        // Parcourir chaque caractère du texte crypté
        for (char caractere : encryptedData.toCharArray()) {
            // Convertir le caractère en code binaire (8 bits)
            String chaineBinaire = String.format("%8s", Integer.toBinaryString(caractere)).replace(' ', '0');

            // Appliquer l'inverse du décalage à chaque bit
            StringBuilder cleDuCryptage = new StringBuilder();
            for (char bit : chaineBinaire.toCharArray()) {
                // Inverser le décalage du bit (0 ou 1)
                int bitOriginal = (bit - '0' - decalage + 2) % 2;
                cleDuCryptage.append(bitOriginal);
            }

            // Convertir la clé de cryptage décryptée en caractère
            char caractereDecrypte = (char) Integer.parseInt(cleDuCryptage.toString(), 2);
            texteDecrypte.append(caractereDecrypte);
        }

        return texteDecrypte.toString();
    }
}
