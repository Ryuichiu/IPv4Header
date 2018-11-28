package bs14.luca.controller;

import bs14.luca.ipheader.IPv4Header;

import java.util.Scanner;

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
        System.out.println(ip.getOutputBin());
        var inputBin = ip.getOutputBin();
        var ipBin = new IPv4Header(inputBin);
        System.out.println(ipBin.getOutputBinToDec());
        //4-24-0-1-0-128-0-1.1.1.1-2.2.2.2"
        //4-5-24-736-0-1-0-128-0-188-1.1.1.1-2.2.2.2
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
        while (!"4".equals(version)) version = sc.nextLine();
        System.out.println("Please enter the time to live (ttl): ");
        var ttl = sc.nextLine();
        System.out.println("Please enter source IP: ");
        var sourceIP = sc.nextLine();
        System.out.println("Please enter destination IP: ");
        var destinationIP = sc.nextLine();

        return String.join("-", version, "24", "0", "1", "0", ttl, "0", sourceIP, destinationIP);
    }
}
