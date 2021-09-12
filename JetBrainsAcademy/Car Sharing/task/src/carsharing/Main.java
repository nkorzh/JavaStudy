package carsharing;

import carsharing.console.ConsoleHandler;
import carsharing.database.DbCarsharing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        ConsoleHandler console;
        try (DbCarsharing db = new DbCarsharing(args)) {
            console = new ConsoleHandler(
                    new BufferedReader(new InputStreamReader(System.in)),
                    new BufferedWriter(new OutputStreamWriter(System.out)),
                    new BufferedWriter(new OutputStreamWriter(System.err)),
                    db
            );
            console.startApplication();
        } catch (ClassNotFoundException e) {
            System.err.println("Can't find database driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Can't launch, looks like database is already in use: " + e.getMessage());
        }
    }
}