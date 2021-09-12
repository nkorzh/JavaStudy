package carsharing.console;

import carsharing.database.entity.Company;

public class CompanyState extends ListState<State> {
    Company company;

    public CompanyState(String companyId, State parent) {
        super(companyId, parent);
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getCompanyId() {
        return String.valueOf(company.getId());
    }
}
