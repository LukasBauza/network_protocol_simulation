import java.util.ArrayList;
import java.util.Scanner;

public class PingProtocol {
    short icmpIdentifier;
    short ipIdentifier;

    public void ping(PC sourcePC, PC destinationPC, ArrayList<PC> pcList, ArrayList<Router> routerList) {
        Scanner scanner = new Scanner(System.in);
        // Check if it is in the sourceDevice ArpTable. If it's not, then you need to do an ARP request and reply and update,
        // the information for both devices ARP tables.

        System.out.println("Select source interface you want to ping from");
        displayNICList(sourcePC.getNICList());
        // -1 for index 0 for the NICList
        NIC sourceNIC = sourcePC.getNICList().get(scanner.nextInt() - 1);

        System.out.println("Select destination interface you want to ping to");
        displayNICList(destinationPC.getNICList());
        NIC destinationNIC = destinationPC.getNICList().get(scanner.nextInt() - 1);

        System.out.println("Checking if destination interface is in local network");
        delay(1);

        if (localNIC(sourceNIC, destinationNIC)) {
            System.out.println("Destination interface is in local network");
            System.out.println("Checking if destination interface is in ARP table");
            if (sourcePC.getARPTable().entryExists(destinationNIC.getMacAddress()) != -1) {
                System.out.println("Destination interface is in ARP table");
            }
            else if (ipExists(destinationNIC.getIpAddress(), pcList, routerList)) {
                // ARP should be successful only if there is an IP address matching the destination in the simulation.
                arpProcessSuccessful(sourcePC, destinationPC);
            } else {
                // ARP should fail if there is no potential device found within the simulation.
                arpProcessFailed(sourcePC, destinationPC);
            }
            System.out.println("~~~~~~~~ Starting Local Ping Process ~~~~~~~");
            localPing(sourcePC, destinationPC, icmpIdentifier, ipIdentifier);
        } else {
            System.out.println("Destination interface is in remote network");
            System.out.println("Checking if destination default gateway is in ARP table");
            // Both the sender and receiver need to have their default gateway set up for remote ping to work.
            if(pcHasDefaultGateway(sourcePC) && pcHasDefaultGateway(destinationPC)) {
                System.out.println("Remote ping failed");
                System.out.println("Check if both devices have a default gateway set up");
            }
            // TODO: First remote ping should always fail. Second one should succeed.
            if (ipExists(destinationNIC.getIpAddress(), pcList, routerList)) {
                // ARP should be successful only if there is an IP address matching the destination in the simulation.
                arpProcessSuccessful(sourcePC, destinationPC);
            } else {
                // ARP should fail if there is no potential device found within the simulation.
                arpProcessFailed(sourcePC, destinationPC);
            }
            System.out.println("~~~~~~~~ Starting Remote Ping Process ~~~~~~~");
            remotePing(sourcePC, destinationPC, icmpIdentifier, ipIdentifier);
        }
    }

    private boolean localNIC(NIC sourceNIC, NIC destinationNIC) {
        // Need to check if the device is a local device or if it is a remote device. This is done by checking if they
        // are in the same network or not.

        IPAddress sourceNetwork = sourceNIC.getNetwork();
        IPAddress destinationNetwork = destinationNIC.getNetwork();

        System.out.println("Network of source: " + sourceNetwork);
        System.out.println("Network of destination: " + destinationNetwork);

        return sourceNetwork.equals(destinationNetwork);
    }

    private void delay(int seconds) {
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

    private void displayNICList(ArrayList<NIC> nicList) {
        for (int i = 0; i < nicList.size(); i++) {
            System.out.println(i + 1 + " " + nicList.get(i).getName());
        }
    }

    private void arpProcessSuccessful(Device sourceDevice, Device destinationDevice) {
        NIC sourceNIC = sourceDevice.getNICList().get(0);
        NIC destinationNIC = destinationDevice.getNICList().get(0);

        System.out.println("Sending ARP request and waiting for ARP reply");
        System.out.println("From " + sourceNIC.getIpAddress() + " to " + destinationNIC.getIpAddress());
        delay(1);
        System.out.println("Received ARP reply from " + destinationNIC.getIpAddress());
        System.out.println("Adding interface to ARP table");
        delay(1);
        // Adding to the source Device ARP table, with the necessary details.
        sourceDevice.getARPTable().addEntry(
                "Internet",                 // Placeholder for now, not really needed in my protocol, just in case for future.
                destinationNIC.getIpAddress(),
                "-",                           // Placeholder for now, not really needed in my protocol, just in case for future.
                destinationNIC.getMacAddress(),
                "ARPA",                        // Placeholder for now, not really needed in my protocol, just in case for future.
                destinationNIC.getName());
        // Adding to the destination Device ARP table, with the necessary details.
        destinationDevice.getARPTable().addEntry(
                "Internet",                 // Placeholder for now, not really needed in my protocol, just in case for future.
                sourceNIC.getIpAddress(),
                "-",                           // Placeholder for now, not really needed in my protocol, just in case for future.
                sourceNIC.getMacAddress(),
                "ARPA",                        // Placeholder for now, not really needed in my protocol, just in case for future.
                sourceNIC.getName());

        System.out.println("--------- " + sourceDevice.getName() + " ARP Table ---------");
        System.out.println(sourceDevice.getARPTable());
        delay(2);

        System.out.println("--------- " + destinationDevice.getName() + " ARP Table ---------");
        System.out.println(destinationDevice.getARPTable());
        delay(2);
    }


    private void arpProcessFailed(Device sourceDevice, Device destinationDevice) {
        NIC sourceNIC = sourceDevice.getNICList().get(0);
        NIC destinationNIC = destinationDevice.getNICList().get(0);

        System.out.println("Sending ARP request and waiting for ARP reply");
        System.out.println("From " + sourceNIC.getIpAddress() + " to " + destinationNIC.getIpAddress());
        delay(3);

        System.out.println("Request time out");
        System.out.println("ARP process failed");
    }

    private boolean ipExists(IPAddress ipAddress, ArrayList<PC> pcList, ArrayList<Router> routerList) {
        // Go through every device within the network and check whether a NIC contains a matching IP address.

        // Go through the list of PCs
        for (int i = 0; i < pcList.size(); i++) {
            for (NIC nic: pcList.get(i).getNICList()) {
                if (nic.getIpAddress().equals(ipAddress)) {
                    return true;
                }
            }
        }

        // Go through the list of Routers
        for (int i = 0; i < routerList.size(); i++) {
            for (NIC nic: routerList.get(i).getNICList()) {
                if (nic.getIpAddress().equals(ipAddress)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void localPing(PC sourcePC, PC destinationPC, short icmpIdentifier, short ipIdentifier) {
        // Ping needs to loop 4 times in order to be completed
        for (int pingCount = 1; pingCount <= 4; pingCount++) {
            ipIdentifier = (short) (ipIdentifier + 0x0001);         // Increment IP identifier when creating IP Headers.
            icmpIdentifier = (short) (icmpIdentifier + 0x0001);     // Same thing for ICMP

            // The source PC creates the IP packet to be sent to the destination
            System.out.println("Creating IP packet for ping.");
            IPPacket ipPacketSource = createIpHeader(sourcePC.getNICList().get(0).getIpAddress(),
                    destinationPC.getNICList().get(0).getIpAddress(),
                    ipIdentifier);
            // Delays are just for simulating time for processing/sending.
            delay(1);
            System.out.println("Created the following IP packet for ping to destination PC");
            System.out.println(ipPacketSource);
            delay(1);

            System.out.println("Creating ICMP packet for ping to destination PC");
            ICMPPacket icmpPacketSource = createIcmpPacket(icmpIdentifier);
            delay(1);
            System.out.println("Created the following ICMP packet: ");
            System.out.println(icmpPacketSource);
            delay(1);

            System.out.println(sourcePC.getName() + " " + "sending ICMP echo request to " + destinationPC.getName());
            delay(1);

            System.out.println(destinationPC.getName() + " " + "received ICMP echo request from " + sourcePC.getName());

            // The destination PC creates the IP packet to be sent back to the source
            System.out.println("Creating IP packet for ping.");
            IPPacket ipPacketDestination = createIpHeader(destinationPC.getNICList().get(0).getIpAddress(),
                    sourcePC.getNICList().get(0).getIpAddress(),
                    ipIdentifier);
            delay(1);
            System.out.println("Created the following IP packet for ping.");
            System.out.println(ipPacketDestination);
            delay(1);

            System.out.println("Creating ICMP packet for ping.");
            ICMPPacket icmpPacketDestination = createIcmpPacket(icmpIdentifier);
            System.out.println("Created the following ICMP packet: ");
            System.out.println(icmpPacketDestination);
            delay(1);

            System.out.println(destinationPC.getName() + " " + "sending ICMP echo reply to " + sourcePC.getName());
            delay(1);

            System.out.println(sourcePC.getName() + " " + "received ICMP echo reply from " + destinationPC.getName());
        }

        System.out.println("Ping complete");
    }

    private void remotePing(PC sourcePC, PC destinationPC, short icmpIdentifier, short ipIdentifier) {
        // Ping needs to loop 4 times to be completed.
        for (int pingCount = 1; pingCount <= 4; pingCount++) {
            ipIdentifier = (short) (ipIdentifier + 0x0001);         // Increment IP identifier when creating IP Headers.
            icmpIdentifier = (short) (icmpIdentifier + 0x0001);     // Same thing for ICMP

            // The source PC creates the IP packet to be sent to its default gateway
            System.out.println("Creating IP packet for ping.");
            // The PC will be sending the IP packet to its default gateway first.
            IPPacket ipPacketSource = createIpHeader(sourcePC.getNICList().get(0).getIpAddress(),
                    destinationPC.getNICList().get(0).getIpAddress(),
                    ipIdentifier);
            System.out.println("Created the following IP packet for ping to destination PC");
            System.out.println(ipPacketSource);
            // Delays are just for simulating time for processing/sending.
            delay(1);

            System.out.println("Creating ICMP packet for ping to destination PC");
            ICMPPacket icmpPacketSource = createIcmpPacket(icmpIdentifier);
            System.out.println("Created the following ICMP packet: ");
            System.out.println(icmpPacketSource);
            delay(1);

            System.out.println(sourcePC.getName() + " " + "sending ICMP echo request to its default gateway: " + sourcePC.getDefaultGatewayIPAddress());
            delay(1);

            System.out.println(destinationPC.getName() + " " + "received ICMP echo request from its default gateway: " + destinationPC.getDefaultGatewayIPAddress());

            // The destination PC creates the IP packet to be sent back to the source
            System.out.println("Creating IP packet for ping.");
            IPPacket ipPacketDestination = createIpHeader(destinationPC.getNICList().get(0).getIpAddress(),
                    sourcePC.getNICList().get(0).getIpAddress(),
                    ipIdentifier);
            System.out.println("Created the following IP packet for ping.");
            System.out.println(ipPacketDestination);
            delay(1);

            System.out.println(destinationPC.getName() + " " + "sending ICMP echo reply to its default gateway: " + destinationPC.getDefaultGatewayIPAddress());
            delay(1);

            System.out.println(sourcePC.getName() + " " + "received ICMP echo reply from its default gateway: " + sourcePC.getDefaultGatewayIPAddress());
        }
    }

    private IPPacket createIpHeader(IPAddress sourceIP, IPAddress destinationIP, short ipIdentifier) {
        // This just builds the IP Header for the ping.
        IPPacket ipPacket = new IPPacket(
                (byte) 4,
                (byte) 5,
                (byte) 0,
                (short) 128,
                ipIdentifier,
                (byte) 0,
                (short) 0,
                (byte) 128,
                (byte) 0x01,                            // 1 indicates ICMP
                (short) 0,
                sourceIP,                               // Source IP
                destinationIP                           // Destination IP
        );

        return ipPacket;
    }

    private ICMPPacket createIcmpPacket(short icmpIdentifier) {
        // This just builds the ICMP Header for ping.
        ICMPPacket icmpPacket = new ICMPPacket(
                (byte) 0,                           // Echo request
                (byte) 0,
                (short) 0,
                icmpIdentifier,                     // Amount of packets made.
                (short) 1                           // First icmp ping between devices.
        );

        return icmpPacket;
    }

    private boolean pcHasDefaultGateway(PC pc) {
        return pc.getDefaultGatewayIPAddress() != null && pc.getDefaultGatewaySubnetMask() != null;
    }
}