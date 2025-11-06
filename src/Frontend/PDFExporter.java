package Frontend;

import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Backend.Employee;

public class PDFExporter {
    public void exportToPDF(Employee emp, String filePath) throws Exception {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(filePath));
        doc.open();
        doc.add(new Paragraph(emp.generateSalarySlip()));
        doc.close();
    }
}
