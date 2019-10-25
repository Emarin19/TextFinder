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
    public SearchResult(Document document, String contextPhrase, Pair<Integer, Integer> reference){
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
        setTexts(reference, contextPhrase);
    }
    public void initialize(){

    }
    public void setTexts(Pair<Integer, Integer> reference, String contextPhrase){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        //fileDate.setText(sdf.format(doc.getFile().lastModified()));
        fileDate.setText(doc.getDateString());
        fileSize.setText(doc.getSize() + "KB");
        fileName.setText(doc.getName());
        if(doc.getType() == DocumentType.DOC){
            refA.setText("paragrafo: " + reference.getKey().toString());
            refB.setText("pos: " + reference.getValue().toString());
        }else {
            refA.setText("linea: " + reference.getKey().toString());
            refB.setText("pos: " + reference.getValue().toString());
        }

        //
        Text lol = new Text(contextPhrase);
        //lol.setFill(Color.RED);
        context.getChildren().add(lol);

    }

    public Document getDoc() {
        return doc;
    }
}
