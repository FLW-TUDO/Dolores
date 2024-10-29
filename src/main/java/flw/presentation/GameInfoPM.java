/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
import flw.business.calcs.AbstractCalculator;
import flw.business.calcs.CapacityCalculator;
import flw.business.calcs.ConveyorCalculator;
import flw.business.calcs.CostsIncomeCalculator;
import flw.business.calcs.EmployeeCalculator;
import flw.business.calcs.JobCalculator;
import flw.business.calcs.PalletThroughputCalc;
import flw.business.calcs.PostPalletThroughputCalculator;
import flw.business.calcs.SaveRequiredDataCalculator;
import flw.business.calcs.SaveStatisticsCalculator;
import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresPlayer;
import flw.business.core.DoloresState;
import flw.business.statistics.ArticleAndOrderStatistics;
import flw.business.statistics.ConveyorStatistics;
import flw.business.statistics.EmployeeStatistics;
import flw.business.statistics.PalletStatistics;
import flw.business.statistics.StateStatistics;
import flw.business.store.ArticleDynamics;
import flw.business.store.ConveyorDynamics;
import flw.business.store.EmployeeDynamics;
import flw.business.store.Message;
import flw.business.store.OrderDynamics;
import flw.business.store.PalletsInProgress;
import flw.business.store.StockGround;
import flw.business.store.Storage;
import flw.business.util.DoloresConst;
import flw.business.util.Processes;
import flw.business.util.ScenarioFactory;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author tilu
 */
@ManagedBean(name = "gameInfoPM")
@SessionScoped
public class GameInfoPM implements Serializable {

    private int gameIdLoad;
    private String[] employeeCount;
    private String[] palletOverviewTransported;
    private String[] palletOverviewNotTransported;
    private double[] employeeWorkload;
    private DoloresGameInfo info;
    private String corpName;
    private String playerName;
    private String street;
    private String location;
    private List<AbstractCalculator> lCalculators;
    private int gameId;
    private DoloresState currentState;
    private boolean fromLoading = false;
    private List<Message> messages;
    private boolean gameStateOk;
    private DoloresPlayer player;
    @EJB
    private Service service;

    /**
     * Creates a new instance of GameInfoPM
     */
    public GameInfoPM() {
    }

    public void init() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        DoloresPlayer loadPlayer = service.loadPlayer(principal.getName());
        this.player = loadPlayer;

    }

    /**
     * Creates a new scenario for the logged in player In order to do this
     * variables get initialized
     *
     * @return String to the main site
     *
     */
    public String newScenario() {
        //if(info != null)System.out.println(info.toString());
        this.init();

        info = new DoloresGameInfo();
        info.setCorpName(corpName);
        info.setLocation(location);
        info.setPlayerName(playerName);
        info.setStreet(street);
        
        info.setPlayer(player);

        info = service.update(info);

        player = service.mergePlayer(player);
        try {
            saveHistory();
        } catch (IOException ex) {
            Logger.getLogger(GameInfoPM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DoloresState ds = new DoloresState();
        
        info.setCurrentState(ds);
        
        ds.setGameInfo(info);

        ds.setValue(DoloresConst.DOLORES_KEY_PMAX, "255");

        ds = loadJsons(ds);
        
        palletOverviewNotTransported = new String[7];
        palletOverviewTransported = new String[7];
        palletOverviewNotTransported[0] = "0";
        palletOverviewNotTransported[1] = "0";
        palletOverviewNotTransported[5] = "0";
        palletOverviewNotTransported[6] = "0";
        palletOverviewNotTransported[3] = "0";
        palletOverviewNotTransported[4] = "0";

        palletOverviewTransported[0] = "140";
        palletOverviewTransported[1] = "140";
        palletOverviewTransported[5] = "140";
        palletOverviewTransported[6] = "255";
        palletOverviewTransported[3] = "255";
        palletOverviewTransported[4] = "255";
        
        info = service.update(info);
        this.currentState = ds;
        gameId = (int) (long) info.getId();
        return this.nextRound();
    }
    
    public String revertRound(){
        currentState = info.getCurrentState();
        DoloresState prevState = service.getLastState(info);
        
        //info.setRollback(false);
        
        info.previousRound(prevState);
        
        int a100101 = 0;
        int a100102 = 0;
        int a100103 = 0;
        int a100104 = 0;
        
        for(StockGround stock: prevState.getStorage().getOccStocks()){
            if(stock.getStoredArticle().getArticleNumber() == 100101) a100101++;
            if(stock.getStoredArticle().getArticleNumber() == 100102) a100102++;
            if(stock.getStoredArticle().getArticleNumber() == 100103) a100103++;
            if(stock.getStoredArticle().getArticleNumber() == 100104) a100104++;
        }
        
        prevState.getStorage().getStoredAmount().get(1).setValue(a100101);
        prevState.getStorage().getStoredAmount().get(2).setValue(a100102);
        prevState.getStorage().getStoredAmount().get(3).setValue(a100103);
        prevState.getStorage().getStoredAmount().get(4).setValue(a100104);
            
        service.deleteMessages((long) gameId, currentState.getRoundNumber());
        
        service.deleteStatistics((long) gameId, currentState.getRoundNumber() - 1);
        
        service.deleteLastState(info);
        
        service.updateSingleGame(info);
        
        this.currentState = info.getCurrentState();

        info = service.update(info);
        
        this.currentState = info.getCurrentState();
         
        return "main.xhtml?faces-redirect=true";
    }

    public String previousRound(){
        
        //info.setRollback(false);
        currentState = info.getCurrentState();
        DoloresState lastState = service.getLastState(info);

        int indexToRemove = info.getArticleAndOrderStatisticses().size() - 1;
        info.getArticleAndOrderStatisticses().remove(indexToRemove);
        info.getPalletStatisticses().remove(indexToRemove);
        info.getConveyorStatisticses().remove(indexToRemove);
        info.getEmployeeStatisticses().remove(indexToRemove);
        info.getStateStatisticses().remove(indexToRemove);

        // Hier vielleicht die Nachrichten nicht löschen
        List<Message> toRemove = new ArrayList<>();
        for (Message m : info.getMessages()) {
            if (m.getRoundNumber() == currentState.getRoundNumber() - 1) {
                toRemove.add(m);
            }
        }
        info.getMessages().removeAll(toRemove);

        service.deleteStatistics((long) gameId, currentState.getRoundNumber() - 1);
        service.deleteMessages((long) gameId, currentState.getRoundNumber());

        //lastState.setStorage(new Storage());

        for (PalletsInProgress pd : lastState.getPallets()) {
            if(pd.getStockGround() != null){
                System.out.println("Stockgrind sollte eigtl null sein");
            }

            for (OrderDynamics oD : lastState.getOpenOrderDynamics()) {
                if (pd.getOrderno() == oD.getOrderno()) {
                    pd.setOrderDynamics(oD);
                }
            }
            int strategyStorage = Integer.valueOf(lastState.getValue(DoloresConst.DOLORES_KEY_STRATEGY_STORAGE));
            int strategyIn = Integer.valueOf(lastState.getValue(DoloresConst.DOLORES_KEY_STRATEGY_INCOMING_GOODS));
            //PalletsInProgress pd = (PalletsInProgress) xstream.fromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/jsonsLoad/pallets/PalletsInProgress" + i + ".json"));
            Storage s = lastState.getStorage();
            if (pd.getCurrentProcess() != 2) {
                s.getPalletsNotInStorage().add(pd);
            } else{
                StockGround freeStockGround = s.getFreeStockGround(pd.getOrderDynamics().getArticleDynamics(), strategyIn, strategyStorage);
                lastState.getStorage().stockPallet(pd.getOrderDynamics().getArticleDynamics(), freeStockGround.getStockGroundId(), pd);
            }
        }

        currentState = lastState;
        
        info.setCurrentState(currentState);

        info = service.update(info);

        currentState = info.nextRound(info.getCurrentState(), false);

        info.setCurrentState(currentState);
        this.info = service.update(info);
        this.currentState = info.getCurrentState();

        return "main.xhtml?faces-redirect=true";
    }
    
    
    

    /**
     * Method gets called if users presses "next round" button
     *
     * The method makes all the calculation/preparation calls to the calculation
     * classes
     *
     * @return "main" as String to redirect to main page
     */
    public String nextRound() {
        fillCalculators();
        for (AbstractCalculator calc : lCalculators) {
            calc.calculate(info);
        }                             //info.getCurrentState()
        
        currentState = info.nextRound(info.getCurrentState(), false);
        info.setCurrentState(currentState);
        
        //scripting for first period
        if (currentState.getRoundNumber() == 11) {
            currentState.setValue("workload_workers_en", "9.00");
            currentState.setValue("workload_workers_wv", "47.00");
            currentState.setValue("workload_workers_la", "32.00");
            currentState.setValue("workload_workers_wk", "58.00");
            currentState.setValue("workload_workers_ve", "21.00");
            currentState.setValue("workload_conveyors_en", "9.00");
            currentState.setValue("workload_conveyors_la", "22.00");
            currentState.setValue("workload_conveyors_ve", "21.00");
        }
        //System.out.println("1) info = service.update(info); Anfang");
        info = service.update(info);
        //System.out.println("1) info = service.update(info); Ende");
        
        for (AbstractCalculator calc : lCalculators) {
            calc.prepareNextRound(info);
        }     
        
        //System.out.println("2) info = service.update(info); startet");
        this.info = service.update(info);
        
        //System.out.println("2) info = service.update(info); beendet");     
        this.currentState = info.getCurrentState();
   
        return "main.xhtml?faces-redirect=true";
    }

    /**
     * Persists the generated Statistics generated by the Calculation classes
     */
    public void persistNewValuesToStatistics() {

        for (Message m : info.getMessages()) {
            if (m.getId() == null) {
                service.persistStuff(m);
            }
        }

        for (EmployeeStatistics es : info.getEmployeeStatisticses()) {
            if (es.getId() == null) {
                service.persistStuff(es);
            }
        }

        for (ConveyorStatistics cs : info.getConveyorStatisticses()) {
            if (cs.getId() == null) {
                service.persistStuff(cs);
            }
        }
        for (PalletStatistics ps : info.getPalletStatisticses()) {
            if (ps.getId() == null) {
                service.persistStuff(ps);
            }
        }

        for (StateStatistics ss : info.getStateStatisticses()) {
            if (ss.getId() == null) {
                service.persistStuff(ss);
            }
        }

        for (ArticleAndOrderStatistics aaos : info.getArticleAndOrderStatisticses()) {
            if (aaos.getId() == null) {
                service.persistStuff(aaos);
            }
        }
    }

    /**
     * Calculates the current number of employees in different processes
     */
    public void calculateEmployeeCount() {
        employeeCount = new String[5];
        int[] employeeCountInt = new int[5];
        currentState = info.getCurrentState();

        for (EmployeeDynamics ed : currentState.getEmployeeDynamics()) {
            if (ed.isReady(currentState.getRoundNumber())) {
                switch (ed.getProcess()) {
                    case 0: {
                        employeeCountInt[0]++;
                        break;
                    }
                    case 1: {
                        employeeCountInt[1]++;
                        break;
                    }
                    case 2: {
                        employeeCountInt[2]++;
                        break;
                    }
                    case 3: {
                        employeeCountInt[3]++;
                        break;
                    }
                    case 4: {
                        employeeCountInt[4]++;
                        break;
                    }

                }
            }
        }
        for (int i = 0; i < 5; i++) {
            employeeCount[i] = String.valueOf(employeeCountInt[i]);
        }
    }

    public double[] getEmployeeWorkload() {
        calculateEmployeeWorkload();
        return employeeWorkload;
    }

    public void setEmployeeWorkload(double[] employeeWorkload) {
        this.employeeWorkload = employeeWorkload;
    }

    /**
     * Calculates the employee workload using DB Queries
     */
    public void calculateEmployeeWorkload() {
        employeeWorkload = new double[5];
        //currentState = info.getCurrentState();
        double temp;
        int i = 0;
        for (String process : Processes.getInstance().getProcessAbbrevationsCapital()) {
            employeeWorkload[i] = 100 * Double.valueOf(getSingleString("WORKLOAD_WORKERS_" + process, currentState.getRoundNumber() - 1));
            i++;
        }
    }

    public String[] getPalletOverviewTransported() {
        if (currentState.getRoundNumber() == 11) {
            return palletOverviewTransported;
        }
        calculateTransportedPallets();
        return palletOverviewTransported;

    }

    public void setPalletOverviewTransported(String[] palletOverviewTransported) {
        this.palletOverviewTransported = palletOverviewTransported;
    }

    /**
     * Calculates the transported pallets from last round
     */
    public void calculateTransportedPallets() {

        palletOverviewTransported = new String[7];
        //currentState = info.getCurrentState();
        int i = 0;
        for (String process : Processes.getInstance().getProcessAbbrevations()) {
            palletOverviewTransported[i] = currentState.getValue(new StringBuilder("pallets_transported_").append(process).toString());
            i++;
        }
        palletOverviewTransported[5] = currentState.getValue(new StringBuilder("pallets_transported_la_in").toString());
        palletOverviewTransported[6] = currentState.getValue(new StringBuilder("pallets_transported_la_out").toString());
    }

    public String[] getPalletOverviewNotTransported() {
        if (currentState.getRoundNumber() == 11) {
            return palletOverviewNotTransported;
        }
        calculateNotTransportedPallets();
        return palletOverviewNotTransported;
    }

    public void setPalletOverviewNotTransported(String[] palletOverviewNotTransported) {
        this.palletOverviewNotTransported = palletOverviewNotTransported;
    }

    /**
     * Calculates the not transported pallets from last round
     */
    public void calculateNotTransportedPallets() {
        palletOverviewNotTransported = new String[7];
        //currentState = info.getCurrentState();
        int i = 0;
        for (String process : Processes.getInstance().getProcessAbbrevations()) {
            palletOverviewNotTransported[i] = (currentState.getValue(new StringBuilder("not_transported_pallets_").append(process).toString()));
            i++;
        }
        palletOverviewNotTransported[5] = (currentState.getValue(new StringBuilder("not_transported_pallets_la_in").toString()));
        palletOverviewNotTransported[6] = (currentState.getValue(new StringBuilder("not_transported_pallets_la_out").toString()));
    }

    /**
     * Initializes the calculator classes
     */
    private void fillCalculators() {
        lCalculators = new ArrayList<>();
        lCalculators.add(new EmployeeCalculator(lCalculators));
        lCalculators.add(new ConveyorCalculator(lCalculators));
        lCalculators.add(new CapacityCalculator(lCalculators));
        lCalculators.add(new PalletThroughputCalc(lCalculators, service));
        lCalculators.add(new PostPalletThroughputCalculator(lCalculators, service)); //done -> only size reference to jobDynamics!
        lCalculators.add(new SaveRequiredDataCalculator(lCalculators));
        lCalculators.add(new CostsIncomeCalculator(lCalculators));
        lCalculators.add(new JobCalculator(lCalculators)); //done -> should work
        lCalculators.add(new SaveStatisticsCalculator(lCalculators));
    }

    /**
     * loads the Game specified in @param gameInfo
     *
     * @param gameInfo
     * @param player
     * @return to the main page
     */
    public String loadGame(DoloresGameInfo gameInfo, DoloresPlayer player) {
        this.info = gameInfo;
        this.player = player;
        service.updateSingleGame(gameInfo);
        this.currentState = info.getCurrentState();
        info.setPlayer(player);
        
        int a100101 = 0;
        int a100102 = 0;
        int a100103 = 0;
        int a100104 = 0;
        
        for(StockGround stock: currentState.getStorage().getOccStocks()){
            if(stock.getStoredArticle().getArticleNumber() == 100101) a100101++;
            if(stock.getStoredArticle().getArticleNumber() == 100102) a100102++;
            if(stock.getStoredArticle().getArticleNumber() == 100103) a100103++;
            if(stock.getStoredArticle().getArticleNumber() == 100104) a100104++;
        }
        
        currentState.getStorage().getStoredAmount().get(1).setValue(a100101);
        currentState.getStorage().getStoredAmount().get(2).setValue(a100102);
        currentState.getStorage().getStoredAmount().get(3).setValue(a100103);
        currentState.getStorage().getStoredAmount().get(4).setValue(a100104);
        
        Collections.reverse(gameInfo.getMessages());

        return "main.xhtml?faces-redirect=true";
    }

    /**
     * Loads the first 10 rounds for statistical poupose from json files
     *
     * @throws IOException
     */
    public void saveHistory() throws IOException {
        ScenarioFactory instance = ScenarioFactory.getInstance();
        info = instance.createHistory(info);
        for (ArticleAndOrderStatistics a : info.getArticleAndOrderStatisticses()) {
            service.persistStuff(a);
        }
        for (ConveyorStatistics a : info.getConveyorStatisticses()) {
            service.persistStuff(a);
        }
        for (EmployeeStatistics a : info.getEmployeeStatisticses()) {
            service.persistStuff(a);
        }
        for (PalletStatistics a : info.getPalletStatisticses()) {
            service.persistStuff(a);
        }
        for (StateStatistics a : info.getStateStatisticses()) {
            service.persistStuff(a);
        }

        info = service.update(info);
    }

    /**
     * loads all objects to work with -> init
     *
     * @param ds the state
     * @return the state, if not successfull -> null
     */
    public DoloresState loadJsons(DoloresState ds) {

        //Standard Values for all Stuff
        try {
            ScenarioFactory instance = ScenarioFactory.getInstance();
            return instance.loadJsons(ds);

        } catch (IOException ex) {
            Logger.getLogger(GameInfoPM.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * Only used for Debbuging purposes
     *
     * @param process
     * @return
     */
    public int woSindDiePaletten(int process) {

        List<PalletsInProgress> palletsNotInStorage = currentState.getStorage().getPalletsNotInStorage();
        int count = 0;
        for (PalletsInProgress pip : palletsNotInStorage) {
            if (process == pip.getCurrentProcess()) {
                count++;
            }
        }
        return count;

    }

    /**
     * Calls the DB Service to recieve a single value from the db. e.g.
     * "COSTS_SALARY" in Round X
     *
     * @param dat
     * @param roundnumber
     * @return
     */
    public String getSingleString(String dat, int roundnumber) {

        try {
            return service.retriveSingleValues(dat, this.getInfo().getId(), roundnumber);
        } catch (SQLException ex) {
            Logger.getLogger(InfoPm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * Checks if the user is unlocked/locked gets called on initialization of
     * xhtml pages
     *
     * @throws IOException
     */
    public void check() throws IOException {

        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        DoloresPlayer loadPlayer = service.loadPlayer(principal.getName());
        this.player = loadPlayer;

        try {

            if (!player.isUnlocked()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("waitForUnlock.xhtml");
            }
        } catch (NullPointerException npe) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/dolores/faces/loginerror.xhtml");
        }
    }

    /**
     * Checks if the User is logged in or calls an URL directly (e.g. from a
     * boomark) -> redirects to the index page
     *
     * @throws IOException
     */
    public void checkMain() throws IOException {

        if (currentState.getStorage().getFreeStockGroundCount() == 0) {
            int debug = 0;
        }

        if (null == info) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./../index.xhtml");
        }
    }

    /**
     * Checks if the User is logged in or calls an URL directly (e.g. from a
     * boomark) -> redirects to the index page
     *
     * @throws IOException
     */
    public void checkMenu() throws IOException {

        if (null == info) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./../../index.xhtml");
        }
    }

    /**
     * checks if the game is over (e.g. constant in gamestate properties)
     *
     * @return
     */
    public boolean gameOver() {
        return info.getCurrentState().getPropVariables().get(DoloresConst.DOLORES_GAME_STATE).equals(DoloresConst.GameState.GAME_STATE_END);
    }

    public DoloresGameInfo getInfo() {
        return info;
    }

    public void setInfo(DoloresGameInfo info) {
        this.info = info;
    }

    public List<AbstractCalculator> getlCalculators() {
        return lCalculators;
    }

    public int getGameIdLoad() {
        return gameIdLoad;
    }

    public void setGameIdLoad(int gameIdLoad) {
        this.gameIdLoad = gameIdLoad;
    }

    public List<Message> getMessages() {
        messages = info.getMessages();
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public boolean isFromLoading() {
        return fromLoading;
    }

    public void setFromLoading(boolean fromLoading) {
        this.fromLoading = fromLoading;
    }

    public void setEmployeeCount(String[] employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String[] getEmployeeCount() {

        calculateEmployeeCount();
        return employeeCount;

    }

    public int getGameId() {
        return gameId;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public boolean isGameStateOk() {
        if (info.getCurrentState().getValue(DoloresConst.DOLORES_GAME_STATE).equals(DoloresConst.GameState.GAME_STATE_END)) {
            gameStateOk = false;
        } else {
            gameStateOk = true;
        }
        return gameStateOk;
    }

    public void setGameStateOk(boolean gameStateOk) {
        this.gameStateOk = gameStateOk;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DoloresState getCurrentState() {

        return currentState;
    }

    public void setCurrentState(DoloresState currentState) {
        this.currentState = currentState;
    }
}
