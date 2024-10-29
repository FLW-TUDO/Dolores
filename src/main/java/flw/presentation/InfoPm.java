/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
import flw.business.core.DoloresState;
import flw.business.store.ArticleDynamics;
import flw.business.store.ConveyorDynamics;
import flw.business.store.CustomerJobDynamics;
import flw.business.store.EmployeeDynamics;
import flw.business.store.OrderDynamics;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author tilu
 */
@ManagedBean(name = "infoPM")
@ViewScoped
public class InfoPm implements Serializable {

    @ManagedProperty(value = "#{gameInfoPM}")
    private GameInfoPM gameInfoPM;
    @EJB
    Service service;
    /*
     * Data for cash Page
     */
    int roundNrMinusOne;
    private double block1;
    private String sales_income;
    private String variable_order_costs;
    private String storage_cost;
    private double block2;
    private String costs_sal_ovt;
    private String costs_qm_measure;
    private String costs_new_comp;
    private String work_climinv;
    private double block3;
    private String income_conv_sale;
    private String it_costs;
    private String costs_conv;
    private String costs_new;
    private double block4;
    private String costs_usd;
    private String costs_abc;
    private String balance2;
    private String deposit_interest;
    private String balance1;
    /*
     * Data for stats
     */
    private String empCountWfp;
    private String empCountWofp;
    private String empCount;
    private double empWorkload;
    private String convCountWfp;
    private String convCountWofp;
    private String convCount;
    private double workloadConv;
    private String empOvt;
    private double palletsToTransport;
    private String palletsTransported;
    private String empCountWfpNew;
    private String empCountWofpNew;
    private String empCountNew;
    private String convCountWfpNew;
    private String convCountWofpNew;
    private String convCountNew;
    private String empOvtNew;
    private String palletsToTransportNew;

    public InfoPm() {
    }

    public void setGameInfoPM(GameInfoPM gameInfoPM) {
        this.gameInfoPM = gameInfoPM;
    }

    public int getRoundNrMinusOne() {
        return gameInfoPM.getInfo().getCurrentState().getRoundNumber() - 1;
    }

    public void setRoundNrMinusOne(int roundNrMinusOne) {
        this.roundNrMinusOne = roundNrMinusOne;
    }

    public void setBlock1(double block1) {
        this.block1 = block1;
    }

    public double getBlock1() {

        return Double.valueOf(getSales_income()) - Double.valueOf(getVariable_order_costs()) - Double.valueOf(getStorage_cost());
    }

    public double getSales_income() {
        if (null == sales_income) {
            sales_income = getSingleString("SALES_INCOME", gameInfoPM.getCurrentState().getRoundNumber() - 1);
        }
        return Double.valueOf(sales_income);
    }

    public void setSales_income(String sales_income) {
        this.sales_income = sales_income;
    }

    public double getVariable_order_costs() {
        double cost = 0d;
        for (ArticleDynamics artDyn : gameInfoPM.getCurrentState().getArticleDynamics()) {
            cost += Double.valueOf(getSingleString(new StringBuilder("ORDER_COSTS_" + artDyn.getArticleNumber()).toString(), gameInfoPM.getCurrentState().getRoundNumber() - 1));
            cost += Double.valueOf(getSingleString(new StringBuilder("ORDER_FIX_COSTS_" + artDyn.getArticleNumber()).toString(), gameInfoPM.getCurrentState().getRoundNumber() - 1));
        }

        return cost;
    }

    public void setVariable_order_costs(String variable_order_costs) {
        this.variable_order_costs = variable_order_costs;
    }

    public double getStorage_cost() {
        if (null == storage_cost) {
            storage_cost = getSingleString("STORAGE_COST", gameInfoPM.getCurrentState().getRoundNumber() - 1);

        }
        return Double.valueOf(storage_cost);
    }

    public void setStorage_cost(String storage_cost) {
        this.storage_cost = storage_cost;
    }

    public double getBlock2() {
        return 0.0 - (Double.valueOf(getCosts_sal_ovt()) + Double.valueOf(getCosts_qm_measure()) + Double.valueOf(getCosts_new_comp()) + getWork_climinv());
    }

    public void setBlock2(double block2) {
        this.block2 = block2;
    }

    public double getCosts_sal_ovt() {

        if (null == costs_sal_ovt) {
            String costs_salary = getSingleString("COSTS_SALARY", gameInfoPM.getCurrentState().getRoundNumber() - 1);
            String costs_overtime = getSingleString("COSTS_OVERTIME", gameInfoPM.getCurrentState().getRoundNumber() - 1);
            double x = Double.valueOf(costs_salary) + Double.valueOf(costs_overtime);
            costs_sal_ovt = String.valueOf(x);
        }
        return Double.valueOf(costs_sal_ovt);
    }

    public void setCosts_sal_ovt(String costs_sal_ovt) {
        this.costs_sal_ovt = costs_sal_ovt;
    }

    public double getCosts_qm_measure() {

        if (null == costs_qm_measure) {
            costs_qm_measure = getSingleString("COSTS_QUALIFICATION_MEASURE", gameInfoPM.getCurrentState().getRoundNumber() - 1);

        }
        return Double.valueOf(costs_qm_measure);
    }

    public void setCosts_qm_measure(String costs_qm_measure) {
        this.costs_qm_measure = costs_qm_measure;
    }

    /*
     * TODO
     * TEST
     */
    public double getCosts_new_comp() {

        if (null == costs_new_comp) {

            String newc = null;
            List list = service.retriveSingleValuesEmp(gameInfoPM.getInfo().getId(), gameInfoPM.getCurrentState().getRoundNumber() - 1);
            Object[] toArray = list.toArray();
            for (Object o : toArray) {
                newc = o.toString();
            }
            String comp = getSingleString("COSTS_COMPENSATION", gameInfoPM.getCurrentState().getRoundNumber() - 1);
            double x = Double.valueOf(newc) + Double.valueOf(comp);
            costs_new_comp = String.valueOf(x);
        }

        return Double.valueOf(costs_new_comp);
    }

    public void setCosts_new_comp(String costs_new_comp) {
        this.costs_new_comp = costs_new_comp;
    }

    public double getWork_climinv() {

        if (null == work_climinv) {
            work_climinv = getSingleString("WORK_CLIMATE_INVEST", gameInfoPM.getCurrentState().getRoundNumber() - 1);

        }
        return Double.valueOf(work_climinv);

    }

    public void setWork_climinv(String work_climinv) {
        this.work_climinv = work_climinv;
    }

    public double getBlock3() {
        return Double.valueOf(getIncome_conv_sale()) - Double.valueOf(getIt_costs()) - Double.valueOf(getCosts_conv()) - Double.valueOf(getCosts_new());
    }

    public void setBlock3(double block3) {
        this.block3 = block3;
    }

    public double getIncome_conv_sale() {

        if (income_conv_sale == null) {
            income_conv_sale = getSingleString("INCOME_CONVEYOR_SALE", gameInfoPM.getCurrentState().getRoundNumber() - 1);
        }
        return Double.valueOf(income_conv_sale);
    }

    public void setIncome_conv_sale(String income_conv_sale) {
        this.income_conv_sale = income_conv_sale;
    }

    public double getIt_costs() {

        if (it_costs == null) {
            it_costs = getSingleString("ITCOSTS", gameInfoPM.getCurrentState().getRoundNumber() - 1);
        }
        return Double.valueOf(it_costs);
    }

    public void setIt_costs(String it_costs) {
        this.it_costs = it_costs;
    }

    public double getCosts_conv() {

        if (costs_conv == null) {
            String repair = getSingleString("COSTS_REPAIR", gameInfoPM.getCurrentState().getRoundNumber() - 1);
            String overhaul = getSingleString("COSTS_OVERHAUL", gameInfoPM.getCurrentState().getRoundNumber() - 1);
            String maint = getSingleString("COSTS_MAINTENANCE", gameInfoPM.getCurrentState().getRoundNumber() - 1);
            double x = Double.valueOf(repair) + Double.valueOf(maint) + Double.valueOf(overhaul);
            costs_conv = String.valueOf(x);
        }
        return Double.valueOf(costs_conv);
    }

    public void setCosts_conv(String costs_conv) {
        this.costs_conv = costs_conv;
    }

    public double getCosts_new() {

        if (costs_new == null) {
            costs_new = getSingleString("COSTS_NEW", gameInfoPM.getCurrentState().getRoundNumber() - 1);

        }
        return Double.valueOf(costs_new);
    }

    public void setCosts_new(String costs_new) {
        this.costs_new = costs_new;
    }

    public double getBlock4() {
        return 0.0 - Double.valueOf(getCosts_usd()) - Double.valueOf(getCosts_abc());
    }

    public void setBlock4(double block4) {
        this.block4 = block4;
    }

    public double getCosts_usd() {

        if (costs_usd == null) {
            costs_usd = getSingleString("COSTS_USD", gameInfoPM.getCurrentState().getRoundNumber() - 1);
        }
        return Double.valueOf(costs_usd);
    }

    public void setCosts_usd(String costs_usd) {
        this.costs_usd = costs_usd;
    }

    public double getCosts_abc() {

        if (costs_abc == null) {
            costs_abc = getSingleString("COSTS_ABC", gameInfoPM.getCurrentState().getRoundNumber() - 1);
        }
        return Double.valueOf(costs_abc);
    }

    public void setCosts_abc(String costs_abc) {
        this.costs_abc = costs_abc;
    }

    public double getBalance2() {
        if (balance2 == null) {
            balance2 = getSingleString("ACCOUNTBALANCE", gameInfoPM.getCurrentState().getRoundNumber() - 2);
        }
        return Double.valueOf(balance2);
    }

    public void setBalance2(String balance2) {
        this.balance2 = balance2;
    }

    public double getDeposit_interest() {
        if (deposit_interest == null) {
            deposit_interest = getSingleString("DEPOSIT_INTEREST", gameInfoPM.getCurrentState().getRoundNumber() - 1);
        }

        double temp = 100 * Double.valueOf(deposit_interest);
        int temp1 = (int) temp;
        temp = (double) temp1;
        temp = temp / 100;

        return temp;
    }

    public void setDeposit_interest(String deposit_interest) {
        this.deposit_interest = deposit_interest;
    }

    public double getBalance1() {
        if (balance1 == null) {
            balance1 = getSingleString("ACCOUNTBALANCE", gameInfoPM.getCurrentState().getRoundNumber() - 1);
        }
        return Double.valueOf(balance1);
    }

    /*
     TODO: Caching. 
     Aktuell wird jede Map neu befüllt.
     Achtung: verschiedene Rundennummern. 
     */
    public String getSingleString(String dat, int roundnumber /*, int type*/) {
        String xd = null;
        List list;
        try {
            /* TODO
             switch (type) {
             case 1:
             list= service.retriveSingleValues(dat, gameInfoPM.getInfo().getId(), roundnumber);
             break;
             case 2:
             list = service.retriveSingleValues(dat, gameInfoPM.getInfo().getId(), roundnumber);
             break;
             case 3:
             list = service.retriveSingleValues(dat, gameInfoPM.getInfo().getId(), roundnumber);
             break;
             case 4:
             list = service.retriveSingleValues(dat, gameInfoPM.getInfo().getId(), roundnumber);
             break;
             case 5:
             list = service.retriveSingleValueState(dat, gameInfoPM.getInfo().getId(), roundnumber);
             break;
             }
             */
            if (dat.equals("PALLETS_TRANSPORTED_LA")) {
                int in = Integer.valueOf(service.retriveSingleValues("PALLETS_TRANSPORTED_LA_IN", gameInfoPM.getInfo().getId(), roundnumber));
                int out = Integer.valueOf(service.retriveSingleValues("PALLETS_TRANSPORTED_LA_OUT", gameInfoPM.getInfo().getId(), roundnumber));
                return String.valueOf(in + out);
            }

            return service.retriveSingleValues(dat, gameInfoPM.getInfo().getId(), roundnumber);
        } catch (SQLException ex) {
            Logger.getLogger(InfoPm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public void setBalance1(String balance1) {
        this.balance1 = balance1;
    }

    public String getEmpCountWfp(String process) {
        empCountWfp = getSingleString("COUNT_" + process + "_WFP", gameInfoPM.getCurrentState().getRoundNumber() - 1);
        return empCountWfp;
    }

    public void setEmpCountWfp(String empCountWfp) {
        this.empCountWfp = empCountWfp;
    }

    public String getEmpCountWofp(String process) {
        empCountWofp = getSingleString("COUNT_" + process + "_WOFP", gameInfoPM.getCurrentState().getRoundNumber() - 1);
        return empCountWofp;
    }

    public void setEmpCountWofp(String empCountWofp) {
        this.empCountWofp = empCountWofp;
    }

    public String getEmpCount(String process) {
        empCount = getSingleString("COUNT_" + process, gameInfoPM.getCurrentState().getRoundNumber() - 1);
        return empCount;
    }

    public void setEmpCount(String empCount) {
        this.empCount = empCount;
    }

    public double getEmpWorkload(String process) {
        empWorkload = 100 * Double.valueOf(getSingleString("WORKLOAD_WORKERS_" + process, gameInfoPM.getCurrentState().getRoundNumber() - 1));
        return empWorkload;
    }

    public void setEmpWorkload(double empWorkload) {
        this.empWorkload = empWorkload;
    }

    public String getConvCountWfp(String process) {
        convCountWfp = getSingleString("CONVEYOR_COUNT_" + process + "_WFP", gameInfoPM.getCurrentState().getRoundNumber() - 1);
        return convCountWfp;

    }

    public void setConvCountWfp(String convCountWfp) {
        this.convCountWfp = convCountWfp;
    }

    public String getConvCountWofp(String process) {
        convCountWofp = getSingleString("CONVEYOR_COUNT_" + process + "_WOFP", gameInfoPM.getCurrentState().getRoundNumber() - 1);
        return convCountWofp;
    }

    public void setConvCountWofp(String convCountWofp) {
        this.convCountWofp = convCountWofp;
    }

    public String getConvCount(String process) {
        convCount = getSingleString("CONVEYOR_COUNT_" + process, gameInfoPM.getCurrentState().getRoundNumber() - 1);
        return convCount;
    }

    public void setConvCount(String convCount) {
        this.convCount = convCount;
    }

    public double getWorkloadConv(String process) {
        workloadConv = 100 * Double.valueOf(getSingleString("WORKLOAD_CONVEYORS_" + process, gameInfoPM.getCurrentState().getRoundNumber() - 1));
        return workloadConv;

    }

    public void setWorkloadConv(double workloadConv) {
        this.workloadConv = workloadConv;
    }

    public String getEmpOvt(String process) {
        empOvt = getSingleString("OVT_" + process, gameInfoPM.getCurrentState().getRoundNumber() - 1);
        return empOvt;
    }

    public void setEmpOvt(String empOvt) {
        this.empOvt = empOvt;
    }

    public double getPalletsToTransport(String process) {
        double transported = Double.valueOf(getSingleString("PALLETS_TRANSPORTED_" + process, gameInfoPM.getCurrentState().getRoundNumber() - 1));
        double notTransported = Double.valueOf(getSingleString("NOT_TRANSPORTED_PALLETS_" + process, gameInfoPM.getCurrentState().getRoundNumber() - 1));
        palletsToTransport = transported + notTransported;
        return palletsToTransport;
    }

    public void setPalletsToTransport(double palletsToTransport) {
        this.palletsToTransport = palletsToTransport;
    }

    public String getPalletsTransported(String process) {
        palletsTransported = getSingleString("PALLETS_TRANSPORTED_" + process, gameInfoPM.getCurrentState().getRoundNumber() - 1);
        return palletsTransported;
    }

    public void setPalletsTransported(String palletsTransported) {
        this.palletsTransported = palletsTransported;
    }

    public String getEmpCountWfpNew(int process) {
        int count = 0;
        DoloresState currentState = gameInfoPM.getInfo().getCurrentState();
        switch (process) {
            case 0:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && ed.hasForkliftPermit() && ed.getProcess() == 0)) {
                        count++;
                    }
                }
                break;
            case 1:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && ed.hasForkliftPermit() && ed.getProcess() == 1)) {
                        count++;
                    }
                }
                break;
            case 2:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && ed.hasForkliftPermit() && ed.getProcess() == 2)) {
                        count++;
                    }
                }
                break;
            case 3:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && ed.hasForkliftPermit() && ed.getProcess() == 3)) {
                        count++;
                    }
                }
                break;
            case 4:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && ed.hasForkliftPermit() && ed.getProcess() == 4)) {
                        count++;
                    }
                }
                break;
            case 5:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && ed.hasForkliftPermit() && ed.getProcess() == 5)) {
                        count++;
                    }
                }
                break;

        }
        empCountWfpNew = String.valueOf(count);
        return empCountWfpNew;
    }

    public void setEmpCountWfpNew(String empCountWfpNew) {
        this.empCountWfpNew = empCountWfpNew;
    }

    public String getEmpCountWofpNew(int process) {
        int count = 0;
        DoloresState currentState = gameInfoPM.getInfo().getCurrentState();
        switch (process) {
            case 0:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && !ed.hasForkliftPermit() && ed.getProcess() == 0)) {
                        count++;
                    }
                }
                break;
            case 1:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && !ed.hasForkliftPermit() && ed.getProcess() == 1)) {
                        count++;
                    }
                }
                break;
            case 2:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && !ed.hasForkliftPermit() && ed.getProcess() == 2)) {
                        count++;
                    }
                }
                break;
            case 3:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && !ed.hasForkliftPermit() && ed.getProcess() == 3)) {
                        count++;
                    }
                }
                break;
            case 4:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && !ed.hasForkliftPermit() && ed.getProcess() == 4)) {
                        count++;
                    }
                }
                break;
            case 5:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && !ed.hasForkliftPermit() && ed.getProcess() == 5)) {
                        count++;
                    }
                }
                break;

        }
        empCountWofpNew = String.valueOf(count);
        return empCountWofpNew;
    }

    public void setEmpCountWofpNew(String empCountWofpNew) {
        this.empCountWofpNew = empCountWofpNew;
    }

    public String getEmpCountNew(int process) {
        int count = 0;
        DoloresState currentState = gameInfoPM.getInfo().getCurrentState();
        switch (process) {
            case 0:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && ed.getProcess() == 0)) {
                        count++;
                    }
                }
                break;
            case 1:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && ed.getProcess() == 1)) {
                        count++;
                    }
                }
                break;
            case 2:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && ed.getProcess() == 2)) {
                        count++;
                    }
                }
                break;
            case 3:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && ed.getProcess() == 3)) {
                        count++;
                    }
                }
                break;
            case 4:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && ed.getProcess() == 4)) {
                        count++;
                    }
                }
                break;
            case 5:
                for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
                    if ((ed.isReady(currentState.getRoundNumber()) && ed.getProcess() == 5)) {
                        count++;
                    }
                }
                break;

        }
        empCountNew = String.valueOf(count);
        return empCountNew;
    }

    public void setEmpCountNew(String empCountNew) {
        this.empCountNew = empCountNew;
    }

    public String getConvCountWfpNew(int process) {
        int count = 0;
        DoloresState currentState = gameInfoPM.getInfo().getCurrentState();
        switch (process) {
            case 0:
                for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
                    if ((cd.isReady(currentState.getRoundNumber()) && cd.isNeedsForkliftPermit() && cd.getProcess() == 0)) {
                        count++;
                    }
                }
                break;
            case 1:
                for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
                    if ((cd.isReady(currentState.getRoundNumber()) && cd.isNeedsForkliftPermit() && cd.getProcess() == 1)) {
                        count++;
                    }
                }
                break;
            case 2:
                for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
                    if ((cd.isReady(currentState.getRoundNumber()) && cd.isNeedsForkliftPermit() && cd.getProcess() == 2)) {
                        count++;
                    }
                }
                break;
            case 3:
                for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
                    if ((cd.isReady(currentState.getRoundNumber()) && cd.isNeedsForkliftPermit() && cd.getProcess() == 3)) {
                        count++;
                    }
                }
                break;
            case 4:
                for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
                    if ((cd.isReady(currentState.getRoundNumber()) && cd.isNeedsForkliftPermit() && cd.getProcess() == 4)) {
                        count++;
                    }
                }
                break;
            case 5:
                for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
                    if ((cd.isReady(currentState.getRoundNumber()) && cd.isNeedsForkliftPermit() && cd.getProcess() == 5)) {
                        count++;
                    }
                }
                break;

        }
        convCountWfpNew = String.valueOf(count);
        return convCountWfpNew;
    }

    public void setConvCountWfpNew(String convCountWfpNew) {
        this.convCountWfpNew = convCountWfpNew;
    }

    public String getConvCountWofpNew(int process) {
        int count = 0;
        DoloresState currentState = gameInfoPM.getInfo().getCurrentState();
        switch (process) {
            case 0:
                for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
                    if ((cd.isReady(currentState.getRoundNumber()) && !cd.isNeedsForkliftPermit() && cd.getProcess() == 0)) {
                        count++;
                    }
                }
                break;
            case 1:
                for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
                    if ((cd.isReady(currentState.getRoundNumber()) && !cd.isNeedsForkliftPermit() && cd.getProcess() == 1)) {
                        count++;
                    }
                }
                break;
            case 2:
                for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
                    if ((cd.isReady(currentState.getRoundNumber()) && !cd.isNeedsForkliftPermit() && cd.getProcess() == 2)) {
                        count++;
                    }
                }
                break;
            case 3:
                for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
                    if ((cd.isReady(currentState.getRoundNumber()) && !cd.isNeedsForkliftPermit() && cd.getProcess() == 3)) {
                        count++;
                    }
                }
                break;
            case 4:
                for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
                    if ((cd.isReady(currentState.getRoundNumber()) && !cd.isNeedsForkliftPermit() && cd.getProcess() == 4)) {
                        count++;
                    }
                }
                break;
            case 5:
                for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
                    if ((cd.isReady(currentState.getRoundNumber()) && !cd.isNeedsForkliftPermit() && cd.getProcess() == 5)) {
                        count++;
                    }
                }
                break;

        }
        convCountWofpNew = String.valueOf(count);
        return convCountWofpNew;
    }

    public void setConvCountWofpNew(String convCountWofpNew) {
        this.convCountWofpNew = convCountWofpNew;
    }

    public String getConvCountNew(int process) {
        int count = 0;
        DoloresState currentState = gameInfoPM.getInfo().getCurrentState();
        for (ConveyorDynamics cd : currentState.getConveyorDynamics()) {
            if ((cd.isReady(currentState.getRoundNumber()) && cd.getProcess() == process)) {
                count++;
            }
        }
        convCountNew = String.valueOf(count);
        return convCountNew;
    }

    public void setConvCountNew(String convCountNew) {
        this.convCountNew = convCountNew;
    }

    public String getEmpOvtNew(String process) {
        int count = 0;
        DoloresState currentState = gameInfoPM.getInfo().getCurrentState();
        empOvtNew = currentState.getValue("ovt_" + process);
        return empOvtNew;
    }

    public void setEmpOvtNew(String empOvtNew) {
        this.empOvtNew = empOvtNew;
    }

    public String getPalletsToTransportNew(int process) {
        int count = 0;
        DoloresState currentState = gameInfoPM.getInfo().getCurrentState();

        if (process == 0 || process == 1) {
            for (OrderDynamics od : currentState.getOpenOrderDynamics()) {
                if (od.getDeliveryPeriod() == currentState.getRoundNumber()) {
                    count += od.getDeliveryAmount();
                }
            }
        } else if (process == 2) {
            for (CustomerJobDynamics cjd : currentState.getJobDynamics()) {
                count += cjd.getToDeliver();
            }
            for (OrderDynamics od : currentState.getOpenOrderDynamics()) {
                if (od.getDeliveryPeriod() == currentState.getRoundNumber()) {
                    count += od.getDeliveryAmount();
                }
            }

        } else if (process == 3 || process == 4) {
            for (CustomerJobDynamics cjd : currentState.getJobDynamics()) {
                count += cjd.getToDeliver();
            }
        }
        palletsToTransportNew = String.valueOf(count);
        return palletsToTransportNew;
    }

    public void setPalletsToTransportNew(String palletsToTransportNew) {
        this.palletsToTransportNew = palletsToTransportNew;
    }
}
