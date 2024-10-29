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
public class ArticleAndOrderStatistics implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    private DoloresGameInfo gameInfo;
    private int roundNumber;
    private int job_pallet_count_late_100101;
    private int job_pallet_count_late_100102;
    private int job_pallet_count_late_100103;
    private int job_pallet_count_late_100104;
    private int job_pallet_count_late_overall;
    private int job_pallet_count_new_overall;
    private int job_pallet_count_new_overall_100101;
    private int job_pallet_count_new_overall_100102;
    private int job_pallet_count_new_overall_100103;
    private int job_pallet_count_new_overall_100104;
    private int job_pallet_count_open_100101;
    private int job_pallet_count_open_100102;
    private int job_pallet_count_open_100103;
    private int job_pallet_count_open_100104;
    private int job_pallet_count_open_overall;
    private int job_pallet_count_overall;
    private int job_pallet_count_overall_100101;
    private int job_pallet_count_overall_100102;
    private int job_pallet_count_overall_100103;
    private int job_pallet_count_overall_100104;
    private int process_pallet_count_overall;
    private int process_pallet_count_100101;
    private int process_pallet_count_100102;
    private int process_pallet_count_100103;
    private int process_pallet_count_100104;
    private int process_pallet_count_en;
    private int process_pallet_count_en_100101;
    private int process_pallet_count_en_100102;
    private int process_pallet_count_en_100103;
    private int process_pallet_count_en_100104;
    private int process_pallet_count_wv;
    private int process_pallet_count_wv_100101;
    private int process_pallet_count_wv_100102;
    private int process_pallet_count_wv_100103;
    private int process_pallet_count_wv_100104;
    private int process_pallet_count_la;
    private int process_pallet_count_la_100101;
    private int process_pallet_count_la_100102;
    private int process_pallet_count_la_100103;
    private int process_pallet_count_la_100104;
    private int process_pallet_count_wk;
    private int process_pallet_count_wk_100101;
    private int process_pallet_count_wk_100102;
    private int process_pallet_count_wk_100103;
    private int process_pallet_count_wk_100104;
    private int process_pallet_count_ve;
    private int process_pallet_count_ve_100101;
    private int process_pallet_count_ve_100102;
    private int process_pallet_count_ve_100103;
    private int process_pallet_count_ve_100104;
    private int ordered_pallet_count_overall;
    private int ordered_pallet_count_100101;
    private int ordered_pallet_count_100102;
    private int ordered_pallet_count_100103;
    private int ordered_pallet_count_100104;
    private int new_ordered_pallet_count_overall;
    private int new_ordered_pallet_count_100101;
    private int new_ordered_pallet_count_100102;
    private int new_ordered_pallet_count_100103;
    private int new_ordered_pallet_count_100104;
    private int ordered_but_not_delivered_overall;
    private int ordered_but_not_delivered_100101;
    private int ordered_but_not_delivered_100102;
    private int ordered_but_not_delivered_100103;
    private int ordered_but_not_delivered_100104;
    private int used_pallets_overall;
    private int used_pallets_100101;
    private int used_pallets_100102;
    private int used_pallets_100103;
    private int used_pallets_100104;
    private double fix_order_costs;
    private double variable_order_costs;
    private double variable_order_costs_100101;
    private double variable_order_costs_100102;
    private double variable_order_costs_100103;
    private double variable_order_costs_100104;
   
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
     * @return the job_pallet_count_late_100101
     */
    public int getJob_pallet_count_late_100101() {
        return job_pallet_count_late_100101;
    }

    /**
     * @param job_pallet_count_late_100101 the job_pallet_count_late_100101 to set
     */
    public void setJob_pallet_count_late_100101(int job_pallet_count_late_100101) {
        this.job_pallet_count_late_100101 = job_pallet_count_late_100101;
    }

    /**
     * @return the job_pallet_count_late_100102
     */
    public int getJob_pallet_count_late_100102() {
        return job_pallet_count_late_100102;
    }

    /**
     * @param job_pallet_count_late_100102 the job_pallet_count_late_100102 to set
     */
    public void setJob_pallet_count_late_100102(int job_pallet_count_late_100102) {
        this.job_pallet_count_late_100102 = job_pallet_count_late_100102;
    }

    /**
     * @return the job_pallet_count_late_100103
     */
    public int getJob_pallet_count_late_100103() {
        return job_pallet_count_late_100103;
    }

    /**
     * @param job_pallet_count_late_100103 the job_pallet_count_late_100103 to set
     */
    public void setJob_pallet_count_late_100103(int job_pallet_count_late_100103) {
        this.job_pallet_count_late_100103 = job_pallet_count_late_100103;
    }

    /**
     * @return the job_pallet_count_late_100104
     */
    public int getJob_pallet_count_late_100104() {
        return job_pallet_count_late_100104;
    }

    /**
     * @param job_pallet_count_late_100104 the job_pallet_count_late_100104 to set
     */
    public void setJob_pallet_count_late_100104(int job_pallet_count_late_100104) {
        this.job_pallet_count_late_100104 = job_pallet_count_late_100104;
    }

    /**
     * @return the job_pallet_count_late_overall
     */
    public int getJob_pallet_count_late_overall() {
        return job_pallet_count_late_overall;
    }

    /**
     * @param job_pallet_count_late_overall the job_pallet_count_late_overall to set
     */
    public void setJob_pallet_count_late_overall(int job_pallet_count_late_overall) {
        this.job_pallet_count_late_overall = job_pallet_count_late_overall;
    }

    /**
     * @return the job_pallet_count_new_overall
     */
    public int getJob_pallet_count_new_overall() {
        return job_pallet_count_new_overall;
    }

    /**
     * @param job_pallet_count_new_overall the job_pallet_count_new_overall to set
     */
    public void setJob_pallet_count_new_overall(int job_pallet_count_new_overall) {
        this.job_pallet_count_new_overall = job_pallet_count_new_overall;
    }

    /**
     * @return the job_pallet_count_new_overall_100101
     */
    public int getJob_pallet_count_new_overall_100101() {
        return job_pallet_count_new_overall_100101;
    }

    /**
     * @param job_pallet_count_new_overall_100101 the job_pallet_count_new_overall_100101 to set
     */
    public void setJob_pallet_count_new_overall_100101(int job_pallet_count_new_overall_100101) {
        this.job_pallet_count_new_overall_100101 = job_pallet_count_new_overall_100101;
    }

    /**
     * @return the job_pallet_count_new_overall_100102
     */
    public int getJob_pallet_count_new_overall_100102() {
        return job_pallet_count_new_overall_100102;
    }

    /**
     * @param job_pallet_count_new_overall_100102 the job_pallet_count_new_overall_100102 to set
     */
    public void setJob_pallet_count_new_overall_100102(int job_pallet_count_new_overall_100102) {
        this.job_pallet_count_new_overall_100102 = job_pallet_count_new_overall_100102;
    }

    /**
     * @return the job_pallet_count_new_overall_100103
     */
    public int getJob_pallet_count_new_overall_100103() {
        return job_pallet_count_new_overall_100103;
    }

    /**
     * @param job_pallet_count_new_overall_100103 the job_pallet_count_new_overall_100103 to set
     */
    public void setJob_pallet_count_new_overall_100103(int job_pallet_count_new_overall_100103) {
        this.job_pallet_count_new_overall_100103 = job_pallet_count_new_overall_100103;
    }

    /**
     * @return the job_pallet_count_new_overall_100104
     */
    public int getJob_pallet_count_new_overall_100104() {
        return job_pallet_count_new_overall_100104;
    }

    /**
     * @param job_pallet_count_new_overall_100104 the job_pallet_count_new_overall_100104 to set
     */
    public void setJob_pallet_count_new_overall_100104(int job_pallet_count_new_overall_100104) {
        this.job_pallet_count_new_overall_100104 = job_pallet_count_new_overall_100104;
    }

    /**
     * @return the job_pallet_count_open_100101
     */
    public int getJob_pallet_count_open_100101() {
        return job_pallet_count_open_100101;
    }

    /**
     * @param job_pallet_count_open_100101 the job_pallet_count_open_100101 to set
     */
    public void setJob_pallet_count_open_100101(int job_pallet_count_open_100101) {
        this.job_pallet_count_open_100101 = job_pallet_count_open_100101;
    }

    /**
     * @return the job_pallet_count_open_100102
     */
    public int getJob_pallet_count_open_100102() {
        return job_pallet_count_open_100102;
    }

    /**
     * @param job_pallet_count_open_100102 the job_pallet_count_open_100102 to set
     */
    public void setJob_pallet_count_open_100102(int job_pallet_count_open_100102) {
        this.job_pallet_count_open_100102 = job_pallet_count_open_100102;
    }

    /**
     * @return the job_pallet_count_open_100103
     */
    public int getJob_pallet_count_open_100103() {
        return job_pallet_count_open_100103;
    }

    /**
     * @param job_pallet_count_open_100103 the job_pallet_count_open_100103 to set
     */
    public void setJob_pallet_count_open_100103(int job_pallet_count_open_100103) {
        this.job_pallet_count_open_100103 = job_pallet_count_open_100103;
    }

    /**
     * @return the job_pallet_count_open_100104
     */
    public int getJob_pallet_count_open_100104() {
        return job_pallet_count_open_100104;
    }

    /**
     * @param job_pallet_count_open_100104 the job_pallet_count_open_100104 to set
     */
    public void setJob_pallet_count_open_100104(int job_pallet_count_open_100104) {
        this.job_pallet_count_open_100104 = job_pallet_count_open_100104;
    }

    /**
     * @return the job_pallet_count_open_overall
     */
    public int getJob_pallet_count_open_overall() {
        return job_pallet_count_open_overall;
    }

    /**
     * @param job_pallet_count_open_overall the job_pallet_count_open_overall to set
     */
    public void setJob_pallet_count_open_overall(int job_pallet_count_open_overall) {
        this.job_pallet_count_open_overall = job_pallet_count_open_overall;
    }

    /**
     * @return the job_pallet_count_overall
     */
    public int getJob_pallet_count_overall() {
        return job_pallet_count_overall;
    }

    /**
     * @param job_pallet_count_overall the job_pallet_count_overall to set
     */
    public void setJob_pallet_count_overall(int job_pallet_count_overall) {
        this.job_pallet_count_overall = job_pallet_count_overall;
    }

    /**
     * @return the job_pallet_count_overall_100101
     */
    public int getJob_pallet_count_overall_100101() {
        return job_pallet_count_overall_100101;
    }

    /**
     * @param job_pallet_count_overall_100101 the job_pallet_count_overall_100101 to set
     */
    public void setJob_pallet_count_overall_100101(int job_pallet_count_overall_100101) {
        this.job_pallet_count_overall_100101 = job_pallet_count_overall_100101;
    }

    /**
     * @return the job_pallet_count_overall_100102
     */
    public int getJob_pallet_count_overall_100102() {
        return job_pallet_count_overall_100102;
    }

    /**
     * @param job_pallet_count_overall_100102 the job_pallet_count_overall_100102 to set
     */
    public void setJob_pallet_count_overall_100102(int job_pallet_count_overall_100102) {
        this.job_pallet_count_overall_100102 = job_pallet_count_overall_100102;
    }

    /**
     * @return the job_pallet_count_overall_100103
     */
    public int getJob_pallet_count_overall_100103() {
        return job_pallet_count_overall_100103;
    }

    /**
     * @param job_pallet_count_overall_100103 the job_pallet_count_overall_100103 to set
     */
    public void setJob_pallet_count_overall_100103(int job_pallet_count_overall_100103) {
        this.job_pallet_count_overall_100103 = job_pallet_count_overall_100103;
    }

    /**
     * @return the job_pallet_count_overall_100104
     */
    public int getJob_pallet_count_overall_100104() {
        return job_pallet_count_overall_100104;
    }

    /**
     * @param job_pallet_count_overall_100104 the job_pallet_count_overall_100104 to set
     */
    public void setJob_pallet_count_overall_100104(int job_pallet_count_overall_100104) {
        this.job_pallet_count_overall_100104 = job_pallet_count_overall_100104;
    }

    /**
     * @return the process_pallet_count_overall
     */
    public int getProcess_pallet_count_overall() {
        return process_pallet_count_overall;
    }

    /**
     * @param process_pallet_count_overall the process_pallet_count_overall to set
     */
    public void setProcess_pallet_count_overall(int process_pallet_count_overall) {
        this.process_pallet_count_overall = process_pallet_count_overall;
    }

    /**
     * @return the process_pallet_count_100101
     */
    public int getProcess_pallet_count_100101() {
        return process_pallet_count_100101;
    }

    /**
     * @param process_pallet_count_100101 the process_pallet_count_100101 to set
     */
    public void setProcess_pallet_count_100101(int process_pallet_count_100101) {
        this.process_pallet_count_100101 = process_pallet_count_100101;
    }

    /**
     * @return the process_pallet_count_100102
     */
    public int getProcess_pallet_count_100102() {
        return process_pallet_count_100102;
    }

    /**
     * @param process_pallet_count_100102 the process_pallet_count_100102 to set
     */
    public void setProcess_pallet_count_100102(int process_pallet_count_100102) {
        this.process_pallet_count_100102 = process_pallet_count_100102;
    }

    /**
     * @return the process_pallet_count_100103
     */
    public int getProcess_pallet_count_100103() {
        return process_pallet_count_100103;
    }

    /**
     * @param process_pallet_count_100103 the process_pallet_count_100103 to set
     */
    public void setProcess_pallet_count_100103(int process_pallet_count_100103) {
        this.process_pallet_count_100103 = process_pallet_count_100103;
    }

    /**
     * @return the process_pallet_count_100104
     */
    public int getProcess_pallet_count_100104() {
        return process_pallet_count_100104;
    }

    /**
     * @param process_pallet_count_100104 the process_pallet_count_100104 to set
     */
    public void setProcess_pallet_count_100104(int process_pallet_count_100104) {
        this.process_pallet_count_100104 = process_pallet_count_100104;
    }

    /**
     * @return the process_pallet_count_en
     */
    public int getProcess_pallet_count_en() {
        return process_pallet_count_en;
    }

    /**
     * @param process_pallet_count_en the process_pallet_count_en to set
     */
    public void setProcess_pallet_count_en(int process_pallet_count_en) {
        this.process_pallet_count_en = process_pallet_count_en;
    }

    /**
     * @return the process_pallet_count_en_100101
     */
    public int getProcess_pallet_count_en_100101() {
        return process_pallet_count_en_100101;
    }

    /**
     * @param process_pallet_count_en_100101 the process_pallet_count_en_100101 to set
     */
    public void setProcess_pallet_count_en_100101(int process_pallet_count_en_100101) {
        this.process_pallet_count_en_100101 = process_pallet_count_en_100101;
    }

    /**
     * @return the process_pallet_count_en_100102
     */
    public int getProcess_pallet_count_en_100102() {
        return process_pallet_count_en_100102;
    }

    /**
     * @param process_pallet_count_en_100102 the process_pallet_count_en_100102 to set
     */
    public void setProcess_pallet_count_en_100102(int process_pallet_count_en_100102) {
        this.process_pallet_count_en_100102 = process_pallet_count_en_100102;
    }

    /**
     * @return the process_pallet_count_en_100103
     */
    public int getProcess_pallet_count_en_100103() {
        return process_pallet_count_en_100103;
    }

    /**
     * @param process_pallet_count_en_100103 the process_pallet_count_en_100103 to set
     */
    public void setProcess_pallet_count_en_100103(int process_pallet_count_en_100103) {
        this.process_pallet_count_en_100103 = process_pallet_count_en_100103;
    }

    /**
     * @return the process_pallet_count_en_100104
     */
    public int getProcess_pallet_count_en_100104() {
        return process_pallet_count_en_100104;
    }

    /**
     * @param process_pallet_count_en_100104 the process_pallet_count_en_100104 to set
     */
    public void setProcess_pallet_count_en_100104(int process_pallet_count_en_100104) {
        this.process_pallet_count_en_100104 = process_pallet_count_en_100104;
    }

    /**
     * @return the process_pallet_count_wv
     */
    public int getProcess_pallet_count_wv() {
        return process_pallet_count_wv;
    }

    /**
     * @param process_pallet_count_wv the process_pallet_count_wv to set
     */
    public void setProcess_pallet_count_wv(int process_pallet_count_wv) {
        this.process_pallet_count_wv = process_pallet_count_wv;
    }

    /**
     * @return the process_pallet_count_wv_100101
     */
    public int getProcess_pallet_count_wv_100101() {
        return process_pallet_count_wv_100101;
    }

    /**
     * @param process_pallet_count_wv_100101 the process_pallet_count_wv_100101 to set
     */
    public void setProcess_pallet_count_wv_100101(int process_pallet_count_wv_100101) {
        this.process_pallet_count_wv_100101 = process_pallet_count_wv_100101;
    }

    /**
     * @return the process_pallet_count_wv_100102
     */
    public int getProcess_pallet_count_wv_100102() {
        return process_pallet_count_wv_100102;
    }

    /**
     * @param process_pallet_count_wv_100102 the process_pallet_count_wv_100102 to set
     */
    public void setProcess_pallet_count_wv_100102(int process_pallet_count_wv_100102) {
        this.process_pallet_count_wv_100102 = process_pallet_count_wv_100102;
    }

    /**
     * @return the process_pallet_count_wv_100103
     */
    public int getProcess_pallet_count_wv_100103() {
        return process_pallet_count_wv_100103;
    }

    /**
     * @param process_pallet_count_wv_100103 the process_pallet_count_wv_100103 to set
     */
    public void setProcess_pallet_count_wv_100103(int process_pallet_count_wv_100103) {
        this.process_pallet_count_wv_100103 = process_pallet_count_wv_100103;
    }

    /**
     * @return the process_pallet_count_wv_100104
     */
    public int getProcess_pallet_count_wv_100104() {
        return process_pallet_count_wv_100104;
    }

    /**
     * @param process_pallet_count_wv_100104 the process_pallet_count_wv_100104 to set
     */
    public void setProcess_pallet_count_wv_100104(int process_pallet_count_wv_100104) {
        this.process_pallet_count_wv_100104 = process_pallet_count_wv_100104;
    }

    /**
     * @return the process_pallet_count_la
     */
    public int getProcess_pallet_count_la() {
        return process_pallet_count_la;
    }

    /**
     * @param process_pallet_count_la the process_pallet_count_la to set
     */
    public void setProcess_pallet_count_la(int process_pallet_count_la) {
        this.process_pallet_count_la = process_pallet_count_la;
    }

    /**
     * @return the process_pallet_count_la_100101
     */
    public int getProcess_pallet_count_la_100101() {
        return process_pallet_count_la_100101;
    }

    /**
     * @param process_pallet_count_la_100101 the process_pallet_count_la_100101 to set
     */
    public void setProcess_pallet_count_la_100101(int process_pallet_count_la_100101) {
        this.process_pallet_count_la_100101 = process_pallet_count_la_100101;
    }

    /**
     * @return the process_pallet_count_la_100102
     */
    public int getProcess_pallet_count_la_100102() {
        return process_pallet_count_la_100102;
    }

    /**
     * @param process_pallet_count_la_100102 the process_pallet_count_la_100102 to set
     */
    public void setProcess_pallet_count_la_100102(int process_pallet_count_la_100102) {
        this.process_pallet_count_la_100102 = process_pallet_count_la_100102;
    }

    /**
     * @return the process_pallet_count_la_100103
     */
    public int getProcess_pallet_count_la_100103() {
        return process_pallet_count_la_100103;
    }

    /**
     * @param process_pallet_count_la_100103 the process_pallet_count_la_100103 to set
     */
    public void setProcess_pallet_count_la_100103(int process_pallet_count_la_100103) {
        this.process_pallet_count_la_100103 = process_pallet_count_la_100103;
    }

    /**
     * @return the process_pallet_count_la_100104
     */
    public int getProcess_pallet_count_la_100104() {
        return process_pallet_count_la_100104;
    }

    /**
     * @param process_pallet_count_la_100104 the process_pallet_count_la_100104 to set
     */
    public void setProcess_pallet_count_la_100104(int process_pallet_count_la_100104) {
        this.process_pallet_count_la_100104 = process_pallet_count_la_100104;
    }

    /**
     * @return the process_pallet_count_wk
     */
    public int getProcess_pallet_count_wk() {
        return process_pallet_count_wk;
    }

    /**
     * @param process_pallet_count_wk the process_pallet_count_wk to set
     */
    public void setProcess_pallet_count_wk(int process_pallet_count_wk) {
        this.process_pallet_count_wk = process_pallet_count_wk;
    }

    /**
     * @return the process_pallet_count_wk_100101
     */
    public int getProcess_pallet_count_wk_100101() {
        return process_pallet_count_wk_100101;
    }

    /**
     * @param process_pallet_count_wk_100101 the process_pallet_count_wk_100101 to set
     */
    public void setProcess_pallet_count_wk_100101(int process_pallet_count_wk_100101) {
        this.process_pallet_count_wk_100101 = process_pallet_count_wk_100101;
    }

    /**
     * @return the process_pallet_count_wk_100102
     */
    public int getProcess_pallet_count_wk_100102() {
        return process_pallet_count_wk_100102;
    }

    /**
     * @param process_pallet_count_wk_100102 the process_pallet_count_wk_100102 to set
     */
    public void setProcess_pallet_count_wk_100102(int process_pallet_count_wk_100102) {
        this.process_pallet_count_wk_100102 = process_pallet_count_wk_100102;
    }

    /**
     * @return the process_pallet_count_wk_100103
     */
    public int getProcess_pallet_count_wk_100103() {
        return process_pallet_count_wk_100103;
    }

    /**
     * @param process_pallet_count_wk_100103 the process_pallet_count_wk_100103 to set
     */
    public void setProcess_pallet_count_wk_100103(int process_pallet_count_wk_100103) {
        this.process_pallet_count_wk_100103 = process_pallet_count_wk_100103;
    }

    /**
     * @return the process_pallet_count_wk_100104
     */
    public int getProcess_pallet_count_wk_100104() {
        return process_pallet_count_wk_100104;
    }

    /**
     * @param process_pallet_count_wk_100104 the process_pallet_count_wk_100104 to set
     */
    public void setProcess_pallet_count_wk_100104(int process_pallet_count_wk_100104) {
        this.process_pallet_count_wk_100104 = process_pallet_count_wk_100104;
    }

    /**
     * @return the process_pallet_count_ve
     */
    public int getProcess_pallet_count_ve() {
        return process_pallet_count_ve;
    }

    /**
     * @param process_pallet_count_ve the process_pallet_count_ve to set
     */
    public void setProcess_pallet_count_ve(int process_pallet_count_ve) {
        this.process_pallet_count_ve = process_pallet_count_ve;
    }

    /**
     * @return the process_pallet_count_ve_100101
     */
    public int getProcess_pallet_count_ve_100101() {
        return process_pallet_count_ve_100101;
    }

    /**
     * @param process_pallet_count_ve_100101 the process_pallet_count_ve_100101 to set
     */
    public void setProcess_pallet_count_ve_100101(int process_pallet_count_ve_100101) {
        this.process_pallet_count_ve_100101 = process_pallet_count_ve_100101;
    }

    /**
     * @return the process_pallet_count_ve_100102
     */
    public int getProcess_pallet_count_ve_100102() {
        return process_pallet_count_ve_100102;
    }

    /**
     * @param process_pallet_count_ve_100102 the process_pallet_count_ve_100102 to set
     */
    public void setProcess_pallet_count_ve_100102(int process_pallet_count_ve_100102) {
        this.process_pallet_count_ve_100102 = process_pallet_count_ve_100102;
    }

    /**
     * @return the process_pallet_count_ve_100103
     */
    public int getProcess_pallet_count_ve_100103() {
        return process_pallet_count_ve_100103;
    }

    /**
     * @param process_pallet_count_ve_100103 the process_pallet_count_ve_100103 to set
     */
    public void setProcess_pallet_count_ve_100103(int process_pallet_count_ve_100103) {
        this.process_pallet_count_ve_100103 = process_pallet_count_ve_100103;
    }

    /**
     * @return the process_pallet_count_ve_100104
     */
    public int getProcess_pallet_count_ve_100104() {
        return process_pallet_count_ve_100104;
    }

    /**
     * @param process_pallet_count_ve_100104 the process_pallet_count_ve_100104 to set
     */
    public void setProcess_pallet_count_ve_100104(int process_pallet_count_ve_100104) {
        this.process_pallet_count_ve_100104 = process_pallet_count_ve_100104;
    }

    /**
     * @return the ordered_pallet_count_overall
     */
    public int getOrdered_pallet_count_overall() {
        return ordered_pallet_count_overall;
    }

    /**
     * @param ordered_pallet_count_overall the ordered_pallet_count_overall to set
     */
    public void setOrdered_pallet_count_overall(int ordered_pallet_count_overall) {
        this.ordered_pallet_count_overall = ordered_pallet_count_overall;
    }

    /**
     * @return the ordered_pallet_count_100101
     */
    public int getOrdered_pallet_count_100101() {
        return ordered_pallet_count_100101;
    }

    /**
     * @param ordered_pallet_count_100101 the ordered_pallet_count_100101 to set
     */
    public void setOrdered_pallet_count_100101(int ordered_pallet_count_100101) {
        this.ordered_pallet_count_100101 = ordered_pallet_count_100101;
    }

    /**
     * @return the ordered_pallet_count_100102
     */
    public int getOrdered_pallet_count_100102() {
        return ordered_pallet_count_100102;
    }

    /**
     * @param ordered_pallet_count_100102 the ordered_pallet_count_100102 to set
     */
    public void setOrdered_pallet_count_100102(int ordered_pallet_count_100102) {
        this.ordered_pallet_count_100102 = ordered_pallet_count_100102;
    }

    /**
     * @return the ordered_pallet_count_100103
     */
    public int getOrdered_pallet_count_100103() {
        return ordered_pallet_count_100103;
    }

    /**
     * @param ordered_pallet_count_100103 the ordered_pallet_count_100103 to set
     */
    public void setOrdered_pallet_count_100103(int ordered_pallet_count_100103) {
        this.ordered_pallet_count_100103 = ordered_pallet_count_100103;
    }

    /**
     * @return the ordered_pallet_count_100104
     */
    public int getOrdered_pallet_count_100104() {
        return ordered_pallet_count_100104;
    }

    /**
     * @param ordered_pallet_count_100104 the ordered_pallet_count_100104 to set
     */
    public void setOrdered_pallet_count_100104(int ordered_pallet_count_100104) {
        this.ordered_pallet_count_100104 = ordered_pallet_count_100104;
    }

    /**
     * @return the new_ordered_pallet_count_overall
     */
    public int getNew_ordered_pallet_count_overall() {
        return new_ordered_pallet_count_overall;
    }

    /**
     * @param new_ordered_pallet_count_overall the new_ordered_pallet_count_overall to set
     */
    public void setNew_ordered_pallet_count_overall(int new_ordered_pallet_count_overall) {
        this.new_ordered_pallet_count_overall = new_ordered_pallet_count_overall;
    }

    /**
     * @return the new_ordered_pallet_count_100101
     */
    public int getNew_ordered_pallet_count_100101() {
        return new_ordered_pallet_count_100101;
    }

    /**
     * @param new_ordered_pallet_count_100101 the new_ordered_pallet_count_100101 to set
     */
    public void setNew_ordered_pallet_count_100101(int new_ordered_pallet_count_100101) {
        this.new_ordered_pallet_count_100101 = new_ordered_pallet_count_100101;
    }

    /**
     * @return the new_ordered_pallet_count_100102
     */
    public int getNew_ordered_pallet_count_100102() {
        return new_ordered_pallet_count_100102;
    }

    /**
     * @param new_ordered_pallet_count_100102 the new_ordered_pallet_count_100102 to set
     */
    public void setNew_ordered_pallet_count_100102(int new_ordered_pallet_count_100102) {
        this.new_ordered_pallet_count_100102 = new_ordered_pallet_count_100102;
    }

    /**
     * @return the new_ordered_pallet_count_100103
     */
    public int getNew_ordered_pallet_count_100103() {
        return new_ordered_pallet_count_100103;
    }

    /**
     * @param new_ordered_pallet_count_100103 the new_ordered_pallet_count_100103 to set
     */
    public void setNew_ordered_pallet_count_100103(int new_ordered_pallet_count_100103) {
        this.new_ordered_pallet_count_100103 = new_ordered_pallet_count_100103;
    }

    /**
     * @return the new_ordered_pallet_count_100104
     */
    public int getNew_ordered_pallet_count_100104() {
        return new_ordered_pallet_count_100104;
    }

    /**
     * @param new_ordered_pallet_count_100104 the new_ordered_pallet_count_100104 to set
     */
    public void setNew_ordered_pallet_count_100104(int new_ordered_pallet_count_100104) {
        this.new_ordered_pallet_count_100104 = new_ordered_pallet_count_100104;
    }

    /**
     * @return the ordered_but_not_delivered_overall
     */
    public int getOrdered_but_not_delivered_overall() {
        return ordered_but_not_delivered_overall;
    }

    /**
     * @param ordered_but_not_delivered_overall the ordered_but_not_delivered_overall to set
     */
    public void setOrdered_but_not_delivered_overall(int ordered_but_not_delivered_overall) {
        this.ordered_but_not_delivered_overall = ordered_but_not_delivered_overall;
    }

    /**
     * @return the ordered_but_not_delivered_100101
     */
    public int getOrdered_but_not_delivered_100101() {
        return ordered_but_not_delivered_100101;
    }

    /**
     * @param ordered_but_not_delivered_100101 the ordered_but_not_delivered_100101 to set
     */
    public void setOrdered_but_not_delivered_100101(int ordered_but_not_delivered_100101) {
        this.ordered_but_not_delivered_100101 = ordered_but_not_delivered_100101;
    }

    /**
     * @return the ordered_but_not_delivered_100102
     */
    public int getOrdered_but_not_delivered_100102() {
        return ordered_but_not_delivered_100102;
    }

    /**
     * @param ordered_but_not_delivered_100102 the ordered_but_not_delivered_100102 to set
     */
    public void setOrdered_but_not_delivered_100102(int ordered_but_not_delivered_100102) {
        this.ordered_but_not_delivered_100102 = ordered_but_not_delivered_100102;
    }

    /**
     * @return the ordered_but_not_delivered_100103
     */
    public int getOrdered_but_not_delivered_100103() {
        return ordered_but_not_delivered_100103;
    }

    /**
     * @param ordered_but_not_delivered_100103 the ordered_but_not_delivered_100103 to set
     */
    public void setOrdered_but_not_delivered_100103(int ordered_but_not_delivered_100103) {
        this.ordered_but_not_delivered_100103 = ordered_but_not_delivered_100103;
    }

    /**
     * @return the ordered_but_not_delivered_100104
     */
    public int getOrdered_but_not_delivered_100104() {
        return ordered_but_not_delivered_100104;
    }

    /**
     * @param ordered_but_not_delivered_100104 the ordered_but_not_delivered_100104 to set
     */
    public void setOrdered_but_not_delivered_100104(int ordered_but_not_delivered_100104) {
        this.ordered_but_not_delivered_100104 = ordered_but_not_delivered_100104;
    }

    /**
     * @return the used_pallets_overall
     */
    public int getUsed_pallets_overall() {
        return used_pallets_overall;
    }

    /**
     * @param used_pallets_overall the used_pallets_overall to set
     */
    public void setUsed_pallets_overall(int used_pallets_overall) {
        this.used_pallets_overall = used_pallets_overall;
    }

    /**
     * @return the used_pallets_100101
     */
    public int getUsed_pallets_100101() {
        return used_pallets_100101;
    }

    /**
     * @param used_pallets_100101 the used_pallets_100101 to set
     */
    public void setUsed_pallets_100101(int used_pallets_100101) {
        this.used_pallets_100101 = used_pallets_100101;
    }

    /**
     * @return the used_pallets_100102
     */
    public int getUsed_pallets_100102() {
        return used_pallets_100102;
    }

    /**
     * @param used_pallets_100102 the used_pallets_100102 to set
     */
    public void setUsed_pallets_100102(int used_pallets_100102) {
        this.used_pallets_100102 = used_pallets_100102;
    }

    /**
     * @return the used_pallets_100103
     */
    public int getUsed_pallets_100103() {
        return used_pallets_100103;
    }

    /**
     * @param used_pallets_100103 the used_pallets_100103 to set
     */
    public void setUsed_pallets_100103(int used_pallets_100103) {
        this.used_pallets_100103 = used_pallets_100103;
    }

    /**
     * @return the used_pallets_100104
     */
    public int getUsed_pallets_100104() {
        return used_pallets_100104;
    }

    /**
     * @param used_pallets_100104 the used_pallets_100104 to set
     */
    public void setUsed_pallets_100104(int used_pallets_100104) {
        this.used_pallets_100104 = used_pallets_100104;
    }

    /**
     * @return the fix_order_costs
     */
    public double getFix_order_costs() {
        return fix_order_costs;
    }

    /**
     * @param fix_order_costs the fix_order_costs to set
     */
    public void setFix_order_costs(double fix_order_costs) {
        this.fix_order_costs = fix_order_costs;
    }

    /**
     * @return the variable_order_costs
     */
    public double getVariable_order_costs() {
        return variable_order_costs;
    }

    /**
     * @param variable_order_costs the variable_order_costs to set
     */
    public void setVariable_order_costs(double variable_order_costs) {
        this.variable_order_costs = variable_order_costs;
    }

    /**
     * @return the variable_order_costs_100101
     */
    public double getVariable_order_costs_100101() {
        return variable_order_costs_100101;
    }

    /**
     * @param variable_order_costs_100101 the variable_order_costs_100101 to set
     */
    public void setVariable_order_costs_100101(double variable_order_costs_100101) {
        this.variable_order_costs_100101 = variable_order_costs_100101;
    }

    /**
     * @return the variable_order_costs_100102
     */
    public double getVariable_order_costs_100102() {
        return variable_order_costs_100102;
    }

    /**
     * @param variable_order_costs_100102 the variable_order_costs_100102 to set
     */
    public void setVariable_order_costs_100102(double variable_order_costs_100102) {
        this.variable_order_costs_100102 = variable_order_costs_100102;
    }

    /**
     * @return the variable_order_costs_100103
     */
    public double getVariable_order_costs_100103() {
        return variable_order_costs_100103;
    }

    /**
     * @param variable_order_costs_100103 the variable_order_costs_100103 to set
     */
    public void setVariable_order_costs_100103(double variable_order_costs_100103) {
        this.variable_order_costs_100103 = variable_order_costs_100103;
    }

    /**
     * @return the variable_order_costs_100104
     */
    public double getVariable_order_costs_100104() {
        return variable_order_costs_100104;
    }

    /**
     * @param variable_order_costs_100104 the variable_order_costs_100104 to set
     */
    public void setVariable_order_costs_100104(double variable_order_costs_100104) {
        this.variable_order_costs_100104 = variable_order_costs_100104;
    }
}
