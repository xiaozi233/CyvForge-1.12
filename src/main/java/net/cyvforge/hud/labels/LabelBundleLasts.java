package net.cyvforge.hud.labels;

import net.cyvforge.CyvForge;
import net.cyvforge.config.CyvClientColorHelper;
import net.cyvforge.config.CyvClientConfig;
import net.cyvforge.event.events.ParkourTickListener;
import net.cyvforge.hud.LabelBundle;
import net.cyvforge.hud.structure.DraggableHUDElement;
import net.cyvforge.hud.structure.ScreenPosition;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MovementInput;

import java.text.DecimalFormat;

public class LabelBundleLasts extends LabelBundle {

    public LabelBundleLasts() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLast45";}
            public String getDisplayName() {return "Last 45";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 74);}
            public void render(ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                DecimalFormat df = CyvForge.df;
                String str = df.format(ParkourTickListener.last45);

                drawString("Last 45: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(str, pos.getAbsoluteX() + 1 + font.getStringWidth("Last 45: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(ScreenPosition pos) {
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                StringBuilder str = new StringBuilder("0.");
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str.append("0");

                drawString("Last 45: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(str.toString(), pos.getAbsoluteX() + 1 + font.getStringWidth("Last 45: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLastInput";}
            public String getDisplayName() {return "Last Input";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 92);}
            public void render(ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                String str;
                if (CyvClientConfig.getBoolean("WADdisplay", false)) {
                    str = (mc.gameSettings.keyBindForward.isKeyDown() ? "W" : "")
                            + (mc.gameSettings.keyBindLeft.isKeyDown() ? "A" : "")
                            + (mc.gameSettings.keyBindBack.isKeyDown() ? "S" : "")
                            + (mc.gameSettings.keyBindRight.isKeyDown() ? "D" : "");
                } else {
                    MovementInput input = mc.player.movementInput;
                    str = (input.moveForward > 0 ? "W" : "")
                            + (input.moveStrafe > 0 ? "A" : "")
                            + (input.moveForward < 0 ? "S" : "")
                            + (input.moveStrafe < 0 ? "D" : "");
                }

                if (str.isEmpty()) str = "";

                drawString("Last Input: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(str, pos.getAbsoluteX() + 1 + font.getStringWidth("Last Input: "),
                        pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(ScreenPosition pos) {
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                drawString("Last Input: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString("WASD", pos.getAbsoluteX() + 1 + font.getStringWidth("Last Input: "),
                        pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLastTurningYaw";}
            public String getDisplayName() {return "Last Turning";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 83);}
            public void render(ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                DecimalFormat df = CyvForge.df;
                String z = df.format(ParkourTickListener.lastTurning);

                drawString("Last Turning: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(z+"\u00B0", pos.getAbsoluteX() + 1 + font.getStringWidth("Last Turning: "),
                        pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(ScreenPosition pos) {
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                StringBuilder str = new StringBuilder("0.");
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str.append("0");

                drawString("Last Turning: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(str.toString()+"\u00B0", pos.getAbsoluteX() + 1 + font.getStringWidth("Last Turning: "),
                        pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLastSidestep";}
            public String getDisplayName() {return "Last Sidestep";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 101);}
            public void render(ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                String str = "None";

                if (ParkourTickListener.sidestep == 0) {
                    str = "WAD " + ParkourTickListener.sidestepTime + " ticks";
                }
                else if (ParkourTickListener.sidestep == 1) {
                    str = "WDWA";
                }
                else if (ParkourTickListener.sidestep == 2 && CyvClientConfig.getBoolean("markInSidestep", true)) {
                    str = "Mark " + ParkourTickListener.sidestepTime + "t";
                }

                drawString("Last Sidestep: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(str, pos.getAbsoluteX() + 1 + font.getStringWidth("Last Sidestep: "),
                        pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(ScreenPosition pos) {
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                drawString("Last Sidestep: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString("WAD", pos.getAbsoluteX() + 1 + font.getStringWidth("Last Sidestep: "),
                        pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelGrounded";}
            public String getDisplayName() {return "Grounded";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(400, 400);}
            public void render(ScreenPosition pos) {
                if (!this.isVisible) return;
                boolean isGrounded = ParkourTickListener.airtime == 0;
                long color = isGrounded ? 0x55FF55L : 0xFF5555L;
                FontRenderer font = mc.fontRenderer;
                String isGroundedString = isGrounded ? "True" : "False";

                drawString("Grounded: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color);
                drawString(isGroundedString, pos.getAbsoluteX() + 1 + font.getStringWidth("Grounded: "),
                        pos.getAbsoluteY() + 1, color);
            }
            public void renderDummy(ScreenPosition pos) {
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                drawString("Grounded: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString("True/False", pos.getAbsoluteX() + 1 + font.getStringWidth("Grounded: "),
                        pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelRuntime";}
            public String getDisplayName() {return "Runtime";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(400, 409);}
            public void render(ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                int runtime = ParkourTickListener.lastRunTime;
                drawString("Runtime: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(runtime, pos.getAbsoluteX() + 1 + font.getStringWidth("Runtime: "),
                        pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(ScreenPosition pos) {
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                drawString("Runtime: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(0, pos.getAbsoluteX() + 1 + font.getStringWidth("Runtime: "),
                        pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelStoptime";}
            public String getDisplayName() {return "Stoptime";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(400, 418);}
            public void render(ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                int stoptime = ParkourTickListener.lastStopTime;
                drawString("Stoptime: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(stoptime, pos.getAbsoluteX() + 1 + font.getStringWidth("Stoptime: "),
                        pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(ScreenPosition pos) {
                long color1 = CyvClientColorHelper.color1.getDrawColor();
                long color2 = CyvClientColorHelper.color2.getDrawColor();
                FontRenderer font = mc.fontRenderer;

                drawString("Stoptime: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(0, pos.getAbsoluteX() + 1 + font.getStringWidth("Stoptime: "),
                        pos.getAbsoluteY() + 1, color2);
            }
        });
    }

}
