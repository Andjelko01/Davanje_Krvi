import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Osoba
{
    protected String ime;
    protected String prezime;
    protected String jmbg;
    protected String adresa;
    protected String telefon;
    protected String imeRoditelja;
    protected String pol;

    public Osoba() {
    }

    public Osoba(String ime, String prezime, String jmbg, String adresa, String telefon, String imeRoditelja, String pol) {
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.adresa = adresa;
        this.telefon = telefon;
        this.imeRoditelja = imeRoditelja;
        this.pol = pol;

    }

    public static LocalDate getDatumRodjenja(String jmbg)
    {
        String datumString=jmbg.substring(0,2)+"."+jmbg.substring(2,4)+"."+((jmbg.charAt(5) =='9')?"2"+jmbg.substring(4,7):"1"+jmbg.substring(4,7));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(datumString, formatter);
    }

    //Metode za proveru unosa podataka za objekte
    public static Boolean ProveraStringa(String unos) {
       if (unos.isEmpty()){
            System.out.println("Nije moguce uneti prazan tekst");
            return true;
        }
        else {
            return false;
        }
    }
    public static Boolean PrekidUnosa(String unos)
    {
        if (unos.toLowerCase().trim().equals("stop")){
            return true;
        }
        else{
            return false;
        }
    }

    public static String[] DodajOsobu(){
        Scanner scanner= new Scanner(System.in);
        String[] podaci=new String[7];
        String ime,prezime,jmbg,adresa,telefon,imeRoditelja,pol;
        do {
            System.out.println("Unesite ime");
            podaci[0]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[0])){
                return null;
            }
        }while(ProveraStringa(podaci[0]));

        do {
            System.out.println("Unesite prezime");
            podaci[1]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[1])){
                return null;
            }
        }while(ProveraStringa(podaci[1]));

        do {
            System.out.println("Unesite jmbg");
            podaci[2]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[2])){
                return null;
            }
        }while(ProveraStringa(podaci[2]));
        do {
            System.out.println("Unesite adresu");
            podaci[3]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[3])){
                return null;
            }
        }while(ProveraStringa(podaci[3]));

        do {
            System.out.println("Unesite telefon");
            podaci[4]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[4])){
                return null;
            }
        }while(ProveraStringa(podaci[4]));

        do {
            System.out.println("Unesite ime roditelja");
            podaci[5]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[5])){
                return null;
            }
        }while(ProveraStringa(podaci[5]));

        do {
            System.out.println("Unesite pol (Musko/Zensko)");
            podaci[6]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[6])){
                return null;
            }
        }while(ProveraStringa(podaci[6]));
        return podaci;
    }
    //Kraj metoda za unos podataka za objekat
}