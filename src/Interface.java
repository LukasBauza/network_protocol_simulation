public class Interface {
    // The name of an interface should not be changed once created.
    private final String name;
    private IPAddress ipAddress;
    private SubnetMask subNetMask;
    private final MACAddress macAddress;

    public Interface(String name, IPAddress ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.macAddress = new MACAddress();
    }

    public String getName() {
        return name;
    }

    public IPAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(IPAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public MACAddress getMacAddress() {
        return macAddress;
    }

    public SubnetMask getSubnetMask() {
        return subNetMask;
    }

    public void setSubnetMask(SubnetMask subnetMask) {
        this.subNetMask = subnetMask;
    }
}
