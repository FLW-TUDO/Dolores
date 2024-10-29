/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.business;

import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresPlayer;
import flw.business.core.DoloresState;
import flw.business.store.Employee;
import flw.business.util.DoloresConst;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * @author tilu
 */
@Stateless
public class Service {

    private List<DoloresState> DoloresStates;
    private List<Employee> employeeTempList;
    private DoloresState lastState;
    @PersistenceContext(unitName = "DoloresDS")
    private EntityManager em;

    HashMap stateMap, articleAndOrderMap, conveyorMap, employeeMap, palletMap;

    /**
     * retrieves the player by String playername from DB
     *
     * @param playerName
     * @return
     */
    public DoloresPlayer loadPlayer(String playerName) {
        em.clear();
        em.flush();
        DoloresPlayer player = em.find(DoloresPlayer.class, playerName);
        return player;
    }

    public void updatePlayer(DoloresPlayer doloPlayer) {
        em.merge(doloPlayer);
    }

    /**
     * Updates the WCI called from xhtml
     *
     * @param wciIndex
     * @param currentState
     */
    public void updateWorkClimateIndex(int wciIndex, DoloresState currentState) {

        em.clear();
        em.flush();

        int x;
        switch (wciIndex) {
            case 1:
                x = 0;
                currentState.setValue(DoloresConst.DOLORES_KEY_WORK_CLIMATE_INVEST, String.valueOf(DoloresConst.WORK_CLIMATE_INVEST[x]));

                break;
            case 2:
                x = 1;
                currentState.setValue(DoloresConst.DOLORES_KEY_WORK_CLIMATE_INVEST, String.valueOf(DoloresConst.WORK_CLIMATE_INVEST[x]));
                break;
            case 3:
                x = 2;
                currentState.setValue(DoloresConst.DOLORES_KEY_WORK_CLIMATE_INVEST, String.valueOf(DoloresConst.WORK_CLIMATE_INVEST[x]));
                break;
            case 4:
                x = 3;
                currentState.setValue(DoloresConst.DOLORES_KEY_WORK_CLIMATE_INVEST, String.valueOf(DoloresConst.WORK_CLIMATE_INVEST[x]));
                break;
            case 5:
                x = 4;
                currentState.setValue(DoloresConst.DOLORES_KEY_WORK_CLIMATE_INVEST, String.valueOf(DoloresConst.WORK_CLIMATE_INVEST[x]));
                break;

            default:
                System.out.print("Fehler WCI");
                break;
        }
        em.merge(currentState);

    }

    public void deleteState(DoloresState state) {
        em.merge(state);
        em.remove(state);
    }
    
    /*
        Deletes selected Player from DB
    */
    public void deletePlayer(DoloresPlayer player) throws SQLException
    {
        Connection connection = em.unwrap(Connection.class);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM t_user WHERE user_email = '" + player.getId() + "'");
        try{
            em.remove(em.merge(player));
            em.flush();
            System.out.println("Löschen erfolgreich");
        } catch(Exception e){
            System.out.println("Löschen fehlgeschlagen");
        } 
        
    }
    /**
     * Deletes Game o from DB with {@link EntityManager}.
     * First delete connection between game o and its player in 
     * table t_user_doloresgameinfo. After that delete the rest of game o.
     * @param o Game object
     * @author stbi
     */
    public void deleteGame(DoloresGameInfo o) throws SQLException{
        long gameID = o.getId();
        Connection connection = em.unwrap(Connection.class);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM doloresgameinfo WHERE GAMEINFO_ID = '" + gameID + "'");
        try{
            em.remove(em.merge(o));
            em.flush();
            System.out.println("Löschen erfolgreich");
        } catch(Exception e){
            System.out.println("Löschen fehlgeschlagen");
        }
    }
    
    /**
     *
     * Retrieves Statistics from DB
     *
     * @param value value is the actual value in DB
     * @param gameId
     * @return
     */
    public List retrieveStats(String value, Long gameId) {
        em.clear();
        em.flush();

        String[] table = {"STATESTATISTICS", "ArticleAndOrderStatistics", "ConveyorStatistics", "EmployeeStatistics", "PalletStatistics"};
        Long gameIdLong = new Long(gameId);

        String queryString;
        Query nativeQuery;
        List resultList = new ArrayList();

        int y = 0;

        Connection connection = em.unwrap(Connection.class);
        Statement stmt;
        try {
            stmt = connection.createStatement();

            while (y < 5) {
                if (columnchecker(stmt.executeQuery("SELECT * FROM " + table[y] + " s WHERE s.GAME_ID = " + gameId), value)) {
                    ResultSet rs = stmt.executeQuery("SELECT s.ROUNDNUMBER, s." + value + " FROM " + table[y] + " s WHERE s.GAME_ID = " + gameId);

                    ResultSetMetaData meta = rs.getMetaData();
                    int numCol = meta.getColumnCount();

                    while (rs.next()) {
                        Object valueObj = rs.getObject(value);
                        Object roundObj = rs.getObject("ROUNDNUMBER");
                        Object[] temp = new Object[2];
                        temp[0] = roundObj;
                        temp[1] = valueObj;
                        resultList.add(temp);
                    }
                    if (resultList.isEmpty()) {
                        y++;
                    } else {
                        return resultList;
                    }
                } else {
                    y++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList;
    }

    public boolean columnchecker(ResultSet crsm, String name) {
        ResultSetMetaData meta;
        try {
            meta = crsm.getMetaData();
            int numCol = meta.getColumnCount();

            for (int i = 1; i < numCol + 1; i++) {
                if (meta.getColumnName(i).equals(name)) {
                    return true;
                }

            }
            return false;

        } catch (SQLException ex) {
            Logger.getLogger(Service.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * Retrieves the for the ADMIN important Data to Export eg roundnumber,
     * accountbalance, lastAction, satisfaction
     *
     * @param gameId
     * @return
     */
    public List retrieveExportData(Long gameId) {
        em.clear();
        em.flush();

        Long gameIdLong = new Long(gameId);

        String queryString;
        Query nativeQuery;
        List resultList = null;

        int y = 0;

        try {
            queryString = new StringBuilder("SELECT s.ROUNDNUMBER, s.ACCOUNTBALANCE, s.SATISFACTION, s.pointInTime  FROM STATESTATISTICS s WHERE s.GAME_ID = " + gameId).toString();
            nativeQuery = em.createNativeQuery(queryString);
            resultList = nativeQuery.getResultList();

        } catch (PersistenceException ex) {
        }

        return resultList;
    }

    /**
     *
     * Retrieves a single Value from the DB
     *
     * eg. SELECT s.ACCOUNTBALANCE FROM STATESTATISTICS WHERE s.GAME_ID='5' AND
     * s.ROUNDNUMBER ='10'
     *
     * @param value
     * @param gameId
     * @param roundNumber
     * @return
     */
    public String retriveSingleValues(String value, Long gameId, int roundNumber) throws SQLException {
        em.clear();
        em.flush();

        String[] table = {"STATESTATISTICS", "ArticleAndOrderStatistics", "ConveyorStatistics", "EmployeeStatistics", "PalletStatistics"};
        Long gameIdLong = gameId;

        if (null == stateMap) {
            stateMap = new HashMap<>();
            articleAndOrderMap = new HashMap();
            conveyorMap = new HashMap();
            employeeMap = new HashMap();
            palletMap = new HashMap();
            stateMap.put("Roundnumber", 0);
        }

        stateMap.clear();
        articleAndOrderMap.clear();
        conveyorMap.clear();
        employeeMap.clear();
        palletMap.clear();

        int y = 0;

        while (y < 5) {

            Connection connection = em.unwrap(Connection.class);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table[y] + " s WHERE s.GAME_ID = " + gameId + " AND s.ROUNDNUMBER = " + roundNumber);
            ResultSetMetaData rsmd = rs.getMetaData();

            rs.next();

            if (y == 0) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    stateMap.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));
                }
            } else if (y
                    == 1) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    articleAndOrderMap.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));
                }
            } else if (y
                    == 2) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    conveyorMap.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));
                }
            } else if (y
                    == 3) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    employeeMap.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));
                }
            } else if (y
                    == 4) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    palletMap.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));
                }
            }
            y++;

        }
        y = 0;

        while (y < 5) {
            if (y == 0) {
                if (null != stateMap.get(value)) {
                    return stateMap.get(value).toString();
                }
            } else if (y == 1) {
                if (null != articleAndOrderMap.get(value)) {
                    articleAndOrderMap.get(value).toString();
                }
            } else if (y == 2) {
                if (null != conveyorMap.get(value)) {
                    return conveyorMap.get(value).toString();
                }
            } else if (y == 3) {
                if (null != employeeMap.get(value)) {
                    return employeeMap.get(value).toString();
                }
            } else if (y == 4) {
                if (null != palletMap.get(value)) {
                    return palletMap.get(value).toString();
                }
            }
            y++;
        }

        return null;
    }
    
    public void updateSingleGame(DoloresGameInfo game){
            ArrayList<DoloresState> states = new ArrayList(this.retriveSingleStates(game));
            if(!states.isEmpty()) game.setCurrentState(states.get(states.size() - 1));
    }
    
    String[] table = {"STATESTATISTICS", "ArticleAndOrderStatistics", "ConveyorStatistics", "EmployeeStatistics", "PalletStatistics"};
    
    public void deleteLastState(DoloresGameInfo info){
        try{
            Connection connection = em.unwrap(Connection.class);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM doloresstate WHERE GAMEINFO_GAMEINFO_ID = '" + info.getId() + "' AND ROUNDNUMBER = '" + info.getCurrentState().getRoundNumber() + "'");              
        }catch(SQLException s)
        {
            System.out.println("Objekt(e) konnte(n) nicht gelöscht werden.");
        }
        System.out.println("Löschen erfolgreich");
    }
    
    public DoloresState getLastState(DoloresGameInfo info)
    {
        Collection<DoloresState> states = retriveSingleStates(info);
        ArrayList<DoloresState> newStates = new ArrayList<>(states);
        
        if(newStates.size() > 1)
            return newStates.get(newStates.size() - 2);
        else
            throw new IllegalArgumentException("Spiel besitzt keine vorherige Periode");
    }
    
    
    public Collection<DoloresGameInfo> retriveSingleGames(DoloresPlayer player)
    {
        em.clear();
        em.flush();

        Query query = em.createQuery("SELECT d FROM DoloresGameInfo d");
        Collection<DoloresGameInfo> collection = (Collection<DoloresGameInfo>) query.getResultList();
        Collection<DoloresGameInfo> results = new ArrayList<>();
        
        for(DoloresGameInfo g : collection)
        {
            if(g.getDoloresPlayer() != null && g.getDoloresPlayer().getId().equalsIgnoreCase(player.getId())){
                results.add(g);
            }      
        }
        return results;
    }
    
    public Collection<DoloresGameInfo> retriveSingleGamesADM(DoloresPlayer player)
    {
        em.clear();
        em.flush();

        Query query = em.createQuery("SELECT d FROM DoloresGameInfo d");
        Collection<DoloresGameInfo> collection = (Collection<DoloresGameInfo>) query.getResultList();
        Collection<DoloresGameInfo> results = new ArrayList<>();
        
        for(DoloresGameInfo g : collection)
        {
            if(g.getDoloresPlayer() != null && g.getDoloresPlayer().getId().equalsIgnoreCase(player.getId())){
                results.add(g);
                updateSingleGame(g);
            }      
        }
        Collections.reverse((List<DoloresGameInfo>) results);
        return results;
    }
    
    public Collection<DoloresState> retriveSingleStates(DoloresGameInfo game)
    {
        em.clear();
        em.flush();

        Query query = em.createQuery("SELECT d FROM DoloresState d");
        Collection<DoloresState> collection = (Collection<DoloresState>) query.getResultList();
        Collection<DoloresState> results = new ArrayList<>();
        
        for(DoloresState g : collection)
        {
            if(g.getGameInfo() != null && g.getGameInfo().getId() == game.getId()){
                results.add(g);
            }
                
        }
        
        return results;
    }
    
    public List<DoloresState> getLastNStatesManualSave(DoloresGameInfo game,int count, boolean includeCurrentRound) {
        List<DoloresState> toReturn = new ArrayList<>();
        List<DoloresState> lDoloresStates = new ArrayList(retriveSingleStates(game));
             
        int index = lDoloresStates.size();

        if (index < count) {
            count = index;
        }

        index--;

        if (includeCurrentRound) {
            for (int i = 0; i < count; i++) {
                toReturn.add(lDoloresStates.get(index));
                index = index - 1;
            }
        } else {
            index = index - 1;
            for (int i = 0; (i < count && i > -1); i++) {

                if (index > -1) {

                    toReturn.add(lDoloresStates.get(index));
                    index = index - 1;
                }
            }
        }        
        return toReturn;
    }
    
    public List retriveSingleValueState(String value, Long gameId, int roundNumber) {
        em.clear();
        em.flush();

        Long gameIdLong = new Long(gameId);

        String queryString;
        Query nativeQuery;
        List resultList = null;

        int y = 0;
        while (resultList == null && y < 5) {
            try {
                queryString = new StringBuilder("SELECT s." + value + " FROM STATESTATISTICS s WHERE s.GAME_ID = " + gameId + " AND s.ROUNDNUMBER = " + roundNumber).toString();
                nativeQuery = em.createNativeQuery(queryString);
                resultList = nativeQuery.getResultList();

            } catch (PersistenceException ex) {
                y++;
            }
        }

        return resultList;
    }

    /**
     *
     * Returns all Players (DoloresPlayer) from the DB
     *
     * @return
     */
    public Collection<DoloresPlayer> retrieveAllPlayers() {
        em.clear();
        em.flush();

        Query query = em.createQuery("SELECT d FROM DoloresPlayer d");
        Collection<DoloresPlayer> collection = (Collection<DoloresPlayer>) query.getResultList();
        return collection;
    }

    /**
     *
     * Returns all GameInfoObjects (DoloresGameInfo) from the DB
     *
     * @return Collection of all games
     */
    public Collection<DoloresGameInfo> retrieveAllGameInfos() {
        em.clear();
        em.flush();

        Query query = em.createQuery("SELECT d FROM DoloresGameInfo d");
        Collection<DoloresGameInfo> collection = (Collection<DoloresGameInfo>) query.getResultList();
        return collection;
    }

    /**
     *
     * returns the cost for new employees
     *
     * @param gameId
     * @param roundNumber
     * @return
     */
    public List retriveSingleValuesEmp(Long gameId, int roundNumber) {
        em.clear();
        em.flush();

        Long gameIdLong = new Long(gameId);

        String queryString;
        Query nativeQuery;
        List resultList = null;

        int y = 0;
        while (resultList == null && y < 5) {
            try {
                queryString = new StringBuilder("SELECT s.COSTS_NEW FROM EmployeeStatistics s WHERE s.GAME_ID = " + gameId + " AND s.ROUNDNUMBER = " + roundNumber).toString();
                nativeQuery = em.createNativeQuery(queryString);
                resultList = nativeQuery.getResultList();

            } catch (PersistenceException ex) {
                y++;
            }
        }

        return resultList;
    }

    public void persistGameInfo(DoloresGameInfo doloresgameinfo) {

        em.persist(doloresgameinfo);

    }

    @Deprecated
    public void begin() {
        em.getTransaction().begin();
    }

    public DoloresGameInfo update(DoloresGameInfo info) {
        DoloresGameInfo infoToBeUpdated = em.merge(info);
        em.persist(infoToBeUpdated);
        return infoToBeUpdated;
    }

    public void persistStuff(Object o) {
        em.persist(o);
    }

    public void mergeEmployee(Employee e) {
        em.merge(e);
    }

    public void persistState(DoloresState doloresState) {
        em.persist(doloresState);

    }

    public DoloresState updateState(DoloresState ds) {
        return em.merge(ds);

    }

    public DoloresPlayer mergePlayer(DoloresPlayer player) {
        return em.merge(player);
    }

    public void deleteStatistics(Long gameId, int roundNumber) {
        try {
            em.clear();
            em.flush();

            String[] table = {"STATESTATISTICS", "ArticleAndOrderStatistics", "ConveyorStatistics", "EmployeeStatistics", "PalletStatistics"};
            Long gameIdLong = gameId;

            int y = 0;

            while (y < 5) {
                
                    Connection connection = em.unwrap(Connection.class);
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate("DELETE FROM " + table[y] + " WHERE GAME_ID = '" + gameId + "' AND ROUNDNUMBER = '" + roundNumber + "'");

                    y++;
            }
        } catch (SQLException ex) {
                Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public void deleteMessages(Long gameId, int roundNumber) {
        try {
            em.clear();
            em.flush();
            
            Connection connection = em.unwrap(Connection.class);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM message WHERE GAME_ID = '" + gameId + "' AND ROUNDNUMBER = '" + roundNumber + "'");
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
