package net.cyvforge.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.cyvforge.CyvForge;
import net.cyvforge.config.CyvClientConfig;
import net.cyvforge.event.ConfigLoader;
import net.cyvforge.hud.HUDManager;
import net.cyvforge.hud.structure.DraggableHUDElement;
import net.cyvforge.hud.structure.ScreenPosition;
import net.cyvforge.util.GuiUtils;
import net.cyvforge.util.defaults.CyvGui;
import net.cyvforge.util.HUDPreset;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import java.io.*;
import java.util.ArrayList;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;

public class GuiPresets extends CyvGui {
    int sizeX = 220;
    int sizeY = 220;
    public ArrayList<PresetLine> presetLines = new ArrayList<>();
    SubButton addNewButton;
    SubButton backBtn;
    File presetDir;
    float scroll = 0;
    float vScroll = 0;
    int maxScroll = 0;

    public GuiPresets() {
        super("HUD Presets");
        this.presetDir = new File(Minecraft.getMinecraft().gameDir, "config/cyvforge/presets");
        if (!presetDir.exists()) presetDir.mkdirs();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        int startX = this.width / 2 - sizeX / 2;
        int startY = this.height / 2 - sizeY / 2;
        this.backBtn = new SubButton("Back", startX, startY - 25, 40, 14);
        this.addNewButton = new SubButton("Create New Preset", this.width / 2 - 75, startY + sizeY - 25, 150, 20);
        this.addNewButton.setEnabled(true);
        refreshPresets();
        saveCurrentLayoutToSelected();
    }

    public void refreshPresets() {
        presetLines.clear();
        File[] files = presetDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File f : files) {
                presetLines.add(new PresetLine(f));
            }
        }

        String selected = CyvClientConfig.getString("selectedPreset", "none");
        boolean found = false;
        PresetLine toApply = null;

        for (PresetLine line : presetLines) {
            if (line.file.getName().equals(selected)) {
                found = true;
                break;
            }
        }

        if (!found && !presetLines.isEmpty()) {
            toApply = presetLines.get(0);
            CyvClientConfig.set("selectedPreset", toApply.file.getName());
            toApply.applyPreset();
            ConfigLoader.save(CyvForge.config, false);
        } else if (presetLines.isEmpty()) {
            CyvClientConfig.set("selectedPreset", "none");
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        int startX = this.width / 2 - sizeX / 2;
        int startY = this.height / 2 - sizeY / 2;

        maxScroll = Math.max(0, (presetLines.size() * 22) - (sizeY - 70));

        int bx = startX;
        int by = startY - 25;
        int bw = 40;
        int bh = 14;
        boolean backHover = mouseX >= bx && mouseX <= bx + bw && mouseY >= by && mouseY <= by + bh;

        int backColor = backHover ? 0xBF333333 : 0xBF000000;
        GuiUtils.drawRoundedRect(bx, by, bx + bw, by + bh, 5, backColor);
        GuiUtils.drawCenteredString("Back", bx + bw / 2, by + 3, 0xFFFFFFFF, false);

        GuiUtils.drawRoundedRect(startX - 5, startY - 5, startX + sizeX + 5, startY + sizeY + 5, 5, CyvForge.theme.background1);
        GuiUtils.drawCenteredString("HUD Presets", this.width / 2, startY + 5, 0xFFFFFFFF, true);
        this.addNewButton.draw(mouseX, mouseY);

        int factor = sr.getScaleFactor();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(startX * factor, (height - (startY + sizeY - 30)) * factor, sizeX * factor, (sizeY - 60) * factor);

        String currentSelected = CyvClientConfig.getString("selectedPreset", "");
        for (int i = 0; i < presetLines.size(); i++) {
            int yPos = (int) (startY + 40 + (i * 22) - scroll);
            PresetLine line = presetLines.get(i);

            if (line.file.getName().equals(currentSelected)) {
                GuiUtils.drawRectOutline(startX + 1, yPos - 4, startX + sizeX - 2, yPos + 18, 0xFFFFFFFF);
            }
            line.draw(yPos, mouseX, mouseY);
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        if (maxScroll > 0) {
            int barHeight = sizeY - 70;
            int scrollHeight = Math.max(10, (int) ((float) barHeight * barHeight / (maxScroll + barHeight)));
            int scrollPos = (int) (scroll / maxScroll * (barHeight - scrollHeight));

            Gui.drawRect(startX - 3, startY + 35, startX - 1, startY + 35 + barHeight, 0x30FFFFFF);
            Gui.drawRect(startX - 3, startY + 35 + scrollPos, startX - 1, startY + 35 + scrollPos + scrollHeight, 0xFFFFFFFF);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseEvent) throws IOException {
        int startX = this.width / 2 - sizeX / 2;
        int startY = this.height / 2 - sizeY / 2;

        if (mouseX >= startX && mouseX <= startX + 40 && mouseY >= startY - 25 && mouseY <= startY - 11) {
            mc.displayGuiScreen(new GuiMPK());
            return;
        }

        if (this.addNewButton.clicked(mouseX, mouseY, mouseEvent)) {
            saveCurrentAsNew();
            refreshPresets();
            return;
        }

        ArrayList<PresetLine> tempLines = new ArrayList<>(presetLines);
        for (int i = 0; i < tempLines.size(); i++) {
            int yPos = (int) (startY + 40 + (i * 22) - scroll);

            if (mouseY >= startY + 30 && mouseY <= startY + sizeY - 30) {
                if (tempLines.get(i).mouseClicked(yPos, mouseX, mouseY, mouseEvent)) break;
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            saveCurrentLayoutToSelected();
            mc.displayGuiScreen(new GuiMPK());
            return;
        }

        ArrayList<PresetLine> linesCopy = new ArrayList<>(presetLines);
        for (PresetLine line : linesCopy) {
            if (line.nameField.isFocused()) {
                line.nameField.textboxKeyTyped(typedChar, keyCode);
                if (keyCode == Keyboard.KEY_RETURN) {
                    line.saveName();
                    refreshPresets();
                    return;
                }
            }
        }
    }

    private void saveCurrentAsNew() {
        String name = "Preset " + (presetLines.size() + 1);
        HUDPreset preset = new HUDPreset(name);
        for (DraggableHUDElement e : HUDManager.registeredRenderers) {
            ScreenPosition p = (e.position != null) ? e.position : e.load();
            if (p == null) p = e.getDefaultPosition();
            preset.positions.put(e.getName(), p.getAbsoluteX() + "," + p.getAbsoluteY() + "," + e.isVisible + "," + e.isEnabled);
        }
        savePresetToFile(new File(presetDir, name + ".json"), preset);
        CyvClientConfig.set("selectedPreset", name + ".json");
        ConfigLoader.save(CyvForge.config, false);
    }

    public static void savePresetToFile(File file, HUDPreset preset) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
            new GsonBuilder().setPrettyPrinting().create().toJson(preset, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void saveCurrentLayoutToSelected() {
        String selectedFileName = CyvClientConfig.getString("selectedPreset", "none");
        if (selectedFileName.equals("none") || selectedFileName.isEmpty()) return;

        File presetDir = new File(Minecraft.getMinecraft().gameDir, "config/cyvforge/presets");
        File file = new File(presetDir, selectedFileName);
        if (!file.exists()) return;

        HUDPreset preset;
        try (Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
            preset = new Gson().fromJson(reader, HUDPreset.class);
        } catch (Exception e) {
            preset = new HUDPreset(selectedFileName.replace(".json", ""));
        }

        for (DraggableHUDElement e : HUDManager.registeredRenderers) {
            ScreenPosition p = (e.position != null) ? e.position : e.load();
            if (p == null) p = e.getDefaultPosition();
            preset.positions.put(e.getName(), p.getAbsoluteX() + "," + p.getAbsoluteY() + "," + e.isVisible + "," + e.isEnabled);
        }

        savePresetToFile(file, preset);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        saveCurrentLayoutToSelected();
        super.onGuiClosed();
    }

    class PresetLine {
        File file; HUDPreset data; GuiTextField nameField; SubButton editBtn, deleteBtn;

        public PresetLine(File f) {
            this.file = f;
            try (Reader reader = new InputStreamReader(new FileInputStream(f), "UTF-8")) {
                this.data = new Gson().fromJson(reader, HUDPreset.class);
            } catch (Exception e) { this.data = new HUDPreset(f.getName().replace(".json", "")); }
            nameField = new GuiTextField(0, fontRenderer, 0, 0, 110, 14);
            nameField.setText(data.presetName);
            nameField.setEnableBackgroundDrawing(true);
            editBtn = new SubButton("Edit", 0, 0, 40, 14);
            deleteBtn = new SubButton("X", 0, 0, 15, 14);
        }

        public void draw(int y, int mx, int my) {
            int xStart = width / 2 - sizeX / 2 + 5;
            nameField.x = xStart; nameField.y = y;
            editBtn.setX(xStart + 120); editBtn.setY(y);
            deleteBtn.setX(xStart + 165); deleteBtn.setY(y);
            nameField.drawTextBox(); editBtn.draw(mx, my); deleteBtn.draw(mx, my);
        }

        public void saveName() {
            String newName = nameField.getText().trim();
            if (newName.isEmpty() || newName.equals(data.presetName)) return;
            File newFile = new File(presetDir, newName + ".json");
            if (file.renameTo(newFile)) {
                if (CyvClientConfig.getString("selectedPreset", "").equals(file.getName())) {
                    CyvClientConfig.set("selectedPreset", newFile.getName());
                }
                this.file = newFile;
                data.presetName = newName;
                savePresetToFile(file, data);
            }
            nameField.setFocused(false);
            ConfigLoader.save(CyvForge.config, false);
        }

        public boolean mouseClicked(int y, int mx, int my, int btn) {
            int xStart = width / 2 - sizeX / 2 + 5;
            nameField.mouseClicked(mx, my, btn);
            if (mx >= xStart && mx <= xStart + sizeX && my >= y && my <= y + 16) {
                if (mx >= xStart + 165 && mx <= xStart + 185) {
                    boolean wasSelected = CyvClientConfig.getString("selectedPreset", "").equals(file.getName());
                    if (file.delete()) {
                        if (wasSelected) {
                            CyvClientConfig.set("selectedPreset", "none");
                        }
                        refreshPresets();
                    }
                    return true;
                }
                saveCurrentLayoutToSelected();
                CyvClientConfig.set("selectedPreset", file.getName());
                applyPreset();
                ConfigLoader.save(CyvForge.config, false);
                if (mx >= xStart + 120 && mx <= xStart + 160) {
                    mc.displayGuiScreen(new GuiHUDPositions(true, true));
                }
                return true;
            }
            return false;
        }

        public void applyPreset() {
            for (DraggableHUDElement e : HUDManager.registeredRenderers) {
                String dataStr = data.positions.get(e.getName());
                if (dataStr != null) {
                    String[] pts = dataStr.split(",");
                    e.save(new ScreenPosition(Integer.parseInt(pts[0]), Integer.parseInt(pts[1])));
                    e.load();
                    if (pts.length >= 3) e.isVisible = Boolean.parseBoolean(pts[2]);
                    if (pts.length >= 4) e.setEnabled(Boolean.parseBoolean(pts[3]));
                }
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        int eventDWheel = net.cyvforge.event.events.GuiHandler.scrollBuffer;
        net.cyvforge.event.events.GuiHandler.scrollBuffer = 0;

        if (eventDWheel != 0 && !org.lwjgl.input.Mouse.isButtonDown(0)) {
            vScroll -= eventDWheel * 0.05;
        }

        super.handleMouseInput();
    }

    @Override
    public void updateScreen() {
        scroll += vScroll;
        vScroll *= 0.7f;
        if (scroll < 0) scroll = 0;
        if (scroll > maxScroll) scroll = maxScroll;
    }
}