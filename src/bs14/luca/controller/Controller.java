package bs14.luca.controller;

import bs14.luca.ipheader.IPv4Header;

import java.util.regex.Pattern;

public class Controller {
    public static void main(String[] args) {
      //  var header = new IPv4Header("4-24-0-2-0-128-0-192.168.1.1-192.168.2.1");
        System.out.println(!"010101010100111100100000101000000100010101000019999111001".contains("[01]"));
        System.out.println(!Pattern.compile("[^01 ]").matcher("000010011110101010001100011100011010100010910101010000001111101").find());
    }
}
