package application.utilitaire;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import application.modele.Visite;


/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class GenererPdf {
    
    private static LinkedHashMap<String, Visite> visites;

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @throws IOException 
     */
    private static void m() throws IOException {
     // Chemin du fichier PDF à générer
        String dest = "m.pdf";
        
        try {
            // Créer un PdfWriter (fichier de sortie)
            PdfWriter writer = new PdfWriter(dest);

            // Créer un PdfDocument en utilisant le PdfWriter
            PdfDocument pdf = new PdfDocument(writer);

            // Créer un document iText (contenu du PDF)
            Document document = new Document(pdf);

            // Ajouter du texte au document
            document.add(new Paragraph("Du coup ?"));
            document.add(new Paragraph(visites.toString()));

            // Fermer le document pour générer le PDF
            document.close();

            System.out.println("Le fichier PDF a été généré avec succès !");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @param visites2
     */
    public static void j(LinkedHashMap<String, Visite> visites2) {
        visites = visites2;
        System.out.println("1");
        System.out.println(visites);
        try {
            m();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
}