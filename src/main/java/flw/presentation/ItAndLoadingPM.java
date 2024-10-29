/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
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
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author tilu
 *
 */
@ManagedBean(name = "ialPM")
@SessionScoped
public class ItAndLoadingPM implements Serializable {

    public ItAndLoadingPM() {
    }
    @ManagedProperty(value = "#{gameInfoPM}")
    private GameInfoPM gameInfoPM;
    @EJB
    private Service service;
    private String[] module;
    private int gameInfoId;
    private int itCost;
    private int paletteCount;
    private int loadingEquipIndex;
    private boolean itOn = false;
    private boolean basePossible = false;
    private boolean it1Possible = false;
    private boolean it2Possible = false;
    private boolean it3Possible = false;
    private boolean optOrderChecked;
    private boolean notificationReorder;
    private boolean notificationSafetyStock;
    private boolean statusReport;
    private boolean lookInStorage;

    public boolean isOptOrderChecked() {
        if(!itOn){
            optOrderChecked = false;
        }
        return optOrderChecked;
    }

    public void setOptOrderChecked(boolean optOrderChecked) {
        this.optOrderChecked = optOrderChecked;
    }
    
    public boolean isNotificationReorder() {
        if(!itOn){
            notificationReorder = false;
        }
        return notificationReorder;
    }

    public void setNotificationReorder(boolean notificationReorder) {
        this.notificationReorder = notificationReorder;
    }
    public boolean isNotificationSafetyStock() {
        if(!itOn){
            notificationSafetyStock = false;
        }
        return notificationSafetyStock;
    }

    public void setNotificationSafetyStock(boolean notificationSafetyStock) {
        this.notificationSafetyStock = notificationSafetyStock;
    }
    
    public boolean isStatusReport() {
        if(!itOn){
            statusReport = false;
        }
        return statusReport;
    }

    public void setStatusReport(boolean statusReport) {
        this.statusReport = statusReport;
    }
    
    public boolean isLookInStorage() {
        if(!itOn){
            lookInStorage = false;
        }
        return lookInStorage;
    }

    public void setLookInStorage(boolean lookInStorage) {
        this.lookInStorage = lookInStorage;
    }
    
    public void saveOptOrder() {
        if (optOrderChecked) {
            gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_ENABLED, "True");
        } else {
            gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_ENABLED, "False");
        }
        
        String summary = optOrderChecked ? ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.optOrder") : ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.optOrdernDeac");  
  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));  
        
    }

    public void saveNotificationReorder() {
        if (notificationReorder) {
            gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_ENABLED, "True");
        } else {
            gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_ENABLED, "False");
        }
        
        String summary = notificationReorder ? ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.reorder") : ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.reorderDeac");  
  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));  
    }
    
    public void saveNotificationSafetyStock() {
        if (notificationSafetyStock) {
            gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_ENABLED, "True");
        } else {
            gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_ENABLED, "False");
        }
        
        String summary = notificationSafetyStock ? ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.safety") : ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.safetyDeac");  
  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));  
    }
    
    public void saveStatusReport() {
        if (statusReport) {
             gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_ENABLED, "True");
        } else {
             gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_ENABLED, "False");
        }
        
        String summary = statusReport ? ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.report") : ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.reportDeac");  
  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));  
    }
    
    public void saveLookInStorage() {
        if (lookInStorage) {
            gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_ENABLED, "True");
        } else {
            gameInfoPM.getCurrentState().setValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_ENABLED, "False");
        }
        String summary = lookInStorage ? ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.lookSto") : ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.lookStoDeac");  
  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));  
    }
    
    public void saveLoadingIndex() {
        
        DoloresState ds = gameInfoPM.getCurrentState();

        ds.setValue(DoloresConst.DOLORES_KEY_LOADING_EQUIPMENT_COSTS, String.valueOf(loadingEquipIndex));
        switch(loadingEquipIndex){
            case 0: FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("java.loading70")));
                break;
            case 450: FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("java.loading80")));      
                break;
            case 800: FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("java.loading95")));   
                break;
        }
        
    }
    
    
    
    public int getPaletteCount() {
        DoloresState ds = gameInfoPM.getCurrentState();

        return ds.getPalletsFromStorage().size();
        //return 0;

    }

    public void setPaletteCount(int paletteCount) {
        this.paletteCount = paletteCount;
    }

    public int getLoadingEquipIndex() {
        return loadingEquipIndex;
    }

    public void setLoadingEquipIndex(int loadingEquipIndex) {
        this.loadingEquipIndex = loadingEquipIndex;
    }

    public String[] getModule() {



        DoloresState ds = gameInfoPM.getCurrentState();

        String[] tempModule = new String[5];
        int count = 0;

        if (Boolean.parseBoolean(ds.getValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_ENABLED))) {
            tempModule[count] = "1";
            count++;
        }

        if (Boolean.parseBoolean(ds.getValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_ENABLED))) {
            tempModule[count] = "2";
            count++;
        }
        if (Boolean.parseBoolean(ds.getValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_ENABLED))) {
            tempModule[count] = "3";
            count++;
        }
        if (Boolean.parseBoolean(ds.getValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_ENABLED))) {
            tempModule[count] = "4";
            count++;
        }
        if (Boolean.parseBoolean(ds.getValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_ENABLED))) {
            tempModule[count] = "5";
            count++;
        }
        module = tempModule;

        return module;
    }

    public void setModule(String[] module) {
        this.module = module;


        DoloresState ds = gameInfoPM.getInfo().getCurrentState();

        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_ENABLED, "False");

        for (int x = 0; x < module.length; x++) {

            if (Integer.valueOf(module[x]) == 1) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_ENABLED, "True");
            }
            if (Integer.valueOf(module[x]) == 2) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_ENABLED, "True");
            }
            if (Integer.valueOf(module[x]) == 3) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_ENABLED, "True");
            }
            if (Integer.valueOf(module[x]) == 4) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_ENABLED, "True");
            }
            if (Integer.valueOf(module[x]) == 5) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_ENABLED, "True");
            }


        }


    }

    public void onChangeModule() {



        DoloresState ds = gameInfoPM.getInfo().getCurrentState();

        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_ENABLED, "False");
        ds.setValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_ENABLED, "False");

        for (int x = 0; x < module.length; x++) {

            if (Integer.valueOf(module[x]) == 1) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_ENABLED, "True");
            }
            if (Integer.valueOf(module[x]) == 2) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_ENABLED, "True");
            }
            if (Integer.valueOf(module[x]) == 3) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_ENABLED, "True");
            }
            if (Integer.valueOf(module[x]) == 4) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_ENABLED, "True");
            }
            if (Integer.valueOf(module[x]) == 5) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_ENABLED, "True");
            }


        }
    }

    public void setGameInfoPM(GameInfoPM gameInfoPM) {
        this.gameInfoPM = gameInfoPM;
    }

    public void setModules() {

        DoloresState ds = gameInfoPM.getCurrentState();



        for (int x = 0; x < module.length; x++) {

            if (Integer.valueOf(module[x]) == 1) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_ENABLED, "True");
            }
            if (Integer.valueOf(module[x]) == 2) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_ENABLED, "True");
            }
            if (Integer.valueOf(module[x]) == 3) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_ENABLED, "True");
            }
            if (Integer.valueOf(module[x]) == 4) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_ENABLED, "True");
            }
            if (Integer.valueOf(module[x]) == 5) {
                ds.setValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_ENABLED, "True");
            }


        }
        service.updateState(ds);

    }

    public int getItCost() {

        DoloresState ds = gameInfoPM.getCurrentState();


        if (Integer.valueOf(ds.getValue(DoloresConst.DOLORES_KEY_BACKTO_BASIC)) == 0) {
            basePossible = true;
            it1Possible = true;
            it2Possible = true;
            
            if(Integer.valueOf(ds.getValue(DoloresConst.DOLORES_KEY_IT_COSTS)) == 0){
                basePossible = false;
            }
            
        } else {
            basePossible = false;
        }
        if (Integer.valueOf(ds.getValue(DoloresConst.DOLORES_KEY_BACK_TO_IT1)) == 0) {

            it1Possible = true;
            it2Possible = true;
             if(Integer.valueOf(ds.getValue(DoloresConst.DOLORES_KEY_IT_COSTS)) == 850){
                it1Possible = false;
            }
        } else {
            it1Possible = false;
        }

        if (Integer.valueOf(ds.getValue(DoloresConst.DOLORES_KEY_BACK_TO_IT2)) == 0) {
            it2Possible = true;
             if(Integer.valueOf(ds.getValue(DoloresConst.DOLORES_KEY_IT_COSTS)) == 1300){
                it2Possible = false;
            }
        } else {
            it2Possible = false;
        }
        
        if(Integer.valueOf(ds.getValue(DoloresConst.DOLORES_KEY_IT_COSTS)) == 1600){
                it3Possible = false;
            }
        else it3Possible=true;


        if (Integer.valueOf(ds.getValue(DoloresConst.DOLORES_KEY_IT_COSTS)) > 0) {
            itOn = true;
        } else {
            itOn = false;
        }

        return Integer.valueOf(ds.getValue(DoloresConst.DOLORES_KEY_IT_COSTS));

    }

    public boolean isBasePossible() {
        return basePossible;
    }

    public void setBasePossible(boolean basePossible) {
        this.basePossible = basePossible;
    }

    public boolean isIt1Possible() {
        return it1Possible;
    }

    public void setIt1Possible(boolean it1Possible) {
        this.it1Possible = it1Possible;
    }

    public boolean isIt2Possible() {
        return it2Possible;
    }

    public void setIt2Possible(boolean it2Possible) {
        this.it2Possible = it2Possible;
    }

    public boolean isIt3Possible() {
        return it3Possible;
    }

    public void setIt3Possible(boolean it3Possible) {
        this.it3Possible = it3Possible;
    }
    
    

    public void setItCost(int itCost) {
        this.itCost = itCost;
        DoloresState ds = gameInfoPM.getCurrentState();
        if (itCost == 0) {

            ds.setValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_ENABLED, "False");

            ds.setValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_ENABLED, "False");

            ds.setValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_ENABLED, "False");

            ds.setValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_ENABLED, "False");
            ds.setValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_ENABLED, "False");

            itOn = false;
        } else {
            itOn = true;
        }


        ds.setValue(DoloresConst.DOLORES_KEY_IT_COSTS, String.valueOf(itCost));

        if (itCost == 1600) {
            ds.setValue(DoloresConst.DOLORES_KEY_BACKTO_BASIC, "7");
            ds.setValue(DoloresConst.DOLORES_KEY_BACK_TO_IT1, "5");
            ds.setValue(DoloresConst.DOLORES_KEY_BACK_TO_IT2, "3");
        } else if (itCost == 1300) {
            ds.setValue(DoloresConst.DOLORES_KEY_BACKTO_BASIC, "7");
            ds.setValue(DoloresConst.DOLORES_KEY_BACK_TO_IT1, "5");
            ds.setValue(DoloresConst.DOLORES_KEY_BACK_TO_IT2, "0");
        } else if (itCost == 850) {
            ds.setValue(DoloresConst.DOLORES_KEY_BACKTO_BASIC, "7");
            ds.setValue(DoloresConst.DOLORES_KEY_BACK_TO_IT1, "0");
            ds.setValue(DoloresConst.DOLORES_KEY_BACK_TO_IT2, "0");
        } else {
            ds.setValue(DoloresConst.DOLORES_KEY_BACKTO_BASIC, "0");
            ds.setValue(DoloresConst.DOLORES_KEY_BACK_TO_IT1, "0");
            ds.setValue(DoloresConst.DOLORES_KEY_BACK_TO_IT2, "0");
        }

    }

    

    public boolean isItOn() {
        return itOn;
    }

    public void setItOn(boolean itOn) {
        this.itOn = itOn;
    }
}
