public class LocalPingTest {
    public static void main(String[] args) {
        short icmpIdentifier = 0x0000;
        short ipIdentifier = 0x0000;

        IPAddress pcAIPAddress = new IPAddress("192.168.1.1");
        SubnetMask pcASubnetMask = new SubnetMask("255.255.255.0");
        PC pcA = new PC("PCA", pcAIPAddress, pcASubnetMask);
        IPAddress pcBIPAddress = new IPAddress("192.168.1.2");
        SubnetMask pcBSubnetMask = new SubnetMask("255.255.255.0");
        PC pcB = new PC("PCB", pcBIPAddress, pcBSubnetMask);

        for (int pingCount = 1; pingCount <= 4; pingCount++) {
            System.out.println("Creating IP header for ping.");
            ipIdentifier = (short) (ipIdentifier + 0x0001);         // Increment IP identifier when creating IP Headers.
            IPHeader ipHeader = createIpHeader(pcA, pcB, ipIdentifier);
            pause(1);

            System.out.println("Creating ICMP header for ping.");
            icmpIdentifier = (short) (icmpIdentifier + 0x0001);
            ICMPHeader icmpHeader = createIcmpHeader(pcA, pcB, icmpIdentifier);
            pause(1);

            System.out.println(pcA.getName() + " " + "sending ICMP echo request to " + pcB.getName());
            pause(2);

            System.out.println(pcB.getName() + " " + "received ICMP echo request from " + pcA.getName());
            pause(1);

            System.out.println(pcB.getName() + " " + "sending ICMP echo reply to " + pcA.getName());
            pause(2);

            System.out.println(pcA.getName() + " " + "received ICMP echo reply from " + pcB.getName());
            pause(1);
        }

        System.out.println("Packets: Sent = 4, Received = 4, Lost = 0");
    }

    public static IPHeader createIpHeader(PC pcA, PC pcB, short ipIdentifier) {
        IPHeader ipHeader = new IPHeader(
                (byte) 4,
                (byte) 5,
                (byte) 0,
                (short) 128,
                ipIdentifier,
                (byte) 0,
                (short) 0,
                (byte) 128,
                (byte) 0x01,                                    // 1 indicates ICMP
                (short) 0,
                pcA.getPortFA00().getIpAddress(),          // Source IP
                pcB.getPortFA00().getIpAddress()           // Destination IP
        );

        return ipHeader;
    }

    public static ICMPHeader createIcmpHeader(PC pcA, PC pcB, short icmpIdentifier) {
        ICMPHeader icmpHeader = new ICMPHeader(
                (byte) 0,                          // Echo request
                (byte) 0,
                (short) 0,
                icmpIdentifier,         // Amount of packets made.
                (short) 1                          // First icmp ping between devices.
        );

        return icmpHeader;
    }

    public static void pause(int seconds) {
        for (int i = 1; i <= seconds; i++) {
            System.out.print(".");
            // Puts the thread to sleep for seconds * 1000, where 1000 is milliseconds.
            try{
                Thread.sleep((long) seconds * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.print(".");
        System.out.println();
    }
}
