import java.util.ArrayList;
import java.util.Scanner;

// delete this comment
public class Main {
    public static void main(String[] args) {

        ArrayList<PC> pcList = new ArrayList<>();

        int option;
        do {
            System.out.print("########################################\n");
            System.out.print("Welcome to the PING protocol simulation.\n");
            System.out.print("########################################\n\n");
            printMenu(pcList);

            Scanner scanner = new Scanner(System.in);
            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    pcList.add(createPC());
                    break;

                case 2:
                    // This will need to show a list of PC's where the user can select what PC to delete.
                    System.out.println("Select a PC to delete");
                    int selection = Integer.parseInt(scanner.nextLine());
                    pcList.remove(selection);
                    break;

                case 3:
                    for(int i = 0; i < pcList.size(); i++) {
                        // Prints the name of the PC
                        PC pc = pcList.get(i);
                        System.out.println(i + " " + pc.getName() + " " + pc.getIpAddress().getIpAddress() + " " + pc.getMacAddress().getMacAddress());
                    }
                    break;

                case 4:
                    System.out.println("Not finished");
                    break;

                case 5:
                    System.out.println("Bye");
                    break;

                default:
                    System.out.println("Invalid option");
            }

            // Print the menu after an action has been taken by the user.
            //printMenu(pcList);
        } while (option != 5);

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

    public static MACAddress createMAC(String macAddressString) {
        // tries to create an MACAddress, if it doesn't work then it will catch
        //      the error from within the class and prints it out here.
        boolean valid;

        do {
            try {
                new MACAddress(macAddressString);
                valid = true;
            } catch (IllegalArgumentException e) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Please enter a valid MAC address: ");
                macAddressString = scanner.nextLine();
                valid = false;
            }
        } while (!valid);

        return new MACAddress(macAddressString);
    }

    public static PC createPC() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the name for the PC: ");
        String name = scanner.nextLine();

        System.out.print("Please enter the IP address for " + name + ": ");
        String ipInput = scanner.nextLine();
        IPAddress ip = createIP(ipInput);
        System.out.println("This is the IP address for " + name + ": " + ip.getIpAddress());

        System.out.print("Please enter the MAC address for " + name + ": ");
        String macInput = scanner.nextLine();
        MACAddress mac = createMAC(macInput);
        System.out.println("This is the MAC address for " + name + ": " + mac.getMacAddress());

        PC pc = new PC(name, ip, mac);

        System.out.println("Here is all the details of " + pc.getName() + ": ");
        System.out.println(pc.getIpAddress().getIpAddress());
        System.out.println(pc.getMacAddress().getMacAddress());

        return pc;
    }

    public static void printMenu(ArrayList<PC> pcList) {
        // Options displayed for the user to choose on what actions they want to take.
        String[] menuOptions = {
                "Create device",
                "Delete device",
                "Select device",
                "Exit Program "
        };

        System.out.print("------------------------------------------------------------------------------------\n");
        System.out.println("Options     \t\t\t| List of PC's");
        System.out.print("------------------------------------------------------------------------------------\n");

        // Picks the largest array/list to iterate through and prints out all of the options from the menu as well as
        // the devices to the right of the options.
        for(int i = 0; i < Math.max(pcList.size(), menuOptions.length); i++) {

            if (i < menuOptions.length) {
                // This is for printing out the menu and the devices.
                System.out.print(i+1 + " " + menuOptions[i] + "\t\t\t" + "|");
            }
            if (i < pcList.size()) {
                // This is for printing out the devices only, once all of the options are printed.
                PC pc = pcList.get(i);
                System.out.print(" " + i+1 + pc.getName() + " " + pc.getIpAddress().getIpAddress() + " " + pc.getMacAddress().getMacAddress());
            }
            // Print a new line after every line.
            System.out.println();
        }

        // Some space for user input and the menu.
        System.out.println();
    }
}