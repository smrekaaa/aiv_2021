package si.um.feri.aiv.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RegijeDao implements Regije {

    Logger log= LoggerFactory.getLogger(RegijeDao.class);

    private static List<Regija> regije = Collections.synchronizedList(new ArrayList<Regija>());

    @Override
    public Regija najdiNaziv(String naziv) {
        for (Regija r: regije){
            if (r.getNaziv().equals(naziv)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void shrani(Regija r) {
        if (regije.contains(r)) {
            log.info("Regija že obstaja");
            return;
        }

        for (Regija rr : regije) {
           if (rr.getNaziv().equals(r.getNaziv())) {
               log.info("Regija že obstaja");
               return;
           }
        }

        regije.add(r);
        log.info("Regija dodana");
    }

    @Override
    public void spremeniRegijo(String naziv, String imeSkrbnika, String priimekSkrbnika,
                               String email, int stPrebivalcev) {
        Regija r = najdiNaziv(naziv);

        if (r != null) {
            r.setNaziv(naziv);
            r.setImeSkrbnika(imeSkrbnika);
            r.setPriimekSkrbnika(priimekSkrbnika);
            r.setEmailSkrbnika(email);
            r.setStPrebivalcev(stPrebivalcev);
            log.info("Regija posodobljena");
        }
        log.info("Regija ni bila posodobljena");
    }

    @Override
    public void izbrisiRegijo(Regija r) {
        if (!regije.contains(r)) {
            return;
        }

        r.setDnevniPodatki(null);
        regije.remove(r);

        log.info("Regija izbrisana");
    }

    @Override
    public List<Regija> vrniVseRegije() {
        return regije;
    }
    // Dnevni vnos

    @Override
    public void dodajDnevniVnos(DnevniPodatek novDnevniPodatek, Regija r) {

        for (DnevniPodatek dp : r.getDnevniPodatki()) {
            if (dp.getDatum().equals(novDnevniPodatek.getDatum())) {
                log.info("Dnevni podatek že obstaja");
                return;
            }
        }
        r.dodajDnevniPodatek(novDnevniPodatek);
    }

    @Override
    public List<DnevniPodatek> vrniVseDnevnePodatkeRegije(Regija r) {
        return r.getDnevniPodatki();
    }


    @Override
    public List<DnevniPodatek> vrniVseDnevnePodatkeNaDatum(Date d) {
        List<DnevniPodatek> dps = new ArrayList<DnevniPodatek>();
        for (Regija r : regije) {
            for (DnevniPodatek dp : r.getDnevniPodatki()) {
                if (dp.getDatum().equals(d)) dps.add(dp);
            }
        }
        return dps;
    }

    @Override

    public HashMap<String, DnevniPodatek> vrniVseDnevnePodatke() {
        HashMap<String, DnevniPodatek> dps = new HashMap<String, DnevniPodatek>();
        for (Regija r : regije) {
            for (DnevniPodatek dp : r.getDnevniPodatki()) {
                if (compareDate(dp.getDatum(), new Date())) {
                    dps.put(r.getNaziv(), dp);
                }
            }
        }
        return dps;
    }

    @Override
    public void izbrisiDnevniVnos(DnevniPodatek dp, Regija r) {
       r.getDnevniPodatki().remove(dp);
    }

    @Override
    public void spremeniDnevniVnos(Regija r, Date datum, int okuzeni, int hospitalizirani, int testirani) {

        int i = regije.indexOf(r);
        List<DnevniPodatek> dps = regije.get(i).getDnevniPodatki();
        for (DnevniPodatek dp : dps) {
            if (compareDate(dp.getDatum(), datum)) {
                dp.setOkuzeni(okuzeni);
                dp.setHospitalizirani(hospitalizirani);
                dp.setTestirani(testirani);
            }
        }
    }


    private boolean compareDate(Date d1, Date d2) {
        LocalDate ld1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ld2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (ld1.equals(ld2)) {
            return true;
        }
        return false;
    }

}
