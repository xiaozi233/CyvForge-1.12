package net.cyvforge.gui;

import net.cyvforge.CyvForge;
import net.cyvforge.config.ColorTheme;
import net.cyvforge.event.ConfigLoader;
import net.cyvforge.event.events.GuiHandler;
import net.cyvforge.gui.config.ConfigPanel;
import net.cyvforge.hud.HUDManager;
import net.cyvforge.hud.structure.DraggableHUDElement;
import net.cyvforge.util.defaults.CyvGui;
import net.cyvforge.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;

public class GuiMPK extends CyvGui {
    int sizeX = 100;
    int sizeY = 250;

    ArrayList<LabelLine> labelLines;
    int selectedIndex = -1;

    float vScroll = 0;
    float scroll = 0;
    int maxScroll = 0;
    boolean scrollClicked = false;

    GuiTextField searchBar;
    SubButton guiEditButton;
    SubButton settingsButton;
    SubButton macroButton;
    SubButton presetsButton;
    SubButton chatMacrosButton;

    DraggableHUDElement selectedSettingsElement = null;
    ArrayList<ConfigPanel> settingsPanels = new ArrayList<>();

    public GuiMPK() {
        super("MPK Gui");
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        mc.displayGuiScreen(null);
    }

    @Override
    public void initGui() {
        int maxTextWidth = 0;
        for (DraggableHUDElement l : HUDManager.registeredRenderers) {
            if (l.getDisplayName() == null || l.getDisplayName().trim().isEmpty()) continue;

            int w = Minecraft.getMinecraft().fontRenderer.getStringWidth(l.getDisplayName());
            if (w > maxTextWidth) maxTextWidth = w;
        }
        this.sizeX = maxTextWidth + 20;

        this.labelLines = new ArrayList<>();
        this.guiEditButton = new SubButton("Edit Positions", sr.getScaledWidth() / 2 + sizeX / 2 + 50,
                sr.getScaledHeight() / 2 - sizeY / 2, 100, 15);
        this.guiEditButton.setEnabled(true);

        this.settingsButton = new SubButton("Settings", sr.getScaledWidth() / 2 + sizeX / 2 + 50,
                sr.getScaledHeight() / 2 - sizeY / 2 + 20, 100, 15);
        this.settingsButton.setEnabled(true);

        this.macroButton = new SubButton("Open Macro", sr.getScaledWidth() / 2 + sizeX / 2 + 50,
                sr.getScaledHeight() / 2 - sizeY / 2 + 40, 100, 15);
        this.macroButton.setEnabled(Minecraft.getMinecraft().isSingleplayer());

        this.presetsButton = new SubButton("HUD Presets", sr.getScaledWidth() / 2 + sizeX / 2 + 50,
                sr.getScaledHeight() / 2 - sizeY / 2 + 60, 100, 15);
        this.presetsButton.setEnabled(true);

        this.chatMacrosButton = new SubButton("Chat Macros", sr.getScaledWidth() / 2 + sizeX / 2 + 50,
                sr.getScaledHeight() / 2 - sizeY / 2 + 80, 100, 15);
        this.chatMacrosButton.setEnabled(true);

        this.updateLabels(false);

        maxScroll = (int) Math.max(0, Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 2 * Math.ceil(labelLines.size()) - (sizeY-20));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        this.searchBar = new GuiTextField(0, Minecraft.getMinecraft().fontRenderer,
                sr.getScaledWidth()/2-sizeX/2 - 12,
                sr.getScaledHeight()/2-sizeY/2 - 10 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT,
                75,
                Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) {
            @Override
            public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
                if (super.textboxKeyTyped(p_146201_1_, p_146201_2_)) {
                    updateLabels(true);
                    return true;
                } else {
                    return false;
                }
            }
        };
        this.searchBar.setEnableBackgroundDrawing(false);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) { //exit the gui
            if (this.searchBar.isFocused()) {
                this.searchBar.setFocused(false);
                this.searchBar.setText("");
                updateLabels(true);
            } else this.mc.displayGuiScreen(null);
        } else {
            if (!this.searchBar.isFocused()) this.searchBar.setFocused(true);
            this.searchBar.textboxKeyTyped(typedChar, keyCode);
        }
    }

    public void updateLabels(boolean fromSearch) {
        this.labelLines.clear();

        for (DraggableHUDElement l : HUDManager.registeredRenderers) {
            if (l.getDisplayName() == null || l.getDisplayName().trim().isEmpty()) {
                continue;
            }

            if (!fromSearch || l.getDisplayName().toLowerCase().contains(this.searchBar.getText().toLowerCase())
                    || l.getName().toLowerCase().contains(this.searchBar.getText().toLowerCase()))
                labelLines.add(new LabelLine(l));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();

        maxScroll = (int) Math.max(0, Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 2 * Math.ceil(labelLines.size()) - (sizeY-20));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        if (selectedSettingsElement != null) {
            int leftMargin = 15;
            int gapBetween = 15;

            int mainListLeft = sr.getScaledWidth() / 2 - sizeX / 2 - 15;

            int pX = leftMargin;
            int pWidth = mainListLeft - gapBetween - pX;

            int pY = sr.getScaledHeight() / 2 - sizeY / 2 - 4;
            int pHeight = sizeY + 8;

            GuiUtils.drawRoundedRect(pX, pY, pX + pWidth, pY + pHeight, 5, CyvForge.theme.background1);
            GuiUtils.drawRectOutline(pX, pY, pX + pWidth, pY + pHeight, CyvForge.theme.border2);

            GuiUtils.drawCenteredString(selectedSettingsElement.getDisplayName(), pX + pWidth / 2, pY + 10, 0xFFFFFFFF, true);

            if (settingsPanels.isEmpty()) {
                GuiUtils.drawCenteredString("No settings", pX + pWidth / 2, pY + 40, 0xFFFFFFFF, true);
            } else {
                for (int i = 0; i < settingsPanels.size(); i++) {
                    ConfigPanel p = settingsPanels.get(i);
                    p.setPos(pX + 10, pY + 30 + (i * 20), pWidth - 20);
                    p.draw(mouseX, mouseY, 0);
                }
            }
        }

        // draw main background
        GuiUtils.drawRoundedRect(sr.getScaledWidth()/2 - sizeX/2 - 15, sr.getScaledHeight()/2 - sizeY/2 - 4,
                sr.getScaledWidth()/2 + sizeX/2 + 14, sr.getScaledHeight()/2 + sizeY/2 + 4, 5, CyvForge.theme.background1);

        int centerx = sr.getScaledWidth() * sr.getScaleFactor() / 2;
        int centery = sr.getScaledHeight() * sr.getScaleFactor() / 2;
        int scaleFactor = sr.getScaleFactor();

        GuiUtils.drawCenteredString("Labels:", sr.getScaledWidth()/2, 5 + sr.getScaledHeight()/2 - sizeY/2, 0xFFFFFFFF, true);

        // draw side button background
        final int BUTTON_X = sr.getScaledWidth() / 2 + sizeX / 2 + 50;
        final int BUTTON_SIZE = 100;
        final int BUTTON_COUNT = 5;
        GuiUtils.drawRoundedRect(BUTTON_X - 4, sr.getScaledHeight()/2 - sizeY/2 - 4,
                BUTTON_X + BUTTON_SIZE + 4, sr.getScaledHeight()/2 - sizeY/2 + BUTTON_COUNT * 20,
                5, CyvForge.theme.background1);

        // draw buttons
        this.guiEditButton.draw(mouseX, mouseY);
        this.settingsButton.draw(mouseX, mouseY);
        this.macroButton.draw(mouseX, mouseY);
        this.presetsButton.draw(mouseX, mouseY);
        this.chatMacrosButton.draw(mouseX, mouseY);

        //draw searchbar
        ColorTheme theme = CyvForge.theme;
        boolean isHovered = this.searchBar.isFocused() ||
                (mouseX > searchBar.x - 3 &&
                        mouseX < searchBar.x + searchBar.width  + 3&&
                        mouseY > searchBar.y - 3.5 &&
                        mouseY < searchBar.y + searchBar.height + 2.5);

        GuiUtils.drawRoundedRect(searchBar.x - 3,
                searchBar.y - 3.5f,
                searchBar.x + searchBar.width + 3,
                searchBar.y + searchBar.height + 2.5f,
                2, theme.background1);
        GuiUtils.drawRoundedRect(searchBar.x - 1.5f,
                searchBar.y - 2,
                searchBar.x + searchBar.width + 1.5f,
                searchBar.y + searchBar.height + 1f,
                2, isHovered ? theme.main2 : theme.secondary1);
        GuiUtils.drawRoundedRect(searchBar.x - 1.5f,
                searchBar.y - 2,
                searchBar.x + searchBar.width + 1.5f,
                searchBar.y + searchBar.height + 1,
                2, theme.highlight);
        if (!this.searchBar.isFocused() && this.searchBar.getText().length() == 0) {
            GuiUtils.drawString("Search", searchBar.x + 16,
                    searchBar.y + 0.5f,
                    0xFFFFFFFF, true);
        }

        this.searchBar.drawTextBox();

        GL11.glScissor(centerx - ((sizeX + 10)*scaleFactor/2),
                centery - (sizeY*scaleFactor/2) + 3,
                sizeX*scaleFactor, sizeY*scaleFactor - (fontRenderer.FONT_HEIGHT * scaleFactor * 2));
        GL11.glEnable(GL11.GL_SCISSOR_TEST);


        int index = 0;
        for (LabelLine l : labelLines) {
            int yHeight = (int) ((index + 1) * mc.fontRenderer.FONT_HEIGHT*2 - scroll + (sr.getScaledHeight()/2 - sizeY/2));
            l.drawEntry(index, (int) scroll, mouseX, mouseY, index == this.selectedIndex);
            index++;
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        //draw scrollbar
        int scrollbarHeight = (int) ((sizeY - 8 - 15)/(0.01*maxScroll+1));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        int top = sr.getScaledHeight()/2-sizeY/2+4+15;
        int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * (scroll /maxScroll));
        if (maxScroll == 0) amount = top;

        int color = CyvForge.theme.border2;
        if (mouseX > sr.getScaledWidth()/2+sizeX/2+2 && mouseX < sr.getScaledWidth()/2+sizeX/2+8 &&
                mouseY > amount && mouseY < amount+scrollbarHeight) {
            color = CyvForge.theme.border1;
        }

        GuiUtils.drawRoundedRect(sr.getScaledWidth()/2+sizeX/2+2, amount,
                sr.getScaledWidth()/2+sizeX/2+8, amount+scrollbarHeight, 3, color);

    }

    @Override
    public void handleMouseInput() throws IOException {
        int eventDWheel = GuiHandler.scrollBuffer;
        GuiHandler.scrollBuffer = 0;

        if (eventDWheel != 0 && (!scrollClicked || !org.lwjgl.input.Mouse.isButtonDown(0))) {
            vScroll -= eventDWheel * 0.05;
        }

        super.handleMouseInput();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseEvent) throws IOException {
        if (selectedSettingsElement != null) {
            for (ConfigPanel p : settingsPanels) {
                if (p.mouseInBounds(mouseX, mouseY)) {
                    p.mouseClicked(mouseX, mouseY, mouseEvent);
                    return;
                }
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseEvent);

        int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
        int top = sr.getScaledHeight()/2-sizeY/2+4+15;
        int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * (scroll /maxScroll));
        if (maxScroll == 0) amount = top;

        // check scrollbar
        if (mouseX > sr.getScaledWidth()/2+sizeX/2+2 && mouseX < sr.getScaledWidth()/2+sizeX/2+8 &&
                mouseY > amount && mouseY < amount+scrollbarHeight) {
            this.scrollClicked = true;
            return;
        } else {
            this.scrollClicked = false;
        }

        // check searchbar
        this.searchBar.mouseClicked(mouseX, mouseY, mouseEvent);
        if (this.searchBar.isFocused()) {
            updateLabels(true);
            return;
        }

        // check buttons
        if (this.guiEditButton.clicked(mouseX, mouseY, mouseEvent)) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiHUDPositions(true, false));
            return;
        } else if (this.settingsButton.clicked(mouseX, mouseY, mouseEvent)) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiModConfig(true));
            return;
        } else if (this.macroButton.clicked(mouseX, mouseY, mouseEvent)) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiMacro());
            return;
        } else if (this.presetsButton.clicked(mouseX, mouseY, mouseEvent)) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPresets());
            return;
        } else if (this.chatMacrosButton.clicked(mouseX, mouseY, mouseEvent)) {
            Minecraft.getMinecraft().displayGuiScreen(new net.cyvforge.keybinding.ChatMacro.GuiChatMacro());
            return;
        }

        // check labels
        if (mouseX < sr.getScaledWidth()/2-this.sizeX/2 || mouseX > sr.getScaledWidth()/2+this.sizeX/2
                || mouseY < sr.getScaledHeight()/2-this.sizeY/2 || mouseY > sr.getScaledHeight()/2+this.sizeY/2) {
            return;
        }

        int index=0;
        for (LabelLine l : labelLines) {
            if (l.isPressed(index, mouseX, mouseY, mouseEvent)) {
                l.mouseClicked(index, mouseX, mouseY, mouseEvent);
                return;
            }
            index++;
        }

    }

    @Override
    public void mouseClickMove(int x, int y, int mouseButton, long time) {
        if (this.scrollClicked) {
            int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
            int top = sr.getScaledHeight()/2-sizeY/2+4+15;
            int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;

            scroll = (int) ((float) (y - (sr.getScaledHeight()/2-this.sizeY/2+15) - scrollbarHeight/2) /(bottom - top) * maxScroll);

            if (scroll > maxScroll) scroll = maxScroll;
            if (scroll < 0) scroll = 0;
        }

    }

    @Override
    public void updateScreen() {
        this.searchBar.updateCursorCounter();

        //smooth scrolling
        this.scroll += this.vScroll;
        this.vScroll *= 0.75;

    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        ConfigLoader.save(CyvForge.config, false);
    }

    class LabelLine {
        DraggableHUDElement label;
        int xStart;
        int width;
        int height;

        public LabelLine(DraggableHUDElement label) {
            this.label = label;
            this.width = GuiMPK.this.sizeX;
            this.xStart = sr.getScaledWidth()/2 - width/2 - 5;
            this.height = fontRenderer.FONT_HEIGHT * 2;
        }

        public void drawEntry(int slotIndex, int scroll, int mouseX, int mouseY, boolean isSelected) {
            int yHeight = (slotIndex + 1) * fontRenderer.FONT_HEIGHT*2 - scroll + (sr.getScaledHeight()/2 - sizeY/2);
            GuiUtils.drawRoundedRect(xStart, yHeight + 1,
                    xStart + width, yHeight + height - 1,
                    3, label.isEnabled ? CyvForge.theme.shade2 : CyvForge.theme.secondary1);

            GuiUtils.drawString(label.getDisplayName(), xStart + 4, yHeight + height/3, 0xFFFFFFFF, true);

            ArrayList<net.cyvforge.gui.config.ConfigPanel> testPanels = new ArrayList<>();
            net.cyvforge.gui.GuiHUDConfig.loadSettingsFor(label, testPanels, GuiMPK.this);
            boolean hasSettings = !testPanels.isEmpty();

            if (hasSettings) {
                int gearX = xStart + width - 8;
                int gearY = yHeight + (this.height / 2);

                boolean isGearHovered = mouseX >= gearX - 4 && mouseX <= gearX + 4 &&
                                        mouseY >= gearY - 5 && mouseY <= gearY + 5;

                int gearColor;
                if (selectedSettingsElement == label) {
                    gearColor = 0xFFFFFF00;
                } else {
                    gearColor = label.isEnabled ? net.cyvforge.CyvForge.theme.mainBase() : 0xFFAAAAAA;
                    if (isGearHovered) {
                        gearColor = label.isEnabled ? 0xFFFFFFFF : 0xFFCCCCCC;
                    }
                }

                drawSettingsIcon(gearX, gearY, gearColor);
            }
        }

        public boolean isPressed(int slotIndex, int mouseX, int mouseY, int mouseEvent) {
            float yHeight = (slotIndex + 1) * fontRenderer.FONT_HEIGHT*2 - scroll + (sr.getScaledHeight()/2 - sizeY/2);
            return mouseX > xStart && mouseX < xStart + width && mouseY > yHeight && mouseY < yHeight + height;
        }

        public void mouseClicked(int slotIndex, int mouseX, int mouseY, int mouseEvent) {
            int gearX = xStart + width - 8;
            int yHeight = (int) ((slotIndex + 1) * fontRenderer.FONT_HEIGHT * 2 - scroll + (sr.getScaledHeight() / 2 - sizeY / 2));
            int gearY = yHeight + (this.height / 2);

            ArrayList<net.cyvforge.gui.config.ConfigPanel> testPanels = new ArrayList<>();
            net.cyvforge.gui.GuiHUDConfig.loadSettingsFor(label, testPanels, GuiMPK.this);
            boolean hasSettings = !testPanels.isEmpty();

            if (hasSettings && mouseX >= gearX - 4 && mouseX <= gearX + 4 && mouseY >= gearY - 5 && mouseY <= gearY + 5) {
                if (selectedSettingsElement == label) {
                    selectedSettingsElement = null;
                    settingsPanels.clear();
                } else {
                    selectedSettingsElement = label;
                    settingsPanels.clear();
                    net.cyvforge.gui.GuiHUDConfig.loadSettingsFor(label, settingsPanels, GuiMPK.this);
                }
                return;
            }

            label.setEnabled(!label.isEnabled);
            ConfigLoader.save(CyvForge.config, false);
            GuiPresets.saveCurrentLayoutToSelected();
        }

        private void drawSettingsIcon(float x, float y, int color) {
            float radius = 1.0f;
            float gap = 4.0f;

            net.cyvforge.util.GuiUtils.drawRoundedRect(x - radius, y - gap - radius, x + radius, y - gap + radius, radius, color);
            net.cyvforge.util.GuiUtils.drawRoundedRect(x - radius, y - radius, x + radius, y + radius, radius, color);
            net.cyvforge.util.GuiUtils.drawRoundedRect(x - radius, y + gap - radius, x + radius, y + gap + radius, radius, color);
        }

    }

    @Override public int getSizeX() { return this.sizeX; }
    @Override public int getSizeY() { return this.sizeY; }
}
