package application.utilitaire;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import application.modele.VisiteMoyenneResultat;

import java.io.FileNotFoundException;
import java.util.List;

public class CreerPdf {

	/**
	 * Génère un rapport PDF avec la liste donnée de VisiteMoyenneResultat.
	 *
	 * @param destination le chemin du fichier où le PDF sera enregistré
	 * @param results la liste de VisiteMoyenneResultat à inclure dans le rapport
	 * @throws FileNotFoundException si le fichier de destination ne peut pas être créé
	 */
    public static void generatePdf(String destination, List<VisiteMoyenneResultat> results) throws FileNotFoundException {
        // Creer un PDF writer
        PdfWriter writer = new PdfWriter(destination);
        // Initialiser le doc PDF
        PdfDocument pdfDoc = new PdfDocument(writer);
        // Initialiser un document layout
        Document document = new Document(pdfDoc);

        // ajoute le contenu dans le PDF
        document.add(new Paragraph("Rapport des visites des conférenciers\n\n"));

        // Boucle les résultats et les ajoute au PDF
        for (VisiteMoyenneResultat resultat : results) {
            String conferencier = resultat.getIntituleExposition();
            double moyenneVisites = resultat.getMoyenneVisites();

            document.add(new Paragraph("Conférencier : " + conferencier));
            document.add(new Paragraph("Nombre moyen de visites : " + moyenneVisites + "\n"));
        }

        // Ferme le document
        document.close();
        System.out.println("PDF généré avec succès !");
    }
}

