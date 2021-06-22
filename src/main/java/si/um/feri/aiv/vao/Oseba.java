package si.um.feri.aiv.vao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Oseba {

	public Oseba() {
		this("", "","");
	}
	
	public Oseba(String ime, String priimek, String email) {
		this.id=next_id++;
		this.email = email;
		this.ime = ime;
		this.priimek = priimek;
		datumVpisa=new GregorianCalendar();
		kontakti=new ArrayList<Kontakt>();
	}

	private static int next_id=0;

	private int id;
	
	private String ime;

	private String priimek;
	
	private String email;

	private Calendar datumVpisa;
	
	private List<Kontakt> kontakti;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Kontakt> getKontakti() {
		return kontakti;
	}

	public void setKontakti(List<Kontakt> kontakti) {
		this.kontakti = kontakti;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPriimek() {
		return priimek;
	}

	public void setPriimek(String priimek) {
		this.priimek = priimek;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getDatumVpisa() {
		return datumVpisa;
	}

	public void setDatumVpisa(Calendar datumVpisa) {
		this.datumVpisa = datumVpisa;
	}
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("dd. MM. yyyy HH:mm:ss");

	@Override
	public String toString() {
		return id+": "+ime + " " + priimek + " ("+email+"); vpis: "+sdf.format(datumVpisa.getTime());
	}
	
}