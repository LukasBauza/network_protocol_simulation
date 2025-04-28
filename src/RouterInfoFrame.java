import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RouterInfoFrame extends JFrame {
    private ArrayList<InfoField> infoFields  = new ArrayList<>();
    private JButton saveButton = new JButton("Save");
    private JButton editPortTypeButton = new JButton("Edit PortType");

    public RouterInfoFrame(String title, String[] labels, String[] fields, Router router, RouterButton routerButton) {
        if (labels.length != fields.length) {
            throw new IllegalArgumentException("Number of labels and fields do not match");
        }

        super.setTitle(title);
        super.setSize(500, 500);
        super.setLocationRelativeTo(null);                          // Make it center.
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    // DISPOSE_ON_CLOSE Make sure the whole app doesn't shut down.
        super.setResizable(false);

        JTabbedPane tabs = new JTabbedPane();
        JPanel generalInformationPanel = new JPanel();
        generalInformationPanel.setLayout(new GridLayout(0, 1));

        for (int i = 0; i < labels.length; i++) {
            infoFields.add(new InfoField(labels[i], fields[i]));
            // Add the JLabel and JTextField to the JFrame.
            generalInformationPanel.add(infoFields.get(i));
        }
        //setDeviceInfoTab(tabs);
        tabs.addTab("General", generalInformationPanel);

        OSPFNeighboursScrollPane ospfNeighboursScrollPane = new OSPFNeighboursScrollPane(router);
        tabs.addTab("OSPF Neighbours", ospfNeighboursScrollPane);

        super.add(tabs);

        this.saveButton.addActionListener(e -> {
            System.out.println("Saving ");
            router.setName(infoFields.get(0).getTextField().getText());
            routerButton.setText(infoFields.get(0).getTextField().getText());
            this.setTitle(infoFields.get(0).getTextField().getText());
            try {
                IPAddress ipGig00 = new IPAddress(infoFields.get(1).getTextField().getText());
                router.setPort00IPAddress(ipGig00);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid IP address for Gig0/0");
            }
            try {
                SubnetMask subnetGig00 = new SubnetMask(infoFields.get(2).getTextField().getText());
                router.setPort00SubnetMask(subnetGig00);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid subnet mask for Gig0/0");
            }
            try {
                IPAddress ipGig01 = new IPAddress(infoFields.get(4).getTextField().getText());
                router.setPort01IPAddress(ipGig01);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid IP address for Gig0/1");
            }
            try {
                SubnetMask subnetGig01 = new SubnetMask(infoFields.get(5).getTextField().getText());
                router.setPort01SubnetMask(subnetGig01);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid subnet mask for Gig0/1");
            }
            try {
                IPAddress ipGig02 = new IPAddress(infoFields.get(7).getTextField().getText());
                router.setPort02IPAddress(ipGig02);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid IP address for Gig0/2");
            }
            try {
                SubnetMask subnetGig02 = new SubnetMask(infoFields.get(8).getTextField().getText());
                router.setPort02SubnetMask(subnetGig02);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid subnet mask for Gig0/2");
            }
            try {
                RID rid = new RID(infoFields.get(9).getTextField().getText());
                router.setRid(rid);
                System.out.println(router.getRid());
            } catch (IllegalArgumentException exceptions) {
                System.out.println("Invalid RID");
            }
        });

        generalInformationPanel.add(saveButton);

        this.editPortTypeButton.addActionListener(e -> {
            JFrame editPortTypeFrame = new JFrame("Edit Port Type");
            editPortTypeFrame.setSize(300, 150);
            editPortTypeFrame.setLocationRelativeTo(null);
            editPortTypeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            editPortTypeFrame.setResizable(false);
            editPortTypeFrame.setLayout(new GridLayout(0, 1));

            JComboBox<String> port00TypeComboBox = new JComboBox<>(new String[]{"Port 00: GigabitEthernet", "Port 00: FastEthernet"});
            JComboBox<String> port01TypeComboBox = new JComboBox<>(new String[]{"Port 01: GigabitEthernet", "Port 01: FastEthernet"});
            JComboBox<String> port02TypeComboBox = new JComboBox<>(new String[]{"Port 02: GigabitEthernet", "Port 02: FastEthernet"});

            // Set the current port types in the combo boxes
            String currentPort00Type = router.getPort00().getType();
            String currentPort01Type = router.getPort01().getType();
            String currentPort02Type = router.getPort02().getType();

            port00TypeComboBox.setSelectedItem("Port 00: " + currentPort00Type);
            port01TypeComboBox.setSelectedItem("Port 01: " + currentPort01Type);
            port02TypeComboBox.setSelectedItem("Port 02: " + currentPort02Type);

            editPortTypeFrame.add(port00TypeComboBox);
            editPortTypeFrame.add(port01TypeComboBox);
            editPortTypeFrame.add(port02TypeComboBox);

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(e1 -> {
                String port00Type = port00TypeComboBox.getSelectedItem().toString().split(": ")[1];
                String port01Type = port01TypeComboBox.getSelectedItem().toString().split(": ")[1];
                String port02Type = port02TypeComboBox.getSelectedItem().toString().split(": ")[1];
                
                router.setPort00Type(port00Type);
                router.setPort01Type(port01Type);
                router.setPort02Type(port02Type);
                
                // Update the labels in the info fields
                infoFields.get(1).getLabel().setText(port00Type + " 0/0 IP Address");
                infoFields.get(2).getLabel().setText(port00Type + " 0/0 Subnet Mask");
                infoFields.get(3).getLabel().setText(port00Type + " 0/0 MAC Address");
                infoFields.get(4).getLabel().setText(port01Type + " 0/1 IP Address");
                infoFields.get(5).getLabel().setText(port01Type + " 0/1 Subnet Mask");
                infoFields.get(6).getLabel().setText(port01Type + " 0/1 MAC Address");
                infoFields.get(7).getLabel().setText(port02Type + " 0/2 IP Address");
                infoFields.get(8).getLabel().setText(port02Type + " 0/2 Subnet Mask");
                infoFields.get(9).getLabel().setText(port02Type + " 0/2 MAC Address");
                
                // Update the cost path labels
                PreconfiguredNetworkPanel networkPanel = (PreconfiguredNetworkPanel) routerButton.getParent();
                networkPanel.placeCostLabels();
                
                System.out.println("Router Port 00: " + router.getPort00().getType());
                System.out.println("Router Port 01: " + router.getPort01().getType());
                System.out.println("Router Port 02: " + router.getPort02().getType());
                editPortTypeFrame.dispose();
            });
            editPortTypeFrame.add(saveButton);

            editPortTypeFrame.setVisible(true);
        });

        generalInformationPanel.add(editPortTypeButton);
    }

    public void setEditable(String fieldLabel, boolean editable) {
        for (InfoField infoField : infoFields) {
            if (infoField.getLabel().getText().equals(fieldLabel)) {
                infoField.setEditable(editable);
            }
        }
    }
}