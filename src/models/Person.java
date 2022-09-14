package models;

public class Person {
    private int currentFloor;
    private final int needFloor;

    public Person(int startFloor, int needFloor) {
        this.currentFloor = startFloor;
        this.needFloor = needFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getNeedFloor() {
        return needFloor;
    }

    public boolean needUp() {
        return needFloor > currentFloor;
    }

    public boolean needDown() {
        return needFloor < currentFloor;
    }

    public boolean needMove() {
        return currentFloor != needFloor;
    }

    @Override
    public String toString() {
        return "{" + currentFloor +
                ", " + needFloor +
                '}';
    }
}
