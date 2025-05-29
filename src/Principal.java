import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal {

    private JPanel panelMain;
    private static JLabel background;
    private JLabel characLabel;
    private Timer timer;
    private int senconds = 0;

    // 钩子角度控制
    private double angle = 90;
    private double angleDelta = 1.5;
    private Timer hookTimer;

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

        // 添加钩子面板
        HookPanel hookPanel = new HookPanel();
        hookPanel.setOpaque(false); // 透明以显示背景
        hookPanel.setBounds(0, 0, 900, 600);
        background.add(hookPanel);

        // 摆动定时器
        hookTimer = new Timer(16, e -> {
            angle += angleDelta;
            if (angle >= 150 || angle <= 85) {
                angleDelta = -angleDelta;
            }
            hookPanel.setAngle(angle);
            hookPanel.repaint();
        });
        hookTimer.start();
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

    // 内部类：绘制钩子（旋转）
    class HookPanel extends JPanel {
        private Image hookImage;
        private double angle = 90;

        public HookPanel() {
            hookImage = new ImageIcon("src/img_free_/hook2.png").getImage();
        }

        public void setAngle(double angle) {
            this.angle = angle;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            int centerX = 680;  // 角色头顶坐标 x
            int centerY = 110;   // 角色头顶坐标 y

            double rad = Math.toRadians(angle);
            int length = 150;

            // 钩子末端
            int hookX = centerX + (int) (length * Math.cos(rad));
            int hookY = centerY + (int) (length * Math.sin(rad));

            // 绘制线
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(centerX, centerY, hookX, hookY);


            int imgWidth = 30;
            int imgHeight = 30;
            int offset = 8;  // 调整到刚好接上线

            g2.drawImage(hookImage, hookX - imgWidth / 2, hookY - offset, imgWidth, imgHeight, this);
            // 绘制钩子图片（钩子尾端）
        }
    }
}
