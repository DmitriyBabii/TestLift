package models;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private static final int MIN_FLOORS = 5;
    private static final int MAX_FLOORS = 20;

    private static final int MIN_PEOPLE_ON_FLOOR = 0;
    private static final int MAX_PEOPLE_ON_FLOOR = 10;

    private final List<Floor> floors;
    private final Lift lift;

    public Building() {
        int countFloors = Randomize.getRandomBetween(MIN_FLOORS, MAX_FLOORS);
        this.floors = new ArrayList<>(countFloors);
        for (int i = 0; i < countFloors; i++) {
            int countOfPeople = Randomize.getRandomBetween(MIN_PEOPLE_ON_FLOOR, MAX_PEOPLE_ON_FLOOR);
            List<Person> people = new ArrayList<>(countOfPeople);
            for (int j = 0; j < countOfPeople; j++) {
                int floor = Randomize.getRandomBetween(0, countFloors - 1);
                people.add(new Person(i, floor));
            }
            floors.add(new Floor(i, people));
        }
        this.lift = new Lift(floors.size());
    }

    public void run() {
        print();
        while (isThereSomeoneToMove()) {
            int freePlace;
            int currentFloor = lift.getCurrentFloor();
            List<Person> peopleFromLift = lift.removePeople();
            floors.get(currentFloor).addPeople(peopleFromLift);
            if ((freePlace = lift.getFreePlace()) != 0) {
                List<Person> peopleFromFloor = floors.get(currentFloor).removePeople(freePlace, lift.getDirectional());
                lift.addPeople(peopleFromFloor);
            }
            lift.go();
            print();
        }
    }

    private boolean isThereSomeoneToMove() {
        boolean isThereInFloats = floors.stream()
                .flatMap(floor -> floor.getPeople().stream())
                .anyMatch(Person::needMove);
        boolean isThereInLift = lift.getPeople().stream().anyMatch(Person::needMove);
        return isThereInFloats || isThereInLift;
    }

    private void print() {
        System.out.println("\nBuilding:");
        for (int i = floors.size() - 1; i >= 0; i--) {
            System.out.println(floors.get(i) + (lift.isThere(i) ? " <-- lift " + lift.getCountPeople() : ""));
        }
    }
}
