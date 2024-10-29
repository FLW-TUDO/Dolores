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
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author tilu
 */
@Entity
public class PalletStatistics implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    private DoloresGameInfo gameInfo;
    private int roundNumber;
    //wahrscheinlichkeiten
    private double prob_damage;
    private double prob_transport_demage_en_with_les;
    private double prob_transport_demage_en_without_les;
    private double prob_transport_demage_la_with_les;
    private double prob_transport_demage_la_without_les;
    private double prob_transport_demage_ve_with_les;
    private double prob_transport_demage_ve_without_les;
    private double prob_wrong_delivery;
    private double prob_wrong_retrieval;
    private double true_speed_en_wousd;
    private double true_speed_en_wusd;
    private double true_speed_la_wousd;
    private double true_speed_la_wusd;
    private double true_speed_ve_wousd;
    private double true_speed_ve_wusd;
    private double stock_value_100101;
    private double stock_value_100102;
    private double stock_value_100103;
    private double stock_value_100104;
    private int estimated_pallets_en;
    private int estimated_pallets_la;
    private int estimated_pallets_la_in;
    private int estimated_pallets_la_out;
    private int estimated_pallets_ve;
    private int estimated_pallets_wk;
    private int estimated_pallets_wv;
    private int not_transported_pallets_en;
    private int not_transported_pallets_la;
    private int not_transported_pallets_la_in;
    private int not_transported_pallets_la_out;
    private int not_transported_pallets_ve;
    private int not_transported_pallets_wk;
    private int not_transported_pallets_wv;
    private int pallets_transported_en;
    private int pallets_transported_la;
    private int pallets_transported_la_in;
    private int pallets_transported_la_out;
    private int pallets_transported_ve;
    private int pallets_transported_wk;
    private int pallets_transported_wv;
    private int pallets_with_error_0;
    private int pallets_with_error_1;
    private int pallets_with_error_2;
    private int pallets_with_error_3;
    private int pallets_with_error_4;
    private int pallets_with_error_5;
    private int pallets_with_error_6;
    private int new_arrived_pallet_count_overall;
    private int new_arrived_pallet_count_100101;
    private int new_arrived_pallet_count_100102;
    private int new_arrived_pallet_count_100103;
    private int new_arrived_pallet_count_100104;

    
    private int order_costs_100101;
    private int order_costs_100102;
    private int order_costs_100103;
    private int order_costs_100104;
    private int order_fix_costs_100101;
    private int order_fix_costs_100102;
    private int order_fix_costs_100103;
    private int order_fix_costs_100104;
    
    
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
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    /**
     * @return the prob_damage
     */
    public double getProb_damage() {
        return prob_damage;
    }

    /**
     * @param prob_damage the prob_damage to set
     */
    public void setProb_damage(double prob_damage) {
        this.prob_damage = prob_damage;
    }

    /**
     * @return the prob_transport_demage_en_with_les
     */
    public double getProb_transport_demage_en_with_les() {
        return prob_transport_demage_en_with_les;
    }

    /**
     * @param prob_transport_demage_en_with_les the prob_transport_demage_en_with_les to set
     */
    public void setProb_transport_demage_en_with_les(double prob_transport_demage_en_with_les) {
        this.prob_transport_demage_en_with_les = prob_transport_demage_en_with_les;
    }

    /**
     * @return the prob_transport_demage_en_without_les
     */
    public double getProb_transport_demage_en_without_les() {
        return prob_transport_demage_en_without_les;
    }

    /**
     * @param prob_transport_demage_en_without_les the prob_transport_demage_en_without_les to set
     */
    public void setProb_transport_demage_en_without_les(double prob_transport_demage_en_without_les) {
        this.prob_transport_demage_en_without_les = prob_transport_demage_en_without_les;
    }

    /**
     * @return the prob_transport_demage_la_with_les
     */
    public double getProb_transport_demage_la_with_les() {
        return prob_transport_demage_la_with_les;
    }

    /**
     * @param prob_transport_demage_la_with_les the prob_transport_demage_la_with_les to set
     */
    public void setProb_transport_demage_la_with_les(double prob_transport_demage_la_with_les) {
        this.prob_transport_demage_la_with_les = prob_transport_demage_la_with_les;
    }

    /**
     * @return the prob_transport_demage_la_without_les
     */
    public double getProb_transport_demage_la_without_les() {
        return prob_transport_demage_la_without_les;
    }

    /**
     * @param prob_transport_demage_la_without_les the prob_transport_demage_la_without_les to set
     */
    public void setProb_transport_demage_la_without_les(double prob_transport_demage_la_without_les) {
        this.prob_transport_demage_la_without_les = prob_transport_demage_la_without_les;
    }

    /**
     * @return the prob_transport_demage_ve_with_les
     */
    public double getProb_transport_demage_ve_with_les() {
        return prob_transport_demage_ve_with_les;
    }

    /**
     * @param prob_transport_demage_ve_with_les the prob_transport_demage_ve_with_les to set
     */
    public void setProb_transport_demage_ve_with_les(double prob_transport_demage_ve_with_les) {
        this.prob_transport_demage_ve_with_les = prob_transport_demage_ve_with_les;
    }

    /**
     * @return the prob_transport_demage_ve_without_les
     */
    public double getProb_transport_demage_ve_without_les() {
        return prob_transport_demage_ve_without_les;
    }

    /**
     * @param prob_transport_demage_ve_without_les the prob_transport_demage_ve_without_les to set
     */
    public void setProb_transport_demage_ve_without_les(double prob_transport_demage_ve_without_les) {
        this.prob_transport_demage_ve_without_les = prob_transport_demage_ve_without_les;
    }

    /**
     * @return the prob_wrong_delivery
     */
    public double getProb_wrong_delivery() {
        return prob_wrong_delivery;
    }

    /**
     * @param prob_wrong_delivery the prob_wrong_delivery to set
     */
    public void setProb_wrong_delivery(double prob_wrong_delivery) {
        this.prob_wrong_delivery = prob_wrong_delivery;
    }

    /**
     * @return the prob_wrong_retrieval
     */
    public double getProb_wrong_retrieval() {
        return prob_wrong_retrieval;
    }

    /**
     * @param prob_wrong_retrieval the prob_wrong_retrieval to set
     */
    public void setProb_wrong_retrieval(double prob_wrong_retrieval) {
        this.prob_wrong_retrieval = prob_wrong_retrieval;
    }

    /**
     * @return the true_speed_en_wousd
     */
    public double getTrue_speed_en_wousd() {
        return true_speed_en_wousd;
    }

    /**
     * @param true_speed_en_wousd the true_speed_en_wousd to set
     */
    public void setTrue_speed_en_wousd(double true_speed_en_wousd) {
        this.true_speed_en_wousd = true_speed_en_wousd;
    }

    /**
     * @return the true_speed_en_wusd
     */
    public double getTrue_speed_en_wusd() {
        return true_speed_en_wusd;
    }

    /**
     * @param true_speed_en_wusd the true_speed_en_wusd to set
     */
    public void setTrue_speed_en_wusd(double true_speed_en_wusd) {
        this.true_speed_en_wusd = true_speed_en_wusd;
    }

    /**
     * @return the true_speed_la_wousd
     */
    public double getTrue_speed_la_wousd() {
        return true_speed_la_wousd;
    }

    /**
     * @param true_speed_la_wousd the true_speed_la_wousd to set
     */
    public void setTrue_speed_la_wousd(double true_speed_la_wousd) {
        this.true_speed_la_wousd = true_speed_la_wousd;
    }

    /**
     * @return the true_speed_la_wusd
     */
    public double getTrue_speed_la_wusd() {
        return true_speed_la_wusd;
    }

    /**
     * @param true_speed_la_wusd the true_speed_la_wusd to set
     */
    public void setTrue_speed_la_wusd(double true_speed_la_wusd) {
        this.true_speed_la_wusd = true_speed_la_wusd;
    }

    /**
     * @return the true_speed_ve_wousd
     */
    public double getTrue_speed_ve_wousd() {
        return true_speed_ve_wousd;
    }

    /**
     * @param true_speed_ve_wousd the true_speed_ve_wousd to set
     */
    public void setTrue_speed_ve_wousd(double true_speed_ve_wousd) {
        this.true_speed_ve_wousd = true_speed_ve_wousd;
    }

    /**
     * @return the true_speed_ve_wusd
     */
    public double getTrue_speed_ve_wusd() {
        return true_speed_ve_wusd;
    }

    /**
     * @param true_speed_ve_wusd the true_speed_ve_wusd to set
     */
    public void setTrue_speed_ve_wusd(double true_speed_ve_wusd) {
        this.true_speed_ve_wusd = true_speed_ve_wusd;
    }

    /**
     * @return the stock_value_100101
     */
    public double getStock_value_100101() {
        return stock_value_100101;
    }

    /**
     * @param stock_value_100101 the stock_value_100101 to set
     */
    public void setStock_value_100101(double stock_value_100101) {
        this.stock_value_100101 = stock_value_100101;
    }

    /**
     * @return the stock_value_100102
     */
    public double getStock_value_100102() {
        return stock_value_100102;
    }

    /**
     * @param stock_value_100102 the stock_value_100102 to set
     */
    public void setStock_value_100102(double stock_value_100102) {
        this.stock_value_100102 = stock_value_100102;
    }

    /**
     * @return the stock_value_100103
     */
    public double getStock_value_100103() {
        return stock_value_100103;
    }

    /**
     * @param stock_value_100103 the stock_value_100103 to set
     */
    public void setStock_value_100103(double stock_value_100103) {
        this.stock_value_100103 = stock_value_100103;
    }

    /**
     * @return the stock_value_100104
     */
    public double getStock_value_100104() {
        return stock_value_100104;
    }

    /**
     * @param stock_value_100104 the stock_value_100104 to set
     */
    public void setStock_value_100104(double stock_value_100104) {
        this.stock_value_100104 = stock_value_100104;
    }

    /**
     * @return the estimated_pallets_en
     */
    public int getEstimated_pallets_en() {
        return estimated_pallets_en;
    }

    /**
     * @param estimated_pallets_en the estimated_pallets_en to set
     */
    public void setEstimated_pallets_en(int estimated_pallets_en) {
        this.estimated_pallets_en = estimated_pallets_en;
    }

    /**
     * @return the estimated_pallets_la
     */
    public int getEstimated_pallets_la() {
        return estimated_pallets_la;
    }

    /**
     * @param estimated_pallets_la the estimated_pallets_la to set
     */
    public void setEstimated_pallets_la(int estimated_pallets_la) {
        this.estimated_pallets_la = estimated_pallets_la;
    }

    /**
     * @return the estimated_pallets_la_in
     */
    public int getEstimated_pallets_la_in() {
        return estimated_pallets_la_in;
    }

    /**
     * @param estimated_pallets_la_in the estimated_pallets_la_in to set
     */
    public void setEstimated_pallets_la_in(int estimated_pallets_la_in) {
        this.estimated_pallets_la_in = estimated_pallets_la_in;
    }

    /**
     * @return the estimated_pallets_la_out
     */
    public int getEstimated_pallets_la_out() {
        return estimated_pallets_la_out;
    }

    /**
     * @param estimated_pallets_la_out the estimated_pallets_la_out to set
     */
    public void setEstimated_pallets_la_out(int estimated_pallets_la_out) {
        this.estimated_pallets_la_out = estimated_pallets_la_out;
    }

    /**
     * @return the estimated_pallets_ve
     */
    public int getEstimated_pallets_ve() {
        return estimated_pallets_ve;
    }

    /**
     * @param estimated_pallets_ve the estimated_pallets_ve to set
     */
    public void setEstimated_pallets_ve(int estimated_pallets_ve) {
        this.estimated_pallets_ve = estimated_pallets_ve;
    }

    /**
     * @return the estimated_pallets_wk
     */
    public int getEstimated_pallets_wk() {
        return estimated_pallets_wk;
    }

    /**
     * @param estimated_pallets_wk the estimated_pallets_wk to set
     */
    public void setEstimated_pallets_wk(int estimated_pallets_wk) {
        this.estimated_pallets_wk = estimated_pallets_wk;
    }

    /**
     * @return the estimated_pallets_wv
     */
    public int getEstimated_pallets_wv() {
        return estimated_pallets_wv;
    }

    /**
     * @param estimated_pallets_wv the estimated_pallets_wv to set
     */
    public void setEstimated_pallets_wv(int estimated_pallets_wv) {
        this.estimated_pallets_wv = estimated_pallets_wv;
    }

    /**
     * @return the not_transported_pallets_en
     */
    public int getNot_transported_pallets_en() {
        return not_transported_pallets_en;
    }

    /**
     * @param not_transported_pallets_en the not_transported_pallets_en to set
     */
    public void setNot_transported_pallets_en(int not_transported_pallets_en) {
        this.not_transported_pallets_en = not_transported_pallets_en;
    }

    /**
     * @return the not_transported_pallets_la
     */
    public int getNot_transported_pallets_la() {
        return not_transported_pallets_la;
    }

    /**
     * @param not_transported_pallets_la the not_transported_pallets_la to set
     */
    public void setNot_transported_pallets_la(int not_transported_pallets_la) {
        this.not_transported_pallets_la = not_transported_pallets_la;
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

    /**
     * @return the not_transported_pallets_la_out
     */
    public int getNot_transported_pallets_la_out() {
        return not_transported_pallets_la_out;
    }

    /**
     * @param not_transported_pallets_la_out the not_transported_pallets_la_out to set
     */
    public void setNot_transported_pallets_la_out(int not_transported_pallets_la_out) {
        this.not_transported_pallets_la_out = not_transported_pallets_la_out;
    }

    /**
     * @return the not_transported_pallets_ve
     */
    public int getNot_transported_pallets_ve() {
        return not_transported_pallets_ve;
    }

    /**
     * @param not_transported_pallets_ve the not_transported_pallets_ve to set
     */
    public void setNot_transported_pallets_ve(int not_transported_pallets_ve) {
        this.not_transported_pallets_ve = not_transported_pallets_ve;
    }

    /**
     * @return the not_transported_pallets_wk
     */
    public int getNot_transported_pallets_wk() {
        return not_transported_pallets_wk;
    }

    /**
     * @param not_transported_pallets_wk the not_transported_pallets_wk to set
     */
    public void setNot_transported_pallets_wk(int not_transported_pallets_wk) {
        this.not_transported_pallets_wk = not_transported_pallets_wk;
    }

    /**
     * @return the not_transported_pallets_wv
     */
    public int getNot_transported_pallets_wv() {
        return not_transported_pallets_wv;
    }

    /**
     * @param not_transported_pallets_wv the not_transported_pallets_wv to set
     */
    public void setNot_transported_pallets_wv(int not_transported_pallets_wv) {
        this.not_transported_pallets_wv = not_transported_pallets_wv;
    }

    /**
     * @return the pallets_transported_en
     */
    public int getPallets_transported_en() {
        return pallets_transported_en;
    }

    /**
     * @param pallets_transported_en the pallets_transported_en to set
     */
    public void setPallets_transported_en(int pallets_transported_en) {
        this.pallets_transported_en = pallets_transported_en;
    }

    /**
     * @return the pallets_transported_la
     */
    public int getPallets_transported_la() {
        return pallets_transported_la;
    }

    /**
     * @param pallets_transported_la the pallets_transported_la to set
     */
    public void setPallets_transported_la(int pallets_transported_la) {
        this.pallets_transported_la = pallets_transported_la;
    }

    /**
     * @return the pallets_transported_la_in
     */
    public int getPallets_transported_la_in() {
        return pallets_transported_la_in;
    }

    /**
     * @param pallets_transported_la_in the pallets_transported_la_in to set
     */
    public void setPallets_transported_la_in(int pallets_transported_la_in) {
        this.pallets_transported_la_in = pallets_transported_la_in;
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
     * @return the pallets_transported_ve
     */
    public int getPallets_transported_ve() {
        return pallets_transported_ve;
    }

    /**
     * @param pallets_transported_ve the pallets_transported_ve to set
     */
    public void setPallets_transported_ve(int pallets_transported_ve) {
        this.pallets_transported_ve = pallets_transported_ve;
    }

    /**
     * @return the pallets_transported_wk
     */
    public int getPallets_transported_wk() {
        return pallets_transported_wk;
    }

    /**
     * @param pallets_transported_wk the pallets_transported_wk to set
     */
    public void setPallets_transported_wk(int pallets_transported_wk) {
        this.pallets_transported_wk = pallets_transported_wk;
    }

    /**
     * @return the pallets_transported_wv
     */
    public int getPallets_transported_wv() {
        return pallets_transported_wv;
    }

    /**
     * @param pallets_transported_wv the pallets_transported_wv to set
     */
    public void setPallets_transported_wv(int pallets_transported_wv) {
        this.pallets_transported_wv = pallets_transported_wv;
    }

    /**
     * @return the pallets_with_error_0
     */
    public int getPallets_with_error_0() {
        return pallets_with_error_0;
    }

    /**
     * @param pallets_with_error_0 the pallets_with_error_0 to set
     */
    public void setPallets_with_error_0(int pallets_with_error_0) {
        this.pallets_with_error_0 = pallets_with_error_0;
    }

    /**
     * @return the pallets_with_error_1
     */
    public int getPallets_with_error_1() {
        return pallets_with_error_1;
    }

    /**
     * @param pallets_with_error_1 the pallets_with_error_1 to set
     */
    public void setPallets_with_error_1(int pallets_with_error_1) {
        this.pallets_with_error_1 = pallets_with_error_1;
    }

    /**
     * @return the pallets_with_error_2
     */
    public int getPallets_with_error_2() {
        return pallets_with_error_2;
    }

    /**
     * @param pallets_with_error_2 the pallets_with_error_2 to set
     */
    public void setPallets_with_error_2(int pallets_with_error_2) {
        this.pallets_with_error_2 = pallets_with_error_2;
    }

    /**
     * @return the pallets_with_error_3
     */
    public int getPallets_with_error_3() {
        return pallets_with_error_3;
    }

    /**
     * @param pallets_with_error_3 the pallets_with_error_3 to set
     */
    public void setPallets_with_error_3(int pallets_with_error_3) {
        this.pallets_with_error_3 = pallets_with_error_3;
    }

    /**
     * @return the pallets_with_error_4
     */
    public int getPallets_with_error_4() {
        return pallets_with_error_4;
    }

    /**
     * @param pallets_with_error_4 the pallets_with_error_4 to set
     */
    public void setPallets_with_error_4(int pallets_with_error_4) {
        this.pallets_with_error_4 = pallets_with_error_4;
    }

    /**
     * @return the pallets_with_error_5
     */
    public int getPallets_with_error_5() {
        return pallets_with_error_5;
    }

    /**
     * @param pallets_with_error_5 the pallets_with_error_5 to set
     */
    public void setPallets_with_error_5(int pallets_with_error_5) {
        this.pallets_with_error_5 = pallets_with_error_5;
    }

    /**
     * @return the pallets_with_error_6
     */
    public int getPallets_with_error_6() {
        return pallets_with_error_6;
    }

    /**
     * @param pallets_with_error_6 the pallets_with_error_6 to set
     */
    public void setPallets_with_error_6(int pallets_with_error_6) {
        this.pallets_with_error_6 = pallets_with_error_6;
    }

    /**
     * @return the new_arrived_pallet_count_overall
     */
    public int getNew_arrived_pallet_count_overall() {
        return new_arrived_pallet_count_overall;
    }

    /**
     * @param new_arrived_pallet_count_overall the new_arrived_pallet_count_overall to set
     */
    public void setNew_arrived_pallet_count_overall(int new_arrived_pallet_count_overall) {
        this.new_arrived_pallet_count_overall = new_arrived_pallet_count_overall;
    }

    /**
     * @return the new_arrived_pallet_count_100101
     */
    public int getNew_arrived_pallet_count_100101() {
        return new_arrived_pallet_count_100101;
    }

    /**
     * @param new_arrived_pallet_count_100101 the new_arrived_pallet_count_100101 to set
     */
    public void setNew_arrived_pallet_count_100101(int new_arrived_pallet_count_100101) {
        this.new_arrived_pallet_count_100101 = new_arrived_pallet_count_100101;
    }

    /**
     * @return the new_arrived_pallet_count_100102
     */
    public int getNew_arrived_pallet_count_100102() {
        return new_arrived_pallet_count_100102;
    }

    /**
     * @param new_arrived_pallet_count_100102 the new_arrived_pallet_count_100102 to set
     */
    public void setNew_arrived_pallet_count_100102(int new_arrived_pallet_count_100102) {
        this.new_arrived_pallet_count_100102 = new_arrived_pallet_count_100102;
    }

    /**
     * @return the new_arrived_pallet_count_100103
     */
    public int getNew_arrived_pallet_count_100103() {
        return new_arrived_pallet_count_100103;
    }

    /**
     * @param new_arrived_pallet_count_100103 the new_arrived_pallet_count_100103 to set
     */
    public void setNew_arrived_pallet_count_100103(int new_arrived_pallet_count_100103) {
        this.new_arrived_pallet_count_100103 = new_arrived_pallet_count_100103;
    }

    /**
     * @return the new_arrived_pallet_count_100104
     */
    public int getNew_arrived_pallet_count_100104() {
        return new_arrived_pallet_count_100104;
    }

    /**
     * @param new_arrived_pallet_count_100104 the new_arrived_pallet_count_100104 to set
     */
    public void setNew_arrived_pallet_count_100104(int new_arrived_pallet_count_100104) {
        this.new_arrived_pallet_count_100104 = new_arrived_pallet_count_100104;
    }

    /**
     * @return the order_costs_100101
     */
    public int getOrder_costs_100101() {
        return order_costs_100101;
    }

    /**
     * @param order_costs_100101 the order_costs_100101 to set
     */
    public void setOrder_costs_100101(int order_costs_100101) {
        this.order_costs_100101 = order_costs_100101;
    }

    /**
     * @return the order_costs_100102
     */
    public int getOrder_costs_100102() {
        return order_costs_100102;
    }

    /**
     * @param order_costs_100102 the order_costs_100102 to set
     */
    public void setOrder_costs_100102(int order_costs_100102) {
        this.order_costs_100102 = order_costs_100102;
    }

    /**
     * @return the order_costs_100103
     */
    public int getOrder_costs_100103() {
        return order_costs_100103;
    }

    /**
     * @param order_costs_100103 the order_costs_100103 to set
     */
    public void setOrder_costs_100103(int order_costs_100103) {
        this.order_costs_100103 = order_costs_100103;
    }

    /**
     * @return the order_costs_100104
     */
    public int getOrder_costs_100104() {
        return order_costs_100104;
    }

    /**
     * @param order_costs_100104 the order_costs_100104 to set
     */
    public void setOrder_costs_100104(int order_costs_100104) {
        this.order_costs_100104 = order_costs_100104;
    }

    /**
     * @return the order_fix_costs_100101
     */
    public int getOrder_fix_costs_100101() {
        return order_fix_costs_100101;
    }

    /**
     * @param order_fix_costs_100101 the order_fix_costs_100101 to set
     */
    public void setOrder_fix_costs_100101(int order_fix_costs_100101) {
        this.order_fix_costs_100101 = order_fix_costs_100101;
    }

    /**
     * @return the order_fix_costs_100102
     */
    public int getOrder_fix_costs_100102() {
        return order_fix_costs_100102;
    }

    /**
     * @param order_fix_costs_100102 the order_fix_costs_100102 to set
     */
    public void setOrder_fix_costs_100102(int order_fix_costs_100102) {
        this.order_fix_costs_100102 = order_fix_costs_100102;
    }

    /**
     * @return the order_fix_costs_100103
     */
    public int getOrder_fix_costs_100103() {
        return order_fix_costs_100103;
    }

    /**
     * @param order_fix_costs_100103 the order_fix_costs_100103 to set
     */
    public void setOrder_fix_costs_100103(int order_fix_costs_100103) {
        this.order_fix_costs_100103 = order_fix_costs_100103;
    }

    /**
     * @return the order_fix_costs_100104
     */
    public int getOrder_fix_costs_100104() {
        return order_fix_costs_100104;
    }

    /**
     * @param order_fix_costs_100104 the order_fix_costs_100104 to set
     */
    public void setOrder_fix_costs_100104(int order_fix_costs_100104) {
        this.order_fix_costs_100104 = order_fix_costs_100104;
    }
}