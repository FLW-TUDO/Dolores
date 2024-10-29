/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author tilu
 */
@ManagedBean(name = "roundManagerPM")
@SessionScoped
public class RoundManagerPM implements Serializable {

    @EJB
    private Service service;
    @ManagedProperty(value = "#{gameInfoPM}")
    private GameInfoPM gameInfoPM;
    private int gameInfoId;

    public RoundManagerPM() {
    }

    public void setGameInfoPM(GameInfoPM gameInfoPM) {
        this.gameInfoPM = gameInfoPM;
    }

    public String nextRound() {

        return "main";
    }
}
