
package com.mycompany.qlhssv20.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:sqlserver://DESKTOP-CB6QBSM;"
            + "databaseName=QLHSSV20;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASSWORD = "hihi";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Ket noi SQL Server thanh cong!");
        } catch (SQLException e) {
            System.out.println("Ket noi that bai!");
            e.printStackTrace();
        }
    }
}
