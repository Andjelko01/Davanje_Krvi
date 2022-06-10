public abstract class Osoba
{
    protected String ime;
    protected String prezime;
    protected String jmbg;
    protected String adresa;
    protected String telefon;
    protected String imeRoditelja;
    protected String pol;

    public Osoba() {
    }

    public Osoba(String ime, String prezime, String jmbg, String adresa, String telefon, String imeRoditelja, String pol) {
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.adresa = adresa;
        this.telefon = telefon;
        this.imeRoditelja = imeRoditelja;
        this.pol = pol;
    }

}