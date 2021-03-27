package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImportController {

    @FXML
    private TextField textFile;

    @FXML
    private Button buttonFile;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button ButtonOk;

    @FXML
    void initialize() {
        buttonFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(buttonFile.getScene().getWindow());
            textFile.setText(file.getAbsolutePath());
        });

        ButtonOk.setOnAction(event -> {
            Controller.overrideFile(textFile.getText());
            Stage stage = (Stage) ButtonOk.getScene().getWindow();
            stage.close();
        });

        buttonCancel.setOnAction(event -> {
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        });
    }
}
