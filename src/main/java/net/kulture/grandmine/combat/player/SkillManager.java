package net.kulture.grandmine.combat.player;

import net.kulture.grandmine.combat.skills.Skill;
import net.kulture.grandmine.combat.skills.SkillRegistry;
import net.kulture.grandmine.combat.CombatStyle;
import net.kulture.grandmine.combat.CombatStyles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;

public class SkillManager {
    private Player player;
    private PlayerSkillData skillData;
    private CombatStyle currentCombatStyle;

    public SkillManager(Player player) {
        this.player = player;
        this.skillData = new PlayerSkillData(player);
        this.skillData.setManager(this); // <- avoid recursion
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
            this.skillData = new PlayerSkillData(player);
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
        System.out.println("Setting combat style: " + style.getId());

        // Learn all default skills of the combat style if not already learned
        for (Skill skill : style.getDefaultSkillObjects()) {
            String skillId = skill.getId();
            if (!skillData.hasLearned(skillId)) {
                System.out.println("Learning default skill: " + skillId);
                skillData.learnSkill(skillId, 1);
            } else {
                System.out.println("Already learned skill: " + skillId);
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
                System.out.println("Equipping skill in slot " + i + ": " + defaults.get(i));
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
        System.out.println("Trying to use skill in slot " + slot + ": " + skillId);
        if (skillId == null) {
            System.out.println("Skill ID in slot " + slot + " is null. Maybe not equipped?");
            return false;
        }

        Skill skill = SkillRegistry.getSkillById(skillId);
        if (skill == null) {
            System.out.println("Skill ID '" + skillId + "' is not found in registry.");
            return false;
        }

        if (skillData.isOnCooldown(skillId)) {
            System.out.println("Skill '" + skillId + "' is on cooldown.");
            // Optional: Log or handle cooldown message here
            return false;
        }

        if (!skillData.hasLearned(skillId)) {
            System.out.println("Player hasn't learned skill: " + skillId);
            return false;
        }


        return skill.execute(player, this);  // Skill handles its own cooldown if needed
    }


    // Call this method regularly, e.g. every tick, to reduce cooldown timers
    public void tickCooldowns() {
        skillData.tickCooldowns();
    }

    public void serializeNBT(CompoundTag tag) {
        CompoundTag cooldownsTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : cooldowns.entrySet()) {
            cooldownsTag.putInt(entry.getKey(), entry.getValue());
        }
        tag.put("Cooldowns", cooldownsTag);
    }
    private final Map<String, Integer> cooldowns = new java.util.HashMap<>();


    public void deserializeNBT(CompoundTag tag) {
        cooldowns.clear();
        CompoundTag cooldownsTag = tag.getCompound("Cooldowns");
        for (String key : cooldownsTag.getAllKeys()) {
            cooldowns.put(key, cooldownsTag.getInt(key));
        }
    }
}
