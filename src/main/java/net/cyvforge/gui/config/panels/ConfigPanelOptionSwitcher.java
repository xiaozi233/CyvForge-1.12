package net.cyvforge.gui.config.panels;

import net.cyvforge.CyvForge;
import net.cyvforge.config.CyvClientConfig;
import net.cyvforge.gui.config.ConfigPanel;
import net.cyvforge.util.defaults.CyvGui;
import net.cyvforge.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;

public class ConfigPanelOptionSwitcher<T> implements ConfigPanel {
    public int sliderValue;
    public String configOption;
    public String displayString;
    public final int index;
    public final T[] sliderValues;
    public CyvGui screenIn;

    private int xPosition;
    private int yPosition;
    private int sizeX;
    private int sizeY;

    public ConfigPanelOptionSwitcher(ArrayList<ConfigPanel> array, String configOption, String displayString, T[] options, CyvGui screenIn) {
        this.index = array.size();
        this.displayString = displayString;
        this.configOption = configOption;
        this.screenIn = screenIn;

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        sizeX = screenIn.getSizeX()-20;
        sizeY = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT*3/2;
        this.xPosition = sr.getScaledWidth()/2-screenIn.getSizeX()/2+10;
        this.yPosition = sr.getScaledHeight()/2-screenIn.getSizeY()/2+10 + (index * Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 2);

        this.sliderValues = options;
        this.sliderValue = 0;
        for (int i=0; i<this.sliderValues.length; i++) {
            if (this.sliderValues[i].toString().equals(CyvClientConfig.getString(configOption, ""))) {
                this.sliderValue = i;
                break;
            }
        }

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

        //text label
        GuiUtils.drawString(this.displayString, this.xPosition, this.yPosition+this.sizeY/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2+1-scroll, textColor, active);

        //bg
        GuiUtils.drawRoundedRect(this.xPosition+this.sizeX/2, this.yPosition-scroll, this.xPosition+this.sizeX, this.yPosition+this.sizeY-scroll, 3, bgColor);

        //amount
        GuiUtils.drawCenteredString(""+this.sliderValues[this.sliderValue], this.xPosition+this.sizeX*3/4, this.yPosition+this.sizeY/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2+1-scroll, textColor, active);

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

        if (mouseButton == 0) this.sliderValue++;
        else if (mouseButton == 1) this.sliderValue--;
        if (this.sliderValue >= this.sliderValues.length) this.sliderValue = 0;
        if (this.sliderValue < 0) this.sliderValue = this.sliderValues.length-1;

        CyvClientConfig.set(this.configOption, this.sliderValues[this.sliderValue]);
        onValueChange();

    }


    @Override
    public void keyTyped(char typedChar, int keyCode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save() {
        CyvClientConfig.set(this.configOption, this.sliderValues[this.sliderValue]);
    }

}