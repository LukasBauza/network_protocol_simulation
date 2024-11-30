public class PC {
    private String name;
    private MACAddress macAddress;
    // TODO: Set the interface to hold the ipAddress instead of the ipAddress variable.
    //private IPAddress ipAddress;
    // PC's only have one interface and it is always a fastEthernet0.
    private Interface interfaceFA00 = new Interface("fastEthernet0", null);

    PC(String name, IPAddress ipAddress) {
        this.name = name;
        // Sets the IP address of the interface for the PC.
        interfaceFA00.setIpAddress(ipAddress);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MACAddress getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(MACAddress macAddress) {
        this.macAddress = macAddress;
    }

    public Interface getInterfaceFA00() { return interfaceFA00; }

    // You shouldn't be able to change the name of the interface, as they are always predefined. Only the IP can be
    // changed.
    public void setInterfaceFA00IPAddress(IPAddress ipAddress) {
        interfaceFA00.setIpAddress(ipAddress);
    }


}