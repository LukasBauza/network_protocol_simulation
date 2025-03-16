import java.util.ArrayList;
import java.util.List;

public class PC extends Device {
    // This is the default gateway for the PC, which should be to a router NIC.
    private IPAddress defaultGatewayIPAddress;
    private SubnetMask defaultGatewaySubnetMask;

    PC(String name, IPAddress ipaddress, SubnetMask subnetMask, NICManager nicManager) {
        // Use the parent class constructor to add the name variable within the Device class.
        super(name);

        // Set up the NIC for the PC, which only has the FastEthernet 0/0
        NIC fa00 = new NIC("FastEthernet 0/0");
        fa00.setIpAddress(ipaddress);
        fa00.setSubnetMask(subnetMask);
        // Add the fa00 NIC to the ArrayList of nicList.
        // Set the NIC for the parent class Device.
        super.setNICList(new ArrayList<>(List.of(fa00)));
    }

    public NIC getPortFA00() { return super.getNICList().get(0); }

    // You shouldn't be able to change the name of the interface, as they are always predefined. Only the IP can be
    // changed. MAC generated within the Port class randomly.
    public void setPortFA00(IPAddress ipAddress, SubnetMask subnetMask) {
        super.getNICList().get(0).setIpAddress(ipAddress);
        super.getNICList().get(0).setSubnetMask(subnetMask);
    }

    public IPAddress getDefaultGatewayIPAddress() { return defaultGatewayIPAddress; }
    public SubnetMask getDefaultGatewaySubnetMask() { return defaultGatewaySubnetMask; }

    public void setDefaultGateway(IPAddress ipaddress, SubnetMask subnetMask) {
        this.defaultGatewayIPAddress = ipaddress;
        this.defaultGatewaySubnetMask = subnetMask;
    }
}