import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PacketAnimation extends JPanel {
    private List<Point> pointList;      // List of ponts to do the animation
    private int currentPoint = 0;       // Current point in the animation
    private boolean animating = false;  // Used for checking if the animation is still going
    private Image letterImage;          // Used for the packet image

    private int currentX, currentY; // Current position of the image
    private int endX, endY;         // Target position

    private static final int TIMER_DELAY = 20; // ms
    private static final int SPEED = 4; // pixels per frame

    public PacketAnimation(List<Point> pointList) {
        this.pointList = pointList;

        // Load the letter icon
        ImageIcon icon = new ImageIcon("images/icons8-letter-50.png");
        letterImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        // A timer is created, that has a delay set by the TIMER_DELAY, which is the amoutn of time.
        // Then it calls the updatePosition() method, which then moves the packet to the next increment
        // of x and y.
        Timer timer = new Timer(TIMER_DELAY, e -> updatePosition());
        timer.start();
    }

    public void startAnimation() {
        // If there are less than 2 points, then there cannot be an animation
        if (pointList.size() < 2) return;

        currentPoint = 0;
        animating = true;
        currentX = pointList.get(0).x;
        currentY = pointList.get(0).y;
        endX = pointList.get(1).x;
        endY = pointList.get(1).y;
    }

    private void updatePosition() {
        if (!animating) return;

        // Calculate the difference between the target coordinates and the current position
        int dx = endX - currentX;
        int dy = endY - currentY;
        // Use Pythagorean theorem to get the point from the start to the end.
        // This is done to get the straight point from the starting point, to the ending point. This
        // is needed because of the angles that are used when making connections.
        double distance = Math.sqrt(dx * dx + dy * dy);

        // If the distance of the packet is really close, then just start moving to the next point.
        // This is done because sometimes it would get stuck, as it never reached the point exactly.
        if (distance <= 5) {
            // Move to next point
            currentPoint++;
            // Out of bounds check
            if (currentPoint + 1 < pointList.size()) {
                // Move to the next end point.
                endX = pointList.get(currentPoint + 1).x;
                endY = pointList.get(currentPoint + 1).y;
            } else {
                // If all points finished, then animation is finished.
                animating = false;
            }
        } else {
            // Move in direction of the current end point
            // The stepX and stepY is used to determine how much each axis contributes to the overall movement.
            // This all depends on the angle at which it moves. It is basically checking the rate of change of y,
            // and the rate of change of x. These are then added to the currentX and currentY, which moves it
            // by the required rate of change.
            // The SPEED is then used to also calculate how much the actual change should take place, which is used
            // to just make th animation faster or slower.
            double stepX = SPEED * dx / distance;
            double stepY = SPEED * dy / distance;
            currentX += (int) stepX;
            currentY += (int) stepY;
        }

        repaint(); // Redraw at new position
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Clears the panel, and applies the background colour
        // This is needed to repaint the packet
        super.paintComponent(g);

        if (animating) {
            // If it is still animating, then it will draw the image of the packet, at the positions
            g.drawImage(letterImage, currentX, currentY, null);
        }
    }

    // Testing
    public static void main(String[] args) {
        List<Point> corners = List.of(
                new Point(100, 100),
                new Point(500, 100),
                new Point(500, 500),
                new Point(100, 500),
                new Point(300, 300),
                new Point(300, 100)
        );

        JFrame frame = new JFrame();
        PacketAnimation panel = new PacketAnimation(corners);
        frame.add(panel);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        SwingUtilities.invokeLater(panel::startAnimation);
    }
}
