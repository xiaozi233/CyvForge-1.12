package net.cyvforge.integration;

import net.cyvforge.CyvForge;
import java.lang.reflect.Method;

public class OptiFineIntegration {

    private static boolean available;
    private static Method getFpsMinMethod = null;

    static {
        try {
            Class<?> configClass = Class.forName("Config");
            getFpsMinMethod = configClass.getMethod("getFpsMin");
            available = true;
        } catch (Exception e) {
            available = false;
        }
    }

    public static int getMinFps() {
        if (!available || getFpsMinMethod == null) {
            return -1;
        }

        try {
            Object result = getFpsMinMethod.invoke(null);
            return (int) result;
        } catch (Exception e) {
            available = false;
            CyvForge.LOGGER.error("An unexpected error occurred during invoke OptiFine method.");
            return -1;
        }
    }
}