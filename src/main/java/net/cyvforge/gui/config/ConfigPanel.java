package net.cyvforge.gui.config;


public interface ConfigPanel {
    boolean mouseInBounds(int mouseX, int mouseY);
    void mouseClicked(int mouseX, int mouseY, int mouseButton);
    void keyTyped(char typedChar, int keyCode);
    void draw(int mouseX, int mouseY, int scroll);
    default void update() {}
    void mouseDragged(int mouseX, int mouseY);
    void save();
    default void select() {}
    default void unselect() {}

    default void onValueChange() {}
    default boolean isEnabled() { return true; }
    default int getIndex() { return 0; }
    void setPos(int x, int y, int width);
}