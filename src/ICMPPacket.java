public class ICMPPacket {
    private byte type;                  // 0 for ICMP echo request. 8 for ICMP echo reply.
    private byte code;                  // 0 for both echo request and echo reply.
    private short checksum;             // Not really used within simulation.
    private short identifier;           // ID for ICMP packet (0x0001 to 0xFFFF). All pings included.
    private short sequenceNumber;       // Keeps number of ICMP ping successes are made, in one ping call. (1 to 4)

    // Constructor for the ICMPPacket
    public ICMPPacket(byte type, byte code, short checksum, short identifier, short sequenceNumber) {
        this.type = type;
        this.code = code;
        this.checksum = checksum;
        this.identifier = identifier;
        this.sequenceNumber = sequenceNumber;
    }

    // This returns the structure of the packet, used for visualising to the user.
    public String toString() {
        return """
               | 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 |
               |-------------------------------------------------------------------------------------------------|
                         Type = %d                Code = %d                          Checksum = %d
               |-------------------------------------------------------------------------------------------------|
                                   Identifier = %d                               Sequence Number = %d
               |-------------------------------------------------------------------------------------------------|
                                                            Data
               |-------------------------------------------------------------------------------------------------|
               """.formatted(type, code, checksum, identifier, sequenceNumber);
    }
}
