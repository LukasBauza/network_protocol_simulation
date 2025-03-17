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
}
