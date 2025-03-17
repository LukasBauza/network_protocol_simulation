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
}
