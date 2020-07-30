package ponei.elixe.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ponei.elixe.Elixe;
import ponei.elixe.settings.gui.SettingsGuiMain;

public class SettingsCommand extends CommandBase {
    private Elixe mod;
    public SettingsCommand(Elixe elixe) {
        mod = elixe;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender){
        return true;
    }

    @Override
    public String getCommandName() {
        return "elixe";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/elixe";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(new SettingsGuiMain(this.mod));
    }
}


