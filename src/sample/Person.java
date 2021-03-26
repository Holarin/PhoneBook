package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * Класс, описывающий человека
 * {@link #secondName} - фамилия
 * {@link #firstName} - имя
 * {@link #fathersName} - отчество
 * {@link #mobileNumbers} - номер телефона мобильного/домашнего
 * {@link #address} - адрес проживанич
 * {@link #dateOfBirth} - дата рождения
 * {@link #description} - комментарий о человеке
 */
public class Person {
    public String getSecondName() {
        return secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public String getMobileNumbers() {
        return mobileNumbers;
    }

    public String getAddress() {
        return address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDescription() {
        return description;
    }

    private String secondName;
    private String firstName;
    private String fathersName;
    private String mobileNumbers;
    private String address;
    private String dateOfBirth;
    private String description;

    public Person(String csvString) {
        String[] strings = csvString.split(";");
        if (strings.length == 7) {
            secondName = strings[0];
            firstName = strings[1];
            fathersName = strings[2];
            mobileNumbers = strings[3];
            address = strings[4];
            dateOfBirth = strings[5];
            description = strings[6];
        }
    }

    public Person(String secondName,
                  String firstName,
                  String fathersName,
                  String mobileNumbers,
                  String address,
                  String dateOfBirth,
                  String description) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.fathersName = fathersName;
        this.mobileNumbers = mobileNumbers;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
    }

    boolean isValidDescription(String description) {
        return true;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public void setMobileNumbers(String mobileNumbers) {
        this.mobileNumbers = mobileNumbers;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toCsvString() {
        return secondName + ";" +
                firstName + ";" +
                (fathersName.equals("") ? " " : fathersName) + ";" +
                mobileNumbers + ";" +
                address + ";" +
                dateOfBirth + ";" +
                (description.equals("") ? " " : description);
    }

    /**
     * Поддерживаемый формат дат:
     * YYYY-MM-DD
     * DD-MM-YYYY
     */
    boolean isValidDateOfBirth(String dateOfBirth) {
        if (dateOfBirth.length() != 10) {
            return false;
        }
        int separatorCounter = 0;
        if (dateOfBirth.charAt(4) == '-' && dateOfBirth.charAt(7) == '-') {
            for (Character ch : dateOfBirth.toCharArray()) {
                if (ch == '-') {
                    separatorCounter++;
                    continue;
                }

                if (!Character.isDigit(ch)) {
                    return false;
                }
            }

            return (separatorCounter == 2);
        }

        if (!((dateOfBirth.charAt(2) != '.') && (dateOfBirth.charAt(5) != '.'))) {
            return false;
        }

        for (Character ch : dateOfBirth.toCharArray()) {
            if (ch == '.') {
                separatorCounter++;
                continue;
            }

            if (!Character.isDigit(ch)) {
                return false;
            }
        }

        return (separatorCounter == 2);
    }

    boolean isValidAddress(String address) {
        return !address.equals("");
    }

    boolean isValidMobileNumber(String mobileNumbers) {
        if (mobileNumbers.equals("")) {
            return false;
        }
        int counterSlash = 0;
        for (Character ch : mobileNumbers.toCharArray()) {
            if (ch == '/') {
                counterSlash++;
                continue;
            }

            if (!Character.isDigit(ch)) {
                return false;
            }
        }

        return (counterSlash <= 1);
    }

    boolean isValidFathersName(String fName) {
        for (Character ch : fName.toCharArray()) {
            if (!Character.isAlphabetic(ch)) {
                return false;
            }
        }
        return true;
    }

    boolean isValidFirstName(String fName) {
        if (fName.equals("")) {
            return false;
        }
        for (Character ch : fName.toCharArray()) {
            if (!Character.isAlphabetic(ch)) {
                return false;
            }
        }
        return true;
    }

    boolean isValidSecondName(String sName) {
        if (sName.equals("")) {
            return false;
        }
        for (Character ch : sName.toCharArray()) {
            if (!Character.isAlphabetic(ch)) {
                return false;
            }
        }
        return true;
    }

    Person() {}

    @Override
    public String toString() {
        return "Person{" +
                "secondName='" + secondName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", fathersName='" + fathersName + '\'' +
                ", mobileNumbers='" + mobileNumbers + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
