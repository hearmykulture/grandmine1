package net.kulture.grandmine.combat.player;

import net.kulture.grandmine.combat.skills.Skill;
import net.kulture.grandmine.combat.skills.SkillRegistry;
import net.kulture.grandmine.combat.CombatStyle;
import net.kulture.grandmine.combat.CombatStyles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class SkillManager {

    private final Player player;
    private final PlayerSkillData skillData;

    private CombatStyle currentCombatStyle;  // <-- Add this field

    public SkillManager(Player player) {
        this.player = player;
        this.skillData = new PlayerSkillData();

        this.currentCombatStyle = null;  // or initialize to default if you want
    }

    // Save skill data and combat style to NBT
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();

        // Save skill data
        tag.put("SkillData", skillData.saveToNBT());

        // Save current combat style ID as string
        tag.putString("CurrentCombatStyle", currentCombatStyle != null ? currentCombatStyle.getId().toString() : "");

        return tag;
    }


    // Load skill data and combat style from NBT
    public void loadFromNBT(CompoundTag tag) {
        skillData.loadFromNBT(tag);  // Assuming PlayerSkillData has loadFromNBT()
        if (tag.contains("CurrentCombatStyle")) {
            String styleId = tag.getString("CurrentCombatStyle");
            CombatStyle style = CombatStyles.getById(styleId);  // You'll implement this method next
            setCurrentCombatStyle(style);
        }
    }


    // -----------------------------
    // Combat Style Getter/Setter
    // -----------------------------

    public void setCurrentCombatStyle(CombatStyle style) {
        this.currentCombatStyle = style;

        if (style == null) return;

        // Auto-learn default skills if not already known
        for (Skill skill : style.getDefaultSkillObjects()) {
            String skillId = skill.getId();
            if (!skillData.hasLearned(skillId)) {
                skillData.learnSkill(skillId, 1);
            }
        }

        // Auto-equip default skills if slots are empty or exceed new style’s slot limit
        List<String> currentEquipped = skillData.getAllEquippedSkills();
        int maxSlots = style.getMaxSkillSlots();

        // Clear if too many skills equipped or invalid ones exist
        boolean shouldReset = currentEquipped.size() > maxSlots ||
                currentEquipped.stream().anyMatch(id -> SkillRegistry.getSkillById(id) == null);

        if (shouldReset || currentEquipped.isEmpty()) {
            List<Skill> defaultSkills = style.getDefaultSkillObjects();
            int slotsToFill = Math.min(maxSlots, defaultSkills.size());

            for (int i = 0; i < slotsToFill; i++) {
                skillData.equipSkill(i, defaultSkills.get(i).getId());
            }

            // Clear extra slots if any
            for (int i = slotsToFill; i < currentEquipped.size(); i++) {
                skillData.equipSkill(i, null);
            }
        }
    }


    public CombatStyle getCurrentCombatStyle() {
        return this.currentCombatStyle;
    }

    // -----------------------------
    // Skill Data Accessor
    // -----------------------------

    public PlayerSkillData getData() {
        return this.skillData;
    }

    // -----------------------------
    // Skill Learning
    // -----------------------------

    public boolean learnSkill(Skill skill) {
        String skillId = skill.getId();

        if (skillData.hasLearned(skillId)) {
            return false;
        }

        for (String prereq : skill.getPrerequisites()) {
            if (!skillData.hasLearned(prereq)) {
                return false;
            }
        }

        boolean unlocked = false;
        for (CombatStyle style : skill.getCombatStyles()) {
            if (style.isUnlockedBy(player)) {
                unlocked = true;
                break;
            }
        }

        if (!unlocked) {
            return false;
        }

        skillData.learnSkill(skillId, 1);
        return true;
    }

    // -----------------------------
    // Skill Equipping
    // -----------------------------

    public boolean equipSkill(int slot, Skill skill) {
        if (!skillData.hasLearned(skill.getId())) {
            return false;
        }

        // Optional: Validate slot range or allowed slots for current style
        skillData.equipSkill(slot, skill.getId());
        return true;
    }

    // -----------------------------
    // Skill Use
    // -----------------------------

    public boolean useSkill(int slot) {
        String skillId = skillData.getEquippedSkill(slot);
        if (skillId == null) return false;

        Skill skill = SkillRegistry.getSkillById(skillId);
        if (skill == null) return false;

        if (skillData.isOnCooldown(skillId)) return false;

        // TODO: Hook in stamina/haki check system
        // TODO: Call animation & damage logic here (e.g., Epic Fight API)

        // Set cooldown
        skillData.setCooldown(skillId, 100);

        return true;
    }

}

