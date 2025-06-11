import connectionDB.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm {
    public JPanel LoginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginForm() {
        LoginPanel = new JPanel(new GridLayout(3, 2, 10, 10)); //分成网格
        LoginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        LoginPanel.add(new JLabel("Nombre de usuario:"));
        usernameField = new JTextField();
        LoginPanel.add(usernameField);

        LoginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        LoginPanel.add(passwordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Registrar");

        LoginPanel.add(loginButton);
        LoginPanel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (authenicate(username, password)) {
                    JOptionPane.showMessageDialog(LoginPanel, "Inicio de sesión exitoso");
                    openMainFrame();
                    SwingUtilities.getWindowAncestor(LoginPanel).dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginPanel, "Nombre de usuario o contraseña incorrectos", "Error al iniciar sesión", JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginPanel, "Nombre de usuario y contraseñas no pueden estar vacios",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (createNewUser(username, password)) {
                    JOptionPane.showMessageDialog(LoginPanel, "Usuario ya registrado con exito", "Error", JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                }else {
                    JOptionPane.showMessageDialog(LoginPanel, "Usuario ya registrado con exito", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Verificar de inicio de sesión
     * @param username
     * @param password
     * @return
     */
    private boolean authenicate(String username, String password) {
        String sql = "SELECT * FROM usuarios WHERE nom = ? AND password = ?";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
            return rs.next();//有匹配用户就返回true

        }catch (SQLException e) {
            JOptionPane.showMessageDialog(LoginPanel, "Error en base de datos: " + e.getMessage()
            , "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // crear un nuevo usuario
    private boolean createNewUser(String username, String password) {
        String sql = "INSERT INTO usuarios (nom, password) VALUES (?, ?)";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            return true;

        }catch (SQLException e) {
            JOptionPane.showMessageDialog(LoginPanel, "Error en base de datos: " + e.getMessage()
                    , "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Después de iniciar sesión correctamente, será redirigido a la interfaz principal.
     */
    private void openMainFrame() {
        GamePanel principal = new GamePanel();
        principal.createAndShowGUI();
    }


}
