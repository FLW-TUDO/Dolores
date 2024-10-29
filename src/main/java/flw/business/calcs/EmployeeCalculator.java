/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.calcs;

import flw.business.core.DoloresGameInfo;
import flw.business.statistics.EmployeeStatistics;
import flw.business.store.EmployeeDynamics;
import flw.business.util.DoloresConst;
import flw.business.util.Processes;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tilu
 */
public class EmployeeCalculator extends AbstractCalculator {

    private int fullTimeEmployeeCount;
    private int partTimeEmployeeCount;
    private int temporaryEmployeeCount;
    private Map<String, Double> mDoubles = new TreeMap<String, Double>();
    private Map<String, Integer> mInts = new TreeMap<String, Integer>();
    private List<String> messages = new ArrayList<String>();
    private EmployeeStatistics empStat;

    /**
     * @return Overall count of fulltime Employees
     */
    public int getFullTimeEmployeeCount() {
        return fullTimeEmployeeCount;
    }

    /**
     *
     * @return Overall count of Part-time Employees
     */
    public int getPartTimeEmployeeCount() {
        return partTimeEmployeeCount;
    }

    /**
     *
     * @return Overall count of Temporary Employees
     */
    public int getTemporaryEmployeeCount() {
        return temporaryEmployeeCount;
    }

    public EmployeeStatistics getEmpStat() {
        return empStat;
    }

    public void setEmpStat(EmployeeStatistics empStat) {
        this.empStat = empStat;
    }

    public EmployeeCalculator(List<AbstractCalculator> lCalculators) {
        super(lCalculators);

        empStat = new EmployeeStatistics();

        //instanciate variables for every process
        for (String abbr : Processes.getInstance().getProcessAbbrevations()) {
            mInts.put(new StringBuilder("count_").append(abbr).append("_wfp").toString(), 0);



            mInts.put(new StringBuilder("count_").append(abbr).append("_wofp").toString(), 0);
            mInts.put(new StringBuilder("count_").append(abbr).append("_full").toString(), 0);
            mInts.put(new StringBuilder("count_").append(abbr).append("_part").toString(), 0);
            mInts.put(new StringBuilder("count_").append(abbr).append("_temporary").toString(), 0);
            mInts.put(new StringBuilder("count_").append(abbr).toString(), 0);
            if (Processes.getInstance().hasProcessConveyors(abbr)) {
                mDoubles.put(new StringBuilder("cycletime_").append(abbr).toString(), 0d);
                mDoubles.put(new StringBuilder("crash_chance_").append(abbr).toString(), 0d);
                mInts.put(new StringBuilder("capacity_").append(abbr).append("_wfp").toString(), 0);
                mInts.put(new StringBuilder("capacity_").append(abbr).append("_wofp").toString(), 0);
                mInts.put(new StringBuilder("working_time_").append(abbr).append("_wofp").toString(), 0);
                mInts.put(new StringBuilder("working_time_").append(abbr).append("_wfp").toString(), 0);
                mInts.put(new StringBuilder("working_time_").append(abbr).toString(), 0);
            } else {
                mDoubles.put(new StringBuilder("failure_factor_").append(abbr).toString(), 0d);
                mInts.put(new StringBuilder("working_time_").append(abbr).toString(), 0);
            }
            mInts.put(new StringBuilder("capacity_").append(abbr).toString(), 0);
            mInts.put(new StringBuilder("working_time_").append(abbr).toString(), 0);
            mDoubles.put(new StringBuilder("motivation_").append(abbr).toString(), 0d);
            mDoubles.put(new StringBuilder("avg_motivation_").append(abbr).toString(), 0d);
            mInts.put(new StringBuilder("costs_overtime_").append(abbr).toString(), 0);
            mInts.put(new StringBuilder("costs_salary_").append(abbr).toString(), 0);
            mInts.put(new StringBuilder("costs_").append(abbr).toString(), 0);
        }
        //initialise global variables
        mInts.put("overall_count", 0);
        mInts.put("avg_motivation", 0);
        mInts.put("costs_overtime", 0);
        mInts.put("costs_salary", 0);
        mInts.put("costs_compensation", 0);
        mInts.put("costs_emp", 0);
        mInts.put("costs_new", 0);
    }

    @Override
    public void calculate(DoloresGameInfo gameInfo) {
        //Get num of current periode
        int aktround = Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER));

        empStat.setRoundNumber(aktround);



        for (EmployeeDynamics employee : gameInfo.getCurrentState().getEmployeeDynamics()) {
            //If Employee is working for company currently

            if (employee.getEmployee().getEmploymentPeriode() == aktround + 1) {

                if (employee.getContractType() == DoloresConst.EMPLOYEE_CONTRACT_FULL_TIME || employee.getContractType() == DoloresConst.EMPLOYEE_CONTRACT_HALF_TIME) {
                    gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_COSTS_NEW_EMPLOYEES,
                            String.valueOf(Integer.valueOf(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_COSTS_NEW_EMPLOYEES)) + DoloresConst.EMPLOYEE_COST_NEW_FULLTIME));
                } else if (employee.getContractType() == DoloresConst.EMPLOYEE_CONTRACT_TEMPORARY) {
                    gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_COSTS_NEW_EMPLOYEES,
                            String.valueOf(Integer.valueOf(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_COSTS_NEW_EMPLOYEES)) + DoloresConst.EMPLOYEE_COST_NEW_TEMPORARY));
                }

            }


            if (employee.getEmployee().isReady(aktround)) {
                boolean validContract = true;
                //Get abbrevation for Process the employee is working in
                String processOfEmployee = Processes.getInstance().getProcessAbbrevations()[employee.getProcess()];

                //Count Employee for full, half or temporary Workers
                if (employee.getContractType() == DoloresConst.EMPLOYEE_CONTRACT_FULL_TIME) {   //Full time worker
                    String key = new StringBuilder("count_").append(processOfEmployee).append("_full").toString();
                    mInts.put(key, mInts.get(key) + 1);
                } else if (employee.getContractType() == DoloresConst.EMPLOYEE_CONTRACT_HALF_TIME) {   //Half time worker
                    String key = new StringBuilder("count_").append(processOfEmployee).append("_part").toString();
                    mInts.put(key, mInts.get(key) + 1);
                } else if (employee.getContractType() == DoloresConst.EMPLOYEE_CONTRACT_TEMPORARY) {   //temporary worker
                    String key = new StringBuilder("count_").append(processOfEmployee).append("_temporary").toString();
                    mInts.put(key, mInts.get(key) + 1);
                } else {
                    messages.add(new StringBuilder(DoloresConst.EMPLOYEE_NOTIFICATION_ILLEGAL_CONTRACT).append("-").append(employee.getFirstName()).append("-").append(employee.getLastName()).toString());
                    //NotificationService.addNotification(String.format(NotificationMessages.getInstance().getMessage(DoloresConst.EMPLOYEE_NOTIFICATION_ILLEGAL_CONTRACT), employee.getFirstName(), employee.getLastName(), employee.getId()));
                    validContract = false;
                }

                if (Processes.getInstance().hasProcessConveyors(processOfEmployee)) {   //Employee works in Process with Conveyors

                    //Count Employee for Workers with Forkliftpermit or without
                    if (employee.hasForkliftPermit() && validContract) {	//employee has a forklift permit

                        //count employee to sum of workers with forklift permit of process related to employee
                        String key = new StringBuilder("count_").append(processOfEmployee).append("_wfp").toString();
                        mInts.put(key, mInts.get(key) + 1);

                        //calculate cycletime and crash_chance to sum process related to employee
                        if (employee.hasSecurityTraining()) {
                            key = new StringBuilder("cycletime_").append(processOfEmployee).toString();
                            mDoubles.put(key, mDoubles.get(key) + DoloresConst.EMPLOYEE_FACTOR_CYCLETIME_SECURITY_TRAINING);
                            key = new StringBuilder("crash_chance_").append(processOfEmployee).toString();
                            mDoubles.put(key, mDoubles.get(key) + DoloresConst.EMPLOYEE_FACTOR_CRASHCHANCE_SECURITY_TRAINING);
                        } else {
                            key = new StringBuilder("cycletime_").append(processOfEmployee).toString();
                            mDoubles.put(key, mDoubles.get(key) + DoloresConst.EMPLOYEE_FACTOR_CYCLETIME_FORKLIFT_PERMIT);
                            key = new StringBuilder("crash_chance_").append(processOfEmployee).toString();
                            mDoubles.put(key, mDoubles.get(key) + DoloresConst.EMPLOYEE_FACTOR_CRASHCHANCE_FORKLIFT_PERMIT);
                        }

                        if (employee.getContractType() == DoloresConst.EMPLOYEE_CONTRACT_HALF_TIME) {
                            key = new StringBuilder("working_time_").append(processOfEmployee).append("_wfp").toString();
                            mInts.put(key, mInts.get(key) + Math.round((float) ((DoloresConst.DOLORES_GAME_WORKING_TIME_PERIOD + Integer.parseInt(gameInfo.getCurrentState().getValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(processOfEmployee).toString())) * 3600) * DoloresConst.EMPLOYEE_FACTOR_HALF_TIME)));
                        } else {
                            key = new StringBuilder("working_time_").append(processOfEmployee).append("_wfp").toString();
                            mInts.put(key, mInts.get(key) + DoloresConst.DOLORES_GAME_WORKING_TIME_PERIOD + Integer.parseInt(gameInfo.getCurrentState().getValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(processOfEmployee).toString())) * 3600);
                        }
                    } else if (validContract) {	//employee does not have a forklift permit

                        //count employee to sum of workers without forklift permit of process related to employee
                        String key = new StringBuilder("count_").append(processOfEmployee).append("_wofp").toString();
                        mInts.put(key, mInts.get(key) + 1);
                        //calculate cycletime and crash_chance to sum process related to employee
                        key = new StringBuilder("cycletime_").append(processOfEmployee).toString();
                        mDoubles.put(key, mDoubles.get(key) + DoloresConst.EMPLOYEE_FACTOR_CYCLETIME_DEFAULT);
                        key = new StringBuilder("crash_chance_").append(processOfEmployee).toString();
                        mDoubles.put(key, mDoubles.get(key) + DoloresConst.EMPLOYEE_FACTOR_CRASHCHANCE_DEFAULT);

                        //Add working time to sum of process related to employee
                        if (employee.getContractType() == DoloresConst.EMPLOYEE_CONTRACT_HALF_TIME) {
                            key = new StringBuilder("working_time_").append(processOfEmployee).append("_wofp").toString();
                            mInts.put(key, mInts.get(key) + Math.round((float) ((DoloresConst.DOLORES_GAME_WORKING_TIME_PERIOD + Integer.parseInt(gameInfo.getCurrentState().getValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(processOfEmployee).toString())) * 3600) * DoloresConst.EMPLOYEE_FACTOR_HALF_TIME)));
                        } else {
                            key = new StringBuilder("working_time_").append(processOfEmployee).append("_wofp").toString();


                            mInts.put(key, mInts.get(key) + DoloresConst.DOLORES_GAME_WORKING_TIME_PERIOD + Integer.parseInt(gameInfo.getCurrentState().getValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(processOfEmployee).toString())) * 3600);
                        }
                    }
                } else {
                    //Employee works in a Control-Process

                    //calculate failure Factor for current employee and add it to sum of process related to employee
                    if (employee.hasQMSeminar()) {
                        String key = new StringBuilder("failure_factor_").append(processOfEmployee).toString();
                        mDoubles.put(key, mDoubles.get(key) + DoloresConst.EMPLOYEE_FACTOR_FAILURE_RATE_QMSEMINAR);
                    } else {
                        String key = new StringBuilder("failure_factor_").append(processOfEmployee).toString();
                        mDoubles.put(key, mDoubles.get(key) + DoloresConst.EMPLOYEE_FACTOR_FAILURE_RATE_DEFAULT);
                    }

                    //Add working time to sum of process related to employee
                    if (employee.getContractType() == DoloresConst.EMPLOYEE_CONTRACT_HALF_TIME) {
                        String key = new StringBuilder("working_time_").append(processOfEmployee).toString();
                        mInts.put(key, mInts.get(key) + Math.round((float) ((DoloresConst.DOLORES_GAME_WORKING_TIME_PERIOD + Integer.parseInt(gameInfo.getCurrentState().getValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(processOfEmployee).toString())) * 3600) * DoloresConst.EMPLOYEE_FACTOR_HALF_TIME)));
                    } else {
                        String key = new StringBuilder("working_time_").append(processOfEmployee).toString();
                        mInts.put(key, mInts.get(key) + DoloresConst.DOLORES_GAME_WORKING_TIME_PERIOD + Integer.parseInt(gameInfo.getCurrentState().getValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(processOfEmployee).toString())) * 3600);
                    }
                }


            }
        }
        for (String abbr : Processes.getInstance().getProcessAbbrevations()) {

            mInts.put(new StringBuilder("count_").append(abbr).toString(), mInts.get(new StringBuilder("count_").append(abbr).append("_wfp").toString()) + mInts.get(new StringBuilder("count_").append(abbr).append("_wofp").toString()));
            this.fullTimeEmployeeCount += mInts.get(new StringBuilder("count_").append(abbr).append("_full").toString());
            this.partTimeEmployeeCount += mInts.get(new StringBuilder("count_").append(abbr).append("_part").toString());
            this.temporaryEmployeeCount += mInts.get(new StringBuilder("count_").append(abbr).append("_temporary").toString());
            mDoubles.put(new StringBuilder("temporary_factor_").append(abbr).toString(), mInts.get(new StringBuilder("count_").append(abbr).append("_temporary").toString()) * 1d / mInts.get(new StringBuilder("count_").append(abbr).toString()));
            if (Processes.getInstance().hasProcessConveyors(abbr)) {
                mInts.put(new StringBuilder("working_time_").append(abbr).toString(), mInts.get(new StringBuilder("working_time_").append(abbr).append("_wfp").toString()) + mInts.get(new StringBuilder("working_time_").append(abbr).append("_wofp").toString()));
            }

            if ("wv".equals(abbr) || "wk".equals(abbr)) {
                mInts.put(new StringBuilder("count_").append(abbr).toString(), mInts.get(new StringBuilder("count_").append(abbr).append("_full").toString()) + mInts.get(new StringBuilder("count_").append(abbr).append("_part").toString()) + mInts.get(new StringBuilder("count_").append(abbr).append("_temporary").toString()));
            }

        }
        mInts.put("overall_count", this.temporaryEmployeeCount + this.partTimeEmployeeCount + this.fullTimeEmployeeCount);
        mInts.put("fullTimeEmployeeCount", this.fullTimeEmployeeCount);
        mInts.put("partTimeEmployeeCount", this.partTimeEmployeeCount);
        mInts.put("temporaryEmployeeCount", this.temporaryEmployeeCount);
        mDoubles.put("temporary_factor", this.temporaryEmployeeCount * 1d / mInts.get("overall_count"));
        calculateMotivationCapacityAndCosts(gameInfo);

    }

    private void calculateMotivationCapacityAndCosts(DoloresGameInfo gameInfo) {
        //Get current round Number
        int aktround = Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER));
        List<Integer> workClimateInvestLevel = Arrays.asList(DoloresConst.WORK_CLIMATE_INVEST);


        for (EmployeeDynamics employee : gameInfo.getCurrentState().getEmployeeDynamics()) {

            if (employee.isReady(aktround)) {


                /*
                 * Calculate qualification costs
                 */

                String key = new StringBuilder("costs_qualification").toString();
                mInts.put(key, 0);

                if (employee.getFpRound() == gameInfo.getCurrentState().getRoundNumber() + 1) {
                    mInts.put(key, mInts.get(key) + 2300);
                }
                if (employee.getQmRound() == gameInfo.getCurrentState().getRoundNumber() + 1) {
                    mInts.put(key, mInts.get(key) + 2250);
                }
                if (employee.getSecRound() == gameInfo.getCurrentState().getRoundNumber() + 1) {
                    mInts.put(key, mInts.get(key) + 1700);
                }

                //Get Process Abbrevation for current Employee
                String processOfEmployee = Processes.getInstance().getProcessAbbrevations()[employee.getProcess()];

                /*
                 * BEGIN Calculate Motivation Factors for current employee
                 */

                //Motivation by work climate invest
                double motivation_work_climate_invest = DoloresConst.WORK_CLIMATE_FACTOR[workClimateInvestLevel.indexOf(gameInfo.getCurrentState().getWorkClimateInvest())];

                //BEGIN Motivation by overtime
                double motivation_overtime = 0;
                int overtime_process = Integer.parseInt(gameInfo.getCurrentState().getValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(processOfEmployee).toString()));
                for (Integer integer : DoloresConst.OVERTIME_BORDERS) {
                    if (overtime_process <= integer) {
                        motivation_overtime = DoloresConst.OVERTIME_FACTOR[integer];
                        break;
                    }
                }
                if (motivation_overtime == 0) {
                    motivation_overtime = DoloresConst.OVERTIME_FACTOR[DoloresConst.OVERTIME_BORDERS.length];
                }
                //END Motivation by overtime

                //BEGIN Motivation by temporary Workers
                double motivation_temporary_workers = 0;
                int count = 1;
                for (Double border : DoloresConst.TEMPORARY_WORKER_FACTOR) {
                    if (mDoubles.get("temporary_factor") <= border) {
                        motivation_temporary_workers = DoloresConst.TEMPORARY_WORKER_MOTIVATION_FACTOR[count];
                        break;
                    }
                    count++;
                }
                if (motivation_temporary_workers == 0) {
                    motivation_temporary_workers = DoloresConst.TEMPORARY_WORKER_MOTIVATION_FACTOR[DoloresConst.TEMPORARY_WORKER_FACTOR.length];
                }
                //END Motivation by temporary Workers

                //BEGIN Motivation by salary
                double motivation_salary = 0;
                //calculate base_salary without bonus and overtime
                int base_salary = 0;
                if (!employee.hasForkliftPermit() && !employee.hasSecurityTraining()) {
                    if (employee.hasQMSeminar()) {
                        base_salary = DoloresConst.SALARY_WITH_QMSEMINAR;
                    } else {
                        base_salary = DoloresConst.SALARY_NORMAL;
                    }
                } else if (employee.hasForkliftPermit() && !employee.hasSecurityTraining()) {
                    if (employee.hasQMSeminar()) {
                        base_salary = DoloresConst.SALARY_WITH_QMSEMINAR_AND_FP;
                    } else {
                        base_salary = DoloresConst.SALARY_WITH_FORKLIFT_PERMIT;
                    }
                } else if (employee.hasForkliftPermit() && employee.hasSecurityTraining()) {
                    if (employee.hasQMSeminar()) {
                        base_salary = DoloresConst.SALARY_WITH_FP_AND_SECURITY_TRAINING;
                    } else {
                        base_salary = DoloresConst.SALARY_WITH_QSEMINAR_FP_AND_SECURITY_TRAINING;
                    }
                }
                //calculate motivation factor
                for (int i = DoloresConst.SALARY_BONUS_BORDERS.length - 1; i >= 0; i--) {
                    if ((employee.getContractType() == DoloresConst.EMPLOYEE_CONTRACT_HALF_TIME ? employee.getSalary() * (1 / DoloresConst.EMPLOYEE_FACTOR_HALF_TIME) : employee.getSalary()) - base_salary >= DoloresConst.SALARY_BONUS_BORDERS[i]) {
                        motivation_salary = DoloresConst.SALARY_BONUS_MOTIVATION_FACTORS[i + 2];
                        break;
                    }
                }
                if (motivation_salary == 0) {
                    motivation_salary = DoloresConst.SALARY_BONUS_MOTIVATION_FACTORS[1];
                }
                //END Motivation by salary

                //add motivation of current employee to sum of his related process and set motivation on employee
                employee.setMotivationPercentage(motivation_work_climate_invest * DoloresConst.WORK_CLIMATE_FACTOR[0] + motivation_temporary_workers * DoloresConst.TEMPORARY_WORKER_MOTIVATION_FACTOR[0] + motivation_salary * DoloresConst.SALARY_BONUS_MOTIVATION_FACTORS[0] + motivation_overtime * DoloresConst.OVERTIME_FACTOR[0]);
                key = new StringBuilder("motivation_").append(processOfEmployee).toString();
                mDoubles.put(key, mDoubles.get(key) + employee.getMotivationPercentage());

                /*
                 * END Calculate Motivation Factors for current employee
                 */

                //if employee works half time only reduce his base salary
                if (employee.getContractType() == DoloresConst.EMPLOYEE_CONTRACT_HALF_TIME) {
                    base_salary *= DoloresConst.EMPLOYEE_FACTOR_HALF_TIME;
                }
                //calculate costs for overtime and salary for current employee and add them to related sums of his process
                int aktovertimefee = ((employee.getSalary()) / ((DoloresConst.DOLORES_GAME_WORKING_TIME_PERIOD) / 3600)) * Integer.parseInt(gameInfo.getCurrentState().getValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(processOfEmployee).toString()));
                key = new StringBuilder("costs_salary_").append(processOfEmployee).toString();
                mInts.put(key, mInts.get(key) + base_salary);
                key = new StringBuilder("costs_overtime_").append(processOfEmployee).toString();
                mInts.put(key, mInts.get(key) + aktovertimefee);
                //if employee leave company because he is fired, calculate the compensation for him
                if (employee.getEndPeriode() == aktround) {	//calculate compensation for fired employee
                    int employedDuration = aktround - employee.getEmploymentPeriode();
                    mInts.put("costs_compensation", mInts.get("costs_compensation") + (int) (Math.round(employee.getSalary() * employedDuration) * DoloresConst.EMPLOYEE_FACTOR_COMPENSATION));

                    messages.add(new StringBuilder(DoloresConst.EMPLOYEE_NOTIFICATION_LEAVING_AFTER_ROUND).append("-").append(employee.getFirstName()).append("-").append(employee.getLastName()).append("-").append(employee.getProcess()).toString());
                    //NotificationService.addNotification(String.format(NotificationMessages.getInstance().getMessage(DoloresConst.EMPLOYEE_NOTIFICATION_LEAVING_AFTER_ROUND), employee.getFirstName(), employee.getLastName(), employee.getId(), Processes.getInstance().getProcessAbbrevations()[employee.getProcess()]));
                }

            }
        }

        for (String abbr : Processes.getInstance().getProcessAbbrevations()) {
            //calculate average motivation for current process
            double avgmotivation = mDoubles.get(new StringBuilder("motivation_").append(abbr).toString()) / mInts.get(new StringBuilder("count_").append(abbr).toString());
            mDoubles.put(new StringBuilder("avg_motivation_").append(abbr).toString(), avgmotivation);
            if (avgmotivation <= DoloresConst.EMPLOYEE_MOTIVATION_WARNING_BORDER) {

                messages.add(DoloresConst.EMPLOYEE_NOTIFICATION_MOTIVATION_PROBLEM);

            }
            if (Processes.getInstance().hasProcessConveyors(abbr)) {	//Process contains Conveyors

                //calculate capacity of employees with and without Forklift Permit
                mInts.put(new StringBuilder("capacity_").append(abbr).append("_wfp").toString(), Math.round((float) (mInts.get(new StringBuilder("working_time_").append(abbr).append("_wfp").toString()) * mDoubles.get(new StringBuilder("avg_motivation_").append(abbr).toString()))));
                mInts.put(new StringBuilder("capacity_").append(abbr).append("_wofp").toString(), Math.round((float) (mInts.get(new StringBuilder("working_time_").append(abbr).append("_wofp").toString()) * mDoubles.get(new StringBuilder("avg_motivation_").append(abbr).toString()))));
                //calculate overall capacity of current process
                mInts.put(new StringBuilder("capacity_").append(abbr).toString(), mInts.get(new StringBuilder("capacity_").append(abbr).append("_wfp").toString()) + mInts.get(new StringBuilder("capacity_").append(abbr).append("_wofp").toString()));
                //calculate crashchance of current provess
                String crash = new StringBuilder("crash_chance_").append(abbr).toString();
                //calculate absolut count of employees of current process
                String count = new StringBuilder("count_").append(abbr).toString();
                //calculate average crash chance of current process
                mDoubles.put(new StringBuilder("avg_crash_chance_").append(abbr).toString(), mDoubles.get(crash) / mInts.get(count));
                //calculate overall cycletime and average cycletime of current process
                String working = new StringBuilder("cycletime_").append(abbr).toString();
                mDoubles.put(new StringBuilder("avg_cycletime_").append(abbr).toString(), mDoubles.get(working) / mInts.get(count));
            } else {	//Process is control process

                //calculate capacity of current process
                mInts.put(new StringBuilder("capacity_").append(abbr).toString(), Math.round((float) (mInts.get(new StringBuilder("working_time_").append(abbr).toString()) * mDoubles.get(new StringBuilder("avg_motivation_").append(abbr).toString()))));
                //calculate average failure_rate of current process
                String failure = new StringBuilder("failure_factor_").append(abbr).toString();
                String count = new StringBuilder("count_").append(abbr).toString();
                mDoubles.put(new StringBuilder("avg_failure_rate_").append(abbr).toString(), mDoubles.get(failure) / mInts.get(count));
            }
            //calculate costs of overtime and salary and overall costs of current process

            int overtime = mInts.get(new StringBuilder("costs_overtime_").append(abbr).toString());
            int salary = mInts.get(new StringBuilder("costs_salary_").append(abbr).toString());

            mInts.put("costs_overtime", mInts.get("costs_overtime") + overtime);
            mInts.put("costs_salary", mInts.get("costs_salary") + salary);
            mInts.put(new StringBuilder("costs_").append(abbr).toString(), salary + overtime);
        }
        //calculate overall costs of all processes for current round
        int newCost = Integer.valueOf(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_COSTS_NEW_EMPLOYEES));
        mInts.put("costs_new", newCost);
        mInts.put("costs_emp", mInts.get("costs_overtime") + mInts.get("costs_salary") + mInts.get("costs_compensation") + mInts.get("costs_new") /*+ mInts.get("costs_qualification")*/);


    }

    /**
     *
     * @param process The Name of the Process
     * @return The factor of temporary Employees in given Process
     */
    public double getTemporaryFactor(int process) {
        return mDoubles.get(new StringBuilder("temporary_factor_").append(Processes.getInstance().getProcessAbbrevations()[process]).toString());
    }

    /**
     *
     * @return The Factor of temporary Employees of all processes
     */
    public double getOverallTemporaryFactor() {
        return mDoubles.get("temporary_factor");
    }

    /**
     *
     * @return The overall count of Employees of all Processes
     */
    public int getOverallEmployeeCount() {
        return mInts.get("overall_count");
    }

    /**
     * @param process The process
     * @return The overall count of Employees in Processe
     */
    public int getOverallEmployeeProcess(String process) {
        return mInts.get(new StringBuilder("count_").append(process).toString());
    }

    /**
     *
     * @param process The Process
     * @return The Sum of workingtime of all employees in current process
     */
    public int getWorkingTime(String process) {
        return mInts.get(new StringBuilder("working_time_").append(process).toString());
    }

    /**
     *
     * @param processAbbr The Process (conveyor process only!)
     * @return The average Crash chance of given Process
     */
    public double getAverageCrashChance(String processAbbr) {
        String key = new StringBuilder("avg_crash_chance_").append(processAbbr).toString();
        if (mDoubles.containsKey(key)) {
            return mDoubles.get(key);
        } else {
            //NotificationService.addNotification(String.format(NotificationMessages.getInstance().getMessage(DoloresConst.PROCESS_NOTIFICATION_HAS_NO_CONVEYORS), processAbbr));
            return Double.NaN;
        }
    }

    /**
     *
     * @param processAbbr The Process (control process only!)
     * @return The average failure Rate of given Process
     */
    public double getAverageFailureRate(String processAbbr) {
        String key = new StringBuilder("avg_failure_rate_").append(processAbbr).toString();
        if (mDoubles.containsKey(key)) {
            return mDoubles.get(key);
        } else {
            //NotificationService.addNotification(String.format(NotificationMessages.getInstance().getMessage(DoloresConst.PROCESS_NOTIFICATION_IS_NOT_CONTROL_PROCESS), processAbbr));
            return Double.NaN;
        }
    }

    /**
     *
     * @param processAbbr The Process (Conveyor process only!)
     * @return The average cycle time of given Process
     */
    public double getAverageCycleTime(String processAbbr) {
        String key = new StringBuilder("avg_cycletime_").append(processAbbr).toString();
        if (mDoubles.containsKey(key)) {
            return mDoubles.get(key);
        } else {
            //NotificationService.addNotification(String.format(NotificationMessages.getInstance().getMessage(DoloresConst.PROCESS_NOTIFICATION_HAS_NO_CONVEYORS), processAbbr));
            return Double.NaN;
        }
    }

    /**
     *
     * @param processAbbr The process
     * @return The Employee-capacity of given Process (with and without forklift
     * permit) and for Control-Processes
     */
    public double getCapacity(String processAbbr) {
        
        // 0 as return value breaks the calculation, so return a small value instaed.
        if(mInts.get(new StringBuilder("capacity_").append(processAbbr).toString()) == 0){
            return 0.00001;
        }
        
        return (double)mInts.get(new StringBuilder("capacity_").append(processAbbr).toString());
    }

    /**
     *
     * @param processAbbr The Process
     * @param needsForkliftPermit <code>true</code> only employees with forklift
     * will be involved, otherwise only employees without forklift permit will
     * be involved
     * @return capacity of given Process related to "needsForkliftPermit"
     */
    public int getCapacity(String processAbbr, boolean needsForkliftPermit) {
        if (!needsForkliftPermit) {
            String key = new StringBuilder("capacity_").append(processAbbr).append("_wofp").toString();
            if (mInts.containsKey(key)) {
                return mInts.get(key);
            } else {
                //NotificationService.addNotification(String.format(NotificationMessages.getInstance().getMessage(DoloresConst.PROCESS_NOTIFICATION_HAS_NO_CONVEYORS), processAbbr));
                return -1;
            }
        } else {
            String key = new StringBuilder("capacity_").append(processAbbr).append("_wfp").toString();
            if (mInts.containsKey(key)) {
                return mInts.get(key);
            } else {
                //NotificationService.addNotification(String.format(NotificationMessages.getInstance().getMessage(DoloresConst.PROCESS_NOTIFICATION_HAS_NO_CONVEYORS), processAbbr));
                return -1;
            }
        }
    }

    /**
     *
     * @param processAbbr The Process
     * @return The average employee motivation (in form of 0.xx) of given
     * process
     */
    public double getAverageMotivation(String processAbbr) {
        return mDoubles.get(new StringBuilder("avg_motivation_").append(processAbbr).toString());
    }

    /**
     *
     * @param process The Process
     * @return The costs for all employees of given process
     */
    public int getCosts(String process) {
        return mInts.get(new StringBuilder("costs_").append(process).toString());
    }

    /**
     *
     * @return Overall costs for Employees (salary, overtimefee, new, fired)
     */
    public int getOverallCosts() {
        return mInts.get("costs_emp");
    }

    /**
     *
     * @return The costs for employees of all Processes
     */
    public double getOverallEmployeeCosts() {
        return mDoubles.get("costs_emp");
    }

    /**
     *
     * @param process The process
     * @return The costs of salary for all employees of given process
     */
    public double getSalaryCosts(String process) {
        return mDoubles.get(new StringBuilder("costs_salary_").append(process).toString());
    }

    /**
     *
     * @param process The process
     * @return The costs of overtime for all employees of given process
     */
    public double getOvertimeCosts(String process) {
        return mDoubles.get(new StringBuilder("costs_overtime_").append(process).toString());
    }

    /**
     *
     * @return The costs for salary for employees of all processes
     */
    public double getOverallSalaryCosts() {
        return mDoubles.get("costs_salary");
    }

    /**
     *
     * @return The costs for overtime for employees of all processes
     */
    public double getOverallOvertimeCosts() {
        return mDoubles.get("costs_overtime");
    }

    /**
     *
     * @return The costs for compensation for fired employees of all processes
     */
    public double getCompensationCosts() {
        return mDoubles.get("costs_compensation");
    }

    @Override
    public void prepareNextRound(DoloresGameInfo gameInfo) {
        int aktround = gameInfo.getCurrentState().getRoundNumber();

        for (EmployeeDynamics employee : gameInfo.getCurrentState().getEmployeeDynamics()) {
            //If Employee is working for company currently
            if (employee.getEmployee().isReady(aktround)) {
                if (employee.getFpRound() == gameInfo.getCurrentState().getRoundNumber()) {
                    employee.setQualifications(employee.getQualifications() + DoloresConst.EMPLOYEE_QUALIFICATION_FORKLIFT_PERMIT);
                    messages.add(new StringBuilder(DoloresConst.EMPLOYEE_NOTIFICATION_FP_DONE).append("-").append(employee.getFirstName()).append("-").append(employee.getLastName()).toString());
                }
                if (employee.getQmRound() == gameInfo.getCurrentState().getRoundNumber()) {
                    employee.setQualifications(employee.getQualifications() + DoloresConst.EMPLOYEE_QUALIFICATION_QMSEMINAR);
                    messages.add(new StringBuilder(DoloresConst.EMPLOYEE_NOTIFICATION_QM_DONE).append("-").append(employee.getFirstName()).append("-").append(employee.getLastName()).toString());
                }
                if (employee.getSecRound() == gameInfo.getCurrentState().getRoundNumber()) {
                    employee.setQualifications(employee.getQualifications() + DoloresConst.EMPLOYEE_QUALIFICATION_SECURITY_TRAINING);
                    messages.add(new StringBuilder(DoloresConst.EMPLOYEE_NOTIFICATION_SECURITY_DONE).append("-").append(employee.getFirstName()).append("-").append(employee.getLastName()).toString());
                }


                if (employee.getEmploymentPeriode() == aktround) {
                    messages.add(new StringBuilder(DoloresConst.EMPLOYEE_NOTIFICATION_JOINING_IN_ROUND).append("-").append(employee.getFirstName()).append("-").append(employee.getLastName()).append("-").append(employee.getProcess()).toString());
                }
            }
        }

    }

    @Override
    public List<Object> getToUpdate() {
        return new ArrayList<Object>(); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void transfer() {
        for (Field f : empStat.getClass().getDeclaredFields()) {
            if (mInts.get(f.getName()) != null && !Double.isNaN(mInts.get(f.getName())) && !Double.isInfinite(mInts.get(f.getName()))) {
                try {
                    f.setAccessible(true);
                    f.set(empStat, mInts.get(f.getName()));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (mDoubles.get(f.getName()) != null && !Double.isNaN(mDoubles.get(f.getName())) && !Double.isInfinite(mDoubles.get(f.getName()))) {
                try {
                    f.setAccessible(true);

                    if ("temporary_factor_wk".equals(f.getName())) {
                        int x = 5;
                    }
                    f.set(empStat, mDoubles.get(f.getName()));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}