/*
 * ExporterControleur.java                           
 * 13 oct. 2024
 * IUT de Rodez, pas de copyright
 */
package application.controleur;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import application.EchangeurDeVue;
import application.utilitaire.EchangeDiffieHellman;
import application.utilitaire.ExportationCSV;
import application.utilitaire.ExportationCSVException;
import application.utilitaire.GenerationDonneeSecreteException;
import application.utilitaire.GestionFichiers;
import application.utilitaire.Serveur;
import application.utilitaire.Vigenere;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;


/**
 * Contrôleur pour la gestion de l'exportation des données.
 * 
 * Cette classe permet à l'utilisateur de se connecter à un serveur 
 * en fournissant une adresse IP et un port. Elle gère également 
 * la validation des entrées et l'affichage des erreurs éventuelles.
 * 
 * @version 1.0
 */
public class ExporterControleur {

    @FXML
    private Button btnAide;

    @FXML
    private Button btnExporter;

    @FXML
    private Button btnRetour;

    @FXML
    private Label labelIp;

    @FXML
    private Label labelPort;
    
    /**
     * Méthode d'initialisation appelée après le chargement de 
     * l'interface utilisateur.
     */
    @FXML
    public void initialize() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            labelIp.setText(socket.getLocalAddress().getHostAddress());
        } catch (Exception e) {
            Alert boiteIpInconnu = new Alert(Alert.AlertType.ERROR, 
                    "Impossible de connaître l'adresse IP de l'interface Ethernet", 
                    ButtonType.OK);
            boiteIpInconnu.setTitle("Erreur adresse IP inconnue");
            boiteIpInconnu.setHeaderText("Erreur adresse IP inconnue");
            boiteIpInconnu.showAndWait(); 
        }
    } 

    @FXML
    void btnExporterAction(ActionEvent event) {
        
        final String[] NOMS_FICHIERS_CRYPTAGE
        = {
            "conferenciers.csv", "employes.csv",
            "expositions.csv", "visites.csv"
        };
        
        final String[] NOMS_FICHIERS_ALPHABET
        = {
            "conferenciers_alphabet", "employes_alphabet",
            "expositions_alphabet", "visites_alphabet"
        };
        
        final String[] NOMS_FICHIERS_ENVOIS
        = {
            "conferenciers_crypté", "employes_crypté",
            "expositions_crypté", "visites_crypté"
        };
        
        EchangeurDeVue.creerPopUp("chargementPopUp");
        
        try {
            ExportationCSV.exporterDonnees();
        } catch (ExportationCSVException e) {
            e.printStackTrace();
        }
        
        Thread attente;
        attente = new Thread(() -> {
            
            int cleSecrete = -1;
            try {
                cleSecrete = EchangeDiffieHellman.genererDonneeSecreteAlice();
                System.out.println(cleSecrete);
            } catch (GenerationDonneeSecreteException e) {
                e.printStackTrace();
            }
            
            for (String nomFichier : NOMS_FICHIERS_CRYPTAGE) {
                
                String alphabet = Vigenere.recupererAlphabet(nomFichier);
                String nomFichierAlphabet
                = nomFichier.substring(0, nomFichier.lastIndexOf("."))
                  + "_alphabet";
                try {
                    GestionFichiers.ecrireFichier(nomFichierAlphabet, alphabet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String cleChiffrement
                = Vigenere.genererCleChiffrement(cleSecrete, alphabet);
                
                System.out.println(cleChiffrement);
                
                Vigenere.crypter(nomFichier, cleChiffrement, alphabet);
            }
            
            Serveur.envoyerFichiers(65432, NOMS_FICHIERS_ENVOIS);
            Serveur.envoyerFichiers(65432, NOMS_FICHIERS_ALPHABET);
            
            // FIXME erreur thread
//            if (!Thread.currentThread().isInterrupted()) {
//                EchangeurDeVue.fermerPopUp("chargementPopUp");
//                EchangeurDeVue.changerVue("exporterValideVue");
//            }
        });
        
      ChargementPopUpControleur controleur
      = EchangeurDeVue.getFXMLLoader("chargementPopUp").getController();
      controleur.setThreadAttente(attente);
      attente.start();
        
//        EchangeurDeVue.creerPopUp("chargementPopUp");
//        
//        int p, g;
//        
//        p = Mathematiques.trouverNombrePremier(
//                Mathematiques.genererNombreAleatoire(1000,9999));
//        System.out.println(p);
//        
//        g = Mathematiques.trouverDernierGroupeMultiplicatif(p);
//        System.out.println(g);
//        
//        int a = Mathematiques.genererNombreAleatoire(1,p);
//        int gExpA = Mathematiques.calculExponentielleModulo(g, a, p);
//        
//        // Initialiser le thread
//        Thread attente;
//        attente = new Thread(() -> {
//            
//            String[] fichiersCles = {"p.txt", "g.txt", "g^a.txt"};
//            try {
//                GestionFichiers.ecrireFichier(fichiersCles[0],
//                                              Integer.toString(p));
//                GestionFichiers.ecrireFichier(fichiersCles[1],
//                                              Integer.toString(g));
//                GestionFichiers.ecrireFichier(
//                    fichiersCles[2], Integer.toString(gExpA)
//                );
//            } catch (IOException e) {
//                e.printStackTrace();
//                Thread.currentThread().interrupt();
//            }  
//            InetAddress ipClient = Serveur.envoyerFichiers(65432, fichiersCles);
//            
//            if (!Thread.currentThread().isInterrupted()) {
//                
//                Client.recevoirFichiers(ipClient.getHostAddress(), 65432,
//                                        new String[] {"g^b.txt"}, null);
//                int gExpB;
//                try {
//                    gExpB = Integer.parseInt(
//                            GestionFichiers.lireFichier("g^b.txt"));
//                } catch (NumberFormatException | IOException e) {
//                    gExpB = 0;
//                    e.printStackTrace();
//                }
//                
//                int cleSecret
//                = Mathematiques.calculExponentielleModulo(gExpB, a, p);
//                
//                String nomFichierCrypte;
//                try {
//                    nomFichierCrypte
//                    = Cryptage.creerFichierDonnees(Integer.toString(cleSecret));
//                } catch (CryptageException erreur) {
//                    nomFichierCrypte = null;
//                }
//                System.out.println("test");
//                String[] fichiersCryptes = {nomFichierCrypte};
//                
//                Serveur.envoyerFichiers(65432, fichiersCryptes);
//                // for (String cheminFichier : fichiersCryptes) {
////                  try {
////                      Files.delete(Path.of(cheminFichier));
////                  } catch (IOException erreur) {
////                      // Ne rien faire
////                  }
////              }
//            }
//
//            /* 
//             * Tout s'est déroulé si il n'a pas été interrompu
//             * donc on affiche la vue de confirmation de
//             * l'exportation 
//             */
//            if (!Thread.currentThread().isInterrupted()) {
//                EchangeurDeVue.fermerPopUp("chargementPopUp");
//                EchangeurDeVue.changerVue("exporterValideVue");
//            }
//        });
//        
//        ChargementPopUpControleur controleur
//        = EchangeurDeVue.getFXMLLoader("chargementPopUp").getController();
//        controleur.setThreadAttente(attente);
//        attente.start();
//        
////        try {
////            // Files.delete(Path.of(nomFichierCrypte));
////        } catch (IOException e) {
////            
////        }
    }

    @FXML
    void btnAideAction(ActionEvent event) {
        AccueilControleur.lancerAide();
    }

    @FXML
    void btnRetourAction(ActionEvent event) {
        EchangeurDeVue.changerVue("accueilVue");
    }
    
}
