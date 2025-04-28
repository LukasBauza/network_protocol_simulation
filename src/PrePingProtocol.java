import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PrePingProtocol {
    private Device sourceDevice;
    private Device destinationDevice;
    private Router[] routers;
    private PC[] pcs;
    private boolean canPing;
    private PrePathCalculation prePathCalculation;
    private PreconfiguredNetworkPanel networkPanel;

    // Declare coordinates as fields but don't initialize yet
    private Point PC0_COORDINATES;
    private Point PC1_COORDINATES;
    private Point PC2_COORDINATES;
    private Point R0_COORDINATES;
    private Point R1_COORDINATES;
    private Point R2_COORDINATES;
    private Point R3_COORDINATES;
    private Point R4_COORDINATES;
    private Point R5_COORDINATES;
    private Point R6_COORDINATES;

    public PrePingProtocol(Device sourceDevice, String strDestinationIP, String strDestinationSubnet, PreconfiguredNetworkPanel networkPanel) {
        this.sourceDevice = sourceDevice;
        this.networkPanel = networkPanel;
        this.routers = networkPanel.getRouters();
        this.pcs = networkPanel.getPCs();
        
        // Initialize coordinates after networkPanel is set
        this.PC0_COORDINATES = networkPanel.getDeviceCoordinates("PC0");
        this.PC1_COORDINATES = networkPanel.getDeviceCoordinates("PC1");
        this.PC2_COORDINATES = networkPanel.getDeviceCoordinates("PC2");
        this.R0_COORDINATES = networkPanel.getDeviceCoordinates("R0");
        this.R1_COORDINATES = networkPanel.getDeviceCoordinates("R1");
        this.R2_COORDINATES = networkPanel.getDeviceCoordinates("R2");
        this.R3_COORDINATES = networkPanel.getDeviceCoordinates("R3");
        this.R4_COORDINATES = networkPanel.getDeviceCoordinates("R4");
        this.R5_COORDINATES = networkPanel.getDeviceCoordinates("R5");
        this.R6_COORDINATES = networkPanel.getDeviceCoordinates("R6");

        if (!destinationExists(new IPAddress(strDestinationIP), new SubnetMask(strDestinationSubnet))) {
            canPing = false;
            System.out.println("Destination does not exist");
            // Show error popup if destination doesn't exist
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    String.format("""
                            IP Address: %s\n
                            SubnetMask: %s\n
                            Destination does not exist in the network""", strDestinationIP, strDestinationSubnet),
                    "Destination Not Found",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        } else {
            canPing = true;
            this.destinationDevice = NICManager.getInstance().getDevice(new IPAddress(strDestinationIP), new SubnetMask(strDestinationSubnet));
            this.prePathCalculation = new PrePathCalculation(this.routers);
        }
    }

    public void ping() {
        if (canPing) {
            String sourceDeviceName = sourceDevice.getName();
            String destinationDeviceName = destinationDevice.getName();
            String path = prePathCalculation.getShortestPath(sourceDeviceName, destinationDeviceName);
            System.out.println(sourceDeviceName + " -> " + destinationDeviceName);
            pathAnimation(path, sourceDeviceName);
        }
    }

    private boolean destinationExists(IPAddress ipAddress, SubnetMask subnetMask) {
        // Get the NICManager instance which keeps track of all NICs in the network
        NICManager nicManager = NICManager.getInstance();
        
        // Check if the IP and subnet mask combination exists in any NIC in the network
        return nicManager.ipAndSubnetExists(ipAddress, subnetMask);
    }

    private void pathAnimation(String path, String startingPoint) {
        System.out.println(path);
        PacketAnimation anim;
        List<Point> pointList;

        switch (path) {
            // PC0 to PC1 paths
            case "PC0--R0--R1--R2--R3--PC1":
                if (startingPoint.equals("PC0")) {
                    pointList = List.of(PC0_COORDINATES, R0_COORDINATES, R1_COORDINATES, R2_COORDINATES, R3_COORDINATES, PC1_COORDINATES);
                } else {
                    pointList = List.of(PC1_COORDINATES, R3_COORDINATES, R2_COORDINATES, R1_COORDINATES, R0_COORDINATES, PC0_COORDINATES);
                }
                break;

            case "PC0--R0--R1--R4--R2--R3--PC1":
                if (startingPoint.equals("PC0")) {
                    pointList = List.of(PC0_COORDINATES, R0_COORDINATES, R1_COORDINATES, R4_COORDINATES, R2_COORDINATES, R3_COORDINATES, PC1_COORDINATES);
                } else {
                    pointList = List.of(PC1_COORDINATES, R3_COORDINATES, R2_COORDINATES, R4_COORDINATES, R1_COORDINATES, R0_COORDINATES, PC0_COORDINATES);
                }
                break;

            case "PC0--R0--R5--R6--R4--R2--R3--PC1":
                if (startingPoint.equals("PC0")) {
                    pointList = List.of(PC0_COORDINATES, R0_COORDINATES, R5_COORDINATES, R6_COORDINATES, R4_COORDINATES, R2_COORDINATES, R3_COORDINATES, PC1_COORDINATES);
                } else {
                    pointList = List.of(PC1_COORDINATES, R3_COORDINATES, R2_COORDINATES, R4_COORDINATES, R6_COORDINATES, R5_COORDINATES, R0_COORDINATES, PC0_COORDINATES);
                }
                break;

            case "PC0--R0--R5--R6--R4--R1--R2--R3--PC1":
                if (startingPoint.equals("PC0")) {
                    pointList = List.of(PC0_COORDINATES, R0_COORDINATES, R5_COORDINATES, R6_COORDINATES, R4_COORDINATES, R1_COORDINATES, R2_COORDINATES, R3_COORDINATES, PC1_COORDINATES);
                } else {
                    pointList = List.of(PC1_COORDINATES, R3_COORDINATES, R2_COORDINATES, R1_COORDINATES, R4_COORDINATES, R6_COORDINATES, R5_COORDINATES, R0_COORDINATES, PC0_COORDINATES);
                }
                break;

            // PC0 to PC2 paths
            case "PC0--R0--R5--R6--PC2":
                if (startingPoint.equals("PC0")) {
                    pointList = List.of(PC0_COORDINATES, R0_COORDINATES, R5_COORDINATES, R6_COORDINATES, PC2_COORDINATES);
                } else {
                    pointList = List.of(PC2_COORDINATES, R6_COORDINATES, R5_COORDINATES, R0_COORDINATES, PC0_COORDINATES);
                }
                break;

            case "PC0--R0--R1--R4--R6--PC2":
                if (startingPoint.equals("PC0")) {
                    pointList = List.of(PC0_COORDINATES, R0_COORDINATES, R1_COORDINATES, R4_COORDINATES, R6_COORDINATES, PC2_COORDINATES);
                } else {
                    pointList = List.of(PC2_COORDINATES, R6_COORDINATES, R4_COORDINATES, R1_COORDINATES, R0_COORDINATES, PC0_COORDINATES);
                }
                break;

            case "PC0--R0--R1--R2--R4--R6--PC2":
                if (startingPoint.equals("PC0")) {
                    pointList = List.of(PC0_COORDINATES, R0_COORDINATES, R1_COORDINATES, R2_COORDINATES, R4_COORDINATES, R6_COORDINATES, PC2_COORDINATES);
                } else {
                    pointList = List.of(PC2_COORDINATES, R6_COORDINATES, R4_COORDINATES, R2_COORDINATES, R1_COORDINATES, R0_COORDINATES, PC0_COORDINATES);
                }
                break;

            // PC1 to PC2 paths
            case "PC1--R3--R2--R4--R6--PC2":
                if (startingPoint.equals("PC1")) {
                    pointList = List.of(PC1_COORDINATES, R3_COORDINATES, R2_COORDINATES, R4_COORDINATES, R6_COORDINATES, PC2_COORDINATES);
                } else {
                    pointList = List.of(PC2_COORDINATES, R6_COORDINATES, R4_COORDINATES, R2_COORDINATES, R3_COORDINATES, PC1_COORDINATES);
                }
                break;

            case "PC1--R3--R2--R1--R4--R6--PC2":
                if (startingPoint.equals("PC1")) {
                    pointList = List.of(PC1_COORDINATES, R3_COORDINATES, R2_COORDINATES, R1_COORDINATES, R4_COORDINATES, R6_COORDINATES, PC2_COORDINATES);
                } else {
                    pointList = List.of(PC2_COORDINATES, R6_COORDINATES, R4_COORDINATES, R1_COORDINATES, R2_COORDINATES, R3_COORDINATES, PC1_COORDINATES);
                }
                break;

            case "PC1--R3--R2--R1--R0--R5--R6--PC2":
                if (startingPoint.equals("PC1")) {
                    pointList = List.of(PC1_COORDINATES, R3_COORDINATES, R2_COORDINATES, R1_COORDINATES, R0_COORDINATES, R5_COORDINATES, R6_COORDINATES, PC2_COORDINATES);
                } else {
                    pointList = List.of(PC2_COORDINATES, R6_COORDINATES, R5_COORDINATES, R0_COORDINATES, R1_COORDINATES, R2_COORDINATES, R3_COORDINATES, PC1_COORDINATES);
                }
                break;

            default:
                System.out.println("Unknown path: " + path);
                return;
        }

        // Create and setup the animation
        anim = new PacketAnimation(pointList);
        anim.setPreferredSize(networkPanel.getSize());
        anim.setSize(networkPanel.getSize());
        anim.setOpaque(false);
        anim.setBounds(0, 0, networkPanel.getWidth(), networkPanel.getHeight());
        
        // Add the animation panel at index 0 (bottom layer)
        networkPanel.add(anim, 0);
        networkPanel.revalidate();
        networkPanel.repaint();
        
        // Start the animation on the EDT
        SwingUtilities.invokeLater(anim::startAnimation);
    }
}
