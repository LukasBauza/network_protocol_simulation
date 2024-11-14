import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // -----------------------------------------
        // Menu implementation for allowing the user to select what they want to do within the application.
        //PC[] pcArray = new PC[10];
        ArrayList<PC> pcList = new ArrayList<>();

        int option;
        do {
            System.out.println("Welcome to the PING protocol simulation. Please select and option from below:");
            System.out.println("1. Create PC");
            System.out.println("2. Delete PC");
            System.out.println("3. List PC's");
            System.out.println("4. PING between two PC's");
            System.out.println("5. Exit");
            Scanner scanner = new Scanner(System.in);
            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    pcList.add(createPC());
                    break;
                case 2:
                    // This will need to show a list of PC's where the user can select what PC to delete.
                    System.out.println("Select a PC to delete");
                    for(int i = 0; i < pcList.size(); i++) {
                        // Prints the name of the details of the PC
                        PC pc = pcList.get(i);
                        System.out.println(i + " " + pc.getName() + " " + pc.getIpAddress().getIpAddress() + " " + pc.getMacAddress().getMacAddress());
                    }
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
            }
        } while (option != 5);

        // -----------------------------------------

        //Scanner scanner = new Scanner(System.in);

        //System.out.print("Please enter the name for the PC: ");
        //String name1 = scanner.nextLine();

        //System.out.print("Please enter the IP address for " + name1 + ": ");
        //String ipInput1 = scanner.nextLine();
        //IPAddress ip1 = createIP(ipInput1);
        //System.out.println("This is the IP address for " + name1 + ": " + ip1.getIpAddress());

        //System.out.print("Please enter the MAC address for " + name1 + ": ");
        //String macInput1 = scanner.nextLine();
        //MACAddress mac1 = createMAC(macInput1);
        //System.out.println("This is the MAC address for " + name1 + ": " + mac1.getMacAddress());

        //PC pc1 = new PC(name1, ip1, mac1);

        //System.out.println("Here is all the details of " + pc1.getName() + ": ");
        //System.out.println(pc1.getIpAddress().getIpAddress());
        //System.out.println(pc1.getMacAddress().getMacAddress());

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
}