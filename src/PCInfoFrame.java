import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class PCInfoFrame extends JFrame {

    private ArrayList<InfoField> infoFields = new ArrayList<>();
    private JButton saveButton = new JButton("Save");
    private JButton pingButton = new JButton("Ping another PC");
    private PreconfiguredNetworkPanel networkPanel;

    public PCInfoFrame(
        String title,
        String[] labels,
        String[] fields,
        PC pc,
        PCButton pcButton,
        PreconfiguredNetworkPanel networkPanel
    ) {
        if (labels.length != fields.length) {
            throw new IllegalArgumentException(
                "Number of labels and fields do not match"
            );
        }

        this.networkPanel = networkPanel;

        super.setTitle(title);
        super.setSize(300, 300);
        super.setLocationRelativeTo(null); // Make it center.
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // DISPOSE_ON_CLOSE Make sure the whole app doesn't shut down.
        super.setResizable(false);

        JTabbedPane tabs = new JTabbedPane();
        JPanel generalInformationPanel = new JPanel();
        generalInformationPanel.setLayout(new GridLayout(0, 1));

        for (int i = 0; i < labels.length; i++) {
            infoFields.add(new InfoField(labels[i], fields[i]));
            // Add the JLabel and JTextField to the JFrame.
            generalInformationPanel.add(infoFields.get(i));
        }
        tabs.addTab("General", generalInformationPanel);
        super.add(tabs);

        this.saveButton.addActionListener(e -> {
                System.out.println("Saving");
                pc.setName(infoFields.get(0).getTextField().getText());
                pcButton.setText(infoFields.get(0).getTextField().getText());
                this.setTitle(infoFields.get(0).getTextField().getText());
                try {
                    IPAddress ipFa00 = new IPAddress(
                        infoFields.get(1).getTextField().getText()
                    );
                    pc.setPortFA00IPAddress(ipFa00);
                } catch (IllegalArgumentException exception) {
                    System.out.println("Invalid IP address for Fa 0/0");
                }
                try {
                    SubnetMask subnetFa00 = new SubnetMask(
                        infoFields.get(2).getTextField().getText()
                    );
                    pc.setPortFA00SubnetMask(subnetFa00);
                } catch (IllegalArgumentException exception) {
                    System.out.println("Invalid Subnet mask for Fa 0/0");
                }
            });
        generalInformationPanel.add(saveButton);

        this.pingButton.addActionListener(e1 -> {
            System.out.println("Pinging");

            JFrame destinationQuery = new JFrame();
            destinationQuery.setSize(400, 200);
            destinationQuery.setLocationRelativeTo(null);
            destinationQuery.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            destinationQuery.setResizable(false);
            destinationQuery.setVisible(true);
            destinationQuery.setLayout(new GridLayout(0, 1));

            InfoField infoDestinationIPAddress = new InfoField("Destination IP Address", "");
            destinationQuery.add(infoDestinationIPAddress);

            InfoField infoDestinationSubnetMask = new InfoField("Destination Subnet Mask", "");
            destinationQuery.add(infoDestinationSubnetMask);

            JButton startPingButton = new JButton("Start ping");
            startPingButton.addActionListener(e2 -> {
                String strDestinationIP = infoDestinationIPAddress.getTextField().getText();
                String strDestinationSubnetMask = infoDestinationSubnetMask.getTextField().getText();
                PrePingProtocol prePingProtocol = new PrePingProtocol(pc, strDestinationIP, strDestinationSubnetMask, networkPanel);
                prePingProtocol.ping();
            });
            destinationQuery.add(startPingButton);

        });
        generalInformationPanel.add(pingButton);
    }

    public void setEditable(String fieldLabel, boolean editable) {
        for (InfoField infoField : infoFields) {
            if (infoField.getLabel().getText().equals(fieldLabel)) {
                infoField.setEditable(editable);
            }
        }
    }

    private boolean destinationExists(String destinationIPAddress, String destinationSubnetMask) {
        // Convert the destination subnet mask string to a SubnetMask object
        SubnetMask destSubnetMask = new SubnetMask(destinationSubnetMask);

        // Convert the destination IP address string to an IPAddress object
        IPAddress destIPAddress = new IPAddress(destinationIPAddress);

        // Get the NICManager instance which keeps track of all NICs in the network
        NICManager nicManager = NICManager.getInstance();

        // Check if the IP and subnet mask combination exists in any NIC in the network
        return nicManager.ipAndSubnetExists(destIPAddress, destSubnetMask);
    }
}
