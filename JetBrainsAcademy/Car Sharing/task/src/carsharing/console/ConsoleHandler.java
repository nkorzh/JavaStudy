package carsharing.console;

import carsharing.database.DbCarsharing;
import carsharing.database.entity.Car;
import carsharing.database.entity.Company;
import carsharing.database.entity.Customer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import static carsharing.console.utils.TextUtils.NEWLINE;

public class ConsoleHandler { // should be singleton
    private ListState<State> mainMenu;
    private ListState<State> manager;

    private CompanyState companyState;
    final BufferedReader in;
    final BufferedWriter out;
    final BufferedWriter err;
    final DbCarsharing storage;

    void initMenu() {
        mainMenu = new ListState<>("", null, "Exit");
        manager = new ListState<>("Log in as a manager", mainMenu);
        companyState = new CompanyState("", manager);
        CustomerState customerState = new CustomerState("", mainMenu);
        customerState.setUpdateCustomer(storage::getCustomer);
        
        ListState<Customer> chooseCustomer = new ListState<>("Log in as a customer", mainMenu);
        chooseCustomer.setToState(customer -> {
            customerState.setCustomerId(((Customer) customer).getId());
            return customerState;
        });
        chooseCustomer.setHeader("Customer list:");
        chooseCustomer.setEmptyText("The customer list is empty!");
        chooseCustomer.setListUpdater(storage::getCustomers);

        customerState.setParent(chooseCustomer);
        
        manager.setPrintChildren(true);
        mainMenu.setPrintChildren(true);
        companyState.setPrintChildren(true);
        customerState.setPrintChildren(true);

        /*
         * no car options
         */
        // car list choose

        ListState<Car> chooseCar = new ListState<>("", null);
        chooseCar.setListUpdater(() -> storage.getAvailableCars(customerState.getCompanyId()));
        chooseCar.setHeader("Choose a car:");
        chooseCar.setEmptyText("The car list is empty!");

        PostRequestState chooseCarRequest = new PostRequestState("", chooseCar);
        chooseCarRequest.setAwaitInput(false);
        chooseCarRequest.setSuccess(customerState);
        chooseCarRequest.setError(customerState);
        chooseCar.setToState(listItem -> {
            Car car = (Car) listItem;
            chooseCarRequest.setPostMethod(s -> storage.rentCar(
                customerState.getCustomerId(),
                String.valueOf(car.getId())
            ));
            chooseCarRequest.setSuccessMsg("You rented '" + car.getName() + "'");
            chooseCarRequest.setErrorMsg("Could not rent '" + car.getName() + "'. Unknown error");
            return chooseCarRequest;
        });

        // rent a car option (choose company)
        ListState<Company> rentCar = new ListState<>("Rent a car", customerState);
        rentCar.setListUpdater(storage::getCompanies);
        rentCar.setToState(listItem -> {
            Company company = (Company) listItem;
            customerState.setChosenCompany(company);
            return chooseCar;
        });
        rentCar.setHeader("Choose a company:");
        rentCar.setEmptyText("The company list is empty!");

        chooseCar.setParent(rentCar);
        // return + check car
        InfoState returnNoCar = new InfoState("Return a rented car", customerState);
        returnNoCar.setEmptyText("You didn't rent a car!");
        InfoState checkNonexistentCar = new InfoState("My rented car", customerState);
        checkNonexistentCar.setEmptyText("You didn't rent a car!");

        customerState.setCarNotRented(List.of(rentCar, returnNoCar, checkNonexistentCar));

        /*
         * rented car options
         */
        InfoState alreadyRented = new InfoState("Rent a car", customerState);
        alreadyRented.setEmptyText("You've already rented a car!");
        // return car
        PostRequestState returnCar = new PostRequestState("Return a rented car", customerState);
        returnCar.setAwaitInput(false);
        returnCar.setSuccessMsg("You've returned a rented car!");
        returnCar.setErrorMsg("Unknown error: you have not returned your car");
        returnCar.setPostMethod(s -> storage.returnCar(customerState.getCustomerId()));
        // check car print state
        InfoState carShowState = new InfoState("", customerState);
        // check car state
        PostRequestState showCar = new PostRequestState("My rented car", customerState);
        showCar.setAwaitInput(false);
        showCar.setPostMethod(s -> {
            String customerId = customerState.getCustomerId();
            Car car = storage.showCar(customerId);
            if (car == null) {
                showCar.setErrorMsg("Could not get car of customer#" + customerId);
                return false;
            }
            String companyId = car.getCompanyId();
            Company company = storage.getCompany(companyId);
            if (company == null) {
                showCar.setErrorMsg("Could not get company#" + companyId);
                return false;
            }
            carShowState.setMessage(String.join(NEWLINE,
                "Your rented car:",
                car.getName(),
                "Company:",
                company.getName()
            ));
            return true;
        });
        showCar.setSuccess(carShowState);
        customerState.setCarRented(List.of(alreadyRented, returnCar, showCar));

        // create customer
        PostRequestState createCustomer = new PostRequestState("Create a customer", mainMenu, "Enter the customer name:");
        createCustomer.setSuccessMsg("The customer was added!" + NEWLINE);
        createCustomer.setPostMethod(storage::createCustomer);

        // create a car
        PostRequestState createCar = new PostRequestState("Create a car", companyState, "Enter the car name:");
        createCar.setSuccessMsg("The car was created!" + NEWLINE);
        createCar.setPostMethod(carName -> storage.createCar(carName, companyState.getCompanyId()));

        // create company
        PostRequestState createCompany = new PostRequestState("Create a company", manager, "Enter the company name:");
        createCompany.setSuccessMsg("The company was created!" + NEWLINE);
        createCompany.setPostMethod(storage::createCompany);

        // car list show
        ListState<Car> carList = new ListState<>("Car list", companyState);
        carList.setListUpdater(() -> storage.getAllCars(companyState.getCompanyId()));
        carList.setHeader("Car list:");
        carList.setEmptyText("The car list is empty!");
        carList.setAwaitInput(false);
        carList.setLastZero(false);

        // company list show
        ListState<Company> companyList = new ListState<>("Company list", manager);
        companyList.setToState(listItem -> {
            Company company = (Company) listItem;
            companyState.setCompany(company);
            companyState.setHeader(String.format("'%s' company", company.getName()));
            return companyState;
        });
        companyList.setListUpdater(storage::getCompanies);
        companyList.setHeader("Choose a company:");
        companyList.setEmptyText("The company list is empty!");

        companyState.setChildren(List.of(carList, createCar));
        mainMenu.setChildren(List.of(manager, chooseCustomer, createCustomer));
        manager.setChildren(List.of(companyList, createCompany));
    }

    public ConsoleHandler(BufferedReader in, BufferedWriter out, BufferedWriter err, DbCarsharing storage) {
        this.in = in;
        this.out = out;
        this.err = err;
        this.storage = storage;
        storage.setErrorLog(this::error);
        initMenu();
    }

    public void startApplication() {
        try {
            State state = mainMenu;
            while ((state = state.switchState(out, in)) != null) {
            }
        } catch (final IOException ignored) {
        } catch (final NumberFormatException e) {
            numberError();
        }
    }

    protected void numberError() {
        error("Number required!");
    }

    protected void error(final String text) {
        try {
            err.write(text);
            err.flush();
        } catch (IOException ignored) {
        }
    }
}

