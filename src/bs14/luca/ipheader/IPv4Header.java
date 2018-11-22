package bs14.luca.ipheader;

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
    private int version; //user
    private String versionString; //user

    /**
     * @param ihl (5 x 32 bits -> calc)
     *            IP Header Length (IHL)
     *            The length of the ip header data length (a multiple of 32)
     *            4 bits
     */
    private int ihl; //calc
    private String ihlString; //calc

    /**
     * @param tos Type Of Service (TOS)
     *            Can be set or read to prioritize IP data packages (QOS)
     *            8 bits
     */
    private int tos = 24; //manual
    private String tosString = "24"; //manual

    /**
     * @param packetLength (total length = header + data ---> 576 <= 160 + x Bits <= 65535 ---> data length = 514 to 65375 Bits available for data ---> calc)
     *                     Total length of the packet incl. header (576 - 65535 Bytes)
     *                     16 bits
     */
    private int packetLength; //calc
    private String packetLengthString; //calc

    /**
     *  @param identification This and the following two param Flags and Fragment Offset are responsible the reassembly.
     *                        Unique identification of a datagram.
     *                        This with Source IP field will make the user able to determine related fragments and reassemble with the Fragment Offset
     *                        16 bits
     */
    private int identification = 0; //manual
    private String identificationString = "0"; //manual

    /**
     * @param flags 3 bits with following meanings:
     *              0 - 0 (reserved)
     *              1 - DF (don't fragment) - if 1 no fragmentation
     *              2 - MF (more fragments) - if 1 more fragments will follow, 0 is the last (or only) fragment
     */
    private int flags = 1; //manual
    private String flagsString = "0"; //manual

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

    /**
     *  @param ttl Time To Live (TTL)
     *             A value which represents how long a package can life. Each station (router) will decrease this number by 1 or the seconds the package remained in the station.
     *             8 bits
     */
    private int ttl; //user
    private String ttlString; //user

    /**
     *  @param protocol (= 0 because payload is not relevant for the purpose of this application)
     *                  Calls the type of protocol used for the userdata in the IPv4Header (6 = TCP, 17 = UDP)
     *                  8 bits
     */
    private int protocol = 0; //manual
    private String protocolString = "0"; //manual

    /**
     *  @param checksum (needs to be calculated with someones own algorithm -> https://en.wikipedia.org/wiki/IPv4_header_checksum)
     *                  A sum that checks only the header.
     *                  16 bits
     */
    private int checksum; //calc
    private String checksumString; //calc

    /**
     *  @param sourceIP Contains the source adress of the IP packet
     *                  32 bits
     */
    private int[] sourceIP; //user
    private String sourceIpString; //user

    /**
     *  @param targetIP Contains the target adress of the IP packet
     *                  32 bits
     */
    private int[] destinationIP; //user
    private String destinationIpString; //user

    public IPv4Header (String s) {
        var ss = s.split("-");
        var si = new int[ss.length-2];
        for (int i = 0; i < si.length; i++) si[i] = Integer.parseInt(ss[i]);

        version = si[0];
        versionString = ss[0];

        ihl = calcIhl();
        ihlString = Integer.toString(ihl);

        tos = si[1];
        tosString = ss[1];

        packetLength = getPacketLength();
        packetLengthString = Integer.toString(packetLength);

        identification = si[2];
        identificationString = ss[2];

        flags = si[3];
        flagsString = ss[3];

        fragmentOffset = si[4];
        fragmentOffsetString = ss[4];

        ttl = si[5];
        ttlString = ss[5];

        protocol = si[6];
        protocolString = ss[6];

        checksum = getChecksum();
        checksumString = Integer.toString(checksum);

        sourceIpString = ss[7];
        var sipStringArray = sourceIpString.split("\\.");
        sourceIP = new int[sipStringArray.length];
        for (int i = 0; i < sourceIP.length; i++) sourceIP[i] = Integer.parseInt(sipStringArray[i]);

        destinationIpString = ss[8];
        var dipStringArray = destinationIpString.split("\\.");
        destinationIP = new int[dipStringArray.length];
        for (int i = 0; i < destinationIP.length; i++) destinationIP[i] = Integer.parseInt(dipStringArray[i]);
    }

    public int getChecksum() {
        return 0;
    }

    private int getPacketLength() {
        var sc = new Scanner(System.in);
        System.out.println("Do you want to add data(true/false)?");
        var add = sc.nextBoolean();
        if (add) {
            System.out.println("How big(576 - 65535)?");
            return calcIhl()*32+sc.nextInt()+576;
        }

        return calcIhl()*32+576;
    }

    private int calcIhl() {
        return (4+4+8+16+16+3+13+8+8+16+32+32)/32;
    }

    public String getOutput() {
        return String.join("-", versionString,ihlString,tosString,packetLengthString,
                identificationString,flagsString,fragmentOffsetString,ttlString,
                protocolString,checksumString,sourceIpString,destinationIpString);
    }

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

        addLeadingZeros(versionBin, 4);//=for (int i = 0; i < 4-versionBin.length(); i++) versionBin = "0" + versionBin;
        for (int i = 0; i < 4-ihlBin.length(); i++) ihlBin = "0" + ihlBin;
        for (int i = 0; i < 8-tosBin.length(); i++) tosBin = "0" + tosBin;
        for (int i = 0; i < 16-packetLengthBin.length(); i++) packetLengthBin = "0" + packetLengthBin;
        for (int i = 0; i < 16-identificationBin.length(); i++) identificationBin = "0" + identificationBin;
        for (int i = 0; i < 3-flagsBin.length(); i++) flagsBin = "0" + flagsBin;
        for (int i = 0; i < 13-fragsBin.length(); i++) fragsBin = "0" + fragsBin;
        for (int i = 0; i < 8-ttlBin.length(); i++) ttlBin = "0" + ttlBin;
        for (int i = 0; i < 8-protocolBin.length(); i++) protocolBin = "0" + protocolBin;
        for (int i = 0; i < 16-checksumBin.length(); i++) checksumBin = "0" + checksumBin;
        for (int j = 0; j < sipBinArray.length; j++) {
            for (int i = 0; i < 32-sipBinArray[j].length(); i++) sipBinArray[j] = "0" + sipBinArray[j];
        }
        for (int j = 0; j < dipBinArray.length; j++) {
            for (int i = 0; i < 32-dipBinArray[j].length(); i++) dipBinArray[j] = "0" + dipBinArray[j];
        }

        var sourceIpBin = String.join(".", sipBinArray[0], sipBinArray[1], sipBinArray[2], sipBinArray[3]);
        var destinationIpBin = String.join(".", dipBinArray[0], dipBinArray[1], dipBinArray[2], dipBinArray[3]);

        return String.join("-", versionBin,ihlBin,tosBin,packetLengthBin,identificationBin,flagsBin,fragsBin,ttlBin,protocolBin,checksumBin,sourceIpBin,destinationIpBin);
    }

    private void addLeadingZeros(String bin, int length) {
        for (int i = 0; i < length-bin.length();i++) bin = "0"+bin;
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
