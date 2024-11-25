/*
 * ThreadExportation.java                           
 * 19 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.IOException;

import application.controleur.ExporterControleur;

/**
 * Thread gérant le processus d'exportation sécurisé de fichiers.
 * Ce thread effectue les étapes suivantes dans un cycle continu :
 * <ul>
 *     <li>Génération d'une clé secrète pour le chiffrement via l'échange
 *         Diffie-Hellman.</li>
 *     <li>Préparation des fichiers nécessaires au chiffrement et au
 *         transfert.</li>
 *     <li>Chiffrement des données à exporter en utilisant l'algorithme de
 *         Vigenère.</li>
 *     <li>Envoi des fichiers chiffrés et des alphabets via un réseau.</li>
 *     <li>Nettoyage des fichiers temporaires utilisés.</li>
 * </ul>
 * Le thread peut être arrêté proprement en appelant la méthode
 * {@link #arreterExportation()}.
 * 
 * @author Romain Augé
 * @author Ayoub Laluti
 * @author Baptiste Thenieres
 * @author Esteban Vroemen
 * @version 1.0
 */
public class ThreadExportation extends Thread {
    
    private int portExportation = Reseau.getPortExportation();

    private boolean arretExportation = false;
    
    @Override 
    public void run() { 

        while (!arretExportation) {
            
            int cleSecrete = -1;
            try {
                cleSecrete = EchangeDiffieHellman.genererDonneeSecreteAlice();
            } catch (GenerationDonneeSecreteException e) {
                if (!arretExportation) {
                    ExporterControleur.lancerErreurGenerationDonneeSecrete(
                            e.getMessage());
                }
            }
            
            if (!Thread.interrupted()) {
                
                String[] nomFichiersDonnees = Vigenere.getNomsFichiersDonnees();
                for (int indiceNomFichier = 0;
                        indiceNomFichier < nomFichiersDonnees.length;
                        indiceNomFichier++) {

                    String cleChiffrement
                    = Vigenere.genererCleChiffrement(cleSecrete);

                    Vigenere.crypter(nomFichiersDonnees[indiceNomFichier],
                                     cleChiffrement);
                }

                Reseau.envoyerFichiers(portExportation,
                                       Vigenere.getNomsFichiersEnvois());

                EchangeDiffieHellman.supprimerFichiersBob();
                Vigenere.supprimerFichiersEnvois();
            }
            
            // Forcément généré au début
            EchangeDiffieHellman.supprimerFichiersAlice();
        } 
        
        Vigenere.supprimerFichiersDonnees();
    }        

    /**
     * Indique que le processus d'exportation doit s'arrêter
     */
    public void arreterExportation() {
        arretExportation = true;
        this.interrupt();
    }
    
}
