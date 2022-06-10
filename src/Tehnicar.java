import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
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

    public Tehnicar(String ime, String prezime, String jmbg, String adresa, String telefon, String imeRoditelja, String pol, int id_tehnicar, String strucnaSprema) {
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
                int id =;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return tehnicars;
    }
}
