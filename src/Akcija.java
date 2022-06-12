import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;
import java.text.*;
import java.time.format.*;

public class Akcija
{
    protected int id_akcije;
    protected LocalDate datumAkcije;
    protected int vremePocetka;
    protected int vremeKraja;
    protected String mestoOdrzavanja;
    protected Doktor doktor;
    protected ArrayList<Tehnicar> tehnicari;

    public Akcija(int id_akcije, LocalDate datumAkcije, int vremePocetka, int vremeKraja, String mestoOdrzavanja, Doktor doktor, ArrayList<Tehnicar> tehnicari) {
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
                LocalDate datum_akcije = (LocalDate) jsonObject.get("datum_akcije");
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

    public static void DodajAkciju()
    {
        Scanner scanner = new Scanner(System.in);
        LocalDate date = null;

        System.out.print("Unesite datum akcije(DD/MM/GGGG): ");
        try {
            String unos = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            date = LocalDate.parse(unos, formatter);

            if(date.isBefore(LocalDate.now())) {
                throw new DateTimeException("Neispravan datum");
            }

        } catch (Exception e) {
            System.err.println("Uneti datum nije validan");
        }

        System.out.print("Unesite vreme pocetka akcije: ");
        int pocetak = scanner.nextInt();
        if(pocetak > 23 || pocetak < 0)
        {
            System.err.println("Vreme pocetka nije validno");
            return;
        }
        System.out.print("Unesite vreme kraja akcije: ");
        int kraj = scanner.nextInt();
        if(kraj > 23 || kraj < 0 || kraj < pocetak)
        {
            System.err.println("Vreme kraja akcije nije validno");
            return;
        }
        System.out.print("Unesite mesto odrzavanja akcije: ");
        String mesto = scanner.nextLine();

        System.out.println("Izaberite doktora za akciju");
        ArrayList<Doktor> doktori = Doktor.UcitajJSON("doktori.json");
        for (Doktor d:doktori)
        {
            System.out.println(d.getId_doktora() + "." + " " + d.ime + " " + d.prezime);
        }
        int id_doce = scanner.nextInt();
        Doktor doca = null;
        for (Doktor d:doktori)
        {
            if(id_doce == d.getId_doktora())
            {
                doca = d;
                break;
            }
        }
        if(doca == null)
        {
            System.err.println("Ne postoji doktor sa unetim ID");
            return;
        }

        int brTehnicara = scanner.nextInt();
        if(brTehnicara < 2 || brTehnicara > 5)
        {
            System.err.println("Broj tehnicara mora biti od 2 do 5");
            return;
        }
        ArrayList<Tehnicar> izabraniTehnicari = new ArrayList<>();
        ArrayList<Tehnicar> tehnicari = Tehnicar.UcitajJSON("tehnicari.json");
        for (int i = 0; i < brTehnicara; i++)
        {
            System.out.println("Izaberite tehnicara za akciju");
            for (Tehnicar t:tehnicari)
            {
                System.out.println(t.getId_tehnicar() + "." + " " + t.ime + " " + t.prezime);
            }
            int id_teh = scanner.nextInt();
            Tehnicar teh = null;
            for (Tehnicar t:tehnicari)
            {
                if(id_teh == t.getId_tehnicar())
                {
                    teh = t;
                    break;
                }
            }
            if(teh == null)
            {
                System.err.println("Ne postoji tehnicar sa unetim ID");
                return;
            }
            else
                izabraniTehnicari.add(teh);

            Akcija a = new Akcija(Akcija.getNextId(),date,pocetak,kraj,mesto,doca,izabraniTehnicari);
        }


    }

    private static int getNextId()
    {
        int id = 0;
        ArrayList<Akcija> akcijas = UcitajJSON("akcije.json");
        for (Akcija a : akcijas)
            if(a.id_akcije > id)
                id = a.id_akcije;

        id++;

        return id;
    }

    public static void MenuAkcija()
    {
        Scanner scanner = new Scanner(System.in);
        String glavni_meni = "1. Dodaj Akciju \t2. Pokreni akciju \t3. Pregledaj izvestaj \t0. Nazad";
        int input;
        do
        {
            System.out.println(glavni_meni);
            input = scanner.nextInt();

            switch (input)
            {
                case 1:
                    Akcija.DodajAkciju();
                    break;
                case 2:
                    //Akcija.PokreniAkciju();
                    break;
                case 3:
                    //Izvestaj.PrikaziIzvestaj();
                    break;
                default:
                    break;
            }
        }
        while (input != 0);
    }
}
