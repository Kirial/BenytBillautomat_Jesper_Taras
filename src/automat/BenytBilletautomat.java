package automat;

public class BenytBilletautomat {

    public static void main(String[] arg) {
        Billetautomat automat = new Billetautomat();
        java.util.Scanner tastatur = new java.util.Scanner(System.in, "windows-1252");
        System.out.println("BenytBilletautomat version 3");
        System.out.println();
        long kortnr = 0;
        int valg = 0;
        while (true) {
            System.out.println("-----------------------------------------------");
            System.out.println("Se listen neden under");
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
                System.out.println("Tast 16 for at tilføre Billet Type");
                automat.Udskriv();
            }

            valg = GetNumber(tastatur);
            if (valg >= 1000000) {
                kortnr = valg;
                valg = 7;
            }
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
                case 4:
                    automat.getBilletpris();
                    break;
                case 5:
                    System.out.println("indtest dit navn");
                    String RKnavn = tastatur.nextLine();
                    System.out.println("indtest password");
                    boolean RKOK = true;
                    int myPass = 0;
                    while(RKOK){
                        myPass =GetNumber(tastatur);
                        if(myPass > 0){
                            RKOK = false;
                        }
                    }
                    RejseKort newRK = new RejseKort(RKnavn, myPass);
                    newRK.getRejsekortnr();
                    break;
                case 7:
                    RejsekortMenue(kortnr, tastatur);
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
                case 16:
                    if (automat.erMontør()) {
                        automat.AddBillet();
                    }
                    break;
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

    public static void RejsekortMenue(long d, java.util.Scanner tastatur) {
        try {
            RejseKort RK = new RejseKort(d);
            boolean user = true;
            int myValg = 0;
            int Pass;
            System.out.println("Password");
            Pass = GetNumber(tastatur);
            if (!(RK.confPass(Pass))) {
                user = false;
            }
            while (user) {
                RK.printManual();
                myValg = GetNumber(tastatur);
                switch (myValg) {
                    case 1:
                        System.out.println("din Saldo er på " + RK.getSaldo() + " kr");

                        break;
                    case 2:
                        System.out.println("Skriv beløb(min 50 kr)");
                        int thisBeløb = GetNumber(tastatur);
                        double nyBeløb = (RK.getSaldo()) + thisBeløb;
                        if (nyBeløb > 50 || nyBeløb % 50 == 0) {
                            RK.Money(nyBeløb);
                        } else {
                            System.out.println("Error");
                        }
                        break;
                    case 3:
                        System.out.println("Skriv en ny Password");
                        int gPass = GetNumber(tastatur);
                        System.out.println("Skriv en ny Password");
                        int nyPass = GetNumber(tastatur);
                        RK.changePass(gPass, nyPass);
                        break;
                    case 4:
                        System.out.println("Du er logget UD");
                        user = false;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Din rejse Kort findes ikke i databassen");
        }
    }
}
