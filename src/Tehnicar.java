import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

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
            System.err.println("Greska pri ucitavanju Tehnicara.");
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

}
