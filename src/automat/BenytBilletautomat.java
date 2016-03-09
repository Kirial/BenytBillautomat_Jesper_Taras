package automat;

public class BenytBilletautomat {

    public static void main(String[] arg) {
        Billetautomat automat = new Billetautomat();
        java.util.Scanner tastatur = new java.util.Scanner(System.in);
        
        System.out.println("BenytBilletautomat version 3");
        System.out.println();
        int valg = 0;
        while (true) {
            System.out.println("-----------------------------------------------");
            System.out.println("En billet koster " + automat.getBilletpris() + " kroner");
            System.out.println("Balancen er på " + automat.getBalance() + " kroner");
            System.out.println();
            System.out.println("Tast 1 for at indbetale penge");
            System.out.println("Tast 2 for at udskrive din billet");
            System.out.println("Tast 3 for at få returpengene");
            System.out.println();
            System.out.println("Tast 10 for at logge ind som montør");
            if (automat.erMontør()) {
                System.out.println("Tast 11 for at se status (montør)");
                System.out.println("Tast 12 for at nulstille (montør)");
                System.out.println("Tast 13 for at sætte billetpris (montør)");
                System.out.println("Tast 14 for at logge ud af montørtilstand");
                System.out.println("Tast 15 for at visse transaktioner");
                automat.Udskriv();
            }

            valg = GetNumber(tastatur);

            switch (valg) {
                case 0:
                    break;
                case 1:
                    System.out.print("Skriv beløb: ");
                    int beløb = GetNumber(tastatur);
                    automat.indsætPenge(beløb);
                    break;
                case 2:
                    automat.udskrivBillet();
                    break;
                case 3:
                    int beløbRetur = automat.returpenge();
                    System.out.println("Du fik " + beløbRetur + " retur retur");
                    break;
                case 10:
                    System.out.print("Skriv kode: ");
                    String kode = tastatur.next();
                    automat.montørLogin(kode);
                    break;
                case 11:
                    if (automat.erMontør()) {
                        System.out.println("Antal billetter solgt: " + automat.getAntalBilletterSolgt());
                        System.out.println("Total indkomst: " + automat.getTotal() + " kr");
                    } else {
                        System.out.println("Afvist - log ind først");
                    }
                    break;

                case 12:
                    automat.nulstil();
                    break;
                case 13:
                    if (automat.erMontør()) {
                        System.out.print("Skriv beløb: ");
                        int nyPris = GetNumber(tastatur);
                        automat.setBilletpris(nyPris);
                    } else {
                        System.out.println("Afvist - log ind først");
                    }
                    break;
                case 14:
                    if (automat.erMontør()) {
                        automat.montørLogin("");
                        break;
                    }
                default:
                    System.out.println("Ugyldigt valg, prøv igen");
                    break;

            }
        }
    }
    
      public static int GetNumber(java.util.Scanner tastatur) {
      int number = 0;
        try {
            number = tastatur.nextInt();
        } catch (Exception e) {
            System.out.println("du skal skrive et tal");
            System.out.println(e.getMessage());
            number = 0;
        }
        tastatur.nextLine();
        return number;
    }
}
