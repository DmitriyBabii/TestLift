package models;

import enums.Directional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Lift {
    private static final int CAPACITY = 5;

    private final List<Person> people = new ArrayList<>(CAPACITY);

    private final int countOfFloors;
    private int currentFloor = 0;
    private int needFloor;
    private Directional directional = Directional.UP;

    public Lift(int countOfFloors) {
        this.countOfFloors = countOfFloors;
    }

    public void go() {
        if (currentFloor == countOfFloors - 1) {
            directional = Directional.DOWN;
        } else if (currentFloor == 0) {
            directional = Directional.UP;
        }

        if (directional == Directional.UP) {
            currentFloor++;
        } else {
            currentFloor--;
        }
    }

    public void addPeople(List<Person> peopleFromFloor) {
        people.addAll(peopleFromFloor);
        System.out.println("Come to lift: " + peopleFromFloor.size());
        setNeedFloor();
    }

    public List<Person> removePeople() {
        List<Person> swap = people.stream()
                .filter(person -> person.getNeedFloor() == currentFloor)
                .collect(Collectors.toList());
        System.out.println("Out from lift: " + swap.size());
        return remove(swap);
    }

    private List<Person> remove(List<Person> people) {
        for (Person person : people) {
            this.people.remove(person);
        }
        return people;
    }

    private void setNeedFloor() {
        Optional<Integer> value;
        if (directional == Directional.UP) {
            value = findBiggerNeedFloor();
            if (value.isPresent()) {
                needFloor = value.get();
            } else {
                value = findSmallerNeedFloor();
                if (value.isPresent()) {
                    changeDirectional();
                }
            }
        } else {
            value = findSmallerNeedFloor();
            if (value.isPresent()) {
                needFloor = value.get();
            } else {
                value = findBiggerNeedFloor();
                if (value.isPresent()) {
                    changeDirectional();
                }
            }
        }
    }

    private Optional<Integer> findBiggerNeedFloor() {
        return people.stream()
                .map(Person::getNeedFloor)
                .filter(floor -> floor > currentFloor)
                .max(Integer::compare);
    }

    private Optional<Integer> findSmallerNeedFloor() {
        return people.stream()
                .map(Person::getNeedFloor)
                .filter(floor -> floor < currentFloor)
                .min(Integer::compare);
    }

    private void changeDirectional() {
        if (directional == Directional.UP) {
            directional = Directional.DOWN;
        } else {
            directional = Directional.UP;
        }
    }

    public Directional getDirectional() {
        return directional;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getFreePlace() {
        return CAPACITY - people.size();
    }

    public boolean isThere(int floor) {
        return currentFloor == floor;
    }

    public int getCountPeople() {
        return people.size();
    }
}
