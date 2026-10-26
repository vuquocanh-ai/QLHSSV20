
package com.mycompany.qlhssv20.controller;

import com.mycompany.qlhssv20.dao.SinhVienDAO;
import com.mycompany.qlhssv20.model.SinhVien;
import com.mycompany.qlhssv20.view.SinhVienView;
import com.mycompany.qlhssv20.dao.LopDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SinhVienController {

    private final SinhVienView view;
    private final SinhVienDAO dao;
    private final LopDAO lopDAO = new LopDAO();
    
    public SinhVienController(SinhVienView view) {
        this.view = view;
        this.dao = new SinhVienDAO();
        taiDanhSachLop();

       view.btnTaiLai.addActionListener(e -> taiDanhSach());
view.btnLamMoi.addActionListener(e -> lamMoi());
view.btnThem.addActionListener(e -> themSinhVien());
view.btnSua.addActionListener(e -> suaSinhVien());
view.btnXoa.addActionListener(e -> xoaSinhVien());

// Sự kiện tìm kiếm
view.btnTimKiem.addActionListener(e -> timKiemSinhVien());

// Nhấn Enter trong ô tìm kiếm cũng thực hiện tìm kiếm
view.txtTimKiem.addActionListener(e -> timKiemSinhVien());

        view.tblSinhVien.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()
                    && view.tblSinhVien.getSelectedRow() >= 0) {
                hienThiLenForm();
            }
        });

        taiDanhSach();
    }

    private void taiDanhSach() {
        try {
            List<SinhVien> danhSach = dao.getAll();
            view.model.setRowCount(0);

            for (SinhVien sv : danhSach) {
                view.model.addRow(new Object[]{
                    sv.getMaSV(),
                    sv.getHoTen(),
                    sv.getNgaySinh(),
                    sv.getGioiTinh(),
                    sv.getMaLop(),
                    sv.getSoDienThoai(),
                    sv.getEmail(),
                    sv.getDiaChi()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Không tải được danh sách sinh viên:\n" + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private SinhVien docForm() {
        String maSV = view.txtMaSV.getText().trim();
        String hoTen = view.txtHoTen.getText().trim();
        String ngaySinhText = view.txtNgaySinh.getText().trim();
        String gioiTinh = (String) view.cboGioiTinh.getSelectedItem();
        String maLop = view.txtMaLop.getSelectedItem() == null
        ? ""
        : view.txtMaLop.getSelectedItem().toString().trim();
        String sdt = view.txtSoDienThoai.getText().trim();
        String email = view.txtEmail.getText().trim();
        String diaChi = view.txtDiaChi.getText().trim();

        if (maSV.isEmpty() || hoTen.isEmpty()) {
            throw new IllegalArgumentException(
                    "Mã sinh viên và họ tên không được để trống!");
        }

        LocalDate ngaySinh = null;
        if (!ngaySinhText.isEmpty()) {
            ngaySinh = LocalDate.parse(ngaySinhText);
        }

        return new SinhVien(maSV, hoTen, ngaySinh, gioiTinh,
                maLop.isEmpty() ? null : maLop,
                sdt, email, diaChi);
    }

    private void themSinhVien() {
        try {
            SinhVien sv = docForm();
            if (dao.them(sv)) {
                JOptionPane.showMessageDialog(view, "Thêm sinh viên thành công!");
                taiDanhSach();
                lamMoi();
            }
        } catch (IllegalArgumentException | DateTimeParseException ex) {
            JOptionPane.showMessageDialog(view,
                    "Kiểm tra lại thông tin:\n" + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Không thêm được sinh viên:\n" + ex.getMessage());
        }
    }

    private void suaSinhVien() {
        try {
            SinhVien sv = docForm();
            if (dao.sua(sv)) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                taiDanhSach();
                lamMoi();
            } else {
                JOptionPane.showMessageDialog(view, "Không tìm thấy sinh viên để sửa.");
            }
        } catch (IllegalArgumentException | DateTimeParseException ex) {
            JOptionPane.showMessageDialog(view,
                    "Kiểm tra lại thông tin:\n" + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Không sửa được sinh viên:\n" + ex.getMessage());
        }
    }

    private void xoaSinhVien() {
        String maSV = view.txtMaSV.getText().trim();

        if (maSV.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Hãy chọn sinh viên cần xóa trong bảng!");
            return;
        }

        int chon = JOptionPane.showConfirmDialog(view,
                "Bạn có chắc muốn xóa sinh viên " + maSV + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (chon == JOptionPane.YES_OPTION) {
            try {
                if (dao.xoa(maSV)) {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    taiDanhSach();
                    lamMoi();
                } else {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy sinh viên.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view,
                        "Không xóa được sinh viên:\n" + ex.getMessage());
            }
        }
    }

    private void hienThiLenForm() {
        int row = view.tblSinhVien.getSelectedRow();
        if (row < 0) {
            return;
        }

        view.txtMaSV.setText(String.valueOf(view.model.getValueAt(row, 0)));
        view.txtHoTen.setText(String.valueOf(view.model.getValueAt(row, 1)));

        Object ngaySinh = view.model.getValueAt(row, 2);
        view.txtNgaySinh.setText(
                ngaySinh == null ? "" : ngaySinh.toString());

        view.cboGioiTinh.setSelectedItem(view.model.getValueAt(row, 3));
       view.txtMaLop.setSelectedItem(
               view.model.getValueAt(row, 4) == null ? ""
                : view.model.getValueAt(row, 4).toString());
        view.txtSoDienThoai.setText(
                view.model.getValueAt(row, 5) == null ? ""
                : view.model.getValueAt(row, 5).toString());
        view.txtEmail.setText(
                view.model.getValueAt(row, 6) == null ? ""
                : view.model.getValueAt(row, 6).toString());
        view.txtDiaChi.setText(
                view.model.getValueAt(row, 7) == null ? ""
                : view.model.getValueAt(row, 7).toString());
    }

    private void lamMoi() {
        view.txtMaSV.setText("");
        view.txtHoTen.setText("");
        view.txtNgaySinh.setText("");
        view.cboGioiTinh.setSelectedIndex(0);
        view.txtMaLop.setSelectedIndex(-1);
        view.txtSoDienThoai.setText("");
        view.txtEmail.setText("");
        view.txtDiaChi.setText("");
        view.tblSinhVien.clearSelection();
    }
    private void taiDanhSachLop() {
    try {
        view.txtMaLop.removeAllItems();

        for (String[] lop : lopDAO.getAll()) {
            view.txtMaLop.addItem(lop[0]);
        }

        view.txtMaLop.setSelectedIndex(-1);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(
                view,
                "Không tải được danh sách lớp:\n" + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
private void timKiemSinhVien() {
    String tuKhoa = view.txtTimKiem.getText().trim();

    if (tuKhoa.isEmpty()) {
        taiDanhSach();
        return;
    }

    try {
        List<SinhVien> danhSach = dao.getAll();
        view.model.setRowCount(0);

        for (SinhVien sv : danhSach) {
            String maSV = sv.getMaSV() == null
                    ? "" : sv.getMaSV();

            String hoTen = sv.getHoTen() == null
                    ? "" : sv.getHoTen();

            if (maSV.toLowerCase().contains(tuKhoa.toLowerCase())
                    || hoTen.toLowerCase().contains(tuKhoa.toLowerCase())) {

                view.model.addRow(new Object[]{
                    sv.getMaSV(),
                    sv.getHoTen(),
                    sv.getNgaySinh(),
                    sv.getGioiTinh(),
                    sv.getMaLop(),
                    sv.getSoDienThoai(),
                    sv.getEmail(),
                    sv.getDiaChi()
                });
            }
        }

        if (view.model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    view,
                    "Không tìm thấy sinh viên có mã hoặc họ tên: " + tuKhoa,
                    "Kết quả tìm kiếm",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(
                view,
                "Tìm kiếm thất bại:\n" + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


}
