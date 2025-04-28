public class NIC {
    private Device assignedDevice;
    private String type;
    private IPAddress ipAddress;
    private SubnetMask subNetMask;
    private MACAddress macAddress;
    private NIC connection;
    private NICManager nicManager = NICManager.getInstance();
    // Default is always 0 for OSPF
    private int priority = 0;
    // Assuming that OSPF is always configured.
    // States:
    //          1. Down
    //          2. Init
    //          3. Two-way
    //          4. ExStart
    //          5. Exchange
    //          6. Loading
    //          7. Full
    private String state = "Full";
    // TODO: Placeholder, can change it to values between 20 and 40 seconds everytime the user
    // looks at the value
    private String deadTime = "00:00:31";

    public NIC(String type, Device assignedDevice) {
        this.type = type;
        this.assignedDevice = assignedDevice;
        // Add the NIC to the NICManager to keep track of NICs automatically.
        nicManager.addNIC(this);
        setMacAddress();
    }

    private void setMacAddress() {
        int count = 10;
        // create a MACAddress for the NIC, if it already exists, then keep creating one until it is unique, or if looped
        // 10 times. 10 times to make sure it won't infinitely loop, the likelihood of generated 10 of the same MACAddresses
        // is almost zero.
        do {
            macAddress = new MACAddress();
        } while (nicManager.macExists(macAddress) && count-- > 0);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type.equalsIgnoreCase("GigabitEthernet") || type.equalsIgnoreCase("FastEthernet")) {
            this.type = type;
        } else {
            System.out.println("Invalid port type");
        }
    }

    public IPAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(IPAddress ipAddress) {
        if (ipAddress == null) {
            System.out.println("IP Address entered is empty");
        } else if (ipAddress.equals(this.ipAddress)) {
            System.out.println("IP Address is the same");
        } else if (subNetMask != null && nicManager.ipAndSubnetExists(ipAddress, subNetMask)) {
            System.err.println("setIpAddress() method called");
            // Check if the combination of ip address and subnet mask is already set up for another NIC
            System.err.println("This combination of IP Address and Subnet Mask already exists");
            // Reset the subnet mask also
            this.subNetMask = null;
        } else {
            System.out.println("IP Address set to: " + ipAddress);
            this.ipAddress = ipAddress;
        }
    }

    public MACAddress getMacAddress() {
        return macAddress;
    }

    public SubnetMask getSubnetMask() {
        return subNetMask;
    }

    public void setSubnetMask(SubnetMask subnetMask) {
        if (subnetMask == null) {
            System.out.println("Subnet Mask entered is empty");
        } else if (subnetMask.equals(this.subNetMask)) {
            System.out.println("Subnet Mask already set");
        } else if (ipAddress != null && nicManager.ipAndSubnetExists(ipAddress, subnetMask)) {
            System.err.println("setSubnetMask() method called");
            System.err.println("This combination of IP Address and Subnet Mask already exists");
            // Reset the ip address also.
            this.ipAddress = null;
        } else {
            this.subNetMask = subnetMask;
        }
    }

    public IPAddress getNetwork() {
        IPAddress network = new IPAddress();

        // AND the IP address bits with the subnet mask bits, and it should return the network bits for each byte.
        byte byte3 = (byte) (this.ipAddress.getIpAddress()[3] & this.subNetMask.getSubnetMask()[3]);
        byte byte2 = (byte) (this.ipAddress.getIpAddress()[2] & this.subNetMask.getSubnetMask()[2]);
        byte byte1 = (byte) (this.ipAddress.getIpAddress()[1] & this.subNetMask.getSubnetMask()[1]);
        byte byte0 = (byte) (this.ipAddress.getIpAddress()[0] & this.subNetMask.getSubnetMask()[0]);

        // Set the bytes for each segment of the network.
        network.setByte3(byte3);
        network.setByte2(byte2);
        network.setByte1(byte1);
        network.setByte0(byte0);

        return network;
    }

    /**
     * Sets up a connection to another NIC.
     * @param otherNIC This is the other NIC that this instance will connect to.
     */
    public void setConnection(NIC otherNIC) {
        this.connection = otherNIC;
    }

    /**
     * Checks whether this NIC is connected to another NIC.
     * @return True if there is a connection, false if not.
     */
    public boolean isConnected() {
        return this.connection != null;
    }

    public NIC getConnectedNIC() {
        return this.connection;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDeadTime() {
        return this.deadTime;
    }

    public void setDeadTime(String deadTime) {
        this.deadTime = deadTime;
    }

    public String getState() {
        return state;
    }

    public Device getAssignedDevice() {
        return assignedDevice;
    }

}