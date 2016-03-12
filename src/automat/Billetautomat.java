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
    private int balance; // Hvor mange penge kunden p.t. har puttet i automaten
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
            while ((s = myFile.readLine()) != null) {
                String Navn = s.substring(s.indexOf('=') + 1, s.indexOf('¤'));
                s = s.replace(s.substring(1, (s.indexOf("¤") + 1)), " ");
                double thisPris = Double.parseDouble(s.substring(s.indexOf('=') + 1, s.indexOf('¤')));
                s = s.replace(s.substring(1, (s.indexOf("¤") + 1)), " ");
                double zone = Double.parseDouble(s.substring(s.indexOf('=') + 1, s.indexOf('¤')));
                BilletTyper billet = new BilletTyper(Navn, thisPris, zone, "read");
                billeter.add(billet);
            }
            myFile.close();
        } catch (IOException | NumberFormatException f) {

            try {
                PrintWriter myFile = new PrintWriter("BilletTypper.txt", "UTF-8"); // create file and write to file (charset utf8)
                BilletTyper billet = new BilletTyper("Voksen Billet", 10, 0.9);
                billeter.add(billet);
                String s = "Navn=" + billet.getString() + "¤Pris=" + billet.getDouble() + "¤ZoneR=" + billet.getZoneR() + "¤";
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
                BilletTyper billet = new BilletTyper(Navn, number, zoneRBT);
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
        for (BilletTyper b : billeter) {
            System.out.println(b.getString() + " " + b.getDouble());
        }
    }

    /**
     * Modtag nogle penge (i kroner) fra en kunde.
     */
    public void indsætPenge(int beløb) {
        balance = balance + beløb;
        transaktioner.add((new SimpleDateFormat("dd/MM-yyyy HH:mm:ss").format(new Date())) + " Brugeren har kastet ind " + beløb + " kr");
    }

    /**
     * Giver balancen (beløbet maskinen har modtaget til den næste billet).
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Udskriv en billet. Opdater total og nedskriv balancen med billetprisen
     */
    public void udskrivBillet() {
        transaktioner.add((new SimpleDateFormat("dd/MM-yyyy HH:mm:ss").format(new Date())) + " Brugeren har udskrevet billet");
        if (balance < billetpris) {
            System.out.println("Du mangler at indbetale nogle penge");
            return;
        }
        System.out.println("##########B##T#########");
        System.out.println("# BlueJ Trafikselskab #");
        System.out.println("#                     #");
        System.out.println("#        Billet       #");

        System.out.print("#        ");
        space(billetpris);
        System.out.println("        #");

        System.out.println("#                     #");
        System.out.println("##########B##T#########");

        System.out.print("#Du har ");
        space(balance - billetpris);
        System.out.println(" til gode#");

        System.out.println("##########B##T#########");
        System.out.println();

        antalBilletterSolgt = antalBilletterSolgt + 1;
        balance = balance - billetpris; // Billetter koster 10 kroner
    }

    public void space(int tal) {
        if (tal < 10) {
            System.out.print(tal + " kr ");
        } else if (tal < 100) {
            System.out.print(tal + " kr");
        } else {
            System.out.print(+tal + "kr");
        }
    }

    public int returpenge() {
        int returbeløb = balance;
        balance = 0;
        transaktioner.add((new SimpleDateFormat("dd/MM-yyyy HH:mm:ss").format(new Date())) + " Brugeren fik " + returbeløb + " kr tilbage");
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

    public void setBilletpris(int billetpris) {
        this.billetpris = billetpris;
    }

    public void nulstil() {
        if (montørtilstand) {
            antalBilletterSolgt = 0;
        } else {
            System.out.println("Afvist - log ind først");
        }
    }

    public void setAntalBilletterSolgt(int antalBilletterSolgt) {
        if (montørtilstand) {
            this.antalBilletterSolgt = antalBilletterSolgt;
        } else {
            System.out.println("Afvist - log ind først");
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

}
