/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.util;

/**
 *
 * @author tilu
 */
public class ExportObject {
    
    private String round;
    private String accountbalance;
    private String satisfaction;
    private String time;

    public ExportObject(String round, String accountbalance, String satisfaction, String time) {
        this.round = round;
        this.accountbalance = accountbalance;
        this.satisfaction = satisfaction;
        this.time = time;
        
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getAccountbalance() {
        return accountbalance + " €";
    }

    public void setAccountbalance(String accountbalance) {
        this.accountbalance = accountbalance;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    
    
}
