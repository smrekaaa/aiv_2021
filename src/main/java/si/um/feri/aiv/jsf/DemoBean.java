package si.um.feri.aiv.jsf;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.ejb.Osebe;
import si.um.feri.aiv.ejb.OsebeDao;
import si.um.feri.aiv.vao.Kontakt;
import si.um.feri.aiv.vao.Oseba;

@Named("demo")
@SessionScoped
public class DemoBean implements Serializable {

//	private static final long serialVersionUID = -8979220536758073133L;

	Logger log=LoggerFactory.getLogger(DemoBean.class);

	private Osebe dao=new OsebeDao();
	
	private Oseba novaOseba=new Oseba();

	private Oseba izbranaOseba=new Oseba();
	
	private String izbranEmail;
	
	private String novKontaktTipKontakta;
	
	private String novKontakt;
	
	public List<Oseba> getVseOsebe() {
		log.info("getVseOsebe");
		return dao.vrniVse();
	}
	
	public void setIzbranEmail(String email) {
		log.info("setIzbranEmail");
		izbranEmail=email;
		izbranaOseba=dao.najdi(email);
	}
	
	public String getIzbranEmail() {
		return izbranEmail;
	}

	public void dodajOsebo() {
		log.info("dodajOsebo");
		dao.shrani(novaOseba);
		novaOseba=new Oseba();
	}
	
	public void dodajKontakt() {
		log.info("dodajKontakt");
		izbranaOseba=dao.dodajKontakt(new Kontakt(novKontaktTipKontakta,novKontakt), izbranaOseba);
		novKontaktTipKontakta="";
		novKontakt="";
	}

	public Oseba getNovaOseba() {
		return novaOseba;
	}
	
	public void setNovaOseba(Oseba novaOseba) {
		this.novaOseba = novaOseba;
	}

	public Oseba getIzbranaOseba() {
		return izbranaOseba;
	}

	public void setIzbranaOseba(Oseba izbranaOseba) {
		this.izbranaOseba = izbranaOseba;
	}

	public String getNovKontaktTipKontakta() {
		return novKontaktTipKontakta;
	}

	public void setNovKontaktTipKontakta(String novKontaktTipKontakta) {
		this.novKontaktTipKontakta = novKontaktTipKontakta;
	}

	public String getNovKontakt() {
		return novKontakt;
	}

	public void setNovKontakt(String novKontakt) {
		this.novKontakt = novKontakt;
	}
	
}
