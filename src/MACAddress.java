// TODO: this class can be combined with IPAddress for code reusablity

public class MACAddress {
    private String macAddress;

    public MACAddress(String macAddress) {
        // constructor used for checking that each segment is the right type and in the right range.
        //      if a wrong type is entered, it will throw out an error into main, which
        //      can be caught and used for error handling.
        if (validMACAddress(macAddress)) {
            this.macAddress = macAddress;
        } else {
            throw new IllegalArgumentException("Each segment must be between 0 and FF.");
        }
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMACAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    private boolean validMACAddress(String macAddress) {
        // used for checking if the entered MAC address is in the correct
        // format

        // Regex pattern that will accept 0:0:0:0:0:0 to FF:FF:FF:FF:FF:FF
        String regex = "[0-9a-fA-F]{1,2}[:][0-9a-fA-F]{1,2}[:][0-9a-f-A-F]{1,2}[:][0-9a-fA-F]{1,2}[:][0-9a-fA-F]{1,2}[:][0-9a-fA-F]{1,2}";

        // This will check if the pattern is at least correct, but won't check
        // if the numbers within the pattern are below 255
        if (!macAddress.matches(regex)) {
            return false;
        }

        // This will check if the numbers within the macAddress are not too big
        // Will split the string at hte "." and will have 6 digits
        String[] arrMACAddress = macAddress.split("[:]", 6);

        // Each digit within the array will be converted from a String to an in
        // and will be checked if it is not over FF in hex value.
        for (String digit : arrMACAddress) {
            int number = Integer.parseInt(digit, 16);   // converts text to hex
            if (number > 0xFF) {
                return false;
            }
        }

        // if all checks are complete, then the MAC Address format is correct
        return true;
    }
}
