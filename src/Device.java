import java.util.ArrayList;
import java.util.Scanner;

abstract public class Device {
    private String name;
    private ARPTable arpTable = new ARPTable();
    private ArrayList<NIC> nicList;

    public Device(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public ARPTable getARPTable() { return arpTable; }

    public void setNICList(ArrayList<NIC> nicList) { this.nicList = nicList; }

    public ArrayList<NIC> getNICList() { return this.nicList; }

}