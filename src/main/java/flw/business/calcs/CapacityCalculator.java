package flw.business.calcs;

import flw.business.core.DoloresGameInfo;
import flw.business.util.DoloresConst;
import flw.business.util.Processes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author alpl
 */
public class CapacityCalculator extends AbstractCalculator {
    
    static final Logger LOG = LoggerFactory.getLogger(CapacityCalculator.class);
    private Map<String, Double> mDouble = new TreeMap<>();
    private EmployeeCalculator employee;

    public CapacityCalculator(List<AbstractCalculator> lCalculators)
    {
	super(lCalculators);
	for (String process : Processes.getInstance().getProcessAbbrevationsWithConveyors())
	{
	    mDouble.put(new StringBuilder("capacity_").append(process).append("_wfp").toString(), 0.0);
	    mDouble.put(new StringBuilder("capacity_").append(process).append("_wofp").toString(), 0.0);
	    mDouble.put(new StringBuilder("unneeded_wfp_employees_").append(process).toString(), 0.0);
	}
	mDouble.put("capacity_storage_in", 0.0);
	mDouble.put("capacity_storage_out", 0.0);
    }

    @Override
    public void calculate(DoloresGameInfo gameInfo)
    {
	ConveyorCalculator conveyor = null;
	employee = null;
	//Get needed Calculators
	for (AbstractCalculator abstractCalculator : lCalculators)
	{
	    if (abstractCalculator instanceof ConveyorCalculator)
	    {
		conveyor = (ConveyorCalculator) abstractCalculator;
	    }
	    else if (abstractCalculator instanceof EmployeeCalculator)
	    {
		employee = (EmployeeCalculator) abstractCalculator;
	    }
	    if (conveyor != null && employee != null)
	    {
		break;
	    }
	}

	if (conveyor != null && employee != null)
	{   //if all calculator found

	    for (String process : Processes.getInstance().getProcessAbbrevationsWithConveyors())
	    {
		//calculate capacity with fp of current process
		double capacity_wfp = Math.min(employee.getCapacity(process, true), conveyor.getCapacity(process, true));
		mDouble.put(new StringBuilder("capacity_").append(process).append("_wfp").toString(), capacity_wfp);
		//count employees with fp of current process, but fp is not needed because of lesser conveyor count with fp
		double uneeded_emloyees_wfp = 0;
		if (employee.getCapacity(process, true) > conveyor.getCapacity(process, true))
		{
		    uneeded_emloyees_wfp = employee.getCapacity(process, true) - conveyor.getCapacity(process, true);
		    mDouble.put(new StringBuilder("unneeded_wfp_employees_").append(process).toString(), uneeded_emloyees_wfp);
		}
		//calculate capacity without fp of current process
		double capacity_wofp = Math.min(employee.getCapacity(process, false) + uneeded_emloyees_wfp, conveyor.getCapacity(process, false));
		mDouble.put(new StringBuilder("capacity_").append(process).append("_wofp").toString(), capacity_wofp);

		//calculate overall capacity of current process
		mDouble.put(new StringBuilder("capacity_").append(process).toString(), capacity_wfp + capacity_wofp);
		mDouble.put(new StringBuilder("capacity_").append(process).append("_overall").toString(), capacity_wfp + capacity_wofp);
	    }
	    //The storage capacity has to be devided into incoming and outgoing transportation
            
            double storage_factor = Double.valueOf(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_STORAGE_FACTOR));
            
	    double capacity_storage_out = Math.round((float) (mDouble.get(new StringBuilder("capacity_").append(Processes.getInstance().getProcessAbbrevations()[DoloresConst.PROCESS_STORAGE]).toString()) * storage_factor));
	    
            mDouble.put("capacity_storage_out", capacity_storage_out);
            mDouble.put("capacity_storage_in", mDouble.get(new StringBuilder("capacity_").append(Processes.getInstance().getProcessAbbrevations()[DoloresConst.PROCESS_STORAGE]).toString()) - capacity_storage_out);
	    
            double capin = mDouble.get(new StringBuilder("capacity_").append(Processes.getInstance().getProcessAbbrevations()[DoloresConst.PROCESS_STORAGE]).toString()) - capacity_storage_out;
            int x = 0;
        }
	else
	{
            LOG.error("One of the Calculators was not found. Game ID: "+ gameInfo.getId());
            
	}
    }

    /**
     *
     * @param process The Process
     * @return The current available capacity of given process (with and without fp)
     */
    public double getCapacity(String process)
    {
	String key = new StringBuilder("capacity_").append(process).toString();
	if (!mDouble.containsKey(key) && !Processes.getInstance().hasProcessConveyors(process))
	{
	    mDouble.put(key, (double)employee.getCapacity(process));
	}
	return mDouble.get(key);
    }

    /**
     *
     * @param process The Process
     * @return The overall capacity of given Process in current periode
     */
    public double getOverallCapacity(String process)
    {
	Double d = mDouble.get(new StringBuilder("capacity_").append(process).append("_overall").toString());
	if (d == null)
	{
	    return employee.getCapacity(process);
	}
	return mDouble.get(new StringBuilder("capacity_").append(process).append("_overall").toString());
    }

    /**
     *
     * @param process The process
     * @param needsForkliftPermit <code>true</code> this method should only involve employees with fp, otherwise it schould only involve employees without fp
     * @return The capacity of given process related to "needsForkliftPermit"
     */
    public double getCapacity(String process, boolean needsForkliftPermit)
    {
	if (needsForkliftPermit)
	{
	    return mDouble.get(new StringBuilder("capacity_").append(process).append("_wfp").toString());
	}
	else
	{
	    return mDouble.get(new StringBuilder("capacity_").append(process).append("_wofp").toString());
	}
    }

    /**
     *
     * @return Capacity of transportation from previous process into storage process
     */
    public double getStorageInputCapacity()
    {
	return mDouble.get("capacity_storage_in");
    }

    public void decreaseStorageInputCapacity(double time)
    {
	mDouble.put("capacity_storage_in", mDouble.get("capacity_storage_in") - time);
    }

    public void decreaseStorageOutputCapacity(double time)
    {
	mDouble.put("capacity_storage_out", mDouble.get("capacity_storage_out") - time);
    }

    /**
     *
     * @return Capacity of transportation from storage process into next process
     */
    public double getStorageOutputCapacity()
    {
	return mDouble.get("capacity_storage_out");
    }

    /**
     *
     * @param process The Process
     * @return if more employees of given process have a fp than conveyor with fp this method returns the difference
     */
    public double getEmployeesWithUnneededFP(String process)
    {
	return mDouble.get(new StringBuilder("unneeded_wfp_employees_").append(process).toString());
    }

    public void decreaseCapacity(String process, double amount)
    {
	String key = new StringBuilder("capacity_").append(process).toString();
	double newVal = mDouble.get(key) - amount;
	mDouble.put(key, (newVal >= 0 ? newVal : 0));
    }

    @Override
    public void prepareNextRound(DoloresGameInfo gameInfo)
    {
    }

    @Override
    public List<Object> getToUpdate() {
        return new ArrayList<>(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
