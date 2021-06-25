package si.um.feri.aiv.patterns.observers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

public class UrejenaRegija implements Opazovalec{

    Logger log= LoggerFactory.getLogger(UrejenaRegija.class);

    @Override
    public void akcija(Regija r) {
        System.out.println("OPAZOVALEC:\nUstvarjenanova regija:\n");
        System.out.println(r.toString() + "\n");
    }

    @Override
    public void akcija(DnevniPodatek dp, Regija r) {

    }
}
