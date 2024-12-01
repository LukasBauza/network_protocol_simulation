public class ICMPHeader {
    private byte type;                  // 0 for ICMP echo request. 8 for ICMP echo reply.
    private byte code;                  // 0 for both echo request and echo reply.
    private short checksum;             // Not really used within simulation.
    private short identifier;           // ID for ICMP packet (0x0001 to 0xFFFF). All pings included.
    private short sequenceNumber;       // Keeps number of ICMP ping successes are made, in one ping call. (1 to 4)

    public ICMPHeader(byte type, byte code, short checksum, short identifier, short sequenceNumber) {
        this.type = type;
        this.code = code;
        this.checksum = checksum;
        this.identifier = identifier;
        this.sequenceNumber = sequenceNumber;
    }

    public byte getType() {
        return type;
    }

    public byte getCode() {
        return code;
    }

    public short getChecksum() {
        return checksum;
    }

    public short getIdentifier() {
        return identifier;
    }

    public short getSequenceNumber() {
        return sequenceNumber;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public void setChecksum(short checksum) {
        this.checksum = checksum;
    }

    public void setIdentifier(short identifier) {
        this.identifier = identifier;
    }

    public void setSequenceNumber(short sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String toString() {
        // TODO: Try to print it in the frame format.
        String fromBit0To31 = "Type: " + Byte.toString(type) + ", Code: " + Byte.toString(code) + ", Checksum: " + Short.toString(checksum);
        String fromBit32To63 = "Identifier: " + Short.toString(identifier) + ", Sequence Number: " + Short.toString(sequenceNumber);
        return fromBit0To31 + fromBit32To63;
    }
}
