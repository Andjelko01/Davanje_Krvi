import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        //ArrayList<Akcija> akcije = (ArrayList<Akcija>) new Tehnicar().UcitajJSON("imenik.json");
        Tehnicar t=new Tehnicar();
        ArrayList<Tehnicar> th = (ArrayList<Tehnicar>) t.UcitajJSON("test");
    }
}
