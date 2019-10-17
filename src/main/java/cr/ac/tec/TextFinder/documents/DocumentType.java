package cr.ac.tec.TextFinder.documents;

public enum DocumentType {
    TXT("txtDoc"),
    PDF("pdfDoc"),
    DOC("wordDoc");
    public String pathToImage;
    DocumentType(String pathToImage){
        this.pathToImage = pathToImage;
    }
}
