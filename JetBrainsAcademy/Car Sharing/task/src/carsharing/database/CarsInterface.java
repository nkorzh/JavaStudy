package carsharing.database;

import carsharing.database.entity.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CarsInterface {
    String createStmt = "CREATE TABLE IF NOT EXISTS CAR(" +
        "ID INT PRIMARY KEY AUTO_INCREMENT," +
        "NAME VARCHAR UNIQUE NOT NULL," +
        "COMPANY_ID INT NOT NULL," +
        "CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
        ");";

    List<Car> getAllCars(String companyId);

    List<Car> getAvailableCars(String companyId);

    boolean createCar(String carName, String companyId);

    default Car extractCar(ResultSet res) throws SQLException {
            return new Car(res.getString("ID"), res.getString("NAME"), res.getString("COMPANY_ID"));

    }
}
