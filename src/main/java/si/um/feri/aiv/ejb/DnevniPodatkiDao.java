package si.um.feri.aiv.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.vao.DnevniPodatek;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DnevniPodatkiDao implements DnevniPodatki {

    Logger log= LoggerFactory.getLogger(DnevniPodatkiDao.class);

    private static List<DnevniPodatek> dnevniPodatki = Collections.synchronizedList(new ArrayList<DnevniPodatek>());


    @Override
    public DnevniPodatek najdi(ZonedDateTime datum) {
        for (DnevniPodatek dp: dnevniPodatki) {
            if (dp.getDatum().equals(datum)) {
                return dp;
            }
        }
        return null;
    }

    @Override
    public void shrani(DnevniPodatek dnevniPodatek) {
        if (dnevniPodatki.contains(dnevniPodatek)) return;
        dnevniPodatki.add(dnevniPodatek);
    }

    @Override
    public void izbrisiDnevniPodatek(DnevniPodatek dp) {
        if (!dnevniPodatki.contains(dp)) return;
        dnevniPodatki.remove(dp);
    }

    @Override
    public void SpremeniDnevniPodatek(int stOkuzenih, int stPrebolelih, int stHospitaliziranih, Date datum) {

    }

    @Override
    public List<DnevniPodatek> vrniVseDnevnePodatke() {
        return dnevniPodatki;
    }
}
