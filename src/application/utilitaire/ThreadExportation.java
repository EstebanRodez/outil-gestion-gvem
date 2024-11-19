/*
 * ThreadExportation.java                           
 * 19 nov. 2024
 * IUT de Rodez, pas de copyright
 */
package application.utilitaire;

import java.io.IOException;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class ThreadExportation extends Thread {
    
    private static final int PORT_EXPORTATION = Reseau.getPortExportation();

    private boolean arretExportation = false;
    
    @Override 
    public void run() { 

        while (!arretExportation) {
            
            int cleSecrete = -1;
            try {
                cleSecrete = EchangeDiffieHellman.genererDonneeSecreteAlice();
                System.out.println(cleSecrete);
            } catch (GenerationDonneeSecreteException e) {
                interrupt();
            }
            
            if (!Thread.interrupted()) {
                
                String[] nomFichiersDonnees = Vigenere.getNomsFichiersDonnees();
                String[] nomFichiersAlphabet = Vigenere.getNomsFichiersAlphabet();
                for (int indiceNomFichier = 0;
                        indiceNomFichier < nomFichiersDonnees.length;
                        indiceNomFichier++) {

                    String alphabet
                    = Vigenere.recupererAlphabet(nomFichiersDonnees[indiceNomFichier]);
                    try {
                        GestionFichiers.ecrireFichier(
                                nomFichiersAlphabet[indiceNomFichier], alphabet);
                    } catch (IOException e) {
                        // Ne rien faire
                    }
                    String cleChiffrement
                    = Vigenere.genererCleChiffrement(cleSecrete, alphabet);

                    System.out.println(cleChiffrement);

                    Vigenere.crypter(nomFichiersDonnees[indiceNomFichier],
                            cleChiffrement, alphabet);
                }

                Reseau.envoyerFichiers(PORT_EXPORTATION,
                        Vigenere.getNomsFichiersEnvois());
                Reseau.envoyerFichiers(PORT_EXPORTATION, nomFichiersAlphabet);

                EchangeDiffieHellman.supprimerFichiersAlice();
                EchangeDiffieHellman.supprimerFichiersBob();
                Vigenere.supprimerFichiersDonnees();
                Vigenere.supprimerFichiersAlphabet();
                Vigenere.supprimerFichiersEnvois();
            }
            
        } 

    }        

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    public void arreterExportation() {
        arretExportation = true;
    }
    
}
