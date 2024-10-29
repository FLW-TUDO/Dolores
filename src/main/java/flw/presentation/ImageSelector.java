/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author tilu
 */
@ManagedBean(name = "ImagePm")
@SessionScoped
public class ImageSelector implements Serializable {

    private String localeStringToLowercase;

   

   

    /**
     * Creates a new instance of OrderPM
     */
    public ImageSelector() {

    }

    

    public String getLocaleStringToLowercase() {

        localeStringToLowercase = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString().toLowerCase();

        if("de_de".equals(localeStringToLowercase) || "de".equals(localeStringToLowercase) || "de-de".equals(localeStringToLowercase)) return ".jpg";
        
        return "_en.jpg";
        
        
    }

    public void setLocaleStringToLowercase(String localeStringToLowercase) {
        this.localeStringToLowercase = localeStringToLowercase;
    }

}
