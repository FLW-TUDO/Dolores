/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresState;
import flw.business.store.ConveyorDynamics;
import flw.business.store.ConveyorFactory;
import flw.business.util.DoloresConst;
import flw.business.util.Processes;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author tilu
 */
@ManagedBean(name = "conveyor")
@SessionScoped
public class ConveyorPM implements Serializable {

    @ManagedProperty(value = "#{gameInfoPM}")
    private GameInfoPM gameInfoPM;
    private ConveyorDynamics clickedDynamics;
    private double[] conveyorWorkload;
    private int[] conveyorCount;
    private List<ConveyorDynamics> marketConveyorDynamics;
    private List<ConveyorDynamics> conveyorDynamics;
    private double maintenanceCost;
    private List<ConveyorDynamics> currentDynamics;
    private List<ConveyorDynamics> futureDynamics;
    private int count = 0;
    @EJB
    Service service;

    public void setGameInfoPM(GameInfoPM gameInfoPM) {
        this.gameInfoPM = gameInfoPM;
    }

    public ConveyorPM() {
        marketConveyorDynamics = new ArrayList<>();
    }

    public List<ConveyorDynamics> getFutureDynamics() {
        futureDynamics = new ArrayList<>();
        List<ConveyorDynamics> tempList = conveyorDynamics;

        int currentRoundNumber = gameInfoPM.getCurrentState().getRoundNumber();

        for (ConveyorDynamics cD : tempList) {
            if (cD.getRoundForDelivery() > currentRoundNumber) {
                futureDynamics.add(cD);
            }
        }

        return futureDynamics;
    }

    public double[] getConveyorWorkload() {
        
      
        
        conveyorWorkload = new double[5];
        DoloresState ds = gameInfoPM.getCurrentState();
        int i = 0;
        for (String process : Processes.getInstance().getProcesses_with_conveyors_capital()) {
            conveyorWorkload[i] = 100 * Double.valueOf(getSingleString("WORKLOAD_CONVEYORS_" + process, gameInfoPM.getCurrentState().getRoundNumber() - 1));
            i++;
        }
        return conveyorWorkload;
    }

    public void setConveyorWorkload(double[] conveyorWorkload) {
        this.conveyorWorkload = conveyorWorkload;
    }

    public void setConveyorCount(int[] conveyorCount) {
        this.conveyorCount = conveyorCount;
    }

    public int[] getConveyorCount() {
        conveyorCount = new int[3];
        DoloresState ds = gameInfoPM.getCurrentState();
        int i = 0;
        for (String process : Processes.getInstance().getProcessAbbrevationsWithConveyors()) {
            conveyorCount[i] = Integer.valueOf(ds.getValue(new StringBuilder("conveyor_count_").append(process).toString())); //NOI18N
            i++;
        }



        return conveyorCount;
    }

    public List<ConveyorDynamics> getCurrentDynamics() {

        currentDynamics = new ArrayList<>();
        List<ConveyorDynamics> tempList = getConveyorDynamics();
        DoloresState ds = gameInfoPM.getCurrentState();
        for (ConveyorDynamics cD : tempList) {

            if (cD.isReady(ds.getRoundNumber())) {
                currentDynamics.add(cD);
            }
        }

        return currentDynamics;



    }

    public List<ConveyorDynamics> getConveyorDynamics() {

        DoloresState ds = gameInfoPM.getCurrentState();
        this.conveyorDynamics = ds.getConveyorDynamics();

        return conveyorDynamics;


    }

    public void setCurrentDynamics(List<ConveyorDynamics> currentDynamics) {
        this.currentDynamics = currentDynamics;
    }

    public void enableMaintenanceListener(ActionEvent event) {

        conveyorDynamics.remove(clickedDynamics);
        currentDynamics.remove(clickedDynamics);

        clickedDynamics.setMaintenanceEnabled(true);

        currentDynamics.add(clickedDynamics);
        conveyorDynamics.add(clickedDynamics);
        gameInfoPM.getCurrentState().setConveyorDynamics(conveyorDynamics);

    }

    public void enableMaintenance() {

        conveyorDynamics.remove(clickedDynamics);
        currentDynamics.remove(clickedDynamics);

        clickedDynamics.setMaintenanceEnabled(true);

        currentDynamics.add(clickedDynamics);
        conveyorDynamics.add(clickedDynamics);
        gameInfoPM.getCurrentState().setConveyorDynamics(conveyorDynamics);
        
    }

    public void disableMaintenance() {
        conveyorDynamics.remove(clickedDynamics);
        currentDynamics.remove(clickedDynamics);

        clickedDynamics.setMaintenanceEnabled(false);

        currentDynamics.add(clickedDynamics);
        conveyorDynamics.add(clickedDynamics);
        gameInfoPM.getCurrentState().setConveyorDynamics(conveyorDynamics);
    }

    public void doOverhaul() {
        conveyorDynamics.remove(clickedDynamics);
        currentDynamics.remove(clickedDynamics);

        clickedDynamics.setOverhaul(true);

        currentDynamics.add(clickedDynamics);
        conveyorDynamics.add(clickedDynamics);
        gameInfoPM.getCurrentState().setConveyorDynamics(conveyorDynamics);
    }



    public int getMaintenanceCost() {

        maintenanceCost = 0;
        for (ConveyorDynamics dyn : currentDynamics) {
            if (dyn.isMaintenanceEnabled()) {
                maintenanceCost = maintenanceCost + dyn.getMaintenanceCost();
            }
        }
        return (int)maintenanceCost;
    }

    public void setMaintenanceCost(double maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public double getRepairCost() {
        DoloresState currentState = gameInfoPM.getCurrentState();
        return Double.valueOf(currentState.getValue("costs_repair")); //NOI18N
    }
    
    public int getRoundforDeliveryDLG(){
        if(clickedDynamics==null){
            return 0;
        }
        return gameInfoPM.getCurrentState().getRoundNumber()+clickedDynamics.getTimeToDelivery();
    }

    public double getRepairTime() {
        DoloresState currentState = gameInfoPM.getCurrentState();
        return Double.valueOf(currentState.getValue("repair_duration")); //NOI18N
    }

    public void ajaxUpdateConveyorDynamics() {

        DoloresGameInfo info = gameInfoPM.getInfo();
        info.getCurrentState().setConveyorDynamics(conveyorDynamics);
        //System.out.println(conveyorDynamics.get(0).getProcess());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.convPRO")));
    }
    
    public void updateConveyorDynamics() {

        DoloresGameInfo info = gameInfoPM.getInfo();
        info.getCurrentState().setConveyorDynamics(conveyorDynamics);
    }

    public void sell() {
        ConveyorDynamics cD = clickedDynamics;
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState ds = info.getCurrentState();

        cD.setSold(true);
    }

    
    /*
     * TODO den Markt nachfüllen.
     */
    public List<ConveyorDynamics> getMarketConveyorDynamics() {


        if (count < 8) {

            List<ConveyorDynamics> temp = ConveyorFactory.getConveyorDynamicsBase();
            marketConveyorDynamics = temp;
            count = 8;

        }

        return marketConveyorDynamics;
    }

    /**
     * Buys the clicked conveyor
     */
    
    public void buy() {

        ConveyorDynamics cD = clickedDynamics;



        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState ds = info.getCurrentState();
        int rNumber = Integer.valueOf(ds.getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER));

        ConveyorDynamics createBoughtConveyor = ConveyorFactory.createBoughtConveyor(cD, rNumber, 0);

        ds.addConveyorDynamics(createBoughtConveyor);

        createBoughtConveyor.setState(ds);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.convBUY")));
        

    }

    public ConveyorDynamics getClickedDynamics() {
        return clickedDynamics;
    }

    public void setClickedDynamics(ConveyorDynamics clickedDynamics) {
        this.clickedDynamics = clickedDynamics;
    }
    
    public String getSingleString(String dat, int roundnumber /*, int type*/) {
        String xd = null;
        List list;
        try {
            return service.retriveSingleValues(dat, gameInfoPM.getInfo().getId(), roundnumber);
        } catch (SQLException ex) {
            Logger.getLogger(InfoPm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
}
