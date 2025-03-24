import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PCInfoFrame extends JFrame{
    private ArrayList<InfoField> infoFields  = new ArrayList<>();
    private JButton saveButton = new JButton("Save");

    public PCInfoFrame(String title, String[] labels, String[] fields, PC pc, PCButton pcButton) {
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
        tabs.addTab("General", generalInformationPanel);
        super.add(tabs);

        this.saveButton.addActionListener(e -> {
            System.out.println("Saving");
            pc.setName(infoFields.get(0).getTextField().getText());
            pcButton.setText(infoFields.get(0).getTextField().getText());
            this.setTitle(infoFields.get(0).getTextField().getText());
            try {
                IPAddress ipFa00 = new IPAddress(infoFields.get(1).getTextField().getText());
                pc.setPortFA00IPAddress(ipFa00);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid IP address for Fa 0/0");
            }
            try {
                SubnetMask subnetFa00 = new SubnetMask(infoFields.get(2).getTextField().getText());
                pc.setPortFA00SubnetMask(subnetFa00);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid Subnet mask for Fa 0/0");
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
}
