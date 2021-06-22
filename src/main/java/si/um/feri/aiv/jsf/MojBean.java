package si.um.feri.aiv.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.ejb.DnevniPodatki;
import si.um.feri.aiv.ejb.DnevniPodatkiDao;
import si.um.feri.aiv.ejb.Regije;
import si.um.feri.aiv.ejb.RegijeDao;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Named("moj")
@SessionScoped
public class MojBean implements Serializable {

    private static final long serialVersionUID = -8979220536758073133L;

    Logger log= LoggerFactory.getLogger(MojBean.class);

    private Regije rdao = new RegijeDao();
    private Regija novaRegija = new Regija();
    private Regija izbranaRegija = null;
    private DnevniPodatki dpdao = new DnevniPodatkiDao();
    private DnevniPodatek novDnevniPodatek = new DnevniPodatek();
    private DnevniPodatek izbranDnevniPodatek = null;



    // Regija ------------------------------

    public List<Regija> getVseRegije(){
        log.info("getVseRegije");
        return rdao.vrniVseRegije();
    }

    public void dodajRegijo() {
        log.info("dodajRegijo");
        rdao.shrani(novaRegija);
        novaRegija=new Regija();
    }

    public void izbrisiRegijo(Regija r) {
        log.info("deleteRegija");
        rdao.izbrisiRegijo(r);
    }

    public void updateRegija(Regija r) {
        log.info("updateRegija");
        rdao.spremeniRegijo(r.getNaziv(), r.getImeSkrbnika(),
                r.getPriimekSkrbnika(), r.getEmailSkrbnika(), r.getStPrebivalcev());
    }

    // Dnevni podatek ------------------------------

    public Object[] getVsiDnevniPodatki(){
        log.info("getVsiDnevniPodatki");
        return rdao.vrniVseDnevnePodatke().entrySet().toArray();
    }

    public List<DnevniPodatek> getVsiDnevniPodatkiRegije(){
        log.info("getVsiDnevniPodatkiRegije");
        return rdao.vrniVseDnevnePodatkeRegije(izbranaRegija);
    }

    public void dodajDnevniPodatek() {
        log.info("dodajDnevniPodatek");
        rdao.dodajDnevniVnos(novDnevniPodatek, izbranaRegija);
        novDnevniPodatek = new DnevniPodatek();
    }

    public void izbrisiDnevniPodatek(DnevniPodatek dp, Regija r) {
        log.info("izbrisiDnevniPodatek");
        rdao.izbrisiDnevniVnos(dp, r);
      }

    public void updateDnevniPodatek(DnevniPodatek dp) {
        log.info("updateDnevniPodatel");
        rdao.spremeniDnevniVnos(izbranaRegija, dp.getDatum(), dp.getOkuzeni(),
                dp.getHospitalizirani(), dp.getTestirani());
    }

    // Podateki
    public void setPodatki() throws ParseException{
        log.info("setPodatki");

        Regija r1 = new Regija("Savinjska", "Ela", "Pirnat", "ep@mail.com", 300);
        Regija r2 = new Regija("Podravska", "Pupa", "Kos", "pk@mail.com", 500);

        Date date1 = new SimpleDateFormat("dd.MM.yyyy").parse("22.06.2021");
        Date date2 = new SimpleDateFormat("dd.MM.yyyy").parse("21.06.2021");
        DnevniPodatek dp1 = new DnevniPodatek(2, 2, 2, date1);
        DnevniPodatek dp2 = new DnevniPodatek(3, 3, 4, date2);
        DnevniPodatek dp3 = new DnevniPodatek(4, 4, 4, date1);
        DnevniPodatek dp4 = new DnevniPodatek(5, 5, 5, date2);
        rdao.shrani(r1);
        rdao.shrani(r2);
        rdao.dodajDnevniVnos(dp1, r1);
        rdao.dodajDnevniVnos(dp2, r1);
        rdao.dodajDnevniVnos(dp3, r2);
        rdao.dodajDnevniVnos(dp4, r2);

    }

    //Setters & Getters - Regija

    public Regije getRdao() {
        return rdao;
    }

    public void setRdao(Regije rdao) {
        this.rdao = rdao;
    }

    public Regija getNovaRegija() {
        return novaRegija;
    }

    public void setNovaRegija(Regija novaRegija) {
        this.novaRegija = novaRegija;
    }

    public Regija getIzbranaRegija() {
        return izbranaRegija;
    }

    public void setIzbranaRegija(Regija izbranaRegija) {
        this.izbranaRegija = izbranaRegija;
    }

    public DnevniPodatki getDpdao() {
        return dpdao;
    }

    public void setDpdao(DnevniPodatki dpdao) {
        this.dpdao = dpdao;
    }

    public DnevniPodatek getNovDnevniPodatek() {
        return novDnevniPodatek;
    }

    public void setNovDnevniPodatek(DnevniPodatek novDnevniPodatek) {
        this.novDnevniPodatek = novDnevniPodatek;
    }

    public DnevniPodatek getIzbranDnevniPodatek() {
        return izbranDnevniPodatek;
    }

    public void setIzbranDnevniPodatek(DnevniPodatek izbranDnevniPodatek) {
        this.izbranDnevniPodatek = izbranDnevniPodatek;
    }
}
