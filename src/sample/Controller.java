package sample;

import com.sun.javafx.iio.gif.GIFImageLoaderFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public static final String FILENAME = "data.dat";

    public static List<Person> personList = new ArrayList<>();

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
    private MenuItem export;

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
    private MenuItem importMenu;

    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem about;

    @FXML
    private void initialize() {
        DbPhone phone = null;
        try {
            phone = DbPhone.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            phone.createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
            searchPrint(search(textSearch.getText(), personList));
        });

        buttonDelete.setOnAction(event -> {
            deleteSelected();
        });

        buttonEdit.setOnAction(event -> {
            editSelected();
        });

        buttonAdd.setOnAction(event -> {
            add();
        });

        addContact.setOnAction(event -> {
            add();
        });

        exit.setOnAction(event -> {
            exit();
        });

        importMenu.setOnAction(event -> {
            openNewScene("export.fxml");
            if (ExportController.change) {
                printList(personList);
                ExportController.change = false;
            }
        });

        about.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("?? ????????????????????");
            alert.setHeaderText("???????????????????? ??????????");
            alert.setContentText("???????????????????? ?????????????????????? ?? ?????????????? ???? ?????????????? ???? ?????????????????????? ???????????????? ???? ????????????." +
                    " ?????????????? ?????????????????? ?? ???????????? ?????????? ?? ????????????????????.");

            alert.showAndWait();
        });

        export.setOnAction(event -> {
            openNewScene("import.fxml");
        });
    }

    public void exit() {
        overrideFile(FILENAME);
        System.exit(0);
    }

    public void searchPrint(List<Person> persons) {
        table.getItems().clear();
        table.getItems().addAll(persons);
    }

    public void printList(List<Person> persons) {
        for (Person person : persons) {
            boolean isFound = false;
            for (Person person1 : table.getItems()) {
                if (person.equals(person1)) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                table.getItems().add(person);
            }
        }
    }

    public void setupTable() {

        secondNameCol = new TableColumn<>("??????????????");
        secondNameCol.setMinWidth(200.);
        secondNameCol.setCellValueFactory(new PropertyValueFactory<>("SecondName"));

        firstNameCol = new TableColumn<>("??????");
        firstNameCol.setMinWidth(200.);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));

        fathersNameCol = new TableColumn<>("????????????????");
        fathersNameCol.setMinWidth(200.);
        fathersNameCol.setCellValueFactory(new PropertyValueFactory<>("FathersName"));

        mobileNumbersCol = new TableColumn<>("??????????????????/\n???????????????? ??????????????");
        mobileNumbersCol.setMinWidth(200.);
        mobileNumbersCol.setCellValueFactory(new PropertyValueFactory<>("MobileNumbers"));

        addressCol = new TableColumn<>("??????????");
        addressCol.setMinWidth(200.);
        addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));

        dateOfBirthCol = new TableColumn<>("???????? ????????????????");
        dateOfBirthCol.setMinWidth(200.);
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("DateOfBirth"));

        descriptionCol = new TableColumn<>("??????????????????????");
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

    List<Person> search(String textSearch, List<Person> personList) {
        List<Person> persons = new ArrayList<>();
        if (textSearch.equals("")) {
            return personList;
        }
        String[] names = textSearch.split(" ");

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

        return persons;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public static void getPersonsFromFile(String filename) {
        DbPhone phone = null;
        try {
            phone = DbPhone.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        personList = phone.getPersons();
    }

    public static void overrideFile(String filename) {
        try {
            DbPhone phone = DbPhone.getInstance();
            phone.addAll(personList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
