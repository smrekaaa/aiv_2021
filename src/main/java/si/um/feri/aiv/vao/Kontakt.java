package si.um.feri.aiv.vao;

public class Kontakt {
	
	public Kontakt() {
		this("neznan","Ob obali 3");
	}
	
	public Kontakt(String tip, String naziv) {
		super();
		this.id=next_id++;
		this.tip = tip;
		this.naziv = naziv;
	}

	private static int next_id=0;
	
	private int id;
	
	private String tip;
	
	private String naziv;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
}
