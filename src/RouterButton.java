import javax.swing.*;

public class RouterButton extends JButton {
    Router router;

    public RouterButton(String name) {
        super(name);
        this.router = new Router(name);

        super.addActionListener(e -> {
            String[] labels = {
                    "Name",
                    "Gig 0/0 IP Address",
                    "Gig 0/0 Subnet Mask",
                    "Gig 0/0 MAC Address",
                    "Gig 0/1 IP Address",
                    "Gig 0/1 Subnet Mask",
                    "Gig 0/1 MAC Address",
                    "Gig 0/2 IP Address",
                    "Gig 0/2 Subnet Mask",
                    "Gig 0/2 MAC Address"
            };

            String[] fields = {
                    router.getName(),
                    router.getPortGig00().getIpAddress() == null ? "" : router.getPortGig00().getIpAddress().toString(),
                    router.getPortGig00().getSubnetMask() == null ? ""  : router.getPortGig00().getSubnetMask().toString(),
                    router.getPortGig00().getMacAddress() == null ? ""  : router.getPortGig00().getMacAddress().toString(),
                    router.getPortGig01().getIpAddress() == null ? "" : router.getPortGig01().getIpAddress().toString(),
                    router.getPortGig01().getSubnetMask() == null ? ""  : router.getPortGig01().getSubnetMask().toString(),
                    router.getPortGig01().getMacAddress() == null ? ""  : router.getPortGig01().getMacAddress().toString(),
                    router.getPortGig02().getIpAddress() == null ? "" : router.getPortGig02().getIpAddress().toString(),
                    router.getPortGig02().getSubnetMask() == null ? ""  : router.getPortGig02().getSubnetMask().toString(),
                    router.getPortGig02().getMacAddress() == null ? ""  : router.getPortGig02().getMacAddress().toString(),
            };

            DeviceInfoFrame deviceInfoFrame = new DeviceInfoFrame(
                    router.getName(),
                    labels,
                    fields,
                    router,
                    this
            );

            deviceInfoFrame.setEditable("Gig 0/0 MAC Address", false);
            deviceInfoFrame.setEditable("Gig 0/1 MAC Address", false);
            deviceInfoFrame.setEditable("Gig 0/2 MAC Address", false);

            deviceInfoFrame.setVisible(true);
        });
    }

    public Router getRouter() {
        return router;
    }
}