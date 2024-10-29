/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;

import java.util.Comparator;

/**
 *
 * @author alpl
 */
public class PalletsInProgressComparatorByDeliveryDate implements Comparator {

    private int searchedProcess;

    public PalletsInProgressComparatorByDeliveryDate(int process) {
        searchedProcess = process;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof PalletsInProgress && o2 instanceof PalletsInProgress) {
            PalletsInProgress oip1 = (PalletsInProgress) o1;
            PalletsInProgress oip2 = (PalletsInProgress) o2;

            if (oip1.getOrderDynamics().getDeliveryPeriod() < oip2.getOrderDynamics().getDeliveryPeriod()) {
                if (oip1.getCurrentProcess() != searchedProcess && oip2.getCurrentProcess() == searchedProcess) {
                    return 1;
                }
                return -1;
            } else if (oip1.getOrderDynamics().getDeliveryPeriod() == oip2.getOrderDynamics().getDeliveryPeriod()) {
                if (oip1.getCurrentProcess() != searchedProcess && oip2.getCurrentProcess() == searchedProcess) {
                    return 1;
                } else if (oip1.getCurrentProcess() == searchedProcess && oip2.getCurrentProcess() != searchedProcess) {
                    return -1;
                } else {
                    return 0;
                }
            } else {
                if (oip1.getCurrentProcess() == searchedProcess && oip2.getCurrentProcess() != searchedProcess) {
                    return -1;
                }
                return 1;
            }
        } else {
            return 0;
        }
    }
}
