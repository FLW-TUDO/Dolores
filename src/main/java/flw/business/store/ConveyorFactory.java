/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;

import flw.business.util.CsvObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tilu
 */
public class ConveyorFactory {

    private volatile static ConveyorFactory singleton;

    private static ConveyorFactory getInstance() {
        if (singleton == null) {
            singleton = new ConveyorFactory();
        }
        return singleton;
    }

    public static List<Conveyor> getConveyorBase() {
        ConveyorFactory factory = ConveyorFactory.getInstance();
        return factory.getConveyors();
    }

    public static List<ConveyorDynamics> getConveyorDynamicsBase() {
        ConveyorFactory factory = ConveyorFactory.getInstance();
        return factory.getDynamics();
    }

    /**
     *
     * @param base
     * @param periodBought
     * @param process
     * @return ConveyorDynamics with Conveyor already set. -> need to persist
     * return value!
     */
    public static ConveyorDynamics createBoughtConveyor(ConveyorDynamics base, int periodBought, int process) {


        ConveyorDynamics toReturn = base.createCopy(base.getOverhaul_costs(), process);
        Conveyor con = base.getConveyor().createCopy();
        toReturn.setPeriodBought(periodBought);
        toReturn.setCurrentValue(base.getPrice() * 0.8);
        toReturn.setConveyor(con);
        return toReturn;
    }
    private final List<Conveyor> lConveyors;
    private final List<ConveyorDynamics> lDynamics;

    public ConveyorFactory() {
        lConveyors = new ArrayList<Conveyor>();
        lDynamics = new ArrayList<ConveyorDynamics>();
        fillConveyors();
    }

    private void fillConveyors() {
        try {
            CsvObject co = new CsvObject(loadResource("conveyor_base.csv"));
            List<String[]> lData = co.getTableData();
            for (String[] arrStr : lData) {
                if (arrStr.length == 11) {
                    Conveyor c = new Conveyor(arrStr[0], Integer.parseInt(arrStr[1]),
                            Double.parseDouble(arrStr[2]), Integer.parseInt(arrStr[3]),
                            Double.parseDouble(arrStr[4]), Integer.parseInt(arrStr[5]),
                            Boolean.valueOf(arrStr[7]), Boolean.valueOf(arrStr[8]), Integer.parseInt(arrStr[9]), Integer.parseInt(arrStr[10]));

                    ConveyorDynamics cD = new ConveyorDynamics(Integer.parseInt(arrStr[6]), -1);
                    cD.setMaintenanceEnabled(false);
                    cD.setConveyor(c);
                    lConveyors.add(c);
                    lDynamics.add(cD);
                }
            }
        } catch (IOException ex) {
        }
    }

    /**
     *
     * @return list of conveyors (no dynamics!!)
     */
    public List<Conveyor> getConveyors() {
        return lConveyors;
    }

    /**
     *
     * @return list of conveyordynamics
     */
    public List<ConveyorDynamics> getDynamics() {
        return lDynamics;
    }

    protected String loadResource(String resourceFileName) throws IOException {
        String toReturn = null;
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/" + resourceFileName);

        try {
            byte[] buffer = new byte[4096];
            StringBuilder sb = new StringBuilder();
            while (input.read(buffer) > 0) {
                sb.append(new String(buffer, "UTF-8"));
            }
            toReturn = sb.toString();
        } catch (IOException ex) {
            /*logger.error(new StringBuilder(
             "Unable to load embedded resource: '").append(
             resourceFileName).append("'").toString(), ex);*/
            System.out.println("Error loading file");
            throw ex;
        }
        input.close();
        return toReturn;
    }
}
