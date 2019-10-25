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
    private String searched;
    private Document doc;
    public SearchResult(Document document, String contextPhrase, Pair<Integer, Integer> reference, String searched){
        this.doc = document;
        this.searched = searched;
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
            refA.setText("parrafo: " + reference.getKey().toString());
            refB.setText("pos: " + reference.getValue().toString());
        }else {
            refA.setText("linea: " + reference.getKey().toString());
            refB.setText("pos: " + reference.getValue().toString());
        }

        //Text lol = new Text(contextPhrase);
        //context.getChildren().add(lol);
        setContext(contextPhrase);
    }
    public void setContext(String phrase){
        String[] origin = searched.split(" ");
        String[] paragraph = phrase.split(" ");
        String prevMessage = "";


        for (int i=0; i<paragraph.length-1; i++) {
            String posibleText = paragraph[i];
            boolean success = true;
            if (paragraph[i].compareToIgnoreCase(origin[0])==0){
                for (int j= 1 ;j<origin.length-1;j++){
                    if(paragraph[++i].compareToIgnoreCase(origin[j])==0){
                        posibleText += " "+ paragraph[i];
                    }else{
                        success = false;
                    }
                }
                if (success){
                    Text prev = new Text(prevMessage);
                    Text found = new Text(" " + searched);
                    found.setFill(Color.RED);
                    context.getChildren().addAll(prev, found);
                }else{
                    prevMessage += " " + posibleText;
                }
            }else{
                prevMessage += " " + paragraph[i];
            }
        }
        Text finalText = new Text(prevMessage);
        context.getChildren().add(finalText);
    }

    public Document getDoc() {
        return doc;
    }
}
