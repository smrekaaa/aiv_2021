package si.um.feri.aiv.ejb;

import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.print.attribute.standard.DateTimeAtCompleted;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Stateless
@Local(DnevniPodatekDao.class)
public class DnevniPodatekDao extends Dao<DnevniPodatek> implements DnevniPodatki {

    public DnevniPodatekDao() throws SQLException {
        super("java:/PostgresDS",
                "CREATE TABLE  IF NOT EXISTS dnevni_podatek (\n" +
                        "\tdnevni_podatek_id SERIAL PRIMARY KEY,\n" +
                        "\tdatum TIMESTAMP UNIQUE NOT NULL,\n" +
                        "\thospitalizirani VARCHAR (90) NOT NULL,\n" +
                        "\ttestirani VARCHAR (90) NOT NULL,\n" +
                        "\tokuzeni VARCHAR (90) NOT NULL,\n" +
                        "\tregija_id INT NOT NULL,\n" +
                        "\tCONSTRAINT fk_regija\n" +
                        "   \t\tFOREIGN KEY(regija_id) \n" +
                        "      \t\tREFERENCES regija(regija_id)\n" +
                        ");");
    }

    private static DnevniPodatekDao inst;

    static {
        try {
            inst = new DnevniPodatekDao();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static DnevniPodatekDao getInstance() {
        return inst;
    }

    @Override
    public DnevniPodatek najdi(int id, Connection conn) throws Exception {
        log.info("DAO: iščem dnevni podatek -" + id);
        DnevniPodatek dp = null;
        PreparedStatement ps = conn.prepareStatement("select * from dnevni_podatek where dnevni_podatek_id=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int dnevni_podatek_id = rs.getInt("dnevni_podatek_id");
            int okuzeni = rs.getInt("okuzeni");
            int hospitalizirani = rs.getInt("hospitalizirani");
            int testirani = rs.getInt("testirani");
            int regijaId = rs.getInt("regija_id");
            long datum = rs.getTimestamp("datum").getTime();

            dp = new DnevniPodatek(dnevni_podatek_id, okuzeni, hospitalizirani, testirani, datum, regijaId);
            break;
        }
        return dp;
    }

    @Override
    public void shrani(DnevniPodatek obj, Connection conn) throws Exception {
        log.info("DAO: shranjujem/urejam dnevni Podatek");
        if (obj == null) return;

        Regija r = RegijaDao.getInstance().najdi(obj.getRegijaId());

        if (najdi(obj.getId()) != null) {
            PreparedStatement ps = conn.prepareStatement("update dnevni_podatek set okuzeni=? , hospitalizirani=? , testirani=? where dnevni_podatek_id=?");
            ps.setInt(1, obj.getOkuzeni());
            ps.setInt(2, obj.getHospitalizirani());
            ps.setInt(3, obj.getTestirani());
            ps.setInt(4, obj.getId());
            ps.executeUpdate();

            r.obvestiVseOpazovalceZaUrejenDnevniPodatek(obj);

        } else {
            PreparedStatement ps = conn.prepareStatement("insert into dnevni_podatek(okuzeni, hospitalizirani, testirani, datum, regija_id) values (?,?,?,?,?)");
            ps.setInt(1, obj.getOkuzeni());
            ps.setInt(2, obj.getHospitalizirani());
            ps.setInt(3, obj.getTestirani());
            ps.setTimestamp(4, new Timestamp(obj.getDatum().getTimeInMillis()));
            ps.setInt(5,obj.getRegijaId());
            ps.executeUpdate();

            r.obvestiVseOpazovalceZaNovDnevniPodatek(obj);

            ResultSet res = ps.getGeneratedKeys();
            while (res.next())
                obj.setId(res.getInt(1));
            res.close();
        }
    }

    @Override
    public List<DnevniPodatek> vrniVse(Connection conn) throws Exception {
        log.info("DAO: vracam vse dnevne podatke");

        List<DnevniPodatek> dps = new ArrayList<DnevniPodatek>();
        ResultSet rs = conn.createStatement().executeQuery("select * from dnevni_podatek");

        while (rs.next()) {
            int dnevni_podatek_id = rs.getInt("dnevni_podatek_id");
            int okuzeni = rs.getInt("okuzeni");
            int hospitalizirani = rs.getInt("hospitalizirani");
            int testirani = rs.getInt("testirani");
            long datum = rs.getTimestamp("datum").getTime();
            int regijaId = rs.getInt("regija_id");

            DnevniPodatek dp = new DnevniPodatek(dnevni_podatek_id, okuzeni, hospitalizirani, testirani,  datum, regijaId);
            dps.add(dp);

            log.info("DP dodan v bazo");
        }
        rs.close();

        return dps;
    }

    @Override
    public HashMap<String, DnevniPodatek> vrniVseNaDatum(GregorianCalendar d) throws Exception {
        log.info("DAO: vracam vse dnevne podatke na datum: " + d.toString());
        Connection conn = super.getBaza().getConnection();

        java.sql.Date d1 = new java.sql.Date(d.getTimeInMillis());
        HashMap<String, DnevniPodatek> dps = new HashMap<String, DnevniPodatek>();

        PreparedStatement ps = conn.prepareStatement("select * from dnevni_podatek d inner join regija r on r.regija_id = d.regija_id where date(d.datum)=?");
        ps.setDate(1, d1);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int dnevni_podatek_id = rs.getInt("dnevni_podatek_id");
            int okuzeni = rs.getInt("okuzeni");
            int hospitalizirani = rs.getInt("hospitalizirani");
            int testirani = rs.getInt("testirani");
            long datum = rs.getTimestamp("datum").getTime();
            datum = datum + (24*3600);
            int regijaId = rs.getInt("regija_id");
            String regijaNaziv = rs.getString("naziv");

            DnevniPodatek dp = new DnevniPodatek(dnevni_podatek_id, okuzeni, hospitalizirani, testirani, datum, regijaId);
            dps.put(regijaNaziv, dp);

        }
        rs.close();
        conn.close();

        return dps;
    }

    @Override
    public List<DnevniPodatek> vrniVseRegije(int regijaId) throws Exception {
        log.info("DAO: vracam vse dnevne podatke regije");
        Connection conn = super.getBaza().getConnection();

        List<DnevniPodatek> dps = new ArrayList<DnevniPodatek>();

        PreparedStatement ps = conn.prepareStatement("select * from dnevni_podatek where regija_id=?");
        ps.setInt(1, regijaId );
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int dnevni_podatek_id = rs.getInt("dnevni_podatek_id");
            int okuzeni = rs.getInt("okuzeni");
            int hospitalizirani = rs.getInt("hospitalizirani");
            int testirani = rs.getInt("testirani");
            long datum = rs.getTimestamp("datum").getTime();

            DnevniPodatek dp = new DnevniPodatek(dnevni_podatek_id, okuzeni, hospitalizirani, testirani, datum, regijaId);
            dps.add(dp);
        }
        rs.close();
        conn.close();

        return dps;
    }

    @Override
    public void izbrisi(int id, Connection conn) throws Exception {
        log.info("DAO: iščem dp-" + id);
        PreparedStatement ps = conn.prepareStatement("delete from dnevni_podatek where dnevni_podatek_id=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.close();
    }
}
