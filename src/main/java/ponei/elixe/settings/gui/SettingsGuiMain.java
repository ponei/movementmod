package ponei.elixe.settings.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import ponei.elixe.Elixe;

import java.io.IOException;

public class SettingsGuiMain extends SettingsGui {


    public SettingsGuiMain(Elixe mod) {
        this.mod = mod;
    }

    private GuiButtonExt speedometerButton;

    @Override
    public void initGui(){
        speedometerButton = new GuiButtonExt(0, getCenter(50), getHeight(0), 100, 20, "speedometer");
        buttonList.add(speedometerButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == speedometerButton){
            mc.displayGuiScreen(new SettingsGuiSpeedometer(mod, this));
        }
    }
}
