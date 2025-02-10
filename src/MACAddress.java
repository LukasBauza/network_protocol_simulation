// TODO: Need to add subnet for PC.

import java.util.Random;

public class MACAddress {
    private final byte[] macAddress;

    public MACAddress() {
        this.macAddress = generateMacAddress();
    }

    public byte[] getMacAddress() {
        return macAddress;
    }

    public String toString() {
        // Converts all the bytes into a String, appends to a ":" for separation.

        // Makes sure that the format of the string is in HEX.
        String byte0 = String.format("%02X", this.macAddress[0]);
        String byte1 = String.format("%02X", this.macAddress[1]);
        String byte2 = String.format("%02X", this.macAddress[2]);
        String byte3 = String.format("%02X", this.macAddress[3]);
        String byte4 = String.format("%02X", this.macAddress[4]);
        String byte5 = String.format("%02X", this.macAddress[5]);

        return byte0 + ":" + byte1 + ":" + byte2 + ":" + byte3 + ":" + byte4 + ":" + byte5;
    }

    private byte[] generateMacAddress() {
        Random random = new Random();
        byte[] macAddress = new byte[6];

        // Iterates through the macAddress array, and generates a random integer between 0 and 255, and casts it to a byte.
        for (int i = 0; i < macAddress.length; i++) {
            macAddress[i] = (byte) random.nextInt(0, 255);
        }

        return macAddress;
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
        MACAddress macAddress = (MACAddress) object;
        // Check if all the subnet mask values match, if not then return false.
        if(this.macAddress[0] != macAddress.getMacAddress()[0]) return false;
        if(this.macAddress[1] != macAddress.getMacAddress()[1]) return false;
        if(this.macAddress[2] != macAddress.getMacAddress()[2]) return false;
        if(this.macAddress[3] != macAddress.getMacAddress()[3]) return false;
        // Once all conditions are met, then the objects equal.
        return true;
    }
}
