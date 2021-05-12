package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import sample.Person;

import java.util.ArrayList;
import java.util.List;

class SearchTest {

    @org.junit.jupiter.api.Test
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

    @Test
    void searchTest() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Петров;Виталик;Петрович;88005553535;г.Москва;2021-03-02; "));
        personList.add(new Person("Иванов;Сергей;Викторович;88005553535;г.Москва;2021-03-02; "));
        List<Person> p = new ArrayList<>();
        p.add(personList.get(0));
        Assertions.assertEquals(search("Петров", personList), p);
        Assertions.assertNotEquals(search("Иванов", personList), p);
    }
}