/*
 * Employe.java                           
 * 8 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

/**
 * Un membre du personnel ou employé du musée est identifié par son
 * nom et son prénom. Il n’y a pas d’homonymes. Un employé possède
 * éventuellement un numéro de téléphone codé sur 4 chiffres.
 * @version 1.0
 */
public class Employe {
    
    private static final String NUMTEL_DEFAUT = "Inconnu";
    
    private static final String ERREUR_IDENTIFIANT_INVALIDE =
    """
    Impossible de créer un employé.
    L'identifiant de l'employé ne doit pas être nul ou vide.                
    """;
    
    private static final String ERREUR_NOM_INVALIDE =
    """
    Impossible de créer un employé.
    Le nom de l'employé ne doit pas être nul ou vide.                
    """;
    
    private static final String ERREUR_NUMTEL_INVALIDE =
    """
    Impossible de créer un employé.
    Le numéro de téléphone de l'employé ne doit pas être nul ou vide.                
    """;
    
    private static final String ERREUR_NUMTEL_LONGUEUR_INVALIDE =
    """
    Impossible de créer un employé.
    Le numéro de téléphone de l'employé doit contenir uniquement 4 caractères.                
    """;
    
    private static final String ERREUR_NUMTEL_CARACTERE_INVALIDE =
    """
    Impossible de créer un employé.
    Le numéro de téléphone de l'employé doit contenir uniquement des chiffres.              
    """;
    
    private static final String ERREUR_PRENOM_INVALIDE =
    """
    Impossible de créer un employé.
    Le prénom de l'employé ne doit pas être nul ou vide.                
    """;
    
    private String identifiant;
    
    private String nom;
    
    private String prenom;
    
    private String numTel;
    
    /**
     * Crée un employé avec un identifiant, un nom et un prénom.
     * Le numéro de téléphone est initialisé par défaut.
     * 
     * @param identifiant l'identifiant unique de l'employé
     * @param nom le nom de l'employé
     * @param prenom le prénom de l'employé
     * @throws IllegalArgumentException si la référence de
     *                                  l'identifiant est nul ou
     *                                  l'identifiant est vide
     * @throws IllegalArgumentException si la référence du nom est
     *                                  nul ou le nom est vide
     * @throws IllegalArgumentException si la référence du prénom est
     *                                  nul ou le prénom est vide
     */
    public Employe(String identifiant, String nom, String prenom) {
        
        if (identifiant == null || identifiant.isBlank()) {
            throw new IllegalArgumentException(ERREUR_IDENTIFIANT_INVALIDE);
        }
        
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException(ERREUR_NOM_INVALIDE);
        }
        
        if (prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException(ERREUR_PRENOM_INVALIDE);
        }
        
        this.identifiant = identifiant.trim();
        this.nom = nom.trim();
        this.prenom = prenom.trim();
        this.numTel = NUMTEL_DEFAUT;
    }
    
    /**
     * Crée un employé avec un identifiant, un nom, un prénom et un
     * numéro de téléphone fournis en argument.
     * 
     * @param identifiant l'identifiant unique de l'employé
     * @param nom le nom de l'employé
     * @param prenom le prénom de l'employé
     * @param numTel le numéro de téléphone de l'employé
     * @throws IllegalArgumentException si la référence de
     *                                  l'identifiant est nul ou
     *                                  l'identifiant est vide
     * @throws IllegalArgumentException si la référence du nom est
     *                                  nul ou le nom est vide
     * @throws IllegalArgumentException si la référence du prénom est
     *                                  nul ou le prénom est vide
     * @throws IllegalArgumentException si la référence du numéro de
     *                                  téléphone est nul ou le
     *                                  numéro de téléphone est vide
     * @throws IllegalArgumentException si le numéro de téléphone ne
     *                                  contient pas uniquement 4
     *                                  caractères
     * @throws IllegalArgumentException si le numéro de téléphone ne
     *                                  contient pas uniquement des
     *                                  chiffres
     */
    public Employe(String identifiant, String nom, String prenom, 
                   String numTel) {
        
        if (identifiant == null || identifiant.isBlank()) {
            throw new IllegalArgumentException(ERREUR_IDENTIFIANT_INVALIDE);
        }
        
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException(ERREUR_NOM_INVALIDE);
        }
        
        if (prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException(ERREUR_PRENOM_INVALIDE);
        }
        
        if (numTel == null || numTel.isBlank()) {
            throw new IllegalArgumentException(ERREUR_NUMTEL_INVALIDE);
        }
        
        if (numTel.length() != 4) {
            throw new IllegalArgumentException(ERREUR_NUMTEL_LONGUEUR_INVALIDE);
        }
        
        if (!numTel.matches("(\\d){4}")) {
            throw new IllegalArgumentException(
                    ERREUR_NUMTEL_CARACTERE_INVALIDE);
        }
        
        this.identifiant = identifiant.trim();
        this.nom = nom.trim();
        this.prenom = prenom.trim();
        this.numTel = numTel.trim();
    }
    
    /**
     * Récupère l'identifiant de l'employé.
     * @return l'identifiant de l'employé
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * Récupère le numéro de téléphone de l'employé.
     * @return numTel le numéro de téléphone de l'employé
     */
    public String getNumTel() {
        return numTel;
    }

    /**
     * Définit le numéro de téléphone du client.<br>
     * Le numéro de téléphone fourni devra contenir uniquement 4
     * chiffres. Si ce n'est pas le cas, une exception sera levée.
     * 
     * @param numTel le numéro de téléphone à définir
     * @throws IllegalArgumentException si la référence du numéro de
     *                                  téléphone est nul ou le
     *                                  numéro de téléphone est vide
     * @throws IllegalArgumentException si le numéro de téléphone ne
     *                                  contient pas uniquement 4
     *                                  caractères
     * @throws IllegalArgumentException si le numéro de téléphone ne
     *                                  contient pas uniquement des
     *                                  chiffres
     */
    public void setNumTel(String numTel) {
        
        if (numTel == null || numTel.isBlank()) {
            throw new IllegalArgumentException(ERREUR_NUMTEL_INVALIDE);
        }
        
        if (numTel.length() != 4) {
            throw new IllegalArgumentException(ERREUR_NUMTEL_LONGUEUR_INVALIDE);
        }
        
        if (!numTel.matches("(\\d){4}")) {
            throw new IllegalArgumentException(
                    ERREUR_NUMTEL_CARACTERE_INVALIDE);
        }
        
        this.numTel = numTel.trim();
    }

    /**
     * Récupère le nom de l'employé.
     * @return nom le nom de l'employé
     */
    public String getNom() {
        return nom;
    }

    /**
     * Récupère le prénom de l'employé
     * @return prenom le prénom de l'employé
     */
    public String getPrenom() {
        return prenom;
    }

}
