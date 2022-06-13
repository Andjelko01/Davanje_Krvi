import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Tehnicar extends Osoba{
    private int id_tehnicar;
    private String strucnaSprema;

    public int getId_tehnicar() {
        return id_tehnicar;
    }

    public void setId_tehnicar(int id_tehnicar) {
        this.id_tehnicar = id_tehnicar;
    }

    public String getStrucnaSprema() {
        return strucnaSprema;
    }

    public void setStrucnaSprema(String strucnaSprema) {
        this.strucnaSprema = strucnaSprema;
    }

    public Tehnicar(String ime, String prezime, String jmbg, String adresa, String telefon, String imeRoditelja, String pol, int id_tehnicar, String strucnaSprema)
    {
        super(ime, prezime, jmbg, adresa, telefon, imeRoditelja, pol);
        this.id_tehnicar = id_tehnicar;
        this.strucnaSprema = strucnaSprema;
    }

    public Tehnicar()
    {
    }

    public static ArrayList<Tehnicar> UcitajJSON(String path_name)
    {
        ArrayList<Tehnicar> tehnicars = new ArrayList<>();
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
                int id = Integer.parseInt(jsonObject.get("id_tehnicar").toString());
                String strucna_sprema = jsonObject.get("strucna_sprema").toString();

                Tehnicar tehnicar = new Tehnicar(ime,prezime,jmbg,adresa,telefon,imeRoditelja,pol,id,strucna_sprema);
                tehnicars.add(tehnicar);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            //System.err.println("Greska pri ucitavanju Tehnicara.");
        }

        return tehnicars;
    }

    public static void UpisiUJSON(ArrayList<Tehnicar> tehnicari, String path_name)
    {
        PrintWriter pw = null;
        try
        {
            JSONArray jsonArray = new JSONArray();
            pw = new PrintWriter(path_name);

            for (Tehnicar t:tehnicari
            )
            {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id_tehnicar",t.id_tehnicar);
                jsonObject.put("ime",t.ime);
                jsonObject.put("prezime",t.prezime);
                jsonObject.put("jmbg",t.jmbg);
                jsonObject.put("adresa",t.adresa);
                jsonObject.put("telefon",t.telefon);
                jsonObject.put("ime_roditelja",t.imeRoditelja);
                jsonObject.put("pol",t.pol);
                jsonObject.put("strucna_sprema",t.strucnaSprema);

                jsonArray.add(jsonObject);
            }

            pw.write(jsonArray.toJSONString());

        }
        catch (Exception e)
        {
            System.err.println("Greska pri ucitavanju Tehnicara.");
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
        ArrayList<Tehnicar> tehnicars = UcitajJSON("tehnicari.json");
        for (Tehnicar t : tehnicars)
            if(t.id_tehnicar > id)
                id = t.id_tehnicar;

        id++;

        return id;
    }

    public static void MenuTehnicar()
    {
        Scanner scanner = new Scanner(System.in);
        String meni = "1. Dodaj tehnicara \t2. Izmeni tehnicara \t0. Nazad";
        int input;
        do
        {
            System.out.println(meni);
            input = scanner.nextInt();

            switch (input)
            {
                case 1:
                    Tehnicar.DodajTehnicara();
                    break;
                case 2:
                    ArrayList<Tehnicar> tehnicari=UcitajJSON("tehnicari.json");
                    for (Tehnicar t:tehnicari)
                    {
                        System.out.println(t);
                    }
                    System.out.println("Unesite redni broj tehnicara koji zelite da izmenite:");
                    int rb=scanner.nextInt();
                    for (Tehnicar t:tehnicari)
                    {
                        if (t.id_tehnicar==rb)
                        {
                            t.IzmeniTehnicara();
                        }
                    }
                    UpisiUJSON(tehnicari,"tehnicari.json");

                    break;
                default:
                    break;
            }
        }
        while (input != 0);
    }




    public static void DodajTehnicara()
    {
        boolean postojiTehnicar=false;
        ArrayList<Tehnicar> ucitan = Tehnicar.UcitajJSON("tehnicari.json");
        String strucnaSprema;
        String[] podaci=null;
        do
        {
        Scanner scanner= new Scanner(System.in);
        System.out.println("Dodavanje tehnicara je u toku, u svakom trenutku mozete da uneste stop kako bi prekinuli unos");
        podaci=DodajOsobu();
        if (podaci==null)
        {
            return;
        }
        do {
            System.out.print("Unesite strucnu spremu tehnicara: ");
            strucnaSprema=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(strucnaSprema)){
                return;
            }
        }while(ProveraStringa(strucnaSprema));
            postojiTehnicar=false;
        for (Tehnicar t:ucitan)
        {
            if (podaci[2].equals(t.jmbg))
            {
                postojiTehnicar=true;
                System.out.println("Vec postoji tehnicar sa tim JMBG molim vas unesite ponovo tehnicara");
            }

        }
        }while(postojiTehnicar);
        Tehnicar t=new Tehnicar(podaci[0],podaci[1],podaci[2],podaci[3],podaci[4],podaci[5],podaci[6],getNextId(),strucnaSprema);

        ucitan.add(t);
        Tehnicar.UpisiUJSON(ucitan,"tehnicari.json");
    }

    public void IzmeniTehnicara(){
        System.out.println("Unesite vrednost koju zelite da izmenite bas kako je ispisano u meni-ju");
        System.out.println("1.ime 2.prezime 3.jmbg 4.adresa 5. telefon 6.imeRoditelja 7.pol 8.strucnaSprema");
        Scanner scanner= new Scanner(System.in);
        String promena=scanner.nextLine();
        if (promena.toLowerCase().equals("strucnasprema"))
        {
            setStrucnaSprema(promena);
        }
        else{
            this.izmeniPodatke(promena);
        }
    }

    @Override
    public String toString()
    {
        return id_tehnicar+". "+ ime+" (" + imeRoditelja+") "  +prezime+" " +jmbg+" "+ adresa+" "+ telefon+" "+  this.getDatumRodjenja(this.jmbg);
    }
    public static ArrayList<Tehnicar> DostupniTehnicari(LocalDate datum)
    {
        ArrayList<Tehnicar> tehnicari=Tehnicar.UcitajJSON("tehnicari.json");
        ArrayList<Akcija> akcije=Akcija.UcitajJSON("akcije.json");
        for (Akcija a:akcije)
        {
            if(a.datumAkcije.equals(datum))
            {
                for (Tehnicar teh:a.tehnicari)
                {
                    for (int i=0;i<tehnicari.size();i++)
                    {
                        if (teh.id_tehnicar==tehnicari.get(i).id_tehnicar)
                        {
                            tehnicari.remove(i--);
                        }
                    }
                }
            }
        }
        return tehnicari;
    }

}
