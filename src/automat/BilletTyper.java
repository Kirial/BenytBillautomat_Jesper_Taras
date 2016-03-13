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
    private int ID;
    private String type;
    private double pris;
    private int antalS;
    private double ZoneR;
    private static int lastID;
    BilletTyper(String s, double p, double z,int i) {
        lastID = ID+1;
        ID = i;
        type = s;
        pris = p;
        antalS = 0;
        ZoneR = z;
        try {
            Writer myFile = new BufferedWriter(new FileWriter("BilletTypper.txt", true));
            String Str = "\nNavn=¤" + s + "¤Pris=¤" + p + "¤ZoneR=¤" + z + "¤"+"¤antalS=¤"+antalS+"¤ID=¤"+ID;
            myFile.append(Str);
            myFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    BilletTyper(String s, double p, double z,int i, String str) {
        lastID = ID+1;
        ID = i;
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
    public int getID(){
     return ID;
    }
    public int getLastID(){
        return lastID;
    }

    public void addAntal(int i) {
        antalS = i;
    }
    public void changeZoner(double d){
        ZoneR = d;
    }
    
    public void changePris(double d){
        pris = d; 
    }
}
