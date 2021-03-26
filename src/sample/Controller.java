package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final String FILENAME = "data.dat";

    private static List<Person> personList = new ArrayList<>();

    static public Person transferPerson = null;

    public TableColumn<Person, String> secondNameCol;
    public TableColumn<Person, String> firstNameCol;
    public TableColumn<Person, String> fathersNameCol;
    public TableColumn<Person, String> mobileNumbersCol;
    public TableColumn<Person, String> addressCol;
    public TableColumn<Person, String> dateOfBirthCol;
    public TableColumn<Person, String> descriptionCol;

    @FXML
    private MenuItem addContact;

    @FXML
    private MenuItem editContact;

    @FXML
    private MenuItem deleteContact;

    @FXML
    private TableView<Person> table;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonAdd;

    @FXML
    private TextField textSearch;

    @FXML
    private Button buttonSearch;

    @FXML
    private MenuItem exit;

    @FXML
    private void initialize() {

        setupTable();

        getPersonsFromFile(FILENAME);

        printList(personList);
        table.setOnMouseClicked(event -> {
            onButtons();
        });

        editContact.setOnAction(event -> {
            editSelected();
        });

        deleteContact.setOnAction(event -> {
            deleteSelected();
        });

        buttonSearch.setOnAction(event -> {
            search();
        });

        buttonDelete.setOnAction(event -> {
            deleteSelected();
        });

        buttonEdit.setOnAction(event -> {
            editSelected();
        });

        buttonAdd.setOnAction(event ->  {
            add();
        });

        addContact.setOnAction(event -> {
            add();
        });

        exit.setOnAction(event -> {
            exit();
        });
    }

    public void exit() {
        overrideFile(FILENAME);
        System.exit(0);
    }

    public void printList(List<Person> persons) {
        table.getItems().clear();
        for (Person person : persons) {
            table.getItems().add(person);
        }
    }

    public void setupTable() {
        secondNameCol = new TableColumn<>("Фамилия");
        secondNameCol.setMinWidth(200.);
        secondNameCol.setCellValueFactory(new PropertyValueFactory<>("SecondName"));

        firstNameCol = new TableColumn<>("Имя");
        firstNameCol.setMinWidth(200.);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));

        fathersNameCol = new TableColumn<>("Отчество");
        fathersNameCol.setMinWidth(200.);
        fathersNameCol.setCellValueFactory(new PropertyValueFactory<>("FathersName"));

        mobileNumbersCol = new TableColumn<>("Мобильный/\nДомашний телефон");
        mobileNumbersCol.setMinWidth(200.);
        mobileNumbersCol.setCellValueFactory(new PropertyValueFactory<>("MobileNumbers"));

        addressCol = new TableColumn<>("Адрес");
        addressCol.setMinWidth(200.);
        addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));

        dateOfBirthCol = new TableColumn<>("Дата рождения");
        dateOfBirthCol.setMinWidth(200.);
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("DateOfBirth"));

        descriptionCol = new TableColumn<>("Комментарий");
        descriptionCol.setMinWidth(200.);
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Main.minWidth = secondNameCol.getWidth() +
                firstNameCol.getWidth() +
                fathersNameCol.getWidth() +
                mobileNumbersCol.getWidth() +
                addressCol.getWidth() +
                dateOfBirthCol.getWidth() +
                descriptionCol.getWidth();

        table.getColumns().addAll(secondNameCol,
                firstNameCol,
                fathersNameCol,
                mobileNumbersCol,
                addressCol,
                dateOfBirthCol,
                descriptionCol);
    }

    public void add() {
        openNewScene("add.fxml");
        if (transferPerson != null) {
            table.getItems().add(transferPerson);
            personList.add(transferPerson);
            transferPerson = null;
        } else {
            editContact.setDisable(true);
            deleteContact.setDisable(true);
        }
    }

    public void search() {
        List<Person> persons = new ArrayList<>();
        if (textSearch.getText().equals("")) {
            printList(personList);
            return;
        }
        String[] names = textSearch.getText().split(" ");

        for (Person person : personList) {
            for (String str : names) {
                if (person.getFirstName().equals(str)) {
                    persons.add(person);
                    break;
                }

                if (person.getSecondName().equals(str)) {
                    persons.add(person);
                    break;
                }

                if (person.getFathersName().equals(str)) {
                    persons.add(person);
                    break;
                }
            }
        }

        printList(persons);
    }

    public void onButtons() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            editContact.setDisable(false);
            deleteContact.setDisable(false);
        }
    }

    public void editSelected() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            transferPerson = table.getSelectionModel().getSelectedItem();
            openNewScene("edit.fxml");
            if (transferPerson != null) {
                int index = table.getSelectionModel().getSelectedIndex();
                personList.remove(table.getSelectionModel().getSelectedItem());
                table.getItems().remove(table.getSelectionModel().getSelectedItem());
                table.getItems().add(index, transferPerson);
                personList.add(index, transferPerson);
                transferPerson = null;
            }
        } else {
            editContact.setDisable(true);
            deleteContact.setDisable(true);
        }
    }

    public void deleteSelected() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            personList.remove(table.getSelectionModel().getSelectedItem());
            table.getItems().remove(table.getSelectionModel().getSelectedItem());
        } else {
            deleteContact.setDisable(true);
            editContact.setDisable(true);
        }
    }

    public void openNewScene(String window) {
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
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    void getPersonsFromFile(String filename) {
        personList = new ArrayList<>();
        try {
            File file = new File(filename);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            while (line != null && line != "") {
                personList.add(new Person(line));
                // считываем остальные строки в цикле
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void overrideFile(String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename);
            for (Person travel : personList) {
                writer.println(travel.toCsvString());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Can not override file");
        }
    }
}
