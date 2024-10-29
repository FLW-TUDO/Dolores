/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.calcs;

import flw.business.core.DoloresGameInfo;
import flw.business.statistics.StateStatistics;
import flw.business.store.ArticleDynamics;
import flw.business.store.CustomerJobDynamics;
import flw.business.store.OrderDynamics;
import flw.business.util.DoloresConst;
import flw.business.util.Processes;
import java.lang.reflect.Field;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tilu
 */
public class SaveStatisticsCalculator extends AbstractCalculator {

    public SaveStatisticsCalculator(List<AbstractCalculator> lCalculators) {
        super(lCalculators);
    }

    @Override
    public void calculate(DoloresGameInfo gameInfo) {



        PostPalletThroughputCalculator poptc = (PostPalletThroughputCalculator) lCalculators.get(4);

        ConveyorCalculator conc = (ConveyorCalculator) lCalculators.get(1);
        EmployeeCalculator eC = (EmployeeCalculator) lCalculators.get(0);
        //PalletThroughputCalculator ptc = (PalletThroughputCalculator) lCalculators.get(3);
        PalletThroughputCalc ptc = (PalletThroughputCalc) lCalculators.get(3);

        if (gameInfo.getCurrentState().getRoundNumber() > 10) {

            eC.transfer();
            gameInfo.getEmployeeStatisticses().add(eC.getEmpStat());
            eC.getEmpStat().setGameInfo(gameInfo);

            conc.transfer();
            gameInfo.getConveyorStatisticses().add(conc.getConvStat());
            conc.getConvStat().setGameInfo(gameInfo);

            ptc.transfer();
            gameInfo.getPalletStatisticses().add(ptc.getPalStat());

            ptc.getPalStat().setGameInfo(gameInfo);



            StateStatistics stateStat = new StateStatistics();

            poptc.transfer(eC.getEmpStat(), stateStat);

            int lateJobCount = 0;
            for (CustomerJobDynamics cjd : gameInfo.getCurrentState().getJobDynamics()) {




                if (cjd.getOrderPeriode() < Integer.valueOf(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER))) {
                    lateJobCount++;
                }
                gameInfo.getCurrentState().setValue("lateJobCount", String.valueOf(lateJobCount));

            }



            gameInfo.getCurrentState().setValue("freeStorageSpace", String.valueOf(gameInfo.getCurrentState().getStorage().getFreeStockGroundCount()));




            for (Field f : stateStat.getClass().getDeclaredFields()) {

                if (gameInfo.getCurrentState().getPropVariables().get(f.getName()) != null) {
                    try {
                        f.setAccessible(true);

                        if (f.getName().equals("satisfaction")) {
                            double d = Double.parseDouble(gameInfo.getCurrentState().getPropVariables().get(f.getName()).toString());
                            double newd = d * 100;
                            int satisfaction = (int) newd;
                            f.set(stateStat, satisfaction);
                        }


                        if (f.getType().equals(int.class)) {
                            f.set(stateStat, Integer.parseInt(gameInfo.getCurrentState().getPropVariables().get(f.getName()).toString()));
                        } else if (f.getType().equals(double.class)) {
                            f.set(stateStat, Double.parseDouble(gameInfo.getCurrentState().getPropVariables().get(f.getName()).toString()));
                        } else if (f.getType().equals(boolean.class)) {
                            f.set(stateStat, Boolean.parseBoolean(gameInfo.getCurrentState().getPropVariables().get(f.getName()).toString()));
                        } else if (f.getType().equals(String.class)) {
                            f.set(stateStat, gameInfo.getCurrentState().getPropVariables().get(f.getName()));
                        }

                        //f.set(stateStat, gameInfo.getCurrentState().getPropVariables().get(f.getName()));
                    } catch (IllegalArgumentException ex) {
                        //(Logger.getLogger(SaveStatisticsCalculator.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        //Logger.getLogger(SaveStatisticsCalculator.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            }


            if (gameInfo.getCurrentState().getRoundNumber() != 10) {
                stateStat.setGameInfo(gameInfo);
                stateStat.setRoundNumber(gameInfo.getCurrentState().getRoundNumber());
                gameInfo.getStateStatisticses().add(stateStat);
                stateStat.setPointInTime(System.currentTimeMillis());
            }
            //test


            TreeMap<String, Integer> stats = initalizeArticleInProcess();
            TreeMap<String, Integer> initializeOrderStats = initializeOrderStats();

            for (String process : Processes.getInstance().getProcessAbbrevations()) {
                for (ArticleDynamics artDyn : gameInfo.getCurrentState().getArticleDynamics()) {
                    stats.put(new StringBuilder("process_pallet_count_").append(process).toString(), artDyn.getPalletCount(process) + stats.get(new StringBuilder("process_pallet_count_").append(process).toString()));
                    stats.put(new StringBuilder("process_pallet_count_").append(process).append("_").append(artDyn.getArticleNumber()).toString(), artDyn.getPalletCount(process));
                }
            }
            for (ArticleDynamics artDyn : gameInfo.getCurrentState().getArticleDynamics()) {
                stats.put(new StringBuilder("process_pallet_count_").append(artDyn.getArticleNumber()).toString(), artDyn.getPalletCountOfAllProcesses());
                stats.put(new StringBuilder("process_pallet_count_overall").toString(), artDyn.getPalletCountOfAllProcesses() + stats.get("process_pallet_count_overall"));

                stats.put("used_pallets_overall", stats.get("used_pallets_overall") + artDyn.getConsumption());
                stats.put(new StringBuilder("used_pallets_").append(artDyn.getArticleNumber()).toString(), artDyn.getConsumption());

                /*
                 used_pallets_overall", 0);
                 process_stats.put("used_pallets_100101", 0);*/
            }


            for (OrderDynamics od : gameInfo.getCurrentState().getOpenOrderDynamics()) {
                if (od.isNewOrder()) {
                    initializeOrderStats.put(new StringBuilder("new_ordered_pallet_count_").append(od.getArticleNumber()).toString(),
                            od.getOrderAmount() + initializeOrderStats.get(new StringBuilder("new_ordered_pallet_count_").append(od.getArticleNumber()).toString()));
                    initializeOrderStats.put(new StringBuilder("new_ordered_pallet_count_overall").toString(),
                            od.getOrderAmount() + initializeOrderStats.get(new StringBuilder("new_ordered_pallet_count_overall").toString()));
                    od.setNewOrder(false);





                    initializeOrderStats.put(new StringBuilder("fix_order_costs").toString(), (od.getOrder().getFixCosts()) + (initializeOrderStats.get("fix_order_costs")).intValue());
                    initializeOrderStats.put(new StringBuilder("variable_order_costs").toString(), ((od.getOrder().getDeliveryCosts() * od.getOrder().getOrderAmount()) + (initializeOrderStats.get("variable_order_costs")).intValue()));
                    initializeOrderStats.put(new StringBuilder("variable_order_costs_").append(od.getArticleNumber()).toString(), (od.getOrder().getDeliveryCosts() * od.getOrder().getOrderAmount()) + (initializeOrderStats.get(new StringBuilder("variable_order_costs_").append(od.getArticleNumber()).toString()).intValue()));

                }
                if (!od.isComplete()) {
                    initializeOrderStats.put(new StringBuilder("ordered_pallet_count_").append(od.getArticleNumber()).toString(),
                            od.getOrderAmount() + initializeOrderStats.get(new StringBuilder("ordered_pallet_count_").append(od.getArticleNumber()).toString()));
                    initializeOrderStats.put(new StringBuilder("ordered_pallet_count_overall").toString(),
                            od.getOrderAmount() + initializeOrderStats.get(new StringBuilder("ordered_pallet_count_overall").toString()));
                }



            }


            // ptc.calculateArticleValues(gameInfo);

            for (Field f : ptc.getAaos().getClass().getDeclaredFields()) {
                if (stats.get(f.getName()) != null && !Double.isNaN(stats.get(f.getName()))) {
                    try {
                        f.setAccessible(true);
                        f.set(ptc.getAaos(), stats.get(f.getName()));
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (initializeOrderStats.get(f.getName()) != null && !Double.isNaN(initializeOrderStats.get(f.getName()))) {
                    try {
                        f.setAccessible(true);
                        f.set(ptc.getAaos(), initializeOrderStats.get(f.getName()));
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(EmployeeCalculator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }


            if (gameInfo.getCurrentState().getRoundNumber() != 10) {
                ptc.getAaos().setGameInfo(gameInfo);
                ptc.getAaos().setRoundNumber(gameInfo.getCurrentState().getRoundNumber());
                gameInfo.getArticleAndOrderStatisticses().add(ptc.getAaos());
            }





        }





    }

    @Override
    public void prepareNextRound(DoloresGameInfo gameInfo) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> getToUpdate() {
        return null;
    }

    public TreeMap<String, Integer> initalizeArticleInProcess() {


        TreeMap<String, Integer> process_stats = new TreeMap<String, Integer>();

        process_stats.put("process_pallet_count_overall", 0);
        process_stats.put("process_pallet_count_100101", 0);
        process_stats.put("process_pallet_count_100102", 0);
        process_stats.put("process_pallet_count_100103", 0);
        process_stats.put("process_pallet_count_100104", 0);

        process_stats.put("process_pallet_count_en", 0);
        process_stats.put("process_pallet_count_en_100101", 0);
        process_stats.put("process_pallet_count_en_100102", 0);
        process_stats.put("process_pallet_count_en_100103", 0);
        process_stats.put("process_pallet_count_en_100104", 0);

        process_stats.put("process_pallet_count_wv", 0);
        process_stats.put("process_pallet_count_wv_100101", 0);
        process_stats.put("process_pallet_count_wv_100102", 0);
        process_stats.put("process_pallet_count_wv_100103", 0);
        process_stats.put("process_pallet_count_wv_100104", 0);

        process_stats.put("process_pallet_count_la", 0);
        process_stats.put("process_pallet_count_la_100101", 0);
        process_stats.put("process_pallet_count_la_100102", 0);
        process_stats.put("process_pallet_count_la_100103", 0);
        process_stats.put("process_pallet_count_la_100104", 0);

        process_stats.put("process_pallet_count_wk", 0);
        process_stats.put("process_pallet_count_wk_100101", 0);
        process_stats.put("process_pallet_count_wk_100102", 0);
        process_stats.put("process_pallet_count_wk_100103", 0);
        process_stats.put("process_pallet_count_wk_100104", 0);

        process_stats.put("process_pallet_count_ve", 0);
        process_stats.put("process_pallet_count_ve_100101", 0);
        process_stats.put("process_pallet_count_ve_100102", 0);
        process_stats.put("process_pallet_count_ve_100103", 0);
        process_stats.put("process_pallet_count_ve_100104", 0);

        process_stats.put("used_pallets_overall", 0);
        process_stats.put("used_pallets_100101", 0);
        process_stats.put("used_pallets_100102", 0);
        process_stats.put("used_pallets_100103", 0);
        process_stats.put("used_pallets_100104", 0);



        return process_stats;
    }

    public TreeMap<String, Integer> initializeOrderStats() {
        TreeMap<String, Integer> stats = new TreeMap<String, Integer>();

        stats.put("ordered_pallet_count_overall", 0);
        stats.put("ordered_pallet_count_100101", 0);
        stats.put("ordered_pallet_count_100102", 0);
        stats.put("ordered_pallet_count_100103", 0);
        stats.put("ordered_pallet_count_100104", 0);

        stats.put("new_ordered_pallet_count_overall", 0);
        stats.put("new_ordered_pallet_count_100101", 0);
        stats.put("new_ordered_pallet_count_100102", 0);
        stats.put("new_ordered_pallet_count_100103", 0);
        stats.put("new_ordered_pallet_count_100104", 0);

        stats.put("ordered_but_not_delivered_overall", 0);
        stats.put("ordered_but_not_delivered_100101", 0);
        stats.put("ordered_but_not_delivered_100102", 0);
        stats.put("ordered_but_not_delivered_100103", 0);
        stats.put("ordered_but_not_delivered_100104", 0);


        stats.put("fix_order_costs", 0);
        stats.put("variable_order_costs", 0);
        stats.put("variable_order_costs_100101", 0);
        stats.put("variable_order_costs_100102", 0);
        stats.put("variable_order_costs_100103", 0);
        stats.put("variable_order_costs_100104", 0);

        return stats;
    }
}
