package com.axmed555.visuals;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    private static final String CATEGORY = "key.categories.axmed555_visuals";

    public static final KeyMapping openGuiKey = new KeyMapping(
        "key.axmed555_visuals.open_gui",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_RIGHT_SHIFT,
        CATEGORY
    );

    public static final KeyMapping bind1Key = new KeyMapping(
        "key.axmed555_visuals.bind1",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_KP_1,
        CATEGORY
    );

    public static final KeyMapping bind2Key = new KeyMapping(
        "key.axmed555_visuals.bind2",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_KP_2,
        CATEGORY
    );

    public static final KeyMapping bind3Key = new KeyMapping(
        "key.axmed555_visuals.bind3",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_KP_3,
        CATEGORY
    );

    public static final KeyMapping bind4Key = new KeyMapping(
        "key.axmed555_visuals.bind4",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_KP_4,
        CATEGORY
    );

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (openGuiKey.consumeClick()) {
            mc.setScreen(new com.axmed555.visuals.gui.ConfigScreen(mc.screen));
        }

        if (bind1Key.consumeClick()) {
            sendBindMessage(Config.BIND_MESSAGE_1.get());
        }

        if (bind2Key.consumeClick()) {
            sendBindMessage(Config.BIND_MESSAGE_2.get());
        }

        if (bind3Key.consumeClick()) {
            sendBindMessage(Config.BIND_MESSAGE_3.get());
        }

        if (bind4Key.consumeClick()) {
            sendBindMessage(Config.BIND_MESSAGE_4.get());
        }
    }

    private static void sendBindMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.getConnection() != null) {
            mc.player.connection.sendChat(message);
        }
    }
}
