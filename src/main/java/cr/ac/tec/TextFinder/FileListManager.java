package cr.ac.tec.TextFinder;

import cr.ac.tec.TextFinder.documents.Document;
import cr.ac.tec.util.Collections.List.TecList;

public class FileListManager {
    static FileListManager instance;
    ViewController viewController;
    private TecList<Document> documentsList = null;

    private FileListManager(){
        documentsList = new TecList<>();
    }
    public static FileListManager getInstance() {
        if(instance==null) {
            instance = new FileListManager();
        }
        return instance;
    }
    public synchronized void addDocument(Document newDocument){
        documentsList.add(newDocument);
        System.out.println(newDocument.getTree().toString());
    }
    public synchronized void deleteDocument(Document toDelete){
        documentsList.removeValue(toDelete);
    }
    public synchronized TecList<Document> getDocumentsList(){
        final TecList<Document> readOnlyDocuments = documentsList;
        return readOnlyDocuments;
    }
    public void sortListByDate(){
        //doSomething
    }
    public void sortListBySize(){
        //doSomething
    }
    public void sortListByName(){
        //doSomething
    }
}
