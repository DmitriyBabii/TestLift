package models;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private static final int MIN_FLOURS = 5;
    private static final int MAX_FLOURS = 20;

    private static final int MIN_PEOPLE_ON_FLOUR = 0;
    private static final int MAX_PEOPLE_ON_FLOUR = 10;

    private final Floor[] floors;
    private final Lift lift;

    public Building() {
        this.floors = new Floor[Randomize.getRandomBetween(MIN_FLOURS, MAX_FLOURS)];
        for (int i = 0; i < floors.length; i++) {
            int countOfPeople = Randomize.getRandomBetween(MIN_PEOPLE_ON_FLOUR, MAX_PEOPLE_ON_FLOUR);
            List<Person> people = new ArrayList<>(countOfPeople);
            for (int j = 0; j < countOfPeople; j++) {
                int floor = Randomize.getRandomBetween(0, floors.length - 1);
                people.add(new Person(i, floor));
            }
            floors[i] = new Floor(i, people);
        }
        this.lift = new Lift(floors.length);
    }

    public void run() {
        print();
        for (int i = 0; i < 20; i++) {
            int freePlace;
            int currentFloor = lift.getCurrentFloor();
            List<Person> peopleFromLift = lift.removePeople();
            floors[currentFloor].addPeople(peopleFromLift);
            if ((freePlace = lift.getFreePlace()) != 0) {
                List<Person> peopleFromFloor = floors[currentFloor].removePeople(freePlace, lift.getDirectional());
                lift.addPeople(peopleFromFloor);
            }
            lift.go();
            print();
        }
    }

//    private boolean isThereSomeoneToMove(){
//
//    }

    private void print() {
        System.out.println("\nBuilding:");
        for (int i = floors.length - 1; i >= 0; i--) {
            System.out.println(floors[i] + (lift.isThere(i) ? " <-- lift " + lift.getCountPeople() : ""));
        }
    }
}
