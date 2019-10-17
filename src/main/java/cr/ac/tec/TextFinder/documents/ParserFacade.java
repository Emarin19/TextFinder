package cr.ac.tec.TextFinder.documents;

import java.io.File;

public class ParserFacade {
    public static Document parse(DocumentType type, File file){
        switch (type) {
            case TXT:
                return TxtParser.getInstance().parseDocument(file);
            case DOC:
                return DocParser.getInstance().parseDocument(file);
            case PDF:
                return PdfParser.getInstance().parseDocument(file);
        }
        return null;
    }
}
