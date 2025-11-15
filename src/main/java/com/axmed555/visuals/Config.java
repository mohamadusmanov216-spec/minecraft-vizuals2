package com.axmed555.visuals;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue SHOW_TRAIL;
    public static final ForgeConfigSpec.BooleanValue SHOW_HITBOX;
    public static final ForgeConfigSpec.BooleanValue SHOW_HIT_EFFECTS;
    public static final ForgeConfigSpec.BooleanValue SHOW_JUMP_EFFECTS;
    public static final ForgeConfigSpec.BooleanValue SHOW_SPRINT_EFFECTS;
    public static final ForgeConfigSpec.BooleanValue SHOW_ATTACK_EFFECTS;
    public static final ForgeConfigSpec.BooleanValue SHOW_KILL_EFFECTS;
    
    public static final ForgeConfigSpec.BooleanValue SHOW_TRAJECTORIES;
    public static final ForgeConfigSpec.BooleanValue SHOW_SKY_EFFECTS;
    
    public static final ForgeConfigSpec.EnumValue<TrailStyle> TRAIL_STYLE;
    public static final ForgeConfigSpec.EnumValue<SkyStyle> SKY_STYLE;
    
    public static final ForgeConfigSpec.IntValue TRAIL_RED;
    public static final ForgeConfigSpec.IntValue TRAIL_GREEN;
    public static final ForgeConfigSpec.IntValue TRAIL_BLUE;
    
    public static final ForgeConfigSpec.IntValue HITBOX_RED;
    public static final ForgeConfigSpec.IntValue HITBOX_GREEN;
    public static final ForgeConfigSpec.IntValue HITBOX_BLUE;
    
    public static final ForgeConfigSpec.IntValue HIT_EFFECT_RED;
    public static final ForgeConfigSpec.IntValue HIT_EFFECT_GREEN;
    public static final ForgeConfigSpec.IntValue HIT_EFFECT_BLUE;
    
    // Crown colors (gradient)
    public static final ForgeConfigSpec.IntValue CROWN_R1;
    public static final ForgeConfigSpec.IntValue CROWN_G1;
    public static final ForgeConfigSpec.IntValue CROWN_B1;
    public static final ForgeConfigSpec.IntValue CROWN_R2;
    public static final ForgeConfigSpec.IntValue CROWN_G2;
    public static final ForgeConfigSpec.IntValue CROWN_B2;
    
    // Aura colors (gradient)
    public static final ForgeConfigSpec.IntValue AURA_R1;
    public static final ForgeConfigSpec.IntValue AURA_G1;
    public static final ForgeConfigSpec.IntValue AURA_B1;
    public static final ForgeConfigSpec.IntValue AURA_R2;
    public static final ForgeConfigSpec.IntValue AURA_G2;
    public static final ForgeConfigSpec.IntValue AURA_B2;
    
    // Wings colors (gradient)
    public static final ForgeConfigSpec.IntValue WINGS_R1;
    public static final ForgeConfigSpec.IntValue WINGS_G1;
    public static final ForgeConfigSpec.IntValue WINGS_B1;
    public static final ForgeConfigSpec.IntValue WINGS_R2;
    public static final ForgeConfigSpec.IntValue WINGS_G2;
    public static final ForgeConfigSpec.IntValue WINGS_B2;
    
    // Halo colors (gradient)
    public static final ForgeConfigSpec.IntValue HALO_R1;
    public static final ForgeConfigSpec.IntValue HALO_G1;
    public static final ForgeConfigSpec.IntValue HALO_B1;
    public static final ForgeConfigSpec.IntValue HALO_R2;
    public static final ForgeConfigSpec.IntValue HALO_G2;
    public static final ForgeConfigSpec.IntValue HALO_B2;
    
    public static final ForgeConfigSpec.EnumValue<HatStyle> HAT_STYLE;
    
    public static final ForgeConfigSpec.ConfigValue<String> BIND_MESSAGE_1;
    public static final ForgeConfigSpec.ConfigValue<String> BIND_MESSAGE_2;
    public static final ForgeConfigSpec.ConfigValue<String> BIND_MESSAGE_3;
    public static final ForgeConfigSpec.ConfigValue<String> BIND_MESSAGE_4;

    static {
        BUILDER.push("Visual Effects");
        
        SHOW_TRAIL = BUILDER.comment("Enable particle trail effect")
            .define("showTrail", true);
        SHOW_HITBOX = BUILDER.comment("Enable enlarged hitbox rendering")
            .define("showHitbox", true);
        SHOW_HIT_EFFECTS = BUILDER.comment("Enable hit effects")
            .define("showHitEffects", true);
        SHOW_JUMP_EFFECTS = BUILDER.comment("Enable jump circle effects")
            .define("showJumpEffects", true);
        SHOW_SPRINT_EFFECTS = BUILDER.comment("Enable sprint particle effects")
            .define("showSprintEffects", true);
        SHOW_ATTACK_EFFECTS = BUILDER.comment("Enable attack particle effects")
            .define("showAttackEffects", true);
        SHOW_KILL_EFFECTS = BUILDER.comment("Enable kill celebration effects")
            .define("showKillEffects", true);
        
        SHOW_TRAJECTORIES = BUILDER.comment("Show projectile and jump trajectories")
            .define("showTrajectories", true);
        
        SHOW_SKY_EFFECTS = BUILDER.comment("Enable custom sky effects")
            .define("showSkyEffects", false);
        
        TRAIL_STYLE = BUILDER.comment("Trail particle style")
            .defineEnum("trailStyle", TrailStyle.NORMAL);
        
        SKY_STYLE = BUILDER.comment("Sky effect style")
            .defineEnum("skyStyle", SkyStyle.STARS);
        
        BUILDER.push("Trail Color");
        TRAIL_RED = BUILDER.defineInRange("trailRed", 255, 0, 255);
        TRAIL_GREEN = BUILDER.defineInRange("trailGreen", 0, 0, 255);
        TRAIL_BLUE = BUILDER.defineInRange("trailBlue", 0, 0, 255);
        BUILDER.pop();
        
        BUILDER.push("Hitbox Color");
        HITBOX_RED = BUILDER.defineInRange("hitboxRed", 0, 0, 255);
        HITBOX_GREEN = BUILDER.defineInRange("hitboxGreen", 255, 0, 255);
        HITBOX_BLUE = BUILDER.defineInRange("hitboxBlue", 0, 0, 255);
        BUILDER.pop();
        
        BUILDER.push("Hit Effect Color");
        HIT_EFFECT_RED = BUILDER.defineInRange("hitEffectRed", 255, 0, 255);
        HIT_EFFECT_GREEN = BUILDER.defineInRange("hitEffectGreen", 255, 0, 255);
        HIT_EFFECT_BLUE = BUILDER.defineInRange("hitEffectBlue", 0, 0, 255);
        BUILDER.pop();
        
        BUILDER.push("Crown Colors (Gradient)");
        CROWN_R1 = BUILDER.defineInRange("crownR1", 255, 0, 255);
        CROWN_G1 = BUILDER.defineInRange("crownG1", 215, 0, 255);
        CROWN_B1 = BUILDER.defineInRange("crownB1", 0, 0, 255);
        CROWN_R2 = BUILDER.defineInRange("crownR2", 255, 0, 255);
        CROWN_G2 = BUILDER.defineInRange("crownG2", 255, 0, 255);
        CROWN_B2 = BUILDER.defineInRange("crownB2", 0, 0, 255);
        BUILDER.pop();
        
        BUILDER.push("Aura Colors (Gradient)");
        AURA_R1 = BUILDER.defineInRange("auraR1", 138, 0, 255);
        AURA_G1 = BUILDER.defineInRange("auraG1", 43, 0, 255);
        AURA_B1 = BUILDER.defineInRange("auraB1", 226, 0, 255);
        AURA_R2 = BUILDER.defineInRange("auraR2", 0, 0, 255);
        AURA_G2 = BUILDER.defineInRange("auraG2", 255, 0, 255);
        AURA_B2 = BUILDER.defineInRange("auraB2", 255, 0, 255);
        BUILDER.pop();
        
        BUILDER.push("Wings Colors (Gradient)");
        WINGS_R1 = BUILDER.defineInRange("wingsR1", 255, 0, 255);
        WINGS_G1 = BUILDER.defineInRange("wingsG1", 255, 0, 255);
        WINGS_B1 = BUILDER.defineInRange("wingsB1", 255, 0, 255);
        WINGS_R2 = BUILDER.defineInRange("wingsR2", 135, 0, 255);
        WINGS_G2 = BUILDER.defineInRange("wingsG2", 206, 0, 255);
        WINGS_B2 = BUILDER.defineInRange("wingsB2", 250, 0, 255);
        BUILDER.pop();
        
        BUILDER.push("Halo Colors (Gradient)");
        HALO_R1 = BUILDER.defineInRange("haloR1", 100, 0, 255);
        HALO_G1 = BUILDER.defineInRange("haloG1", 200, 0, 255);
        HALO_B1 = BUILDER.defineInRange("haloB1", 255, 0, 255);
        HALO_R2 = BUILDER.defineInRange("haloR2", 255, 0, 255);
        HALO_G2 = BUILDER.defineInRange("haloG2", 255, 0, 255);
        HALO_B2 = BUILDER.defineInRange("haloB2", 100, 0, 255);
        BUILDER.pop();
        
        HAT_STYLE = BUILDER.comment("Cosmetic hat style")
            .defineEnum("hatStyle", HatStyle.CROWN);
        
        BUILDER.push("Keybind Messages");
        BIND_MESSAGE_1 = BUILDER.comment("Message for bind slot 1")
            .define("bindMessage1", "");
        BIND_MESSAGE_2 = BUILDER.comment("Message for bind slot 2")
            .define("bindMessage2", "");
        BIND_MESSAGE_3 = BUILDER.comment("Message for bind slot 3")
            .define("bindMessage3", "");
        BIND_MESSAGE_4 = BUILDER.comment("Message for bind slot 4")
            .define("bindMessage4", "");
        BUILDER.pop();
        
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public enum HatStyle {
        NONE,
        CROWN,
        AURA,
        WINGS,
        HALO
    }
    
    public enum TrailStyle {
        NORMAL,
        FIRE,
        STARS,
        LIGHTNING,
        MAGIC,
        HEARTS,
        SPIRAL,
        ENDER
    }
    
    public enum SkyStyle {
        STARS,
        SHOOTING_STARS,
        AURORA,
        SPARKLING
    }
}
