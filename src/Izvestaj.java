import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class Izvestaj
{
    private int id_akcije;
    private int brojDavalaca;
    private int brojPrvihDavanja;
    private int brojZena;
    private int brojMuskaraca;
    private int brojOdbijenih;
    private ArrayList<Davalac> listaDavalaca;

    public Izvestaj(int id_akcije, int brojDavalaca, int brojPrvihDavanja, int brojZena, int brojMuskaraca, int brojOdbijenih, ArrayList<Davalac> listaDavalaca) {
        this.id_akcije = id_akcije;
        this.brojDavalaca = brojDavalaca;
        this.brojPrvihDavanja = brojPrvihDavanja;
        this.brojZena = brojZena;
        this.brojMuskaraca = brojMuskaraca;
        this.brojOdbijenih = brojOdbijenih;
        this.listaDavalaca = new ArrayList<Davalac>();
    }

    public int getId_akcije() {
        return id_akcije;
    }

    public void setId_akcije(int id_akcije) {
        this.id_akcije = id_akcije;
    }

    public int getBrojDavaoca() {
        return brojDavalaca;
    }

    public void setBrojDavaoca(int brojDavalaca) {
        this.brojDavalaca = brojDavalaca;
    }

    public int getBrojPrvihDavanja() {
        return brojPrvihDavanja;
    }

    public void setBrojPrvihDavanja(int brojPrvihDavanja) {
        this.brojPrvihDavanja = brojPrvihDavanja;
    }

    public int getBrojZena() {
        return brojZena;
    }

    public void setBrojZena(int brojZena) {
        this.brojZena = brojZena;
    }

    public int getBrojMuskaraca() {
        return brojMuskaraca;
    }

    public void setBrojMuskaraca(int brojMuskaraca) {
        this.brojMuskaraca = brojMuskaraca;
    }

    public int getBrojOdbijenih() {
        return brojOdbijenih;
    }

    public void setBrojOdbijenih(int brojOdbijenih) {
        this.brojOdbijenih = brojOdbijenih;
    }

    public ArrayList<Davalac> getListaDavaoca() {
        return listaDavalaca;
    }

    public void setListaDavaoca(ArrayList<Davalac> listaDavalaca) {
        this.listaDavalaca = listaDavalaca;
    }

    public void dodajDavaoca(Davalac davalac){
        this.listaDavalaca.add(davalac);
    }

    public static ArrayList<Izvestaj> UcitajJSON(String path_name)
    {
        ArrayList<Izvestaj> izvestajs = new ArrayList<>();
        try
        {
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader(path_name));

            for (Object o:jsonArray
            )
            {
                JSONObject jsonObject = (JSONObject) o;

                int id = Integer.parseInt(jsonObject.get("id_akcija").toString());
                int broj_davaoca = Integer.parseInt(jsonObject.get("broj_davalaca").toString());
                int broj_prvih_davanja = Integer.parseInt(jsonObject.get("broj_prvih_davanja").toString());
                int broj_zena = Integer.parseInt(jsonObject.get("broj_zena").toString());
                int broj_muskaraca = Integer.parseInt(jsonObject.get("broj_muskaraca").toString());
                int broj_odbijenih = Integer.parseInt(jsonObject.get("broj_odbijenih").toString());

                ArrayList<Davalac> lista_davalaca = new ArrayList<>();

                ArrayList<Davalac> list_svih = Davalac.UcitajJSON("davaoci.json");

                JSONArray iddavalaca = (JSONArray) jsonObject.get("lista_id_davalaca");
                for (Object idD:iddavalaca
                     )
                {
                    for (Davalac d:list_svih
                         )
                    {
                        if(d.getId_davaoca() == Integer.parseInt(idD.toString()))
                            lista_davalaca.add(d);
                    }
                }

                Izvestaj izvestaj = new Izvestaj(id,broj_davaoca,broj_prvih_davanja,broj_zena,broj_muskaraca,broj_odbijenih,lista_davalaca);
                izvestajs.add(izvestaj);
            }

        }
        catch (Exception e)
        {
            System.err.println("Greska pri ucitavanju Davalaca.");
        }

        return izvestajs;
    }

    public static void UpisiUJSON(ArrayList<Izvestaj> izvestaji, String path_name)
    {
        PrintWriter pw = null;
        try
        {
            JSONArray jsonArray = new JSONArray();
            pw = new PrintWriter(path_name);

            for (Izvestaj i:izvestaji
            )
            {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id_akcija",i.id_akcije);
                jsonObject.put("broj_davalaca",i.brojDavalaca);
                jsonObject.put("broj_prvih_davanja",i.brojPrvihDavanja);
                jsonObject.put("broj_zena",i.brojZena);
                jsonObject.put("broj_muskaraca",i.brojMuskaraca);
                jsonObject.put("broj_odbijenih",i.brojOdbijenih);

                JSONArray listaid = new JSONArray();
                for(Davalac d : i.listaDavalaca)
                {
                    listaid.add(d.getId_davaoca());
                }
                jsonObject.put("lista_id_davalaca",listaid);

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

    public static void StampajIzvestaj(Akcija akcija, ArrayList<Davalac> davaoci_akcije,int brojOdbijenih)
    {
        ArrayList<Davalac> sviDavaoci = Davalac.UcitajJSON("davaoci.json");
        int brojPrvih = 0;
        int brojZena = 0;
        int brojMuskaraca = 0;
        for (Davalac d:davaoci_akcije)
        {
            for (Davalac da: sviDavaoci)
            {
                if(d.getId_davaoca() == da.getId_davaoca())
                    da.AzurirajDavaoca();
            }

            if(d.getBrojDavanja() == 1)
                brojPrvih++;

            if(d.pol.toLowerCase() == "musko" || d.pol.toLowerCase() == "muski" || d.pol.toLowerCase() == "m")
                brojMuskaraca++;
            else
                brojZena++;
        }

        ArrayList<Izvestaj> sviIzvestaji = Izvestaj.UcitajJSON("izvestaji.json");
        Izvestaj izvestaj = new Izvestaj(akcija.id_akcije,davaoci_akcije.size(),brojPrvih,brojZena,brojMuskaraca,brojOdbijenih,davaoci_akcije);
        sviIzvestaji.add(izvestaj);
        Izvestaj.UpisiUJSON(sviIzvestaji,"izvestaji.json");
    }

    public static void PrikazIzvestaja()
    {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Izvestaj> sviIzvestaji = Izvestaj.UcitajJSON("izvestaji.json");
        ArrayList<Akcija> sveAkcije = Akcija.UcitajJSON("akcije.json");
        Akcija izabrana_akcija = null;

        int izbor = -1;
        System.out.println("Izaberite akciju ili unesite 0 da prekinete akciju");
        do
        {
            for (Akcija a: sveAkcije)
            {
                System.out.println(a.id_akcije + ". " + a.datumAkcije + " " + a.vremePocetka + "-" + a.vremeKraja + " - " + a.mestoOdrzavanja);
            }
            izbor = scanner.nextInt();
            for (Akcija a: sveAkcije)
            {
                if(a.id_akcije == izbor)
                {
                    izabrana_akcija = a;
                    break;
                }
            }

            if(izabrana_akcija == null)
                System.err.println("Nema akcije za izabrani id");
        }
        while (izbor!=0 || izabrana_akcija==null);
    }

}
