package ponei.elixe.settings;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import ponei.elixe.Elixe;

import java.io.File;

public class SettingsManager {
    private Elixe mod;
    private File cfgFile;
    private Configuration cfg;

    public SettingsManager(Elixe mod, File cfgFile) {
        this.mod = mod;
        this.cfgFile = cfgFile;
    }

    public void updateSettings(boolean save){
        cfg = new Configuration(cfgFile);
        if (save) {
            updateSpeedometer(true);
            cfg.save();
        } else {
            cfg.load();
            updateSpeedometer(false);
        }
    }

    private void updateSpeedometer(boolean save){
        Property prop;

        prop = cfg.get("speedometer", "enabled", true);
        if (save) {
            prop.setValue(mod.speedometer.enabled);
        }
        else {
            mod.speedometer.toggleMeter(prop.getBoolean());
        }

        prop = cfg.get("speedometer", "shadow", true);
        if (save) {
            prop.setValue(mod.speedometer.drawShadow);
        }
        else {
            mod.speedometer.drawShadow = prop.getBoolean();
        }

        prop = cfg.get("speedometer", "loglength", 30);
        if (save) {
            prop.setValue(mod.speedometer.logLength);
        }
        else {
            mod.speedometer.logLength = prop.getInt();
        }

        prop = cfg.get("speedometer", "height", 32);
        if (save) {
            prop.setValue(mod.speedometer.meterHei);
        }
        else {
            mod.speedometer.meterHei = prop.getInt();
        }

        prop = cfg.get("speedometer", "width", 100);
        if (save) {
            prop.setValue(mod.speedometer.meterWid);
        }
        else {
            mod.speedometer.meterWid = prop.getInt();
        }

        prop = cfg.get("speedometer", "y", 60);
        if (save) {
            prop.setValue(mod.speedometer.meterYOffset);
        }
        else {
            mod.speedometer.meterYOffset = prop.getInt();
        }

        prop = cfg.get("speedometer", "x", 120);
        if (save) {
            prop.setValue(mod.speedometer.meterXOffset);
        }
        else {
            mod.speedometer.meterXOffset = prop.getInt();
        }
    }
}
