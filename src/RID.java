public class RID {
    //private String ipAddress;
    private byte[] rid = new byte[4];

    public RID(String rid) {
        // constructor used for checking that each segment is the right type and in the right range.
        //      if a wrong type is entered, it will throw out an error into main, which
        //      can be caught and used for error handling.
        if (validIpAddress(rid)) {
            this.rid = ipStringToByteArray(rid);
        } else {
            throw new IllegalArgumentException("Each segment must be between 0 and 255.");
        }
    }

    public RID() {
        this.rid = new byte[4];
    }

    public byte[] getRid() {
        return rid;
    }

    public String toString() {
        // Needs to be converted to an unsigned int, to not have minus values. As bytes are represented in negatives.
        String byte0 = Integer.toString(Byte.toUnsignedInt(rid[0]));
        String byte1 = Integer.toString(Byte.toUnsignedInt(rid[1]));
        String byte2 = Integer.toString(Byte.toUnsignedInt(rid[2]));
        String byte3 = Integer.toString(Byte.toUnsignedInt(rid[3]));

        return byte0 + "." + byte1 + "." + byte2 + "." + byte3;
    }

    public void setRid(String rid) {
        //this.ipAddress = ipAddress;
        if (validIpAddress(rid)) {
            this.rid = ipStringToByteArray(rid);
        } else {
            throw new IllegalArgumentException("Each segment must be between 0 and 255.");
        }
    }

    public void setByte3(byte byte3) {
        this.rid[3] = byte3;
    }

    public void setByte2(byte byte2) {
        this.rid[2] = byte2;
    }

    public void setByte1(byte byte1) {
        this.rid[1] = byte1;
    }

    public void setByte0(byte byte0) {
        this.rid[0] = byte0;
    }

    private boolean validIpAddress(String rid) {
        // used for checking if the entered IP address is in the correct
        // format

        // Regex pattern that will accept 0.0.0.0 to 999.999.999.999
        String regex = "[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}";

        // This will check if the pattern is at least correct, but won't check
        // if the numbers within the pattern are below 255
        if (!rid.matches(regex)) {
            return false;
        }

        // This will check if the numbers within the rid are not too big
        // Will split the string at hte "." and will have 4 digits
        String[] arrRid = rid.split("[.]", 4);

        // Each digit within the array will be converted from a String to an in
        // and will be checked if it is not over 255
        for (String digit : arrRid) {
            int number = Integer.parseInt(digit);
            if (number > 0x255) {
                return false;
            }
        }

        // if all checks are complete, then the RID format is correct
        return true;
    }

    private byte[] ipStringToByteArray(String strRid) {

        byte[] rid = new byte[4];
        // Splits the ip address string at every ".", of a maximum of 4.
        String[] strBytes = strRid.split("[.]", 4);

        for (int i = 0; i < strBytes.length; i++) {
            rid[i] = (byte) Integer.parseInt(strBytes[i]);
        }

        return rid;
    }

    // Overrides the original equals method, which only compares memory locations matching, instead we also need to compare,
    // if each instance is the same, while still being in another memory location.
    public boolean equals(Object object) {
        // Checks if both references (this and object) point to the same memory location, if so they are the same.
        if(this == object) return true;
        // Checks if the object is null or if the objects are of the same class, if so, then they are not equal, so
        // return false.
        if(object == null || this.getClass() != object.getClass()) return false;
        // Cast the object to the SubnetMask object class.
        RID ipAddress = (RID) object;
        // Check if all the subnet mask values match, if not then return false.
        if(this.rid[0] != ipAddress.getRid()[0]) return false;
        if(this.rid[1] != ipAddress.getRid()[1]) return false;
        if(this.rid[2] != ipAddress.getRid()[2]) return false;
        if(this.rid[3] != ipAddress.getRid()[3]) return false;
        // Once all conditions are met, then the objects equal.
        return true;
    }
}
