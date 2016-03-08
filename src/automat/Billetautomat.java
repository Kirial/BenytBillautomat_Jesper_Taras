package automat;
import java.util.*;
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
        private ArrayList<String> transaktioner = new ArrayList<String>();

	/**
	 * Opret en billetautomat der sælger billetter til 10 kr.
	 */
	public Billetautomat() {
		billetpris = 10;
		balance = 0;
		antalBilletterSolgt = 0;
                transaktioner.clear();
	}

	/**
	 * Giver prisen for en billet. 
	 */
	public int getBilletpris() {
		int resultat = billetpris;                
                return resultat;
	}

	/**
	 * Modtag nogle penge (i kroner) fra en kunde.
	 */
	public void indsætPenge(int beløb) {
        balance = balance + beløb;
        transaktioner.add((new SimpleDateFormat("dd/MM-yyyy HH:mm:ss").format(new Date()))+" Brugeren har kastet ind "+beløb+" kr");       
	}

	/**
	 * Giver balancen (beløbet maskinen har modtaget til den næste billet).
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * Udskriv en billet.
	 * Opdater total og nedskriv balancen med billetprisen
	 */
	public void udskrivBillet() {
            transaktioner.add((new SimpleDateFormat("dd/MM-yyyy HH:mm:ss").format(new Date()))+" Brugeren har udskrevet billet");
		if (balance<billetpris) {
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
                space(balance-billetpris);
                System.out.println(" til gode#");
                
		System.out.println("##########B##T#########");
		System.out.println();

		antalBilletterSolgt = antalBilletterSolgt + 1;
		balance = balance - billetpris; // Billetter koster 10 kroner
	}
        public void space(int tal){
            if(tal < 10){
                System.out.print(tal+" kr ");
            }
            else if(tal <100){
                System.out.print(tal + " kr");
            }
            else{
                System.out.print(+ tal + "kr");
            }
        }

	public int returpenge() {
		int returbeløb = balance;
		balance = 0;
                transaktioner.add((new SimpleDateFormat("dd/MM-yyyy HH:mm:ss").format(new Date()))+" Brugeren fik "+returbeløb+" kr tilbage");
		System.out.println("Du får "+returbeløb+" kr retur");
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
        public void Udskriv(){
            for(String s : transaktioner){
                System.out.println(s);
            }
        }
        
        
    public int GetNumber(java.util.Scanner tastatur) {
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