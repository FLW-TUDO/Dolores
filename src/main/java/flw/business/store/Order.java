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
import javax.persistence.Table;

/**
 *
 * @author tilu
 */
@Entity
@Table(name = "Orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int orderPeriod;
    private int deliveryPeriod;
    private int deliveryWish;
    private int fixCosts;
    private int deliveryCosts;
    private int realPurchasePrice; //ANM: genutzt als Preis. Beim erzeugen Rabatte abziehen (oder auch nachträglich neuen Preis setzen!.)
    private int orderAmount;
    
    @OneToOne
    private OrderDynamics orderDynamics;
    
    @ManyToOne
    private DoloresGameInfo gameInfo;

    public Order(int period, int deliveryDuration, int deliveryWish, int fixcosts, int deliveryCosts, int realPurchasePrice, int orderAmount) {
        this();

        if (deliveryWish == 0) {
            this.deliveryWish = period + deliveryDuration;
            this.deliveryPeriod = period + deliveryDuration;
        } else {
            int help = period + deliveryDuration;
            if (help > deliveryWish) {
                this.deliveryWish = help;
                this.deliveryPeriod = help;
            } else {
                this.deliveryWish = deliveryWish;
                this.deliveryPeriod = deliveryWish;
            }
        }

        this.orderPeriod = period;
        this.fixCosts = fixcosts;
        this.deliveryCosts = deliveryCosts;
        this.realPurchasePrice = realPurchasePrice;
        this.orderAmount = orderAmount;
    }

    public Order() {
        this.orderPeriod = -1;
        this.deliveryPeriod = -1;
        this.deliveryWish = 0;

        this.fixCosts = 0;
        this.deliveryCosts = 0;
        this.realPurchasePrice = 0;
        this.orderAmount = 0;

    }
    
    public void setGameInfo(DoloresGameInfo gameInfo){
        this.gameInfo = gameInfo;
    }
    
    public DoloresGameInfo getGameInfo(){
        return this.gameInfo;
    }
    
    public void setorderDynamics(OrderDynamics orderDynamics){
        this.orderDynamics = orderDynamics;
    }

    public int getDeliveryCosts() {
        return deliveryCosts;
    }

    public void setDeliveryCosts(int deliveryCosts) {
        this.deliveryCosts = deliveryCosts;
    }

    public int getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public void setDeliveryPeriod(int deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    public int getDeliveryWish() {
        return deliveryWish;
    }

    public void setDeliveryWish(int deliveryWish) {
        this.deliveryWish = deliveryWish;
    }

    public int getFixCosts() {
        return fixCosts;
    }
    
    public void setFixCosts(int fixCosts){
        this.fixCosts = fixCosts;
    }

    public int getOrderAmount() {
        return orderAmount;
    }
    
    public void setOrderAmount(int orderAmount){
        this.orderAmount = orderAmount;
    }

    public int getOrderPeriod() {
        return orderPeriod;
    }
    
    public void setOrderPeriod(int orderPeriod){
        this.orderPeriod = orderPeriod;
    }

    public int getRealPurchasePrice() {
        return realPurchasePrice;
    }
    
    public void setRealPurchasePrice(int realPurchasePrice){
        this.realPurchasePrice = realPurchasePrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "flw.business.store.Order[ id=" + id + " ]";
    }
}
