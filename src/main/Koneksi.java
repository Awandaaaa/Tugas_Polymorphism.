package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static final String URL = "jdbc:mysql://localhost:3306/atk?serverTimezone=UTC"; // Tambah zona waktu untuk JDBC 8+
    private static final String USER = "root"; // Ubah sesuai kebutuhan
    private static final String PASSWORD = "";  // Ubah sesuai kebutuhan

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver tidak ditemukan: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

