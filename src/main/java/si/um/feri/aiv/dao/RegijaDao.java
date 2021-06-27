package si.um.feri.aiv.dao;

import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

@Local
public interface RegijaDao {

    public void dodajRegijo(Regija r);

    public void urediRegijo(Regija r);

    public void izbrisiRegijo(Regija r);

    public Regija najdiRegijo(int id);

    public List<Regija> getRegije();

    public Regija dodajDnevniPodatek(Regija r, DnevniPodatek dp);
}
