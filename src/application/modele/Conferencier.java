/*
 * Conferencier.java                           
 * 8 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

import java.util.Arrays;

/**
 * Tout conférencier est décrit par son nom, son prénom et sa
 * spécialité. Cette dernière est spécifiée par une liste de
 * mots-clés (au plus 6). Un numéro de téléphone est associé au
 * conférencier. Il ne peut y avoir d’homonyme à la fois sur le nom 
 * et le prénom. Un conférencier employé par le musée est présent 
 * tous les jours où le musée est ouvert, sauf lorsqu'il est en 
 * congés ou arrêt de travail. A tout conférencier employé du musée
 * est donc associée une liste d’indisponibilités. On ne fera pas de
 * nuance entre les raisons de l’indisponibilité. Une indisponibilité
 * est soit un jour isolé, soit une période délimitée par une date de
 * début et une date de fin. Les conférenciers extérieurs peuvent
 * aussi avoir des indisponibilités connues à l’avance et décrites
 * sous la même forme que pour les employés. Les conférenciers
 * internes et externes sont clairement différenciés.
 * @author Romain Augé
 * @author Esteban Vroemen
 * @version 1.0
 */
public class Conferencier {
    
    private static final String ERREUR_IDENTIFIANT_INVALIDE =
    """
    Impossible de créer un conférencier.
    L'identifiant du conférencier ne doit pas être nul ou vide.                
    """;
    
    private static final String ERREUR_NOM_INVALIDE =
    """
    Impossible de créer un conférencier.
    Le nom du conférencier ne doit pas être nul ou vide.                
    """;

    private static final String ERREUR_PRENOM_INVALIDE =
    """
    Impossible de créer un conférencier.
    Le prénom du conférencier ne doit pas être nul ou vide.                
    """;
    
    private static final String ERREUR_SPECIALITE_INVALIDE =
    """
    Impossible de créer un conférencier.
    La liste de spécialités du conférencier ne doit pas être nul, vide
    ou sa taille ne doit pas être supérieure à 6.           
    """;
    
    private static final String ERREUR_NUMTEL_INVALIDE =
    """
    Impossible de créer un conférencier.
    Le numéro de téléphone du conférencier ne doit pas être nul ou vide.                
    """;
    
    private static final String ERREUR_NUMTEL_CARACTERE_INVALIDE =
    """
    Impossible de créer un conférencier.
    Le numéro de téléphone du conférencier doit contenir
    uniquement des chiffres.              
    """;
    
    private static final String ERREUR_NUMTEL_LONGUEUR_INVALIDE =
    """
    Impossible de créer un conférencier.
    Le numéro de téléphone du conférencier doit contenir
    uniquement 9 caractères.                
    """;
    
    private static final String ERREUR_INDISPONIBILITES_INVALIDE =
    """
    Impossible de créer un conférencier.
    La référence de la liste des indisponibilités d'un conférencier
    ne doit pas être nulle.                
    """;
    
    private static final String ERREUR_INDISPONIBILITES_VALEURS_INVALIDES =
    """
    Impossible de créer un conférencier.
    Une valeur de la liste des indisponibilités du conférencier
    n'est pas valide.                
    """;
    
    private String identifiant;
    
    private String nom;

    private String prenom;

    private String[] specialites;

    private String numTel;

    private boolean estInterne;

    private Indisponibilite[] indisponibilites;
    
    /**
     * Crée un conférencier avec un nom, un prénom, un numéro de
     * téléphone, une liste de ses spécialités et s'il est interne
     * ou non.
     * 
     * @param identifiant l'identifiant unique de conférencier
     * @param nom le nom du conférencier
     * @param prenom le prénom du conférencier
     * @param specialites la liste des spécialités du conférencier
     * @param numTel le numéro de téléphone du conférencier
     * @param estInterne si la conférencier est interne ou non
     * @throws IllegalArgumentException si la référence de
     *                                  l'identifiant est nulle ou
     *                                  l'identifiant est vide
     * @throws IllegalArgumentException si la référence du nom est
     *                                  nulle ou le nom est vide
     * @throws IllegalArgumentException si la référence du prénom est
     *                                  nulle ou le prénom est vide
     * @throws IllegalArgumentException si la référence de la liste 
     *                                  des spécialités est nulle,
     *                                  la liste est vide
     *                                  ou sa taille est supérieur
     *                                  à 6
     * @throws IllegalArgumentException si la référence du numéro de
     *                                  téléphone est nulle, le
     *                                  numéro de téléphone est vide,
     *                                  contient autre chose que des
     *                                  chiffres ou ne contient pas 9
     *                                  caractères
     */
    public Conferencier(String identifiant, String nom, String prenom,
                        String[] specialites, String numTel,
                        boolean estInterne) {
        
        if (identifiant == null || identifiant.isBlank()) {
            throw new IllegalArgumentException(ERREUR_IDENTIFIANT_INVALIDE);
        }
        
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException(ERREUR_NOM_INVALIDE);
        }

        if (prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException(ERREUR_PRENOM_INVALIDE);
        }

        if (specialites == null || specialites.length == 0 
            || specialites.length > 6) {
            throw new IllegalArgumentException(ERREUR_SPECIALITE_INVALIDE);   
        }
        if (numTel == null || numTel.isBlank()) {
            throw new IllegalArgumentException(ERREUR_NUMTEL_INVALIDE);
        }

        if (numTel.length() != 9) {
            throw new IllegalArgumentException(ERREUR_NUMTEL_LONGUEUR_INVALIDE);
        }

        if (!numTel.matches("(\\d){9}")) {
            throw new IllegalArgumentException(
                    ERREUR_NUMTEL_CARACTERE_INVALIDE);
        }

        this.identifiant = identifiant.trim();
        this.nom = nom.trim();
        this.prenom = prenom.trim();
        this.specialites = specialites;
        this.numTel = numTel.trim();
        this.estInterne = estInterne;
        this.indisponibilites = null;
    }
    
    /**
     * Crée un conférencier avec un nom, un prénom, un numéro de
     * téléphone, une liste de ses spécialités, s'il est interne ou
     * non et une liste de des indisponibilités du conférencier.
     * 
     * @param identifiant l'identifiant unique de conférencier
     * @param nom le nom du conférencier
     * @param prenom le prénom du conférencier
     * @param specialites la liste des spécialités du conférencier
     * @param numTel le numéro de téléphone du conférencier
     * @param estInterne si la conférencier est interne ou non
     * @param indisponibilites la liste de ses indisponibités
     *                         potentielles
     * @throws IllegalArgumentException si la référence de
     *                                  l'identifiant est nulle ou
     *                                  l'identifiant est vide
     * @throws IllegalArgumentException si la référence du nom est
     *                                  nulle ou le nom est vide
     * @throws IllegalArgumentException si la référence du prénom est
     *                                  nulle ou le prénom est vide
     * @throws IllegalArgumentException si la référence de la liste 
     * 					des spécialités est nulle,
     *                                  la liste est vide
     *                                  ou sa taille est supérieur
     *                                  à 6
     * @throws IllegalArgumentException si la référence du numéro de
     * 					téléphone est nulle, le
     *                                  numéro de téléphone est vide,
     * 					contient autre chose que des
     * 					chiffres ou ne contient pas 9
     *                                  caractères
     * @throws IllegalArgumentException si la référence de la liste
     *                                  des indisponibilités est
     *                                  nulle
     * @throws IllegalArgumentException si une valeur de la liste des
     *                                  indisponibilités est invalide
     */
    public Conferencier(String identifiant, String nom, String prenom,
                        String[] specialites, String numTel, boolean estInterne,
                        Indisponibilite[] indisponibilites) {
        
        if (identifiant == null || identifiant.isBlank()) {
            throw new IllegalArgumentException(ERREUR_IDENTIFIANT_INVALIDE);
        }
        
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException(ERREUR_NOM_INVALIDE);
        }

        if (prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException(ERREUR_PRENOM_INVALIDE);
        }

        if (specialites == null || specialites.length == 0 
            || specialites.length > 6) {
            throw new IllegalArgumentException(ERREUR_SPECIALITE_INVALIDE);   
        }
        if (numTel == null || numTel.isBlank()) {
            throw new IllegalArgumentException(ERREUR_NUMTEL_INVALIDE);
        }

        if (numTel.length() != 9) {
            throw new IllegalArgumentException(ERREUR_NUMTEL_LONGUEUR_INVALIDE);
        }

        if (!numTel.matches("(\\d){9}")) {
            throw new IllegalArgumentException(
                    ERREUR_NUMTEL_CARACTERE_INVALIDE);
        }
        
        if (indisponibilites == null) {
            throw new IllegalArgumentException(
                    ERREUR_INDISPONIBILITES_INVALIDE);
        }
        
        /*
         * On vérifie la validité de chaque valeur contenue dans le
         * tableau des indisponibilités
         */
        for (Indisponibilite valeur : indisponibilites) {
            
            if (valeur == null) {
                throw new IllegalArgumentException(
                        ERREUR_INDISPONIBILITES_VALEURS_INVALIDES);
            }
        }
        
        this.identifiant = identifiant.trim();
        this.nom = nom.trim();
        this.prenom = prenom.trim();
        this.specialites = specialites;
        this.numTel = numTel.trim();
        this.estInterne = estInterne;
        this.indisponibilites = indisponibilites;
    }
    
    @Override
	public boolean equals(Object conferencierAComparer) {
    	if (this == conferencierAComparer) { // objet de même référence
    		 return true;
    	}
    	
    	// si conferencierAComparer n'est pas un objet de type Conferencier
    	 if ( ! (conferencierAComparer instanceof Conferencier)) {
    		 return false;
    	 }
    	 
    	// sinon : conferencierAComparer est un objet de type Conferencier
    	 Conferencier confAComparer = (Conferencier) conferencierAComparer;
    	 return identifiant.equals(confAComparer.identifiant) 
    			&& nom.equals(confAComparer.nom) 
    			&& prenom.equals(confAComparer.prenom) 
    			&& specialites.equals(confAComparer.specialites) 
    			&& numTel == confAComparer.numTel
    			&& estInterne
    			&& (indisponibilites == null || indisponibilites.equals(confAComparer.indisponibilites));
	}

	@Override
	public String toString() {
		// concaténation de la description héritée de la classe Conferencier
		return  "identifiant : " + identifiant + ", nom : " 
				+ nom + ", prenom : " + prenom + ", specialites : " 
				+ Arrays.toString(specialites) + ", numéro de téléphone : " + numTel 
				+ ", status(interne ou externe) : " + estInterne 
				+ ", liste des indisponibilites : " + indisponibilites;
	}

	/**
     * Récupère l'identifiant du conférencier.
     * @return l'identifiant du conférencier
     */
    public String getIdentifiant() {
        return identifiant;
    }
    
    /**
     * Récupère le nom du conférencier.
     * @return le nom du conférencier
     */
    public String getNom() {
        return nom;
    }

    /**
     * Récupère le prénom du conférencier.
     * @return le prénom du conférencier
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Récupère les spécialités du conférencier.
     * @return les spécialités du conférencier
     */
    public String[] getSpecialites() {
        return specialites;
    }

    /**
     * Récupère le numéro de téléphone du conférencier.
     * @return le numéro de téléphone du conférencier
     */
    public String getNumTel() {
        return numTel;
    }

    /**
     * Récupère si le conférencier est interne ou non.
     * @return est interne ou non
     */
    public boolean estInterne() {
        return estInterne;
    }

    /**
     * Récupère la liste des indisponibilités du conférencier.
     * @return la liste des indisponibilités du conférencier
     */
    public Indisponibilite[] getIndisponibilites() {
        return indisponibilites;
    }
}