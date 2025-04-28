import javax.swing.*;
import java.awt.*;

public class RouterButton extends JButton {
    Router router;

    public RouterButton(String name) {
        super();
        this.router = new Router(name);
        
        // Set the router icon
        ImageIcon icon = new ImageIcon("images/icons8-router-symbol-50.png");
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        // This method comes from the AbstractButton class.
        setIcon(new ImageIcon(scaledImage));

        // Remove the border around the button.
        setBorderPainted(false);
        // Match the same colour as the background.
        setBackground(new Color(240, 240, 240));
        
        // Set tooltip to show the router name
        setToolTipText(name);

        super.addActionListener(e -> {
            String[] labels = {
                    "Name",
                    router.getPort00().getType() + " 0/0 IP Address",
                    router.getPort00().getType() + " 0/0 Subnet Mask",
                    router.getPort00().getType() + " 0/0 MAC Address",
                    router.getPort01().getType() + " 0/1 IP Address",
                    router.getPort01().getType() + " 0/1 Subnet Mask",
                    router.getPort01().getType() + " 0/1 MAC Address",
                    router.getPort02().getType() + " 0/2 IP Address",
                    router.getPort02().getType() + " 0/2 Subnet Mask",
                    router.getPort02().getType() + " 0/2 MAC Address",
                    "RID",
            };

            String[] fields = {
                    router.getName(),
                    router.getPort00().getIpAddress() == null ? "" : router.getPort00().getIpAddress().toString(),
                    router.getPort00().getSubnetMask() == null ? ""  : router.getPort00().getSubnetMask().toString(),
                    router.getPort00().getMacAddress() == null ? ""  : router.getPort00().getMacAddress().toString(),
                    router.getPort01().getIpAddress() == null ? "" : router.getPort01().getIpAddress().toString(),
                    router.getPort01().getSubnetMask() == null ? ""  : router.getPort01().getSubnetMask().toString(),
                    router.getPort01().getMacAddress() == null ? ""  : router.getPort01().getMacAddress().toString(),
                    router.getPort02().getIpAddress() == null ? "" : router.getPort02().getIpAddress().toString(),
                    router.getPort02().getSubnetMask() == null ? ""  : router.getPort02().getSubnetMask().toString(),
                    router.getPort02().getMacAddress() == null ? ""  : router.getPort02().getMacAddress().toString(),
                    router.getRid() == null ? "" : router.getRid(),
            };

            RouterInfoFrame routerInfoFrame = new RouterInfoFrame(
                    router.getName(),
                    labels,
                    fields,
                    router,
                    this
            );

            routerInfoFrame.setEditable(router.getPort00().getType() + " 0/0 MAC Address", false);
            routerInfoFrame.setEditable(router.getPort01().getType() + " 0/1 MAC Address", false);
            routerInfoFrame.setEditable(router.getPort02().getType() + " 0/2 MAC Address", false);
            routerInfoFrame.setEditable("Port Type", false);

            routerInfoFrame.setVisible(true);
        });
    }

    public Router getRouter() {
        return router;
    }
}