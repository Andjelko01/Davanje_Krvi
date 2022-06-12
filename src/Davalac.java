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

public class Davalac extends Osoba{
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
                LocalDate poslednje_davanje = (LocalDate) jsonObject.get("poslednje_davanje");
                boolean aktivnost = (boolean) jsonObject.get("aktivan");

                Davalac davalac = new Davalac(ime,prezime,jmbg,adresa,telefon,imeRoditelja,pol,id,krvna_grupa,broj_davanja,poslednje_davanje,aktivnost);
                davalacs.add(davalac);
            }

        }
        catch (Exception e)
        {
            System.err.println("Greska pri ucitavanju Davalaca.");
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
                jsonObject.put("poslednje_davanje",d.poslednjeDavanje);
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
    public static void DodajDavaoca()
    {
        String krvnaGrupa,brojDavanja,datumDavanja;
        LocalDate poslednjeDavanje;
        boolean aktivan;
        Scanner scanner= new Scanner(System.in);
        System.out.println("Dodavanje davaoca je u toku, u svakom trenutku mozete da uneste stop kako bi prekinuli unos");
        String[] podaci=DodajOsobu();
        if (podaci==null)
        {
            return;
        }
        do {
            System.out.println("Unesite krvnu grupu");
            krvnaGrupa=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(krvnaGrupa)){
                return;
            }
        }while(ProveraStringa(krvnaGrupa));
        do {
            System.out.println("Unesite broj davanja");
            brojDavanja=scanner.nextLine();
            scanner.reset();
            if (PrekidUnosa(brojDavanja)){
                return;
            }
        }while(ProveraStringa(brojDavanja));
        int godine= getDatumRodjenja(podaci[2]).getYear()-LocalDate.now().getYear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        //Davalac davaoc= new Davalac(podaci[0],podaci[1],podaci[2],podaci[3],podaci[4],podaci[5],podaci[6],getNextId(),krvnaGrupa,Integer.parseInt(brojDavanja),formatter.format(LocalDate.now()),(godine>=18 || godine<65)?true:false);
    }

}
