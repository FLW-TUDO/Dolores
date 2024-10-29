/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.calcs;

import flw.business.business.Service;
import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresState;
import flw.business.statistics.EmployeeStatistics;
import flw.business.statistics.StateStatistics;
import flw.business.store.ArticleDynamics;
import flw.business.store.OrderDynamics;
import flw.business.util.DoloresConst;
import flw.business.util.Processes;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author alpl
 */
public class PostPalletThroughputCalculator extends AbstractCalculator {

    private ConveyorCalculator conveyors;
    private EmployeeCalculator employees;
    private CapacityCalculator capacity;
    //private PalletThroughputCalculator pallet;
    private PalletThroughputCalc pallet;
    private Map<String, Double> mDoubles = new HashMap<>();
    private List<Object> toUpdate = new ArrayList<Object>();
    private Map<String, Double> mDoubleStat = new HashMap<String, Double>();
    private Map<String, Integer> mIntStat = new HashMap<String, Integer>();
    
    @EJB
    private Service service;

    public PostPalletThroughputCalculator(List<AbstractCalculator> lCalculators, Service service) {
        super(lCalculators);
        this.service = service;
    }

    @Override
    public void calculate(DoloresGameInfo gameInfo) {
        for (AbstractCalculator abstractCalculator : lCalculators) {
            if (abstractCalculator instanceof ConveyorCalculator) {
                conveyors = (ConveyorCalculator) abstractCalculator;
            } else if (abstractCalculator instanceof EmployeeCalculator) {
                employees = (EmployeeCalculator) abstractCalculator;
            } else if (abstractCalculator instanceof CapacityCalculator) {
                capacity = (CapacityCalculator) abstractCalculator;
            //} else if (abstractCalculator instanceof PalletThroughputCalculator) {
              //  pallet = (PalletThroughputCalculator) abstractCalculator;
            }
            else if (abstractCalculator instanceof PalletThroughputCalc) {
                pallet = (PalletThroughputCalc) abstractCalculator;
            }
        }
        for (String process : Processes.getInstance().getProcessAbbrevations()) {
            double x = employees.getCapacity(process);
            

            if (process.equals("la")) {
                Double temp = capacity.getStorageOutputCapacity() + capacity.getStorageInputCapacity();
                mDoubles.put(new StringBuilder("workload_workers_").append(process).toString(), (double) ((x - temp) / x));
            } else {

                mDoubles.put(new StringBuilder("workload_workers_").append(process).toString(), (double) ((capacity.getOverallCapacity(process) - capacity.getCapacity(process)) / x));
            }
        }
        for (String process : Processes.getInstance().getProcessAbbrevationsWithConveyors()) {
            if (process.equals("la")) {
                mDoubles.put(new StringBuilder("workload_conveyors_").append(process).toString(), (double) ((capacity.getOverallCapacity(process) - (capacity.getStorageInputCapacity() + capacity.getStorageOutputCapacity())) / conveyors.getCapacity(process)));
            } else {
                mDoubles.put(new StringBuilder("workload_conveyors_").append(process).toString(), (double) ((capacity.getOverallCapacity(process) - capacity.getCapacity(process)) / conveyors.getCapacity(process)));
            }
        }
        //To get Count of Article in every single Process use Article.getPalletCount(String process)
        //To get Overall Count of Pallets of an Article in Company use Article.getPalletCountOfAllProcesses()
        for (int i = 0; i < Processes.getInstance().getProcessAbbrevations().length; i++) {
            int valueInProcess = 0;
            int overallValueOfArticles = 0;
            //for (ArticleDynamics aD : ArticleFactory.getInstance().getArticleDynamics())
            for (ArticleDynamics aD : gameInfo.getCurrentState().getArticleDynamics()) {
                valueInProcess += aD.getPalletCount(Processes.getInstance().getProcessAbbrevations()[i]) * aD.getPricePerPallet();

                gameInfo.getCurrentState().setValue(new StringBuilder(DoloresConst.DOLORES_KEY_STOCK_VALUE[i]).append("_").append(aD.getArticleNumber()).toString(),
                        String.valueOf(aD.getPalletCount(Processes.getInstance().getProcessAbbrevations()[i]) * aD.getPricePerPallet()));

                if (i == 0) {
                    overallValueOfArticles += aD.getPalletCountOfAllProcesses() * aD.getPricePerPallet();
                }
            }
            if (i == 0) {
                gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_STOCK_VALUE, String.valueOf(overallValueOfArticles));
                gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_COMPANY_VALUE, String.valueOf(overallValueOfArticles + conveyors.getCurrentConveyorValueSum()));
                mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_STOCK_VALUE, overallValueOfArticles);
                mDoubleStat.put(DoloresConst.DOLORES_KEY_COMPANY_VALUE, overallValueOfArticles + conveyors.getCurrentConveyorValueSum());
            }

            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_STOCK_VALUE[i], String.valueOf(valueInProcess));
            mIntStat.put(DoloresConst.DOLORES_KEY_STOCK_VALUE[i], valueInProcess);

        }
        //Calculate average Consumption of Articles
        
        List<DoloresState> history = service.getLastNStatesManualSave(gameInfo, DoloresConst.ARTICLE_CONSUMPTION_HISTORY_TIME, true);
        HashMap<Integer, Integer> pastConsumptions = new HashMap<Integer, Integer>();
        pastConsumptions.put(100101, 0);
        pastConsumptions.put(100102, 0);
        pastConsumptions.put(100103, 0);
        pastConsumptions.put(100104, 0);
        for (DoloresState doloresState : history) {
            for (ArticleDynamics articleDyn : doloresState.getArticleDynamics()) {
                
                pastConsumptions.put(articleDyn.getArticleNumber(), pastConsumptions.get(articleDyn.getArticleNumber()) + articleDyn.getConsumption());
            }
        }
        int pallets_in_preparation_for_devlivery = 0;
        int pallets_not_in_storage = 0;
        int overall_consumption = 0;
        for (ArticleDynamics article : gameInfo.getCurrentState().getArticleDynamics()) {
            article.setAvgConsumption(pastConsumptions.get(article.getArticleNumber()) / history.size());
            if (article.getAvgConsumption() != 0) {
                article.setEstimatedRange(article.getPalletCount(Processes.getInstance().getProcessAbbrevations()[2]) / article.getAvgConsumption());
            } else {
                article.setEstimatedRange(Integer.MAX_VALUE);
            }
            //calculate optimal order amount for article
            article.setOptimalOrderAmount(getOptimalOrderAmount(article, pastConsumptions));
            pallets_in_preparation_for_devlivery += article.getPalletCount(Processes.getInstance().getProcessAbbrevations()[3]);
            pallets_in_preparation_for_devlivery += article.getPalletCount(Processes.getInstance().getProcessAbbrevations()[4]);
            pallets_not_in_storage += article.getPalletCountNotInStorage();
            overall_consumption += article.getConsumption();
        }
        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_PALLETS_IN_PREPARATION_FOR_DELIVERY, String.valueOf(pallets_in_preparation_for_devlivery));
        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_PALLETS_NOT_IN_STORAGE, String.valueOf(pallets_not_in_storage));
        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_CONSUMPTION, String.valueOf(overall_consumption));

        mIntStat.put(DoloresConst.DOLORES_KEY_PALLETS_IN_PREPARATION_FOR_DELIVERY, pallets_in_preparation_for_devlivery);
        mIntStat.put(DoloresConst.DOLORES_KEY_PALLETS_NOT_IN_STORAGE, (pallets_not_in_storage));
        mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_CONSUMPTION, (overall_consumption));

        //Calculate reclamation factors
        int apr = 0;
        for (int i = 0; i <= 5; i++) {
            apr += pallet.getPalletCountWithError(i);
        }
        if (pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1]) != 0) {
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_PERCENTAGE, String.valueOf(apr / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_PERCENTAGE, apr / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1]));
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_DAMAGED, String.valueOf(pallet.getPalletCountWithError(0) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_DAMAGED, (pallet.getPalletCountWithError(0) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_WRONG_DELIVERED, String.valueOf(pallet.getPalletCountWithError(1) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_WRONG_DELIVERED, (pallet.getPalletCountWithError(1) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_WRONG_RETRIEVAL, String.valueOf(pallet.getPalletCountWithError(2) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_WRONG_RETRIEVAL, (pallet.getPalletCountWithError(2) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_WRONG_PALLETS, String.valueOf((pallet.getPalletCountWithError(1) + pallet.getPalletCountWithError(2)) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_WRONG_PALLETS, ((pallet.getPalletCountWithError(1) + pallet.getPalletCountWithError(2)) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_EN, String.valueOf(pallet.getPalletCountWithError(3) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_EN, (pallet.getPalletCountWithError(3) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_LA, String.valueOf(pallet.getPalletCountWithError(4) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_LA, (pallet.getPalletCountWithError(4) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_VE, String.valueOf(pallet.getPalletCountWithError(5) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_VE, (pallet.getPalletCountWithError(5) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_ON_TRANSPORT, String.valueOf((pallet.getPalletCountWithError(3) + pallet.getPalletCountWithError(4) + pallet.getPalletCountWithError(5)) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_ON_TRANSPORT, ((pallet.getPalletCountWithError(3) + pallet.getPalletCountWithError(4) + pallet.getPalletCountWithError(5)) / pallet.getTransportedPallets(Processes.getInstance().getProcessAbbrevations()[Processes.getInstance().getProcessAbbrevations().length - 1])));
        } else {

            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_PERCENTAGE, 0);
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_DAMAGED, 0);
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_WRONG_DELIVERED, 0);
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_WRONG_RETRIEVAL, 0);
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_WRONG_PALLETS, 0);
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_EN, 0);
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_LA, 0);
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_VE, 0);
            mIntStat.put(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_ON_TRANSPORT, 0);
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_PERCENTAGE, "0");
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_DAMAGED, "0");
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_WRONG_DELIVERED, "0");
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_WRONG_RETRIEVAL, "0");
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_WRONG_PALLETS, "0");
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_EN, "0");
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_LA, "0");
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_VE, "0");
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_ERROR_ON_TRANSPORT, "0");
        }

        int orderedPallets = 0;
        int orderCosts = 0;

        for (OrderDynamics orderDynamics : gameInfo.getCurrentState().getOpenOrderDynamics()) {

            orderedPallets += orderDynamics.getOrderAmount();
            int currentCost = orderDynamics.getOrderAmount() * (orderDynamics.getRealPurchasePrice() + orderDynamics.getDeliveryCosts());
            orderCosts += currentCost;
            if (!orderDynamics.isAlreadyCalc()) {
                orderDynamics.getArticleDynamics().setOrderedPallets(orderDynamics.getArticleDynamics().getOrderedPallets() + orderDynamics.getOrderAmount());
                orderDynamics.getArticleDynamics().setOrderCosts(orderDynamics.getArticleDynamics().getOrderCosts() + currentCost);
                orderDynamics.setAlreadyCalc(true);
            }
            toUpdate.add(orderDynamics.getArticleDynamics());

            //if(orderDynamics.isComplete()){
            //  gameInfo.getCurrentState().getOpenOrderDynamics().remove(orderDynamics);
            //}
        }
        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_CURRENT_ORDERED_AMOUNT, String.valueOf(orderedPallets));
        mIntStat.put(DoloresConst.DOLORES_KEY_CURRENT_ORDERED_AMOUNT, (orderedPallets));
        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_CURRENT_ORDER_COSTS, String.valueOf(orderCosts));
        mIntStat.put(DoloresConst.DOLORES_KEY_CURRENT_ORDER_COSTS, (orderCosts));

        //Calculate <Lieferbereitschaftsgrad> und <Kundenzufriedenheit>
        double f_lbg = (gameInfo.getCurrentState().getJobDynamics().size() > 0) ? (Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_ACCURATE_FINISHED_CUSTOMER_JOBS))) / (gameInfo.getCurrentState().getJobDynamics().size()) : 0;
        double customerSatisfaction = (1d - Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_OVERALL_RECLAMATION_PERCENTAGE))) * f_lbg;
        customerSatisfaction = (customerSatisfaction <= 0 ? 0d : customerSatisfaction);
        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_SERVICE_LEVEL, String.valueOf(f_lbg));
        mDoubleStat.put(DoloresConst.DOLORES_KEY_SERVICE_LEVEL, f_lbg);

        if (customerSatisfaction > 0.001) {
            customerSatisfaction = customerSatisfaction * 100;
            int customerSatisfactionInt = (int) customerSatisfaction;
            double temp = (double) customerSatisfactionInt;
            customerSatisfaction = temp / 100;
        }

        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_CUSTOMER_SATISFACTION, String.valueOf(customerSatisfaction));
        mDoubleStat.put(DoloresConst.DOLORES_KEY_CUSTOMER_SATISFACTION, (customerSatisfaction));

        if (gameInfo.getCurrentState().getRoundNumber() == 10) {
            mDoubleStat.put(DoloresConst.DOLORES_KEY_CUSTOMER_SATISFACTION, 0.99);
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_CUSTOMER_SATISFACTION, String.valueOf(0.99));
        }
    }

    public int getOptimalOrderAmount(ArticleDynamics article, HashMap<Integer, Integer> pastConsumtions) {
        int optimalebestellmenge0 = (int) Math.round(Math.sqrt(
                ((float) (2 * pastConsumtions.get(article.getArticleNumber()) * article.getFixCosts()))
                / (((float) article.getPricePerPallet()) * DoloresConst.STOCK_CARRYING_FACTOR)));
        int optimalebestellmenge1 = (int) Math.round(Math.sqrt(
                ((float) (2 * pastConsumtions.get(article.getArticleNumber()) * article.getFixCosts()))
                / (((float) article.getRebateCost1()) * DoloresConst.STOCK_CARRYING_FACTOR)));
        int optimalebestellmenge2 = (int) Math.round(Math.sqrt(
                ((float) (2 * pastConsumtions.get(article.getArticleNumber()) * article.getFixCosts()))
                / (((float) article.getRebateCost2()) * DoloresConst.STOCK_CARRYING_FACTOR)));

        if (optimalebestellmenge1 < article.getRebateAmount1()) {
            optimalebestellmenge1 = article.getRebateAmount1();
        }
        if (optimalebestellmenge2 < article.getRebateAmount2()) {
            optimalebestellmenge2 = article.getRebateAmount2();
        }

        double bestellkosten0 = (int) Math.round((float) article.getFixCosts() / (float) optimalebestellmenge0)
                + (article.getPricePerPallet() * pastConsumtions.get(article.getArticleNumber()))
                + (int) Math.round(((float) optimalebestellmenge0 * article.getPricePerPallet()) * DoloresConst.STOCK_CARRYING_FACTOR / 2.0f);

        double bestellkosten1 = (int) Math.round((float) article.getFixCosts() / (float) optimalebestellmenge1)
                + (article.getRebateCost1() * pastConsumtions.get(article.getArticleNumber()))
                + (int) Math.round(((float) optimalebestellmenge1 * article.getRebateCost1()) * DoloresConst.STOCK_CARRYING_FACTOR / 2.0f);

        double bestellkosten2 = (int) Math.round((float) article.getFixCosts() / (float) optimalebestellmenge2)
                + (article.getRebateCost2() * pastConsumtions.get(article.getArticleNumber()))
                + (int) Math.round(((float) optimalebestellmenge2 * article.getRebateCost2()) * DoloresConst.STOCK_CARRYING_FACTOR / 2.0f);
        double kosten = Math.min(Math.min(bestellkosten0, bestellkosten1), bestellkosten2);
        if (kosten == bestellkosten0) {
            return optimalebestellmenge0;
        } else if (kosten == bestellkosten1) {
            return optimalebestellmenge1;
        } else {
            return optimalebestellmenge2;
        }
    }

    public double getWorkerWorkload(String process) {
        return mDoubles.get(new StringBuilder("workload_workers_").append(process).toString());
    }

    public double getConveyorWorkload(String process) {
        return mDoubles.get(new StringBuilder("workload_conveyors_").append(process).toString());
    }

    public List<Object> getToUpdate() {
        return toUpdate;
    }

    public void setToUpdate(List<Object> toUpdate) {
        this.toUpdate = toUpdate;
    }

    @Override
    public void prepareNextRound(DoloresGameInfo gameInfo) {
    }

    public void transfer(EmployeeStatistics empStat, StateStatistics stateStat) {

        for (Field f : empStat.getClass().getDeclaredFields()) {

            

            if (mDoubles.get(f.getName()) != null && !Double.isNaN(mDoubles.get(f.getName())) && !Double.isInfinite(mDoubles.get(f.getName()))) {
                try {
                    f.setAccessible(true);
                    f.set(empStat, mDoubles.get(f.getName()));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        for (Field f : stateStat.getClass().getDeclaredFields()) {
            if (mDoubleStat.get(f.getName()) != null && mDoubleStat.get(f.getName()) != Double.NaN) {
                try {
                    f.setAccessible(true);
                    if(f.getName().equals("satisfaction")){
                        double get = mDoubleStat.get(f.getName());
                        int satisfaction =(int) get*100;
                        f.set(stateStat, satisfaction);
                    }else{
                        f.set(stateStat, mDoubleStat.get(f.getName()));
                    }
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (mIntStat.get(f.getName()) != null && mIntStat.get(f.getName()) != Double.NaN) {
                try {
                    f.setAccessible(true);
                    f.set(stateStat, mIntStat.get(f.getName()));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
