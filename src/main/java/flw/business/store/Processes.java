/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alpl
 */
public class Processes {

    private static volatile Processes singleton;
    private final ArrayList<String> processes_with_conveyors = new ArrayList<String>(3);
   private final String [] processAbbrevations = {"en", "wv", "la", "wk", "ve"};
    
    private Processes()
    {
	processes_with_conveyors.add("en");
	processes_with_conveyors.add("la");
	processes_with_conveyors.add("ve");
    }
    
    public static Processes getInstance() {
        if (singleton == null) {
            singleton = new Processes();
        }
        return singleton;
    }

    public boolean hasProcessConveyors(String processAbbrevation) {
        return processes_with_conveyors.contains(processAbbrevation);
    }

    public String[] getProcessAbbrevations() {
        return this.processAbbrevations;
    }

    public List<String> getProcessAbbrevationsWithConveyors() {
        return this.processes_with_conveyors;
    }

   
}
