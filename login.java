import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class LoginBD {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Pro Saber BD");
        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setBounds(20, 20, 80, 25);
        JTextField userText = new JTextField();
        userText.setBounds(100, 20, 165, 25);

        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setBounds(20, 60, 80, 25);
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(100, 60, 165, 25);

        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.setBounds(100, 100, 150, 25);

        JLabel result = new JLabel("");
        result.setBounds(20, 140, 300, 25);

        frame.add(userLabel);
        frame.add(userText);
        frame.add(passLabel);
        frame.add(passwordText);
        frame.add(loginButton);
        frame.add(result);

        frame.setSize(350, 230);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                String pass = new String(passwordText.getPassword());

                try {
                    Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/prosaber", "root", "TU_CONTRASEÑA"
                    );
                    String query = "SELECT rol FROM usuarios WHERE username = ? AND password = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, user);
                    stmt.setString(2, pass);

                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String rol = rs.getString("rol");
                        result.setText("Bienvenido " + user + " (" + rol + ")");
                        if (rol.equals("directivo")) {
                            JOptionPane.showMessageDialog(frame, "Redirigiendo a directivo...");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Redirigiendo a usuario...");
                        }
                    } else {
                        result.setText("Credenciales incorrectas");
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    result.setText("Error en la conexión");
                }
            }
        });
    }
}
