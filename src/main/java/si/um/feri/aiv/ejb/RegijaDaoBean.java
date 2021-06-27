package si.um.feri.aiv.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.dao.RegijaDao;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
@Local(RegijaDao.class)
public class RegijaDaoBean implements RegijaDao {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("osebe_demo");

    @PersistenceContext
    EntityManager em = emf.createEntityManager();

    Logger log = LoggerFactory.getLogger(RegijaDaoBean.class);

    @Override
    public void dodajRegijo(Regija r) {
        log.info("Dodaj regijo:" + r.toString());
        if (r == null) return;
        System.out.println(r.toString());
        if (em.find(Regija.class, r.getRegijaId()) != null) return;
        em.persist(r);
    }

    @Override
    public void urediRegijo(Regija r) {
        em.merge(r);
        r.obvestiVseOpazovalceZaUrejenoRegijo();
        log.info("R bean: urediRegijo");
    }

    @Override
    public void izbrisiRegijo(Regija r) {
        Query q=em.createQuery("select r from regija r where r.regijaId = :i");
        q.setParameter("i", r.getRegijaId());
        Regija delete = (Regija) q.getSingleResult();
        em.remove(delete);
        log.info("R bean: izbrisiRegijo");
    }

    @Override
    public Regija najdiRegijo(int id) {
        Query q=em.createQuery("select r from regija r where r.regijaId = :i");
        q.setParameter("i", id);
        return (Regija) q.getSingleResult();
    }

    @Override
    public List<Regija> getRegije() {
        Query q=em.createQuery("select r from regija r");
        log.info("R bean: getRegije");
        return (List<Regija>) q.getResultList();
    }


    @Override
    public Regija dodajDnevniPodatek(Regija r, DnevniPodatek dp) {
        return null;
    }
}
