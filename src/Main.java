import java.util.ArrayList;
import java.util.Scanner;

// delete this comment
public class Main {
    public static void main(String[] args) {

        // All the created PCs within the simulation.
        ArrayList<PC> pcList = new ArrayList<>();
        // All the created Routers within the simulation.
        ArrayList<Router> routerList = new ArrayList<>();
        // Keeps track of all the default gateways that have been assigned to a PC.
        ArrayList<PC> assignedDefaultGatewayList = new ArrayList<>();

        System.out.print("########################################\n");
        System.out.print("Welcome to the PING protocol simulation.\n");
        System.out.print("########################################\n\n");

        String menu = "main";
        int exit = 0;
        int option = -1;
        int pcSelection = -1;
        int routerSelection = -1;
        Scanner scanner = new Scanner(System.in);
        PingProtocol pingProtocol = new PingProtocol();

        do {
            if (menu.equals("main")) {
                displayMainMenu(pcList, routerList);

                // Checks whether the scanner had received an int or not, if it doesn't it prnts the below.
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next(); // Consume the invalid input
                }
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        // Create PC and add it to the list of PC's
                        pcList.add(createPC());
                        break;

                    case 2:
                        // Delete PC
                        // This will need to show a list of PC's where the user can select what PC to delete.
                        System.out.println("Select a PC to delete");
                        // Checks whether the scanner had received an int or not, if it doesn't it prnts the below.
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next(); // Consume the invalid input
                        }
                        // -1 because it is based on the index of the ArrayList of the PC.
                        pcSelection = scanner.nextInt() - 1;

                        try {
                            pcList.remove(pcSelection);
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("PC does not exist");
                        }

                        break;

                    case 3:
                        // Select PC
                        System.out.println("Select a PC from the list");
                        // Checks whether the scanner had received an int or not, if it doesn't it prnts the below.
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next(); // Consume the invalid input
                        }
                        pcSelection = scanner.nextInt() - 1;

                        try {
                            // Check if the PC exists within the pcList.
                            pcList.get(pcSelection);
                            menu = "pc";
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("PC does not exist");
                            menu = "main";
                        }

                        // Reset the option for the PC menu.
                        option = -1;
                        break;

                    case 4:
                        // Create a Router
                        routerList.add(createRouter());
                        break;

                    case 5:
                        // Delete a Router
                        System.out.println("Select a Router to delete");
                        // Checks whether the scanner had received an int or not, if it doesn't it prnts the below.
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next(); // Consume the invalid input
                        }
                        routerSelection = scanner.nextInt() - 1;

                        try {
                            routerList.remove(routerSelection);
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Router does not exist");
                        }

                        break;

                    case 6:
                        // Select a Router
                        System.out.println("Select a Router from the list");
                        // Checks whether the scanner had received an int or not, if it doesn't it prnts the below.
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next(); // Consume the invalid input
                        }
                        routerSelection = scanner.nextInt() - 1;

                        try {
                            // Check if the Router exists within the routerList.
                            routerList.get(routerSelection);
                            menu = "router";
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Router does not exist");
                            menu = "main";
                        }

                        // Reset the option for the PC menu.
                        option = -1;
                        break;

                    case 7:
                        // Exit the program.
                        System.out.println("Bye");
                        exit = 1;
                        break;

                    default:
                        System.out.println("Invalid option");
                        break;
                }

            } else if (menu.equals("pc")) {
                displayPCMenu(pcList, pcSelection);
                // Checks whether the scanner had received an int or not, if it doesn't it prnts the below.
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next(); // Consume the invalid input
                }
                option = scanner.nextInt();
                // Removes any new line characters that nextInt() didn't use. If not then any nextLine() function used
                // on the scanner, will automatically have \n as its input.
                scanner.nextLine();
                PC pc = pcList.get(pcSelection);

                switch (option) {
                    // Change name
                    case 1:
                        System.out.println("Enter the new name for the PC");
                        String name = scanner.nextLine();
                        pc.setName(name);
                        break;

                    // Change IP address
                    case 2:
                        System.out.println("Enter the new IP for the PC");
                        String ipAddressString = scanner.nextLine();
                        IPAddress ipAddress = createIP(ipAddressString);

                        System.out.println("Enter the subnet mask for the PC");
                        String subnetMaskString = scanner.nextLine();
                        SubnetMask subnetMask = createSubnetMask(subnetMaskString);

                        pc.setPortFA00(ipAddress, subnetMask);
                        break;

                    // Set default gateway for the PC
                    case 3:
                        System.out.println("Enter the IP and subnet mask for the default gateway");
                        String defaultGatewayString = scanner.nextLine();
                        IPAddress defaultGatewayIP = createIP(defaultGatewayString);

                        System.out.println("Enter the subnet mask for the default gateway");
                        String defaultGatewaySubnetMaskString = scanner.nextLine();
                        SubnetMask defaultGatewaySubnet = createSubnetMask(defaultGatewaySubnetMaskString);

                        if(!checkDefaultGatewayExists(routerList, defaultGatewayIP, defaultGatewaySubnet)) {
                            // Checks to see whether the default gateway exists within the network.
                            System.out.println("The provided default gateway does not exist within the network.");
                        } else if (checkDefaultGatewayAssigned(assignedDefaultGatewayList, defaultGatewayIP, defaultGatewaySubnet)) {
                            // Checks if the default gateway is not assigned, to another PC.
                            System.out.println("The provided default gateway is already assigned to another device.");
                        } else {
                            // Sets the default gateway for the PC.
                            pc.setDefaultGateway(defaultGatewayIP, defaultGatewaySubnet);
                            // Add the PC to the list of assigned default gateways, only if the PC is not already in the
                            // list
                            if(!assignedDefaultGatewayList.contains(pc)) {
                                assignedDefaultGatewayList.add(pc);
                            }
                        }

                        break;

                    case 4:
                        // Start the ping process with another device.
                        System.out.println("Enter the IP address you want to ping to");
                        IPAddress destinationIP = new IPAddress(scanner.nextLine());
                        int destinationPCIndex = getIndexFromPCListWithIP(destinationIP, pcList);
                        if(destinationPCIndex != -1) {
                            pingProtocol.ping(pc, pcList.get(destinationPCIndex), pcList, routerList);
                        } else {
                            System.out.println("Destination PC does not exist");
                        }
                        break;

                    case 5:
                        // Return to the main menu.
                        menu = "main";
                        break;

                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } else if (menu.equals("router")) {
                displayRouterMenu(routerList, routerSelection);
                // Checks whether the scanner had received an int or not, if it doesn't it prnts the below.
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next(); // Consume the invalid input
                }
                option = scanner.nextInt();
                // Removes any new line characters that nextInt() didn't use. If not then any nextLine() function used
                // on the scanner, will automatically have \n as its input.
                scanner.nextLine();
                Router router = routerList.get(routerSelection);

                switch (option) {
                    case 1:
                        System.out.println("Enter the new name for the router");
                        String name = scanner.nextLine();
                        router.setName(name);
                        break;

                        case 2:
                            System.out.println("Enter IP address for GigabitEthernet 0/0");
                            String ipAddress00String = scanner.nextLine();
                            IPAddress ipAddress00 = createIP(ipAddress00String);

                            System.out.println("Enter the subnet mask for GigabitEthernet 0/0");
                            String subnetMask00String = scanner.nextLine();
                            SubnetMask subnetMask00 = createSubnetMask(subnetMask00String);

                            router.setPortGig00(ipAddress00, subnetMask00);
                            break;

                        case 3:
                            System.out.println("Enter IP address for GigabitEthernet 0/1");
                            String ipAddress01String = scanner.nextLine();
                            IPAddress ipAddress01 = createIP(ipAddress01String);

                            System.out.println("Enter the subnet mask for GigabitEthernet 0/1");
                            String subnetMask01String = scanner.nextLine();
                            SubnetMask subnetMask01 = createSubnetMask(subnetMask01String);

                            router.setPortGig01(ipAddress01, subnetMask01);
                            break;

                        case 4:
                            System.out.println("Enter IP address for GigabitEthernet 0/2");
                            String ipAddress02String = scanner.nextLine();
                            IPAddress ipAddress02 = createIP(ipAddress02String);

                            System.out.println("Enter the subnet mask for GigabitEthernet 0/2");
                            String subnetMask02String = scanner.nextLine();
                            SubnetMask subnetMask02 = createSubnetMask(subnetMask02String);

                            router.setPortGig02(ipAddress02, subnetMask02);
                            break;

                            case 5:
                                menu = "main";
                                break;
                }
            }
        } while (exit == 0);
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
        return gig00Match(routerList, defaultGatewayIPAddress, subnetMask) || (gig01Match(routerList, defaultGatewayIPAddress, subnetMask)) ||
                gig02Match(routerList, defaultGatewayIPAddress, subnetMask);
    }

    public static boolean checkDefaultGatewayAssigned(ArrayList<PC> assignedDefaultGatewayList, IPAddress ipAddress, SubnetMask subnetMask) {

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