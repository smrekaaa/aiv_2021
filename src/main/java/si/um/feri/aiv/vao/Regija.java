package si.um.feri.aiv.vao;
import si.um.feri.aiv.patterns.observers.NovDnevniPodatekMail;
import si.um.feri.aiv.patterns.observers.Opazovalec;
import si.um.feri.aiv.patterns.observers.UrejenDnevniPodatek;
import si.um.feri.aiv.patterns.observers.UrejenaRegija;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("regija")
@SessionScoped
@Entity(name = "regija")
public class Regija implements Serializable {

    public Regija(int id, String naziv, String imeSkrbnika, String priimekSkrbnika, String emailSkrbnika, int stPrebivalcev) {
        this.regijaId = id;
        this.naziv = naziv;
        this.imeSkrbnika = imeSkrbnika;
        this.priimekSkrbnika = priimekSkrbnika;
        this.emailSkrbnika = emailSkrbnika;
        this.stPrebivalcev = stPrebivalcev;
        this.dnevniPodatki = new ArrayList<DnevniPodatek>();
        this.registrirajOpazovalcaZaUrejenoRegijo(new UrejenaRegija());
        this.registrirajOpazovalcaZaUrejenDnevniPodatek(new UrejenDnevniPodatek());
        this.registrirajOpazovalcaZaNovDnevniPodatek(new NovDnevniPodatekMail());
    }
    public Regija(String naziv, String imeSkrbnika, String priimekSkrbnika, String emailSkrbnika, int stPrebivalcev) {
        this.naziv = naziv;
        this.imeSkrbnika = imeSkrbnika;
        this.priimekSkrbnika = priimekSkrbnika;
        this.emailSkrbnika = emailSkrbnika;
        this.stPrebivalcev = stPrebivalcev;
        this.dnevniPodatki = new ArrayList<DnevniPodatek>();
        this.registrirajOpazovalcaZaUrejenoRegijo(new UrejenaRegija());
        this.registrirajOpazovalcaZaUrejenDnevniPodatek(new UrejenDnevniPodatek());
        this.registrirajOpazovalcaZaNovDnevniPodatek(new NovDnevniPodatekMail());
    }
    public Regija(){
        this("","", "", "", 0);
        this.registrirajOpazovalcaZaUrejenoRegijo(new UrejenaRegija());
        this.registrirajOpazovalcaZaUrejenDnevniPodatek(new UrejenDnevniPodatek());
        this.registrirajOpazovalcaZaNovDnevniPodatek(new NovDnevniPodatekMail());
    };

    private int regijaId;

    private String naziv;

    private String imeSkrbnika;

    private String priimekSkrbnika;

    private String emailSkrbnika;

    private int stPrebivalcev;

    private List<DnevniPodatek> dnevniPodatki;

    private List<Opazovalec> opazovalciUrejenaRegija = new ArrayList<>();
    private List<Opazovalec> opazovalciNovDnevniPodatek = new ArrayList<>();
    private List<Opazovalec> opazovalciUrejenDnevniPodatek = new ArrayList<>();

    // Opazovalci
    public void registrirajOpazovalcaZaUrejenoRegijo(Opazovalec o) {
        opazovalciUrejenaRegija.add(o);
    }

    public void obvestiVseOpazovalceZaUrejenoRegijo() {
        for (Opazovalec o : opazovalciUrejenaRegija) {
            o.akcija(this);
        }
    }

    public void registrirajOpazovalcaZaNovDnevniPodatek(Opazovalec o) {
        opazovalciNovDnevniPodatek.add(o);
    }

    public void obvestiVseOpazovalceZaNovDnevniPodatek(DnevniPodatek dp) {
        for (Opazovalec o: opazovalciNovDnevniPodatek) {
            o.akcija(dp, this);
        }
    }

    public void registrirajOpazovalcaZaUrejenDnevniPodatek(Opazovalec o) {
        opazovalciUrejenDnevniPodatek.add(o);
    }

    public void obvestiVseOpazovalceZaUrejenDnevniPodatek(DnevniPodatek dp) {
        System.out.println("a aaaaaaaaaaaaaa urejen podatek");
        for (Opazovalec o: opazovalciUrejenDnevniPodatek) {
            System.out.println("a aaaaaaaaaaaaaa urejen podatek");
            o.akcija(dp, this);
        }
    }

    //Setters&Getters

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int getRegijaId() {
        return regijaId;
    }

    public void setRegijaId(int id) {
        this.regijaId = id;
    }

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

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public List<DnevniPodatek> getDnevniPodatki() {
        return dnevniPodatki;
    }

    public void setDnevniPodatki(List<DnevniPodatek> dnevniPodatki) {
        this.dnevniPodatki = dnevniPodatki;
    }

    // toString

    @Override
    public String toString() {
        return "Regija{" +
                "id=" + regijaId +
                ", naziv='" + naziv + '\'' +
                ", imeSkrbnika='" + imeSkrbnika + '\'' +
                ", priimekSkrbnika='" + priimekSkrbnika + '\'' +
                ", emailSkrbnika='" + emailSkrbnika + '\'' +
                ", stPrebivalcev=" + stPrebivalcev +
                ", dnevniPodatki=" + dnevniPodatki +
                '}';
    }

    // Other methods
    public void dodajDnevniPodatek(DnevniPodatek dp) {
        this.dnevniPodatki.add(dp);
    }
}
