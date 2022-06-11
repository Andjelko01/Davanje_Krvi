import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        //ArrayList<Akcija> akcije = (ArrayList<Akcija>) new Tehnicar().UcitajJSON("imenik.json");
        //Tehnicar t=new Tehnicar();
        //ArrayList<Tehnicar> th = (ArrayList<Tehnicar>) t.UcitajJSON("test");

        Tehnicar.UpisiUJSON(new ArrayList<>(),"tehnicari.json");
        Doktor.UpisiUJSON(new ArrayList<>(),"doktori.json");
        Davalac.UpisiUJSON(new ArrayList<>(),"davaoci.json");
        Akcija.UpisiUJSON(new ArrayList<>(),"akcije.json");
        Izvestaj.UpisiUJSON(new ArrayList<>(),"izvestaji.json");

        Scanner scanner = new Scanner(System.in);
        String glavni_meni = "1. Dodaj Akciju \t2. Popuni Izvestaj \t3. Pokreni Akciju \t4. \t0. Napusti program";
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

                    break;
                case 3:

                    break;
                case 4:

                    break;
            }
        }
        while (input != 0);
    }
}
