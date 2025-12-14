package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Locale;

@Named  // reference in XHTML as #{localeBean}
@SessionScoped
public class LocaleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // default locale
    private Locale locale = Locale.ENGLISH;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        // update the current view
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    // called when a radio button is selected
    public void changeLocale(String lang) {
        switch (lang) {
            case "es":
                locale = new Locale("es");
                break;
            case "eus":
                locale = new Locale("eus");
                break;
            default:
                locale = Locale.ENGLISH;
        }
        // update the page with the new locale
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    // helper for radio buttons to store the selected language string
    public String getSelectedLanguage() {
        if (locale.getLanguage().equals("es")) return "es";
        if (locale.getLanguage().equals("eus")) return "eus";
        return "en";
    }

    public void setSelectedLanguage(String lang) {
        changeLocale(lang);
    }
}