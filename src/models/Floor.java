package models;

import enums.Directional;
import interfaces.PeopleAddable;

import java.util.List;
import java.util.stream.Collectors;

public class Floor implements PeopleAddable {

    private final int numberOfFloor;
    private final List<Person> people;

    public Floor(int numberOfFloor, List<Person> people) {
        this.numberOfFloor = numberOfFloor;
        this.people = people;
    }

    @Override
    public void addPeople(List<Person> peopleFromFloor) {
        peopleFromFloor = peopleFromFloor.stream()
                .peek(person -> person.setCurrentFloor(numberOfFloor)).collect(Collectors.toList());
        people.addAll(peopleFromFloor);
    }

    public List<Person> removePeople(int count, Directional directional) {
        List<Person> swap = people.stream()
                .filter(person -> person.needMove() &&
                        (directional == Directional.UP ? person.needUp() : person.needDown()))
                .limit(count)
                .collect(Collectors.toList());
        return remove(swap);

    }

    protected List<Person> remove(List<Person> people) {
        for (Person person : people) {
            this.people.remove(person);
        }
        return people;
    }

    public List<Person> getPeople() {
        return people;
    }

    @Override
    public String toString() {
        return String.format("Flour %-2s: %2d people", numberOfFloor, people.size());
    }
}
