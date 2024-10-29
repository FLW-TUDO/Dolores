/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;

import flw.business.core.DoloresGameInfo;
import java.io.Serializable;
import javax.persistence.Entity;
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
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
    private boolean gender;
    private int age;
    private int employmentPeriode = -1;
    /*
     * EMPLOYEE_CONTRACT_FULL_TIME = 0;
     * EMPLOYEE_CONTRACT_HALF_TIME = 1;
     * EMPLOYEE_CONTRACT_TEMPORARY = 2;
     */
    private int contractType = -1;
    private int endPeriode = Integer.MAX_VALUE;     //if != -1 the employee is fired and leave after this periode the company
    
    @OneToOne
    private EmployeeDynamics employeeDynamics;
    
    @ManyToOne
    private DoloresGameInfo gameInfo;

    public Employee() {
        //dynamics = this.setDynamics(new EmployeeDynamics(id));
    }

    public Employee(String firstName, String lastName, boolean gender, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;


    }
    
    public void setGameInfo(DoloresGameInfo gameInfo){
        this.gameInfo = gameInfo;
    }
    
    public DoloresGameInfo getGameInfo(){
        return this.gameInfo;
    }
    
    public void setEmployeeDynamics(EmployeeDynamics employeeDynamics){
        this.employeeDynamics = employeeDynamics;
    }

    public Long getId() {
        return id;
    }

    //@Deprecated
    public void setId(Long id) {
        this.id = id;
    }

    public void fire(int periode) {
        this.endPeriode = periode;
    }

    public boolean isReady(int aktperiode) {
        return this.employmentPeriode <= aktperiode && aktperiode <= this.endPeriode;
    }

    public boolean isFuture(int aktperiode) {
        return this.employmentPeriode >= aktperiode;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setContractType(int contractType) {
        this.contractType = contractType;
    }

    public void setEndPeriode(int endPeriode) {
        this.endPeriode = endPeriode;
    }

    public int getEndPeriode() {
        return endPeriode;
    }

    public int getEmploymentPeriode() {
        return employmentPeriode;
    }

    public void setEmploymentPeriode(int employmentPeriode) {
        this.employmentPeriode = employmentPeriode;
    }

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isGender() {
        return gender;
    }

    public String getLastName() {
        return lastName;
    }

    public int getContractType() {
        return contractType;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(lastName);
        sb.append(", ").append(firstName);
        sb.append(", ").append(gender ? "m" : "f");
        sb.append(" (").append(age).append(")");
        return sb.toString();
    }
}
