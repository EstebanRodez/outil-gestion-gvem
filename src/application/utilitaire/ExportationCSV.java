/*
 * ExportationCSV.java                           
 * 15 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import application.controleur.AccueilControleur;
import application.modele.Conferencier;
import application.modele.Donnees;
import application.modele.Employe;
import application.modele.Exposition;
import application.modele.ExpositionTemporaire;
import application.modele.Indisponibilite;
import application.modele.Visite;

/**
 * Classe utilitaire responsable de l'exportation des données de l'application 
 * au format CSV.
 * 
 * <p>Cette classe gère l'exportation de différentes entités, telles que les
 * conférenciers, les employés, les expositions et les visites, dans des
 * fichiers CSV distincts.</p>
 * 
 * <p>Chaque fichier CSV est créé avec une structure définie, adaptée au
 * type de données exportées.</p>
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0 
 */
public class ExportationCSV {
    
    // Format pour les dates au format jj/MM/aaaa
    private static final DateTimeFormatter DATE_FORMAT 
    = AccueilControleur.getDateFormatterFR();
    
    /**
     * Exporte l'ensemble des données de l'application dans des fichiers CSV.
     * 
     * <p>Les entités suivantes sont exportées :</p>
     * <ul>
     *   <li>Les conférenciers dans le fichier <code>conferenciers.csv</code>
     *   </li>
     *   <li>Les employés dans le fichier <code>employes.csv</code></li>
     *   <li>Les expositions dans le fichier <code>expositions.csv</code></li>
     *   <li>Les visites dans le fichier <code>visites.csv</code></li>
     * </ul>
     * 
     * @throws ExportationCSVException si un identifiant n'a pas pu être
     *                                 associé à un objet.
     * @throws IOException si une erreur survient lors de l'écriture des
     *                     fichiers.
     */
    public static void exporterDonnees() throws IOException,
                                                ExportationCSVException {
        
        Donnees donnees = TraitementDonnees.getDonnees();
        
        exporterConferenciers(donnees);
        exporterEmployes(donnees);
        exporterExpositions(donnees);
        exporterVisites(donnees);
    }
    
    private static void exporterConferenciers(Donnees donnees)
            throws IOException {
        
        FileOutputStream fileOutputStream
        = new FileOutputStream("conferenciers.csv");
        OutputStreamWriter outputStreamWriter
        = new OutputStreamWriter(fileOutputStream, "windows-1252");
        BufferedWriter fluxEcriture = new BufferedWriter(outputStreamWriter);
        
        LinkedHashMap<String, Conferencier> conferenciers
        = donnees.getConferenciers();
        
        for (Map.Entry<String, Conferencier> paire : conferenciers.entrySet()) {
            
            Conferencier conferencier = paire.getValue();
            fluxEcriture.write(paire.getKey());
            fluxEcriture.write(";");
            fluxEcriture.write(conferencier.getNom());
            fluxEcriture.write(";");
            fluxEcriture.write(conferencier.getPrenom());
            fluxEcriture.write(";#");
            fluxEcriture.write(conferencier.toStringSpecialites());
            fluxEcriture.write("#;");
            fluxEcriture.write(conferencier.getNumTel());
            fluxEcriture.write(";");
            fluxEcriture.write(conferencier.estInterne() ? "oui" : "non");
            if (conferencier.getIndisponibilites() != null) {
                exporterIndisponibilites(fluxEcriture, conferencier);
            }
            fluxEcriture.write("\n");
        }
        
        fluxEcriture.close();
    }
    
    private static void exporterIndisponibilites(BufferedWriter fluxEcriture,
                                                 Conferencier conferencier)
                                                         throws IOException {
        
        for (Indisponibilite indisponibilite 
             : conferencier.getIndisponibilites()) {
            
            fluxEcriture.write(";");
            fluxEcriture.write(
                indisponibilite.getDateDebut().format(DATE_FORMAT)
            );
            fluxEcriture.write(";");
            if (indisponibilite.getDateFin() == null) {
                fluxEcriture.write(
                    indisponibilite.getDateDebut().format(DATE_FORMAT)
                );
            } else {
                fluxEcriture.write(
                    indisponibilite.getDateFin().format(DATE_FORMAT)
                );
            }
        }
    }
    
    private static void exporterEmployes(Donnees donnees)
            throws IOException {
        
        FileOutputStream fileOutputStream
        = new FileOutputStream("employes.csv");
        OutputStreamWriter outputStreamWriter
        = new OutputStreamWriter(fileOutputStream, "windows-1252");
        BufferedWriter fluxEcriture = new BufferedWriter(outputStreamWriter);
        
        LinkedHashMap<String, Employe> employes = donnees.getEmployes();
        
        for (Map.Entry<String, Employe> paire : employes.entrySet()) {
            
            Employe employe = paire.getValue();
            fluxEcriture.write(paire.getKey());
            fluxEcriture.write(";");
            fluxEcriture.write(employe.getNom());
            fluxEcriture.write(";");
            fluxEcriture.write(employe.getPrenom());
            fluxEcriture.write(";");
            fluxEcriture.write(employe.getNumTel());
            fluxEcriture.write("\n");
        }
        
        fluxEcriture.close();
        
    }
    
    private static void exporterExpositions(Donnees donnees)
            throws IOException {
        
        FileOutputStream fileOutputStream
        = new FileOutputStream("expositions.csv");
        OutputStreamWriter outputStreamWriter
        = new OutputStreamWriter(fileOutputStream, "windows-1252");
        BufferedWriter fluxEcriture = new BufferedWriter(outputStreamWriter);
        
        LinkedHashMap<String, Exposition> expositions
        = donnees.getExpositions();
        
        for (Map.Entry<String, Exposition> paire : expositions.entrySet()) {
            
            Exposition exposition = paire.getValue();
            fluxEcriture.write(paire.getKey());
            fluxEcriture.write(";");
            fluxEcriture.write(exposition.getIntitule());
            fluxEcriture.write(";");
            fluxEcriture.write(Integer.toString(exposition.getPeriodeDebut()));
            fluxEcriture.write(";");
            fluxEcriture.write(Integer.toString(exposition.getPeriodeFin()));
            fluxEcriture.write(";");
            fluxEcriture.write(Integer.toString(exposition.getNbOeuvre()));
            fluxEcriture.write(";#");
            fluxEcriture.write(exposition.toStringMotsCles());
            fluxEcriture.write("#;");
            fluxEcriture.write(exposition.getResume());;
            if (exposition instanceof ExpositionTemporaire) {
                
                ExpositionTemporaire expositionTemp
                = (ExpositionTemporaire) exposition;
                fluxEcriture.write(";");
                fluxEcriture.write(
                    expositionTemp.getDateDebut().format(DATE_FORMAT)
                );
                fluxEcriture.write(";");
                fluxEcriture.write(
                    expositionTemp.getDateFin().format(DATE_FORMAT)
                );
            }
            fluxEcriture.write("\n");
        }
        
        fluxEcriture.close();
    }
    
    private static void exporterVisites(Donnees donnees)
            throws IOException, ExportationCSVException {
        
        FileOutputStream fileOutputStream
        = new FileOutputStream("visites.csv");
        OutputStreamWriter outputStreamWriter
        = new OutputStreamWriter(fileOutputStream, "windows-1252");
        BufferedWriter fluxEcriture = new BufferedWriter(outputStreamWriter);
        
        LinkedHashMap<String, Visite> visites = donnees.getVisites();
        
        for (Map.Entry<String, Visite> paire : visites.entrySet()) {
            
            Visite visite = paire.getValue();
            fluxEcriture.write(paire.getKey());
            fluxEcriture.write(";");
            fluxEcriture.write(
                getIdentifiantExposition(donnees, visite.getExposition())
            );
            fluxEcriture.write(";");
            fluxEcriture.write(
                getIdentifiantConferencier(donnees, visite.getConferencier())
            );
            fluxEcriture.write(";");
            fluxEcriture.write(
                getIdentifiantEmploye(donnees, visite.getEmploye())
            );
            fluxEcriture.write(";");
            fluxEcriture.write(visite.getDate().format(DATE_FORMAT));
            fluxEcriture.write(";");
            fluxEcriture.write(visite.toStringHoraireDebut());
            fluxEcriture.write(";");
            fluxEcriture.write(visite.getClient().getIntitule());
            fluxEcriture.write(";");
            fluxEcriture.write(visite.getClient().getNumTel());
            fluxEcriture.write("\n");
        }
        
        fluxEcriture.close();
    }
    
    private static String getIdentifiantExposition(Donnees donnees,
            Exposition exposition) throws ExportationCSVException {
        
        LinkedHashMap<String, Exposition> expositions
        = donnees.getExpositions();
        
        if (!expositions.containsValue(exposition)) {
            
            throw new ExportationCSVException();
        }
        
        for (Map.Entry<String, Exposition> paire : expositions.entrySet()) {
            
            if (paire.getValue().equals(exposition)) {
                return paire.getKey();
            }
        }
        
        // Jamais le cas
        return null;
    }
    
    private static String getIdentifiantConferencier(Donnees donnees,
            Conferencier conferencier) throws ExportationCSVException {
        
        LinkedHashMap<String, Conferencier> conferenciers
        = donnees.getConferenciers();
        
        if (!conferenciers.containsValue(conferencier)) {
            
            throw new ExportationCSVException();
        }
        
        for (Map.Entry<String, Conferencier> paire : conferenciers.entrySet()) {
            
            if (paire.getValue().equals(conferencier)) {
                return paire.getKey();
            }
        }
        
        // Jamais le cas
        return null;
    }
    
    private static String getIdentifiantEmploye(Donnees donnees,
            Employe employe) throws ExportationCSVException {
        
        LinkedHashMap<String, Employe> employes = donnees.getEmployes();
        
        if (!employes.containsValue(employe)) {
            
            throw new ExportationCSVException();
        }
        
        for (Map.Entry<String, Employe> paire : employes.entrySet()) {
            
            if (paire.getValue().equals(employe)) {
                return paire.getKey();
            }
        }
        
        // Jamais le cas
        return null;
    }
}
