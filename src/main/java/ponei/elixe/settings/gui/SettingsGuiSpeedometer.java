package ponei.elixe.settings.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiSlider;
import ponei.elixe.Elixe;

import java.io.IOException;

public class SettingsGuiSpeedometer extends SettingsGui {
    private SettingsGuiMain parent;

    public SettingsGuiSpeedometer(Elixe mod, SettingsGuiMain parent) {
        this.mod = mod;
        this.parent = parent;
    }

    private GuiSlider meterWidth, meterHeight, meterLogLength;
    private GuiButtonExt meterEnabled, meterShadow, resetPosition, back;

    @Override
    public void initGui() {
        int widOffset = 75;
        meterEnabled = new GuiButtonExt(0, getCenter(widOffset), getHeight(0), 150, 20, "enabled: " + mod.speedometer.enabled);
        meterShadow = new GuiButtonExt(1, getCenter(widOffset), getHeight(1), 150, 20, "draw shadow: " + mod.speedometer.enabled);
        meterWidth = new GuiSlider(2, getCenter(widOffset), getHeight(2), 150, 20, "width: ", "px", 2, 400, mod.speedometer.meterWid, false, true);
        meterHeight = new GuiSlider(3, getCenter(widOffset), getHeight(3), 150, 20, "height: ", "px", 2, 400, mod.speedometer.meterHei, false, true);
        meterLogLength = new GuiSlider(4, getCenter(widOffset), getHeight(4), 150, 20, "log length: ", " ticks", 2, 80, mod.speedometer.logLength, false, true);
        resetPosition = new GuiButtonExt(5, getCenter(widOffset), getHeight(5), 150, 20, "reset position");
        back = new GuiButtonExt(6, getCenter(widOffset), getHeight(6), 150, 20, "back");
        buttonList.add(meterEnabled);
        buttonList.add(meterShadow);
        buttonList.add(meterWidth);
        buttonList.add(meterHeight);
        buttonList.add(meterLogLength);
        buttonList.add(resetPosition);
        buttonList.add(back);
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        mod.speedometer.meterWid = meterWidth.getValueInt();
        mod.speedometer.meterHei = meterHeight.getValueInt();
        if (mod.speedometer.logLength != meterLogLength.getValueInt()) {
            mod.speedometer.clearLog();
        }
        mod.speedometer.logLength = meterLogLength.getValueInt();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == back) {
            mc.displayGuiScreen(parent);
        } else if (button == resetPosition) {
            mod.speedometer.meterXOffset = 200;
            mod.speedometer.meterYOffset = 60;
        } else if (button == meterEnabled) {

            mod.speedometer.toggleMeter(!mod.speedometer.enabled);
            button.displayString = "enabled: " + mod.speedometer.enabled;

        } else if (button == meterShadow) {
            mod.speedometer.drawShadow = !mod.speedometer.drawShadow;
            button.displayString = "draw shadow: " + mod.speedometer.drawShadow;
        }

    }
}
