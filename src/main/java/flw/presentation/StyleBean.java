/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author tilu
 */
@ManagedBean(name = "stylePM")
@SessionScoped
public class StyleBean implements Serializable {

    private String image;
    private String style;
    private String localeStringToLowercase;

    @ManagedProperty(value = "#{gameInfoPM}")
    private GameInfoPM gameInfoPM;

    @ManagedProperty(value = "#{orderPM}")
    private OrderPM orderPM;

    @EJB
    private Service service;

    /**
     * Creates a new instance of OrderPM
     */
    public StyleBean() {

    }

    public void setGameInfoPM(GameInfoPM gameInfoPM) {
        this.gameInfoPM = gameInfoPM;
    }

    public void setOrderPM(OrderPM orderPM) {
        this.orderPM = orderPM;
    }

    public String getImage() {
        int artNr = 0;
        try {
            artNr = orderPM.getClickedArticleDyn().getArticleNumber();
        } catch (Exception e) {
            return "./../../resources/img/auspuff";
        }

        switch (artNr) {
            case 100101:
                image = "./../../resources/img/auspuff";

                break;
            case 100102:
                image = "./../../resources/img/bremsscheibe";
                break;
            case 100103:
                image = "./../../resources/img/rueckleuchte";
                break;
            case 100104:
                image = "./../../resources/img/stossdaempfer";
                break;
        }
        return image;
    }
    
    public String getConveyorDescription(int id){
        if(id==0){
            return "";
        }
        ResourceBundle bundle = ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        return bundle.getString("conv.description"+id);
    }
    
    public void setImage(String image) {
        this.image = image;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getLocaleStringToLowercase() {

        localeStringToLowercase = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString().toLowerCase();

        return localeStringToLowercase;
    }

    public void setLocaleStringToLowercase(String localeStringToLowercase) {
        this.localeStringToLowercase = localeStringToLowercase;
    }

}
