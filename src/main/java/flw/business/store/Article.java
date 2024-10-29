/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;

import flw.business.core.DoloresGameInfo;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    private ArticleDynamics articleDynamics;
    private String name;
    private double pricePerPallet;		//price per pellet the user paid to delivering company 
    private int rebateCost1;
    private int rebateAmount1;
    private int rebateCost2;
    private int rebateAmount2;
    private int articleNumber;
    private int fixCosts;
    private double salePrice;			//price per pallet the customers pay to player-company
    private String abc_classification;
    private int normalDelivery;
    private int expressDelivery;
    private int normalDeliveryCost;
    private int expressDeliveryCost;
    private int minOrder;
    
    @ManyToOne
    private DoloresGameInfo gameInfo;

    public Article() {
    }

    public Article(String name, double salePrice, int articleNumber, double pricePerPallet, int fixcosts, int rabateAmount1, int rabateAmount2, int rabateCost1, int rebateCost2, int normalDelivery, int expressDelivery, int minOrder, String abc_classification) {

        this.name = name;
        this.salePrice = salePrice;
        this.articleNumber = articleNumber;
        this.pricePerPallet = pricePerPallet;
        this.abc_classification = abc_classification;
        this.rebateAmount1 = rabateAmount1; // ab 50 paletten
        this.rebateAmount2 = rabateAmount2;
        this.rebateCost1 = rabateCost1; // kostets nur noch so und so viel
        this.rebateCost2 = rebateCost2;
        this.articleDynamics = null;
        this.fixCosts = fixcosts;
        this.normalDelivery = normalDelivery;
        this.expressDelivery = expressDelivery;
        this.minOrder = minOrder;
        this.normalDeliveryCost = 20;
        this.expressDeliveryCost = 45;


    }
    
    public void setGameInfo(DoloresGameInfo gameInfo){
        this.gameInfo = gameInfo;
    }
    
    public DoloresGameInfo getGameInfo(){
        return this.gameInfo;
    }
    
    public void setArticleDynamics(ArticleDynamics articleDynamics){
        this.articleDynamics = articleDynamics;
    }

    public int getMinOrder() {
        return minOrder;
    }

    public int getFixCosts() {
        return fixCosts;
    }

    public int getRebateAmount1() {
        return rebateAmount1;
    }

    public int getRebateAmount2() {
        return rebateAmount2;
    }

    public int getRebateCost1() {
        return rebateCost1;
    }

    public int getRebateCost2() {
        return rebateCost2;
    }

    public double getSalePrice() {
        return salePrice;
    }
    
    
    public double getSalePriceAsInt() {
        return (int)salePrice;
    }

    public String getAbcClassification() {
        return abc_classification;
    }

    public int getArticleNumber() {
        return articleNumber;
    }

    public String getName() {
        return name;
    }

    public double getPricePerPallet() {
        return (int)pricePerPallet;
    }
    
    public int getPricePerPalletView(){
        return (int)pricePerPallet;
    }

   
    
    public String getAbc_classification() {
        return abc_classification;
    }

    public int getNormalDelivery() {
        return normalDelivery;
    }

    public int getExpressDelivery() {
        return expressDelivery;
    }

    public int getNormalDeliveryCost() {
        return normalDeliveryCost;
    }

    public int getExpressDeliveryCost() {
        return expressDeliveryCost;
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
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "flw.business.store.Article[ id=" + id + " ]";
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param pricePerPallet the pricePerPallet to set
     */
    public void setPricePerPallet(double pricePerPallet) {
        this.pricePerPallet = pricePerPallet;
    }

    /**
     * @param rebateCost1 the rebateCost1 to set
     */
    public void setRebateCost1(int rebateCost1) {
        this.rebateCost1 = rebateCost1;
    }

    /**
     * @param rebateAmount1 the rebateAmount1 to set
     */
    public void setRebateAmount1(int rebateAmount1) {
        this.rebateAmount1 = rebateAmount1;
    }

    /**
     * @param rebateCost2 the rebateCost2 to set
     */
    public void setRebateCost2(int rebateCost2) {
        this.rebateCost2 = rebateCost2;
    }

    /**
     * @param rebateAmount2 the rebateAmount2 to set
     */
    public void setRebateAmount2(int rebateAmount2) {
        this.rebateAmount2 = rebateAmount2;
    }

    /**
     * @param articleNumber the articleNumber to set
     */
    public void setArticleNumber(int articleNumber) {
        this.articleNumber = articleNumber;
    }

    /**
     * @param fixCosts the fixCosts to set
     */
    public void setFixCosts(int fixCosts) {
        this.fixCosts = fixCosts;
    }

    /**
     * @param salePrice the salePrice to set
     */
    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * @param abc_classification the abc_classification to set
     */
    public void setAbc_classification(String abc_classification) {
        this.abc_classification = abc_classification;
    }

    /**
     * @param normalDelivery the normalDelivery to set
     */
    public void setNormalDelivery(int normalDelivery) {
        this.normalDelivery = normalDelivery;
    }

    /**
     * @param expressDelivery the expressDelivery to set
     */
    public void setExpressDelivery(int expressDelivery) {
        this.expressDelivery = expressDelivery;
    }

    /**
     * @param normalDeliveryCost the normalDeliveryCost to set
     */
    public void setNormalDeliveryCost(int normalDeliveryCost) {
        this.normalDeliveryCost = normalDeliveryCost;
    }

    /**
     * @param expressDeliveryCost the expressDeliveryCost to set
     */
    public void setExpressDeliveryCost(int expressDeliveryCost) {
        this.expressDeliveryCost = expressDeliveryCost;
    }

    /**
     * @param minOrder the minOrder to set
     */
    public void setMinOrder(int minOrder) {
        this.minOrder = minOrder;
    }
}
