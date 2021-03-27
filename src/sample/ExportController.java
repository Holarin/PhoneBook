package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExportController {
    private List<Person> persons = new ArrayList<>();

    public static boolean change = false;

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
            persons = new ArrayList<>();
            try {
                File file = fileChooser.showOpenDialog(buttonFile.getScene().getWindow());
                textFile.setText(file.getAbsolutePath());
                //создаем объект FileReader для объекта File
                FileReader fr = new FileReader(file);
                //создаем BufferedReader с существующего FileReader для построчного считывания
                BufferedReader reader = new BufferedReader(fr);
                // считаем сначала первую строку
                String line = reader.readLine();
                while (line != null && line != "") {
                    persons.add(new Person(line));
                    // считываем остальные строки в цикле
                    line = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ButtonOk.setOnAction(event -> {
            Controller.personList.clear();
            Controller.personList.addAll(persons);
            change = true;
            Stage stage = (Stage) ButtonOk.getScene().getWindow();
            stage.close();
        });

        buttonCancel.setOnAction(event -> {
            change = false;
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        });
    }
}
