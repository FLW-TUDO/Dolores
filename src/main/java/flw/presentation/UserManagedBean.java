/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
import flw.business.business.UserService;
import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresPlayer;
import java.security.Principal;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author tilu
 */
@ManagedBean
@RequestScoped
public class UserManagedBean {

    @ManagedProperty(value = "#{gameInfoPM}")
    private GameInfoPM gameInfoPM;
    @EJB
    private Service service;

    @EJB
    private UserService userBean;
    private String email;
    private String password;
    private DoloresPlayer currentPlayer;
    private String username;

    /**
     * Creates a new instance of UserManagedBean
     */
    public UserManagedBean() {

    }

    /**
     * This method saves a new user
     */
    public void saveUser() {
        ResourceBundle bundle = ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        boolean res = false;
        if (email != null && password != null) {

            String role = "user";

            if (email.equals("admin")) {
                role = "admin";

            }

            res = userBean.addNewUser(email, password, role, false);

            email = "";
            password = "";
        }
        if (res) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("growl.newUser"), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().execute("newUserDialog.hide()");
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("growl.newUserError"), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setGameInfoPM(GameInfoPM gameInfoPM) {
        this.gameInfoPM = gameInfoPM;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context
                .getExternalContext().getRequest();
        Principal userPrincipal = request.getUserPrincipal();

        return userPrincipal.getName();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DoloresPlayer getCurrentPlayer() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context
                .getExternalContext().getRequest();
        Principal userPrincipal = request.getUserPrincipal();

        currentPlayer = userBean.retrieveCurrentPlayer(userPrincipal.getName());
        return currentPlayer;
    }
    
    public Collection<DoloresGameInfo> getSingleGames() { 
        //singleGames = selectedPlayer.getGames();
        Collection<DoloresGameInfo> singleGames = service.retriveSingleGames(getCurrentPlayer());
        return singleGames;
    }

    public void setCurrentPlayer(DoloresPlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    public String userLogout() {
        service.update(gameInfoPM.getInfo());
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

}
