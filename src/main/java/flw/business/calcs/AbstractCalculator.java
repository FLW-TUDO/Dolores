/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.calcs;

import flw.business.core.DoloresGameInfo;
import java.util.List;

/**

 * @author chmo
 */
public abstract class AbstractCalculator
{
    protected List<AbstractCalculator> lCalculators;

    /**
     * By giving reference to all calculators, current calculator states are 
     * available...
     * @param lCalculators
     */
    public AbstractCalculator(List<AbstractCalculator> lCalculators)
    {
        this.lCalculators = lCalculators;
    }

    public abstract void calculate(DoloresGameInfo gameInfo);

    public abstract void prepareNextRound(DoloresGameInfo gameInfo);
    
    public abstract List<Object> getToUpdate();
}
