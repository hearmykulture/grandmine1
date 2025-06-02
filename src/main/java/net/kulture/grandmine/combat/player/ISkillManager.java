package net.kulture.grandmine.combat.player;

import net.minecraft.nbt.CompoundTag;

public interface ISkillManager {
    CompoundTag serializeNBT();
    void deserializeNBT(CompoundTag nbt);

    // Optionally, add methods to access your skill data here
    PlayerSkillData getSkillData();
}
