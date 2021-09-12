package carsharing.database.entity;

public class Car {
    String id;
    String name;
    String companyId;

    public Car(String id, String name, String companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return name;
    }
}
