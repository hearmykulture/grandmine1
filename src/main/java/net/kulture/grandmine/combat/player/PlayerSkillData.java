package net.kulture.grandmine.combat.player;

import net.kulture.grandmine.combat.skills.Skill;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class PlayerSkillData {

    // Learned skills and their levels (e.g., {"soru": 2, "geppo": 1})
    private final Map<String, Integer> learnedSkills = new HashMap<>();

    // Currently equipped skills (e.g., slot 0 = "soru", slot 1 = "rankyaku")
    private final List<String> equippedSkillIds = new ArrayList<>();

    // Cooldowns per skill ID (track time remaining)
    private final Map<String, Integer> skillCooldowns = new HashMap<>();

    public PlayerSkillData() {}

    // --------------------------
    // LEARNED SKILLS
    // --------------------------

    public boolean hasLearned(String skillId) {
        return learnedSkills.containsKey(skillId);
    }

    public int getSkillLevel(String skillId) {
        return learnedSkills.getOrDefault(skillId, 0);
    }

    public void learnSkill(String skillId, int level) {
        learnedSkills.put(skillId, level);
    }

    public void upgradeSkill(String skillId) {
        learnedSkills.put(skillId, getSkillLevel(skillId) + 1);
    }

    // --------------------------
    // EQUIPPED SKILLS
    // --------------------------

    public void equipSkill(int slot, String skillId) {
        ensureEquippedListSize(slot + 1);
        equippedSkillIds.set(slot, skillId);
    }

    public String getEquippedSkill(int slot) {
        return slot < equippedSkillIds.size() ? equippedSkillIds.get(slot) : null;
    }

    public List<String> getAllEquippedSkills() {
        return equippedSkillIds;
    }

    private void ensureEquippedListSize(int size) {
        while (equippedSkillIds.size() < size) {
            equippedSkillIds.add(null);
        }
    }

    // --------------------------
    // COOLDOWNS
    // --------------------------

    public boolean isOnCooldown(String skillId) {
        return skillCooldowns.getOrDefault(skillId, 0) > 0;
    }

    public void setCooldown(String skillId, int ticks) {
        skillCooldowns.put(skillId, ticks);
    }

    public void tickCooldowns() {
        skillCooldowns.replaceAll((skillId, timeLeft) -> Math.max(0, timeLeft - 1));
    }

    // --------------------------
    // NBT SAVE/LOAD
    // --------------------------

    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();

        CompoundTag skillsTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : learnedSkills.entrySet()) {
            skillsTag.putInt(entry.getKey(), entry.getValue());
        }
        tag.put("LearnedSkills", skillsTag);

        CompoundTag cooldownsTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : skillCooldowns.entrySet()) {
            cooldownsTag.putInt(entry.getKey(), entry.getValue());
        }
        tag.put("SkillCooldowns", cooldownsTag);

        for (int i = 0; i < equippedSkillIds.size(); i++) {
            tag.putString("EquippedSkill_" + i, equippedSkillIds.get(i) != null ? equippedSkillIds.get(i) : "");
        }

        return tag;
    }

    public void loadFromNBT(CompoundTag tag) {
        learnedSkills.clear();
        skillCooldowns.clear();
        equippedSkillIds.clear();

        CompoundTag skillsTag = tag.getCompound("LearnedSkills");
        for (String key : skillsTag.getAllKeys()) {
            learnedSkills.put(key, skillsTag.getInt(key));
        }

        CompoundTag cooldownsTag = tag.getCompound("SkillCooldowns");
        for (String key : cooldownsTag.getAllKeys()) {
            skillCooldowns.put(key, cooldownsTag.getInt(key));
        }

        int index = 0;
        while (tag.contains("EquippedSkill_" + index)) {
            String id = tag.getString("EquippedSkill_" + index);
            equippedSkillIds.add(id.isEmpty() ? null : id);
            index++;
        }
    }

}
