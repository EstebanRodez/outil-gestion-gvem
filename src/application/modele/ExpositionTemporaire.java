/*
 * ExpositionTemporaire.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

import java.time.LocalDate;

/**
 * Une exposition temporaire sera décrite par :<br>
 * - un intitulé,<br>
 * - la période que ses oeuvres recouvrent (deux années),<br>
 * - le nombre d’œuvres,<br>
 * - des mots-clés (au plus 10)<br>
 * - et un résumé constitué de quelques lignes de texte.<br>
 * - une date de début de l'exposition<br>
 * - une date de fin de l'exposition<br>
 * Par exemple, l'exposition "Les paysages impressionnistes" 
 * regroupera 20 tableaux datés de 1880 à 1895 et sera décrite par
 * les mots-clés : paysage, impressionnisme, Monet, Pissarro, Sysley
 * et Signac. Le résumé pourrait être « Très belle exposition mettant
 * en évidence les approches différentes de grands peintres du
 * mouvement impressionniste ». Pour une exposition temporaire,
 * on connaîtra, en plus des éléments précédents, la date de début
 * et la date de fin de celle-ci. Par exemple, du 17 mars 2025
 * au 21 juin 2025.
 */
public class ExpositionTemporaire extends Exposition {
    
    private static final String ERREUR_DATE_DEBUT_INVALIDE =
    """
    Impossible de créer une exposition temporaire.
    La référence de la date de début d'une exposition temporaire
    ne doit pas être nulle.                 
    """;
    
    private static final String ERREUR_DATE_FIN_INVALIDE =
    """
    Impossible de créer une exposition temporaire.
    La référence de la date de fin d'une exposition temporaire ne doit pas être
    nulle.                
    """;
    
    private static final String ERREUR_DATE_FIN_INFERIEUR =
    """
    Impossible de créer une exposition temporaire.
    La date de fin d'une exposition temporaire ne doit pas être inférieure ou
    égale à la date de début.                
    """;
    
    private LocalDate dateDebut;
    
    private LocalDate dateFin;
    
    /**
     * Initialise une nouvelle exposition temporaire avec un
     * identifiant, un intitulé, une période avec une année de début
     * et une année de fin, un nombre d'oeuvres, une liste de mots
     * clés, un résumé, une date de début et une date de fin.
     * 
     * @param identifiant l'identifiant/code de l'exposition
     *                    temporaire
     * @param intitule le nom ou la désignation de l'exposition
     *                 temporaire
     * @param periodeDebut l'année de début que les oeuvres de
     *                     l'exposition recouvrent
     * @param periodeFin l'année de fin que les oeuvres de
     *                   l'exposition recouvrent
     * @param nbOeuvre le nombre d'oeuvre présentes dans l'exposition
     * @param motsCles liste de mots clés permettant de retrouver
     *                 l'exposition
     * @param resume le résumé de l'exposition
     * @param dateDebut la date de début de l'exposition
     * @param dateFin la date de fin de de l'exposition
     */
    public ExpositionTemporaire(String identifiant, String intitule,
                                int periodeDebut, int periodeFin, int nbOeuvre,
                                String[] motsCles, String resume, 
                                LocalDate dateDebut, LocalDate dateFin) {
        
        super(identifiant, intitule, periodeDebut, periodeFin, nbOeuvre,
              motsCles, resume);
        
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
     * Récupère la date de début de l'exposition temporaire
     * @return dateDebut la date de début de l'exposition temporaire
     */
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    /**
     * Récupère la date de fin de l'exposition temporaire
     * @return dateFin la date de fin de l'exposition temporaire
     */
    public LocalDate getDateFin() {
        return dateFin;
    }

}
