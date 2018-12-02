package bs14.luca.controller;

import bs14.luca.ipheader.IPv4Header;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Controller {

    /**
     * Creates 2 new IPv4Header objects with a concatenated binary input and a concatenated decimal input and prints the
     * plain output, the concatenated output converted to a binary string and the concatenated binary input converted
     * to a plain string
     *
     * @param args
     */

    public static void main(String[] args) {
        var input = getInput();
        var ip = new IPv4Header(input);
        System.out.println(ip.getOutput());
        var inputBin = ip.getOutputBin();
        System.out.println(inputBin);
        var ipBin = new IPv4Header(inputBin);
        System.out.println(ipBin.getOutputBinToDec());
    }

    /**
     * Asks the user to enter input into the console to collect values for the IPv4Header
     *
     * @return concatenated string with the "-" delimiter
     */
    private static String getInput() {
        var sc = new Scanner(System.in);

        System.out.println("Please enter the version: ");
        var version = sc.nextLine();
        while (!"4".equals(version)) {
            System.out.print("This version accepts only IPv4\n>> ");
            version = sc.nextLine();
        }
        System.out.println("Please enter the time to live (ttl = 1 - 256): ");
        var ttl = getTtl();
        System.out.println("Please enter source IP: ");
        var sourceIP = getIP();
        System.out.println("Please enter destination IP: ");
        var destinationIP = getIP();

        return String.join("-", version, "24", "0", "1", "0", ttl, "0", sourceIP, destinationIP);
    }

    /**
     * Asks the user to enter an IP address and repeats so util a valid IP was entered
     *
     * @return the IP
     */
    private static String getIP() {
        var sc = new Scanner(System.in);
        String s = sc.nextLine();
        var dots = s.length()-s.replace(".","").length();
        while(Pattern.compile("[^0-9.]").matcher(s).find() || dots != 3 || (s.length() < 7 && s.length() > 35)) {
            System.out.println("Please enter IP:");
            s = sc.nextLine();
        }
        return s;
    }

    /**
     * Asks the user to enter a valid number between 1 and 256 and returns every time at least 1 or maximum 256
     *
     * @return Time To Live
     */
    private static String getTtl() {
        var sc = new Scanner(System.in);
        int ttl = 0;
        try {
            ttl = sc.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Please enter a valid number:");
            getTtl();
        }

        var s = Integer.toString(Math.max(1, Math.min(ttl, 256)));
        return s;
    }
}
