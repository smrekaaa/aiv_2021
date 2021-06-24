package si.um.feri.aiv.ejb;

import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public interface Regije {

    void dodajDnevniVnos(DnevniPodatek novDnevniPodatek, Regija r);

    Regija najdiNaziv(String naziv) throws SQLException;

    // Shrani ali posodobi
    void shrani(Regija novaRegija) throws SQLException;

    //void spremeniRegijo(String naziv, String imeSkrbnika,
     //                   String PriimekSkrbnika, String email, int stPrebivalcev);

    void izbrisiRegijo(Regija regija);

    List<Regija> vrniVseRegije() throws Exception;

    List<DnevniPodatek> vrniVseDnevnePodatkeRegije(Regija r);

    List<DnevniPodatek> vrniVseDnevnePodatkeNaDatum(GregorianCalendar d);

    HashMap<String, DnevniPodatek> vrniVseDnevnePodatke();

    void izbrisiDnevniVnos (DnevniPodatek dp, Regija r);

    void spremeniDnevniVnos(Regija r, Date datum, int okuzeni, int hospitalizirani, int testirani);

}
