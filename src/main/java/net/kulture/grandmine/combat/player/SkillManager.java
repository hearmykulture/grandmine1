package net.kulture.grandmine.combat.player;

import net.kulture.grandmine.combat.skills.Skill;
import net.kulture.grandmine.combat.skills.SkillRegistry;
import net.kulture.grandmine.combat.CombatStyle;
import net.kulture.grandmine.combat.CombatStyles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class SkillManager {

    private Player player;  // The player this SkillManager belongs to
    private PlayerSkillData skillData = new PlayerSkillData();
    private CombatStyle currentCombatStyle;

    // Constructor with Player parameter
    public SkillManager(Player player) {
        this.player = player;
        this.currentCombatStyle = null;
    }

    // No-arg constructor for compatibility
    public SkillManager() {
        this(null);
    }

    // -----------------------------
    // NBT Save / Load
    // -----------------------------
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.put("SkillData", skillData.saveToNBT());
        tag.putString("CurrentCombatStyle", currentCombatStyle != null ? currentCombatStyle.getId().toString() : "");
        return tag;
    }

    public void loadFromNBT(CompoundTag tag) {
        if (tag.contains("SkillData")) {
            CompoundTag skillDataTag = tag.getCompound("SkillData");
            this.skillData.loadFromNBT(skillDataTag);
        } else {
            // If no SkillData, initialize empty PlayerSkillData
            this.skillData = new PlayerSkillData();
        }

        if (tag.contains("CurrentCombatStyle")) {
            String styleId = tag.getString("CurrentCombatStyle");
            CombatStyle style = CombatStyles.getById(styleId);
            this.setCurrentCombatStyle(style);
        } else {
            this.currentCombatStyle = null;
        }
    }

    // -----------------------------
    // Combat Style Logic
    // -----------------------------
    public void setCurrentCombatStyle(CombatStyle style) {
        this.currentCombatStyle = style;
        if (style == null) return;

        // Learn all default skills of the combat style if not already learned
        for (Skill skill : style.getDefaultSkillObjects()) {
            String skillId = skill.getId();
            if (!skillData.hasLearned(skillId)) {
                skillData.learnSkill(skillId, 1);
            }
        }

        List<String> equipped = skillData.getAllEquippedSkills();
        int maxSlots = style.getMaxSkillSlots();

        boolean shouldReset = equipped.size() > maxSlots ||
                equipped.stream().anyMatch(id -> id != null && SkillRegistry.getSkillById(id) == null);

        if (shouldReset || equipped.isEmpty()) {
            List<Skill> defaults = style.getDefaultSkillObjects();
            int slotsToFill = Math.min(maxSlots, defaults.size());

            for (int i = 0; i < slotsToFill; i++) {
                skillData.equipSkill(i, defaults.get(i).getId());
            }

            for (int i = slotsToFill; i < equipped.size(); i++) {
                skillData.equipSkill(i, null);
            }
        }
    }

    public CombatStyle getCurrentCombatStyle() {
        return this.currentCombatStyle;
    }

    // -----------------------------
    // Skill Logic
    // -----------------------------

    public PlayerSkillData getData() {
        return this.skillData;
    }

    public boolean learnSkill(Skill skill) {
        String skillId = skill.getId();

        if (skillData.hasLearned(skillId)) return false;

        // Check prerequisites
        for (String prereq : skill.getPrerequisites()) {
            if (!skillData.hasLearned(prereq)) return false;
        }

        skillData.learnSkill(skillId, 1);
        return true;
    }

    public boolean equipSkill(int slot, Skill skill) {
        if (!skillData.hasLearned(skill.getId())) return false;
        skillData.equipSkill(slot, skill.getId());
        return true;
    }

    public boolean useSkill(int slot) {
        String skillId = skillData.getEquippedSkill(slot);
        if (skillId == null) return false;

        Skill skill = SkillRegistry.getSkillById(skillId);
        if (skill == null || skillData.isOnCooldown(skillId)) return false;

        // Here you would trigger the skill effect, animations, etc.
        skillData.setCooldown(skillId, 100); // example cooldown of 100 ticks
        return true;
    }

    // Call this method regularly, e.g. every tick, to reduce cooldown timers
    public void tickCooldowns() {
        skillData.tickCooldowns();
    }
}
