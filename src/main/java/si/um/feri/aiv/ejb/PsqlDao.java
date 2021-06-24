package si.um.feri.aiv.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.jsf.MojBean;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class PsqlDao implements Regije{

    Logger log= LoggerFactory.getLogger(PsqlDao.class);
    // private static List<Regija> regije = Collections.synchronizedList(new ArrayList<Regija>());
    DataSource db;

    public PsqlDao() {
        try {
            db=(DataSource)new InitialContext().lookup("java:/PostgresDS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // REGIJA

    @Override
    public void shrani(Regija nr) throws SQLException {
        log.info("DAO: shranjujem regijo");
        Connection conn=null;

        try {
            conn = db.getConnection();

            if (najdiNaziv(nr.getNaziv()) != null) {
                PreparedStatement ps = conn.prepareStatement("update regija set naziv=? , ime_skrbnika=? , priimek_skrbnika=? , email_skrbnika=? , st_prebivalcev=? where regija_id=?");
                ps.setString(1, nr.getNaziv());
                ps.setString(2, nr.getImeSkrbnika());
                ps.setString(3, nr.getPriimekSkrbnika());
                ps.setString(4, nr.getEmailSkrbnika());
                ps.setInt(5, nr.getStPrebivalcev());
                ps.setInt(6, nr.getId());
                ps.executeUpdate();
            } else {
                PreparedStatement ps = conn.prepareStatement("insert into regija(naziv, ime_skrbnika, priimek_skrbnika, email_skrbnika, st_prebivalcev) values (?,?,?,?,?)");
                ps.setString(1, nr.getNaziv());
                ps.setString(2, nr.getImeSkrbnika());
                ps.setString(3, nr.getPriimekSkrbnika());
                ps.setString(4, nr.getEmailSkrbnika());
                ps.setInt(5, nr.getStPrebivalcev());
                ps.executeUpdate();

                ResultSet res = ps.getGeneratedKeys();
                while (res.next())
                    nr.setId(res.getInt(1));
                res.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }

    }

    @Override
    public Regija najdiNaziv(String naziv) throws SQLException {
        log.info("DAO: iščem " + naziv);

        Connection conn = null;
        Regija rr = null;

        try {
            conn =  db.getConnection();

            PreparedStatement ps = conn.prepareStatement("select * from regija where naziv=?");
            ps.setString(1, naziv);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rr = new Regija(
                        rs.getInt("regija_id"),
                        naziv,
                        rs.getString("ime_skrbnika"),
                        rs.getString("priimek_skrbnika"),
                        rs.getString("email_skrbnika"),
                        rs.getInt("st_prebivalcev"));
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return rr;
    }


    @Override
    public List<Regija> vrniVseRegije() throws Exception{
        log.info("DAO: vracam vse regije");

        List<Regija> regije = new ArrayList<>();
        String sql = "SELECT * FROM regija;";
        Connection conn = null;

        try {
            conn = db.getConnection();

            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("regija_id");
                String naziv = rs.getString("naziv");
                String imeSkrbnika = rs.getString("ime_skrbnika");
                String priimekSkrbnika = rs.getString("priimek_skrbnika");
                String emailSkrbnika = rs.getString("email_skrbnika");
                int stPrebivalcev = rs.getInt("st_prebivalcev");

                Regija r = new Regija(id, naziv, imeSkrbnika, priimekSkrbnika, emailSkrbnika, stPrebivalcev);
                regije.add(r);

                log.info("Regija dodana v bazo");
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return regije;
    }

    @Override
    public void dodajDnevniVnos(DnevniPodatek novDnevniPodatek, Regija r) {
     /*   try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }*/
    }

/*
    @Override
    public void spremeniRegijo(String naziv, String imeSkrbnika, String PriimekSkrbnika, String email, int stPrebivalcev) {

    }*/

    @Override
    public void izbrisiRegijo(Regija regija) {

    }

    @Override
    public List<DnevniPodatek> vrniVseDnevnePodatkeRegije(Regija r) {
        return null;
    }

    @Override
    public List<DnevniPodatek> vrniVseDnevnePodatkeNaDatum(GregorianCalendar d) {
        return null;
    }

    @Override
    public HashMap<String, DnevniPodatek> vrniVseDnevnePodatke() {
        return null;
    }

    @Override
    public void izbrisiDnevniVnos(DnevniPodatek dp, Regija r) {

    }

    @Override
    public void spremeniDnevniVnos(Regija r, Date datum, int okuzeni, int hospitalizirani, int testirani) {

    }
}
