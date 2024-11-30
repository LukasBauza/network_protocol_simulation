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
        // Converts all of the bytes into a String, appends to a ":" for separation.

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
}
