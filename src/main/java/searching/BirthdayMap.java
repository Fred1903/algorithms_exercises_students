package searching;

import java.time.LocalDate;
import java.util.*;


/**
 * This class represents a map that maps birthdays to persons.
 * Your map should be able to handle multiple people born on the same day.
 * Your map should be able to query efficiently for
 * - people born on a specific date and
 * - people born in a specific year.
 * You can assume that the input is valid for dates (format 'YYYY-MM-DD') and years (format 'YYYY').
 * The years do not start with 0.
 * The time complexity of the operations should be in O(log n + k)
 * where k is the number of people born on the specified date or year
 * and n is the number of different birthdays in the map.
 *
 * Complete the class to make the tests in BirthdayMapTest pass.
 * Do not modify the signature of existing methods.
 * Feel free to add instance variables and new methods.
 * Also feel free to import and use existing java classes.
 */
class BirthdayMap {
    // Hint: feel free to use existing java classes from Java such as java.util.TreeMap
    TreeMap<LocalDate,List<Person>> birthdayTreeMap;


    BirthdayMap() {
        // TODO
        birthdayTreeMap=new TreeMap<LocalDate,List<Person>>();
    }

    /**
     * Adds a person to the map.
     * The key is the birthday of the person.
     * The time complexity of the method should be in O(log n)
     * where n is the number of different birthdays in the map.
     * @param person
     */
    void addPerson(Person person) {
        // TODO
        if(person==null || person.birthday==null || person.name==null)return;

        List<Person> personsWithThisBirthday;
        LocalDate birthday = setStringDatetoDateType(person.birthday);

        if(birthdayTreeMap.containsKey(birthday)){
            personsWithThisBirthday=birthdayTreeMap.get(birthday);
            personsWithThisBirthday.add(person);
        }
        else{
            personsWithThisBirthday= new ArrayList<Person>();
            personsWithThisBirthday.add(person);
            //Uniquement valable si les entrées des dates se font sous le format "YYYY-MM-DD"
            birthdayTreeMap.put(birthday, personsWithThisBirthday);
        }
    }

    LocalDate setStringDatetoDateType(String date){
        int year = Integer.parseInt(date.substring(0,4));//de 0 compris a 4 non compris
        int month = Integer.parseInt(date.substring(5,7));
        int day = Integer.parseInt(date.substring(8,10));

        return LocalDate.of(year,month,day);
    }

    /**
     * The function returns a list of Person objects in the map born on the specified date.
     * @param date a String input representing the date (in 'YYYY-MM-DD' format)
     *             for which we want to retrieve people born on.
     * @return A list of Person objects representing all people born on the specified date.
     *          An empty list is returned if no entries are found for the specified date.
     */
    List<Person> getPeopleBornOnDate(String date) {
        // TODO
        List<Person> personsWithThisBirthday = birthdayTreeMap.get(setStringDatetoDateType(date));
        if (personsWithThisBirthday == null) return new ArrayList<>();
        return personsWithThisBirthday;
    }


    /**
     * The function returns a consolidated list of Person objects
     * in the map born in the specified year.
     * @param year A String input representing the year (in 'YYYY' format)
     *             for which we want to retrieve people born in.
     * @return A consolidated list of Person objects representing all people born in the specified year.
     *         If no entries are found for the specified year, the function returns an empty list.
     */
    List<Person> getPeopleBornInYear(String year) {
        // TODO

        int y = Integer.parseInt(year);
        //début et fin de l’année
        LocalDate startOfYear = LocalDate.of(y, 1, 1);
        LocalDate endOfYear = LocalDate.of(y, 12, 31);

        //sub-map des dates dans l’année
        SortedMap<LocalDate, List<Person>> subMap =
                birthdayTreeMap.subMap(startOfYear, true, endOfYear, true);

        //toutes les personnes trouvées
        List<Person> personsWithThisBirthdayYear = new ArrayList<>();
        for (List<Person> list : subMap.values()) {
            personsWithThisBirthdayYear.addAll(list);
        }

        return personsWithThisBirthdayYear;
    }


    /**
     * Example usage of the BirthdayMap class, feel free to modify.
     */
    public static void main(String[] args) {
        Person alice = new Person("Alice", "2000-07-17");
        Person bob = new Person("Bob", "2000-08-15");
        Person charlie = new Person("Charlie", "2001-06-06");

        BirthdayMap map = new BirthdayMap();
        map.addPerson(alice);
        map.addPerson(bob);
        map.addPerson(charlie);

        System.out.println(map.getPeopleBornOnDate("2000-07-17"));
        System.out.println(map.getPeopleBornInYear("2000"));
    }

}

class Person {
    String name;
    String birthday; // format: YYYY-MM-DD

    Person(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

