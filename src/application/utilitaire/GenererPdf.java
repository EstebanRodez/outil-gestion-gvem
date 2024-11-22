package application.utilitaire;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;


import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import application.controleur.AccueilControleur;
import application.modele.Conferencier;
import application.modele.Exposition;
import application.modele.ExpositionTemporaire;
import application.modele.Visite;
import application.modele.VisiteCalculResultat;

/**
 * Permet de generer des pdf a partir des données importées
 * 
 * @author Ayoub Laluti
 * @version 1.0
 */
public class GenererPdf {

    private static final DateTimeFormatter FORMAT_DATE 
    = AccueilControleur.getDateFormatterFR();
    
    private static final String TITRE_PDF 
        = "Gestion des visites des expositions d’un musée";
    
    private static final String POLICE_ECRITURE = "Helvetica";

    private static final DeviceRgb COULEUR_DONNEES_IMPORTEES 
        = new DeviceRgb(255, 238, 142); 
    
    private static final DeviceRgb COULEUR_DONNEES_CALCULEES  
        = new DeviceRgb(233, 137, 115);
    
    private static final DeviceRgb COULEUR_STATISTIQUE  
        = new DeviceRgb(29, 198, 144);
    
    private static final float[] FORMAT_VISITE = {6, 6, 6, 6, 15, 5, 15, 7};

    private static final String[] ENTETE_VISITE 
        = {"Identifiant", "Conférencier", "Date", "Employé", "Exposition", 
            "Horaire Début", "Client", "Numéro de Téléphone"};
    
    private static final float[] FORMAT_EXPOSITION 
        = {6, 6, 5, 5, 3, 20, 20, 15, 15};
    
    private static final String[] ENTETE_EXPOSITION 
        = {"Identifiant", "Intitulé", "Période début", "Période fin",
            "Nombre d'oeuvre", "Mot clé", "Résumé", "Date début", "Date fin"};

    private static final float[] FORMAT_CONFERENCIER 
        = {5, 5, 5, 15, 5, 3, 10};
    
    private static final String[] ENTETE_CONFERENCIER
    = {"Identifiant", "Nom", "Prénom", "Spécialité", "Téléphone", "Employé",
       "Indisponiblité"};
    
    private static final float[] FORMAT_DEUX_COLONNE = {10,10};
    
    private static final float[] FORMAT_TROIS_COLONNE = {10,5,5};
       
    private static LinkedHashMap<String, Visite> visitesPdf;
    
    private static LinkedHashMap<String, Exposition> expositionsPdf;
    
    private static LinkedHashMap<String, Conferencier> conferenciersPdf;
    
    private static LinkedHashMap<String, Visite> donneesCalculeesPdf;
    
    private static List<VisiteCalculResultat> listeDonneesPdf;
    
    private static PdfWriter destination;
    
    private static PdfDocument pdf;
    
    private static Document document;

    /**
     * Méthode publique pour générer un PDF à partir des visites.
     * @param visites Données des visites.
     * @param cheminPdf le chemin ou sera stocké le fichier pdf
     * @param type si c'est données importées, calculées ou statistiqu
     * @throws IOException 
     */
    public static void visitePdf(LinkedHashMap<String, Visite> visites,
                                 String cheminPdf, char type) throws IOException {
        visitesPdf = visites;
        creerEntetePDF(cheminPdf, "Visite", null);
        donneesVisite(type);
    }
    
    /**
     * Méthode publique pour générer un PDF à partir des expositions.
     * @param expositions Données des expositions.
     * @param cheminPdf le chemin ou sera stocké le fichier pdf
     * @throws IOException 
     */
    public static void expositionsPdf(LinkedHashMap<String, Exposition> expositions,
                                      String cheminPdf) throws IOException {
        expositionsPdf = expositions;
        creerEntetePDF(cheminPdf, "Exposition", null);
        donneesExposition();       
    }
    
    /**
     * Méthode publique pour générer un PDF à partir des conferenciers.
     * @param conferenciers Données des conferenciers.
     * @param cheminPdf le chemin ou sera stocké le fichier pdf
     * @throws IOException
     */
    public static void conferenciersPdf(LinkedHashMap<String, Conferencier> conferenciers,
                                        String cheminPdf) throws IOException {
        conferenciersPdf = conferenciers;
        creerEntetePDF(cheminPdf, "Conferencier", null);
        donneesConferencier();     
    }
    
    /**
     * Méthode publique pour générer un PDF à partir de données calculées
     * a trois colonnes.
     * @param donneesCalculees les données a convertir en pdf
     * @param cheminPdf le chemin ou stocker le fichier
     * @param choix le type de filtre
     * @param type le type de données Exposition/Conferencier
     * @throws IOException 
     */
    public static void troisColonneCalculeesPdf(LinkedHashMap<String, Visite> donneesCalculees,
                                                String cheminPdf, String choix, 
                                                String type) throws IOException {
        donneesCalculeesPdf = donneesCalculees;
        creerEntetePDF(cheminPdf, type, choix);
        donneesTroisColonneCalculees(type);
    }   
    
    /**
     * Méthode publique pour générer un PDF à partir de données a deux colonnes
     * @param listeDonnees liste des données
     * @param cheminPdf le chemin ou stocker le fichier
     * @param choix le type de filtre
     * @param type type de donnée Exposition ou Conferencier
     * @param theme Calculé ou Statistique
     * @param typeEvenement Programmé (P) ou Prevu (V)
     * @param date les dates du filtre
     * @throws IOException 
     */
    public static void deuxColonnePdf(List<VisiteCalculResultat> listeDonnees, 
                                      String cheminPdf, String choix, 
                                      String type, char theme,
                                      char typeEvenement, 
                                      String date) throws IOException {
        listeDonneesPdf = listeDonnees;
        creerEntetePDF(cheminPdf, type, choix + " " + date);
        donneesDeuxColonne(type, theme, typeEvenement);
    }

    /**
     * Créer un fichier pdf avec l'entete
     * @param choix Le choix si un filtre est appliqué
     * @throws IOException 
     */
    private static void creerEntetePDF(String chemin, String type,
                                       String choix) throws IOException {
        Paragraph titre,
                  sousTitre1,
                  sousTitre2,
                  sousTitreFiltre;
        String date;

        destination = new PdfWriter(chemin);
        pdf = new PdfDocument(destination);
        document = new Document(pdf);
               
        titre = new Paragraph(TITRE_PDF)
                .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(15);
        sousTitre1 = new Paragraph("Données des " + type + "s")
                    .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(12);
        
        date = java.time.LocalDate.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        sousTitre2 = new Paragraph("Publication du " + date)
                   .setTextAlignment(TextAlignment.CENTER)
                       .setFontSize(10);
        
        document.add(titre);
        document.add(sousTitre1);
        document.add(sousTitre2); 
        document.add(new Paragraph("\n"));    
        
        if (choix != null) {
            sousTitreFiltre = new Paragraph(choix)
                    .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(10);
            document.add(sousTitreFiltre);
        }
    }
    
    /**
     * Ajoute des cellules d'en-tête à un tableau PDF en utilisant
     * les spécifications suivantes :
     * - Le texte de chaque cellule est pris à partir du tableau 
     * - La police est spécifiée par le paramètre.
     * - La taille de la police est fixée à 7.
     * - Le texte est centré dans chaque cellule.
     * - La couleur de fond des cellules est définie par le paramètre .
     * 
     * Cette méthode est utilisée pour ajouter une ligne d'en-tête 
     * à un tableau PDF, où chaque cellule représente un intitulé de colonne.
     * 
     * @param table Le tableau auquel les cellules d'en-tête doivent être ajoutées. 
     * @param font La police à utiliser pour le texte dans les cellules d'en-tête. 
     * @param couleur La couleur de fond des cellules d'en-tête. 
     * @param typeEntete Un tableau de chaînes de caractères représentant 
     *        les titres des colonnes à afficher dans les cellules d'en-tête.
     */
    private static void enteteTableau(Table table, PdfFont font,
                                      DeviceRgb couleur, String[] typeEntete) {
        for (String header : typeEntete) {
            table.addHeaderCell(creerEnteteTableau
                    (header, font, 7, TextAlignment.CENTER, couleur));
        }
    }
    
    /**
     * Crée une cellule d'en-tête pour un tableau PDF 
     * avec les spécifications suivantes :
     * - Le texte de la cellule.
     * - La police du texte.
     * - La taille de la police.
     * - L'alignement du texte.
     * - La couleur de fond de la cellule.
     * 
     * Cette méthode est utilisée pour générer des cellules d'en-tête 
     * dans un tableau PDF, où chaque cellule représente un titre de colonne.
     * Elle permet de personnaliser la présentation du texte dans les cellules
     * d'en-tête, ainsi que l'apparence de leur fond.
     * 
     * @param text Le texte à afficher dans la cellule d'en-tête.
     * @param font La police à appliquer au texte de la cellule.
     * @param fontSize La taille de la police à utiliser pour le texte.
     * @param alignment L'alignement du texte dans la cellule.
     * @param backgroundColor La couleur de fond de la cellule.
     * @return Une Cell configurée avec le texte, la police,
     *         la taille de la police, l'alignement et la couleur de fond.
     */
    private static Cell creerEnteteTableau(String text, PdfFont font, 
                                           float fontSize, 
                                           TextAlignment alignment,
                                           DeviceRgb backgroundColor) {
        return new Cell().add(new Paragraph(text).setFont(font)
                .setFontSize(fontSize).setTextAlignment(alignment))
                    .setBackgroundColor(backgroundColor);
    }
    
    /**
     * Crée une cellule standard pour un tableau 
     * avec les spécifications suivantes :
     * - Le texte à afficher.
     * - La police à appliquer au texte.
     * - La taille de la police est définie à 7.
     * - L'alignement du texte est centré.
     * 
     * Cette méthode est généralement utilisée pour générer 
     * des cellules dans le corps du tableau (pas l'en-tête).
     * 
     * @param text Le texte à afficher dans la cellule.
     * @param font La police à appliquer au texte.
     * @return Une cellule (`Cell`) configurée avec le texte,
     *         la police, la taille de police (7), et un alignement centré.
     */
    private static Cell creerCelluleTableau(String text, PdfFont font) {
        return new Cell().add(new Paragraph(text).setFont(font)
                .setFontSize(7).setTextAlignment(TextAlignment.CENTER));
    }
      
    /**
     * Génère un PDF avec un tableau contenant les données des visites.
     * @param type si c'est données importées, calculées ou statistique
     * @throws IOException
     */
    private static void donneesVisite(char type) throws IOException { 
        Table table;
        PdfFont font;
        
        font = PdfFontFactory.createRegisteredFont(POLICE_ECRITURE);
        table = new Table(UnitValue.createPercentArray(FORMAT_VISITE))
                    .useAllAvailableWidth();
        
        if (type == 'I') {       
            enteteTableau(table, font, COULEUR_DONNEES_IMPORTEES, ENTETE_VISITE);
        } else  {
            enteteTableau(table, font, COULEUR_DONNEES_CALCULEES, ENTETE_VISITE);
        } 
 
        for (Entry<String, Visite> entry : visitesPdf.entrySet()) {
            Visite visite = entry.getValue();
                
            table.addCell(creerCelluleTableau(
                    entry.getKey().toString(), font)); // Identifiant
            table.addCell(creerCelluleTableau(
                    visite.getConferencier().getNom().toString(), font)); // Conférencier
            table.addCell(creerCelluleTableau(
                    visite.getDate().format(FORMAT_DATE).toString(), font)); // Date
            table.addCell(creerCelluleTableau(
                    visite.getEmploye().getNom().toString(), font)); // Employé
            table.addCell(creerCelluleTableau(
                    visite.getExposition().getIntitule().toString(), font)); // Exposition
            table.addCell(creerCelluleTableau(
                    visite.toStringHoraireDebut().toString(), font)); // Horaire Début
            table.addCell(creerCelluleTableau(
                    visite.getClient().getIntitule().toString(), font)); // Client
            table.addCell(creerCelluleTableau(
                    visite.getClient().getNumTel().toString(), font)); // Numéro de Téléphone
        }

        document.add(table);
        document.close();
    }
    
    /**
     * Génère un PDF avec un tableau contenant les données des expositions.
     * @throws IOException 
     */
    private static void donneesExposition() throws IOException {
        Table table;
        PdfFont font;
        
        font = PdfFontFactory.createRegisteredFont(POLICE_ECRITURE);
        table = new Table(UnitValue.createPercentArray(FORMAT_EXPOSITION))
                    .useAllAvailableWidth();
        
        enteteTableau(table, font, COULEUR_DONNEES_IMPORTEES, ENTETE_EXPOSITION);
     
        
        for (Entry<String, Exposition> entry : expositionsPdf.entrySet()) {
            Exposition expo  = entry.getValue();
            
            table.addCell(creerCelluleTableau(
                    entry.getKey(), font));
            table.addCell(creerCelluleTableau(
                    expo.getIntitule().toString(), font));
            table.addCell(creerCelluleTableau(
                    Integer.toString(expo.getPeriodeDebut()), font));
            table.addCell(creerCelluleTableau(
                    Integer.toString(expo.getPeriodeFin()), font));
            table.addCell(creerCelluleTableau(
                    Integer.toString(expo.getNbOeuvre()), font));
            table.addCell(creerCelluleTableau(
                    expo.toStringMotsCles(), font));
            table.addCell(creerCelluleTableau(
                    expo.getResume().toString(), font));
            
            if (expo instanceof ExpositionTemporaire) { 
                ExpositionTemporaire expoTempo = (ExpositionTemporaire) expo;
                
                table.addCell(creerCelluleTableau(
                        expoTempo.getDateDebut().toString(), font));
                table.addCell(creerCelluleTableau(
                        expoTempo.getDateFin().toString(), font));
            } else {
                table.addCell(creerCelluleTableau("", font));
                table.addCell(creerCelluleTableau("", font));
            }
        }

        document.add(table);
        document.close();  
    }
    
    /**
     * Génère un PDF avec un tableau contenant les données des expositions.
     * @throws IOException 
     */
    private static void donneesConferencier() throws IOException {
        Table table;
        PdfFont font;
        
        String[] specialitesTextes,
                 indisponibilitesTextes;
        
        font = PdfFontFactory.createRegisteredFont(POLICE_ECRITURE);
        table = new Table(UnitValue.createPercentArray(FORMAT_CONFERENCIER))
                    .useAllAvailableWidth();
        
        enteteTableau(table, font, COULEUR_DONNEES_IMPORTEES, ENTETE_CONFERENCIER);
            
        for (Entry<String, Conferencier> entry : conferenciersPdf.entrySet()) {
            Conferencier conferencier = entry.getValue();
                
            table.addCell(creerCelluleTableau(
                    entry.getKey().toString(), font)); // Identifiant
            table.addCell(creerCelluleTableau(
                    conferencier.getNom(), font)); // Nom
            table.addCell(creerCelluleTableau(
                    conferencier.getPrenom(), font)); // Prenom
            
            specialitesTextes
                = new String[conferencier.getSpecialites().length];
            
            for (int indexSpecialité = 0; 
                 indexSpecialité < conferencier.getSpecialites().length;
                 indexSpecialité++) {
                specialitesTextes[indexSpecialité] 
                        = conferencier.getSpecialites()[indexSpecialité]
                                      .toString();             
            }
            
            table.addCell(creerCelluleTableau
                    (String.join(", ", specialitesTextes), font)); // Spécialité
            
            table.addCell(creerCelluleTableau(
                    conferencier.getNumTel(), font)); // Telephone
            table.addCell(creerCelluleTableau(
                    conferencier.estInterne() ? "Oui" : "Non", font)); // Employé
            
            if (conferencier.getIndisponibilites() == null
                    || conferencier.getIndisponibilites().length == 0) {
                table.addCell(creerCelluleTableau("Aucune indisponibilité"
                                                   , font)); 
            } else {
                indisponibilitesTextes
                    = new String[conferencier.getIndisponibilites().length];
                
                for (int indexIndispo = 0; 
                     indexIndispo < conferencier.getIndisponibilites().length;
                     indexIndispo++) {
                    indisponibilitesTextes[indexIndispo] 
                            = conferencier.getIndisponibilites()[indexIndispo]
                                          .toString();
                }
                
                table.addCell(creerCelluleTableau
                                 (String.join(", ", indisponibilitesTextes)
                                 , font));
            } 
        }
        document.add(table);
        document.close();      
    }
    
    /**
     * Génère un PDF avec un tableau contenant les données des calculées 
     * qui possède 3 colonnes.
     * @param type Exposition ou Conferencier
     * @throws IOException 
     */
    private static void donneesTroisColonneCalculees(String type) throws IOException {
        Table table;
        PdfFont font;
        String[] ENTETE_TROIS_COLONNE
            = {" n'ayant aucune visite", "Date", "Heure début"};
        
        font = PdfFontFactory.createRegisteredFont(POLICE_ECRITURE);
        table = new Table(UnitValue.createPercentArray(FORMAT_TROIS_COLONNE))
                    .useAllAvailableWidth();
        
        ENTETE_TROIS_COLONNE[0] = type + ENTETE_TROIS_COLONNE[0];

        enteteTableau(table, font, COULEUR_DONNEES_CALCULEES
                      , ENTETE_TROIS_COLONNE);
        
        for (Entry<String, Visite> entry : donneesCalculeesPdf.entrySet()) {
            Visite visite = entry.getValue();
            
            if (type.equals("Exposition")) {
                table.addCell(creerCelluleTableau(
                        visite.getExposition().getIntitule().toString(),
                        font)); // Exposition 
            } else {
                table.addCell(creerCelluleTableau(
                        visite.getConferencier().getNom(), font)); // Conferencier
            }
            
            table.addCell(creerCelluleTableau(
                    visite.getDate().format(FORMAT_DATE), font)); // Date
            table.addCell(creerCelluleTableau(
                    visite.toStringHoraireDebut().toString(), font)); // Horaire Début
        }

        document.add(table);
        document.close();
        
    }
    
    /**
     * Génère un PDF avec un tableau contenant les données a deux colonnes.
     * @param type type de donnée Exposition ou Conferencier
     * @param theme Calculé ou Statistique
     * @param typeEvenement Programmé (P) ou Prevu (V)
     * @throws IOException 
     */
    private static void donneesDeuxColonne(String type, char theme, 
                                           char typeEvenement) throws IOException {
        Table table;
        PdfFont font;
        Boolean isStat = false;
        
        String[] ENTETE_DEUX_COLONNE_PROG
        = {"", "Nombre moyen de visites programmées"};
        
        String[] ENTETE_DEUX_COLONNE_PREVU
        = {"", "Nombre moyen de visites prévues"};
        
        font = PdfFontFactory.createRegisteredFont(POLICE_ECRITURE);
        table = new Table(UnitValue.createPercentArray(FORMAT_DEUX_COLONNE))
                    .useAllAvailableWidth();
        
        if (typeEvenement == 'P') {
            ENTETE_DEUX_COLONNE_PROG[0] = type + ENTETE_DEUX_COLONNE_PROG[0];
            
            if (theme == 'C') {
                enteteTableau(table, font, COULEUR_DONNEES_CALCULEES
                        , ENTETE_DEUX_COLONNE_PROG);
            } else if (theme == 'S') {
                isStat = true;
                enteteTableau(table, font, COULEUR_STATISTIQUE
                        , ENTETE_DEUX_COLONNE_PROG);
            }    
            
        } else if (typeEvenement == 'V') {
            ENTETE_DEUX_COLONNE_PREVU[0] = type + ENTETE_DEUX_COLONNE_PREVU[0];
            
            if (theme == 'C') {
                enteteTableau(table, font, COULEUR_DONNEES_CALCULEES
                        , ENTETE_DEUX_COLONNE_PROG);
            } else if (theme == 'S') {
                enteteTableau(table, font, COULEUR_STATISTIQUE
                        , ENTETE_DEUX_COLONNE_PROG);
            }
        }
        
        for (VisiteCalculResultat entry : listeDonneesPdf) {
            
            table.addCell(creerCelluleTableau(entry.getIntitule(), font));
         
            if (isStat) {
                table.addCell(creerCelluleTableau(
                                  entry.getCalculVisitesPourcentage(), font));
            } else {
                table.addCell(creerCelluleTableau(
                                  Double.toString(entry.getCalculVisites()),
                                                      font));
            }
        }
        document.add(table);
        document.close();
    }
}

