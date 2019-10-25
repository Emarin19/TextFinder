package cr.ac.tec.TextFinder.documents;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class SearchResult extends AnchorPane {
    public Button abrirDoc;
    public TextFlow context;
    public Label fileName;
    public Label fileSize;
    public Label fileDate;
    public Label refA;
    public Label refB;
    private Document doc;
    public SearchResult(Document document, String context, Pair<Integer, Integer> reference){
        this.doc = document;
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("SearchResult.fxml")
        );
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
        setLabels(reference);
    }
    public void initialize(){
        Text lol = new Text("");
        lol.setFill(Color.WHITE);
        context.getChildren().add(lol);
    }
    public void setLabels(Pair<Integer, Integer> reference){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        //fileDate.setText(sdf.format(doc.getFile().lastModified()));
        fileDate.setText(doc.getDateString());
        fileSize.setText(doc.getSize() + "KB");
        fileName.setText(doc.getName());
        refA.setText(reference.getKey().toString());
        refB.setText(reference.getValue().toString());
    }

    public Document getDoc() {
        return doc;
    }
}
