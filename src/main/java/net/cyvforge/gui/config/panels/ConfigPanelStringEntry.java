package net.cyvforge.gui.config.panels;

import net.cyvforge.CyvForge;
import net.cyvforge.config.CyvClientConfig;
import net.cyvforge.gui.config.ConfigPanel;
import net.cyvforge.util.defaults.CyvGui;
import net.cyvforge.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ConfigPanelStringEntry implements ConfigPanel {
    public GuiTextField field;
    public String configOption;
    public String displayString;
    public final int index;
    public CyvGui screenIn;

    private int xPosition;
    private int yPosition;
    private int sizeX;
    private int sizeY;

    public ConfigPanelStringEntry(ArrayList<ConfigPanel> array, String configOption, String displayString, CyvGui screenIn) {
        this.index = array.size();
        this.displayString = displayString;
        this.configOption = configOption;
        this.screenIn = screenIn;

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        this.sizeX = screenIn.getSizeX() - 20;
        this.sizeY = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 3 / 2;
        this.xPosition = sr.getScaledWidth() / 2 - screenIn.getSizeX() / 2 + 10;
        this.yPosition = sr.getScaledHeight() / 2 - screenIn.getSizeY() / 2 + 10 + (index * Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 2);

        this.field = new GuiTextField(0, Minecraft.getMinecraft().fontRenderer, this.xPosition + this.sizeX / 2 + 2, this.yPosition + 2, this.sizeX / 2 - 4, this.sizeY - 4);
        this.field.setText(CyvClientConfig.getString(configOption, "Cyv"));
        this.field.setEnableBackgroundDrawing(false);
        this.field.setMaxStringLength(12);
        this.field.setTextColor(0xFFFFFFFF);

        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void draw(int mouseX, int mouseY, int scroll) {
        boolean active = isEnabled();
        int textColor = active ? 0xFFFFFFFF : 0xFF777777;

        GuiUtils.drawString(this.displayString, this.xPosition, this.yPosition + this.sizeY / 2 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 2 + 1 - scroll, textColor, active);

        int boxColor = field.isFocused() ? 0x80555555 : CyvForge.theme.shade2;
        GuiUtils.drawRoundedRect(this.xPosition + this.sizeX / 2, this.yPosition - scroll, this.xPosition + this.sizeX, this.yPosition + this.sizeY - scroll, 3, boxColor);

        this.field.y = this.yPosition + 3 - scroll;
        this.field.drawTextBox();
    }

    @Override
    public void setPos(int x, int y, int width) {
        this.xPosition = x;
        this.yPosition = y;
        this.sizeX = width;
        if (this.field != null) {
            this.field.x = this.xPosition + this.sizeX / 2 + 2;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= this.xPosition + this.sizeX / 2 && mouseX <= this.xPosition + this.sizeX &&
                mouseY >= this.yPosition && mouseY <= this.yPosition + this.sizeY) {
            this.field.setFocused(true);
        } else {
            this.field.setFocused(false);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (this.field.isFocused()) {
            this.field.textboxKeyTyped(typedChar, keyCode);
            CyvClientConfig.set(this.configOption, this.field.getText());

            onValueChange();

            if (keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_ESCAPE) {
                this.field.setFocused(false);
            }
        }
    }

    @Override
    public void save() {
        CyvClientConfig.set(this.configOption, this.field.getText());
    }

    @Override
    public void update() {
        this.field.updateCursorCounter();
    }

    @Override
    public void unselect() {
        this.field.setFocused(false);
    }

    @Override
    public void mouseDragged(int mouseX, int mouseY) {}

    @Override
    public boolean mouseInBounds(int mouseX, int mouseY) {
        return mouseX > this.xPosition + this.sizeX / 2 && mouseY > this.yPosition
                && mouseX < this.xPosition + this.sizeX && mouseY < this.yPosition + this.sizeY;
    }

    @Override public int getIndex() { return this.index; }
}