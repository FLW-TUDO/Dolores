/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;

import flw.business.core.DoloresState;
import java.io.Serializable;
import javax.persistence.CascadeType;
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
public class CustomerJobDynamics implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int toDeliver;
    private int requestedAmount;
    private int articleNumber;
    private boolean finished = false;
    @OneToOne(cascade = CascadeType.ALL)
    private ArticleDynamics articleDynamics;
    @ManyToOne
    private DoloresState state;
    @OneToOne
    private CustomerJob customerJob;

    public CustomerJobDynamics() {
    }

    public CustomerJobDynamics(int toDeliver, int requestedAmount) {
        this.toDeliver = toDeliver;
        this.requestedAmount = requestedAmount;
    }

    

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(int articleNumber) {
        this.articleNumber = articleNumber;
    }

    public DoloresState getState() {
        return state;
    }

    public void setState(DoloresState state) {
        this.state = state;
        if (!state.getJobDynamics().contains(this)) {
            state.getJobDynamics().add(this);
        }
    }

    public CustomerJob getCustomerJob() {
        return customerJob;
    }

    public void setCustomerJob(CustomerJob customerJob) {
        this.customerJob = customerJob;
        customerJob.setCustomerJobDynamics(this);
    }

    public int getToDeliver() {
        return toDeliver;
    }

    public void setToDeliver(int toDeliver) {
        this.toDeliver = toDeliver;
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(int requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    //Methods to Access CustomerJob Values
    public int getOrderPeriode() {
        return this.customerJob.getOrderPeriode();
    }

    public ArticleDynamics getArticleDynamics() {
        return articleDynamics;
    }

    public void setArticleDynamics(ArticleDynamics articleDynamics) {
        this.articleNumber = articleDynamics.getArticleNumber();

        this.articleDynamics = articleDynamics;
    }

    public double getJobvalue() {
        return this.customerJob.getJobvalue();
    }

    public void setJobvalue(double jobvalue) {
        this.customerJob.setJobvalue(jobvalue);
    }

    public int getPalette_amount() {
        return this.customerJob.getPalette_amount();
    }

    public void setPalette_amount(int palette_amount) {
        this.customerJob.setPalette_amount(palette_amount);
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
        if (!(object instanceof CustomerJobDynamics)) {
            return false;
        }
        CustomerJobDynamics other = (CustomerJobDynamics) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "flw.business.store.CustomerJobDynamics[ id=" + id + " ]";
    }
}
