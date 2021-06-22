package si.um.feri.aiv.ejb;

import si.um.feri.aiv.vao.DnevniPodatek;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface DnevniPodatki {

    DnevniPodatek najdi(ZonedDateTime datum);

    void shrani(DnevniPodatek dnevniPodatek);

    void izbrisiDnevniPodatek(DnevniPodatek dp);

    void SpremeniDnevniPodatek(int stOkuzenih, int stPrebolelih,
                               int stHospitaliziranih, Date datum);

    List<DnevniPodatek> vrniVseDnevnePodatke();
}
