package automat;

import java.util.*;
import java.io.*;
import java.util.Date; // tips fra stackoverflow 
import java.text.SimpleDateFormat;// tips fra stackoverflow 

/**
 * Model af en simpel billetautomat til enkeltbilletter med én fast pris.
 */
public class Billetautomat {

    private int billetpris;    // Prisen for én billet.
    private double balance; // Hvor mange penge kunden p.t. har puttet i automaten
    private int antalBilletterSolgt; // Antal billetter automaten i alt har solgt
    private boolean montørtilstand;
    private ArrayList<String> transaktioner = new ArrayList<>();
    private ArrayList<BilletTyper> billeter = new ArrayList<>();

    public Billetautomat() {
        balance = 0;
        antalBilletterSolgt = 0;
        try {
            BufferedReader myFile = new BufferedReader(new FileReader("BilletTypper.txt"));
            String s;
            String[] arraySTR = {""};
            while ((s = myFile.readLine()) != null) {
                arraySTR = s.split("¤");
                String Navn = arraySTR[1];
                double thisPris = Double.parseDouble(arraySTR[3]);
                double zone = Double.parseDouble(arraySTR[5]);
                int myAntalS = Integer.parseInt(arraySTR[7]);
                int id = Integer.parseInt(arraySTR[9]);
                BilletTyper billet = new BilletTyper(Navn, thisPris, zone, id, "read");
                billet.addAntal(myAntalS);
                billeter.add(billet);
            }
            myFile.close();
        } catch (IOException | NumberFormatException f) {

            try {
                PrintWriter myFile = new PrintWriter("BilletTypper.txt", "UTF-8"); // create file and write to file (charset utf8)
                BilletTyper billet = new BilletTyper("Voksen Billet", 10, 0.9, 1);
                billeter.add(billet);
                String s = "Navn=¤" + billet.getString() + "¤Pris=¤" + billet.getDouble() + "¤ZoneR=¤" + billet.getZoneR() + "¤ID="+billet.getID();
                myFile.println(s);
                myFile.close();
                //  BilletListe.write(BilletTyper.getZoneR());
            } catch (Exception nf) {
                System.out.println("Something is wrong \n" + nf.getMessage());

            }
            System.out.println("Error call support" + f);
        }

        transaktioner.clear();

    }

    public void AddBillet() {
        if (montørtilstand) {
            double number = 0;
            double zoneRBT = 0;
            String Navn;
            System.out.println("Test navn til Billeten");
            java.util.Scanner tastatur = new java.util.Scanner(System.in, "windows-1252");
            Navn = tastatur.nextLine();

            for (int i = 0; i < 2; i++) {
                if (i == 1) {
                    System.out.println("Test Zone Rabbat");
                } else {
                    System.out.println("Test Pris");
                }
                try {
                    zoneRBT = tastatur.nextDouble();
                    number = number + zoneRBT;
                } catch (Exception e) {
                    System.out.println("du skal skrive et tal (husk ',' hvis det er ikke en heltal)");
                    System.out.println(e.getMessage());
                    zoneRBT = 0;
                    i--;
                }

                tastatur.nextLine();

            }
            number = number - zoneRBT;

            try {
                BilletTyper billet = null;
                billet = new BilletTyper(Navn, number, zoneRBT, billet.getLastID());
                billeter.add(billet);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * Giver prisen for en billet.
     */
    public void getBilletpris() {
        System.out.print("ID Type                ");
        for (int i = 2; i < 10; i++) {
            System.out.print("Zone " + i + "   ");
        }
        System.out.println("");
        for (BilletTyper b : billeter) {
            int l = (b.getString()).length();
            int PrintID= b.getID();
            System.out.print(PrintID);
            while (PrintID < 100) {
                System.out.print(" ");
                PrintID = PrintID*10;
            }
            System.out.print(b.getString());
            while (l <= 15) {
                System.out.print(" ");
                l++;
            }
            for (int i = 2; i < 10; i++) {
                System.out.print("     " + (roundUP(b.getDouble() * b.getZoneR() * i)));
            }
            System.out.println(" ");
        }
    }

    /**
     * Modtag nogle penge (i kroner) fra en kunde.
     */
    public void indsætPenge(int beløb) {
        balance = balance + beløb;
        String temp = ((new SimpleDateFormat("dd/MM-yyyy HH:mm:ss").format(new Date())) + " Brugeren har kastet ind " + beløb + " kr");

        logbog(temp);
    }

    /**
     * Giver balancen (beløbet maskinen har modtaget til den næste billet).
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Udskriv en billet. Opdater total og nedskriv balancen med billetprisen
     */
    public void udskrivBillet(int antalZoner, int thisID, long l) {
        BilletTyper tempB = null;
        String RKJN = "Nej";
        double nyPris = 0;
        boolean billetFindes = false;
        double Saldo = balance;
        String[] info = {""};

        for (BilletTyper b : billeter) {
            if (thisID == b.getID()) {
                tempB = b;
                nyPris = b.getDouble();
                billetFindes = true;
            }
        }
        if (!billetFindes) {
            return;
        }
        if (l > 1000000) {
            RKJN = "Ja";
            nyPris = nyPris * 0.85;
            nyPris = roundUP(nyPris);
            try {
                BufferedReader myFile = new BufferedReader(new FileReader("RejsekortListe.txt"));
                String tempStr = "";
                while ((tempStr = myFile.readLine()) != null) {
                    info = tempStr.split("¤");
                    String tempL = l + "";
                    if (tempL.equals(info[3])) {
                        if (balance > 0) {
                            returpenge();
                        }
                        Saldo = Double.parseDouble(info[9]);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (Saldo < (nyPris)) {
            System.out.println("Du mangler at indbetale nogle penge");
            return;
        }

        System.out.println("##########B##T#########");
        System.out.println("# BlueJ Trafikselskab #");
        System.out.println("#                     #");
        System.out.println("#        Billet       #");
        System.out.println("#                     #");
        System.out.println("#        Zone " + antalZoner + "       #");

        System.out.print("#  " + tempB.getString()+ "");
        space(nyPris);
        System.out.println("        #");

        System.out.println("#                     #");
        System.out.println("##########B##T#########");
        Saldo = Saldo - nyPris;
        if (l > 1000000) {

            try {
                FileOutputStream myFile = new FileOutputStream("RejsekortListe.txt");
                info[9] = Saldo + "";
                String temp = "";
                for (int i = 0; i <= 9; i++) {
                    temp = temp + info[i] + "¤";
                }
                myFile.write(temp.getBytes());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Error");
            }
        } else {
            balance = Saldo;
        }
        String S = (new SimpleDateFormat("dd/MM-yyyy HH:mm:ss").format(new Date())) + " Brugeren Har Købt";
        S = S + tempB.getString() + " Pris=" + nyPris + " RejseKort" + RKJN;
        logbog(S);
    }

    public void space(double tal) {
        if (tal < 10) {
            System.out.print(tal + " kr ");
        } else if (tal < 100) {
            System.out.print(tal + " kr");
        } else {
            System.out.print(+tal + "kr");
        }
    }

    public double returpenge() {
        double returbeløb = balance;
        balance = 0;
        String logS = (new SimpleDateFormat("dd/MM-yyyy HH:mm:ss").format(new Date())) + " Brugeren fik " + returbeløb + " kr tilbage";
        logbog(logS);
        System.out.println("Du får " + returbeløb + " kr retur");
        return returbeløb;
    }

    void montørLogin(String adgangskode) {
        if ("1234".equals(adgangskode)) {
            montørtilstand = true;
            System.out.println("Montørtilstand aktiveret");
            System.out.println("Du kan nu angive billetpris");
        } else {
            montørtilstand = false;
            System.out.println("Montørtilstand deaktiveret");
        }
    }

    public int getTotal() {
        if (montørtilstand) {
            return billetpris * antalBilletterSolgt;
        } else {
            System.out.println("Afvist - log ind først");
            return 0;
        }
    }

    public int getAntalBilletterSolgt() {
        if (montørtilstand) {
            return antalBilletterSolgt;
        } else {
            System.out.println("Afvist - log ind først");
            return 0;
        }
    }

    public void setBilletpris(int billetPris, String BilletType) {
        BilletTyper billet = null;
        for (BilletTyper b : billeter) {
            if ((b.getString()).equals(BilletType)) {
                billet = b;
                break;
            }
        }
        try {
            FileOutputStream myFile = new FileOutputStream("RejsekortListe.txt");
            String temp = "\nNavn=¤" + BilletType + "¤Pris=¤" + billetPris + "¤ZoneR=¤" + billet.getZoneR() + "¤" + "¤antalS=¤" + billet.getAntalS();;
            myFile.write(temp.getBytes());
            billet.changePris(billetPris);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void changeZoneR(int ZoneRbt, String BilletType) {
        BilletTyper billet = null;
        for (BilletTyper b : billeter) {
            if ((b.getString()).equals(BilletType)) {
                billet = b;
                break;
            }
        }
        try {
            FileOutputStream myFile = new FileOutputStream("RejsekortListe.txt");
            String temp = "\nNavn=¤" + BilletType + "¤Pris=¤" + billet.getDouble() + "¤ZoneR=¤" + ZoneRbt + "¤" + "¤antalS=¤" + billet.getAntalS();;
            myFile.write(temp.getBytes());
            billet.changeZoner(ZoneRbt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean erMontør() {
        return montørtilstand;
    }

    public void Udskriv() {
        for (String s : transaktioner) {
            System.out.println(s);
        }
    }

    private double roundUP(double d) {
        int temp = (int) d;
        double rest = d - temp;
        if (rest < 0.25) {
            rest = 0;
        } else if (rest < 0.75) {
            rest = 0.5;
        } else {
            rest = 1;
        }
        return (rest + temp);
    }

    private void logbog(String s) {
        try {
            Writer myFile = new BufferedWriter(new FileWriter("LogBog.txt", true));
            myFile.append(s + "\n");
            myFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
