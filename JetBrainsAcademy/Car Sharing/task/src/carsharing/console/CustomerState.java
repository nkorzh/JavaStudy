package carsharing.console;


import carsharing.database.entity.Company;
import carsharing.database.entity.Customer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class CustomerState extends ListState<State> {
    // already rented car notification
    Function<String, Customer> updateCustomer;
    List<State> carRented;
    List<State> carNotRented;

    Company chosenCompany;
    Customer customer;
    String customerId;

    public CustomerState(String customerName, State parent) {
        super(customerName, parent);
        updateCustomer = s -> null;
    }

    @Override
    public State switchState(BufferedWriter writer, BufferedReader reader) throws IOException {
        if (!updateState()) {
            setTextPrinter(() -> "Error updating state: did not find customer with id: " + customerId);
            print(writer);
            setTextPrinter(defaultTextPrinter());
            return parent;
        }
        return super.switchState(writer, reader);
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return String.valueOf(customer.getId());
    }

    public String getCompanyName() {
        return chosenCompany == null ? "<null>" : chosenCompany.getName();
    }

    public void setChosenCompany(Company chosenCompany) {
        this.chosenCompany = chosenCompany;
    }

    public boolean isCarRented() {
        return customer.getRentedCarId() != null;
    }

    public String getCompanyId() {
        return String.valueOf(chosenCompany.getId());
    }

    public boolean updateState() {
        if (customerId == null) {
            return false;
        }
        Customer updatedCustomer = updateCustomer.apply(customerId);
        if (updatedCustomer == null) {
            return false;
        }
        customer = updatedCustomer;
        setChildren(isCarRented() ? carRented : carNotRented);
        return true;
    }

    public void setCarRented(List<State> carRented) {
        this.carRented = carRented;
    }

    public void setCarNotRented(List<State> carNotRented) {
        this.carNotRented = carNotRented;
    }

    public void setUpdateCustomer(Function<String, Customer> updateCustomer) {
        this.updateCustomer = updateCustomer;
    }
}
