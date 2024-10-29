/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.core;

import flw.business.statistics.ArticleAndOrderStatistics;
import flw.business.statistics.ConveyorStatistics;
import flw.business.statistics.EmployeeStatistics;
import flw.business.statistics.PalletStatistics;
import flw.business.statistics.StateStatistics;
import flw.business.store.ArticleDynamics;
import flw.business.store.ConveyorDynamics;
import flw.business.store.CustomerJobDynamics;
import flw.business.store.Employee;
import flw.business.store.Article;
import flw.business.store.Conveyor;
import flw.business.store.EmployeeDynamics;
import flw.business.store.Message;
import flw.business.store.Order;
import flw.business.store.OrderDynamics;
import flw.business.store.PalletsInProgress;
import flw.business.store.StockGround;
import flw.business.store.Storage;
import flw.business.util.DoloresConst;
import flw.presentation.GameInfoPM;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author tilu
 */
@Entity
public class DoloresGameInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "GAMEINFO_ID")
    private Long id;
    private long lastAction = System.currentTimeMillis();
    private String corpName;
    private String playerName;
    private String street;
    private String location;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameInfo", fetch = FetchType.LAZY)
    private List<Message> messages;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "gameInfo", fetch = FetchType.LAZY)
    private List<EmployeeStatistics> employeeStatisticses;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "gameInfo", fetch = FetchType.LAZY)
    private List<ConveyorStatistics> conveyorStatisticses;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "gameInfo", fetch = FetchType.LAZY)
    private List<PalletStatistics> palletStatisticses;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "gameInfo", fetch = FetchType.LAZY)
    private List<StateStatistics> stateStatisticses;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "gameInfo", fetch = FetchType.LAZY)
    private List<ArticleAndOrderStatistics> articleAndOrderStatisticses;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "gameInfo", fetch = FetchType.EAGER)
    private DoloresState currentState;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "gameInfo", fetch = FetchType.LAZY)
    private List<Employee> employees;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "gameInfo", fetch = FetchType.LAZY)
    private List<Article> articles;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "gameInfo", fetch = FetchType.LAZY)
    private List<Conveyor> conveyors;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "gameInfo", fetch = FetchType.LAZY)
    private List<Order> orders;    

    
    @OneToOne
    private DoloresPlayer doloresPlayer;
    
    private boolean rollback = false;
    
    @Transient
    private boolean isUpdated = false;
    
    
    @Transient
    private GameInfoPM pm;
    
    public String setDate(){
        Date temp = new Date(lastAction);
        return temp.getHours() + ":" + temp.getMinutes() + ":" + temp.getSeconds();
    }
    
    public void addOrder(Order order){
        this.orders.add(order);
        order.setGameInfo(this);
    }
    
    public List<Order> getOrder(){
        return this.orders;
    }
    
    public void addEmployee(Employee employee){
        this.employees.add(employee);
        employee.setGameInfo(this);
    }
    
    public List<Employee> getEmployee(){
        return this.employees;
    }
    
    public void addConveyor(Conveyor conveyor){
        this.conveyors.add(conveyor);
        conveyor.setGameInfo(this);
    }
    
    public List<Conveyor> getConveyor(){
        return this.conveyors;
    }
    
    public void addArticle(Article article){
        this.articles.add(article);
        article.setGameInfo(this);
    }
    
    public List<Article> getArticle(){
        return this.articles;
    }
    
    public List<EmployeeStatistics> getEmployeeStatisticses() {
        return employeeStatisticses;
    }

    public List<ArticleAndOrderStatistics> getArticleAndOrderStatisticses() {
        return articleAndOrderStatisticses;
    }

    public void setArticleAndOrderStatisticses(List<ArticleAndOrderStatistics> articleAndOrderStatisticses) {
        this.articleAndOrderStatisticses = articleAndOrderStatisticses;
    }

    public void setEmployeeStatisticses(List<EmployeeStatistics> employeeStatisticses) {
        this.employeeStatisticses = employeeStatisticses;
    }

    public List<ConveyorStatistics> getConveyorStatisticses() {
        return conveyorStatisticses;
    }

    public void setConveyorStatisticses(List<ConveyorStatistics> conveyorStatisticses) {
        this.conveyorStatisticses = conveyorStatisticses;
    }

    public List<PalletStatistics> getPalletStatisticses() {



        return palletStatisticses;
    }

    public void setPalletStatisticses(List<PalletStatistics> palletStatisticses) {
        this.palletStatisticses = palletStatisticses;
    }

    public List<StateStatistics> getStateStatisticses() {
        return stateStatisticses;
    }

    public void setStateStatisticses(List<StateStatistics> stateStatisticses) {
        this.stateStatisticses = stateStatisticses;
    }
    
    public DoloresGameInfo(Long id, String corpName, long lastAction, String location, String playerName, boolean rollback, String street, DoloresPlayer doloresPlayer){
        this();
        
        this.id = id;
        this.corpName = corpName;
        this.lastAction = lastAction;
        this.location = location;
        this.playerName = playerName;
        this.rollback = rollback;
        this.street = street;
        this.doloresPlayer = doloresPlayer;
        
        /*
        this.lDoloresStates = new LinkedList<DoloresState>();
        this.messages = new LinkedList<Message>();
        this.employeeStatisticses = new LinkedList<EmployeeStatistics>();
        this.conveyorStatisticses = new LinkedList<ConveyorStatistics>();
        this.palletStatisticses = new LinkedList<PalletStatistics>();
        this.stateStatisticses = new LinkedList<StateStatistics>();
        this.articleAndOrderStatisticses = new LinkedList<ArticleAndOrderStatistics>();
        */
    }
    
    public DoloresGameInfo() {
//        this.lDoloresStates = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.employeeStatisticses = new ArrayList<>();
        this.conveyorStatisticses = new ArrayList<>();
        this.palletStatisticses = new ArrayList<>();
        this.stateStatisticses = new ArrayList<>();
        this.articleAndOrderStatisticses = new ArrayList<>();
        isUpdated = false;
    }

    /**
     * Initializes a new game info object. Used to create a new game.
     *
     * @param corpName
     * @param playerName
     * @param street
     * @param location
     */
    public DoloresGameInfo(String corpName, String playerName, String street, String location) {

        this();

        this.corpName = corpName;
        this.playerName = playerName;
        this.street = street;
        this.location = location;
    }
    
    /**
     * renews the lastaction time
     *
     * @return the current State
     */
    public DoloresState getCurrentState() {
        lastAction = System.currentTimeMillis();
        return currentState;
    }
    
    public void setCurrentState(DoloresState currentState){
        this.currentState = currentState;
        currentState.setGameInfo(this);
    }
    
    public String getAverageSatisfaction(){
        double satisfaction = 0;
        for(StateStatistics stst : stateStatisticses){
            if(stst.getRoundNumber() > 9)satisfaction += stst.getSatisfaction();
        }
        return (int)(satisfaction / (stateStatisticses.size() - 9)) + " %";
    }


    /**
     *
     * @return the Messages sorted by actuality
     */
    public List<Message> getMessages() {
        //Collections.sort(messages, new MessageComparatorByRoundNumber());
        return messages;
    }
    
    public void addMessage(Message message){
        this.messages.add(0, message);
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

//    public void setlDoloresStates(List<DoloresState> lDoloresStates) {
//        this.lDoloresStates = lDoloresStates;
//    }
    
    private void deleteMessages(){
        int indexToRemove = articleAndOrderStatisticses.size() - 1;
        articleAndOrderStatisticses.remove(indexToRemove);
        palletStatisticses.remove(indexToRemove);
        conveyorStatisticses.remove(indexToRemove);
        employeeStatisticses.remove(indexToRemove);
        stateStatisticses.remove(indexToRemove);
        
        List<Message> toRemove = new ArrayList<>();
        for (Message m : messages) {
            if (m.getRoundNumber() == currentState.getRoundNumber()) {
                toRemove.add(m);
            }
        }
        messages.removeAll(toRemove);
    }
    
    private void revertStorage(DoloresState prevState){
        Storage s = new Storage();
        
        for (PalletsInProgress pd : prevState.getPallets()) {
            if(pd.getStockGround() != null){
                System.out.println("Stockgrind sollte eigtl null sein");
            }

            for (OrderDynamics oD : prevState.getOpenOrderDynamics()) {
                if (pd.getOrderno() == oD.getOrderno()) {
                    pd.setOrderDynamics(oD);
                }
            }
            int strategyStorage = Integer.valueOf(prevState.getValue(DoloresConst.DOLORES_KEY_STRATEGY_STORAGE));
            int strategyIn = Integer.valueOf(prevState.getValue(DoloresConst.DOLORES_KEY_STRATEGY_INCOMING_GOODS));
            if (pd.getCurrentProcess() != 2) {
                s.getPalletsNotInStorage().add(pd);
            } else{
                StockGround freeStockGround = s.getFreeStockGround(pd.getOrderDynamics().getArticleDynamics(), strategyIn, strategyStorage);
                s.stockPallet(pd.getOrderDynamics().getArticleDynamics(), freeStockGround.getStockGroundId(), pd);
            }
        }
        
        prevState.setStorage(s);
    }
    
    public void previousRound(DoloresState prevState){
        this.deleteMessages();
        
        this.revertStorage(prevState);
        
        //Revert Dynamcis for each Article
        for(ArticleDynamics aD : currentState.getArticleDynamics()){
            for(ArticleDynamics aD_old : prevState.getArticleDynamics()){
                if(aD.getArticle().getId() == aD_old.getArticle().getId()){
                    aD.getArticle().setArticleDynamics(aD_old);
                }
            }
        }
        
        //Revert Dynamcis for each Employee
        for(EmployeeDynamics eD : currentState.getEmployeeDynamics()){
            for(EmployeeDynamics eD_old : prevState.getEmployeeDynamics()){
                if(eD.getEmployee().getId() == eD_old.getEmployee().getId()){
                    eD.getEmployee().setEmployeeDynamics(eD_old);
                }
            }
        }
        
        //Revert Dynamcis for each Conveyor
        for(ConveyorDynamics cD : currentState.getConveyorDynamics()){
            for(ConveyorDynamics cD_old : prevState.getConveyorDynamics()){
                if(cD.getConveyor().getId() == cD_old.getConveyor().getId()){
                    cD.getConveyor().setConveyorDynamcis(cD_old);
                }
            }
        }
    }

    /**
     *
     *
     *
     * @param lastState
     * @param save should not be used -> always false
     * @return the new State
     */
    public DoloresState nextRound(DoloresState lastState, boolean save) {
        lastAction = System.currentTimeMillis();


        DoloresState newState = new DoloresState(lastState);
        //int roundNumber = Integer.parseInt(lastState.getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER));
        int roundNumber = lastState.getRoundNumber();



        if (save) {
            newState.setLoadable(true);
            newState.setValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER, String.valueOf(roundNumber));
            newState.setRoundNumber(roundNumber);
        } else {
            newState.setLoadable(false);
            newState.setValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER, String.valueOf(roundNumber + 1));
            newState.setRoundNumber(roundNumber + 1);
        }



        //this.addState(newState);
        List<EmployeeDynamics> employeeDynamics = newState.getEmployeeDynamics();
        List<ConveyorDynamics> conveyorDynamics = newState.getConveyorDynamics();
        List<ArticleDynamics> articleDynamics = newState.getArticleDynamics();
        List<CustomerJobDynamics> jobDynamics = newState.getJobDynamics();
        List<OrderDynamics> orderDynamics = newState.getOpenOrderDynamics();
        //LinkedList<PalletsInProgress> pallets = newState.getPallets();



        for (CustomerJobDynamics cJD : jobDynamics) {
            cJD.setState(newState);

            for (ArticleDynamics aD : articleDynamics) {
                if (cJD.getArticleNumber() == aD.getArticleNumber()) {
                    cJD.setArticleDynamics(aD);
                }
            }
        }


        for (EmployeeDynamics eD : employeeDynamics) {
            eD.setState(newState);
            eD.setRoundnumber(roundNumber + 1);
        }

        for (ConveyorDynamics cD : conveyorDynamics) {
            cD.setState(newState);
        }



        for (ArticleDynamics aD : articleDynamics) {
            aD.setState(newState);
        }


        for (CustomerJobDynamics cJD : jobDynamics) {
            cJD.setState(newState);

            for (ArticleDynamics aD : articleDynamics) {
                if (cJD.getArticleNumber() == aD.getArticleNumber()) {
                    cJD.setArticleDynamics(aD);
                }
            }
        }


        for (OrderDynamics oD : orderDynamics) {
            oD.setState(newState);

            for (ArticleDynamics aD : articleDynamics) {
                if (oD.getArticleNumber() == aD.getArticleNumber()) {
                    //oD.setArticleDynamics(aD.getArticleNumber());
                    oD.setArticleNumber(aD.getArticleNumber());
                }
            }
        }

        //System.out.println("Round changed successfully!");
        currentState = newState;
        return newState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public void setPlayer(DoloresPlayer player)
    {
        this.doloresPlayer = player;
    }
    
    public DoloresPlayer getDoloresPlayer()
    {
        return this.doloresPlayer;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "flw.business.core.DoloresGameInfo[ id=" + id + " ]";
    }

//    public List<DoloresState> getlDoloresStates() {
//
//
//        return lDoloresStates;
//    }

    /*
     * 
     * 
     *  -> NO OCCURENCES!!!!
     * 
     * 
     * only use if autosave ( 2 Sates for 1 Round)
     * 
     */
//    public List<DoloresState> getLastNStatesAutoSave(int count, boolean includeCurrentRound) {
//        List<DoloresState> toReturn = new ArrayList<>();
//        int index = lDoloresStates.size();
//
//        if (index / 2 < count) {
//            count = index / 2;
//        }
//
//        index--;
//
//        if (includeCurrentRound) {
//            for (int i = 0; i < count; i++) {
//                toReturn.add(lDoloresStates.get(index));
//                index = index - 2;
//            }
//        } else {
//            index = index - 2;
//            for (int i = 0; i < count; i++) {
//                toReturn.add(lDoloresStates.get(index));
//                index = index - 2;
//            }
//        }
//
//        return toReturn;
//    }

    public long getLastAction() {
        return lastAction;
    }
    
    public String getDate() {
        Date date = new Date();
        date.setTime(lastAction);
        String str = date.toString();
        str = str.replace("CET " + (1900 + date.getYear()), "");
        return str;
    }

    public void setLastAction(long lastAction) {
        this.lastAction = lastAction;
    }

    public boolean isRollback() {
        return rollback;
    }

    public void setRollback(boolean rollback) {
        this.rollback = rollback;
    }
    
    

    /**
     *
     * @param count the number of how many last states
     * @param includeCurrentRound
     * @return The Last "count" states
     */
//    public List<DoloresState> getLastNStatesManualSave(int count, boolean includeCurrentRound) {
//        List<DoloresState> toReturn = new ArrayList<>();
//        int index = lDoloresStates.size();
//
//        if (index < count) {
//            count = index;
//        }
//
//        index--;
//
//        if (includeCurrentRound) {
//            for (int i = 0; i < count; i++) {
//                toReturn.add(lDoloresStates.get(index));
//                index = index - 1;
//            }
//        } else {
//            index = index - 1;
//            for (int i = 0; (i < count && i > -1); i++) {
//
//                if (index > -1) {
//
//                    toReturn.add(lDoloresStates.get(index));
//                    index = index - 1;
//                }
//            }
//        }
//
//        return toReturn;
//    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DoloresGameInfo other = (DoloresGameInfo) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.playerName == null) ? (other.playerName != null) : !this.playerName.equals(other.playerName)) {
            return false;
        }
        return true;
    }
}
