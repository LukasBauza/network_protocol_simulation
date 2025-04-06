import java.util.ArrayList;
import java.util.List;

public class Router extends Device {
    RID rid;

    public Router(String name) {
        // Use the parent class constructor to add the name variable within the Device class.
        super(name);

        // Set up the NIC for the Router, which is Gig 0/0, Gig 0/1, Gig 0/2. There is only a name for them set, as the
        // user should be able to set the IP and subnet mask at a later time.
        NIC gig00 = new NIC("GigabitEthernet 0/0");
        NIC gig01 = new NIC("GigabitEthernet 0/1");
        NIC gig02 = new NIC("GigabitEthernet 0/2");

        // Add the NICs to the ArrayList of the nic list within the parent class.
        super.setNICList(new ArrayList<>(List.of(gig00, gig01, gig02)));
    }

    public ArrayList<NIC> getNICList() { return super.getNICList(); }

    public NIC getPortGig00() { return super.getNICList().get(0); }

    public void setPortGig00(IPAddress ipAddress, SubnetMask subnetMask) {
        super.getNICList().get(0).setIpAddress(ipAddress);
        super.getNICList().get(0).setSubnetMask(subnetMask);
    }

    public void setPortGig00IPAddress(IPAddress ipAddress) {
        super.getNICList().get(0).setIpAddress(ipAddress);
    }

    public void setPortGig00SubnetMask(SubnetMask subnetMask) {
        super.getNICList().get(0).setSubnetMask(subnetMask);
    }

    public void setPortGig01(IPAddress ipAddress, SubnetMask subnetMask) {
        super.getNICList().get(1).setIpAddress(ipAddress);
        super.getNICList().get(1).setSubnetMask(subnetMask);
    }

    public NIC getPortGig01() { return super.getNICList().get(1); }

    public void setPortGig01IPAddress(IPAddress ipAddress) {
        super.getNICList().get(1).setIpAddress(ipAddress);
    }

    public void setPortGig01SubnetMask(SubnetMask subnetMask) {
        super.getNICList().get(1).setSubnetMask(subnetMask);
    }

    public NIC getPortGig02() { return super.getNICList().get(2); }

    public void setPortGig02(IPAddress ipAddress, SubnetMask subnetMask) {
        super.getNICList().get(2).setIpAddress(ipAddress);
        super.getNICList().get(2).setSubnetMask(subnetMask);
    }

    public void setPortGig02IPAddress(IPAddress ipAddress) {
        super.getNICList().get(2).setIpAddress(ipAddress);
    }

    public void setPortGig02SubnetMask(SubnetMask subnetMask) {
        super.getNICList().get(2).setSubnetMask(subnetMask);
    }

    public void setRid(RID rid) {
        this.rid = rid;
    }

    public String getRid() {
        if (this.rid == null) {
            return "";
        }
        return rid.toString();
    }
}