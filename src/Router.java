public class Router extends Device {
    private NIC gig00 = new NIC("GigabitEthernet 0/0");
    private NIC gig01 = new NIC("GigabitEthernet 0/1");
    private NIC gig02 = new NIC("GigabitEthernet 0/2");

    public Router(String name) {
        super(name);
    }

    public NIC getPortGig00() { return gig00; }
    public void setPortGig00(IPAddress ipAddress, SubnetMask subnetMask) {
        this.gig00.setIpAddress(ipAddress);
        this.gig00.setSubnetMask(subnetMask);
    }

    public NIC getPortGig01() { return gig01; }
    public void setPortGig01(IPAddress ipAddress, SubnetMask subnetMask) {
        this.gig01.setIpAddress(ipAddress);
        this.gig01.setSubnetMask(subnetMask);
    }

    public NIC getPortGig02() { return gig02; }
    public void setPortGig02(IPAddress ipAddress, SubnetMask subnetMask) {
        this.gig02.setIpAddress(ipAddress);
        this.gig02.setSubnetMask(subnetMask);
    }
}
