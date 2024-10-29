/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresState;
import flw.business.store.Employee;
import flw.business.store.EmployeeDynamics;
import flw.business.store.EmployeeFactory;
import flw.business.util.DoloresConst;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author tilu
 */
@ManagedBean(name = "employee")
@ViewScoped
public class EmployeePM implements Serializable {

    @ManagedProperty(value = "#{gameInfoPM}")
    private GameInfoPM gameInfoPM;
    private List<EmployeeDynamics> marketEmployeeDynamics;
    private String employeeOvertimeEN;
    private String employeeOvertimeWV;
    private String employeeOvertimeLA;
    private String employeeOvertimeWK;
    private String employeeOvertimeVE;
    private List<EmployeeDynamics> dynamics;
    private List<EmployeeDynamics> currentDynamics;
    private EmployeeDynamics clickedDynamics;
    private DataModel<EmployeeDynamics> marketEmployeeDynamicsModel;
    private int count = 0;
    @EJB
    private Service service;

    public EmployeePM() {
    }

    @PostConstruct
    public void init() {
        marketEmployeeDynamicsModel = new ListDataModel<>(marketEmployeeDynamics);
    }

    public void setGameInfoPM(GameInfoPM gameInfoPM) {
        this.gameInfoPM = gameInfoPM;

    }

    public int getRoundNumber() {

        return gameInfoPM.getCurrentState().getRoundNumber();
    }

    public DataModel<EmployeeDynamics> getMarketEmployeeDynamicsModel() {
        marketEmployeeDynamicsModel = new ListDataModel<>(this.getMarketEmployeeDynamics());
        return marketEmployeeDynamicsModel;
    }

    public EmployeeDynamics getClickedDynamics() {
        return clickedDynamics;
    }

    public void setClickedDynamics(EmployeeDynamics clickedDynamics) {
        this.clickedDynamics = clickedDynamics;
    }

    /**
     *
     * @return The dynamics of current employeed employees
     */
    public List<EmployeeDynamics> getCurrentDynamics() {

        currentDynamics = new ArrayList<>();
        List<EmployeeDynamics> tempList = getEmployeeDynamics();
        int currentRoundNumber = gameInfoPM.getCurrentState().getRoundNumber();

        for (EmployeeDynamics ed : tempList) {
            if (ed.isReady(currentRoundNumber)) {
                currentDynamics.add(ed);
            }
        }

        return currentDynamics;
    }

    /**
     *
     * @return The dynamics of future employeed employees
     */
    public List<EmployeeDynamics> getFutureDynamics() {

        currentDynamics = new ArrayList<>();
        List<EmployeeDynamics> tempList = getEmployeeDynamics();
        int currentRoundNumber = gameInfoPM.getCurrentState().getRoundNumber();

        for (EmployeeDynamics ed : tempList) {
            if (ed.isFuture(currentRoundNumber)) {
                currentDynamics.add(ed);
            }
        }

        return currentDynamics;
    }

    /**
     * gets called to update the overtime
     */
    public void ajaxUpdateEmployeeDynamics() {
        ResourceBundle bundle = ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale());

        DoloresGameInfo info = gameInfoPM.getInfo();
        info.getCurrentState().setEmployeeDynamics(dynamics);

        //System.out.println(bundle.getString("growl.einsatzplan"));

        //System.out.println(currentDynamics.get(0).getProcess());

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundle.getString("growl.einsatzplan")));
    }

    public void updateEmployeeDynamics() {
        DoloresGameInfo info = gameInfoPM.getInfo();
        info.getCurrentState().setEmployeeDynamics(dynamics);
    }

    /**
     *
     * @return The Dynamics of ALL Employees (future + current)
     */
    public List<EmployeeDynamics> getEmployeeDynamics() {

        DoloresGameInfo info = gameInfoPM.getInfo();
        this.dynamics = info.getCurrentState().getEmployeeDynamics();
        return dynamics;
    }

    public String getEmployeeOvertimeEN() {
        return this.getEmployeeOvertime("0.en");
    }

    public void setEmployeeOvertimeEN(String employeeOvertimeEN) {
        this.setEmployeeOvertime(employeeOvertimeEN);
        this.employeeOvertimeEN = employeeOvertimeEN;
    }

    public String getEmployeeOvertimeWV() {
        return this.getEmployeeOvertime("0.wv");
    }

    public void setEmployeeOvertimeWV(String employeeOvertimeWV) {
        this.setEmployeeOvertime(employeeOvertimeWV);
        this.employeeOvertimeWV = employeeOvertimeWV;
    }

    public String getEmployeeOvertimeLA() {
        return this.getEmployeeOvertime("0.la");
    }

    public void setEmployeeOvertimeLA(String employeeOvertimeLA) {
        this.setEmployeeOvertime(employeeOvertimeLA);
        this.employeeOvertimeLA = employeeOvertimeLA;
    }

    public String getEmployeeOvertimeWK() {
        return this.getEmployeeOvertime("0.wk");
    }

    public void setEmployeeOvertimeWK(String employeeOvertimeWK) {
        this.setEmployeeOvertime(employeeOvertimeWK);
        this.employeeOvertimeWK = employeeOvertimeWK;
    }

    public String getEmployeeOvertimeVE() {
        return this.getEmployeeOvertime("0.ve");
    }

    public void setEmployeeOvertimeVE(String employeeOvertimeVE) {
        this.employeeOvertimeVE = employeeOvertimeVE;
    }

    public String getEmployeeOvertime(String process) {
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState ds = info.getCurrentState();
        String[] segs = process.split(Pattern.quote("."));

        String temp = ds.getValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(segs[1]).toString());
        String employeeOvertime = new StringBuilder(temp).append(".").append(segs[1]).toString();
        return employeeOvertime;
    }

    public void setEmployeeOvertime(String employeeOvertime) {

        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState ds = info.getCurrentState();
        String[] segs = employeeOvertime.split(Pattern.quote("."));

        ds.setValue(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(segs[1]).toString(), segs[0]);

    }

    public void ajaxEmployeeOvtEN() {
        this.setEmployeeOvertime(employeeOvertimeEN);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.ovtEN")));
    }

    public void ajaxEmployeeOvtWV() {
        this.setEmployeeOvertime(employeeOvertimeWV);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.ovtWV")));
    }

    public void ajaxEmployeeOvtLA() {
        this.setEmployeeOvertime(employeeOvertimeLA);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.ovtLA")));
    }

    public void ajaxEmployeeOvtWK() {
        this.setEmployeeOvertime(employeeOvertimeWK);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.ovtWK")));
    }

    public void ajaxEmployeeOvtVE() {
        this.setEmployeeOvertime(employeeOvertimeVE);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.ovtVE")));
    }



    public void setQM() {
        clickedDynamics.setQmRound(gameInfoPM.getCurrentState().getRoundNumber() + 2);
        clickedDynamics.setQualifications(clickedDynamics.getQualifications() + DoloresConst.EMPLOYEE_QUALIFICATION_QMSEMINAR_INPROGRESS);
    }

    public void setSec() {
        clickedDynamics.setSecRound(gameInfoPM.getCurrentState().getRoundNumber() + 1);
        clickedDynamics.setQualifications(clickedDynamics.getQualifications() + DoloresConst.EMPLOYEE_QUALIFICATION_SECURITY_TRAINING_INPROGRESS);
    }

    public void setFP() {
        clickedDynamics.setFpRound(gameInfoPM.getCurrentState().getRoundNumber() + 2);
        clickedDynamics.setQualifications(clickedDynamics.getQualifications() + DoloresConst.EMPLOYEE_QUALIFICATION_FORKLIFT_PERMIT_INPROGRESS);
    }

    public void employ(int cType) {
        EmployeeDynamics eD = clickedDynamics;

        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState ds = info.getCurrentState();

        Employee e = eD.getEmployee();
        e.setContractType(cType);
        if(cType == 2){
            e.setEndPeriode(Integer.parseInt(ds.getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER)) + 4);
            e.setEmploymentPeriode(Integer.parseInt(ds.getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER)) + 1);
            eD.setSalary((int) (eD.getSalary() * 1.20));
        }
        else e.setEmploymentPeriode(Integer.parseInt(ds.getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER)) + 3);

        ds.addEmployeeDynamics(eD);

        marketEmployeeDynamics.remove(eD);

        count--;

    }

    //check in calc if endperiod == currentperiod -> remove!
    public void unemploy() {

        EmployeeDynamics e = clickedDynamics;
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState ds = info.getCurrentState();
        if(e.getContractType() == 2) e.fire(Integer.parseInt(ds.getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER)) + 1);
            else e.fire(Integer.parseInt(ds.getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER)) + 3);
        service.mergeEmployee(e.getEmployee());
    }

    public List<EmployeeDynamics> getMarketEmployeeDynamics() {

        if (count == 0) {

            marketEmployeeDynamics = new ArrayList<>();
        }

        while (count < 10) {
            Employee e = EmployeeFactory.createEmployee();
            EmployeeDynamics eD = EmployeeFactory.createEmployeeDynamics(e.getAge());
            eD.setProcess(0);
            eD.setEmployee(e);

            marketEmployeeDynamics.add(eD);
            count++;
        }
        return marketEmployeeDynamics;
    }


}
