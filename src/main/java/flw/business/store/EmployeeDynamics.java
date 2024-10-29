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
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author tilu
 */
@Entity
@NamedQuery(name = "findAllEmpDyn", query = "SELECT c FROM EmployeeDynamics c")
public class EmployeeDynamics implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int qmRound = 0;
    private int fpRound = 0;
    private int secRound = 0;
    /*
     * 1 = FP
     * 3 = FP + Security
     * 4 = QM
     * 5 = FP + QM
     * 7 = FP + QM + Security
     */
    private int qualifications;
    private double motivationPercentage;
    private int process = 0;
    private int salary;
    private int roundnumber;
    //private Long employeeId;
    @ManyToOne
    private DoloresState state;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Employee employee;

    public int getQmRound() {
        return qmRound;
    }

    public void setQmRound(int qmRound) {
        this.qmRound = qmRound;
    }

    public int getFpRound() {
        return fpRound;
    }

    public void setFpRound(int fpRound) {
        this.fpRound = fpRound;
    }

    public int getSecRound() {
        return secRound;
    }

    public void setSecRound(int secRound) {
        this.secRound = secRound;
    }

    public int getCompensationCost() {
        int employedDuration = roundnumber - employee.getEmploymentPeriode();
        if(employee.getContractType() == 2) return 0;
        return (int) (Math.round(this.getSalary() * employedDuration) * DoloresConst.EMPLOYEE_FACTOR_COMPENSATION);
    }

    public boolean isTemporary() {
        return this.employee.getContractType() == 2;
    }

    public EmployeeDynamics() {
    }

    public EmployeeDynamics(Long employeeId) {

        this.qualifications = 0;

    }

    public EmployeeDynamics(int qualifications, double motivationPercentage, int process, int salary, Long employeeId) {
        this();
        this.qualifications = qualifications;
        this.process = process;
        this.motivationPercentage = motivationPercentage;
        this.salary = salary;

    }

    public EmployeeDynamics(int qualifications, int salary) {

        this.qualifications = qualifications;

        this.salary = salary;

    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        employee.setEmployeeDynamics(this);

    }

    public DoloresState getState() {
        return state;
    }

    public void setState(DoloresState state) {
        this.state = state;
        if (!state.getEmployeeDynamics().contains(this)) {
            state.addEmployeeDynamics(this);
        }

    }

    public int getRoundnumber() {
        return roundnumber;
    }

    public void setRoundnumber(int roundnumber) {
        this.roundnumber = roundnumber;
    }

    public void raiseMotivation(double value) {

        setMotivationPercentage(getMotivationPercentage() + value);
        if (getMotivationPercentage() > 100) {
            setMotivationPercentage(100);
        }
    }

    public boolean employ(int periode, int process, int contractType) {
        if (employee.getEmploymentPeriode() == -1) {
            employee.setEmploymentPeriode(periode);
            employee.setContractType(contractType);
            this.process = process;
            return true;
        } else {
            return false;
        }
    }

    public boolean hasForkliftPermit() {
        return ((this.getQualifications() & DoloresConst.EMPLOYEE_QUALIFICATION_FORKLIFT_PERMIT) != 0);
    }

    public boolean hasQMSeminar() {
        return ((this.getQualifications() & DoloresConst.EMPLOYEE_QUALIFICATION_QMSEMINAR) != 0);
    }

    public boolean hasSecurityTraining() {
        return ((this.getQualifications() & DoloresConst.EMPLOYEE_QUALIFICATION_SECURITY_TRAINING) != 0);
    }

    public boolean hasForkliftPermitInProgress() {
        return ((this.getQualifications() & DoloresConst.EMPLOYEE_QUALIFICATION_FORKLIFT_PERMIT_INPROGRESS) != 0);
    }

    public boolean hasQMSeminarInProgress() {
        return ((this.getQualifications() & DoloresConst.EMPLOYEE_QUALIFICATION_QMSEMINAR_INPROGRESS) != 0);
    }

    public boolean hasSecurityTrainingInProgress() {
        return ((this.getQualifications() & DoloresConst.EMPLOYEE_QUALIFICATION_SECURITY_TRAINING_INPROGRESS) != 0);
    }

    public boolean hasNoEndPeriod() {
        return (employee.getEndPeriode() == Integer.MAX_VALUE);
    }

    public void fire(int periode) {
        this.employee.setEndPeriode(periode);
    }

    public boolean isReady(int aktperiode) {
        return employee.getEmploymentPeriode() <= aktperiode && aktperiode <= employee.getEndPeriode();
    }

    public boolean isFuture(int aktperiode) {
        return employee.getEmploymentPeriode() > aktperiode;
    }

    public int getContractType() {
        return employee.getContractType();
    }

    public int getEndPeriode() {
        return employee.getEndPeriode();
    }

    public int getEmploymentPeriode() {
        return employee.getEmploymentPeriode();
    }

    public String getFirstName() {
        return employee.getFirstName();
    }

    public String getLastName() {
        return employee.getLastName();
    }

    public int getAge() {
        return employee.getAge();
    }

    public EmployeeDynamics evolve() {
        EmployeeDynamics ed = new EmployeeDynamics();
        ed.qualifications = this.qualifications;
        ed.setProcess(process);
        ed.setMotivationPercentage(motivationPercentage);
        ed.setSalary(salary);
        return ed;
    }

    public int getQualifications() {
        return qualifications;
    }

    public void setQualifications(int qualifications) {
        this.qualifications = qualifications;
    }

    public double getMotivationPercentage() {
        return motivationPercentage;
    }

    public double getMotivationPercentageAsInt() {
        return motivationPercentage*100;
    }

    public void setMotivationPercentage(double motivationPercentage) {
        this.motivationPercentage = motivationPercentage;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "flw.business.store.EmployeeDynamics[ id=" + id + " ]";
    }
}


/*
 *  private String firstName;
 private String lastName;
 private boolean gender;
 private int age;
 private int employmentPeriode = -1;
 private int contractType = -1;
 private int endPeriode = Integer.MAX_VALUE; 
 */
