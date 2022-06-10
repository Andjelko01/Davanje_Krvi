import java.util.ArrayList;

public class Izvestaj
{
    private int id_akcije;
    private int brojDavaoca;
    private int brojPrvihDavanja;
    private int brojZena;
    private int brojMuskaraca;
    private int brojOdbijenih;
    private ArrayList<Davalac> listaDavaoca;

    public Izvestaj(int id_akcije, int brojDavaoca, int brojPrvihDavanja, int brojZena, int brojMuskaraca, int brojOdbijenih, ArrayList<Davalac> listaDavaoca) {
        this.id_akcije = id_akcije;
        this.brojDavaoca = brojDavaoca;
        this.brojPrvihDavanja = brojPrvihDavanja;
        this.brojZena = brojZena;
        this.brojMuskaraca = brojMuskaraca;
        this.brojOdbijenih = brojOdbijenih;
        this.listaDavaoca = new ArrayList<Davalac>();
    }

    public int getId_akcije() {
        return id_akcije;
    }

    public void setId_akcije(int id_akcije) {
        this.id_akcije = id_akcije;
    }

    public int getBrojDavaoca() {
        return brojDavaoca;
    }

    public void setBrojDavaoca(int brojDavaoca) {
        this.brojDavaoca = brojDavaoca;
    }

    public int getBrojPrvihDavanja() {
        return brojPrvihDavanja;
    }

    public void setBrojPrvihDavanja(int brojPrvihDavanja) {
        this.brojPrvihDavanja = brojPrvihDavanja;
    }

    public int getBrojZena() {
        return brojZena;
    }

    public void setBrojZena(int brojZena) {
        this.brojZena = brojZena;
    }

    public int getBrojMuskaraca() {
        return brojMuskaraca;
    }

    public void setBrojMuskaraca(int brojMuskaraca) {
        this.brojMuskaraca = brojMuskaraca;
    }

    public int getBrojOdbijenih() {
        return brojOdbijenih;
    }

    public void setBrojOdbijenih(int brojOdbijenih) {
        this.brojOdbijenih = brojOdbijenih;
    }

    public ArrayList<Davalac> getListaDavaoca() {
        return listaDavaoca;
    }

    public void setListaDavaoca(ArrayList<Davalac> listaDavaoca) {
        this.listaDavaoca = listaDavaoca;
    }

    public void dodajDavaoca(Davalac davalac){
        this.listaDavaoca.add(davalac);
    }


}
