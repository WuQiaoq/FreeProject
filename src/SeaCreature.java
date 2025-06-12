import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeaCreature {
    private JLabel label;
    private int x, y;
    private int speed;
    private Timer timer;
    private boolean isGif;
    private boolean isMoving = true;

    /**
     * @param x  horizontal
     * @param y  vertical
     * @param speed velocidad
     * @param container el container para añadir mas componentes
     * @param imagePath ruta de image
     * @param isGif determinar el photo es gif
     */
    public SeaCreature(int x, int y,  int speed, Container container, String imagePath, boolean isGif) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.isGif = isGif;

        ImageIcon icon;

        if (isGif) {
            icon = new ImageIcon(imagePath);
            label = new JLabel(icon);
            label.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        }
        // si es una image normal, configure el tamaño
        else {
            Image img = new ImageIcon(imagePath).getImage().getScaledInstance(70, 30,Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
            label = new JLabel(icon);
            label.setBounds(x, y, 70, 30);
        }

        container.add(label);
        container.repaint();
    }

    public void start() {
        timer = new Timer(50, e -> {
            if (!isMoving) {
                return;
            }
            x += speed;
            label.setLocation(x, y);

            Container parentCont = label.getParent();
            int parentWidth =  parentCont.getWidth();
            int labelWidth = label.getWidth();

            // Quitar la photo si sobrepasa la pantalla
            if (speed > 0 && x > parentWidth) {
                removeAndStop(parentCont);
            }else if (speed < 0 && x + labelWidth < 0 ) {
                removeAndStop(parentCont);
            }
        });
        timer.start();
    }

    private void removeAndStop(Container parent) {
        timer.stop();
        parent.remove(label);
        parent.revalidate();
        parent.repaint();
    }

    //stop moving the component of the sea creature
    public void stopMoving() {
        isMoving = false;
        if (timer != null) {
            timer.stop();
        }
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
        if (label.getParent() != null) {
            Container parent = label.getParent();
            parent.remove(label);
            parent.revalidate();
            parent.repaint();
        }
    }

    //获取label
    public JLabel getLabel() {
        return label;
    }

    //碰到生物的矩形边界
    public Rectangle getBounds() {
        Rectangle bounds = label.getBounds();
        return new Rectangle(bounds.x, bounds.y + 10, bounds.width, bounds.height);
    }
}