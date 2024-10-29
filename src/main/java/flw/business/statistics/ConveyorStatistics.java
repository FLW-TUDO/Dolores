/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.statistics;


import flw.business.core.DoloresGameInfo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author tilu
 */
@Entity
public class ConveyorStatistics implements AbstractStatistics {

   
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    private DoloresGameInfo gameInfo;
    private int roundNumber;
    private double avg_condition;
    private double avg_condition_en;
    private double avg_condition_la;
    private double avg_condition_ve;
    private double avg_speed_en;
    private double avg_speed_la;
    private double avg_speed_ve;
    private double conditions_en;
    private double conditions_la;
    private double conditions_ve;
    private double costs;
    private double costs_maintenance;
    private double costs_new;
    private double costs_overhaul;
    private double costs_repair;
    private double currentValues;
    private double speed_en;
    private double speed_en_wfp;
    private double speed_en_wofp;
    private double speed_la;
    private double speed_la_wfp;
    private double speed_la_wofp;
    private double speed_ve;
    private double speed_ve_wfp;
    private double speed_ve_wofp;
    private double values_en;
    private double values_la;
    private double values_ve;
    private int capacity_en;
    private int capacity_en_wfp;
    private int capacity_en_wofp;
    private int capacity_la;
    private int capacity_la_wfp;
    private int capacity_la_wofp;
    private int capacity_ve;
    private int capacity_ve_wfp;
    private int capacity_ve_wofp;
    private int conveyor_count;
    private int conveyor_count_en;
    private int conveyor_count_en_wfp;
    private int conveyor_count_en_wofp;
    private int conveyor_count_la;
    private int conveyor_count_la_wfp;
    private int conveyor_count_la_wofp;
    private int conveyor_count_ve;
    private int conveyor_count_ve_wfp;
    private int conveyor_count_ve_wofp;
    private int repair_duration;
    private int repair_duration_en;
    private int repair_duration_la;
    private int repair_duration_ve;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DoloresGameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(DoloresGameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    /**
     * @return the avg_condition
     */
    public double getAvg_condition() {
        return avg_condition;
    }

    /**
     * @param avg_condition the avg_condition to set
     */
    public void setAvg_condition(double avg_condition) {
        this.avg_condition = avg_condition;
    }

    /**
     * @return the avg_condition_en
     */
    public double getAvg_condition_en() {
        return avg_condition_en;
    }

    /**
     * @param avg_condition_en the avg_condition_en to set
     */
    public void setAvg_condition_en(double avg_condition_en) {
        this.avg_condition_en = avg_condition_en;
    }

    /**
     * @return the avg_condition_la
     */
    public double getAvg_condition_la() {
        return avg_condition_la;
    }

    /**
     * @param avg_condition_la the avg_condition_la to set
     */
    public void setAvg_condition_la(double avg_condition_la) {
        this.avg_condition_la = avg_condition_la;
    }

    /**
     * @return the avg_condition_ve
     */
    public double getAvg_condition_ve() {
        return avg_condition_ve;
    }

    /**
     * @param avg_condition_ve the avg_condition_ve to set
     */
    public void setAvg_condition_ve(double avg_condition_ve) {
        this.avg_condition_ve = avg_condition_ve;
    }

    /**
     * @return the avg_speed_en
     */
    public double getAvg_speed_en() {
        return avg_speed_en;
    }

    /**
     * @param avg_speed_en the avg_speed_en to set
     */
    public void setAvg_speed_en(double avg_speed_en) {
        this.avg_speed_en = avg_speed_en;
    }

    /**
     * @return the avg_speed_la
     */
    public double getAvg_speed_la() {
        return avg_speed_la;
    }

    /**
     * @param avg_speed_la the avg_speed_la to set
     */
    public void setAvg_speed_la(double avg_speed_la) {
        this.avg_speed_la = avg_speed_la;
    }

    /**
     * @return the avg_speed_ve
     */
    public double getAvg_speed_ve() {
        return avg_speed_ve;
    }

    /**
     * @param avg_speed_ve the avg_speed_ve to set
     */
    public void setAvg_speed_ve(double avg_speed_ve) {
        this.avg_speed_ve = avg_speed_ve;
    }

    /**
     * @return the conditions_en
     */
    public double getConditions_en() {
        return conditions_en;
    }

    /**
     * @param conditions_en the conditions_en to set
     */
    public void setConditions_en(double conditions_en) {
        this.conditions_en = conditions_en;
    }

    /**
     * @return the conditions_la
     */
    public double getConditions_la() {
        return conditions_la;
    }

    /**
     * @param conditions_la the conditions_la to set
     */
    public void setConditions_la(double conditions_la) {
        this.conditions_la = conditions_la;
    }

    /**
     * @return the conditions_ve
     */
    public double getConditions_ve() {
        return conditions_ve;
    }

    /**
     * @param conditions_ve the conditions_ve to set
     */
    public void setConditions_ve(double conditions_ve) {
        this.conditions_ve = conditions_ve;
    }

    /**
     * @return the costs
     */
    public double getCosts() {
        return costs;
    }

    /**
     * @param costs the costs to set
     */
    public void setCosts(double costs) {
        this.costs = costs;
    }

    /**
     * @return the costs_maintenance
     */
    public double getCosts_maintenance() {
        return costs_maintenance;
    }

    /**
     * @param costs_maintenance the costs_maintenance to set
     */
    public void setCosts_maintenance(double costs_maintenance) {
        this.costs_maintenance = costs_maintenance;
    }

    /**
     * @return the costs_new
     */
    public double getCosts_new() {
        return costs_new;
    }

    /**
     * @param costs_new the costs_new to set
     */
    public void setCosts_new(double costs_new) {
        this.costs_new = costs_new;
    }

    /**
     * @return the costs_overhaul
     */
    public double getCosts_overhaul() {
        return costs_overhaul;
    }

    /**
     * @param costs_overhaul the costs_overhaul to set
     */
    public void setCosts_overhaul(double costs_overhaul) {
        this.costs_overhaul = costs_overhaul;
    }

    /**
     * @return the costs_repair
     */
    public double getCosts_repair() {
        return costs_repair;
    }

    /**
     * @param costs_repair the costs_repair to set
     */
    public void setCosts_repair(double costs_repair) {
        this.costs_repair = costs_repair;
    }

    /**
     * @return the currentValues
     */
    public double getCurrentValues() {
        return currentValues;
    }

    /**
     * @param currentValues the currentValues to set
     */
    public void setCurrentValues(double currentValues) {
        this.currentValues = currentValues;
    }

    /**
     * @return the speed_en
     */
    public double getSpeed_en() {
        return speed_en;
    }

    /**
     * @param speed_en the speed_en to set
     */
    public void setSpeed_en(double speed_en) {
        this.speed_en = speed_en;
    }

    /**
     * @return the speed_en_wfp
     */
    public double getSpeed_en_wfp() {
        return speed_en_wfp;
    }

    /**
     * @param speed_en_wfp the speed_en_wfp to set
     */
    public void setSpeed_en_wfp(double speed_en_wfp) {
        this.speed_en_wfp = speed_en_wfp;
    }

    /**
     * @return the speed_en_wofp
     */
    public double getSpeed_en_wofp() {
        return speed_en_wofp;
    }

    /**
     * @param speed_en_wofp the speed_en_wofp to set
     */
    public void setSpeed_en_wofp(double speed_en_wofp) {
        this.speed_en_wofp = speed_en_wofp;
    }

    /**
     * @return the speed_la
     */
    public double getSpeed_la() {
        return speed_la;
    }

    /**
     * @param speed_la the speed_la to set
     */
    public void setSpeed_la(double speed_la) {
        this.speed_la = speed_la;
    }

    /**
     * @return the speed_la_wfp
     */
    public double getSpeed_la_wfp() {
        return speed_la_wfp;
    }

    /**
     * @param speed_la_wfp the speed_la_wfp to set
     */
    public void setSpeed_la_wfp(double speed_la_wfp) {
        this.speed_la_wfp = speed_la_wfp;
    }

    /**
     * @return the speed_la_wofp
     */
    public double getSpeed_la_wofp() {
        return speed_la_wofp;
    }

    /**
     * @param speed_la_wofp the speed_la_wofp to set
     */
    public void setSpeed_la_wofp(double speed_la_wofp) {
        this.speed_la_wofp = speed_la_wofp;
    }

    /**
     * @return the speed_ve
     */
    public double getSpeed_ve() {
        return speed_ve;
    }

    /**
     * @param speed_ve the speed_ve to set
     */
    public void setSpeed_ve(double speed_ve) {
        this.speed_ve = speed_ve;
    }

    /**
     * @return the speed_ve_wfp
     */
    public double getSpeed_ve_wfp() {
        return speed_ve_wfp;
    }

    /**
     * @param speed_ve_wfp the speed_ve_wfp to set
     */
    public void setSpeed_ve_wfp(double speed_ve_wfp) {
        this.speed_ve_wfp = speed_ve_wfp;
    }

    /**
     * @return the speed_ve_wofp
     */
    public double getSpeed_ve_wofp() {
        return speed_ve_wofp;
    }

    /**
     * @param speed_ve_wofp the speed_ve_wofp to set
     */
    public void setSpeed_ve_wofp(double speed_ve_wofp) {
        this.speed_ve_wofp = speed_ve_wofp;
    }

    /**
     * @return the values_en
     */
    public double getValues_en() {
        return values_en;
    }

    /**
     * @param values_en the values_en to set
     */
    public void setValues_en(double values_en) {
        this.values_en = values_en;
    }

    /**
     * @return the values_la
     */
    public double getValues_la() {
        return values_la;
    }

    /**
     * @param values_la the values_la to set
     */
    public void setValues_la(double values_la) {
        this.values_la = values_la;
    }

    /**
     * @return the values_ve
     */
    public double getValues_ve() {
        return values_ve;
    }

    /**
     * @param values_ve the values_ve to set
     */
    public void setValues_ve(double values_ve) {
        this.values_ve = values_ve;
    }

    /**
     * @return the capacity_en
     */
    public int getCapacity_en() {
        return capacity_en;
    }

    /**
     * @param capacity_en the capacity_en to set
     */
    public void setCapacity_en(int capacity_en) {
        this.capacity_en = capacity_en;
    }

    /**
     * @return the capacity_en_wfp
     */
    public int getCapacity_en_wfp() {
        return capacity_en_wfp;
    }

    /**
     * @param capacity_en_wfp the capacity_en_wfp to set
     */
    public void setCapacity_en_wfp(int capacity_en_wfp) {
        this.capacity_en_wfp = capacity_en_wfp;
    }

    /**
     * @return the capacity_en_wofp
     */
    public int getCapacity_en_wofp() {
        return capacity_en_wofp;
    }

    /**
     * @param capacity_en_wofp the capacity_en_wofp to set
     */
    public void setCapacity_en_wofp(int capacity_en_wofp) {
        this.capacity_en_wofp = capacity_en_wofp;
    }

    /**
     * @return the capacity_la
     */
    public int getCapacity_la() {
        return capacity_la;
    }

    /**
     * @param capacity_la the capacity_la to set
     */
    public void setCapacity_la(int capacity_la) {
        this.capacity_la = capacity_la;
    }

    /**
     * @return the capacity_la_wfp
     */
    public int getCapacity_la_wfp() {
        return capacity_la_wfp;
    }

    /**
     * @param capacity_la_wfp the capacity_la_wfp to set
     */
    public void setCapacity_la_wfp(int capacity_la_wfp) {
        this.capacity_la_wfp = capacity_la_wfp;
    }

    /**
     * @return the capacity_la_wofp
     */
    public int getCapacity_la_wofp() {
        return capacity_la_wofp;
    }

    /**
     * @param capacity_la_wofp the capacity_la_wofp to set
     */
    public void setCapacity_la_wofp(int capacity_la_wofp) {
        this.capacity_la_wofp = capacity_la_wofp;
    }

    /**
     * @return the capacity_ve
     */
    public int getCapacity_ve() {
        return capacity_ve;
    }

    /**
     * @param capacity_ve the capacity_ve to set
     */
    public void setCapacity_ve(int capacity_ve) {
        this.capacity_ve = capacity_ve;
    }

    /**
     * @return the capacity_ve_wfp
     */
    public int getCapacity_ve_wfp() {
        return capacity_ve_wfp;
    }

    /**
     * @param capacity_ve_wfp the capacity_ve_wfp to set
     */
    public void setCapacity_ve_wfp(int capacity_ve_wfp) {
        this.capacity_ve_wfp = capacity_ve_wfp;
    }

    /**
     * @return the capacity_ve_wofp
     */
    public int getCapacity_ve_wofp() {
        return capacity_ve_wofp;
    }

    /**
     * @param capacity_ve_wofp the capacity_ve_wofp to set
     */
    public void setCapacity_ve_wofp(int capacity_ve_wofp) {
        this.capacity_ve_wofp = capacity_ve_wofp;
    }

    /**
     * @return the conveyor_count
     */
    public int getConveyor_count() {
        return conveyor_count;
    }

    /**
     * @param conveyor_count the conveyor_count to set
     */
    public void setConveyor_count(int conveyor_count) {
        this.conveyor_count = conveyor_count;
    }

    /**
     * @return the conveyor_count_en
     */
    public int getConveyor_count_en() {
        return conveyor_count_en;
    }

    /**
     * @param conveyor_count_en the conveyor_count_en to set
     */
    public void setConveyor_count_en(int conveyor_count_en) {
        this.conveyor_count_en = conveyor_count_en;
    }

    /**
     * @return the conveyor_count_en_wfp
     */
    public int getConveyor_count_en_wfp() {
        return conveyor_count_en_wfp;
    }

    /**
     * @param conveyor_count_en_wfp the conveyor_count_en_wfp to set
     */
    public void setConveyor_count_en_wfp(int conveyor_count_en_wfp) {
        this.conveyor_count_en_wfp = conveyor_count_en_wfp;
    }

    /**
     * @return the conveyor_count_en_wofp
     */
    public int getConveyor_count_en_wofp() {
        return conveyor_count_en_wofp;
    }

    /**
     * @param conveyor_count_en_wofp the conveyor_count_en_wofp to set
     */
    public void setConveyor_count_en_wofp(int conveyor_count_en_wofp) {
        this.conveyor_count_en_wofp = conveyor_count_en_wofp;
    }

    /**
     * @return the conveyor_count_la
     */
    public int getConveyor_count_la() {
        return conveyor_count_la;
    }

    /**
     * @param conveyor_count_la the conveyor_count_la to set
     */
    public void setConveyor_count_la(int conveyor_count_la) {
        this.conveyor_count_la = conveyor_count_la;
    }

    /**
     * @return the conveyor_count_la_wfp
     */
    public int getConveyor_count_la_wfp() {
        return conveyor_count_la_wfp;
    }

    /**
     * @param conveyor_count_la_wfp the conveyor_count_la_wfp to set
     */
    public void setConveyor_count_la_wfp(int conveyor_count_la_wfp) {
        this.conveyor_count_la_wfp = conveyor_count_la_wfp;
    }

    /**
     * @return the conveyor_count_la_wofp
     */
    public int getConveyor_count_la_wofp() {
        return conveyor_count_la_wofp;
    }

    /**
     * @param conveyor_count_la_wofp the conveyor_count_la_wofp to set
     */
    public void setConveyor_count_la_wofp(int conveyor_count_la_wofp) {
        this.conveyor_count_la_wofp = conveyor_count_la_wofp;
    }

    /**
     * @return the conveyor_count_ve
     */
    public int getConveyor_count_ve() {
        return conveyor_count_ve;
    }

    /**
     * @param conveyor_count_ve the conveyor_count_ve to set
     */
    public void setConveyor_count_ve(int conveyor_count_ve) {
        this.conveyor_count_ve = conveyor_count_ve;
    }

    /**
     * @return the conveyor_count_ve_wfp
     */
    public int getConveyor_count_ve_wfp() {
        return conveyor_count_ve_wfp;
    }

    /**
     * @param conveyor_count_ve_wfp the conveyor_count_ve_wfp to set
     */
    public void setConveyor_count_ve_wfp(int conveyor_count_ve_wfp) {
        this.conveyor_count_ve_wfp = conveyor_count_ve_wfp;
    }

    /**
     * @return the conveyor_count_ve_wofp
     */
    public int getConveyor_count_ve_wofp() {
        return conveyor_count_ve_wofp;
    }

    /**
     * @param conveyor_count_ve_wofp the conveyor_count_ve_wofp to set
     */
    public void setConveyor_count_ve_wofp(int conveyor_count_ve_wofp) {
        this.conveyor_count_ve_wofp = conveyor_count_ve_wofp;
    }

    /**
     * @return the repair_duration
     */
    public int getRepair_duration() {
        return repair_duration;
    }

    /**
     * @param repair_duration the repair_duration to set
     */
    public void setRepair_duration(int repair_duration) {
        this.repair_duration = repair_duration;
    }

    /**
     * @return the repair_duration_en
     */
    public int getRepair_duration_en() {
        return repair_duration_en;
    }

    /**
     * @param repair_duration_en the repair_duration_en to set
     */
    public void setRepair_duration_en(int repair_duration_en) {
        this.repair_duration_en = repair_duration_en;
    }

    /**
     * @return the repair_duration_la
     */
    public int getRepair_duration_la() {
        return repair_duration_la;
    }

    /**
     * @param repair_duration_la the repair_duration_la to set
     */
    public void setRepair_duration_la(int repair_duration_la) {
        this.repair_duration_la = repair_duration_la;
    }

    /**
     * @return the repair_duration_ve
     */
    public int getRepair_duration_ve() {
        return repair_duration_ve;
    }

    /**
     * @param repair_duration_ve the repair_duration_ve to set
     */
    public void setRepair_duration_ve(int repair_duration_ve) {
        this.repair_duration_ve = repair_duration_ve;
    }
}
