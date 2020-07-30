package ponei.elixe.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import scala.Console;

import java.util.ArrayList;

public class Speedometer {
    private Minecraft mc;

    public Speedometer(Minecraft mc) {
        this.mc = mc;
    }

    public boolean enabled;
    public boolean drawShadow;
    public boolean beingDragged;
    public int logLength;
    public int meterHei;
    public int meterWid;
    public int meterYOffset;
    public int meterXOffset;

    private ArrayList<Float> motionLog = new ArrayList<Float>();
    private int hei;
    private float meterWidMid;

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.theWorld != null && mc.thePlayer != null && event.entityLiving == mc.thePlayer) {
            motionLog.add(0, (float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ));
            fixLogLength();
        }
    }

    public void toggleMeter(boolean state){
        if (state){
            enabled = true;
            MinecraftForge.EVENT_BUS.register(this);
        } else {
            enabled = false;
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }

    private void fixLogLength() {
        if (motionLog.size() - logLength == 1) {
            motionLog.remove(logLength);
        }
    }

    public void clearLog() {
        motionLog.clear();
    }

    private float minX, maxX, minY, maxY;
    private void updateRectangle() {
        minX = meterXOffset - meterWidMid;
        maxX = meterXOffset + meterWidMid;
        maxY = meterYOffset + 24;
        minY = meterYOffset - meterHei;
    }

    public boolean contains(final int mouseX, final int mouseY) {
        updateRectangle();
        return mouseX > minX && mouseX < maxX && mouseY > minY && mouseY < maxY;
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (motionLog.size() > 0 && event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            //res and shite
            ScaledResolution res = event.resolution;
            hei = res.getScaledHeight();
            meterWidMid = (float) meterWid / 2F;

            //start gl
            GL11.glPushAttrib(GL11.GL_CURRENT_BIT);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            //meter text
            String speed = String.format("%.2f", motionLog.get(0) * 100);
            float textWid = mc.fontRendererObj.getStringWidth(speed) / 2;

            mc.fontRendererObj.drawStringWithShadow(speed, meterXOffset - textWid, meterYOffset + 5, 0xFFFFFFFF);
            mc.fontRendererObj.drawStringWithShadow("km/h", meterXOffset - 12, meterYOffset + 15, 0xFFFFFFFF);
            //end text

            //meter lines
            GL11.glDisable(GL11.GL_TEXTURE_2D);

            drawMeter();
            if (beingDragged) {
                updateRectangle();
                meterRectangle();
            }
            if (drawShadow) {
                meterShadow();
            }


            GL11.glEnable(GL11.GL_TEXTURE_2D);
            //end lines

            //end gl
            GL11.glPopAttrib();
        }
    }

    private void meterRectangle() {
        GL11.glBegin(GL11.GL_LINE_LOOP);

        GL11.glVertex2f(minX, minY);
        GL11.glVertex2f(minX, maxY);
        GL11.glVertex2f(maxX, maxY);
        GL11.glVertex2f(maxX, minY);

        GL11.glEnd();
    }

    private void meterShadow() {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        RenderItem renderItem = mc.getRenderItem();
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer worldrenderer = tess.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(meterXOffset + meterWidMid, meterYOffset - meterHei, renderItem.zLevel).color(0F, 0F, 0F, 0F).endVertex();
        worldrenderer.pos(meterXOffset - meterWidMid, meterYOffset - meterHei, renderItem.zLevel).color(0F, 0F, 0F, 0F).endVertex();
        worldrenderer.pos(meterXOffset - meterWidMid, meterYOffset, renderItem.zLevel).color(0F, 0F, 0F, 0.4F).endVertex();
        worldrenderer.pos(meterXOffset + meterWidMid, meterYOffset, renderItem.zLevel).color(0F, 0F, 0F, 0.4F).endVertex();
        tess.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    private void drawMeter() {
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glLineWidth(2);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(meterXOffset - meterWidMid, meterYOffset);
        GL11.glVertex2f(meterXOffset + meterWidMid, meterYOffset);
        GL11.glEnd();

        if (motionLog.size() > 1) {
            float lineStep = (float) meterWid / ((float) logLength - 1F);
            float baseX = meterXOffset + meterWidMid;
            float maxMotion = Math.max(0.32F, getMaxValue(motionLog));

            GL11.glBegin(GL11.GL_LINE_STRIP);
            for (int i = 0; motionLog.size() > i; i++) {
                float lineY = (motionLog.get(i) * meterHei) / maxMotion;
                GL11.glVertex2f(baseX - i * lineStep, meterYOffset - lineY);

            }
            GL11.glEnd();
        }

    }

    private float getMaxValue(ArrayList<Float> numbers) {
        float maxValue = numbers.get(0);
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) > maxValue) {
                maxValue = numbers.get(i);
            }
        }
        return maxValue;
    }
}
