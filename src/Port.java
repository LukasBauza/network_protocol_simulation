public class Port {
    // The name of an interface should not be changed once created.
    private final String name;
    private IPAddress ipAddress;
    private SubnetMask subNetMask;
    private final MACAddress macAddress = new MACAddress();
    // Down = false, Up = true
    private Boolean state = false;

    public Port(String name, IPAddress ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }

    public Port(String name) {
        this.name = name;
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

    public Boolean getState() { return state; }

    public void setState(Boolean state) { this.state = state; }
}