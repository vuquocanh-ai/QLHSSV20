
package com.mycompany.qlhssv20.dao;

import com.mycompany.qlhssv20.model.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LopDAO {

    public List<String[]> getAll() throws SQLException {
        List<String[]> danhSach = new ArrayList<>();

        String sql = "SELECT MaLop, Khoa, Nganh FROM Lop ORDER BY MaLop";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                danhSach.add(new String[]{
                    rs.getString("MaLop"),
                    rs.getString("Khoa"),
                    rs.getString("Nganh")
                });
            }
        }

        return danhSach;
    }

    public boolean them(String maLop, String khoa, String nganh)
            throws SQLException {
        String sql = "INSERT INTO Lop (MaLop, Khoa, Nganh) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maLop);
            ps.setString(2, khoa);
            ps.setString(3, nganh);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean sua(String maLop, String khoa, String nganh)
            throws SQLException {
        String sql = "UPDATE Lop SET Khoa = ?, Nganh = ? WHERE MaLop = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, khoa);
            ps.setString(2, nganh);
            ps.setString(3, maLop);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean xoa(String maLop) throws SQLException {
        String sql = "DELETE FROM Lop WHERE MaLop = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maLop);
            return ps.executeUpdate() > 0;
        }
    }
}
