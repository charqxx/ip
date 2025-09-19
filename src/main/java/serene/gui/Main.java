package serene.gui;

import javafx.application.Application;
import javafx.scene.Scene;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import serene.Serene;
import serene.exception.SereneException;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Serene serene = new Serene();

    public Main() throws SereneException {
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setSerene(serene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
