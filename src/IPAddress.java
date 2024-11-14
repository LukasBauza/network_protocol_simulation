public class IPAddress {
    private String ipAddress;

    public IPAddress(String ipAddress) {
        // constructor used for checking that each segment is the right type and in the right range.
        //      if a wrong type is entered, it will throw out an error into main, which
        //      can be caught and used for error handling.
        if (validIpAddress(ipAddress)) {
            this.ipAddress = ipAddress;
        } else {
            throw new IllegalArgumentException("Each segment must be between 0 and 255.");
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    private boolean validIpAddress(String ipAddress) {
        // used for checking if the entered IP address is in the correct
        // format

        // Regex pattern that will accept 0.0.0.0 to 999.999.999.999
        String regex = "[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}";

        // This will check if the pattern is at least correct, but won't check
        // if the numbers within the pattern are below 255
        if (!ipAddress.matches(regex)) {
            return false;
        }

        // This will check if the numbers within the ipAddress are not too big
        // Will split the string at hte "." and will have 4 digits
        String[] arrIPAddress = ipAddress.split("[.]", 4);

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
}