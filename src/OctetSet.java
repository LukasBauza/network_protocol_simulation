public abstract class OctetSet {
    // an abstract class cannot have instances, this is because you wouldn't have an instance of an OctetSet within
    // a network simulation.

    // protected is a data modifier
    // protected data within the class means that only child classes are able to access the data, and nothing else.
    protected int size;
    protected char separator;
    protected int count;
    protected int[] octets;

    public OctetSet(int size, char separator, int count, int[] octets) {
        // constructor for the class
        this.size = size;
        this.separator = separator;
        this.count = count;
        this.octets = octets;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSeparator() {
        return separator;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int[] getOctets() {
        return octets;
    }

    public void setOctets(int[] octets) {
        this.octets = octets;
    }
}