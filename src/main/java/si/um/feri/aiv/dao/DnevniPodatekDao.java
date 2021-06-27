package si.um.feri.aiv.dao;

import si.um.feri.aiv.FilterType;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import javax.ejb.Local;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Local
public interface DnevniPodatekDao {

    public void dodajDnevniPodatek(DnevniPodatek dp, Regija r);

    public void urediDnevniPodatek(DnevniPodatek dp);

    public void izbrisiDnevniPodatek(DnevniPodatek dp);

    public DnevniPodatek najdiDnevniPodatek(int id, int r_id);

    public List<DnevniPodatek> getDnevnePodatkeRegije(int r_id);

    public HashMap<String, DnevniPodatek> getDnevnePodatkeNaDatum(Calendar d);

    public HashMap<String, DnevniPodatek> getFiltriraneDnevnePodatke(FilterType ft);


}
