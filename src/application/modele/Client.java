/*
 * Client.java                           
 * 8 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

/**
 * Un client est représenté avec un intitulé et un numéro de 
 * téléphone. Un client a forcément lié à une visite qu'il a réservé.
 * L’intitulé est un texte libre qui pourrait être un nom et un 
 * prénom et/ou le nom d’un organisme (par exemple 
 * « M.Jacques Dupont » ou « Les amis des musées de Rodez » ou encore
 * « M. Jacques Dupont pour les amis des musées de Rodez »)
 * @author Esteban Vroemen
 * @version 1.0
 */
public class Client {
    
    private static final String ERREUR_INTITULE_INVALIDE =
    """
    Impossible de créer un client.
    L'intitulé du client ne doit pas être nul ou vide.                
    """;
    
    private static final String ERREUR_NUMTEL_INVALIDE =
    """
    Impossible de créer un client.
    Le numéro de téléphone du client ne doit pas être nul ou vide.              
    """;
    
    private static final String ERREUR_NUMTEL_LONGUEUR_INVALIDE =
    """
    Impossible de créer un client.
    Le numéro de téléphone du client doit contenir 9 caractères uniquement.              
    """;
    
    private static final String ERREUR_NUMTEL_CARACTERE_INVALIDE =
    """
    Impossible de créer un client.
    Le numéro de téléphone du client doit contenir uniquement des chiffres.              
    """;

    private String intitule;
    
    private String numTel;
    
    /**
     * Initialise un nouveau client avec un intitulé et un numéro de
     * téléphone.
     * 
     * @param intitule le nom ou la désignation du client
     * @param numTel le numéro de téléphone du client
     * @throws IllegalArgumentException si la référence de l'intitulé
     *                                  est nul ou l'intitulé est vide
     * @throws IllegalArgumentException si la référence du numéro de
     *                                  téléphone est nul ou le 
     *                                  numéro de téléphone est vide
     * @throws IllegalArgumentException si le numéro de téléphone ne
     *                                  contient pas uniquement 9 
     *                                  caractères
     * @throws IllegalArgumentException si le numéro de téléphone ne
     *                                  contient pas uniquement des
     *                                  chiffres
     */
    public Client(String intitule, String numTel) {
        
        if (intitule == null || intitule.isBlank()) {
            throw new IllegalArgumentException(ERREUR_INTITULE_INVALIDE);
        }
        
        if (numTel == null || numTel.isBlank()) {
            throw new IllegalArgumentException(ERREUR_NUMTEL_INVALIDE);
        }
        
        if (numTel.trim().length() != 9) {
            throw new IllegalArgumentException(
                    ERREUR_NUMTEL_LONGUEUR_INVALIDE);
        }
        
        if (!numTel.matches("(\\d){9}")) {
            throw new IllegalArgumentException(
                    ERREUR_NUMTEL_CARACTERE_INVALIDE);
        }
        
        this.intitule = intitule.trim();
        this.numTel = numTel.trim();
    }
    
    /**
     * Récupère l'intitulé du client
     * @return l'intitulé du client
     */
    public String getIntitule() {
        return intitule;
    }
    
    /**
     * Récupère le numéro de téléphone du client
     * @return le numéro de téléphone du client
     */
    public String getNumTel() {
        return numTel;
    }

    /* non javadoc - @see java.lang.Object#equals(java.lang.Object) */
    @Override
    public boolean equals(Object objet) {

        if (objet instanceof Client) {
            
            Client client = (Client) objet;
            return this.getIntitule().equals(client.getIntitule())
                   && this.getNumTel().equals(client.getNumTel());
        }
        
        return false;
    }

    /* non javadoc - @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return getIntitule() + " " + getNumTel();
    }
    
    
}
