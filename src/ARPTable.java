import java.util.ArrayList;

public class ARPTable {
    private ArrayList<ARPEntry> entries;

    public ARPTable() {
        ArrayList<ARPEntry> entries;
    }

    public void addEntry(String protocol, IPAddress ipAddress, Integer age, MACAddress macAddress, String type, Port port) {
        ARPEntry entry = new ARPEntry(protocol, ipAddress, age, macAddress, type, port);
        this.entries.add(entry);
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
        private Integer age;
        private MACAddress macAddresse;
        private String type;
        private Port port;

        public ARPEntry(String protocol, IPAddress ipAddress, Integer age, MACAddress macAddress, String type, Port port) {
            this.protocol = protocol;
            this.ipAddresse = ipAddress;
            this.age = age;
            this.macAddresse = macAddress;
            this.type = type;
            this.port = port;
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
            entry.append(port.getName());
            entry.append("\n");

            return entry.toString();
        }
    }
}
