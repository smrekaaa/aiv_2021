package si.um.feri.aiv.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.dao.DnevniPodatekDao;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Stateless
@Local(DnevniPodatekDao.class)
public class DnevniPodatekDaoBean implements DnevniPodatekDao {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("osebe_demo");

    @PersistenceContext
    EntityManager em = emf.createEntityManager();

    Logger log = LoggerFactory.getLogger(DnevniPodatekDaoBean.class);

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
        HashMap<String, DnevniPodatek> dpMap = new HashMap<String, DnevniPodatek>();

        //Query q=em.createQuery("select dp from dnevni_podatek dp where dp.datum=:d");
        Query q=em.createQuery("select dp from dnevni_podatek dp"); //ni logično sploh da to deluje, samo deluje?????
        //q.setParameter("d", d);
        List<DnevniPodatek> dps = q.getResultList();

        for (DnevniPodatek o:dps) {
            String rNaziv = em.find(Regija.class, o.getRegijaId()).getNaziv();
            dpMap.put(rNaziv, o);
        }
        log.info("DP bean: getDnevnePodatkeNaDatum");
        return dpMap;
    }

    private String dateFormater(Calendar c) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        fmt.setCalendar(c);
        return fmt.format(c.getTime());
    }
}
