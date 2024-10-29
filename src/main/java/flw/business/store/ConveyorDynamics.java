/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;


import flw.business.core.DoloresState;
import flw.business.util.DoloresConst;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
/**
 *
 * @author tilu
 */
@Entity
public class ConveyorDynamics implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private double conditionPercentage = 100d;
    private int periodBought = -1;
    private int status = DoloresConst.CONVEYOR_STATE_OK;
    private int process = 0;
    private int overhaul_costs;
    private double currentValue;
    private boolean maintenanceEnabled;
    private boolean overhaul = false;
    private boolean sold;
    
    @ManyToOne
    private DoloresState state;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Conveyor conveyor;

    public ConveyorDynamics() {
    }

    public ConveyorDynamics(int overhaul_costs, int process) {
        this();
        this.overhaul_costs = overhaul_costs;
        this.process = process;
        this.sold = false;
    }

    public ConveyorDynamics createCopy(int overhaul_costs, int process) {
        ConveyorDynamics toReturn = new ConveyorDynamics(overhaul_costs, process);
        return toReturn;
    }

    public ConveyorDynamics evolve() {
        ConveyorDynamics toReturn = new ConveyorDynamics();
        toReturn.setConditionPercentage(this.conditionPercentage);
        toReturn.setCurrentValue(this.currentValue);
        toReturn.setMaintenanceEnabled(this.maintenanceEnabled);
        toReturn.setOverhaul(this.overhaul);
        toReturn.setOverhaul_costs(this.overhaul_costs);
        toReturn.setPeriodBought(this.periodBought);
        toReturn.setProcess(this.process);
        toReturn.setStatus(this.status);

        return toReturn;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public Conveyor getConveyor() {
        return conveyor;
    }

    public void setConveyor(Conveyor conveyor) {
        this.conveyor = conveyor;
        conveyor.setConveyorDynamcis(this);
    }

    public DoloresState getState() {
        return state;
    }

    public void setState(DoloresState state) {
        this.state = state;
        if (!state.getConveyorDynamics().contains(this)) {
            state.addConveyorDynamics(this);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getConditionPercentage() {
        return conditionPercentage;
    }

    public void setConditionPercentage(double conditionPercentage) {
        this.conditionPercentage = conditionPercentage;
    }

    public int getPeriodBought() {
        return periodBought;
    }

    public void setPeriodBought(int periodBought) {
        this.periodBought = periodBought;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProcess() {
        return process;
    }
    
    public String getProcessName(){
        String val = "";
        switch(process){
            case 0: 
                val = "global.en";
                break;
            case 1: 
                val = "global.wv";
                break;
            case 2: 
                val = "global.la";
                break;
            case 3: 
                val = "global.wk";
                break;
            case 4: 
                val = "global.ve";
                break;
        }
        return val;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getOverhaul_costs() {
        return overhaul_costs;
    }

    public void setOverhaul_costs(int overhaul_costs) {
        this.overhaul_costs = overhaul_costs;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public boolean isMaintenanceEnabled() {
        return maintenanceEnabled;
    }

    public void setMaintenanceEnabled(boolean maintenanceEnabled) {
        this.maintenanceEnabled = maintenanceEnabled;
    }

    public boolean isOverhaul() {
        return overhaul;
    }

    public void setOverhaul(boolean overhaul) {
        this.overhaul = overhaul;
    }

    public boolean isReady(int aktRound) {

        boolean stateBool = this.getRoundForDelivery() <= aktRound && this.getConditionPercentage() > DoloresConst.CONVEYOR_SCRAP_LIMIT;
        if (!stateBool && this.getStatus() != DoloresConst.CONVEYOR_STATE_SCRAP) {

            if ((this.getRoundForDelivery() <= aktRound)) {
                this.setStatus(DoloresConst.CONVEYOR_STATE_SCRAP);
            }


        }
        return stateBool;
    }

    public int getRoundForDelivery() {
        return this.getPeriodBought() + this.getConveyor().getTimeToDelivery();
    }

    public int getMaintenanceCost() {
        return this.conveyor.getMaintenanceCost();
    }

    public int getTimeToRepair() {
        return conveyor.getTimeToRepair();
    }

    public boolean needsForkliftPermit() {
        return this.conveyor.isNeedsForkliftPermit();
    }

    public double getSpeed() {
        return conveyor.getSpeed();
    }

    private int getPriceView(){
        return (int) conveyor.getPrice();
    }
    
    public double getPrice() {
        return conveyor.getPrice();
    }

    public String getName() {
        return conveyor.getName();
    }

    public int getCapacity() {
        return conveyor.getCapacity();
    }

    public void setCapacity(int capacity) {
        this.conveyor.setCapacity(capacity);
    }

    public int getTimeToDelivery() {
        return conveyor.getTimeToDelivery();
    }

    public void setTimeToDelivery(int timeToDelivery) {
        this.conveyor.setTimeToDelivery(timeToDelivery);
    }

    public boolean isUseInStorage() {
        return conveyor.isUseInStorage();

    }

    public void setUseInStorage(boolean useInStorage) {
        this.conveyor.setUseInStorage(useInStorage);
    }

    public boolean isNeedsForkliftPermit() {
        return conveyor.isNeedsForkliftPermit();
    }

    public void setNeedsForkliftPermit(boolean needsForkliftPermit) {
        this.conveyor.setNeedsForkliftPermit(needsForkliftPermit);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConveyorDynamics)) {
            return false;
        }
        ConveyorDynamics other = (ConveyorDynamics) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "flw.business.store.ConveyorDynamics[ id=" + id + " ]";
    }
}
