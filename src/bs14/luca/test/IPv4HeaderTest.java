package bs14.luca.test;

import bs14.luca.ipheader.IPv4Header;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IPv4HeaderTest {

    //Testet die Ausgabe der eingegebenen dezimalen Werte (Notwendig für Note 4)
    @Test
    void testGetOutputCase1()
    {
        IPv4Header ip = new IPv4Header("4-24-0-2-0-128-0-192.168.1.1-192.168.2.1");
        assertEquals("4-5-24-160-0-2-0-128-0-243-192.168.1.1-192.168.2.1", ip.getOutput());

    }
    @Test
    void testGetOutputCase2()
    {
        IPv4Header ip = new IPv4Header("4-24-0-1-0-120-0-1.1.1.1-2.2.2.2");
        assertEquals("4-5-24-160-0-1-0-120-0-311-1.1.1.1-2.2.2.2", ip.getOutput());

    }
    @Test
    void testGetOutputCase3()
    {
        IPv4Header ip = new IPv4Header("4-24-0-0-0-30-0-192.168.1.1-8.8.8.8");
        assertEquals("4-5-24-160-0-0-0-30-0-316-192.168.1.1-8.8.8.8", ip.getOutput());
    }
    @Test
    void testGetOutputCase4()
    {
        IPv4Header ip = new IPv4Header("4-24-0-2-0-128-0-0.0.0.0-192.168.2.1");
        assertEquals("4-5-24-160-0-2-0-128-0-218-0.0.0.0-192.168.2.1", ip.getOutput());

    }

    //Testet die Ausgabe der binären Form der eingegebenen dezimalen Werte (Notwendig für Note 3)
    @Test
    void testGetOutputBinCase1()
    {
        IPv4Header ip = new IPv4Header("4-24-0-2-0-128-0-192.168.1.1-192.168.2.1");
        assertEquals("0100-0101-00011000-0000000010100000-0000000000000000-010-0000000000000-10000000-00000000-0000000011110011-11000000.10101000.00000001.00000001-11000000.10101000.00000010.00000001", ip.getOutputBin());
    }

    @Test
    void testGetOutputBinCase2()
    {
        IPv4Header ip = new IPv4Header("4-24-0-1-0-120-0-1.1.1.1-2.2.2.2");
        assertEquals("0100-0101-00011000-0000000010100000-0000000000000000-001-0000000000000-01111000-00000000-0000000100110111-00000001.00000001.00000001.00000001-00000010.00000010.00000010.00000010", ip.getOutputBin());
    }

    @Test
    void testGetOutputBinCase3()
    {
        IPv4Header ip = new IPv4Header("4-24-0-0-0-30-0-192.168.1.1-8.8.8.8");
        assertEquals("0100-0101-00011000-0000000010100000-0000000000000000-000-0000000000000-00011110-00000000-0000000100111100-11000000.10101000.00000001.00000001-00001000.00001000.00001000.00001000", ip.getOutputBin());
    }

    @Test
    void testGetOutputBinCase4()
    {
        IPv4Header ip = new IPv4Header("4-24-0-2-0-128-0-0.0.0.0-192.168.2.1");
        assertEquals("0100-0101-00011000-0000000010100000-0000000000000000-010-0000000000000-10000000-00000000-0000000011011010-00000000.00000000.00000000.00000000-11000000.10101000.00000010.00000001", ip.getOutputBin());
    }

    //Testet die Ausgabe der eingegebenen binären Werte in der dezimalen Darstellung (Notwendig für Note 2)
    @Test
    void testGetOutputBinToDecCase1()
    {
        IPv4Header ip = new IPv4Header("0100-0101-00011000-0000010110100000-0000000000000000-010-0000000000000-10000000-00000000-0000000000000000-11000000.10101000.00000001.00000001-11000000.10101000.00000010.00000001");
        assertEquals("4-5-24-1440-0-2-0-128-0-0-192.168.1.1-192.168.2.1", ip.getOutputBinToDec());
    }

    @Test
    void testGetOutputBinToDecCase2()
    {
        IPv4Header ip = new IPv4Header("0100-0101-00011000-0000010110100000-0000000000000000-001-0000000000000-01111000-00000000-0000000000000000-00000001.00000001.00000001.00000001-00000010.00000010.00000010.00000010");
        assertEquals("4-5-24-1440-0-1-0-120-0-0-1.1.1.1-2.2.2.2", ip.getOutputBinToDec());

    }

    @Test
    void testGetOutputBinToDecCase3()
    {
        IPv4Header ip = new IPv4Header("0100-0101-00011000-0000010110100000-0000000000000000-000-0000000000000-00011110-00000000-0000000000000000-11000000.10101000.00000001.00000001-00001000.00001000.00001000.00001000");
        assertEquals("4-5-24-1440-0-0-0-30-0-0-192.168.1.1-8.8.8.8", ip.getOutputBinToDec());

    }

    @Test
    void testGetOutputBinToDecCase4()
    {
        IPv4Header ip = new IPv4Header("0100-0101-00011000-0000010110100000-0000000000000000-010-0000000000000-10000000-00000000-0000000000000000-00000000.00000000.00000000.00000000-11000000.10101000.00000010.00000001");
        assertEquals("4-5-24-1440-0-2-0-128-0-0-0.0.0.0-192.168.2.1", ip.getOutputBinToDec());
    }

//Ergänzen Sie hier die notwendigen Test zur Überprüfung Ihrer Header-Checksum (Notwendig für Note 1)

    //Testet die Ausgabe der berechneten Checksum in der dezimalen Form
    @Test
    void testGetChecksumCase1() {
        IPv4Header ip = new IPv4Header("4-24-0-2-0-128-0-192.168.1.1-192.168.2.1");
        assertEquals(243, ip.getChecksum());
    }

    @Test
    void testGetChecksumCase2() {
        IPv4Header ip = new IPv4Header("4-24-0-1-0-120-0-1.1.1.1-2.2.2.2");
        assertEquals(311, ip.getChecksum());
    }

    @Test
    void testGetChecksumCase3() {
        IPv4Header ip = new IPv4Header("4-24-0-0-0-30-0-192.168.1.1-8.8.8.8");
        assertEquals(316, ip.getChecksum());
    }

    @Test
    void testGetChecksumCase4() {
        IPv4Header ip = new IPv4Header("4-24-0-2-0-128-0-0.0.0.0-192.168.2.1");
        assertEquals(218, ip.getChecksum());
    }
}