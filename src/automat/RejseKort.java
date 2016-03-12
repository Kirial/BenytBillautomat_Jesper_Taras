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
public class RejseKort {

    private int ID = 0;
    private String navn;
    private int password;
    private double konto;
    private long RejsekortNummer = 0;

    RejseKort(long RK) {
        boolean found = false;
        try {
            BufferedReader myFile = new BufferedReader(new FileReader("RejseKortListe.txt"));
            String str;
            String[] arrayLine = {""};
            String temp = RK + "";

            while ((str = myFile.readLine()) != null) {
                arrayLine = str.split("¤");

                if (arrayLine[3].equals(temp)) {
                    found = true;
                    break;
                }
            }
            myFile.close();

            if (!found) {
                System.out.println("test");
                throw new IllegalArgumentException();
            } else {
                try{
                ID = Integer.parseInt(arrayLine[1]);
                RejsekortNummer = RK;
                navn = arrayLine[5];
                password = Integer.parseInt(arrayLine[7]);
                konto =Double.parseDouble(arrayLine[9]) ;
                }catch(Exception e){
                    System.out.println("Kontakt venlist Support error 300");
                    throw new IllegalArgumentException(); 
                }
                
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException();
        }

    }

    RejseKort(String s, int p, int k) {
        try {
            BufferedReader myFile = new BufferedReader(new FileReader("RejseKortListe.txt"));
            String str;
            String line = "";
            while ((str = myFile.readLine()) != null) {
                line = str;
            }
            myFile.close();

            String[] arrayLine = line.split("¤");
            ID = (Integer.parseInt(arrayLine[1])) + 1;
            RejsekortNummer = (Integer.parseInt(arrayLine[3])) + 1;
        } catch (Exception e) {
            System.out.println("Første Rejsekort");
            ID = 1;
            RejsekortNummer = 1000000;
        }

        navn = s;
        password = p;
        konto = 0;
        String indsæt = "ID=¤" + ID + "¤Rejsekortnummer=¤" + RejsekortNummer + "¤Navn=¤" + navn + "¤Password=¤" + password + "¤Saldo=¤" + konto;
        try {
            Writer myFile = new BufferedWriter(new FileWriter("RejsekortListe.txt", true));
            myFile.append(indsæt);
            myFile.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
