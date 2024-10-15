/*
 * Conferencier.java                           
 * 8 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

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
 * @version 1.0
 */
public class Conferencier{
	
	private static final String ERREUR_NOM_INVALIDE =
		    """
		    Impossible de créer un employé.
		    Le nom de l'employé ne doit pas être nul ou vide.                
		    """;
	
	private static final String ERREUR_PRENOM_INVALIDE =
		    """
		    Impossible de créer un employé.
		    Le prénom de l'employé ne doit pas être nul ou vide.                
		    """;
	private static final String ERREUR_SPECIALITE_INVALIDE =
		    """
		    Impossible de créer unconférencier.
		    La liste de spécialités du conférencier ne doit pas être nul, vide
		    ou superieure à 6                
		    """;
	private static final String ERREUR_NUMTEL_INVALIDE =
		    """
		    Impossible de créer un employé.
		    Le numéro de téléphone de l'employé ne doit pas être nul ou vide.                
		    """;
	private static final String ERREUR_NUMTEL_CARACTERE_INVALIDE =
		    """
		    Impossible de créer un employé.
		    Le numéro de téléphone de l'employé doit contenir uniquement des chiffres.              
		    """;
	private static final String ERREUR_NUMTEL_LONGUEUR_INVALIDE =
		    """
		    Impossible de créer un employé.
		    Le numéro de téléphone de l'employé doit contenir uniquement 4 caractères.                
		    """;
	
	 private String nom;
	    
	 private String prenom;
	    
	 private String[] specialite;
	 
	 private String numTel;
	 
	 private boolean estInterne;
	 
	 private Indisponibilite[] indisponibilites;

	 /**
      * Crée un conférencier avec un nom et un prénom.
      * Le numéro de téléphone est initialisé par défaut.
      * 
      * @param identifiant l'identifiant unique de conférencier
      * @param nom le nom de conférencier
      * @param prenom le prénom de conférencier
      * @param specialite la liste des specialités du conférencier
      * @param numTel numéro de téléphone du conférencier
      * @throws IllegalArgumentException si la référence du nom est
      *                                  nul ou le nom est vide
      * @throws IllegalArgumentException si la référence du prénom est
      *                                  nul ou le prénom est vide
      * @throws IllegalArgumentException si la référence de la liste des 
      * 								 spécialités est nul, vide ou 
      * 								 superieure à 6
      * @throws IllegalArgumentException si la référence du numéro de téléphone
      * 								 est nul, vide, contient autre chose 
      * 								 que des chiffres ou contient plus ou
      * 								 moins de 4 caractères
      */
	public Conferencier(String nom, String prenom, String[] specialite,
			String numTel, boolean estInterne, Indisponibilite[] indisponibilites) {
		if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException(ERREUR_NOM_INVALIDE);
        }
        
        if (prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException(ERREUR_PRENOM_INVALIDE);
        }
        // TODO vérifier que deux conférenciers (nom, prenom) ne soient pas similaire
        
        if (specialite== null || specialite.length == 0 || specialite.length > 6) {
            throw new IllegalArgumentException(ERREUR_SPECIALITE_INVALIDE);   
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
        
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.numTel = numTel;
        this.estInterne = estInterne;
        this.indisponibilites = indisponibilites;
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
     * @return les spécialités du conférencier.
     */
    public String[] getSpecialite() {
        return specialite;
    }
    
    /**
     * Récupère le numéro de téléphone du conférencier.
     * @return le numéro de téléphone du conférencier.
     */
    public String getNumTel() {
        return numTel;
    }
    
    /**
     * Récupère si le conférencier est interne ou non
     * @return est interne ou non
     */
    public boolean getEstInterne() {
    	return estInterne;
    }
    
    /**
     * Récupère la liste des indisponibilités du conférencier
     * @return la liste des indisponibilités du conférencier
     */
    public Indisponibilite[] getIndisponibilites() {
    	return indisponibilites;
    }
}