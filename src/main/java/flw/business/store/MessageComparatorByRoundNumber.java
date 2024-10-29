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
public class MessageComparatorByRoundNumber implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof Message && o2 instanceof Message) {
            Message stock1 = (Message) o1;
            Message stock2 = (Message) o2;

            if (stock1.getRoundNumber() > stock2.getRoundNumber()) {
                return 1;
            } else if (stock1.getRoundNumber() == stock2.getRoundNumber()) {
                return 0;

            } else {
                return -1;
            }
        } else {
            return 0;
        }
    }
}
