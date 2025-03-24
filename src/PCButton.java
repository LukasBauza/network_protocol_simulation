import javax.swing.*;
import java.awt.*;

public class PCButton extends JButton {
    PC pc;

    public PCButton(String name) {
        super(name);
        this.pc = new PC(name);

        super.addActionListener(e -> {
            String[] labels = {
                    "Name",
                    "Fa 0/0 IP Address",
                    "Fa 0/0 Subnet Mask"
            };

            String[] fields = {
                    pc.getName(),
                    pc.getPortFA00().getIpAddress() == null ? "" : pc.getPortFA00().getIpAddress().toString(),
                    pc.getPortFA00().getSubnetMask() == null ? "" : pc.getPortFA00().getSubnetMask().toString(),
            };

            PCInfoFrame pcInfoFrame = new PCInfoFrame(
                    pc.getName(),
                    labels,
                    fields,
                    pc,
                    this
            );

            pcInfoFrame.setEditable("Fa 0/0 MAC Address", false);

            pcInfoFrame.setVisible(true);
        });
    }

    public PC getPC() {
        return pc;
    }

    public JFrame getInfoFrame() {
        JFrame frame = new JFrame();

        frame.setSize(	600, 400);
        frame.setTitle(pc.getName());
        frame.setLocationRelativeTo(null);
        // DISPOSE_ON_CLOSE will ensure that the windows won't all close.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        JPanel pcInfoPanel = new JPanel();
        pcInfoPanel.setLayout(new GridLayout(0, 2));

        return frame;
    }
}
