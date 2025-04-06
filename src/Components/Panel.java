package Components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class Panel extends JPanel { // Calendario Inicio
 
    private Color backgroundColor = new Color(255, 255, 255);
    private Color borderColor = new Color(200, 200, 200);
    private int radius = 20;

    public Panel() {
        setOpaque(false); // Para permitir la transparencia y bordes redondeados
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    public int getRadius() {
        return radius;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public Color getBorderColor() {
        return borderColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibuja borde
        g2.setColor(borderColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Dibuja panel con margen interno
        g2.setColor(backgroundColor);
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }
}
