package carsharing.database;

import carsharing.database.entity.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CompanyInterface {
    String createStmt = "CREATE TABLE IF NOT EXISTS COMPANY(" +
        "ID INT PRIMARY KEY AUTO_INCREMENT, " +
        "NAME VARCHAR UNIQUE NOT NULL" +
        ");";

    boolean createCompany(String name);

    List<Company> getCompanies();

    Company getCompany(String companyId);

    default Company extractCompany(ResultSet res) throws SQLException {
        return new Company(res.getString("ID"), res.getString("NAME"));
    }
}
