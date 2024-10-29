/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.calcs;

import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresState;
import flw.business.store.Message;
import flw.business.store.Storage;
import flw.business.util.DoloresConst;
import flw.business.util.Processes;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author alpl
 */
public class SaveRequiredDataCalculator extends AbstractCalculator {

    public SaveRequiredDataCalculator(List<AbstractCalculator> lCalculators) {
        super(lCalculators);
    }

    @Override
    public void calculate(DoloresGameInfo gameInfo) {
        DoloresState currentState = gameInfo.getCurrentState();

        ResourceBundle bundle = ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale());

        PostPalletThroughputCalculator poptc = (PostPalletThroughputCalculator) lCalculators.get(4);

        ConveyorCalculator conc = (ConveyorCalculator) lCalculators.get(1);
        EmployeeCalculator eC = (EmployeeCalculator) lCalculators.get(0);
        //PalletThroughputCalculator ptc = (PalletThroughputCalculator) lCalculators.get(3);
        PalletThroughputCalc ptc = (PalletThroughputCalc) lCalculators.get(3);

        for (String process : Processes.getInstance().getProcessAbbrevationsWithConveyors()) {

            currentState.setValue(new StringBuilder("conveyor_count_").append(process).toString(), String.valueOf(conc.getCount(process)));
            currentState.setValue(new StringBuilder("workload_conveyors_").append(process).toString(), String.valueOf(poptc.getConveyorWorkload(process)));

        }

        for (String process : Processes.getInstance().getProcessAbbrevations()) {

            currentState.setValue(new StringBuilder("employee_count_").append(process).toString(), String.valueOf(eC.getOverallEmployeeProcess(process)));
            currentState.setValue(new StringBuilder("pallets_transported_").append(process).toString(), String.valueOf(ptc.getTransportedPallets(process)));
            currentState.setValue(new StringBuilder("not_transported_pallets_").append(process).toString(), String.valueOf(ptc.getNotTransportedPallets(process)));
            currentState.setValue(new StringBuilder("workload_workers_").append(process).toString(), String.valueOf(poptc.getWorkerWorkload(process) * 100));
        }

        currentState.setValue(new StringBuilder("pallets_transported_la_in").toString(), String.valueOf(ptc.getTransportedPallets("la_in")));
        currentState.setValue(new StringBuilder("pallets_transported_la_out").toString(), String.valueOf(ptc.getTransportedPallets("la_out")));
        currentState.setValue(new StringBuilder("not_transported_pallets_la_in").toString(), String.valueOf(ptc.getNotTransportedPalletsLaIn()));
        currentState.setValue(new StringBuilder("not_transported_pallets_la_out").toString(), String.valueOf(ptc.getNotTransportedPalletsLaOut()));
        currentState.setValue("costs_repair", String.valueOf(conc.getOverallRepairCost()));
        currentState.setValue("repair_duration", String.valueOf(conc.getOverallRepairDuration()));

        int currentRoundNumber = currentState.getRoundNumber();

        for (String message : ptc.getMessages()) {

            if (message.contains("maxround")) {
                gameInfo.addMessage(new Message(bundle.getString("messages") + gameInfo.getCurrentState().getRoundNumber() + bundle.getString("messages1"), currentRoundNumber, gameInfo));
            } else if (message.contains("bankrupt")) {
                gameInfo.addMessage(new Message(bundle.getString("messages2"), currentRoundNumber, gameInfo));
            } else if (message.contains("financial_is")) {
                gameInfo.addMessage(new Message(bundle.getString("messages3"), currentRoundNumber, gameInfo));
            } else if (message.contains("financial_got")) {
                gameInfo.addMessage(new Message(bundle.getString("messages4"), currentRoundNumber, gameInfo));
            } else if (message.contains("gamestate_back_ok")) {
                gameInfo.addMessage(new Message(bundle.getString("messages5"), currentRoundNumber, gameInfo));
            }

            if (message.contains(DoloresConst.ORDER_NOTIFICATION_DELIVERED)) {
                String[] split = message.split("-");

                gameInfo.addMessage(new Message(bundle.getString("messages6") + " " + split[1] + " " + bundle.getString("messages7"), currentRoundNumber, gameInfo));
            } else if (message.contains(DoloresConst.ORDER_NOTIFICATION_HALF_DELIVERED)) {
                String[] split = message.split("-");
                gameInfo.addMessage(new Message(bundle.getString("messages6") + " " + split[1] + " " + bundle.getString("messages8") + split[2], currentRoundNumber, gameInfo));
            } else if (message.contains(DoloresConst.ORDER_NOTIFICATION_LATE_HALF_DELIVERED)) {
                String[] split = message.split("-");
                gameInfo.addMessage(new Message(bundle.getString("messages6") + " " + split[1] + " " + bundle.getString("messages8") + split[2] + bundle.getString("messages11") + split[3] + bundle.getString("messages10"), currentRoundNumber, gameInfo));
            } else if (message.contains(DoloresConst.ORDER_NOTIFICATION_LATE_DELIVERED)) {
                String[] split = message.split("-");
                gameInfo.addMessage(new Message(bundle.getString("messages6") + " " + split[1] + " " + bundle.getString("messages9") + split[2] + bundle.getString("messages10"), currentRoundNumber, gameInfo));
            }

        }

        for (String message : conc.getMessages()) {
            /*if(message.contains(DoloresConst.CONVEYOR_NOTIFICATION_DELIVER)){
             String[] split = message.split("?");
             gameInfo.addMessage(new Message("Ein in Periode " + split[3] +
             " bestelltes Fördermittel (Inventarnummer: " + split[2] +
             ") ist eingetroffen. Kosten:" +split[4], currentRoundNumber, gameInfo)); 
             }*/
            if (message.contains(DoloresConst.CONVEYOR_NOTIFICATION_BREAKDOWN)) {
                String[] split = message.split("\\?");
                gameInfo.addMessage(new Message(bundle.getString("messages12") + split[2] + bundle.getString("messages13"), currentRoundNumber, gameInfo));
            } else if (message.contains(DoloresConst.CONVEYOR_NOTIFICATION_GOT_SCRAP)) {
                String[] split = message.split("\\?");
                gameInfo.addMessage(new Message(bundle.getString("messages12") + split[2] + bundle.getString("messages14"), currentRoundNumber, gameInfo));
            } else if (message.contains(DoloresConst.CONVEYOR_NOTIFICATION_CRITICAL_CONDITION)) {

                String[] split = message.split("\\?");
                gameInfo.addMessage(new Message(bundle.getString("messages15") + split[1] + bundle.getString("messages16"), currentRoundNumber, gameInfo));
            }

        }

        for (String message : eC.getMessages()) {
            if (message.contains(DoloresConst.EMPLOYEE_NOTIFICATION_ILLEGAL_CONTRACT)) {
                String[] split = message.split("-");
                gameInfo.addMessage(new Message(bundle.getString("messages17") + " " + split[1] + " " + split[2] + " " + bundle.getString("messages18"), currentRoundNumber, gameInfo));
            } else if (message.contains(DoloresConst.EMPLOYEE_NOTIFICATION_MOTIVATION_PROBLEM)) {

                gameInfo.addMessage(new Message(bundle.getString("messages19"), currentRoundNumber, gameInfo));
            } else if (message.contains(DoloresConst.EMPLOYEE_NOTIFICATION_LEAVING_AFTER_ROUND)) {

                String[] split = message.split("-");
                gameInfo.addMessage(new Message(bundle.getString("messages17") + " " + split[1] + " " + split[2] + " " + bundle.getString("messages20"), currentRoundNumber, gameInfo));
            }

        }

        Storage storage = currentState.getStorage();
        int currentStoredPallets = 0;

        for (int artNr = 100101; artNr < 100105; artNr++) {

            currentStoredPallets += storage.getStoredPalletCount(artNr);

            if (storage.getStoredPalletCount(artNr) == 0) {
                gameInfo.addMessage(new Message(bundle.getString("messages21") + " " + artNr + " " + bundle.getString("messages22"), currentRoundNumber, gameInfo));
            }
        }

        //currentState.setValue(new StringBuilder("storage_cost").toString() , String.valueOf(currentStoredPallets*DoloresConst.STORAGE_COST_FACTOR));
    }

    @Override
    public void prepareNextRound(DoloresGameInfo gameInfo) {
        DoloresState currentState = gameInfo.getCurrentState();
        int currentRoundNumber = currentState.getRoundNumber();
        ResourceBundle bundle = ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        Storage storage = currentState.getStorage();
        int currentStoredPallets = 0;

        /*
         for (int artNr = 100101; artNr < 100105; artNr++) {

         currentStoredPallets += storage.getStoredPalletCount(artNr);

         if (storage.getStoredPalletCount(artNr) == 0) {
         gameInfo.addMessage(new Message(bundle.getString("messages21") +" "+ artNr +" "+ bundle.getString("messages22"), currentRoundNumber, gameInfo));
         }
         }*/
        ConveyorCalculator conc = (ConveyorCalculator) lCalculators.get(1);

        for (String message : conc.getMessages()) {
            if (message.contains(DoloresConst.CONVEYOR_NOTIFICATION_DELIVER)) {
                String[] split = message.split("=");
                gameInfo.addMessage(new Message(bundle.getString("messages37") + " " + split[3]
                        + " " + bundle.getString("messages38") + " " + split[2]
                        + " " + bundle.getString("messages39") + " " + split[4] + " €.", currentRoundNumber, gameInfo));
            }
        }

        EmployeeCalculator eC = (EmployeeCalculator) lCalculators.get(0);

        for (String message : eC.getMessages()) {
            if (message.contains(DoloresConst.EMPLOYEE_NOTIFICATION_JOINING_IN_ROUND)) {
                String[] split = message.split("-");
                gameInfo.addMessage(new Message(bundle.getString("messages23") + split[1] + " " + split[2] + " " + bundle.getString("messages24"), currentRoundNumber, gameInfo));

            }
            if (message.contains(DoloresConst.EMPLOYEE_NOTIFICATION_FP_DONE)) {
                String[] split = message.split("-");
                gameInfo.addMessage(new Message(bundle.getString("messages23") + split[1] + " " + split[2] + bundle.getString("messages25"), currentRoundNumber, gameInfo));
            }
            if (message.contains(DoloresConst.EMPLOYEE_NOTIFICATION_QM_DONE)) {
                String[] split = message.split("-");
                gameInfo.addMessage(new Message(bundle.getString("messages23") + split[1] + " " + split[2] + bundle.getString("messages26"), currentRoundNumber, gameInfo));
            }
            if (message.contains(DoloresConst.EMPLOYEE_NOTIFICATION_SECURITY_DONE)) {
                String[] split = message.split("-");
                gameInfo.addMessage(new Message(bundle.getString("messages23") + split[1] + " " + split[2] + bundle.getString("messages27"), currentRoundNumber, gameInfo));
            }
        }

        //PalletThroughputCalculator ptc = (PalletThroughputCalculator) lCalculators.get(3);
        PalletThroughputCalc ptc = (PalletThroughputCalc) lCalculators.get(3);
        for (String message : ptc.getMessages()) {
            if (message.contains("maxround")) {
                gameInfo.addMessage(new Message(bundle.getString("messages28") + gameInfo.getCurrentState().getRoundNumber() + bundle.getString("messages29"), currentRoundNumber, gameInfo));
            } else if (message.contains("bankrupt")) {
                gameInfo.addMessage(new Message(bundle.getString("messages30"), currentRoundNumber, gameInfo));
            } else if (message.contains("financial_is")) {
                gameInfo.addMessage(new Message(bundle.getString("messages31"), currentRoundNumber, gameInfo));
            } else if (message.contains("financial_got")) {
                gameInfo.addMessage(new Message(bundle.getString("messages32"), currentRoundNumber, gameInfo));
            } else if (message.contains("no_customers")) {
                gameInfo.addMessage(new Message(bundle.getString("messages33"), currentRoundNumber, gameInfo));
            }

            if (message.contains("customers_crit")) {
                gameInfo.addMessage(new Message(bundle.getString("messages34"), currentRoundNumber, gameInfo));
            } else if (message.contains("customers_leave")) {
                gameInfo.addMessage(new Message(bundle.getString("messages35"), currentRoundNumber, gameInfo));
            } else if (message.contains("gamestate_back_ok")) {
                gameInfo.addMessage(new Message(bundle.getString("messages36"), currentRoundNumber, gameInfo));
            }
        }

    }

    @Override
    public List<Object> getToUpdate() {
        return new ArrayList<Object>(); //To change body of generated methods, choose Tools | Templates.
    }
}
