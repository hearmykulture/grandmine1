package net.kulture.grandmine.combat;

import net.minecraft.resources.ResourceLocation;
import java.util.HashMap;
import java.util.Map;

import java.util.*;

public class CombatStyles {

    private static final Map<ResourceLocation, CombatStyle> STYLE_MAP = new HashMap<>();

    public static final CombatStyle BRAWLER = register(new CombatStyle(
            new ResourceLocation("grandmine", "brawler"),
            "Brawler",
            "Close-range hand-to-hand combat with punches, combos, and slams.",
            List.of(
                    new ResourceLocation("grandmine", "punch"),
                    new ResourceLocation("grandmine", "geppo")
            ),
            4,
            Set.of("fist", "empty"),
            new AlwaysUnlockedRequirement()
    ));

    // ... other styles and methods ...
    public static void init() {
        // Just referencing BRAWLER triggers class loading and registration
        // Add any other styles you have here
        CombatStyles.BRAWLER.toString();
    }
    private static CombatStyle register(CombatStyle style) {
        STYLE_MAP.put(style.getId(), style);
        return style;
    }

    public static CombatStyle get(ResourceLocation id) {
        return STYLE_MAP.get(id);
    }

    public static CombatStyle getById(String id) {
        try {
            ResourceLocation resourceLocation = new ResourceLocation(id);
            return get(resourceLocation);
        } catch (Exception e) {
            return null;
        }
    }

    public static Collection<CombatStyle> getAll() {
        return STYLE_MAP.values();
    }
}

