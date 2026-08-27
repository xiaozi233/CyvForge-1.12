package net.cyvforge.gui.config.panels;

import net.cyvforge.CyvForge;
import net.cyvforge.gui.config.ConfigPanel;
import net.cyvforge.util.defaults.CyvGui;
import net.cyvforge.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;

public class ConfigPanelAction implements ConfigPanel {
    public String displayString;
    public final int index;
    public CyvGui screenIn;
    public Runnable action;

    private int xPosition;
    private int yPosition;
    private int sizeX;
    private int sizeY;

    public ConfigPanelAction(ArrayList<ConfigPanel> array, String displayString, Runnable action, CyvGui screenIn) {
        this.index = array.size();
        this.displayString = displayString;
        this.action = action;
        this.screenIn = screenIn;

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        this.sizeX = screenIn.getSizeX() - 20;
        this.sizeY = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 3 / 2;
        this.xPosition = sr.getScaledWidth() / 2 - screenIn.getSizeX() / 2 + 10;
        this.yPosition = sr.getScaledHeight() / 2 - screenIn.getSizeY() / 2 + 10 + (index * Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 2);
    }

    @Override
    public void draw(int mouseX, int mouseY, int scroll) {
        boolean active = isEnabled();
        int textColor = active ? 0xFFFFFFFF : 0xFF777777;
        int bgColor;

        if (!active) {
            bgColor = 0x80555555;
        } else {
            bgColor = this.mouseInBounds(mouseX, mouseY + scroll) ? CyvForge.theme.accent1 : CyvForge.theme.accent2;
        }

        int btnWidth = (int)(sizeX * 0.6);
        int btnX = this.xPosition + (sizeX - btnWidth) / 2;

        //bg
        GuiUtils.drawRoundedRect(btnX, this.yPosition - scroll, btnX + btnWidth, this.yPosition + this.sizeY - scroll, 3, bgColor);

        //text label
        GuiUtils.drawCenteredString(this.displayString, btnX + (btnWidth / 2), this.yPosition + this.sizeY / 2 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 2 + 1 - scroll, textColor, active);
    }

    @Override
    public void setPos(int x, int y, int width) {
        this.xPosition = x;
        this.yPosition = y;
        this.sizeX = width;
    }

    @Override public int getIndex() {
        return this.index;
    }

    @Override public void mouseDragged(int mouseX, int mouseY) {}

    @Override
    public boolean mouseInBounds(int mouseX, int mouseY) {
        int btnWidth = (int)(sizeX * 0.6);
        int btnX = this.xPosition + (sizeX - btnWidth) / 2;

        if (isEnabled() && mouseX > btnX && mouseY > this.yPosition
                && mouseX < btnX + btnWidth && mouseY < this.yPosition + this.sizeY) return true;
        return false;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!isEnabled()) return;
        if (action != null) action.run();
    }

    @Override public void keyTyped(char typedChar, int keyCode) {
    }

    @Override public void save() {}
    @Override public void update() {}
}