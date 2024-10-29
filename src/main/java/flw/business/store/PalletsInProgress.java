/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;


import flw.business.core.DoloresState;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author tilu
 */
@Entity
public class PalletsInProgress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private boolean unitLoadSafetyDevices;
    private int error;
    private int demandRound;
    private int currentProcess;
    private int orderno;
    private boolean stored = false;
    private int stockgroundId;
    
    @OneToOne(cascade = CascadeType.REMOVE)
    private OrderDynamics orderDynamics;
    
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private StockGround stockGround;
    
    @ManyToOne
    private DoloresState state;

    public PalletsInProgress() {
        this.error = 0;
        this.unitLoadSafetyDevices = false;

    }

    public PalletsInProgress(int demandRound, int currentProcess, int orderno) {
        this.demandRound = demandRound;
        this.currentProcess = currentProcess;
        this.orderno = orderno;
    }

    public PalletsInProgress(boolean unitLoadSafetyDevices, int error) {
        this();

        this.unitLoadSafetyDevices = unitLoadSafetyDevices;
        this.error = error;
    }
    
    public void setState(DoloresState state){
        this.state = state;
    }
    
    public DoloresState getState(){
        return this.state;
    }

    public StockGround getStockGround() {
        return stockGround;
    }

    public void setStockGround(StockGround stockGround) {
        this.stockGround = stockGround;
    }
    
    

    public int getDemandRound() {
        return demandRound;
    }

    public void setDemandRound(int demandRound) {
        this.demandRound = demandRound;
    }

    public int getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(int currentProcess) {
        this.currentProcess = currentProcess;
    }

    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    public boolean isStored() {
        return stored;
    }

    public void setStored(boolean stored) {
        this.stored = stored;
    }

    public int getStockgroundId() {
        return stockgroundId;
    }

    public void setStockgroundId(int stockgroundId) {
        this.stockgroundId = stockgroundId;
    }

    public OrderDynamics getOrderDynamics() {
        return orderDynamics;
    }

    public void setOrderDynamics(OrderDynamics orderDynamics) {
        this.orderDynamics = orderDynamics;
        if(!orderDynamics.getPalletsInProgress().contains(this)){
            orderDynamics.getPalletsInProgress().add(this);
        }
    }

    

    public boolean isUnitLoadSafetyDevices() {
        return unitLoadSafetyDevices;
    }

    public void setUnitLoadSafetyDevices(boolean unitLoadSafetyDevices) {
        this.unitLoadSafetyDevices = unitLoadSafetyDevices;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
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
    public String toString() {
        return "flw.business.store.PalletsInProgress[ id=" + id + " ]";
    }
}
