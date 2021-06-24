package si.um.feri.aiv.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.ejb.*;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="moj")
@SessionScoped
public class MojBean implements Serializable {

    private static final long serialVersionUID = -8979220536758073133L;

    Logger log= LoggerFactory.getLogger(MojBean.class);

    //private RegijaDao rdao = new RegijaDao();
    private Regija novaRegija = new Regija();
    private Regija izbranaRegija = null;
    //private DnevniPodatekDao dpdao = new DnevniPodatekDao();
    private DnevniPodatek novDnevniPodatek = new DnevniPodatek();
    private DnevniPodatek izbranDnevniPodatek = null;

    // Regija ------------------------------

    public void dodajRegijo() throws Exception {
        log.info("dodajRegijo");
        RegijaDao.getInstance().shrani(novaRegija);
        novaRegija=new Regija();
    }

    public void izbrisiRegijo(Regija r) throws Exception {
        log.info("deleteRegija");
        RegijaDao.getInstance().izbrisi(r.getId());
    }

    public void updateRegija(Regija r) throws Exception {
        log.info("updateRegija");
        RegijaDao.getInstance().shrani(r);
    }

    // Dnevni podatek ------------------------------

    public  Object[] getVsiDnevniPodatki() throws Exception {
        log.info("getVsiDnevniPodatki");
        Calendar d = new GregorianCalendar().getInstance();
        return DnevniPodatekDao.getInstance().vrniVseNaDatum((GregorianCalendar)d).entrySet().toArray();
    }

    public List<DnevniPodatek> getVsiDnevniPodatkiRegije() throws Exception {
        log.info("getVsiDnevniPodatkiRegije");
        return DnevniPodatekDao.getInstance().vrniVseRegije(izbranaRegija.getId());
    }

    public void dodajDnevniPodatek() throws Exception {
        log.info("dodajDnevniPodatek");
        DnevniPodatekDao.getInstance().shrani(novDnevniPodatek);
        novDnevniPodatek = new DnevniPodatek();
        novDnevniPodatek.setRegijaId(this.izbranaRegija.getId());
    }

    public void izbrisiDnevniPodatek(DnevniPodatek dp) throws Exception {
        log.info("izbrisiDnevniPodatek");
        DnevniPodatekDao.getInstance().izbrisi(dp.getId());
      }

    public void updateDnevniPodatek(DnevniPodatek dp) throws Exception {
        log.info("updateDnevniPodatel");
        DnevniPodatekDao.getInstance().shrani(dp);
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
        this.novDnevniPodatek.setRegijaId(this.izbranaRegija.getId());
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
