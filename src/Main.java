import java.util.Scanner;

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
}

public class Main {
    public static void main(String[] args) {
        PC pc0 = new PC();

        String pc0Name = pc0.getName();
        String pc0IP = pc0.getIpAddress();
        String pc0SubnetMask = pc0.getSubnetMask();
        String pc0MAC = pc0.getMAC();

        System.out.println("Name for PC: " + pc0Name);
        System.out.println("IP address for " + pc0Name + ": " + pc0IP);
        System.out.println("Subnet mask for " + pc0Name + ": " + pc0SubnetMask);
        System.out.println("MAC address for " + pc0Name + ": " + pc0MAC);

        //Scanner in = new Scanner(System.in);

        //System.out.println("Enter name for PC: ");
        //pc0.name  = in.nextLine();
        //System.out.println("Name for PC: " + pc0.name);

        //System.out.println("Enter IP address for " + pc0.name + ":");
        //pc0.ip  = in.nextLine();
        //System.out.println("IP address for PC0: " + pc0.ip);

        //System.out.println("Enter subnet mask for " + pc0.name + ":");
        //pc0.subnetMask  = in.nextLine();
        //System.out.println("Subnet mask for " + pc0.subnetMask);
    }
}