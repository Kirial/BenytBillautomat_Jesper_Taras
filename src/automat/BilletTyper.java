/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automat;

import java.io.*;

/**
 *
 * @author Family
 */
public class BilletTyper {

    private String type;
    private double pris;
    private int antalS;
    private double ZoneR;

    BilletTyper(String s, double p, double z) {
        type = s;
        pris = p;
        antalS = 0;
        ZoneR = z;
        try {
            FileOutputStream myFile = new FileOutputStream("BilletTypper.txt");
            Writer BilletListe = new BufferedWriter(new OutputStreamWriter(myFile));
            String Str = "Navn=" + s + "¤Pris=" + p + "¤ZoneR=" + z + "¤\n";
            BilletListe.write(Str);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    BilletTyper(String s, double p, double z, String str) {
        type = s;
        pris = p;
        antalS = 0;
        ZoneR = z;
    }

    public String getString() {
        return type;
    }

    public double getDouble() {
        return pris;
    }

    public int getAntalS() {
        return antalS;
    }

    public double getZoneR() {
        return ZoneR;
    }

    public void addAntal(int i) {
        antalS = i;
    }
}
