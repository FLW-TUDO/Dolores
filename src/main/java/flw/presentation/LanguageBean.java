package flw.presentation;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

@SessionScoped
@Named
public class LanguageBean implements Serializable {

    //private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    private Locale locale;

    public LanguageBean() {

    }

    public void init() {
        if (locale == null) {
            locale = locale = new Locale("de");
        }

    }

    public Locale getLocale() {
        if (locale == null) {
            locale = new Locale("de");
        }
        return locale;
    }

    public String getLanguage() {
        if (locale == null) {
            locale = locale = new Locale("de");
        }
        return locale.getLanguage();
    }

    /**
     * Sets the current {@code Locale} for each user session
     *
     * @param language
     *
     */
    public void changeLanguage(String language) throws IOException {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/faces/index.xhtml");
    }

    public void changeLanguageMain(String language) throws IOException {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);

    }

    private String localeCode;

    private static Map<String, Object> countries;

    static {
        countries = new LinkedHashMap<String, Object>();
        countries.put("Deutsch", Locale.GERMAN);
        countries.put("English", Locale.ENGLISH); //label, value

    }

    public Map<String, Object> getCountriesInMap() {
        return countries;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.locale = new Locale(localeCode);
        this.localeCode = localeCode;
    }

    //value change event listener
    public void countryLocaleCodeChanged(ValueChangeEvent e) {

        String newLocaleValue = e.getNewValue().toString();

        //loop country map to compare the locale code
        for (Map.Entry<String, Object> entry : countries.entrySet()) {

            if (entry.getValue().toString().equals(newLocaleValue)) {

                FacesContext.getCurrentInstance()
                        .getViewRoot().setLocale((Locale) entry.getValue());

            }
        }
    }

}
