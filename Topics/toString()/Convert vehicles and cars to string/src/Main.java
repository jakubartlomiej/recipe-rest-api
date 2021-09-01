class Vehicle {

    protected String licensePlate;

    public Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}

class Car extends Vehicle {

    protected int numberOfSeats;

    public Car(String licensePlate, int numberOfSeats) {
        super(licensePlate);
        this.numberOfSeats = numberOfSeats;
    }
}