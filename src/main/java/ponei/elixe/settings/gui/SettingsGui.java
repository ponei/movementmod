package ponei.elixe.settings.gui;

import net.minecraft.client.gui.GuiScreen;

import ponei.elixe.Elixe;

import java.awt.*;
import java.io.IOException;

public class SettingsGui extends GuiScreen {
    private int draggingComponent = 0;
    private int lastX;
    private int lastY;
    protected Elixe mod;

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRendererObj, "elixe", width / 2, 5, new Color(255, 255, 255).getRGB());

        dragComponents(mouseX, mouseY);

        this.lastX = mouseX;
        this.lastY = mouseY;
    }

    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        holdComponents(mouseX, mouseY);
    }

    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        releaseComponents();
    }

    private void holdComponents(int mouseX, int mouseY) {
        if (mod.speedometer.contains(mouseX, mouseY)) {
            mod.speedometer.beingDragged = true;
        }
    }

    private void releaseComponents() {
        mod.speedometer.beingDragged = false;
    }

    private void dragComponents(int mouseX, int mouseY) {
        if (mod.speedometer.beingDragged) {
            mod.speedometer.meterXOffset += mouseX - this.lastX;
            mod.speedometer.meterYOffset += mouseY - this.lastY;
        }
    }

    @Override
    public void onGuiClosed() {
        mod.settingsManager.updateSettings(true);
    }

    protected int getCenter(int off) {
        return width / 2 - off;
    }

    protected int getHeight(int row) {
        return 40 + row * 22;
    }
}
