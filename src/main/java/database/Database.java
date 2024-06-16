package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	public static Connection getConnection() {
		String url = "jdbc:postgresql://localhost:5432/edupay";
		String user = "postgres";
		String password = "postgres";

		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {
			// Connection object
			Connection connection = null;

			// Establish the connection
			connection = DriverManager.getConnection(url, user, password);

			return connection;
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
