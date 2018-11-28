package bs14.luca.ipheader;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This is a simple implementation a of a concatenated String as normal String or as Bits and a conversion from a normal String to a BinaryString
 *
 * @Author Luca Vollandt
 */
public class IPv4Header {

    /**
     * @param version The version number of the IPv4-packet
     *                4 bits
     */
    private int version; //user
    private String versionString; //user
    private int versionBinLength = 4;

    /**
     * @param ihl (5 x 32 bits -> calc)
     *            IP Header Length (IHL)
     *            The length of the ip header data length (a multiple of 32)
     *            4 bits
     */
    private int ihl; //calc
    private String ihlString; //calc
    private int ihlBinLength = 4;

    /**
     * @param tos Type Of Service (TOS)
     *            Can be set or read to prioritize IP data packages (QOS)
     *            8 bits
     */
    private int tos = 24; //manual
    private String tosString = "24"; //manual
    private int tosBinLength = 8;

    /**
     * @param packetLength (total length = header + data ---> 576 <= 160 + x Bits <= 65535 ---> data length = 514 to 65375 Bits available for data ---> calc)
     *                     Total length of the packet incl. header (576 - 65535 Bytes)
     *                     16 bits
     */
    private int packetLength; //calc
    private String packetLengthString; //calc
    private int packetBinLength = 16;

    /**
     *  @param identification This and the following two param Flags and Fragment Offset are responsible the reassembly.
     *                        Unique identification of a datagram.
     *                        This with Source IP field will make the user able to determine related fragments and reassemble with the Fragment Offset
     *                        16 bits
     */
    private int identification = 0; //manual
    private String identificationString = "0"; //manual
    private int identificationBinLength = 16;

    /**
     * @param flags 3 bits with following meanings:
     *              0 - 0 (reserved)
     *              1 - DF (don't fragment) - if 1 no fragmentation
     *              2 - MF (more fragments) - if 1 more fragments will follow, 0 is the last (or only) fragment
     */
    private int flags = 1; //manual
    private String flagsString = "0"; //manual
    private int flagsBinLength = 3;

    /**
     *  @param fragmentOffset 13 bits
     *                        A number that indicates the position within the packet where the fragment starts for fragmented packets.
     *                        The numbering refers to data blocks of 64 bit or 8 byte size and is independent of the fragmentation.
     *                        A packet can therefore be split into smaller and smaller fragments several times in a row if necessary.
     *                        Only the number of the first contained data block (offset) and the total length field have to be adapted to the length of the fragment.
     *                        The first fragment, or an unfragmented packet, contains the value zero as offset.
     *                        If a packet with 800 bytes of user data (offset numbering from 0 to 99) is divided into two fragments, the offset of the second fragment is number 50.
     *                        Since the offset contains no indication how large the original packet is, the very last fragment must set the MF flag to zero.
     */
    private int fragmentOffset = 0; //manual
    private String fragmentOffsetString = "0"; //manual
    private int fragsBinLength = 13;

    /**
     *  @param ttl Time To Live (TTL)
     *             A value which represents how long a package can life. Each station (router) will decrease this number by 1 or the seconds the package remained in the station.
     *             8 bits
     */
    private int ttl; //user
    private String ttlString; //user
    private int ttlBinLength = 8;

    /**
     *  @param protocol (= 0 because payload is not relevant for the purpose of this application)
     *                  Calls the type of protocol used for the userdata in the IPv4Header (6 = TCP, 17 = UDP)
     *                  8 bits
     */
    private int protocol = 0; //manual
    private String protocolString = "0"; //manual
    private int protocolBinLength = 8;

    /**
     *  @param checksum (needs to be calculated with someones own algorithm -> https://en.wikipedia.org/wiki/IPv4_header_checksum)
     *                  A sum that checks only the header.
     *                  16 bits
     */
    private int checksum; //calc
    private String checksumString; //calc
    private int checksumBinLength = 16;

    /**
     *  @param sourceIP Contains the source address of the IP packet
     *                  32 bits
     */
    private int[] sourceIP; //user
    private String sourceIpString; //user
    private int sipBinArrayLength = 32;

    /**
     *  @param destinationIP Contains the target address of the IP packet
     *                       32 bits
     */
    private int[] destinationIP; //user
    private String destinationIpString; //user
    private int dipBinArrayLength = 32;

    /**
     * Splits the input and sets the fields initially
     *
     * @param s user input
     */
    public IPv4Header (String s) {
        var isBin = !Pattern.compile("[^01.-]").matcher(s).find();
        var ss = s.split("-");
        var si = new int[ss.length-2];
        if (!isBin) {
            for (int i = 0; i < si.length; i++) si[i] = Integer.parseInt(ss[i]);
        }

        version = si[0];
        versionString = ss[0];

        ihl = calcIhl();
        ihlString = isBin?ss[1]:Integer.toString(ihl);

        tos = si[1];
        tosString = isBin?ss[2]:ss[1];

        packetLength = isBin?0:getPacketLength();
        packetLengthString = isBin?ss[3]:Integer.toString(packetLength);

        identification = si[2];
        identificationString = isBin?ss[4]:ss[2];

        flags = si[3];
        flagsString = isBin?ss[5]:ss[3];

        fragmentOffset = si[4];
        fragmentOffsetString = isBin?ss[6]:ss[4];

        ttl = si[5];
        ttlString = isBin?ss[7]:ss[5];

        protocol = si[6];
        protocolString = isBin?ss[8]:ss[6];

        sourceIpString = isBin?ss[10]:ss[7];
        var sipStringArray = sourceIpString.split("\\.");
        sourceIP = new int[sipStringArray.length];
        for (int i = 0; i < sourceIP.length; i++) sourceIP[i] = Integer.parseInt(sipStringArray[i]);

        destinationIpString = isBin?ss[11]:ss[8];
        var dipStringArray = destinationIpString.split("\\.");
        destinationIP = new int[dipStringArray.length];
        for (int i = 0; i < destinationIP.length; i++) destinationIP[i] = Integer.parseInt(dipStringArray[i]);

        checksum = calcChecksum();
        checksumString = isBin?ss[9]:Integer.toString(checksum);
    }

    /**
     * implementation of a modified and simplified version of the Fletcher Checksum algorithm as Fletcher-16
     * calc the checksum of the ip header
     *
     * @return sum1+sum2
     */
    public int calcChecksum() {
        var sum1 = 0;
        var sum2 = 0;

        sum1 = (sum1 + version) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + ihl) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + tos) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + packetLength) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + identification) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + flags) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + fragmentOffset) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + ttl) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + protocol) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + checksum) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + sourceIP[0]) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + sourceIP[1]) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + sourceIP[2]) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + sourceIP[3]) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + destinationIP[0]) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + destinationIP[1]) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + destinationIP[2]) % 255;
        sum2 = (sum1+sum2) % 255;
        sum1 = (sum1 + destinationIP[3]) % 255;
        sum2 = (sum1+sum2) % 255;

        return sum1+sum2;
    }

    /**
     * getter for the checksum
     *
     * @return checksum
     */
    public int getChecksum() {
        return checksum;
    }

    /**
     * Asks for additional data and its length
     *
     * @return packetLength
     */
    private int getPacketLength() {
        var sc = new Scanner(System.in);
        System.out.println("Do you want to add data(true/false)?");
        var add = false;//sc.nextBoolean();
        if (add) {
            System.out.println("How big(576 - 65535)?");
            return calcIhl()*32+sc.nextInt()+576;
        }

        return calcIhl()*32+576;
    }

    /**
     * gets the length of all fields and returns a multiple of 32
     *
     * @return ihl
     */
    private int calcIhl() {
        return (versionBinLength+ihlBinLength+tosBinLength+packetBinLength
                +identificationBinLength+flagsBinLength+fragsBinLength+ttlBinLength+
                protocolBinLength+checksumBinLength+sipBinArrayLength+dipBinArrayLength)/32;
    }

    /**
     * a plain concatenated string of all decimal values
     *
     * @return output
     */
    public String getOutput() {
        return String.join("-", versionString,ihlString,tosString,packetLengthString,
                identificationString,flagsString,fragmentOffsetString,ttlString,
                protocolString,checksumString,sourceIpString,destinationIpString);
    }

    /**
     * a converted concatenated string of all binary values
     *
     * @return output as a binary
     */
    public String getOutputBin() {
        var versionBin = Integer.toBinaryString(version);
        var ihlBin = Integer.toBinaryString(ihl);
        var tosBin = Integer.toBinaryString(tos);
        var packetLengthBin = Integer.toBinaryString(packetLength);
        var identificationBin = Integer.toBinaryString(identification);
        var flagsBin = Integer.toBinaryString(flags);
        var fragsBin = Integer.toBinaryString(fragmentOffset);
        var ttlBin = Integer.toBinaryString(ttl);
        var protocolBin = Integer.toBinaryString(protocol);
        var checksumBin = Integer.toBinaryString(checksum);
        var sipBinArray = new String[sourceIP.length];
        for (int i = 0; i < sipBinArray.length; i++) sipBinArray[i] = Integer.toBinaryString(sourceIP[i]);
        var dipBinArray = new String[destinationIP.length];
        for (int i = 0; i < dipBinArray.length; i++) dipBinArray[i] = Integer.toBinaryString(destinationIP[i]);

        versionBin = addLeadingZeros(versionBin, 4);
        ihlBin = addLeadingZeros(ihlBin, 4);
        tosBin = addLeadingZeros(tosBin, 8);
        packetLengthBin = addLeadingZeros(packetLengthBin, 16);
        identificationBin = addLeadingZeros(identificationBin, 16);
        flagsBin = addLeadingZeros(flagsBin, 3);
        fragsBin = addLeadingZeros(fragsBin, 13);
        ttlBin = addLeadingZeros(ttlBin, 8);
        protocolBin = addLeadingZeros(protocolBin, 8);
        checksumBin = addLeadingZeros(checksumBin, 16);
        for (int i = 0; i < sipBinArray.length; i++) sipBinArray[i] = addLeadingZeros(sipBinArray[i], 8);
        for (int i = 0; i < dipBinArray.length; i++) dipBinArray[i] = addLeadingZeros(dipBinArray[i], 8);

        var sourceIpBin = String.join(".", sipBinArray[0], sipBinArray[1], sipBinArray[2], sipBinArray[3]);
        var destinationIpBin = String.join(".", dipBinArray[0], dipBinArray[1], dipBinArray[2], dipBinArray[3]);

        return String.join("-", versionBin,ihlBin,tosBin,packetLengthBin,identificationBin,flagsBin,fragsBin,ttlBin,protocolBin,checksumBin,sourceIpBin,destinationIpBin);
    }

    /**
     * adds leading zeros to the input binary string till its length exceeds the input length
     *
     * @param bin binary string
     * @param length total length of the output
     * @return formatted binary string
     */
    private String addLeadingZeros(String bin, int length) {
        var sb = new StringBuilder(bin);
        for (int i = 0; i < length-bin.length();i++) sb.insert(0,0);
        return sb.toString();
    }

    /**
     * converts the fields in the form of a binary string to a concatenated decimal string
     *
     * @return output as a decimal
     */
    public String getOutputBinToDec() {
        var sb = new StringBuilder();
        var res = 0;
        var sipStringArray = sourceIpString.split("\\.");
        var dipStringArray = destinationIpString.split("\\.");

        String[] split = {versionString, ihlString, tosString, packetLengthString,
                identificationString, flagsString, fragmentOffsetString, ttlString,
                protocolString, checksumString, sipStringArray[0], sipStringArray[1],
                sipStringArray[2], sipStringArray[3], dipStringArray[0], dipStringArray[1],
                dipStringArray[2], dipStringArray[3]};
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            for (int j = s.length() - 1; j >= 0; j--) {
                var bit = Integer.parseInt(s.substring(j,j+1));
                res += bit * Math.pow(2, s.length()-1-j);
            }
            sb.append(res);
            res = 0;
            if (i >= 10 && i < 17) {
                if (i == 13) sb.append("-"); else sb.append(".");
            } else if (i < split.length-1) {
                sb.append("-");
            }
        }

        return sb.toString();
    }
}
