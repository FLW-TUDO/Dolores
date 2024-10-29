package flw.business.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alpl
 */
public class Processes {

    private static volatile Processes singleton;
    private final ArrayList<String> processes_with_conveyors = new ArrayList<String> (3);
    private final ArrayList<String> processes_with_conveyors_capital = new ArrayList<String> (3);
    private final String [] processAbbrevations = {"en", "wv", "la", "wk", "ve"};
    private final String [] processAbbrevationsCapital = {"EN", "WV", "LA", "WK", "VE"};
    
    private Processes()
    {
	processes_with_conveyors.add("en");
	processes_with_conveyors.add("la");
	processes_with_conveyors.add("ve");
        processes_with_conveyors_capital.add("EN");
	processes_with_conveyors_capital.add("LA");
	processes_with_conveyors_capital.add("VE");
    }
    
    public static Processes getInstance()
    {
	if(singleton == null)
	{
	    singleton = new Processes();
	}
	return singleton;
    }
    
    public boolean hasProcessConveyors(String processAbbrevation)
    {
	return processes_with_conveyors.contains(processAbbrevation);
    }
    
    public String[] getProcessAbbrevations()
    {
	return this.processAbbrevations;
    }
    
    public String[] getProcessAbbrevationsCapital()
    {
	return this.processAbbrevationsCapital;
    }
    
    public List<String> getProcessAbbrevationsWithConveyors()
    {
	return this.processes_with_conveyors;
    }

    public ArrayList<String> getProcesses_with_conveyors_capital() {
        return this.processes_with_conveyors_capital;
    }
    
    

}
