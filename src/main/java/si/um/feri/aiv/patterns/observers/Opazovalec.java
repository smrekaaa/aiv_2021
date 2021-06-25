package si.um.feri.aiv.patterns.observers;

import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

public interface Opazovalec {

    void akcija(Regija r);

    void akcija(DnevniPodatek dp, Regija r);
}
