import javax.swing.*;

public class StartGame {
    private JPanel panelLauncher;

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartGame");
        frame.setContentPane(new StartGame().panelLauncher);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setSize(400,200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
