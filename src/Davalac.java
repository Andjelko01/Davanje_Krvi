import java.util.Date;

public class Davalac extends Osoba{
    private int id_davaoca;
    private String krvnaGrupa;
    private int brojDavanja;
    private Date poslednjeDavanje;
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

    public Date getPoslednjeDavanje() {
        return poslednjeDavanje;
    }

    public void setPoslednjeDavanje(Date poslednjeDavanje) {
        this.poslednjeDavanje = poslednjeDavanje;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    public Davalac(String ime, String prezime, String jmbg, String adresa, String telefon, String imeRoditelja, String pol, int id_davaoca, String krvnaGrupa, int brojDavanja, Date poslednjeDavanje, boolean aktivan) {
        super(ime, prezime, jmbg, adresa, telefon, imeRoditelja, pol);
        this.id_davaoca = id_davaoca;
        this.krvnaGrupa = krvnaGrupa;
        this.brojDavanja = brojDavanja;
        this.poslednjeDavanje = poslednjeDavanje;
        this.aktivan = aktivan;
    }
}
