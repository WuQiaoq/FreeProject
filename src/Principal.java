import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Qiaoqiao
 * @version 1.0
 * @since 2025/05/23
 */
public class Principal {

    private JPanel panelMain;
    private static JLabel background;
    private JLabel characLabel;
    private Timer timer;
    private int senconds = 0;

    // 钩子角度控制
    private double angle = 90; //初始状态为垂直
    private double angleDelta = 1.5; //控制钩子左右摆动速度和方向
    private Timer hookTimer; //每16ms刷新一次

    public Principal() {
        background = new JLabel(new ImageIcon("src/img_free_/back.jpg"));
        background.setBounds(0, 0, 900, 600);
        background.setLayout(null);

        characLabel = new JLabel(new ImageIcon("src/img_free_/character_without.png"));
        characLabel.setBounds(680, 65, 200, 200);
        background.add(characLabel);

        JLabel timerLabel = new JLabel("00:00");
        timerLabel.setBounds(10, 10, 100, 30);
        background.add(timerLabel);

        // 计时器：每秒更新一次时间
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                senconds++;
                int minutes = senconds / 60;
                int seconds = senconds % 60;
                timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
            }
        });
        timer.start();

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fishfishfish");
        frame.setContentPane(new Principal().panelMain = new JPanel(null));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().add(background);
        frame.setVisible(true);
    }


}
