package sample;

import java.lang.ref.PhantomReference;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DbPhone {

    private static final String USERDATA = "jdbc:derby:database;create=true"; //Установите после двоеточия адрес к БД
    private static Connection conn = null;
    private static Statement statement = null;
    //Будем использовать один экземпляр класса
    private static DbPhone instance = null;

    public static synchronized DbPhone getInstance() throws SQLException {
        if (instance == null)
            instance = new DbPhone();
        return instance;
    }
    //здесь будем хранить соединение с БДq

    void createTable() throws SQLException {
        statement = conn.createStatement();
        String table = "BOOK";
        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet tables = dbm.getTables(null, null, table.toUpperCase(Locale.ROOT), null);
        if (tables.next()) {
            System.out.println("Connected");
        } else {
            statement.execute("CREATE TABLE BOOK (\n" +
                    "\tsecondName varchar(200),\n" +
                    "\tfirstName varchar(200),\n" +
                    "\tfathersName varchar(200),\n" +
                    "\tmobileNumbers varchar(200),\n" +
                    "\taddress varchar(200),\n" +
                    "\tdateOfBirth varchar(200),\n" +
                    "\tdescription varchar(200)\n" +
                    ")");
        }
    }

    public void addAll(List<Person> phones) {
        exec("DELETE FROM BOOK");
        for (Person p : phones) {
            addPhone(p);
        }
    }
    public void addPhone(Person phone) {
        String query = "INSERT INTO BOOK(secondName, firstName, fathersName, mobileNumbers, address, dateOfBirth, description) ";
        query += "VALUES(" +
                "'" + phone.getSecondName() + "', " +
                "'" + phone.getFirstName() + "', " +
                "'" + phone.getFathersName() + "', " +
                "'" + phone.getMobileNumbers() + "', " +
                "'" + phone.getAddress() + "', " +
                "'" + phone.getDateOfBirth() + "', " +
                "'" + phone.getDescription() + "')";
        exec(query);
    }

    /*
    * String secondName,
                  String firstName,
                  String fathersName,
                  String mobileNumbers,
                  String address,
                  String dateOfBirth,
                  String description
                  * */
    public List<Person> getPersons() {
        ResultSet rs = execQuery("SELECT * FROM BOOK");
        List<Person> personList = new ArrayList<>();

        while (true) {
            try {
                while (rs.next()) {
                    personList.add(new Person(rs.getString("secondName"),
                            rs.getString("firstName"),
                            rs.getString("fathersName"),
                            rs.getString("mobileNumbers"),
                            rs.getString("address"),
                            rs.getString("dateOfBirth"),
                            rs.getString("description")));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return personList;
        }
    }

    public void exec(String query) {
        try {
            statement = conn.createStatement();
            System.out.println(query);
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet resultSet = null;

        try {
            statement = conn.createStatement();
            System.out.println(query);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    private DbPhone() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(USERDATA);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(USERDATA);
    }
}
