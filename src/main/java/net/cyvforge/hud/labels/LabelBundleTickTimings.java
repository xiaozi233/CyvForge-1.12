package net.cyvforge.hud.labels;

import net.cyvforge.config.CyvClientColorHelper;
import net.cyvforge.config.CyvClientConfig;
import net.cyvforge.event.events.ParkourTickListener;
import net.cyvforge.hud.LabelBundle;
import net.cyvforge.hud.structure.DraggableHUDElement;
import net.cyvforge.hud.structure.ScreenPosition;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;

public class LabelBundleTickTimings extends LabelBundle {

    public LabelBundleTickTimings() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLastTiming";}
            public String getDisplayName() {return "Last Timing";}
            public int getWidth() {
                FontRenderer font = mc.fontRenderer;
                return font.getStringWidth("Last Timing: [Timing & numbers here]");
            }
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 56);}
            public void render(ScreenPosition pos) {
            if (!this.isVisible) return;
                renderLabel(pos, "Last Timing: ", ParkourTickListener.lastTiming, true);
            }
            public void renderDummy(ScreenPosition pos) {
                renderLabel(pos, "Last Timing: ", "[Timing]", this.isVisible);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelAirtime";}
            public String getDisplayName() {return "Airtime";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 65);}
            public void render(ScreenPosition pos) {
            if (!this.isVisible) return;
                renderLabel(pos, "Airtime: ", String.valueOf(ParkourTickListener.lastAirtime), true);
            }
            public void renderDummy(ScreenPosition pos) {
                renderLabel(pos, "Airtime: ", "0", this.isVisible);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelStoptime";}
            public String getDisplayName() {return "Stoptime";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 74);}
            public void render(ScreenPosition pos) {
            if (!this.isVisible) return;
                renderLabel(pos, "Stoptime: ", String.valueOf(ParkourTickListener.lastStopTime), true);
            }
            public void renderDummy(ScreenPosition pos) {
                renderLabel(pos, "Stoptime: ", "0", this.isVisible);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelWaittime";}
            public String getDisplayName() {return "Waittime";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 83);}
            public void render(ScreenPosition pos) {
            if (!this.isVisible) return;
                renderLabel(pos, "Waittime: ", String.valueOf(ParkourTickListener.lastWaitTime), true);
            }
            public void renderDummy(ScreenPosition pos) {
                renderLabel(pos, "Waittime: ", "0", this.isVisible);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelRuntime";}
            public String getDisplayName() {return "Runtime";}
            public int getWidth() {
                if (CyvClientConfig.getBoolean("hideRuntimeLabelName", false)) {
                    return mc.fontRenderer.getStringWidth("000");
                }
                return getLabelWidth(getDisplayName());
            }
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 92);}
            public void render(ScreenPosition pos) {
                if (!this.isVisible) return;
                int val = ParkourTickListener.lastRunTime;
                boolean hideIfZero = CyvClientConfig.getBoolean("hideRuntimeIfZero", false);
                boolean hideName = CyvClientConfig.getBoolean("hideRuntimeLabelName", false);

                if (val == 0 && hideIfZero) return;

                String prefix = hideName ? "" : "Runtime: ";

                renderLabel(pos, prefix, String.valueOf(val), true);
            }
            public void renderDummy(ScreenPosition pos) {
                boolean hideName = CyvClientConfig.getBoolean("hideRuntimeLabelName", false);
                String prefix = hideName ? "" : "Runtime: ";
                renderLabel(pos, prefix, "0", this.isVisible);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelTier";}
            public String getDisplayName() {return "Tier";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 137);}
            public void render(ScreenPosition pos) {
            if (!this.isVisible) return;
                int tier = 12 - ParkourTickListener.lastAirtime;
                renderLabel(pos, "Tier: ", String.valueOf(tier), true);
            }
            public void renderDummy(ScreenPosition pos) {
                renderLabel(pos, "Tier: ", "0", this.isVisible);
            }
        });
    }
    
    private void renderLabel(ScreenPosition pos, String label, String value, boolean active) {
        long color1 = active ? CyvClientColorHelper.color1.getDrawColor() : 0xFFAAAAAA;
        long color2 = active ? CyvClientColorHelper.color2.getDrawColor() : 0xFFAAAAAA;
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;

        font.drawString(label, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, (int)color1, true);
        font.drawString(value, pos.getAbsoluteX() + 1 + font.getStringWidth(label), pos.getAbsoluteY() + 1, (int)color2, true);
    }
}
