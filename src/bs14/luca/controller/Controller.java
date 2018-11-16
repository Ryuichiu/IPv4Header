package bs14.luca.controller;

import bs14.luca.ipheader.IPv4Header;

import java.util.regex.Pattern;

public class Controller {
    public static void main(String[] args) {
        var header = new IPv4Header("4-24-0-2-0-128-0-192.168.1.1-192.168.2.1");
        System.out.println(!Pattern.compile("[^01 ]").matcher("00001001111010101000110001110001101010001010101010000001111101").find());
    }
}
