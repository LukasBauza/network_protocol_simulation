abstract public class Device {
    private String name;
    private NetworkInterface[] networkInterfaces;
    private  int maxInterfaces;
    private ARPTable arpTable;

    public Device(String name, int maxInterfaces) {
        this.name = name;
        // Set a maximum amount of interfaces depending on what type of device it is.
        this.maxInterfaces = maxInterfaces;
        this.networkInterfaces = new NetworkInterface[this.maxInterfaces];
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public NetworkInterface[] getNetworkInterfaces() { return networkInterfaces; }

    // You shouldn't be able to change the name of the interface, as they are always predefined. Only the IP can be
    // changed.
    public void setNetworkInterface(String interfaceName, IPAddress ipAddress) {
        for (NetworkInterface networkInterface : networkInterfaces) {
            if (networkInterface.getName().equals(interfaceName)) {
                networkInterface.setIpAddress(ipAddress);
                // Don't need to set MAC as it is randomly generated.
            } else {
                throw new IllegalArgumentException("Network interface " + interfaceName + " does not exist");
            }
        }
    }

    public ARPTable getARPTable() { return arpTable; }

}