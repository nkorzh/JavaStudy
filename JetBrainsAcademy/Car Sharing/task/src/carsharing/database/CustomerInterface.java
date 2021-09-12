package carsharing.database;

import carsharing.database.entity.Car;
import carsharing.database.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CustomerInterface {
    String createStmt = "CREATE TABLE IF NOT EXISTS CUSTOMER(" +
        "ID INT PRIMARY KEY AUTO_INCREMENT," +
        "NAME VARCHAR UNIQUE NOT NULL," +
        "RENTED_CAR_ID INT DEFAULT NULL," +
        "CONSTRAINT FK_CAR FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
        ");";

    List<Customer> getCustomers();

    boolean createCustomer(String name);

    boolean rentCar(String customerId, String carId);

    Customer getCustomer(String customerId);

    boolean returnCar(String customerId);

    /**
     * Method to get the rented car of customer or null, if customer did not rent one.
     * @param customerId the id of the customer
     * @return rented car of null
     */
    Car showCar(String customerId);

    default Customer extractCustomer(ResultSet res) throws SQLException {
        return new Customer(res.getString("ID"), res.getString("NAME"), res.getString("RENTED_CAR_ID"));
    }
}
