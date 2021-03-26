package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    static double minWidth = 500;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Phone book");
        primaryStage.setScene(new Scene(root, minWidth, 400));
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(minWidth);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void openNewScene(String window, Node node) {
        Stage oldStage = (Stage)node.getScene().getWindow();
        oldStage.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
    }
}
