import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PacketAnimation extends JPanel {
    private List<Point> pointList;      // List of Points for animation.
    private int startX, startY;         // Starting positions
    private int endX, endY;         // Ending positions
    private float progress = 0f; // Progress of the animation (0.0 - 1.0)
    private int currentCorner = 0; // Current corner we're animating towards
    private boolean animating = false; // Flag to check if the animation is ongoing

    private static final int TIMER_DELAY = 20; // Timer delay in ms for smoother animation
    private static final float PROGRESS_INCREMENT = 0.02f; // How much progress increases per frame
    private static final int CIRCLE_SIZE = 10; // Size of the animated circle

    public PacketAnimation(List<Point> pointList) {
        this.pointList = pointList;

        // Timer to update position every 10 ms for smooth transition
        Timer timer = new Timer(TIMER_DELAY, e -> updatePosition());
        timer.start();
    }
    
    // Start the animation from the first point to the next
    public void startAnimation() {
        currentCorner = 0; // Start at the first point
        animating = true;
        progress = 0f;
        startX = pointList.get(currentCorner).x;
        startY = pointList.get(currentCorner).y;
        endX = pointList.get((currentCorner + 1) % pointList.size()).x; // The next point
        endY = pointList.get((currentCorner + 1) % pointList.size()).y;
    }


    private void updatePosition() {
        if (animating) {
            progress += PROGRESS_INCREMENT; // Increment progress by 1% every frame
            if (progress >= 1f) {
                progress = 1f;
                // Move to the next point
                currentCorner = (currentCorner + 1) % pointList.size();
                startX = endX;
                startY = endY;
                endX = pointList.get((currentCorner + 1) % pointList.size()).x;
                endY = pointList.get((currentCorner + 1) % pointList.size()).y;
                progress = 0f; // Reset progress for the next point animation
                if (currentCorner == pointList.size() - 1) {
                    animating = false; // Stop once we've reached the last point
                }
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (animating) {
            // Linearly interpolate between start and end positions
            int x = (int) (startX + progress * (endX - startX));
            int y = (int) (startY + progress * (endY - startY));

            g.setColor(Color.RED); // Set the circle color
            g.fillOval(x - 20, y - 20, 40, 40); // Draw the circle (diameter = 40px)
        }
    }

    public static void main(String[] args) {
        // Example of setting up 6 corners using Point objects
        List<Point> corners = List.of(
                new Point(100, 100),
                new Point(500, 100),
                new Point(500, 500),
                new Point(100, 500),
                new Point(300, 300),
                new Point(300, 100)
        );

        JFrame frame = new JFrame("Smooth Circle Animation");
        PacketAnimation panel = new PacketAnimation(corners);
        frame.add(panel);
        frame.setSize(600, 600); // Size of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the animation after the window is visible
        SwingUtilities.invokeLater(panel::startAnimation);
    }
}
