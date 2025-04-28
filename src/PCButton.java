import javax.swing.*;
import java.awt.*;

public class PCButton extends JButton {
    private PC pc;
    private PreconfiguredNetworkPanel networkPanel;

    public PCButton(String name, PreconfiguredNetworkPanel networkPanel) {
        super();
        this.pc = new PC(name);
        this.networkPanel = networkPanel;

        // Set the pc icon
        ImageIcon icon = new ImageIcon("images/icons8-pc-50.png");
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));

        // Remove the border around the button.
        setBorderPainted(false);
        // Match the same colour as the background.
        setBackground(new Color(240, 240, 240));

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
                    this,
                    networkPanel
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

        JPanel pcInfoPanel = new JPanel();
        pcInfoPanel.setLayout(new GridLayout(0, 2));

        return frame;
    }
}
