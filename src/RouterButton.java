import javax.swing.*;

public class RouterButton extends JButton {
    Router router;

    public RouterButton(String name) {
        super(name);
        this.router = new Router(name);
    }

    public Router getRouter() {
        return router;
    }

    public JFrame getInfoFrame() {
        JFrame frame = new JFrame();

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        // DISPOSE_ON_CLOSE will ensure that the windows won't all close.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return frame;
    }
}
