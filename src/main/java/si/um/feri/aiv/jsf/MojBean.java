package si.um.feri.aiv.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.FilterType;
import si.um.feri.aiv.dao.DnevniPodatekDao;
import si.um.feri.aiv.dao.RegijaDao;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="moj")
@SessionScoped
public class MojBean implements Serializable {

    @EJB
    private RegijaDao rdao; // = RegijaDao.getInstance();

    @EJB
    private DnevniPodatekDao dpdao; // = DnevniPodatekDao.getInstance();

    private static final long serialVersionUID = -8979220536758073133L;

    Logger log= LoggerFactory.getLogger(MojBean.class);

    private Regija novaRegija = new Regija();
    private Regija izbranaRegija = null;
    private DnevniPodatek novDnevniPodatek = new DnevniPodatek();
    private DnevniPodatek izbranDnevniPodatek = null;
    private Object[] vsiDnevniPodatki = null;
    private FilterType filterType = null;

    // Regija ------------------------------

    public void dodajRegijo() throws Exception {
        log.info("dodajRegijo " + novaRegija);
        rdao.dodajRegijo(novaRegija);

        novaRegija=new Regija();
    }

    public void izbrisiRegijo(Regija r) throws Exception {
        log.info("deleteRegija");
        rdao.izbrisiRegijo(r);
    }

    public void updateRegija(Regija r) throws Exception {
        log.info("updateRegija");
        rdao.urediRegijo(r);
    }

    // Dnevni podatek ------------------------------

    public void setVsiDnevniPodatki() {

        if (vsiDnevniPodatki == null) {
            log.info("setVsiDnevniPodatki");
            if (filterType == null) {
                Calendar d = new GregorianCalendar().getInstance();
                this.vsiDnevniPodatki = dpdao.getDnevnePodatkeNaDatum(d).entrySet().toArray();
                filterType = FilterType.NORMAL;
            }
        }
       else  {
           this.vsiDnevniPodatki = dpdao.getFiltriraneDnevnePodatke(filterType).entrySet().toArray();

         }
    }

    public Object[] getVsiDnevniPodatki() throws Exception {

        if (vsiDnevniPodatki == null) {
            log.info("setVsiDnevniPodatki");
            setVsiDnevniPodatki();
        }

        log.info("getVsiDnevniPodatki");
        return vsiDnevniPodatki;
    }

    public void getFiltriranePodatke(FilterType ft) {
        log.info("getFiltriranePodatke");
        filterType = ft;
        vsiDnevniPodatki = dpdao.getFiltriraneDnevnePodatke(filterType).entrySet().toArray();;
    }

    public List<DnevniPodatek> getVsiDnevniPodatkiRegije() throws Exception {
        log.info("getVsiDnevniPodatkiRegije");
        return dpdao.getDnevnePodatkeRegije(izbranaRegija.getRegijaId());
    }

    public void dodajDnevniPodatek() throws Exception {
        log.info("dodajDnevniPodatek");
        dpdao.dodajDnevniPodatek(novDnevniPodatek, izbranaRegija);
        novDnevniPodatek = new DnevniPodatek();
        novDnevniPodatek.setRegijaId(this.izbranaRegija.getRegijaId());
        Calendar d = new GregorianCalendar().getInstance();
        this.vsiDnevniPodatki = dpdao.getDnevnePodatkeNaDatum(d).entrySet().toArray();

    }

    public void dodajDnevniPodatekClone(DnevniPodatek dp) throws Exception {
        log.info("dodajDnevniPodatekClone");
        this.novDnevniPodatek = (DnevniPodatek) dp.clone();
        Calendar d = new GregorianCalendar().getInstance();
        this.vsiDnevniPodatki = dpdao.getDnevnePodatkeNaDatum(d).entrySet().toArray();
    }

    public void izbrisiDnevniPodatek(DnevniPodatek dp) throws Exception {
        log.info("izbrisiDnevniPodatek");
        dpdao.izbrisiDnevniPodatek(dp);
        Calendar d = new GregorianCalendar().getInstance();
        this.vsiDnevniPodatki = dpdao.getDnevnePodatkeNaDatum(d).entrySet().toArray();
      }

    public void updateDnevniPodatek(DnevniPodatek dp) throws Exception {
        log.info("updateDnevniPodatel");
        dpdao.urediDnevniPodatek(dp);
        Calendar d = new GregorianCalendar().getInstance();
        this.vsiDnevniPodatki = dpdao.getDnevnePodatkeNaDatum(d).entrySet().toArray();
    }

    //Setters & Getters - Regija

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
        this.novDnevniPodatek.setRegijaId(this.izbranaRegija.getRegijaId());
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

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }
}
