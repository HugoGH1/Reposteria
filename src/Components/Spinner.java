package Components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSpinnerUI;

public class Spinner extends JSpinner {

    public Spinner() {
    this(new SpinnerNumberModel(0, 0, 100, 1)); // Valor inicial, min, max, step
    }

    private Color lineColor = new Color(225, 141, 150);
    private boolean mouseOver = false;

    public Spinner(SpinnerModel model) {
        super(model);
        setUI(new SpinnerCustomUI());
        setBorder(new EmptyBorder(15, 3, 5, 3));
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = false;
                repaint();
            }
        });
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        repaint();
    }

    private class SpinnerCustomUI extends BasicSpinnerUI {
        @Override
        protected Component createNextButton() {
            return new ArrowButton(true);
        }

        @Override
        protected Component createPreviousButton() {
            return new ArrowButton(false);
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = c.getWidth();
            int height = c.getHeight();

            // Línea inferior
            g2.setColor(mouseOver ? lineColor : new Color(150, 150, 150));
            g2.fillRect(2, height - 1, width - 4, 1);
            g2.dispose();
        }

        private class ArrowButton extends JButton {
            private final boolean up;

            public ArrowButton(boolean up) {
                this.up = up;
                setContentAreaFilled(false);
                setBorder(new EmptyBorder(5, 5, 5, 5));
                setBackground(new Color(150, 150, 150));
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                int size = 8;
                int x = (w - size) / 2;
                int y = (h - size) / 2 + (up ? -2 : 2);

                int[] px = {x, x + size, x + size / 2};
                int[] py = up
                        ? new int[]{y + size, y + size, y}
                        : new int[]{y, y, y + size};

                g2.setColor(getBackground());
                g2.fillPolygon(px, py, 3);
                g2.dispose();
            }
        }
    }
}
