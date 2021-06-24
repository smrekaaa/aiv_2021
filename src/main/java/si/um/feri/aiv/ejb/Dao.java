package si.um.feri.aiv.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class Dao<T> {

    protected Logger log= LoggerFactory.getLogger(Dao.class);

    private String jndi;

    protected DataSource getBaza() throws Exception {
        return (DataSource)new InitialContext().lookup(jndi);
    }

    protected Dao(String jndi, String t) {
        log.info("Nov Dao: " + jndi);
        this.jndi = jndi;
        try {
            kreirajTabele(t);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    private void kreirajTabele(String createTable) throws SQLException {
        log.info(createTable);
        Connection conn = null;
        try {
            conn = getBaza().getConnection();
            conn.createStatement().execute(createTable);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    public T najdi (int id) throws Exception {
        Connection conn = null;
        T ret = null;

        try {
            conn = getBaza().getConnection();
            ret = najdi (id, conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return ret;
    }

    public abstract T najdi(int id, Connection conn) throws Exception;

    public void shrani(T obj) throws Exception {
        log.info("Shranjujem "+obj);
        Connection conn= null;
        try {
            conn = getBaza().getConnection();
            shrani(obj, conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    public abstract void shrani(T obj, Connection conn) throws Exception;

    public List<T> vrniVse() throws Exception {
        Connection conn = null;
        List<T> ret = null;
        try {
            conn = getBaza().getConnection();
            ret = vrniVse(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return ret;
    }

    public abstract List<T> vrniVse(Connection conn) throws Exception;

    public void izbrisi (int id) throws Exception {
        Connection conn = null;

        try {
            conn = getBaza().getConnection();
            izbrisi (id, conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    public abstract void izbrisi(int id, Connection conn) throws Exception;



}
