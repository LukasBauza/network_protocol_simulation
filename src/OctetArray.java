public abstract class OctetArray {
    private byte[] octetArray;
    private String separator;

    public OctetArray(String separator, int size) {
        this.octetArray = new byte[size];
        this.separator = separator;
    }

    public byte[] getOctetArray() {
        return octetArray;
    }

    public String toString() {
        String bytesString = "";
        for (int i = 0; i < octetArray.length; i++) {
            if (!(i == octetArray.length - 1)) {
                bytesString = bytesString + Integer.toString(Byte.toUnsignedInt(octetArray[i])) + separator;
            } else {
                // Octet at the very right doesn't need the separator.
                bytesString = bytesString + Integer.toString(Byte.toUnsignedInt(octetArray[i]));
            }
        }
        return bytesString;
    }

    // TODO: Start here next
    public void setOctetArray(String octetArray) {

    }

    public void setSpecificByte(int byteLocation, byte byteVal) {

    }

    public boolean equals(Object object) {
        return true;
    }

    public byte[] octetArrayStringToByteArray(String octetArray) {
        return null;
    }
}
