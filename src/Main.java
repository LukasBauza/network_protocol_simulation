import java.util.Scanner;

// Questions:
// 1. Can I have print statements within class methods?
// 2. Can I wrap all the get functions into a class method?
// 3. Can I print out all the variables within a class method?

class PC {
    private String name;
    private String ip;
    private String subnetMask;
    private String mac;

    public String getName() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter name for PC: ");
        name  = in.nextLine();

        return name;
    }

    public String getIpAddress() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter IP address for " + name + ":");
        ip  = in.nextLine();

        return ip;
    }

    public String getSubnetMask() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter subnet mask address for " + name + ":");
        subnetMask  = in.nextLine();

        return subnetMask;
    }

    public String getMAC() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter mac address for " + name + ":");
        mac  = in.nextLine();

        return mac;
    }

    public void getDetails() {
        name = getName();
        ip = getIpAddress();
        subnetMask = getSubnetMask();
        mac = getMAC();
    }

    public void printDetails() {
        System.out.println("Name for PC: " + name);
        System.out.println("IP address for " + name + ": " + ip);
        System.out.println("Subnet mask for " + name + ": " + subnetMask);
        System.out.println("MAC address for " + name + ": " + mac);
    }

}

public class Main {
    public static void main(String[] args) {
        PC pc0 = new PC();
        pc0.getDetails();
        pc0.printDetails();
    }
}