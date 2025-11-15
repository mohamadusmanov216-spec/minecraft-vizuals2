package com.axmed555.visuals.gui;

import com.axmed555.visuals.Config;
import com.axmed555.visuals.KeyInputHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class ConfigScreen extends Screen {
    private final Screen parentScreen;
    private int yOffset = 30;
    private int currentPage = 0;
    
    private EditBox bind1Field;
    private EditBox bind2Field;
    private EditBox bind3Field;
    private EditBox bind4Field;

    public ConfigScreen(Screen parentScreen) {
        super(Component.translatable("gui.axmed555_visuals.title"));
        this.parentScreen = parentScreen;
    }

    @Override
    protected void init() {
        int leftX = this.width / 2 - 150;
        int rightX = this.width / 2 + 10;
        yOffset = 30;

        addToggleButton("gui.axmed555_visuals.trail", Config.SHOW_TRAIL, leftX);
        addToggleButton("gui.axmed555_visuals.hitbox", Config.SHOW_HITBOX, rightX);
        
        yOffset += 25;
        
        addToggleButton("gui.axmed555_visuals.hit_effects", Config.SHOW_HIT_EFFECTS, leftX);
        addToggleButton("gui.axmed555_visuals.jump_effects", Config.SHOW_JUMP_EFFECTS, rightX);
        
        yOffset += 25;
        
        addToggleButton("gui.axmed555_visuals.sprint_effects", Config.SHOW_SPRINT_EFFECTS, leftX);
        addToggleButton("gui.axmed555_visuals.attack_effects", Config.SHOW_ATTACK_EFFECTS, rightX);
        
        yOffset += 25;
        
        addToggleButton("gui.axmed555_visuals.kill_effects", Config.SHOW_KILL_EFFECTS, leftX);
        addToggleButton("gui.axmed555_visuals.trajectories", Config.SHOW_TRAJECTORIES, rightX);
        
        yOffset += 25;
        
        addToggleButton("gui.axmed555_visuals.sky_effects", Config.SHOW_SKY_EFFECTS, leftX);
        
        yOffset += 10;
        
        if (Config.SHOW_SKY_EFFECTS.get()) {
            Config.SkyStyle currentSky = Config.SKY_STYLE.get();
            int skyBtnWidth = 90;
            int skyY = yOffset;
            
            for (Config.SkyStyle style : Config.SkyStyle.values()) {
                int skyX = leftX + (style.ordinal() % 3) * (skyBtnWidth + 5);
                if (style.ordinal() % 3 == 0 && style.ordinal() > 0) {
                    skyY += 25;
                }
                
                int finalSkyY = skyY;
                String skyKey = "gui.axmed555_visuals.sky." + style.name().toLowerCase();
                Component skyLabel = Component.translatable(skyKey);
                if (currentSky == style) {
                    skyLabel = Component.literal("✓ ").append(skyLabel);
                }
                
                addRenderableWidget(Button.builder(skyLabel, (btn) -> {
                    Config.SKY_STYLE.set(style);
                    this.init(this.minecraft, this.width, this.height);
                }).bounds(skyX, finalSkyY, skyBtnWidth, 20).build());
            }
            
            yOffset = skyY + 35;
        }
        
        Config.TrailStyle currentTrail = Config.TRAIL_STYLE.get();
        int trailBtnWidth = 70;
        int trailY = yOffset;
        
        for (Config.TrailStyle style : Config.TrailStyle.values()) {
            int col = style.ordinal() % 4;
            int row = style.ordinal() / 4;
            int trailX = leftX + col * (trailBtnWidth + 5);
            int finalTrailY = trailY + row * 25;
            
            String key = "gui.axmed555_visuals.trail." + style.name().toLowerCase();
            Component label = Component.translatable(key);
            if (currentTrail == style) {
                label = Component.literal("✓ ").append(label);
            }
            
            addRenderableWidget(Button.builder(label, (btn) -> {
                Config.TRAIL_STYLE.set(style);
                this.init(this.minecraft, this.width, this.height);
            }).bounds(trailX, finalTrailY, trailBtnWidth, 20).build());
        }
        
        int colorY = trailY + 55;
        int leftY = colorY;
        int rightY = colorY;

        yOffset = leftY;
        addSlider("gui.axmed555_visuals.trail_red", Config.TRAIL_RED.get(), 0, 255, leftX, (val) -> Config.TRAIL_RED.set(val));
        addSlider("gui.axmed555_visuals.trail_green", Config.TRAIL_GREEN.get(), 0, 255, leftX, (val) -> Config.TRAIL_GREEN.set(val));
        addSlider("gui.axmed555_visuals.trail_blue", Config.TRAIL_BLUE.get(), 0, 255, leftX, (val) -> Config.TRAIL_BLUE.set(val));
        leftY = yOffset;

        yOffset = rightY;
        addSlider("gui.axmed555_visuals.hitbox_red", Config.HITBOX_RED.get(), 0, 255, rightX, (val) -> Config.HITBOX_RED.set(val));
        addSlider("gui.axmed555_visuals.hitbox_green", Config.HITBOX_GREEN.get(), 0, 255, rightX, (val) -> Config.HITBOX_GREEN.set(val));
        addSlider("gui.axmed555_visuals.hitbox_blue", Config.HITBOX_BLUE.get(), 0, 255, rightX, (val) -> Config.HITBOX_BLUE.set(val));
        rightY = yOffset;

        yOffset = Math.max(leftY, rightY);

        leftY = yOffset;
        rightY = yOffset;
        
        yOffset = leftY;
        addSlider("gui.axmed555_visuals.hit_red", Config.HIT_EFFECT_RED.get(), 0, 255, leftX, (val) -> Config.HIT_EFFECT_RED.set(val));
        leftY = yOffset;
        
        yOffset = rightY;
        addSlider("gui.axmed555_visuals.hit_green", Config.HIT_EFFECT_GREEN.get(), 0, 255, rightX, (val) -> Config.HIT_EFFECT_GREEN.set(val));
        addSlider("gui.axmed555_visuals.hit_blue", Config.HIT_EFFECT_BLUE.get(), 0, 255, rightX, (val) -> Config.HIT_EFFECT_BLUE.set(val));
        rightY = yOffset;

        yOffset = Math.max(leftY, rightY);

        yOffset += 10;
        
        int fieldWidth = 300;
        
        bind1Field = new EditBox(this.font, leftX, yOffset, fieldWidth, 20, 
            Component.translatable("gui.axmed555_visuals.bind1"));
        bind1Field.setMaxLength(256);
        bind1Field.setValue(Config.BIND_MESSAGE_1.get());
        bind1Field.setResponder((text) -> Config.BIND_MESSAGE_1.set(text));
        addRenderableWidget(bind1Field);
        yOffset += 25;
        
        bind2Field = new EditBox(this.font, leftX, yOffset, fieldWidth, 20,
            Component.translatable("gui.axmed555_visuals.bind2"));
        bind2Field.setMaxLength(256);
        bind2Field.setValue(Config.BIND_MESSAGE_2.get());
        bind2Field.setResponder((text) -> Config.BIND_MESSAGE_2.set(text));
        addRenderableWidget(bind2Field);
        yOffset += 25;
        
        bind3Field = new EditBox(this.font, leftX, yOffset, fieldWidth, 20,
            Component.translatable("gui.axmed555_visuals.bind3"));
        bind3Field.setMaxLength(256);
        bind3Field.setValue(Config.BIND_MESSAGE_3.get());
        bind3Field.setResponder((text) -> Config.BIND_MESSAGE_3.set(text));
        addRenderableWidget(bind3Field);
        yOffset += 25;
        
        bind4Field = new EditBox(this.font, leftX, yOffset, fieldWidth, 20,
            Component.translatable("gui.axmed555_visuals.bind4"));
        bind4Field.setMaxLength(256);
        bind4Field.setValue(Config.BIND_MESSAGE_4.get());
        bind4Field.setResponder((text) -> Config.BIND_MESSAGE_4.set(text));
        addRenderableWidget(bind4Field);
        yOffset += 30;

        int hatBtnWidth = 90;
        int hatY = yOffset;
        Config.HatStyle currentHat = Config.HAT_STYLE.get();

        for (Config.HatStyle style : Config.HatStyle.values()) {
            int hatX = leftX + (style.ordinal() % 3) * (hatBtnWidth + 5);
            if (style.ordinal() % 3 == 0 && style.ordinal() > 0) {
                hatY += 25;
            }

            int finalHatY = hatY;
            String hatKey = "gui.axmed555_visuals.hat." + style.name().toLowerCase();
            Component hatLabel = Component.translatable(hatKey);
            if (currentHat == style) {
                hatLabel = Component.literal("✓ ").append(hatLabel);
            }
            
            addRenderableWidget(Button.builder(hatLabel, (btn) -> {
                Config.HAT_STYLE.set(style);
                this.init(this.minecraft, this.width, this.height);
            }).bounds(hatX, finalHatY, hatBtnWidth, 20).build());
        }
        
        yOffset = hatY + 35;
        
        if (currentHat != Config.HatStyle.NONE) {
            leftY = yOffset;
            rightY = yOffset;
            String styleName = currentHat.name().toLowerCase();
            
            yOffset = leftY;
            addSlider("gui.axmed555_visuals.hat." + styleName + "_r1", 
                currentHat == Config.HatStyle.CROWN ? Config.CROWN_R1.get() :
                currentHat == Config.HatStyle.AURA ? Config.AURA_R1.get() :
                currentHat == Config.HatStyle.WINGS ? Config.WINGS_R1.get() :
                Config.HALO_R1.get(), 0, 255, leftX, (val) -> {
                    if (currentHat == Config.HatStyle.CROWN) Config.CROWN_R1.set(val);
                    else if (currentHat == Config.HatStyle.AURA) Config.AURA_R1.set(val);
                    else if (currentHat == Config.HatStyle.WINGS) Config.WINGS_R1.set(val);
                    else Config.HALO_R1.set(val);
                });
            
            addSlider("gui.axmed555_visuals.hat." + styleName + "_g1", 
                currentHat == Config.HatStyle.CROWN ? Config.CROWN_G1.get() :
                currentHat == Config.HatStyle.AURA ? Config.AURA_G1.get() :
                currentHat == Config.HatStyle.WINGS ? Config.WINGS_G1.get() :
                Config.HALO_G1.get(), 0, 255, leftX, (val) -> {
                    if (currentHat == Config.HatStyle.CROWN) Config.CROWN_G1.set(val);
                    else if (currentHat == Config.HatStyle.AURA) Config.AURA_G1.set(val);
                    else if (currentHat == Config.HatStyle.WINGS) Config.WINGS_G1.set(val);
                    else Config.HALO_G1.set(val);
                });
            
            addSlider("gui.axmed555_visuals.hat." + styleName + "_b1", 
                currentHat == Config.HatStyle.CROWN ? Config.CROWN_B1.get() :
                currentHat == Config.HatStyle.AURA ? Config.AURA_B1.get() :
                currentHat == Config.HatStyle.WINGS ? Config.WINGS_B1.get() :
                Config.HALO_B1.get(), 0, 255, leftX, (val) -> {
                    if (currentHat == Config.HatStyle.CROWN) Config.CROWN_B1.set(val);
                    else if (currentHat == Config.HatStyle.AURA) Config.AURA_B1.set(val);
                    else if (currentHat == Config.HatStyle.WINGS) Config.WINGS_B1.set(val);
                    else Config.HALO_B1.set(val);
                });
            leftY = yOffset;
            
            yOffset = rightY;
            addSlider("gui.axmed555_visuals.hat." + styleName + "_r2", 
                currentHat == Config.HatStyle.CROWN ? Config.CROWN_R2.get() :
                currentHat == Config.HatStyle.AURA ? Config.AURA_R2.get() :
                currentHat == Config.HatStyle.WINGS ? Config.WINGS_R2.get() :
                Config.HALO_R2.get(), 0, 255, rightX, (val) -> {
                    if (currentHat == Config.HatStyle.CROWN) Config.CROWN_R2.set(val);
                    else if (currentHat == Config.HatStyle.AURA) Config.AURA_R2.set(val);
                    else if (currentHat == Config.HatStyle.WINGS) Config.WINGS_R2.set(val);
                    else Config.HALO_R2.set(val);
                });
            
            addSlider("gui.axmed555_visuals.hat." + styleName + "_g2", 
                currentHat == Config.HatStyle.CROWN ? Config.CROWN_G2.get() :
                currentHat == Config.HatStyle.AURA ? Config.AURA_G2.get() :
                currentHat == Config.HatStyle.WINGS ? Config.WINGS_G2.get() :
                Config.HALO_G2.get(), 0, 255, rightX, (val) -> {
                    if (currentHat == Config.HatStyle.CROWN) Config.CROWN_G2.set(val);
                    else if (currentHat == Config.HatStyle.AURA) Config.AURA_G2.set(val);
                    else if (currentHat == Config.HatStyle.WINGS) Config.WINGS_G2.set(val);
                    else Config.HALO_G2.set(val);
                });
            
            addSlider("gui.axmed555_visuals.hat." + styleName + "_b2", 
                currentHat == Config.HatStyle.CROWN ? Config.CROWN_B2.get() :
                currentHat == Config.HatStyle.AURA ? Config.AURA_B2.get() :
                currentHat == Config.HatStyle.WINGS ? Config.WINGS_B2.get() :
                Config.HALO_B2.get(), 0, 255, rightX, (val) -> {
                    if (currentHat == Config.HatStyle.CROWN) Config.CROWN_B2.set(val);
                    else if (currentHat == Config.HatStyle.AURA) Config.AURA_B2.set(val);
                    else if (currentHat == Config.HatStyle.WINGS) Config.WINGS_B2.set(val);
                    else Config.HALO_B2.set(val);
                });
            rightY = yOffset;
            
            yOffset = Math.max(leftY, rightY);
        }

        addRenderableWidget(Button.builder(
            Component.translatable("gui.axmed555_visuals.done"),
            (btn) -> this.minecraft.setScreen(this.parentScreen))
            .bounds(this.width / 2 - 100, this.height - 27, 200, 20)
            .build());
    }

    private void addToggleButton(String translationKey, net.minecraftforge.common.ForgeConfigSpec.BooleanValue configValue, int x) {
        String statusKey = configValue.get() ? "gui.axmed555_visuals.on" : "gui.axmed555_visuals.off";
        Component label = Component.translatable(translationKey).append(": ").append(Component.translatable(statusKey));
        
        addRenderableWidget(Button.builder(label, (btn) -> {
            configValue.set(!configValue.get());
            String newStatusKey = configValue.get() ? "gui.axmed555_visuals.on" : "gui.axmed555_visuals.off";
            btn.setMessage(Component.translatable(translationKey).append(": ").append(Component.translatable(newStatusKey)));
        }).bounds(x, yOffset, 140, 20).build());
    }

    private void addSlider(String labelKey, int value, int min, int max, int x, java.util.function.Consumer<Integer> onChange) {
        Component baseLabel = Component.translatable(labelKey);
        addRenderableWidget(new ForgeSlider(x, yOffset, 140, 20, 
            baseLabel.copy().append(": "), 
            Component.literal(""), 
            min, max, value, 1.0, 0, true) {
            @Override
            protected void applyValue() {
                onChange.accept((int)this.getValue());
                this.setMessage(Component.translatable(labelKey).append(": " + (int)this.getValue()));
            }
        });
        yOffset += 25;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xFFFFFF);
        
        int infoY = this.height - 50;
        graphics.drawCenteredString(this.font, 
            "Open GUI: " + KeyInputHandler.openGuiKey.saveString(), 
            this.width / 2, infoY, 0xAAAAAA);
        graphics.drawCenteredString(this.font, 
            "Binds: NumPad 1-4", 
            this.width / 2, infoY + 12, 0xAAAAAA);
    }

    @Override
    public void removed() {
        com.axmed555.visuals.AxmedVisuals.saveConfig();
        super.removed();
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parentScreen);
    }
}
