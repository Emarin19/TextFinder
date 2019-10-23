package cr.ac.tec.TextFinder.documents;

public enum DocumentType {
    TXT("txtDoc.png"),
    PDF("pdfDoc.png"),
    DOC("wordDoc.png");
    public String pathToImage;
    DocumentType(String pathToImage){
        this.pathToImage = pathToImage;
    }
}
