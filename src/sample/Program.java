package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Program extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1265, 653);
        stage.setTitle("Максимізація еритроцитів");
        stage.setScene(scene);
        Controller controller = loader.getController();
        controller.initialize();
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}