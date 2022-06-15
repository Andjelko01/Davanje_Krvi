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

    public static LocalDate getDatumRodjenja(String jmbg) {
        if (!jmbg.equals("0000000000000"))
        {
            try
            {
                String datumString = jmbg.substring(0, 2) + "." + jmbg.substring(2, 4) + "." + ((jmbg.charAt(4) == '9') ? "1" + jmbg.substring(4, 7) : "2" + jmbg.substring(4, 7));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                return LocalDate.parse(datumString, formatter);
            } catch (Exception e)
            {
               // System.out.println("Niste uneli validan JMBG");
                return LocalDate.now();
            }
        }
        else
        {
            return LocalDate.now();
        }
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
        do
        {
            System.out.print("Unesite ime: ");
            podaci[0]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[0])){
                return null;
            }
        }while(ProveraStringa(podaci[0]));

        do
        {
            System.out.print("Unesite prezime: ");
            podaci[1]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[1])){
                return null;
            }
        }while(ProveraStringa(podaci[1]));

        do
        {
            System.out.print("Unesite jmbg: ");
            podaci[2]=ProveraJMBG(scanner.nextLine());
            scanner.reset();
            if (PrekidUnosa(podaci[2])){
                return null;
            }
        }while(ProveraStringa(podaci[2]));

        do
        {
            System.out.print("Unesite adresu: ");
            podaci[3]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[3])){
                return null;
            }
        }while(ProveraStringa(podaci[3]));

        do
        {
            System.out.print("Unesite telefon: ");
            podaci[4]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[4])){
                return null;
            }
        }while(ProveraStringa(podaci[4]));

        do
        {
            System.out.print("Unesite ime roditelja: ");
            podaci[5]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[5])){
                return null;
            }
        }while(ProveraStringa(podaci[5]));

        do
        {
            System.out.print("Unesite pol (Musko/Zensko): ");
            podaci[6]=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(podaci[6])){
                return null;
            }
        }while(ProveraStringa(podaci[6]));
        return podaci;
    }
    //Kraj metoda za unos podataka za objekat

    //Pocetak metoda za izmenu podataka za objekat
    public void izmeniPodatke(String vrednost)
    {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Usli ste u sistem da promenite "+vrednost+" unesite vrednost: ");
        String promena= scanner.nextLine();
        switch (vrednost.toLowerCase()){
            case "ime":
                ime=promena;
                break;
            case "prezime":
                prezime=promena;
                break;
            case "jmbg":
                jmbg=promena;
                break;
            case "adresa":
                adresa=promena;
                break;
            case "telefon":
                telefon=promena;
                break;
            case "imeroditelja":
                imeRoditelja=promena;
                break;
            case "pol":
                pol=promena;
                break;
            default:
                System.out.println("Uneli ste pogresan paramtar");
                break;
        }
    }
    //Kraj metoda za izmenu podataka za objekat

    public static String ProveraJMBG(String jmbg)
    {
        if (jmbg.length()==13)
        {
            switch(Integer.parseInt(jmbg.substring(2,4)))
            {
                case 1,3,5,7,8,10,12:
                    if (Integer.parseInt(jmbg.substring(0,2))<=31)
                    {
                        return jmbg;
                    }
                    else
                    {
                       return "0000000000000";
                    }
                case 4,6,9,11:
                    if (Integer.parseInt(jmbg.substring(0,2))<=30)
                    {
                        return jmbg;
                    }
                    else
                    {
                        return "0000000000000";
                    }
                case 2:
                    if (Integer.parseInt(jmbg.substring(0,2))<=29)
                    {
                        return jmbg;
                    }
                    else
                    {
                        return "0000000000000";
                    }
                default:
                    return "0000000000000";
            }

        }
        return "0";
    }
}