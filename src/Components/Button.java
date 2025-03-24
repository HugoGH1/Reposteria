
package Components;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class Button extends JButton {

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }

    public Color getColorOver() {
        return colorIn;
    }

    public void setColorIn(Color colorOver) {
        this.colorIn = colorOver;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    String colorB;
    public Button() {
        //  Init Color
        colorB = "#73df5c";
        setColor(Color.decode(colorB));
        colorOut = new Color(Color.decode(colorB).getRGB());
        colorIn = new Color(93, 179, 75);
        setContentAreaFilled(false);
        //  Add event mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(colorIn);
                over = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(colorOut);
                over = false;

            }
        });
    }

    private boolean over;
    private Color color;
    private Color colorOut;
    private Color colorIn;
    private Color borderColor;
    private int radius = 0;

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //  Paint Border
        g2.setColor(new Color(204,204,204));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.setColor(colorOut);
        //  Border set 2 Pix
        g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, radius, radius);
        super.paintComponent(grphcs);
    }
}