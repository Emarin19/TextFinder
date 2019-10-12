package cr.ac.tec.TextFinder.documents;

import java.io.File;

public class ParserFactory {
    public static TextFileParser getParser(DocumentType type, File file){
        switch (type) {
            case TXT:
                return new TxtParser(file);
            case DOC:
               return new DocParser(file);
            case PDF:
                return new PdfParser(file);
        }
        return null;
    }
}
