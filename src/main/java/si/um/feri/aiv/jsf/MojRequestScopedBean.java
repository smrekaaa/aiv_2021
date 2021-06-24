package si.um.feri.aiv.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.ejb.DnevniPodatekDao;
import si.um.feri.aiv.ejb.PsqlDao;
import si.um.feri.aiv.ejb.RegijaDao;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="mojrs")
@RequestScoped
public class MojRequestScopedBean {

    Logger log = LoggerFactory.getLogger(MojRequestScopedBean.class);

    List<Regija> getVseRegijeRet = null;
    List<DnevniPodatek> getDnevniPodatekRet = null;

    public List<Regija> getVseRegije() {
        log.info("JSF BEAN: getVseOsebe!");
        if (getVseRegijeRet == null) {
            try {
                getVseRegijeRet = RegijaDao.getInstance().vrniVse();
            } catch (Exception e) {
                getVseRegijeRet = new ArrayList<>();
            }
        }
        return getVseRegijeRet;
    }

    public List<DnevniPodatek> getVseDanasnjeDnevneVnose() {
        log.info("JSF BEAN: getVseDanasnjeDnevneVnose!");
        if (getDnevniPodatekRet == null) {
            try {
                getDnevniPodatekRet = DnevniPodatekDao.getInstance().vrniVse();
            } catch (Exception e) {
                getDnevniPodatekRet = new ArrayList<>();
            }
        }
        return getDnevniPodatekRet;
    }

}
