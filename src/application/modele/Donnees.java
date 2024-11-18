/**
 * Donnees.java
 * 6 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * TODO commenter le fonctionnement
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class Donnees implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private LinkedHashMap<String, Exposition> expositions
    = new LinkedHashMap<>();
    
    private LinkedHashMap<String, Employe> employes
    = new LinkedHashMap<>();
    
    private LinkedHashMap<String, Conferencier> conferenciers
    = new LinkedHashMap<>();
    
    private ArrayList<Client> clients
    = new ArrayList<>();
    
    private LinkedHashMap<String, Visite> visites
    = new LinkedHashMap<>();

    /**
     * @return expositions
     */
    public LinkedHashMap<String, Exposition> getExpositions() {
        return expositions;
    }

    /**
     * @param expositions the expositions to set
     */
    public void setExpositions(LinkedHashMap<String, Exposition> expositions) {
        this.expositions = expositions;
    }

    /**
     * @return employes
     */
    public LinkedHashMap<String, Employe> getEmployes() {
        return employes;
    }

    /**
     * @param employes the employes to set
     */
    public void setEmployes(LinkedHashMap<String, Employe> employes) {
        this.employes = employes;
    }

    /**
     * @return conferenciers
     */
    public LinkedHashMap<String, Conferencier> getConferenciers() {
        return conferenciers;
    }

    /**
     * @param conferenciers the conferenciers to set
     */
    public void setConferenciers(LinkedHashMap<String, Conferencier> conferenciers) {
        this.conferenciers = conferenciers;
    }

    /**
     * @return clients
     */
    public ArrayList<Client> getClients() {
        return clients;
    }

    /**
     * @param clients the clients to set
     */
    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    /**
     * @return visites
     */
    public LinkedHashMap<String, Visite> getVisites() {
        return visites;
    }

    /**
     * @param visites the visites to set
     */
    public void setVisites(LinkedHashMap<String, Visite> visites) {
        this.visites = visites;
    }
    
    /**
     * Indique si les données stockées en mémoire sont vides
     * 
     * @return true si les données en mémoire sont vides sinon false
     */
    public boolean isDonneesVides() {
        
        return expositions.size() == 0 && visites.size() == 0
               && conferenciers.size() == 0 && employes.size() == 0;
    }
}
