import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        frame.setSize(600, 400);
        frame.setTitle(router.getName());
        // Make sure it is in the middle of the screen.
        frame.setLocationRelativeTo(null);
        // DISPOSE_ON_CLOSE will ensure that the windows won't all close.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new CardLayout());

        JTabbedPane tabs = new JTabbedPane();

        JPanel routerInfoPanel = new JPanel();
        routerInfoPanel.setLayout(new GridLayout(0, 2));

        JLabel[] routerLeftInfoLabels = {
                new JLabel("Name"),
                new JLabel("Gig 0/0 IP Address"),
                new JLabel("Gig 0/0 Subnet Mask"),
                new JLabel("Gig 0/0 MAC Address"),
                new JLabel("Gig 0/1 IP Address"),
                new JLabel("Gig 0/1 Subnet Mask"),
                new JLabel("Gig 0/1 MAC Address"),
                new JLabel("Gig 0/2 IP Address"),
                new JLabel("Gig 0/2 Subnet Mask"),
                new JLabel("Gig 0/2 MAC Address"),
        };

        JPanel routerLeftInfoPanel = new JPanel(new GridLayout(0, 1));
        for (JLabel label : routerLeftInfoLabels) {
            routerLeftInfoPanel.add(label);
        }
        routerInfoPanel.add(routerLeftInfoPanel);

        JTextField[] routerRightTextFields = {
                new JTextField(router.getName()),
                router.getPortGig00().getIpAddress() == null ? new JTextField() : new JTextField(router.getPortGig00().getIpAddress().toString()),
                router.getPortGig00().getSubnetMask() == null ? new JTextField() : new JTextField(router.getPortGig00().getSubnetMask().toString()),
                router.getPortGig00().getMacAddress() == null ? new JTextField() : new JTextField(router.getPortGig00().getMacAddress().toString()),
                router.getPortGig01().getIpAddress() == null ? new JTextField() : new JTextField(router.getPortGig01().getIpAddress().toString()),
                router.getPortGig01().getSubnetMask() == null ? new JTextField() : new JTextField(router.getPortGig01().getSubnetMask().toString()),
                router.getPortGig01().getMacAddress() == null ? new JTextField() : new JTextField(router.getPortGig01().getMacAddress().toString()),
                router.getPortGig02().getIpAddress() == null ? new JTextField() : new JTextField(router.getPortGig02().getIpAddress().toString()),
                router.getPortGig02().getSubnetMask() == null ? new JTextField() : new JTextField(router.getPortGig02().getSubnetMask().toString()),
                router.getPortGig02().getMacAddress() == null ? new JTextField() : new JTextField(router.getPortGig02().getMacAddress().toString()),
        };

        // Make sure the user cannot change the MAC Address.
        routerRightTextFields[3].setEditable(false);
        routerRightTextFields[6].setEditable(false);
        routerRightTextFields[9].setEditable(false);

        JPanel routerRightInfoPanel = new JPanel(new GridLayout(0, 1));
        for (JTextField textField : routerRightTextFields) {
            routerRightInfoPanel.add(textField);
        }
        routerInfoPanel.add(routerRightInfoPanel);

        JButton saveButton = new JButton("Save Changes");
        routerInfoPanel.add(saveButton);
        saveButton.addActionListener(e -> {
            System.out.println("Saving changes");
            for (int i = 0; i < routerRightTextFields.length; i++) {
                if (i != 3 && i != 6 && i != 9) {
                    routerRightTextFields[i].setText(routerRightTextFields[i].getText());
                    System.out.println(routerRightTextFields[i].getText());
                }
            }
            router.setName(routerRightTextFields[0].getText());
            // Makes sure that the RouterButton is updated also.
            this.setText(router.getName());
        });

        JPanel routerOSPInfoPanel = new JPanel();

        tabs.addTab("Router Information", routerInfoPanel);

        frame.add(tabs);
        return frame;
    }
}