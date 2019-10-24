package cr.ac.tec.TextFinder;

import cr.ac.tec.TextFinder.documents.*;
import cr.ac.tec.util.Collections.List.TecList;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ViewController {
    public VBox fileList;
    public ScrollPane fileListScrollPane;
    public Button searchButton;
    public CheckBox phraseCheckBox;
    public TextField searchTextField;
    public GridPane searchPane;
    public MenuBar menu;
    public VBox resultContainer;
    public BorderPane rootPane;
    Stage currentStage;
    public void configureControl(Stage stage){
        this.currentStage = stage;
        FileListManager.getInstance().viewController = this;
    }
    public void test(){
    }
    public void onSearchButtonClicked(){
        FileListManager.getInstance().setSrchResults(new TecList<>());
        //phrase selected
        boolean isPhrase = phraseCheckBox.isSelected();
        if (isPhrase){

        }else{

        }
    }
    public void addByFolder(){
        DirectoryChooser dC = new DirectoryChooser();
        File files = dC.showDialog(currentStage);
        if (files==null)
            return;
        for (File file: files.listFiles()) {
            if(file==null)
                continue;
            Document doc = ParserFacade.parse(file);
            if (doc==null)
                continue;
            FileListManager.getInstance().addDocument(doc);
            fileList.getChildren().add(doc);
        }
    }
    public void addFileToList(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().removeAll();
        FileChooser.ExtensionFilter filters = new FileChooser.ExtensionFilter("Archivos permitidos", "*.docx","*.pdf" ,"*.txt");
        fileChooser.getExtensionFilters().addAll(filters);
        List<File> filesToAdd= fileChooser.showOpenMultipleDialog(currentStage);
        if(filesToAdd ==null)
            return;
        for (File file: filesToAdd) {
            if(file==null)
                return;
            Document doc = ParserFacade.parse(file);
            if (doc==null)
                return;
            FileListManager.getInstance().addDocument(doc);
            fileList.getChildren().add(doc);
        }
    }
    public void sortByDate(){
        System.out.print("lamo");
        FileListManager.getInstance().sortResults(SortBy.DATE);
    }
    public void sortBySize(){
        FileListManager.getInstance().sortResults(SortBy.SIZE);
    }
    public void sortByName(){
        FileListManager.getInstance().sortResults(SortBy.NAME);
    }
    public void refreshDocumentResults(){}
}
