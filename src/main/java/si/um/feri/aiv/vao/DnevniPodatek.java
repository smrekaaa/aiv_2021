package si.um.feri.aiv.vao;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DnevniPodatek implements Serializable, Cloneable {

    public DnevniPodatek(int okuzeni, int hospitalizirani, int testirani, GregorianCalendar datum) {
        this.okuzeni = okuzeni;
        this.hospitalizirani = hospitalizirani;
        this.testirani = testirani;
        this.datum = datum;
    }

    public DnevniPodatek(int id, int okuzeni, int hospitalizirani, int testirani, long datum, int regijaId) {
        this.id = id;
        this.okuzeni = okuzeni;
        this.hospitalizirani = hospitalizirani;
        this.testirani = testirani;
        this.datum = new GregorianCalendar();
        this.datum.setTimeInMillis(datum);
        this.regijaId = regijaId;
    }

    public DnevniPodatek(){
        this.datum = new GregorianCalendar();
    };

    private int id;

    private int okuzeni;

    private int hospitalizirani;

    private int testirani;

    private Calendar datum;

    private int regijaId;

    // Setters & Getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOkuzeni() {
        return okuzeni;
    }

    public void setOkuzeni(int okuzeni) {
        this.okuzeni = okuzeni;
    }

    public int getHospitalizirani() {
        return hospitalizirani;
    }

    public void setHospitalizirani(int hospitalizirani) {
        this.hospitalizirani = hospitalizirani;
    }

    public int getTestirani() {
        return testirani;
    }

    public void setTestirani(int testirani) {
        this.testirani = testirani;
    }

    public Calendar getDatum() {
        return datum;
    }

    public void setDatum(GregorianCalendar datum) {
        this.datum = datum;
    }

    public int getRegijaId() {
        return regijaId;
    }

    public void setRegijaId(int regijaId) {
        this.regijaId = regijaId;
    }

    private static SimpleDateFormat sdf=new SimpleDateFormat("dd. MM. yyyy HH:mm:ss");

    // toString

    @Override
    public String toString() {
        sdf.setTimeZone(this.datum.getTimeZone());
        return "DnevniPodatek{" +
                "id=" + id +
                ", okuzeni=" + okuzeni +
                ", hospitalizirani=" + hospitalizirani +
                ", testirani=" + testirani +
                ", datum=" + sdf.format(this.datum.getTime()) +
                ", regijaId=" + regijaId +
                '}';
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
