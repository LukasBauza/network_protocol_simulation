public class IPHeader {
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

    public IPHeader(byte version, byte ihl, byte dscp, short totalLength, short identification, byte flags, short fragmentOffset, byte timeToLive, byte protocol, short headerCheckSum, IPAddress sourceAddress, IPAddress destinationAddress) {
    }

    public byte getVersion() {
        return version;
    }

    public byte getIhl() {
        return ihl;
    }

    public byte getDscp() {
        return dscp;
    }

    public short getTotalLength() {
        return totalLength;
    }

    public short getIdentification() {
        return identification;
    }

    public byte getFlags() {
        return flags;
    }

    public short getFragmentOffset() {
        return fragmentOffset;
    }

    public byte getTimeToLive() {
        return timeToLive;
    }

    public byte getProtocol() {
        return protocol;
    }

    public short getHeaderCheckSum() {
        return headerCheckSum;
    }

    public IPAddress getSourceAddress() {
        return sourceAddress;
    }

    public IPAddress getDestinationAddress() {
        return destinationAddress;
    }

    public void setVersion(byte version) {
        if (checkByteMaxSize(version, (byte) 0b00001111)) {
            this.version = version;
        } else {
            throw new IllegalArgumentException("Invalid version size, must be between 00000000 and 00001111");
        }
    }

    public void setIhl(byte ihl) {
        if (checkByteMaxSize(ihl, (byte) 0b00001111)) {
            this.ihl = ihl;
        } else {
            throw new IllegalArgumentException("Invalid IHL size, must be between 00000000 and 00001111");
        }
    }

    public void setDscp(byte dscp) {
        this.dscp = dscp;
    }

    public void setTotalLength(short totalLength) {
        this.totalLength = totalLength;
    }

    public void setIdentification(short identification) {
        this.identification = identification;
    }

    public void setFlags(byte flags) {
        if (checkByteMaxSize(flags, (byte) 0b00000111)) {
            this.flags = flags;
        } else {
            throw new IllegalArgumentException("Invalid Flags size, must be between 00000000 and 00000111");
        }
    }

    public void setFragmentOffset(short fragmentOffset) {
        if (checkShortMaxSize(fragmentOffset, (short) 0b0001111111111111)) {
            this.fragmentOffset = fragmentOffset;
        } else {
            throw new IllegalArgumentException("Invalid Fragment Offset size, must be between 00000000 00000000 and 00011111 11111111");
        }
    }

    public void setTimeToLive(byte timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void setProtocol(byte protocol) {
        this.protocol = protocol;
    }

    public void setHeaderCheckSum(short headerCheckSum) {
        this.headerCheckSum = headerCheckSum;
    }

    public void setSourceAddress(IPAddress sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public void setDestinationAddress(IPAddress destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    private boolean checkByteMaxSize(byte myByte, byte maxSizeByte) {
        return (myByte <= maxSizeByte);
    }

    private boolean checkShortMaxSize(short myShort, short maxSizeShort) {
        return (myShort <= maxSizeShort);
    }
}