package com.axmed555.visuals;

import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TitleScreenModifier {
    
    @SubscribeEvent
    public void onTitleScreenRender(ScreenEvent.Render event) {
        if (event.getScreen() instanceof TitleScreen) {
            // Добавляем красивый текст "Visuals by axmed555"
            // Анимированные частицы на фоне
            // Специальные эффекты как у Nursultan
        }
    }
}
