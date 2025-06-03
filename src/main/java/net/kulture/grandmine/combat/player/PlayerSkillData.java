package net.kulture.grandmine.combat.player;

import net.kulture.grandmine.combat.skills.Skill;
import net.kulture.grandmine.combat.skills.SkillRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class PlayerSkillData {

    private final Map<String, Integer> learnedSkills = new HashMap<>();
    private final List<String> equippedSkillIds = new ArrayList<>();
    private final Map<String, Integer> skillCooldowns = new HashMap<>();
    private SkillManager manager;
    public void setManager(SkillManager manager) {
        this.manager = manager;
    }

    private final Player player;

    public PlayerSkillData(Player player) {
        this.player = player;
    }


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
        if (skillId == null) {
            System.out.println("LEARN FAILED: skillId is null.");
            return;
        }

        if (!hasLearned(skillId)) {
            learnedSkills.put(skillId, level);
            System.out.println("LEARNED SKILL: " + skillId + " at level " + level);
        } else {
            System.out.println("SKILL ALREADY LEARNED: " + skillId);
        }
    }

    public void upgradeSkill(String skillId) {
        learnedSkills.put(skillId, getSkillLevel(skillId) + 1);
    }

    // --------------------------
    // EQUIPPED SKILLS
    // --------------------------

    public void equipSkill(int slot, String skillId) {
        System.out.println("EQUIP: Setting slot " + slot + " to " + skillId);
        System.out.println("EQUIPPED LIST: " + equippedSkillIds);

        System.out.println("EquipSkill called for slot " + slot + " with ID: " + skillId);
        if (slot < 0 || slot >= equippedSkillIds.size()) {
            System.out.println("Invalid slot: " + slot);
            return;
        }

        equippedSkillIds.set(slot, skillId);
        System.out.println("Skill equipped: " + skillId + " in slot " + slot);


        if (skillId == null) {
            System.out.println("EQUIP FAILED: skillId is null.");
            return;
        }

        if (!hasLearned(skillId)) {
            System.out.println("EQUIP FAILED: Skill not learned - " + skillId);
            return;
        }

        ensureEquippedListSize(slot + 1);
        equippedSkillIds.set(slot, skillId);
        System.out.println("EQUIPPED SKILL: " + skillId + " to slot " + slot);
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
    // SKILL USAGE
    // --------------------------

    public boolean useSkill(int slot) {
        System.out.println("USE: Slot " + slot + " contains: " + getEquippedSkill(slot));
        System.out.println("FULL EQUIPPED LIST: " + equippedSkillIds);
        String skillId = getEquippedSkill(slot);
        System.out.println("Trying to use skill in slot " + slot + ": " + skillId);

        if (skillId == null) {
            System.out.println("USE FAILED: No skill equipped in slot " + slot);
            return false;
        }

        Skill skill = SkillRegistry.getSkillById(skillId);
        if (skill == null) {
            System.out.println("USE FAILED: Skill not found in registry - " + skillId);
            return false;
        }

        if (isOnCooldown(skillId)) {
            System.out.println("USE FAILED: Skill on cooldown - " + skillId);
            return false;
        }

        // Execute the skill logic (make sure your Skill class implements this method)
        boolean result = skill.execute(player, manager);


        if (result) {
            setCooldown(skillId, (int) skill.getCooldownSeconds());
            System.out.println("USE SUCCESS: Skill used - " + skillId);
        } else {
            System.out.println("USE FAILED: Skill execution returned false - " + skillId);
        }

        return result;
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
        skillCooldowns.replaceAll((skillId, ticks) -> Math.max(0, ticks - 1));
    }

    public void tick() {
        tickCooldowns();
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

        if (tag.contains("LearnedSkills")) {
            CompoundTag skillsTag = tag.getCompound("LearnedSkills");
            for (String key : skillsTag.getAllKeys()) {
                learnedSkills.put(key, skillsTag.getInt(key));
            }
        }

        if (tag.contains("SkillCooldowns")) {
            CompoundTag cooldownsTag = tag.getCompound("SkillCooldowns");
            for (String key : cooldownsTag.getAllKeys()) {
                skillCooldowns.put(key, cooldownsTag.getInt(key));
            }
        }

        int index = 0;
        while (tag.contains("EquippedSkill_" + index)) {
            String id = tag.getString("EquippedSkill_" + index);
            ensureEquippedListSize(index + 1);
            equippedSkillIds.set(index, id.isEmpty() ? null : id);
            index++;
        }
    }

    public boolean tryActivateSkill(String skillId, int cooldownTicks) {
        if (isOnCooldown(skillId)) {
            return false;
        }
        setCooldown(skillId, cooldownTicks);
        return true;
    }
}
