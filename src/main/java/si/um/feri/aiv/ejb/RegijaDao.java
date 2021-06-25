package si.um.feri.aiv.ejb;

import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local(RegijaDao.class)
public class RegijaDao extends Dao<Regija> implements Regije{

    public RegijaDao() {
        super("java:/PostgresDS",
                "create table if not exists regija(regija_id serial primary key,naziv varchar (90) unique not null,ime_skrbnika varchar (90) not null,priimek_skrbnika varchar (90) not null,email_skrbnika varchar (90) unique not null,st_prebivalcev int not null);");
    }

    private static RegijaDao inst = new RegijaDao();

    public static RegijaDao getInstance() {
        return inst;
    }

    @Override
    public Regija najdi(int id, Connection conn) throws Exception {
        log.info("DAO: iščem regijo-" + id);
        Regija rr = null;
        PreparedStatement ps = conn.prepareStatement("select * from regija where regija_id=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int regija_id = rs.getInt("regija_id");
            String naziv = rs.getString("naziv");
            String imeSkrbnika = rs.getString("ime_skrbnika");
            String priimekSkrbnika = rs.getString("priimek_skrbnika");
            String emailSkrbnika = rs.getString("email_skrbnika");
            int stPrebivalcev = rs.getInt("st_prebivalcev");

            rr = new Regija(regija_id, naziv, imeSkrbnika, priimekSkrbnika, emailSkrbnika, stPrebivalcev);
            break;
        }
        return rr;
    }

    @Override
    public void shrani(Regija obj, Connection conn) throws Exception {
        log.info("DAO: shranjujem regijo");
        if (obj == null) return;

        if (najdi(obj.getId()) != null) {
            PreparedStatement ps = conn.prepareStatement("update regija set naziv=? , ime_skrbnika=? , priimek_skrbnika=? , email_skrbnika=? , st_prebivalcev=? where regija_id=?");
            ps.setString(1, obj.getNaziv());
            ps.setString(2, obj.getImeSkrbnika());
            ps.setString(3, obj.getPriimekSkrbnika());
            ps.setString(4, obj.getEmailSkrbnika());
            ps.setInt(5, obj.getStPrebivalcev());
            ps.setInt(6, obj.getId());
            ps.executeUpdate();

            obj.obvestiVseOpazovalceZaUrejenoRegijo();
        } else {
            PreparedStatement ps = conn.prepareStatement("insert into regija(naziv, ime_skrbnika, priimek_skrbnika, email_skrbnika, st_prebivalcev) values (?,?,?,?,?)");
            ps.setString(1, obj.getNaziv());
            ps.setString(2, obj.getImeSkrbnika());
            ps.setString(3, obj.getPriimekSkrbnika());
            ps.setString(4, obj.getEmailSkrbnika());
            ps.setInt(5, obj.getStPrebivalcev());
            ps.executeUpdate();

            ResultSet res = ps.getGeneratedKeys();
            while (res.next())
                obj.setId(res.getInt(1));
            res.close();
        }

    }

    @Override
    public List<Regija> vrniVse(Connection conn) throws Exception {
        log.info("DAO: vracam vse regije");

        List<Regija> regije = new ArrayList<Regija>();
        ResultSet rs = conn.createStatement().executeQuery("select * from regija");

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

        return regije;
    }

    @Override
    public void izbrisi(int id, Connection conn) throws Exception {
        log.info("DAO: iščem regijo-" + id);
            PreparedStatement ps = conn.prepareStatement("delete from regija where regija_id=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        rs.close();
    }

}
