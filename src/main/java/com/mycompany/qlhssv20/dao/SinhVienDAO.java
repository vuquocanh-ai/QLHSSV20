
package com.mycompany.qlhssv20.dao;

import com.mycompany.qlhssv20.model.DBConnection;
import com.mycompany.qlhssv20.model.SinhVien;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SinhVienDAO {

    // 1. Lấy danh sách sinh viên
    public List<SinhVien> getAll() throws SQLException {
        List<SinhVien> danhSach = new ArrayList<>();

        String sql = "SELECT MaSV, HoTen, NgaySinh, GioiTinh, "
                   + "MaLop, SoDienThoai, Email, DiaChi FROM SinhVien";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SinhVien sv = new SinhVien();
                sv.setMaSV(rs.getString("MaSV"));
                sv.setHoTen(rs.getString("HoTen"));

                Date ngaySinh = rs.getDate("NgaySinh");
                if (ngaySinh != null) {
                    sv.setNgaySinh(ngaySinh.toLocalDate());
                }

                sv.setGioiTinh(rs.getString("GioiTinh"));
                sv.setMaLop(rs.getString("MaLop"));
                sv.setSoDienThoai(rs.getString("SoDienThoai"));
                sv.setEmail(rs.getString("Email"));
                sv.setDiaChi(rs.getString("DiaChi"));

                danhSach.add(sv);
            }
        }

        return danhSach;
    }

    // 2. Thêm sinh viên
    public boolean them(SinhVien sv) throws SQLException {
        String sql = "INSERT INTO SinhVien "
                   + "(MaSV, HoTen, NgaySinh, GioiTinh, MaLop, "
                   + "SoDienThoai, Email, DiaChi) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sv.getMaSV());
            ps.setString(2, sv.getHoTen());

            if (sv.getNgaySinh() == null) {
                ps.setNull(3, java.sql.Types.DATE);
            } else {
                ps.setDate(3, Date.valueOf(sv.getNgaySinh()));
            }

            ps.setString(4, sv.getGioiTinh());
            ps.setString(5, sv.getMaLop());
            ps.setString(6, sv.getSoDienThoai());
            ps.setString(7, sv.getEmail());
            ps.setString(8, sv.getDiaChi());

            return ps.executeUpdate() > 0;
        }
    }

    // 3. Sửa thông tin sinh viên theo mã
    public boolean sua(SinhVien sv) throws SQLException {
        String sql = "UPDATE SinhVien SET HoTen=?, NgaySinh=?, "
                   + "GioiTinh=?, MaLop=?, SoDienThoai=?, "
                   + "Email=?, DiaChi=? WHERE MaSV=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sv.getHoTen());

            if (sv.getNgaySinh() == null) {
                ps.setNull(2, java.sql.Types.DATE);
            } else {
                ps.setDate(2, Date.valueOf(sv.getNgaySinh()));
            }

            ps.setString(3, sv.getGioiTinh());
            ps.setString(4, sv.getMaLop());
            ps.setString(5, sv.getSoDienThoai());
            ps.setString(6, sv.getEmail());
            ps.setString(7, sv.getDiaChi());
            ps.setString(8, sv.getMaSV());

            return ps.executeUpdate() > 0;
        }
    }

    // 4. Xóa sinh viên theo mã
    public boolean xoa(String maSV) throws SQLException {
        String sql = "DELETE FROM SinhVien WHERE MaSV=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSV);
            return ps.executeUpdate() > 0;
        }
    }
}
