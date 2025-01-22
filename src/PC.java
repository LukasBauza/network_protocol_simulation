public class PC extends Device {
    // PC's only have one interface, and it is always a fastEthernet0/0.
    private Port fa00 = new Port("FastEthernet 0/0");
    private IPAddress defaultGatewayIPAddress;
    private SubnetMask defaultGatewaySubnetMask;

    PC(String name, IPAddress ipaddress, SubnetMask subnetMask) {
        super(name);
        fa00.setIpAddress(ipaddress);
        fa00.setSubnetMask(subnetMask);
    }

    // Overloading the constructor based on the arguments.
    PC(String name) {
        // Call the parent Device class. With the maximum amount of interfaces set to 1.
        super(name);
    }

    public Port getPortFA00() { return fa00; }

    // You shouldn't be able to change the name of the interface, as they are always predefined. Only the IP can be
    // changed. MAC generated within the Port class randomly.
    public void setPortFA00(IPAddress ipAddress, SubnetMask subnetMask) {
        this.fa00.setIpAddress(ipAddress);
        this.fa00.setSubnetMask(subnetMask);
    }

    public IPAddress getDefaultGatewayIPAddress() { return defaultGatewayIPAddress; }
    public SubnetMask getDefaultGatewaySubnetMask() { return defaultGatewaySubnetMask; }

    public void setDefaultGateway(IPAddress ipaddress, SubnetMask subnetMask) {
        this.defaultGatewayIPAddress = ipaddress;
        this.defaultGatewaySubnetMask = subnetMask;
    }
}