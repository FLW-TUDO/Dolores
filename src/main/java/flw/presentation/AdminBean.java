/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresPlayer;
import flw.business.util.ExportObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author tilu
 */
@ManagedBean(name = "admPM")
@SessionScoped
public class AdminBean implements Serializable {

    private CartesianChartModel balanceChart;
    private DoloresGameInfo storedInfo;
    private CartesianChartModel storageInfoChart1;
    private CartesianChartModel storageInfoChart2;
    private CartesianChartModel storageInfoChart3;
    private CartesianChartModel storageInfoChart4;
    @EJB
    private Service service;
    
    private DoloresGameInfo selectedGame;
    private List<ExportObject> exports;

    
   
    private DoloresPlayer[] selectedPlayers;
    private DoloresPlayer selectedPlayer;
    
    public AdminBean() {
        super();
    }
    
    @PostConstruct
    public void init(){

    }
    
    /**
     * Updates all games in this bean von DB
     */

    public Collection<DoloresGameInfo> getGames() {

        return service.retrieveAllGameInfos();
    }
    public DoloresPlayer[] getSelectedPlayers() {
        return selectedPlayers;
    }

    public void setSelectedPlayers(DoloresPlayer[] selectedPlayers) {
        this.selectedPlayers = selectedPlayers;
    }
    
    /**
     * Updates the list of games from selected player, sets them in
     * the player and returns them
     * @return Collection of games from selected player.
     */
    public Collection<DoloresGameInfo> getSingleGames() {
        return service.retriveSingleGamesADM(selectedPlayer);
    }

    public String viewGames(DoloresPlayer clickedPlayer) {
        selectedPlayer = clickedPlayer;
        return "viewSingle.xhtml?faces-redirect=true";
    }

    public DoloresGameInfo getSelectedGame() {
        return selectedGame;
    }

    public void setSelectedGame(DoloresGameInfo selectedGame) {
        this.selectedGame = selectedGame;
    }

    public DoloresPlayer getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(DoloresPlayer selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }
    
    public void unlockSelectetPlayer(DoloresPlayer clickedPlayer){
        clickedPlayer.setUnlocked(true);
        service.updatePlayer(clickedPlayer);
    }
    
    public void lockSelectetPlayer(DoloresPlayer clickedPlayer){
        clickedPlayer.setUnlocked(false);
        service.updatePlayer(clickedPlayer);
    }
    
    public void allowRollback(DoloresGameInfo info){
        info.setRollback(true);
        service.update(info);
    }
    
    public void disableRollback(DoloresGameInfo info){
        info.setRollback(false);
        service.update(info);
    }

    public void deleteSingleGame() throws SQLException {
        service.deleteGame(selectedGame);
        //if(selectedPlayer != null) selectedPlayer.removeGame(selectedGame);
        selectedGame = null;
    }
    
    public void loadSingleGame() throws FileNotFoundException, IOException{
        System.out.println("Ein DoloresGame für " + selectedPlayer.getId() + " wird geladen.");
        //service.loadGame(selectedPlayer);
    }
    
    public void saveSingleGame() throws IOException, SQLException{
        System.out.println("Spieler " + selectedGame.getPlayerName() + " von Benutzer/Player " + selectedPlayer.getId() + " wird gespeichert.");
        //service.saveGameObject(selectedGame.getId());
        //service.saveGameSQL(selectedGame.getId());
        System.out.println("Spiel gespeichert.");
    }
    
    /**
     * Löscht ausgewählten Spieler. Überprüft vorher ob der Spieler noch 
     * Spiele hat und löscht diese entsprechend.
     * @throws java.sql.SQLException
     */
    public void deletePlayer() throws SQLException{
        deleteAllGamesFromSelectedPlayer();
        System.out.println("Entferne Spieler " + selectedPlayer.getId() + ".");
        service.deletePlayer(selectedPlayer);
    }
    
    /**
     * Löscht alle Spiele vom ausgewählten Spieler
     */
    public void deleteAllGamesFromSelectedPlayer() throws SQLException{
        // Spiele des Spielers löschen
        Collection<DoloresGameInfo> playerGames = service.retriveSingleGames(selectedPlayer);
        int i = 0;
        for(DoloresGameInfo g : playerGames){
            System.out.println("Entferne Spiel " + i++ + " aus der Datenbank.");
            selectedGame = g;
            service.deleteGame(selectedGame);
            selectedGame = null;
        }
        System.out.println("Erledigt.");
    }
    
    public int getGamesCount(DoloresPlayer player){
        return service.retriveSingleGames(player).size();
    }

    /**
     *
     * @return GameInfos with lastaction under 300000 miliseconds (old 6000000)
     */
    public Collection<DoloresGameInfo> getRunningGames() {
        Collection<DoloresGameInfo> tempList = service.retrieveAllGameInfos();
        ArrayList<DoloresGameInfo> runningGames = new ArrayList<>();
        for (DoloresGameInfo info : tempList) {
            // 60000000
            if (info.getLastAction() + 300000 > System.currentTimeMillis()) {
                runningGames.add(info);
            }
        }
        return runningGames;
    }

    /**
     * Sync all players with DB and refresh them in this AdminBean
     * @return all players from DB
     */
    public Collection<DoloresPlayer> getPlayers() {
        // aktuelle Spieler aus der DB laden, damit Bean konsistent bleibt
        return service.retrieveAllPlayers();
    }

    public CartesianChartModel getBalanceChart() {
        return balanceChart;
    }

    public void setBalanceChart(CartesianChartModel balanceChart) {
        this.balanceChart = balanceChart;
    }

    /**
     *
     * @param gameInfo
     * @return the Balance Chart (Accountbalance)
     */
    public CartesianChartModel getBalance(DoloresGameInfo gameInfo) {
        CartesianChartModel balanceChartLocal = new CartesianChartModel();
        List retrieveStats = service.retrieveStats("ACCOUNTBALANCE", gameInfo.getId());
        LineChartSeries series1 = new LineChartSeries();

        //series1.setLabel("Kontostand");

        Object[] toArray = retrieveStats.toArray();
        for (Object o : toArray) {
            //series1.set(o[1], o[0]);
            Object[] totoArray = (Object[]) o;
            String x = totoArray[0].toString();
            String y = totoArray[1].toString();

            int xd = Integer.parseInt(x) + 1;
            double yd = Double.parseDouble(y);

            if(xd > 9)series1.set(xd, yd);
        }

        balanceChartLocal.addSeries(series1);

        return balanceChartLocal;
    }

    /**
     *
     * @param gameInfo
     * @return the Satisfaction Chart (Satisfaction)
     */
    public CartesianChartModel getSatisfaction(DoloresGameInfo gameInfo) {
        CartesianChartModel satChart = new CartesianChartModel();
        List retrieveStats = service.retrieveStats("SATISFACTION", gameInfo.getId());
        LineChartSeries series1 = new LineChartSeries();


        Object[] toArray = retrieveStats.toArray();
        for (Object o : toArray) {
            Object[] totoArray = (Object[]) o;
            String x = totoArray[0].toString();
            String y = totoArray[1].toString();

            int xd = Integer.parseInt(x) + 1;
            double yd = Double.valueOf(y);
            if(xd > 9) series1.set(xd, yd);
        }

        satChart.addSeries(series1);

        return satChart;
    }

    public String storeInfo(DoloresGameInfo info) {
        storedInfo = info;
        return "details.xhtml";
    }

    public String export(DoloresGameInfo info) {
        storedInfo = info;
        return "export.xhtml";
    }

    public DoloresGameInfo getStoredInfo() {
        return storedInfo;
    }

    public void setStoredInfo(DoloresGameInfo storedInfo) {
        this.storedInfo = storedInfo;
    }

    /**
     *
     * @param info
     * @return the Storage Chart (100101)
     */
    public CartesianChartModel getStorageInfoChart1(DoloresGameInfo info) {
        storageInfoChart1 = new CartesianChartModel();
        List retrieveStats = service.retrieveStats("PROCESS_PALLET_COUNT_LA_100101", info.getId());
        LineChartSeries series1 = new LineChartSeries();
        Object[] toArray = retrieveStats.toArray();
        for (Object o : toArray) {
            //series1.set(o[1], o[0]);
            Object[] totoArray = (Object[]) o;
            String x = totoArray[0].toString();
            String y = totoArray[1].toString();

            int xd = Integer.valueOf(x).intValue();
            double yd = Double.valueOf(y).doubleValue();

            series1.set(xd, yd);
        }

        storageInfoChart1.addSeries(series1);

        return storageInfoChart1;
    }

    public void setStorageInfoChart1(CartesianChartModel storageInfoChart1) {
        this.storageInfoChart1 = storageInfoChart1;
    }

    /**
     *
     * @param info
     * @return the Storage Chart (100102)
     */
    public CartesianChartModel getStorageInfoChart2(DoloresGameInfo info) {
        storageInfoChart2 = new CartesianChartModel();
        
        List retrieveStats = service.retrieveStats("PROCESS_PALLET_COUNT_LA_100102", info.getId());
        LineChartSeries series1 = new LineChartSeries();


        Object[] toArray = retrieveStats.toArray();
        for (Object o : toArray) {
            //series1.set(o[1], o[0]);
            Object[] totoArray = (Object[]) o;
            String x = totoArray[0].toString();
            String y = totoArray[1].toString();

            int xd = Integer.parseInt(x);
            double yd = Double.parseDouble(y);

            series1.set(xd, yd);
        }

        storageInfoChart2.addSeries(series1);

        return storageInfoChart2;
    }

    public void setStorageInfoChart2(CartesianChartModel storageInfoChart2) {
        this.storageInfoChart2 = storageInfoChart2;
    }

    /**
     *
     * @param info
     * @return the Storage Chart (100103)
     */
    public CartesianChartModel getStorageInfoChart3(DoloresGameInfo info) {
        storageInfoChart3 = new CartesianChartModel();

        List retrieveStats = service.retrieveStats("PROCESS_PALLET_COUNT_LA_100103", info.getId());
        LineChartSeries series1 = new LineChartSeries();


        Object[] toArray = retrieveStats.toArray();
        for (Object o : toArray) {
            //series1.set(o[1], o[0]);
            Object[] totoArray = (Object[]) o;
            String x = totoArray[0].toString();
            String y = totoArray[1].toString();

            int xd = Integer.parseInt(x);
            double yd = Double.valueOf(y);

            series1.set(xd, yd);
        }

        storageInfoChart3.addSeries(series1);

        return storageInfoChart3;
    }

    public void setStorageInfoChart3(CartesianChartModel storageInfoChart3) {
        this.storageInfoChart3 = storageInfoChart3;
    }

    /**
     *
     * @param info
     * @return the Storage Chart (100104)
     */
    public CartesianChartModel getStorageInfoChart4(DoloresGameInfo info) {
        storageInfoChart4 = new CartesianChartModel();

        List retrieveStats = service.retrieveStats("PROCESS_PALLET_COUNT_LA_100104", info.getId());
        LineChartSeries series1 = new LineChartSeries();


        Object[] toArray = retrieveStats.toArray();
        for (Object o : toArray) {
            //series1.set(o[1], o[0]);
            Object[] totoArray = (Object[]) o;
            String x = totoArray[0].toString();
            String y = totoArray[1].toString();

            int xd = Integer.parseInt(x);
            double yd = Double.parseDouble(y);

            series1.set(xd, yd);
        }

        storageInfoChart4.addSeries(series1);

        return storageInfoChart4;
    }

    public void setStorageInfoChart4(CartesianChartModel storageInfoChart4) {
        this.storageInfoChart4 = storageInfoChart4;
    }

    public List<ExportObject> getExports() {
        List retrieveStats = service.retrieveExportData(storedInfo.getId());
        exports = new ArrayList<>();
        Object[] toArray = retrieveStats.toArray();
        for (Object o : toArray) {
            //series1.set(o[1], o[0]);
            Object[] totoArray = (Object[]) o;
            String x = totoArray[0].toString();
            int round = Integer.parseInt(x);
            if(round > 9)
            {
                String y = totoArray[1].toString();
                y = "" + (int) Double.parseDouble(y);
                String z = totoArray[2].toString();
                z += "%";
                z = z.replace(".", ",");


                String a = totoArray[3].toString();

                Long time = Long.valueOf(a);

                Calendar cal = Calendar.getInstance();

                cal.setTimeInMillis(time);

                java.util.Date date = cal.getTime();

                String currentTime = date.getHours() + ":" + date.getMinutes() + " " + date.getDate() + "." + (date.getMonth() + 1) + "." + (date.getYear() + 1900);
                ExportObject e = new ExportObject(x, y, z, currentTime);

                exports.add(e);
            }
        }
        return exports;
    }

    public void setExports(List<ExportObject> exports) {
        this.exports = exports;
    }
}