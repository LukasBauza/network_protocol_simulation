public class SubnetMask {
    private byte[] subnetMask = new byte[4];

    public SubnetMask(String subnetMask) {
        // constructor used for checking that each segment is the right type and in the right range.
        //      if a wrong type is entered, it will throw out an error into main, which
        //      can be caught and used for error handling.
        if (validSubnetMask(subnetMask)) {
            this.subnetMask = subnetToByteArray(subnetMask);
        } else {
            throw new IllegalArgumentException("Each segment must be between 0 and 255.");
        }
    }

    public byte[] getSubnetMask() {
        return subnetMask;
    }

    public String toString() {
        // Needs to be converted to an unsigned int, to not have minus values. As bytes are represented in negatives.
        String byte0 = Integer.toString(Byte.toUnsignedInt(subnetMask[0]));
        String byte1 = Integer.toString(Byte.toUnsignedInt(subnetMask[1]));
        String byte2 = Integer.toString(Byte.toUnsignedInt(subnetMask[2]));
        String byte3 = Integer.toString(Byte.toUnsignedInt(subnetMask[3]));

        return byte0 + "." + byte1 + "." + byte2 + "." + byte3;
    }

    public void setSubnetMask(String subnetMask) {
        if (validSubnetMask(subnetMask)) {
            this.subnetMask = subnetToByteArray(subnetMask);
        } else {
            throw new IllegalArgumentException("Each segment must be between 0 and 255.");
        }
    }

    public void setByte3(byte byte3) {
        this.subnetMask[3] = byte3;
    }

    public void setByte2(byte byte2) {
        this.subnetMask[2] = byte2;
    }

    public void setByte1(byte byte1) {
        this.subnetMask[1] = byte1;
    }

    public void setByte0(byte byte0) {
        this.subnetMask[0] = byte0;
    }

    private boolean validSubnetMask(String subnetMask) {
        // used for checking if the entered IP address is in the correct
        // format

        // Regex pattern that will accept 0.0.0.0 to 999.999.999.999
        String regex = "[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}";

        // This will check if the pattern is at least correct, but won't check
        // if the numbers within the pattern are below 255
        if (!subnetMask.matches(regex)) {
            return false;
        }

        // This will check if the numbers within the ipAddress are not too big
        // Will split the string at hte "." and will have 4 digits
        String[] arrIPAddress = subnetMask.split("[.]", 4);

        // Each digit within the array will be converted from a String to an in
        // and will be checked if it is not over 255
        for (String digit : arrIPAddress) {
            int number = Integer.parseInt(digit);
            if (number > 0x255) {
                return false;
            }
        }

        // if all checks are complete, then the IP Address format is correct
        return true;
    }

    private byte[] subnetToByteArray(String strSubnetMask) {

        byte[] ipAddress = new byte[4];
        // Splits the ip address string at every ".", of a maximum of 4.
        String[] strBytes = strSubnetMask.split("[.]", 4);

        for (int i = 0; i < strBytes.length; i++) {
            ipAddress[i] = (byte) Integer.parseInt(strBytes[i]);
        }

        return ipAddress;
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
        SubnetMask subnetMask = (SubnetMask) object;
        // Check if all of the subnet mask values match, if not then return false.
        if(this.subnetMask[0] != subnetMask.getSubnetMask()[0]) return false;
        if(this.subnetMask[1] != subnetMask.getSubnetMask()[1]) return false;
        if(this.subnetMask[2] != subnetMask.getSubnetMask()[2]) return false;
        if(this.subnetMask[3] != subnetMask.getSubnetMask()[3]) return false;
        // Once all conditions are met, then the objects equal.
        return true;
    }
}
