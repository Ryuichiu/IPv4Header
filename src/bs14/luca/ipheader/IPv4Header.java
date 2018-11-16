package bs14.luca.ipheader;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * This is a simple implementation a of a concortinated String as normal String or as Bits and a convertion from a anormal String to a BitsString
 *
 * @Author Luca Vollandt
 */
public class IPv4Header {

    /**
     * @param version The verion number of the IPv4-packet
     *                4 bits
     */
    private int version;

    /**
     * @param ihl (5 x 32 bits -> calc)
     *            IP Header Length (IHL)
     *            The length of the ip header data length (a multiple of 32)
     *            4 bits
     */
    private int ihl;

    /**
     * @param tos Type Of Service (TOS)
     *            Can be set or read to prioritize IP data packages (QOS)
     *            8 bits
     */
    private int tos = 24;

    /**
     * @param packetLength (total length = header + data ---> 576 <= 160 + x Bits <= 655353 ---> data length = 514 to 655193 Bits available for data ---> calc)
     *                     Total length of the packet incl. header (576 - 655353 Bytes)
     *                     16 bits
     */
    private int packetLength;

    /**
     *  @param identification This and the following two param Flags and Fragment Offset are responsible the reassembly.
     *                        Unique identification of a datagram.
     *                        This with Source IP field will make the user able to determine related fragments and reassemble with the Fragment Offset
     *                        16 bits
     */
    private int identification = 0;

    /**
     * @param flags 3 bits with following meanings:
     *              0 - 0 (reserved)
     *              1 - DF (don't fragment) - if 1 no fragmentation
     *              2 - MF (more fragments) - if 1 more fragments will follow, 0 is the last (or only) fragment
     */
    private int flags = 3;

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
    private int fragmentOffset = 0;

    /**
     *  @param ttl Time To Live (TTL)
     *             A value which represents how long a package can life. Each station (router) will decrease this number by 1 or the seconds the package remained in the station.
     *             8 bits
     */
    private int ttl;

    /**
     *  @param protocol (= 0 because payload is not relevant for the purpose of this application)
     *                  Calls the type of protocol used for the userdata in the IPv4Header (6 = TCP, 17 = UDP)
     *                  8 bits
     */
    private int protocol = 0;

    /**
     *  @param checksum (needs to be calculated with someones own algorithm -> https://en.wikipedia.org/wiki/IPv4_header_checksum)
     *                  A sum that checks only the header.
     *                  16 bits
     */
    private int checksum;

    /**
     *  @param sourceIP Contains the source adress of the IP packet
     *                  32 bits
     */
    private int sourceIP;

    /**
     *  @param targetIP Contains the target adress of the IP packet
     *                  32 bits
     */
    private int targetIP;

    public IPv4Header(int version, int ttl, int sourceIP, int sinkIP) {
        this.version = version;
        this.ttl = ttl;
        this.sourceIP = sourceIP;
        this.targetIP = sinkIP;
    }

    public IPv4Header(String concortination) {
        this.version = Integer.parseInt(concortination.split(!(concortination.contains("^[01 ]"))?"[ ]":"[-]")[0]);
        this.ttl = Integer.parseInt(concortination.split(!(concortination.contains("^[01 ]"))?"[ ]":"[-]")[7]);
        this.sourceIP = Integer.parseInt(concortination.split(!(concortination.contains("^[01 ]"))?"[ ]":"[-]")[10]);
        this.targetIP = Integer.parseInt(concortination.split(!(concortination.contains("^[01 ]"))?"[ ]":"[-]")[11]);
    }

    public IPv4Header() {
        var input = new Scanner(System.in);
        System.out.println("Please enter version number: ");
        this.version = input.nextInt();
        System.out.println("Please enter the time to life (TTL): ");
        this.ttl = input.nextInt();
        System.out.println("Please enter the source IP: ");
        this.sourceIP = input.nextInt();
        System.out.println("Please enter the target IP: ");
        this.targetIP = input.nextInt();
    }

    public String getOutput() {
        var versionString =         Integer.toString(version);
        var ihlString =             Integer.toString(ihl);
        var tosString =             Integer.toString(tos);
        var packetLengthString =    Integer.toString(packetLength);
        var identificationString =  Integer.toString(identification);
        var flagsString =           Integer.toString(flags);
        var ttlString =             Integer.toString(ttl);
        var fragmentOffsetString =  Integer.toString(fragmentOffset);
        var protocolString =        Integer.toString(protocol);
        var checksumString =        Integer.toString(checksum);
        var sourceIpString =        Integer.toString(sourceIP);
        var targetIpString =          Integer.toString(targetIP);

        var formater = new DecimalFormat("000");
        formater.applyPattern(flagsString);

        return String.join("-",
                versionString,ihlString,tosString,packetLengthString,identificationString,flagsString,
                fragmentOffsetString,ttlString,protocolString,checksumString,sourceIpString,targetIpString);
    }

    public String getOutputBin() {
        var versionString =         Integer.toBinaryString(version);
        var ihlString =             Integer.toBinaryString(ihl);
        var tosString =             Integer.toBinaryString(tos);
        var packetLengthString =    Integer.toBinaryString(packetLength);
        var identificationString =  Integer.toBinaryString(identification);
        var flagsString =           Integer.toBinaryString(flags);
        var ttlString =             Integer.toBinaryString(ttl);
        var fragmentOffsetString =  Integer.toBinaryString(fragmentOffset);
        var protocolString =        Integer.toBinaryString(protocol);
        var checksumString =        Integer.toBinaryString(checksum);
        var sourceIpString =        Integer.toBinaryString(sourceIP);
        var targetIpString =          Integer.toBinaryString(targetIP);

        var formatter = new DecimalFormat("000");
        formatter.format(flagsString);

        return String.join(" ", versionString, ihlString, tosString, packetLengthString, identificationString, flagsString,
                ttlString, fragmentOffsetString, protocolString, checksumString, sourceIpString, targetIpString);
    }

    public String getOutputBinToDec() {
        var input = getOutputBin();
        var sb = new StringBuilder();
        var res = 0;

        String[] split = input.split("[ ]");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            for (int j = s.length() - 1; j >= 0; j--) {
                res += Math.pow(2, j);
            }
            sb.append(res);
            res = 0;
            if (i < split.length-1) sb.append("-");
        }

        return sb.toString();
    }
}
