package si.um.feri.aiv.patterns.observers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.jsf.Mail;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

public class NovDnevniPodatekMail implements Opazovalec {

    Logger log= LoggerFactory.getLogger(NovDnevniPodatekMail.class);

    @Override
    public void akcija(Regija r) {

    }

    @Override
    public void akcija(DnevniPodatek dp, Regija r) {

        System.out.println("OPAZOVALEEEC MAIIIIL");
        String email_naslov =  r.getEmailSkrbnika();
        String vsebina = "Dodan nov dnevni podatek ze regijo:" + r.getNaziv()+ "\n" + dp.toString();
        String zadeva = "Nov dnevni podatek za regijo " + r.getNaziv();
        System.out.print("OPAZOVALEC: \n Na mail " + email_naslov + " je bilo poslano obvestilo");

        Mail mail = new Mail();
        mail.send(email_naslov, zadeva, vsebina);
    }
}
