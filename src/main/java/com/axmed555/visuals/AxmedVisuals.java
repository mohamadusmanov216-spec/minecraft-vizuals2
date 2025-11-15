package com.axmed555.visuals;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("axmed555_visuals")
public class AxmedVisuals {
    public static final String MOD_ID = "axmed555_visuals";
    public static final Logger LOGGER = LogManager.getLogger();
    private static ModConfig clientConfig;

    public AxmedVisuals() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onConfigLoad);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerKeys);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
        
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory(
                (mc, screen) -> new com.axmed555.visuals.gui.ConfigScreen(screen)));
        
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    private void registerKeys(net.minecraftforge.client.event.RegisterKeyMappingsEvent event) {
        event.register(KeyInputHandler.openGuiKey);
        event.register(KeyInputHandler.bind1Key);
        event.register(KeyInputHandler.bind2Key);
        event.register(KeyInputHandler.bind3Key);
        event.register(KeyInputHandler.bind4Key);
    }

    private void onConfigLoad(final ModConfig.Loading event) {
        if (event.getConfig().getType() == ModConfig.Type.CLIENT) {
            clientConfig = event.getConfig();
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(KeyInputHandler.class);
        LOGGER.info("Visuals by axmed555 initialized!");
    }

    @SubscribeEvent
    public void onScreenInit(ScreenEvent.Init event) {
        // Кастомный начальный экран с "Visuals by axmed555"
        if (event.getScreen() instanceof net.minecraft.client.gui.screens.TitleScreen) {
            // Здесь будет код для красивого начального экрана
            LOGGER.info("Custom title screen loaded: Visuals by axmed555");
        }
    }

    public static void saveConfig() {
        if (clientConfig != null) {
            clientConfig.save();
        }
    }
}
