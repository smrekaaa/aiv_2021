package si.um.feri.aiv.ejb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.vao.Kontakt;
import si.um.feri.aiv.vao.Oseba;

public class OsebeDao implements Osebe {

	Logger log=LoggerFactory.getLogger(OsebeDao.class);
	
	private static List<Oseba> osebe=Collections.synchronizedList(new ArrayList<Oseba>());
	
	@Override
	public void shrani(Oseba o)  {
		log.info("DAO: shranjujem "+o);
		osebe.add(o);
	}

	@Override
	public List<Oseba> vrniVse() {
		log.info("DAO: vracam vse");
		return osebe;
	}
	
	@Override
	public Oseba dodajKontakt(Kontakt k, Oseba o) {
		Oseba najdena=najdi(o.getId());
		najdena.getKontakti().add(k);
		return najdena;
	}
	
	@Override
	public Oseba najdi(int id) {
		for (Oseba o : osebe)
			if (o.getId()==id)
				return o;
		return new Oseba();
	}
	
	@Override
	public Oseba najdi(String email)  {
		for (Oseba o : osebe)
			if (o.getEmail().equals(email))
				return o;
		return new Oseba();
	}

}