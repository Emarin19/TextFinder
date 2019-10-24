package cr.ac.tec.TextFinder.documents;

import java.io.File;

public class ParserFacade {
    public static Document parse(File file){
        try {
            if (file.getName().endsWith(".txt") || file.getName().endsWith(".TXT")) {
                return TxtParser.getInstance().parseDocument(file);
            } else if (file.getName().endsWith(".docx") || file.getName().endsWith(".DOCX")) {
                Document result = DocParser.getInstance().parseDocument(file);
                return result;
            } else if (file.getName().endsWith(".pdf") || file.getName().endsWith(".PDF")) {
                return PdfParser.getInstance().parseDocument(file);
            } else
                return null;
        }catch (Exception e){
            return null;
        }
    }
}
