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
}
