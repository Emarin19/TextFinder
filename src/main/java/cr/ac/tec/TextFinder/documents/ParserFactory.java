package cr.ac.tec.TextFinder.documents;

public class ParserFactory {
    public static TextFileParser getParser(DocumentType type){
        switch (type) {
            case TXT:
                return new TxtParser();
            case PDF:
                return new PdfParser();
            case DOC:
               return new DocParser();
        }
        return null;
    }
}
