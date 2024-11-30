import java.util.ArrayList;
import java.util.Scanner;

// delete this comment
public class Main {
    public static void main(String[] args) {

        ArrayList<PC> pcList = new ArrayList<>();

        System.out.print("########################################\n");
        System.out.print("Welcome to the PING protocol simulation.\n");
        System.out.print("########################################\n\n");

        String menu = "main";
        int exit = 0;
        int option = -1;
        int pcSelection = -1;
        Scanner scanner = new Scanner(System.in);

        do {
            if (menu.equals("main")) {
                printMainMenu(pcList);
                option = scanner.nextInt();

                switch (option) {
                    // Create device
                    case 1:
                        pcList.add(createPC());
                        break;

                    case 2:
                        // Delete device
                        // This will need to show a list of PC's where the user can select what PC to delete.
                        System.out.println("Select a PC to delete");
                        // -1 becuase it is based on the index of the ArrayList of the PC.
                        pcSelection = scanner.nextInt() - 1;
                        pcList.remove(pcSelection);
                        break;

                    case 3:
                        // Select device
                        System.out.println("Select a PC from the list");
                        pcSelection = scanner.nextInt() - 1;
                        menu = "pc";
                        // Reset the option for the PC menu.
                        option = -1;
                        break;

                    case 4:
                        System.out.println("Bye");
                        exit = 1;
                        break;

                    default:
                        System.out.println("Invalid option");
                        break;
                }

            } else if (menu.equals("pc")) {
                printPCMenu(pcList);
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
                        pc.setInterfaceFA00IPAddress(ipAddress);
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
        System.out.println("This is the IP address for " + name + ": " + ip.toString());

        PC pc = new PC(name, ip);

        System.out.println("Here is all the details of " + pc.getName() + ": ");
        System.out.println(pc.getInterfaceFA00().getIpAddress().toString());
        System.out.println(pc.getInterfaceFA00().getMacAddress().toString());

        return pc;
    }

    public static void menuTemplate(ArrayList<PC> pcList, String[] menuOptions) {
        System.out.print("------------------------------------------------------------------------------------\n");
        System.out.println("Options     \t\t\t| List of PC's");
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
                // This is for printing out the devices only, once all of the options are printed.
                PC pc = pcList.get(i);
                //                                                                Needs to get the IPAddress object and then the IPAddress as a string
                System.out.print(" " + lineNum + "." + " " + pc.getName() + " " + pc.getInterfaceFA00().getIpAddress().toString() + " " + pc.getInterfaceFA00().getMacAddress().toString());
            }
            // Print a new line after every line.
            System.out.println();
        }

        // Some space for user input and the menu.
        System.out.println();
    }

    public static void printMainMenu(ArrayList<PC> pcList) {
        // Options displayed for the user to choose on what actions they want to take.
        String[] mainMenuOptions = {
                "Create PC    ",
                "Delete PC    ",
                "Select PC    ",
                "Exit Program "
        };

        menuTemplate(pcList, mainMenuOptions);
    }

    public static void printPCMenu(ArrayList<PC> pcList) {
        // Options displayed for the user to choose on what actions they want to take.
        String[] deviceMenuOptions = {
                "Change PC name        ",
                "Change PC IP address  ",
                "Connect to another PC ",
                "Return to main menu   "
        };

        menuTemplate(pcList, deviceMenuOptions);
    }

}