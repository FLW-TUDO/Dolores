/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author tilu
 */
@ManagedBean(name = "menu")
@SessionScoped
public class MenuBean implements Serializable {

    public MenuBean() {
    }

    public String onStatistics() {
        return "statistics?faces-redirect=true";
    }
}
