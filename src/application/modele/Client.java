/*
 * Client.java                           
 * 8 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.modele;

/**
 * Un client est représenté avec un intitulé et un numéro de 
 * téléphone. Un client a forcément lié à une visite qu'il a réservé.
 * L’intitulé est un texte libre qui pourrait être un nom et un 
 * prénom et/ou le nom d’un organisme (par exemple 
 * « M.Jacques Dupont » ou « Les amis des musées de Rodez » ou encore
 * « M. Jacques Dupont pour les amis des musées de Rodez »)
 * @version 1.0
 */
public class Client {

    private String intitule;
    
    private String numTel;
    
    /**
     * Initialise un nouveau client avec un intitulé et un numéro de téléphone
     * @param intitule le nom ou la désignation du client
     * @param numTel le numéro de téléphone du client
     */
    public Client(String intitule, String numTel) {
        
    }
    
    /**
     * Récupère l'intitulé du client
     * @return l'intitulé du client
     */
    public String getIntitule() {
        return intitule;
    }
    
    /**
     * Récupère le numéro de téléphone du client
     * @return le numéro de téléphone du client
     */
    public String getNumTel() {
        return numTel;
    }
}
