public class PC extends Device{
    // PC's only have one interface and it is always a fastEthernet0.
    private NetworkInterface networkInterfaceFA00 = new NetworkInterface("fastEthernet0", null);

    PC(String name, IPAddress ipAddress) {
        // Call the parent Device class. With the maximum amount of interfaces set to 1.
        super(name, 1);
        // Sets the IP address of the interface for the PC.
        networkInterfaceFA00.setIpAddress(ipAddress);
    }
}