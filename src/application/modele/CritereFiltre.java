/*
 * CritereFiltre.java                           
 * 29 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

import java.time.LocalDate;

/**
 * TODO commenter la responsabilité de cette class (SRP)
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
     * TODO commenter le rôle de cette méthode (SRP)
     * @return le type d'exposition
     */
    public String getTypeExposition() { 
        return typeExposition; 
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param typeExposition
     */
    public void setTypeExposition(String typeExposition) {
        this.typeExposition = typeExposition;
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return le nom du conférencier
     */
    
    public String getConferencier() { 
        return conferencier; 
    }
            
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param conferencier
     */
    public void setConferencier(String conferencier) { 
        this.conferencier = conferencier; 
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return le nom du conférencier
     */
    
    public String getExposition() { 
        return exposition; 
    }
            
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param exposition 
     */
    public void setExposition(String exposition) { 
        this.exposition = exposition; 
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return la date de debut 
     */
    public LocalDate getDateDebut() { 
        return dateDebut; 
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param dateDebut
     */
    public void setDateDebut(LocalDate dateDebut) { 
        this.dateDebut = dateDebut; 
        }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return la date de fin 
     */
    public LocalDate getDateFin() { 
        return dateFin; 
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param dateFin
     */
    public void setDateFin(LocalDate dateFin) { 
        this.dateFin = dateFin; 
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return la date de fin 
     */
    public int getHoraireDebut() { 
        return horaireDebut; 
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param horaireDebut 
     */
    public void setHoraireDebut(int horaireDebut) { 
        this.horaireDebut = horaireDebut; 
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return la date de fin 
     */
    public int getHoraireFin() { 
        return horaireFin; 
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param horaireFin 
     */
    public void setHoraireFin(int horaireFin) { 
        this.horaireFin = horaireFin; 
    }
}
