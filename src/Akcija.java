import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class Akcija
{
    protected int id_akcije;
    protected Date datumAkcije;
    protected int vremePocetka;
    protected int vremeKraja;
    protected String mestoOdrzavanja;
    protected Doktor doktor;
    protected ArrayList<Tehnicar> tehnicari;

    public Akcija(int id_akcije, Date datumAkcije, int vremePocetka, int vremeKraja, String mestoOdrzavanja, Doktor doktor, ArrayList<Tehnicar> tehnicari) {
        this.id_akcije = id_akcije;
        this.datumAkcije = datumAkcije;
        this.vremePocetka = vremePocetka;
        this.vremeKraja = vremeKraja;
        this.mestoOdrzavanja = mestoOdrzavanja;
        this.doktor = doktor;
        this.tehnicari = tehnicari;
    }

    public static ArrayList<Akcija> UcitajJSON(String path_name)
    {
        ArrayList<Akcija> akcijas = new ArrayList<>();
        try
        {
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader(path_name));

            for (Object o:jsonArray
            )
            {
                JSONObject jsonObject = (JSONObject) o;

                int id = Integer.parseInt(jsonObject.get("id_akcija").toString());
                int vreme_pocetka = Integer.parseInt(jsonObject.get("vreme_pocetka").toString());
                int vreme_kraja = Integer.parseInt(jsonObject.get("vreme_kraja").toString());
                Date datum_akcije = (Date) jsonObject.get("datum_akcije");
                String mesto_odrzavanja = jsonObject.get("mesto_odrzavanja").toString();
                int id_doktor = Integer.parseInt(jsonObject.get("id_doktora").toString());
                Doktor doktor = null;
                for (Doktor d:Doktor.UcitajJSON("doktori.json"))
                {
                    if (d.getId_doktora() == id_doktor) {
                        doktor = d;
                        break;
                    }
                }

                ArrayList<Tehnicar> list_svih = Tehnicar.UcitajJSON("tehnicari.json");

                ArrayList<Tehnicar> tehnicari = new ArrayList<>();

                JSONArray lista_id_tehnicara = (JSONArray) jsonObject.get("tehnicari");
                for (Object idD:lista_id_tehnicara
                )
                {
                    for (Tehnicar t:list_svih
                    )
                    {
                        if(t.getId_tehnicar() == Integer.parseInt(idD.toString()))
                            tehnicari.add(t);
                    }
                }

                Akcija akcija = new Akcija(id,datum_akcije,vreme_pocetka,vreme_kraja,mesto_odrzavanja,doktor,tehnicari);
                akcijas.add(akcija);
            }

        }
        catch (Exception e)
        {
            System.err.println("Greska pri ucitavanju Akcija.");
        }

        return akcijas;
    }

    public static void UpisiUJSON(ArrayList<Akcija> akcije, String path_name)
    {
        PrintWriter pw = null;
        try
        {
            JSONArray jsonArray = new JSONArray();
            pw = new PrintWriter(path_name);

            for (Akcija a:akcije
            )
            {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id_akcija",a.id_akcije);
                jsonObject.put("datum_akcije",a.datumAkcije);
                jsonObject.put("vreme_pocetka",a.vremePocetka);
                jsonObject.put("vreme_kraja",a.vremeKraja);
                jsonObject.put("mesto_odrzavanja",a.mestoOdrzavanja);
                jsonObject.put("id_doktora",a.doktor.getId_doktora());

                JSONArray teh = new JSONArray();
                for(Tehnicar t : a.tehnicari)
                {
                    teh.add(t.getId_tehnicar());
                }
                jsonObject.put("tehnicari",teh);

                jsonArray.add(jsonObject);
            }

            pw.write(jsonArray.toJSONString());

        }
        catch (Exception e)
        {
            System.err.println("Greska pri upisivanju Akcije.");
        }
        finally
        {
            if(pw!=null)
                pw.close();
        }
    }
}
