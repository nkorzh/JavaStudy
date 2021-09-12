package carsharing.database;

import carsharing.database.entity.Car;
import carsharing.database.entity.Company;
import carsharing.database.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DbCarsharing extends AbstractDatabase implements CarsInterface, CompanyInterface, CustomerInterface {
    Consumer<String> error;

    public DbCarsharing(String[] args) throws SQLException, ClassNotFoundException {
        super(args);
        initDatabase();
        createTable(CompanyInterface.createStmt);
        createTable(CarsInterface.createStmt);
        createTable(CustomerInterface.createStmt);
        error = e -> {
        };
    }

    public void setErrorLog(Consumer<String> error) {
        this.error = error;
    }

    @Override
    public List<Car> getAllCars(String companyId) {
        return getList(
            "SELECT" +
                "   ID, NAME, COMPANY_ID " +
                "FROM CAR " +
                "WHERE " +
                "   COMPANY_ID = " + companyId +
                " ORDER BY ID;",
            res -> {
                try {
                    return extractCar(res);
                } catch (SQLException e) {
                    error.accept("Could not extract Car: " + e.getMessage());
                    return null;
                }
            },
            "Could not get car list: ");
    }

    @Override
    public List<Car> getAvailableCars(String companyId) {
        return getList(
            "SELECT " +
                "   ID, NAME, COMPANY_ID " +
                "FROM " +
                "   CAR " +
                "WHERE " +
                "   COMPANY_ID = " + companyId + " " +
                "   AND " +
                "   ID NOT IN ( " +
                "       SELECT RENTED_CAR_ID " +
                "       FROM CUSTOMER " +
                "       WHERE " +
                "           RENTED_CAR_ID IS NOT NULL)" +

                ";",
            res -> {
                try {
                    return extractCar(res);
                } catch (SQLException e) {
                    error.accept("Could not extract Car: " + e.getMessage());
                    return null;
                }
            },
            "Could not get list of available cars"
        );
    }

    @Override
    public boolean createCar(String carName, String companyId) {
        try {
            return stmt.executeUpdate(
                "INSERT INTO CAR (NAME, COMPANY_ID) VALUES " +
                    "('" + carName + "',  " + companyId + ");"
            ) == 1;
        } catch (SQLException e) {
            error.accept("Could not create car '" + carName + "' at company#" + companyId + ": " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean createCompany(String name) {
        try {
            return stmt.executeUpdate(
                "INSERT INTO COMPANY (NAME) VALUES " +
                    "('" + name + "');"
            ) == 1;
        } catch (SQLException e) {
            error.accept("Could not create company with name '" + name + "': " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Company> getCompanies() {
        return getList("SELECT" +
                "   ID, NAME " +
                "FROM COMPANY " +
                "ORDER BY " +
                "ID;",
            res -> {
                try {
                    return extractCompany(res);
                } catch (SQLException e) {
                    error.accept("Could not extract Company: " + e.getMessage());
                    return null;
                }
            },
            "Could not get company list: "
        );
    }

    @Override
    public Company getCompany(String companyId) {
        final String getCompanyRequest = "SELECT " +
            "   ID, NAME " +
            "FROM COMPANY " +
            "WHERE ID = " + companyId + ";";
        try {
            ResultSet res = stmt.executeQuery(getCompanyRequest);
            if (res.next()) {
                return extractCompany(res);
            }
        } catch (final SQLException e) {
            error.accept("Could not extract company#" + companyId + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Customer> getCustomers() {
        return getList(
            "SELECT" +
                "   ID, NAME, RENTED_CAR_ID " +
                "FROM CUSTOMER " +
                "ORDER BY " +
                "ID;",
            res -> {
                try {
                    return extractCustomer(res);
                } catch (SQLException e) {
                    error.accept("Could not extract Customer: " + e.getMessage());
                    return null;
                }
            },
            "Could not get customers list: "
        );
    }

    @Override
    public Customer getCustomer(String customerId) {
        final String getCustomerRequest = "SELECT " +
            "   ID, NAME, RENTED_CAR_ID " +
            "FROM CUSTOMER " +
            "WHERE ID = " + customerId + ";";
        try {
            ResultSet res = stmt.executeQuery(getCustomerRequest);
            if (res.next()) {
                return extractCustomer(res);
            }
        } catch (final SQLException e) {
            error.accept("Could not extract customer#" + customerId + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean createCustomer(String name) {
        try {
            return stmt.executeUpdate(
                "INSERT INTO CUSTOMER (NAME) VALUES " +
                    "('" + name + "');"
            ) == 1;
        } catch (SQLException e) {
            error.accept("Could not create Customer '" + name + "': " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean rentCar(String customerId, String carId) {
        final String checkAvailabilityRequest = "SELECT COUNT(*) " +
            "FROM CUSTOMER " +
            "WHERE RENTED_CAR_ID = " + carId + ";";
        try {
            ResultSet res = stmt.executeQuery(checkAvailabilityRequest);
            if (res.next()) {
                boolean isAvailable = Integer.parseInt(res.getString("COUNT(*)")) == 0;
                if (isAvailable) {
                    final String rentRequest = "UPDATE CUSTOMER " +
                        "SET RENTED_CAR_ID = " + carId + " " +
                        "WHERE ID = " + customerId + ";";
                    return stmt.executeUpdate(rentRequest) == 1;
                }
            }
        } catch (SQLException e) {
            error.accept("Could not rent car#" + carId + " for customer#" + customerId + ": " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean returnCar(String customerId) {
        final String returnCarRequest = "UPDATE " +
            "CUSTOMER " +
            "SET RENTED_CAR_ID = NULL " +
            "WHERE ID = " + customerId + ";";
        try {
            return stmt.executeUpdate(returnCarRequest) == 1;
        } catch (final SQLException e) {
            error.accept("Could not return car of customer" + customerId + ":" + e.getMessage());
        }
        return false;
    }

    @Override
    public Car showCar(String customerId) {
        final String getCarRequest = "SELECT " +
            "   ID, NAME, COMPANY_ID " +
            "FROM CAR " +
            "WHERE ID IN (SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = " + customerId + ");";
        try {
            ResultSet res = stmt.executeQuery(getCarRequest);
            if (res.next()) {
                return extractCar(res);
            }
        } catch (SQLException e) {
            error.accept("Could not get car of customer#" + customerId + ": " + e.getMessage());
        }
        return null;
    }

    protected <T> List<T> getList(String request, Function<ResultSet, T> extractEntity, String errorMessage) {
        final List<T> list = new ArrayList<>();
        try {
            final ResultSet res = stmt.executeQuery(request);
            while (res.next()) {
                list.add(extractEntity.apply(res));
            }
            if (list.stream().anyMatch(Objects::isNull)) {
                error.accept("Error extracting entities from result set, check out log!");
                return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
            }
        } catch (SQLException e) {
            error.accept(errorMessage + e.getMessage());
            return Collections.emptyList();
        }
        return list;
    }
}
