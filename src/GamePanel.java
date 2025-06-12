import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;
import java.util.ArrayList;


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
    private final int minLength = 100, maxLength = 440;
    private boolean extending = false, retracting = false; //伸出， 缩回
    private SeaCreature hookedCreature = null; // los peces capturados

    private List<SeaCreature> creatureList = new ArrayList<>(); // array list para guarda los critura marina

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
        creatureList.add(fish);

        // cangrejo
        SeaCreature cangrejo = new SeaCreature(0, 480, 3, background, "src/img_free_/cangrejo1.gif", true);
        cangrejo.start();
        creatureList.add(cangrejo);

        //manta
        new Timer(4000, e -> {
            SeaCreature manta = new SeaCreature(800, 400, -4, background, "src/img_free_/manta.gif", true);
            creatureList.add(manta);
            manta.start();
            ((Timer)e.getSource()).stop();
        }).start();

        //7 segundos un ciclo
        new Timer(7000, e -> {
            SeaCreature fish1 = new SeaCreature(0, 400, 3, background, "src/img_free_/sardina_right.png", false);
            creatureList.add(fish1);
            fish1.start();
        }).start();

        //30 segundos
        new Timer(30000, e -> {
            SeaCreature orbe = new SeaCreature(0, 420, 2, background, "src/img_free_/orbe.gif", true);
            orbe.start();
            creatureList.add(orbe);
            ((Timer)e.getSource()).stop();//llamar su stop()
        }).start();


        // actualizado cada segundo
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
        int hookBaseX = 678; //数字越大，钩子越靠右
        int hookBaseY = 15;

        // 添加钩子图标
        ImageIcon originalIcon = new ImageIcon("src/img_free_/hook.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(35, 145, Image.SCALE_SMOOTH);
        hookLabel = new JLabel(new ImageIcon(scaledImage));
        hookLabel.setSize(35, 145);
        background.add(hookLabel);

        // 初始化钩子位置
        hookLabel.setBounds(hookBaseX - 30, hookBaseY + hookLength, 35, 145);

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
                int hookX = hookBaseX - 30;//偏移值
                int hookY = hookBaseY + hookLength;
                hookLabel.setBounds(hookX, hookY, 35, 145);

                Rectangle hookRect = hookLabel.getBounds();
                for (SeaCreature creature : creatureList) {
                    Rectangle creatureRect = creature.getBounds();

                    if (hookRect.intersects(creatureRect)) {
                        // 抓到鱼了
                        JOptionPane.showMessageDialog(panelMain, "You have caught the fish!!!", null, JOptionPane.INFORMATION_MESSAGE);
                        hookedCreature = creature;

                        creature.stopMoving();
                        //eliminar el componente
                        background.remove(creature.getLabel());
                        creatureList.remove(creature);
                        background.repaint();

                        extending = false;
                        retracting = true;
                        break; //每次只能抓一条鱼
                    }

                }
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

