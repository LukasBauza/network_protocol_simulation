import java.util.Vector;

public class ARPTable {
    private Vector<String> protocols;
    private Vector<IPAddress> ipAddresses;
    private Vector<Integer> ages;
    private Vector<MACAddress> macAddresses;
    private Vector<String> types;
    private Vector<Interface> interfaces;

    public ARPTable() {
        protocols = new Vector<>();
        ipAddresses = new Vector<>();
        ages = new Vector<>();
        macAddresses = new Vector<>();
        types = new Vector<>();
        interfaces = new Vector<>();
    }

    public void addEntry(String protocol, IPAddress ipAddress, Integer age, MACAddress macAddress, String type, Interface inter) {
        this.protocols.add(protocol);
        this.ipAddresses.add(ipAddress);
        this.ages.add(age);
        this.macAddresses.add(macAddress);
        this.types.add(type);
        this.interfaces.add(inter);
    }

    public void removeEntry(int entryIndex) {
        this.protocols.remove(entryIndex);
        this.ipAddresses.remove(entryIndex);
        this.ages.remove(entryIndex);
        this.macAddresses.remove(entryIndex);
        this.types.remove(entryIndex);
        this.interfaces.remove(entryIndex);
    }

    public String toString() {
        StringBuilder entries = new StringBuilder("Protocol\tAddress\t\tAge (min)\t Hardware Addr\t Type\t Interface\n");
        for (int i = 0; i < protocols.size(); i++) {
            entries.append(protocols.get(i));
            entries.append("\t");
            entries.append(ipAddresses.get(i).toString());
            entries.append("\t\t");
            entries.append(ages.get(i).toString());
            entries.append("\t");
            entries.append(macAddresses.get(i).toString());
            entries.append("\t");
            entries.append(types.get(i));
            entries.append("\t");
            entries.append(interfaces.get(i).getName());
        }

        return entries.toString();
    }
}