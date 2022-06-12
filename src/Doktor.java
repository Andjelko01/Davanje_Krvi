import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Doktor extends Osoba{
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

                jsonObject.put("id_davalac",d.id_doktora);
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
                    //Doktor.DodajDoktora();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                default:
                    break;
            }
        }
        while (input != 0);
    }
}
