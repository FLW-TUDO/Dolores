/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;


import flw.business.core.DoloresState;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 *
 * @author tilu
 */
@Entity
public class OrderDynamics implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int deliveryAmount = 0;
    private int orderno;
    private int articleNumber;
    private boolean complete = false;
    private boolean newOrder = true;
    private boolean alreadyCalc = false;
    
    

    @Transient
    Random random = new Random();

    @ManyToOne(cascade = CascadeType.REMOVE)
    private DoloresState state;

    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Order order;
    
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PalletsInProgress> palletsInProgress;

    public OrderDynamics() {
    }

    
    
    
    public OrderDynamics(int articleNumber) {
        this.articleNumber = articleNumber;
        Random r = new Random();
        float test = (100000 + r.nextFloat() * 900000) - 1;
        this.orderno = (int) test;
        palletsInProgress = new ArrayList<PalletsInProgress>();
    }

    /**
     *
     * @param state
     * @param articleDynamics
     * @param order sets automatically the article number!
     * @param artNr
     */
    public OrderDynamics(DoloresState state, ArticleDynamics articleDynamics, Order order, int artNr) {
        this.state = state;
        this.order = order;
        if(order != null) order.setorderDynamics(this);
        this.articleNumber = artNr;
        orderno = random.nextInt();
        this.deliveryAmount = order.getOrderAmount();
    }

    public ArticleDynamics getArticleDynamics() {
        return state.getGameInfo().getCurrentState().getArticleDynamics(this.articleNumber);
        //return state.getArticleDynamics(this.articleNumber); neue Version funktioniert nicht
    }

    public List<PalletsInProgress> getPalletsInProgress() {
        if(null == palletsInProgress){
            palletsInProgress = new ArrayList<>();
        }
        return palletsInProgress;
    }

    public void setPalletsInProgress(List<PalletsInProgress> palletsInProgress) {
        this.palletsInProgress = palletsInProgress;
    }

    

    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    public void setRandomOrderNo() {
        Random r = new Random();
        float test = (100000 + r.nextFloat() * 900000) - 1;
        this.orderno = (int) test;
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
    
    public void setStateAsNull(DoloresState state){
        this.state = state;
    }

    public void setState(DoloresState state) {
        this.state = state;
        if (!state.getOpenOrderDynamics().contains(this)) {
            state.addOrderDynamcis(this);
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        order.setorderDynamics(this);
    }

    public int getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(int deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDeliveryCosts() {
        return this.order.getDeliveryCosts();
    }

    public void setDeliveryCosts(int deliveryCosts) {
        this.order.setDeliveryCosts(deliveryCosts);
    }

    public int getDeliveryPeriod() {
        return this.order.getDeliveryPeriod();
    }

    public void setDeliveryPeriod(int deliveryPeriod) {
        this.order.setDeliveryPeriod(deliveryPeriod);
    }

    public int getDeliveryWish() {
        return this.order.getDeliveryWish();
    }

    public void setDeliveryWish(int deliveryWish) {
        this.order.setDeliveryWish(deliveryWish);
    }

    public boolean isAlreadyCalc() {
        return alreadyCalc;
    }

    public void setAlreadyCalc(boolean alreadyCalc) {
        this.alreadyCalc = alreadyCalc;
    }

    public int getFixCosts() {
        return this.order.getFixCosts();
    }

    public int getOrderAmount() {
        return this.order.getOrderAmount();
    }

    public int getOrderPeriod() {
        return this.order.getOrderPeriod();
    }

    public int getRealPurchasePrice() {
        return this.order.getRealPurchasePrice();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderDynamics other = (OrderDynamics) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isNewOrder() {
        return newOrder;
    }

    public void setNewOrder(boolean onPallet) {
        this.newOrder = onPallet;
    }

    @Override
    public String toString() {
        return "flw.business.store.OrderDynamics[ id=" + id + " ]";
    }
}
