/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.util;

/**
 *
 * @author chmo
 */
public class DoloresConst {
    
    
    public static final String DOLORES_GAME_ROUND_NUMBER = "roundnumber";
    public static final String DOLORES_GAME_ACCOUNT_BALANCE = "accountbalance";
    //public static final String DOLORES_GAME_CUSOMTER_SATISFACTION = "customersatisfaction";
    public static final String DOLORES_GAME_STATUS_CHANGE_PERIODE = "status_change_periode";	//int
    public static final String DOLORES_GAME_LAST_PERIODE = "last_periode";			//Initializing needed, int
    public static final String DOLORES_GAME_STATE = "game_state";
    public static final int DOLORES_GAME_INITIAL_OVERTIME = 3;
    public static final int DOLORES_GAME_MAX_NUM_CRITICAL_STATE = 3;
    public static final String DOLORES_TODO = "todo";
    public static final int DOLORES_GAME_WORKING_TIME_PERIOD = 27000;
    public static final String DOLORES_KEY_OVERTIME = "ovt_";					//Initializing needed, int
    public static final String DOLORES_KEY_ORDER_COUNT = "order_count";
    public static final String DOLORES_KEY_ORDER_AMOUNT = "order_amount";
    public static final String DOLORES_KEY_WORK_CLIMATE_INVEST = "work_climate_invest";
    public static final String DOLORES_KEY_CUSTOMER_SATISFACTION = "satisfaction";
    public static final String DOLORES_KEY_PMAX = "pmax";
    public static final String DOLORES_KEY_LOADING_EQUIPMENT_COSTS = "leq";
    public static final String DOLORES_KEY_IT_COSTS = "itcosts";
    public static final String DOLORES_KEY_UNIT_SECURITY_DEVICES_USED = "unit_security_devices_used"; //boolean
    public static final String DOLORES_KEY_FACTOR_PALLET_CONTROL_WE = "f_control_we";
    public static final String DOLORES_KEY_FACTOR_PALLET_CONTROL_WA = "f_control_wa";
    public static final String DOLORES_KEY_ACCURATE_DELIVERED_PALLETS = "accurate_delivered_pallets";
    public static final String DOLORES_KEY_ACCURATE_FINISHED_CUSTOMER_JOBS = "accurate_finished_jobs";
    public static final String DOLORES_KEY_LATE_FINISHED_CUSTOMER_JOBS = "late_finished_jobs";
    public static final String DOLORES_KEY_OVERALL_FINISHED_JOBS = "overall_finished_jobs";
    public static final String DOLORES_KEY_OVERALL_FINISHED_JOBS_PALLETS = "overall_finished_jobs_pallets";
    public static final String[] DOLORES_KEY_STOCK_VALUE = {"stock_value_en", "stock_value_wv", "stock_value_la", "stock_value_wk", "stock_value_ve"};
    public static final String DOLORES_KEY_OVERALL_STOCK_VALUE = "stock_value";
    public static final String DOLORES_KEY_COMPANY_VALUE = "company_value";
    public static final String DOLORES_KEY_PALLETS_IN_PREPARATION_FOR_DELIVERY = "pallets_delivery_prep";
    public static final String DOLORES_KEY_PALLETS_NOT_IN_STORAGE = "pallets_not_in_storage";
    public static final String DOLORES_KEY_OVERALL_CONSUMPTION = "overall_consumption";
    public static final String DOLORES_KEY_OVERALL_RECLAMATION_PERCENTAGE = "overall_reclamation_percentage";
    public static final String DOLORES_KEY_OVERALL_RECLAMATION_DAMAGED = "overall_reclamation_damaged";
    public static final String DOLORES_KEY_OVERALL_RECLAMATION_WRONG_DELIVERED = "overall_reclamation_w_delivered";
    public static final String DOLORES_KEY_OVERALL_RECLAMATION_WRONG_RETRIEVAL = "overall_reclamation_w_retrieval";
    public static final String DOLORES_KEY_OVERALL_RECLAMATION_WRONG_PALLETS = "overall_reclamation_w_pallets";
    public static final String DOLORES_KEY_OVERALL_RECLAMATION_ERROR_EN = "overall_reclamation_e_en";
    public static final String DOLORES_KEY_OVERALL_RECLAMATION_ERROR_LA = "overall_reclamation_e_la";
    public static final String DOLORES_KEY_OVERALL_RECLAMATION_ERROR_VE = "overall_reclamation_e_ve";
    public static final String DOLORES_KEY_OVERALL_RECLAMATION_ERROR_ON_TRANSPORT = "overall_reclamation_e_transport";
    public static final String DOLORES_KEY_CURRENT_ORDERED_AMOUNT = "current_ordered_pallets";
    public static final String DOLORES_KEY_CURRENT_ORDER_COSTS = "current_order_costs";
    public static final String DOLORES_KEY_SERVICE_LEVEL = "service_level"; //lieferbereitschaftsgrad
    public static final String DOLORES_KEY_OVERALL_MODUL_COSTS = "overall_modul_costs";
    public static final String DOLORES_KEY_NEXT_ABC_ANALYSIS_PERIODE = "abc_analysis_periode";
    public static final String DOLORES_KEY_NEXT_ABC_ZONING_PERIODE = "abc_zoning_periode";
    public static final String DOLORES_KEY_COSTS_UNIT_SECURITY_DEVICES = "costs_usd";	//0 wenn UNIT_SECURITY_DEVICES_USED = false -> int
    public static final String DOLORES_KEY_COSTS_SUM_CURRENT_ROUND = "costs_round";
    public static final String DOLORES_KEY_INCOME_SUM_CURRENT_ROUND = "income_round";
    
    public static final String DOLORES_KEY_STRATEGY_INCOMING_GOODS = "strategy_incoming"; //    0=arbitrarily, 1=route-optimized
    public static final String DOLORES_KEY_STRATEGY_OUTGOING_GOODS = "strategy_outgoing"; //    0=fifo, 1=lifo, 2=route-optimized, 3=arbitrarily
    public static final String DOLORES_KEY_STRATEGY_STORAGE = "strategy_storage";    //         0=arbitrarily, 1=fixed-Storage, 2=ABC
    

    public static final String DOLORES_KEY_MODUL_ORDER_AMOUNT_ENABLED = "modul_order_amount";       //MODUL BESTELLMENGE -> SET IN GUI?
    public static final String DOLORES_KEY_MODUL_ORDER_AMOUNT_COSTS = "modul_order_amount_costs";
    public static final String DOLORES_KEY_MODUL_REORDER_LEVEL_ENABLED = "modul_reorder_level";      //MODUL MELDEBESTAND -> SET IN GUI?
    public static final String DOLORES_KEY_MODUL_REORDER_LEVEL_COSTS = "modul_reorder_level_costs";
    public static final String DOLORES_KEY_MODUL_SAFETY_STOCK_ENABLED = "modul_safety_stock";      //MODUL SICHERHEITSBESTAND -> SET IN GUI?
    public static final String DOLORES_KEY_MODUL_SAFETY_STOCK_COSTS = "modul_safety_stock_costs";
    public static final String DOLORES_KEY_MODUL_LOOK_IN_STORAGE_ENABLED = "modul_look_in_storage";      //MODUL BLICK INS LAGER -> SET IN GUI?
    public static final String DOLORES_KEY_MODUL_LOOK_IN_STORAGE_COSTS = "modul_look_in_storage_costs";
    public static final String DOLORES_KEY_MODUL_STATUS_REPORT_ENABLED = "modul_status_report";      //MODUL STATUSBERICHT -> SET IN GUI?
    public static final String DOLORES_KEY_MODUL_STATUS_REPORT_COSTS = "modul_status_report_costs";
    
    
    
    public static final String DOLORES_KEY_COSTS_NEW_EMPLOYEES = "costs_new_employees";		    //SET IN GUI -> DOUBLE
    public static final String DOLORES_KEY_COSTS_QUALIFICATION_MEASURE = "costs_qualification_measure";	    //SET IN GUI -> "Kosten für weiterbildung" -> DOUBLE
    public static final String DOLORES_KEY_INCOME_CONVEYOR_SALE = "income_conveyor_sale";	    //SET IN GUI -> "Einnahmen durch verkauf von FM" -> DOUBLE

    public static final String DOLORES_KEY_ABC_ZONE_ROUND = "abc_zone_round";
    public static final String DOLORES_KEY_ABC_ANALYSIS_ROUND = "abc_analysis_round";
    public static final String DOLORES_KEY_BACKTO_BASIC = "back_to_basic_storage";
    public static final String DOLORES_KEY_BACK_TO_IT1 = "back_to_it_level1";
    public static final String DOLORES_KEY_BACK_TO_IT2 = "back_to_it_level2";
    
    public static final String[] DOLORES_KEYS_WORKERS_WORKLOAD = {"worker_workload_en", "worker_worload_we", "worker_workload_la", "worker_workload_wa", "worker_workload_ve"};
    public static final String[] DOLORES_KEYS_CONVEYORS_WORKLOAD = {"conveyor_workload_en", "conveyor_workload_la", "conveyor_workload_ve"};

    public static final double CONVEYOR_SCRAP_LIMIT = 20;
    public static final double CONVEYOR_BREAKDOWN_POSSIBLE_LIMIT = 40;
    public static final int CONVEYOR_STATE_OK = 0;
    public static final int CONVEYOR_STATE_BREAKDOWN = 1;
    public static final int CONVEYOR_STATE_SCRAP = 2;
    public static final String CONVEYOR_NOTIFICATION_GOT_SCRAP = "conveyor_scrap";
    public static final String CONVEYOR_NOTIFICATION_BREAKDOWN = "conveyor_breakdown";
    public static final String CONVEYOR_NOTIFICATION_CRITICAL_CONDITION = "conveyor_critical";
    public static final String CONVEYOR_NOTIFICATION_DELIVER = "conveyor_deliver";
    public static final double CONVEYOR_DISABILITY_FACTOR = 0.02d;
    public static final double COVEYOR_REPAIR_COST = 0.5;
    public static final double CONVEYOR_DAMAGE_WITH_MAINTENANCE = 2;
    public static final double CONVEYOR_DAMAGE_WITHOUT_MAINTENANCE = 5;
    public static final double CONVEYOR_FACTOR_RESALE = 0.85;
    public static final double CONVEYOR_CONDITIONS_CRITICAL_LIMIT = 0.3;

    public static final double SPEED_FACTOR_WITH_UNIT_SECURITY_DEVICES = 1d;
    public static final double SPEED_FACTOR_WITHOUT_UNIT_SECURITY_DEVICES = 0.9;
    public static final int TIME_TAKEUP_RELEASE = 5;

    public static final int EMPLOYEE_CONTRACT_FULL_TIME = 0;
    public static final int EMPLOYEE_CONTRACT_HALF_TIME = 1;
    public static final int EMPLOYEE_CONTRACT_TEMPORARY = 2;
    
    public static final int EMPLOYEE_COST_NEW_FULLTIME = 500;
    public static final int EMPLOYEE_COST_NEW_TEMPORARY = 200;      
    
    public static final String EMPLOYEE_NOTIFICATION_ILLEGAL_CONTRACT = "emp_illegalContract";
    public static final int EMPLOYEE_QUALIFICATION_FORKLIFT_PERMIT = 1;
    public static final int EMPLOYEE_QUALIFICATION_SECURITY_TRAINING = 2;
    public static final int EMPLOYEE_QUALIFICATION_QMSEMINAR = 4;
    public static final int EMPLOYEE_QUALIFICATION_FORKLIFT_PERMIT_INPROGRESS = 8;
    public static final int EMPLOYEE_QUALIFICATION_SECURITY_TRAINING_INPROGRESS = 16;
    public static final int EMPLOYEE_QUALIFICATION_QMSEMINAR_INPROGRESS = 32;
    public static final double EMPLOYEE_FACTOR_CYCLETIME_DEFAULT = 0.85;
    public static final double EMPLOYEE_FACTOR_CYCLETIME_FORKLIFT_PERMIT = 0.95;
    public static final double EMPLOYEE_FACTOR_CYCLETIME_SECURITY_TRAINING = 1.00;
    public static final double EMPLOYEE_FACTOR_CRASHCHANCE_DEFAULT = 0.1;
    public static final double EMPLOYEE_FACTOR_CRASHCHANCE_FORKLIFT_PERMIT = 0.05;
    public static final double EMPLOYEE_FACTOR_CRASHCHANCE_SECURITY_TRAINING = 0.01;
    public static final double EMPLOYEE_FACTOR_FAILURE_RATE_DEFAULT = 1.0;
    public static final double EMPLOYEE_FACTOR_FAILURE_RATE_QMSEMINAR = 0.5;
    public static final double EMPLOYEE_FACTOR_HALF_TIME = 0.6;
    public static final double EMPLOYEE_MOTIVATION_WARNING_BORDER = 0.5;
    public static final String PROCESS_NOTIFICATION_HAS_NO_CONVEYORS = "proc_no_conveyors";
    public static final String PROCESS_NOTIFICATION_IS_NOT_CONTROL_PROCESS = "proc_not_controlproc";
    public static final double EMPLOYEE_FACTOR_COMPENSATION = 0.3;
    public static final String EMPLOYEE_NOTIFICATION_LEAVING_AFTER_ROUND = "emp_leave";
    public static final String EMPLOYEE_NOTIFICATION_JOINING_IN_ROUND = "emp_join";
    public static final String EMPLOYEE_NOTIFICATION_MOTIVATION_PROBLEM = "emp_motivation";
    public static final String EMPLOYEE_NOTIFICATION_QM_DONE = "emp_qm_done";
    public static final String EMPLOYEE_NOTIFICATION_SECURITY_DONE = "emp_sec_done";
    public static final String EMPLOYEE_NOTIFICATION_FP_DONE = "emp_fp_done";

    //MOTIVATION FACTORS, IN FACTOR-ARRAYS INDEX 0 IS THE OVERALL INFLUENCE OF CURRENT FACTOR
    public static final Integer[] WORK_CLIMATE_INVEST = {0, 100, 250, 400, 550, 700};

    public static final double[] WORK_CLIMATE_FACTOR = {0.25, 0.8, 0.85, 0.9, 0.95, 1.0};

    public static final Double[] TEMPORARY_WORKER_MOTIVATION_FACTOR = {0.25, 1.0, 0.95, 0.9, 0.85, 0.8, 0.7};
    public static final double[] TEMPORARY_WORKER_FACTOR = {0.1, 0.2, 0.3, 0.4, 0.5};

    
    public static final int[] KENNZIFFER_LIEFERMENGENABWEICHUNG = {100, 0, 0, 0, 0, 0};
    public static final int[] LIEFERMENGENABWEICHUNG = {0, 5, 10, 15, 20, 25};
    
    public static final int[] KENNZIFFER_LIEFERTERMINABWEICHUNG = {100, 0, 0, 0};
    public static final int[] LIEFERTERMINABWEICHUNG = {0, 1, 2, 3};
    

    
    public static final double[] OVERTIME_FACTOR = {0.25, 1.0, 0.9, 0.8, 0.7};
    public static final int[] OVERTIME_BORDERS = {1, 2, 3};

    public static final int[] SALARY_BONUS_BORDERS = {5, 10, 15, 20, 25};
    public static final double[] SALARY_BONUS_MOTIVATION_FACTORS = {0.25, 0.8, 0.85, 0.88, 0.9, 0.95, 1.0};

    public static final int SALARY_NORMAL = 85;
    public static final int SALARY_WITH_FORKLIFT_PERMIT = 125;
    public static final int SALARY_WITH_FP_AND_SECURITY_TRAINING = 145;
    public static final int SALARY_WITH_QMSEMINAR = 110;
    public static final int SALARY_WITH_QMSEMINAR_AND_FP = 150;
    public static final int SALARY_WITH_QSEMINAR_FP_AND_SECURITY_TRAINING = 170;

    public static final double[] CUSTOMER_SATISFACTION_LEVEL_FACTOR = {-0.5, -0.4, -0.3, -0.3, -0.2, 0.0, 0.2, 0.4, 0.8, 1.0};
    public static final double[] CUSTOMER_SATISFACTION_LEVEL = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};

    public static final int PROCESS_UNLOADING = 0;
    public static final int PROCESS_WARENVEREINNAHMUNG = 1;
    public static final int PROCESS_STORAGE = 2;
    public static final int PROCESS_GOOD_CONTROL = 3;
    public static final int PROCESS_LOADING = 4;

    public static final int PALLET_INCREASE = 25;
    public static final int PALLET_CONTROL_TIME_STATIC_WE = 40;
    public static final int PALLET_CONTROL_TIME_DYNAMIC_WE = 150;
    public static final int PALLET_CONTROL_TIME_STATIC_WA = 20;
    public static final int PALLET_CONTROL_TIME_DYNAMIC_WA = 100;
    public static final int PALLET_UNIT_SECURITY_DEVICES_TIME = 50;

    public static final int[] AMOUNT_CLASSIFICATION = {6, 4, 3, 8, 8};

    public static final int[] LOADING_EQUIPMENT_COSTS = {0, 450, 800};
    public static final int[] LOADING_EQUIPMENT_CONDITION = {70, 80, 95};
    public static final double[] LOADING_EQUIPMENT_FACTOR = {0.9, 0.95, 0.99};
    public static final double[] LOADING_EQUIPMENT_CRASH_FACTOR = {1d - LOADING_EQUIPMENT_FACTOR[0], 1d - LOADING_EQUIPMENT_FACTOR[1], 1d - LOADING_EQUIPMENT_FACTOR[2]};

    public static final int[] IT_COSTS = {0, 850, 1300, 1600};
    public static final double[] IT_FACTOR = {0, 0.02, 0.05, 0.1};
    public static final double STORAGE_COST_FACTOR = 0.15;

    public static final double ERROR_NONE = 100;
    public static final double ERROR_DAMAGED = 10;
    public static final double ERROR_WRONG_DELIVERED = 10;
    public static final double ERROR_WRONG_RETRIEVEL = 10;
    public static final double ERROR_TRANSPORT_DAMAGE_EN = 10;
    public static final double ERROR_TRANSPORT_DAMAGE_LA = 10;
    public static final double ERROR_TRANSPORT_DAMAGE_VE = 10;
    public static final double ERROR_SUM = ERROR_NONE + ERROR_DAMAGED + ERROR_WRONG_DELIVERED + ERROR_WRONG_RETRIEVEL + ERROR_TRANSPORT_DAMAGE_EN + ERROR_TRANSPORT_DAMAGE_LA + ERROR_TRANSPORT_DAMAGE_VE;

    /*public static final double FACTOR_CONTROL_WE = 1d;
    public static final double FACTOR_CONTROL_WA = 1d;*/

    public static final String ORDER_NOTIFICATION_DELIVERED = "order_complete";
    public static final String ORDER_NOTIFICATION_HALF_DELIVERED = "order_half";
    public static final String ORDER_NOTIFICATION_LATE_DELIVERED = "order_late_complete";
    public static final String ORDER_NOTIFICATION_LATE_HALF_DELIVERED = "order_late_half";
    
    
                            
    
    
    
    public static final double GLOBAL_CRASH_FACTOR_WITH_LOADING_EQUIPMENT = 0.3d;
    public static final double GLOBAL_CRASH_FACTOR_WITH_UNIT_SECURITY_DEVICES = 0.3d;
    public static final double GLOBAL_CRASH_FACTOR_EMPLOYEE = 0.4d;

    public static final double PROBABILITY_CRASH_WITH_UNIT_SAFETY_DEVICES = 0d;
    public static final double PROBABILITY_CRASH_WITHOUT_UNIT_SAFETY_DEVICES = 0.05d;

    public static final int TIME_CRASH = 1200;

    public static final double WAY_EN_WV = 25d;
    public static final double WAY_VE_TRUCK = 25d;

    public static final String[] ERRORS = {"Schaden", "Falsche Anlieferung", "Falsche Auslagerung", "Transportschaden bei Entladung", "Transportschaden im Lager", "Transportschaden in Verladung", "-"};

    public static final int[] LIFT_LAYER_DURATION = {0, 5, 10, 15};

    public static final int ARTICLE_CONSUMPTION_HISTORY_TIME = 5;

    public static final int STOCK_CARRYING_COSTS = 8501;
    public static final double STOCK_CARRYING_FACTOR = 0.15;

    public static final double PROCESS_FACTOR_CAPACITY_IN = 0.5;
    public static final String DOLORES_KEY_STORAGE_FACTOR = "storage_factor";
    
    
    public static final int COSTS_ABC_ANALYSIS = 10000;
    public static final int COSTS_ABC_ZONING = 5000;
    public static final int COSTS_UNIT_SECURITY_DEVICES_PER_PALLET = 12;	//int

    public static final double FACTOR_DEBIT_INTEREST = 0.1;	//SOLLZINS
    public static final double FACTOR_CREDIT_INTEREST = 0.02;	//HABENZINS

    public static final int STORAGE_STOCK_GROUND_COUNT = 3072;

    public static final String[] PROCESSES_ABBREVATIONS = {"en", "wv", "la", "wk", "ve"};
    public static final String[] PROCESSES_WITH_CONVEYORS_ABBREVATIONS = {"en", "la", "ve"};

    public class GameState
    {
	public static final String GAME_STATE_OK = "ok";
	public static final String GAME_STATE_CRITICAL = "critical";
	public static final String GAME_STATE_END = "end";
    }
}
