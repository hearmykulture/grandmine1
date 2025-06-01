package net.kulture.grandmine.combat.skills;

import net.kulture.grandmine.combat.CombatStyles;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class SkillRegistry {

    private static final Map<String, Skill> SKILL_MAP = new HashMap<>();

    public static void registerSkills() {
        register(new Skill(
                "geppo",
                "Geppo",
                "Walk on air",
                SkillType.MOVEMENT,
                List.of(CombatStyles.BRAWLER),
                20f,
                0f,
                5f,
                1,
                new ResourceLocation("grandmine:textures/icons/geppo.png"),
                List.of(),
                null
        ));

        // Add more skills here...
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
