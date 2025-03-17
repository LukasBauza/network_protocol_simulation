import javax.swing.*;
import java.awt.*;

public class Line extends JPanel {
    private int x1, y1, x2, y2;
    private Color color;

    public Line(int x1, int y1, int x2, int y2, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    // Method to draw the line
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(color);
        g2D.setStroke(new BasicStroke(5)); // Set line thickness
        g2D.drawLine(x1, y1, x2, y2);
    }
}
