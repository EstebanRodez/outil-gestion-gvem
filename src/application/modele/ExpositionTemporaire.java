/*
 * ExpositionTemporaire.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

import java.time.LocalDate;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class ExpositionTemporaire extends Exposition {
    
    private LocalDate dateDebut;
    
    private LocalDate dateFin;
    
    /**
     * TODO commenter l'état initial
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
     * @param dateDebut
     * @param dateFin
     */
    public ExpositionTemporaire(String identifiant, String intitule,
                                int periodeDebut, int periodeFin, int nbOeuvre,
                                String[] motsCles, String resume, 
                                LocalDate dateDebut, LocalDate dateFin) {
        
        super(identifiant, intitule, periodeDebut, periodeFin, nbOeuvre,
              motsCles, resume);
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
