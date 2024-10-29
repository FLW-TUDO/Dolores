/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
import flw.business.core.DoloresState;
import flw.business.util.DoloresConst;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

/**
 *
 * @author tilu
 */
@ManagedBean(name = "gameState")
@SessionScoped
public class GameStatePM implements Serializable {

    @ManagedProperty(value = "#{gameInfoPM}")
    private GameInfoPM gameInfoPM;
    private int workClimateIndex;
    private int gameInfoId;
    private String currentBalance;
    private double customerSatisfaction;
    private int roundNumber;
    @EJB
    private Service service;

    public void setGameInfoPM(GameInfoPM gameInfoPM) {
        this.gameInfoPM = gameInfoPM;
    }

    public double getCustomerSatisfaction() {
        DoloresState ds = gameInfoPM.getCurrentState();

        String g = ds.getValue(DoloresConst.DOLORES_KEY_CUSTOMER_SATISFACTION);
        double temp = Double.valueOf(g);
        temp = temp * 100;
        temp = Math.round(temp);
        customerSatisfaction = temp / 100;
        return customerSatisfaction;
    }

    public void setCustomerSatisfaction(double customerSatisfaction) {
        this.customerSatisfaction = customerSatisfaction;
    }

    public String getCurrentBalance() {

        DoloresState ds = gameInfoPM.getCurrentState();

        return ds.getValue(DoloresConst.DOLORES_GAME_ACCOUNT_BALANCE);




    }

    public int getRoundNumber() {

        DoloresState ds = gameInfoPM.getCurrentState();
        roundNumber = Integer.valueOf(ds.getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER));

        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getWorkClimateIndex() {
        return workClimateIndex;
    }

    public void setWorkClimateIndex(int workClimateIndex) {
        this.workClimateIndex = workClimateIndex;
    }

    
    public void ajaxUpdateWorkClimateIndex() {
        
        service.updateWorkClimateIndex(workClimateIndex, gameInfoPM.getCurrentState());
        //System.out.println(workClimateIndex);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.bk")));

    }
    
    
}
