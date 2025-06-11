import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainMenu {

    private JPanel MainPanel;
    private JPanel centerPanel;

    public MainMenu() {
        // 用普通 JPanel，设置灰色背景
        MainPanel = new JPanel();
        MainPanel.setBackground(Color.WHITE);
        MainPanel.setLayout(new BorderLayout());

        // 中间面板透明，保证背景灰色透出
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);

        // 创建带图片和文字的透明按钮
        JButton startButton = createImageButton("src/img_free_/logo_.jpg", "Start", 250, 200);
        // 按钮透明处理
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);

        // 点击事件示例
        startButton.addActionListener(e -> {
            JFrame loginFrame = new JFrame("Iniciar sesión");
            loginFrame.setContentPane(new LoginForm().LoginPanel);
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setSize(400, 200);
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setResizable(false);
            loginFrame.setVisible(true);

            // 关闭当前主菜单窗口
            SwingUtilities.getWindowAncestor(MainPanel).dispose();
        });


        centerPanel.add(startButton);
        centerPanel.setPreferredSize(startButton.getPreferredSize());

        // wrapper 用于居中显示中间面板
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(centerPanel);

        MainPanel.add(wrapper, BorderLayout.CENTER);
    }

    // 创建带图标和文字的按钮（图标等比例缩放）
    private JButton createImageButton(String imagePath, String text, int maxWidth, int maxHeight) {
        JButton button = new JButton(text);

        try {
            BufferedImage original = ImageIO.read(new File(imagePath));
            int w = original.getWidth();
            int h = original.getHeight();

            double scale = Math.min((double) maxWidth / w, (double) maxHeight / h);
            if (scale > 1.0) scale = 1.0;

            int newW = (int)(w * scale);
            int newH = (int)(h * scale);

            Image scaled = original.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaled));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 文字居中显示在图标下方
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);

        button.setPreferredSize(new Dimension(maxWidth + 20, maxHeight + 40));

        return button;
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        MainMenu menu = new MainMenu();
        frame.setContentPane(menu.getMainPanel());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
