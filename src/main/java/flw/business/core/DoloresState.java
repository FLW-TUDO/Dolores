/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.core;

import flw.business.store.ArticleDynamics;
import flw.business.store.ConveyorDynamics;
import flw.business.store.CustomerJobDynamics;
import flw.business.store.EmployeeDynamics;
import flw.business.store.OrderDynamics;
import flw.business.store.PalletsInProgress;
import flw.business.store.StockGround;
import flw.business.store.Storage;
import flw.business.util.Copier;
import flw.business.util.DoloresConst;
import flw.business.util.Processes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author tilu
 */
@Entity
/*@NamedQuery(
 name="findCurrentStateByGameInfo", 
 query="SELECT c FROM DoloresState c WHERE c.roundNumber LIKE :rNumber" 
 )*/
public class DoloresState implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private final long pointInTime;
    private final int environmentVersion;
    private final Properties propVariables;
    private int roundNumber = 0;
    private boolean loadable = false;
    private transient boolean areDynamicsInitialized = false;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Storage storage;
    
    //@OneToOne
    //private StorageTest test;
    
    @OneToOne
    private DoloresGameInfo gameInfo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state", cascade = CascadeType.ALL)
    private List<EmployeeDynamics> employeeDynamics;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state", cascade = CascadeType.ALL)
    private List<ConveyorDynamics> conveyorDynamics;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state", cascade = CascadeType.ALL)
    private List<ArticleDynamics> articleDynamics;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state", cascade = CascadeType.ALL)
    private List<CustomerJobDynamics> jobDynamics;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state", cascade = CascadeType.ALL)
    private List<OrderDynamics> orderDynamics;

    
     
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "state", cascade = CascadeType.ALL)
    private List<PalletsInProgress> pallets;
     
    
    public DoloresState() {

        //id = UUID.randomUUID().getMostSignificantBits();
        this.areDynamicsInitialized = true;
        pointInTime = System.currentTimeMillis();
        environmentVersion = 1;
        roundNumber = 1;

        propVariables = new Properties();

        propVariables.setProperty(DoloresConst.DOLORES_KEY_WORK_CLIMATE_INVEST, String.valueOf(DoloresConst.WORK_CLIMATE_INVEST[0]));
        propVariables.setProperty(DoloresConst.DOLORES_KEY_ORDER_COUNT, String.valueOf(0));
        propVariables.setProperty(DoloresConst.DOLORES_KEY_ORDER_AMOUNT, String.valueOf(0));
        propVariables.setProperty(DoloresConst.DOLORES_GAME_ROUND_NUMBER, String.valueOf(10));
        propVariables.setProperty(DoloresConst.DOLORES_GAME_ACCOUNT_BALANCE, String.valueOf(368943));
        propVariables.setProperty(DoloresConst.DOLORES_KEY_CUSTOMER_SATISFACTION, "0.99");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_COSTS_NEW_EMPLOYEES, "0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_IT_COSTS, "0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_LOADING_EQUIPMENT_COSTS, "0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_COSTS_UNIT_SECURITY_DEVICES, "0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_COSTS_QUALIFICATION_MEASURE, "0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_INCOME_CONVEYOR_SALE, "0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_PMAX, "0");
        propVariables.setProperty(DoloresConst.DOLORES_GAME_LAST_PERIODE, "999999");
        propVariables.setProperty(DoloresConst.DOLORES_GAME_STATE, DoloresConst.GameState.GAME_STATE_OK);
        propVariables.setProperty(DoloresConst.DOLORES_KEY_STRATEGY_INCOMING_GOODS, "0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_STRATEGY_OUTGOING_GOODS, "3");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_STRATEGY_STORAGE, "0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WA, "1.0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WE, "1.0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_UNIT_SECURITY_DEVICES_USED, "True");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_BACKTO_BASIC, "0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_BACK_TO_IT1, "0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_BACK_TO_IT2, "0");
        propVariables.setProperty(DoloresConst.DOLORES_KEY_STORAGE_FACTOR, "0.5");

        for (String process : Processes.getInstance().getProcessAbbrevations()) {
            String key = new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append(process).toString();
            propVariables.setProperty(key, String.valueOf(DoloresConst.DOLORES_GAME_INITIAL_OVERTIME));
        }

        orderDynamics = new ArrayList<>();
        conveyorDynamics = new ArrayList<>();
        employeeDynamics = new ArrayList<>();
        articleDynamics = new ArrayList<>();
        jobDynamics = new ArrayList<>();
        
        
        storage = new Storage();
        storage.setState(this);
        this.setStorage(storage);
    }
    
    private Storage createNewStorage(DoloresState baseState){
        Storage s = new Storage();
        
        for(PalletsInProgress pallet: baseState.getPalletsFromStorage()){
            PalletsInProgress palletsInProgress = new PalletsInProgress(baseState.roundNumber, pallet.getCurrentProcess(), pallet.getOrderno());
            palletsInProgress.setDemandRound(pallet.getDemandRound());
            palletsInProgress.setError(pallet.getError());
            palletsInProgress.setState(baseState);
            
            for (OrderDynamics oD : baseState.getOpenOrderDynamics()) {
                if (palletsInProgress.getOrderno() == oD.getOrderno()) {
                    palletsInProgress.setOrderDynamics(oD);
                }
            }
            
            int strategyStorage = Integer.valueOf(baseState.getValue(DoloresConst.DOLORES_KEY_STRATEGY_STORAGE));
            int strategyIn = Integer.valueOf(baseState.getValue(DoloresConst.DOLORES_KEY_STRATEGY_INCOMING_GOODS));
            
            if (palletsInProgress.getCurrentProcess() != 2) {
                s.getPalletsNotInStorage().add(palletsInProgress);
            } else{
                StockGround freeStockGround = s.getFreeStockGround(palletsInProgress.getOrderDynamics().getArticleDynamics(), strategyIn, strategyStorage);
                s.stockPallet(palletsInProgress.getOrderDynamics().getArticleDynamics(), freeStockGround.getStockGroundId(), palletsInProgress);
            }
            
        }      
        return s;
    }

    public DoloresState(DoloresState baseState) {
        
        //id = UUID.randomUUID().getMostSignificantBits();

        baseState.setPallets(baseState.getPalletsFromStorage());
        
        List<PalletsInProgress> tempPallets = new ArrayList<>();
        
        for(PalletsInProgress pallet: baseState.getPalletsFromStorage()){
            PalletsInProgress palletsInProgress = new PalletsInProgress(baseState.roundNumber, pallet.getCurrentProcess(), pallet.getOrderno());
            palletsInProgress.setDemandRound(pallet.getDemandRound());
            palletsInProgress.setError(pallet.getError());
            palletsInProgress.setStockgroundId(pallet.getStockgroundId());
            palletsInProgress.setState(baseState);
            tempPallets.add(palletsInProgress);
        }
        
        baseState.setPallets(tempPallets);
        
        this.areDynamicsInitialized = true;
        pointInTime = System.currentTimeMillis();

        propVariables = new Properties();

        copyProperties(baseState.propVariables, propVariables);

        environmentVersion = baseState.environmentVersion;

        this.conveyorDynamics = new Copier<>().copyToLinkedListCD(baseState.getConveyorDynamics());
        this.employeeDynamics = new Copier<>().copyToLinkedListED(baseState.getEmployeeDynamics());
        this.articleDynamics = new Copier<>().copyToLinkedListAD(baseState.getArticleDynamics());
        this.jobDynamics = new Copier<>().copyToLinkedListCJD(baseState.getJobDynamics());
        this.orderDynamics = new Copier<>().copyToLinkedListOD(baseState.getOpenOrderDynamics());
       
        this.storage = createNewStorage(baseState);
        
        this.storage.setState(this);
        
        if (Integer.valueOf(propVariables.getProperty(DoloresConst.DOLORES_KEY_BACKTO_BASIC)) > 0) {
            Integer valueOf = Integer.valueOf(propVariables.getProperty(DoloresConst.DOLORES_KEY_BACKTO_BASIC));
            propVariables.setProperty(DoloresConst.DOLORES_KEY_BACKTO_BASIC, Integer.toString(valueOf - 1));
        }

        if (Integer.valueOf(propVariables.getProperty(DoloresConst.DOLORES_KEY_BACK_TO_IT1)) > 0) {
            Integer valueOf = Integer.valueOf(propVariables.getProperty(DoloresConst.DOLORES_KEY_BACK_TO_IT1));
            propVariables.setProperty(DoloresConst.DOLORES_KEY_BACK_TO_IT1, Integer.toString(valueOf - 1));
        }
        if (Integer.valueOf(propVariables.getProperty(DoloresConst.DOLORES_KEY_BACK_TO_IT2)) > 0) {
            Integer valueOf = Integer.valueOf(propVariables.getProperty(DoloresConst.DOLORES_KEY_BACK_TO_IT2));
            propVariables.setProperty(DoloresConst.DOLORES_KEY_BACK_TO_IT2, Integer.toString(valueOf - 1));
        }
        this.propVariables.put(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append("en").toString(), "0");
        this.propVariables.put(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append("wv").toString(), "0");
        this.propVariables.put(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append("la").toString(), "0");
        this.propVariables.put(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append("wk").toString(), "0");
        this.propVariables.put(new StringBuilder(DoloresConst.DOLORES_KEY_OVERTIME).append("ve").toString(), "0");
        this.propVariables.put(DoloresConst.DOLORES_KEY_COSTS_NEW_EMPLOYEES, "0");
        
        //int freeStockGroundCount = storage.getFreeStockGroundCount();
    }
    
    public DoloresState (int environmentVersion, int pointInTime, Properties prop){
        this.environmentVersion = environmentVersion;
        this.pointInTime = pointInTime;
        this.propVariables = prop;
        orderDynamics = new ArrayList<>();
        conveyorDynamics = new ArrayList<>();
        employeeDynamics = new ArrayList<>();
        articleDynamics = new ArrayList<>();
        jobDynamics = new ArrayList<>();
        this.setStorage(new Storage());
    }

    
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWorkClimateInvest() {
        return Integer.parseInt(this.propVariables.get(DoloresConst.DOLORES_KEY_WORK_CLIMATE_INVEST).toString());
    }

    public List<ArticleDynamics> getArticleDynamics() {
        return articleDynamics;
    }
    
    public ArticleDynamics getArticleDynamics(int artNr) {
        for(ArticleDynamics artDyn: this.articleDynamics){
            if(artDyn.getArticleNumber()==artNr){
                return artDyn;
            }
        }
        return null;
    }
    
    public int getEnvironmentVersion(){
        return this.environmentVersion;
        
    }
    
    public boolean getLoadable(){
        return this.loadable;
    }
    
    public void setArticleDynamics(List<ArticleDynamics> articleDynamics) {
        this.articleDynamics = articleDynamics;
    }

    public List<CustomerJobDynamics> getJobDynamics() {
        return jobDynamics;
    }

    public void setJobDynamics(List<CustomerJobDynamics> jobDynamics) {
        this.jobDynamics = jobDynamics;
    }

    public List<EmployeeDynamics> getEmployeeDynamics() {
        return employeeDynamics;
    }

    public void setEmployeeDynamics(List<EmployeeDynamics> employeeDynamics) {
        this.employeeDynamics = employeeDynamics;
    }

    public List<ConveyorDynamics> getConveyorDynamics() {
        return conveyorDynamics;
    }

    public void setConveyorDynamics(List<ConveyorDynamics> conveyorDynamics) {
        this.conveyorDynamics = conveyorDynamics;
    }

    public DoloresGameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(DoloresGameInfo doloGameInfo) {
        this.gameInfo = doloGameInfo;
        //doloGameInfo.setCurrentState(this);
//        if (doloGameInfo != null && !doloGameInfo.getlDoloresStates().contains(this)) {
//            doloGameInfo.getlDoloresStates().add(this);
//        }
    }

    public void addEmployeeDynamics(EmployeeDynamics eD) {
        this.employeeDynamics.add(eD);
        this.gameInfo.addEmployee(eD.getEmployee());
        if (eD.getState() != this) {
            eD.setState(this);
        }
    }

    public void addConveyorDynamics(ConveyorDynamics cD) {
        this.conveyorDynamics.add(cD);
        this.gameInfo.addConveyor(cD.getConveyor());
        if (cD.getState() != this) {
            cD.setState(this);
        }
    }

    public void addArticleDynamics(ArticleDynamics ad) {
        this.articleDynamics.add(ad);
        this.gameInfo.addArticle(ad.getArticle());
        if (ad.getState() != this) {
            ad.setState(this);
        }
    }

    public void addCustomerJobDynamics(CustomerJobDynamics cJD) {
        this.jobDynamics.add(cJD);
        if (cJD.getState() != this) {
            cJD.setState(this);
        }
    }

    public String getValue(String varName) {
        return propVariables.getProperty(varName);

    }

    public void setValue(String varName, String value) {

        if (varName.equals("accountbalance")) {
            int breakpoint = 0;
        }

        propVariables.put(varName, value);
    }

    public double getAccountBalanceAsDouble() {
        return Double.valueOf(propVariables.getProperty("accountbalance"));
    }
    
    public String getAccountBalanceAsStr() {
        String str = propVariables.getProperty("accountbalance"); 
        return "" + (int) Double.parseDouble(str);
    }

    public double getSatisfactionAsDouble() {
        return 100 * Double.valueOf(propVariables.getProperty("satisfaction"));
    }
    
    public String getSatisfactionAsStr() {
        return (int)(100 * Double.valueOf(propVariables.getProperty("satisfaction"))) + "%";
    }

    public boolean containsValue(String varName) {
        return propVariables.containsKey(varName);
    }

    public void removeCustomerJob(CustomerJobDynamics job) {
        this.jobDynamics.remove(job);

    }

    public long getPointInTime() {
        return pointInTime;
    }

    public Storage getStorage() {
        return this.storage;
    }
    
    public void setStorage(Storage storage){
        this.storage = storage;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public boolean isLoadable() {
        return loadable;
    }

    public void setLoadable(boolean loadable) {
        this.loadable = loadable;
    }

    public List<PalletsInProgress> getPalletsFromStorage() {
        return getStorage().getPallets();  
    }

    public List<PalletsInProgress> getPallets() {
        return pallets;
    }

    public void setPallets(List<PalletsInProgress> pallets) {
        this.pallets = pallets;
    }

    public List<OrderDynamics> getOpenOrderDynamics() {
        return orderDynamics;
    }
    
    public void addOrderDynamcis(OrderDynamics oD){
        this.orderDynamics.add(oD);
        this.gameInfo.addOrder(oD.getOrder());
        if (oD.getState() != this) {
            oD.setState(this);
        }
    }

    public void removeOrderDynamics(OrderDynamics orderD) {

        this.orderDynamics.remove(orderD);
    }

    public static Properties copyProperties(Properties src_prop, Properties dest_prop) {
        for (Enumeration propertyNames = src_prop.propertyNames();
                propertyNames.hasMoreElements();) {
            Object key = propertyNames.nextElement();
            dest_prop.put(key, src_prop.get(key));
        }
        return dest_prop;
    }

    public Properties getPropVariables() {
        return propVariables;
    }

    public String getBalance() {
        return getValue("ACCOUNTBALANCE");
    }

    public String getDateAsString() {
        Date date = new Date(pointInTime);
        return date.toString();
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
        if (!(object instanceof DoloresState)) {
            return false;
        }
        DoloresState other = (DoloresState) object;
        return this.pointInTime == other.pointInTime;

    }

    @Override
    public String toString() {
        return "flw.business.core.DoloresState[ id=" + id + " ]";
    }
    
    
    
}
