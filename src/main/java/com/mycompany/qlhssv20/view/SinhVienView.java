
package com.mycompany.qlhssv20.view;

import com.mycompany.qlhssv20.dao.LopDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class SinhVienView extends JFrame {

    private final Color NAVY = new Color(25, 42, 75);
    private final Color BLUE = new Color(49, 94, 168);
    private final Color BG = new Color(244, 247, 252);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(40, 52, 72);
    private final Color GREEN = new Color(32, 135, 83);
    private final Color RED = new Color(190, 55, 55);
    private final Color GRAY = new Color(100, 110, 125);

    // ===== FORM SINH VIÊN =====
    public JTextField txtMaSV = new JTextField(15);
    public JTextField txtHoTen = new JTextField(15);
    public JTextField txtNgaySinh = new JTextField(15);

    public JComboBox<String> cboGioiTinh =
            new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});

    public JComboBox<String> txtMaLop = new JComboBox<>();
    public JTextField txtSoDienThoai = new JTextField(15);
    public JTextField txtEmail = new JTextField(15);
    public JTextField txtDiaChi = new JTextField(15);

    // ===== TÌM KIẾM =====
    public JTextField txtTimKiem = new JTextField(20);
    public JButton btnTimKiem = new JButton("Tìm kiếm");

    // ===== NÚT SINH VIÊN =====
    public JButton btnThem = new JButton("＋ Thêm");
    public JButton btnSua = new JButton("✎ Sửa");
    public JButton btnXoa = new JButton("✕ Xóa");
    public JButton btnLamMoi = new JButton("Làm mới");
    public JButton btnTaiLai = new JButton("↻ Tải danh sách");

    public DefaultTableModel model;
    public JTable tblSinhVien;

    private final JPanel content = new JPanel(new CardLayout());

    private final JLabel lblTrang = new JLabel("Tổng quan");
    private final JLabel lblTongSV = new JLabel("0");
    private final JLabel lblTongLop = new JLabel("0");

    public SinhVienView() {
        setTitle("QUẢN LÝ SINH VIÊN UTT");
        setSize(1250, 780);
        setMinimumSize(new Dimension(1050, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(BG);

        add(createSidebar(), BorderLayout.WEST);
        add(createMainArea(), BorderLayout.CENTER);

        content.add(createDashboard(), "DASHBOARD");
        content.add(createStudentPage(), "SINHVIEN");
        content.add(createClassPage(), "LOP");

        showPage("DASHBOARD");
    }

    // =====================================================
    // SIDEBAR
    // =====================================================
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setBackground(NAVY);
        sidebar.setBorder(new EmptyBorder(20, 15, 20, 15));

        JLabel logo = new JLabel();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        URL logoURL = getClass().getResource("/images/utt.png");

        if (logoURL != null) {
            ImageIcon icon = new ImageIcon(logoURL);
            Image image = icon.getImage().getScaledInstance(
                    100, 100, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(image));
        } else {
            logo.setText("UTT");
            logo.setForeground(WHITE);
            logo.setFont(new Font("Arial", Font.BOLD, 30));
        }

        JLabel title = new JLabel("QUẢN LÝ SINH VIÊN");
        title.setForeground(WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 15));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(12));
        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(35));

        sidebar.add(createMenuButton("▦  Tổng quan",
                () -> showPage("DASHBOARD")));

        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(createMenuButton("♙  Quản lý sinh viên",
                () -> showPage("SINHVIEN")));

        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(createMenuButton("▤  Quản lý lớp học",
                () -> showPage("LOP")));

        sidebar.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("UTT - Quản lý đào tạo");
        footer.setForeground(new Color(200, 210, 230));
        footer.setFont(new Font("Arial", Font.PLAIN, 11));
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(footer);

        return sidebar;
    }

    private JButton createMenuButton(String text, Runnable action) {
        JButton button = new JButton(text);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setPreferredSize(new Dimension(200, 45));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(WHITE);
        button.setBackground(NAVY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> action.run());

        return button;
    }

    // =====================================================
    // KHU VỰC NỘI DUNG
    // =====================================================
    private JPanel createMainArea() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(WHITE);
        header.setBorder(new EmptyBorder(18, 25, 18, 25));

        lblTrang.setFont(new Font("Arial", Font.BOLD, 22));
        lblTrang.setForeground(NAVY);

        JLabel subtitle = new JLabel("HỆ THỐNG QUẢN LÝ ĐÀO TẠO");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitle.setForeground(GRAY);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(WHITE);
        titlePanel.add(lblTrang);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        content.setBackground(BG);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        main.add(header, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);

        return main;
    }

    private void showPage(String page) {
        CardLayout cl = (CardLayout) content.getLayout();
        cl.show(content, page);

        switch (page) {
            case "DASHBOARD":
                lblTrang.setText("Tổng quan");
                break;
            case "SINHVIEN":
                lblTrang.setText("Quản lý sinh viên");
                break;
            case "LOP":
                lblTrang.setText("Quản lý lớp học");
                break;
            default:
                lblTrang.setText("Quản lý đào tạo");
        }

        content.revalidate();
        content.repaint();
    }

    // =====================================================
    // TRANG TỔNG QUAN
    // =====================================================
    private JPanel createDashboard() {
        JPanel page = new JPanel(new BorderLayout(15, 15));
        page.setBackground(BG);

        JLabel welcome = new JLabel(
                "<html><h2>Chào mừng đến với hệ thống UTT</h2>"
                + "<p>Quản lý thông tin sinh viên và lớp học.</p></html>");

        welcome.setForeground(NAVY);
        page.add(welcome, BorderLayout.NORTH);

        GridLayout cards = new GridLayout(1, 2, 20, 20);
JPanel cardPanel = new JPanel(cards);
        cardPanel.setBackground(BG);

        cardPanel.add(createStatCard(
                "TỔNG SỐ SINH VIÊN", lblTongSV, BLUE));

        cardPanel.add(createStatCard(
                "TỔNG SỐ LỚP HỌC", lblTongLop, GREEN));

        page.add(cardPanel, BorderLayout.CENTER);

        JLabel note = new JLabel(
                "Chọn chức năng ở thanh menu bên trái để bắt đầu.");
        note.setForeground(GRAY);

        page.add(note, BorderLayout.SOUTH);

        return page;
    }

    private JPanel createStatCard(String title, JLabel value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 240)),
                new EmptyBorder(25, 25, 25, 25)));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setForeground(GRAY);

        value.setFont(new Font("Arial", Font.BOLD, 36));
        value.setForeground(color);

        card.add(lblTitle);
        card.add(Box.createVerticalStrut(20));
        card.add(value);

        return card;
    }

    // =====================================================
    // TRANG QUẢN LÝ SINH VIÊN
    // =====================================================
    private JPanel createStudentPage() {
        JPanel page = new JPanel(new BorderLayout(12, 12));
        page.setBackground(BG);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(BG);

        JPanel form = new JPanel(new GridLayout(4, 4, 12, 10));
        form.setBackground(WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("THÔNG TIN SINH VIÊN"),
                new EmptyBorder(12, 12, 12, 12)));

        addField(form, "Mã sinh viên:", txtMaSV);
        addField(form, "Họ tên:", txtHoTen);
        addField(form, "Ngày sinh (yyyy-MM-dd):", txtNgaySinh);
        addField(form, "Giới tính:", cboGioiTinh);

        addField(form, "Mã lớp:", txtMaLop);
        addField(form, "Số điện thoại:", txtSoDienThoai);
        addField(form, "Email:", txtEmail);
        addField(form, "Địa chỉ:", txtDiaChi);

        styleButton(btnThem, GREEN);
        styleButton(btnSua, BLUE);
        styleButton(btnXoa, RED);
        styleButton(btnLamMoi, GRAY);
        styleButton(btnTaiLai, NAVY);
        styleButton(btnTimKiem, BLUE);

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttons.setBackground(BG);

        buttons.add(btnThem);
        buttons.add(btnSua);
        buttons.add(btnXoa);
        buttons.add(btnLamMoi);
        buttons.add(btnTaiLai);

        JPanel searchPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder(
                "TÌM KIẾM SINH VIÊN"));

        txtTimKiem.setToolTipText(
                "Nhập mã sinh viên hoặc họ tên cần tìm");

        searchPanel.add(new JLabel("Mã sinh viên hoặc họ tên:"));
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTimKiem);

        top.add(form);
        top.add(buttons);
        top.add(searchPanel);

        String[] columns = {
            "Mã SV", "Họ tên", "Ngày sinh", "Giới tính",
            "Mã lớp", "Số điện thoại", "Email", "Địa chỉ"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblSinhVien = new JTable(model);
        styleTable(tblSinhVien);

        JScrollPane scroll = new JScrollPane(tblSinhVien);
        scroll.setBorder(BorderFactory.createTitledBorder(
                "DANH SÁCH SINH VIÊN"));

        page.add(top, BorderLayout.NORTH);
        page.add(scroll, BorderLayout.CENTER);

        return page;
    }

    private void addField(JPanel panel, String label, JComponent field) {
        JLabel lbl = new JLabel(label);
        lbl.setForeground(TEXT);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));

        field.setFont(new Font("Arial", Font.PLAIN, 13));

        panel.add(lbl);
        panel.add(field);
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(135, 36));
    }

    // =====================================================
    // TRANG QUẢN LÝ LỚP HỌC
    // =====================================================
    private JPanel createClassPage() {
        JPanel page = new JPanel(new BorderLayout(12, 12));
        page.setBackground(BG);

        JTextField txtMaLopQL = new JTextField(12);
        JTextField txtKhoa = new JTextField(12);
        JTextField txtNganh = new JTextField(12);

        DefaultTableModel classModel = new DefaultTableModel(
                new String[]{"Mã lớp", "Khóa", "Ngành"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tblLop = new JTable(classModel);
        styleTable(tblLop);

        LopDAO dao = new LopDAO();

        Runnable loadClasses = () -> {
            classModel.setRowCount(0);

            try {
                List<?> data = dao.getAll();

                for (Object item : data) {
                    if (item instanceof Object[]) {
                        classModel.addRow((Object[]) item);
                    } else {
                        classModel.addRow(new Object[]{item, "", ""});
                    }
                }

                lblTongLop.setText(String.valueOf(classModel.getRowCount()));

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Không thể tải danh sách lớp học.\n"
                        + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        };

        JPanel form = new JPanel(new GridLayout(2, 3, 10, 10));
        form.setBackground(WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("THÔNG TIN LỚP HỌC"),
                new EmptyBorder(12, 12, 12, 12)));

        form.add(new JLabel("Mã lớp:"));
        form.add(new JLabel("Khóa:"));
        form.add(new JLabel("Ngành:"));

        form.add(txtMaLopQL);
        form.add(txtKhoa);
        form.add(txtNganh);

        JButton btnThemLop = new JButton("Thêm lớp");
        JButton btnSuaLop = new JButton("Sửa lớp");
        JButton btnXoaLop = new JButton("Xóa lớp");
        JButton btnMoiLop = new JButton("Làm mới");
        JButton btnTaiLop = new JButton("Tải danh sách");

        styleButton(btnThemLop, GREEN);
        styleButton(btnSuaLop, BLUE);
        styleButton(btnXoaLop, RED);
        styleButton(btnMoiLop, GRAY);
        styleButton(btnTaiLop, NAVY);

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 8, 8));
        buttons.setBackground(BG);

        buttons.add(btnThemLop);
        buttons.add(btnSuaLop);
        buttons.add(btnXoaLop);
        buttons.add(btnMoiLop);
        buttons.add(btnTaiLop);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(BG);
        top.add(form);
        top.add(buttons);

        page.add(top, BorderLayout.NORTH);
        page.add(new JScrollPane(tblLop), BorderLayout.CENTER);

        tblLop.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }

            int row = tblLop.getSelectedRow();

            if (row >= 0) {
                txtMaLopQL.setText(String.valueOf(classModel.getValueAt(row, 0)));
                txtKhoa.setText(String.valueOf(classModel.getValueAt(row, 1)));
                txtNganh.setText(String.valueOf(classModel.getValueAt(row, 2)));
            }
        });

        btnThemLop.addActionListener(e -> {
            String ma = txtMaLopQL.getText().trim();
            String khoa = txtKhoa.getText().trim();
            String nganh = txtNganh.getText().trim();

            if (ma.isEmpty() || khoa.isEmpty() || nganh.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ thông tin lớp.");
                return;
            }

            try {
                dao.them(ma, khoa, nganh);
                loadClasses.run();

                JOptionPane.showMessageDialog(this,
                        "Đã gửi yêu cầu thêm lớp.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Không thể thêm lớp: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSuaLop.addActionListener(e -> {
            String ma = txtMaLopQL.getText().trim();
            String khoa = txtKhoa.getText().trim();
            String nganh = txtNganh.getText().trim();

            if (ma.isEmpty() || khoa.isEmpty() || nganh.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ thông tin lớp.");
                return;
            }

            try {
                dao.sua(ma, khoa, nganh);
                loadClasses.run();

                JOptionPane.showMessageDialog(this,
                        "Đã gửi yêu cầu cập nhật lớp.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Không thể sửa lớp: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXoaLop.addActionListener(e -> {
            String ma = txtMaLopQL.getText().trim();

            if (ma.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Hãy chọn hoặc nhập mã lớp cần xóa.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn xóa lớp " + ma + "?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    dao.xoa(ma);
                    loadClasses.run();

                    JOptionPane.showMessageDialog(this,
                            "Đã gửi yêu cầu xóa lớp.");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể xóa lớp: " + ex.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnMoiLop.addActionListener(e -> {
            txtMaLopQL.setText("");
            txtKhoa.setText("");
            txtNganh.setText("");
            tblLop.clearSelection();
        });

        btnTaiLop.addActionListener(e -> loadClasses.run());

        loadClasses.run();

        return page;
    }

    // =====================================================
    // ĐỊNH DẠNG BẢNG
    // =====================================================
    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setForeground(TEXT);
        table.setBackground(WHITE);
        table.setGridColor(new Color(225, 230, 240));
        table.setSelectionBackground(new Color(215, 229, 250));
        table.setSelectionForeground(NAVY);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 36));
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean selected,
                    boolean focus,
                    int row,
                    int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, selected, focus, row, column);

                label.setOpaque(true);
                label.setBackground(NAVY);
                label.setForeground(WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 12));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                return label;
            }
        });
    }

    // =====================================================
    // CẬP NHẬT THỐNG KÊ
    // =====================================================
    public void capNhatThongKe(int tongSinhVien, int tongLop) {
        lblTongSV.setText(String.valueOf(tongSinhVien));
        lblTongLop.setText(String.valueOf(tongLop));
    }
}
