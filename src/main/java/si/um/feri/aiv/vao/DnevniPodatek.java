package si.um.feri.aiv.vao;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class DnevniPodatek {

    public DnevniPodatek(int okuzeni, int hospitalizirani, int testirani, Date datum) {
        this.okuzeni = okuzeni;
        this.hospitalizirani = hospitalizirani;
        this.testirani = testirani;
        this.datum = datum;
    }

    public DnevniPodatek(){};

    private int okuzeni;

    private int hospitalizirani;

    private int testirani;

    private Date datum;

    // Setters & Getters

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

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    // Other methods
//    public Date reformatDate(Date d) {
//        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy hh:mm");
//        return formater.parse(d);
//    }

}
