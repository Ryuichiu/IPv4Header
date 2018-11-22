package bs14.luca.controller;

import bs14.luca.ipheader.IPv4Header;

import java.util.Scanner;

public class Controller {

    /**
     * version 0
     * ttl 7
     * qip 10
     * dip 11
     *
     * tos 2 24
     * id 4 0
     * flags 5 0
     * fragoff 6 0
     * proc 8 0
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
        //4-5-24-1440-0-1-0-128-0-0-1.1.1.1-2.2.2.2
    }

    private static String getInput() {
        var sc = new Scanner(System.in);

        System.out.println("Please enter the version: ");
        var version = sc.nextLine();
        System.out.println("Please enter the time to live (ttl): ");
        var ttl = sc.nextLine();
        System.out.println("Please enter source IP: ");
        var sourceIP = sc.nextLine();
        System.out.println("Please enter destination IP: ");
        var destinationIP = sc.nextLine();

        return String.join("-", version, "24", "0", "1", "0", ttl, "0", sourceIP, destinationIP);
    }
}
