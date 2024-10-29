/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.statistics;

import flw.business.core.DoloresGameInfo;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author tilu
 */
@Entity
public class StateStatistics implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    private DoloresGameInfo gameInfo;
    private int roundNumberEntity;
    /*
     * PostPalletData
     */
    private int stock_value;
    private double company_value;
    private int stock_value_wk;
    private int stock_value_wv;
    private int stock_value_ve;
    private int stock_value_en;
    private int stock_value_la;
    private long pointInTime;
    private int costs_new_employees;
    private double stock_value_en_100101;
    private double stock_value_en_100102;
    private double stock_value_en_100103;
    private double stock_value_en_100104;
    private double stock_value_wv_100101;
    private double stock_value_wv_100102;
    private double stock_value_wv_100103;
    private double stock_value_wv_100104;
    private double stock_value_la_100101;
    private double stock_value_la_100102;
    private double stock_value_la_100103;
    private double stock_value_la_100104;
    private double stock_value_wk_100101;
    private double stock_value_wk_100102;
    private double stock_value_wk_100103;
    private double stock_value_wk_100104;
    private double stock_value_ve_100101;
    private double stock_value_ve_100102;
    private double stock_value_ve_100103;
    private double stock_value_ve_100104;
    private int pallets_delivery_prep;
    private int pallets_not_in_storage;
    private int overall_consumption;
    private double overall_reclamation_percentage;
    private double overall_reclamation_w_pallets;
    private double overall_reclamation_w_retrieval;
    private double overall_reclamation_w_delivered;
    private double overall_reclamation_damaged;
    private double overall_reclamation_e_en;
    private double overall_reclamation_e_ve;
    private double overall_reclamation_e_transport;
    private double overall_reclamation_e_la;
    private double current_ordered_pallets;
    private double current_order_costs;
    private double service_level;
    private int satisfaction;
    private int costs_abc;
    private int debit_interest_cost;
    private int ovt_en;
    private int ovt_wv;
    private int ovt_la;
    private int ovt_wk;
    private int ovt_ve;
    /*
     * --------------------------------
     */
    private int late_finished_jobs_pallet_count;
    private int late_finished_jobs_pallet_count_100101;
    private int late_finished_jobs_pallet_count_100102;
    private int late_finished_jobs_pallet_count_100103;
    private int late_finished_jobs_pallet_count_100104;
    private int late_finished_jobs;
    private int new_jobs;
    private int lateJobCount;
    private int freeStorageSpace;
    private boolean modul_order_amount;
    private int strategy_incoming;
    private double pmax;
    private boolean unit_security_devices_used;
    private boolean modul_safety_stock;
    private double overall_modul_costs;
    private double modul_safety_stock_costs;
    private double costs_round;
    private double income_conveyor_sale;
    private double deposit_interest;
    private double modul_reorder_level_costs;
    private int last_periode;
    private int strategy_storage;
    private String game_state;
    private double repair_duration;
    private boolean modul_look_in_storage;
    private double accountbalance;
    private boolean modul_reorder_level;
    private double itcosts;
    private int work_climate_invest;
    private double leq;
    private boolean modul_status_report;
    private int overall_finished_jobs;
    private int overall_finished_jobs_pallets;
    private boolean modul_status_report_costs;
    private int order_count;
    private double costs_usd;
    private double f_control_we;
    private double costs_qualification_measure;
    private double f_control_wa;
    private int strategy_outgoing;
    private int roundnumber;
    private double modul_order_amount_costs;
    private double modul_look_in_storage_costs;
    private double income_round;
    private double sales_income;
    private double sales_income_100101;
    private double sales_income_100102;
    private double sales_income_100103;
    private double sales_income_100104;
    private double workload_conveyors_la;
    private double storage_cost;
    private int pallets_transported_la_out;
    private int accurate_delivered_pallets;
    private int accurate_finished_jobs;
    private int not_transported_pallets_la_in;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DoloresGameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(DoloresGameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public int getRoundNumber() {
        return getRoundNumberEntity();
    }

    public void setRoundNumber(int roundNumber) {
        this.setRoundNumberEntity(roundNumber);
    }

    public void setPointInTime(long pointInTime) {
        this.pointInTime = pointInTime;
    }

    /**
     * @return the roundNumberEntity
     */
    public int getRoundNumberEntity() {
        return roundNumberEntity;
    }

    /**
     * @param roundNumberEntity the roundNumberEntity to set
     */
    public void setRoundNumberEntity(int roundNumberEntity) {
        this.roundNumberEntity = roundNumberEntity;
    }

    /**
     * @return the stock_value
     */
    public int getStock_value() {
        return stock_value;
    }

    /**
     * @param stock_value the stock_value to set
     */
    public void setStock_value(int stock_value) {
        this.stock_value = stock_value;
    }

    /**
     * @return the company_value
     */
    public double getCompany_value() {
        return company_value;
    }

    /**
     * @param company_value the company_value to set
     */
    public void setCompany_value(double company_value) {
        this.company_value = company_value;
    }

    /**
     * @return the stock_value_wk
     */
    public int getStock_value_wk() {
        return stock_value_wk;
    }

    /**
     * @param stock_value_wk the stock_value_wk to set
     */
    public void setStock_value_wk(int stock_value_wk) {
        this.stock_value_wk = stock_value_wk;
    }

    /**
     * @return the stock_value_wv
     */
    public int getStock_value_wv() {
        return stock_value_wv;
    }

    /**
     * @param stock_value_wv the stock_value_wv to set
     */
    public void setStock_value_wv(int stock_value_wv) {
        this.stock_value_wv = stock_value_wv;
    }

    /**
     * @return the stock_value_ve
     */
    public int getStock_value_ve() {
        return stock_value_ve;
    }

    /**
     * @param stock_value_ve the stock_value_ve to set
     */
    public void setStock_value_ve(int stock_value_ve) {
        this.stock_value_ve = stock_value_ve;
    }

    /**
     * @return the stock_value_en
     */
    public int getStock_value_en() {
        return stock_value_en;
    }

    /**
     * @param stock_value_en the stock_value_en to set
     */
    public void setStock_value_en(int stock_value_en) {
        this.stock_value_en = stock_value_en;
    }

    /**
     * @return the stock_value_la
     */
    public int getStock_value_la() {
        return stock_value_la;
    }

    /**
     * @param stock_value_la the stock_value_la to set
     */
    public void setStock_value_la(int stock_value_la) {
        this.stock_value_la = stock_value_la;
    }

    /**
     * @return the pointInTime
     */
    public long getPointInTime() {
        return pointInTime;
    }

    /**
     * @return the costs_new_employees
     */
    public int getCosts_new_employees() {
        return costs_new_employees;
    }

    /**
     * @param costs_new_employees the costs_new_employees to set
     */
    public void setCosts_new_employees(int costs_new_employees) {
        this.costs_new_employees = costs_new_employees;
    }

    /**
     * @return the stock_value_en_100101
     */
    public double getStock_value_en_100101() {
        return stock_value_en_100101;
    }

    /**
     * @param stock_value_en_100101 the stock_value_en_100101 to set
     */
    public void setStock_value_en_100101(double stock_value_en_100101) {
        this.stock_value_en_100101 = stock_value_en_100101;
    }

    /**
     * @return the stock_value_en_100102
     */
    public double getStock_value_en_100102() {
        return stock_value_en_100102;
    }

    /**
     * @param stock_value_en_100102 the stock_value_en_100102 to set
     */
    public void setStock_value_en_100102(double stock_value_en_100102) {
        this.stock_value_en_100102 = stock_value_en_100102;
    }

    /**
     * @return the stock_value_en_100103
     */
    public double getStock_value_en_100103() {
        return stock_value_en_100103;
    }

    /**
     * @param stock_value_en_100103 the stock_value_en_100103 to set
     */
    public void setStock_value_en_100103(double stock_value_en_100103) {
        this.stock_value_en_100103 = stock_value_en_100103;
    }

    /**
     * @return the stock_value_en_100104
     */
    public double getStock_value_en_100104() {
        return stock_value_en_100104;
    }

    /**
     * @param stock_value_en_100104 the stock_value_en_100104 to set
     */
    public void setStock_value_en_100104(double stock_value_en_100104) {
        this.stock_value_en_100104 = stock_value_en_100104;
    }

    /**
     * @return the stock_value_wv_100101
     */
    public double getStock_value_wv_100101() {
        return stock_value_wv_100101;
    }

    /**
     * @param stock_value_wv_100101 the stock_value_wv_100101 to set
     */
    public void setStock_value_wv_100101(double stock_value_wv_100101) {
        this.stock_value_wv_100101 = stock_value_wv_100101;
    }

    /**
     * @return the stock_value_wv_100102
     */
    public double getStock_value_wv_100102() {
        return stock_value_wv_100102;
    }

    /**
     * @param stock_value_wv_100102 the stock_value_wv_100102 to set
     */
    public void setStock_value_wv_100102(double stock_value_wv_100102) {
        this.stock_value_wv_100102 = stock_value_wv_100102;
    }

    /**
     * @return the stock_value_wv_100103
     */
    public double getStock_value_wv_100103() {
        return stock_value_wv_100103;
    }

    /**
     * @param stock_value_wv_100103 the stock_value_wv_100103 to set
     */
    public void setStock_value_wv_100103(double stock_value_wv_100103) {
        this.stock_value_wv_100103 = stock_value_wv_100103;
    }

    /**
     * @return the stock_value_wv_100104
     */
    public double getStock_value_wv_100104() {
        return stock_value_wv_100104;
    }

    /**
     * @param stock_value_wv_100104 the stock_value_wv_100104 to set
     */
    public void setStock_value_wv_100104(double stock_value_wv_100104) {
        this.stock_value_wv_100104 = stock_value_wv_100104;
    }

    /**
     * @return the stock_value_la_100101
     */
    public double getStock_value_la_100101() {
        return stock_value_la_100101;
    }

    /**
     * @param stock_value_la_100101 the stock_value_la_100101 to set
     */
    public void setStock_value_la_100101(double stock_value_la_100101) {
        this.stock_value_la_100101 = stock_value_la_100101;
    }

    /**
     * @return the stock_value_la_100102
     */
    public double getStock_value_la_100102() {
        return stock_value_la_100102;
    }

    /**
     * @param stock_value_la_100102 the stock_value_la_100102 to set
     */
    public void setStock_value_la_100102(double stock_value_la_100102) {
        this.stock_value_la_100102 = stock_value_la_100102;
    }

    /**
     * @return the stock_value_la_100103
     */
    public double getStock_value_la_100103() {
        return stock_value_la_100103;
    }

    /**
     * @param stock_value_la_100103 the stock_value_la_100103 to set
     */
    public void setStock_value_la_100103(double stock_value_la_100103) {
        this.stock_value_la_100103 = stock_value_la_100103;
    }

    /**
     * @return the stock_value_la_100104
     */
    public double getStock_value_la_100104() {
        return stock_value_la_100104;
    }

    /**
     * @param stock_value_la_100104 the stock_value_la_100104 to set
     */
    public void setStock_value_la_100104(double stock_value_la_100104) {
        this.stock_value_la_100104 = stock_value_la_100104;
    }

    /**
     * @return the stock_value_wk_100101
     */
    public double getStock_value_wk_100101() {
        return stock_value_wk_100101;
    }

    /**
     * @param stock_value_wk_100101 the stock_value_wk_100101 to set
     */
    public void setStock_value_wk_100101(double stock_value_wk_100101) {
        this.stock_value_wk_100101 = stock_value_wk_100101;
    }

    /**
     * @return the stock_value_wk_100102
     */
    public double getStock_value_wk_100102() {
        return stock_value_wk_100102;
    }

    /**
     * @param stock_value_wk_100102 the stock_value_wk_100102 to set
     */
    public void setStock_value_wk_100102(double stock_value_wk_100102) {
        this.stock_value_wk_100102 = stock_value_wk_100102;
    }

    /**
     * @return the stock_value_wk_100103
     */
    public double getStock_value_wk_100103() {
        return stock_value_wk_100103;
    }

    /**
     * @param stock_value_wk_100103 the stock_value_wk_100103 to set
     */
    public void setStock_value_wk_100103(double stock_value_wk_100103) {
        this.stock_value_wk_100103 = stock_value_wk_100103;
    }

    /**
     * @return the stock_value_wk_100104
     */
    public double getStock_value_wk_100104() {
        return stock_value_wk_100104;
    }

    /**
     * @param stock_value_wk_100104 the stock_value_wk_100104 to set
     */
    public void setStock_value_wk_100104(double stock_value_wk_100104) {
        this.stock_value_wk_100104 = stock_value_wk_100104;
    }

    /**
     * @return the stock_value_ve_100101
     */
    public double getStock_value_ve_100101() {
        return stock_value_ve_100101;
    }

    /**
     * @param stock_value_ve_100101 the stock_value_ve_100101 to set
     */
    public void setStock_value_ve_100101(double stock_value_ve_100101) {
        this.stock_value_ve_100101 = stock_value_ve_100101;
    }

    /**
     * @return the stock_value_ve_100102
     */
    public double getStock_value_ve_100102() {
        return stock_value_ve_100102;
    }

    /**
     * @param stock_value_ve_100102 the stock_value_ve_100102 to set
     */
    public void setStock_value_ve_100102(double stock_value_ve_100102) {
        this.stock_value_ve_100102 = stock_value_ve_100102;
    }

    /**
     * @return the stock_value_ve_100103
     */
    public double getStock_value_ve_100103() {
        return stock_value_ve_100103;
    }

    /**
     * @param stock_value_ve_100103 the stock_value_ve_100103 to set
     */
    public void setStock_value_ve_100103(double stock_value_ve_100103) {
        this.stock_value_ve_100103 = stock_value_ve_100103;
    }

    /**
     * @return the stock_value_ve_100104
     */
    public double getStock_value_ve_100104() {
        return stock_value_ve_100104;
    }

    /**
     * @param stock_value_ve_100104 the stock_value_ve_100104 to set
     */
    public void setStock_value_ve_100104(double stock_value_ve_100104) {
        this.stock_value_ve_100104 = stock_value_ve_100104;
    }

    /**
     * @return the pallets_delivery_prep
     */
    public int getPallets_delivery_prep() {
        return pallets_delivery_prep;
    }

    /**
     * @param pallets_delivery_prep the pallets_delivery_prep to set
     */
    public void setPallets_delivery_prep(int pallets_delivery_prep) {
        this.pallets_delivery_prep = pallets_delivery_prep;
    }

    /**
     * @return the pallets_not_in_storage
     */
    public int getPallets_not_in_storage() {
        return pallets_not_in_storage;
    }

    /**
     * @param pallets_not_in_storage the pallets_not_in_storage to set
     */
    public void setPallets_not_in_storage(int pallets_not_in_storage) {
        this.pallets_not_in_storage = pallets_not_in_storage;
    }

    /**
     * @return the overall_consumption
     */
    public int getOverall_consumption() {
        return overall_consumption;
    }

    /**
     * @param overall_consumption the overall_consumption to set
     */
    public void setOverall_consumption(int overall_consumption) {
        this.overall_consumption = overall_consumption;
    }

    /**
     * @return the overall_reclamation_percentage
     */
    public double getOverall_reclamation_percentage() {
        return overall_reclamation_percentage;
    }

    /**
     * @param overall_reclamation_percentage the overall_reclamation_percentage to set
     */
    public void setOverall_reclamation_percentage(double overall_reclamation_percentage) {
        this.overall_reclamation_percentage = overall_reclamation_percentage;
    }

    /**
     * @return the overall_reclamation_w_pallets
     */
    public double getOverall_reclamation_w_pallets() {
        return overall_reclamation_w_pallets;
    }

    /**
     * @param overall_reclamation_w_pallets the overall_reclamation_w_pallets to set
     */
    public void setOverall_reclamation_w_pallets(double overall_reclamation_w_pallets) {
        this.overall_reclamation_w_pallets = overall_reclamation_w_pallets;
    }

    /**
     * @return the overall_reclamation_w_retrieval
     */
    public double getOverall_reclamation_w_retrieval() {
        return overall_reclamation_w_retrieval;
    }

    /**
     * @param overall_reclamation_w_retrieval the overall_reclamation_w_retrieval to set
     */
    public void setOverall_reclamation_w_retrieval(double overall_reclamation_w_retrieval) {
        this.overall_reclamation_w_retrieval = overall_reclamation_w_retrieval;
    }

    /**
     * @return the overall_reclamation_w_delivered
     */
    public double getOverall_reclamation_w_delivered() {
        return overall_reclamation_w_delivered;
    }

    /**
     * @param overall_reclamation_w_delivered the overall_reclamation_w_delivered to set
     */
    public void setOverall_reclamation_w_delivered(double overall_reclamation_w_delivered) {
        this.overall_reclamation_w_delivered = overall_reclamation_w_delivered;
    }

    /**
     * @return the overall_reclamation_damaged
     */
    public double getOverall_reclamation_damaged() {
        return overall_reclamation_damaged;
    }

    /**
     * @param overall_reclamation_damaged the overall_reclamation_damaged to set
     */
    public void setOverall_reclamation_damaged(double overall_reclamation_damaged) {
        this.overall_reclamation_damaged = overall_reclamation_damaged;
    }

    /**
     * @return the overall_reclamation_e_en
     */
    public double getOverall_reclamation_e_en() {
        return overall_reclamation_e_en;
    }

    /**
     * @param overall_reclamation_e_en the overall_reclamation_e_en to set
     */
    public void setOverall_reclamation_e_en(double overall_reclamation_e_en) {
        this.overall_reclamation_e_en = overall_reclamation_e_en;
    }

    /**
     * @return the overall_reclamation_e_ve
     */
    public double getOverall_reclamation_e_ve() {
        return overall_reclamation_e_ve;
    }

    /**
     * @param overall_reclamation_e_ve the overall_reclamation_e_ve to set
     */
    public void setOverall_reclamation_e_ve(double overall_reclamation_e_ve) {
        this.overall_reclamation_e_ve = overall_reclamation_e_ve;
    }

    /**
     * @return the overall_reclamation_e_transport
     */
    public double getOverall_reclamation_e_transport() {
        return overall_reclamation_e_transport;
    }

    /**
     * @param overall_reclamation_e_transport the overall_reclamation_e_transport to set
     */
    public void setOverall_reclamation_e_transport(double overall_reclamation_e_transport) {
        this.overall_reclamation_e_transport = overall_reclamation_e_transport;
    }

    /**
     * @return the overall_reclamation_e_la
     */
    public double getOverall_reclamation_e_la() {
        return overall_reclamation_e_la;
    }

    /**
     * @param overall_reclamation_e_la the overall_reclamation_e_la to set
     */
    public void setOverall_reclamation_e_la(double overall_reclamation_e_la) {
        this.overall_reclamation_e_la = overall_reclamation_e_la;
    }

    /**
     * @return the current_ordered_pallets
     */
    public double getCurrent_ordered_pallets() {
        return current_ordered_pallets;
    }

    /**
     * @param current_ordered_pallets the current_ordered_pallets to set
     */
    public void setCurrent_ordered_pallets(double current_ordered_pallets) {
        this.current_ordered_pallets = current_ordered_pallets;
    }

    /**
     * @return the current_order_costs
     */
    public double getCurrent_order_costs() {
        return current_order_costs;
    }

    /**
     * @param current_order_costs the current_order_costs to set
     */
    public void setCurrent_order_costs(double current_order_costs) {
        this.current_order_costs = current_order_costs;
    }

    /**
     * @return the service_level
     */
    public double getService_level() {
        return service_level;
    }

    /**
     * @param service_level the service_level to set
     */
    public void setService_level(double service_level) {
        this.service_level = service_level;
    }

    /**
     * @return the satisfaction
     */
    public int getSatisfaction() {
        return satisfaction;
    }

    /**
     * @param satisfaction the satisfaction to set
     */
    public void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }

    /**
     * @return the costs_abc
     */
    public int getCosts_abc() {
        return costs_abc;
    }

    /**
     * @param costs_abc the costs_abc to set
     */
    public void setCosts_abc(int costs_abc) {
        this.costs_abc = costs_abc;
    }

    /**
     * @return the debit_interest_cost
     */
    public int getDebit_interest_cost() {
        return debit_interest_cost;
    }

    /**
     * @param debit_interest_cost the debit_interest_cost to set
     */
    public void setDebit_interest_cost(int debit_interest_cost) {
        this.debit_interest_cost = debit_interest_cost;
    }

    /**
     * @return the ovt_en
     */
    public int getOvt_en() {
        return ovt_en;
    }

    /**
     * @param ovt_en the ovt_en to set
     */
    public void setOvt_en(int ovt_en) {
        this.ovt_en = ovt_en;
    }

    /**
     * @return the ovt_wv
     */
    public int getOvt_wv() {
        return ovt_wv;
    }

    /**
     * @param ovt_wv the ovt_wv to set
     */
    public void setOvt_wv(int ovt_wv) {
        this.ovt_wv = ovt_wv;
    }

    /**
     * @return the ovt_la
     */
    public int getOvt_la() {
        return ovt_la;
    }

    /**
     * @param ovt_la the ovt_la to set
     */
    public void setOvt_la(int ovt_la) {
        this.ovt_la = ovt_la;
    }

    /**
     * @return the ovt_wk
     */
    public int getOvt_wk() {
        return ovt_wk;
    }

    /**
     * @param ovt_wk the ovt_wk to set
     */
    public void setOvt_wk(int ovt_wk) {
        this.ovt_wk = ovt_wk;
    }

    /**
     * @return the ovt_ve
     */
    public int getOvt_ve() {
        return ovt_ve;
    }

    /**
     * @param ovt_ve the ovt_ve to set
     */
    public void setOvt_ve(int ovt_ve) {
        this.ovt_ve = ovt_ve;
    }

    /**
     * @return the late_finished_jobs_pallet_count
     */
    public int getLate_finished_jobs_pallet_count() {
        return late_finished_jobs_pallet_count;
    }

    /**
     * @param late_finished_jobs_pallet_count the late_finished_jobs_pallet_count to set
     */
    public void setLate_finished_jobs_pallet_count(int late_finished_jobs_pallet_count) {
        this.late_finished_jobs_pallet_count = late_finished_jobs_pallet_count;
    }

    /**
     * @return the late_finished_jobs_pallet_count_100101
     */
    public int getLate_finished_jobs_pallet_count_100101() {
        return late_finished_jobs_pallet_count_100101;
    }

    /**
     * @param late_finished_jobs_pallet_count_100101 the late_finished_jobs_pallet_count_100101 to set
     */
    public void setLate_finished_jobs_pallet_count_100101(int late_finished_jobs_pallet_count_100101) {
        this.late_finished_jobs_pallet_count_100101 = late_finished_jobs_pallet_count_100101;
    }

    /**
     * @return the late_finished_jobs_pallet_count_100102
     */
    public int getLate_finished_jobs_pallet_count_100102() {
        return late_finished_jobs_pallet_count_100102;
    }

    /**
     * @param late_finished_jobs_pallet_count_100102 the late_finished_jobs_pallet_count_100102 to set
     */
    public void setLate_finished_jobs_pallet_count_100102(int late_finished_jobs_pallet_count_100102) {
        this.late_finished_jobs_pallet_count_100102 = late_finished_jobs_pallet_count_100102;
    }

    /**
     * @return the late_finished_jobs_pallet_count_100103
     */
    public int getLate_finished_jobs_pallet_count_100103() {
        return late_finished_jobs_pallet_count_100103;
    }

    /**
     * @param late_finished_jobs_pallet_count_100103 the late_finished_jobs_pallet_count_100103 to set
     */
    public void setLate_finished_jobs_pallet_count_100103(int late_finished_jobs_pallet_count_100103) {
        this.late_finished_jobs_pallet_count_100103 = late_finished_jobs_pallet_count_100103;
    }

    /**
     * @return the late_finished_jobs_pallet_count_100104
     */
    public int getLate_finished_jobs_pallet_count_100104() {
        return late_finished_jobs_pallet_count_100104;
    }

    /**
     * @param late_finished_jobs_pallet_count_100104 the late_finished_jobs_pallet_count_100104 to set
     */
    public void setLate_finished_jobs_pallet_count_100104(int late_finished_jobs_pallet_count_100104) {
        this.late_finished_jobs_pallet_count_100104 = late_finished_jobs_pallet_count_100104;
    }

    /**
     * @return the late_finished_jobs
     */
    public int getLate_finished_jobs() {
        return late_finished_jobs;
    }

    /**
     * @param late_finished_jobs the late_finished_jobs to set
     */
    public void setLate_finished_jobs(int late_finished_jobs) {
        this.late_finished_jobs = late_finished_jobs;
    }

    /**
     * @return the new_jobs
     */
    public int getNew_jobs() {
        return new_jobs;
    }

    /**
     * @param new_jobs the new_jobs to set
     */
    public void setNew_jobs(int new_jobs) {
        this.new_jobs = new_jobs;
    }

    /**
     * @return the lateJobCount
     */
    public int getLateJobCount() {
        return lateJobCount;
    }

    /**
     * @param lateJobCount the lateJobCount to set
     */
    public void setLateJobCount(int lateJobCount) {
        this.lateJobCount = lateJobCount;
    }

    /**
     * @return the freeStorageSpace
     */
    public int getFreeStorageSpace() {
        return freeStorageSpace;
    }

    /**
     * @param freeStorageSpace the freeStorageSpace to set
     */
    public void setFreeStorageSpace(int freeStorageSpace) {
        this.freeStorageSpace = freeStorageSpace;
    }

    /**
     * @return the modul_order_amount
     */
    public boolean isModul_order_amount() {
        return modul_order_amount;
    }

    /**
     * @param modul_order_amount the modul_order_amount to set
     */
    public void setModul_order_amount(boolean modul_order_amount) {
        this.modul_order_amount = modul_order_amount;
    }

    /**
     * @return the strategy_incoming
     */
    public int getStrategy_incoming() {
        return strategy_incoming;
    }

    /**
     * @param strategy_incoming the strategy_incoming to set
     */
    public void setStrategy_incoming(int strategy_incoming) {
        this.strategy_incoming = strategy_incoming;
    }

    /**
     * @return the pmax
     */
    public double getPmax() {
        return pmax;
    }

    /**
     * @param pmax the pmax to set
     */
    public void setPmax(double pmax) {
        this.pmax = pmax;
    }

    /**
     * @return the unit_security_devices_used
     */
    public boolean isUnit_security_devices_used() {
        return unit_security_devices_used;
    }

    /**
     * @param unit_security_devices_used the unit_security_devices_used to set
     */
    public void setUnit_security_devices_used(boolean unit_security_devices_used) {
        this.unit_security_devices_used = unit_security_devices_used;
    }

    /**
     * @return the modul_safety_stock
     */
    public boolean isModul_safety_stock() {
        return modul_safety_stock;
    }

    /**
     * @param modul_safety_stock the modul_safety_stock to set
     */
    public void setModul_safety_stock(boolean modul_safety_stock) {
        this.modul_safety_stock = modul_safety_stock;
    }

    /**
     * @return the overall_modul_costs
     */
    public double getOverall_modul_costs() {
        return overall_modul_costs;
    }

    /**
     * @param overall_modul_costs the overall_modul_costs to set
     */
    public void setOverall_modul_costs(double overall_modul_costs) {
        this.overall_modul_costs = overall_modul_costs;
    }

    /**
     * @return the modul_safety_stock_costs
     */
    public double getModul_safety_stock_costs() {
        return modul_safety_stock_costs;
    }

    /**
     * @param modul_safety_stock_costs the modul_safety_stock_costs to set
     */
    public void setModul_safety_stock_costs(double modul_safety_stock_costs) {
        this.modul_safety_stock_costs = modul_safety_stock_costs;
    }

    /**
     * @return the costs_round
     */
    public double getCosts_round() {
        return costs_round;
    }

    /**
     * @param costs_round the costs_round to set
     */
    public void setCosts_round(double costs_round) {
        this.costs_round = costs_round;
    }

    /**
     * @return the income_conveyor_sale
     */
    public double getIncome_conveyor_sale() {
        return income_conveyor_sale;
    }

    /**
     * @param income_conveyor_sale the income_conveyor_sale to set
     */
    public void setIncome_conveyor_sale(double income_conveyor_sale) {
        this.income_conveyor_sale = income_conveyor_sale;
    }

    /**
     * @return the deposit_interest
     */
    public double getDeposit_interest() {
        return deposit_interest;
    }

    /**
     * @param deposit_interest the deposit_interest to set
     */
    public void setDeposit_interest(double deposit_interest) {
        this.deposit_interest = deposit_interest;
    }

    /**
     * @return the modul_reorder_level_costs
     */
    public double getModul_reorder_level_costs() {
        return modul_reorder_level_costs;
    }

    /**
     * @param modul_reorder_level_costs the modul_reorder_level_costs to set
     */
    public void setModul_reorder_level_costs(double modul_reorder_level_costs) {
        this.modul_reorder_level_costs = modul_reorder_level_costs;
    }

    /**
     * @return the last_periode
     */
    public int getLast_periode() {
        return last_periode;
    }

    /**
     * @param last_periode the last_periode to set
     */
    public void setLast_periode(int last_periode) {
        this.last_periode = last_periode;
    }

    /**
     * @return the strategy_storage
     */
    public int getStrategy_storage() {
        return strategy_storage;
    }

    /**
     * @param strategy_storage the strategy_storage to set
     */
    public void setStrategy_storage(int strategy_storage) {
        this.strategy_storage = strategy_storage;
    }

    /**
     * @return the game_state
     */
    public String getGame_state() {
        return game_state;
    }

    /**
     * @param game_state the game_state to set
     */
    public void setGame_state(String game_state) {
        this.game_state = game_state;
    }

    /**
     * @return the repair_duration
     */
    public double getRepair_duration() {
        return repair_duration;
    }

    /**
     * @param repair_duration the repair_duration to set
     */
    public void setRepair_duration(double repair_duration) {
        this.repair_duration = repair_duration;
    }

    /**
     * @return the modul_look_in_storage
     */
    public boolean isModul_look_in_storage() {
        return modul_look_in_storage;
    }

    /**
     * @param modul_look_in_storage the modul_look_in_storage to set
     */
    public void setModul_look_in_storage(boolean modul_look_in_storage) {
        this.modul_look_in_storage = modul_look_in_storage;
    }

    /**
     * @return the accountbalance
     */
    public double getAccountbalance() {
        return accountbalance;
    }

    /**
     * @param accountbalance the accountbalance to set
     */
    public void setAccountbalance(double accountbalance) {
        this.accountbalance = accountbalance;
    }

    /**
     * @return the modul_reorder_level
     */
    public boolean isModul_reorder_level() {
        return modul_reorder_level;
    }

    /**
     * @param modul_reorder_level the modul_reorder_level to set
     */
    public void setModul_reorder_level(boolean modul_reorder_level) {
        this.modul_reorder_level = modul_reorder_level;
    }

    /**
     * @return the itcosts
     */
    public double getItcosts() {
        return itcosts;
    }

    /**
     * @param itcosts the itcosts to set
     */
    public void setItcosts(double itcosts) {
        this.itcosts = itcosts;
    }

    /**
     * @return the work_climate_invest
     */
    public int getWork_climate_invest() {
        return work_climate_invest;
    }

    /**
     * @param work_climate_invest the work_climate_invest to set
     */
    public void setWork_climate_invest(int work_climate_invest) {
        this.work_climate_invest = work_climate_invest;
    }

    /**
     * @return the leq
     */
    public double getLeq() {
        return leq;
    }

    /**
     * @param leq the leq to set
     */
    public void setLeq(double leq) {
        this.leq = leq;
    }

    /**
     * @return the modul_status_report
     */
    public boolean isModul_status_report() {
        return modul_status_report;
    }

    /**
     * @param modul_status_report the modul_status_report to set
     */
    public void setModul_status_report(boolean modul_status_report) {
        this.modul_status_report = modul_status_report;
    }

    /**
     * @return the overall_finished_jobs
     */
    public int getOverall_finished_jobs() {
        return overall_finished_jobs;
    }

    /**
     * @param overall_finished_jobs the overall_finished_jobs to set
     */
    public void setOverall_finished_jobs(int overall_finished_jobs) {
        this.overall_finished_jobs = overall_finished_jobs;
    }

    /**
     * @return the overall_finished_jobs_pallets
     */
    public int getOverall_finished_jobs_pallets() {
        return overall_finished_jobs_pallets;
    }

    /**
     * @param overall_finished_jobs_pallets the overall_finished_jobs_pallets to set
     */
    public void setOverall_finished_jobs_pallets(int overall_finished_jobs_pallets) {
        this.overall_finished_jobs_pallets = overall_finished_jobs_pallets;
    }

    /**
     * @return the modul_status_report_costs
     */
    public boolean isModul_status_report_costs() {
        return modul_status_report_costs;
    }

    /**
     * @param modul_status_report_costs the modul_status_report_costs to set
     */
    public void setModul_status_report_costs(boolean modul_status_report_costs) {
        this.modul_status_report_costs = modul_status_report_costs;
    }

    /**
     * @return the order_count
     */
    public int getOrder_count() {
        return order_count;
    }

    /**
     * @param order_count the order_count to set
     */
    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }

    /**
     * @return the costs_usd
     */
    public double getCosts_usd() {
        return costs_usd;
    }

    /**
     * @param costs_usd the costs_usd to set
     */
    public void setCosts_usd(double costs_usd) {
        this.costs_usd = costs_usd;
    }

    /**
     * @return the f_control_we
     */
    public double getF_control_we() {
        return f_control_we;
    }

    /**
     * @param f_control_we the f_control_we to set
     */
    public void setF_control_we(double f_control_we) {
        this.f_control_we = f_control_we;
    }

    /**
     * @return the costs_qualification_measure
     */
    public double getCosts_qualification_measure() {
        return costs_qualification_measure;
    }

    /**
     * @param costs_qualification_measure the costs_qualification_measure to set
     */
    public void setCosts_qualification_measure(double costs_qualification_measure) {
        this.costs_qualification_measure = costs_qualification_measure;
    }

    /**
     * @return the f_control_wa
     */
    public double getF_control_wa() {
        return f_control_wa;
    }

    /**
     * @param f_control_wa the f_control_wa to set
     */
    public void setF_control_wa(double f_control_wa) {
        this.f_control_wa = f_control_wa;
    }

    /**
     * @return the strategy_outgoing
     */
    public int getStrategy_outgoing() {
        return strategy_outgoing;
    }

    /**
     * @param strategy_outgoing the strategy_outgoing to set
     */
    public void setStrategy_outgoing(int strategy_outgoing) {
        this.strategy_outgoing = strategy_outgoing;
    }

    /**
     * @return the modul_order_amount_costs
     */
    public double getModul_order_amount_costs() {
        return modul_order_amount_costs;
    }

    /**
     * @param modul_order_amount_costs the modul_order_amount_costs to set
     */
    public void setModul_order_amount_costs(double modul_order_amount_costs) {
        this.modul_order_amount_costs = modul_order_amount_costs;
    }

    /**
     * @return the modul_look_in_storage_costs
     */
    public double getModul_look_in_storage_costs() {
        return modul_look_in_storage_costs;
    }

    /**
     * @param modul_look_in_storage_costs the modul_look_in_storage_costs to set
     */
    public void setModul_look_in_storage_costs(double modul_look_in_storage_costs) {
        this.modul_look_in_storage_costs = modul_look_in_storage_costs;
    }

    /**
     * @return the income_round
     */
    public double getIncome_round() {
        return income_round;
    }

    /**
     * @param income_round the income_round to set
     */
    public void setIncome_round(double income_round) {
        this.income_round = income_round;
    }

    /**
     * @return the sales_income
     */
    public double getSales_income() {
        return sales_income;
    }

    /**
     * @param sales_income the sales_income to set
     */
    public void setSales_income(double sales_income) {
        this.sales_income = sales_income;
    }

    /**
     * @return the sales_income_100101
     */
    public double getSales_income_100101() {
        return sales_income_100101;
    }

    /**
     * @param sales_income_100101 the sales_income_100101 to set
     */
    public void setSales_income_100101(double sales_income_100101) {
        this.sales_income_100101 = sales_income_100101;
    }

    /**
     * @return the sales_income_100102
     */
    public double getSales_income_100102() {
        return sales_income_100102;
    }

    /**
     * @param sales_income_100102 the sales_income_100102 to set
     */
    public void setSales_income_100102(double sales_income_100102) {
        this.sales_income_100102 = sales_income_100102;
    }

    /**
     * @return the sales_income_100103
     */
    public double getSales_income_100103() {
        return sales_income_100103;
    }

    /**
     * @param sales_income_100103 the sales_income_100103 to set
     */
    public void setSales_income_100103(double sales_income_100103) {
        this.sales_income_100103 = sales_income_100103;
    }

    /**
     * @return the sales_income_100104
     */
    public double getSales_income_100104() {
        return sales_income_100104;
    }

    /**
     * @param sales_income_100104 the sales_income_100104 to set
     */
    public void setSales_income_100104(double sales_income_100104) {
        this.sales_income_100104 = sales_income_100104;
    }

    /**
     * @return the workload_conveyors_la
     */
    public double getWorkload_conveyors_la() {
        return workload_conveyors_la;
    }

    /**
     * @param workload_conveyors_la the workload_conveyors_la to set
     */
    public void setWorkload_conveyors_la(double workload_conveyors_la) {
        this.workload_conveyors_la = workload_conveyors_la;
    }

    /**
     * @return the storage_cost
     */
    public double getStorage_cost() {
        return storage_cost;
    }

    /**
     * @param storage_cost the storage_cost to set
     */
    public void setStorage_cost(double storage_cost) {
        this.storage_cost = storage_cost;
    }

    /**
     * @return the pallets_transported_la_out
     */
    public int getPallets_transported_la_out() {
        return pallets_transported_la_out;
    }

    /**
     * @param pallets_transported_la_out the pallets_transported_la_out to set
     */
    public void setPallets_transported_la_out(int pallets_transported_la_out) {
        this.pallets_transported_la_out = pallets_transported_la_out;
    }

    /**
     * @return the accurate_delivered_pallets
     */
    public int getAccurate_delivered_pallets() {
        return accurate_delivered_pallets;
    }

    /**
     * @param accurate_delivered_pallets the accurate_delivered_pallets to set
     */
    public void setAccurate_delivered_pallets(int accurate_delivered_pallets) {
        this.accurate_delivered_pallets = accurate_delivered_pallets;
    }

    /**
     * @return the accurate_finished_jobs
     */
    public int getAccurate_finished_jobs() {
        return accurate_finished_jobs;
    }

    /**
     * @param accurate_finished_jobs the accurate_finished_jobs to set
     */
    public void setAccurate_finished_jobs(int accurate_finished_jobs) {
        this.accurate_finished_jobs = accurate_finished_jobs;
    }

    /**
     * @return the not_transported_pallets_la_in
     */
    public int getNot_transported_pallets_la_in() {
        return not_transported_pallets_la_in;
    }

    /**
     * @param not_transported_pallets_la_in the not_transported_pallets_la_in to set
     */
    public void setNot_transported_pallets_la_in(int not_transported_pallets_la_in) {
        this.not_transported_pallets_la_in = not_transported_pallets_la_in;
    }
}
