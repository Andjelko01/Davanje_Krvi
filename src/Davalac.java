import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Davalac extends Osoba implements Uredjivanje{
    private int id_davaoca;
    private String krvnaGrupa;
    private int brojDavanja;
    private LocalDate poslednjeDavanje;
    private boolean aktivan;


    public int getId_davaoca() {
        return id_davaoca;
    }

    public void setId_davaoca(int id_davaoca) {
        this.id_davaoca = id_davaoca;
    }

    public String getKrvnaGrupa() {
        return krvnaGrupa;
    }

    public void setKrvnaGrupa(String krvnaGrupa) {
        this.krvnaGrupa = krvnaGrupa;
    }

    public int getBrojDavanja() {
        return brojDavanja;
    }

    public void setBrojDavanja(int brojDavanja) {
        this.brojDavanja = brojDavanja;
    }

    public LocalDate getPoslednjeDavanje() {
        return poslednjeDavanje;
    }

    public void setPoslednjeDavanje(LocalDate poslednjeDavanje) {
        this.poslednjeDavanje = poslednjeDavanje;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    public Davalac(String ime, String prezime, String jmbg, String adresa, String telefon, String imeRoditelja, String pol, int id_davaoca, String krvnaGrupa, int brojDavanja, LocalDate poslednjeDavanje, boolean aktivan)
    {
        super(ime, prezime, jmbg, adresa, telefon, imeRoditelja, pol);
        this.id_davaoca = id_davaoca;
        this.krvnaGrupa = krvnaGrupa;
        this.brojDavanja = brojDavanja;
        this.poslednjeDavanje = poslednjeDavanje;
        this.aktivan = aktivan;
    }

    public static ArrayList<Davalac> UcitajJSON(String path_name)
    {
        ArrayList<Davalac> davalacs = new ArrayList<>();
        try
        {
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader(path_name));

            for (Object o:jsonArray
            )
            {
                JSONObject jsonObject = (JSONObject) o;
                String ime = jsonObject.get("ime").toString();
                String prezime = jsonObject.get("prezime").toString();
                String jmbg = jsonObject.get("jmbg").toString();
                String adresa = jsonObject.get("adresa").toString();
                String telefon = jsonObject.get("telefon").toString();
                String imeRoditelja = jsonObject.get("ime_roditelja").toString();
                String pol = jsonObject.get("pol").toString();
                int id = Integer.parseInt(jsonObject.get("id_davalac").toString());
                String krvna_grupa = jsonObject.get("krvna_grupa").toString();
                int broj_davanja = Integer.parseInt(jsonObject.get("broj_davanja").toString());
                String datumPosDav = jsonObject.get("poslednje_davanje").toString();
                LocalDate poslednje_davanje = LocalDate.parse(datumPosDav);
                boolean aktivnost = (boolean) jsonObject.get("aktivan");

                Davalac davalac = new Davalac(ime,prezime,jmbg,adresa,telefon,imeRoditelja,pol,id,krvna_grupa,broj_davanja,poslednje_davanje,aktivnost);
                davalacs.add(davalac);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
//            System.err.println("Greska pri ucitavanju Davalaca.");
        }

        return davalacs;
    }

    public static void UpisiUJSON(ArrayList<Davalac> davaoci, String path_name)
    {
        PrintWriter pw = null;
        try
        {
            JSONArray jsonArray = new JSONArray();
            pw = new PrintWriter(path_name);

            for (Davalac d:davaoci
            )
            {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id_davalac",d.id_davaoca);
                jsonObject.put("ime",d.ime);
                jsonObject.put("prezime",d.prezime);
                jsonObject.put("jmbg",d.jmbg);
                jsonObject.put("adresa",d.adresa);
                jsonObject.put("telefon",d.telefon);
                jsonObject.put("ime_roditelja",d.imeRoditelja);
                jsonObject.put("pol",d.pol);
                jsonObject.put("krvna_grupa",d.krvnaGrupa);
                jsonObject.put("broj_davanja",d.brojDavanja);
                jsonObject.put("poslednje_davanje",d.poslednjeDavanje.toString());
                jsonObject.put("aktivan",d.aktivan);

                jsonArray.add(jsonObject);
            }

            pw.write(jsonArray.toJSONString());

        }
        catch (Exception e)
        {
            System.err.println("Greska pri upisivanju Davalaca.");
        }
        finally
        {
            if(pw!=null)
                pw.close();
        }
    }

    private static int getNextId()
    {
        int id = 0;
        ArrayList<Davalac> davalacs = UcitajJSON("davaoci.json");
        for (Davalac d : davalacs)
            if(d.id_davaoca > id)
                id = d.id_davaoca;

        id++;

        return id;
    }

    public static void MenuDavaoc()
    {
        Scanner scanner = new Scanner(System.in);
        String meni = "1. Dodaj davaoca \t2. Izmeni davaoca \t0. Nazad";
        int input;
        do
        {
            System.out.println(meni);
            input = scanner.nextInt();

            switch (input)
            {
                case 1:
                    DodajDavaoca();
                    break;
                case 2:
                    ArrayList<Davalac> davaoci=UcitajJSON("davaoci.json");
                    for (Davalac d:davaoci)
                    {
                        System.out.println(d);
                    }
                    System.out.println("Unesite redni broj davaoca koji zelite da izmenite:");
                    int rb=0;
                    try
                    {
                        rb=scanner.nextInt();
                    }
                    catch (Exception e)
                    {
                        System.out.println("Niste uneli validan broj davaoca");
                    }
                    for (Davalac d:davaoci)
                    {
                        if (d.id_davaoca==rb)
                        {
                            d.Izmeni();
                        }
                    }
                    UpisiUJSON(davaoci,"davaoci.json");

                    break;
                case 3:

                    break;
                default:
                    break;
            }
        }
        while (input != 0);
    }

    public static Davalac DodajDavaoca()
    {

        String krvnaGrupa,
                brojDavanja,
                datumDavanja;
        LocalDate poslednjeDavanje;
        boolean aktivan;
        boolean postojiDavalac;
        int godine=0;
        ArrayList<Davalac> sviDavaoci = Davalac.UcitajJSON("davaoci.json");
        String[] podaci=null;
        do
        {
            Scanner scanner= new Scanner(System.in);
            System.out.println("Dodavanje davaoca je u toku, u svakom trenutku mozete da uneste stop kako bi prekinuli unos");
            podaci=DodajOsobu();
            if (podaci==null)
            {
                return null;
            }
            do {
                System.out.print("Unesite krvnu grupu: ");
                krvnaGrupa=scanner.nextLine();
                scanner.reset();
                if (PrekidUnosa(krvnaGrupa)){
                    return null;
                }
            }while(ProveraStringa(krvnaGrupa));
            do {
                System.out.print("Unesite broj davanja: ");
                brojDavanja=scanner.nextLine();
                scanner.reset();
                if (PrekidUnosa(brojDavanja)){
                    return null;
                }
            }while(ProveraStringa(brojDavanja));

             godine= getDatumRodjenja(podaci[2]).getYear()-LocalDate.now().getYear();
            postojiDavalac=false;
            for (Davalac t:sviDavaoci)
            {
                if (podaci[2].equals(t.jmbg))
                {
                    postojiDavalac=true;
                    System.out.println("Vec postoji davalac sa tim JMBG molim vas unesite ponovo davaoca");
                }
            }

        }while(postojiDavalac);

        Davalac davaoc= new Davalac(podaci[0],podaci[1],podaci[2],podaci[3],podaci[4],podaci[5],podaci[6],getNextId(),krvnaGrupa,Integer.parseInt(brojDavanja),LocalDate.now(),(godine>=18 || godine<65)?true:false);
        sviDavaoci.add(davaoc);
        Davalac.UpisiUJSON(sviDavaoci,"davaoci.json");
        System.out.println("Uspesno ste dodali davaoca");
        return davaoc;
    }

    @Override
    public void Izmeni()
    {
        System.out.println("Unesite vrednost koju zelite da izmenite bas kako je ispisano u meni-ju");
        System.out.println("1.ime 2.prezime 3.jmbg 4.adresa 5. telefon 6.imeRoditelja 7.pol 8.krvnaGrupa 9.brojDavanja");
        Scanner scanner= new Scanner(System.in);
        String promena=scanner.nextLine();
        switch(promena.toLowerCase())
        {
            case "krvnagrupa":
                System.out.println("Unesite novu krvu grupu");
                setKrvnaGrupa(scanner.nextLine());
                break;
            case "brojdavanja":
                System.out.println("Unesite novi broj davanja");
                setBrojDavanja(scanner.nextInt());
                break;
            default:
                this.izmeniPodatke(promena);
                break;
        }

    }

    public void AzurirajDavaoca()
    {
        this.poslednjeDavanje=LocalDate.now();
        this.brojDavanja++;
    }

    @Override
    public String toString()
    {
        return id_davaoca+". "+ ime+" (" + imeRoditelja+") "  +prezime+" " +jmbg+" "+ adresa+" "+ telefon+" "+  this.getDatumRodjenja(this.jmbg);
    }


}
