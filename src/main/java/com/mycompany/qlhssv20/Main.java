
package com.mycompany.qlhssv20;

import com.mycompany.qlhssv20.controller.SinhVienController;
import com.mycompany.qlhssv20.view.SinhVienView;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SinhVienView view = new SinhVienView();
            new SinhVienController(view);
            view.setVisible(true);
        });
    }
}
