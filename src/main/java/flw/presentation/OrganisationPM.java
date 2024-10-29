/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresState;
import flw.business.util.DoloresConst;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SlideEndEvent;

/**
 *
 * @author tilu
 */
@ManagedBean(name = "orgaPM")
@SessionScoped
public class OrganisationPM implements Serializable {
    private String palletControlFactor;
    private String palletControlFactorWA;
    private String securityDeviceUsed;
    @ManagedProperty(value = "#{gameInfoPM}")
    private GameInfoPM gameInfoPM;
    private int gameInfoId;
    @EJB
    private Service service;
    private int storageFactorPercentIn = 50;
    private int storageFactorPercentOut = 50;
    private String strategyIncoming;
    private String strategyStorage;
    private String strategyOutgoing;
    private boolean abcAnalyse = false;
    private boolean isAbcRdy = false;
    private boolean zoneRdy = false;
    private int debugStorage;

    public OrganisationPM() {
    }
    
    public void setGameInfoPM(GameInfoPM gameInfoPM) {
        this.gameInfoPM = gameInfoPM;
    }

    public boolean isIsAbcRdy() {
        String value = gameInfoPM.getCurrentState().getValue(DoloresConst.DOLORES_KEY_NEXT_ABC_ANALYSIS_PERIODE);
        if (value != null) {
            int x = Integer.valueOf(value);
            if (x < gameInfoPM.getCurrentState().getRoundNumber()) {
                isAbcRdy = true;
                abcAnalyse = false;     //TODO ABC ANALYSE PRÜFEN
            }
        }
        return isAbcRdy;
    }

    public void setIsAbcRdy(boolean isAbcRdy) {
        this.isAbcRdy = isAbcRdy;
    }

    public boolean isAbcAnalyse() {
        return abcAnalyse;
    }

    public void setAbcAnalyse(boolean abc) {
        this.abcAnalyse = abc;
        if (abc) {
            gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_NEXT_ABC_ANALYSIS_PERIODE, String.valueOf(gameInfoPM.getCurrentState().getRoundNumber()));
        }
    }

    public boolean isZoneRdy() {
        String value = gameInfoPM.getCurrentState().getValue(DoloresConst.DOLORES_KEY_NEXT_ABC_ZONING_PERIODE);
        if (value != null) {
            int x = Integer.valueOf(value);
            if (x < gameInfoPM.getCurrentState().getRoundNumber()) {
                zoneRdy = true;
            }

        }
        return zoneRdy;
    }

    public void setZoneRdy(boolean zoneRdy) {
        this.zoneRdy = zoneRdy;
    }

    public void doAbcZoning() {
        gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_NEXT_ABC_ZONING_PERIODE, String.valueOf(gameInfoPM.getCurrentState().getRoundNumber()));
    }

    public String getPalletControlFactorWA() {
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState currentState = info.getCurrentState();
        return currentState.getValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WA);
    }

    public void setPalletControlFactorWA(String palletControlFactorWA) {
        this.palletControlFactorWA = palletControlFactorWA;
    }

    public int getGameInfoId() {
        return gameInfoId;
    }

    public void setGameInfoId(int gameInfoId) {
        this.gameInfoId = gameInfoId;
    }

    public String getStrategyIncoming() {
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState currentState = info.getCurrentState();

        return currentState.getValue(DoloresConst.DOLORES_KEY_STRATEGY_INCOMING_GOODS);
    }

    public void setStrategyIncoming(String strategyIncoming) {
        this.strategyIncoming = strategyIncoming;
    }

    public String getStrategyStorage() {
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState currentState = info.getCurrentState();
        return currentState.getValue(DoloresConst.DOLORES_KEY_STRATEGY_STORAGE);
    }

    public void setStrategyStorage(String strategyStorage) {
        this.strategyStorage = strategyStorage;
    }

    public String getStrategyOutgoing() {
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState currentState = info.getCurrentState();
        return currentState.getValue(DoloresConst.DOLORES_KEY_STRATEGY_OUTGOING_GOODS);
    }

    public void setStrategyOutgoing(String strategyOutgoing) {
        this.strategyOutgoing = strategyOutgoing;
    }

    public String getPalletControlFactor() {
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState currentState = info.getCurrentState();
        return currentState.getValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WE);
    }

    public void setPalletControlFactor(String palletControlFactor) {
        this.palletControlFactor = palletControlFactor;
    }

    public String getSecurityDeviceUsed() {
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState currentState = info.getCurrentState();
        return currentState.getValue(DoloresConst.DOLORES_KEY_UNIT_SECURITY_DEVICES_USED);
    }

    public void setSecurityDeviceUsed(String securityDeviceUsed) {
        this.securityDeviceUsed = securityDeviceUsed;
    }

    public void ajaxUpdateIncomingOrganisationPalletControl() {
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState currentState = info.getCurrentState();
        currentState.setValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WE, palletControlFactor);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.kontrollAkt")));
    }

    public void ajaxUpdateIncomingOrganisationSecurity() {
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState currentState = info.getCurrentState();
        currentState.setValue(DoloresConst.DOLORES_KEY_UNIT_SECURITY_DEVICES_USED, securityDeviceUsed);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.kontrollAkt1")));
    }

    public void ajaxUpdateStrategyIncoming() {
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState currentState = info.getCurrentState();
        currentState.setValue(DoloresConst.DOLORES_KEY_STRATEGY_INCOMING_GOODS, strategyIncoming);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.kontrollAkt2")));
       
        
    }

    public void ajaxUpdateStrategyStorage() {
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState currentState = info.getCurrentState();
        currentState.setValue(DoloresConst.DOLORES_KEY_STRATEGY_STORAGE, strategyStorage);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.kontrollAkt3")));
    }

    public void ajaxUpdateStrategyOutgoing() {
        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState currentState = info.getCurrentState();
        currentState.setValue(DoloresConst.DOLORES_KEY_STRATEGY_OUTGOING_GOODS, strategyOutgoing);
    
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.kontrollAkt4")));
    }

    

    public void ajaxUpdateOutgoingOranisation() {

        DoloresGameInfo info = gameInfoPM.getInfo();
        DoloresState currentState = info.getCurrentState();
        currentState.setValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WA, palletControlFactorWA);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.kontrollAkt5")));

    }

    public int getStorageFactorPercentIn() {
        return storageFactorPercentIn;
    }

    public void setStorageFactorPercentIn(int storageFactorPercentIn) {
        this.storageFactorPercentIn = storageFactorPercentIn;
        storageFactorPercentOut = 100 - storageFactorPercentIn;
    }

    public int getDebugStorage() {
        return debugStorage;
    }

    public void setDebugStorage(int value){
        this.debugStorage = value;
        double x = (double) value;
        double factor = x / 100;
        
        gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_STORAGE_FACTOR, String.valueOf(factor));
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Auslagerung auf "+value+"%"));
        
    }
    
    public void onSlideEnd(SlideEndEvent event) {
        int value = event.getValue();
        double x = (double) value;
        double factor = x / 100;

        gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_STORAGE_FACTOR, String.valueOf(factor));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.kontrollAkt6")));
    }

//    public int getStorageFactorPercentOut() {
//        double storage_factor = Double.valueOf(gameInfoPM.getCurrentState().getValue(DoloresConst.DOLORES_KEY_STORAGE_FACTOR));
//        storageFactorPercentOut = (int) (storage_factor * 100);
//        return storageFactorPercentOut;
//    }
    
    public int getStorageFactorPercentOut(){
        return storageFactorPercentOut;
    }

    public void setStorageFactorPercentOut(int storageFactorPercentOut) {
        this.storageFactorPercentOut = storageFactorPercentOut;
        storageFactorPercentIn = 100 - storageFactorPercentOut;
    }
}
