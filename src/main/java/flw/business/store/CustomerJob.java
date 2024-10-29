/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author tilu
 */
@Entity
public class CustomerJob implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private double jobvalue;
    private int palette_amount;
    private int orderPeriode;
    @OneToOne
    private CustomerJobDynamics customerJobDynamics;
    
    
    public void setCustomerJobDynamics(CustomerJobDynamics customerJobDynamics){
        this.customerJobDynamics = customerJobDynamics;
    }
    
    public CustomerJob(double jobvalue, int palette_amount, int orderPeriode) {
        this();
        this.jobvalue = jobvalue;
        this.palette_amount = palette_amount;
        this.orderPeriode = orderPeriode;

    }

    public CustomerJob() {

        this.jobvalue = 0;
        this.palette_amount = 0;
        this.orderPeriode = -1;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderPeriode() {
        return orderPeriode;
    }
    
    public void setOrderPeriode(int orderPeriode){
        this.orderPeriode = orderPeriode;
    }

    public double getJobvalue() {
        return jobvalue;
    }

    public void setJobvalue(double jobvalue) {
        this.jobvalue = jobvalue;
    }

    public int getPalette_amount() {
        return palette_amount;
    }

    public void setPalette_amount(int palette_amount) {
        this.palette_amount = palette_amount;
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
        if (!(object instanceof CustomerJob)) {
            return false;
        }
        CustomerJob other = (CustomerJob) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "flw.business.store.CustomerJob[ id=" + id + " ]";
    }
}
