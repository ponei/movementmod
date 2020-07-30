package ponei.elixe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.command.ICommand;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import ponei.elixe.components.Speedometer;
import ponei.elixe.settings.SettingsCommand;
import ponei.elixe.settings.SettingsManager;
import scala.Console;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mod(modid = Elixe.MOD_ID, name = Elixe.MOD_ID, version = Elixe.VERSION)
public class Elixe
{
    public static final String MOD_ID   = "elixe";
    public static final String VERSION  = "@VERSION@";


    private Minecraft mc = Minecraft.getMinecraft();

    private File settingsFile;
    public SettingsManager settingsManager;

    public Speedometer speedometer;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        //components
        speedometer = new Speedometer(mc);

        //commands
        ClientCommandHandler.instance.registerCommand(new SettingsCommand(this));

        //settings
        settingsFile = new File(this.mc.mcDataDir, "config/elixe.config");
        settingsManager = new SettingsManager(this, settingsFile);
        settingsManager.updateSettings(false);
    }


}
