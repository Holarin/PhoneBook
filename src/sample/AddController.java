package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddController {

    @FXML
    private TextField textSecondName;

    @FXML
    private TextField textFathersName;

    @FXML
    private TextField textFirstName;

    @FXML
    private TextField textMobile;

    @FXML
    private TextField textHomeMobile;

    @FXML
    private TextField textAddress;

    @FXML
    private TextField textDescription;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonSave;

    @FXML
    private DatePicker textDateOfBirth;

    private final String OK_DATA = "-fx-text-inner-color: black;";
    private final String NOT_OK_DATA = "-fx-text-inner-color: red;";

    @FXML
    void initialize() {

        buttonSave.setOnAction(event -> {
            boolean isValid = true;
            Person checker = new Person();

            if (checker.isValidFathersName(textFathersName.getText())) {
                checker.setFathersName(textFathersName.getText());
                textFathersName.setStyle(OK_DATA);
            } else {
                textFathersName.setStyle(NOT_OK_DATA);
                isValid = false;
            }

            if (checker.isValidSecondName(textSecondName.getText())) {
                checker.setSecondName(textSecondName.getText());
                textSecondName.setStyle(OK_DATA);
            } else {
                textSecondName.setStyle(NOT_OK_DATA);
                isValid = false;
            }

            if (checker.isValidFirstName(textFirstName.getText())) {
                checker.setFirstName(textFirstName.getText());
                textFirstName.setStyle(OK_DATA);
            } else {
                textFirstName.setStyle(NOT_OK_DATA);
                isValid = false;
            }

            String mobile = "";

            if (!textMobile.getText().equals("")) {
                mobile += textMobile.getText();
                if (!textHomeMobile.getText().equals("")) {
                    mobile +='/' + textHomeMobile.getText();
                }
            } else {
                mobile += textHomeMobile.getText();
            }

            if (checker.isValidMobileNumber(mobile)) {
                checker.setMobileNumbers(mobile);
                textHomeMobile.setStyle(OK_DATA);
                textMobile.setStyle(OK_DATA);
            } else {
                isValid = false;
                textMobile.setStyle(NOT_OK_DATA);
                textHomeMobile.setStyle(NOT_OK_DATA);
            }

            if (checker.isValidDateOfBirth(textDateOfBirth.getValue().toString())) {
                checker.setDateOfBirth(textDateOfBirth.getValue().toString());
                textDateOfBirth.setStyle(OK_DATA);
            } else {
                isValid = false;
                textDateOfBirth.setStyle(NOT_OK_DATA);
            }

            if (checker.isValidAddress(textAddress.getText())) {
                checker.setAddress(textAddress.getText());
                textAddress.setStyle(OK_DATA);
            } else {
                isValid = false;
                textAddress.setStyle(NOT_OK_DATA);
            }

            if (checker.isValidDescription(textDescription.getText())) {
                checker.setDescription(textDescription.getText());
                textDescription.setStyle(OK_DATA);
            } else {
                isValid = false;
                textDescription.setStyle(NOT_OK_DATA);
            }
            if (isValid) {
                Controller.transferPerson = checker;
                Stage stage = (Stage) buttonSave.getScene().getWindow();
                stage.close();
            } else {
                Controller.transferPerson = null;
            }
        });

        buttonCancel.setOnAction(event -> {
            Controller.transferPerson = null;
            Stage stage = (Stage) buttonSave.getScene().getWindow();
            stage.close();
        });
    }

}
