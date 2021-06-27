package si.um.feri.aiv.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.aiv.dao.DnevniPodatekDao;
import si.um.feri.aiv.dao.RegijaDao;
import si.um.feri.aiv.vao.DnevniPodatek;
import si.um.feri.aiv.vao.Regija;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="mojrs")
@RequestScoped
public class MojRequestScopedBean {

    @EJB
    private RegijaDao rdao; // = RegijaDao2.getInstance();

    @EJB
    private DnevniPodatekDao dpdao; // = DnevniPodatekDao2.getInstance();

    Logger log = LoggerFactory.getLogger(MojRequestScopedBean.class);

    List<Regija> getVseRegijeRet = null;
    List<DnevniPodatek> getDnevniPodatekRet = null;

    public List<Regija> getVseRegije() {
        log.info("JSF BEAN: getVseOsebe!");
        if (getVseRegijeRet == null) {
            try {
                getVseRegijeRet = rdao.getRegije();
            } catch (Exception e) {
                getVseRegijeRet = new ArrayList<>();
            }
        }
        return getVseRegijeRet;
    }

}
