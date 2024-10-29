/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.calcs;

import flw.business.core.DoloresGameInfo;
import flw.business.statistics.ConveyorStatistics;
import flw.business.store.Conveyor;
import flw.business.store.ConveyorDynamics;
import flw.business.store.Message;
import flw.business.util.DoloresConst;
import flw.business.util.Processes;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tilu
 */
public class ConveyorCalculator extends AbstractCalculator{
     Map<String, Double> mDoubles = new TreeMap<String, Double>();
    Map<String, Integer> mInts = new TreeMap<String, Integer>();
    private List<String> messages = new ArrayList<String>();
    
    private ConveyorStatistics convStat;

    public ConveyorCalculator(List<AbstractCalculator> lCalculators)
    {
	super(lCalculators);
	mDoubles.clear();
	mInts.clear();
        
        convStat = new ConveyorStatistics();
                
        
	//initialize variables
	for (String process : Processes.getInstance().getProcessAbbrevationsWithConveyors())
	{
	    mDoubles.put(new StringBuilder("speed_").append(process).toString(), 0d);
	    mDoubles.put(new StringBuilder("speed_").append(process).append("_wfp").toString(), 0d);//Conveyors needing a ForkliftPermit (with forkliftpermit)
	    mDoubles.put(new StringBuilder("speed_").append(process).append("_wofp").toString(), 0d);//Conveyors not needing a ForkliftPermit (without forkliftpermit)
	    mDoubles.put(new StringBuilder("conditions_").append(process).toString(), 0d);
	    mDoubles.put(new StringBuilder("values_").append(process).toString(), 0d);
	    mDoubles.put(new StringBuilder("avg_condition_").append(process).toString(), 0d);
	    mInts.put(new StringBuilder("conveyor_count_").append(process).toString(), 0);
	    mInts.put(new StringBuilder("conveyor_count_").append(process).append("_wfp").toString(), 0); //Conveyors needing a ForkliftPermit (with forkliftpermit)
	    mInts.put(new StringBuilder("conveyor_count_").append(process).append("_wofp").toString(), 0); //Conveyors not needing a ForkliftPermit (without forkliftpermit)
	    mInts.put(new StringBuilder("capacity_").append(process).toString(), 0);
	    mInts.put(new StringBuilder("capacity_").append(process).append("_wfp").toString(), 0);//Conveyors needing a ForkliftPermit (with forkliftpermit)
	    mInts.put(new StringBuilder("capacity_").append(process).append("_wofp").toString(), 0);//Conveyors not needing a ForkliftPermit (without forkliftpermit)
	    mInts.put(new StringBuilder("repair_duration_").append(process).toString(), 0);
	}
	mDoubles.put("costs_new", 0d);
	mDoubles.put("costs_repair", 0d);
	mDoubles.put("costs_maintenance", 0d);
	mDoubles.put("costs_overhaul", 0d);
	mDoubles.put("currentValues", 0d);
	mDoubles.put("costs", 0d);
	mDoubles.put("avg_condition", 0d);
	mInts.put("repair_duration", 0);
	mInts.put("conveyor_count", 0);
    }

    @Override
    public void calculate(DoloresGameInfo gameInfo)
    {
        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_INCOME_CONVEYOR_SALE, "0");
        
        List<ConveyorDynamics> toRemove = new ArrayList<ConveyorDynamics>();
        
        convStat.setRoundNumber(gameInfo.getCurrentState().getRoundNumber());

	for (ConveyorDynamics conveyorDynamics : gameInfo.getCurrentState().getConveyorDynamics())
	{
            if(conveyorDynamics.isSold()){
                 double currentValue = Double.valueOf(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_INCOME_CONVEYOR_SALE)) + conveyorDynamics.getCurrentValue();
                
                gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_INCOME_CONVEYOR_SALE, String.valueOf(currentValue));
                toRemove.add(conveyorDynamics);
                
            }
            
            
	    if (conveyorDynamics.isReady(Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER))))
	    {	//conveyor is baught and is not scrap
		//reset conveyor status, because a breakdown from last round was repaired already and if it is scrap you didnt reach this point
		conveyorDynamics.setStatus(DoloresConst.CONVEYOR_STATE_OK);
		if (conveyorDynamics.getConditionPercentage() <= DoloresConst.CONVEYOR_BREAKDOWN_POSSIBLE_LIMIT)
		{   //conveyor COULD be broken down in current period
		    if (Math.random() < 0.5) //TODO: Check if a dynamic breakdown in conjunction with current condition of Conveyor is useful
		    {	//converyor will be broken down in current Period
			conveyorDynamics.setStatus(DoloresConst.CONVEYOR_STATE_BREAKDOWN);
			messages.add(new StringBuilder(DoloresConst.CONVEYOR_NOTIFICATION_BREAKDOWN).append("?").append(conveyorDynamics.getName()).append("?").append(conveyorDynamics.getConveyor().getId()).toString());
                        
                        
                    }
		}

		if (conveyorDynamics.getStatus() == DoloresConst.CONVEYOR_STATE_OK || conveyorDynamics.getStatus() == DoloresConst.CONVEYOR_STATE_BREAKDOWN)
		{   //conveyor is ready or has a breakdown in current period
		    String currentProcessAbbr = Processes.getInstance().getProcessAbbrevations()[conveyorDynamics.getProcess()];
		    if (conveyorDynamics.needsForkliftPermit())
		    {
			//conveyor needs a forklift permit, add its speed to sum of its process
			String key = new StringBuilder("speed_").append(currentProcessAbbr).append("_wfp").toString();
			mDoubles.put(key, mDoubles.get(key) + conveyorDynamics.getSpeed());

			//count current conveyor to count of current process with forklift permit
			key = new StringBuilder("conveyor_count_").append(currentProcessAbbr).append("_wfp").toString();
			mInts.put(key, mInts.get(key) + 1);

			//calculate its capacity and add it to capacity sum of current process
			key = new StringBuilder("capacity_").append(currentProcessAbbr).append("_wfp").toString();
			int currentCapacity = DoloresConst.DOLORES_GAME_WORKING_TIME_PERIOD + (Integer.parseInt(gameInfo.getCurrentState().getValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(currentProcessAbbr).toString())) * 3600);
			//if conveyor was broken down in current period, subtract repair duration from capacity
			if(conveyorDynamics.getStatus() == DoloresConst.CONVEYOR_STATE_BREAKDOWN)
			{
			    currentCapacity -=  conveyorDynamics.getTimeToRepair();
			}
			mInts.put(key, mInts.get(key) + currentCapacity);
		    }
		    else
		    {	//conveyor do not need a forklift permit, add its speed to sum of its process
			String key = new StringBuilder("speed_").append(currentProcessAbbr).append("_wofp").toString();
			mDoubles.put(key, mDoubles.get(key) + conveyorDynamics.getSpeed());

			//count current conveyor to count of current process without forklift permit
			key = new StringBuilder("conveyor_count_").append(currentProcessAbbr).append("_wofp").toString();
			mInts.put(key, mInts.get(key) + 1);
			
			//calculate its capacity and add it to capacity sum of current process
			key = new StringBuilder("capacity_").append(currentProcessAbbr).append("_wofp").toString();
			int currentCapacity = DoloresConst.DOLORES_GAME_WORKING_TIME_PERIOD + (Integer.parseInt(gameInfo.getCurrentState().getValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(currentProcessAbbr).toString())) * 3600);
			//if conveyor was broken down in current period, subtract repair duration from capacity
			if(conveyorDynamics.getStatus() == DoloresConst.CONVEYOR_STATE_BREAKDOWN)
			{
			    currentCapacity -=  conveyorDynamics.getTimeToRepair();
			}
			currentCapacity = (currentCapacity > 0 ? currentCapacity : 0);
			mInts.put(key, mInts.get(key) + currentCapacity);
		    }
		}
	    }
            if(conveyorDynamics.getStatus() == 2){
                if(!messages.contains(new StringBuilder(DoloresConst.CONVEYOR_NOTIFICATION_GOT_SCRAP).append("?").append(conveyorDynamics.getName()).append("?").append(conveyorDynamics.getConveyor().getId()).toString())){
                    
                    messages.add(new StringBuilder(DoloresConst.CONVEYOR_NOTIFICATION_GOT_SCRAP).append("?").append(conveyorDynamics.getName()).append("?").append(conveyorDynamics.getConveyor().getId()).toString());
                }
            }
            
	    calculateConveyorCostsAndNewStatus(gameInfo, conveyorDynamics);
	}
        for(ConveyorDynamics convDyn: toRemove){
            gameInfo.getCurrentState().getConveyorDynamics().remove(convDyn);
        }
        
        
	int overall_conveyor_count = 0;
	for (String process : Processes.getInstance().getProcessAbbrevationsWithConveyors())
	{
	    //calculate sum of speeds of all conveyors in current process
	    String wfp = new StringBuilder("speed_").append(process).append("_wfp").toString();
	    String wofp = new StringBuilder("speed_").append(process).append("_wofp").toString();
	    double speed = mDoubles.get(wfp) + mDoubles.get(wofp);
	    mDoubles.put(new StringBuilder("speed_").append(process).toString(), speed);

	    //count all conveyors of current process and set it
	    wfp = new StringBuilder("conveyor_count_").append(process).append("_wfp").toString();
	    wofp = new StringBuilder("conveyor_count_").append(process).append("_wofp").toString();
	    int count = mInts.get(wfp) + mInts.get(wofp);
	    mInts.put(new StringBuilder("conveyor_count_").append(process).toString(), count);
	    //count the conveyor of current process to the overall count
	    overall_conveyor_count += count;

	    //if process contains any conveyor calculate the average speed for this process
	    if (count > 0)
	    {
		mDoubles.put(new StringBuilder("avg_speed_").append(process).toString(), speed / count);
	    }

            
      
            
            
            
	    wfp = new StringBuilder("capacity_").append(process).append("_wfp").toString();
	    wofp = new StringBuilder("capacity_").append(process).append("_wofp").toString();
	    if (count > 1)
	    { //Behinderungsfaktor bei mehr als einem FM im Prozess
		int wfp_capacity = Math.round((float) mInts.get(wfp) * (((float) count - 1) * (float) DoloresConst.CONVEYOR_DISABILITY_FACTOR));
		int wofp_capacity = Math.round((float) mInts.get(wofp) * (((float) count - 1) * (float) DoloresConst.CONVEYOR_DISABILITY_FACTOR));
		wfp_capacity = (wfp_capacity > 0 ? wfp_capacity : 0);
		wofp_capacity = (wofp_capacity > 0 ? wofp_capacity : 0);
		mInts.put(wfp, mInts.get(wfp)- wfp_capacity);
		mInts.put(wofp, mInts.get(wofp)- wofp_capacity);
	    }

	    //calculate and set capacity of current Process
	    mInts.put(new StringBuilder("capacity_").append(process).toString(), mInts.get(wfp) + mInts.get(wofp));

	    //add repair duration and values of the conveyors of current process to the sum
	    mInts.put("repair_duration", mInts.get("repair_duration") + mInts.get(new StringBuilder("repair_duration_").append(process).toString()));
	    mDoubles.put("currentValues", mDoubles.get(new StringBuilder("values_").append(process).toString()) + mDoubles.get("currentValues"));

	    //calculate average Condition for current Process and add sum of conditions to overall avg_condition
	    mDoubles.put(new StringBuilder("avg_condition_").append(process).toString(), mDoubles.get(new StringBuilder("conditions_").append(process).toString()) / count);
	    mDoubles.put("avg_condition", mDoubles.get("avg_condition") + mDoubles.get(new StringBuilder("conditions_").append(process).toString()));
	}
	//Set count of all Conveyors
	mInts.put("conveyor_count", overall_conveyor_count);
	//Calculate overall average Condition of Conveyors of all Processes
	mDoubles.put("avg_condition", mDoubles.get("avg_condition") / overall_conveyor_count);
	//Set the Sum of calculated costs
	mDoubles.put("costs", mDoubles.get("costs_repair") + mDoubles.get("costs_maintenance") + mDoubles.get("costs_new") + mDoubles.get("costs_overhaul"));
    }

    private void calculateConveyorCostsAndNewStatus(DoloresGameInfo gameInfo, ConveyorDynamics conveyorDynamics)
    {
	    int aktRound = Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER));
	    if (conveyorDynamics.isReady(aktRound))
	    {
                if(conveyorDynamics.getPeriodBought()==-1){
                    
                }
                
                else if(conveyorDynamics.getRoundForDelivery() == aktRound)
		{   //Conveyor was bought some rounds ago and will be delivered this round, so pay the price
		    mDoubles.put("costs_new", mDoubles.get("costs_new") + conveyorDynamics.getPrice());
		}
		if (conveyorDynamics.getStatus() == DoloresConst.CONVEYOR_STATE_BREAKDOWN)
		{
		    //calculate costs of repair for current conveyor, because it was broken down this period
		    double aktrepcost = Math.round(conveyorDynamics.getPrice() * (conveyorDynamics.getConditionPercentage() / 100) * DoloresConst.COVEYOR_REPAIR_COST);
		    mDoubles.put("costs_repair", mDoubles.get("costs_repair") + aktrepcost);
                    
                    
                    
		    //calculate duration of repairing current conveyor
		    String key = new StringBuilder("repair_duration_").append(Processes.getInstance().getProcessAbbrevations()[conveyorDynamics.getProcess()]).toString();
		    mInts.put(key, mInts.get(key) + conveyorDynamics.getTimeToRepair());
		}
		if (conveyorDynamics.isOverhaul())
		{   //Calculate new Condition after Overhauling current conveyor
		    int alter = aktRound - conveyorDynamics.getPeriodBought();
		    double alterzustand = conveyorDynamics.getConditionPercentage();
		    conveyorDynamics.setConditionPercentage(100 * (1 - (alter * (DoloresConst.CONVEYOR_DAMAGE_WITH_MAINTENANCE/100))));
		    //calculate costs of overhauling current conveyor
		    
                    //double costs = (conveyorDynamics.getConditionPercentage() - alterzustand) * conveyorDynamics.getOverhaul_costs();
		    mDoubles.put("costs_overhaul", mDoubles.get("costs_overhaul") + conveyorDynamics.getOverhaul_costs());
		    //Reset conveyor overhaul flag, so it is not repeated in next period
		    conveyorDynamics.setOverhaul(false);
		}
		else
		{
		    //Calculate new Condition of the current conveyor
		    if (conveyorDynamics.isMaintenanceEnabled())
		    {
			conveyorDynamics.setConditionPercentage(conveyorDynamics.getConditionPercentage() - DoloresConst.CONVEYOR_DAMAGE_WITH_MAINTENANCE);
                        String keyy = "costs_maintenance";
                        mDoubles.put(keyy, mDoubles.get(keyy) + conveyorDynamics.getMaintenanceCost());
		    }
		    else
		    {
			conveyorDynamics.setConditionPercentage(conveyorDynamics.getConditionPercentage() - DoloresConst.CONVEYOR_DAMAGE_WITHOUT_MAINTENANCE);
		    }
		    if (conveyorDynamics.getConditionPercentage() <= 0)
		    {
			conveyorDynamics.setConditionPercentage(0d);
		    }
		}
		//Calculate new value of Conveyor and set it
		conveyorDynamics.setCurrentValue(conveyorDynamics.getPrice() * (conveyorDynamics.getConditionPercentage() / 100) * DoloresConst.CONVEYOR_FACTOR_RESALE);
		//Add new Condition to Condition Sum of its Process
		String key = new StringBuilder("conditions_").append(Processes.getInstance().getProcessAbbrevations()[conveyorDynamics.getProcess()]).toString();
		mDoubles.put(key, mDoubles.get(key) + conveyorDynamics.getConditionPercentage());
		//Add new current value to values of its Process
		key = new StringBuilder("values_").append(Processes.getInstance().getProcessAbbrevations()[conveyorDynamics.getProcess()]).toString();
		mDoubles.put(key, mDoubles.get(key) + conveyorDynamics.getCurrentValue());
		//If Condition of Conveyor is critical now, give a warning
		if(conveyorDynamics.getConditionPercentage() < (DoloresConst.CONVEYOR_CONDITIONS_CRITICAL_LIMIT * 100))
		{
                    
                    if(!messages.contains(new StringBuilder(DoloresConst.CONVEYOR_NOTIFICATION_GOT_SCRAP).append("?").append(conveyorDynamics.getName()).append("?").append(conveyorDynamics.getId()).toString())){
                    
                        messages.add(new StringBuilder(DoloresConst.CONVEYOR_NOTIFICATION_CRITICAL_CONDITION).append("?").append(conveyorDynamics.getName()).append("?").append(conveyorDynamics.getConveyor().getId()).toString());
                    
                    }
		    
		}
	    }
            if(conveyorDynamics.getStatus() == 2){
                if(!messages.contains(new StringBuilder(DoloresConst.CONVEYOR_NOTIFICATION_GOT_SCRAP).append("?").append(conveyorDynamics.getName()).append("?").append(conveyorDynamics.getId()).toString())){
                    
                    messages.add(new StringBuilder(DoloresConst.CONVEYOR_NOTIFICATION_GOT_SCRAP).append("?").append(conveyorDynamics.getName()).append("?").append(conveyorDynamics.getConveyor().getId()).toString());
                    
                }
            }
            
            
    }

    /**
     * Returns the average condition of all Converyors from all Processes
     * @return The average condition as percent
     */
    public double getOverallAverageCondition()
    {
	return mDoubles.get("avg_condition");
    }

    /**
     * Returns the average condtion of the conveyors of the given process
     * @param process The process
     * @return the average condition as percent
     */
    public double getAverageCondition(String process)
    {
	return mDoubles.get(new StringBuilder("avg_condition_").append(process).toString());
    }

    /**
     * Returns the repair duration of all Converyors from all Processes
     * @return The repair duration
     */
    public int getOverallRepairDuration()
    {
	return mInts.get("repair_duration");
    }

    public double getOverallRepairCost()
    {
	return mDoubles.get("costs_repair");
    }

    public double getMaintenanceCost()
    {
	return mDoubles.get("costs_maintenance");
    }

    public double getOverhaulCost()
    {
	return mDoubles.get("costs_overhaul");
    }

    public double getNewConveyorCost()
    {
	return mDoubles.get("costs_new");
    }

    /**
     * Returns repair duration of the conveyors of the given process
     * @param process The process
     * @return the repair duration
     */
    public double getRepairDuration(String process)
    {
	return mInts.get(new StringBuilder("repair_duration_").append(process).toString());
    }

    public double getCurrentConveyorValueSum()
    {
	return mDoubles.get("currentValues");
    }

    public double getCurrentConveyorValueSum(String process)
    {
	return mDoubles.get(new StringBuilder("values_").append(process).toString());
    }

    public double getOverallConveyorCosts()
    {
	return mDoubles.get("costs");
    }

    /**
     * Returns the Overall Count of Converyors of the given Process
     * @param name The Abbrevation of the Process (Look in DoloresConst)
     * @return The count of Conveyors
     */
    public int getCount(String process)
    {
	return this.mInts.get(new StringBuilder("conveyor_count_").append(process).toString());
    }

    /**
     * Returns the Count of Converyors of the given Process wich needs/needs not a ForkliftPermit.
     * If you want to get the Overall Count of conveyors use getCount(String name) instead.
     * @param name The Abbrevation of the Process (Look in DoloresConst)
     * @param needsForliftPermit <code>true</code> if only Converyors wich need a ForkliftPermit should be count, otherwise only Conveyors not needing a ForkliftPermit will be counted
     * @return The count of Conveyors
     */
    public int getCount(String name, boolean needsForkliftPermit)
    {
	if (needsForkliftPermit)
	{
	    return this.mInts.get(new StringBuilder("conveyer_count_").append(name).append("_wfp").toString());
	}
	else
	{
	    return this.mInts.get(new StringBuilder("conveyer_count_").append(name).append("wofp").toString());
	}
    }

    /**
     * Returns the Overall Speedsum of the Converyors of the Process
     * @param process The Process to get the Speedsum for
     * @return The Sum of Speeds of the Converyors of the Process
     */
    public double getSpeedSum(String process)
    {
	return this.mDoubles.get(new StringBuilder("speed_").append(process).toString());
    }

    /**
     * Returns SpeedSums for Conveyors wich need or needs not a ForkliftPermit.
     * To get the overall Speedsum of a Process use getSpeedSum(String process) instead
     * @param process The Process to get the Speedsum for
     * @param needsForkliftPermit If <code>true</code> the Speeds of Coveyors wich needs a ForkliftPermit are returned, otherwise only those wich don't need a ForkliftPermit are Returned
     * @return The Sum of Speeds for the Process and if the counted Converyors needs a ForkliftPermit or not
     */
    public double getSpeedSum(String process, boolean needsForkliftPermit)
    {
	if (needsForkliftPermit)
	{
	    return this.mDoubles.get(new StringBuilder("speed_").append(process).append("wpf").toString());
	}
	else
	{
	    return this.mDoubles.get(new StringBuilder("speed_").append(process).append("wopf").toString());
	}
    }

    /**
     * Returns the Overall ConditionSum of the Converyors of the Process
     * @param process The Process to get the Speedsum for
     * @return The Sum of Conditions of the Converyors of the Process
     */
    public double getConditionSum(String process)
    {
	return this.mDoubles.get(new StringBuilder("conditions_").append(process).toString());
    }

    /**
     * Returns the Overall ValueSum of the Converyors of the Process
     * @param process The Process to get the Speedsum for
     * @return The Sum of Current Values of the Converyors of the Process
     */
    public double getCurrentValueSum(String process)
    {
	return this.mDoubles.get(new StringBuilder("values_").append(process).toString());
    }


    public double getAverageSpeed(String process)
    {
         Double get = this.mDoubles.get(new StringBuilder("avg_speed_").append(process).toString());
        
         if(null == get){
             this.mDoubles.put(new StringBuilder("avg_speed_").append(process).toString(), 0.0);
             //this.mDoubles.set(new StringBuilder("avg_speed_").append(process).toString());
             return 0.0;
         }
         else{
             return get;
         }
	
    }

    public double getAverageSpeed(String process, boolean withUnitSecurityDevices)
    {
	if(withUnitSecurityDevices)
	{
	    return this.mDoubles.get(new StringBuilder("avg_speed_").append(process).toString()) * DoloresConst.SPEED_FACTOR_WITH_UNIT_SECURITY_DEVICES;
	}
	return this.mDoubles.get(new StringBuilder("avg_speed_").append(process).toString()) * DoloresConst.SPEED_FACTOR_WITHOUT_UNIT_SECURITY_DEVICES;
    }

    /**
     * Returns overall Capacity of the given Process
     * @param processname The Name of the Process
     * @return overall Capacity of the given Process
     */
    public double getCapacity(String processname)
    {
        // 0 breaks the calculation, so return some small value
        if(mInts.get(new StringBuilder("capacity_").append(processname).toString()) == 0){
            return 0.0001;
        }
	return (double )mInts.get(new StringBuilder("capacity_").append(processname).toString());
    }

    /**
     * Returns the capacity of the Process for conveyors only with / without forkliftPermit
     * To get the overall Capacity of all Conveyors of the Process use getCapacity(String processname) instead
     * @param processname The name of the Process
     * @param needsForkliftPermit if <code>true</code> method returns capacity of all Conveyors wich needs a forklift permit, otherwise it only returns capacity of converyors wich NOT needs a forklift permit
     * @return the capacity for given parameters
     */
    public int getCapacity(String processname, boolean needsForkliftPermit)
    {
	if (needsForkliftPermit)
	{
	    return mInts.get(new StringBuilder("capacity_").append(processname).append("_wfp").toString());
	}
	else
	{
	    return mInts.get(new StringBuilder("capacity_").append(processname).append("_wofp").toString());
	}
    }

    public double getOverallCosts()
    {
	return mDoubles.get("costs");
    }

    @Override
    public void prepareNextRound(DoloresGameInfo gameInfo)
    {
        for (ConveyorDynamics conveyorDynamics : gameInfo.getCurrentState().getConveyorDynamics())
	{
            if(conveyorDynamics.getRoundForDelivery() == Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER))){             
                messages.add(new StringBuilder(DoloresConst.CONVEYOR_NOTIFICATION_DELIVER).append("=").append(conveyorDynamics.getName()).append("=").append(conveyorDynamics.getConveyor().getId()).append("=").append(conveyorDynamics.getPeriodBought()).append("=").append((int)conveyorDynamics.getPrice()).toString());             
            }
        }
	
    }
    
    public List<Object> getToUpdate() {
        return new ArrayList<Object>(); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public ConveyorStatistics getConvStat() {
        return convStat;
    }

    public void setConvStat(ConveyorStatistics convStat) {
        this.convStat = convStat;
    }

    
    public void transfer(){
        for ( Field f : convStat.getClass().getDeclaredFields()){
            if(mInts.get(f.getName()) != null && !Double.isNaN(mInts.get(f.getName()))){
                try {
                    f.setAccessible(true);
                    f.set(convStat, mInts.get(f.getName()));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(mDoubles.get(f.getName()) != null && !Double.isNaN(mDoubles.get(f.getName()))){
                try {
                    f.setAccessible(true);
                    f.set(convStat, mDoubles.get(f.getName()));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
        }
    }
    
}
