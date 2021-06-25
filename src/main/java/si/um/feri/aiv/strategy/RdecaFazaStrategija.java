package si.um.feri.aiv.strategy;

import si.um.feri.aiv.jsf.Mail;

public class RdecaFazaStrategija implements Strategija{

    @Override
    public void sendMail(String email, String content) {
        Mail mail = new Mail();
        content = "Dodan je nov dnevni podatek s podatki za prehod v rdeco fazo:\n \n "+ content;
        mail.send(email, "Nov podatek - Rdeca faza", content);
    }
}
