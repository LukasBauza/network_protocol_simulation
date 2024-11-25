public class Interface {
    private String name;
    private IPAddress ipAddress;

    public Interface(String name, IPAddress ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IPAddress getSourceIPAddress() {
        return ipAddress;
    }

    public void setSourceIPAddress(IPAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
}
