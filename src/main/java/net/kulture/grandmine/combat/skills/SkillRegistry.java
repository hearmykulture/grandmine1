package net.kulture.grandmine.combat.skills;

import net.kulture.grandmine.combat.CombatStyles;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class SkillRegistry {

    private static final Map<String, Skill> SKILL_MAP = new HashMap<>();

    // Declare your skills here first (before registering)
    public static final Skill PUNCH = new SkillPunch();
    // public static final Skill GEPPO = new SkillGeppo(); (when added)

    // Register skills in this method
    public static void registerSkills() {
        register(PUNCH);
        // register(GEPPO); (future skills go here)
    }

    private static void register(Skill skill) {
        SKILL_MAP.put(skill.getId(), skill);
    }

    public static Skill getSkillById(String id) {
        return SKILL_MAP.get(id);
    }

    public static Collection<Skill> getAllSkills() {
        return SKILL_MAP.values();
    }
}
