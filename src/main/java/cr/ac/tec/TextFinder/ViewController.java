package cr.ac.tec.TextFinder;

import cr.ac.tec.TextFinder.documents.Document;
import cr.ac.tec.TextFinder.documents.DocumentType;
import cr.ac.tec.TextFinder.documents.ParserFacade;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ViewController {
    public VBox fileList;
    public ScrollPane fileListScrollPane;
    public Button searchButton;
    public CheckBox phraseCheckBox;
    public TextField searchTextField;
    public GridPane searchPane;
    public MenuBar menu;
    public AnchorPane resultContainer;
    public BorderPane rootPane;
    Stage currentStage;

    public void configureControl(Stage stage){
        this.currentStage = stage;
    }
    public void onSearchButtonClicked(){
        //phrase selected
        boolean isPhrase = phraseCheckBox.isSelected();
        if (isPhrase){

        }else{

        }
    }
    public void refreshFileList(){}
    public void addFileToList(){
        File file;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().removeAll();
        FileChooser.ExtensionFilter docx = new FileChooser.ExtensionFilter("Word", "*.docx");
        FileChooser.ExtensionFilter pdf = new FileChooser.ExtensionFilter("pdf", "*.pdf");
        FileChooser.ExtensionFilter txt = new FileChooser.ExtensionFilter("txt", "*.txt");
        fileChooser.getExtensionFilters().addAll(docx,pdf,txt);
        file= fileChooser.showOpenDialog(currentStage);
        if(file==null)
            return;
        Document doc = null;
        if(file.getName().endsWith(".txt")){
            doc = ParserFacade.parse(DocumentType.TXT, file);
        }
        else if(file.getName().endsWith(".docx")){
            doc = ParserFacade.parse(DocumentType.DOC, file);
        }
        else if(file.getName().endsWith(".pdf")){
            doc = ParserFacade.parse(DocumentType.PDF, file);
        }else{
            return;
        }
        FileListManager.getInstance().addDocument(doc);
    }
    public void sortByDate(){
        //doSomething
    }
    public void sortBySize(){
        //doSomething
    }
    public void sortByName(){
        //doSomething
    }
    public void refreshDocumentResults(){}
}
