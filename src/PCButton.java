import javax.swing.*;

public class PCButton extends JButton {
    PC pc;

    public PCButton(String name) {
        super(name);
        this.pc = new PC(name);
    }

    public PC getPC() {
        return pc;
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
