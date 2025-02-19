/*
 * CritereFiltre.java                           
 * 29 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

import java.time.LocalDate;

/**
 * Représente les critères de filtrage pour les visites.
 * Elle contient des informations sur le type d'exposition, le conférencier,
 * l'exposition, ainsi que les dates de début et de fin, et les horaires.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class CritereFiltreVisite {
    
    private boolean expositionTemporaire;
    private boolean expositionPermanente;
    private boolean estInterne;
    private boolean externe;
    
    private String conferencier;
    private String exposition;
    
    private LocalDate dateDebut;
    private LocalDate dateFin;
    
    private int horaireDebut;
    private int horaireFin;

    /**
     * Récupère si l'exposition est permanente ou non.
     * @return true si l'exposition est permanente sinon false
     */
    public boolean getExpositionPermanente() { 
        return expositionPermanente; 
    }
    
    /**
     * Récupère si l'exposition est temporaire ou non.
     * @return true si l'exposition est temporaire sinon false
     */
    public boolean getExpositionTemporaire() { 
        return expositionTemporaire; 
    }
    
    /**
     * Définit si l'exposition est temporaire ou non.
     * @param expositionTemporaire si l'exposition est temporaire
     */
    public void setExpositionTemporaire(boolean expositionTemporaire) {
        this.expositionTemporaire = expositionTemporaire;
        this.expositionPermanente = false;
    }
    
    /**
     * Définit si l'exposition est permanente ou non.
     * @param expositionPermanente si l'exposition est permanente
     */
    public void setExpositionPermanente(boolean expositionPermanente) {
        this.expositionPermanente = expositionPermanente;
        this.expositionTemporaire = false;
    }
    
    /**
     * Récupère si le conférencier est interne ou non.
     * @return true si le conférencier est interne sinon false
     */
    public boolean getInterne() { 
        return estInterne; 
    }
    
    /**
     * Récupère si le conférencier est non interne ou non.
     * @return true si le conférencier est non interne sinon false
     */
    public boolean getExterne() { 
        return externe; 
    }
    
    /**
     * Définit si le conférencier est interne ou non.
     * @param estInterne si le conférencier est interne.
     */
    public void setEstInterne(boolean estInterne) {
        this.estInterne = estInterne;
        this.externe = false;
    }
    
    /**
     * Définit si le conférencier est externe ou non.
     * @param externe si le conférencier est interne.
     */
    public void setExterne(boolean externe) {
        this.externe = externe;
        this.estInterne = false;
    }

    /**
     * Récupère le nom du conférencier.
     * @return le nom du conférencier
     */
    public String getConferencier() { 
        return conferencier; 
    }
            
    /**
     * Définit le nom du conférencier.
     * @param conferencier le nom du conférencier à définir
     */
    public void setConferencier(String conferencier) { 
        this.conferencier = conferencier; 
    }
    
    /**
     * Récupère le nom de l'exposition.
     * @return le nom de l'exposition
     */
    public String getExposition() { 
        return exposition; 
    }
            
    /**
     * Définit le nom de l'exposition.
     * @param exposition le nom de l'exposition à définir
     */
    public void setExposition(String exposition) { 
        this.exposition = exposition; 
    }

    /**
     * Récupère la date de début.
     * @return la date de début
     */
    public LocalDate getDateDebut() { 
        return dateDebut; 
    }
    
    /**
     * Définit la date de début.
     * @param dateDebut la date de début à définir
     */
    public void setDateDebut(LocalDate dateDebut) { 
        this.dateDebut = dateDebut; 
    }

    /**
     * Récupère la date de fin.
     * @return la date de fin
     */
    public LocalDate getDateFin() { 
        return dateFin; 
    }
    
    /**
     * Définit la date de fin.
     * @param dateFin la date de fin à définir
     */
    public void setDateFin(LocalDate dateFin) { 
        this.dateFin = dateFin; 
    }
    
    /**
     * Récupère l'horaire de début.
     * @return l'horaire de début
     */
    public int getHoraireDebut() { 
        return horaireDebut; 
    }
    
    /**
     * Définit l'horaire de début.
     * @param horaireDebut l'horaire de début à définir
     */
    public void setHoraireDebut(int horaireDebut) { 
        this.horaireDebut = horaireDebut; 
    }
    
    /**
     * Récupère l'horaire de fin.
     * @return l'horaire de fin
     */
    public int getHoraireFin() { 
        return horaireFin; 
    }
    
    /**
     * Définit l'horaire de fin.
     * @param horaireFin l'horaire de fin à définir
     */
    public void setHoraireFin(int horaireFin) { 
        this.horaireFin = horaireFin; 
    }
}