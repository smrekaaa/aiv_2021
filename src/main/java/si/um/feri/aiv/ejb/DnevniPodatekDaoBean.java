package si.um.feri.aiv.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.FilterType;
import si.um.feri.aiv.dao.DnevniPodatekDao;
import si.um.feri.aiv.patterns.iterators.IteratorC;
import si.um.feri.aiv.patterns.iterators.IteratorDP;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Stateless
@Local(DnevniPodatekDao.class)
public class DnevniPodatekDaoBean implements DnevniPodatekDao {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("osebe_demo");

    @PersistenceContext
    EntityManager em = emf.createEntityManager();

    Logger log = LoggerFactory.getLogger(DnevniPodatekDaoBean.class);
    private HashMap<String, DnevniPodatek> dpMap;
    List<DnevniPodatek> dps = new ArrayList<>();

    @Override
    public void dodajDnevniPodatek(DnevniPodatek dp, Regija r) {

        if (dp == null) return;

        java.sql.Date d1 = new java.sql.Date(dp.getDatum().getTimeInMillis());

        Query q = em.createQuery("select dp from dnevni_podatek dp where dp.datum=:d AND dp.regijaId=:rid");
        q.setParameter("d", dp.getDatum());
        q.setParameter("rid", dp.getRegijaId());
        if (q.getResultList().size() > 0) return;

        em.persist(dp);
        r.obvestiVseOpazovalceZaNovDnevniPodatek(dp);
        log.info("DP bean: dodajDnevniPodatek");
    }

    @Override
    public void urediDnevniPodatek(DnevniPodatek dp) {
        em.merge(dp);

        Regija r = em.find(Regija.class, dp.getRegijaId());
        r.obvestiVseOpazovalceZaUrejenDnevniPodatek(dp);
        log.info("DP bean: urediDnevniPodatek");

    }

    @Override
    public void izbrisiDnevniPodatek(DnevniPodatek dp) {
        Query q=em.createQuery("select dp from dnevni_podatek dp where dp.id = :i");
        q.setParameter("i", dp.getId());
        DnevniPodatek delete = (DnevniPodatek) q.getSingleResult();
        em.remove(delete);
        log.info("DP bean: izbrisiDnevniPodatek");

    }

    @Override
    public DnevniPodatek najdiDnevniPodatek(int id, int r_id) {
        Query q=em.createQuery("select dp from dnevni_podatek dp where dp.id = :i");
        q.setParameter("i", id);
        return (DnevniPodatek) q.getSingleResult();
    }

    @Override
    public List<DnevniPodatek> getDnevnePodatkeRegije(int r_id) {
        Query q=em.createQuery("select dp from dnevni_podatek dp where dp.regijaId = :i");
        q.setParameter("i", r_id);;

        log.info("DP bean: getDnevnePodatkeRegije");
        return q.getResultList();
    }

    @Override
    public HashMap<String, DnevniPodatek> getDnevnePodatkeNaDatum(Calendar d) {
        java.sql.Date d1 = new java.sql.Date(d.getTimeInMillis());
        dpMap = new HashMap<String, DnevniPodatek>();

        //Query q=em.createQuery("select dp from dnevni_podatek dp where dp.datum=:d");
        Query q=em.createQuery("select dp from dnevni_podatek dp"); //ni logično sploh da to deluje, samo deluje?????
        //q.setParameter("d", d);
        dps = q.getResultList();

        for (DnevniPodatek o:dps) {
            String rNaziv = em.find(Regija.class, o.getRegijaId()).getNaziv();
            dpMap.put(rNaziv, o);
        }

        log.info("DP bean: getDnevnePodatkeNaDatum");
        return dpMap;
    }

    @Override
    public HashMap<String, DnevniPodatek> getFiltriraneDnevnePodatke(FilterType ft) {

        switch (ft) {
            case NORMAL:
                log.info("NORMAL");
                return dpMap;
            case MIN_POS:
                System.out.println("MIN_POS");
                HashMap<String, DnevniPodatek> k = getDnevnePodatkeMinPos();
                return k;
            case ZELENA:
                System.out.println("ZELENA");
                return  getDnevnePodatkeZelena();
            case RUMENA:
                System.out.println("RUMENA");
                return  getDnevnePodatkeRumena();
            case ORANZNA:
                System.out.println("ORANZNA");
                return  getDnevnePodatkeOranzna();
            case RDECA:
                System.out.println("RDECA");
                return  getDnevnePodatkeRdeca();
        }
        return null;
    }

    private HashMap<String, DnevniPodatek> getDnevnePodatkeMinPos() {

        HashMap<String, DnevniPodatek> m = new HashMap<String, DnevniPodatek>();
        IteratorDP i = new IteratorDP();
        i.nastavi(dps);
        Calendar now = Calendar.getInstance();

        IteratorC<DnevniPodatek> iter = i.vsiDnevniPodatki();
        while (!iter.jeKonec()) {
            try {
                DnevniPodatek dp = iter.naprej();
                Regija r = em.find(Regija.class, dp.getRegijaId());
                float v = ((float)dp.getOkuzeni()/(float)r.getStPrebivalcev());
                if (v <= 0.1 && compareDatesNoTime(dp.getDatum(),now)) {
                    m.put(r.getNaziv(), dp);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return m;
    }

    private HashMap<String, DnevniPodatek> getDnevnePodatkeZelena() {

        HashMap<String, DnevniPodatek> m = new HashMap<String, DnevniPodatek>();
        IteratorDP i = new IteratorDP();
        i.nastavi(dps);
        Calendar now = Calendar.getInstance();

        IteratorC<DnevniPodatek> iter = i.vsiDnevniPodatki();
        while (!iter.jeKonec()) {
            try {
                DnevniPodatek dp = iter.naprej();
                Regija r = em.find(Regija.class, dp.getRegijaId());
                if (dp.getOkuzeni() < 300 &&
                    compareDatesNoTime(dp.getDatum(),now)) {
                    m.put(r.getNaziv(), dp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return m;
    }

    private HashMap<String, DnevniPodatek> getDnevnePodatkeRumena() {

        HashMap<String, DnevniPodatek> m = new HashMap<String, DnevniPodatek>();
        IteratorDP i = new IteratorDP();
        i.nastavi(dps);
        Calendar now = Calendar.getInstance();

        IteratorC<DnevniPodatek> iter = i.vsiDnevniPodatki();
        while (!iter.jeKonec()) {
            try {
                DnevniPodatek dp = iter.naprej();
                Regija r = em.find(Regija.class, dp.getRegijaId());
                if (    (dp.getOkuzeni() < 600 && dp.getOkuzeni() >= 300) &&
                         dp.getHospitalizirani() < 500
                        && compareDatesNoTime(dp.getDatum(),now)) {
                    m.put(r.getNaziv(), dp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return m;
    }

    private HashMap<String, DnevniPodatek> getDnevnePodatkeOranzna() {

        HashMap<String, DnevniPodatek> m = new HashMap<String, DnevniPodatek>();
        IteratorDP i = new IteratorDP();
        i.nastavi(dps);
        Calendar now = Calendar.getInstance();

        IteratorC<DnevniPodatek> iter = i.vsiDnevniPodatki();
        while (!iter.jeKonec()) {
            try {
                DnevniPodatek dp = iter.naprej();
                Regija r = em.find(Regija.class, dp.getRegijaId());
                if (    (dp.getOkuzeni() < 1000 && dp.getOkuzeni() >= 600) &&
                        (dp.getHospitalizirani() < 1000 && dp.getHospitalizirani() >= 500)
                        && compareDatesNoTime(dp.getDatum(),now)) {
                    m.put(r.getNaziv(), dp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return m;
    }

    private HashMap<String, DnevniPodatek> getDnevnePodatkeRdeca() {

        HashMap<String, DnevniPodatek> m = new HashMap<String, DnevniPodatek>();
        IteratorDP i = new IteratorDP();
        i.nastavi(dps);
        Calendar now = Calendar.getInstance();

        IteratorC<DnevniPodatek> iter = i.vsiDnevniPodatki();
        while (!iter.jeKonec()) {
            try {
                DnevniPodatek dp = iter.naprej();
                Regija r = em.find(Regija.class, dp.getRegijaId());
                if (    dp.getOkuzeni() >= 1000 ||
                        dp.getHospitalizirani() >= 1000
                        && compareDatesNoTime(dp.getDatum(),now)) {
                    m.put(r.getNaziv(), dp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return m;
    }

    private String dateFormater(Calendar c) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        fmt.setCalendar(c);
        return fmt.format(c.getTime());
    }

    private boolean compareDatesNoTime (Calendar c1, Calendar c2) {
        if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
            return false;
        if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH))
            return false;
        if (c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH))
            return false;
        return true;
    }
}
