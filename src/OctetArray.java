public abstract class OctetArray {
    private byte[] octetArray;
    private int octetArraySize;

    public OctetArray(int octetArraySize) {
        this.octetArraySize = octetArraySize;
    }

    public byte[] getOctetArray() { return octetArray; }

    public String toString() {
        String[] byteString = new String[octetArraySize];
        for (int i = 0; i < octetArraySize; i++) {
            byteString[i] = Integer.toString(Byte.toUnsignedInt(octetArray[i]));
        }

        System.out.println(String.join(".", byteString));

        return String.join(".", byteString);
    }
}
