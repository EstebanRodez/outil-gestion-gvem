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
 */
public class CritereFiltre {
    
    private String typeExposition;
    private String conferencier;
    private String exposition;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int horaireDebut;
    private int horaireFin;

    /**
     * Récupère le type d'exposition.
     * @return le type d'exposition
     */
    public String getTypeExposition() { 
        return typeExposition; 
    }
    
    /**
     * Définit le type d'exposition.
     * @param typeExposition le type d'exposition à définir
     */
    public void setTypeExposition(String typeExposition) {
        this.typeExposition = typeExposition;
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