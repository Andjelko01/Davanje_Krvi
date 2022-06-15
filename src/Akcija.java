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

            for (Object o:jsonArray)
            {
                JSONObject jsonObject = (JSONObject) o;

                int id = Integer.parseInt(jsonObject.get("id_akcija").toString());
                int vreme_pocetka = Integer.parseInt(jsonObject.get("vreme_pocetka").toString());
                int vreme_kraja = Integer.parseInt(jsonObject.get("vreme_kraja").toString());
                String datumaAkc = jsonObject.get("datum_akcije").toString();
                    LocalDate datum_akcije = LocalDate.parse(datumaAkc);
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
            //System.err.println("Greska pri ucitavanju Akcija.");
            e.printStackTrace();
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
                jsonObject.put("datum_akcije",a.datumAkcije.toString());
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
        String input = "";
            System.out.print("Unesite datum akcije(DD.MM.GGGG): ");
            boolean isDateValid=false;
            do
            {
                try
                {
                    isDateValid=true;
                    String unos = scanner.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    date = LocalDate.parse(unos, formatter);

                    if(date.isBefore(LocalDate.now()))
                    {
                        isDateValid=false;
                        throw new DateTimeException("Neispravan datum");
                    }

                } catch (Exception e)
                {
                    isDateValid=false;
                    System.err.println("Uneti datum nije validan");
                }
            }while(!isDateValid);

            if(Doktor.DostupniDoktori(date).size() < 1 ||
                Tehnicar.DostupniTehnicari(date).size() < 2)
            {
                System.err.println("Nema dovoljno slobodnog osoblja za izabrani datum");
                return;
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
            String mesto;
            boolean isMestoEmpty = false;
        scanner.reset();
        do
            {
                System.out.print("Unesite mesto odrzavanja akcije: ");
                scanner.reset();
                mesto = scanner.nextLine();
                if (mesto.isEmpty())
                {
                    isMestoEmpty = true;
                    System.out.println("Niste uneli nista, ako zelite da prekinete kreiranje akcije unesite 0 ili unesite mesto akcije");
                } else
                {
                    isMestoEmpty = false;
                }
                if (mesto == "0")
                {
                    return;
                }
            }while(isMestoEmpty);

            Doktor doca;
            do
            {
                System.out.println("Izaberite doktora za akciju");
                ArrayList<Doktor> doktori = Doktor.DostupniDoktori(date);
                //Stampaj doktore
                for (Doktor d : doktori) {
                    System.out.println(d.getId_doktora() + "." + " " + d.ime + " " + d.prezime);
                }
                int id_doce = scanner.nextInt();
                doca = null;
                //izabrani doca ispitivanje
                for (Doktor d : doktori) {
                    if (id_doce == d.getId_doktora()) {
                        doca = d;
                        break;
                    }
                }
                if (doca == null) {
                    System.err.println("Ne postoji doktor sa unetim ID");
                    return;
                }
            }
            while(doca==null);

            //izbor broja tehnicara
            int brTehnicara = 0;
            do
            {
                System.out.println("Unesite broj tehnicara na akciji(2-5): ");
                brTehnicara = scanner.nextInt();

                if(brTehnicara < 2 || brTehnicara > 5)
                {
                    System.err.println("Broj tehnicara mora biti od 2 do 5");
                    return;
                }
            }while (brTehnicara < 2 || brTehnicara > 5);

            //izbor tehnicara
            ArrayList<Tehnicar> izabraniTehnicari = new ArrayList<>();
            ArrayList<Tehnicar> tehnicari = Tehnicar.DostupniTehnicari(date);
            for (int i = 0; i < brTehnicara; i++) {
                boolean isTehValid = true;
                Tehnicar teh = null;
                do {
                    System.out.println("Izaberite tehnicara za akciju");
                    //Stampanje tehnicara
                    for (Tehnicar t : tehnicari)
                    {
                        System.out.println(t.getId_tehnicar() + "." + " " + t.ime + " " + t.prezime);
                        // dodaj Filtriraj slobodne tehnicare
                    }

                    int id_teh = scanner.nextInt();

                    teh = null;
                    //pick tehnicar
                    for (Tehnicar t : tehnicari)
                    {
                       if (id_teh == t.getId_tehnicar())
                       {
                           teh = t;
                           isTehValid = true;
                           break;
                       }
                    }
                    if (teh == null)
                    {
                        isTehValid = false;
                        System.err.println("Ne postoji tehnicar sa unetim ID");
                    }
                    for (Tehnicar t : izabraniTehnicari)
                    {
                        if (id_teh == t.getId_tehnicar())
                        {
                            System.err.println("Ne mozete izabrati istog tehnicara dva puta");
                            isTehValid = false;
                            break;
                        }
                    }

                    if(isTehValid)
                        izabraniTehnicari.add(teh);
                    //KRAJ DODAVANJE TEHNICARA
                } while (!isTehValid);
            }

        Akcija a = new Akcija(Akcija.getNextId(),date,pocetak,kraj,mesto,doca,izabraniTehnicari);
        ArrayList<Akcija> sveAkcije = Akcija.UcitajJSON("akcije.json");
        sveAkcije.add(a);
        Akcija.UpisiUJSON(sveAkcije,"akcije.json");

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
                    Akcija.PokreniAkciju();
                    break;
                case 3:
                    Izvestaj.PrikazIzvestaja();

                    break;
                default:
                    break;
            }
        }
        while (input != 0);
    }

    public static void PokreniAkciju()
    {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Akcija> sveAkcije = Akcija.UcitajJSON("akcije.json");
        ArrayList<Izvestaj> sviIzvestaji = Izvestaj.UcitajJSON("izvestaji.json");
        ArrayList<Davalac> sviDavaoci = Davalac.UcitajJSON("davaoci.json");

        //Prikaz akcija koje su danas i koje se nisu desile

        boolean exist = false;
        ArrayList<Akcija> moguceAkcije = new ArrayList<>();
        for (Akcija a : sveAkcije) {
            exist = false;
            for (Izvestaj i : sviIzvestaji) {
                if (a.id_akcije == i.getId_akcije()) {
                    exist = true;
                    break;
                }
            }

            if (a.datumAkcije.equals(LocalDate.now()) && !exist)
                moguceAkcije.add(a);
        }

        Akcija izabrana_akcija = null;
        //Izaberi akciju
        System.out.println("Izaberite akciju, ili unesite 0 da otkazete pokretanje akcije: ");
        for (Akcija a : moguceAkcije) {
            System.out.println(a.id_akcije + ". " + a.vremePocetka + " - " + a.vremeKraja + " " + a.mestoOdrzavanja + " " + a.datumAkcije);
        }
        int izbor;
        boolean valid = false;
        do {
            izbor = scanner.nextInt();
            if (izbor == 0)
                return;
            for (Akcija a : moguceAkcije) {
                if (izbor == a.id_akcije) {
                    valid = true;
                    izabrana_akcija = a;
                }
            }
        } while (!valid);

        int input;
        ArrayList<Davalac> davaoci_za_akciju = new ArrayList<>();
        do {
            System.out.println("1. Izaberi Davaoca \t0. Zavrsi akciju i odstampaj izvestaj");
            input = scanner.nextInt();
            boolean postojiDavalac = false;

            if (input == 1) {
                postojiDavalac = false;
                System.out.println("Unesite jmbg korisnika");
                if(scanner.hasNext())
                    scanner.nextLine();
                String jmbg_unos = scanner.nextLine();
                for (Davalac d : sviDavaoci) {
                    if (jmbg_unos.equals(d.jmbg) && !postojiDavalacUAkciji(jmbg_unos,davaoci_za_akciju))
                    {
                        davaoci_za_akciju.add(d);
                        postojiDavalac = true;
                        break;
                    }
                }
                Davalac d = null;
                if (!postojiDavalac) {
                    System.out.println("Ne postoji davalac sa unetim JMBG, dodajete novog korisnika: ");

                    d = Davalac.DodajDavaoca();

                    if (d != null && !postojiDavalacUAkciji(d.jmbg,davaoci_za_akciju))
                        davaoci_za_akciju.add(d);
                    else
                        System.err.println("Neuspesno dodavanje davaoca, pokusajte ponovo");
                }
            } else {
                System.err.println("Greska, pokusajte ponovo!");
            }
        } while (input != 0);
        System.out.print("Unesite broj odbijenih davalaca: ");
        scanner.reset();
        int odb = scanner.nextInt();

        System.out.println("Stampanje izvestaja...");
        Izvestaj.StampajIzvestaj(izabrana_akcija, davaoci_za_akciju, odb);
    }

    public static boolean postojiDavalacUAkciji(String JMBG,ArrayList<Davalac> davaoci)
    {
        for (Davalac d:davaoci)
        {
             if (JMBG.equals(d.jmbg))
             {
                 System.err.println("Davalac se već nalazi u akciji");
                 return true;
             }
        }
        return false;
    }

}
