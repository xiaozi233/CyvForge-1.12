package net.cyvforge.hud.nonlabels;

import net.cyvforge.CyvForge;
import net.cyvforge.config.CyvClientColorHelper;
import net.cyvforge.config.CyvClientConfig;
import net.cyvforge.hud.structure.DraggableHUDElement;
import net.cyvforge.hud.structure.ScreenPosition;
import net.cyvforge.keybinding.KeybindingTogglesprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.text.DecimalFormat;

public class TogglesprintHUD extends DraggableHUDElement {
    public String getName() {return "togglesprintHUD";}
    public String getDisplayName() {return "Togglesprint HUD";}
    public int getWidth() {
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        return font.getStringWidth("[Sprint Toggled]");
    }
    public int getHeight() {return 9;}
    public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 232);}
    public void render(ScreenPosition pos) {
        if (!this.isVisible) return;
        if (!KeybindingTogglesprint.isSprintToggled()) return;
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        FontRenderer font = mc.fontRenderer;
        DecimalFormat df = CyvForge.df;
        String p = "Sprint Toggled | " + (KeybindingTogglesprint.currentMode == KeybindingTogglesprint.SprintMode.JAM ? "JAM" : "FMM");
        drawString("[", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        drawString(p, pos.getAbsoluteX() + 1 + font.getStringWidth("["), pos.getAbsoluteY() + 1, color2);
        drawString("]", pos.getAbsoluteX() + 1+ font.getStringWidth("[" + p), pos.getAbsoluteY() + 1, color1);

    }
    public void renderDummy(ScreenPosition pos) {
        int d = CyvClientConfig.getInt("df",5);
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        FontRenderer font = mc.fontRenderer;
        drawString("[", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        drawString("Sprint Toggled", pos.getAbsoluteX() + 1 + font.getStringWidth("["), pos.getAbsoluteY() + 1, color2);
        drawString("]", pos.getAbsoluteX() + 1+ font.getStringWidth("[Sprint Toggled"), pos.getAbsoluteY() + 1, color1);
    }

}
