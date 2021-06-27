package si.um.feri.aiv.patterns.iterators;

import java.util.Iterator;
import java.util.function.Consumer;

public interface IteratorC<E> {

    public E naprej() throws Exception;
    boolean jeKonec();
}
