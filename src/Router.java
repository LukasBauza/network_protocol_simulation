public class Router extends Device {
    private Port gig00 = new Port("GigabitEthernet 0/0");
    private Port gig01 = new Port("GigabitEthernet 0/1");
    private Port gig02 = new Port("GigabitEthernet 0/2");

    public Router(String name) {
        super(name);
    }

    public Port getPortGig00() { return gig00; }
    public void setPortGig00(Port port) { this.gig00 = port; }

    public Port getPortGig01() { return gig01; }
    public void setPortGig01(Port port) { this.gig01 = port; }

    public Port getPortGig02() { return gig02; }
    public void setPortGig02(Port port) { this.gig02 = port; }
}
