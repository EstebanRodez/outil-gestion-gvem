/*
 * CryptageCesar.java
 * 23 oct. 2024
 * IUT de Rodez pas de copyright
 */
package application.utilitaire;

/**
 * La classe CryptageCesar permet de crypter les données des fichiers CSV
 * en appliquant un décalage binaire à chaque caractère du fichier.
 * @author Romain Augé
 * @version 1.1
 */
public class CryptageCesar {
    private final int decalage;

    // Constructeur qui initialise le décalage pour le cryptage
    /**
     * TODO commenter l'état initial
     * @param decalage
     */
    public CryptageCesar(int decalage) {
        this.decalage = decalage;
    }

    // Méthode pour crypter le texte en modifiant les bits de chaque caractère
    /**
     * TODO commenter le rôle de la méthode
     * @param texte
     * @return le texte crypté
     */
    public String encrypt(String texte) {
        StringBuilder texteCrypte = new StringBuilder();

        // Parcourir chaque caractère du texte
        for (char caractere : texte.toCharArray()) {
            // Convertir le caractère en son code binaire (8 bits)
            String chaineBinaire = String.format("%8s", Integer.toBinaryString(caractere)).replace(' ', '0');

            // Appliquer le décalage binaire à chaque bit
            StringBuilder cleDuCryptage = new StringBuilder();
            for (char bit : chaineBinaire.toCharArray()) {
                // Calculer le bit décalé
                int bitDecale = (bit - '0' + decalage) % 2; // Appliquer le décalage binaire (0 ou 1)
                cleDuCryptage.append(bitDecale);
            }

            // Convertir la clé de cryptage (bits décalés) en caractère crypté
            char caractereCrypte = (char) Integer.parseInt(cleDuCryptage.toString(), 2);
            texteCrypte.append(caractereCrypte);
        }

        return texteCrypte.toString();
    }
}
