import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DeviceInfoFrame extends JFrame {
    private ArrayList<InfoField> infoFields  = new ArrayList<>();
    private JButton saveButton = new JButton("Save");
    private Router router;
    private RouterButton routerButton;

    public DeviceInfoFrame(String title, String[] labels, String[] fields, Router router, RouterButton routerButton) {
        if (labels.length != fields.length) {
            throw new IllegalArgumentException("Number of labels and fields do not match");
        }

        super.setTitle(title);
        super.setSize(300, 300);
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
        super.add(tabs);

        this.saveButton.addActionListener(e -> {
            System.out.println("Saving ");
            router.setName(infoFields.get(0).getTextField().getText());
            routerButton.setText(infoFields.get(0).getTextField().getText());
            this.setTitle(infoFields.get(0).getTextField().getText());
            try {
                IPAddress ipGig00 = new IPAddress(infoFields.get(1).getTextField().getText());
                router.setPortGig00IPAddress(ipGig00);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid IP address for Gig0/0");
            }
            try {
                SubnetMask subnetGig00 = new SubnetMask(infoFields.get(2).getTextField().getText());
                router.setPortGig00SubnetMask(subnetGig00);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid subnet mask for Gig0/0");
            }
            try {
                IPAddress ipGig01 = new IPAddress(infoFields.get(4).getTextField().getText());
                router.setPortGig01IPAddress(ipGig01);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid IP address for Gig0/1");
            }
            try {
                SubnetMask subnetGig01 = new SubnetMask(infoFields.get(5).getTextField().getText());
                router.setPortGig01SubnetMask(subnetGig01);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid subnet mask for Gig0/1");
            }
            try {
                IPAddress ipGig02 = new IPAddress(infoFields.get(7).getTextField().getText());
                router.setPortGig02IPAddress(ipGig02);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid IP address for Gig0/2");
            }
            try {
                SubnetMask subnetGig02 = new SubnetMask(infoFields.get(8).getTextField().getText());
                router.setPortGig02SubnetMask(subnetGig02);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid subnet mask for Gig0/2");
            }
        });

        generalInformationPanel.add(saveButton);
    }

    public void setEditable(String fieldLabel, boolean editable) {
        for (InfoField infoField : infoFields) {
            if (infoField.getLabel().getText().equals(fieldLabel)) {
                infoField.setEditable(editable);
            }
        }
    }

    private void setDeviceInfoTab(JTabbedPane tabs) {
        tabs.setLayout(new GridLayout(0, 1));

        for (InfoField infoField : infoFields) {
            tabs.add(infoField);
        }
    }
}