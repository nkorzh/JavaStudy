package carsharing.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractDatabase implements AutoCloseable {
    protected String jdbcDriver = "org.h2.Driver";
    protected String url = "jdbc:h2:";

    protected Connection conn = null;
    protected Statement stmt = null;
    protected String path;

    public AbstractDatabase(String[] args) {
        try {
            path = getDBPath(args);
            Files.createDirectories(Paths.get(path).toAbsolutePath().getParent());
        } catch (final InvalidPathException | IOException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String createTableStmt) throws SQLException {
        stmt.executeUpdate(createTableStmt);
    }

    public void initDatabase() throws ClassNotFoundException, SQLException {
        Class.forName(jdbcDriver);
        conn = DriverManager.getConnection(url + path);
        conn.setAutoCommit(true);

        stmt = conn.createStatement();
    }

    private String getDBPath(String[] args) {
        String dbFileName = "default";
        if (args.length >= 2 && args[0] != null && args[1] != null &&
                args[0].equals("-databaseFileName")) {
            dbFileName = args[1];
        }
//        return  "./src/carsharing/db/" + dbFileName;
        return "D:/DOC/Programming_Course_ITMO/JetBrainsAcademy/CarSharing/" +
                "Car Sharing/task/src/carsharing/db/" + dbFileName;
    }

    @Override
    public void close() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (final SQLException ignored) {
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (final SQLException se) {
            se.printStackTrace();
        }
    }
}
