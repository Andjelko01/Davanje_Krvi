import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Doktor extends Osoba implements Informacije, Uredjivanje{
    private int id_doktora;

    public Doktor(String ime, String prezime, String jmbg, String adresa, String telefon, String imeRoditelja, String pol, int id_doktora) {
        super(ime, prezime, jmbg, adresa, telefon, imeRoditelja, pol);
        this.id_doktora = id_doktora;
    }

    public int getId_doktora() {
        return id_doktora;
    }

    public void setId_doktora(int id_doktora) {
        this.id_doktora = id_doktora;
    }

    public static ArrayList<Doktor> UcitajJSON(String path_name)
    {
        ArrayList<Doktor> doktors = new ArrayList<>();
        try
        {
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader(path_name));

            for (Object o:jsonArray)
            {
                JSONObject jsonObject = (JSONObject) o;

                String ime = jsonObject.get("ime").toString();
                String prezime = jsonObject.get("prezime").toString();
                String jmbg = jsonObject.get("jmbg").toString();
                String adresa = jsonObject.get("adresa").toString();
                String telefon = jsonObject.get("telefon").toString();
                String imeRoditelja = jsonObject.get("ime_roditelja").toString();
                String pol = jsonObject.get("pol").toString();
                int id = Integer.parseInt(jsonObject.get("id_doktor").toString());

                Doktor doktor = new Doktor(ime,prezime,jmbg,adresa,telefon,imeRoditelja,pol,id);
                doktors.add(doktor);
            }

        }
        catch (Exception e)
        {
           System.err.println("Greska pri ucitavanju Doktora.");
        }

        return doktors;
    }

    public static void UpisiUJSON(ArrayList<Doktor> doktori, String path_name)
    {
        PrintWriter pw = null;
        try
        {
            JSONArray jsonArray = new JSONArray();
            pw = new PrintWriter(path_name);

            for (Doktor d:doktori
            )
            {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id_doktor",d.id_doktora);
                jsonObject.put("ime",d.ime);
                jsonObject.put("prezime",d.prezime);
                jsonObject.put("jmbg",d.jmbg);
                jsonObject.put("adresa",d.adresa);
                jsonObject.put("telefon",d.telefon);
                jsonObject.put("ime_roditelja",d.imeRoditelja);
                jsonObject.put("pol",d.pol);

                jsonArray.add(jsonObject);
            }

            pw.write(jsonArray.toJSONString());

        }
        catch (Exception e)
        {
            System.err.println("Greska pri upisivanju Doktora.");
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
        ArrayList<Doktor> doktors = UcitajJSON("doktori.json");
        for (Doktor d : doktors)
            if(d.id_doktora > id)
                id = d.id_doktora;
        id++;

        return id;
    }

    public static void MenuDoktor()
    {
        Scanner scanner = new Scanner(System.in);
        String meni = "1. Dodaj doktora \t2. Izmeni doktora \t0. Nazad";
        int input;
        do
        {
            System.out.println(meni);
            input = scanner.nextInt();
            switch (input)
            {
                case 1:
                    Doktor.DodajDoktora();
                    break;
                case 2:
                    int unos;
                    ArrayList<Doktor> doktori=UcitajJSON("doktori.json");
                    for (Doktor d:doktori)
                    {
                        System.out.println(d);
                    }
                    System.out.println("Unesite redni broj doktora koji zelite da izmenite (ako zelite da prekinete unesite 0):");
                    int rb;
                    scanner.hasNext();
                        unos=scanner.nextInt();
                        if (unos==0)
                        {
                            break;
                        }
                    for (Doktor d:doktori)
                    {
                        if (d.id_doktora==unos)
                        {
                            d.Izmeni();
                        }
                    }
                    UpisiUJSON(doktori,"doktori.json");
                    break;
                default:
                    break;
            }
        }
        while (input != 0);
    }

    public static void DodajDoktora()
    {
        Scanner scanner= new Scanner(System.in);
        String[] podaci=null;
        ArrayList<Doktor> doktori=UcitajJSON("doktori.json");
        boolean postojiDoktor=false;
        do
        {
            System.out.println("Dodavanje doktora je u toku, u svakom trenutku mozete da uneste stop kako bi prekinuli unos");
            podaci=DodajOsobu();
            postojiDoktor=false;
            if (podaci==null)
            {
                return;
            }
            for (Doktor d:doktori)
            {
                if (podaci[2].equals(d.jmbg))
                {
                    postojiDoktor=true;
                    System.out.println("Vec postoji doktor sa tim JMBG molim vas unesite ponovo tehnicara");
                }
            }
        }while(postojiDoktor);
        Doktor t=new Doktor(podaci[0],podaci[1],podaci[2],podaci[3],podaci[4],podaci[5],podaci[6],getNextId());
    }
    @Override
    public void Izmeni()
    {
        System.out.println("Unesite vrednost koju zelite da izmenite bas kako je ispisano u meni-ju");
        System.out.println("1.ime 2.prezime 3.jmbg 4.adresa 5. telefon 6.imeRoditelja 7.pol");
        Scanner scanner= new Scanner(System.in);
        this.izmeniPodatke(scanner.nextLine());
    }

    @Override
    public String toString()
    {
        return id_doktora+". "+ ime+" (" + imeRoditelja+") "  +prezime+" " +jmbg+" "+ adresa+" "+ telefon+" "+  this.getDatumRodjenja(this.jmbg);
    }

    //Samo dostpuni doktori za taj datum
    public static ArrayList<Doktor> DostupniDoktori(LocalDate datum)
    {
        ArrayList<Doktor> doktori=Doktor.UcitajJSON("doktori.json");
        ArrayList<Akcija> akcije=Akcija.UcitajJSON("akcije.json");
        for (Akcija a:akcije)
        {
                if(a.datumAkcije.equals(datum))
                {
                    for (int i=0;i<doktori.size();i++)
                    {
                        if (doktori.get(i).id_doktora==a.doktor.id_doktora)
                        {
                            doktori.remove(i--);
                        }
                    }
                }
            }
        return doktori;
    }

    @Override
    public void Pauza(Akcija a) {
        System.out.println("Doktor je na pauzi od "+(a.vremePocetka+2)+":00 do"+(a.vremePocetka+2)+":15");
    }
}
