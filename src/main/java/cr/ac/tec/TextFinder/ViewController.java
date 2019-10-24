package cr.ac.tec.TextFinder;

import cr.ac.tec.TextFinder.documents.Document;
import cr.ac.tec.TextFinder.documents.ParserFacade;
import cr.ac.tec.TextFinder.documents.TxtParser;
import cr.ac.tec.util.Collections.List.TecList;
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
    public Document document;
    public TecList<Document> list;
    Stage currentStage;

    public void configureControl(Stage stage){
        this.currentStage = stage;
    }
    public void onSearchButtonClicked(){
        System.out.println(document.getName());
        System.out.println(document.getDate());
        System.out.println(document.getSize());
        System.out.println(document.getTree().searchNode("libre").getValue());
        document.getContext("libre");


        //System.out.println(document.getTree().searchNode("Emanuel"));
        //TxtParser.getContext(document, "en");
    }
    public void refreshFileList(){
    }
    public void addFileToList(){
        File file;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().removeAll();
        FileChooser.ExtensionFilter filters = new FileChooser.ExtensionFilter("Archivos permitidos", "*.docx","*.pdf" ,"*.txt");
        fileChooser.getExtensionFilters().addAll(filters);
        file = fileChooser.showOpenDialog(currentStage);
        if(file==null)
            return;
        Document doc = ParserFacade.parse(file);
        if (doc==null)
            return;
        FileListManager.getInstance().addDocument(doc);
        fileList.getChildren().add(doc);
        document = doc;

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
