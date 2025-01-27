import java.util.ArrayList;

abstract public class Device {
    // Abstract class for holding data memebers and methods that are common within the Router and PC child classes.

    private String name;                            // Name of the device
    private ARPTable arpTable = new ARPTable();     // ARP table for the device.
    private ArrayList<NIC> nicList;                 // Interfaces for the device

    // Constructor which is used within the creation of the child class.
    public Device(String name) {
        this.name = name;
    }

    // Get and set functions for retrieving data members, or changing their values.

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public ARPTable getARPTable() { return arpTable; }

    public void setNICList(ArrayList<NIC> nicList) { this.nicList = nicList; }

    public ArrayList<NIC> getNICList() { return this.nicList; }

}