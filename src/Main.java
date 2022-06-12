import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        //ArrayList<Akcija> akcije = (ArrayList<Akcija>) new Tehnicar().UcitajJSON("imenik.json");
        //Tehnicar t=new Tehnicar();
        //ArrayList<Tehnicar> th = (ArrayList<Tehnicar>) t.UcitajJSON("test");

//        ArrayList<Tehnicar> tehnicars = new ArrayList<>();
//        ArrayList<Doktor> doktors = new ArrayList<>();
//        ArrayList<Davalac> davalacs = new ArrayList<>();
//        ArrayList<Akcija> akcijas = new ArrayList<>();
//        ArrayList<Izvestaj> izvestajs = new ArrayList<>();

//        davalacs.add(new Davalac("Nemanja","Andjelkovic","1308001733522","Petra Petrovica 33",
//                "+381618041098","Nebojsa","Muski",1,"A",5,
//                null,true));
//
//        Davalac.UpisiUJSON(davalacs,"davaoci.json");
////        Akcija.UpisiUJSON(new ArrayList<>(),"akcije.json");
//        Izvestaj.UpisiUJSON(new ArrayList<>(),"izvestaji.json");

//        Scanner scanner = new Scanner(System.in);
//        String glavni_meni = "1. Upravljaj Akcijama \t2. Upravljaj doktorima \t3. Upravljaj Tehnicarima \t0. Napusti program";
//        int input;
//        do
//        {
//            System.out.println(glavni_meni);
//            input = scanner.nextInt();
//
//            switch (input)
//            {
//                case 1:
//                    Akcija.MenuAkcija();
//                    break;
//                case 2:
//                    Doktor.MenuDoktor();
//                    break;
//                case 3:
//                    Tehnicar.MenuTehnicar();
//                    break;
//                default:
//                    break;
//            }
//        }
//        while (input != 0);
//        System.out.println("Program se zatvara, dovidjenja!");
        Tehnicar.DodajTehnicara();

    }
}
