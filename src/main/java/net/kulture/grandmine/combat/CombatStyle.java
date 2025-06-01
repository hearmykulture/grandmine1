package net.kulture.grandmine.combat;

import net.kulture.grandmine.combat.skills.Skill;
import net.kulture.grandmine.combat.skills.SkillRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a Combat Style in the mod.
 * Defines what skills it grants access to, unlock requirements,
 * weapon restrictions, and skill slot limits.
 */
public class CombatStyle {

    private final ResourceLocation id;  // Unique ID, e.g. "grandmine:brawler"
    private final String name;           // User-friendly name
    private final String description;    // Short description for UI

    private final List<ResourceLocation> defaultSkills; // Skills granted by default by this style
    private final int maxSkillSlots;     // How many skills can be equipped under this style

    private final Set<String> allowedWeaponTypes;  // E.g., "fist", "sword", "dagger" (custom tags)

    private final UnlockRequirement unlockRequirement; // Condition to unlock this style

    /**
     * Constructor
     */
    public CombatStyle(ResourceLocation id,
                       String name,
                       String description,
                       List<ResourceLocation> defaultSkills,
                       int maxSkillSlots,
                       Set<String> allowedWeaponTypes,
                       UnlockRequirement unlockRequirement) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultSkills = defaultSkills;
        this.maxSkillSlots = maxSkillSlots;
        this.allowedWeaponTypes = allowedWeaponTypes;
        this.unlockRequirement = unlockRequirement;
    }

    // Getters
    public ResourceLocation getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<ResourceLocation> getDefaultSkills() { return defaultSkills; }
    public int getMaxSkillSlots() { return maxSkillSlots; }
    public Set<String> getAllowedWeaponTypes() { return allowedWeaponTypes; }

    /**
     * Checks if a player meets unlock requirements for this style.
     * @param player the player instance (could be Minecraft's Player or your own wrapper)
     * @return true if unlocked
     */
    public boolean isUnlockedBy(Player player) {
        return unlockRequirement == null || unlockRequirement.isUnlocked(player);
    }

    /**
     * Checks if a weapon type is allowed by this style.
     * @param weaponType a string tag for weapon type, e.g. "sword"
     * @return true if allowed
     */
    public boolean isWeaponAllowed(String weaponType) {
        return allowedWeaponTypes == null || allowedWeaponTypes.isEmpty() || allowedWeaponTypes.contains(weaponType);
    }

    /**
     * Returns the base Skill objects for this style’s default skills.
     * Useful for auto-equipping skills when style is selected.
     */
    public List<Skill> getDefaultSkillObjects() {
        return defaultSkills.stream()
                .map(loc -> SkillRegistry.getSkillById(loc.toString())) // Convert ResourceLocation to String
                .filter(skill -> skill != null)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CombatStyle)) return false;
        CombatStyle other = (CombatStyle) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "CombatStyle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
