import java.util.ArrayList;
import java.util.Scanner;

// delete this comment
public class Main {
    public static void main(String[] args) {

        ArrayList<PC> pcList = new ArrayList<>();
        ArrayList<Router> routerList = new ArrayList<>();

        System.out.print("########################################\n");
        System.out.print("Welcome to the PING protocol simulation.\n");
        System.out.print("########################################\n\n");

        String menu = "main";
        int exit = 0;
        int option = -1;
        int pcSelection = -1;
        int routerSelection = -1;
        Scanner scanner = new Scanner(System.in);

        do {
            if (menu.equals("main")) {
                displayMainMenu(pcList, routerList);
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
                        routerSelection = scanner.nextInt() - 1;

                        try {
                            // Check if the PC exists within the pcList.
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
                displayPCMenu(pcList);
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
                        pc.setPortFA00(ipAddress);
                        break;

                    // Set connection with other PC
                    case 3:
                        System.out.println("Select another PC you want to connect to");
                        break;

                    case 4:
                        // Start the ping process with another device.
                        System.out.println("Enter the IP address you want to ping to");
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
                displayRouterMenu(routerList);
                option = scanner.nextInt();
                // Removes any new line characters that nextInt() didn't use. If not then any nextLine() function used
                // on the scanner, will automatically have \n as its input.
                scanner.nextLine();
                Router router = routerList.get(routerSelection);

                switch (option) {

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

    public static PC createPC() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the name for the PC: ");
        String name = scanner.nextLine();

        System.out.print("Please enter the IP address for " + name + ": ");
        String ipInput = scanner.nextLine();
        IPAddress ip = createIP(ipInput);

        return new PC(name, ip);
    }

    public static Router createRouter() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the name for the Router: ");
        String name = scanner.nextLine();

        System.out.print("Please enter the IP address for " + name + ": ");
        String ipInput = scanner.nextLine();
        IPAddress ip = createIP(ipInput);

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
        System.out.println("Options     \t\t\t| List of Devices");
        System.out.print("------------------------------------------------------------------------------------\n");

        // Picks the largest array/list to iterate through and prints out all of the options from the menu as well as
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

    public static void displayPCMenu(ArrayList<PC> pcList) {
        // Options displayed for the user to choose on what actions they want to take.
        String[] menuOptions = {
                "Change PC name                     ",
                "Change FastEthernet 0/0 IP address ",
                "Connect to another device          ",
                "Ping another device                ",
                "Return to main menu                "
        };

        System.out.print("------------------------------------------------------------------------------------\n");
        System.out.println("Options     \t\t\t\t\t\t\t\t| List of PC's");
        System.out.print("------------------------------------------------------------------------------------\n");

        // Picks the largest array/list to iterate through and prints out all of the options from the menu as well as
        // the devices to the right of the options.
        for(int i = 0; i < Math.max(pcList.size(), menuOptions.length); i++) {
            int lineNum = i + 1;

            if (i < menuOptions.length) {
                // This is for printing out the menu and the devices.
                System.out.print(lineNum + "." + " " + menuOptions[i] + "\t\t" + "|");
            }
            if (i < pcList.size()) {
                // This is for printing out the devices only, once all the options are printed.
                PC pc = pcList.get(i);
                System.out.print(" " + lineNum + "." + " " + pc.getName());
            }
            // Print a new line after every line.
            System.out.println();
        }

        // Some space for user input and the menu.
        System.out.println();
    }

    public static void displayRouterMenu(ArrayList<Router> routerList) {
        // Options displayed for the user to choose on what actions they want to take.
        String[] menuOptions = {
                "Change Router name                    ",
                "Change GigabitEthernet 0/0 IP address ",
                "Change GigabitEthernet 0/1 IP address ",
                "Change GigabitEthernet 0/2 IP address ",
                "Connect to another device             ",
                "Return to main menu                   "
        };

        System.out.print("------------------------------------------------------------------------------------\n");
        System.out.println("Options    \t\t\t\t\t\t\t\t\t\t| List of Router's");
        System.out.print("------------------------------------------------------------------------------------\n");

        // Picks the largest array/list to iterate through and prints out all of the options from the menu as well as
        // the devices to the right of the options.
        for(int i = 0; i < Math.max(routerList.size(), menuOptions.length); i++) {
            int lineNum = i + 1;

            if (i < menuOptions.length) {
                // This is for printing out the menu and the devices.
                System.out.print(lineNum + "." + " " + menuOptions[i] + "\t\t" + "|");
            }
            if (i < routerList.size()) {
                // This is for printing out the devices only, once all the options are printed.
                Router router = routerList.get(i);
                System.out.print(" " + lineNum + "." + " " + router.getName());
            }
            // Print a new line after every line.
            System.out.println();
        }

        // Some space for user input and the menu.
        System.out.println();
    }
}