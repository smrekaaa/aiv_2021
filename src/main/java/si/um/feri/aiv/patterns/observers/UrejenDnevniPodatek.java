package si.um.feri.aiv.patterns.observers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

public class UrejenDnevniPodatek implements Opazovalec{

    Logger log= LoggerFactory.getLogger(UrejenDnevniPodatek.class);

    @Override
    public void akcija(Regija r) {

    }

    @Override
    public void akcija(DnevniPodatek dp, Regija r) {
        System.out.println("OPAZOVALEC:\nUstvarjene spremembe za dnevni podatek izbrano regije:\n");
        System.out.println(r.toString() + "\n");
        System.out.println(dp.toString());
    }
}
