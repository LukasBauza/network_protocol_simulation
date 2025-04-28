import java.util.ArrayList;
import java.util.List;

public class Router extends Device {
    RID rid;

    public Router(String name) {
        // Use the parent class constructor to add the name variable within the Device class.
        super(name);

        // Set up the NIC for the Router, which is Gig 0/0, Gig 0/1, Gig 0/2. There is only a name for them set, as the
        // user should be able to set the IP and subnet mask at a later time.
        NIC port00 = new NIC("GigabitEthernet", this);
        NIC port01 = new NIC("GigabitEthernet", this);
        NIC port02 = new NIC("GigabitEthernet", this);

        // Add the NICs to the ArrayList of the nic list within the parent class.
        super.setNICList(new ArrayList<>(List.of(port00,  port01, port02)));
    }

    public ArrayList<NIC> getNICList() { return super.getNICList(); }

    public NIC getPort00() { return super.getNICList().get(0); }

    public void setPort00(IPAddress ipAddress, SubnetMask subnetMask) {
        super.getNICList().get(0).setIpAddress(ipAddress);
        super.getNICList().get(0).setSubnetMask(subnetMask);
    }

    public void setPort00IPAddress(IPAddress ipAddress) {
        super.getNICList().get(0).setIpAddress(ipAddress);
    }

    public void setPort00SubnetMask(SubnetMask subnetMask) {
        super.getNICList().get(0).setSubnetMask(subnetMask);
    }

    public void setPort00Type(String type) {
        super.getNICList().get(0).setType(type);
    }

    public void setPort01(IPAddress ipAddress, SubnetMask subnetMask) {
        super.getNICList().get(1).setIpAddress(ipAddress);
        super.getNICList().get(1).setSubnetMask(subnetMask);
    }

    public NIC getPort01() { return super.getNICList().get(1); }

    public void setPort01IPAddress(IPAddress ipAddress) {
        super.getNICList().get(1).setIpAddress(ipAddress);
    }

    public void setPort01SubnetMask(SubnetMask subnetMask) {
        super.getNICList().get(1).setSubnetMask(subnetMask);
    }

    public void setPort01Type(String type) {
        super.getNICList().get(1).setType(type);
    }

    public NIC getPort02() { return super.getNICList().get(2); }

    public void setPort02(IPAddress ipAddress, SubnetMask subnetMask) {
        super.getNICList().get(2).setIpAddress(ipAddress);
        super.getNICList().get(2).setSubnetMask(subnetMask);
    }

    public void setPort02IPAddress(IPAddress ipAddress) {
        super.getNICList().get(2).setIpAddress(ipAddress);
    }

    public void setPort02SubnetMask(SubnetMask subnetMask) {
        super.getNICList().get(2).setSubnetMask(subnetMask);
    }

    public void setPort02Type(String type) {
        super.getNICList().get(2).setType(type);
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