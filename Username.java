import javax.swing.*;

public class Username {
    public String getUserName() {
        // Membuat JFrame untuk input nama
        JFrame frame = new JFrame("Input Your Name");
        String name = JOptionPane.showInputDialog(frame, "Enter your name:");

        // Mengembalikan nama yang dimasukkan oleh pengguna, atau null jika dibatalkan
        return name;
    }
}
