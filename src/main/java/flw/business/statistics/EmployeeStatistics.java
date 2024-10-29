/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.statistics;


import flw.business.core.DoloresGameInfo;
import java.io.Serializable;
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
public class EmployeeStatistics implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
   
    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    private DoloresGameInfo gameInfo;
    private int roundNumber;
    private int costs_new;
    private int fullTimeEmployeeCount;
    private int partTimeEmployeeCount;
    private int temporaryEmployeeCount;
    private int avg_motivation;
    private int capacity_en;
    private int capacity_en_wfp;
    private int capacity_en_wofp;
    private int capacity_la;
    private int capacity_la_wfp;
    private int capacity_la_wofp;
    private int capacity_ve;
    private int capacity_ve_wfp;
    private int capacity_ve_wofp;
    private int capacity_wk;
    private int capacity_wv;
    private int costs_emp;
    private int costs_compensation;
    private int costs_en;
    private int costs_ve;
    private int costs_la;
    private int costs_wk;
    private int costs_wv;
    private int costs_overtime;
    private int costs_overtime_en;
    private int costs_overtime_la;
    private int costs_overtime_ve;
    private int costs_overtime_wk;
    private int costs_overtime_wv;
    private int costs_salary;
    private int costs_salary_en;
    private int costs_salary_la;
    private int costs_salary_ve;
    private int costs_salary_wk;
    private int costs_salary_wv;
    private int costs_qualification;
    private int count_en;
    private int count_en_full;
    private int count_en_part;
    private int count_en_temporary;
    private int count_en_wfp;
    private int count_en_wofp;
    private int count_la;
    private int count_la_full;
    private int count_la_part;
    private int count_la_temporary;
    private int count_la_wfp;
    private int count_la_wofp;
    private int count_ve;
    private int count_ve_full;
    private int count_ve_part;
    private int count_ve_temporary;
    private int count_ve_wfp;
    private int count_ve_wofp;
    private int count_wk;
    private int count_wk_full;
    private int count_wk_part;
    private int count_wk_temporary;
    private int count_wk_wfp;
    private int count_wk_wofp;
    private int count_wv;
    private int count_wv_full;
    private int count_wv_part;
    private int count_wv_temporary;
    private int count_wv_wfp;
    private int count_wv_wofp;
    private int overall_count;
    private int working_time_en;
    private int working_time_en_wfp;
    private int working_time_en_wofp;
    private int working_time_la;
    private int working_time_la_wfp;
    private int working_time_la_wofp;
    private int working_time_ve;
    private int working_time_ve_wfp;
    private int working_time_ve_wofp;
    private int working_time_wk;
    private int working_time_wv;
    private double avg_crash_chance_en;
    private double avg_crash_chance_la;
    private double avg_crash_chance_ve;
    private double avg_cycletime_en;
    private double avg_cycletime_la;
    private double avg_cycletime_ve;
    private double avg_failure_rate_wk;
    private double avg_failure_rate_wv;
    private double avg_motivation_en;
    private double avg_motivation_la;
    private double avg_motivation_ve;
    private double avg_motivation_wk;
    private double avg_motivation_wv;
    private double crash_chance_en;
    private double crash_chance_la;
    private double crash_chance_ve;
    private double cycletime_en;
    private double cycletime_la;
    private double cycletime_ve;
    private double failure_factor_wk;
    private double failure_factor_wv;
    private double motivation_en;
    private double motivation_la;
    private double motivation_wk;
    private double motivation_wv;
    private double temporary_factor;
    private double temporary_factor_en;
    private double temporary_factor_la;
    private double temporary_factor_ve;
    private double temporary_factor_wk;
    private double temporary_factor_wv;
    //PalletThrouhputCalc
    private double workload_workers_wv;
    private double workload_workers_ve;
    private double workload_workers_en;
    private double workload_workers_wk;
    private double workload_conveyors_ve;
    private double workload_conveyors_la;
    private double workload_conveyors_en;
    private double workload_workers_la;

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
     * @return the costs_new
     */
    public int getCosts_new() {
        return costs_new;
    }

    /**
     * @param costs_new the costs_new to set
     */
    public void setCosts_new(int costs_new) {
        this.costs_new = costs_new;
    }

    /**
     * @return the fullTimeEmployeeCount
     */
    public int getFullTimeEmployeeCount() {
        return fullTimeEmployeeCount;
    }

    /**
     * @param fullTimeEmployeeCount the fullTimeEmployeeCount to set
     */
    public void setFullTimeEmployeeCount(int fullTimeEmployeeCount) {
        this.fullTimeEmployeeCount = fullTimeEmployeeCount;
    }

    /**
     * @return the partTimeEmployeeCount
     */
    public int getPartTimeEmployeeCount() {
        return partTimeEmployeeCount;
    }

    /**
     * @param partTimeEmployeeCount the partTimeEmployeeCount to set
     */
    public void setPartTimeEmployeeCount(int partTimeEmployeeCount) {
        this.partTimeEmployeeCount = partTimeEmployeeCount;
    }

    /**
     * @return the temporaryEmployeeCount
     */
    public int getTemporaryEmployeeCount() {
        return temporaryEmployeeCount;
    }

    /**
     * @param temporaryEmployeeCount the temporaryEmployeeCount to set
     */
    public void setTemporaryEmployeeCount(int temporaryEmployeeCount) {
        this.temporaryEmployeeCount = temporaryEmployeeCount;
    }

    /**
     * @return the avg_motivation
     */
    public int getAvg_motivation() {
        return avg_motivation;
    }

    /**
     * @param avg_motivation the avg_motivation to set
     */
    public void setAvg_motivation(int avg_motivation) {
        this.avg_motivation = avg_motivation;
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
     * @return the capacity_wk
     */
    public int getCapacity_wk() {
        return capacity_wk;
    }

    /**
     * @param capacity_wk the capacity_wk to set
     */
    public void setCapacity_wk(int capacity_wk) {
        this.capacity_wk = capacity_wk;
    }

    /**
     * @return the capacity_wv
     */
    public int getCapacity_wv() {
        return capacity_wv;
    }

    /**
     * @param capacity_wv the capacity_wv to set
     */
    public void setCapacity_wv(int capacity_wv) {
        this.capacity_wv = capacity_wv;
    }

    /**
     * @return the costs_emp
     */
    public int getCosts_emp() {
        return costs_emp;
    }

    /**
     * @param costs_emp the costs_emp to set
     */
    public void setCosts_emp(int costs_emp) {
        this.costs_emp = costs_emp;
    }

    /**
     * @return the costs_compensation
     */
    public int getCosts_compensation() {
        return costs_compensation;
    }

    /**
     * @param costs_compensation the costs_compensation to set
     */
    public void setCosts_compensation(int costs_compensation) {
        this.costs_compensation = costs_compensation;
    }

    /**
     * @return the costs_en
     */
    public int getCosts_en() {
        return costs_en;
    }

    /**
     * @param costs_en the costs_en to set
     */
    public void setCosts_en(int costs_en) {
        this.costs_en = costs_en;
    }

    /**
     * @return the costs_ve
     */
    public int getCosts_ve() {
        return costs_ve;
    }

    /**
     * @param costs_ve the costs_ve to set
     */
    public void setCosts_ve(int costs_ve) {
        this.costs_ve = costs_ve;
    }

    /**
     * @return the costs_la
     */
    public int getCosts_la() {
        return costs_la;
    }

    /**
     * @param costs_la the costs_la to set
     */
    public void setCosts_la(int costs_la) {
        this.costs_la = costs_la;
    }

    /**
     * @return the costs_wk
     */
    public int getCosts_wk() {
        return costs_wk;
    }

    /**
     * @param costs_wk the costs_wk to set
     */
    public void setCosts_wk(int costs_wk) {
        this.costs_wk = costs_wk;
    }

    /**
     * @return the costs_wv
     */
    public int getCosts_wv() {
        return costs_wv;
    }

    /**
     * @param costs_wv the costs_wv to set
     */
    public void setCosts_wv(int costs_wv) {
        this.costs_wv = costs_wv;
    }

    /**
     * @return the costs_overtime
     */
    public int getCosts_overtime() {
        return costs_overtime;
    }

    /**
     * @param costs_overtime the costs_overtime to set
     */
    public void setCosts_overtime(int costs_overtime) {
        this.costs_overtime = costs_overtime;
    }

    /**
     * @return the costs_overtime_en
     */
    public int getCosts_overtime_en() {
        return costs_overtime_en;
    }

    /**
     * @param costs_overtime_en the costs_overtime_en to set
     */
    public void setCosts_overtime_en(int costs_overtime_en) {
        this.costs_overtime_en = costs_overtime_en;
    }

    /**
     * @return the costs_overtime_la
     */
    public int getCosts_overtime_la() {
        return costs_overtime_la;
    }

    /**
     * @param costs_overtime_la the costs_overtime_la to set
     */
    public void setCosts_overtime_la(int costs_overtime_la) {
        this.costs_overtime_la = costs_overtime_la;
    }

    /**
     * @return the costs_overtime_ve
     */
    public int getCosts_overtime_ve() {
        return costs_overtime_ve;
    }

    /**
     * @param costs_overtime_ve the costs_overtime_ve to set
     */
    public void setCosts_overtime_ve(int costs_overtime_ve) {
        this.costs_overtime_ve = costs_overtime_ve;
    }

    /**
     * @return the costs_overtime_wk
     */
    public int getCosts_overtime_wk() {
        return costs_overtime_wk;
    }

    /**
     * @param costs_overtime_wk the costs_overtime_wk to set
     */
    public void setCosts_overtime_wk(int costs_overtime_wk) {
        this.costs_overtime_wk = costs_overtime_wk;
    }

    /**
     * @return the costs_overtime_wv
     */
    public int getCosts_overtime_wv() {
        return costs_overtime_wv;
    }

    /**
     * @param costs_overtime_wv the costs_overtime_wv to set
     */
    public void setCosts_overtime_wv(int costs_overtime_wv) {
        this.costs_overtime_wv = costs_overtime_wv;
    }

    /**
     * @return the costs_salary
     */
    public int getCosts_salary() {
        return costs_salary;
    }

    /**
     * @param costs_salary the costs_salary to set
     */
    public void setCosts_salary(int costs_salary) {
        this.costs_salary = costs_salary;
    }

    /**
     * @return the costs_salary_en
     */
    public int getCosts_salary_en() {
        return costs_salary_en;
    }

    /**
     * @param costs_salary_en the costs_salary_en to set
     */
    public void setCosts_salary_en(int costs_salary_en) {
        this.costs_salary_en = costs_salary_en;
    }

    /**
     * @return the costs_salary_la
     */
    public int getCosts_salary_la() {
        return costs_salary_la;
    }

    /**
     * @param costs_salary_la the costs_salary_la to set
     */
    public void setCosts_salary_la(int costs_salary_la) {
        this.costs_salary_la = costs_salary_la;
    }

    /**
     * @return the costs_salary_ve
     */
    public int getCosts_salary_ve() {
        return costs_salary_ve;
    }

    /**
     * @param costs_salary_ve the costs_salary_ve to set
     */
    public void setCosts_salary_ve(int costs_salary_ve) {
        this.costs_salary_ve = costs_salary_ve;
    }

    /**
     * @return the costs_salary_wk
     */
    public int getCosts_salary_wk() {
        return costs_salary_wk;
    }

    /**
     * @param costs_salary_wk the costs_salary_wk to set
     */
    public void setCosts_salary_wk(int costs_salary_wk) {
        this.costs_salary_wk = costs_salary_wk;
    }

    /**
     * @return the costs_salary_wv
     */
    public int getCosts_salary_wv() {
        return costs_salary_wv;
    }

    /**
     * @param costs_salary_wv the costs_salary_wv to set
     */
    public void setCosts_salary_wv(int costs_salary_wv) {
        this.costs_salary_wv = costs_salary_wv;
    }

    /**
     * @return the costs_qualification
     */
    public int getCosts_qualification() {
        return costs_qualification;
    }

    /**
     * @param costs_qualification the costs_qualification to set
     */
    public void setCosts_qualification(int costs_qualification) {
        this.costs_qualification = costs_qualification;
    }

    /**
     * @return the count_en
     */
    public int getCount_en() {
        return count_en;
    }

    /**
     * @param count_en the count_en to set
     */
    public void setCount_en(int count_en) {
        this.count_en = count_en;
    }

    /**
     * @return the count_en_full
     */
    public int getCount_en_full() {
        return count_en_full;
    }

    /**
     * @param count_en_full the count_en_full to set
     */
    public void setCount_en_full(int count_en_full) {
        this.count_en_full = count_en_full;
    }

    /**
     * @return the count_en_part
     */
    public int getCount_en_part() {
        return count_en_part;
    }

    /**
     * @param count_en_part the count_en_part to set
     */
    public void setCount_en_part(int count_en_part) {
        this.count_en_part = count_en_part;
    }

    /**
     * @return the count_en_temporary
     */
    public int getCount_en_temporary() {
        return count_en_temporary;
    }

    /**
     * @param count_en_temporary the count_en_temporary to set
     */
    public void setCount_en_temporary(int count_en_temporary) {
        this.count_en_temporary = count_en_temporary;
    }

    /**
     * @return the count_en_wfp
     */
    public int getCount_en_wfp() {
        return count_en_wfp;
    }

    /**
     * @param count_en_wfp the count_en_wfp to set
     */
    public void setCount_en_wfp(int count_en_wfp) {
        this.count_en_wfp = count_en_wfp;
    }

    /**
     * @return the count_en_wofp
     */
    public int getCount_en_wofp() {
        return count_en_wofp;
    }

    /**
     * @param count_en_wofp the count_en_wofp to set
     */
    public void setCount_en_wofp(int count_en_wofp) {
        this.count_en_wofp = count_en_wofp;
    }

    /**
     * @return the count_la
     */
    public int getCount_la() {
        return count_la;
    }

    /**
     * @param count_la the count_la to set
     */
    public void setCount_la(int count_la) {
        this.count_la = count_la;
    }

    /**
     * @return the count_la_full
     */
    public int getCount_la_full() {
        return count_la_full;
    }

    /**
     * @param count_la_full the count_la_full to set
     */
    public void setCount_la_full(int count_la_full) {
        this.count_la_full = count_la_full;
    }

    /**
     * @return the count_la_part
     */
    public int getCount_la_part() {
        return count_la_part;
    }

    /**
     * @param count_la_part the count_la_part to set
     */
    public void setCount_la_part(int count_la_part) {
        this.count_la_part = count_la_part;
    }

    /**
     * @return the count_la_temporary
     */
    public int getCount_la_temporary() {
        return count_la_temporary;
    }

    /**
     * @param count_la_temporary the count_la_temporary to set
     */
    public void setCount_la_temporary(int count_la_temporary) {
        this.count_la_temporary = count_la_temporary;
    }

    /**
     * @return the count_la_wfp
     */
    public int getCount_la_wfp() {
        return count_la_wfp;
    }

    /**
     * @param count_la_wfp the count_la_wfp to set
     */
    public void setCount_la_wfp(int count_la_wfp) {
        this.count_la_wfp = count_la_wfp;
    }

    /**
     * @return the count_la_wofp
     */
    public int getCount_la_wofp() {
        return count_la_wofp;
    }

    /**
     * @param count_la_wofp the count_la_wofp to set
     */
    public void setCount_la_wofp(int count_la_wofp) {
        this.count_la_wofp = count_la_wofp;
    }

    /**
     * @return the count_ve
     */
    public int getCount_ve() {
        return count_ve;
    }

    /**
     * @param count_ve the count_ve to set
     */
    public void setCount_ve(int count_ve) {
        this.count_ve = count_ve;
    }

    /**
     * @return the count_ve_full
     */
    public int getCount_ve_full() {
        return count_ve_full;
    }

    /**
     * @param count_ve_full the count_ve_full to set
     */
    public void setCount_ve_full(int count_ve_full) {
        this.count_ve_full = count_ve_full;
    }

    /**
     * @return the count_ve_part
     */
    public int getCount_ve_part() {
        return count_ve_part;
    }

    /**
     * @param count_ve_part the count_ve_part to set
     */
    public void setCount_ve_part(int count_ve_part) {
        this.count_ve_part = count_ve_part;
    }

    /**
     * @return the count_ve_temporary
     */
    public int getCount_ve_temporary() {
        return count_ve_temporary;
    }

    /**
     * @param count_ve_temporary the count_ve_temporary to set
     */
    public void setCount_ve_temporary(int count_ve_temporary) {
        this.count_ve_temporary = count_ve_temporary;
    }

    /**
     * @return the count_ve_wfp
     */
    public int getCount_ve_wfp() {
        return count_ve_wfp;
    }

    /**
     * @param count_ve_wfp the count_ve_wfp to set
     */
    public void setCount_ve_wfp(int count_ve_wfp) {
        this.count_ve_wfp = count_ve_wfp;
    }

    /**
     * @return the count_ve_wofp
     */
    public int getCount_ve_wofp() {
        return count_ve_wofp;
    }

    /**
     * @param count_ve_wofp the count_ve_wofp to set
     */
    public void setCount_ve_wofp(int count_ve_wofp) {
        this.count_ve_wofp = count_ve_wofp;
    }

    /**
     * @return the count_wk
     */
    public int getCount_wk() {
        return count_wk;
    }

    /**
     * @param count_wk the count_wk to set
     */
    public void setCount_wk(int count_wk) {
        this.count_wk = count_wk;
    }

    /**
     * @return the count_wk_full
     */
    public int getCount_wk_full() {
        return count_wk_full;
    }

    /**
     * @param count_wk_full the count_wk_full to set
     */
    public void setCount_wk_full(int count_wk_full) {
        this.count_wk_full = count_wk_full;
    }

    /**
     * @return the count_wk_part
     */
    public int getCount_wk_part() {
        return count_wk_part;
    }

    /**
     * @param count_wk_part the count_wk_part to set
     */
    public void setCount_wk_part(int count_wk_part) {
        this.count_wk_part = count_wk_part;
    }

    /**
     * @return the count_wk_temporary
     */
    public int getCount_wk_temporary() {
        return count_wk_temporary;
    }

    /**
     * @param count_wk_temporary the count_wk_temporary to set
     */
    public void setCount_wk_temporary(int count_wk_temporary) {
        this.count_wk_temporary = count_wk_temporary;
    }

    /**
     * @return the count_wk_wfp
     */
    public int getCount_wk_wfp() {
        return count_wk_wfp;
    }

    /**
     * @param count_wk_wfp the count_wk_wfp to set
     */
    public void setCount_wk_wfp(int count_wk_wfp) {
        this.count_wk_wfp = count_wk_wfp;
    }

    /**
     * @return the count_wk_wofp
     */
    public int getCount_wk_wofp() {
        return count_wk_wofp;
    }

    /**
     * @param count_wk_wofp the count_wk_wofp to set
     */
    public void setCount_wk_wofp(int count_wk_wofp) {
        this.count_wk_wofp = count_wk_wofp;
    }

    /**
     * @return the count_wv
     */
    public int getCount_wv() {
        return count_wv;
    }

    /**
     * @param count_wv the count_wv to set
     */
    public void setCount_wv(int count_wv) {
        this.count_wv = count_wv;
    }

    /**
     * @return the count_wv_full
     */
    public int getCount_wv_full() {
        return count_wv_full;
    }

    /**
     * @param count_wv_full the count_wv_full to set
     */
    public void setCount_wv_full(int count_wv_full) {
        this.count_wv_full = count_wv_full;
    }

    /**
     * @return the count_wv_part
     */
    public int getCount_wv_part() {
        return count_wv_part;
    }

    /**
     * @param count_wv_part the count_wv_part to set
     */
    public void setCount_wv_part(int count_wv_part) {
        this.count_wv_part = count_wv_part;
    }

    /**
     * @return the count_wv_temporary
     */
    public int getCount_wv_temporary() {
        return count_wv_temporary;
    }

    /**
     * @param count_wv_temporary the count_wv_temporary to set
     */
    public void setCount_wv_temporary(int count_wv_temporary) {
        this.count_wv_temporary = count_wv_temporary;
    }

    /**
     * @return the count_wv_wfp
     */
    public int getCount_wv_wfp() {
        return count_wv_wfp;
    }

    /**
     * @param count_wv_wfp the count_wv_wfp to set
     */
    public void setCount_wv_wfp(int count_wv_wfp) {
        this.count_wv_wfp = count_wv_wfp;
    }

    /**
     * @return the count_wv_wofp
     */
    public int getCount_wv_wofp() {
        return count_wv_wofp;
    }

    /**
     * @param count_wv_wofp the count_wv_wofp to set
     */
    public void setCount_wv_wofp(int count_wv_wofp) {
        this.count_wv_wofp = count_wv_wofp;
    }

    /**
     * @return the overall_count
     */
    public int getOverall_count() {
        return overall_count;
    }

    /**
     * @param overall_count the overall_count to set
     */
    public void setOverall_count(int overall_count) {
        this.overall_count = overall_count;
    }

    /**
     * @return the working_time_en
     */
    public int getWorking_time_en() {
        return working_time_en;
    }

    /**
     * @param working_time_en the working_time_en to set
     */
    public void setWorking_time_en(int working_time_en) {
        this.working_time_en = working_time_en;
    }

    /**
     * @return the working_time_en_wfp
     */
    public int getWorking_time_en_wfp() {
        return working_time_en_wfp;
    }

    /**
     * @param working_time_en_wfp the working_time_en_wfp to set
     */
    public void setWorking_time_en_wfp(int working_time_en_wfp) {
        this.working_time_en_wfp = working_time_en_wfp;
    }

    /**
     * @return the working_time_en_wofp
     */
    public int getWorking_time_en_wofp() {
        return working_time_en_wofp;
    }

    /**
     * @param working_time_en_wofp the working_time_en_wofp to set
     */
    public void setWorking_time_en_wofp(int working_time_en_wofp) {
        this.working_time_en_wofp = working_time_en_wofp;
    }

    /**
     * @return the working_time_la
     */
    public int getWorking_time_la() {
        return working_time_la;
    }

    /**
     * @param working_time_la the working_time_la to set
     */
    public void setWorking_time_la(int working_time_la) {
        this.working_time_la = working_time_la;
    }

    /**
     * @return the working_time_la_wfp
     */
    public int getWorking_time_la_wfp() {
        return working_time_la_wfp;
    }

    /**
     * @param working_time_la_wfp the working_time_la_wfp to set
     */
    public void setWorking_time_la_wfp(int working_time_la_wfp) {
        this.working_time_la_wfp = working_time_la_wfp;
    }

    /**
     * @return the working_time_la_wofp
     */
    public int getWorking_time_la_wofp() {
        return working_time_la_wofp;
    }

    /**
     * @param working_time_la_wofp the working_time_la_wofp to set
     */
    public void setWorking_time_la_wofp(int working_time_la_wofp) {
        this.working_time_la_wofp = working_time_la_wofp;
    }

    /**
     * @return the working_time_ve
     */
    public int getWorking_time_ve() {
        return working_time_ve;
    }

    /**
     * @param working_time_ve the working_time_ve to set
     */
    public void setWorking_time_ve(int working_time_ve) {
        this.working_time_ve = working_time_ve;
    }

    /**
     * @return the working_time_ve_wfp
     */
    public int getWorking_time_ve_wfp() {
        return working_time_ve_wfp;
    }

    /**
     * @param working_time_ve_wfp the working_time_ve_wfp to set
     */
    public void setWorking_time_ve_wfp(int working_time_ve_wfp) {
        this.working_time_ve_wfp = working_time_ve_wfp;
    }

    /**
     * @return the working_time_ve_wofp
     */
    public int getWorking_time_ve_wofp() {
        return working_time_ve_wofp;
    }

    /**
     * @param working_time_ve_wofp the working_time_ve_wofp to set
     */
    public void setWorking_time_ve_wofp(int working_time_ve_wofp) {
        this.working_time_ve_wofp = working_time_ve_wofp;
    }

    /**
     * @return the working_time_wk
     */
    public int getWorking_time_wk() {
        return working_time_wk;
    }

    /**
     * @param working_time_wk the working_time_wk to set
     */
    public void setWorking_time_wk(int working_time_wk) {
        this.working_time_wk = working_time_wk;
    }

    /**
     * @return the working_time_wv
     */
    public int getWorking_time_wv() {
        return working_time_wv;
    }

    /**
     * @param working_time_wv the working_time_wv to set
     */
    public void setWorking_time_wv(int working_time_wv) {
        this.working_time_wv = working_time_wv;
    }

    /**
     * @return the avg_crash_chance_en
     */
    public double getAvg_crash_chance_en() {
        return avg_crash_chance_en;
    }

    /**
     * @param avg_crash_chance_en the avg_crash_chance_en to set
     */
    public void setAvg_crash_chance_en(double avg_crash_chance_en) {
        this.avg_crash_chance_en = avg_crash_chance_en;
    }

    /**
     * @return the avg_crash_chance_la
     */
    public double getAvg_crash_chance_la() {
        return avg_crash_chance_la;
    }

    /**
     * @param avg_crash_chance_la the avg_crash_chance_la to set
     */
    public void setAvg_crash_chance_la(double avg_crash_chance_la) {
        this.avg_crash_chance_la = avg_crash_chance_la;
    }

    /**
     * @return the avg_crash_chance_ve
     */
    public double getAvg_crash_chance_ve() {
        return avg_crash_chance_ve;
    }

    /**
     * @param avg_crash_chance_ve the avg_crash_chance_ve to set
     */
    public void setAvg_crash_chance_ve(double avg_crash_chance_ve) {
        this.avg_crash_chance_ve = avg_crash_chance_ve;
    }

    /**
     * @return the avg_cycletime_en
     */
    public double getAvg_cycletime_en() {
        return avg_cycletime_en;
    }

    /**
     * @param avg_cycletime_en the avg_cycletime_en to set
     */
    public void setAvg_cycletime_en(double avg_cycletime_en) {
        this.avg_cycletime_en = avg_cycletime_en;
    }

    /**
     * @return the avg_cycletime_la
     */
    public double getAvg_cycletime_la() {
        return avg_cycletime_la;
    }

    /**
     * @param avg_cycletime_la the avg_cycletime_la to set
     */
    public void setAvg_cycletime_la(double avg_cycletime_la) {
        this.avg_cycletime_la = avg_cycletime_la;
    }

    /**
     * @return the avg_cycletime_ve
     */
    public double getAvg_cycletime_ve() {
        return avg_cycletime_ve;
    }

    /**
     * @param avg_cycletime_ve the avg_cycletime_ve to set
     */
    public void setAvg_cycletime_ve(double avg_cycletime_ve) {
        this.avg_cycletime_ve = avg_cycletime_ve;
    }

    /**
     * @return the avg_failure_rate_wk
     */
    public double getAvg_failure_rate_wk() {
        return avg_failure_rate_wk;
    }

    /**
     * @param avg_failure_rate_wk the avg_failure_rate_wk to set
     */
    public void setAvg_failure_rate_wk(double avg_failure_rate_wk) {
        this.avg_failure_rate_wk = avg_failure_rate_wk;
    }

    /**
     * @return the avg_failure_rate_wv
     */
    public double getAvg_failure_rate_wv() {
        return avg_failure_rate_wv;
    }

    /**
     * @param avg_failure_rate_wv the avg_failure_rate_wv to set
     */
    public void setAvg_failure_rate_wv(double avg_failure_rate_wv) {
        this.avg_failure_rate_wv = avg_failure_rate_wv;
    }

    /**
     * @return the avg_motivation_en
     */
    public double getAvg_motivation_en() {
        return avg_motivation_en;
    }

    /**
     * @param avg_motivation_en the avg_motivation_en to set
     */
    public void setAvg_motivation_en(double avg_motivation_en) {
        this.avg_motivation_en = avg_motivation_en;
    }

    /**
     * @return the avg_motivation_la
     */
    public double getAvg_motivation_la() {
        return avg_motivation_la;
    }

    /**
     * @param avg_motivation_la the avg_motivation_la to set
     */
    public void setAvg_motivation_la(double avg_motivation_la) {
        this.avg_motivation_la = avg_motivation_la;
    }

    /**
     * @return the avg_motivation_ve
     */
    public double getAvg_motivation_ve() {
        return avg_motivation_ve;
    }

    /**
     * @param avg_motivation_ve the avg_motivation_ve to set
     */
    public void setAvg_motivation_ve(double avg_motivation_ve) {
        this.avg_motivation_ve = avg_motivation_ve;
    }

    /**
     * @return the avg_motivation_wk
     */
    public double getAvg_motivation_wk() {
        return avg_motivation_wk;
    }

    /**
     * @param avg_motivation_wk the avg_motivation_wk to set
     */
    public void setAvg_motivation_wk(double avg_motivation_wk) {
        this.avg_motivation_wk = avg_motivation_wk;
    }

    /**
     * @return the avg_motivation_wv
     */
    public double getAvg_motivation_wv() {
        return avg_motivation_wv;
    }

    /**
     * @param avg_motivation_wv the avg_motivation_wv to set
     */
    public void setAvg_motivation_wv(double avg_motivation_wv) {
        this.avg_motivation_wv = avg_motivation_wv;
    }

    /**
     * @return the crash_chance_en
     */
    public double getCrash_chance_en() {
        return crash_chance_en;
    }

    /**
     * @param crash_chance_en the crash_chance_en to set
     */
    public void setCrash_chance_en(double crash_chance_en) {
        this.crash_chance_en = crash_chance_en;
    }

    /**
     * @return the crash_chance_la
     */
    public double getCrash_chance_la() {
        return crash_chance_la;
    }

    /**
     * @param crash_chance_la the crash_chance_la to set
     */
    public void setCrash_chance_la(double crash_chance_la) {
        this.crash_chance_la = crash_chance_la;
    }

    /**
     * @return the crash_chance_ve
     */
    public double getCrash_chance_ve() {
        return crash_chance_ve;
    }

    /**
     * @param crash_chance_ve the crash_chance_ve to set
     */
    public void setCrash_chance_ve(double crash_chance_ve) {
        this.crash_chance_ve = crash_chance_ve;
    }

    /**
     * @return the cycletime_en
     */
    public double getCycletime_en() {
        return cycletime_en;
    }

    /**
     * @param cycletime_en the cycletime_en to set
     */
    public void setCycletime_en(double cycletime_en) {
        this.cycletime_en = cycletime_en;
    }

    /**
     * @return the cycletime_la
     */
    public double getCycletime_la() {
        return cycletime_la;
    }

    /**
     * @param cycletime_la the cycletime_la to set
     */
    public void setCycletime_la(double cycletime_la) {
        this.cycletime_la = cycletime_la;
    }

    /**
     * @return the cycletime_ve
     */
    public double getCycletime_ve() {
        return cycletime_ve;
    }

    /**
     * @param cycletime_ve the cycletime_ve to set
     */
    public void setCycletime_ve(double cycletime_ve) {
        this.cycletime_ve = cycletime_ve;
    }

    /**
     * @return the failure_factor_wk
     */
    public double getFailure_factor_wk() {
        return failure_factor_wk;
    }

    /**
     * @param failure_factor_wk the failure_factor_wk to set
     */
    public void setFailure_factor_wk(double failure_factor_wk) {
        this.failure_factor_wk = failure_factor_wk;
    }

    /**
     * @return the failure_factor_wv
     */
    public double getFailure_factor_wv() {
        return failure_factor_wv;
    }

    /**
     * @param failure_factor_wv the failure_factor_wv to set
     */
    public void setFailure_factor_wv(double failure_factor_wv) {
        this.failure_factor_wv = failure_factor_wv;
    }

    /**
     * @return the motivation_en
     */
    public double getMotivation_en() {
        return motivation_en;
    }

    /**
     * @param motivation_en the motivation_en to set
     */
    public void setMotivation_en(double motivation_en) {
        this.motivation_en = motivation_en;
    }

    /**
     * @return the motivation_la
     */
    public double getMotivation_la() {
        return motivation_la;
    }

    /**
     * @param motivation_la the motivation_la to set
     */
    public void setMotivation_la(double motivation_la) {
        this.motivation_la = motivation_la;
    }

    /**
     * @return the motivation_wk
     */
    public double getMotivation_wk() {
        return motivation_wk;
    }

    /**
     * @param motivation_wk the motivation_wk to set
     */
    public void setMotivation_wk(double motivation_wk) {
        this.motivation_wk = motivation_wk;
    }

    /**
     * @return the motivation_wv
     */
    public double getMotivation_wv() {
        return motivation_wv;
    }

    /**
     * @param motivation_wv the motivation_wv to set
     */
    public void setMotivation_wv(double motivation_wv) {
        this.motivation_wv = motivation_wv;
    }

    /**
     * @return the temporary_factor
     */
    public double getTemporary_factor() {
        return temporary_factor;
    }

    /**
     * @param temporary_factor the temporary_factor to set
     */
    public void setTemporary_factor(double temporary_factor) {
        this.temporary_factor = temporary_factor;
    }

    /**
     * @return the temporary_factor_en
     */
    public double getTemporary_factor_en() {
        return temporary_factor_en;
    }

    /**
     * @param temporary_factor_en the temporary_factor_en to set
     */
    public void setTemporary_factor_en(double temporary_factor_en) {
        this.temporary_factor_en = temporary_factor_en;
    }

    /**
     * @return the temporary_factor_la
     */
    public double getTemporary_factor_la() {
        return temporary_factor_la;
    }

    /**
     * @param temporary_factor_la the temporary_factor_la to set
     */
    public void setTemporary_factor_la(double temporary_factor_la) {
        this.temporary_factor_la = temporary_factor_la;
    }

    /**
     * @return the temporary_factor_ve
     */
    public double getTemporary_factor_ve() {
        return temporary_factor_ve;
    }

    /**
     * @param temporary_factor_ve the temporary_factor_ve to set
     */
    public void setTemporary_factor_ve(double temporary_factor_ve) {
        this.temporary_factor_ve = temporary_factor_ve;
    }

    /**
     * @return the temporary_factor_wk
     */
    public double getTemporary_factor_wk() {
        return temporary_factor_wk;
    }

    /**
     * @param temporary_factor_wk the temporary_factor_wk to set
     */
    public void setTemporary_factor_wk(double temporary_factor_wk) {
        this.temporary_factor_wk = temporary_factor_wk;
    }

    /**
     * @return the temporary_factor_wv
     */
    public double getTemporary_factor_wv() {
        return temporary_factor_wv;
    }

    /**
     * @param temporary_factor_wv the temporary_factor_wv to set
     */
    public void setTemporary_factor_wv(double temporary_factor_wv) {
        this.temporary_factor_wv = temporary_factor_wv;
    }

    /**
     * @return the workload_workers_wv
     */
    public double getWorkload_workers_wv() {
        return workload_workers_wv;
    }

    /**
     * @param workload_workers_wv the workload_workers_wv to set
     */
    public void setWorkload_workers_wv(double workload_workers_wv) {
        this.workload_workers_wv = workload_workers_wv;
    }

    /**
     * @return the workload_workers_ve
     */
    public double getWorkload_workers_ve() {
        return workload_workers_ve;
    }

    /**
     * @param workload_workers_ve the workload_workers_ve to set
     */
    public void setWorkload_workers_ve(double workload_workers_ve) {
        this.workload_workers_ve = workload_workers_ve;
    }

    /**
     * @return the workload_workers_en
     */
    public double getWorkload_workers_en() {
        return workload_workers_en;
    }

    /**
     * @param workload_workers_en the workload_workers_en to set
     */
    public void setWorkload_workers_en(double workload_workers_en) {
        this.workload_workers_en = workload_workers_en;
    }

    /**
     * @return the workload_workers_wk
     */
    public double getWorkload_workers_wk() {
        return workload_workers_wk;
    }

    /**
     * @param workload_workers_wk the workload_workers_wk to set
     */
    public void setWorkload_workers_wk(double workload_workers_wk) {
        this.workload_workers_wk = workload_workers_wk;
    }

    /**
     * @return the workload_conveyors_ve
     */
    public double getWorkload_conveyors_ve() {
        return workload_conveyors_ve;
    }

    /**
     * @param workload_conveyors_ve the workload_conveyors_ve to set
     */
    public void setWorkload_conveyors_ve(double workload_conveyors_ve) {
        this.workload_conveyors_ve = workload_conveyors_ve;
    }

    /**
     * @return the workload_conveyors_la
     */
    public double getWorkload_conveyors_la() {
        return workload_conveyors_la;
    }

    /**
     * @param workload_conveyors_la the workload_conveyors_la to set
     */
    public void setWorkload_conveyors_la(double workload_conveyors_la) {
        this.workload_conveyors_la = workload_conveyors_la;
    }

    /**
     * @return the workload_conveyors_en
     */
    public double getWorkload_conveyors_en() {
        return workload_conveyors_en;
    }

    /**
     * @param workload_conveyors_en the workload_conveyors_en to set
     */
    public void setWorkload_conveyors_en(double workload_conveyors_en) {
        this.workload_conveyors_en = workload_conveyors_en;
    }

    /**
     * @return the workload_workers_la
     */
    public double getWorkload_workers_la() {
        return workload_workers_la;
    }

    /**
     * @param workload_workers_la the workload_workers_la to set
     */
    public void setWorkload_workers_la(double workload_workers_la) {
        this.workload_workers_la = workload_workers_la;
    }
}