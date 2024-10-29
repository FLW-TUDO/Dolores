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
public class PalletsInProgressComparatorByDemandDate implements Comparator {

    private int searchedProcess;

    public PalletsInProgressComparatorByDemandDate(int process) {
        searchedProcess = process;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof PalletsInProgress && o2 instanceof PalletsInProgress) {
            PalletsInProgress p1 = (PalletsInProgress) o1;
            PalletsInProgress p2 = (PalletsInProgress) o2;
            if (p1.getDemandRound() < p2.getDemandRound()) {
                if (p1.getCurrentProcess() != searchedProcess && p2.getCurrentProcess() == searchedProcess) {
                    return 1;
                }
                return -1;
            } else if (p1.getDemandRound() > p2.getDemandRound()) {
                if (p1.getCurrentProcess() == searchedProcess && p2.getCurrentProcess() != searchedProcess) {
                    return -1;
                }
                return 1;
            } else {
                if (p1.getCurrentProcess() != searchedProcess && p2.getCurrentProcess() == searchedProcess) {
                    return 1;
                } else if (p1.getCurrentProcess() == searchedProcess && p2.getCurrentProcess() != searchedProcess) {
                    return -1;
                }
            }
        }
        return 0;
    }
}
