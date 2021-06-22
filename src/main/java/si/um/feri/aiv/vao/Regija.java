package si.um.feri.aiv.vao;
import java.util.ArrayList;
import java.util.List;

public class Regija {


    public Regija(String naziv, String imeSkrbnika, String priimekSkrbnika, String emailSkrbnika, int stPrebivalcev) {
        this.naziv = naziv;
        this.imeSkrbnika = imeSkrbnika;
        this.priimekSkrbnika = priimekSkrbnika;
        this.emailSkrbnika = emailSkrbnika;
        this.stPrebivalcev = stPrebivalcev;
        this.dnevniPodatki = new ArrayList<DnevniPodatek>();
    }
    public Regija(){
        this("","", "", "", 0);
    };

    private String naziv;

    private String imeSkrbnika;

    private String priimekSkrbnika;

    private String emailSkrbnika;

    private int stPrebivalcev;

    private List<DnevniPodatek> dnevniPodatki;

    //Setters&Getters

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getImeSkrbnika() {
        return imeSkrbnika;
    }

    public void setImeSkrbnika(String imeSkrbnika) {
        this.imeSkrbnika = imeSkrbnika;
    }

    public String getPriimekSkrbnika() {
        return priimekSkrbnika;
    }

    public void setPriimekSkrbnika(String priimekSkrbnika) {
        this.priimekSkrbnika = priimekSkrbnika;
    }

    public String getEmailSkrbnika() {
        return emailSkrbnika;
    }

    public void setEmailSkrbnika(String emailSkrbnika) {
        this.emailSkrbnika = emailSkrbnika;
    }

    public int getStPrebivalcev() {
        return stPrebivalcev;
    }

    public void setStPrebivalcev(int stPrebivalcev) {
        this.stPrebivalcev = stPrebivalcev;
    }

    public List<DnevniPodatek> getDnevniPodatki() {
        return dnevniPodatki;
    }

    public void setDnevniPodatki(List<DnevniPodatek> dnevniPodatki) {
        this.dnevniPodatki = dnevniPodatki;
    }

    // Other methods
    public void dodajDnevniPodatek(DnevniPodatek dp) {
        this.dnevniPodatki.add(dp);
    }
}
