import java.util.ArrayList;

public class ARPTable {
    private ArrayList<ARPEntry> entries = new ArrayList<>();

    public ARPTable() {
    }

    public void removeEntry(int entryIndex) {
        this.entries.remove(entryIndex);
    }

    public String toString() {
        String header = "Protocol\tAddress\t\tAge (min)\t Hardware Addr\t Type\t Interface\n";
        StringBuilder table = new StringBuilder(header);
        for (ARPEntry entry : entries) {
            table.append(entry.toString());
        }

        return table.toString();
    }

    public int entryExists(MACAddress macAddress) {
        // Iterate through all the ARP entries within the ARP table, and check if there is a match for the
        // MAC address, return the index of the entry from the ArrayList

        for(int i = 0; i < entries.size(); i++) {
            if(entries.get(i).macAddress.equals(macAddress)) {
                return i;
            }
        }

        return -1;
    }

    public void addEntry(String protocol, IPAddress ipAddress, String age, MACAddress macAddress, String type, String nicName) {
        ARPEntry entry = new ARPEntry(protocol, ipAddress, age, macAddress, type, nicName);
        int oldEntryIndex = entryExists(macAddress);
        // If the device already exists within the ARP table, just remove it and update it.
        if (oldEntryIndex != -1) {
            this.removeEntry(oldEntryIndex);
        }
        this.entries.add(entry);
    }

    static class ARPEntry {
        private String protocol;
        private IPAddress ipAddress;
        private String age;
        private MACAddress macAddress;
        private String type;
        private String nicName;

        public ARPEntry(String protocol, IPAddress ipAddress, String age, MACAddress macAddress, String type, String nicName) {
            this.protocol = protocol;
            this.ipAddress = ipAddress;
            this.age = age;
            this.macAddress = macAddress;
            this.type = type;
            this.nicName = nicName;
        }

        public String toString() {
            StringBuilder entry = new StringBuilder();

            entry.append(protocol);
            entry.append("\t");
            entry.append(ipAddress.toString());
            entry.append("\t\t");
            entry.append(age.toString());
            entry.append("\t");
            entry.append(macAddress.toString());
            entry.append("\t");
            entry.append(type);
            entry.append("\t");
            entry.append(nicName);
            entry.append("\n");

            return entry.toString();
        }
    }
}