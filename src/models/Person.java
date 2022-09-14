package models;

public class Person {
    private final int startFloor;
    private int needFloor;

    public Person(int startFloor, int needFloor) {
        this.startFloor = startFloor;
        this.needFloor = needFloor;
    }

    public int getNeedFloor() {
        return needFloor;
    }

    public boolean needUp() {
        return needFloor > startFloor;
    }

    public boolean needDown() {
        return needFloor < startFloor;
    }

    public boolean needMove() {
        return startFloor != needFloor;
    }
}
