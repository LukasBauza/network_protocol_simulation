import java.util.ArrayList;

public class NICManager {
    // Holds the all the NICs that have been created.
    private ArrayList<NIC> createdNICs = new ArrayList<>();
    // This will hold the instance of the class, it is used to ensure that this will be the only instance that can be used.
    // static variable to indicate the value is associated with the class, and not the object, this ensures that the same
    // instance is held throughout the class. new NICManager() calls the private constructor, which can only be accessed
    // from within itself.
    private static NICManager instance = new NICManager();

    /**
     * Private constructor, so no other class can call the constructor, thus not allowing any more instances of the class.
     */
    private NICManager() {

    }

    /**
     * Method for returning the instance of the object, since only one instance can exist.
     * @return NICManager instance
     */
    public static NICManager getInstance() {
        return instance;
    }

    /**
     * Inserts a NIC within the ArrayList<NIC>
     * @param nic The NIC to be inserted.
     */
    public void addNIC(NIC nic) {
        createdNICs.add(nic);
    }

    /**
     * Checks if a MACAddress has already been created for another NIC.
     * @param macAddress MACAddress that will be searched for.
     * @return If a MACAddress exists returns true, else false.
     */
    public boolean macExists(MACAddress macAddress) {
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
    public boolean ipAndSubnetExists(IPAddress ipAddress, SubnetMask subnetMask) {
        for (NIC nic : createdNICs) {
            // Check if the IP Address or the Subnet Mask is not equal to null, if so go to the next iteration
            if (nic.getIpAddress() == null || nic.getSubnetMask() == null) {
                continue;
            }
            // If the ipAddress and the subnetMask match with the subnetMask and ipAddress of the NIC, then return true.
            if (nic.getIpAddress().equals(ipAddress) && nic.getSubnetMask().equals(subnetMask)) {
                return true;
            }
        }
        // When no matches are mad then return false.
        return false;
    }

    public Device getDevice(IPAddress ipAddress, SubnetMask subnetMask) {
        for (NIC nic : createdNICs) {
            if (nic.getIpAddress() == null || nic.getSubnetMask() == null) {
                continue;
            }
            if (nic.getIpAddress().equals(ipAddress) && nic.getSubnetMask().equals(subnetMask)) {
                return nic.getAssignedDevice();
            }
        }

        System.out.println("No device found for IP " + ipAddress + " and subnet " + subnetMask);
        return null;
    }
}