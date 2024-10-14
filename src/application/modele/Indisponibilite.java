/*
 * Indisponibilite.java                           
 * 10 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

import java.time.LocalDate;

/**
 * Représente une période d'indisponibilité avec une date de début
 * obligatoire et une date de fin optionnelle.<br>
 * Elle gère les périodes d'indisponibilité en définissant une 
 * plage temporelle pendant laquelle une personne est indisponible. 
 * Si la date de fin n'est pas renseignée, la période
 * d'indisponibilité sera basée uniquement sur la date du début
 * (= 1 jour d'indisponibilité).
 * @author Esteban Vroemen
 * @version 1.0
 */
public class Indisponibilite {
    
    private static final String ERREUR_DATE_DEBUT_INVALIDE =
    """
    Impossible de créer une indisponibilité.
    La référence de la date de début d'une indisponibilité
    ne doit pas être nulle.                 
    """;
    
    private static final String ERREUR_DATE_FIN_INVALIDE =
    """
    Impossible de créer une indisponibilité.
    La référence de la date de fin d'une indisponibilité ne doit pas être nulle.                
    """;
    
    private static final String ERREUR_DATE_FIN_INFERIEUR =
    """
    Impossible de créer une indisponibilité.
    La date de fin d'une indisponibilité ne doit pas être inférieure ou égale
    à la date de début.                
    """;
    
    private LocalDate dateDebut;
    
    private LocalDate dateFin;
    
    /**
     * Initialise une indisponibilité avec une date de début.
     * La date de fin est ignoré. La période d'indisponilité sera
     * sur un jour uniquement.
     * 
     * @param dateDebut La date à laquelle commence l'indisponibilité
     *                  ou le jour de l'indisponibilité
     * @throws IllegalArgumentException si la référence de la date
     *                                  de début est nulle
     */
    public Indisponibilite(LocalDate dateDebut) {
        
        if (dateDebut == null) {
            throw new IllegalArgumentException(ERREUR_DATE_DEBUT_INVALIDE);
        }
        
        this.dateDebut = dateDebut;
    }
    
    /**
     * Initialise une indisponibilité avec une date de début et une
     * date de fin.
     * 
     * @param dateDebut La date à laquelle commence l'indisponibilité
     * @param dateFin La date à laquelle se termine l'indisponibilité
     * @throws IllegalArgumentException si la référence de la date de
     *                                  début est nulle
     * @throws IllegalArgumentException si la référence de la date de
     *                                  fin est nulle
     * @throws IllegalArgumentException si la date de fin est
     *                                  inférieur ou égale à la date
     *                                  de début
     */
    public Indisponibilite(LocalDate dateDebut, LocalDate dateFin) {
        
        if (dateDebut == null) {
            throw new IllegalArgumentException(ERREUR_DATE_DEBUT_INVALIDE);
        }
        
        if (dateFin == null) {
            throw new IllegalArgumentException(ERREUR_DATE_FIN_INVALIDE);
        }
        
        if (dateFin.compareTo(dateDebut) <= 0) {
            throw new IllegalArgumentException(ERREUR_DATE_FIN_INFERIEUR);
        }
        
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    /**
     * Retourne la date de fin de l'indisponibilité.
     * @return dateFin La date de fin de l'indisponibilité, ou null
     *                 si aucune date de fin n'a été définie.
     */
    public LocalDate getDateFin() {
        return dateFin;
    }

    /**
     * Définit la date de fin de l'indisponibilité.
     * @param dateFin La date à laquelle se termine l'indisponibilité
     * @throws IllegalArgumentException si la référence de la date de
     *                                  fin est nulle
     * @throws IllegalArgumentException si la date de fin est
     *                                  inférieur ou égale à la date
     *                                  de début
     */
    public void setDateFin(LocalDate dateFin) {
        
        if (dateFin == null) {
            throw new IllegalArgumentException(ERREUR_DATE_FIN_INVALIDE);
        }
        
        if (dateFin.compareTo(dateDebut) <= 0) {
            throw new IllegalArgumentException(ERREUR_DATE_FIN_INFERIEUR);
        }
        
        this.dateFin = dateFin;
    }

    /**
     * Retourne la date de début de l'indisponibilité.
     * @return dateDebut La date de début de l'indisponibilité
     */
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    
    
}
