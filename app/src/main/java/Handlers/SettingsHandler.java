package Handlers;

/**
 * Created by Chamod on 7/20/2016.
 */
public class SettingsHandler {
    private static SettingsHandler settingsHandler;
    public static SettingsHandler getInstance()
    {
        if(settingsHandler==null)settingsHandler=new SettingsHandler();
        return settingsHandler;
    }

    private SettingsHandler(){};

    private boolean notificationON=false;

    public boolean isNotificationON() {
        return notificationON;
    }

    public void setNotificationON(boolean notificationON) {
        this.notificationON = notificationON;
    }
}
