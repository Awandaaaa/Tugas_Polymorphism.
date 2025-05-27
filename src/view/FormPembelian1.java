package view;

import Form.FormCariBarang;
import Form.FormCariBarang1;
import Form.FormTambahBarang;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import main.Koneksi;
import main.Session;

import java.util.Date;

public class FormPembelian1 extends javax.swing.JPanel {

    /**
     * Creates new form FormPembelian
     */
    private boolean sedangMenyimpan = false;

    public FormPembelian1() {
        initComponents();
        t_kasir.setText(Session.getNama());
        label_users.setText("Login sebagai: " + Session.getRole());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
            "Barcode", "Nama Barang", "Satuan", "Jumlah", "Harga", "Total", "Supplier", "Tanggal"
        });
        tbl_pembelian.setModel(model);

        // DELETE → hapus baris terpilih
        tbl_pembelian.getInputMap(tbl_pembelian.WHEN_FOCUSED).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "hapusBaris");
        tbl_pembelian.getActionMap().put("hapusBaris", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusBarisTerpilih();
            }
        });

// CTRL + DELETE → reset transaksi
        tbl_pembelian.getInputMap(tbl_pembelian.WHEN_FOCUSED).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.CTRL_DOWN_MASK), "resetTransaksi");
        tbl_pembelian.getActionMap().put("resetTransaksi", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTransaksiPembelian();
            }
        });

        // Style tombol
        btn_simpan.setText("SIMPAN");
        btn_simpan.setBackground(new java.awt.Color(70, 130, 180)); // warna biru steel blue
        btn_simpan.setForeground(Color.WHITE);
        btn_simpan.setFont(new java.awt.Font("Serif", Font.BOLD, 12));
        btn_simpan.setFocusPainted(false);
        btn_simpan.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn_simpan.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn_simpan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_hapus.setBackground(new java.awt.Color(100, 149, 237)); // Cornflower Blue
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_simpan.setBackground(new java.awt.Color(70, 130, 180));
            }
        });

        // Style tombol
//        btn_batal.setText("BATAL");
//        btn_batal.setBackground(new java.awt.Color(70, 130, 180)); // warna biru steel blue
//        btn_batal.setForeground(Color.WHITE);
//        btn_batal.setFont(new java.awt.Font("Serif", Font.BOLD, 12));
//        btn_batal.setFocusPainted(false);
//        btn_batal.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
//        btn_batal.setCursor(new Cursor(Cursor.HAND_CURSOR));
//
//        // Hover effect
//        btn_batal.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                btn_batal.setBackground(new java.awt.Color(100, 149, 237)); // Cornflower Blue
//            }
//
//            @Override
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                btn_batal.setBackground(new java.awt.Color(70, 130, 180));
//            }
//        });

        // Style tombol
        btn_tambah.setText("TAMBAH");
        btn_tambah.setBackground(new java.awt.Color(70, 130, 180)); // warna biru steel blue
        btn_tambah.setForeground(Color.WHITE);
        btn_tambah.setFont(new java.awt.Font("Serif", Font.BOLD, 12));
        btn_tambah.setFocusPainted(false);
        btn_tambah.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn_tambah.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn_tambah.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_tambah.setBackground(new java.awt.Color(100, 149, 237)); // Cornflower Blue
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_tambah.setBackground(new java.awt.Color(70, 130, 180));
            }
        });

        // Style tombol
        btn_caribarang.setText("Cari Barang");
        btn_caribarang.setBackground(new java.awt.Color(70, 130, 180)); // warna biru steel blue
        btn_caribarang.setForeground(Color.WHITE);
        btn_caribarang.setFont(new java.awt.Font("Serif", Font.BOLD, 12));
        btn_caribarang.setFocusPainted(false);
        btn_caribarang.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn_caribarang.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn_caribarang.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_caribarang.setBackground(new java.awt.Color(100, 149, 237)); // Cornflower Blue
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_caribarang.setBackground(new java.awt.Color(70, 130, 180));
            }
        });

        // Style tombol
        btn_hapus.setText("HAPUS");
        btn_hapus.setBackground(new java.awt.Color(70, 130, 180)); // warna biru steel blue
        btn_hapus.setForeground(Color.WHITE);
        btn_hapus.setFont(new java.awt.Font("Serif", Font.BOLD, 12));
        btn_hapus.setFocusPainted(false);
        btn_hapus.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn_hapus.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn_hapus.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_hapus.setBackground(new java.awt.Color(100, 149, 237)); // Cornflower Blue
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_hapus.setBackground(new java.awt.Color(70, 130, 180));
            }
        });
    }

    public void isiDataBarangDariBarcode(String barcode) {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = """
            SELECT 
                b.barcode, 
                b.Nama_barang, 
                b.Harga, 
                b.Stok, 
                b.Satuan, 
                s.Nama AS nama_supplier
            FROM 
                barang b
            LEFT JOIN 
                supplier s ON b.id_Supplier = s.id_Supplier
            WHERE 
                b.barcode = ?
        """;

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, barcode);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                t_barcode.setText(rs.getString("barcode"));
                t_namabarang.setText(rs.getString("Nama_barang"));
                t_harga.setText(String.valueOf(rs.getDouble("Harga")));
                t_stok.setText(String.valueOf(rs.getInt("Stok")));
                t_satuan.setText(rs.getString("Satuan"));
                t_supplier.setText(rs.getString("nama_supplier"));

                // Set tanggal hari ini
                d_tanggal.setDate(new java.util.Date()); // untuk JDateChooser
                t_jumlah.setText("1");
                t_jumlah.requestFocus();

            } else {
//                JOptionPane.showMessageDialog(null, "Barang dengan barcode tersebut tidak ditemukan.");
                kosongkanFieldBarang();
            }

            rs.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormPembelian1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void kosongkanFieldBarang() {
        t_barcode.setText("");
        t_namabarang.setText("");
        t_harga.setText("");
        t_stok.setText("");
        t_satuan.setText("");
        t_supplier.setText("");
        t_jumlah.setText("");
        d_tanggal.setDate(null);
    }

    private void hitungTotalDanKembalian() {
        try {
            double subtotal = 0;

            for (int i = 0; i < tbl_pembelian.getRowCount(); i++) {
                subtotal += Double.parseDouble(tbl_pembelian.getValueAt(i, 5).toString());
            }

            t_subtotal.setText(String.valueOf(subtotal));

            double diskon = t_diskon.getText().isEmpty() ? 0 : Double.parseDouble(t_diskon.getText());
            double total = subtotal - diskon;
            t_total.setText(String.valueOf(total));

            double bayar = t_bayar.getText().isEmpty() ? 0 : Double.parseDouble(t_bayar.getText());
            double kembali = bayar - total;
            t_kembali.setText(String.valueOf(kembali));

        } catch (Exception e) {
            Logger.getLogger(FormPembelian1.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void tambahKeTabelPembelian() {
        try {
            String barcode = t_barcode.getText();
            String nama = t_namabarang.getText();
            String satuan = t_satuan.getText();
            int jumlahBaru = Integer.parseInt(t_jumlah.getText());
            double harga = Double.parseDouble(t_harga.getText());
            String supplier = t_supplier.getText();

            // Format tanggal
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String tanggal = sdf.format(d_tanggal.getDate());

            DefaultTableModel model = (DefaultTableModel) tbl_pembelian.getModel();
            boolean sudahAda = false;

            for (int i = 0; i < model.getRowCount(); i++) {
                String barcodeTabel = model.getValueAt(i, 0).toString();
                if (barcodeTabel.equals(barcode)) {
                    // Barang sudah ada, update jumlah dan total
                    int jumlahLama = Integer.parseInt(model.getValueAt(i, 3).toString());
                    int jumlahBaruTotal = jumlahLama + jumlahBaru;
                    double totalBaru = jumlahBaruTotal * harga;

                    model.setValueAt(jumlahBaruTotal, i, 3); // kolom jumlah
                    model.setValueAt(totalBaru, i, 5);       // kolom total
                    sudahAda = true;
                    break;
                }
            }

            if (!sudahAda) {
                // Barang belum ada, tambahkan baris baru
                double total = harga * jumlahBaru;
                model.addRow(new Object[]{barcode, nama, satuan, jumlahBaru, harga, total, supplier, tanggal});
            }

            hitungTotalDanKembalian(); // update subtotal dan kembali

            // Kosongkan input
            t_barcode.setText("");
            t_namabarang.setText("");
            t_satuan.setText("");
            t_jumlah.setText("");
            t_harga.setText("");
            t_supplier.setText("");
            t_stok.setText("");
            t_barcode.requestFocus();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid.");
            Logger.getLogger(FormPembelian1.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void simpanTransaksiPembelian() {
        if (!validasiSebelumSimpan() || sedangMenyimpan) {
            return;
        }
        sedangMenyimpan = true;

        Connection conn = null;
        try {
            conn = Koneksi.getConnection();
            conn.setAutoCommit(false);

            String tanggal = new java.text.SimpleDateFormat("yyyy-MM-dd").format(d_tanggal.getDate());
            double total = parseCurrency(t_total.getText());
            double persenDiskon = parsePersen(t_diskon.getText());
            double diskon = (persenDiskon / 100.0) * total;
            double bayar = parseCurrency(t_bayar.getText());
            double kembalian = bayar - (total - diskon);

            int idUser = getIdUser(conn, t_kasir.getText().trim());
            int idSupplierHeader = ambilAtauBuatIdSupplier(conn, tbl_pembelian.getValueAt(0, 6).toString().trim());

            int idPembelian = simpanHeaderPembelian(conn, idSupplierHeader, idUser, tanggal, total, diskon, bayar, kembalian);
            simpanDetailPembelian(conn, idPembelian);

            conn.commit();
            resetForm();
            showDialogSukses();  // ⬅️ Ganti JOptionPane dengan custom dialog

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            showInfoDialog("Gagal menyimpan transaksi: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            sedangMenyimpan = false;
        }
    }

    private void showDialogSukses() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(parentWindow, "Sukses", Dialog.ModalityType.APPLICATION_MODAL);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());

        JLabel label = new JLabel("Transaksi pembelian berhasil disimpan!", JLabel.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        dialog.add(label, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        JPanel panel = new JPanel();
        panel.add(okButton);
        dialog.add(panel, BorderLayout.SOUTH);

        dialog.getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(e -> {
            dialog.dispose();
            // Fokus kembali ke barcode setelah dialog tertutup
            SwingUtilities.invokeLater(() -> {
                btn_simpan.setEnabled(true);
                t_barcode.requestFocus();
            });
        });

        dialog.pack();
        dialog.setLocationRelativeTo(this); // ⬅️ Tengah form
        dialog.setVisible(true);
    }

    private void restoreFocusAfterDialog() {
        SwingUtilities.invokeLater(() -> {
            btn_simpan.setEnabled(true);   // Aktifkan kembali tombol simpan
            t_barcode.requestFocus();      // Fokus ke barcode
        });
    }

    private boolean validasiSebelumSimpan() {
        if (tbl_pembelian.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Belum ada barang yang dibeli!");
            return false;
        }
        if (t_total.getText().trim().isEmpty() || t_diskon.getText().trim().isEmpty() || t_bayar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Field Total, Diskon, dan Bayar tidak boleh kosong!");
            return false;
        }
        if (d_tanggal.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Tanggal pembelian belum diisi!");
            return false;
        }

        double total = parseCurrency(t_total.getText());
        double persenDiskon = parsePersen(t_diskon.getText());  // misalnya 10 berarti 10%
        double diskon = (persenDiskon / 100.0) * total;
        double bayar = parseCurrency(t_bayar.getText());

        if (bayar < (total - diskon)) {
            JOptionPane.showMessageDialog(this, "Jumlah bayar kurang dari total setelah diskon!");
            return false;
        }

        return true;
    }

    private double parseCurrency(String text) {
        return Double.parseDouble(text.trim().replace(",", ""));
    }

    private double parsePersen(String text) {
        text = text.trim().replace("%", "");
        if (text.isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(text);
    }

    private int getIdUser(Connection conn, String namaKasir) throws SQLException {
        String sql = "SELECT Id_user FROM users WHERE nama = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaKasir);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Id_user");
            }
            throw new SQLException("Kasir tidak ditemukan!");
        }
    }

    private int simpanHeaderPembelian(Connection conn, int idSupplier, int idUser, String tanggal,
            double total, double diskon, double bayar, double kembalian) throws SQLException {
        String sql = "INSERT INTO pembelian (Id_Supplier, id_user, Tgl_Pembelian, total, diskon, bayar, kembalian) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idSupplier);
            ps.setInt(2, idUser);
            ps.setDate(3, java.sql.Date.valueOf(tanggal));
            ps.setDouble(4, total);
            ps.setDouble(5, diskon);
            ps.setDouble(6, bayar);
            ps.setDouble(7, kembalian);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("Gagal mendapatkan ID Pembelian!");
        }
    }

    private void simpanDetailPembelian(Connection conn, int idPembelian) throws SQLException {
        String sql = "INSERT INTO pembelianrinci (Jumlah_Beli, Satuan, Harga_Satuan, Total, Id_Pembelian, Id_Barang, barcode, id_supplier) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            Map<String, Integer> supplierCache = new HashMap<>();

            for (int i = 0; i < tbl_pembelian.getRowCount(); i++) {
                String barcode = tbl_pembelian.getValueAt(i, 0).toString().trim();
                String namaBarang = tbl_pembelian.getValueAt(i, 1).toString().trim();
                String satuan = tbl_pembelian.getValueAt(i, 2).toString().trim();
                int jumlah = Integer.parseInt(tbl_pembelian.getValueAt(i, 3).toString().trim());
                double harga = Double.parseDouble(tbl_pembelian.getValueAt(i, 4).toString().trim());
                double totalItem = Double.parseDouble(tbl_pembelian.getValueAt(i, 5).toString().trim());
                String supplierNama = tbl_pembelian.getValueAt(i, 6).toString().trim();
                String kategori = "Alat Tulis"; // Default / statis

                int idSupplier = supplierCache.computeIfAbsent(supplierNama, nama -> {
                    try {
                        return ambilAtauBuatIdSupplier(conn, nama);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return -1;
                    }
                });

                if (idSupplier == -1) {
                    continue;
                }

                int idBarang = ambilIdBarangDariBarcode(conn, barcode);
                if (idBarang == -1) {
                    // Insert barang baru
                    String sqlInsert = "INSERT INTO barang (barcode, Nama_barang, Kategori, Satuan, Harga, Stok, Id_Supplier) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement psBarang = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                        psBarang.setString(1, barcode);
                        psBarang.setString(2, namaBarang);
                        psBarang.setString(3, kategori);
                        psBarang.setString(4, satuan);
                        psBarang.setDouble(5, harga);
                        psBarang.setInt(6, jumlah);
                        psBarang.setInt(7, idSupplier);
                        psBarang.executeUpdate();

                        ResultSet rs = psBarang.getGeneratedKeys();
                        if (rs.next()) {
                            idBarang = rs.getInt(1);
                        } else {
                            JOptionPane.showMessageDialog(this, "Gagal menambahkan barang baru di baris " + (i + 1));
                            continue;
                        }
                    }
                } else {
                    tambahkanStok(conn, barcode, jumlah);
                }

                // Simpan ke pembelian rinci
                ps.setInt(1, jumlah);
                ps.setString(2, satuan);
                ps.setDouble(3, harga);
                ps.setDouble(4, totalItem);
                ps.setInt(5, idPembelian);
                ps.setInt(6, idBarang);
                ps.setString(7, barcode);
                ps.setInt(8, idSupplier);
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }

    private void resetForm() {
        // Kosongkan tabel
        DefaultTableModel model = (DefaultTableModel) tbl_pembelian.getModel();
        model.setRowCount(0);

        // Kosongkan field
        t_total.setText("");
        t_subtotal.setText("");
        t_diskon.setText("");
        t_bayar.setText("");
        t_kembali.setText("");
        kosongkanFieldBarang(); // Fungsi yang sudah ada untuk reset input barang
    }

    private int ambilAtauBuatIdSupplier(Connection conn, String namaSupplier) throws SQLException {
        String sqlSelect = "SELECT Id_Supplier FROM supplier WHERE Nama = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            ps.setString(1, namaSupplier);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Id_Supplier");
            }
        }

        // Jika tidak ditemukan, buat baru tanpa menyet Id_Supplier
        String sqlInsert = "INSERT INTO supplier (Nama) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, namaSupplier);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Gagal menyisipkan supplier baru.");
            }
        }
    }

    private int ambilIdBarangDariBarcode(Connection conn, String barcode) throws SQLException {
        if (barcode == null || barcode.trim().isEmpty()) {
            return -1;
        }

        String sql = "SELECT Id_barang FROM barang WHERE barcode = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barcode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Id_barang");
            } else {
                // Optional: log jika tidak ditemukan
                System.out.println("Barang dengan barcode '" + barcode + "' tidak ditemukan.");
            }
        }
        return -1;
    }

    private void tambahkanStok(Connection conn, String barcode, int jumlah) throws SQLException {
        if (barcode == null || barcode.trim().isEmpty()) {
            return;
        }

        String sql = "UPDATE barang SET stok = stok + ? WHERE barcode = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jumlah);
            ps.setString(2, barcode);
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("Gagal menambahkan stok. Barcode tidak ditemukan: " + barcode);
            }
        }
    }

    private void hapusBarisTerpilih() {
        int selectedRow = tbl_pembelian.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tbl_pembelian.getModel();

        if (model.getRowCount() == 0) {
            showInfoDialog("Tabel pembelian kosong.");
            return;
        }

        if (selectedRow == -1) {
            showInfoDialog("Pilih baris yang ingin dihapus.");
            return;
        }

        showConfirmDialog("Yakin ingin menghapus item ini?", () -> {
            model.removeRow(selectedRow);
            hitungTotalDanKembalian();
            requestFocusKeBarcode();
        });
    }

    private void showSafeConfirmDialog(String message, Runnable onYes) {
        final JDialog dialog = new JDialog((Frame) null, "Konfirmasi", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());

        JLabel label = new JLabel(message);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton yesButton = new JButton("Ya");
        JButton noButton = new JButton("Tidak");

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        yesButton.addActionListener(e -> {
            dialog.dispose();
            SwingUtilities.invokeLater(onYes); // pastikan dijalankan setelah dialog ditutup
        });

        noButton.addActionListener(e -> dialog.dispose());

        // Set YES sebagai default button untuk ENTER
        dialog.getRootPane().setDefaultButton(yesButton);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void resetTransaksiPembelian() {
        DefaultTableModel model = (DefaultTableModel) tbl_pembelian.getModel();

        if (model.getRowCount() == 0) {
            showInfoDialog("Tidak ada data untuk dihapus.");
            return;
        }

        showConfirmDialog("Yakin ingin menghapus SEMUA item dari transaksi?", () -> {
            model.setRowCount(0);
            kosongkanFieldBarang();
            hitungTotalDanKembalian();
            requestFocusKeBarcode();
        });
    }

    private void showInfoDialog(String pesan) {
        // Hindari loop ENTER -> trigger tombol lagi
        JOptionPane optionPane = new JOptionPane(pesan, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, "Informasi");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                requestFocusKeBarcode();
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                requestFocusKeBarcode();
            }
        });

        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void showConfirmDialog(String pesan, Runnable aksiJikaYes) {
        JOptionPane optionPane = new JOptionPane(pesan, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(this, "Konfirmasi");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                requestFocusKeBarcode();
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                requestFocusKeBarcode();
            }
        });

        dialog.setModal(true);
        dialog.setVisible(true);

        Object selectedValue = optionPane.getValue();
        if (selectedValue instanceof Integer && (Integer) selectedValue == JOptionPane.YES_OPTION) {
            aksiJikaYes.run();
        }
    }

    private void requestFocusKeBarcode() {
        SwingUtilities.invokeLater(() -> t_barcode.requestFocus());
    }
    
   

    
    public void terimaDataBarang(String kode, String nama, String satuan, double harga, int stok, String supplier) {
    t_barcode.setText(kode);
    t_namabarang.setText(nama);
    t_satuan.setText(satuan);
    t_harga.setText(String.format("%.0f", harga)); // Tanpa desimal
    t_stok.setText(String.valueOf(stok));
    t_supplier.setText(supplier);
    t_jumlah.setText("1");

    // Set tanggal hari ini
    d_tanggal.setDate(new Date());

    // Fokus ke field jumlah
    t_jumlah.requestFocus();
    SwingUtilities.invokeLater(() -> t_jumlah.selectAll());
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new main.gradasiwarna();
        jLabel3 = new javax.swing.JLabel();
        label_users = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        t_barcode = new javax.swing.JTextField();
        t_kasir = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        t_namabarang = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        t_harga = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        t_stok = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        t_jumlah = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_pembelian = new javax.swing.JTable();
        t_total = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        t_subtotal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_caribarang = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        d_tanggal = new com.toedter.calendar.JDateChooser();
        t_diskon = new javax.swing.JTextField();
        t_bayar = new javax.swing.JTextField();
        t_kembali = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        t_satuan = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        t_supplier = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 255));

        jLabel3.setBackground(new java.awt.Color(0, 51, 255));
        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("08 - 06 - 2025");

        label_users.setBackground(new java.awt.Color(0, 51, 255));
        label_users.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        label_users.setForeground(new java.awt.Color(255, 255, 255));
        label_users.setText("Login Sebagai:");

        jLabel6.setBackground(new java.awt.Color(0, 51, 255));
        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Data Pembelian");

        btn_simpan.setText("simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(0, 51, 255));
        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Barcode");

        t_barcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_barcodeKeyReleased(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(0, 51, 255));
        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nama Barang");

        jLabel11.setBackground(new java.awt.Color(0, 51, 255));
        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Harga");

        t_harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_hargaKeyReleased(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(0, 51, 255));
        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Stok");

        jLabel13.setBackground(new java.awt.Color(0, 51, 255));
        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Jumlah");

        t_jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_jumlahKeyReleased(evt);
            }
        });

        tbl_pembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbl_pembelian);

        jLabel15.setBackground(new java.awt.Color(0, 51, 255));
        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Total");

        jLabel16.setBackground(new java.awt.Color(0, 51, 255));
        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Sub Total");

        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_caribarang.setText("Cari Barang");
        btn_caribarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_caribarangActionPerformed(evt);
            }
        });

        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        jLabel19.setBackground(new java.awt.Color(0, 51, 255));
        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Tanggal");

        t_diskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_diskonActionPerformed(evt);
            }
        });
        t_diskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_diskonKeyReleased(evt);
            }
        });

        t_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_bayarKeyReleased(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(0, 51, 255));
        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Diskon");

        jLabel18.setBackground(new java.awt.Color(0, 51, 255));
        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Bayar");

        jLabel20.setBackground(new java.awt.Color(0, 51, 255));
        jLabel20.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Kembali");

        jLabel14.setBackground(new java.awt.Color(0, 51, 255));
        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Supplier");

        jLabel21.setBackground(new java.awt.Color(0, 51, 255));
        jLabel21.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Satuan");

        t_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_supplierActionPerformed(evt);
            }
        });
        t_supplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_supplierKeyReleased(evt);
            }
        });

        jLabel22.setBackground(new java.awt.Color(0, 51, 255));
        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Kasir");

        jLabel1.setText("%");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_caribarang, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(t_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(t_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(t_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel11))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel10)
                                            .addComponent(t_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel3))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel22)
                                            .addComponent(t_kasir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label_users)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(t_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(t_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(d_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel21))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel13)
                                                    .addComponent(jLabel14)
                                                    .addComponent(t_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(0, 320, Short.MAX_VALUE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(27, 27, 27)
                                .addComponent(t_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(t_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(t_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(38, 38, 38)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(t_total, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(t_subtotal))))
                        .addGap(60, 60, 60)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label_users)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(t_kasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_simpan)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel10)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(t_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(t_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(d_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(t_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(32, 32, 32))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel21)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(t_stok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah)
                    .addComponent(btn_caribarang)
                    .addComponent(btn_hapus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(t_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20))
                    .addComponent(t_bayar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(t_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(t_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)))
                    .addComponent(t_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        hapusBarisTerpilih();
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_caribarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_caribarangActionPerformed
        FormCariBarang1 form = new FormCariBarang1((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), true, this);
        form.setLocationRelativeTo(this);
        form.setVisible(true);
    }//GEN-LAST:event_btn_caribarangActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        tambahKeTabelPembelian();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        simpanTransaksiPembelian();
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void t_barcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_barcodeKeyReleased
        isiDataBarangDariBarcode(t_barcode.getText().trim());
    }//GEN-LAST:event_t_barcodeKeyReleased

    private void t_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_bayarKeyReleased
        hitungTotalDanKembalian();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            t_diskon.requestFocus();
        }
    }//GEN-LAST:event_t_bayarKeyReleased

    private void t_jumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_jumlahKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tambahKeTabelPembelian();
            t_barcode.requestFocus();
        }
    }//GEN-LAST:event_t_jumlahKeyReleased

    private void t_diskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_diskonActionPerformed
        hitungTotalDanKembalian();
    }//GEN-LAST:event_t_diskonActionPerformed

    private void t_supplierKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_supplierKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_t_supplierKeyReleased

    private void t_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_supplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_supplierActionPerformed

    private void t_hargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_hargaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_t_hargaKeyReleased

    private void t_diskonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_diskonKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            simpanTransaksiPembelian();
        }
    }//GEN-LAST:event_t_diskonKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_caribarang;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private com.toedter.calendar.JDateChooser d_tanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_users;
    private javax.swing.JTextField t_barcode;
    private javax.swing.JTextField t_bayar;
    private javax.swing.JTextField t_diskon;
    private javax.swing.JTextField t_harga;
    private javax.swing.JTextField t_jumlah;
    private javax.swing.JTextField t_kasir;
    private javax.swing.JTextField t_kembali;
    private javax.swing.JTextField t_namabarang;
    private javax.swing.JTextField t_satuan;
    private javax.swing.JTextField t_stok;
    private javax.swing.JTextField t_subtotal;
    private javax.swing.JTextField t_supplier;
    private javax.swing.JTextField t_total;
    private javax.swing.JTable tbl_pembelian;
    // End of variables declaration//GEN-END:variables

}
