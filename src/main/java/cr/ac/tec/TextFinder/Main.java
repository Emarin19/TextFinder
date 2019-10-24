package cr.ac.tec.TextFinder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
        primaryStage.setScene(new Scene(root, 1280, 600));
        primaryStage.show();
        ViewController controlGUI = loader.getController();
        controlGUI.configureControl(primaryStage);
    }
}
