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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import application.controleur.AccueilControleur;
import application.modele.Exposition;
import application.modele.ExpositionTemporaire;
import application.modele.Visite;

/**
 * TODO commenter la responsabilité de cette class (SRP)
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
    
    private static LinkedHashMap<String, Visite> visitesPdf;
    
    private static LinkedHashMap<String, Exposition> expositionsPdf;
    
    private static PdfWriter destination;
    
    private static PdfDocument pdf;
    
    private static Document document;

    /**
     * Méthode publique pour générer un PDF à partir des visites.
     * @param visites Données des visites.
     * @param cheminPdf le chemin ou sera stocké le fichier pdf
     * @throws IOException 
     */
    public static void visitePdf(LinkedHashMap<String, Visite> visites,
                                 String cheminPdf) throws IOException {
        visitesPdf = visites;
        creerEntetePDF(cheminPdf, "visites");
        donneesImporteesVisite();
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
        creerEntetePDF(cheminPdf, "expositions");
        donneesImporteesExposition();       
    }
    
    /**
     * Créer un fichier pdf avec l'entete
     * @throws IOException 
     */
    private static void creerEntetePDF(String chemin, 
                                       String type) throws IOException {
        Paragraph titre,
                  sousTitre1,
                  sousTitre2;
        String date;

        destination = new PdfWriter(chemin);
        pdf = new PdfDocument(destination);
        document = new Document(pdf);
               
        titre = new Paragraph(TITRE_PDF)
                .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(15);
        sousTitre1 = new Paragraph("Données des " + type)
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
     * @throws IOException
     */
    private static void donneesImporteesVisite() throws IOException { 
        Table table;
        PdfFont font;
        
        font = PdfFontFactory.createRegisteredFont(POLICE_ECRITURE);
        table = new Table(UnitValue.createPercentArray(FORMAT_VISITE))
                    .useAllAvailableWidth();
        
        enteteTableau(table, font, COULEUR_DONNEES_IMPORTEES, ENTETE_VISITE);
     
        
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
    private static void donneesImporteesExposition() throws IOException {
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
}

