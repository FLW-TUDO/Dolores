/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.UserService;
import flw.business.core.DoloresPlayer;
import flw.business.util.RandomStringGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author tilu
 */
@SessionScoped
@Named
public class PlayerGenerator implements Serializable {

    @EJB
    private UserService userBean;
    private AdminBean admBean;
    int numPlayers;
    private boolean unlocked;
    private List<DoloresPlayer> players = new ArrayList<>();

    public PlayerGenerator() {
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public List<DoloresPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<DoloresPlayer> players) {
        this.players = players;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
    
    /**
     * Generates the number of player which is selected in generateAccounts.xhtml
     * @return redirect to generatedAccounts.xhtml
     */
    public String generatePlayers() {

        boolean res = true;
        String role = "user";
        String email = "";
        String password = "";
        for (int i = 0; i < numPlayers; i++) {
            res = false;
            while(!res){
                email = new StringBuilder().append("dolores_").append(RandomStringGenerator.generateRandomString(RandomStringGenerator.TYPE_UPPER_ONLY, 5)).toString();
                password = RandomStringGenerator.generateRandomPassword();
                res = userBean.addNewUser(email, password, role, unlocked);
            }
            players.add(new DoloresPlayer(email, password));
        }

        return "generatedAccounts.xhtml?faces-redirect=true";
    }
}
