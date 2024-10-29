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
public class Conveyor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private int capacity;
    private double price;
    private int timeToDelivery;
    private boolean useInStorage;
    private boolean needsForkliftPermit;
    private int timeToRepair;
    private double speed;
    private int maintenanceCost;
    private int conveyorId;
    @OneToOne
    private ConveyorDynamics conveyorDynamcis;
    
    @ManyToOne
    private DoloresGameInfo gameInfo;
    
    
    public Conveyor() {
    }

    public Conveyor(String name, int capacity, double price, int timeToDelivery, double speed, int timeToRepair, boolean useInStorage, boolean needsForkliftPermit, int maintenanceCost, int conveyorId) {
        this();
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.timeToDelivery = timeToDelivery;
        this.useInStorage = useInStorage;
        this.needsForkliftPermit = needsForkliftPermit;
        this.speed = speed;
        this.maintenanceCost = maintenanceCost;
        this.timeToRepair = timeToRepair;
        this.conveyorId = conveyorId; 
    }

    public Conveyor createCopy() {
        Conveyor toReturn = new Conveyor(this.name, this.capacity, this.price, this.timeToDelivery, this.speed, this.timeToRepair, this.useInStorage, this.needsForkliftPermit, this.maintenanceCost, this.conveyorId);
        return toReturn;
    }
    
    public void setGameInfo(DoloresGameInfo gameInfo){
        this.gameInfo = gameInfo;
    }
    
    public DoloresGameInfo getGameInfo(){
        return this.gameInfo;
    }

    public void setConveyorDynamcis(ConveyorDynamics conveyorDynamics){
        this.conveyorDynamcis = conveyorDynamics;
    }
    
    public int getTimeToRepair() {
        return timeToRepair;
    }
    
    public void setTimeToRepair(int timeToRepair){
        this.timeToRepair = timeToRepair;
    }

    public boolean needsForkliftPermit() {
        return this.needsForkliftPermit;
    }

    public double getSpeed() {
        return speed;
    }
    
    public void setSpeed(double speed){
        this.speed = speed;
    }

    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price){
        this.price = price;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getTimeToDelivery() {
        return timeToDelivery;
    }

    public void setTimeToDelivery(int timeToDelivery) {
        this.timeToDelivery = timeToDelivery;
    }

    public boolean isUseInStorage() {
        return useInStorage;
    }

    public void setUseInStorage(boolean useInStorage) {
        this.useInStorage = useInStorage;
    }

    public int getConveyorId() {
        return conveyorId;
    }

    public void setConveyorId(int conveyorId) {
        this.conveyorId = conveyorId;
    }

    
    
    public boolean isNeedsForkliftPermit() {
        return needsForkliftPermit;
    }

    public void setNeedsForkliftPermit(boolean needsForkliftPermit) {
        this.needsForkliftPermit = needsForkliftPermit;
    }

    public int getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(int maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
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
        if (!(object instanceof Conveyor)) {
            return false;
        }
        Conveyor other = (Conveyor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "flw.business.store.Conveyor[ id=" + id + " ]";
    }
}
