package si.um.feri.aiv.strategy;

import si.um.feri.aiv.jsf.Mail;

public class RumenaFazaStrategija implements Strategija{

    @Override
    public void sendMail(String email, String content) {

        Mail mail = new Mail();
        content = "Dodan je nov dnevni podatek s podatki za prehod v rumeno fazo:\n \n "+ content;
        mail.send(email, "Nov podatek - Rumena faza", content);
    }
}

