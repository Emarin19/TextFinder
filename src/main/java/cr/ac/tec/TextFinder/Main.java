package cr.ac.tec.TextFinder;

import cr.ac.tec.TextFinder.documents.Document;
import cr.ac.tec.TextFinder.documents.TxtParser;
import cr.ac.tec.util.Collections.List.TecList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.poi.ss.formula.functions.T;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Application start method (origin of application)
     * @param primaryStage stage to be showed
     * @throws IOException exception in case there's an error reading the fxml base file
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewModel.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        ViewController controlGUI = loader.getController();
        controlGUI.configureControl(primaryStage);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("DOCX", "*.docx"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        File file = fileChooser.showOpenDialog(primaryStage);
        Document document = new Document(file);
        System.out.println(document.getTree().contains("Emanuel"));
        TecList value = document.getTree().searchNode("Emanuel").getValue();
        System.out.println(document.getContext("es"));


    }
}
