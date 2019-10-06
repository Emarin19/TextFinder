package cr.ac.tec.TextFinder;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    public void addFileToList(){}
    public void sortByDate(){
        //doSomething
    }
    public void sortBySize(){
        //doSomething
    }
    public void sortByName(){
        //doSomething
    }


}
