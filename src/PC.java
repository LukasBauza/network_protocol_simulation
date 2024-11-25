public class PC {
    private String name;
    private MACAddress macAddress;
    // TODO: Set the interface to hold the ipAddress instead of the ipAddress variable.
    private IPAddress ipAddress;
    private Interface interfaceFA00 = new Interface("fastEthernet0", null);

    PC(String name, IPAddress ipAddress, MACAddress macAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;

        // Sets the IP address of the interface for the PC.
        interfaceFA00.setSourceIPAddress(ipAddress);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setMacAddress(MACAddress macAddress) {
        this.macAddress = macAddress;
    }

    public Interface getInterfaceFA00() { return interfaceFA00; }

    public void setInterfaceFA00(Interface interfaceFA00) { this.interfaceFA00 = interfaceFA00; }


}