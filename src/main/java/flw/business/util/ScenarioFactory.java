/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import flw.business.business.Service;
import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresState;
import flw.business.statistics.ArticleAndOrderStatistics;
import flw.business.statistics.ConveyorStatistics;
import flw.business.statistics.EmployeeStatistics;
import flw.business.statistics.PalletStatistics;
import flw.business.statistics.StateStatistics;
import flw.business.store.ArticleDynamics;
import flw.business.store.ConveyorDynamics;
import flw.business.store.CustomerJobDynamics;
import flw.business.store.Employee;
import flw.business.store.EmployeeDynamics;
import flw.business.store.EmployeeFactory;
import flw.business.store.OrderDynamics;
import flw.business.store.PalletsInProgress;
import flw.business.store.StockGround;
import flw.business.store.Storage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author tilu
 */
public class ScenarioFactory {

    private volatile static ScenarioFactory singleton;
    private List<DoloresState> states;
    @EJB
    private Service service;

    private ScenarioFactory() throws IOException {
        states = new ArrayList<DoloresState>();
        //fillStates();

    }

    public static ScenarioFactory getInstance() throws IOException {
        if (singleton == null) {
            singleton = new ScenarioFactory();
        }
        return singleton;
    }



    public DoloresGameInfo createHistory(DoloresGameInfo info) {

        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        //xstream.setMode(XStream.NO_REFERENCES);
        xstream.autodetectAnnotations(true);

        for (int i = 1; i < 11; i++) {
            ArticleAndOrderStatistics aaos = (ArticleAndOrderStatistics) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/Statistics/ArticleAndOrderStatistics_" + i + ".json"));
            info.getArticleAndOrderStatisticses().add(aaos);
            aaos.setGameInfo(info);

            ConveyorStatistics cs = (ConveyorStatistics) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/Statistics/ConveyorStatistics_" + i + ".json"));
            info.getConveyorStatisticses().add(cs);
            cs.setGameInfo(info);

            EmployeeStatistics es = (EmployeeStatistics) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/Statistics/EmployeeStatistics_" + i + ".json"));
            info.getEmployeeStatisticses().add(es);
            es.setGameInfo(info);

            PalletStatistics ps = (PalletStatistics) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/Statistics/PalletStatistics_" + i + ".json"));
            info.getPalletStatisticses().add(ps);
            ps.setGameInfo(info);

            StateStatistics ss = (StateStatistics) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/Statistics/StateStatistics_" + i + ".json"));
            info.getStateStatisticses().add(ss);
            ss.setGameInfo(info);
            ss.setPointInTime(System.currentTimeMillis());
        }
        return info;

    }

    public DoloresState loadJsons(DoloresState ds) {

        ds.setValue(DoloresConst.DOLORES_KEY_LOADING_EQUIPMENT_COSTS, "0");
        ds.setValue(DoloresConst.DOLORES_KEY_IT_COSTS, "0");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_COSTS, "450");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_COSTS, "200");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_COSTS, "200");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_COSTS, "300");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_COSTS, "500");

        ds.setValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append("en").toString(), "3");
        ds.setValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append("wv").toString(), "3");
        ds.setValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append("la").toString(), "3");
        ds.setValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append("wk").toString(), "3");
        ds.setValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append("ve").toString(), "2");

        ds.setRoundNumber(10);

        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        //xstream.setMode(XStream.NO_REFERENCES);
        xstream.autodetectAnnotations(true);

        for (int i = 0; i < 4; i++) {
            ArticleDynamics ad = (ArticleDynamics) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/jsonsLoad/articles/ArticleDynamics" + i + ".json"));
            ds.addArticleDynamics(ad);
            ad.setState(ds);
            ad.setAllSerializableInteger();
        }

        for (int i = 1; i < 6; i++) {
            ConveyorDynamics cd = (ConveyorDynamics) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/jsonsLoad/conveyors/ConveyorDynamics" + i + ".json"));
            ds.addConveyorDynamics(cd);
            cd.setState(ds);
        }

        for (int i = 1; i < 14; i++) {
            EmployeeDynamics ed = (EmployeeDynamics) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/jsonsLoad/employees/Employee_" + i + ".json"));
            ed.setSalary(EmployeeFactory.getSalary(ed.getQualifications()));
            ds.addEmployeeDynamics(ed);
            ed.setState(ds);
        }
        for (int i = 0; i < 6; i++) {
            OrderDynamics od = (OrderDynamics) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/jsonsLoad/orders/OrderDynamics" + i + ".json"));
            ds.addOrderDynamcis(od);
            od.setState(ds);
        }
        List<PalletsInProgress> palletsTemp = new ArrayList<>();
        for (int i = 0; i < 380; i++) {

            PalletsInProgress pd = (PalletsInProgress) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/jsonsLoad/pallets/PalletsInProgress" + i + ".json"));
            palletsTemp.add(pd);
            
            pd.setId(null);

        }

        for (int i = 0; i < 4; i++) {
            OrderDynamics od = (OrderDynamics) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/jsonsLoad/orderdummies/OrderDynamics" + i + ".json"));
            ds.addOrderDynamcis(od);
            od.setState(ds);
        }

        for (PalletsInProgress pallet : palletsTemp) {
            for (OrderDynamics oD : ds.getOpenOrderDynamics()) {
                if (pallet.getOrderno() == oD.getOrderno()) {
                    pallet.setOrderDynamics(oD);
                }
            }

        }
        for (OrderDynamics oD : ds.getOpenOrderDynamics()) {
            for (ArticleDynamics aD : ds.getArticleDynamics()) {
                if (oD.getArticleNumber() == aD.getArticleNumber()) {
                    oD.setArticleNumber(aD.getArticleNumber());
                }
            }
        }

        for (PalletsInProgress pd : palletsTemp) {
            int strategyStorage = Integer.valueOf(ds.getValue(DoloresConst.DOLORES_KEY_STRATEGY_STORAGE));
            int strategyIn = Integer.valueOf(ds.getValue(DoloresConst.DOLORES_KEY_STRATEGY_INCOMING_GOODS));
            //PalletsInProgress pd = (PalletsInProgress) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/jsonsLoad/pallets/PalletsInProgress" + i + ".json"));
            Storage s = ds.getStorage();
            
            StockGround freeStockGround = s.getFreeStockGround(pd.getOrderDynamics().getArticleDynamics(), strategyIn, strategyStorage);
            ds.getStorage().stockPallet(pd.getOrderDynamics().getArticleDynamics(), freeStockGround.getStockGroundId(), pd);
            pd.setId(null);

        }

        for (CustomerJobDynamics cJD : ds.getJobDynamics()) {
            for (ArticleDynamics aD : ds.getArticleDynamics()) {
                if (cJD.getArticleNumber() == aD.getArticleNumber()) {
                    cJD.setArticleDynamics(aD);
                }
            }
        }

        return ds;
    }

    protected String loadResource(String resourceFileName) throws IOException {
        String toReturn = null;
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/" + resourceFileName)) {
            try {
                byte[] buffer = new byte[4096];
                StringBuilder sb = new StringBuilder();
                while (input.read(buffer) > 0) {
                    sb.append(new String(buffer, "UTF-8"));
                }
                toReturn = sb.toString();
            } catch (IOException ex) {
                System.out.println("error beim laden von file");
                throw ex;
            }
        }        return toReturn;
    }
}
