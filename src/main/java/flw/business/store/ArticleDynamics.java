/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;

import flw.business.core.DoloresState;
import flw.business.util.SerializableInteger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author tilu
 */
@Entity
public class ArticleDynamics implements Serializable {

    private static final long serialVersionUID = 1L;
    private final long pointInTime;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int consumption = 0;                //Consumption of Article in current periode
    private int lateDeliveredPallets = 0;       //Pallets late delivered in current periode
    private double salesIncome = 0;		//incoming money for pallets sold to customers in current periode
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SerializableInteger> palletsOfArticleInProcess;
    @ManyToOne
    DoloresState state;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Article article;
    private int estimatedRange;
    private int avgConsumption;
    private int optimalOrderAmount;
    private int incomingPallets = 0;
    private int orderedPallets;
    private int orderCosts;

    public ArticleDynamics() {
        pointInTime = System.currentTimeMillis();
        palletsOfArticleInProcess = new ArrayList<SerializableInteger>();
    }
    
    public ArticleDynamics(int pointInTime){
        this.pointInTime = pointInTime;
        palletsOfArticleInProcess = new ArrayList<SerializableInteger>();
    }

    public ArticleDynamics(List<SerializableInteger> palletsOfArticleInProcess, DoloresState state, Article article, int estimatedRange, int avgConsumption, int optimalOrderAmount, int orderedPallets, int orderCosts) {
        pointInTime = System.currentTimeMillis();
        this.palletsOfArticleInProcess = palletsOfArticleInProcess;
        this.state = state;
        this.article = article;
        if(this.article != null)    this.article.setArticleDynamics(this);
        this.estimatedRange = estimatedRange;
        this.avgConsumption = avgConsumption;
        this.optimalOrderAmount = optimalOrderAmount;
        this.orderedPallets = orderedPallets;
        this.orderCosts = orderCosts;
        //this.orderCosts = 0;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
        if(this.article != null)    this.article.setArticleDynamics(this);
    }

    public DoloresState getState() {
        return state;
    }

    public void setState(DoloresState state) {
        this.state = state;
        if (!state.getArticleDynamics().contains(this)) {
            state.addArticleDynamics(this);
        }
    }

    public void setAllSerializableInteger()
    {
        for(SerializableInteger si : palletsOfArticleInProcess){
            si.setArticleDynamics(this);
        }
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void increaseSalesIncome(double amount) {
        salesIncome += amount;
    }

    public void increaseIncomingPallets(int amount) {
        setIncomingPallets(getIncomingPallets() + amount);
    }

    public int getOrderCosts() {
        return orderCosts;
    }

    public void setOrderCosts(int orderCosts) {
        this.orderCosts = orderCosts;
    }

    public int getOrderedPallets() {
        return orderedPallets;
    }

    public void setOrderedPallets(int orderedPallets) {
        this.orderedPallets = orderedPallets;
    }

    public int getIncomingPallets() {
        return incomingPallets;
    }

    public void setIncomingPallets(int incomingPallets) {
        this.incomingPallets = incomingPallets;
    }

    public int getOptimalOrderAmount() {
        return optimalOrderAmount;
    }

    public void setOptimalOrderAmount(int optimalOrderAmount) {
        this.optimalOrderAmount = optimalOrderAmount;
    }

    public int getAvgConsumption() {
        return avgConsumption;
    }

    public double getPricePerPallet() {
        return this.article.getPricePerPallet();
    }

    public void setAvgConsumption(int avgConsumption) {
        this.avgConsumption = avgConsumption;
    }

    public int getEstimatedRange() {
        return estimatedRange;
    }

    public void setEstimatedRange(int estimatedRange) {
        this.estimatedRange = estimatedRange;
    }

    public void increaseLateDeliveredPallets() {
        lateDeliveredPallets++;
    }

    public void increaseConsumption() {
        this.consumption++;
    }

    public void increasePalletCount(String process) {
        int processIdx = this.getProcessIndexForAbbrevation(process);
        this.checkProcessInPalletsOfArticleInProcess(process, 0);
        SerializableInteger si = new SerializableInteger(palletsOfArticleInProcess.get(processIdx).getValue() + 1);
        si.setArticleDynamics(this);
        palletsOfArticleInProcess.set(processIdx, si);
    }

    public void decreasePalletCount(String process) {
        int processIdx = this.getProcessIndexForAbbrevation(process);
        this.checkProcessInPalletsOfArticleInProcess(process, 1);
        SerializableInteger si = new SerializableInteger(palletsOfArticleInProcess.get(processIdx).getValue() - 1);
        si.setArticleDynamics(this);
        palletsOfArticleInProcess.set(processIdx, si);
    }

    public int getPalletCount(String process) {
        int processIdx = this.getProcessIndexForAbbrevation(process);
        this.checkProcessInPalletsOfArticleInProcess(process, 0);
        return palletsOfArticleInProcess.get(processIdx).getValue();
    }

    public int getPalletCountOfAllProcesses() {
        int toReturn = 0;
        for (SerializableInteger integer : palletsOfArticleInProcess) {
            toReturn += integer.getValue();
        }
        return toReturn;
    }

    public int getPalletCountNotInStorage() {
        int toReturn = 0;
        int count = 0;
        for (SerializableInteger integer : palletsOfArticleInProcess) {
            if (count != 2) {
                toReturn += integer.getValue();
            }
            count++;
        }
        return toReturn;
    }

    private int getProcessIndexForAbbrevation(String process) {
        for (int i = 0; i < Processes.getInstance().getProcessAbbrevations().length; i++) {
            if (Processes.getInstance().getProcessAbbrevations()[i].equals(process)) {
                return i;
            }
        }
        return -1;
    }

    private void checkProcessInPalletsOfArticleInProcess(String process, int initialValue) {
        int processIdx = this.getProcessIndexForAbbrevation(process);
        while (palletsOfArticleInProcess.size() <= processIdx && processIdx != -1) {
            SerializableInteger si = new SerializableInteger(initialValue);
            si.setArticleDynamics(this);
            palletsOfArticleInProcess.add(si);
        }
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public int getLateDeliveredPallets() {
        return lateDeliveredPallets;
    }

    public void setLateDeliveredPallets(int lateDeliveredPallets) {
        this.lateDeliveredPallets = lateDeliveredPallets;
    }

    public List<SerializableInteger> getPalletsOfArticleInProcess() {
        return palletsOfArticleInProcess;
    }

    public void setPalletsOfArticleInProcess(List<SerializableInteger> palletsOfArticleInProcess) {
        this.palletsOfArticleInProcess = palletsOfArticleInProcess;
    }

    public double getSalesIncome() {
        return salesIncome;
    }

    public void setSalesIncome(double salesIncome) {
        this.salesIncome = salesIncome;
    }

    public String getAbcClassification() {
        return this.article.getAbcClassification();
    }

    public int getArticleNumber() {
        return this.article.getArticleNumber();
    }

    public String getName() {
        return this.article.getName();
    }

    public double getSalePrice() {
        return this.article.getSalePrice();
    }

    public int getFixCosts() {
        return this.article.getFixCosts();
    }

    public int getRebateAmount1() {
        return this.article.getRebateAmount1();
    }

    public int getRebateAmount2() {
        return this.article.getRebateAmount2();
    }

    public int getRebateCost1() {
        return this.article.getRebateCost1();
    }

    public int getRebateCost2() {
        return this.article.getRebateCost2();
    }

    public int getMinOrder() {
        return this.article.getMinOrder();
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
        final ArticleDynamics other = (ArticleDynamics) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "flw.business.store.ArticleDynamics[ id=" + id + " ]";
    }
}
