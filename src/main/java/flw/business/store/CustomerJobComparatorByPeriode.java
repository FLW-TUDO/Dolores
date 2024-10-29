/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;

import java.util.Comparator;

/**
 *
 * @author tilu
 */
public class CustomerJobComparatorByPeriode implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof CustomerJob && o2 instanceof CustomerJob) {
            CustomerJob job1 = (CustomerJob) o1;
            CustomerJob job2 = (CustomerJob) o2;
            if (job1.getOrderPeriode() < job2.getOrderPeriode()) {
                return -1;
            }
            if (job1.getOrderPeriode() > job2.getOrderPeriode()) {
                return 1;
            }
        }
        return 0;
    }
}
