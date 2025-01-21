public class PC extends Device {
    // PC's only have one interface, and it is always a fastEthernet0/0.
    private Port fa00 = new Port("FastEthernet 0/0");
    private IPAddress defaultGateway;

    PC(String name, IPAddress ipaddress) {
        super(name);
        fa00.setIpAddress(ipaddress);
    }

    // Overloading the constructor based on the arguments.
    PC(String name) {
        // Call the parent Device class. With the maximum amount of interfaces set to 1.
        super(name);
    }

    public Port getPortFA00() { return fa00; }

    // You shouldn't be able to change the name of the interface, as they are always predefined. Only the IP can be
    // changed. MAC generated within the Port class randomly.
    public void setPortFA00(IPAddress ipAddress) { this.fa00.setIpAddress(ipAddress); }

    public IPAddress getDefaultGateway() { return defaultGateway; }
    public void setDefaultGateway(IPAddress ipAddress) { this.defaultGateway = ipAddress; }
}