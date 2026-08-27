package net.cyvforge.gui.config.panels;

import net.cyvforge.CyvForge;
import net.cyvforge.config.CyvClientConfig;
import net.cyvforge.gui.config.ConfigPanel;
import net.cyvforge.util.defaults.CyvGui;
import net.cyvforge.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;

public class ConfigPanelToggle implements ConfigPanel {
    public boolean sliderValue;
    public String configOption;
    public String displayString;
    public final int index;
    public CyvGui screenIn;

    private int xPosition;
    private int yPosition;
    private int sizeX;
    private int sizeY;

    public ConfigPanelToggle(ArrayList<ConfigPanel> array, String configOption, String displayString, CyvGui screenIn) {
        this.index = array.size();
        this.displayString = displayString;
        this.configOption = configOption;
        this.screenIn = screenIn;

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        sizeX = screenIn.getSizeX()-20;
        sizeY = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT*3/2;
        this.xPosition = sr.getScaledWidth()/2-screenIn.getSizeX()/2+10;
        this.yPosition = sr.getScaledHeight()/2-screenIn.getSizeY()/2+10 + (index * Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 2);
        this.sliderValue = CyvClientConfig.getBoolean(configOption, false);

    }

    @Override
    public void draw(int mouseX, int mouseY, int scroll) {
        boolean active = isEnabled();
        int textColor = active ? 0xFFFFFFFF : 0xFFAAAAAA;
        int bgColor;

        if (!active) {
            bgColor = 0x80555555;
        } else {
            bgColor = this.mouseInBounds(mouseX, mouseY + scroll) ? CyvForge.theme.accent1 : CyvForge.theme.accent2;
        }

        //text label
        GuiUtils.drawString(this.displayString, this.xPosition, this.yPosition+this.sizeY/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2+1-scroll, textColor, active);

        //bg
        GuiUtils.drawRoundedRect(this.xPosition+this.sizeX/2, this.yPosition-scroll, this.xPosition+this.sizeX, this.yPosition+this.sizeY-scroll, 3, bgColor);

        //amount
        GuiUtils.drawCenteredString(" "+this.sliderValue, this.xPosition+this.sizeX*3/4, this.yPosition+this.sizeY/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2+1-scroll, textColor, active);

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

    @Override
    public void mouseDragged(int mouseX, int mouseY) {

    }

    @Override
    public boolean mouseInBounds(int mouseX, int mouseY) {
        return isEnabled() && mouseX > this.xPosition+this.sizeX/2 && mouseY > this.yPosition
                && mouseX < this.xPosition+this.sizeX && mouseY < this.yPosition+this.sizeY;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!isEnabled()) return;

        this.sliderValue = !this.sliderValue;
        CyvClientConfig.set(this.configOption, this.sliderValue);
        onValueChange();

    }


    @Override
    public void keyTyped(char typedChar, int keyCode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save() {
        CyvClientConfig.set(this.configOption, this.sliderValue);
    }

}