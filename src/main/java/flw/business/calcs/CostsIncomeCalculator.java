/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.calcs;

import flw.business.core.DoloresGameInfo;
import flw.business.store.Article;
import flw.business.store.ArticleDynamics;
import flw.business.util.DoloresConst;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alpl
 */
public class CostsIncomeCalculator extends AbstractCalculator {

    //private PalletThroughputCalculator pallet;
    private PalletThroughputCalc pallet;
    private EmployeeCalculator employees;
    private ConveyorCalculator conveyors;

    public CostsIncomeCalculator(List<AbstractCalculator> lCalculators) {
        super(lCalculators);
    }

    @Override
    public void calculate(DoloresGameInfo gameInfo) {


        gameInfo.getCurrentState().setValue("sales_income", "0");
        gameInfo.getCurrentState().setValue("sales_income_100101", "0");
        gameInfo.getCurrentState().setValue("sales_income_100102", "0");
        gameInfo.getCurrentState().setValue("sales_income_100103", "0");
        gameInfo.getCurrentState().setValue("sales_income_100104", "0");


        for (AbstractCalculator abstractCalculator : lCalculators) {
            //if (abstractCalculator instanceof PalletThroughputCalculator) {
              //  pallet = (PalletThroughputCalculator) abstractCalculator;
            //} 
            if (abstractCalculator instanceof PalletThroughputCalc) {
                pallet = (PalletThroughputCalc) abstractCalculator;
            }
            else if (abstractCalculator instanceof EmployeeCalculator) {
                employees = (EmployeeCalculator) abstractCalculator;
            } else if (abstractCalculator instanceof ConveyorCalculator) {
                conveyors = (ConveyorCalculator) abstractCalculator;
            }
        }
        int overall_costs_of_modules = 0;
        if (Boolean.parseBoolean(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_ENABLED))) {
            overall_costs_of_modules += Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_MODUL_ORDER_AMOUNT_COSTS));
        }
        if (Boolean.parseBoolean(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_ENABLED))) {
            overall_costs_of_modules += Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_MODUL_REORDER_LEVEL_COSTS));
        }
        if (Boolean.parseBoolean(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_ENABLED))) {
            overall_costs_of_modules += Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_MODUL_SAFETY_STOCK_COSTS));
        }
        if (Boolean.parseBoolean(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_ENABLED))) {
            overall_costs_of_modules += Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_MODUL_STATUS_REPORT_COSTS));
        }
        if (Boolean.parseBoolean(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_ENABLED))) {
            overall_costs_of_modules += Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_MODUL_LOOK_IN_STORAGE_COSTS));
        }
        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_OVERALL_MODUL_COSTS, String.valueOf(overall_costs_of_modules));

        //Calculate Costs for ABC-Analysis and ABC-Zoning
        int abc_costs = 0;
        if (gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_NEXT_ABC_ANALYSIS_PERIODE) != null && Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_NEXT_ABC_ANALYSIS_PERIODE)) == Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER))) {
            abc_costs = DoloresConst.COSTS_ABC_ANALYSIS;
        }
        if (gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_NEXT_ABC_ZONING_PERIODE) != null && Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_NEXT_ABC_ZONING_PERIODE)) == Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER))) {
            abc_costs += DoloresConst.COSTS_ABC_ZONING;
        }

        gameInfo.getCurrentState().setValue("costs_abc", String.valueOf(abc_costs));

        int stock_keeping_costs = (int) Math.round(DoloresConst.STORAGE_COST_FACTOR * Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_STOCK_VALUE[2])));
        gameInfo.getCurrentState().setValue("storage_cost", String.valueOf(stock_keeping_costs));
        //Calculate Costs of delivered Orders
        int order_dynamic_costs = 0;
        int order_fix_costs = 0;
        double income = 0;
        int sales_income = 0;
        for (ArticleDynamics article : gameInfo.getCurrentState().getArticleDynamics()) {
            order_dynamic_costs += pallet.getOverallOrderDynamicCosts(article.getArticleNumber());
            order_fix_costs += pallet.getOverallOrderFixCosts(article.getArticleNumber());
            income += article.getSalesIncome();
            sales_income += article.getSalesIncome();


            gameInfo.getCurrentState().setValue(new StringBuilder("sales_income_").append(article.getArticleNumber()).toString(), String.valueOf(article.getSalesIncome() + Double.valueOf(gameInfo.getCurrentState().getValue(new StringBuilder("sales_income_").append(article.getArticleNumber()).toString()))));


        }
        int overall_order_costs = order_dynamic_costs + order_fix_costs;
        double kontostand = Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_GAME_ACCOUNT_BALANCE));
        /*kontostand -= (employees.getOverallCosts() + Integer.parseInt(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_COSTS_NEW_EMPLOYEES)))
                + conveyors.getOverallCosts() + Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_IT_COSTS))
                + Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_WORK_CLIMATE_INVEST)) + overall_costs_of_modules
                + Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_LOADING_EQUIPMENT_COSTS))
                + Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_COSTS_UNIT_SECURITY_DEVICES)) + abc_costs + stock_keeping_costs
                + order_dynamic_costs;*/

        double debit_interest = 0; //sollzins
        double credit_interest = 0; //habenzins
        if (kontostand < 0) {
            debit_interest = kontostand * DoloresConst.FACTOR_DEBIT_INTEREST * (-1); 
        } else {
            credit_interest = kontostand * DoloresConst.FACTOR_CREDIT_INTEREST;
        }
        
        gameInfo.getCurrentState().setValue("deposit_interest", String.valueOf(credit_interest));
        gameInfo.getCurrentState().setValue("debit_interest_cost", String.valueOf(debit_interest));
        
        double allCosts = employees.getOverallCosts() + conveyors.getOverallCosts() + Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_WORK_CLIMATE_INVEST)) 
                + Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_IT_COSTS)) + overall_costs_of_modules 
                + Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_LOADING_EQUIPMENT_COSTS)) 
                + Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_COSTS_UNIT_SECURITY_DEVICES)) 
                + abc_costs + stock_keeping_costs + overall_order_costs + debit_interest 
                + Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_COSTS_QUALIFICATION_MEASURE));
        
        double allIncome = income + credit_interest + Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_INCOME_CONVEYOR_SALE));
        gameInfo.getCurrentState().setValue("sales_income", String.valueOf(sales_income));
        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_COSTS_SUM_CURRENT_ROUND, String.valueOf(allCosts));
        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_INCOME_SUM_CURRENT_ROUND, String.valueOf(allIncome));
        
        kontostand+=allIncome;
        kontostand-=allCosts;
        kontostand = kontostand * 100;

        int kontostandInt = (int) kontostand;
        double temp = (double) kontostandInt;
        kontostand = temp / 100;

        gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_GAME_ACCOUNT_BALANCE, String.valueOf(kontostand));
        
        //System.out.println(kontostand+" in Runde: "+gameInfo.getCurrentState().getRoundNumber());

    }

    @Override
    public void prepareNextRound(DoloresGameInfo gameInfo) {
    }

    public List<Object> getToUpdate() {
        return new ArrayList<Object>(); //To change body of generated methods, choose Tools | Templates.
    }
}
