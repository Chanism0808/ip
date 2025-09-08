import java.io.IOException;

import dupe.Dupe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Dupe using FXML.
 */
public class Main extends Application {
    private static final String FILE_PATH = "data/tasks.txt";

    private final Dupe dupe = new Dupe(FILE_PATH);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDupe(dupe);  // inject the Dupe instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
