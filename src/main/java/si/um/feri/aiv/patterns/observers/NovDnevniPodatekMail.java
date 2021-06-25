package si.um.feri.aiv.patterns.observers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.jsf.Mail;
import si.um.feri.aiv.strategy.*;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

public class NovDnevniPodatekMail implements Opazovalec {

    Logger log= LoggerFactory.getLogger(NovDnevniPodatekMail.class);
    Strategija strategija = null;
    @Override
    public void akcija(Regija r) {

    }

    @Override
    public void akcija(DnevniPodatek dp, Regija r) {

        String email_naslov =  r.getEmailSkrbnika();
        System.out.print("OPAZOVALEC: \n Na mail " + email_naslov + " je bilo poslano obvestilo");
        String vsebina = "Dodan nov dnevni podatek ze regijo:" + r.getNaziv()+ "\n" + dp.toString();

        int hosp = dp.getHospitalizirani();
        int okuzeni = dp.getOkuzeni();

        if (okuzeni < 300) {
            strategija = new ZelenaFazaStrategija();
        } else if (okuzeni < 600 && hosp < 500 ) {
            strategija = new RumenaFazaStrategija();
        } else if (okuzeni < 1000 && hosp < 1000 ) {
            strategija = new OranznaFazaStrategija();
        } else {
            strategija = new RdecaFazaStrategija();
        }
        strategija.sendMail(email_naslov, vsebina);
    }
}
