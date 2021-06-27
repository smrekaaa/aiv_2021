package si.um.feri.aiv.patterns.iterators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.vao.DnevniPodatek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IteratorDP {

    Logger log = LoggerFactory.getLogger(IteratorDP.class);

    private List<DnevniPodatek> list=new ArrayList<>();
    public void dodaj(DnevniPodatek dp) {
        list.add(dp);
    }

    public void nastavi(List<DnevniPodatek> l) {
        list = l;
    }

    public IteratorC<DnevniPodatek> vsiDnevniPodatki() {
        IteratorC<DnevniPodatek> ret = new IteratorC<DnevniPodatek>() {


            private List<DnevniPodatek> c = new ArrayList<>(list);
            private int curr=0;

            @Override
            public DnevniPodatek naprej() throws Exception{
                log.info("Naslednji, prosim.");
                if (jeKonec()) throw new Exception();
                return c.get(curr++);
            }

            @Override
            public boolean jeKonec() {
                if (curr<c.size()) {
                    log.info("Nismo še na koncu.");
                    return false;
                }
                log.info("Konec imenika.");
                return true;
            }
        };
        return ret;
    }
}
