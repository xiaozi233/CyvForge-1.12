package net.cyvforge.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.cyvforge.cyvforge.Tags;
import org.apache.logging.log4j.LogManager;

public class DiscordRPCHandler {
    private boolean running = true;
    private long created = 0;
    public static DiscordRPCHandler instance;

    public DiscordRPCHandler() {
        instance = this;
    }

    public void start() {
        try {
            this.created = System.currentTimeMillis();
            DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
                @Override
                public void apply(DiscordUser user) {
                    LogManager.getLogger().info("DiscordRPC starting...");
                    DiscordRPCHandler.instance.updateStatus("In Main Menu", null, null);
                }

            }).build();

            DiscordRPC.discordInitialize("1122509451920936971", handlers, running);
            Thread RPCThread = new Thread("DiscordRPC Callback") {
                public void run() {
                    while (running) {
                        DiscordRPC.discordRunCallbacks();
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                        }
                    }
                }
            };
            RPCThread.setPriority(Thread.MIN_PRIORITY);
            RPCThread.start();

        } catch (Throwable e) {
            //womp womp no more discordrpc
            LogManager.getLogger().error(e);
        }
    }

    public void shutdown() {
        running = false;
        DiscordRPC.discordShutdown();
    }

    //status updates
    public void updateStatus(String firstline, String smallImage, String ip) {
        try {
            DiscordRichPresence.Builder b = new DiscordRichPresence.Builder("");
            b.setBigImage("icon", "CyvForge-1.12 " + Tags.VERSION);
            b.setDetails(firstline);
            b.setStartTimestamps(this.created);
            b.setSmallImage(smallImage, ip);

            DiscordRPC.discordUpdatePresence(b.build());
        } catch (Throwable ignored) {}
    }

}
