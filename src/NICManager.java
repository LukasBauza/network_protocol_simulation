import java.util.ArrayList;

public class NICManager {
    ArrayList<NIC> createdNICs;

    /**
     * Inserts a NIC within the ArrayList<NIC>
     * @param nic The NIC to be inserted.
     */
    void addNIC(NIC nic) {
        createdNICs.add(nic);
    }

    /**
     * Checks if a MACAddress has already been created for another NIC.
     * @param macAddress MACAddress that will be searched for.
     * @return If a MACAddress exists returns true, else false.
     */
    boolean macExists(MACAddress macAddress) {
        for (NIC nic : createdNICs) {
            if (nic.getMacAddress().equals(macAddress)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a IPAddress and SubnetMask combination are being used by another NIC.
     * @param ipAddress Used for checking for a matching IPAddress.
     * @param subnetMask Used for checking a matching SubnetMask.
     * @return If both the ipAddress and subnetMask match, then return true, else false.
     */
    boolean ipAndSubnetExists(IPAddress ipAddress, SubnetMask subnetMask) {
        for (NIC nic : createdNICs) {
            if (nic.getIpAddress().equals(ipAddress) && nic.getSubnetMask().equals(subnetMask)) {
                return true;
            }
        }
        return false;
    }
}