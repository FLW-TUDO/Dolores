/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.calcs;

import flw.business.business.Service;
import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresState;
import flw.business.statistics.ArticleAndOrderStatistics;
import flw.business.statistics.PalletStatistics;
import flw.business.store.ArticleDynamics;
import flw.business.store.CustomerJobComparatorByPeriode;
import flw.business.store.CustomerJobDynamics;
import flw.business.store.OrderDynamics;
import flw.business.store.PalletsInProgress;
import flw.business.store.PalletsInProgressComparatorByDeliveryDate;
import flw.business.store.PalletsInProgressComparatorByDemandDate;
import flw.business.store.StockGround;
import flw.business.store.Storage;
import flw.business.util.DoloresConst;
import flw.business.util.Processes;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tilu
 */
public class PalletThroughputCalc extends AbstractCalculator {

    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CapacityCalculator.class);
    private final Map<String, Double> mDoubles = new TreeMap<>();
    private final Map<String, Integer> mInts = new TreeMap<>();
    private final Map<String, Integer> jobStatistics = new TreeMap<>();
    private List<PalletsInProgress> palletsInProgress;
    private ConveyorCalculator conveyors = null;
    private EmployeeCalculator emloyees = null;
    private CapacityCalculator capacity = null;
    private final List<CustomerJobDynamics> finishedJobs = new ArrayList<CustomerJobDynamics>();
    private final List<PalletsInProgress> palletsToPersist = new ArrayList<>();
    private int storageCounter;
    int newPallets;
    private List<String> messages = new ArrayList<>();
    boolean isCapacityFullyUsedLaOut;
    boolean isCapacityFullyUsedLaIn;
    private PalletStatistics palStat;
    private ArticleAndOrderStatistics aaos;
    
    @EJB
    private Service service;

    public PalletThroughputCalc(List<AbstractCalculator> lCalculators, Service service) {
        super(lCalculators);
        this.service = service;
        palStat = new PalletStatistics();
        aaos = new ArticleAndOrderStatistics();

        for (String process : Processes.getInstance().getProcessAbbrevations()) {
            mInts.put(new StringBuilder("pallets_transported_").append(process).toString(), 0);
            mInts.put(new StringBuilder("not_transported_pallets_").append(process).toString(), 0);
        }
        for (int i = 0; i < DoloresConst.ERRORS.length; i++) {
            mInts.put(new StringBuilder("pallets_with_error_").append(i).toString(), 0);
        }
        mInts.put("pallets_transported_la_in", 0);
        mInts.put("pallets_transported_la_out", 0);
        mInts.put("not_transported_pallets_la_in", 0);
        mInts.put("not_transported_pallets_la_out", 0);
        mInts.put("new_arrived_pallet_count_overall", 0);
        mInts.put("new_arrived_pallet_count_100101", 0);
        mInts.put("new_arrived_pallet_count_100102", 0);
        mInts.put("new_arrived_pallet_count_100103", 0);
        mInts.put("new_arrived_pallet_count_100104", 0);

        jobStatistics.put(new StringBuilder("job_pallet_count_overall").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_overall_100101").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_overall_100102").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_overall_100103").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_overall_100104").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_new_overall").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_new_overall_100101").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_new_overall_100102").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_new_overall_100103").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_new_overall_100104").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_open_overall").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_open_100101").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_open_100102").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_open_100103").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_open_100104").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_late_overall").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_late_100101").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_late_100102").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_late_100103").toString(), 0);
        jobStatistics.put(new StringBuilder("job_pallet_count_late_100104").toString(), 0);

        for (int i = 0; i <= 6; i++) {
            mInts.put(new StringBuilder("pallets_with_error_").append(i).toString(), 0);
        }
    }

    public PalletStatistics getPalStat() {
        return palStat;
    }

    public void setPalStat(PalletStatistics palStat) {
        this.palStat = palStat;
    }

    /**
     *
     * @return The Article and Order Statistics
     */
    public ArticleAndOrderStatistics getAaos() {
        return aaos;
    }

    public void setAaos(ArticleAndOrderStatistics aaos) {
        this.aaos = aaos;
    }

    @Override
    public void calculate(DoloresGameInfo gameInfo) {
       List<CustomerJobDynamics> jobDynamics = gameInfo.getCurrentState().getJobDynamics();

        for (CustomerJobDynamics cjd : jobDynamics) {
            if (cjd.getOrderPeriode() <= gameInfo.getCurrentState().getRoundNumber()) {
                jobStatistics.put(new StringBuilder("job_pallet_count_overall").toString(), (jobStatistics.get("job_pallet_count_overall") + cjd.getPalette_amount()));

                int artNr = cjd.getArticleNumber();
                jobStatistics.put(new StringBuilder("job_pallet_count_overall_").append(artNr).toString(), (jobStatistics.get(new StringBuilder("job_pallet_count_overall_").append(artNr).toString()) + cjd.getPalette_amount()));
            }

            if (cjd.getOrderPeriode() == gameInfo.getCurrentState().getRoundNumber()) {
                jobStatistics.put(new StringBuilder("job_pallet_count_new_overall").toString(), (jobStatistics.get("job_pallet_count_new_overall") + cjd.getPalette_amount()));

                int artNr = cjd.getArticleNumber();
                jobStatistics.put(new StringBuilder("job_pallet_count_new_overall_").append(artNr).toString(), (jobStatistics.get(new StringBuilder("job_pallet_count_new_overall_").append(artNr).toString()) + cjd.getPalette_amount()));
            }

            // neue kunden aufträge zählen
        }

        gameInfo.getCurrentState().setValue(new StringBuilder("late_finished_jobs_pallet_count").toString(), "0");
        gameInfo.getCurrentState().setValue(new StringBuilder("late_finished_jobs_pallet_count_100101").toString(), "0");
        gameInfo.getCurrentState().setValue(new StringBuilder("late_finished_jobs_pallet_count_100102").toString(), "0");
        gameInfo.getCurrentState().setValue(new StringBuilder("late_finished_jobs_pallet_count_100103").toString(), "0");
        gameInfo.getCurrentState().setValue(new StringBuilder("late_finished_jobs_pallet_count_100104").toString(), "0");

        palStat.setRoundNumber(gameInfo.getCurrentState().getRoundNumber());

        newPallets = 0;
        DoloresState currentState = gameInfo.getCurrentState();

        //geht alle
        this.palletsInProgress = new ArrayList<PalletsInProgress>(currentState.getPalletsFromStorage());
        this.checkStateValueInitialization(currentState);
        int aktround = Integer.parseInt(currentState.getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER));
        for (AbstractCalculator abstractCalculator : lCalculators) {
            if (abstractCalculator instanceof ConveyorCalculator) {
                conveyors = (ConveyorCalculator) abstractCalculator;
            } else if (abstractCalculator instanceof EmployeeCalculator) {
                emloyees = (EmployeeCalculator) abstractCalculator;
            } else if (abstractCalculator instanceof CapacityCalculator) {
                capacity = (CapacityCalculator) abstractCalculator;
            }
        }
        int currentLoadingEquipmentCosts = Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_LOADING_EQUIPMENT_COSTS));
        double currentLoadingEquipmentFactor = -1;
        int currentLoadingEquipmentCondition = -1;
        double currentLoadingEquipmentCrashChance = -1d;
        for (int i = 0; i < DoloresConst.LOADING_EQUIPMENT_COSTS.length; i++) {
            if (currentLoadingEquipmentCosts == DoloresConst.LOADING_EQUIPMENT_COSTS[i]) {
                currentLoadingEquipmentFactor = DoloresConst.LOADING_EQUIPMENT_FACTOR[i];
                currentLoadingEquipmentCondition = DoloresConst.LOADING_EQUIPMENT_CONDITION[i];
                currentLoadingEquipmentCrashChance = DoloresConst.LOADING_EQUIPMENT_CRASH_FACTOR[i];
                i = DoloresConst.LOADING_EQUIPMENT_COSTS.length;
            }
        }

        int currentITCosts = Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_IT_COSTS));
        double currentITFactor = -1d;
        for (int i = 0; i < DoloresConst.IT_COSTS.length; i++) {
            if (currentITCosts == DoloresConst.IT_COSTS[i]) {
                currentITFactor = DoloresConst.IT_FACTOR[i];
                i = DoloresConst.IT_COSTS.length;
            }
        }

        for (String process : Processes.getInstance().getProcessAbbrevationsWithConveyors()) {
            mDoubles.put(new StringBuilder("true_speed_").append(process).append("_wusd").toString(), conveyors.getAverageSpeed(process) * DoloresConst.SPEED_FACTOR_WITH_UNIT_SECURITY_DEVICES);
            mDoubles.put(new StringBuilder("true_speed_").append(process).append("_wousd").toString(), conveyors.getAverageSpeed(process) * DoloresConst.SPEED_FACTOR_WITHOUT_UNIT_SECURITY_DEVICES);
        }

        int trueTakeUpReleaseTime = (int) Math.round(DoloresConst.TIME_TAKEUP_RELEASE * (2d - currentLoadingEquipmentFactor));

        //Calculate Demage Probabilities
        mDoubles.put("prob_damage", (DoloresConst.ERROR_DAMAGED / DoloresConst.ERROR_SUM) * (emloyees.getAverageFailureRate(Processes.getInstance().getProcessAbbrevations()[1]) * (1 - Double.parseDouble(currentState.getValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WE)))));
        mDoubles.put("prob_wrong_delivery", (DoloresConst.ERROR_WRONG_DELIVERED / DoloresConst.ERROR_SUM) * (emloyees.getAverageFailureRate(Processes.getInstance().getProcessAbbrevations()[1]) * (1 - Double.parseDouble(currentState.getValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WE)))));
        mDoubles.put("prob_wrong_retrieval", (DoloresConst.ERROR_WRONG_RETRIEVEL / DoloresConst.ERROR_SUM) * (emloyees.getAverageFailureRate(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 2]) * (1 - Double.parseDouble(currentState.getValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WA)))));
        mDoubles.put("prob_transport_demage_en_with_les", (DoloresConst.ERROR_TRANSPORT_DAMAGE_EN / DoloresConst.ERROR_SUM)
                * (currentLoadingEquipmentCrashChance * DoloresConst.GLOBAL_CRASH_FACTOR_WITH_LOADING_EQUIPMENT
                + DoloresConst.PROBABILITY_CRASH_WITH_UNIT_SAFETY_DEVICES * DoloresConst.GLOBAL_CRASH_FACTOR_WITH_UNIT_SECURITY_DEVICES
                + emloyees.getAverageCrashChance(Processes.getInstance().getProcessAbbrevations()[0]) * DoloresConst.GLOBAL_CRASH_FACTOR_EMPLOYEE));

        mDoubles.put("prob_transport_demage_la_with_les", (DoloresConst.ERROR_TRANSPORT_DAMAGE_LA / DoloresConst.ERROR_SUM) * (currentLoadingEquipmentCrashChance * DoloresConst.GLOBAL_CRASH_FACTOR_WITH_LOADING_EQUIPMENT + DoloresConst.PROBABILITY_CRASH_WITH_UNIT_SAFETY_DEVICES * DoloresConst.GLOBAL_CRASH_FACTOR_WITH_UNIT_SECURITY_DEVICES + emloyees.getAverageCrashChance(Processes.getInstance().getProcessAbbrevations()[2]) * DoloresConst.GLOBAL_CRASH_FACTOR_EMPLOYEE));
        mDoubles.put("prob_transport_demage_ve_with_les", (DoloresConst.ERROR_TRANSPORT_DAMAGE_VE / DoloresConst.ERROR_SUM) * (currentLoadingEquipmentCrashChance * DoloresConst.GLOBAL_CRASH_FACTOR_WITH_LOADING_EQUIPMENT + DoloresConst.PROBABILITY_CRASH_WITH_UNIT_SAFETY_DEVICES * DoloresConst.GLOBAL_CRASH_FACTOR_WITH_UNIT_SECURITY_DEVICES + emloyees.getAverageCrashChance(Processes.getInstance().getProcessAbbrevations()[4]) * DoloresConst.GLOBAL_CRASH_FACTOR_EMPLOYEE));
        mDoubles.put("prob_transport_demage_en_without_les", (DoloresConst.ERROR_TRANSPORT_DAMAGE_EN / DoloresConst.ERROR_SUM) * (currentLoadingEquipmentCrashChance * DoloresConst.GLOBAL_CRASH_FACTOR_WITH_LOADING_EQUIPMENT + DoloresConst.PROBABILITY_CRASH_WITHOUT_UNIT_SAFETY_DEVICES * DoloresConst.GLOBAL_CRASH_FACTOR_WITH_UNIT_SECURITY_DEVICES + emloyees.getAverageCrashChance(Processes.getInstance().getProcessAbbrevations()[0]) * DoloresConst.GLOBAL_CRASH_FACTOR_EMPLOYEE));
        mDoubles.put("prob_transport_demage_la_without_les", (DoloresConst.ERROR_TRANSPORT_DAMAGE_LA / DoloresConst.ERROR_SUM) * (currentLoadingEquipmentCrashChance * DoloresConst.GLOBAL_CRASH_FACTOR_WITH_LOADING_EQUIPMENT + DoloresConst.PROBABILITY_CRASH_WITHOUT_UNIT_SAFETY_DEVICES * DoloresConst.GLOBAL_CRASH_FACTOR_WITH_UNIT_SECURITY_DEVICES + emloyees.getAverageCrashChance(Processes.getInstance().getProcessAbbrevations()[2]) * DoloresConst.GLOBAL_CRASH_FACTOR_EMPLOYEE));
        mDoubles.put("prob_transport_demage_ve_without_les", (DoloresConst.ERROR_TRANSPORT_DAMAGE_VE / DoloresConst.ERROR_SUM) * (currentLoadingEquipmentCrashChance * DoloresConst.GLOBAL_CRASH_FACTOR_WITH_LOADING_EQUIPMENT + DoloresConst.PROBABILITY_CRASH_WITHOUT_UNIT_SAFETY_DEVICES * DoloresConst.GLOBAL_CRASH_FACTOR_WITH_UNIT_SECURITY_DEVICES + emloyees.getAverageCrashChance(Processes.getInstance().getProcessAbbrevations()[4]) * DoloresConst.GLOBAL_CRASH_FACTOR_EMPLOYEE));

        double[] accumulated_probability_with_unit_security_devices = new double[7];
        accumulated_probability_with_unit_security_devices[0] = mDoubles.get("prob_damage");
        accumulated_probability_with_unit_security_devices[1] = accumulated_probability_with_unit_security_devices[0] + mDoubles.get("prob_wrong_delivery");
        accumulated_probability_with_unit_security_devices[2] = accumulated_probability_with_unit_security_devices[1] + mDoubles.get("prob_wrong_retrieval");
        accumulated_probability_with_unit_security_devices[3] = accumulated_probability_with_unit_security_devices[2] + mDoubles.get("prob_transport_demage_en_with_les");
        accumulated_probability_with_unit_security_devices[4] = accumulated_probability_with_unit_security_devices[3] + mDoubles.get("prob_transport_demage_la_with_les");
        accumulated_probability_with_unit_security_devices[5] = accumulated_probability_with_unit_security_devices[4] + mDoubles.get("prob_transport_demage_ve_with_les");
        accumulated_probability_with_unit_security_devices[6] = 1d;

        double[] accumulated_probability_without_unit_security_devices = new double[7];
        accumulated_probability_without_unit_security_devices[0] = mDoubles.get("prob_damage");
        accumulated_probability_without_unit_security_devices[1] = accumulated_probability_without_unit_security_devices[0] + mDoubles.get("prob_wrong_delivery");
        accumulated_probability_without_unit_security_devices[2] = accumulated_probability_without_unit_security_devices[1] + mDoubles.get("prob_wrong_retrieval");
        accumulated_probability_without_unit_security_devices[3] = accumulated_probability_without_unit_security_devices[2] + mDoubles.get("prob_transport_demage_en_without_les");
        accumulated_probability_without_unit_security_devices[4] = accumulated_probability_without_unit_security_devices[3] + mDoubles.get("prob_transport_demage_la_without_les");
        accumulated_probability_without_unit_security_devices[5] = accumulated_probability_without_unit_security_devices[4] + mDoubles.get("prob_transport_demage_ve_without_les");
        accumulated_probability_without_unit_security_devices[6] = 1d;

        //mInts.put(new StringBuilder("pallets_transported_").append(Processes.getInstance().getProcessAbbrevations()[2]).append("_in").toString(), 0);
        //are unitSafetyDevices used?
        boolean usedUnitScurityDevices = Boolean.parseBoolean(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_UNIT_SECURITY_DEVICES_USED));
        double[] usedProbability = (usedUnitScurityDevices ? accumulated_probability_with_unit_security_devices : accumulated_probability_without_unit_security_devices);

        Queue<OrderDynamics> deliveredOrders = new LinkedList<>();

        //Process delivered orders of player in this round
        for (OrderDynamics orderDynamics : currentState.getOpenOrderDynamics()) {

            //Change Paramteters in Method (berechneLieferabweichungen)
            //
            //this.berechneLieferabweichungen(orderDynamics);
            if (orderDynamics.getDeliveryPeriod() == aktround && (!orderDynamics.isComplete())) {

                String key = new StringBuilder("order_costs_").append(orderDynamics.getArticleNumber()).toString();
                if (!mInts.containsKey(key)) {
                    mInts.put(key, 0);
                }
                mInts.put(key, mInts.get(key) + ((orderDynamics.getRealPurchasePrice() + orderDynamics.getDeliveryCosts()) * orderDynamics.getDeliveryAmount()));
                key = new StringBuilder("order_fix_costs_").append(orderDynamics.getArticleNumber()).toString();
                if (!mInts.containsKey(key)) {
                    mInts.put(key, 0);
                }
                mInts.put(key, mInts.get(key) + (orderDynamics.getFixCosts()));

                if (orderDynamics.getOrderAmount() == orderDynamics.getDeliveryAmount() && orderDynamics.getDeliveryPeriod() > orderDynamics.getDeliveryWish()) {
                    //int indexOfArticle = gameInfo.getCurrentState().getArticleDynamics().indexOf(orderDynamics.getArticleDynamics());
                    //ArticleDynamics get = gameInfo.getCurrentState().getArticleDynamics().get(indexOfArticle);

                    //TODO Late deliver chance + part deliver chance
                    messages.add(new StringBuilder(DoloresConst.ORDER_NOTIFICATION_LATE_DELIVERED).append("-").append(orderDynamics.getArticleNumber()).append("-").append((orderDynamics.getDeliveryPeriod() - orderDynamics.getDeliveryWish())).toString());
                }
                if (orderDynamics.getOrderAmount() == orderDynamics.getDeliveryAmount() && orderDynamics.getDeliveryPeriod() == orderDynamics.getDeliveryWish()) {

                    messages.add(new StringBuilder(DoloresConst.ORDER_NOTIFICATION_DELIVERED).append("-").append(orderDynamics.getArticleNumber()).toString());

                }
                if (orderDynamics.getOrderAmount() != orderDynamics.getDeliveryAmount() && orderDynamics.getDeliveryPeriod() == orderDynamics.getDeliveryWish()) {
                    messages.add(new StringBuilder(DoloresConst.ORDER_NOTIFICATION_HALF_DELIVERED).append("-").append(orderDynamics.getArticleNumber()).append("-").append((orderDynamics.getOrderAmount() - orderDynamics.getDeliveryAmount())).toString());

                }
                if (orderDynamics.getOrderAmount() != orderDynamics.getDeliveryAmount() && orderDynamics.getDeliveryPeriod() > orderDynamics.getDeliveryWish()) {
                    messages.add(new StringBuilder(DoloresConst.ORDER_NOTIFICATION_LATE_HALF_DELIVERED).append("-").append(orderDynamics.getArticleNumber()).append("-").append((orderDynamics.getOrderAmount() - orderDynamics.getDeliveryAmount())).append("-").append((orderDynamics.getDeliveryPeriod() - orderDynamics.getDeliveryWish())).toString());
                }

                mInts.put(new StringBuilder("new_arrived_pallet_count_overall").toString(), mInts.get("new_arrived_pallet_count_overall") + orderDynamics.getDeliveryAmount());
                mInts.put(new StringBuilder("new_arrived_pallet_count_").append(orderDynamics.getArticleNumber()).toString(), mInts.get(new StringBuilder("new_arrived_pallet_count_").append(orderDynamics.getArticleNumber()).toString()) + orderDynamics.getDeliveryAmount());
                //System.out.println("Delivered Order");

                int currentCost = orderDynamics.getFixCosts() + orderDynamics.getOrderAmount() * (orderDynamics.getRealPurchasePrice() + orderDynamics.getDeliveryCosts());
                orderDynamics.getArticleDynamics().setOrderCosts(orderDynamics.getArticleDynamics().getOrderCosts() + currentCost);
                //Generate Pallets for Order
                for (int i = 0; i < orderDynamics.getDeliveryAmount(); i++) {
                    //Create random for Error
                    double random = Math.random();
                    int error;
                    for (error = 0; error < usedProbability.length; error++) {
                        if (random < usedProbability[error]) {
                            break;
                        }
                    }

                    //orderDynamics
                    //PalletsInProgress palletsInProgress = new PalletsInProgress(usedUnitScurityDevices, error);
                    PalletsInProgress pallet = new PalletsInProgress(usedUnitScurityDevices, error);

                    pallet.setCurrentProcess(0);
                    pallet.setDemandRound(aktround);
                    pallet.setOrderDynamics(orderDynamics);
                    pallet.setState(currentState);
                    //pallet.setState(currentState);

                    pallet.setOrderno(orderDynamics.getOrderno());

                    //palletsToPersist.add(pallet);
                    palletsInProgress.add(pallet);
                    currentState.getStorage().getPalletsNotInStorage().add(pallet);

                    orderDynamics.getArticleDynamics().increasePalletCount(Processes.getInstance().getProcessAbbrevations()[0]);
                }
                orderDynamics.getArticleDynamics().increaseIncomingPallets(orderDynamics.getDeliveryAmount());

                deliveredOrders.add(orderDynamics);
            }
        }
        //Delete Delivered Orders from OpenDelivery-List
        if (!deliveredOrders.isEmpty()) {

            OrderDynamics orderDynamics;
            while ((orderDynamics = deliveredOrders.poll()) != null) {
                //currentState.removeOrderDynamics(orderDynamics);
                orderDynamics.setComplete(true);
            }
        }

        Storage storage = gameInfo.getCurrentState().getStorage();

        this.startPaletteThroughputEnWv(currentITFactor, trueTakeUpReleaseTime, storage);
        this.startPaletteThroughputWvLa(gameInfo, currentState, storage);
        this.startPaletteThroughputStorePallets(currentITFactor, trueTakeUpReleaseTime, storage, gameInfo.getCurrentState());
        //Processing delivered Pallets finished, now start processing of outgoing pallets
        this.startPaletteThroughputUnstockPallets(gameInfo, currentITFactor, trueTakeUpReleaseTime, aktround, storage, gameInfo.getCurrentState());
        //this.useSpareTimeLaInORLaOut(gameInfo, currentITFactor, trueTakeUpReleaseTime, aktround, storage, currentState);
        this.startPaletteThroughputWaVe(currentState);
        this.startPalletThroughputVeTruck(gameInfo, currentState, aktround, currentITFactor, trueTakeUpReleaseTime);
        this.calculateArticleValues(gameInfo);

    }

    private void checkStateValueInitialization(DoloresState currentState) {

        for (ArticleDynamics artDyn : currentState.getArticleDynamics()) {
            artDyn.setOrderCosts(0);
        }

        if (!currentState.containsValue(DoloresConst.DOLORES_KEY_LOADING_EQUIPMENT_COSTS)) {
            currentState.setValue(DoloresConst.DOLORES_KEY_LOADING_EQUIPMENT_COSTS, "0");
        } else {
            try {
                int val = Integer.parseInt(currentState.getValue(DoloresConst.DOLORES_KEY_LOADING_EQUIPMENT_COSTS));
            } catch (NumberFormatException ex) {
                currentState.setValue(DoloresConst.DOLORES_KEY_LOADING_EQUIPMENT_COSTS, "0");
            }
        }

        if (!currentState.containsValue(DoloresConst.DOLORES_KEY_IT_COSTS)) {
            currentState.setValue(DoloresConst.DOLORES_KEY_IT_COSTS, "0");
        } else {
            try {
                int val = Integer.parseInt(currentState.getValue(DoloresConst.DOLORES_KEY_IT_COSTS));
            } catch (NumberFormatException ex) {
                currentState.setValue(DoloresConst.DOLORES_KEY_IT_COSTS, "0");
            }
        }

        if (!currentState.containsValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WE)) {
            currentState.setValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WE, "1.0");
        } else {
            try {
                double val = Double.parseDouble(currentState.getValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WE));
            } catch (NumberFormatException ex) {
                currentState.setValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WE, "1.0");
            }
        }

        if (!currentState.containsValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WA)) {
            currentState.setValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WA, "1.0");
        } else {
            try {
                double val = Double.parseDouble(currentState.getValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WA));
            } catch (NumberFormatException ex) {
                currentState.setValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WA, "1.0");
            }
        }
        currentState.setValue(DoloresConst.DOLORES_KEY_ACCURATE_DELIVERED_PALLETS, "0");
        currentState.setValue(DoloresConst.DOLORES_KEY_ACCURATE_FINISHED_CUSTOMER_JOBS, "0");
        currentState.setValue(DoloresConst.DOLORES_KEY_LATE_FINISHED_CUSTOMER_JOBS, "0");
    }

    private void startPaletteThroughputEnWv(double currentITFactor, int trueTakeUpReleaseTime, Storage storage) {
        //EN -> WV
        //Sort list by DeliveryDate (oldest first) and Process 0 to beginning of the List
        Collections.sort(storage.getPalletsNotInStorage(), new PalletsInProgressComparatorByDeliveryDate(0));

        boolean capacityIsFullyUsed = false;
        for (PalletsInProgress pallet : storage.getPalletsNotInStorage()) {
            double time;

            //* 3 = error en
            //Crash -> Time Crash auf die zeit draufrechnen
            if (pallet.getError() == 3) {
                time = calculateGameTime(DoloresConst.WAY_EN_WV, conveyors.getAverageSpeed(Processes.getInstance().getProcessAbbrevations()[0]), currentITFactor, trueTakeUpReleaseTime, DoloresConst.LIFT_LAYER_DURATION[0]) + DoloresConst.TIME_CRASH;
                if (time == -1) {
                    capacityIsFullyUsed = true;
                }
            } else {
                time = calculateGameTime(DoloresConst.WAY_EN_WV, conveyors.getAverageSpeed(Processes.getInstance().getProcessAbbrevations()[0]), currentITFactor, trueTakeUpReleaseTime, DoloresConst.LIFT_LAYER_DURATION[0]);
                if (time == -1) {
                    capacityIsFullyUsed = true;
                }
            }
            if (capacity.getCapacity(Processes.getInstance().getProcessAbbrevations()[0]) < time) {

                capacityIsFullyUsed = true;

            }
            if (capacityIsFullyUsed && pallet.getCurrentProcess() == 0) {
                String key = new StringBuilder("not_transported_pallets_").append(Processes.getInstance().getProcessAbbrevations()[0]).toString();
                mInts.put(key, mInts.get(key) + 1);

            } else if (pallet.getCurrentProcess() == 0) {
                //Transport pallet, because the article is currently not Stored, so give it a high priority
                pallet.setCurrentProcess(1);
                pallet.getOrderDynamics().getArticleDynamics().increasePalletCount(Processes.getInstance().getProcessAbbrevations()[1]);
                pallet.getOrderDynamics().getArticleDynamics().decreasePalletCount(Processes.getInstance().getProcessAbbrevations()[0]);
                if (pallet.getError() == 3) {
                    //capacity.decreaseCapacity(Processes.getInstance().getProcessAbbrevations()[0], calculateGameTime(DoloresConst.WAY_EN_WV, conveyors.getAverageSpeed(Processes.getInstance().getProcessAbbrevations()[0]), currentITFactor, trueTakeUpReleaseTime, DoloresConst.LIFT_LAYER_DURATION[0]) + DoloresConst.TIME_CRASH);
                    String key = new StringBuilder("pallets_with_error_").append(3).toString();
                    mInts.put(key, mInts.get(key) + 1);
                }
                capacity.decreaseCapacity(Processes.getInstance().getProcessAbbrevations()[0], time);

                String key = new StringBuilder("pallets_transported_").append(Processes.getInstance().getProcessAbbrevations()[0]).toString();
                mInts.put(key, mInts.get(key) + 1);
            }
        }
    }

    private void startPaletteThroughputWvLa(DoloresGameInfo gameInfo, DoloresState currentState, Storage storage) {
        //WV -> LA
        //Sort list by DeliveryDate (oldest first) and Process 0 to beginning of the List
        Collections.sort(storage.getPalletsNotInStorage(), new PalletsInProgressComparatorByDeliveryDate(1));

        int controlDistance = (Double.parseDouble(currentState.getValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WE)) == 0 ? -1 : (int) Math.round(1 / Double.parseDouble(currentState.getValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WE))));
        //int controlCounter = controlDistance;
        int controlCounter;
        double costs_usd = 0;
        double costs_usd_per_pallet;
        costs_usd_per_pallet = DoloresConst.COSTS_UNIT_SECURITY_DEVICES_PER_PALLET;

        controlCounter = controlDistance;

        boolean capacityIsFullyUsed = false;
        for (PalletsInProgress pallet : storage.getPalletsNotInStorage()) {

            int time = DoloresConst.PALLET_CONTROL_TIME_STATIC_WE;

            if (controlCounter == 1) {
                time += DoloresConst.PALLET_CONTROL_TIME_DYNAMIC_WE;
            }
            if (capacity.getCapacity(Processes.getInstance().getProcessAbbrevations()[1]) < time) {
                capacityIsFullyUsed = true;
            }

            if (capacityIsFullyUsed && pallet.getCurrentProcess() == 1) {
                String key = new StringBuilder("not_transported_pallets_").append(Processes.getInstance().getProcessAbbrevations()[1]).toString();
                mInts.put(key, mInts.get(key) + 1);
            } else if (pallet.getCurrentProcess() == 1) {
                //Transport pallet, because the article is currently not Stored, so give it a high priority

                if (Boolean.parseBoolean(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_UNIT_SECURITY_DEVICES_USED))) {
                    time += DoloresConst.PALLET_UNIT_SECURITY_DEVICES_TIME;
                    costs_usd += costs_usd_per_pallet;
                }

                pallet.setCurrentProcess(2);
                pallet.getOrderDynamics().getArticleDynamics().increasePalletCount(Processes.getInstance().getProcessAbbrevations()[2]);
                pallet.getOrderDynamics().getArticleDynamics().decreasePalletCount(Processes.getInstance().getProcessAbbrevations()[1]);
                //capacity.decreaseCapacity(Processes.getInstance().getProcessAbbrevations()[1], DoloresConst.PALETT_CONTROL_TIME_STATIC_WE);
                capacity.decreaseCapacity(Processes.getInstance().getProcessAbbrevations()[1], time);
                /*if (Boolean.parseBoolean(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_UNIT_SECURITY_DEVICES_USED)))
                 {
                 capacity.decreaseCapacity(Processes.getInstance().getProcessAbbrevations()[1], DoloresConst.PALLET_UNIT_SECURITY_DEVICES_TIME);
                 }*/
                if (controlCounter == 1) {
                    //capacity.decreaseCapacity(Processes.getInstance().getProcessAbbrevations()[1], DoloresConst.PALETT_CONTROL_TIME_DYNAMIC_WE);
                    controlCounter = controlDistance + 1;
                }

                if (pallet.getError() == 0) {
                    mInts.put("pallets_with_error_0", mInts.get("pallets_with_error_0") + 1);
                }
                if (pallet.getError() == 1) {
                    mInts.put("pallets_with_error_1", mInts.get("pallets_with_error_1") + 1);
                }

                String key = new StringBuilder("pallets_transported_").append(Processes.getInstance().getProcessAbbrevations()[1]).toString();
                mInts.put(key, mInts.get(key) + 1);

                controlCounter--;
            }

        }

        currentState.setValue(DoloresConst.DOLORES_KEY_COSTS_UNIT_SECURITY_DEVICES, String.valueOf(costs_usd));
    }

    private void startPaletteThroughputStorePallets(double currentITFactor, int trueTakeUpReleaseTime, Storage storage, DoloresState state) {

        Collections.sort(storage.getPalletsNotInStorage(), new PalletsInProgressComparatorByDeliveryDate(2));

        int strategyStorage = Integer.valueOf(state.getValue(DoloresConst.DOLORES_KEY_STRATEGY_STORAGE));
        int strategyIn = Integer.valueOf(state.getValue(DoloresConst.DOLORES_KEY_STRATEGY_INCOMING_GOODS));

        isCapacityFullyUsedLaIn = false;
        List<PalletsInProgress> palletTemp = storage.getPalletsNotInStorage();

        for (PalletsInProgress pallet : palletTemp) {

            if (!pallet.isStored() && pallet.getCurrentProcess() == 2) {
                StockGround free = storage.getFreeStockGround(pallet.getOrderDynamics().getArticleDynamics(), strategyIn, strategyStorage);
                double gamingtime;

               

                if (free != null && !isCapacityFullyUsedLaIn) {
                    double way = free.getDistSource();

                    if (capacity.getStorageInputCapacity() < way) {
                        isCapacityFullyUsedLaIn = true;
                    } else {
                        gamingtime = this.calculateGameTime(way, conveyors.getAverageSpeed(Processes.getInstance().getProcessAbbrevations()[2], pallet.isUnitLoadSafetyDevices()), currentITFactor, trueTakeUpReleaseTime, DoloresConst.LIFT_LAYER_DURATION[free.getLevel()]);
                        if (gamingtime == -1) {
                            isCapacityFullyUsedLaIn = true;
                        }
                        if (pallet.getError() == 4) {
                            gamingtime += DoloresConst.TIME_CRASH;
                        }

                        storage.stockPallet(pallet.getOrderDynamics().getArticleDynamics(), free.getStockGroundId(), pallet);
                        pallet.setStockgroundId(free.getStockGroundId());
                        pallet.setStored(true);

                        capacity.decreaseStorageInputCapacity(gamingtime);

                        if (pallet.getError() == 4) {
                            String key = "pallets_with_error_4";
                            mInts.put(key, mInts.get(key) + 1);
                            capacity.decreaseStorageInputCapacity(DoloresConst.TIME_CRASH);
                        }

                        String key = new StringBuilder("pallets_transported_").append(Processes.getInstance().getProcessAbbrevations()[2]).append("_in").toString();
                        mInts.put(key, mInts.get(key) + 1);

                    }
                } else {
                    if (isCapacityFullyUsedLaIn) {
                        String key = new StringBuilder("not_transported_pallets_").append(Processes.getInstance().getProcessAbbrevations()[2]).append("_in").toString();
                        mInts.put(key, mInts.get(key) + 1);
                    }

                }

            }
        }

        //Sort again to move the already moved paletts at the new process, so we can start at the beginning of the list again
        Collections.sort(palletTemp, new PalletsInProgressComparatorByDeliveryDate(2));

        for (StockGround sg : storage.getOccStocks()) {
            if (storage.getPalletsNotInStorage().contains(sg.getPallet())) {
                storage.getPalletsNotInStorage().remove(sg.getPallet());
            }
        }

    }

    
    private void startPaletteThroughputUnstockPallets(DoloresGameInfo gameInfo, double currentITFactor, int trueTakeUpReleaseTime, int aktround, Storage storage, DoloresState state) {

        int strategyOut = Integer.valueOf(state.getValue(DoloresConst.DOLORES_KEY_STRATEGY_OUTGOING_GOODS));
        int strategyStorage = Integer.valueOf(state.getValue(DoloresConst.DOLORES_KEY_STRATEGY_STORAGE));
        int strategyIn = Integer.valueOf(state.getValue(DoloresConst.DOLORES_KEY_STRATEGY_INCOMING_GOODS));

        //Sort CustomerJob by Order-Periode
        List<CustomerJobDynamics> jobDynamics = gameInfo.getCurrentState().getJobDynamics();

        Collections.sort(jobDynamics, new CustomerJobComparatorByPeriode());

        isCapacityFullyUsedLaOut = false;
        for (CustomerJobDynamics job : jobDynamics) {
            if ((storage.getStoredPalletCount(job.getArticleDynamics().getArticleNumber()) > 0) && !isCapacityFullyUsedLaOut) {
                int outputPalletsForJob = Math.min((job.getPalette_amount() - job.getRequestedAmount()), Math.min(storage.getStoredPalletCount(job.getArticleDynamics().getArticleNumber()), job.getToDeliver()));

                if (outputPalletsForJob != job.getToDeliver()) {
                    int fehler = 1;
                }

                for (int i = 0; i < outputPalletsForJob; i++) {
                    try {
                        StockGround out = storage.unstockPallet(job.getArticleDynamics(), strategyOut);
                        if (out != null) {
                            //Calculate way to drive and gametime
                            double way = out.getDistDrain();
                            double time = this.calculateGameTime(way, conveyors.getAverageSpeed(Processes.getInstance().getProcessAbbrevations()[2], out.getPallet().isUnitLoadSafetyDevices()), currentITFactor, trueTakeUpReleaseTime, DoloresConst.LIFT_LAYER_DURATION[out.getLevel()]);
                            if (time == -1) {
                                isCapacityFullyUsedLaOut = true;
                                break;
                            }

                            if (time > capacity.getStorageOutputCapacity()) {
                                isCapacityFullyUsedLaOut = true;
                                StockGround freeStockGround = storage.getFreeStockGround(out.getPallet().getOrderDynamics().getArticleDynamics(), strategyIn, strategyStorage);
                                storage.stockPallet(out.getPallet().getOrderDynamics().getArticleDynamics(), freeStockGround.getStockGroundId(), out.getPallet());
                                break;
                            }

                            PalletsInProgress p = out.getPallet();
                            storage.getPalletsNotInStorage().add(p);
                            //get Pallet, assign it to the current job and move it directly to WK
                            
                            p.setCurrentProcess(3);
                            p.setStored(false);
                            p.getOrderDynamics().getArticleDynamics().increasePalletCount(Processes.getInstance().getProcessAbbrevations()[3]);
                            p.getOrderDynamics().getArticleDynamics().decreasePalletCount(Processes.getInstance().getProcessAbbrevations()[2]);

                            //set demand Periode on Pallet
                            p.setDemandRound(aktround);
                            //decrease capacity of storage-output-capacity
                            capacity.decreaseStorageOutputCapacity(time);
                            //increase pallets_transported
                            String key = new StringBuilder("pallets_transported_").append(Processes.getInstance().getProcessAbbrevations()[2]).append("_out").toString();
                            mInts.put(key, mInts.get(key) + 1);
                            //increase requested amount for job and consumption of related article
                            job.setRequestedAmount(job.getRequestedAmount() + 1);
                            job.getArticleDynamics().increaseConsumption();
                        } 
                    } catch (Exception e) {
                        System.out.println("Fehler in Paletten durchlauf.");
                    }
                }

            }
            if (((storage.getStoredPalletCount(job.getArticleDynamics().getArticleNumber()) > 0 && job.getPalette_amount() - job.getRequestedAmount() > 0)) || isCapacityFullyUsedLaOut) {
                if (!isCapacityFullyUsedLaOut) {
                    isCapacityFullyUsedLaOut = true;
                }
                
                String key = new StringBuilder("not_transported_pallets_").append(Processes.getInstance().getProcessAbbrevations()[2]).append("_out").toString();
                mInts.put(key, mInts.get(key) + job.getPalette_amount() - job.getRequestedAmount());
            }
        }
    }

    private void startPaletteThroughputWaVe(DoloresState currentState) {

        Storage storage = currentState.getStorage();
        //WK -> VE
        //Sort list by DemandDate (oldest first) to beginning of the List
        Collections.sort(storage.getPalletsNotInStorage(), new PalletsInProgressComparatorByDemandDate(3));
        int controlDistance = (Double.parseDouble(currentState.getValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WA)) == 0 ? -1 : (int) Math.round(1 / Double.parseDouble(currentState.getValue(DoloresConst.DOLORES_KEY_FACTOR_PALLET_CONTROL_WA))));
        int controlCounter = controlDistance;
        boolean isCapacityFullyUsed = false;

        for (PalletsInProgress pallet : storage.getPalletsNotInStorage()) {
            int time = (controlCounter == 1 ? DoloresConst.PALLET_CONTROL_TIME_STATIC_WA + DoloresConst.PALLET_CONTROL_TIME_DYNAMIC_WA : DoloresConst.PALLET_CONTROL_TIME_STATIC_WA);
            if (capacity.getCapacity(Processes.getInstance().getProcessAbbrevations()[3]) < time) {
                isCapacityFullyUsed = true;
            }
            if (isCapacityFullyUsed && pallet.getCurrentProcess() == 3) {
                String key = new StringBuilder("not_transported_pallets_").append(Processes.getInstance().getProcessAbbrevations()[3]).toString();
                mInts.put(key, mInts.get(key) + 1);
            } else if (pallet.getCurrentProcess() == 3) {
                //Transport pallet
                pallet.setCurrentProcess(4);
                pallet.getOrderDynamics().getArticleDynamics().increasePalletCount(Processes.getInstance().getProcessAbbrevations()[4]);
                pallet.getOrderDynamics().getArticleDynamics().decreasePalletCount(Processes.getInstance().getProcessAbbrevations()[3]);
                capacity.decreaseCapacity(Processes.getInstance().getProcessAbbrevations()[3], DoloresConst.PALLET_CONTROL_TIME_STATIC_WA);
                if (controlCounter == 1) {
                    capacity.decreaseCapacity(Processes.getInstance().getProcessAbbrevations()[3], DoloresConst.PALLET_CONTROL_TIME_DYNAMIC_WA);
                    controlCounter = controlDistance + 1;
                }

                if (pallet.getError() == 2) {
                    mInts.put("pallets_with_error_2", mInts.get("pallets_with_error_2") + 1);
                }
                String key = new StringBuilder("pallets_transported_").append(Processes.getInstance().getProcessAbbrevations()[3]).toString();
                mInts.put(key, mInts.get(key) + 1);

                controlCounter--;
            }
        }

        mInts.put("not_transported_pallets_la", mInts.get("not_transported_pallets_la_in") + mInts.get("not_transported_pallets_la_out"));
    }

    private void startPalletThroughputVeTruck(DoloresGameInfo gameInfo, DoloresState currentState, int aktround, double currentITFactor, int trueTakeUpReleaseTime) {
        Storage storage = currentState.getStorage();
        Collections.sort(storage.getPalletsNotInStorage(), new PalletsInProgressComparatorByDemandDate(4));
        //List<PalletsInProgress> outgoingPalletDynamics = new LinkedList<PalletsInProgress>();

        List<CustomerJobDynamics> jobDynamics = gameInfo.getCurrentState().getJobDynamics();

        Collections.sort(jobDynamics, new CustomerJobComparatorByPeriode());

        int accurateDeliveredPallets = Integer.parseInt(currentState.getValue(DoloresConst.DOLORES_KEY_ACCURATE_DELIVERED_PALLETS));
        boolean isCapacityFullyUsed = false;

        List<PalletsInProgress> toRemove = new ArrayList<PalletsInProgress>();
        int debug = 0;
        for (PalletsInProgress pallet : storage.getPalletsNotInStorage()) {

            double gametime = this.calculateGameTime(DoloresConst.WAY_VE_TRUCK, conveyors.getAverageSpeed(Processes.getInstance().getProcessAbbrevations()[4], pallet.isUnitLoadSafetyDevices()), currentITFactor, trueTakeUpReleaseTime, DoloresConst.LIFT_LAYER_DURATION[0]);
            if (gametime == -1) {
                isCapacityFullyUsed = true;
            }
            if (pallet.getError() == 5) {
                gametime += DoloresConst.TIME_CRASH;
            }
            if ((capacity.getCapacity(Processes.getInstance().getProcessAbbrevations()[4]) < gametime) && pallet.getCurrentProcess() == 4) {
                isCapacityFullyUsed = true;
            }

            if (!isCapacityFullyUsed && pallet.getCurrentProcess() == 4) {

                CustomerJobDynamics finishedJob = null;
                for (CustomerJobDynamics jobD : jobDynamics) {

//Requested pallet count - already delivered pallets
                    //if (jobD.getArticleDynamics().equals(pallet.getOrderDynamics().getArticleDynamics()) && jobD.getToDeliver() > 0 && jobD.getRequestedAmount() - (jobD.getPalette_amount() - jobD.getToDeliver()) > 0) {
                    if ((jobD.getArticleDynamics().getArticleNumber() == pallet.getOrderDynamics().getArticleDynamics().getArticleNumber()) && jobD.getToDeliver() > 0 /*&& jobD.getRequestedAmount() - (jobD.getPalette_amount() - jobD.getToDeliver()) > 0 */) {
                        jobD.setToDeliver(jobD.getToDeliver() - 1);
                        //outgoingPalletDynamics.add(pallet);
                        pallet.getOrderDynamics().getArticleDynamics().decreasePalletCount(Processes.getInstance().getProcessAbbrevations()[4]);
                        if (jobD.getToDeliver() <= 0) {
                            finishedJob = jobD;
                        }
                        if (jobD.getOrderPeriode() == aktround) {
                            accurateDeliveredPallets++;
                        } else {
                            jobD.getArticleDynamics().increaseLateDeliveredPallets();
                        }

                        String key = new StringBuilder("pallets_transported_").append(Processes.getInstance().getProcessAbbrevations()[4]).toString();
                        mInts.put(key, mInts.get(key) + 1);
                        capacity.decreaseCapacity(Processes.getInstance().getProcessAbbrevations()[4], gametime);
                        if (pallet.getError() == 5) {
                            mInts.put("pallets_with_error_5", mInts.get("pallets_with_error_5") + 1);
                        }
                        pallet.getOrderDynamics().getArticleDynamics().increaseSalesIncome(pallet.getOrderDynamics().getArticleDynamics().getSalePrice());
                        //currentState.getPallets().remove(pallet);

                        toRemove.add(pallet);
                        break;
                    }
                }
                if (finishedJob != null) {
                    //currentState.removeCustomerJob(finishedJob);
                    this.finishedJobs.add(finishedJob);
                    finishedJob.setFinished(true);
                    if (finishedJob.getOrderPeriode() == aktround) {
                        currentState.setValue(DoloresConst.DOLORES_KEY_ACCURATE_FINISHED_CUSTOMER_JOBS, String.valueOf(Integer.parseInt(currentState.getValue(DoloresConst.DOLORES_KEY_ACCURATE_FINISHED_CUSTOMER_JOBS)) + 1));
                    } else {
                        currentState.setValue(DoloresConst.DOLORES_KEY_LATE_FINISHED_CUSTOMER_JOBS, String.valueOf(Integer.parseInt(currentState.getValue(DoloresConst.DOLORES_KEY_LATE_FINISHED_CUSTOMER_JOBS)) + 1));
                        int latefinish = Integer.valueOf(currentState.getValue(new StringBuilder("late_finished_jobs_pallet_count_").append(finishedJob.getArticleNumber()).toString()));
                        currentState.setValue(new StringBuilder("late_finished_jobs_pallet_count_").append(finishedJob.getArticleNumber()).toString(), String.valueOf(latefinish + finishedJob.getPalette_amount()));

                        int temp = Integer.parseInt(currentState.getValue("late_finished_jobs_pallet_count")) + finishedJob.getPalette_amount();

                        currentState.setValue(new StringBuilder("late_finished_jobs_pallet_count").toString(), String.valueOf(temp));

                    }
                }
            } else if (isCapacityFullyUsed && pallet.getCurrentProcess() == 4) {
                
                String key = new StringBuilder("not_transported_pallets_").append(Processes.getInstance().getProcessAbbrevations()[4]).toString();
                mInts.put(key, mInts.get(key) + 1);
            }
        }

        for (PalletsInProgress palletToRemove : toRemove) {
            currentState.getStorage().getPalletsNotInStorage().remove(palletToRemove);
        }

        currentState.setValue(DoloresConst.DOLORES_KEY_ACCURATE_DELIVERED_PALLETS, String.valueOf(accurateDeliveredPallets));
        // Statistic stuff jobs

        currentState.setValue(DoloresConst.DOLORES_KEY_OVERALL_FINISHED_JOBS, String.valueOf((Integer.valueOf(currentState.getValue(DoloresConst.DOLORES_KEY_LATE_FINISHED_CUSTOMER_JOBS)) + Integer.valueOf(currentState.getValue(DoloresConst.DOLORES_KEY_ACCURATE_FINISHED_CUSTOMER_JOBS)))));
        currentState.setValue(DoloresConst.DOLORES_KEY_OVERALL_FINISHED_JOBS_PALLETS, String.valueOf((Integer.valueOf(currentState.getValue(DoloresConst.DOLORES_KEY_ACCURATE_DELIVERED_PALLETS)) + Integer.valueOf(currentState.getValue("late_finished_jobs_pallet_count")))));

        for (CustomerJobDynamics cjd : jobDynamics) {
            if (cjd.getOrderPeriode() <= gameInfo.getCurrentState().getRoundNumber()) {
                if (!cjd.isFinished()) {
                    jobStatistics.put(new StringBuilder("job_pallet_count_open_overall").toString(), (jobStatistics.get("job_pallet_count_overall") + cjd.getToDeliver()));
                    int artNr = cjd.getArticleNumber();
                    jobStatistics.put(new StringBuilder("job_pallet_count_open_").append(artNr).toString(),
                            (jobStatistics.get(new StringBuilder("job_pallet_count_open_").append(artNr).toString()) + cjd.getToDeliver()));
                }
            }

            // late != late finished
            // late = still open jobs
            if (cjd.getOrderPeriode() < gameInfo.getCurrentState().getRoundNumber()) {
                if (!cjd.isFinished()) {
                    jobStatistics.put(new StringBuilder("job_pallet_count_late_overall").toString(), (jobStatistics.get("job_pallet_count_late_overall") + cjd.getToDeliver()));
                    int artNr = cjd.getArticleNumber();
                    jobStatistics.put(new StringBuilder("job_pallet_count_late_").append(artNr).toString(),
                            (jobStatistics.get(new StringBuilder("job_pallet_count_late_").append(artNr).toString()) + cjd.getToDeliver()));
                }
            }

        }

    }

    private double calculateGameTime(double strecke, double geschwindigkeit, double f_iv, int t_aufnahmeabgabe, int t_hubsenk) {
        if (geschwindigkeit == (float) 0.0) {
            //LOG.error("Error in calculateGameTime Method. Speed is Zero.");
            return -1;
        } else {

            int time = ((int) Math.round(strecke / geschwindigkeit * (((float) 2.0) - f_iv))) + 2 * t_aufnahmeabgabe + t_hubsenk;

            return time;

        }
    }

    public int getNotTransportedPallets(String process) {
        return mInts.get(new StringBuilder("not_transported_pallets_").append(process).toString());
    }

    public int getTransportedPallets(String process) {
        return mInts.get(new StringBuilder("pallets_transported_").append(process).toString());
    }

    public int getNotTransportedPalletsLaIn() {
        return mInts.get("not_transported_pallets_la_in");
    }

    public int getNotTransportedPalletsLaOut() {
        return mInts.get("not_transported_pallets_la_out");
    }

    public int getOverallNotTransportedPallets() {
        int toReturn = 0;
        for (String process : Processes.getInstance().getProcessAbbrevations()) {
            toReturn += mInts.get(new StringBuilder("not_transported_pallets_").append(process).toString());
        }
        return toReturn;
    }

    /**
     * 0 = damaged 1 = wrong delivery 2 = wrong retrieval 3 = error en 4 = error
     * ve 5 = error wa 6 = no error
     *
     * @param idx
     * @return
     */
    public int getPalletCountWithError(int idx) {
        return mInts.get(new StringBuilder("pallets_with_error_").append(idx).toString());
    }

    public void removeFinishedJobsFromState(DoloresState state) {
        for (CustomerJobDynamics job : this.finishedJobs) {
            state.removeCustomerJob(job);
        }
    }

    public int getOverallOrderDynamicCosts(int articleNumber) {
        String key = new StringBuilder("order_costs_").append(articleNumber).toString();
        if (!mInts.containsKey(key)) {
            return 0;
        }
        return mInts.get(key);
    }

    public int getOverallOrderFixCosts(int articleNumber) {
        String key = new StringBuilder("order_fix_costs_").append(articleNumber).toString();
        if (!mInts.containsKey(key)) {
            return 0;
        }
        return mInts.get(key);
    }

    public int getEstimatedPallets(String process) {
        return mInts.get(new StringBuilder("estimated_pallets_").append(process).toString());
    }

    public int getEstimatedPalletsLaIn() {
        return mInts.get("estimated_pallets_la_in");
    }

    public int getEstimatedPalletsLaOut() {
        return mInts.get("estimated_pallets_la_out");
    }

    @Override
    public void prepareNextRound(DoloresGameInfo gameInfo) {
        int currentRound = Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER));
        int newPalletss = 0; //a_p_bestellungen
        for (OrderDynamics orderDynamics : gameInfo.getCurrentState().getOpenOrderDynamics()) {
            if (orderDynamics.getDeliveryPeriod() == currentRound) {
                newPalletss += orderDynamics.getDeliveryAmount();
            }
        }
        int toTransportEn = newPalletss + this.getNotTransportedPallets(Processes.getInstance().getProcessAbbrevations()[0]);
        mInts.put("estimated_pallets_en", toTransportEn);
        int toTransportWv = toTransportEn + this.getNotTransportedPallets(Processes.getInstance().getProcessAbbrevations()[1]);
        mInts.put("estimated_pallets_wv", toTransportWv);
        int toTransportLaIn = toTransportWv + this.getNotTransportedPalletsLaIn();
        mInts.put("estimated_pallets_la_in", toTransportLaIn);
        int palletsForJobs = 0;
        for (CustomerJobDynamics jobD : gameInfo.getCurrentState().getJobDynamics()) {
            if (jobD.getToDeliver() > 0) {
                palletsForJobs += jobD.getToDeliver();
            }
        }
        int toTransportLaOut = palletsForJobs + this.getNotTransportedPalletsLaOut();
        mInts.put("estimated_pallets_la_out", toTransportLaOut);
        int toTransportWk = toTransportLaOut + this.getNotTransportedPallets(Processes.getInstance().getProcessAbbrevations()[3]);
        mInts.put("estimated_pallets_wk", toTransportWk);
        mInts.put("estimated_pallets_ve", toTransportWk + this.getNotTransportedPallets(Processes.getInstance().getProcessAbbrevations()[4]));
        mInts.put("estimated_pallets_la", toTransportLaIn + toTransportLaOut);

        double company_value = Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_COMPANY_VALUE));
        if (currentRound >= Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_LAST_PERIODE))) {   //Max num of Rounds is reached -> End of Game
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_GAME_STATE, DoloresConst.GameState.GAME_STATE_END);

            messages.add("maxround");

        } else if (company_value < Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_ACCOUNT_BALANCE)) * (-1)) {   //State of game is critical
            if (gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_STATE).equals(DoloresConst.GameState.GAME_STATE_CRITICAL)) {	//GameState was Critical in last round
                if (currentRound - Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_STATUS_CHANGE_PERIODE)) > DoloresConst.DOLORES_GAME_MAX_NUM_CRITICAL_STATE) {   //End Game because the critical State reached maximum roundnum
                    gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_GAME_STATE, DoloresConst.GameState.GAME_STATE_END);
                    //: Generate Message that game is ended because player has to announce bankruptcy
                    messages.add("bankrupt");
                } else {
                    messages.add("financial_is");
                    //: Generate Message that financial state is critical already
                }
            } else if (gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_STATE).equals(DoloresConst.GameState.GAME_STATE_OK)) {
                gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_GAME_STATE, DoloresConst.GameState.GAME_STATE_CRITICAL);
                gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_GAME_STATUS_CHANGE_PERIODE, String.valueOf(currentRound - 1));
                //Generate Message that financial state got critical
                messages.add("financial_got");
            }

        }
        double averageCustomerSatisfaction = 0d;
        List<DoloresState> oldStates = service.getLastNStatesManualSave(gameInfo, DoloresConst.ARTICLE_CONSUMPTION_HISTORY_TIME, false);
        if (oldStates.size() > 0) {
            for (DoloresState doloresState : oldStates) {
                averageCustomerSatisfaction += Double.parseDouble(doloresState.getValue(DoloresConst.DOLORES_KEY_CUSTOMER_SATISFACTION));
            }
            //averageCustomerSatisfaction /= oldStates.size();
            averageCustomerSatisfaction /= 5;
            //if (averageCustomerSatisfaction < oldStates.size()) {
            if (averageCustomerSatisfaction < 0.05) {
                if (gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_STATE).equals(DoloresConst.GameState.GAME_STATE_CRITICAL)) {
                    if (currentRound - Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_STATUS_CHANGE_PERIODE)) > DoloresConst.DOLORES_GAME_MAX_NUM_CRITICAL_STATE) {   //End Game because the critical State reached maximum roundnum
                        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_GAME_STATE, DoloresConst.GameState.GAME_STATE_END);
                        //Generate Message that game is ended because player has no more customers
                        messages.add("no_customers");
                    } else {
                        messages.add("customers_leave");
                        // Generate Message that customers leave company already
                    }
                } else if (gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_STATE).equals(DoloresConst.GameState.GAME_STATE_OK)) {
                    gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_GAME_STATE, DoloresConst.GameState.GAME_STATE_CRITICAL);
                    gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_GAME_STATUS_CHANGE_PERIODE, String.valueOf(currentRound - 1));

                    //Generate Message that customers leave company
                    messages.add("customers_crit");
                }
            }

        } else if (gameInfo.getCurrentState()
                .getValue(DoloresConst.DOLORES_GAME_STATE).equals(DoloresConst.GameState.GAME_STATE_CRITICAL)) {
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_GAME_STATE, DoloresConst.GameState.GAME_STATE_OK);
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_GAME_STATUS_CHANGE_PERIODE, String.valueOf(currentRound - 1));

            messages.add("gamestate_back_ok");
        }
    }

    public List<PalletsInProgress> getPalletsToPersist() {
        return palletsToPersist;
    }

    @Override
    public List<Object> getToUpdate() {
        return new ArrayList<Object>(); //To change body of generated methods, choose Tools | Templates.
    }

    public int getNewPallets() {
        return newPallets;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void transfer() {
        for (Field f : palStat.getClass().getDeclaredFields()) {
            if (mInts.get(f.getName()) != null && !Double.isNaN(mInts.get(f.getName()))) {
                try {
                    f.setAccessible(true);
                    f.set(palStat, mInts.get(f.getName()));

                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(EmployeeCalculator.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(EmployeeCalculator.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else if (mDoubles.get(f.getName()) != null && !Double.isNaN(mDoubles.get(f.getName()))) {
                try {
                    f.setAccessible(true);
                    f.set(palStat, mDoubles.get(f.getName()));

                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(EmployeeCalculator.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(EmployeeCalculator.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        for (Field f : aaos.getClass().getDeclaredFields()) {
            if (jobStatistics.get(f.getName()) != null && !Double.isNaN(jobStatistics.get(f.getName()))) {
                try {
                    f.setAccessible(true);
                    f.set(aaos, jobStatistics.get(f.getName()));

                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(EmployeeCalculator.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(EmployeeCalculator.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public void calculateArticleValues(DoloresGameInfo gameInfo) {

        Storage storage = gameInfo.getCurrentState().getStorage();

        for (ArticleDynamics ad : gameInfo.getCurrentState().getArticleDynamics()) {

            int storedPalletCount = storage.getStoredPalletCount(ad.getArticleNumber());

            mDoubles.put(new StringBuilder("stock_value_").append(ad.getArticleNumber()).toString(), storedPalletCount * ad.getSalePrice());

        }
    }

    public void berechneLieferabweichungen(OrderDynamics order) {

        float[] liefermengenabweichungswkeiten = new float[6];
        float[] lieferterminabweichungswkeiten = new float[4];
        int summe_liefermengenabweichungskennziffern = 0;
        int summe_lieferterminabweichungskennziffern = 0;
        int i;
        // Berechnen der Summe der Liefermengenabweichungskennziffern
        for (i = 0; i < 6; i++) {
            summe_liefermengenabweichungskennziffern += DoloresConst.KENNZIFFER_LIEFERMENGENABWEICHUNG[i]; //liefermengenabweichungskennziffern[lauf];
        }
        // Berechnen der Summe der Lieferterminabweichungskennziffern
        for (i = 0; i < 4; i++) {
            summe_lieferterminabweichungskennziffern += DoloresConst.KENNZIFFER_LIEFERTERMINABWEICHUNG[i]; //lieferterminabweichungskennziffern[lauf];
        }
        // Berechnung der kumulierten Wahrscheinlichkeiten für die Liefermengenabweichungen
        liefermengenabweichungswkeiten[0] = ((float) DoloresConst.KENNZIFFER_LIEFERMENGENABWEICHUNG[0]) / ((float) summe_liefermengenabweichungskennziffern);
        for (i = 1; i < 6; i++) {
            liefermengenabweichungswkeiten[i] = liefermengenabweichungswkeiten[i - 1]
                    + ((float) DoloresConst.KENNZIFFER_LIEFERMENGENABWEICHUNG[i])
                    / ((float) summe_liefermengenabweichungskennziffern);
        }

        // Berechnung der kumulierten Wahrscheinlichkeiten für die Lieferterminabweichungen
        lieferterminabweichungswkeiten[0] = ((float) DoloresConst.KENNZIFFER_LIEFERTERMINABWEICHUNG[0]) / ((float) summe_lieferterminabweichungskennziffern);

        for (i = 1; i < 4; i++) {
            lieferterminabweichungswkeiten[i] = lieferterminabweichungswkeiten[i - 1]
                    + (((float) DoloresConst.KENNZIFFER_LIEFERTERMINABWEICHUNG[i])
                    / ((float) summe_lieferterminabweichungskennziffern));
        }

        Random rnd = new Random();
        float rFloat = rnd.nextFloat();
        i = 0;
        int liefermenge = order.getDeliveryAmount();
        while (i < 6) {
            if (rFloat > liefermengenabweichungswkeiten[i]) {
                i++;
            } else {
                // mala 15.11.2006 Abweichung der Liefermenge prozentual (vorher negative Werte möglich)
                liefermenge = order.getOrderAmount() - (order.getOrderAmount() * DoloresConst.LIEFERMENGENABWEICHUNG[i] / 100);
//                System.out.println("#############################################");
//                System.out.println("Zufallszahl Menge = " + aktuellezufallszahl);
//                System.out.println("Wunschliefermenge = " + wunschliefermenge);
//                System.out.println("Liefermengenabweichung = " + liefermengenabweichungen[lauf]);
//                System.out.println("Liefermenge = " + liefermenge);
//                System.out.println("#############################################");
                break;
            }
        }

        // Wenn die soeben ermittelte Liefermenge kleinergleich Null ist, so wird sie auf den
        // Wert 1 gesetzt, damit es keine Bestellungen geben kann, bei deren Lieferung gar keine
        // Paletten geliefert werden
        if (liefermenge <= 0) {
            liefermenge = 1;
        }

        // Ermitteln des tatsächlichen Liefertermins
        rFloat = rnd.nextFloat();
        i = 0;
        int liefertermin = order.getDeliveryPeriod();
        while (i < 4) {
            if (rFloat > lieferterminabweichungswkeiten[i]) {
                i++;
            } else {
                liefertermin = order.getDeliveryPeriod() + DoloresConst.LIEFERTERMINABWEICHUNG[i];
//                System.out.println("#############################################");
//                System.out.println("Zufallszahl Termin = " + aktuellezufallszahl);
//                System.out.println("Wunschliefertermin = " + wunschliefertermin);
//                System.out.println("Lieferterminabweichung = " + lieferterminabweichungen[lauf]);
//                System.out.println("Liefertermin = " + liefertermin);                
//                System.out.println("#############################################");
                break;
            }
        }

        order.setDeliveryPeriod(liefertermin);
        order.setDeliveryAmount(liefermenge);

    }
}
