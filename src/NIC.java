public class NIC {
    // The name of an interface should not be changed once created.
    private final String name;
    private IPAddress ipAddress;
    private SubnetMask subNetMask;
    private final MACAddress macAddress = new MACAddress();

    public NIC(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public IPAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(IPAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public MACAddress getMacAddress() {
        return macAddress;
    }

    public SubnetMask getSubnetMask() {
        return subNetMask;
    }

    public void setSubnetMask(SubnetMask subnetMask) {
        this.subNetMask = subnetMask;
    }

    public IPAddress getNetwork() {
        IPAddress network = new IPAddress();

        // AND the IP address bits with the subnet mask bits and it should return the network bits for each byte.
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
}