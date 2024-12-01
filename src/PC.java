public class PC {
    private String name;
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

    public Interface getInterfaceFA00() { return interfaceFA00; }

    // You shouldn't be able to change the name of the interface, as they are always predefined. Only the IP can be
    // changed.
    public void setInterfaceFA00IPAddress(IPAddress ipAddress) {
        interfaceFA00.setIpAddress(ipAddress);
        // Don't need to set MAC as it is randomly gnerated.
    }

//    public int ping() {
//
//    }

//    private IPHeader createIPHeader() {
//        IPHeader ipHeader = new IPHeader();
//    }
//
//    private ICMPHeader createICMPHeader() {
//
//    }
}