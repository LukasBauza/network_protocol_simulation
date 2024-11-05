import java.util.Scanner;

// Questions:
// 1. Can I have print statements within class methods?
// 2. Can I wrap all the get functions into a class method?
// 3. Can I print out all the variables within a class method?
// 4. Should you have data validation for a class within the class or in main?
// 5. Should I have an array to be the input of the object, or individual values that
// will be stored in an array?

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the name for the PC: ");
        String name1 = scanner.nextLine();

        System.out.print("Please enter the IP address for " + name1 + ": ");
        String ipInput1 = scanner.nextLine();
        IPAddress ip1 = createIP(ipInput1);
        System.out.println("This is the IP address for " + name1 + ": " + ip1.getIPAddress());

        System.out.print("Please enter the MAC address for " + name1 + ": ");
        String macInput1 = scanner.nextLine();
        MACAddress mac1 = createMAC(macInput1);
        System.out.println("This is the MAC address for " + name1 + ": " + mac1.getMACAddress());

        PC pc1 = new PC(name1, ip1, mac1);

        System.out.println("Here is all the details of " + pc1.getName() + ": ");
        System.out.println(pc1.getIpAddress().getIPAddress());
        System.out.println(pc1.getMacAddress().getMACAddress());

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
}