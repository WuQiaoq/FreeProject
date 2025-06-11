import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Qiaoqiao
 * @version 1.0
 * @since 2025/05/25
 */

public class GamePanel {

    private JPanel panelMain;
    private static JLabel background;
    private JLabel characLabel; // caracter
    private Timer timer;
    private int second = 0;

    private JLabel hookLabel;
    private Timer hookTimer;
    private int hookLength = 100; // longitub de anzuelo
    private final int minLength = 100, maxLength = 400;
    private boolean extending = false, retracting = false; //钩子状态


    public GamePanel() {
        panelMain = new JPanel(null); //设置为绝对布局
        // 设置背景
        background = new JLabel(new ImageIcon("src/img_free_/back.jpg"));
        background.setBounds(0, 0, 900, 600);
        background.setLayout(null);
        panelMain.add(background);

        // caracter
        characLabel = new JLabel(new ImageIcon("src/img_free_/character_without.png"));
        characLabel.setBounds(680, 65, 200, 200);
        background.add(characLabel);

        // 计时器
        JLabel timerLabel = new JLabel("00:00");
        timerLabel.setBounds(10, 10, 100, 30);
        background.add(timerLabel);

        JButton startButton = new JButton("Start game");
        startButton.setBounds(380, 250, 140, 40);
        background.add(startButton);

        startButton.addActionListener(e -> {
            startButton.setVisible(false);
            startGame(timerLabel);
        });

    }

    private void startGame(JLabel timerLabel) {
        // añadir un submarino
        SeaCreature fish = new SeaCreature(0, 400, 4, background, "src/img_free_/sardina_right.png", false);
        fish.start();

        // 添加螃蟹
        SeaCreature cangrejo = new SeaCreature(0, 480, 3, background, "src/img_free_/cangrejo1.gif", true);
        cangrejo.start();

        //魔鬼鱼
        new Timer(3000, e -> {
            SeaCreature manta = new SeaCreature(800, 400, -4, background, "src/img_free_/manta.gif", true);
            manta.start();
            ((Timer)e.getSource()).stop();
        }).start();

        new Timer(7000, e -> {
            SeaCreature fish1 = new SeaCreature(0, 400, 3, background, "src/img_free_/sardina_right.png", false);
            fish1.start();
        }).start();


        // 游戏计时器：每秒更新
        timer = new Timer(1000, e -> {
            second++;
            int minutes = second / 60;
            int sec = second % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, sec));

            //结束游戏
            if (second >= 120 ) {
                timer.stop();
                hookTimer.stop();
                JOptionPane.showMessageDialog(panelMain, "Time is up", "Game over", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });

        timer.start();

        setHook();

    }

    private void setHook() {
        int baseX = 678;//数字越大，钩子越靠右
        int baseY = 15;

        // 添加钩子图标
        ImageIcon originalIcon = new ImageIcon("src/img_free_/hook.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(35, 145, Image.SCALE_SMOOTH);
        hookLabel = new JLabel(new ImageIcon(scaledImage));
        hookLabel.setSize(35, 145);
        background.add(hookLabel);

        // 初始化钩子位置
        hookLabel.setBounds(baseX - 30, baseY + hookLength, 35, 145);

        // Timer 每帧更新钩子状态
        hookTimer = new Timer(16, e -> {
            //是否更新了位置
            boolean updated = false;

            if (extending) {
                hookLength += 10; //钩子往下
                if (hookLength >= maxLength) {
                    hookLength = maxLength;
                    extending = false;// 停止延伸
                    retracting = true;//缩回鱼钩
                }
                //更新
                updated = true;
            }

            if (retracting) {
                hookLength -= 10;
                if (hookLength <= minLength) {
                    hookLength = minLength;
                    retracting = false;

                }
                updated = true;
            }

            //当改变了鱼钩的length时，才去改变钩子的位置
            if (updated) {
                int hookX = baseX - 30;//偏移值
                int hookY = baseY + hookLength;
                hookLabel.setBounds(hookX, hookY, 35, 145);
            }
        });
        hookTimer.start();

        // evento cuando presionado el raton
        background.addMouseListener(new MouseAdapter() {
            @Override

            public void mousePressed(MouseEvent e) {
                //静态的时候，鼠标点击会伸长钩子
                if (!extending && !retracting) {
                    extending = true;
                }
            }
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Fishfishfish");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

