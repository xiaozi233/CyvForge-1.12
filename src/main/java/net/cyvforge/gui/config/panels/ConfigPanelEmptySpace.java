package net.cyvforge.gui.config.panels;

import net.cyvforge.gui.GuiModConfig;
import net.cyvforge.gui.config.ConfigPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;

public class ConfigPanelEmptySpace implements ConfigPanel {
    public final int index;
    public GuiModConfig screenIn;

    private final int xPosition;
    private final int yPosition;
    private final int sizeX;
    private final int sizeY;

    public ConfigPanelEmptySpace(ArrayList<ConfigPanel> array, GuiModConfig screenIn) {
        this.index = array.size();
        this.screenIn = screenIn;

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        sizeX = screenIn.sizeX-20;
        sizeY = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT*3/2;
        this.xPosition = sr.getScaledWidth()/2-screenIn.sizeX/2+10;
        this.yPosition = sr.getScaledHeight()/2-screenIn.sizeY/2+10 + (index * Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 2);

    }

    @Override
    public void draw(int mouseX, int mouseY, int scroll) {
    }

    @Override
    public void mouseDragged(int mouseX, int mouseY) {

    }

    @Override
    public boolean mouseInBounds(int mouseX, int mouseY) {
        return mouseX > this.xPosition + this.sizeX / 2 && mouseY > this.yPosition
                && mouseX < this.xPosition + this.sizeX && mouseY < this.yPosition + this.sizeY;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }


    @Override
    public void keyTyped(char typedChar, int keyCode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save() {
    }

}