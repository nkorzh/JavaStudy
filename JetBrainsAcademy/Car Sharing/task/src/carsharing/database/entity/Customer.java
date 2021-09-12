package carsharing.database.entity;

public class Customer {
    String id;
    String name;
    Integer rentedCarId;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        rentedCarId = null;
    }

    public Customer(String id, String name, String rentedCarId) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rentedCarId == null ? null : Integer.parseInt(rentedCarId);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRentedCarId() {
        return rentedCarId;
    }

    @Override
    public String toString() {
        return name;
    }
}
