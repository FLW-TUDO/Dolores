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
public class StockGroundComparatorByDistSource implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof StockGround && o2 instanceof StockGround) {
            StockGround stock1 = (StockGround) o1;
            StockGround stock2 = (StockGround) o2;

            if (stock1.getDistSource() < stock2.getDistSource()) {
                return -1;
            } else if (stock1.getDistSource() == stock2.getDistSource()) {
                return 0;

            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }
}
