package si.um.feri.aiv.ejb;

import si.um.feri.aiv.vao.DnevniPodatek;

import javax.ejb.Local;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

@Local
public interface DnevniPodatki {

    public HashMap<String, DnevniPodatek> vrniVseNaDatum(GregorianCalendar d) throws Exception ;

    public List<DnevniPodatek> vrniVseRegije(int regijaId) throws Exception;
}
