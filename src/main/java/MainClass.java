import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class MainClass {
    public static void main(String[] args) {
        try {
            // Show a file chooser dialog to select the input PDF file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select PDF File");
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                // Load the selected PDF file
                PDDocument document = PDDocument.load(selectedFile);

                // Get the number of pages
                int numPages = document.getNumberOfPages();

                // Get the range of pages to split from the user
                String range = JOptionPane.showInputDialog(
                        "Enter the range of pages to split (e.g. 1-3):", "1-" + numPages);
                int startPage = Integer.parseInt(range.split("-")[0].trim()) - 1;
                int endPage = Integer.parseInt(range.split("-")[1].trim()) - 1;

                // Split the PDF into single page documents
                for (int i = startPage; i <= endPage; i++) {
                    PDDocument singlePageDoc = new PDDocument();
                    PDPage page = document.getPage(i);
                    singlePageDoc.addPage(page);
                    singlePageDoc.save("output_" + (i + 1) + ".pdf");
                    singlePageDoc.close();
                }

                // Close the main document
                document.close();
                JOptionPane.showMessageDialog(null, "PDF splitted successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
