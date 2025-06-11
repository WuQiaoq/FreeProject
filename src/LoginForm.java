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
    private JPanel LoginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;

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
        exitButton = new JButton("Exit");
        LoginPanel.add(loginButton);
        LoginPanel.add(exitButton);

        loginButton.addActionListener(new ActionListener() {
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

        exitButton.addActionListener(e -> System.exit(0));
    }

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

    //登录成功就跳到主界面
    private void openMainFrame() {
        Principal principal = new Principal();
        principal.createAndShowGUI();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginForm");
        frame.setContentPane(new LoginForm().LoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);//不允许设置最大化
        frame.setLocationRelativeTo(null);//居中
        frame.setSize(400, 200);
        frame.setVisible(true);
    }
}


