import java.util.ArrayList;

public class ARPTable {
    private ArrayList<ARPEntry> entries = new ArrayList<>();

    public ARPTable() {
        ArrayList<ARPEntry> entries;
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

     static class ARPEntry {
        private String protocol;
        private IPAddress ipAddresse;
        private String age;
        private MACAddress macAddresse;
        private String type;
        private String nicName;

        public ARPEntry(String protocol, IPAddress ipAddress, String age, MACAddress macAddress, String type, String nicName) {
            this.protocol = protocol;
            this.ipAddresse = ipAddress;
            this.age = age;
            this.macAddresse = macAddress;
            this.type = type;
            this.nicName = nicName;
        }

        public String toString() {
            StringBuilder entry = new StringBuilder();

            entry.append(protocol);
            entry.append("\t");
            entry.append(ipAddresse.toString());
            entry.append("\t\t");
            entry.append(age.toString());
            entry.append("\t");
            entry.append(macAddresse.toString());
            entry.append("\t");
            entry.append(type);
            entry.append("\t");
            entry.append(nicName);
            entry.append("\n");

            return entry.toString();
        }
    }

    public int entryExists(MACAddress macAddress) {
        // Iterate through all the ARP entries within the ARP table, and check if there is a match for the
        // MAC address, return the index of the entry from the ArrayList

        for(int i = 0; i < entries.size(); i++) {
            if(entries.get(i).macAddresse.equals(macAddress)) {
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
            this.entries.remove(oldEntryIndex);
        }
        this.entries.add(entry);
    }
}
