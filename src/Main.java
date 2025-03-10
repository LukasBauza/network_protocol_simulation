// imports the gui package and the Frame class into this class.

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

// delete this comment
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setTitle("OSPF Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Close the application, when pressing X
        frame.setResizable(false);
        frame.setSize(1000, 300);
        frame.setLayout(new GridLayout());

        Router r1 = new Router("R1");
        Router r2 = new Router("R2");
        Router r3 = new Router("R3");

        // Atomic variable is needed for the sake of syncronising across the difference UI elements, as they won't be
        // updated.
        AtomicReference<Router> selectedRouter = new AtomicReference<>(r1);

        JButton routerButton = new JButton(r1.getName());
        // Function for creating allowing the button to listen for a click, and thus performing the
        // following function (which is a lambda function).
        routerButton.addActionListener(e -> {
            System.out.println("Router " + r1.getName() + " clicked");
            selectedRouter.set(r1);
        });

        JButton pcButton = new JButton(r2.getName());
        pcButton.addActionListener(e -> {
            System.out.println("Router " + r2.getName() + " clicked");
            selectedRouter.set(r2);
        });

        JButton connectButton = new JButton(r3.getName());
        connectButton.addActionListener(e -> {
            System.out.println("Router " + r3.getName() + " clicked");
            selectedRouter.set(r3);
        });

        JPanel itemPanel = new JPanel();
        itemPanel.add(routerButton);
        itemPanel.add(pcButton);
        itemPanel.add(connectButton);
        // rows: 0 is used for allowing as many rows as possible. This fills widgets vertically.
        itemPanel.setLayout(new GridLayout(0, 1));

        JPanel descriptionPanel = new JPanel();

        descriptionPanel.setLayout(new GridLayout(8, 2));

        descriptionPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(selectedRouter.get().getName());
        descriptionPanel.add(nameField);

        descriptionPanel.add(new JLabel("Gig0/0 IP Address:"));
        JTextField gig00IP = new JTextField();
        descriptionPanel.add(gig00IP);

        descriptionPanel.add(new JLabel("Gig0/0 Subnet Mask:"));
        JTextField gig00Mask = new JTextField();
        descriptionPanel.add(gig00Mask);

        descriptionPanel.add(new JLabel("Gig0/1 IP Address:"));
        JTextField gig01IP = new JTextField();
        descriptionPanel.add(gig01IP);

        descriptionPanel.add(new JLabel("Gig0/1 Subnet Mask:"));
        JTextField gig01Mask = new JTextField();
        descriptionPanel.add(gig01Mask);

        descriptionPanel.add(new JLabel("Gig0/2 IP Address:"));
        JTextField gig02IP = new JTextField();
        descriptionPanel.add(gig02IP);

        descriptionPanel.add(new JLabel("Gig0/2 Subnet Mask:"));
        JTextField gig02Mask = new JTextField();
        descriptionPanel.add(gig02Mask);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            Router router = selectedRouter.get();
            router.setPortGig00IPAddress(new IPAddress(gig00IP.getText()));
            System.out.println(new IPAddress(gig00IP.getText()));
            router.setPortGig00SubnetMask(new SubnetMask(gig00Mask.getText()));
            router.setPortGig01IPAddress(new IPAddress(gig01IP.getText()));
            router.setPortGig01SubnetMask(new SubnetMask(gig01Mask.getText()));
            router.setPortGig02IPAddress(new IPAddress(gig02IP.getText()));
            router.setPortGig02SubnetMask(new SubnetMask(gig02Mask.getText()));

            System.out.println("Updated Router: " + router.getName());
            System.out.println("Gig0/0: " + router.getPortGig00().getIpAddress() + " / " + router.getPortGig00().getSubnetMask());
            System.out.println("Gig0/1: " + router.getPortGig01().getIpAddress() + " / " + router.getPortGig01().getSubnetMask());
            System.out.println("Gig0/2: " + router.getPortGig02().getIpAddress() + " / " + router.getPortGig02().getSubnetMask());
        });
        descriptionPanel.add(submitButton, BorderLayout.SOUTH);

        frame.add(descriptionPanel, BorderLayout.CENTER);

        frame.add(itemPanel);
        frame.add(descriptionPanel);
        frame.setVisible(true);                                 // Make frame visible
    }

    public static IPAddress createIP(String ipAddressString) {
        // tries to create an IPAddress, if it doesn't work then it will catch
        //      the error from within the class and prints it out here.
        boolean valid;

        do {
            try {
                new IPAddress(ipAddressString);
                valid = true;
            } catch (IllegalArgumentException e) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Please enter a valid IP address: ");
                ipAddressString = scanner.nextLine();
                valid = false;
            }
        } while (!valid);

        return new IPAddress(ipAddressString);
    }

    public static SubnetMask createSubnetMask(String subnetString) {
        // tries to create an IPAddress, if it doesn't work then it will catch
        //      the error from within the class and prints it out here.
        boolean valid;

        do {
            try {
                new SubnetMask(subnetString);
                valid = true;
            } catch (IllegalArgumentException e) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Please enter a valid subnet mask: ");
                subnetString = scanner.nextLine();
                valid = false;
            }
        } while (!valid);

        return new SubnetMask(subnetString);
    }

    public static PC createPC() {
        // this just prompts the user to enter the needed information for creating a PC. It will ask for the name, IP,
        // and subnet mask and create the object.

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the name for the PC: ");
        String name = scanner.nextLine();

        System.out.print("Please enter the IP address for " + name + ": ");
        String ipInput = scanner.nextLine();
        IPAddress ip = createIP(ipInput);

        System.out.println("Enter the subnet mask for the PC");
        String subnetMaskInput = scanner.nextLine();
        SubnetMask subnetMask = createSubnetMask(subnetMaskInput);

        return new PC(name, ip, subnetMask);
    }

    public static Router createRouter() {
        // this prompts the user to create the router with the input of its name only. The user can change the other
        // details of the router through different options.
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the name for the Router: ");
        String name = scanner.nextLine();

        return new Router(name);
    }

    public static void displayMainMenu(ArrayList<PC> pcList, ArrayList<Router> routerList) {
        // Options displayed for the user to choose on what actions they want to take.
        String[] menuOptions = {
                "Create PC    ",
                "Delete PC    ",
                "Select PC    ",
                "Create Router",
                "Delete Router",
                "Select Router",
                "Exit Program "
        };

        System.out.print("------------------------------------------------------------------------------------\n");
        System.out.println("Options     \t\t\t| List of Devices (PC on Left, Router on Right)");
        System.out.print("------------------------------------------------------------------------------------\n");

        // Picks the largest array/list to iterate through and prints out all the options from the menu as well as
        // the devices to the right of the options.
        int max1 = Math.max(pcList.size(), menuOptions.length);
        int max2 = Math.max(max1, routerList.size());
        // Iterate through the largest array and print out the menu.
        for(int i = 0; i < max2; i++) {
            int lineNum = i + 1;

            if (i < menuOptions.length) {
                // This is for printing out the menu and the devices.
                System.out.print(lineNum + "." + " " + menuOptions[i] + "\t\t" + "|");
            }
            if (i < pcList.size()) {
                // This is for printing out the pc's only, once all the options are printed.
                PC pc = pcList.get(i);
                System.out.print(" " + lineNum + "." + " " + pc.getName());
            }
            if (i < routerList.size()) {
                // This is for printing out the router's only.
                Router router = routerList.get(i);
                System.out.print(" " + lineNum + "." + " " + router.getName());
            }
            // Print a new line after every line.
            System.out.println();
        }

        // Some space for user input and the menu.
        System.out.println();
    }

    public static void displayPCMenu(ArrayList<PC> pcList, Integer pcIndex) {
        // Options displayed for the user to choose on what actions they want to take.
        String[] menuOptions = {
                "Change PC name                     ",
                "Change FastEthernet 0/0 IP address ",
                "Change default gateway             ",
                "Ping another device                ",
                "Return to main menu                "
        };

        String[] pcAttributes = {
                "Name: " + pcList.get(pcIndex).getName(),
                "FastEthernet 0/0 IP address: " + pcList.get(pcIndex).getPortFA00().getIpAddress(),
                "FastEthernet 0/0 subnet mask: " + pcList.get(pcIndex).getPortFA00().getSubnetMask(),
                "FastEthernet 0/0 MAC address: " + pcList.get(pcIndex).getPortFA00().getMacAddress(),
                "Default gateway IP address: " + pcList.get(pcIndex).getDefaultGatewayIPAddress(),
                "Default gateway subnet mask: " + pcList.get(pcIndex).getDefaultGatewaySubnetMask()
        };

        System.out.print("------------------------------------------------------------------------------------\n");
        System.out.println("Options     \t\t\t\t\t\t\t\t| PC Information");
        System.out.print("------------------------------------------------------------------------------------\n");

        // Picks the largest array/list to iterate through and prints out all the options from the menu as well as
        // the devices to the right of the options.
        for(int i = 0; i < Math.max(pcAttributes.length, menuOptions.length); i++) {
            int lineNum = i + 1;

            if (i < menuOptions.length) {
                // This is for printing out the menu and the devices.
                System.out.print(lineNum + "." + " " + menuOptions[i] + "\t\t" + "|");
            } else {
                // Prints out the tabs and barrier for the menu within the router information
                System.out.print("\t\t\t\t\t\t\t\t\t\t\t" + "|");
            }
            if (i < pcAttributes.length) {
                // Prints out the PC details
                System.out.print(" " + pcAttributes[i]);
            }
            // Print a new line after every line.
            System.out.println();
        }

        // Some space for user input and the menu.
        System.out.println();
    }

    public static void displayRouterMenu(ArrayList<Router> routerList, Integer routerIndex) {
        // Options displayed for the user to choose on what actions they want to take.
        String[] menuOptions = {
                "Change Router name                    ",
                "Change GigabitEthernet 0/0 IP address ",
                "Change GigabitEthernet 0/1 IP address ",
                "Change GigabitEthernet 0/2 IP address ",
                "Return to main menu                   "
        };

        String[] routerAttributes = {
                "Name: " + routerList.get(routerIndex).getName(),
                "GigabitEthernet 0/0 IP address: " + routerList.get(routerIndex).getPortGig00().getIpAddress(),
                "GigabitEthernet 0/0 subnet mask: " + routerList.get(routerIndex).getPortGig00().getSubnetMask(),
                "GigabitEthernet 0/0 MAC address: " + routerList.get(routerIndex).getPortGig00().getMacAddress(),
                "GigabitEthernet 0/1 IP address: " + routerList.get(routerIndex).getPortGig01().getIpAddress(),
                "GigabitEthernet 0/1 subnet mask: " + routerList.get(routerIndex).getPortGig01().getSubnetMask(),
                "GigabitEthernet 0/1 MAC address: " + routerList.get(routerIndex).getPortGig01().getMacAddress(),
                "GigabitEthernet 0/2 IP address: " + routerList.get(routerIndex).getPortGig02().getIpAddress(),
                "GigabitEthernet 0/2 subnet mask: " + routerList.get(routerIndex).getPortGig02().getSubnetMask(),
                "GigabitEthernet 0/2 MAC address: " + routerList.get(routerIndex).getPortGig02().getMacAddress(),
        };

        System.out.print("------------------------------------------------------------------------------------\n");
        System.out.println("Options    \t\t\t\t\t\t\t\t\t\t| Router Information");
        System.out.print("------------------------------------------------------------------------------------\n");

        // Picks the largest array/list to iterate through and prints out all the options from the menu as well as
        // the devices to the right of the options.
        for(int i = 0; i < Math.max(routerAttributes.length, menuOptions.length); i++) {
            int lineNum = i + 1;

            if (i < menuOptions.length) {
                // This is for printing out the menu and the devices.
                System.out.print(lineNum + "." + " " + menuOptions[i] + "\t\t" + "|");
            } else {
                // Prints out the tabs and barrier for the menu within the router information
                System.out.print("\t\t\t\t\t\t\t\t\t\t\t\t" + "|");
            }
            if (i < routerAttributes.length) {
                // Prints out the Router details
                System.out.print(" " + routerAttributes[i]);
            }
            // Print a new line after every line.
            System.out.println();
        }

        // Some space for user input and the menu.
        System.out.println();
    }

    public static boolean gig00Match(ArrayList<Router> routerList, IPAddress defaultGatewayIPAddress, SubnetMask subnetMask) {
        // Checks if the router has any assigned subnet mask or IP address to its ports.
        for(Router router : routerList) {
            if((router.getPortGig00().getSubnetMask() == null) || (router.getPortGig00().getIpAddress() == null)) {
                // Move to the next router.
                continue;
            }
            // Checks if the subnet mask or the IP address equal to the required subnet mask and IP for the default
            // gateway.
            if((router.getPortGig00().getSubnetMask().equals(subnetMask)) && (router.getPortGig00().getIpAddress().equals(defaultGatewayIPAddress))) {
                System.out.println("Found match!");
                System.out.println("Router name:" + router.getName());
                System.out.println("Port name: " + router.getPortGig00().getName());
                System.out.println("Port IP address:" + router.getPortGig00().getIpAddress());
                System.out.println("Port Subnet Mask: " + router.getPortGig00().getSubnetMask());
                return true;
            }

            System.out.println(router.getPortGig00().getIpAddress() + " " + router.getPortGig00().getSubnetMask());
            System.out.println(defaultGatewayIPAddress + " " + subnetMask);
        }

        return false;
    }

    public static boolean gig01Match(ArrayList<Router> routerList, IPAddress defaultGatewayIPAddress, SubnetMask subnetMask) {
        // Checks if the router has any assigned subnet mask or IP address to its ports.
        for(Router router : routerList) {
            if((router.getPortGig01().getSubnetMask() == null) || (router.getPortGig01().getIpAddress() == null)) {
                // Move to the next router.
                continue;
            }
            // Checks if the subnet mask or the IP address equal to the required subnet mask and IP for the default
            // gateway.
            if((router.getPortGig01().getSubnetMask().equals(subnetMask)) && (router.getPortGig01().getIpAddress().equals(defaultGatewayIPAddress))) {
                System.out.println("Found match!");
                System.out.println("Router name: " + router.getName());
                System.out.println("Port name: " + router.getPortGig01().getName());
                System.out.println("Port IP address: " + router.getPortGig01().getIpAddress());
                System.out.println("Port Subnet Mask: " + router.getPortGig01().getSubnetMask());
                return true;
            }

            System.out.println(router.getPortGig01().getIpAddress() + " " + router.getPortGig01().getSubnetMask());
        }

        return false;
    }

    public static boolean gig02Match(ArrayList<Router> routerList, IPAddress defaultGatewayIPAddress, SubnetMask subnetMask) {
        // Checks if the router has any assigned subnet mask or IP address to its ports.
        for(Router router : routerList) {
            if((router.getPortGig02().getSubnetMask() == null) || (router.getPortGig02().getIpAddress() == null)) {
                // Move to the next router.
                continue;
            }
            // Checks if the subnet mask or the IP address equal to the required subnet mask and IP for the default
            // gateway.
            if((router.getPortGig02().getSubnetMask().equals(subnetMask)) && (router.getPortGig02().getIpAddress().equals(defaultGatewayIPAddress))) {
                System.out.println("Found match!");
                System.out.println("Router name:" + router.getName());
                System.out.println("Port name: " + router.getPortGig02().getName());
                System.out.println("Port IP address:" + router.getPortGig02().getIpAddress());
                System.out.println("Port Subnet Mask: " + router.getPortGig02().getSubnetMask());
                return true;
            }

            System.out.println(router.getPortGig02().getIpAddress() + " " + router.getPortGig02().getSubnetMask());
        }

        return false;
    }

    public static boolean checkDefaultGatewayExists(ArrayList<Router> routerList, IPAddress defaultGatewayIPAddress, SubnetMask subnetMask) {
        // This is just a holder for the previous gigXXMatch functions, it checks if any of the interfaces matched with the required
        // subnet mask and ip address.
        return gig00Match(routerList, defaultGatewayIPAddress, subnetMask) || (gig01Match(routerList, defaultGatewayIPAddress, subnetMask)) ||
                gig02Match(routerList, defaultGatewayIPAddress, subnetMask);
    }

    public static boolean checkDefaultGatewayAssigned(ArrayList<PC> assignedDefaultGatewayList, IPAddress ipAddress, SubnetMask subnetMask) {
        // This checks if a default gateway is already assigned to another PC for a given router interface.
        for(PC pc : assignedDefaultGatewayList) {
            if((pc.getDefaultGatewaySubnetMask().equals(subnetMask)) && (pc.getDefaultGatewayIPAddress().equals(ipAddress))) {
                return true;
            }
        }

        return false;
    }

    public static int getIndexFromPCListWithIP(IPAddress ip, ArrayList<PC> pcList) {
        // Search through all the PCs in the network and see if there is an available PC.
        for(int i = 0; i < pcList.size(); i++) {
            if(pcList.get(i).getNICList().get(0).getIpAddress().equals(ip)) {
                // return the index from the ArrayList
                return i;
            }
        }

        return -1;
    }
}