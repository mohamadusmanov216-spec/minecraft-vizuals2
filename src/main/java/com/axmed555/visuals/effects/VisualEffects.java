package com.axmed555.visuals.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VisualEffects {
    private static final Minecraft mc = Minecraft.getInstance();
    private static int tickCounter = 0;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && mc.player != null) {
            tickCounter++;
            
            // Эффекты каждые 10 тиков (0.5 секунды)
            if (tickCounter % 10 == 0) {
                spawnParticleEffects();
            }
            
            // Специальные эффекты каждые 100 тиков (5 секунд)
            if (tickCounter % 100 == 0) {
                spawnSpecialEffects();
            }
        }
    }

    private void spawnParticleEffects() {
        if (mc.level == null || mc.player == null) return;
        
        Vec3 playerPos = mc.player.position();
        
        // Частицы вокруг игрока
        for (int i = 0; i < 5; i++) {
            double x = playerPos.x + (Math.random() - 0.5) * 3;
            double y = playerPos.y + Math.random() * 2;
            double z = playerPos.z + (Math.random() - 0.5) * 3;
            
            mc.level.addParticle(
                ParticleTypes.GLOW,
                x, y, z,
                0, 0.1, 0
            );
        }
    }

    private void spawnSpecialEffects() {
        if (mc.level == null || mc.player == null) return;
        
        Vec3 playerPos = mc.player.position();
        
        // Большой взрыв частиц
        for (int i = 0; i < 20; i++) {
            double x = playerPos.x + (Math.random() - 0.5) * 5;
            double y = playerPos.y + Math.random() * 3;
            double z = playerPos.z + (Math.random() - 0.5) * 5;
            
            mc.level.addParticle(
                ParticleTypes.FIREWORK,
                x, y, z,
                (Math.random() - 0.5) * 0.5,
                Math.random() * 0.5,
                (Math.random() - 0.5) * 0.5
            );
        }
    }

    // Метод для вызова эффектов по горячим клавишам
    public static void spawnCustomEffect(String effectType) {
        if (mc.level == null || mc.player == null) return;
        
        Vec3 playerPos = mc.player.position();
        
        switch (effectType) {
            case "BIND1" -> spawnCircleEffect(playerPos, ParticleTypes.HEART);
            case "BIND2" -> spawnCircleEffect(playerPos, ParticleTypes.ELECTRIC_SPARK);
            case "BIND3" -> spawnCircleEffect(playerPos, ParticleTypes.GLOW_SQUID_INK);
            case "BIND4" -> spawnCircleEffect(playerPos, ParticleTypes.DRAGON_BREATH);
        }
    }

    private static void spawnCircleEffect(Vec3 center, net.minecraft.core.particles.ParticleOptions particle) {
        int points = 16;
        double radius = 2.0;
        
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            double x = center.x + radius * Math.cos(angle);
            double z = center.z + radius * Math.sin(angle);
            
            mc.level.addParticle(
                particle,
                x, center.y + 1, z,
                0, 0.1, 0
            );
        }
    }
}
