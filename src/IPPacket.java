public class IPPacket {
    private byte version;
    private byte ihl;
    private byte dscp;
    private short totalLength;
    private short identification;
    private byte flags;
    private short fragmentOffset;
    private byte timeToLive;
    private byte protocol;
    private short headerCheckSum;
    private IPAddress sourceAddress;
    private IPAddress destinationAddress;

    // Constructor for the IPPacket
    public IPPacket(byte version, byte ihl, byte dscp, short totalLength, short identification, byte flags, short fragmentOffset, byte timeToLive, byte protocol, short headerCheckSum, IPAddress sourceAddress, IPAddress destinationAddress) {
        this.version = version;
        this.ihl = ihl;
        this.dscp = dscp;
        this.totalLength = totalLength;
        this.identification = identification;
        this.flags = flags;
        this.fragmentOffset = fragmentOffset;
        this.timeToLive = timeToLive;
        this.protocol = protocol;
        this.headerCheckSum = headerCheckSum;
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
    }

    // This returns the structure of the packet, used for visualising to the user.
    public String toString() {
        return """
               | 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 |
               |-------------------------------------------------------------------------------------------------|
                  Ver = %d    IHL = %d            DSCP = %d                    Total Length =  %d
               |-------------------------------------------------------------------------------------------------|
                                Identification = %d               Flags = %d       Fragment Offset = %d
               |-------------------------------------------------------------------------------------------------|
                   Time to Live = %d          Protocol = %d                 Header Checksum = %d
               |-------------------------------------------------------------------------------------------------|
                                                    Source Address = %s
               |-------------------------------------------------------------------------------------------------|
                                                 Destination Address = %s
               |-------------------------------------------------------------------------------------------------|
                                                           Data
               |-------------------------------------------------------------------------------------------------|
               """.formatted(version & 0xff, ihl, dscp, totalLength, identification, flags, fragmentOffset, timeToLive, protocol, headerCheckSum, sourceAddress.toString(), destinationAddress.toString());
        // version & 0xff is used to make sure that there is no minus value printed from the unsigned byte.
    }
}