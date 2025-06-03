package net.kulture.grandmine.combat.skills;

import net.kulture.grandmine.combat.CombatStyle;
import net.kulture.grandmine.combat.player.SkillManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 * Represents a combat skill in the GrandMine mod.
 */
public abstract class Skill {

    private final String id;
    private final String name;
    private final String description;

    private final SkillType type;
    private final List<CombatStyle> combatStyles;

    private final float cooldownSeconds;
    private final float staminaCost;
    private final float hakiCost;

    private final int levelRequired;

    private final ResourceLocation icon; // For GUI display
    private final List<String> prerequisites;

    private final SkillEffect effect; // Optional effect logic (can be null)

    // -----------------------------
    // Constructor
    // -----------------------------

    public Skill(String id,
                 String name,
                 String description,
                 SkillType type,
                 List<CombatStyle> combatStyles,
                 float cooldownSeconds,
                 float staminaCost,
                 float hakiCost,
                 int levelRequired,
                 ResourceLocation icon,
                 List<String> prerequisites,
                 SkillEffect effect) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.combatStyles = combatStyles;
        this.cooldownSeconds = cooldownSeconds;
        this.staminaCost = staminaCost;
        this.hakiCost = hakiCost;
        this.levelRequired = levelRequired;
        this.icon = icon;
        this.prerequisites = prerequisites;
        this.effect = effect;
    }

    // -----------------------------
    // Getters
    // -----------------------------

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public SkillType getType() {
        return type;
    }

    public List<CombatStyle> getCombatStyles() {
        return combatStyles;
    }

    public float getCooldownSeconds() {
        return cooldownSeconds;
    }

    public float getStaminaCost() {
        return staminaCost;
    }

    public float getHakiCost() {
        return hakiCost;
    }

    public int getLevelRequired() {
        return levelRequired;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public SkillEffect getEffect() {
        return effect;
    }

    public abstract boolean execute(Player player, SkillManager manager);
}
