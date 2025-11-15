package com.axmed555.visuals;

import com.mojang.blaze3d.vertex.PoseStack;
import com.axmed555.visuals.effects.VisualEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.joml.Vector3f;

public class ClientEventHandler {
    private final Minecraft mc = Minecraft.getInstance();
    private boolean wasOnGround = false;
    private boolean previousWasOnGround = false;
    private long lastKillTime = 0;
    private Vec3 cachedJumpMotion = null;
    private long lastTrajectoryTick = 0;
        public ClientEventHandler() {
        MinecraftForge.EVENT_BUS.register(new VisualEffects());
    }


    @SubscribeEvent
    public void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
        
        Player player = mc.player;
        if (player == null) return;

        PoseStack poseStack = event.getPoseStack();
        Vec3 cameraPos = event.getCamera().getPosition();

        if (Config.SHOW_TRAIL.get()) {
            spawnTrailParticles(player);
        }

        if (Config.SHOW_HITBOX.get()) {
            renderEnlargedHitbox(player, poseStack, cameraPos, event.getPartialTick());
        }

        if (Config.HAT_STYLE.get() != Config.HatStyle.NONE) {
            renderHat(player, poseStack, cameraPos, event.getPartialTick());
        }
        
        if (Config.SHOW_TRAJECTORIES.get()) {
            renderTrajectories(player, poseStack, cameraPos);
        }
        
        if (Config.SHOW_SKY_EFFECTS.get()) {
            renderSkyEffects(player);
        }
    }

    private void spawnTrailParticles(Player player) {
        Config.TrailStyle style = Config.TRAIL_STYLE.get();
        
        switch (style) {
            case NORMAL:
                spawnNormalTrail(player);
                break;
            case FIRE:
                spawnFireTrail(player);
                break;
            case STARS:
                spawnStarsTrail(player);
                break;
            case LIGHTNING:
                spawnLightningTrail(player);
                break;
            case MAGIC:
                spawnMagicTrail(player);
                break;
            case HEARTS:
                spawnHeartsTrail(player);
                break;
            case SPIRAL:
                spawnSpiralTrail(player);
                break;
            case ENDER:
                spawnEnderTrail(player);
                break;
        }
        
        if (Config.SHOW_JUMP_EFFECTS.get() && !player.onGround() && wasOnGround) {
            spawnJumpEffect(player);
        }
        wasOnGround = player.onGround();
        
        if (Config.SHOW_SPRINT_EFFECTS.get() && player.isSprinting()) {
            spawnSprintEffect(player);
        }
    }
    
    private void spawnNormalTrail(Player player) {
        if (player.level().random.nextFloat() < 0.3f) {
            float r = Config.TRAIL_RED.get() / 255f;
            float g = Config.TRAIL_GREEN.get() / 255f;
            float b = Config.TRAIL_BLUE.get() / 255f;
            
            double x = player.getX() + (player.level().random.nextDouble() - 0.5) * 0.5;
            double y = player.getY() + player.level().random.nextDouble() * player.getBbHeight();
            double z = player.getZ() + (player.level().random.nextDouble() - 0.5) * 0.5;
            
            player.level().addParticle(new DustParticleOptions(new Vector3f(r, g, b), 1.0F), 
                x, y, z, 0, 0, 0);
        }
    }
    
    private void spawnFireTrail(Player player) {
        if (player.level().random.nextFloat() < 0.4f) {
            double x = player.getX() + (player.level().random.nextDouble() - 0.5) * 0.5;
            double y = player.getY() + player.level().random.nextDouble() * 0.5;
            double z = player.getZ() + (player.level().random.nextDouble() - 0.5) * 0.5;
            
            player.level().addParticle(ParticleTypes.FLAME, x, y, z, 0, 0.05, 0);
            if (player.level().random.nextFloat() < 0.3f) {
                player.level().addParticle(ParticleTypes.LAVA, x, y, z, 0, 0, 0);
            }
        }
    }
    
    private void spawnStarsTrail(Player player) {
        if (player.level().random.nextFloat() < 0.25f) {
            double x = player.getX() + (player.level().random.nextDouble() - 0.5) * 0.6;
            double y = player.getY() + player.level().random.nextDouble() * player.getBbHeight();
            double z = player.getZ() + (player.level().random.nextDouble() - 0.5) * 0.6;
            
            player.level().addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0.01, 0);
            if (player.level().random.nextFloat() < 0.5f) {
                player.level().addParticle(ParticleTypes.GLOW, x, y, z, 0, 0, 0);
            }
        }
    }
    
    private void spawnLightningTrail(Player player) {
        if (player.level().random.nextFloat() < 0.2f) {
            double x = player.getX() + (player.level().random.nextDouble() - 0.5) * 0.3;
            double y = player.getY() + player.level().random.nextDouble() * player.getBbHeight();
            double z = player.getZ() + (player.level().random.nextDouble() - 0.5) * 0.3;
            
            player.level().addParticle(ParticleTypes.ELECTRIC_SPARK, x, y, z, 0, 0.1, 0);
            player.level().addParticle(ParticleTypes.WAX_ON, x, y, z, 
                (player.level().random.nextDouble() - 0.5) * 0.1, 
                player.level().random.nextDouble() * 0.1, 
                (player.level().random.nextDouble() - 0.5) * 0.1);
        }
    }
    
    private void spawnMagicTrail(Player player) {
        if (player.level().random.nextFloat() < 0.35f) {
            double x = player.getX() + (player.level().random.nextDouble() - 0.5) * 0.5;
            double y = player.getY() + player.level().random.nextDouble() * player.getBbHeight();
            double z = player.getZ() + (player.level().random.nextDouble() - 0.5) * 0.5;
            
            player.level().addParticle(ParticleTypes.ENCHANT, x, y, z, 
                (player.level().random.nextDouble() - 0.5) * 0.1, 
                player.level().random.nextDouble() * 0.1, 
                (player.level().random.nextDouble() - 0.5) * 0.1);
            if (player.level().random.nextFloat() < 0.4f) {
                player.level().addParticle(ParticleTypes.WITCH, x, y, z, 0, 0.05, 0);
            }
        }
    }
    
    private void spawnHeartsTrail(Player player) {
        if (player.level().random.nextFloat() < 0.15f) {
            double x = player.getX() + (player.level().random.nextDouble() - 0.5) * 0.4;
            double y = player.getY() + player.level().random.nextDouble() * player.getBbHeight();
            double z = player.getZ() + (player.level().random.nextDouble() - 0.5) * 0.4;
            
            player.level().addParticle(ParticleTypes.HEART, x, y, z, 0, 0.05, 0);
        }
    }
    
    private void spawnSpiralTrail(Player player) {
        if (player.level().random.nextFloat() < 0.3f) {
            long time = player.level().getGameTime();
            double angle = (time % 360) * Math.PI / 180.0;
            double radius = 0.5;
            
            double x = player.getX() + Math.cos(angle) * radius;
            double y = player.getY() + (time % 40) * 0.05;
            double z = player.getZ() + Math.sin(angle) * radius;
            
            float r = Config.TRAIL_RED.get() / 255f;
            float g = Config.TRAIL_GREEN.get() / 255f;
            float b = Config.TRAIL_BLUE.get() / 255f;
            
            player.level().addParticle(new DustParticleOptions(new Vector3f(r, g, b), 1.2F), 
                x, y, z, 0, 0, 0);
        }
    }
    
    private void spawnEnderTrail(Player player) {
        if (player.level().random.nextFloat() < 0.3f) {
            double x = player.getX() + (player.level().random.nextDouble() - 0.5) * 0.5;
            double y = player.getY() + player.level().random.nextDouble() * player.getBbHeight();
            double z = player.getZ() + (player.level().random.nextDouble() - 0.5) * 0.5;
            
            player.level().addParticle(ParticleTypes.PORTAL, x, y, z, 
                (player.level().random.nextDouble() - 0.5) * 0.2, 
                -player.level().random.nextDouble() * 0.1, 
                (player.level().random.nextDouble() - 0.5) * 0.2);
            if (player.level().random.nextFloat() < 0.2f) {
                player.level().addParticle(ParticleTypes.REVERSE_PORTAL, x, y, z, 0, 0.05, 0);
            }
        }
    }
    
    private void spawnJumpEffect(Player player) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        
        for (int i = 0; i < 20; i++) {
            double angle = (i / 20.0) * Math.PI * 2;
            double radius = 0.8;
            
            double px = x + Math.cos(angle) * radius;
            double pz = z + Math.sin(angle) * radius;
            
            player.level().addParticle(ParticleTypes.CLOUD, px, y + 0.1, pz, 0, 0, 0);
        }
    }
    
    private void spawnSprintEffect(Player player) {
        if (player.level().random.nextFloat() < 0.3f) {
            double x = player.getX() + (player.level().random.nextDouble() - 0.5) * 0.3;
            double y = player.getY() + 0.1;
            double z = player.getZ() + (player.level().random.nextDouble() - 0.5) * 0.3;
            
            player.level().addParticle(ParticleTypes.POOF, x, y, z, 0, 0, 0);
        }
    }

    private void renderEnlargedHitbox(Player player, PoseStack poseStack, 
                                     Vec3 cameraPos, float partialTicks) {
        AABB box = player.getBoundingBox();
        double multiplier = 1.5;
        
        double centerX = (box.minX + box.maxX) / 2;
        double centerY = (box.minY + box.maxY) / 2;
        double centerZ = (box.minZ + box.maxZ) / 2;
        
        double width = (box.maxX - box.minX) * multiplier / 2;
        double height = (box.maxY - box.minY) * multiplier / 2;
        double depth = (box.maxZ - box.minZ) * multiplier / 2;
        
        AABB enlargedBox = new AABB(
            centerX - width, centerY - height, centerZ - depth,
            centerX + width, centerY + height, centerZ + depth
        );

        poseStack.pushPose();
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth(2.0f);

        float r = Config.HITBOX_RED.get() / 255f;
        float g = Config.HITBOX_GREEN.get() / 255f;
        float b = Config.HITBOX_BLUE.get() / 255f;
        
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
        LevelRenderer.renderLineBox(poseStack, bufferSource.getBuffer(
            net.minecraft.client.renderer.RenderType.lines()), 
            enlargedBox, r, g, b, 1.0f);

        bufferSource.endBatch();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        
        poseStack.popPose();
    }

    private void renderHat(Player player, PoseStack poseStack, 
                          Vec3 cameraPos, float partialTicks) {
        Config.HatStyle style = Config.HAT_STYLE.get();
        
        poseStack.pushPose();
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        
        double x = player.getX();
        double y = player.getY() + player.getBbHeight() + 0.5;
        double z = player.getZ();
        
        switch (style) {
            case CROWN:
                renderCrown(x, y, z);
                break;
            case AURA:
                renderAura(x, y - 0.5, z);
                break;
            case WINGS:
                renderWings(x, y - 0.3, z);
                break;
            case HALO:
                renderHalo(x, y, z);
                break;
        }
        
        poseStack.popPose();
    }

    private void renderCrown(double x, double y, double z) {
        if (mc.player.level().random.nextFloat() < 0.2f) {
            float gradient = mc.player.level().random.nextFloat();
            float r = lerp(Config.CROWN_R1.get() / 255f, Config.CROWN_R2.get() / 255f, gradient);
            float g = lerp(Config.CROWN_G1.get() / 255f, Config.CROWN_G2.get() / 255f, gradient);
            float b = lerp(Config.CROWN_B1.get() / 255f, Config.CROWN_B2.get() / 255f, gradient);
            
            double px = x + (mc.player.level().random.nextDouble() - 0.5) * 0.4;
            double py = y;
            double pz = z + (mc.player.level().random.nextDouble() - 0.5) * 0.4;
            
            mc.player.level().addParticle(new DustParticleOptions(new Vector3f(r, g, b), 1.5F), 
                px, py, pz, 0, 0.05, 0);
        }
    }

    private void renderAura(double x, double y, double z) {
        if (mc.player.level().random.nextFloat() < 0.3f) {
            double angle = mc.player.level().random.nextDouble() * Math.PI * 2;
            double radius = 0.6;
            double heightOffset = mc.player.level().random.nextDouble() * 2;
            
            float gradient = (float)(heightOffset / 2.0);
            float r = lerp(Config.AURA_R1.get() / 255f, Config.AURA_R2.get() / 255f, gradient);
            float g = lerp(Config.AURA_G1.get() / 255f, Config.AURA_G2.get() / 255f, gradient);
            float b = lerp(Config.AURA_B1.get() / 255f, Config.AURA_B2.get() / 255f, gradient);
            
            mc.player.level().addParticle(new DustParticleOptions(new Vector3f(r, g, b), 1.0F), 
                x + Math.cos(angle) * radius, 
                y + heightOffset, 
                z + Math.sin(angle) * radius, 
                0, 0.1, 0);
        }
    }

    private void renderWings(double x, double y, double z) {
        if (mc.player.level().random.nextFloat() < 0.25f) {
            double offsetX = (mc.player.level().random.nextDouble() - 0.5) * 1.2;
            float side = offsetX > 0 ? 1.0f : 0.0f;
            
            float r = lerp(Config.WINGS_R1.get() / 255f, Config.WINGS_R2.get() / 255f, side);
            float g = lerp(Config.WINGS_G1.get() / 255f, Config.WINGS_G2.get() / 255f, side);
            float b = lerp(Config.WINGS_B1.get() / 255f, Config.WINGS_B2.get() / 255f, side);
            
            mc.player.level().addParticle(new DustParticleOptions(new Vector3f(r, g, b), 1.2F), 
                x + offsetX, 
                y, 
                z, 
                0, -0.05, 0);
        }
    }

    private void renderHalo(double x, double y, double z) {
        if (mc.player.level().random.nextFloat() < 0.3f) {
            double angle = mc.player.level().random.nextDouble() * Math.PI * 2;
            double radius = 0.5;
            
            float gradient = (float)(angle / (Math.PI * 2));
            float r = lerp(Config.HALO_R1.get() / 255f, Config.HALO_R2.get() / 255f, gradient);
            float g = lerp(Config.HALO_G1.get() / 255f, Config.HALO_G2.get() / 255f, gradient);
            float b = lerp(Config.HALO_B1.get() / 255f, Config.HALO_B2.get() / 255f, gradient);
            
            mc.player.level().addParticle(new DustParticleOptions(new Vector3f(r, g, b), 1.3F), 
                x + Math.cos(angle) * radius, 
                y, 
                z + Math.sin(angle) * radius, 
                0, 0, 0);
        }
    }
    
    private float lerp(float start, float end, float t) {
        return start + (end - start) * t;
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().level().isClientSide) return;
        if (mc.player == null) return;
        
        Entity entity = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        
        if (Config.SHOW_HIT_EFFECTS.get() && entity instanceof LivingEntity) {
            spawnHitEffects((LivingEntity) entity);
        }
        
        if (Config.SHOW_ATTACK_EFFECTS.get() && attacker == mc.player && entity instanceof LivingEntity) {
            spawnAttackEffects((LivingEntity) entity);
        }
        
        if (Config.SHOW_KILL_EFFECTS.get() && entity instanceof LivingEntity && attacker == mc.player) {
            LivingEntity living = (LivingEntity) entity;
            if (living.getHealth() - event.getAmount() <= 0) {
                spawnKillEffects(living);
            }
        }
    }

    private void spawnHitEffects(LivingEntity entity) {
        float r = Config.HIT_EFFECT_RED.get() / 255f;
        float g = Config.HIT_EFFECT_GREEN.get() / 255f;
        float b = Config.HIT_EFFECT_BLUE.get() / 255f;
        
        for (int i = 0; i < 10; i++) {
            double x = entity.getX() + (entity.level().random.nextDouble() - 0.5) * entity.getBbWidth();
            double y = entity.getY() + entity.level().random.nextDouble() * entity.getBbHeight();
            double z = entity.getZ() + (entity.level().random.nextDouble() - 0.5) * entity.getBbWidth();
            
            double vx = (entity.level().random.nextDouble() - 0.5) * 0.2;
            double vy = entity.level().random.nextDouble() * 0.2;
            double vz = (entity.level().random.nextDouble() - 0.5) * 0.2;
            
            entity.level().addParticle(new DustParticleOptions(new Vector3f(r, g, b), 1.0F), 
                x, y, z, vx, vy, vz);
        }
    }
    
    private void spawnAttackEffects(LivingEntity entity) {
        double x = entity.getX();
        double y = entity.getY() + entity.getBbHeight() / 2;
        double z = entity.getZ();
        
        for (int i = 0; i < 15; i++) {
            double angle = (i / 15.0) * Math.PI * 2;
            double radius = 0.5;
            
            double px = x + Math.cos(angle) * radius;
            double pz = z + Math.sin(angle) * radius;
            
            entity.level().addParticle(ParticleTypes.CRIT, px, y, pz, 
                Math.cos(angle) * 0.3, 
                0.1, 
                Math.sin(angle) * 0.3);
        }
        
        entity.level().addParticle(ParticleTypes.FLASH, x, y, z, 0, 0, 0);
    }
    
    private void spawnKillEffects(LivingEntity entity) {
        double x = entity.getX();
        double y = entity.getY() + entity.getBbHeight() / 2;
        double z = entity.getZ();
        
        for (int i = 0; i < 50; i++) {
            double vx = (entity.level().random.nextDouble() - 0.5) * 0.4;
            double vy = entity.level().random.nextDouble() * 0.5;
            double vz = (entity.level().random.nextDouble() - 0.5) * 0.4;
            
            entity.level().addParticle(ParticleTypes.FIREWORK, x, y, z, vx, vy, vz);
        }
        
        for (int i = 0; i < 20; i++) {
            double angle = (i / 20.0) * Math.PI * 2;
            double radius = 1.0;
            
            double px = x + Math.cos(angle) * radius;
            double pz = z + Math.sin(angle) * radius;
            
            entity.level().addParticle(ParticleTypes.TOTEM_OF_UNDYING, px, y, pz, 0, 0.3, 0);
        }
        
        entity.level().addParticle(ParticleTypes.EXPLOSION, x, y, z, 0, 0, 0);
    }
    
    private void renderTrajectories(Player player, PoseStack poseStack, Vec3 cameraPos) {
        if (player.level() == null) return;
        
        long currentTick = player.level().getGameTime();
        boolean shouldRenderProjectiles = (currentTick - lastTrajectoryTick) >= 2;
        
        if (shouldRenderProjectiles) {
            lastTrajectoryTick = currentTick;
            
            net.minecraft.world.phys.AABB searchBox = player.getBoundingBox().inflate(32.0);
            player.level().getEntitiesOfClass(
                net.minecraft.world.entity.projectile.Projectile.class,
                searchBox
            ).forEach(this::renderProjectileTrajectory);
        }
        
        if (previousWasOnGround && !player.onGround()) {
            cachedJumpMotion = player.getDeltaMovement();
        }
        
        if (!player.onGround() && cachedJumpMotion != null) {
            renderPlayerJumpTrajectory(player, cachedJumpMotion);
        } else if (player.onGround()) {
            cachedJumpMotion = null;
        }
        
        previousWasOnGround = player.onGround();
    }
    
    private void renderProjectileTrajectory(net.minecraft.world.entity.projectile.Projectile projectile) {
        Vec3 pos = projectile.position();
        Vec3 motion = projectile.getDeltaMovement();
        
        double gravity = projectile instanceof net.minecraft.world.entity.projectile.ThrowableProjectile ? 0.03 : 0.05;
        double drag = 0.99;
        
        Vec3 current = pos;
        Vec3 vel = motion;
        
        for (int i = 0; i < 30; i++) {
            current = current.add(vel);
            vel = vel.multiply(drag, drag, drag);
            vel = vel.add(0, -gravity, 0);
            
            if (i % 4 == 0) {
                projectile.level().addParticle(
                    new DustParticleOptions(new Vector3f(1.0f, 1.0f, 0.0f), 0.5f),
                    current.x, current.y, current.z,
                    0, 0, 0
                );
            }
            
            if (current.y < projectile.level().getMinBuildHeight()) break;
        }
    }
    
    private void renderPlayerJumpTrajectory(Player player, Vec3 initialMotion) {
        Vec3 pos = player.position();
        
        Vec3 current = pos;
        Vec3 vel = new Vec3(initialMotion.x, initialMotion.y, initialMotion.z);
        
        for (int i = 0; i < 20; i++) {
            current = current.add(vel);
            vel = vel.add(0, -0.08, 0);
            vel = vel.multiply(0.98, 0.98, 0.98);
            
            if (i % 3 == 0) {
                player.level().addParticle(
                    new DustParticleOptions(new Vector3f(0.0f, 1.0f, 1.0f), 0.4f),
                    current.x, current.y, current.z,
                    0, 0, 0
                );
            }
            
            if (vel.y < -0.5 && current.y <= pos.y) break;
        }
    }
    
    private long skyTickCounter = 0;
    
    private void renderSkyEffects(Player player) {
        skyTickCounter++;
        Config.SkyStyle style = Config.SKY_STYLE.get();
        
        switch (style) {
            case STARS:
                renderEnhancedStars(player);
                break;
            case SHOOTING_STARS:
                renderShootingStars(player);
                break;
            case AURORA:
                renderAurora(player);
                break;
            case SPARKLING:
                renderSparklingStars(player);
                break;
        }
    }
    
    private void renderEnhancedStars(Player player) {
        if (skyTickCounter % 10 != 0) return;
        
        for (int i = 0; i < 3; i++) {
            double angle = player.level().random.nextDouble() * Math.PI * 2;
            double dist = 30 + player.level().random.nextDouble() * 20;
            double height = 50 + player.level().random.nextDouble() * 30;
            
            double x = player.getX() + Math.cos(angle) * dist;
            double y = player.getY() + height;
            double z = player.getZ() + Math.sin(angle) * dist;
            
            float brightness = 0.7f + player.level().random.nextFloat() * 0.3f;
            player.level().addParticle(
                new DustParticleOptions(new Vector3f(brightness, brightness, 1.0f), 1.2f),
                x, y, z, 0, 0, 0
            );
        }
    }
    
    private void renderShootingStars(Player player) {
        if (skyTickCounter % 40 != 0) return;
        
        double angle = player.level().random.nextDouble() * Math.PI * 2;
        double dist = 40 + player.level().random.nextDouble() * 20;
        double height = 60 + player.level().random.nextDouble() * 20;
        
        double startX = player.getX() + Math.cos(angle) * dist;
        double startY = player.getY() + height;
        double startZ = player.getZ() + Math.sin(angle) * dist;
        
        double vx = (player.level().random.nextDouble() - 0.5) * 0.5;
        double vy = -0.3 - player.level().random.nextDouble() * 0.2;
        double vz = (player.level().random.nextDouble() - 0.5) * 0.5;
        
        for (int i = 0; i < 15; i++) {
            double t = i / 15.0;
            player.level().addParticle(
                ParticleTypes.END_ROD,
                startX + vx * t * 10,
                startY + vy * t * 10,
                startZ + vz * t * 10,
                vx * 0.1, vy * 0.1, vz * 0.1
            );
        }
    }
    
    private void renderAurora(Player player) {
        if (skyTickCounter % 5 != 0) return;
        
        double time = skyTickCounter * 0.02;
        
        for (int i = 0; i < 4; i++) {
            double angle = (i / 4.0) * Math.PI * 2 + time;
            double dist = 25 + Math.sin(time + i) * 5;
            double height = 45 + Math.cos(time * 0.5 + i) * 10;
            
            double x = player.getX() + Math.cos(angle) * dist;
            double y = player.getY() + height;
            double z = player.getZ() + Math.sin(angle) * dist;
            
            float hue = (float)((time + i * 0.3) % 1.0);
            Vector3f color = hueToRgb(hue);
            
            player.level().addParticle(
                new DustParticleOptions(color, 2.0f),
                x, y, z,
                Math.cos(angle + Math.PI/2) * 0.05,
                Math.sin(time) * 0.02,
                Math.sin(angle + Math.PI/2) * 0.05
            );
        }
    }
    
    private void renderSparklingStars(Player player) {
        if (skyTickCounter % 8 != 0) return;
        
        for (int i = 0; i < 2; i++) {
            double angle = player.level().random.nextDouble() * Math.PI * 2;
            double dist = 35 + player.level().random.nextDouble() * 15;
            double height = 55 + player.level().random.nextDouble() * 25;
            
            double x = player.getX() + Math.cos(angle) * dist;
            double y = player.getY() + height;
            double z = player.getZ() + Math.sin(angle) * dist;
            
            player.level().addParticle(ParticleTypes.ENCHANT,
                x, y, z,
                (player.level().random.nextDouble() - 0.5) * 0.02,
                (player.level().random.nextDouble() - 0.5) * 0.02,
                (player.level().random.nextDouble() - 0.5) * 0.02
            );
            
            if (player.level().random.nextFloat() < 0.3f) {
                player.level().addParticle(ParticleTypes.FIREWORK,
                    x, y, z, 0, 0, 0
                );
            }
        }
    }
    
    private Vector3f hueToRgb(float hue) {
        float r = Math.abs(hue * 6.0f - 3.0f) - 1.0f;
        float g = 2.0f - Math.abs(hue * 6.0f - 2.0f);
        float b = 2.0f - Math.abs(hue * 6.0f - 4.0f);
        
        r = Math.max(0, Math.min(1, r));
        g = Math.max(0, Math.min(1, g));
        b = Math.max(0, Math.min(1, b));
        
        return new Vector3f(r, g, b);
    }
}
