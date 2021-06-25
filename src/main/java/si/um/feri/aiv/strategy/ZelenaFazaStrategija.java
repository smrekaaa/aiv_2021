package si.um.feri.aiv.strategy;

import si.um.feri.aiv.jsf.Mail;

public class ZelenaFazaStrategija implements Strategija{

    @Override
    public void sendMail(String email, String content) {

        Mail mail = new Mail();
        content = "Dodan je nov dnevni podatek s podatki za zeleno fazo:\n \n "+ content;
        mail.send(email, "Nov podatek - Zelena faza", content);
    }
}

