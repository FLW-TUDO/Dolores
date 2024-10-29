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
import javax.persistence.Table;

/**
 *
 * @author tilu
 */
@Entity
@Table(name = "Stockground")
public class StockGround implements Serializable {

    private int stockGroundId;
    private int shelfnum;
    private double distSource;
    private double distDrain;
    private double distAvg;
    private String abc;
    private int fixedStockGround;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Article storedArticle;
    private int level;
    private int compartmentNum;
    @OneToOne(cascade = CascadeType.REMOVE)
    private PalletsInProgress pallet;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Storage storageFree;
    
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Storage storageOcc;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    
    public StockGround() {
    }

    public StockGround(int stockGroundId, int shelfnum, int compartmentNum, int level, double distSource, double distDrain, double distAvg, String abc, int fixedStockGround) {
        this.stockGroundId = stockGroundId;
        this.shelfnum = shelfnum;
        this.distAvg = distAvg;
        this.distDrain = distDrain;
        this.distSource = distSource;
        this.abc = abc;
        this.fixedStockGround = fixedStockGround;
        this.level = level;
        this.compartmentNum = compartmentNum;
    }
    
   

    public StockGround(int stockGroundId) {
        this.stockGroundId = stockGroundId;
    }

    public StockGround(Article article) {
        this.stockGroundId = -1;
        this.storedArticle = article;
    }

    public PalletsInProgress getPallet() {
        return pallet;
    }

    public void reset() {
        this.pallet = null;
        this.storedArticle = null;
    }

    public int getCompartmentNum() {
        return compartmentNum;
    }

    public void setPallet(PalletsInProgress pallet) {
        this.pallet = pallet;
        pallet.setStockGround(this);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    

    public int getStockGroundId() {
        return stockGroundId;
    }

    public Article getStoredArticle() {
        return storedArticle;
    }

    public void setStoredArticle(Article storedArticle) {
        this.storedArticle = storedArticle;
    }

    public String getAbc() {
        return abc;
    }

    public double getDistAvg() {
        return distAvg;
    }

    public double getDistDrain() {
        return distDrain;
    }

    public double getDistSource() {
        return distSource;
    }

    public int getFixedStockGround() {
        return fixedStockGround;
    }

    public int getShelfnum() {
        return shelfnum;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StockGround) {
            StockGround g = (StockGround) o;
            if (g.getStockGroundId() != -1 && g.getStockGroundId() == this.getStockGroundId()) {
                return true;
            } else if (g.getStoredArticle() != null && g.getStoredArticle().equals(this.getStoredArticle()) /*&& this.getStoredAmount() > 0*/) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.stockGroundId;
        return hash;
    }

    @Override
    public StockGround clone() {
        StockGround newGround = new StockGround(stockGroundId, shelfnum, compartmentNum, level, distSource, distDrain, distAvg, abc, fixedStockGround);
        newGround.setPallet(pallet);
        newGround.setStoredArticle(storedArticle);
        return newGround;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Storage getStorageFree() {
        return storageFree;
    }

    public void setStorageFree(Storage storageFree) {
        this.storageFree = storageFree;
    }

    public Storage getStorageOcc() {
        return storageOcc;
    }

    public void setStorageOcc(Storage storageOcc) {
        this.storageOcc = storageOcc;
    }

    /**
     * @param stockGroundId the stockGroundId to set
     */
    public void setStockGroundId(int stockGroundId) {
        this.stockGroundId = stockGroundId;
    }

    /**
     * @param shelfnum the shelfnum to set
     */
    public void setShelfnum(int shelfnum) {
        this.shelfnum = shelfnum;
    }

    /**
     * @param distSource the distSource to set
     */
    public void setDistSource(double distSource) {
        this.distSource = distSource;
    }

    /**
     * @param distDrain the distDrain to set
     */
    public void setDistDrain(double distDrain) {
        this.distDrain = distDrain;
    }

    /**
     * @param distAvg the distAvg to set
     */
    public void setDistAvg(double distAvg) {
        this.distAvg = distAvg;
    }

    /**
     * @param abc the abc to set
     */
    public void setAbc(String abc) {
        this.abc = abc;
    }

    /**
     * @param fixedStockGround the fixedStockGround to set
     */
    public void setFixedStockGround(int fixedStockGround) {
        this.fixedStockGround = fixedStockGround;
    }

    /**
     * @param compartmentNum the compartmentNum to set
     */
    public void setCompartmentNum(int compartmentNum) {
        this.compartmentNum = compartmentNum;
    }

    
    
    
}
